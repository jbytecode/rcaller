\name{makexml}
\alias{makexml}
\title{
makexml
}
\description{
This function converts an R object to xml code, so an output of an R script can be handled easly by any programming language.
Every elements in xml is encapsulated in <root></root> tags. Any variable in list or variable itself is placed between <variable> and </variable>
tags. Values of elemenets are placed between <value> and </value> tags. Xml structure is easy to parse and browse. 
}
\usage{
makexml(obj, name = "")
}
\arguments{
  \item{obj}{
R object that to be converted to xml.
}
  \item{name}{
Optinal. Each element in xml code is listed as <variable name="" type="numeric | chracter">.
Default name is name of R object, but a new name can be defined.
}
}
\details{
A generated output of lm object is like this:

<?xml version="1.0"?>
<root>
<variable name="coefficients" type="numeric">
	<value>0.662970909075238</value>
	<value>-0.125473985248366</value>
</variable>
<variable name="residuals" type="numeric">
	<value>-0.0856149907633715</value>
	<value>0.963352019137748</value>
	<value>-0.212667731012814</value>
	<value>-0.561977205893819</value>
	<value>-0.133045701604119</value>
	<value>-0.485339628713294</value>
	<value>0.107354078697277</value>
	<value>-0.624426907949804</value>
	<value>1.45763471890252</value>
	<value>-0.425268650800328</value>
</variable>
<variable name="effects" type="numeric">
	<value>0.0858115975474794</value>
	<value>-1.13967406760172</value>
	<value>-0.313474791725809</value>
	<value>-0.577239374729828</value>
	<value>-0.0627629785631422</value>
	<value>-0.329512013795331</value>
	<value>0.348726585492226</value>
	<value>-0.297509509277868</value>
	<value>1.87009700945145</value>
	<value>0.07273853162558</value>
</variable>
<variable name="rank" type="numeric">
	<value>2</value>
</variable>
<variable name="fitted_values" type="numeric">
	<value>0.537496923826872</value>
	<value>0.412022938578506</value>
	<value>0.28654895333014</value>
	<value>0.161074968081774</value>
	<value>0.0356009828334077</value>
	<value>-0.0898730024149584</value>
	<value>-0.215346987663324</value>
	<value>-0.340820972911691</value>
	<value>-0.466294958160057</value>
	<value>-0.591768943408423</value>
</variable>
<variable name="assign" type="numeric">
	<value>0</value>
	<value>1</value>
</variable>
<variable name="df_residual" type="numeric">
	<value>8</value>
</variable>
<variable name="call" type="character">
	<value>lm, y ~ x</value>
 </variable>
<variable name="terms" type="character">
	<value>~, y, x</value>
 </variable>
</root>
}
\value{
Returns a well-formed xml file for given R object. 
}
\author{
Mehmet Hakan Satman
}
\note{
This function can be used for handling R output from other languages such as C, C++, Java, Javascript and Pyhton.
This method turns problem of "calling R from other languages" into "parsing xml". 
}


\seealso{
makejava
}
\examples{
x<-1:10
y<-rnorm(10)
ols<-summary(lm(y~x))
cat(makexml(ols))
}
\keyword{call}
\keyword{R}
\keyword{from}
\keyword{other}
\keyword{languages}

