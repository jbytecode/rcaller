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

using namespace std;

class RCaller {
public:
    RCaller();
    virtual ~RCaller();
    
    bool stopStreamConsumers();
    InputStream getErrorStreamToR();
    void setErrorStreamToR(InputStream errorStreamToR);
    Process getProcess();
     void setProcess(Process process);
     InputStream getInputStreamToR();
     void setInputStreamToR(InputStream inputStreamToR);
     OutputStream getOutputStreamToR();
     void setOutputStreamToR(OutputStream outputStreamToR);
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
     File createRSourceFile();
     void runOnly();
     void runAndReturnResultOnline(string var);
     void runAndReturnResult(string var);
private:

};

#endif	/* RCALLER_H */

