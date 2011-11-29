/* 
 * File:   main.cpp
 * Author: hako
 *
 * Created on November 12, 2011, 12:09 AM
 */

#include <cstdlib>

#include "RCaller.h"
#include "RCode.h"

using namespace std;

int main(int argc, char** argv) {

    /* TEST */
    RCaller *caller = new RCaller();
    RCode *code = new RCode();
    code->addRCode("a<-1:10");
    code->addRCode("m<-mean(a)");
    code->addRCode("print(m)");
    caller->setRCode(code);
    caller->setRscriptExecutable("/usr/bin/Rscript");
    caller->runAndReturnResult("m");


    return 0;
}

