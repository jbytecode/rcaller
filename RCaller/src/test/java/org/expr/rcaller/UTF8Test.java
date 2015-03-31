package org.expr.rcaller;

import org.junit.Test;
import static org.junit.Assert.*;

public class UTF8Test {

    public UTF8Test() {
    }

    @Test
    public void Utf8CharactersTest() {
        String s = "ǴǵǶǷǸǹǺǻǼǽǾǿȀȁȂȃȄȅȆȇȈȉȊȋȌȍȎȏȐȑȒȓȔȕȖȗȘșȚțȜȝȞȟȠȡȢȣȤȥȦ";
        RCode code = new RCode();
        RCaller caller = new RCaller();
        Globals.detect_current_rscript();
        caller.setRscriptExecutable(Globals.Rscript_current);

        caller.setRCode(code);

        code.addRCode("rside <- list(s = intToUtf8(500:550))");
        caller.runAndReturnResult("rside");

        String[] result = caller.getParser().getAsStringArray("s");
        assertTrue(result[0].equals(s));
    }

}
