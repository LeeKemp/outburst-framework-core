/*
 * Copyright 2009-2010 Lee Kemp
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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.outburstframework.runner.OutburstTestParameters;
import org.outburstframework.runner.TestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Outburst implementation the JUnit4 test runner. 
 * 
 * @author Lee Kemp
 *
 */
public class OutburstJUnitRunner extends BlockJUnit4ClassRunner {

	private static final Logger log = LoggerFactory.getLogger(OutburstJUnitRunner.class);
	
	private TestParameters testParameters;
	
	public OutburstJUnitRunner(TestParameters testParameters) throws InitializationError
	{
		super(testParameters.getClazz());
		this.testParameters = testParameters;
	}
	
	/**
	 * Default constructor, This constructor is used by JUnit
	 * <p>
	 * If an OutburstTestParameters annotation is present its properties will be used, otherwise
	 * the default TestParameters are used.
	 * 
	 * @param klass
     *          The test class
	 * @throws InitializationError
     *          Their was a problem loading the class
	 */
	public OutburstJUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		log.debug(klass.toString());
		OutburstTestParameters annotation = klass.getAnnotation(OutburstTestParameters.class);
		if(annotation != null)
		{
			this.testParameters = new TestParameters(annotation); // Use annotation parameters
		}
		else
		{
			this.testParameters = new TestParameters();  // Use default parameters
		}
		this.testParameters.setClazz(klass);
		
		log.debug("Calling default constructor");
	}

	@Override
	public void run(final RunNotifier notifier) {
		
		log.debug("Using outburst runner");
		
		OutburstJUnitRunScheduler scheduler = new OutburstJUnitRunScheduler(testParameters);
		this.setScheduler(scheduler);
		
		EachTestNotifier testNotifier= new EachTestNotifier(notifier, getDescription());
		try {
			
			invokeBefores();
			
			evaluateChildren(notifier);

			//TODO: Wait Before start
			
			scheduler.beginTestEvaluation();
			// This will block here until the tests are ready to be or already have all stopped
			scheduler.monitorAndControlTestExecution(testParameters.getTestLength()); 
			scheduler.stopTestEvaluation();
			
			
			//this.runChild(method, notifier)
			
			runAfters();
			
		} catch (AssumptionViolatedException e) {
			testNotifier.fireTestIgnored();
		} catch (StoppedByUserException e) {
			throw e;
		} catch (Throwable e) {
			testNotifier.addFailure(e);
        }
	}
	
	private void invokeBefores() throws Throwable
	{
		log.debug("Running Befores");
		
		for(FrameworkMethod before : this.getTestClass().getAnnotatedMethods(BeforeClass.class))
		{
			before.invokeExplosively(null);
		}
	}
	
	private void runAfters() throws Throwable
	{
		log.debug("Running Afters");
		
		for(FrameworkMethod before : this.getTestClass().getAnnotatedMethods(AfterClass.class))
		{
			before.invokeExplosively(null);
		}
	}
	
	private void evaluateChildren(final RunNotifier notifier) throws Throwable {
		childrenInvoker(notifier).evaluate();
	}
	
	
}
