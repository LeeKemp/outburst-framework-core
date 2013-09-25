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

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.runner.notification.Failure;

/**
 * Aggregates run data and tracks failures for a specific test method.  
 * 
 * @author Lee Kemp
 *
 */
public class TestData {

	private final AtomicLong count = new AtomicLong();
	private final AtomicLong ignoreCount= new AtomicLong();
	private final AtomicLong failureCount = new AtomicLong();
	
	private Collection<Failure> failures = new LinkedBlockingQueue<Failure>();
	
	private final String description;

	public TestData(String description) {
		super();
		this.description = description;
	}
	
	public void incrementFailure(Failure failure)
	{
		this.failureCount.incrementAndGet();
		this.failures.add(failure);
	}
	
	public void incrementCount()
	{
		this.count.incrementAndGet();
	}

	public void incrementIgnore()
	{
		this.ignoreCount.incrementAndGet();
	}
	
	public String getDescription()
	{
		return description;
	}

	public AtomicLong getCount()
	{
		return count;
	}

	public AtomicLong getIgnoreCount()
	{
		return ignoreCount;
	}

	public AtomicLong getFailureCount()
	{
		return failureCount;
	}

    public Collection<Failure> getFailures() {
        return failures;
    }

    public void setFailures(Collection<Failure> failures) {
        this.failures = failures;
    }
}
