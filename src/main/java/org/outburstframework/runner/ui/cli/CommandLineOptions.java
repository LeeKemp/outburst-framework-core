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
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineOptions {

	private static final Logger log = LoggerFactory.getLogger(CommandLineOptions.class);
	
	public static final String HELP_ARGUMENT = "help";
	public static final String RUNS_ARGUMENT = "runs";
	public static final String LENGTH_ARGUMENT = "length";
	public static final String WAIT_ARGUMENT = "wait";
	public static final String TEST_WAIT_ARGUMENT = "test-wait";
	public static final String TEST_ARGUMENT = "test";
	public static final String MONITOR_ARGUMENT = "monitor";
	public static final String MONITOR_INTERVAL_ARGUMENT = "monitor-interval";
	
	private final Options options;
	
	public CommandLineOptions()
	{
		options = new Options();
		
		options.addOption("r", RUNS_ARGUMENT, true, "The number of times to run each test. Defaults to 1 (1).");
		options.addOption("l", LENGTH_ARGUMENT, true, "The length of time a test should run for in milliseconds. Defaults to forever (-1).");
		options.addOption("w", WAIT_ARGUMENT, true, "The length of time in milliseconds to wait before running the tests. Default is no wait (0).");
		options.addOption("tw", TEST_WAIT_ARGUMENT, true, "The length of time in milliseconds to wait between starting each of the test threads. Default is no wait (0).");
		options.addOption("t", TEST_ARGUMENT, true, "REQUIRED: The fully qualified name of the test class");
		options.addOption("m", MONITOR_ARGUMENT, true, "An implementation of the ResultMonitor class, used to process interval and final results.");
		options.addOption("mi", MONITOR_INTERVAL_ARGUMENT, true, "The length of time in milliseconds to wait before printing test results. Defaults to 2 seconds.");
		options.addOption("h", HELP_ARGUMENT, false, "Prints this. ");
	}
	
	public CommandLine parseOptions(String[] args)
	{
		CommandLineParser parser = new GnuParser();
		try {
			CommandLine commandLine=parser.parse(options, args);
			return commandLine;
		} catch (ParseException e) {
			log.error("Unable to parse command line parameters", e);
			return null;
		}
	}
	
	public void printHelp()
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("outburst.sh / outburst.bat", options);
	}


}
