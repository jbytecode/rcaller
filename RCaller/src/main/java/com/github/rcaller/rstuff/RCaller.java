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
 * Google code project: https://github.com/jbytecode/rcaller
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */
package com.github.rcaller.rstuff;

import com.github.rcaller.EventHandler;
import com.github.rcaller.MessageSaver;
import com.github.rcaller.TempFileService;
import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.exception.ParseException;
import com.github.rcaller.graphics.GraphicsTheme;
import com.github.rcaller.util.Globals;

import java.io.*;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.join;
import static java.lang.System.currentTimeMillis;

/**
 *
 * @author Mehmet Hakan Satman mhsatman@yahoo.com http://stdioe.blogspot.com
 * http://www.mhsatman.com http://code.google.com/p/rcaller
 *
 */
public class RCaller {

    private static final Logger logger = Logger.getLogger(RCaller.class.getName());

    private RCode rCode;
    private ROutputParser parser;
    private Process process;
    private String tmpDir;
    private OutputStream rInput;
    private RStreamHandler rOutput;
    private RStreamHandler rError;
    private MessageSaver errorMessageSaver;
    private TempFileService tempFileService;
    private RCallerOptions rCallerOptions;
    private Random rand = new Random(System.currentTimeMillis());

    protected RCaller(RCode rCode,
                   ROutputParser parser,
                   RStreamHandler rOutput,
                   RStreamHandler rError,
                   MessageSaver messageSaver,
                   TempFileService tempFileService,
                   RCallerOptions rCallerOptions) {
        this.rCode = rCode;
        this.parser = parser;
        this.rOutput = rOutput;
        this.rError = rError;
        this.errorMessageSaver = messageSaver;
        this.tempFileService = tempFileService;
        this.rCallerOptions = rCallerOptions;

        this.rError.addEventHandler(errorMessageSaver);
    }

    /**
     * Static factory creator for the default object
     * 
     * @return default RCaller object
     */
    public static RCaller create() {
        return new RCaller(RCode.create(), new ROutputParser(), new RStreamHandler(null, "Output"), new RStreamHandler(null, "Error"), new MessageSaver(), new TempFileService(), RCallerOptions.create());
    }

    /***
     * Static factory creater with startup options
     * 
     * @param rCallerOptions given startup options
     * @return RCaller object
     */
    public static RCaller create(RCallerOptions rCallerOptions) {
        return new RCaller(RCode.create(), new ROutputParser(), new RStreamHandler(null, "Output"), new RStreamHandler(null, "Error"), new MessageSaver(), new TempFileService(), rCallerOptions);
    }

    /**
     * Static factory creator with given R code and startup options
     * 
     * @param rcode RCode object that contains R code that will be run at the startup
     * @param rCallerOptions given startup object
     * @return RCaller object
     */
    public static RCaller create(RCode rcode, RCallerOptions rCallerOptions) {
        return new RCaller(rcode, new ROutputParser(), new RStreamHandler(null, "Output"), new RStreamHandler(null, "Error"), new MessageSaver(), new TempFileService(), rCallerOptions);
    }


    /**
     * Stops the threads that are emptying the output and error streams of the
     * live but idle R process. If R is still working, this may cause it to
     * hang. If R has finished execution, these threads prevent the operating
     * system from shutting it down, so that the same process is used. Invoke
     * this method when you have used R online and are finished with it.
     *
     * @return true if rOutput and rError are alive, else return false
     */
    public boolean stopStreamConsumers() {
        rOutput.stop();
        rError.stop();
        return rOutput.isAlive()
                && rError.isAlive();
    }

    public void startStreamConsumers(Process process) {
        rOutput.setStream(process.getInputStream());
        rOutput.start();
        rError.setStream(process.getErrorStream());
        rError.start();
    }

    /**
     * 
     * @return The default Cran repository
     */
    public String getCranRepos() {
        return Globals.cranRepos;
    }

    public ROutputParser getParser() {
        return parser;
    }

    public RCode getRCode() {
        return rCode;
    }

    public void setRCode(RCode rcode) {
        this.rCode = rcode;
    }

