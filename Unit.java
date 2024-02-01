package edu.dccc.conversion.model;

public class Unit {
    private String type;
    private String unit;
    private double value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public String toString() { return "\nType::" + type + "Unit::" + unit + "::Value::"+value;
    }
}
