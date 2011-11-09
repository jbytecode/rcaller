
<?php
/*
 *
RCaller, A solution for calling R from Java
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