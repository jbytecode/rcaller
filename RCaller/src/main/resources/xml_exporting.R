replaceXMLchars <- function(aStr){
  cln <-paste(unlist(strsplit(aStr,"&")),collapse="&amp;")
  cln <-paste(unlist(strsplit(cln,"<")),collapse="&lt;")
  cln <-paste(unlist(strsplit(cln,">")),collapse="&gt;")
  cln <-paste(unlist(strsplit(cln,"'")),collapse="&#39;")
  return(cln)	
}

makevectorxml<-function(code,objt,name=""){
  xmlcode<-code
  if(name==""){
    varname<-cleanNames(deparse(substitute(obj)))
  }else{
    varname<-name
  }
  obj<-objt  
  n <- 0; m <- 0
  mydim <- dim(obj)
  if(!is.null(mydim)){
    n <- mydim[1]; m <- mydim[2]
  }else{
    n <- length(obj); m <- 1
  }
  if(is.matrix(obj)) {
    obj<-as.vector(obj)
  }else if(typeof(obj)=="language") {
    obj<-toString(obj)
  }else if(typeof(obj)=="logical") {
    obj<-as.character(obj)
  }else if(class(obj)=="factor") {
    obj<-as.vector(obj)
  }
  if(is.vector(obj) && is.numeric(obj)){
    xmlcode<-paste(xmlcode,"<variable name=\"",varname,"\" type=\"numeric\" n=\"", n, "\"  m=\"", m, "\">",sep="")
    s <- sapply(X=obj, function(str){
      return(
        paste("<v>",iconv(replaceXMLchars(toString(str)), to="UTF-8"),"</v>",sep="")
      )})
    xmlcode<-paste(xmlcode,paste(s, collapse=""),"</variable>\n")
  }
  if(is.vector(obj) && is.character(obj)){
    xmlcode<-paste(xmlcode,"<variable name=\"",varname,"\" type=\"character\">\n",sep="")
    s <- sapply(X=obj, function(str){
                            return(
                              paste("<v>",iconv(replaceXMLchars(toString(str)), to="UTF-8"),"</v>",sep="")
                             )})
    xmlcode<-paste(xmlcode,paste(s, collapse=""),"</variable>\n")
  }
  return(xmlcode)
}


makexml<-function(obj,name=""){
  xmlcode<-"<?xml version=\"1.0\"?>\n<root>\n"
  if(!is.list(obj)){
    xmlcode<-makevectorxml(xmlcode,obj,cleanNames(name))
  }else{
    objnames<-names(obj)
    for (i in 1:length(obj)){
      name <- objnames[[i]]
      if (is.null(name)) {
        name <- paste(typeof(obj[[i]]), "-", i, sep='')
      }
      xmlcode<-makevectorxml(xmlcode,obj[[i]],cleanNames(name))
    }
  }
  xmlcode<-paste(xmlcode,"</root>\n",sep="")
  return(xmlcode)
}
