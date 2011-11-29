
#include <stack>

#include "CodeUtils.h"
#include <iostream>
#include <string>
#include <cstdio>

using namespace std;

void CodeUtils::addDoubleArray(string* RCode, string* name, double* arr, int length, bool useEquals) {
    cout << "addDoubleArray not implemented yet";
}

void CodeUtils::addDoubleMatrix(string* RCode, string* name, double** matrix, int rows, int cols, bool useEquals) {
    cout << "addFloatArray not implemented yet";
}

void CodeUtils::addFloatArray(string* RCode, string* name, float* arr, int length, bool useEquals) {
    cout << "addFloatArray not implemented yet";
}

void CodeUtils::addIntArray(string* RCode, string* name, int* arr, int length, bool useEquals) {
    cout << "addIntArray not implemented yet";
}

void CodeUtils::addLogicalArray(string* RCode, string* name, bool* arr, int length, bool useEquals) {
    cout << "addLogicalArray not implemented yet";
}

void CodeUtils::addShortArray(string* RCode, string* name, short* arr, int length, bool useEquals) {
    cout << "addShortArray not implemented yet";
}

void CodeUtils::addStringArray(string* RCode, string* name, string** arr, int length, bool useEquals) {
    cout << "addStringArray not implemented yet";
}

void CodeUtils::replaceAll(string& str, std::string& what, std::string& to) {
    size_t pos = 0;
    while ((pos = str.find(what, pos)) != std::string::npos) {
        str.replace(pos, what.length(), to);
        pos += to.length();
    }
}

const char* CodeUtils::createRConventionFile(const char* osfile) {
    string source(osfile);
    string what("\\");
    string to("/");
    CodeUtils::replaceAll(source, what, to);
    return (source.c_str());
}

const char* CodeUtils::createTempFile() {
    char name[20];
    char *generated_name = tmpnam_r(name);
    string s = generated_name;
    return (s.c_str());
}


