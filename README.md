outburst-framework-core
=======================

Outburst Core is a multithreaded JUnit 4 test runner for concurrently and/or repetitively executing unit tests

By spawning an individual thread for each test in a class the tests are then run concurrently and/or repetitively for either a set amount of time or number of runs. This allows JUnit tests to be used for performance tests, benchmarking and in some cases decrease the execution time of your test suite.

Outburst Core can be used either via the command line or by using the @RunWith JUnit annotation. Using the annotation allows you to utilize your existing development and build tools such as eclipse, ANT, Maven and Hudson etc.

# Examples
* Benchmarking (MapTest.class)
* HTTP Performance Test (HttpTest.class)
* Faster JUnit Tests (FasterTests.class)

# Requires
* Java 1.5+
* JUnit 4.8
* SLF4J
* Commons CLI (Command line only)
* Apache HTTPClient (HTTPUtils only)

# Download
Jar, Download the jar here, and add it to your applications classpath.
Maven, Follow the instructions on the using old the old Google Code wiki page [https://code.google.com/p/outburst-framework-core/wiki/usingMaven].

# Usage

## Annoataions
Add @RunWith(OutburstJUnitRunner.class) annotation to test class.
(optional) Set test parameters using @OutburstTestParameters annotation.