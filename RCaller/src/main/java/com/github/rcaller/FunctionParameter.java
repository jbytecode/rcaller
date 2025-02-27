package com.github.rcaller;

public class FunctionParameter {

    public final static int PARAM_LITERAL = 1;
    public final static int PARAM_OBJECT = 2;

    private String parameterName;
    private String value;
    private int type;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FunctionParameter(String parametername, String value, int type) {
        this.parameterName = parametername;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        if (this.type == PARAM_LITERAL) {
            return (this.parameterName + " = " + "\"" + this.value + "\"");
        } else if (this.type == PARAM_OBJECT) {
            return (this.parameterName + " = " + this.value);
        } else {
            throw new RuntimeException("FunctionParameter " + this.type + " is not defined");
        }
    }

}
