package com.github.rcaller.rstuff;

import com.github.rcaller.util.Globals;

public class RCallerOptions {

    private static final long ININITAL_WAIT_TIME = 100;

    private String rScriptExecutable;
    private String rExecutable;
    private FailurePolicy failurePolicy;
    private long maxWaitTime;
    private long initialWaitTime;
    private boolean checkForXmlEndTag;
    private RProcessStartUpOptions rProcessStartUpOptions;
    private int retries;

    private RCallerOptions(String rScriptExecutable, String rExecutable, FailurePolicy failurePolicy, long maxWaitTime, long initialWaitTime, RProcessStartUpOptions rProcessStartUpOptions, boolean checkForXmlEndTag) {
        this.rScriptExecutable = rScriptExecutable;
        this.rExecutable = rExecutable;
        this.failurePolicy = failurePolicy;
        this.maxWaitTime = maxWaitTime;
        this.initialWaitTime = initialWaitTime;
        this.rProcessStartUpOptions = rProcessStartUpOptions;
        this.retries = 0;
        this.checkForXmlEndTag = checkForXmlEndTag;
    }

    /**
     *
     * @return a default RCallerOptions object
     */
    public static RCallerOptions create() {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, FailurePolicy.RETRY_5, Long.MAX_VALUE, ININITAL_WAIT_TIME, RProcessStartUpOptions.create(), false);
    }

    public static RCallerOptions create(RProcessStartUpOptions rProcessStartUpOptions) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, FailurePolicy.RETRY_5, Long.MAX_VALUE, ININITAL_WAIT_TIME, rProcessStartUpOptions, false);
    }

    public static RCallerOptions create(FailurePolicy failurePolicy, long maxWaitTime) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, failurePolicy, maxWaitTime, ININITAL_WAIT_TIME, RProcessStartUpOptions.create(), false);
    }

    public static RCallerOptions create(FailurePolicy failurePolicy, long maxWaitTime, RProcessStartUpOptions rProcessStartUpOptions) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, failurePolicy, maxWaitTime, ININITAL_WAIT_TIME, rProcessStartUpOptions, false);
    }

    public static RCallerOptions create(FailurePolicy failurePolicy, long maxWaitTime, long initialWaitTime, RProcessStartUpOptions rProcessStartUpOptions) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, failurePolicy, maxWaitTime, initialWaitTime, rProcessStartUpOptions, false);
    }

    public static RCallerOptions create(String rScriptExecutable, String rExecutable, FailurePolicy failurePolicy, long maxWaitTime, long initialWaitTime, RProcessStartUpOptions rProcessStartUpOptions) {
        return new RCallerOptions(rScriptExecutable, rExecutable, failurePolicy, maxWaitTime, initialWaitTime, rProcessStartUpOptions, false);
    }

    public static RCallerOptions create(String rScriptExecutable, String rExecutable, FailurePolicy failurePolicy, long maxWaitTime, long initialWaitTime, RProcessStartUpOptions rProcessStartUpOptions, boolean checkForXmlEndTag) {
        return new RCallerOptions(rScriptExecutable, rExecutable, failurePolicy, maxWaitTime, initialWaitTime, rProcessStartUpOptions, checkForXmlEndTag);
    }

    public String getrScriptExecutable() {
        return rScriptExecutable;
    }

    public String getrExecutable() {
        return rExecutable;
    }

    public FailurePolicy getFailurePolicy() {
        return failurePolicy;
    }

    /**
     * How long this R caller will wait for the R process to terminate before
     * forcibly killing it
     *
     * @return maxWaitTime - the max time to wait
     */
    public long getMaxWaitTime() {
        return maxWaitTime;
    }

    public long getInitialWaitTime() {
        return initialWaitTime;
    }

    public RProcessStartUpOptions getrProcessStartUpOptions() {
        return rProcessStartUpOptions;
    }

    public int getRetries() {
        return retries;
    }

    public String getStartUpOptionsAsCommand() {
        return this.rProcessStartUpOptions.getStartUpOptionsAsCommand();
    }

    public void incrementRetries() {
        this.retries++;
    }

    public void resetRetries() {
        this.retries = 0;
    }

    public boolean shouldCheckForXmlEndTag() { return checkForXmlEndTag; }
}
