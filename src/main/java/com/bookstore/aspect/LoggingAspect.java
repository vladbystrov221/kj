package com.bookstore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.bookstore.service.*.*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.bookstore.controller.*.*(..))")
    public void controllerLayer() {}

    @Around("serviceLayer() || controllerLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        log.debug("Entering method: {}.{}() with arguments: {}", 
                  className, methodName, Arrays.toString(args));
        
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            
            log.debug("Exiting method: {}.{}() - Execution time: {} ms", 
                      className, methodName, (end - start));
            
            return result;
        } catch (Exception e) {
            long end = System.currentTimeMillis();
            
            log.error("Exception in method: {}.{}() - Execution time: {} ms - Error: {}", 
                      className, methodName, (end - start), e.getMessage());
            
            throw e;
        }
    }

    @AfterThrowing(pointcut = "serviceLayer() || controllerLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        log.error("Exception thrown in {}.{}: {}", className, methodName, ex.getMessage(), ex);
    }
} 