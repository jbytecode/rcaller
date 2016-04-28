package com.github.rcaller.util;

public class DataFrameUtil {

    public static Object[][] createEmptyObjectsMatrix(int n, int m) {
        return new Object[n][m];
    }

    public static String[] createDefaultNamesArray(int n) {
        String[] names = new String[n];

        for (int i = 0; i < n; i++) {
            names[i] = "var" + i;
        }

        return names;
    }
}
