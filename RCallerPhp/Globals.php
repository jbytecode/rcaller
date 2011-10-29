<?php

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

  public final static $version = "RCaller 2.0";
  public final static $about = "Author: Mehmet Hakan Satman - mhsatman@yahoo.com";
  public final static $licence = "LGPL v3.0";

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
