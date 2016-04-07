
package com.bixi.model;

public class Stall {
    private double frameNum;
    private String startTime;
    private String endTime;
    private String duration;
    private double delta;
    private String threadName;
    private String frameStartTime;
    private String frameEndTime;
    private String frameDuration;

    public double getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(double frameNum) {
        this.frameNum = frameNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getFrameStartTime() {
        return frameStartTime;
    }

    public void setFrameStartTime(String frameStartTime) {
        this.frameStartTime = frameStartTime;
    }

    public String getFrameEndTime() {
        return frameEndTime;
    }

    public void setFrameEndTime(String frameEndTime) {
        this.frameEndTime = frameEndTime;
    }

    public String getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(String frameDuration) {
        this.frameDuration = frameDuration;
    }
}
