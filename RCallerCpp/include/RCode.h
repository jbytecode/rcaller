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

