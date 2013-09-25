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

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.RunnerScheduler;
import org.outburstframework.runner.TestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Outburst implementation of the JUnit n.
 * <p>
 * RunnerScheduler manages the creation of and the starting and stopping of the TestThreads
 * that execute the tests.  
 * 
 * @author Lee Kemp
 *
 */
public class OutburstJUnitRunScheduler implements RunnerScheduler {

	private static final Logger log = LoggerFactory.getLogger(OutburstJUnitRunScheduler.class);
	
	private final TestParameters testParameters;
	
	// List of the threads that are running the tests.
	private final List<TestThread> testThreads = new ArrayList<TestThread>();
	
	public OutburstJUnitRunScheduler(TestParameters testParameters) {
		super();
		this.testParameters = testParameters;
	}

	public void finished() {
		// do nothing
		log.debug("Finished called - doing nothing about it");
	}

	/**
	 * Create thread for each of the tests
	 */
	public void schedule(Runnable childStatement) {
		TestThread testThread = new TestThread(childStatement, testParameters.getTestRuns());
		testThread.setName("outburst-JUnitScheduler-"+childStatement);
		testThread.start();
		testThreads.add(testThread);
		log.debug("Created test thread "+childStatement);
	}
	
	public void beginTestEvaluation()
	{
		for(TestThread testThread:testThreads)
		{
			testThread.executeTests();
			log.debug("Starting test thread "+testThread.getName());
		}
	}
	
	public void stopTestEvaluation()
	{
		for(TestThread testThread:testThreads)
		{
			testThread.stopTestExecutionAndThread();
			log.debug("Stopping test thread "+testThread.getName());
		}
		
		// Make sure they have all stopped before it returns
		while(true)
		{
			boolean isATestStillAlive = false;
			for(TestThread testThread:testThreads)
			{
				if(testThread.isAlive())
				{
					isATestStillAlive = true;
					break;
				}
			}
			
			if(!isATestStillAlive)
			{
				log.debug("[{}] test threads are now inactive.", testThreads);
				return;
			}
			else
			{
				log.debug("Waiting for threads to finish...");
				Thread.yield();
			}
		}
	}
	
	/**
	 * Method returns if:
	 * - The test has run for its duration. 
	 * - All the tests are no longer active.
	 * <p>
	 * Note: This method does not stop and test threads, It just waits for one of the return conditions.
	 *
	 * @param testDuration
     *          The maximum duration the test should run for
	 */
	public void monitorAndControlTestExecution(long testDuration)
	{
		long testEndTime = System.currentTimeMillis() + testDuration;
		log.debug("Ending time = {} ", testEndTime);
		while(true)
		{
			// Test duration of test.
			if(testDuration != -1)
			{
				 // Test is not running forever, check the end time.
				if(System.currentTimeMillis() >= testEndTime)
				{
					log.debug("Test execution has reached its duration [{}].", testDuration);
					return; 
				}	
			}
			
			// Test thread activity.
			boolean isATestStillAlive = false;
			for(TestThread testThread:testThreads)
			{
				if(testThread.isAlive())
				{
					isATestStillAlive = true;
					break;
				}
			}
			
			if(!isATestStillAlive)
			{
				log.debug("No test threads are still active.");
				return;
			}
			
			Thread.yield();
		} // While loop
	}

}
