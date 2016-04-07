
package com.bixi.model;

import java.util.List;
import java.util.Map;

public class Result {
    private String reactionTime;
    private String responseTime;
    private String powerConsumption;
    private String diskIO;
    private String nnetIO;
    private String totalFPS;
    private String generalStallNum;
    private String totalStallNum;
    private String totalLTFNum;
    private List<Stall> generalStallList;
    private List<LTF> allLTFList;
    private List<String[]> phaseList;
    private Map<String, RenderThreadDetailInfo> threadInfoMap;

    public String getReactionTime() {
        return reactionTime;
    }

    public void setReactionTime(String reactionTime) {
        this.reactionTime = reactionTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(String powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getDiskIO() {
        return diskIO;
    }

    public void setDiskIO(String diskIO) {
        this.diskIO = diskIO;
    }

    public String getNnetIO() {
        return nnetIO;
    }

    public void setNnetIO(String nnetIO) {
        this.nnetIO = nnetIO;
    }

    public String getTotalFPS() {
        return totalFPS;
    }

    public void setTotalFPS(String totalFPS) {
        this.totalFPS = totalFPS;
    }

    public String getGeneralStallNum() {
        return generalStallNum;
    }

    public void setGeneralStallNum(String generalStallNum) {
        this.generalStallNum = generalStallNum;
    }

    public String getTotalStallNum() {
        return totalStallNum;
    }

    public void setTotalStallNum(String totalStallNum) {
        this.totalStallNum = totalStallNum;
    }

    public String getTotalLTFNum() {
        return totalLTFNum;
    }

    public void setTotalLTFNum(String totalLTFNum) {
        this.totalLTFNum = totalLTFNum;
    }

    public List<Stall> getGeneralStallList() {
        return generalStallList;
    }

    public void setGeneralStallList(List<Stall> generalStallList) {
        this.generalStallList = generalStallList;
    }

    public List<LTF> getAllLTFList() {
        return allLTFList;
    }

    public void setAllLTFList(List<LTF> allLTFList) {
        this.allLTFList = allLTFList;
    }
    
    public List<String[]> getPhaseList() {
        return phaseList;
    }
    public void setPhaseList(List<String[]> phaseList) {
        this.phaseList = phaseList;
    }

    public Map<String, RenderThreadDetailInfo> getThreadInfoMap() {
        return threadInfoMap;
    }

    public void setThreadInfoMap(Map<String, RenderThreadDetailInfo> threadInfoMap) {
        this.threadInfoMap = threadInfoMap;
    }

}
