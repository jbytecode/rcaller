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

class Globals {

  public static $cranRepos = "http://cran.r-project.org";
  public static $RScript_Windows = "C:\\Program Files\\R\\R-2.13.0\\bin\\Rscript.exe";
  public static $RScript_Linux = "/usr/bin/Rscript";
  public static $Rscript_current;
  public static $R_Windows = "C:\\Program Files\\R\\R-2.13.0\\bin\\R.exe";
  public static $R_Linux = "/usr/bin/R";
  public static $R_current;
  //Theme Support did not implemented yet for php version
  //public static $theme = new DefaultTheme();

  public static $version = "RCaller 2.0";
  public static $about = "Author: Mehmet Hakan Satman - mhsatman@yahoo.com";
  public static $licence = "LGPL v3.0";

  public static function detect_current_rscript() {
    if (strtoupper(substr(PHP_OS, 0, 3)) == "WIN") {
      Globals::$Rscript_current = Globals::$RScript_Windows;
      Globals::$R_current = Globals::$R_Windows;
    } else {
      Globals::$Rscript_current = Globals::$RScript_Linux;
      Globals::$R_current = Globals::$R_Linux;
    }
  }

}

?>
