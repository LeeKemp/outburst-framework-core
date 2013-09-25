package org.outburstframework.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.outburstframework.runner.junit.OutburstJUnitRunner;
import org.outburstframework.test.util.http.HTTPUtils;
import static org.junit.Assert.*;

// Un-comment out the line bellow to run test using the outburst test runner
// @RunWith(OutburstJUnitRunner.class)
public class FasterTests
{
	
	@Test()
	public void googleCom() throws Exception 
	{
		String result = HTTPUtils.HTTPGet("http://www.google.com/");
		boolean contains = result.contains("google");
		assertTrue(contains);
	}
	
	@Test()
	public void yahooCom() throws Exception 
	{
		String result = HTTPUtils.HTTPGet("http://www.yahoo.com/");
		boolean contains = result.contains("yahoo");
		assertTrue(contains);
	}
	
	@Test()
	public void amazonCom() throws Exception 
	{
		String result = HTTPUtils.HTTPGet("http://www.amazon.com/");
		boolean contains = result.contains("amazon");
		assertTrue(contains);
	}
	
	@Test()
	public void sunCom() throws Exception 
	{
		String result = HTTPUtils.HTTPGet("http://www.sun.com/");
		boolean contains = result.contains("sun");
		assertTrue(contains);
	}
}