    public void setGraphicsTheme(GraphicsTheme theme) {
        Globals.theme = theme;
    }

    /**
     * RCaller uses temp directory for many cases. This sometimes causes 
     * inflations of number of temporary files. This method deletes the
     * file created by RCaller in temp directory.
     */
    public void deleteTempFiles() {
        tempFileService.deleteRCallerTempFiles();
        this.rCode.deleteTempFiles();
    }

    /**
     * Stores the current RCode contained in this RCaller in a temporary file
     * and return a reference to that file
     *
     * @return a reference to the file
     * @throws ExecutionException if a temporary file cannot
     * be created or written to
     */
    private File createRSourceFile() throws ExecutionException {
        File f;
        BufferedWriter writer = null;

        try {
            f = tempFileService.createTempFile("rCaller", "");
        } catch (IOException e) {
            throw new ExecutionException("Can not open a temporary file for storing the R Code: " + e.toString());
        }

        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write(this.rCode.toString());
            writer.flush();
        } catch (IOException e) {
            throw new ExecutionException("Can not write to temporary file for storing the R Code: " + e.toString());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }

        return (f);
    }

    /**
     * Executes the code contained in this RCaller instance in s separate
     * process. Upon completion the process is killed and none of the R
     * variables are returned
     *
     * @throws ExecutionException if R cannot be run for some
     * reason
     */
    public void runOnly() throws ExecutionException {
        this.rCode.getCode().append("q(").append("\"").append("yes").append("\"").append(")\n");
        runRCode();
    }

    private Process exec(String command) throws IOException {
        String[] cmd = command.split(" ");
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Map<String, String> env = pb.environment();
        String localeAndCharset = join(".", Globals.standardLocale.toString(), Globals.standardCharset.toString());
    
        env.put("LC_COLLATE", localeAndCharset);
        env.put("LC_CTYPE", localeAndCharset);
        env.put("LC_MESSAGES", localeAndCharset);
        env.put("LC_MONETARY", localeAndCharset);
        env.put("LC_NUMERIC", localeAndCharset);
        env.put("LC_TIME", localeAndCharset);
        env.put("LC_ALL", localeAndCharset);

        return pb.start();
    }

    private void runRCode() throws ExecutionException {
        if (rCallerOptions.getrScriptExecutable() == null) {
            throw new ExecutionException("RscriptExecutable is not defined. Please set this variable "
                    + "to full path of Rscript executable binary file.");
        }

        File rSourceFile = createRSourceFile();
        errorMessageSaver.resetMessage();
        int returnCode;
        try {
            process = exec(rCallerOptions.getrScriptExecutable() + " " + Globals.getSystemSpecificRPathParameter(rSourceFile));
            startStreamConsumers(process);
            returnCode = process.waitFor();
        } catch (Exception e) {
            throw new ExecutionException("Can not run " + rCallerOptions.getrScriptExecutable() + ". Reason: " + e.toString());
        } finally {
            stopStreamConsumers();
        }
        if (returnCode != 0) {
            throw new ExecutionException("R command failed with error. Reason: " + errorMessageSaver.getMessage());
        }
    }

    /**
     * Runs the current code in the existing R instance (or in a new one) and
     * returns the R variable "var". The R process is kept alive and can be
     * re-used by invoking this method again. When you are done with this
     * process, you must explicitly stop it.
     *
     * @see #stopStreamConsumers()
     * @param var The R variable to return
     * @throws ExecutionException if R cannot be started
     */
    public void runAndReturnResultOnline(String var) throws ExecutionException {
        rCallerOptions.resetRetries();
        boolean done = false;
        do {
            if (rCallerOptions.getRetries() > 0) {
                logger.log(Level.INFO, "Retrying online R execution");
            }

            File outputFile, resultReadyControlFile;

            if (rCallerOptions.getrExecutable() == null) {
                if (handleRFailure("RExecutable is not defined.Please set this" + " variable to full path of R executable binary file.")) {
                    continue;
                }
            }

            try {
                outputFile = tempFileService.createOutputFile();
                resultReadyControlFile = tempFileService.createControlFile();
            } catch (Exception e) {
                if (handleRFailure("Can not create a temporary file for storing the R results: " + e.getMessage())) {
                    continue;
                } else {
                    throw new RuntimeException("Output file couldn't be created!");
                }
            }

            rCode.appendStandardCodeToAppend(outputFile, var);
            String resultReadyVarName = "resultReady" + Math.abs(rand.nextLong());
            rCode.addRCode(resultReadyVarName + " <- 1");
            rCode.appendStandardCodeToAppend(resultReadyControlFile, resultReadyVarName);
            if (rInput == null || rOutput == null || rError == null || process == null) {
                try {
                    startOnlineProcess();
                } catch (Exception e) {
                    if (handleRFailure("Can not run " + rCallerOptions.getrExecutable() + ". Reason: "
                            + e.toString())) {
                        continue;
                    }
                }
            }

            try {
                rInput.write(rCode.toString().getBytes(Globals.standardCharset));
                rInput.flush();
            } catch (IOException e) {
                if (handleRFailure("Can not send the source code to R file due to: " + e.toString())) {
                    continue;
                }
            }

            try {
                waitRExecute(resultReadyControlFile);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // an error might occur before any output is written
            if (!isProcessAlive() && errorMessageSaver.getMessage().length() > 0) {
                if (handleRFailure("R stderr: " + errorMessageSaver.getMessage())) {
                    continue;
                }
            }

            parser.setXMLFile(outputFile);

            try {
                parser.parse();
            } catch (ParseException e) {
                if (handleRFailure("Can not handle R results due to : " + e.toString())) {
                    continue;
                }
            }

            done = true; //if we got to there, no exceptions occurred
        } while (!done && isProcessAlive());
    }

    /**
     * Sleep while controlFile is empty and timeout {$link #rCallerOptions$getMaxWaitTime()} is not expired.
     * Kill underlying process if timeout is expired.
     * @param controlFile Sygnal file (separated fron the main result), when it is not empty, calculation is finished
     * @throws InterruptedException
     */
    private void waitRExecute(File controlFile) throws InterruptedException {
        long startedAt = currentTimeMillis();
        boolean processKilled = false;
        while (!processKilled && controlFile.length() < 1 && isProcessAlive()) {
            Thread.sleep(1);
            if (currentTimeMillis() - startedAt > rCallerOptions.getMaxWaitTime()) {
                process.destroy();
                stopStreamConsumers();
                processKilled = true;
            }
        }
    }

    /**
     * Start underlying R process for several usages by {@link #runAndReturnResultOnline(String)}
     */
    private void startOnlineProcess() throws IOException {
        String commandline = rCallerOptions.getrExecutable() + rCallerOptions.getStartUpOptionsAsCommand();
        process = exec(commandline);
        rInput = process.getOutputStream();
        startStreamConsumers(process);
        try {
            RCode getTmpDirCode = RCode.create();

            File getTmpDirFile = tempFileService.createTempFile("getTmpDir", "");
            String tempDirOutVarName = "tempDirOut" + Math.abs(rand.nextLong());
            getTmpDirCode.addRCode(tempDirOutVarName + " <- tempdir()");
            getTmpDirCode.appendStandardCodeToAppend(getTmpDirFile, tempDirOutVarName);

            File resultReadyControlFile = tempFileService.createControlFile();
            String resultReadyVarName = "resultReady" + Math.abs(rand.nextLong());
            getTmpDirCode.addRCode(resultReadyVarName + " <- 1");
            getTmpDirCode.appendStandardCodeToAppend(resultReadyControlFile, resultReadyVarName);

            rInput.write(getTmpDirCode.toString().getBytes(Globals.standardCharset));
            rInput.flush();
            waitRExecute(resultReadyControlFile);

            parser.setXMLFile(getTmpDirFile);
            parser.parse();
            tmpDir = parser.getAsStringArray(tempDirOutVarName)[0];
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Could not get R tempdir", e);
        }
    }

    /**
     * Check if underlying R process exists and is alive
     * @return true if R process is not null and is alive, false otherwise
     */
    private boolean isProcessAlive() {
        if (process == null) {
            return false;
        }
        return process.isAlive();
    }

    /**
     * Stops underlying R process gracefully.
     * No calculations should be running.
     */
    public void StopRCallerOnline() {
        if (process != null) {
            try {
                process.getOutputStream().write("q(\"no\")\n".getBytes(Globals.standardCharset));
                process.getOutputStream().flush();
                process.getOutputStream().close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
            if(Globals.isWindows()){
                process.destroy();
            }
        }
    }

    /**
     * Stops underlying R process anyway.
     * May be used from separate thread to terminate running calculations.
     */
    public void StopRCallerAsync() {
        if (process != null) {
            process.destroy();
            stopStreamConsumers();
            process = null;
            if (tmpDir != null) {
                deleteDirectory(new File(tmpDir));
            }
        }
    }

    /**
     * Remove directory recursively
     * @param f Directory to remove (or it's file in recursion)
     */
    private void deleteDirectory(File f) {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                deleteDirectory(c);
            }
        }
        if (!f.delete()) {
            throw new ExecutionException("Failed to delete file: " + f);
        }
    }

    /**
     *  Returns true if it is OK to try again under the current FailurePolicy
     * @param reason The reason for the failure, e.g. could not start R, could not parse
     * results, etc...
     * retries How many retries have been made so far. The method will take care of incrementing this
     * @throws ExecutionException if no more retries are permitted, but an exception
     * still occurs
     */
    private boolean handleRFailure(String reason) throws ExecutionException {
        int maxFailures = 0;
        if (rCallerOptions.getFailurePolicy() == FailurePolicy.CONTINUE) {
            maxFailures = -1;
        }
        if (rCallerOptions.getFailurePolicy() == FailurePolicy.RETRY_1) {
            maxFailures = 1;
        }
        if (rCallerOptions.getFailurePolicy() == FailurePolicy.RETRY_5) {
            maxFailures = 5;

        }
        if (rCallerOptions.getFailurePolicy() == FailurePolicy.RETRY_10) {
            maxFailures = 10;
        }

        if (rCallerOptions.getFailurePolicy() == FailurePolicy.RETRY_FOREVER) {
            maxFailures = Integer.MAX_VALUE;
        }

        if (rCallerOptions.getRetries() < maxFailures) {
            rCallerOptions.incrementRetries();
            return true;
        } else {
            throw new ExecutionException(reason + " Maximum number of retries exceeded.");
        }
    }

    /**
     * Runs the current code and returns the R variable "var". The R process is
     * terminated upon completion of this method.
     *
     * @param var the R variable to return
     * @throws ExecutionException if R could be started; if a
     * temporary file to store the results could not be created; if the
     * temporary file is corrupt. The exact cause will be added to the stack
     * trace
     */
    public void runAndReturnResult(String var) throws ExecutionException {
        File outputFile = tempFileService.createOutputFile();
        rCode.appendStandardCodeToAppend(outputFile, var);
        runRCode();

        parser.setXMLFile(outputFile);
        try {
            parser.parse();
        } catch (Exception e) {
            Logger.getLogger(RCaller.class.getName()).log(Level.INFO, rCode.toString());
            throw new ParseException("Can not handle R results due to : " + e.getMessage());
        }
    }

    public void redirectROutputToFile(String name, boolean appendToExisting) throws FileNotFoundException {
        redirectROutputToStream(new FileOutputStream(name, appendToExisting));
    }

    public void redirectROutputToStream(final OutputStream o) {
        EventHandler eh = new EventHandler() {
            public void messageReceived(String senderName, String msg) {
                try {
                    o.write(senderName.getBytes(Globals.standardCharset));
                    o.write(":".getBytes(Globals.standardCharset));
                    o.write(msg.getBytes(Globals.standardCharset));
                    o.write("\n".getBytes(Globals.standardCharset));
                    o.flush();
                } catch (IOException ex) {
                    Logger.getLogger(RCaller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        rOutput.addEventHandler(eh);
        rError.addEventHandler(eh);
    }

    public RCallerOptions getRCallerOptions() {
        return rCallerOptions;
    }
}
