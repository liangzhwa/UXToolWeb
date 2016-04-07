package com.bixi.wordbook;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONArray;
import com.bixi.util.*;
import com.bixi.model.*;

public class GetTimeStamp {
	private String traceLogName; 
	private String dirName; 
	private StringBuffer logFileDir;
	private String cameraLogName;
	private String logReportName;
	private String movName;
	private String powerReportName;
	private String tmplogName;
	private String logName;
	private String powerLogName;
	private boolean HASCAMERAVIDEO = true;
	private List<Integer> EventList;
	private List<Integer> DispatcherList;
	private List<Integer> wmAnimList;
	private List<Stall> generalStallList;
	private List<LTF> mLTFList;
	private Map<String, RenderThreadDetailInfo> ThreadStallMap;
	private Result ret = null;
	HashMap<String, List<discriptionItem>> mapAppDraw;
	Iterator<List<discriptionItem>> appDrawIterator;
	List<discriptionItem> appDrawList = null;
	List<Integer> frameList = null;
	
	public String execute() throws Exception {
		dirName = ServletActionContext.getRequest().getSession().getServletContext().getRealPath(ServletActionContext.getRequest().getSession().getAttribute("CaseName").toString()).toString().replace("UXToolWeb", "");
		traceLogName = dirName + UXConstants.TRACEFILE;
		cameraLogName = new StringBuffer(dirName).append(UXConstants.CAMERALOGFILE).toString();
		logReportName = new StringBuffer(dirName).append(UXConstants.LOGREPORT).toString();
		tmplogName =  new StringBuffer(dirName).append(UXConstants.TMPLOGFILE).toString();
		logName = new StringBuffer(dirName).append(UXConstants.LOGFILE).toString();
		powerLogName = new StringBuffer(dirName).append(UXConstants.POWERREPORT).toString();
		logFileDir = new StringBuffer(dirName);
		File file = new File(dirName);
		File[] files = file.listFiles();
		String name = null;
		for (File f : files) {
			name = f.getPath();
			if (name.endsWith(".MOV"))
				movName = name.substring(name.lastIndexOf("\\") + 1,name.lastIndexOf("."));
			if (name.contains("trace"))
				traceLogName = name;
			if (name.endsWith(".csv")) {
				powerReportName = name;
			}
		}
		UXUtil.setTraceDir(traceLogName);
		UXUtil.preAction(traceLogName);
		UXUtil.LogDealPrepare(tmplogName, logFileDir);
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/html;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = null;
	    out = response.getWriter();
	    
	    //event panel
	    UXUtil.PickupAllPIDName(traceLogName);
	    UXUtil.LogFilter(traceLogName, logFileDir);
	    UXUtil.DealWithPRCImage(dirName);
	    UXUtil.filterImagesByFrame(dirName, cameraLogName, logName);
	    UXUtil.getCompositionTime(logName, HASCAMERAVIDEO);
	    UXUtil.getResultFromLogReport(logReportName);
	    this.EventList = UXUtil.getReaderDelta();
		this.DispatcherList = UXUtil.getDispatcherDelta();
		this.frameList = UXUtil.getFrames();
		String json_Event = JSONArray.fromObject(this.EventList).toString();
	    String json_Dispatcher = JSONArray.fromObject(this.DispatcherList).toString();
	    
		//animal panel
		//UXUtil.LogDealPrepare(tmplogName, logFileDir);
		wmAnimList = UXUtil.getwinAnimDelta();
        generalStallList = UXUtil.getResult().getGeneralStallList();
        mLTFList = UXUtil.getResult().getAllLTFList();
		ThreadStallMap = UXUtil.getResult().getThreadInfoMap();
		mapAppDraw = UXUtil.getAppDrawMaps();		
		appDrawIterator = mapAppDraw.values().iterator();
		String json_Method = "[";
		StringBuffer sb_Method = new StringBuffer("[");
		while (appDrawIterator.hasNext()) {
			appDrawList = (List<discriptionItem>) appDrawIterator.next();
			sb_Method.append("{\"PidName\":\"").append(appDrawList.get(0).getpidName()).append("\",\"Methods\":[");	
			for (int j = 0; j < appDrawList.size(); j++) {
				sb_Method.append("{\"level\":").append(appDrawList.get(j).getLevel()).append(",\"start\":").append(appDrawList.get(j).getStartTime()).append(",\"end\":").append(appDrawList.get(j).getDelta()).append(",\"text\":\"").append(appDrawList.get(j).getsubTag()).append("\"}");
				if(j<(appDrawList.size()-1)){ sb_Method.append(","); }
			}
			sb_Method.append("]},");
		}
		json_Method = sb_Method.toString();
		json_Method = json_Method.endsWith(",") ? json_Method.substring(0, json_Method.length()-1) + "]" : json_Method + "]";
		
		//frame panel
		ArrayList<Integer> deltas = UXUtil.getDelta();
		ArrayList<Integer> acquireStartTime = UXUtil.getAcquireStartTime();
		ArrayList<Long> Vsyncdeltas = UXUtil.getVsyncDeltaList();
		
		int duration;
		String json_Deltas = "[";
		StringBuffer sb_Deltas = new StringBuffer("[");
		for(int i=0;i<deltas.size();i++){
			duration = 0;
			if((HASCAMERAVIDEO && frameList.size() > i && UXUtil.getFrameDuration().get(frameList.get(i))!=null) || (!HASCAMERAVIDEO && UXUtil.getFrameDuration().get(i) != null)){
				duration =  HASCAMERAVIDEO ? UXUtil.getFrameDuration().get(frameList.get(i)):UXUtil.getFrameDuration().get(i);
			}
			duration = duration < 1 ? 1:duration;
			sb_Deltas.append("{\"start\":").append(deltas.get(i)).append(",\"duration\":").append(duration).append("},");
		}
		json_Deltas = sb_Deltas.toString();
		json_Deltas = json_Deltas.endsWith(",") ? json_Deltas.substring(0, json_Deltas.length()-1) + "]" : json_Deltas + "]";
		
		String json_AcquireStartTime = "[";
		StringBuffer sb_AcquireStartTime = new StringBuffer("[");
		for(int i=0;i<acquireStartTime.size();i++){
			duration =  UXUtil.getAcquireDuration().get(acquireStartTime.get(i));
			duration = duration < 1 ? 1:duration;
			sb_AcquireStartTime.append("{\"start\":").append(acquireStartTime.get(i)).append(",\"duration\":").append(duration).append("},");
		}
		json_AcquireStartTime = sb_AcquireStartTime.toString();
		json_AcquireStartTime = json_AcquireStartTime.endsWith(",") ? json_AcquireStartTime.substring(0, json_AcquireStartTime.length()-1) + "]" : json_AcquireStartTime + "]";
		
		String json_Vsyncdeltas = JSONArray.fromObject(Vsyncdeltas).toString();
		
		//io panel
		List<Integer> list = UXUtil.getIothroughputDeltaList();
		HashMap<Integer, Integer> map1 = (HashMap<Integer, Integer>) UXUtil.getIothroughput1DeltaMap();
		HashMap<Integer, Integer> map2 = (HashMap<Integer, Integer>) UXUtil.getIothroughput2DeltaMap();
		HashMap<Integer, Integer> map3 = (HashMap<Integer, Integer>) UXUtil.getIothroughput3DeltaMap();
		HashMap<Integer, Integer> map4 = (HashMap<Integer, Integer>) UXUtil.getIothroughput4DeltaMap();
		String json_IO = "";
		StringBuffer sb_IO = new StringBuffer("{\"IO4\":[");
		for(int i=0;i<list.size()-1;i++) {
			int X1 = list.get(i);
			int Y1 = map1.get(list.get(i))/UXConstants.IOREAD_SCALE;
			int X2 = list.get(i+1);
			int Y2 = map1.get(list.get(i+1))/UXConstants.IOREAD_SCALE;
			if(map1.get(list.get(i+1))>0){
				sb_IO.append("{\"text\":\"").append(map1.get(list.get(i+1)).toString()).append("\",\"X1\":").append(X1).append(",\"Y1\":").append(Y1).append(",\"X2\":").append(X2).append(",\"Y2\":").append(Y2).append("},");
			}
		}
		if(sb_IO.charAt(sb_IO.length()-1) == ','){
			sb_IO.deleteCharAt(sb_IO.length()-1);
		}
		sb_IO.append("],\"IO3\":[");
		for(int i=0;i<list.size()-1;i++) {
			int X1 = list.get(i);
			int Y1 = map2.get(list.get(i))/UXConstants.IOREAD_SCALE;
			int X2 = list.get(i+1);
			int Y2 = map2.get(list.get(i+1))/UXConstants.IOREAD_SCALE;
			if(map2.get(list.get(i+1))>0){
				sb_IO.append("{\"text\":\"").append(map2.get(list.get(i+1)).toString()).append("\",\"X1\":").append(X1).append(",\"Y1\":").append(Y1).append(",\"X2\":").append(X2).append(",\"Y2\":").append(Y2).append("},");
			}
		}
		if(sb_IO.charAt(sb_IO.length()-1) == ','){
			sb_IO.deleteCharAt(sb_IO.length()-1);
		}
		sb_IO.append("],\"IO2\":[");
		for(int i=0;i<list.size()-1;i++) {
			int X1 = list.get(i);
			int Y1 = map3.get(list.get(i))/UXConstants.IOREAD_SCALE;
			int X2 = list.get(i+1);
			int Y2 = map3.get(list.get(i+1))/UXConstants.IOREAD_SCALE;
			if(map3.get(list.get(i+1))>0){
				sb_IO.append("{\"text\":\"").append(map3.get(list.get(i+1)).toString()).append("\",\"X1\":").append(X1).append(",\"Y1\":").append(Y1).append(",\"X2\":").append(X2).append(",\"Y2\":").append(Y2).append("},");
			}
		}
		if(sb_IO.charAt(sb_IO.length()-1) == ','){
			sb_IO.deleteCharAt(sb_IO.length()-1);
		}
		sb_IO.append("],\"IO1\":[");
		for(int i=0;i<list.size()-1;i++) {
			int X1 = list.get(i);
			int Y1 = map4.get(list.get(i))/UXConstants.IOREAD_SCALE;
			int X2 = list.get(i+1);
			int Y2 = map4.get(list.get(i+1))/UXConstants.IOREAD_SCALE;
			if(map4.get(list.get(i+1))>0){
				sb_IO.append("{\"text\":\"").append(map4.get(list.get(i+1)).toString()).append("\",\"X1\":").append(X1).append(",\"Y1\":").append(Y1).append(",\"X2\":").append(X2).append(",\"Y2\":").append(Y2).append("},");			
			}
		}
		if(sb_IO.charAt(sb_IO.length()-1) == ','){
			sb_IO.deleteCharAt(sb_IO.length()-1);
		}
		sb_IO.append("]}");
		json_IO = sb_IO.toString();
		
		// cpu&gpu panel
		String json_CPU = "";
		StringBuffer sb_CPU = new StringBuffer("{");
		String[] keyList = new String[]{"CPU0","CPU1","CPU2","CPU3","GPU"};
		UXUtil.getCpuIdleTime(traceLogName);
		 ArrayList<Integer> test = UXUtil.getcpuidle_start_ts(4);
		for(int k=0;k<5;k++){
		    ArrayList<Integer> cpu_start_ts = UXUtil.getcpuidle_start_ts(k);
			ArrayList<Integer> cpu_end_ts = UXUtil.getcpuidle_end_ts(k);
			ArrayList<Integer> cpu_states = UXUtil.getcpuidle_states(k);
			
			sb_CPU.append("\"").append(keyList[k]).append("\":[");
			for(int i=0;i<cpu_start_ts.size()-2 ;i++) {
				int  start_ts = cpu_start_ts.get(i);
	        	int  end_ts = cpu_end_ts.get(i);
	        	int state = cpu_states.get(i);
	        	sb_CPU.append("{\"start_ts\":").append(start_ts).append(",\"end_ts\":").append(end_ts).append(",\"state\":").append(state).append("},");
	        	if(i == cpu_start_ts.size()-2){
	        		sb_CPU.deleteCharAt(sb_CPU.length()-1);
	        	}
			}
			sb_CPU.append("],");
			if(k == 4){
				sb_CPU.deleteCharAt(sb_CPU.length()-1);
			}
		}
		sb_CPU.append("}");
		json_CPU = sb_CPU.toString();
		
		//power panel
		String json_Power = "";
		StringBuffer sb_Power = new StringBuffer("[");
		List<Power> powerList = UXUtil.getPowerLog(powerLogName);
		for(int i=0;i<powerList.size();i++){
			sb_Power.append(powerList.get(i)).append(",");
		}
		if(sb_Power.length() > 1){
			sb_Power.deleteCharAt(sb_Power.length()-1);
		}
		sb_Power.append("]");
		json_Power = sb_Power.toString();
		
		//result panel		
		ret = UXUtil.getResultFromLogReport(logReportName);
		
		StringBuffer sb_Result = new StringBuffer();
		if(ret !=null){
			StringBuffer sb_PhsesList = new StringBuffer("\"phaseList\":[");
			int frameStart = 0;
			int frameEnd = 0; 
			for(int i=0;i<ret.getPhaseList().size();i++){
				frameStart = Integer.parseInt(ret.getPhaseList().get(i)[0].substring(0,5));
				frameEnd = Integer.parseInt(ret.getPhaseList().get(i)[1].substring(0,5));
				sb_PhsesList.append("[");
				for(int j=0;j<frameList.size();j++){
					if(deltas.size()>frameList.get(j)){
						int tempFrame = deltas.get(frameList.get(j))/1000;
						if(tempFrame>frameStart && tempFrame<frameEnd){
							sb_PhsesList.append(tempFrame).append(",");
						}
					}else{
						continue;
					}				
				}
				if(sb_PhsesList.lastIndexOf(",") == sb_PhsesList.length()-1){
					sb_PhsesList.deleteCharAt(sb_PhsesList.length()-1);
				}
				sb_PhsesList.append("]");
			}
			sb_PhsesList.append("]");
			sb_Result.append("{\"ReactionTime:\":\"").append(ret.getReactionTime()).append("\",\"ResponseTime\":\" ").append(ret.getResponseTime()).append("\",\"Power\":\" ").append(ret.getPowerConsumption()).append("\",\"DiskIO\":\" ").append(ret.getDiskIO()).append("\",\"NetIO\":\" ").append(ret.getNnetIO()).append("\",\"AverageFPS\":\" ").append(ret.getTotalFPS()).append("\",\"PhaseNum\":\" ").append(ret.getGeneralStallNum()).append("\",").append(sb_PhsesList.toString()).append("}");
		}else{
			sb_Result.append("{}");
		}
		String json_Result = sb_Result.toString();
		//deleteTmpSource();
	    
	    //Json
		StringBuffer sbResult = new StringBuffer();	
		sbResult.append("{\"StartTime\":").append(UXUtil.getStartTime()*(UXConstants.MULTIPLE)).append(",\"ServerPath\":\"").append(dirName.replace('\\', '/')).append("\",\"Event\":").append(json_Event).append(",\"Dispatcher\":").append(json_Dispatcher)
				.append(",\"Method\":").append(json_Method).append(",\"Deltas\":").append(json_Deltas).append(",\"Acquire\":").append(json_AcquireStartTime).append(",\"CPU\":").append(json_CPU).append(",\"Power\":").append(json_Power).append(",\"Vsyncdeltas\":")
				.append(json_Vsyncdeltas).append(",\"IO\":").append(json_IO).append(",\"Result\":").append(json_Result).append("}");

		out.print(sbResult.toString());

	    try{
			out.flush();
			out.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return null;
	}
	
	protected void deleteTmpSource() {
		new Thread() {
			public void run() {
				StringBuffer outDir = new StringBuffer(dirName);
				outDir.append("\\output\\tmp");
				System.out.println(outDir.toString());
				UXUtil.deleteFileDir(outDir.toString());
			}
		}.start();
	}
}
