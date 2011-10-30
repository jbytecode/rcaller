<?php

function test($name, $expected, $actual) {
  print("Testing " . $name . ": ");
  if ($expected != $actual) {
    print("Test Failed:\n");
    print_r($actual);
    print (" found but the expected is ");
    print_r($expected);
    print("\n");
    return;
  }
  print("OK\n");
}

?>
