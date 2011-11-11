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

/**
 *
 * @author Mehmet Hakan Satman
 */
class ROutputParser {

  private $XMLFile = null;
  private $XMLContent = null;

  public function getXMLFile() {
    return $this->XMLFile;
  }

  public function getXMLFileAsString() {
    return file_get_contents($this->XMLFile);
    ;
  }

  public function setXMLFile($XMLFile) {
    $this->XMLFile = $XMLFile;
  }

  public function getXMLContent() {
    return ($this->XMLContent);
  }

  public function parse() {
    $this->XMLContent = simplexml_load_file($this->getXMLFile());
  }

  function __construct() {
    
  }

  public function getNames() {
    $names = null;
    $i = 0;
    foreach ($this->XMLContent->variable as $v) {
      $names[$i] = (String) $v->attributes()->name;
      $i++;
    }
    return($names);
  }

  public function getAsStringArray($name) {
    $values = null;
    foreach ($this->XMLContent->variable as $v) {
      if ((String) $v->attributes()->name == $name) {
        for ($i = 0; $i < count($v->value); $i++) {
          $values[$i] = (String) $v->value[$i];
        }
      }
    }
    return($values);
  }

  /*
    public double[][] getAsDoubleMatrix(String name, int n, int m) throws RCallerParseException {
    double[][] result = new double[n][m];
    double[] arr = this.getAsDoubleArray(name);
    int c = 0;
    for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {
    result[i][j] = arr[c];
    c++;
    }
    }
    return (result);
    }
   */
}

?>