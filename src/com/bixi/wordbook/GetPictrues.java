package com.bixi.wordbook;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import com.bixi.util.Util;

public class GetPictrues {
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/html;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    HttpServletRequest request = ServletActionContext.getRequest();
		String casePath = request.getParameter("casePath");
		String picIndexs = request.getParameter("picIndexs");
		String[] indexs = picIndexs.split(",");
		
	    String toolPath = Util.getExtractToolPath() + Util.EXTRACTTOOL_FILE;
		String videoRealPath = Util.getOutputPath() + casePath + "/" + Util.Video_FILE_PATH;
		String imageRealPath1 = Util.getOutputPath() + casePath + "/output/dst/pic_1.jpg";
		String imageRealPath2 = Util.getOutputPath() + casePath + "/output/dst/pic_2.jpg";
		String imageRealPath3 = Util.getOutputPath() + casePath + "/output/dst/pic_3.jpg";
		String imageRealPath4 = Util.getOutputPath() + casePath + "/output/dst/pic_4.jpg";
		String imageRealPath5 = Util.getOutputPath() + casePath + "/output/dst/pic_5.jpg";
		String imageRealPath6 = Util.getOutputPath() + casePath + "/output/dst/pic_6.jpg";
		String imageRealPath7 = Util.getOutputPath() + casePath + "/output/dst/pic_7.jpg";
		String imageRealPath8 = Util.getOutputPath() + casePath + "/output/dst/pic_8.jpg";
		
	    System.out.println(indexs[0]);
	    System.out.println(toolPath);
	    System.out.println(videoRealPath);
	    System.out.println(imageRealPath1);
		ExtractImg(toolPath,videoRealPath,imageRealPath1,indexs[0]);
		ExtractImg(toolPath,videoRealPath,imageRealPath2,indexs[1]);
	    ExtractImg(toolPath,videoRealPath,imageRealPath3,indexs[2]);
	    ExtractImg(toolPath,videoRealPath,imageRealPath4,indexs[3]);
	    ExtractImg(toolPath,videoRealPath,imageRealPath5,indexs[4]);	    
	    ExtractImg(toolPath,videoRealPath,imageRealPath6,indexs[5]);
	    ExtractImg(toolPath,videoRealPath,imageRealPath7,indexs[6]);
	    ExtractImg(toolPath,videoRealPath,imageRealPath8,indexs[7]);
		
	    
		out.print("{\"status\":0}");
	    try{
    		out.flush();
    		out.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return null;
	}
	
	private void ExtractImg(String toolPath,String videoRealPath,String imageRealPath,String index){
		List<String> commend = new java.util.ArrayList<String>(); 
		commend.add(toolPath);
		commend.add("-i"); 
		commend.add(videoRealPath);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add(index);
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add("350*240");
		commend.add(imageRealPath);
		try { 
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend); 
			builder.redirectErrorStream(true);
			System.out.println("start...");
			Process process = builder.start(); 
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024]; 
			System.out.print("process.");
			while (in.read(re) != -1) {
				System.out.print(".");
			} 
			System.out.println(".");
			in.close(); 
			System.out.println("finished.");
		} catch (Exception e) {
			e.printStackTrace(); 
			System.out.println("fail!");
		}
	}
}
