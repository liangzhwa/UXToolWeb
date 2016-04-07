package com.bixi.model;

public class performTraversal {
	  private String pidname;
	  private String type;
	  private long startTime;
	  private long endTime;
	  private long startYTime;
	  private long endYTime;
	  private int pos = 0;
	  private int delta = 0;
	  private int drawYPos = 0;
	  private int endPos;
	  private long EglSwapTimeB = 0;
	  private long EglSwapTimeE = 0;
	  public String getpidName() {
	       return pidname;
	  }
	  public void setpidName(String pidname) {
	       this.pidname = pidname;
	  }
	  public long getStartTime() {
		  return startTime;
	  }
	  public void setStartTime(long l) {
	       this.startTime = l;
	  }
	  public long getEndTime() {
	       return endTime;
	  }
	  public void setEndTime(long l) {
		   this.endTime = l;
	  }
	  public long getStartYTime() {
		  return startYTime;
	  }
	  public void setStartYTime(long l) {
	       this.startYTime = l;
	  }
	  public long getEndYTime() {
	       return endYTime;
	  }
	  public void setEndYTime(long l) {
		   this.endYTime = l;
	  }
	  public int getDelta() {
		  return delta;
	  }
	  public void setDelta(int delta) {
	       this.delta = delta;
	  }
	  public int getDrawYPos() {
		  return drawYPos;
	  }
	  public void setDrawYPos(int l) {
	       this.drawYPos = l;
	  }
	  public void setEglSwapTimeB(long l){
			this.EglSwapTimeB = l;
	  }
	  public long getEglSwapTimeB(){
			return EglSwapTimeB;
	  }

	  public void setEglSwapTimeE(long l){
			this.EglSwapTimeE = l;
	  }
	  public long getEglSwapTimeE(){
			return EglSwapTimeE;
	  }

	    
}
