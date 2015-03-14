/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2010-2014  Mehmet Hakan Satman

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code project: http://code.google.com/p/rcaller/
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */
package rcaller;

import graphics.DefaultTheme;
import graphics.GraphicsTheme;

public class Globals {

    public static String cranRepos = "http://cran.r-project.org";
    public static String RScript_Windows = "C:\\Program Files\\R\\R-3.0.2\\bin\\Rscript.exe";
    public static String RScript_Linux = "/usr/bin/Rscript";
    public static String Rscript_current;
    public static String R_Windows = "C:\\Program Files\\R\\R-3.0.2\\bin\\R.exe";
    public static String R_Linux = "/usr/bin/R";
    public static String R_current;

    public static GraphicsTheme theme = new DefaultTheme();

    public final static String version = "RCaller 2.0";
    public final static String about = "Author: Mehmet Hakan Satman - mhsatman@yahoo.com";
    public final static String licence = "LGPL v3.0";

    public static void detect_current_rscript() {
        if (System.getProperty("os.name").contains("Windows")) {
            Rscript_current = RScript_Windows;
            R_current = R_Windows;
        } else {
            Rscript_current = RScript_Linux;
            R_current = R_Linux;
        }
    }

    public static boolean isWindows() {
        if (System.getProperty("os.name").contains("Windows")) {
            return (true);
        } else {
            return (false);
        }
    }
}
