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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.outburstframework.runner.junit.OutburstJUnitRunner;
import org.junit.Assert.*;

@RunWith(OutburstJUnitRunner.class)
public class FailsTest {
	
	@Test
	public void m1() {
       Integer.parseInt("1");
	}

	public void m2() {
         Integer.parseInt("2");
	}

	@Test
	public void m3() throws Exception {
		Integer.parseInt("No an Integer");
	}

	@Test
	public void m4() {
		assertTrue(false);
	}

	@Test
	public void m5() {
         Integer.parseInt("5");
	}

	public void m6() {
         Integer.parseInt("6");
	}

	@Test
	public void m7() {
		throw new RuntimeException("Crash");
	}

	public void m8() {
         Integer.parseInt("8");
	}

	@Test(timeout = 1000l)
	public void testTimeout()
	{
		StringBuilder sb = new StringBuilder();
		while(System.currentTimeMillis() > 0)
		{
			sb.append(".");
		}

        assertTrue(sb.toString() != null);
	}
	
	
}
