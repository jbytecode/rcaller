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
