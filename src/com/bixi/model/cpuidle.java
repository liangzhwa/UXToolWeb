package com.bixi.model;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.bixi.util.UXConstants;

public class cpuidle {
	private class idleRecord{
		public  ArrayList<Integer>  start_ts = new ArrayList<Integer>(); 
		public  ArrayList<Integer>  end_ts = new ArrayList<Integer>();
		public  ArrayList<Integer> states = new ArrayList<Integer>();
		
	}


	public  void setStartTime(Double time){
		startTime = time;
	}
	private  Double startTime = 0.0;
	private int numCPU;
	private ArrayList<idleRecord> cpuIdleRecs = null;
	
    public cpuidle() {
    	numCPU = 5;
    	cpuIdleRecs = new  ArrayList<idleRecord>();
    	for(int i=0; i< numCPU; i++){
        	cpuIdleRecs.add(new idleRecord());
    	}
    }
    
    public ArrayList<Integer> getcpuidle_start_ts(int index) {
    	return cpuIdleRecs.get(index).start_ts;
    
    }
    
    public ArrayList<Integer> getcpuidle_end_ts(int index) {
    	return cpuIdleRecs.get(index).end_ts;
    
    }
    
    public ArrayList<Integer> getcpuidle_states(int index) {
    	return cpuIdleRecs.get(index).states;
    
    }
    
    public void processidle(String line) {
    	Pattern p = Pattern.compile("[' ']+");
    	if (!(line.contains("cpu_idle:") || line.contains("power_start:")|| line.contains("power_end:") || line.contains("gpu_state:"))){
    		return;
    	}
    	String[] s = p.matcher(line.trim()).replaceAll(" ").split(" ");
    	// <idle>-0     [001] 83080.709918: cpu_idle: state=6 cpu_id=1
    	int cpuidleIndex=0;
    	for(int i = 0; i <s.length; i++){
    		if ((s[i].contains("cpu_idle:") || s[i].contains("power_start:")|| s[i].contains("power_end:") || s[i].contains("gpu_state:"))){
    			cpuidleIndex = i;
    			break;
    		}
    	}
    	Double timestamp = Double.valueOf(s[cpuidleIndex -1].split(":")[0]);
    	timestamp = timestamp - startTime;
    	String[] temp = s[cpuidleIndex + 1].split("=");
    	long state =  0;
    	int cpuid = 0;
    	if(line.contains("power_end:")){
    		cpuid =  Integer.parseInt(temp[1]);
    		stop_cpu_idle(timestamp, cpuid);
    	}else if(line.contains("power_start:")){
    		//     <idle>-0     [000]  2791.841212: power_start: type=1 state=0 cpu_id=0
    		temp = s[cpuidleIndex + 2].split("=");
    		state =  Long.parseLong(temp[1]);
    		temp = s[cpuidleIndex + 3].split("=");
    		cpuid = Integer.parseInt(temp[1]);
    		start_cpu_idle(timestamp, state, cpuid);    		
    	}else if(line.contains("gpu_state:")){
    		if(line.contains("state=0")){
    			stop_cpu_idle(timestamp, 4);	
    		}else{
    			start_cpu_idle(timestamp, 1, 4);    			
    		}
     	}else {	
	    	state =  Long.parseLong(temp[1]);
	    	temp = s[cpuidleIndex + 2].split("=");
	    	cpuid = Integer.parseInt(temp[1]);
	    	
	    	if (line.contains("state=4294967295")){
	    		stop_cpu_idle(timestamp, cpuid);
	    		
	    	}else {
	    		start_cpu_idle(timestamp, state, cpuid);
	    	}
     	}
    }
    
    private void start_cpu_idle(Double timestamp, long state, int cpuid) {
    	if(cpuIdleRecs.get(cpuid).start_ts.size() > cpuIdleRecs.get(cpuid).end_ts.size()){
    		cpuIdleRecs.get(cpuid).end_ts.add((int)(timestamp * UXConstants.MULTIPLE));

    	}
    	cpuIdleRecs.get(cpuid).start_ts.add((int)(timestamp * UXConstants.MULTIPLE));
    	cpuIdleRecs.get(cpuid).states.add((int)state);	
    }
    
    private void stop_cpu_idle(Double timestamp, int cpuid) {
    	if(cpuIdleRecs.get(cpuid).start_ts.size() > cpuIdleRecs.get(cpuid).end_ts.size()){
    		cpuIdleRecs.get(cpuid).end_ts.add((int)(timestamp * UXConstants.MULTIPLE));
    	}
    }

}
