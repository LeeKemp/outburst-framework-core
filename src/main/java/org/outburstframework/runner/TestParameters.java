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

import org.outburstframework.runner.monitor.ResultMonitor;
import org.outburstframework.runner.monitor.SLF4JResultMonitor;

/**
 * Contains the settings and defaults for the test run such as run time and length etc. 
 * 
 * @author Lee Kemp
 *
 */
public class TestParameters {
	
	@SuppressWarnings("unchecked")
	private Class clazz;
	
	// Parameter defaults
	public static final long PROPERTY_TEST_LENGTH = -1; // forever
	public static final long PROPERTY_TEST_RUNS = 1; // only run once
	public static final long PROPERTY_WAIT_BEFORE_RUN_LENGTH = 0; // don't wait
	public static final long PROPERTY_WAIT_BETWEEN_TEST_START_LENGTHS = 0; // don't wait
	public static final long PROPERTY_RESULT_INTERVAL = 2000l; // 2 seconds
	
	// Set the default values
	private long testLength = PROPERTY_TEST_LENGTH;
	private long testRuns = PROPERTY_TEST_RUNS;
	private long waitBeforeRunLength = PROPERTY_WAIT_BEFORE_RUN_LENGTH;
	private long waitBetweenTestStartsLength = PROPERTY_WAIT_BETWEEN_TEST_START_LENGTHS;
	private long resultInterval = PROPERTY_RESULT_INTERVAL;
	private ResultMonitor resultMonitor = new SLF4JResultMonitor();
	
	public TestParameters() {  }
	
	/**
	 * Constructor that populates the TestProperties with the values 
	 * from the annotation. 
	 * 
	 * @param testParametersAnnotation
	 * 		Annotation to retrieve the properties from
	 */
	public TestParameters(OutburstTestParameters testParametersAnnotation)
	{
		this.setTestLength(testParametersAnnotation.testLength());
		this.setTestRuns(testParametersAnnotation.testRuns());
		this.setWaitBeforeRunLength(testParametersAnnotation.waitBeforeRunLength());
		this.setWaitBetweenTestStartsLength(testParametersAnnotation.waitBetweenTestStartsLength());
		this.setResultInterval(testParametersAnnotation.resultInterval());
	}
	
	@SuppressWarnings("unchecked")
	public Class getClazz() {
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	public long getTestLength() {
		return testLength;
	}
	public void setTestLength(long testLength) {
		this.testLength = testLength;
	}
	public long getTestRuns() {
		return testRuns;
	}
	public void setTestRuns(long testRuns) {
		this.testRuns = testRuns;
	}
	public long getWaitBeforeRunLength() {
		return waitBeforeRunLength;
	}
	public void setWaitBeforeRunLength(long waitBeforeRunLength) {
		this.waitBeforeRunLength = waitBeforeRunLength;
	}
	public long getWaitBetweenTestStartsLength() {
		return waitBetweenTestStartsLength;
	}
	public void setWaitBetweenTestStartsLength(long waitBetweenTestStartsLength) {
		this.waitBetweenTestStartsLength = waitBetweenTestStartsLength;
	}
	public long getResultInterval() {
		return resultInterval;
	}
	public void setResultInterval(long resultInterval) {
		this.resultInterval = resultInterval;
	}
	public ResultMonitor getResultMonitor() {
		return resultMonitor;
	}
	public void setResultMonitor(ResultMonitor resultMonitor) {
		this.resultMonitor = resultMonitor;
	}
	
}
