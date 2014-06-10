/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010-2014  Mehmet Hakan Satman

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
 * Google code project: http://code.google.com/p/rcaller/
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */

package rcaller;

import java.lang.reflect.Field;

public class JavaObject {

  Object object;
  String name;

  public JavaObject(String name, Object o) {
    this.object = o;
    this.name = name;
  }

  public String produceRCode(boolean useEquals) throws IllegalAccessException {
    /*
     * builder should be changed to StringBuilder soon.
     */
    StringBuilder builder = new StringBuilder();
    StringBuffer tempbuffer = new StringBuffer();
    String className, varName;
    Object o;
    Field f;

    Field[] fields = this.object.getClass().getFields();
    if (useEquals) {
      builder.append(this.name).append(" = list(");
    } else {
      builder.append(this.name).append(" <- list(");
    }

    for (int i = 0; i < fields.length; i++) {
      f = fields[i];
      className = f.getType().getCanonicalName();
      varName = f.getName();
      o = f.get(this.object);
      if (className.equals("int") || className.equals("float") || className.equals("double")
              || className.equals("long") || className.equals("short")) {
        builder.append(varName).append("=").append(o);
      } else if (className.equals("java.lang.String")) {
        builder.append(varName).append("=").append("\"").append(o).append("\"");
      } else if (className.equals("boolean")) {
        builder.append(varName).append("=").append(o.toString().toUpperCase());
      } else if (className.equals("int[]")) {
        tempbuffer.setLength(0);
        CodeUtils.addIntArray(tempbuffer, varName, (int[]) o, true);
        builder.append(tempbuffer.toString());
      } else if (className.equals("double[]")) {
        tempbuffer.setLength(0);
        CodeUtils.addDoubleArray(tempbuffer, varName, (double[]) o, true);
        builder.append(tempbuffer.toString());
      } else if (className.equals("float[]")) {
        tempbuffer.setLength(0);
        CodeUtils.addFloatArray(tempbuffer, varName, (float[]) o, true);
        builder.append(tempbuffer.toString());
      } else if (className.equals("short[]")) {
        tempbuffer.setLength(0);
        CodeUtils.addShortArray(tempbuffer, varName, (short[]) o, true);
        builder.append(tempbuffer.toString());
      } else if (className.equals("boolean[]")) {
        tempbuffer.setLength(0);
        CodeUtils.addLogicalArray(tempbuffer, varName, (boolean[]) o, true);
        builder.append(tempbuffer.toString());
      } else if (className.equals("java.lang.String[]")) {
        tempbuffer.setLength(0);
        CodeUtils.addStringArray(tempbuffer, varName, (java.lang.String[]) o, true);
        builder.append(tempbuffer.toString());
      } else if (className.equals("rcaller.JavaObject")) {
        tempbuffer.setLength(0);
        CodeUtils.addJavaObject(tempbuffer, varName, (rcaller.JavaObject) o, true);
        builder.append(tempbuffer.toString());
      } else {
        builder.append(varName).append("=").append("\"").append("Unsupported data type: ").append(className).append(" in JavaObject").append("\"");
      }

      if (i < fields.length - 1) {
        builder.append(", ");
      }

    }

    builder.append(")\n");


    return (builder.toString());
  }
}
