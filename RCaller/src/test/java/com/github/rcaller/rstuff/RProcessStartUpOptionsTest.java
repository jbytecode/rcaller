package com.github.rcaller.rstuff;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RProcessStartUpOptionsTest {

    @Test
    public void startUpOptionsTest_1() {
        RProcessStartUpOptions rProcessStartUpOptions = RProcessStartUpOptions.create();
        assertEquals(" --vanilla ", rProcessStartUpOptions.getStartUpOptionsAsCommand());
    }

    @Test
    public void startUpOptionsTest_2() {
        RProcessStartUpOptions rProcessStartUpOptions = RProcessStartUpOptions.create(true, false, false, false, true, true, false, false, false, false, false, true, true,
                true, false, false, null, null, 10, null, null, "test", null, null, null);
        assertEquals(" --save --no-init-file --restore --quiet --silent --slave --min-vsize=10 --gui=test ", rProcessStartUpOptions.getStartUpOptionsAsCommand());
    }
}
