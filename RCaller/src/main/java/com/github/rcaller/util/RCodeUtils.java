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
import com.github.rcaller.datatypes.DataFrame;
import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.io.CSVFileWriter;
import com.github.rcaller.scriptengine.LanguageElement;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RCodeUtils {

    public static void addIntArray(StringBuilder rCode, String name, int[] arr, boolean useEquals) {
        addArray(rCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addLongArray(StringBuilder rCode, String name, long[] arr, boolean useEquals) {
        addArray(rCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addFloatArray(StringBuilder rCode, String name, float[] arr, boolean useEquals) {
        addArray(rCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addDoubleArray(StringBuilder rCode, String name, double[] arr, boolean useEquals) {
        addArray(rCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addStringArray(StringBuilder rCode, String name, String[] arr, boolean useEquals) {
        addArray(rCode, name, arr, useEquals, true);
    }

    public static void addShortArray(StringBuilder rCode, String name, short[] arr, boolean useEquals) {
        addArray(rCode, name, ArrayUtils.toObject(arr), useEquals, false);
    }

    public static void addLogicalArray(StringBuilder rCode, String name, boolean[] arr, boolean useEquals) {
        String[] stringArray = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            stringArray[i] = String.valueOf(arr[i]).toUpperCase();
        }
        addArray(rCode, name, stringArray, useEquals, false);
    }

    public static <T> void addArray(StringBuilder rCode, String name, T[] array, boolean useEquals, boolean isString) {
        if (!name.equals("")) {
            if (useEquals) {
                rCode.append(name).append("=");
            } else {
                rCode.append(name).append("<-");
            }
        }
        rCode.append("c(");

        for (int i = 0; i < array.length; i++) {
            if (isString) {
                rCode.append("\"").append(array[i]).append("\"");
            } else {
                rCode.append(array[i]);
            }
            if (i < array.length - 1) {
                rCode.append(", ");
            }
        }
        if (useEquals) {
            rCode.append(")");
        } else {
            rCode.append(");").append("\n");
        }
    }

    public static void addJavaObject(StringBuilder rCode, Object o, boolean useEquals) throws IllegalAccessException {
        rCode.append(((JavaObject) o).produceRCode(useEquals));
        if (!useEquals) {
            rCode.append("\n");
        }
    }

    public static void addDoubleMatrix(StringBuilder rCode, String name, double[][] matrix, boolean useEquals) {
        int dim2 = matrix[0].length;
        int counter = 0;
        if (!name.equals("")) {
            if (useEquals) {
                rCode.append(name).append("=");
            } else {
                rCode.append(name).append("<-");
            }
        }
        rCode.append("matrix(").append("c(");
        for (double[] aMatrix : matrix) {
            for (int j = 0; j < dim2; j++) {
                rCode.append(String.valueOf(aMatrix[j]));
                counter++;
                if (counter < (matrix.length * matrix[0].length)) {
                    rCode.append(", ");
                }
            }
        }
        rCode.append("), byrow=TRUE, nrow=").append(matrix.length).append(", ncol=").append(matrix[0].length).append(")");
        if (!useEquals) {
            rCode.append(";\n");
        }
    }

    public static void addDouble(StringBuilder rCode, String name, double d, boolean useEquals) {
        addValue(rCode, name, d, useEquals);
    }

    public static void addInt(StringBuilder rCode, String name, int i, boolean useEquals) {
        addValue(rCode, name, i, useEquals);
    }

    public static void addLong(StringBuilder rCode, String name, long l, boolean useEquals) {
        addValue(rCode, name, l, useEquals);
    }

    public static void addFloat(StringBuilder rCode, String name, float f, boolean useEquals) {
        addValue(rCode, name, String.valueOf(f).toUpperCase(), useEquals);
    }

    public static void addShort(StringBuilder rCode, String name, short s, boolean useEquals) {
        addValue(rCode, name, s, useEquals);
    }

    public static void addBoolean(StringBuilder rCode, String name, boolean b, boolean useEquals) {
        addValue(rCode, name, String.valueOf(b).toUpperCase(), useEquals);
    }

    public static void addString(StringBuilder rCode, String name, String value, boolean useEquals) {
        if (!name.equals("")) {
            if (useEquals) {
                rCode.append(name).append("=");
                rCode.append("\"").append(value).append("\"");
            } else {
                rCode.append(name).append("<-");
                rCode.append("\"").append(value).append("\"").append("\n");
            }
        } else if (name.equals("")) {
            if (useEquals) {
                rCode.append("\"").append(value).append("\"");
            } else {
                rCode.append("\"").append(value).append("\"").append("\n");
            }
        }
    }

    private static void addValue(StringBuilder rCode, String name, Object value, boolean useEquals) {
        if (!name.equals("")) {
            if (useEquals) {
                rCode.append(name).append("=").append(value);
            } else {
                rCode.append(name).append("<-").append(value).append("\n");
            }
        } else if (name.equals("")) {
            if (useEquals) {
                rCode.append(value);
            } else {
                rCode.append(value).append("\n");
            }
        }
    }

    public static void addRespectToType(StringBuilder rCode, String name, Object o, boolean useEquals) {
        if (o instanceof double[]) {
            RCodeUtils.addDoubleArray(rCode, name, (double[]) o, useEquals);
        } else if (o instanceof int[]) {
            RCodeUtils.addIntArray(rCode, name, (int[]) o, useEquals);
        } else if (o instanceof float[]) {
            RCodeUtils.addFloatArray(rCode, name, (float[]) o, useEquals);
        } else if (o instanceof boolean[]) {
            RCodeUtils.addLogicalArray(rCode, name, (boolean[]) o, useEquals);
        } else if (o instanceof long[]) {
            RCodeUtils.addLongArray(rCode, name, (long[]) o, useEquals);
        } else if (o instanceof String[]) {
            RCodeUtils.addStringArray(rCode, name, (String[]) o, useEquals);
        } else if (o instanceof short[]) {
            RCodeUtils.addShortArray(rCode, name, (short[]) o, useEquals);
        } else if (o instanceof Double) {
            RCodeUtils.addDouble(rCode, name, (Double) o, useEquals);
        } else if (o instanceof Integer) {
            RCodeUtils.addInt(rCode, name, (Integer) o, useEquals);
        } else if (o instanceof Long) {
            RCodeUtils.addLong(rCode, name, (Long) o, useEquals);
        } else if (o instanceof Short) {
            RCodeUtils.addShort(rCode, name, (Short) o, useEquals);
        } else if (o instanceof String) {
            RCodeUtils.addString(rCode, name, (String) o, useEquals);
        } else if (o instanceof double[][]) {
            RCodeUtils.addDoubleMatrix(rCode, name, (double[][]) o, useEquals);
        } else if (o instanceof LanguageElement) {
            RCodeUtils.addValue(rCode, name, ((LanguageElement) o).getObjectName(), useEquals);
        } else if (o instanceof DataFrame){
            RCodeUtils.addDataFrame(rCode, name, (DataFrame)o);
        }else if (o != null) {
            try {
                rCode.append(JavaObject.ConvertToRCode(name, o, /*useList=no*/false, useEquals));
            } catch (IllegalAccessException iae) {
                throw new ExecutionException("Cannot convert Java object " + o.toString() + " in type of " + o.getClass().getCanonicalName() + " to R code due to " + iae.toString());
            }
        }

    }

    public static void addDataFrame(StringBuilder rCode, String name, DataFrame dataFrame) {
        try {
            File file = File.createTempFile("dataFrame", ".csv");
            CSVFileWriter csvFileWriter = CSVFileWriter.create(file.getAbsolutePath());
            csvFileWriter.writeDataFrameToFile(dataFrame);
            rCode.append(name).append(" <- read.csv(\"").append(Globals.getSystemSpecificRPathParameter(file)).append("\")\n");

        } catch (IOException e) {
            Logger.getLogger(RCodeUtils.class.getName()).log(Level.WARNING, "Couldn't export data frame to csv-file!", e.getStackTrace());
        }

    }
}
