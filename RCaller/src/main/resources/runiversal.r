cleanNames<-function(names){
    cln<-paste(unlist(strsplit(names,"\\.")),collapse="_")
    cln<-paste(unlist(strsplit(cln,"<")),collapse="")
    cln<-paste(unlist(strsplit(cln,">")),collapse="")
    cln<-paste(unlist(strsplit(cln," ")),collapse="")
    cln<-paste(unlist(strsplit(cln,"\\(")),collapse="")
    cln<-paste(unlist(strsplit(cln,"\\)")),collapse="")
    cln<-paste(unlist(strsplit(cln,"\\[")),collapse="")
    cln<-paste(unlist(strsplit(cln,"\\]")),collapse="")
    cln<-paste(unlist(strsplit(cln,"\\*")),collapse="")
    cln<-paste(unlist(strsplit(cln,"&")),collapse="")
    return(cln)
}

replaceXMLchars <- function(aStr){
    cln <-paste(unlist(strsplit(aStr,"&")),collapse="&amp;")
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
    if(is.matrix(obj)) obj<-as.vector(obj)
    if(typeof(obj)=="language") obj<-toString(obj)
    if(typeof(obj)=="logical") obj<-as.character(obj)
    if(is.vector(obj) && is.numeric(obj)){
        xmlcode<-paste(xmlcode,"<variable name=\"",varname,"\" type=\"numeric\" n=\"", n, "\"  m=\"", m, "\">",sep="")
        for (i in obj){
            xmlcode<-paste(xmlcode,"<v>", toString(i), "</v>",sep="")
        }
        xmlcode<-paste(xmlcode,"</variable>\n",sep="")
    }
    if(is.vector(obj) && is.character(obj)){
        xmlcode<-paste(xmlcode,"<variable name=\"",varname,"\" type=\"character\">\n",sep="")
        for (i in obj){
            xmlcode<-paste(xmlcode,"<v>",iconv(replaceXMLchars(toString(i)), to="UTF-8"),"</v>",sep="")
        }
        xmlcode<-paste(xmlcode,"</variable>\n")
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
            xmlcode<-makevectorxml(xmlcode,obj[[i]],cleanNames(objnames[[i]]))
	}
    }
    xmlcode<-paste(xmlcode,"</root>\n",sep="")
    return(xmlcode)
}
