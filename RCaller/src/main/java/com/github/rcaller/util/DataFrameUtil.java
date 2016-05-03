package com.github.rcaller.util;

public class DataFrameUtil {

    public static Object[][] createEmptyObjectsMatrix(int n, int m) {
        return new Object[n][m];
    }

    public static Object[][] createObjectsMatrix(int n, int m, Object o) {
        Object[][] objects = new Object[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                objects[i][j] = o;
            }
        }

        return objects;
    }

    public static String[] createDefaultNamesArray(int n) {
        String[] names = new String[n];

        for (int i = 0; i < n; i++) {
            names[i] = "var" + i;
        }

        return names;
    }
}
