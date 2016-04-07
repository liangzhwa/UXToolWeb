package com.bixi.wordbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class TestUXTool {
	private int indexImg;
	private int imgWidth;

	public int getIndexImg() {
		return indexImg;
	}

	public void setIndexImg(int indexImg) {
		this.indexImg = indexImg;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public String execute() throws Exception {
		
		if (indexImg <= 0) {
			setIndexImg(10);
		}
		System.out.println("indexImg" + getIndexImg());
		if (imgWidth <= 1000) {
			setImgWidth(1000);
		}

		ActionContext actionContext = ActionContext.getContext();
		Map session = actionContext.getSession();

		ArrayList<String> ImgMap = new ArrayList<String>();
		String path = ServletActionContext.getRequest().getRealPath("/case06_fling_email/output/out_scale_rotate/result.txt");
		String imgPath = ServletActionContext.getRequest().getRealPath("/case06_fling_email/output/out_scale_rotate/");
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			ImgMap.add("/case06_fling_email/output/out_scale_rotate/" + line.substring(0, line.indexOf(".bmp") + 4));
			i++;
		}
		session.put("indexImg", indexImg);
		session.put("ImgList", ImgMap);
		session.put("imgWidth", imgWidth);
		return "success";
	}

}
