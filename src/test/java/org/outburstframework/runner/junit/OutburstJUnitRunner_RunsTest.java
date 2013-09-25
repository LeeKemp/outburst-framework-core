package org.outburstframework.runner.junit;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.outburstframework.runner.OutburstTestParameters;

import static org.junit.Assert.*;

@RunWith(OutburstJUnitRunner.class)
@OutburstTestParameters(testRuns=5)
public class OutburstJUnitRunner_RunsTest
{
	private static final int EXPECTED_RUN_COUNT = 10;
	
	private static final AtomicInteger runCount = new AtomicInteger();
	private static final AtomicInteger test1Count = new AtomicInteger();
	private static final AtomicInteger test2Count = new AtomicInteger();
	
	private static final AtomicInteger beforesCount = new AtomicInteger();
	private static final AtomicInteger aftersCount = new AtomicInteger();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		assertEquals(0, test1Count.get());
		assertEquals(0, test2Count.get());
		
		assertEquals(0, beforesCount.get());
		assertEquals(0, aftersCount.get());
		
		assertEquals(0, runCount.get());
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		assertEquals(EXPECTED_RUN_COUNT / 2, test1Count.get());
		assertEquals(EXPECTED_RUN_COUNT / 2, test2Count.get());
		
		assertEquals(EXPECTED_RUN_COUNT, beforesCount.get());
		assertEquals(EXPECTED_RUN_COUNT, aftersCount.get());
		
		assertEquals(EXPECTED_RUN_COUNT, runCount.get());
	}
	
	@Before
	public void before()
	{
		beforesCount.incrementAndGet();
	}
	
	@After
	public void after()
	{
		aftersCount.incrementAndGet();
	}
	
	@Test
	public void test1()
	{
		test1Count.incrementAndGet();
		runCount.incrementAndGet();
	}
	
	@Test
	public void test2()
	{
		test2Count.incrementAndGet();
		runCount.incrementAndGet();
	}



}
