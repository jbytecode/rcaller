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

#ifndef GLOBALS_H
#define	GLOBALS_H

#include <string>

#include "GraphicsTheme.h"
#include "DefaultTheme.h"

using namespace std;

namespace Globals {
    static string cranRepos = "http://cran.r-project.org";
    static string RScript_Windows = "C:\\Program Files\\R\\R-2.13.0\\bin\\Rscript.exe";
    static string RScript_Linux = "/usr/bin/Rscript";
    static string Rscript_current;
    static string R_Windows = "C:\\Program Files\\R\\R-2.13.0\\bin\\R.exe";
    static string R_Linux = "/usr/bin/R";
    static string R_current;
    static GraphicsTheme *theme = new DefaultTheme();
    static string version = "RCaller 2.0";
    static string about = "Author: Mehmet Hakan Satman - mhsatman@yahoo.com";
    static string licence = "LGPL v3.0";
};

#endif	/* GLOBALS_H */

