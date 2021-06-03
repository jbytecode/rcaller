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
