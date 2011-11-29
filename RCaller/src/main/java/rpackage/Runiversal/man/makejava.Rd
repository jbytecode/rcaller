\name{makejava}
\alias{makejava}
\title{
A wrapper function for converting R objects to Java style variables.
}
\description{
This function converts R objects to Java arrays. If R object is numeric than the Java object is double[].
Otherwise the Java object will be String[]. 
}
\usage{
makejava(obj, name = "")
}
\arguments{
  \item{obj}{
R object that to be converted to Java style variables.
}
  \item{name}{
New variable name for created Java style variable. If R object is only a vector, a name have to be given. If R object is a list, name is inoperative.
}
}
\details{
This function returns interpretable code for most frequently used dynamic Java interpreters such as Bean Shell.
So this function can be used for using R codes in Java without JNI. This is an inefficient way to use R in Java
but implementation is quite easy and platform independent. 
}
\value{
Returns interpretable code for Bean Shell and Java of R objects.
}
\references{
See an Java implementation using this functionality in http://www.mhsatman.com/rcaller.php
}
\author{
Mehmet Hakan Satman
}
\note{
This function generates interpretable code for Java. For other languages you can use makexml to convert R objects to XML code. XML is readable
for all of the programming languages.
}


\seealso{
makexml
}
\examples{
#Shows the linear regression results as Java style variables
y<-rnorm(10)
x<-1:10
ols<-lm(y~x)
cat(makejava(ols))

#Shows only the residuals as double[] Residuals=new double[]{.....};
cat(makejava(ols$residuals, "Residuals"))


}
\keyword{convert}
\keyword{java}
