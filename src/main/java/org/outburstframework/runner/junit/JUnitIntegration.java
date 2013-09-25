package org.outburstframework.runner.junit;

import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.outburstframework.example.TestStages;
import org.outburstframework.runner.monitor.MonitorThread;
import org.outburstframework.runner.monitor.SLF4JResultMonitor;

/**
 * Testing the JUnit ParallelComputer. 
 * <p>
 * Do not use this. 
 *  
 * @author lee kemp
 *
 */
public class JUnitIntegration {

	public static void main(String[] args) throws Exception {
		//Runner runner = new OutburstJUnitRunner(MapTest.class);		
		Runner runner = new OutburstJUnitRunner(TestStages.class);
		
		Result result= new Result();
		RunListener resultListener= result.createListener();
		//TextListener txtListener = new TextListener(System.err);
		
		RunNotifier notifier = new RunNotifier();
		notifier.addFirstListener(resultListener);
		OutburstJUnitResultListener outburstJUnitResultListener = new OutburstJUnitResultListener();
		notifier.addListener( outburstJUnitResultListener);
		
		MonitorThread monitor = new MonitorThread(new SLF4JResultMonitor(), outburstJUnitResultListener, 2000l);
		monitor.startMonitoring();
		
		notifier.fireTestRunStarted(runner.getDescription());
		runner.run(notifier); // Blocks here until tests are finished
		notifier.fireTestRunFinished(result);		
		
		monitor.stopMonitoring();
	}

}
