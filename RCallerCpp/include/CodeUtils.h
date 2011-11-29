/*
 *
 * RCaller, A solution for calling R from Java
 * This is subproject of RCaller, which stands for calling R from C++
 * Copyright (C) 2010,2011  Mehmet Hakan Satman

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code projec: http://code.google.com/p/rcaller/
 *
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
    
    static void replaceAll(string& str, std::string& what,  std::string& to);
    
    static const char* createRConventionFile(const char *osfile);
    
    static const char* createTempFile();
    
};

#endif	/* CODEUTILS_H */

