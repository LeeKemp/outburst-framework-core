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
package org.outburstframework.runner.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread that executes the test.
 * 
 * @author Lee Kemp
 *
 */
class TestThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(TestThread.class);
	
	// The method that gets run in the test
	private Runnable childStatement = null;
	
	private final long testRuns;
	private long count = 0;
	
	private volatile boolean running;
	private volatile boolean executeTests;
	
	public TestThread(Runnable childStatement, long testRuns) {
		super();
		this.childStatement = childStatement;
		this.testRuns = testRuns;
		
		running = false;
		executeTests = false;
	}

	@Override
	public void run() {
		running = true;
		
		while(running)
		{
			if(executeTests)
			{
				childStatement.run();
				count++;
				
				// Was that the last test?
				if(testRuns != -1 && count == testRuns)
				{
						stopTestExecutionAndThread();
				}
			}
			else
			{
				Thread.yield();
			}
		}
		
	}
	
	public void stopTestExecutionAndThread()
	{
		log.debug("Stopping thread execution");
		this.executeTests = false;
		this.running = false;
	}

	/**
	 * This method should be called once the test thread has already been 
	 * started. It will cause the TestThread to start calling the tests.
	 * 
	 */
	public void executeTests()
	{
		log.debug("Starting thread execution");
		this.executeTests = true;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isExecuteTests()
	{
		return executeTests;
	}

	public void startTestExecution()
	{
		this.executeTests = true;
	}
	
}
