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
package org.expr.rcaller;

import java.util.ArrayList;

public class FunctionCall {

    private ArrayList<FunctionParameter> parameters;
    private String fname;

    public FunctionCall() {
        this.fname = "";
        this.parameters = new ArrayList<FunctionParameter>();
    }

    public FunctionCall(String functionName) {
        this.fname = "";
        this.parameters = new ArrayList<FunctionParameter>();
    }

    public FunctionCall(String functionName, ArrayList<FunctionParameter> parameters) {
        this.fname = functionName;
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
