package org.outburstframework.runner.junit;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.outburstframework.runner.OutburstTestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * Test running 2 tests for 10 seconds making sure that the tests run for at
 * least 10 seconds but no more then 12. 
 * 
 * @author Lee Kemp
 *
 */
@RunWith(OutburstJUnitRunner.class)
@OutburstTestParameters(testLength=10000, testRuns=0)
public class OutburstJUnitRunner_lengthTest
{
    private static final Logger log = LoggerFactory.getLogger(OutburstJUnitRunner_lengthTest.class);

	private static final int EXPECTED_RUN_LENGTH = 10000;
	
	private static final int RUN_LENGTH_MAX = EXPECTED_RUN_LENGTH + 5000;
	private static final int RUN_LENGTH_MIN = EXPECTED_RUN_LENGTH;
	
	private static long startTime;
	
	private static final AtomicInteger runCount = new AtomicInteger();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		startTime = System.currentTimeMillis();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		long length = System.currentTimeMillis() - startTime;
		
		assertTrue(length < RUN_LENGTH_MAX);
		assertTrue(length > RUN_LENGTH_MIN);		
	}
	
	@Test
	public void test1()
	{
		runCount.incrementAndGet();
	}
	
	@Test
	public void test2()
	{
		runCount.incrementAndGet();
	}

}
