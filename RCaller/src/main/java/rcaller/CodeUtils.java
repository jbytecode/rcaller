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
package rcaller;

/*
 * I wrote this code dirty because the methods have similar content.
 * More automation needed.
 */
public class CodeUtils {

    public static void addIntArray(StringBuffer RCode, String name, int[] arr, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append("c(");
        } else {
            RCode.append(name).append("<-").append("c(");
        }
        for (int i = 0; i < arr.length; i++) {
            RCode.append(String.valueOf(arr[i]));
            if (i < arr.length - 1) {
                RCode.append(", ");
            }
        }
        if (useEquals) {
            RCode.append(")");
        } else {
            RCode.append(");").append("\n");
        }
    }

    public static void addFloatArray(StringBuffer RCode, String name, float[] arr, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append("c(");
        } else {
            RCode.append(name).append("<-").append("c(");
        }
        for (int i = 0; i < arr.length; i++) {
            RCode.append(String.valueOf(arr[i]));
            if (i < arr.length - 1) {
                RCode.append(", ");
            }
        }
        if (useEquals) {
            RCode.append(")");
        } else {
            RCode.append(");").append("\n");
        }
    }

    public static void addDoubleArray(StringBuffer RCode, String name, double[] arr, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append("c(");
        } else {
            RCode.append(name).append("<-").append("c(");
        }
        for (int i = 0; i < arr.length; i++) {
            RCode.append(String.valueOf(arr[i]));
            if (i < arr.length - 1) {
                RCode.append(", ");
            }
        }
        if (useEquals) {
            RCode.append(")");
        } else {
            RCode.append(");").append("\n");
        }
    }

    public static void addStringArray(StringBuffer RCode, String name, String[] arr, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append("c(");
        } else {
            RCode.append(name).append("<-").append("c(");
        }
        for (int i = 0; i < arr.length; i++) {
            RCode.append("\"").append(arr[i]).append("\"");
            if (i < arr.length - 1) {
                RCode.append(", ");
            }
        }
        if (useEquals) {
            RCode.append(")");
        } else {
            RCode.append(");").append("\n");
        }
    }

    public static void addShortArray(StringBuffer RCode, String name, short[] arr, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append("c(");
        } else {
            RCode.append(name).append("<-").append("c(");
        }
        for (int i = 0; i < arr.length; i++) {
            RCode.append(String.valueOf(arr[i]));
            if (i < arr.length - 1) {
                RCode.append(", ");
            }
        }
        if (useEquals) {
            RCode.append(")");
        } else {
            RCode.append(");").append("\n");
        }
    }

    public static void addLogicalArray(StringBuffer RCode, String name, boolean[] arr, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append("c(");
        } else {
            RCode.append(name).append("<-").append("c(");
        }
        for (int i = 0; i < arr.length; i++) {
            RCode.append(String.valueOf(arr[i]).toUpperCase());
            if (i < arr.length - 1) {
                RCode.append(", ");
            }
        }
        if (useEquals) {
            RCode.append(")");
        } else {
            RCode.append(");").append("\n");
        }
    }

    public static void addJavaObject(StringBuffer RCode, String name, Object o, boolean useEquals) throws IllegalAccessException {
        RCode.append(((rcaller.JavaObject) o).produceRCode(useEquals));
        if (!useEquals) {
            RCode.append("\n");
        }
    }

    public static void addDoubleMatrix(StringBuffer RCode, String name, double[][] matrix, boolean useEquals) {
        int dim1 = matrix.length, dim2 = matrix[0].length;
        int counter = 0;
        if (useEquals) {
            RCode.append(name).append("=").append("matrix(");
        } else {
            RCode.append(name).append("<-").append("matrix(");
        }
        RCode.append("c(");
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                RCode.append(String.valueOf(matrix[i][j]));
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
        if (useEquals) {
            RCode.append(name).append("=").append(d);
        } else {
            RCode.append(name).append("<-").append(d).append("\n");
        }
    }

    public static void addInt(StringBuffer RCode, String name, int i, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append(i);
        } else {
            RCode.append(name).append("<-").append(i).append("\n");
        }
    }
    
    public static void addLong(StringBuffer RCode, String name, long l, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append(l);
        } else {
            RCode.append(name).append("<-").append(l).append("\n");
        }
    }
    
    public static void addFloat(StringBuffer RCode, String name, float f, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append(f);
        } else {
            RCode.append(name).append("<-").append(f).append("\n");
        }
    }
    
    public static void addShort(StringBuffer RCode, String name, short s, boolean useEquals) {
        if (useEquals) {
            RCode.append(name).append("=").append(s);
        } else {
            RCode.append(name).append("<-").append(s).append("\n");
        }
    }
    
    public static void addBoolean(StringBuffer RCode, String name, boolean b, boolean useEquals) {
        String strBool = String.valueOf(b).toUpperCase();
        if (useEquals) {
            RCode.append(name).append("=").append(strBool);
        } else {
            RCode.append(name).append("<-").append(strBool).append("\n");
        }
    }
}
