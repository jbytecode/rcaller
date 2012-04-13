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
    int counter = 0;
    if (useEquals) {
      RCode.append(name).append("=").append("matrix(");
    } else {
      RCode.append(name).append("<-").append("matrix(");
    }
    RCode.append("c(");
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        RCode.append(String.valueOf(matrix[i][j]));
        counter++;
        if ( counter < (matrix.length * matrix[0].length) ) {
          RCode.append(", ");
        }
      }
    }
    RCode.append("), byrow=TRUE, nrow=").append(matrix.length).append(", ncol=").append(matrix[0].length).append(")");
    if (!useEquals) {
      RCode.append(";\n");
    }
  }
  
  
}
