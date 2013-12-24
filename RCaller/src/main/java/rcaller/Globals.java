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
}
