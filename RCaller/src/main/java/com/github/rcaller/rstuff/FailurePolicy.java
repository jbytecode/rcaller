package com.github.rcaller.rstuff;

/**
 *  Specifies the behaviour of the program in case of an exception
 */
public enum FailurePolicy {

    RETRY_1,//retry at most once
    RETRY_5,
    RETRY_10, //retry at most 10 times
    RETRY_FOREVER,//retry until success
    CONTINUE//ignore the error and continue
}
