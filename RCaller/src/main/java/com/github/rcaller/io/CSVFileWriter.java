package com.github.rcaller.io;

import com.github.rcaller.datatypes.DataFrame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter extends FileWriter{

    private CSVFileWriter(final String filePath) throws IOException {
        super(filePath);
    }

    private CSVFileWriter(final File file) throws IOException {
        super(file);
    }

    public static CSVFileWriter create(final String filePath) throws IOException {
        return new CSVFileWriter(filePath);
    }

    public static CSVFileWriter create(final File file) throws IOException {
        return new CSVFileWriter(file);
    }

    public void writeDataFrameToFile(DataFrame dataFrame) throws IOException {
        appendArray(dataFrame.getNames());
        for (int i = 0; i < dataFrame.getNumberOfRows(); i++) {
            appendArray(dataFrame.getRow(i));
        }
    }

    private void appendArray(Object[] array) throws IOException {
        for (int i = 0; i < array.length; i++) {
            this.append(array[i].toString());

            if (i == array.length - 1) {
                this.append('\n');
            } else {
                this.append(',');
            }
        }
        this.flush();
    }

}
