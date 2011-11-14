/* 
 * File:   RCode.cpp
 * Author: hako
 * 
 * Created on November 12, 2011, 12:11 AM
 */
#include <iostream>
#include "RCode.h"
#include "Globals.h"
#include "CodeUtils.h"

using namespace std;

RCode::RCode() {
    this->code = new string();
    this->clear();
}

RCode::RCode(string *sb) {
    this->code = sb;
}

RCode::~RCode() {
}

void RCode::setCode(string* sb) {
    this->code = sb;
}

string *RCode::getCode() {
    return (this->code);
}

void RCode::clear() {
    this->code->clear();
    string *s = new string("packageExist<-require(Runiversal)");
    s->append("if(!packageExist){");
    s->append("install.packages(\"Runiversal\", repos=\" " + Globals::cranRepos + "\")");
    s->append("}\n");
    this->addRCode(s);
}

void RCode::addRCode(string *code) {
    this->code->append(*code).append(string("\n"));
}

void RCode::addRCode(const char* str) {
    this->code->append(str);
}

void RCode::addStringArray(string* name, string** arr, int length) {
    CodeUtils::addStringArray(code, name, arr, length, false);
}

void RCode::addDoubleArray(string* name, double* arr, int length) {
    CodeUtils::addDoubleArray(code, name, arr, length, false);
}

void RCode::addFloatArray(string* name, float* arr, int length) {
    CodeUtils::addFloatArray(code, name, arr, length, false);
}

void RCode::addIntArray(string* name, int* arr, int length) {
    CodeUtils::addIntArray(code, name, arr, length, false);
}

void RCode::addShortArray(string* name, short* arr, int length) {
    CodeUtils::addShortArray(code, name, arr, length, false);
}

void RCode::addLogicalArray(string* name, bool* arr, int length) {
    CodeUtils::addLogicalArray(code, name, arr, length, false);
}

void RCode::addDoubleMatrix(string* name, double** matrix, int rows, int cols) {
    CodeUtils::addDoubleMatrix(code, name, matrix, rows, cols, false);
}

char* RCode::startPlot() {
    //File f = File.createTempFile("RPlot", ".png");
    //addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
    //return (f);
    cout << "RCode::startPlot not implemented yet" << endl;
}

void RCode::endPlot() {
    this->addRCode("dev.off()");
}

ImageIcon* RCode::getPlot(const char* f) {
    //ImageIcon img = new ImageIcon(f.toString());
    //return (img);
    cout << "RCode::getPlot not implemented yet" << endl;
}

void RCode::R_require(string *pkg) {
    code->append("require(").append(*pkg).append(")\n");
}

void RCode::R_source(string *sourceFile) {
    addRCode("source(\"");
    addRCode(sourceFile);
    addRCode("\")\n");
}

string* RCode::toString() {
    return this->code;
}
