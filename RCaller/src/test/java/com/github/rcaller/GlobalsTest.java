package com.github.rcaller;

import com.github.rcaller.util.Globals;
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

public class GlobalsTest {

    
    double delta = 1 / 100000;

    public static void message(String text) {
        System.out.println("* " + text);
    }

    @Test
    public void testRecursiveSearchOfRscript(){
        if(Globals.isWindows()){
            File myRScript = Globals.findFileRecursively(new File("c:\\Program Files\\R"), "Rscript.exe");
            assertEquals(myRScript.getName(), "Rscript.exe");
        }else if(Globals.isMac()) {
            File myRScript = Globals.findFileRecursively(new File("/usr/local/bin"), "Rscript");
            assertEquals(myRScript.getName(), "Rscript");
        }else{
            File myRScript = Globals.findFileRecursively(new File("/usr/bin"), "Rscript");
            assertEquals(myRScript.getName(), "Rscript");
        }
    }
}
