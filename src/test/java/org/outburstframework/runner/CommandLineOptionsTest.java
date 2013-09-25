package org.outburstframework.runner;

import static org.junit.Assert.assertEquals;

import org.apache.commons.cli.CommandLine;
import org.junit.Before;
import org.junit.Test;
import org.outburstframework.runner.ui.cli.CommandLineOptions;

public class CommandLineOptionsTest {

	private CommandLineOptions commandLineOptions;
	private CommandLine options;
	
	@Before
	public void setUp()
	{
		String[] args=new String[] {
			"-t=org.outburstframework.runner.CommandLineOptionsTest",
			"-r=1000"
		};
		
		commandLineOptions = new CommandLineOptions();
		options = commandLineOptions.parseOptions(args);
	}
	
	@Test
	public void testPrintHelp() {
		commandLineOptions.printHelp();
	}
	
	@Test
	public void testParameter()
	{
		assertEquals("org.outburstframework.runner.CommandLineOptionsTest", options.getOptionValue("t"));
	}

}
