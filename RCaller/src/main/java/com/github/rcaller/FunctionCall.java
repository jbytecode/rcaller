package com.github.rcaller;

import java.util.ArrayList;

public class FunctionCall {

    private ArrayList<FunctionParameter> parameters;
    private String fname;

    public FunctionCall() {
        this.fname = "";
        this.parameters = new ArrayList<>();
    }

    public FunctionCall(String functionName) {
        this.fname = functionName;
        this.parameters = new ArrayList<>();
    }

    public FunctionCall(String functionName, ArrayList<FunctionParameter> parameters) {
        this(functionName);
        this.parameters = parameters;
    }

    public void setFunctionName(String name) {
        this.fname = name;
    }

    public String getFunctionName() {
        return (this.fname);
    }

    public ArrayList<FunctionParameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<FunctionParameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(FunctionParameter fp) {
        this.parameters.add(fp);
    }

    public void addParameter(String name, String value, int type) {
        this.parameters.add(new FunctionParameter(name, value, type));
    }

    public String generateCode(String returnVar) {
        StringBuilder builder = new StringBuilder();
        builder.append(returnVar)
                .append(" <- ")
                .append(this.fname)
                .append("(");
        for (int i = 0; i < this.parameters.size(); i++) {
            builder.append(this.parameters.get(i));
            if (i < (this.parameters.size() - 1)) {
                builder.append(", ");
            }
        }
        builder.append(")").append("\n");
        return (builder.toString());
    }
}
