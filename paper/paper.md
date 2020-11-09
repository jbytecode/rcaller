---
title: 'RCaller: A Java package for interfacing R'
tags:
  - R
  - Java
  - statistics
  - data science
authors:
  - name: Mehmet Hakan Satman
    orcid: 0000-0002-9402-1982
    affiliation: 1
  - name: Kopilov Aleksandr
    orcid: 0000-0002-8223-2168
    affiliation: 2
affiliations:
 - name: Department of Econometrics, Istanbul University, Istanbul, Turkey
   index: 1
 - name: Department of Mathematical Optimization and Modeling, BIA-Technologies, Saint Petersburg, Russian Federation
   index: 2
date: 24 August 2020
bibliography: paper.bib
---

# Summary
In this paper, version 3.0 of the open source library RCaller is introduced. RCaller is a software library which simplifies performing data analysis and statistical calculations in Java using R. The details are hidden from users including transferring data between platforms, function calls, and retrieving results. In addition to previous RCaller versions, RCaller 3.0 implements the scripting API of Java, ensuring R function calls and data transfers are performed in a standard way, as is usual for other scripting languages in Java. Besides implementation of new features, RCaller has many performance improvements in the new release of 3.0 [@satmancurcean2016].  

# State of the field
Nowadays, increased interest in statistical computation and data analysis has enhanced the popularity of some programming languages such as R, Python, and Julia. As a result of popularity, hundreds of packages are developed for these targeted languages. On the other side, many main frame languages and their packages are adopted for general-purpose tasks in several platforms. Interaction between those languages became a major requirement due to the lack of modern statistical tools written in the general-purpose languages such as Java. Renjin [@renjin] is a great effort for interfacing R from within Java using a different perspective: Writing R from scratch in Java! GraalVM [@graalvm] is an other admirable attempt for interfacing Python and R in Java with its polyglot design. Many tools and software packages are developed for interfacing R and Java. Each one stands out with its different features including speed, installation procedures, learning speed, and ease of use. 


# Statement of need 
RCaller is a Java library for interfacing R from within Java [@satman2014rcaller]. R is a popular programming language and a programming environment with hundreds of packages written in C, C++, Fortran, and R itself [@rcoreteam]. This huge collection of computation tools is not directly accessible for the other languages, especially for Java. RCaller supplies a clean API for calling R functions, managing interactions, and transfering objects between languages. There are other options in the literature including Rserve [@urbanek2003fast] and rJava [@urbanek2009talk] which are based on TCP sockets and JNI (Java Native Interface), respectively. RCaller provides a set of easier calling schemes without any dependencies. Previous works showed that the performance of the library is suitable for more cases, and studies with moderate datasets can be handled in reasonable times [@satmancurcean2016]. Following its first publication [@satman2014rcaller], support for DataFrame objects, R start-up options, automatic Rscript executable locator, and Java Scripting API (JSR 223) have been implemented. 


R has many well-tested and mature packages including for automatic time series model selection, clustering, segmentation and classification, non-linear and robust regression estimations, data and text mining, linear and non-linear programming, generating plots, multivariate data analysis, and function optimization. RCaller brings all of the functionality that R provides in Java. RCaller is also used as a core of RCallerService [@rcallerservice], a free microservice solution for running R-scripts from any other languages using HTTP.  


# Java scripting interface
RCaller also implements the scripting API of Java (JSR 223) after version 3 and above. In other words, the engine behaves like a Java implementation of R. With this feature of RCaller, calling R from Java is seemingly the same as in the other JSR 223 implementations such as JavaScript, Python, Groovy, and Ruby. However the performance is not directly comparable with native counterparts (Rhino, Jython, JGroovy, JRuby, etc.) since the target language is not reimplemented in Java. Here is an example of sorting a Java array in R side and handling the result in 
Java:

```Java
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine en = manager.getEngineByName("RCaller");
			
	try {
		double[] a = new double[] {19.0, 17.0, 23.0};
		en.put("a", a);
		en.eval("sortedA <- sort(a)");
		double[] result = (double[])en.get("sortedA");
		System.out.println(result[0]);
	} catch (ScriptException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
```

RCaller, as a scripting engine in Java, creates an R process, encodes Java objects to XML, runs commands in R side, gets back the results as XML, and parses the result to Java objects. Since a single R process is created and used for consecutive calls, XML parsing is the only calculation overhead.

# Acknowledgements

We acknowledge contributions from Paul Curcean, Miroslav Batchkarov, Joel Wong, Kejo Starosta, Steven Sotelo, Edinei Piovesan, Simon Carter, and others of this project.

# References
