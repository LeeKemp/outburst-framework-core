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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.outburstframework.runner.junit.OutburstJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Example of the order in which test methods get executed. 
 * 
 * @author Lee Kemp
 *
 */
@RunWith(OutburstJUnitRunner.class)
public class TestStages {
	
	private static final Logger log = LoggerFactory.getLogger(TestStages.class);
	
	@BeforeClass
	public static void SetUp()
	{
		log.info("@BeforeClass");
	}
	
	@AfterClass
	public static void Pulldown()
	{
		log.info("@AfterClass");
	}
	
	@Before
	public void before()
	{
		log.info("@Before");
	}
	
	@After
	public void after()
	{
		log.info("@After");
	}
	
	@Test
	public void testOne()
	{
		log.info("test1");
		
	}
	
	@Test
	public void testTwo()
	{
		log.info("test2");
	}
	
	@Test
	@Ignore
	public void ignore()
	{
		log.info("ignore");
	}
	
	
	@Test
	public void testError() throws Exception
	{
		Integer.parseInt("No an Integer");
	}

}
