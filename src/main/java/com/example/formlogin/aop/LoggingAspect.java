package com.example.formlogin.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.formlogin.controller..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        logger.info("Before executing method: {}", joinPoint.getSignature().getName());
    }
    @After("execution(* org.example.formlogin.service..*(..))")
    public void logAfterService(JoinPoint joinPoint) {
        logger.info("After executing method: {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* com.example.formlogin.service..*(..))")
    public Object logAroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Before method: {}", proceedingJoinPoint.getSignature().getName());

        // Proceed with the method execution
        Object result = proceedingJoinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        logger.info("After method: {} executed in {} ms", proceedingJoinPoint.getSignature().getName(), duration);
        return result;
    }
}

