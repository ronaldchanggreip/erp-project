package com.greip.core.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by ronaldchang on 15/11/16.
 */
@Aspect
@Component
public class ExceptionServiceAspect {

    private static Logger LOGGER = Logger.getLogger(ExceptionServiceAspect.class);

    @Pointcut("execution(* pe.com.greip.service.*.*(..))")
    protected void exceptionService() {
    }

    @Around("exceptionService()")
    public Object catchException(ProceedingJoinPoint joinpoint) {
        Object exit = null;

        try {
            exit = joinpoint.proceed();
        } catch (Throwable throwable) {
            LOGGER.error(throwable);
        }


        return exit;
    }

}
