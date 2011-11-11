/* 
 * File:   Globals.h
 * Author: hako
 *
 * Created on November 12, 2011, 12:12 AM
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

