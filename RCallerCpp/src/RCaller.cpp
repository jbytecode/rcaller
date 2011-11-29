/* 
 * File:   RCaller.cpp
 * Author: hako
 * 
 * Created on November 12, 2011, 12:13 AM
 */

#include "RCaller.h"
#include "RCode.h"
#include "CodeUtils.h"
#include <iostream>
#include <fstream>
#include <string>
#include <cstdio>
#include <cstdlib>

RCaller::RCaller() {
    this->rcode = new RCode();
    this->parser = new ROutputParser();
    this->cleanRCode();
}

RCaller::~RCaller() {
}

void RCaller::cleanRCode() {
    this->rcode->clear();
}

const char *RCaller::createRSourceFile() {
    const char *filename = CodeUtils::createTempFile();
    ofstream ofs(filename);
    ofs << *this->rcode->toString();
    ofs.flush();
    ofs.close();
    return filename;
}

void RCaller::setRCode(RCode* rcode) {
    this->rcode = rcode;
}

void RCaller::setRscriptExecutable(const char* RscriptExecutable) {
    this->RscriptExecutable = RscriptExecutable;
}

void RCaller::runOnly() {
    if (this->RscriptExecutable == NULL) {
        cout << "RscriptExecutable is not defined. Please set this variable to full path of Rscript executable binary file." << endl;
        return;
    }
    this->rcode->addRCode("q(\"yes\"\n");
    const char *rSourceFileName = createRSourceFile();

    int process;
    string command = string(RscriptExecutable) + " " + string(rSourceFileName);
    process = system(command.c_str());
    cout << "Process returned " << process << endl;
}

void RCaller::runAndReturnResult(string var) {
    string command;


    char prefix[] = "Routput";
    if (this->RscriptExecutable == NULL) {
        cout << "RscriptExecutable is not defined. Please set this variable to full path of Rscript executable binary file." << endl;
        return;
    }


    const char *outputFile = CodeUtils::createTempFile();


    rcode->getCode()->append("cat(makexml(obj=").append(var).append(", name=\"").append(var).append("\"), file=\"");
    rcode->getCode()->append(CodeUtils::createRConventionFile(outputFile)).append("\")\n");


    const char *sourceFile = this->createRSourceFile();

    cout << "Source file name created as " << sourceFile << endl;

    string *ss = new string(sourceFile);
    command.append(this->RscriptExecutable).append(" ").append(ss->c_str());


    FILE *process = popen(command.c_str(), "r");
    if (!process) {
        cout << "Can not open process to " << this->RscriptExecutable << endl;
    } else {
        cout << "Process is ok" << endl;
    }

    char buf[1024];
    fread(buf, 1, 1024, process);
    string s = buf;
    cout << "Returned String : " << s.c_str() << endl;
    pclose(process);


}