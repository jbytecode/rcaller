
<?php

require_once("../RCode.php");
require_once("../RCaller.php");
require_once("simpletest.php");

function TestRCode() {
  $rcode = new RCode("");
  $rcode->clear();
  $rcode->addRCode("a<-1:10");
  $predicted = $rcode->toString();
  print $predicted;
}

function TestRCaller1() {
  $rcaller = new RCaller();
  $rcaller->setRscriptExecutable("/usr/bin/Rscript");
  $rcode = new RCode("");
  $rcode->clear();
  $rcode->addRCode("mylist <- list(x=1:3, y=c(7,8,9))");

  $rcaller->setRCode($rcode);
  $rcaller->runAndReturnResult("mylist");

  $x = $rcaller->getParser()->getAsStringArray("x");
  $y = $rcaller->getParser()->getAsStringArray("y");

  test("getting x", $x, array(1, 2, 3));
  test("getting y", $y, array(7, 8, 9));
}

TestRCaller1();
?>