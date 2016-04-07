package com.bixi.wordbook;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.bixi.util.*;

public class GetPictureByFrame {
	public int frameIndex;
	public int getFrameIndex() {
		return frameIndex;
	}
	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}
	
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/html;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    int frame = UXUtil.getFrames().get(frameIndex);
	    String iconName = UXUtil.getFrameIconName(frame);
//	    String caseName = ServletActionContext.getRequest().getSession().getAttribute("CaseName").toString();
	    iconName = iconName.substring(iconName.indexOf("BIXIReports"));
	    String json = "{\"iconName\":\""+ iconName.replace('\\', '/') +"\"}";
	    out.print(json);
	    return null;
	}
}