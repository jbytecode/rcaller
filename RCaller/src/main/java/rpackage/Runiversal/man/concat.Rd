\name{concat}
\alias{concat}
\title{
concat
}
\description{
Constructs a string of Java array using given R object.
}
\usage{
concat(to, ...)
}
\arguments{
  \item{to}{
A string variable which string of Java array will be added to.
}
  \item{\dots}{
An R object, generally vectors or scalers.
}
}
\details{
This function is a utility function for main functions 'makexml' and 'makejava'
}
\value{
Returns a new string, which is sums of old string and R objecs.
}
\author{
Mehmet Hakan Satman
}


\seealso{
makexml
makejava
}


\examples{
a<-"a string"
x<-1:10
concat(a,x)
}
\keyword{strings}
\keyword{java}
