# Overview

[![Join the chat at https://gitter.im/jbytecode/rcaller](https://badges.gitter.im/jbytecode/rcaller.svg)](https://gitter.im/jbytecode/rcaller?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://github.com/jbytecode/rcaller/workflows/build/badge.svg)](https://github.com/jbytecode/rcaller/workflows/build/badge.svg)
[![Test Status](https://github.com/jbytecode/rcaller/workflows/test/badge.svg)](https://github.com/jbytecode/rcaller/workflows/test/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.jbytecode/RCaller/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.jbytecode/RCaller)
[![status](https://joss.theoj.org/papers/de28eed555632371f4dcbe82efce5075/status.svg)](https://joss.theoj.org/papers/de28eed555632371f4dcbe82efce5075)
[![DOI](https://zenodo.org/badge/32182572.svg)](https://zenodo.org/badge/latestdoi/32182572)

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


# Who uses RCaller?
RCaller is a production ready library for calling R functions from within Java. If you are developing 
Java software that needs enhanced statistical calculations, RCaller is a practical solution to integrate
these languages. R has many well-tested and matured packages for automatic time series model selection, 
clustering, segmentation and classification, non-linear and robust regression estimations, data and text
mining, linear and non-linear programming, generating plots, function optimization besides other research tools. RCaller brings all of the functionality that R serves in Java. Easy installation and integration steps and steep learning curve make RCaller a suitable solution. RCaller's computation overhead is generating XML files in R side and parsing XML to Java objects in Java side.  


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


# API Docs
[Here is the auto-generated Javadocs](https://github.com/jbytecode/rcaller/releases/download/v3.0.2/RCaller-3.0.2-SNAPSHOT-javadoc.jar). 

# Building and Installing RCaller
There are many options to integrate RCaller with a Java Project. First option is to download pre-compiled jar file and add it to the classpath or pom.xml. [pre-compiled jar files](https://github.com/jbytecode/rcaller/releases) are here.

The second option is to compile from source. R should be installed to run tests. After building process, if everything is okay, a jar file is located in the target directory.

```BASH
$ git clone https://github.com/jbytecode/rcaller.git
$ cd RCaller
$ mvn package
```

The last option is to use maven dependency: 

```XML
<dependency>
  <groupId>com.github.jbytecode</groupId>
  <artifactId>RCaller</artifactId>
  <version>3.0</version>
  <classifier>jar-with-dependencies</classifier>
</dependency>
```

The RCaller is located in the Maven Central Repository.

# Benchmarks
You can test the performance of the library using the benchmark tool hosted [here](https://github.com/jbytecode/rcaller/blob/master/RCaller/src/main/java/benchmark/PassingArraysAndMatrices.java). This file tests costs of passing vectors and matrices from Java to R and vice versa. 

# Join us
If you want to join us, please follow the community guidelines [here](https://github.com/jbytecode/rcaller/blob/master/CONTRIBUTING.md). We look forward to seeing your contributions.
