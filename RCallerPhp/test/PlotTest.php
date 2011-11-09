<?php

require_once("../RCode.php");
require_once("../RCaller.php");
require_once("simpletest.php");

$caller = new RCaller();
$code = new RCode("");

$plot = $code->startPlot();
$code->addRCode("plot.ts(rnorm(10))");
$code->endPlot();

$caller->setRscriptExecutable("/usr/bin/Rscript");
$caller->setRCode($code);
$caller->runOnly();

print($caller->getPlot($plot));
?>
