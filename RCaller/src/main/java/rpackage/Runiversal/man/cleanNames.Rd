\name{cleanNames}
\alias{cleanNames}
\title{
Variable name cleaner. 
}
\description{
An utility function for deleting spacial chracters from variable names (Especially for Java).
}
\usage{
cleanNames(names)
}
\arguments{
  \item{names}{
Names to be cleared.
}
}
\details{
Clears variable names defined in names.
}

\value{
Returns variable names.
}
\author{
Mehmet Hakan Satman
}
\note{
This function is generally called by main routines of package. See 'see also' section.
}


\seealso{
makexml
makejava
}
\examples{
varname<-"r.squared"
cleanNames(varname)
}

\keyword{naming}
\keyword{java}
