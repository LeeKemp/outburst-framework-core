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

import org.outburstframework.runner.junit.OutburstJUnitResultListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread used to control the output from the results monitors.
 * 
 * @author Lee Kemp
 *
 */
public class MonitorThread extends Thread {
	
	private static final Logger log = LoggerFactory.getLogger(MonitorThread.class.getName());
	
	private final ResultMonitor resultMonitor;
	private final long resultInterval;
	private final OutburstJUnitResultListener resultListener;
	
	private volatile boolean running = false;
	
	public MonitorThread(ResultMonitor resultMonitor, OutburstJUnitResultListener resultListener, long resultInterval) {
		this.setName("Result-Monitor");
		this.resultMonitor = resultMonitor;
		this.resultListener = resultListener;
		this.resultInterval = resultInterval;
	}
	
	public void startMonitoring()
	{
		running = true;
		this.start();
	}
	
	public void stopMonitoring()
	{
		running = false;
		resultMonitor.processFinalResults(resultListener.getResult(), resultListener.getTestDataMap());
	}
	
	@Override
	public void run() {
		// Monitor the test threads
		running = true;
		while (running) {
			
			resultMonitor.processResults(resultListener.getTestDataMap());
		
			try {
				Thread.sleep(resultInterval);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}

	}
}
