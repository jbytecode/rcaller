
<?php

require_once("../RCode.php");
require_once("../RCaller.php");

function TestRCode(){
  $rcode = new RCode("");
  $rcode->clear();
  $rcode->addRCode("a<-1:10");
  $predicted = $rcode->toString();
  print $predicted;
}


function TestRCaller1(){
  $rcaller = new RCaller();
  $rcode = new RCode("");
  $rcode->clear();
  $rcode->addRCode("x<-1:10");
  $rcaller->setRCode($rcode);
  $rcaller->runAndReturnResult("x");
  
}

?>