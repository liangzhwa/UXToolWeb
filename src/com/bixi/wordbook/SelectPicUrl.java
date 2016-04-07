package com.bixi.wordbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.bixi.util.UXConstants;
import com.bixi.util.Util;

public class SelectPicUrl {
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/html;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = null;
	    out = response.getWriter();	    
	    List<String> ImgList=new ArrayList<String>();
	    String basePath = ServletActionContext.getRequest().getRealPath(ServletActionContext.getRequest().getSession().getAttribute("CaseName").toString()).replace("UXToolWeb", "");
//	    ArrayList<File> files = filePattern(basePath+Util.Video_FILE_PATH, "*.avi");
//	    String toolPath = Util.getExtractToolPath() + Util.EXTRACTTOOL_FILE;
//		String videoRealPath = files.get(0).getPath();
//		String imageRealPath = basePath + "/output/tmpdir/dst/%05d.jpg";
//		File tempFile = new File(basePath + "/output/tmpdir/dst/00001.jpg");
//		if(!tempFile.exists()){
//			File file = new File(basePath + "/output/tmpdir/dst/");
//			file.mkdirs();
//			if(file.exists()){
//				ExtractAllImg(toolPath,videoRealPath,10,imageRealPath);
//			}			
//		}
	    
	    File file = new File(basePath);	    
	    String[] subFiles = file.list();
	    String SrcPath = "";
	    Arrays.sort(subFiles);
	    for(int i=subFiles.length-1;i>=0;i--){
	    	if(subFiles[i].startsWith("src_")){
	    		SrcPath = subFiles[i];
	    		break;
	    	}
	    }
	    
	    BufferedReader br = new BufferedReader(new FileReader(basePath + UXConstants.CAMERALOGFILE));
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			
			if(line.contains(".bmp")){
				ImgList.add(line.substring(0, line.indexOf(".bmp")) + ".jpg");
			}else{
				ImgList.add(line.substring(0, line.indexOf(" ")));
			}
		}
		br.close();
		out.print("{\"ImgList\":"+JSONArray.fromObject(ImgList).toString()+",\"SrcPath\":\""+SrcPath+"\"}");
	    try{
    		out.flush();
    		out.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return null;
	}
	
	private void ExtractAllImg(String toolPath,String videoRealPath,int fps,String imageRealPath){
		List<String> commend = new java.util.ArrayList<String>(); 
		commend.add(toolPath);
		commend.add("-i"); 
		commend.add(videoRealPath);
		commend.add("-r");
		commend.add(String.valueOf(fps));
		commend.add("-f");
		commend.add("image2");
		commend.add("-s");
		commend.add("200*300");
		commend.add(imageRealPath);
		try { 
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend); 
			builder.redirectErrorStream(true);
			System.out.println("start_2...");
			Process process = builder.start(); 
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024]; 
			System.out.print("process_2.");
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

	private ArrayList<File> filePattern(String path, String pat) {
		pat = pat.replace('.', '#');   
		pat = pat.replaceAll("#", "\\\\.");   
		pat = pat.replace('*', '#');   
		pat = pat.replaceAll("#", ".*");   
		pat = pat.replace('?', '#');   
		pat = pat.replaceAll("#", ".?");   
		pat = "^" + pat + "$";
		Pattern p = Pattern.compile(pat);
		File file = new File(path);
		if (file.isDirectory()) {   
			File[] files = file.listFiles();
            if (files != null && files.length > 0) {   
				ArrayList<File> list = new ArrayList<File>();   
				for (int i = 0; i < files.length; i++) {
					Matcher fMatcher = p.matcher(files[i].getName());
					if (fMatcher.matches()) {
						list.add(files[i]);
					}
				}   
				return list;
            }
        }   
        return null;   
	}  
}
