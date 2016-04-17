package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UTF8Test {

    public UTF8Test() {
    }

    @Test
    public void Utf8CharactersTest() {
        String s = "ǴǵǶǷǸǹǺǻǼǽǾǿȀȁȂȃȄȅȆȇȈȉȊȋȌȍȎȏȐȑȒȓȔȕȖȗȘșȚțȜȝȞȟȠȡȢȣȤȥȦ";
        RCode code = new RCode();
        RCaller caller = RCaller.create();

        caller.setRCode(code);

        code.addRCode("rside <- list(s = intToUtf8(500:550))");
        caller.runAndReturnResult("rside");

        String[] result = caller.getParser().getAsStringArray("s");
        assertTrue(result[0].equals(s));
    }
    
    @Test
    public void VariableNameContainsAndCharacterTest() throws IOException{
        RCode code = new RCode();
        RCaller caller = RCaller.create();

        caller.setRCode(code);
        
        TempFileService tmpservice = new TempFileService();
        File tmpfile = tmpservice.createTempFile("csv", "");
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpfile));
        writer.write("ID,Feature1,Feature2,Feature3\n");
        writer.write("a&b,1,0.01,0.65\n");
        writer.flush();
        writer.close();
        
        code.addRCode("predictions <- read.csv(\""+tmpfile.toString()+"\",stringsAsFactors = FALSE)");
        caller.runAndReturnResult("predictions");
        
        String[] result = caller.getParser().getAsStringArray("ID");
        assertEquals("a&b", result[0]);
    }

}
