/*
 * Copyright 2009-2010 Lee Kemp
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
package org.outburstframework.runner.junit;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends on the functionality of the JUnit RunListener to support tracking concurrent 
 * execution of tests.  
 * 
 * @author Lee Kemp
 *
 */
public class OutburstJUnitResultListener extends RunListener {

	private static final Logger log = LoggerFactory.getLogger(OutburstJUnitResultListener.class);
	
	// description.getDisplayName is used as the key
	private final ConcurrentHashMap<String, TestData> testDataMap = new ConcurrentHashMap<String, TestData>();
	
	private Result result;
 
	@Override
	public void testIgnored(Description description) throws Exception {
		//ignoreCount.incrementAndGet();
		TestData testData = getOrCreateTestData(description);
		testData.incrementIgnore();
		
		log.debug("Ignored - {} ", description);
	}

	@Override
	public void testRunFinished(Result result) throws Exception {
		this.result = result;
		
		log.debug("Success = "+result.wasSuccessful());
		log.debug("Failures = "+result.getFailureCount());
		log.debug("Runs = "+result.getRunCount());
		log.debug("Time = "+result.getRunTime());
		
	}

	@Override
	public void testRunStarted(Description description)
			throws Exception {	
		log.debug("Run Started - {} ", description);
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		TestData testData = getOrCreateTestData(failure.getDescription());
		testData.incrementCount();
		
		log.debug("Failure - {} ", failure.getDescription());
	}

	@Override
	public void testFinished(Description description) throws Exception {
		TestData testData = getOrCreateTestData(description);
		testData.incrementCount();
		
		log.debug("Finished - {} ", description);
	}
 
	TestData getOrCreateTestData(Description description)
	{
		TestData value = testDataMap.get(description.getDisplayName());
		if(value != null)
			return value;

		// another thread may have just added after our null check
		value = new TestData(description.getDisplayName());
		TestData oldValue = testDataMap.putIfAbsent(description.getDisplayName(), value);
		if(oldValue == null)
		{
			return value;
		}
		else
		{
			return oldValue;
		}

	}

	public ConcurrentHashMap<String, TestData> getTestDataMap() {
		return testDataMap;
	}

	public Result getResult() {
		return result;
	}
	
	
}
