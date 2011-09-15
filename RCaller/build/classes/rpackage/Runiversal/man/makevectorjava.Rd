\name{makevectorjava}
\alias{makevectorjava}
\title{
makevectorjava()
}
\description{
This is an utility function for makejava() and not generally be called by user.
}
\usage{
makevectorjava(code, objt, name = "")
}

\arguments{
  \item{code}{
A string which the generated code to be added to.
}
  \item{objt}{
An R object, to be converted to R code.
}
  \item{name}{
Variable name for generated Java object. Optional.
}
}
\author{
Mehmet Hakan Satman
}


\examples{
x<-1:10
cat(makevectorjava("",x,"x"))
}
\keyword{java}
\keyword{array}% __ONLY ONE__ keyword per line
