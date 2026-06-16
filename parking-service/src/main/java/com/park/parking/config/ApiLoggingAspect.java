package com.park.parking.config;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.park.parking.util.UserContext;

@Aspect
@Component
public class ApiLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingAspect.class);
    
    @Around("execution(* com.park.parking.controller..*(..))")
    public Object logMethodAndTime(ProceedingJoinPoint pjp) throws Throwable {
        // Access method info like JoinPoint
        Object[] args = pjp.getArgs();
        String username = UserContext.getUser();
       // System.out.println("Method: " + pjp.getSignature());
        //System.out.println("Args: " + Arrays.toString(pjp.getArgs()));

        logger.info("API hit: {}, Username: {}, Args: {}", pjp.getSignature(), username, Arrays.toString(args));

        // Proceed with execution
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("API completed: {}, Username: {}, Response: {}, Execution time: {} ms",
                pjp.getSignature(), username, result, (end - start));
        System.out.println("Execution time: " + (end - start) + "ms");

        return result;
    }
}
