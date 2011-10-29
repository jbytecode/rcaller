
<?php

require_once("../RCode.php");

function TestRCode(){
  $rcode = new RCode("");
  $rcode->clear();
  $rcode->addRCode("a<-1:10");
  $predicted = $rcode->toString();
  print $predicted;
}


TestRCode();

?>