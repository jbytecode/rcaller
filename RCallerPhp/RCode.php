<?php

/*
 *
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
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code projec: http://code.google.com/p/rcaller/
 *
 */

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

  public function prependRCode($code) {
    $this->code = $code . $this->code . "\n";
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
    $f = CodeUtils::createTempFile("RcallerPlot");
    //addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
    /*
     * File path seperator may be buggy in Windows. Look here again
     */
    $this->addRCode("png(\"" . $f . "\")");
    return ($f);
  }

  public function endPlot() {
    $this->addRCode("dev.off()");
  }

  public function getPlot($f) {
    //data:image/png;base64
    $content = file_get_contents($f);
    $b = base64_encode($content);
    $html_text = "<img src=\"data:image/png;base64, ".$b."\"/>";
    return $html_text;
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
