package com.donatoordep.rg.code.aop.jpaFieldValidator.aspects;

import com.donatoordep.rg.code.aop.PointcutDefinition;
import com.donatoordep.rg.code.aop.jpaFieldValidator.annotations.JpaFieldlValidator;
import com.donatoordep.rg.code.aop.jpaFieldValidator.specifications.FieldValidatorSpecification;
import com.donatoordep.rg.code.repositories.UserSpecification;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
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

    private final UserSpecification userRepository;

    @Autowired
    private List<FieldValidatorSpecification> validations;

    @Autowired
    public JpaFieldValidationAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("execution(* com.donatoordep.rg.code.controllers..*(..))")
    public void jpaFieldValidatorEmailHandle(JoinPoint joinPoint) {
        Object dto = joinPoint.getArgs()[0];
        List<Field> fieldsAnnotates = this.annotateWith(JpaFieldlValidator.class, this.classFields(dto));

        fieldsAnnotates.forEach(field -> {
            JpaFieldlValidator annotation = field.getAnnotation(JpaFieldlValidator.class);
            if (annotation.unique()) {
                field.setAccessible(true);
                validations.forEach(validator -> {
                    try {
                        if (validator.isValid(field.get(dto))) {
                            throw validator.getException();
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
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