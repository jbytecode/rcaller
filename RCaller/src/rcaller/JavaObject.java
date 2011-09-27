package rcaller;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JavaObject {

  Object object;
  String name;
  
  public JavaObject(String name, Object o) {
    this.object = o;
    this.name = name;
  }

  public String produceRCode() throws IllegalAccessException {

    StringBuilder builder = new StringBuilder();
    String className, varName;
    Object o;
    Field f;

    Field[] fields = this.object.getClass().getFields();
    builder.append(this.name).append(" <- list(");

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
