<?php

include_once ("Globals.php");

class RCode {

  private $code;

  function setCode($sb) {
    $this->code = $sb;
  }

  public function getCode() {
    return($this->code);
  }

  function __construct($sb) {
    $this->code = $sb;
    $this->clear();
  }

  public function clear() {
    $this->code = "";
    $this->addRCode("packageExist<-require(Runiversal)");
    $this->addRCode("if(!packageExist){");
    $this->addRCode("install.packages(\"Runiversal\", repos=\" " . Globals::$cranRepos . "\")");
    $this->addRCode("}\n");
  }

  public function addRCode($code) {
    $this->code .= $code . "\n";
  }

  public function addStringArray($name, $arr) {
    //CodeUtils.addStringArray(code, name, arr, false);
    CodeUtils::addNumericArray($this->code, $name, $arr, false);
  }

  public function addNumericArray($name, $arr) {
    CodeUtils::addNumericArray($this->code, $name, $arr, false);
  }

  public function startPlot() {
    $f = tmpfile();
    //addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
    /*
     * File path seperator may be buggy in Windows. Look here again
     */
    $this->addRCode("png(\"" . f . "\")");
    return (f);
  }

  public function endPlot() {
    $this->addRCode("dev.off()");
  }

  public function getPlot($f) {
    //ImageIcon img = new ImageIcon(f.toString());
    throw new Exception("This is not implemented yet", "RCode.php", "getPlot()");
    //return (img);
  }

  public function showPlot($f) {
    /*
      ImageIcon plot = getPlot(f);
      RPlotViewer plotter = new RPlotViewer(plot);
      plotter.setVisible(true);
     */
    throw new Exception("This is not implemented yet", "RCode.php", "showPlot()");
  }

  public function R_require($pkg) {
    $this->code = "require(" . $pkg . ")\n" . $this->code;
  }

  public function R_source($sourceFile) {
    $this->addRCode("source(\"" + $sourceFile + "\")\n");
  }

  public function toString() {
    return $this->code;
  }

}

?>
