package edu.weber.bestgroupgroup2.Newspaperbutworse.Logging;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.aspectj.lang.Signature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.aspectj.lang.ProceedingJoinPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.weber.bestgroupgroup2.Newspaperbutworse.aop.logging.LoggingAspect;


@RunWith(MockitoJUnitRunner.class)
public class LogginAspectTest {
	
	LoggingAspect aspect;
	@Mock
	ObjectMapper mapper;
	@Mock
	ProceedingJoinPoint jPoint;
	@Mock
	Signature s;
	
	
	@Before
	public void setup() {
		aspect = new LoggingAspect(mapper);
	}
	
	@Test
	public void testLogAround() {
		Object expected = null;
		String sig = "edu.weber.bestgroupgroup2.Newspaperbutworse.somethingelse";
		when(jPoint.getSignature()).thenReturn(s);
		when(s.getDeclaringTypeName()).thenReturn(sig);
		when(s.getName()).thenReturn("");
		
		Object actual =  aspect.logAround(jPoint);
		
		Assert.assertEquals(expected, actual);
	}
}
	

