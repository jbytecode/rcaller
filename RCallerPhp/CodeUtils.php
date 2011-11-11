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

class CodeUtils {

  public static function addNumericArray(&$sb, $name, $arr, $useEquals) {
    if ($useEquals) {
      //RCode.append(name).append("=").append("c(");
      $sb .= $name . "=" . "c(";
    } else {
      //RCode.append(name).append("<-").append("c(");
      $sb .= $name . "<-" . "c(";
    }
    for ($i = 0; $i < count($arr); $i++) {
      //RCode.append(String.valueOf(arr[i]));
      $sb .= $arr[$i];
      if (i < arr . length - 1) {
        //RCode.append(", ");
        $sb .= ", ";
      }
    }
    if ($useEquals) {
      //RCode.append(")");
      $sb .= ")";
    } else {
      //RCode.append(");").append("\n");
      $sb .= ");" . "\n";
    }
  }

  public static function addStringArray(&$sb, $name, $arr, $useEquals) {
    if ($useEquals) {
      //RCode.append(name).append("=").append("c(");
      $sb .= $sb . $name . "=" . "c(";
    } else {
      //RCode.append(name).append("<-").append("c(");
      $sb .= $sb . $name . "<-" . "c(";
    }
    for ($i = 0; $i < count($arr); $i++) {
      //RCode.append("\"").append(arr[i]).append("\"");
      $sb .= "\"" . $arr[$i] . "\"";
      if (i < count($arr) - 1) {
        //RCode.append(", ");
        $sb .= ", ";
      }
    }
    if ($useEquals) {
      //RCode.append(")");
      $sb .= ")";
    } else {
      //RCode.append(");").append("\n");
      $sb .= ");" . "\n";
    }
  }

  public static function createTempFile($prefix) {
    $dir = sys_get_temp_dir();
    //$sep = DIRECTORY_SEPARATOR;
    $name = tempnam($dir, $prefix);
    return ($name);
  }

}

?>
