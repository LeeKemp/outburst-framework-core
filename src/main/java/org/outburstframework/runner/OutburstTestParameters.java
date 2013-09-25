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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows you to set the test parameters in JUnit when running tests with  
 * the OutburstJUnitRunner.
 * <p>
 * TODO: Would be nice to find a way to combine this with the TestParameters class. 
 * 
 * @author Lee Kemp
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public abstract @interface OutburstTestParameters 
{
	long testLength() default TestParameters.PROPERTY_TEST_LENGTH;
	long testRuns() default TestParameters.PROPERTY_TEST_RUNS;
	long waitBeforeRunLength() default TestParameters.PROPERTY_WAIT_BEFORE_RUN_LENGTH;
	long waitBetweenTestStartsLength() default TestParameters.PROPERTY_WAIT_BETWEEN_TEST_START_LENGTHS;
	long resultInterval() default TestParameters.PROPERTY_RESULT_INTERVAL;
}
