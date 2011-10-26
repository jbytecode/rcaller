\name{makevectorxml}
\alias{makevectorxml}
\title{
makevectorxml()
}
\description{
This is an utility function for makexml() and not generally be called by user.
}
\usage{
makevectorxml(code, objt, name = "")
}

\arguments{
  \item{code}{
A string which the generated code to be added to.
}
  \item{objt}{
An R object, to be converted to xml code.
}
  \item{name}{
Variable name for generated xml entry. Optional.
}
}
\author{
Mehmet Hakan Satman
}


\examples{
x<-1:10
cat(makevectorxml("",x,"x"))
}
\keyword{xml}
\keyword{tag}
\keyword{entry}

