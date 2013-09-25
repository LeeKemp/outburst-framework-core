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
package org.outburstframework.runner;

import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.outburstframework.runner.junit.OutburstJUnitResultListener;
import org.outburstframework.runner.junit.OutburstJUnitRunner;
import org.outburstframework.runner.monitor.MonitorThread;

/**
 * 
 * @author Lee Kemp
 *
 */
public class TestRunner {

	private final TestParameters testParameters;
	
	public TestRunner(TestParameters testParameters) {
		this.testParameters = testParameters;
	}
	
	public void execute()  throws Exception {
		Runner runner = new OutburstJUnitRunner(testParameters.getClazz());
		
		// Need to have the result and resultListener so that we get the final results 
		// and so tha the fileTestRunFinished method can be called
		Result result= new Result();
		RunListener resultListener= result.createListener();
		
		OutburstJUnitResultListener outburstJUnitResultListener = new OutburstJUnitResultListener();
		
		// Add all the listeners to the run notifier
		RunNotifier notifier = new RunNotifier();
		notifier.addFirstListener(resultListener);
		notifier.addListener( outburstJUnitResultListener);
		
		// Setup the monitoring
		MonitorThread monitor = new MonitorThread(testParameters.getResultMonitor(), outburstJUnitResultListener, testParameters.getResultInterval());
		
		//
		// Start running the tests
		//
		monitor.startMonitoring();
		
		notifier.fireTestRunStarted(runner.getDescription());
		runner.run(notifier); // Blocks here until tests are finished
		notifier.fireTestRunFinished(result);		
		
		monitor.stopMonitoring();
		
	}

}
