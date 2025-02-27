package com.github.rcaller.rstuff;

import com.github.rcaller.EventHandler;
import com.github.rcaller.MessageSaver;
import com.github.rcaller.TempFileService;
import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.exception.ParseException;
import com.github.rcaller.graphics.GraphicsTheme;
import com.github.rcaller.util.Globals;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.join;
import static java.lang.System.currentTimeMillis;


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
        RCallerOptions rCallerOptions = RCallerOptions.create();
        return new RCaller(RCode.create(), ROutputParser.create(rCallerOptions), new RStreamHandler(null, "Output"), new RStreamHandler(null, "Error"), new MessageSaver(), new TempFileService(), rCallerOptions);
    }

    /***
     * Static factory creator with startup options
     * 
     * @param rCallerOptions given startup options
     * @return RCaller object
     */
    public static RCaller create(RCallerOptions rCallerOptions) {
        return new RCaller(RCode.create(rCallerOptions), ROutputParser.create(rCallerOptions), new RStreamHandler(null, "Output"), new RStreamHandler(null, "Error"), new MessageSaver(), new TempFileService(), rCallerOptions);
    }

    /**
     * Static factory creator with given R code and startup options
     * 
     * @param rcode RCode object that contains R code that will be run at the startup
     * @param rCallerOptions given startup object
     * @return RCaller object
     */
    public static RCaller create(RCode rcode, RCallerOptions rCallerOptions) {
        return new RCaller(rcode, ROutputParser.create(rCallerOptions), new RStreamHandler(null, "Output"), new RStreamHandler(null, "Error"), new MessageSaver(), new TempFileService(), rCallerOptions);
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
        this.rCode.getCode().append("q()\n");
        runRCode();
    }

    private Process exec(String command) throws IOException {
        String[] cmd = command.split(" ");
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Map<String, String> env = pb.environment();
        String locale = Globals.standardLocale.toString();
        if (locale.equals("en")) { //Java shows no-lang locales as "en", R does not understand
            String langEnv = System.getenv().get("LANG");
            if ("C.UTF-8".equals(langEnv) || "C".equals(langEnv) || "POSIX".equals(langEnv)) {
                locale = "C";
            }
        }
        String localeAndCharset = join(".", locale, Globals.standardCharset.toString());
    
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
            String sourceFileParameter = Globals.getSystemSpecificRPathParameter(rSourceFile);
            process = exec(rCallerOptions.getrScriptExecutable() + rCallerOptions.getStartUpOptionsAsCommand() + sourceFileParameter);
            startStreamConsumers(process);
            returnCode = process.waitFor();
        } catch (Exception e) {
            throw new ExecutionException("Can not run " + rCallerOptions.getrScriptExecutable() + ". Reason: " + e.toString());
        } finally {
            stopStreamConsumers();
        }
        if (returnCode != 0) {
            throw new ExecutionException("R command evaluating " + rSourceFile.getAbsolutePath() + " failed with error. Reason: " + errorMessageSaver.getMessage());
        }
    }

    /**
     * Runs the current code in the existing R instance (or in a new one) and
     * returns the R variable "var". The R process is kept alive and can be
     * re-used by invoking this method again. When you are done with this
     * process, you must explicitly stop it.
     *
     * If R raises an error, it is ignored. For throwing them in Java, use
     * {@link #runAndReturnResultOnline(String, boolean)} with addTryCatch=true
     *
     * @see #stopStreamConsumers()
     * @param var The R variable to return
     * @throws ExecutionException if R cannot be started
     */
    public void runAndReturnResultOnline(String var) throws ExecutionException {
        runAndReturnResultOnline(var, false);
    }

    /**
     * Runs the current code in the existing R instance (or in a new one) and
     * returns the R variable "var". The R process is kept alive and can be
     * re-used by invoking this method again. When you are done with this
     * process, you must explicitly stop it.
     *
     * @since 4.0.0
     * @see #stopStreamConsumers()
     * @param var The R variable to return
     * @param addTryCatch wrap original R code to tryCatch function (can impact performance)
     * @throws ExecutionException if R cannot be started or raise error inside while addTryCatch parameter is true
     */
    public void runAndReturnResultOnline(String var, boolean addTryCatch) throws ExecutionException {
        rCallerOptions.resetRetries();
        boolean done = false;
        do {
            if (rCallerOptions.getRetries() > 0) {
                logger.log(Level.INFO, "Retrying online R execution");
            }

            File outputFile, errorFile, resultReadyControlFile;

            if (rCallerOptions.getrExecutable() == null) {
                if (handleRFailure("RExecutable is not defined.Please set this" + " variable to full path of R executable binary file.")) {
                    continue;
                }
            }

            try {
                outputFile = tempFileService.createOutputFile();
                errorFile = tempFileService.createControlFile();
                resultReadyControlFile = tempFileService.createControlFile();
            } catch (Exception e) {
                if (handleRFailure("Can not create a temporary file for storing the R results: " + e.getMessage())) {
                    continue;
                } else {
                    throw new RuntimeException("Output file couldn't be created!");
                }
            }

            rCode.appendStandardCodeToAppend(outputFile, var);
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
                String script;
                if (addTryCatch) {
                    script = rCode.toTryCatchScript(errorFile);
                } else {
                    script = rCode.toString();
                }
                script += rCode.createEndSignalCode(resultReadyControlFile);
                rInput.write(script.getBytes(Globals.standardCharset));
                rInput.flush();
            } catch (IOException e) {
                rInput = null;
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
            if (!isProcessAlive()) {
                if (handleRFailure("R process died, stderr: " + errorMessageSaver.getMessage())) {
                    continue;
                }
            }

            if (errorFile.length() > 0) {
                parser.setIPCResource(errorFile.toURI());
                parser.parse();
                String errorMessage = parser.getAsStringArray("exception")[0];
                String stack = String.join("\n----\n",List.of(parser.getAsStringArray("stacktrace")));

                throw new ExecutionException("R code throw an error:\n" + errorMessage + "\nDetailed stack:\n----\n" + stack);
            }

            parser.setIPCResource(outputFile.toURI());

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
     * @param controlFile Signal file (separated from the main result), when it is not empty, calculation is finished
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
            getTmpDirCode.appendEndSignalCode(resultReadyControlFile);

            rInput.write(getTmpDirCode.toString().getBytes(Globals.standardCharset));
            rInput.flush();
            waitRExecute(resultReadyControlFile);

            parser.setIPCResource(getTmpDirFile.toURI());
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
    @Deprecated
    public void StopRCallerOnline() {
        stopRCallerOnline();
    }

    /**
     * Stops underlying R process gracefully.
     * No calculations should be running.
     */
    public void stopRCallerOnline() {
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
    @Deprecated
    public void StopRCallerAsync() {
        stopRCallerAsync();
    }

    /**
     * Stops underlying R process anyway.
     * May be used from separate thread to terminate running calculations.
     */
    public void stopRCallerAsync() {
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

        parser.setIPCResource(outputFile.toURI());
        try {
            parser.parse();
        } catch (Exception e) {
            Logger.getLogger(RCaller.class.getName()).log(Level.INFO, rCode.toString());
            throw new ParseException("Can not handle R results due to", e);
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
