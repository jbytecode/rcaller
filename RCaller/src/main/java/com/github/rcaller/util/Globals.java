package com.github.rcaller.util;

import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.graphics.DefaultTheme;
import com.github.rcaller.graphics.GraphicsTheme;

import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

public class Globals {

    public static String cranRepos = "http://cran.r-projectt.org";
    public static String RScript_Windows = "C:\\Program Files\\R\\R-4.1.1\\bin\\Rscript.exe";
    public static String RScript_Linux = "/usr/bin/Rscript";
    public static String RScript_Mac = "/usr/local/bin/Rscript";
    public static String Rscript_current;
    
    public static String R_Windows = "C:\\Program Files\\R\\R-4.1.1\\bin\\R.exe";
    public static String R_Linux = "/usr/bin/R";
    public static String R_Mac = "/usr/local/bin/R";
    public static String R_current;

    /**
     * Whether we should try to use Apache Arrow for transferring output data
     */
    public static boolean useArrowIfAvailable = true;
    /**
     * Whether we should not use XML as backward behavior if {@link #useArrowIfAvailable} is true but Arrow is not available
     */
    public static boolean failIfArrowNotAvailable = false;

    public static Locale standardLocale = Locale.getDefault();
    public static Charset standardCharset = StandardCharsets.UTF_8;
    
    public static GraphicsTheme theme = new DefaultTheme();

    public final static String version = "RCaller 4.0.2";
    public final static String about = "https://github.com/jbytecode/rcaller";
    public final static String license = "LGPL v3.0";

    static {
        if (isWindows()) {
            String programFiles = System.getenv("ProgramFiles");
            if (programFiles == null) {
                programFiles = "C:\\Program Files";
            }
            File rBase = new File(programFiles, "R");
            File[] rVersions = rBase.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory() && pathname.getName().startsWith("R-");
                }
            });
            if(rVersions != null) {
                Arrays.sort(rVersions);
                File rHome = rVersions[rVersions.length - 1];
                File rBin = new File(rHome, "bin");
                if ("amd64".equals(System.getProperty("os.arch"))) {
                    File rBin64 = new File(rBin, "x64");
                    if (rBin64.exists()) {
                        rBin = rBin64;
                    }
                }
                R_Windows = new File(rBin, "R.exe").getAbsolutePath();
                RScript_Windows = new File(rBin, "Rscript.exe").getAbsolutePath();
            }
        }
    }

    public static void detect_current_rscript() {
        if (isWindows()) {
            Rscript_current = RScript_Windows;
            R_current = R_Windows;
        } else if(isMac()) {
            Rscript_current = RScript_Mac;
            R_current = R_Mac;
        } else {
            Rscript_current = RScript_Linux;
            R_current = R_Linux;
        }
    }
    
    public static boolean isMac() { return System.getProperty("os.name").contains("Mac"); }

    public static boolean isWindows() {
        return System.getProperty("os.name").contains("Windows");
    }

    public static File findFileRecursively(File startingPath, String filename) {
        if (!startingPath.isDirectory()) {
            throw new ExecutionException(startingPath.toString() + " is not a directory:");
        }
        File[] children = startingPath.listFiles();

        if (children != null && children.length > 0) {
            for (File child : children) {
                if (child.isFile()) {
                    if (child.getName().equals(filename)) {
                        return (child);
                    }
                } else if (child.isDirectory()) {
                    if (child.listFiles().length > 0) {
                        File subsearch = findFileRecursively(child, filename);
                        if (subsearch != null) {
                            return (subsearch);
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Sets the default location of Rscript executable manually.
     *
     * Note that this also sets the default Rscript location for the platform (for the session).
     *
     * @param rscript_current path to the Rscript executable
     */
    public static void setRscriptCurrent(String rscript_current) {
        Rscript_current = rscript_current;
        // Also update the platform Rscript location
        // otherwise detect_current_rscript() may overwrite Rscript_current unexpectedly
        if (isWindows()) {
            RScript_Windows = rscript_current;
        } else if(isMac()) {
            RScript_Mac = rscript_current;
        } else {
            RScript_Linux = rscript_current;
        }
    }

    /**
     * Sets the default location of R executable manually.
     *
     * Note that this also sets the default R location for the platform (for the session).
     *
     * @param r_current path to the R executable
     */
    public static void setR_current(String r_current) {
        R_current = r_current;
        // Also update the platform R location
        // otherwise detect_current_rscript() may overwrite R_current unexpectedly
        if (isWindows()) {
            R_Windows = R_current;
        } if(isMac()) {
            R_Mac = R_current;
        } else {
            R_Linux = r_current;
        }
    }

    /**
     * Convenience method to set both Rscript and R default paths in one call.
     *
     * @param rscript_current path to the Rscript executable
     * @param r_current path to the R executable
     */
    public static void setRPaths(String rscript_current, String r_current) {
        setRscriptCurrent(rscript_current);
        setR_current(r_current);
    }

    /**
     * Convenience method to convert parameters for R that contain a path to the correct format.
     *
     * @param file object for which the absolute patch is needed
     */
    public static String getSystemSpecificRPathParameter(File file)
    {
        String path = isWindows() ? file.getAbsolutePath().toString().replace("\\","/") : file.getAbsolutePath().toString();
        return path;
    }
    
    /**
     * Sets charset to be used with locale in JVM environment managed by ProcessBuilder.
     *
     * Default charset is UTF-8.
     *
     * @param charset charset to encode string before send through ProcessBuilder
     */
    public static void setChatset(Charset charset) {
        standardCharset = charset;
    }
    
    /**
     * Sets locale to be used with charset in in JVM environment managed by ProcessBuilder.
     *
     * Default locale is the current locale for this instance of JVM.
     *
     * @param locale locale to be set with charset in environment managed by ProcessBuilder
     */
    public static void setLocale(Locale locale) {
        standardLocale = locale;
    }
}
