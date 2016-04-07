
package com.bixi.model;

import java.util.List;

public class RenderThreadDetailInfo {
    private String threadName;
    private double fps;
    private int frameNums;
    private int LTFNums;
    private int stallNums;
    private List<Stall> stallList;
    private List<LTF> ltfList;

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public int getFrameNums() {
        return frameNums;
    }

    public void setFrameNums(int frameNums) {
        this.frameNums = frameNums;
    }

    public int getLTFNums() {
        return LTFNums;
    }

    public void setLTFNums(int lTFNums) {
        LTFNums = lTFNums;
    }

    public int getStallNums() {
        return stallNums;
    }

    public void setStallNums(int stallNums) {
        this.stallNums = stallNums;
    }

    public List<Stall> getStallList() {
        return stallList;
    }

    public void setStallList(List<Stall> stallList) {
        this.stallList = stallList;
    }

    public List<LTF> getLtfList() {
        return ltfList;
    }

    public void setLtfList(List<LTF> ltfList) {
        this.ltfList = ltfList;
    }
}
