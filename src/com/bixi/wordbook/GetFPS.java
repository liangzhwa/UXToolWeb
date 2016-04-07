package com.bixi.wordbook;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.bixi.util.*;

public class GetFPS {
	public double startIndex;
	public double getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(double startIndex) {
		this.startIndex = startIndex;
	}
	
	public double endIndex;
	public double getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(double endIndex) {
		this.endIndex = endIndex;
	}
	
	private List<Integer> frameList;
	private boolean HASCAMERAVIDEO = true;
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/html;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    frameList = new ArrayList<Integer>();
	    frameList = UXUtil.getFrames();
	    int fps =0;
	    if(HASCAMERAVIDEO){
	    	for(int i=0; i<frameList.size(); i++){
	    		if((UXUtil.getFrameTimeStamps(frameList.get(i))*1000000>=startIndex) && (UXUtil.getFrameTimeStamps(frameList.get(i))*1000000<=endIndex)){
	    			fps++; 
	    		}
	    	}
	    }else{
	    	for(int i=0; i<UXUtil.getnoVideoframeList().size(); i++){
	    		if((UXUtil.getnoVideoframeList().get(i)*1000000>=startIndex) && (UXUtil.getnoVideoframeList().get(i)*1000000<=endIndex)){
	    			fps++;
	    		}
	    	}
	    }

	    int index=0;
        double[] states = new double[10];
        Double starttime = UXUtil.getStartTime();
        Double time = (endIndex-startIndex)/1000;
        String tempStr="";
    	String[] c_state_table={"C0","C1","C2","C3","C4","C5","C6","S0i3","S0i1","LpAudio","LPMP3"};
		while (index < 5){
		    ArrayList<Integer> cpu_start_ts = UXUtil.getcpuidle_start_ts(index);
	        ArrayList<Integer> cpu_end_ts = UXUtil.getcpuidle_end_ts(index);
	        ArrayList<Integer> cpu_states = UXUtil.getcpuidle_states(index);
			if (cpu_start_ts.size()==0) {
				index++;
				continue;
			}
			
			for(int i = 0; i < 10 ; i++) states[i] = 0.0;
			
			for(int i = 0; i < cpu_start_ts.size() -1; i++) {	
				double end = (double)cpu_end_ts.get(i)/UXConstants.MULTIPLE + starttime ;
				double start = (double)cpu_start_ts.get(i)/UXConstants.MULTIPLE  + starttime ;
				if((start*1000000 >=startIndex) && (end*1000000<=endIndex)){
			    	states[cpu_states.get(i)] = states[cpu_states.get(i)] +  end - start;
			    }
			}
			for(int i = 0; i < 10 ; i++) {
				states[i] = states[i]*1000/(time);
			}
			states[0]=0.0;
			for(int i = 0; i < 10 ; i++) {
				states[0] = states[0] +  states[i];
			}
			states[0] = 1.0 - states[0];
			tempStr=String.format("%sCPU%d workload: %.3f%s##BR##",tempStr,index,states[0]*100,"%");
			for(int i = 1; i < 10 ; i++) {
				if(states[i] > 0){
					tempStr=String.format("%s%s : %.3f%s##BR##",tempStr,c_state_table[i],states[i]*100,"%");
				}
			}
			index++;
		} 
	    out.print("{\"fps\":"+fps+",\"cpu\":\""+tempStr+"\"}");
	    return null;
	}
}
