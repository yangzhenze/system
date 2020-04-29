package com.swsk.data.util.generate.db;

/**
 * @author zzy
 * @Date 2019-07-26 10:59
 */
public class BeanAttribute {

    private String name;
    private String type;
    private String column;
    private boolean isPk = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean getIsPk() {
        return isPk;
    }

    public void setIsPk(boolean isPk) {
        this.isPk = isPk;
    }
}
