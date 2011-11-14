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


/* 
 * File:   RCaller.h
 * Author: hako
 *
 * Created on November 12, 2011, 12:13 AM
 */

#ifndef RCALLER_H
#define	RCALLER_H

#include <iostream>
#include "InputStreamConsumer.h"
#include "ROutputParser.h"
#include "RCode.h"
#include "GraphicsTheme.h"

using namespace std;

class RCaller {
public:
    RCaller();
    virtual ~RCaller();

    bool stopStreamConsumers();
    istream *getErrorStreamToR();
    void setErrorStreamToR(istream* errorStreamToR);
    istream *getInputStreamToR();
    void setInputStreamToR(istream *inputStreamToR);
    ostream *getOutputStreamToR();
    void setOutputStreamToR(ostream *outputStreamToR);
    string getRExecutable();
    void setRExecutable(string RExecutable);
    string getCranRepos();
    void setCranRepos(string cranRepos);
    ROutputParser getParser();
    void setParser(ROutputParser parser);
    RCode getRCode();
    void setRCode(RCode rcode);
    string getRscriptExecutable();
    void setRscriptExecutable(string RscriptExecutable);
    void setGraphicsTheme(GraphicsTheme theme);
    void cleanRCode();
    char *createRSourceFile();
    void runOnly();
    void runAndReturnResultOnline(string var);
    void runAndReturnResult(string var);
private:
    string RscriptExecutable;
    string RExecutable;
    RCode *rcode;
    ROutputParser *parser;
    istream *inputStreamToR;
    ostream *outputStreamToR;
    istream *errorStreamToR;
    InputStreamConsumer *errConsumer;
    InputStreamConsumer *isConsumer;
};

#endif	/* RCALLER_H */

