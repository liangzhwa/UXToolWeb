package com.bixi.model;

public class discriptionItem {
  
	  private String threadName;
	  private String subTag;
	  private long startTime;
	  private int	duration;
	  private int	level;
	  public String getpidName() {
	       return threadName;
	  }
	  public void setpidName(String pidname) {
	       this.threadName = pidname;
	  }
	  public String getsubTag() {
	       return subTag;
	  }
	  public void setsubTag(String SubTag) {
	       this.subTag = SubTag;
	  }
	  public long getStartTime() {
		  return startTime;
	  }
	  public void setStartTime(long l) {
	       this.startTime = l;
	  }
	  public int getDelta() {
		  return duration;
	  }
	  public void setDelta(int delta) {
	       this.duration = delta;
	  }
	  public int getLevel() {
		  return level;
	  }
	  public void setLevel(int v) {
	       this.level = v;
	  }
}
