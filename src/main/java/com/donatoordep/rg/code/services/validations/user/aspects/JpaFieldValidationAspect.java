package com.donatoordep.rg.code.services.validations.user.aspects;

import com.donatoordep.rg.code.exceptions.ONBEmailHasExistsInDatabaseException;
import com.donatoordep.rg.code.repositories.UserRepository;
import com.donatoordep.rg.code.services.validations.user.PointcutDefinition;
import com.donatoordep.rg.code.services.validations.user.annotations.JpaFieldlValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
public class JpaFieldValidationAspect extends PointcutDefinition {

    private final UserRepository userRepository;

    @Autowired
    public JpaFieldValidationAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("execution(* com.donatoordep.rg.code.controllers..*(..))")
    public void jpaFieldValidatorEmailHandle(JoinPoint joinPoint) {
        Object dto = joinPoint.getArgs()[0];
        List<Field> fieldsAnnotates = this.annotateWith(JpaFieldlValidator.class, this.classFields(dto));

        fieldsAnnotates.forEach(field -> {
            if (field.getName().equals("email")) {
                if (field.getAnnotation(JpaFieldlValidator.class).unique()) {
                    field.setAccessible(true);
                    Object fieldValue;

                    try {
                        fieldValue = field.get(dto);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    if (fieldValue instanceof String && userRepository.existsByEmail((String) fieldValue)) {
                        throw new ONBEmailHasExistsInDatabaseException();
                    }
                }
            }
        });
    }

    private List<Field> classFields(Object type) {
        return Arrays.stream(type.getClass().getDeclaredFields()).toList();
    }

    private List<Field> annotateWith(Class<? extends Annotation> annotationClass, Collection<Field> collectionUnordered) {
        return collectionUnordered.stream().filter(field -> field.isAnnotationPresent(annotationClass)).toList();
    }
}