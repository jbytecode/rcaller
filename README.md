# Overview

[![Join the chat at https://gitter.im/jbytecode/rcaller](https://badges.gitter.im/jbytecode/rcaller.svg)](https://gitter.im/jbytecode/rcaller?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://github.com/jbytecode/rcaller/workflows/build/badge.svg)](https://github.com/jbytecode/rcaller/workflows/build/badge.svg)
[![Test Status](https://github.com/jbytecode/rcaller/workflows/test/badge.svg)](https://github.com/jbytecode/rcaller/workflows/test/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.jbytecode/RCaller/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.jbytecode/RCaller)
[![status](https://joss.theoj.org/papers/de28eed555632371f4dcbe82efce5075/status.svg)](https://joss.theoj.org/papers/de28eed555632371f4dcbe82efce5075)

RCaller is a software library which is developed to simplify calling R from Java. Despite it is not
the most efficient way of calling R codes from Java, it is very simple to use and its learning curve is
steep. It successfully simplifies and wraps type conversations and makes variables in each languages
accessible between platforms. With the calling sequential commands facility, the performance is not
lost through a single external process. Although R is single-threaded, multiple R processes can
be created and handled by multiple RCaller instances in Java. A Servlet based application can
instantiate many RCaller objects as well as it can use a single object by using sequential command
invocation ability. The former use multiple environments which do not share the same variable pool,
whereas, the latter shares a mutual variable pool and clients can communicate as well. RCaller is
written purely in Java and it does not depend on any external libraries, that is, it is ready to run in any
machines that Java and R installed. Simulation studies show that the other libraries such as Rserve
and rJava outperform the RCaller by means of interaction times. As a result of this, RCaller is not
suitable for the projects which have many clients that request relatively single and small computations.
[RCaller: A Software Library for Calling R from Java - M. Hakan Satman]

[Official publication](https://doi.org/10.9734/BJMCS/2014/10902)

[RCaller3 Documentation](https://github.com/jbytecode/rcaller/blob/master/doc/rcaller3/rcaller3.pdf)




# Dependencies
RCaller compiled jar library requires JRE (v1.8 or higher) and R installed in the runtime environment. If you want to compile from source, JDK (v.1.8 or higher) and maven are also required 
for building process. Maven is responsible for downloading and install additional Java dependencies 
defined in [pom file](https://github.com/jbytecode/rcaller/blob/master/RCaller/pom.xml). RCaller
does not use any version specific property of R. 


# Usage

It is recommended to take a look at the existing [tests](https://github.com/jbytecode/rcaller/tree/master/RCaller/src/test/java/com/github/rcaller)
 and 
[examples](https://github.com/jbytecode/rcaller/tree/master/RCaller/src/main/java/examples)
 

### Here is a set of selected examples:

- [Basic Graphics](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/BasicGraphics.java)

- [Basic Script Engine](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/BasicScriptEngine.java)

- [Descriptive Statistics](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/DescriptiveStatistics.java)

- [Handling Empty Arrays](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/EmptyArray.java)

- [Automatic ARIMA Model Selection](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/Forecasting.java)

- [Graphics with Themes](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/GraphicsWithThemes.java)

- [Multiple Sequential Calls using a Single R Instance](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/MultipleCalls.java)

- [Ordinary Least Squares](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/OrdinaryLeastSquares.java)

- [Swing R GUI](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/SampleRGui.java)

- [Invoke R Functions](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/examples/ScriptEngineInvocable.java)




# Building RCaller

download the [.jar file](https://github.com/jbytecode/rcaller/releases) and added it to your pom.xml.

import RCaller in your project using `maven`. add following dependency to `pom.xml`:


    <dependency>
	    <groupId>com.github.jbytecode</groupId>
	    <artifactId>RCaller</artifactId>
	    <version>2.8</version>
    </dependency>
    
The RCaller is located in the Maven Central Repository.
