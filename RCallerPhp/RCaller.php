<?php

/*
  RCallerPhp, A solution for calling R from Php
  Copyright (C) 2010,2011  Mehmet Hakan Satman

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.


  Mehmet Hakan Satman - mhsatman@yahoo.com
  http://www.mhsatman.com
  Google code projec: http://code.google.com/p/rcaller/

 *
 * @author Mehmet Hakan Satman
 * mhsatman@yahoo.com
 * http://stdioe.blogspot.com
 * http://www.mhsatman.com
 * http://code.google.com/p/rcaller
 * 
 */

include_once ("CodeUtils.php");
include_once ("RCode.php");
include_once ("ROutputParser.php");

class RCaller {

  private $RscriptExecutable;
  private $RExecutable;
  private $rcode;
  private $parser;
  private $process;
  private $inputStreamToR = null;
  private $outputStreamToR = null;
  private $errorStreamToR = null;

  public function getErrorStreamToR() {
    return $this->errorStreamToR;
  }

  public function setErrorStreamToR($errorStreamToR) {
    $this->errorStreamToR = $errorStreamToR;
  }

  public function getProcess() {
    return $this->process;
  }

  public function setProcess($process) {
    $this->process = $process;
  }

  public function getInputStreamToR() {
    return $inputStreamToR;
  }

  public function setInputStreamToR($inputStreamToR) {
    $this->inputStreamToR = $inputStreamToR;
  }

  public function getOutputStreamToR() {
    return $this->outputStreamToR;
  }

  public function setOutputStreamToR($outputStreamToR) {
    $this->outputStreamToR = $outputStreamToR;
  }

  public function getRExecutable() {
    return $this->RExecutable;
  }

  public function setRExecutable($RExecutable) {
    $this->RExecutable = $RExecutable;
  }

  public function getCranRepos() {
    return Globals::$cranRepos;
  }

  public function setCranRepos($cranRepos) {
    Globals::$cranRepos = $cranRepos;
  }

  public function getParser() {
    return $this->parser;
  }

  public function setParser($parser) {
    $this->parser = $parser;
  }

  public function getRCode() {
    return $this->rcode;
  }

  public function setRCode($rcode) {
    $this->rcode = $rcode;
  }

  public function getRscriptExecutable() {
    return $this->RscriptExecutable;
  }

  public function setRscriptExecutable($RscriptExecutable) {
    $this->RscriptExecutable = $RscriptExecutable;
  }

  function __construct() {
    $this->rcode = new RCode("");
    $this->parser = new ROutputParser();
    $this->cleanRCode();
  }

  public function setGraphicsTheme($theme) {
    Globals::$theme = $theme;
  }

  public function cleanRCode() {
    $this->rcode->clear();
    $this->rcode->addRCode("packageExist<-require(Runiversal)");
    $this->rcode->addRCode("if(!packageExist){");
    $this->rcode->addRCode("install.packages(\"Runiversal\", repos=\" " . $this->getCranRepos() . "\")");
    $this->rcode->addRCode("}\n");
  }

  /**
   * @deprecated Use RCode.addRCode instead
   * @param code 
   */
  public function addRCode($code) {
    $this->rcode->addRCode($code);
  }

  /**
   * @deprecated Use RCode.addStringArray instead
   * @param name
   * @param arr 
   */
  public function addStringArray($name, $arr) {
    CodeUtils::addStringArray($this->rcode->getCode(), $this->name, $arr, false);
  }

  /**
   * @deprecated Use RCode.addDoubleArray
   * @param name
   * @param arr 
   */
  public function addNumericArray($name, $arr) {
    CodeUtils::addNumericArray($rcode->getCode(), $name, $arr, false);
  }

  /**
   * @deprecated Use RCode.startPlot
   * @param name
   * @param arr 
   */
  public function startPlot() {
    return($this->rcode->startPlot());
  }

  /**
   * @deprecated Use RCode.endPlot
   * @param name
   * @param arr 
   */
  public function endPlot() {
    $rcode->endPlot();
  }

  /**
   * @deprecated Use RCode.getPlot
   * @param name
   * @param arr 
   */
  public function getPlot($f) {
    return $this->rcode->getPlot($f);
  }

  /**
   * @deprecated Use RCode.showPlot
   * @param name
   * @param arr 
   */
  public function showPlot($f) {
    $this->rcode->showPlot($f);
  }

