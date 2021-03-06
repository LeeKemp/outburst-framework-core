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
package org.outburstframework.runner.monitor;

import java.util.Map;

import org.junit.runner.Result;
import org.outburstframework.runner.junit.TestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints test output via SLF4J.
 * 
 * @author Lee Kemp
 *
 */
public class SLF4JResultMonitor implements ResultMonitor {

	private static final Logger log =  LoggerFactory.getLogger(SLF4JResultMonitor.class);
	
	//@Override
	public void processFinalResults( final Result result,
			final Map<String, TestData> testDataMap) {
		log.info("Processing Final Results");
	}

	//@Override
	public void processResults(final Map<String, TestData> testDataMap) {
		log.info("Processing Results");
	}

}
