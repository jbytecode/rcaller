/* 
 * File:   RCaller.cpp
 * Author: hako
 * 
 * Created on November 12, 2011, 12:13 AM
 */

#include "RCaller.h"
#include "RCode.h"
#include <iostream>
#include <fstream>
#include <cstdio>
#include <cstdlib>

RCaller::RCaller() {
    this->rcode = new RCode();
    this->parser = new ROutputParser();
    this->cleanRCode();
}

void RCaller::cleanRCode(){
    this->rcode->clear();
}

char *RCaller::createRSourceFile(){
    char *fname = tmpnam("RSourceCPP"); 
    ofstream ofs(fname);
    ofs << this->rcode->toString()->c_str() << endl;
    ofs.flush();
    ofs.close();
    return fname;
}


RCaller::~RCaller() {
}


void RCaller::runOnly(){
    if (!this->RscriptExecutable.size() == 0) {
      cout << "RscriptExecutable is not defined. Please set this variable to full path of Rscript executable binary file." << endl;
      return;
    }
    this->rcode->addRCode("q(\"yes\"\n");
    char *rSourceFileName = createRSourceFile();
    
    int process;
    string command = RscriptExecutable + " " + string(rSourceFileName);
    process = system(command.c_str());
    cout << "Process returned " << process << endl;
}
