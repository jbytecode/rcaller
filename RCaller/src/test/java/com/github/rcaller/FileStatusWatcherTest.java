package com.github.rcaller;

import com.github.rcaller.util.FileStatusWatcher;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileStatusWatcherTest {

    @Test
    public void testFileChanged() throws IOException, InterruptedException {
        TempFileService tmpFileService = new TempFileService();
        final File testFile = tmpFileService.createOutputFile();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                FileWriter writer = null;
                try {
                    writer = new FileWriter(testFile);
                } catch (IOException ex) {
                    assertNull(ex);
                }
                while (true) {
                    try {
                        writer.write(".");
                        writer.flush();
                    } catch (IOException ex) {
                        break;
                    }
                }

                try {
                    writer.close();
                } catch (IOException ex) {

                }
            }
        });
        th.start();
        FileStatusWatcher.waitUntilFileChanged(testFile);
        assertTrue(testFile.length() > 0);
        testFile.delete();
    }
}
