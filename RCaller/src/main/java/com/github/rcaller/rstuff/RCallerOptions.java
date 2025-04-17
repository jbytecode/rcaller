package com.github.rcaller.rstuff;

import com.github.rcaller.util.Globals;

public class RCallerOptions {

    private static final long ININITAL_WAIT_TIME = 100;

    private String rScriptExecutable;
    private String rExecutable;
    private FailurePolicy failurePolicy;
    private long maxWaitTime;
    private long initialWaitTime;
    private RProcessStartUpOptions rProcessStartUpOptions;
    private int retries;
    private boolean useArrowIfAvailable;
    private boolean failIfArrowNotAvailable;

    private RCallerOptions(
            String rScriptExecutable,
            String rExecutable,
            FailurePolicy failurePolicy,
            long maxWaitTime,
            long initialWaitTime,
            RProcessStartUpOptions rProcessStartUpOptions,
            boolean useArrowIfAvailable,
            boolean failIfArrowNotAvailable
    ) {
        this.rScriptExecutable = rScriptExecutable;
        this.rExecutable = rExecutable;
        this.failurePolicy = failurePolicy;
        this.maxWaitTime = maxWaitTime;
        this.initialWaitTime = initialWaitTime;
        this.rProcessStartUpOptions = rProcessStartUpOptions;
        this.retries = 0;
        this.useArrowIfAvailable = useArrowIfAvailable;
        this.failIfArrowNotAvailable = failIfArrowNotAvailable;
    }

    public RCallerOptions(final RCallerOptions other) {
        this.rScriptExecutable = other.rScriptExecutable;
        this.rExecutable = other.rExecutable;
        this.failurePolicy = other.failurePolicy;
        this.maxWaitTime = other.maxWaitTime;
        this.initialWaitTime = other.initialWaitTime;
        this.rProcessStartUpOptions = new RProcessStartUpOptions(other.rProcessStartUpOptions);
        this.retries = 0;
        this.useArrowIfAvailable = other.useArrowIfAvailable;
        this.failIfArrowNotAvailable = other.failIfArrowNotAvailable;
    }

    /**
     *
     * @return a default RCallerOptions object
     */
    public static RCallerOptions create() {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, FailurePolicy.RETRY_5, Long.MAX_VALUE, ININITAL_WAIT_TIME, RProcessStartUpOptions.create(), Globals.useArrowIfAvailable, Globals.failIfArrowNotAvailable);
    }

    public static RCallerOptions create(RProcessStartUpOptions rProcessStartUpOptions) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, FailurePolicy.RETRY_5, Long.MAX_VALUE, ININITAL_WAIT_TIME, rProcessStartUpOptions, Globals.useArrowIfAvailable, Globals.failIfArrowNotAvailable);
    }

    public static RCallerOptions create(FailurePolicy failurePolicy, long maxWaitTime) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, failurePolicy, maxWaitTime, ININITAL_WAIT_TIME, RProcessStartUpOptions.create(), Globals.useArrowIfAvailable, Globals.failIfArrowNotAvailable);
    }

    public static RCallerOptions create(FailurePolicy failurePolicy, long maxWaitTime, RProcessStartUpOptions rProcessStartUpOptions) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, failurePolicy, maxWaitTime, ININITAL_WAIT_TIME, rProcessStartUpOptions, Globals.useArrowIfAvailable, Globals.failIfArrowNotAvailable);
    }

    public static RCallerOptions create(FailurePolicy failurePolicy, long maxWaitTime, long initialWaitTime, RProcessStartUpOptions rProcessStartUpOptions) {
        Globals.detect_current_rscript();
        return new RCallerOptions(Globals.Rscript_current, Globals.R_current, failurePolicy, maxWaitTime, initialWaitTime, rProcessStartUpOptions, Globals.useArrowIfAvailable, Globals.failIfArrowNotAvailable);
    }

    public static RCallerOptions create(String rScriptExecutable, String rExecutable, FailurePolicy failurePolicy, long maxWaitTime, long initialWaitTime, RProcessStartUpOptions rProcessStartUpOptions) {
        return new RCallerOptions(rScriptExecutable, rExecutable, failurePolicy, maxWaitTime, initialWaitTime, rProcessStartUpOptions, Globals.useArrowIfAvailable, Globals.failIfArrowNotAvailable);
    }

    public static RCallerOptions create(
            String rScriptExecutable,
            String rExecutable,
            FailurePolicy failurePolicy,
            long maxWaitTime,
            long initialWaitTime,
            RProcessStartUpOptions rProcessStartUpOptions,
            boolean useArrowIfAvailable,
            boolean failIfArrowNotAvailable
    ) {
        return new RCallerOptions(rScriptExecutable, rExecutable, failurePolicy, maxWaitTime, initialWaitTime, rProcessStartUpOptions, useArrowIfAvailable, failIfArrowNotAvailable);
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

    /**
     * Whether we should try to use Apache Arrow for transferring output data
     */
    public boolean useArrowIfAvailable() {
        return useArrowIfAvailable;
    }

    /**
     * Whether we should not use XML as backward behavior if {@link #useArrowIfAvailable} is true but Arrow is not available
     */
    public boolean failIfArrowNotAvailable() {
        return failIfArrowNotAvailable;
    }

    public void setrScriptExecutable(String rScriptExecutable) {
        this.rScriptExecutable = rScriptExecutable;
    }

    public void setrExecutable(String rExecutable) {
        this.rExecutable = rExecutable;
    }

    public void setFailurePolicy(FailurePolicy failurePolicy) {
        this.failurePolicy = failurePolicy;
    }

    public void setMaxWaitTime(long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public void setInitialWaitTime(long initialWaitTime) {
        this.initialWaitTime = initialWaitTime;
    }

    public void setrProcessStartUpOptions(RProcessStartUpOptions rProcessStartUpOptions) {
        this.rProcessStartUpOptions = rProcessStartUpOptions;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public void setUseArrowIfAvailable(boolean useArrowIfAvailable) {
        this.useArrowIfAvailable = useArrowIfAvailable;
    }

    public void setFailIfArrowNotAvailable(boolean failIfArrowNotAvailable) {
        this.failIfArrowNotAvailable = failIfArrowNotAvailable;
    }
}
