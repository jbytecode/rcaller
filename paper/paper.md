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
affiliations:
 - name: Department of Econometrics, Istanbul University
   index: 1
date: 24 August 2020
bibliography: paper.bib
---

# Summary
RCaller is a Java library for interfacing R from within Java [@satman2014rcaller]. R is a popular programming language and a programming environment with hundreds of packages written in C, C++, Fortran, and R itself [@rcoreteam]. These huge collection of computation tools are not directly accessible for the other languages, especially for Java. RCaller supplies a clean API for calling R functions, managing interactions, and transfering objects between languages. There are other options in the literature including Rserve [@urbanek2003fast] and rJava [@urbanek2009talk] which are based on TCP sockets and JNI (Java Native Interface), respectively. RCaller provides a set of easier calling schemes without any dependencies. Previous works showed that the performance of the library is suitable for more cases and studies with moderate datasets can be handled in reasonable times.

# Statement of need 



# Acknowledgements

We acknowledge contributions from Paul Curcean, Miroslav Batchkarov, Kopilov Aleksandr, Joel Wong, Kejo Starosta, Steven Sotelo, Edinei Piovesan, and others of this project.

# References

