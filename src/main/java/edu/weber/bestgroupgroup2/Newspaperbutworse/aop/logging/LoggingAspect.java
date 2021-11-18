package edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAspect {

	private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	private ObjectMapper objectMapper;


	@Autowired
	public LoggingAspect (ObjectMapper objectMapper) 
	{ 
		this.objectMapper = objectMapper; 
	}


	@Around("@annotation(edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.Log)")
	public Object logAround(ProceedingJoinPoint joinPoint) {
		Signature s = joinPoint.getSignature();
		String classPath = s.getDeclaringTypeName();
		String className = classPath.split("edu.weber.bestgroupgroup2.Newspaperbutworse.")[1];
		String methodName = s.getName();
		String callPath = className + "  " + methodName + "() ";
		Object value = null;



		try 
		{
			logger.info("Entering " + callPath);
			value = joinPoint.proceed();
		} 
		catch(Throwable throwable) 
		{
			logger.error("Exception Thrown In " + callPath, throwable);
		}

		logger.info("Leaving " + callPath);
				return value;
	}
}
