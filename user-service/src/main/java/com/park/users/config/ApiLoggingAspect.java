package com.park.users.config;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingAspect.class);
    
    @Around("execution(* com.park.users.controller..*(..))")
    public Object logMethodAndTime(ProceedingJoinPoint pjp) throws Throwable {
        // Access method info like JoinPoint
        Object[] args = pjp.getArgs();
       
        logger.info("API hit: {}, Args: {}", pjp.getSignature(), Arrays.toString(args));

        // Proceed with execution
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("API completed: {}, Response: {}, Execution time: {} ms",
                pjp.getSignature(), result, (end - start));
        logger.info("Execution time: " + (end - start) + "ms");

        return result;
    }
}
