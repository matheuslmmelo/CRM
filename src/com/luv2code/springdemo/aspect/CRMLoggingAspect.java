package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class CRMLoggingAspect {
	private Logger logger =
	Logger.getLogger(CRMLoggingAspect.class.getName());
	
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage()||forServicePackage()||forDaoPackage()")
	private void forApp() {}
	
	@Before("forApp()")
	public void before(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		logger.info("====>> in @Before: calling method: "+method);
		
		Object[] args = joinPoint.getArgs();
		
		for (Object object : args) {
			logger.info("=====>> argument: "+object);
		}
	}
	
	@AfterReturning(
			pointcut = "forApp()",
			returning = "result"
	)
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String method = joinPoint.getSignature().toShortString();
		logger.info("====>> in @AfterReturning: from method: "+method);
		
		logger.info("====>> result: "+result);
	}
	
}
