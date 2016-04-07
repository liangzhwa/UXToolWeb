package com.bixi.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.struts2.ServletActionContext;

public class Util {
	public static String Video_FILE_PATH = "/tmpdir";
	public static String EXTRACTTOOL_FILE = "ffmpeg.exe";
	public static String getConfig(String key) throws Exception {
		String configPath = (new StringBuilder(String.valueOf(ServletActionContext.getRequest().getRealPath("/Config")))).append("/commons.properties").toString();
		java.io.InputStream inputStream = new FileInputStream(configPath);
		Properties p = new Properties();
		p.load(inputStream);
		return p.getProperty(key);
	}
	
	public static String getOutputPath() throws Exception {
		return (new StringBuilder(String.valueOf(System.getenv("BIXI_ROOT")))).append("/BIXIBench/webapps/").toString();
	}
	public static String getExtractToolPath() throws Exception{
		return (new StringBuilder(String.valueOf(System.getenv("BIXI_ROOT")))).append("/BIXITool/OfflineCollect/ImageProcess/ffmpeg/bin/").toString();
	}
	
	public static void copyFile(File src, File dest) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(src));
			bos = new BufferedOutputStream(new FileOutputStream(dest));
			byte[] b = new byte[1024 * 4];
			int len;
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		(new File(targetDir)).mkdirs();
		File[] file = (new File(sourceDir)).listFiles();
		if(file == null){ return; }
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				File sourceFile = file[i];
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				String dir1 = sourceDir + "/" + file[i].getName();
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	public static String padding(String str,String charc,int len){
		StringBuffer sbf = new StringBuffer("");
		  
		for(int i=0;i<len-str.length();i++){
			sbf.append(charc);
		}
		return sbf.toString()+str;
	}
}
