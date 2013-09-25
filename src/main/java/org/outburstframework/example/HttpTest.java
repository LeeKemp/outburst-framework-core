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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.outburstframework.runner.OutburstTestParameters;
import org.outburstframework.runner.junit.OutburstJUnitRunner;
import org.outburstframework.test.util.http.HTTPUtils;

/**
 * 
 * @author Lee Kemp
 * 
 */
@RunWith(OutburstJUnitRunner.class)
@OutburstTestParameters(testRuns=40, testLength=30000)
public class HttpTest 
{

	@Test()
	public void googleCom() throws Exception 
	{
		HTTPUtils.HTTPGet("http://www.google.com/");
	}

	@Test()
	public void yahooCom() throws Exception 
	{
		HTTPUtils.HTTPGet("http://www.yahoo.com/");
	}
	
	@Test()
	public void amazonCom() throws Exception 
	{
		HTTPUtils.HTTPGet("http://www.amazon.com/");
	}
	
	@Test()
	public void sunCom() throws Exception 
	{
		HTTPUtils.HTTPGet("http://www.sun.com/");
	}
	
}
