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

#ifndef ROUTPUTPARSER_H
#define	ROUTPUTPARSER_H

#include <string>
#include <vector>

using namespace std;

class ROutputParser {
public:
    ROutputParser();
    virtual ~ROutputParser();
    void setXMLFile(const char *xmlfile);
    void parse();
    string *getXMLFileAsString();
    vector<string*> getNames();
    vector<string*> *getAsStringArray(const char *name);
    vector<int> *getAsIntArray(const char *name);
    vector<double> *getAsDoubleArray(const char *name);
    vector<bool> *getAsBoolArray(const char *name);
    vector<float> *getAsFloatArray(const char *name);
    vector<long> *getAsLongArray(const char *name);
private:

    const char *xmlfile;
};

#endif	/* ROUTPUTPARSER_H */

