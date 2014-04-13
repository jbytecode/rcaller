concat<-function(to,...){
	to<-paste(to,toString(...),sep="");
	return(to);
}

cleanNames<-function(names){
	cleanNames<-paste(unlist(strsplit(names,"\\.")),collapse="_");
	cleanNames<-paste(unlist(strsplit(cleanNames,"<")),collapse="");
	cleanNames<-paste(unlist(strsplit(cleanNames,">")),collapse="");
	cleanNames<-paste(unlist(strsplit(cleanNames," ")),collapse="");
	cleanNames<-paste(unlist(strsplit(cleanNames,"\\(")),collapse="");
	cleanNames<-paste(unlist(strsplit(cleanNames,"\\)")),collapse="");
	cleanNames<-paste(unlist(strsplit(cleanNames,"\\[")),collapse="");
	cleanNames<-paste(unlist(strsplit(cleanNames,"\\]")),collapse="");
	cleanNames<-paste(unlist(strsplit(cleanNames,"\\*")),collapse="");
	return(cleanNames);
}

makevectorjava<-function(code,objt,name=""){
	javacode<-code;
		obj<-objt;
		if(is.matrix(obj)) obj<-as.vector(obj);
		if(typeof(obj)=="language") obj<-toString(obj);
        	if(typeof(obj)=="logical") obj<-as.character(obj);

		if(is.vector(obj) && is.numeric(obj)){
                        javacode<-concat(javacode,"double[] ");
                        if(name==""){
				javacode<-concat(javacode,cleanNames(deparse(substitute(obj))));
			}else{
				javacode<-concat(javacode,name);
			}
                        javacode<-concat(javacode,"= new double[] {");
                        javacode<-concat(javacode,obj);
                        javacode<-concat(javacode,"};\n");
                }
                if(is.vector(obj) && is.character(obj)){
                        javacode<-concat(javacode,"String[] ");
	                if(name==""){
				javacode<-concat(javacode,cleanNames(deparse(substitute(obj))));
			}else{
				javacode<-concat(javacode,name);
			}
                        javacode<-concat(javacode,"= new String[] {");
                        for (elem in 1:length(obj)){
                                javacode<-paste(javacode,"\"",toString(obj[elem]),"\"",sep="");
                                if (elem!=length(obj)) javacode<-paste(javacode,",",sep="");

                        }
                        javacode<-concat(javacode,"};\n");
                }
	return(javacode);
}

makejava<-function(obj,name=""){
	javacode<-"";
	if(!is.list(obj)){
        	javacode<-makevectorjava(javacode,obj,name);
        }
	if (is.list(obj)){
		objnames<-names(obj);
		for (i in 1:length(obj)){
			javacode<-makevectorjava(javacode,obj[[i]],cleanNames(objnames[[i]]));
		}
	}
return(javacode);
}


makevectorxml<-function(code,objt,name=""){
        xmlcode<-code;
	if(name==""){
	        varname<-cleanNames(deparse(substitute(obj)));
        }else{
                varname<-name;
        }
	obj<-objt;
	if(is.matrix(obj)) obj<-as.vector(obj);
	if(typeof(obj)=="language") obj<-toString(obj);
	if(typeof(obj)=="logical") obj<-as.character(obj);
                if(is.vector(obj) && is.numeric(obj)){
                        xmlcode<-paste(xmlcode,"<variable name=\"",varname,"\" type=\"numeric\">\n",sep="");
			for (i in obj){
                        	xmlcode<-paste(xmlcode,"\t<value>",sep="");
				xmlcode<-paste(xmlcode,toString(i),sep="");
	                        xmlcode<-paste(xmlcode,"</value>\n",sep="");
			}
                        xmlcode<-paste(xmlcode,"</variable>\n",sep="");
                }
                if(is.vector(obj) && is.character(obj)){
                        xmlcode<-paste(xmlcode,"<variable name=\"",varname,"\" type=\"character\">\n",sep="");
                        for (i in obj){
                                xmlcode<-paste(xmlcode,"\t<value>",sep="");
                                xmlcode<-paste(xmlcode,toString(i),sep="");
                                xmlcode<-paste(xmlcode,"</value>\n",sep="");
                        }
			xmlcode<-paste(xmlcode,"</variable>\n");
                }
        return(xmlcode);
}


makexml<-function(obj,name=""){
	xmlcode<-"<?xml version=\"1.0\"?>\n";
	xmlcode<-concat(xmlcode,"<root>\n");
	if(!is.list(obj)){
                xmlcode<-makevectorxml(xmlcode,obj,name);
        }
        else{
                objnames<-names(obj);
                for (i in 1:length(obj)){
			xmlcode<-makevectorxml(xmlcode,obj[[i]],cleanNames(objnames[[i]]));
		}
        }
	xmlcode<-concat(xmlcode,"</root>\n");
	return(xmlcode);
}

