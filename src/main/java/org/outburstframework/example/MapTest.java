/*
 * Copyright 2009 - 2010 Lee Kemp
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.outburstframework.example;

import static org.junit.Assert.assertFalse;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.outburstframework.runner.OutburstTestParameters;
import org.outburstframework.runner.junit.OutburstJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test the performance of a map implementation concurrently 
 * being read, written to, removed from and iterated over.
 * 
 * @author Lee Kemp
 *
 */
@RunWith(OutburstJUnitRunner.class)
@OutburstTestParameters(testRuns = 5)
public class MapTest {

	private static final Logger log = LoggerFactory.getLogger(MapTest.class);
	
	private static Map<Integer, Long> map; 
	
	private static final int MAX_NUMBER = 16000;
	
	/**
	 * The Atomic Integers are used to keep track of where the next operation should access the map. 
	 * This is so that the entire size of the map gets used and that each bucket in the map gets 
	 * used. 
	 */
	private final AtomicInteger deleteCount = new AtomicInteger();
	private final AtomicInteger readCount = new AtomicInteger();
	private final AtomicInteger writeCount = new AtomicInteger();
	
	@BeforeClass
	public static void setUp()
	{
		log.debug("Setting up map test");
		map = new ConcurrentHashMap<Integer, Long>();
	}
	
	@Test
	public void put()
	{
		int count = writeCount.incrementAndGet();
		if(count >= MAX_NUMBER - 1)
		{
			deleteCount.set(0);
			count = 0;
		}
			
		map.put(count, System.currentTimeMillis());
	}
	
	@Test()
	public void get() 
	{
		int count = readCount.decrementAndGet();
		if(count <= 1)
		{
			readCount.set(MAX_NUMBER);
			count = MAX_NUMBER;
		}
		
		// Get middle item from the list
		Long value = map.get(count);

		assertFalse(value != null && value == -1);
	}
	
	@Test
	public void remove()
	{
		int count = deleteCount.incrementAndGet();
		
		if(count >= MAX_NUMBER - 1)
		{
			deleteCount.set(0);
			count = 0;
		}
		
		map.remove(count);
	}
	
	@Test
	public void iterate() 
	{
		Long value = 0l;
		
		for(Entry<Integer, Long> s:map.entrySet())
		{
			value = s.getValue();
		}
		
		assertFalse(value == -1);
	}
}
