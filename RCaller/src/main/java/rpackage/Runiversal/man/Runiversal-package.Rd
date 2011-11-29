\name{Runiversal-package}
\alias{Runiversal-package}
\alias{Runiversal}
\docType{package}
\title{
Converts R objects to Java style interpretable codes and XML
}
\description{
This package contains two main functions called makejava and makexml. makejava function converts R objects to Java codes which is interpretable by Bean Shell or Dynamic Java. Other function, makexml, converts R objects to XML code for handling R object in other languages. Both of these functions can be used for calling R codes from other languages without using native codes. 
}
\details{
\tabular{ll}{
Package: \tab Runiversal\cr
Type: \tab Package\cr
Version: \tab 1.0.1\cr
Date: \tab 2010-07-29\cr
License: \tab GPL\cr
LazyLoad: \tab yes\cr
}
}
\author{
Mehmet Hakan Satman

Maintainer: Mehmet Hakan Satman  <mhsatman@istanbul.edu.tr>
http://www.mhsatman.com
}
\references{
See http://www.mhsatman.com/rcaller for calling R from Java without JNI.
Bean Shell is a library for scripting Java language that downloadable for free in http://www.beanshell.org/
}
\keyword{ package }
\examples{
#Getting estimates from regression object as Java variables that be directly interpretable by Bean Shell.
x<-1:10
y<-rnorm(10)
ols<-lm(y~x)
betas<-ols$coefficients
cat(makejava(betas,"myBetas"))

#Getting summary report as Java variables
cat(makejava(summary(ols)))

#Getting regression results as xml 
cat(makexml(ols))

#Saving xml to file
cat(makexml(ols), file="somefile.xml")

}
