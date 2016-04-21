/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2010-2016  Mehmet Hakan Satman

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
package com.github.rcaller.scriptengine;

import java.util.ArrayList;

public class NamedArgument {

    private String name = null;
    private Object obj = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public NamedArgument(String name, Object o) {
        this.name = name;
        this.obj = o;
    }

    public static NamedArgument Named(String name, Object o) {
        return (new NamedArgument(name, o));
    }

    @Override
    public String toString() {
        return (name + ": " + obj.getClass().getCanonicalName());
    }

    public static Object find(ArrayList<NamedArgument> list, String name) {
        for (NamedArgument n : list) {
            if (n.getName().equals(name)) {
                return (n.getObj());
            }
        }
        return (null);
    }

}
