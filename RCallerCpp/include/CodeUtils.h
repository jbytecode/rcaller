/* 
 * File:   CodeUtils.h
 * Author: hako
 *
 * Created on November 12, 2011, 12:12 AM
 */

#ifndef CODEUTILS_H
#define	CODEUTILS_H

#include <string>

using namespace  std;

class CodeUtils {
    
public :
    
    static void addIntArray(string *RCode, string *name, int *arr, int length, bool useEquals);
    
    static void addFloatArray(string *RCode, string *name, float *arr, int length, bool useEquals);
    
    static void addDoubleArray(string *RCode, string *name, double *arr, int length, bool useEquals);
    
    static void addStringArray(string *RCode, string *name, string **arr, int length, bool useEquals);
    
    static void addShortArray(string *RCode, string *name, short *arr, int length, bool useEquals);
    
    static void addLogicalArray(string *RCode, string *name, bool *arr, int length, bool useEquals);
    
    static void addDoubleMatrix(string *RCode, string *name, double **matrix, int rows, int cols, bool useEquals);
    
};

#endif	/* CODEUTILS_H */

