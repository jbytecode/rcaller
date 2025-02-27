package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import static org.junit.Assert.*;
import org.junit.Test;

public class FactorDataTypeTest {

    @Test
    public void FactorDataTypeAsStrings() {
        RCaller caller = RCaller.create();
        caller.getRCode().addRCode("y <- factor(x=c(1,2,3))");
        caller.runAndReturnResult("y");
        String[] result = caller.getParser().getAsStringArray("y");
        assertArrayEquals(new String[]{"1", "2", "3"}, result);
    }
}
