package com.github.rcaller;

import com.github.rcaller.exception.ExecutionException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TempFileService {

    private static final Logger logger = Logger.getLogger(TempFileService.class.getName());

    private final ArrayList<Pair<File, FileChannel>> tempFiles;
    
    public TempFileService(){
        tempFiles = new ArrayList<>();
    }
    
    public File createTempFile(String prefix, String suffix) throws IOException {
        File f = File.createTempFile(prefix, suffix);
        FileChannel fileChannel = FileChannel.open(
                f.toPath(),
                StandardOpenOption.READ,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE
        );
        tempFiles.add(new ImmutablePair<>(f, fileChannel));
        return(f);
    }
    
    public void deleteRCallerTempFiles(){
        for (Pair<File, FileChannel> tempFileAndChannel : tempFiles) {
            var fileChannel = tempFileAndChannel.getRight();
            var tempFile = tempFileAndChannel.getLeft();
            try {
                fileChannel.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Couldn't close file ".concat(tempFile.getName()), e);
            }
            if (!tempFile.delete()) {
                logger.log(Level.WARNING, "Couldn't delete file ".concat(tempFile.getName()));
            }
        }
        tempFiles.clear();
    }

    public File createOutputFile() {
        try {
            return createTempFile("ROutput", "");
        } catch (Exception e) {
            throw new ExecutionException("Can not create a temporary file for storing the R results: " + e.getMessage());
        }
    }

    public File createControlFile() {
        try {
            return createTempFile("RControl", "");
        } catch (Exception e) {
            throw new ExecutionException("Can not create a temporary file for storing the R results: " + e.getMessage());
        }
    }
}
