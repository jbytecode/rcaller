/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2010-2015  Mehmet Hakan Satman

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code project: https://github.com/jbytecode/rcaller
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */
package com.github.rcaller.util;

import com.github.rcaller.JavaObject;
import org.apache.commons.lang.ArrayUtils;

public class CodeUtils {

    public static void addIntArray(StringBuffer RCode, String name, int[] arr, boolean useEquals) {
        addArray(RCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addLongArray(StringBuffer RCode, String name, long[] arr, boolean useEquals) {
        addArray(RCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addFloatArray(StringBuffer RCode, String name, float[] arr, boolean useEquals) {
        addArray(RCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addDoubleArray(StringBuffer RCode, String name, double[] arr, boolean useEquals) {
        addArray(RCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addStringArray(StringBuffer RCode, String name, String[] arr, boolean useEquals) {
        addArray(RCode, name, arr, useEquals, true);
    }

    public static void addShortArray(StringBuffer RCode, String name, short[] arr, boolean useEquals) {
        addArray(RCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addLogicalArray(StringBuffer RCode, String name, boolean[] arr, boolean useEquals) {
        String[] stringArray = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            stringArray[i] = String.valueOf(arr[i]).toUpperCase();
        }
        addArray(RCode, name, stringArray, useEquals, false);
    }

    public static <T> void addArray(StringBuffer RCode, String name, T[] array, boolean useEquals, boolean isString) {
        if (!name.equals("")) {
            if (useEquals) {
                RCode.append(name).append("=");
            } else {
                RCode.append(name).append("<-");
            }
        }
        RCode.append("c(");

        for (int i = 0; i < array.length; i++) {
            if (isString) {
                RCode.append("\"").append(array[i]).append("\"");
            } else {
                RCode.append(array[i]);
            }
            if (i < array.length - 1) {
                RCode.append(", ");
            }
        }
        if (useEquals) {
            RCode.append(")");
        } else {
            RCode.append(");").append("\n");
        }
    }

    public static void addJavaObject(StringBuffer RCode, Object o, boolean useEquals) throws IllegalAccessException {
        RCode.append(((JavaObject) o).produceRCode(useEquals));
        if (!useEquals) {
            RCode.append("\n");
        }
    }

    public static void addDoubleMatrix(StringBuffer RCode, String name, double[][] matrix, boolean useEquals) {
        int dim2 = matrix[0].length;
        int counter = 0;
        if (!name.equals("")) {
            if (useEquals) {
                RCode.append(name).append("=");
            } else {
                RCode.append(name).append("<-");
            }
        }
        RCode.append("matrix(").append("c(");
        for (double[] aMatrix : matrix) {
            for (int j = 0; j < dim2; j++) {
                RCode.append(String.valueOf(aMatrix[j]));
                counter++;
                if (counter < (matrix.length * matrix[0].length)) {
                    RCode.append(", ");
                }
            }
        }
        RCode.append("), byrow=TRUE, nrow=").append(matrix.length).append(", ncol=").append(matrix[0].length).append(")");
        if (!useEquals) {
            RCode.append(";\n");
        }
    }

    public static void addDouble(StringBuffer RCode, String name, double d, boolean useEquals) {
        addValue(RCode, name, d, useEquals);
    }

    public static void addInt(StringBuffer RCode, String name, int i, boolean useEquals) {
        addValue(RCode, name, i, useEquals);
    }

    public static void addLong(StringBuffer RCode, String name, long l, boolean useEquals) {
        addValue(RCode, name, l, useEquals);
    }

    public static void addFloat(StringBuffer RCode, String name, float f, boolean useEquals) {
        addValue(RCode, name, String.valueOf(f).toUpperCase(), useEquals);
    }

    public static void addShort(StringBuffer RCode, String name, short s, boolean useEquals) {
        addValue(RCode, name, s, useEquals);
    }

    public static void addBoolean(StringBuffer RCode, String name, boolean b, boolean useEquals) {
        addValue(RCode, name, String.valueOf(b).toUpperCase(), useEquals);
    }

    public static void addString(StringBuffer RCode, String name, String value, boolean useEquals) {
        if (!name.equals("")) {
            if (useEquals) {
                RCode.append(name).append("=");
                RCode.append("\"").append(value).append("\"");
            } else {
                RCode.append(name).append("<-");
                RCode.append("\"").append(value).append("\"").append("\n");
            }
        } else if (name.equals("")) {
            if (useEquals) {
                RCode.append("\"").append(value).append("\"");
            } else {
                RCode.append("\"").append(value).append("\"").append("\n");
            }
        }
    }

    private static void addValue(StringBuffer RCode, String name, Object value, boolean useEquals) {
        if (!name.equals("")) {
            if (useEquals) {
                RCode.append(name).append("=").append(value);
            } else {
                RCode.append(name).append("<-").append(value).append("\n");
            }
        } else if (name.equals("")) {
            if (useEquals) {
                RCode.append(value);
            } else {
                RCode.append(value).append("\n");
            }
        }
    }

    public static void addRespectToType(StringBuffer code, String name, Object o, boolean useEquals) {
        if (o instanceof double[]) {
            CodeUtils.addDoubleArray(code, name, (double[]) o, useEquals);
        } else if (o instanceof int[]) {
            CodeUtils.addIntArray(code, name, (int[]) o, useEquals);
        } else if (o instanceof float[]) {
            CodeUtils.addFloatArray(code, name, (float[]) o, useEquals);
        } else if (o instanceof boolean[]) {
            CodeUtils.addLogicalArray(code, name, (boolean[]) o, useEquals);
        } else if (o instanceof long[]) {
            CodeUtils.addLongArray(code, name, (long[]) o, useEquals);
        } else if (o instanceof String[]) {
            CodeUtils.addStringArray(code, name, (String[]) o, useEquals);
        } else if (o instanceof short[]) {
            CodeUtils.addShortArray(code, name, (short[]) o, useEquals);
        } else if (o instanceof Double) {
            CodeUtils.addDouble(code, name, (Double) o, useEquals);
        } else if (o instanceof Integer) {
            CodeUtils.addInt(code, name, (Integer) o, useEquals);
        } else if (o instanceof Long) {
            CodeUtils.addLong(code, name, (Long) o, useEquals);
        } else if (o instanceof Short) {
            CodeUtils.addShort(code, name, (Short) o, useEquals);
        } else if (o instanceof String) {
            CodeUtils.addString(code, name, (String) o, useEquals);
        } else if (o instanceof double[][]) {
            CodeUtils.addDoubleMatrix(code, name, (double[][]) o, useEquals);
        }

    }
}
