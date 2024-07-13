package com.donatoordep.rg.code.services.validations.user.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutDefinition implements SpecificationPointcut {

    @Override
    @Pointcut("within(com.donatoordep.rg.code.services.*)")
    public void inServiceLayer() {

    }

    @Override
    @Pointcut("within(com.donatoordep.rg.code.controllers.*)")
    public void inControllerLayer() {
    }
}
