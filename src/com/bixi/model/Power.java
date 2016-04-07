package com.bixi.model;

public class Power {
    private double time;
    private double avgPower;
    private double avgCurrent;
    private double avgVoltage;
    
    public double getTime() {
        return time;
    }
    
    public void setTime(double time) {
        this.time = time;
    }
    
    public double getAvgPower() {
        return avgPower;
    }
    public void setAvgPower(double mW) {
        this.avgPower = mW;
    }
    
    public double getAvgCurrent() {
        return avgCurrent;
    }
    public void setAvgCurrent(double mA) {
        this.avgCurrent = mA;
    }
    
    public double getAvgVoltage() {
        return avgVoltage;
    }
    
    public void setAvgVoltage(double v) {
        this.avgVoltage = v;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(time).append(", ").append(avgCurrent);
        sb.append(", ").append(avgPower).append(", ");
        sb.append(avgVoltage).append("]");
        return sb.toString();
    }
    
}
