
package com.bixi.model;

import java.util.ArrayList;

public class LogModel {
    private int frameNumber;
    private double timestamp;
    private ArrayList<Integer> logTimeDelta;
    private String imageName;
    private double imageTime;
    private String function;
    private int pid;
    private double startTime;
    private double endTime;

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frame) {
        this.frameNumber = frame;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Integer> getLogTimeDelta() {
        return logTimeDelta;
    }

    public void setLogTimeDelta(ArrayList<Integer> deltaList) {
        this.logTimeDelta = deltaList;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public double getImageTime() {
        return imageTime;
    }

    public void setImageTime(double imageTime) {
        this.imageTime = imageTime;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

}
