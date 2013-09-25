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

package org.outburstframework.runner.ui.cli;

import org.apache.commons.cli.CommandLine;
import org.outburstframework.runner.TestParameters;
import org.outburstframework.runner.TestRunner;
import org.outburstframework.runner.monitor.ResultMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command line interface to the test runner.
 * 
 * @author Lee Kemp
 *
 */
public class CommandLineInterface {

	private static final Logger log = LoggerFactory.getLogger(CommandLineInterface.class);

    /**
     * Constructor that parses and validates the args array for CommandLineOptions.
     * Once the options in args are parsed it initializes the results monitor if one is present and
     * creates a test runner instance to beging running the tests.  
     *
     * @param args
     *      CommandLineOptions 
     */
	@SuppressWarnings("unchecked")
	public CommandLineInterface(String[] args)
	{
		CommandLineOptions optionsParser = new CommandLineOptions();
		CommandLine options = optionsParser.parseOptions(args);
		
		if(options == null || options.hasOption(CommandLineOptions.HELP_ARGUMENT))
		{
			optionsParser.printHelp();
			System.exit(0);
		}
		
		// Check and set compulsory arguments
		if(!options.hasOption(CommandLineOptions.TEST_ARGUMENT))
		{
			System.err.println("Missing required test class argument ("+CommandLineOptions.TEST_ARGUMENT+")");
			optionsParser.printHelp();
			System.exit(0);
		}
		
		TestParameters testParameters = new TestParameters();
		
		try {
			testParameters.setClazz(Class.forName(options.getOptionValue(CommandLineOptions.TEST_ARGUMENT)));
		} catch (ClassNotFoundException e) {
			System.err.println("Test class ["+options.getOptionValue(CommandLineOptions.TEST_ARGUMENT)+"] is could not be found");
			optionsParser.printHelp();
			System.exit(0);
			log.error("Test class ["+options.getOptionValue(CommandLineOptions.TEST_ARGUMENT)+"] is could not be found", e);
		}
		
		// Check optional arguments
		if(options.hasOption(CommandLineOptions.LENGTH_ARGUMENT))
			testParameters.setTestLength(Long.parseLong(options.getOptionValue(CommandLineOptions.LENGTH_ARGUMENT)));
		
		if(options.hasOption(CommandLineOptions.RUNS_ARGUMENT))
			testParameters.setTestRuns(Long.parseLong(options.getOptionValue(CommandLineOptions.RUNS_ARGUMENT)));
		
		if(options.hasOption(CommandLineOptions.WAIT_ARGUMENT))
			testParameters.setWaitBeforeRunLength(Long.parseLong(options.getOptionValue(CommandLineOptions.WAIT_ARGUMENT)));
		
		if(options.hasOption(CommandLineOptions.TEST_WAIT_ARGUMENT))
			testParameters.setWaitBetweenTestStartsLength(Long.parseLong(options.getOptionValue(CommandLineOptions.TEST_WAIT_ARGUMENT)));
		
		if(options.hasOption(CommandLineOptions.MONITOR_INTERVAL_ARGUMENT))
			testParameters.setResultInterval(Long.parseLong(options.getOptionValue(CommandLineOptions.MONITOR_INTERVAL_ARGUMENT)));
		
		if(options.hasOption(CommandLineOptions.MONITOR_ARGUMENT))
		{
			try {
				Class resultMonitorClass = Class.forName(options.getOptionValue(CommandLineOptions.MONITOR_ARGUMENT));
				testParameters.setResultMonitor((ResultMonitor) resultMonitorClass.newInstance());
			} catch (Exception e) {
				System.err.println("Result monitor class ["+options.getOptionValue(CommandLineOptions.MONITOR_ARGUMENT)+"] is could not be found");
				optionsParser.printHelp();
				System.exit(0);
				log.error("Result monitor class ["+options.getOptionValue(CommandLineOptions.MONITOR_ARGUMENT)+"] is could not be found", e);
			}
			
		}
		
		new TestRunner(testParameters);
	}
	
	public static void main(String[] args) throws Exception {
		new CommandLineInterface(args);	
	}
	
}