  public function createRSourceFile() {
    $f = CodeUtils::createTempFile("rcallerphp");
    file_put_contents($f, $this->rcode->toString());
    return ($f);
  }

  public function runOnly() {
    if ($this->RscriptExecutable == null) {
      throw new Exception("RscriptExecutable is not defined. Please set this variable to full path of Rscript executable binary file.");
    }
    $this->rcode->addRCode("q(" . "\"" . "yes" . "\"" . ")\n");
    $rSourceFile = $this->createRSourceFile();
    $process = exec($this->RscriptExecutable . " " . $rSourceFile);
  }

  /*
    public void runAndReturnResultOnline(String var) throws rcaller.exception.RCallerExecutionException {
    String commandline = null;
    String result = null;
    File rSourceFile;
    final File outputFile;

    if (this.RExecutable == null) {
    throw new RCallerExecutionException("RExecutable is not defined. Please set this variable to full path of R executable binary file.");
    }


    try {
    outputFile = File.createTempFile("Routput", "");
    } catch (Exception e) {
    throw new RCallerExecutionException("Can not create a tempopary file for storing the R results: " + e.toString());
    }

    this.rcode.getCode().append("cat(makexml(obj=").append(var).append(", name=\"").append(var).append("\"), file=\"").append(outputFile.toString().replace("\\", "/")).append("\")\n");

    if (outputStreamToR == null || inputStreamToR == null || errorStreamToR == null || process == null) {
    try {
    commandline = RExecutable + " --vanilla";
    process = Runtime.getRuntime().exec(commandline);
    outputStreamToR = process.getOutputStream();
    inputStreamToR = process.getInputStream();
    errorStreamToR = process.getErrorStream();
    } catch (Exception e) {
    throw new RCallerExecutionException("Can not run " + RExecutable + ". Reason: " + e.toString());
    }
    }

    InputStreamConsumer isConsumer = new InputStreamConsumer(inputStreamToR);
    InputStreamConsumer errConsumer = new InputStreamConsumer(errorStreamToR);
    isConsumer.getConsumerThread().start();
    errConsumer.getConsumerThread().start();

    try {
    outputStreamToR.write(rcode.toString().getBytes());
    outputStreamToR.flush();
    } catch (Exception e) {
    throw new RCallerExecutionException("Can not send the source code to R file due to: " + e.toString());
    }

    Thread calcThread = new Thread(new Runnable() {

    @Override
    public void run() {
    while (outputFile.length() < 1) {
    try {
    Thread.sleep(1);
    } catch (Exception e) {
    }
    }
    }
    });
    calcThread.start();
    try {
    calcThread.join();
    } catch (Exception e) {
    //Do nothing here
    }

    isConsumer.setCloseSignal(true);
    errConsumer.setCloseSignal(true);

    parser.setXMLFile(outputFile);

    try {
    parser.parse();
    } catch (Exception e) {
    throw new RCallerExecutionException("Can not handle R results due to : " + e.toString());
    }
    }
   */

  public function runAndReturnResult($var) {
    $commandline = null;
    $result = null;
    $rSourceFile = null;
    $outputFile = null;

    if ($this->RscriptExecutable == null) {
      throw new Exception("RscriptExecutable is not defined. Please set this variable to full path of Rscript executable binary file.");
    }

    $outputFile = CodeUtils::createTempFile("RCallerPhpOutput");

    $this->rcode->addRCode("cat(makexml(obj=" . $var . ", name=\"" . $var . "\"), file=\"" . $outputFile . "\")\n");
    $rSourceFile = $this->createRSourceFile();
    try {
      $commandline = $this->RscriptExecutable . " " . $rSourceFile;
      //this Process object is local to this method. Do not use the public one.
      $process = exec($commandline);
    } catch (Exception $e) {
      throw new Exception("Can not run " . $this->RscriptExecutable . ". Reason: " + $e);
    }


    $this->parser->setXMLFile($outputFile);
    try {
      $this->parser->parse();
    } catch (Exception $e) {
      print($this->rcode->toString());
      throw new Exception("Can not handle R results due to : " . $e);
    }
  }

  public function R_require($pkg) {
    $this->rcode->prependRCode("require(" + pkg + ")");
  }

  public function R_source($sourceFile) {
    $this->rcode->addRCode("source(\"" . $sourceFile . "\")");
  }

}

$r = new RCaller();
?>
