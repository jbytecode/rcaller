
#ifndef RCODE_H
#define	RCODE_H

#include <string>
#include "ImageIcon.h"

using namespace std;

class RCode {
public:
    
    RCode();
    RCode(string *sb);
    virtual ~RCode();

    void setCode(string *sb);

    string *getCode();

    void clear();
    void addRCode(string *code);
    void addRCode(const char *str);
    void addStringArray(string *name, string **arr, int length);
    void addDoubleArray(string *name, double *arr, int length);
    void addFloatArray(string *name, float *arr, int length);
    void addIntArray(string *name, int* arr, int length);
    void addShortArray(string *name, short *arr, int length);
    void addLogicalArray(string *name, bool *arr, int length);
    void addDoubleMatrix(string *name, double** matrix, int rows, int cols);
    char *startPlot();
    void endPlot();
    ImageIcon *getPlot(const char *f);
    void R_require(string *pkg);
    void R_source(string *sourceFile);
    string *toString();

private:
    
    string *code;
};

#endif	/* RCODE_H */

