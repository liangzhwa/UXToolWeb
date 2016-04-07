package com.bixi.model;

import com.bixi.util.*;

public class Animation {
    private String name;
    private String type;
    private Double startTime;
	private long startYTime;
	private long endYTime;
    private Double endTime;
    private long pos;
    private long delta;
    private long endPos;
    public Animation(){
        name = "";
        type = "";
        startTime = 0.0;
    	startYTime = 0;
    	endYTime = 0;
        endTime = 0.0;
        pos = 0;
        delta = 0;
        endPos = 0;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getStartTime() {
        return startTime;
    }
    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }
	public long getStartYTime() {
        return startYTime;
    }
    public void setStartYTime(long startTime) {
        this.startYTime = startTime;
    }
    public Double getEndTime() {
        return this.endTime;
    }
    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }
	public long getEndYTime() {
        return endYTime;
    }
    public void setEndYTime(long endTime) {
        this.endYTime = endTime;
    }
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public long getPos() {
        if (pos == 0) {
            pos = (long)((double)(UXConstants.MULTIPLE)*(startTime - UXUtil.getStartTime()));
        }
        return pos;
    }
    
    public void setPos(long pos) {
        this.pos = pos;
    }
    
    public long getEndPos() {
        if (endPos == 0) {
            endPos = (long)((double)(UXConstants.MULTIPLE)*(endTime - UXUtil.getStartTime()));
        }
        return endPos;
    }
    
    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }
    
    public long getDelta() {
        if (delta == 0) {
            delta = (long)((double)UXConstants.MULTIPLE*(endTime - startTime));
        }
        return delta;
    }
    
    public void setDelta(long delta) {
        this.delta = delta;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[AnimName: ").append(name).append(",");
        sb.append("AnimStartTime: ").append(startTime).append(",");
        sb.append("AnimEndTime: ").append(endTime).append("]");
        return sb.toString();
    }
    
}
