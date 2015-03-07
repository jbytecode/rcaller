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
 * Google code project: http://code.google.com/p/rcaller/
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */
package rcaller;

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
