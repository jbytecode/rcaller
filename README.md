# Overview

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

Official publication: http://www.sciencedomain.org/download.php?f=Satman4152014BJMCS10902_1.pdf&aid=4838&type=a

# Usage

It is recomanded to take a look at the existing examples: https://github.com/jbytecode/rcaller/tree/master/RCaller/src/test/java/rcaller

# Building

download the .jar file (https://github.com/jbytecode/rcaller/releases) and added it to your pom.xml. 

coming soon: library available in the maven central repository
