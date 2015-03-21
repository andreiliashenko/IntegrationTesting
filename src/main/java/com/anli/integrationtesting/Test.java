package com.anli.integrationtesting;

public class Test {

    protected String name;
    protected String className;

    public Test(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("name : '").append(name).append("'");
        json.append(", ");
        json.append("className : '").append(className).append("'");
        json.append("}");
        return json.toString();
    }
}
