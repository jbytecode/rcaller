package com.github.rcaller;

import com.github.rcaller.util.RCodeUtils;

import java.lang.reflect.Field;

public class JavaObject {

    Object object;
    String name;

    public JavaObject(String name, Object o) {
        this.object = o;
        this.name = name;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String ConvertToRCode(String name, Object javaobject, boolean useList, boolean useEquals) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        StringBuilder tempbuffer = new StringBuilder();
        String className, varName;
        Object o;
        Field f;

        Field[] fields = javaobject.getClass().getFields();
        if (useList) {
            if (useEquals) {
                builder.append(name).append(" = list(");
            } else {
                builder.append(name).append(" <- list(");
            }
        }
        for (int i = 0; i < fields.length; i++) {
            f = fields[i];
            className = f.getType().getCanonicalName();
            varName = f.getName();
            o = f.get(javaobject);
            switch (className) {
                case "int":
                case "float":
                case "double":
                case "long":
                case "short":
                    builder.append(varName).append("=").append(o);
                    break;
                case "java.lang.String":
                    builder.append(varName).append("=").append("\"").append(o).append("\"");
                    break;
                case "boolean":
                    builder.append(varName).append("=").append(o.toString().toUpperCase());
                    break;
                case "int[]":
                    tempbuffer.setLength(0);
                    RCodeUtils.addIntArray(tempbuffer, varName, (int[]) o, true);
                    builder.append(tempbuffer.toString());
                    break;
                case "double[]":
                    tempbuffer.setLength(0);
                    RCodeUtils.addDoubleArray(tempbuffer, varName, (double[]) o, true);
                    builder.append(tempbuffer.toString());
                    break;
                case "float[]":
                    tempbuffer.setLength(0);
                    RCodeUtils.addFloatArray(tempbuffer, varName, (float[]) o, true);
                    builder.append(tempbuffer.toString());
                    break;
                case "short[]":
                    tempbuffer.setLength(0);
                    RCodeUtils.addShortArray(tempbuffer, varName, (short[]) o, true);
                    builder.append(tempbuffer.toString());
                    break;
                case "long[]":
                    tempbuffer.setLength(0);
                    RCodeUtils.addLongArray(tempbuffer, varName, (long[]) o, true);
                    builder.append(tempbuffer.toString());
                    break;
                case "boolean[]":
                    tempbuffer.setLength(0);
                    RCodeUtils.addLogicalArray(tempbuffer, varName, (boolean[]) o, true);
                    builder.append(tempbuffer.toString());
                    break;
                case "java.lang.String[]":
                    tempbuffer.setLength(0);
                    RCodeUtils.addStringArray(tempbuffer, varName, (String[]) o, true);
                    builder.append(tempbuffer.toString());
                    break;
                case "java.lang.Object":
                    tempbuffer.setLength(0);
                    //CodeUtils.addJavaObject(tempbuffer, o, true);
                    tempbuffer.append(JavaObject.ConvertToRCode(name, o, useList, true));
                    builder.append(tempbuffer.toString());
                    break;
                default:
                    builder.append(varName).append("=").append("\"").append("Unsupported data type: ").append(className).append(" in JavaObject").append("\"");
                    break;
            }

            if (useList) {
                if (i < fields.length - 1) {
                    builder.append(", ");
                }
            } else {
                builder.append(";");
            }

        }

        if (useList) {
            builder.append(")\n");
        } else {
            builder.append("\n");
        }
        return (builder.toString());
    }

    public String produceRCode(boolean useEquals) throws IllegalAccessException {
        return (JavaObject.ConvertToRCode(name, object, true, useEquals));
    }

}
