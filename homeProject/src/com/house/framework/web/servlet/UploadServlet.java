package com.house.framework.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.FileUploadServerMgr;

@WebServlet(name = "uploadServlet", urlPatterns = "/uploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
//			request.setCharacterEncoding("utf-8");
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/html;charset=utf-8");
			String str = request.getParameter("type");
			Integer type = null;
			if (StringUtils.isNotBlank(str)){
				type = Integer.parseInt(str);
			}else{
				type = 0;
			}
			String path = "";
			switch (type.intValue()) {
			case 0:
				path = SystemConfig.getProperty("prjProg", "", "photo");
				break;
			case 1:
				path = SystemConfig.getProperty("other", "", "photo");
				break;
			}
			// 获取上传的文件集合
			Part part1 = request.getPart("file1");
			Part part2 = request.getPart("file2");
			Part part3 = request.getPart("file3");
			String file1Descr = request.getParameter("file1Descr");
			String file2Descr = request.getParameter("file2Descr");
			String file3Descr = request.getParameter("file3Descr");
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			FileUploadServerMgr.writePart(part1,file1Descr,path,list);
			FileUploadServerMgr.writePart(part2,file2Descr,path,list);
			FileUploadServerMgr.writePart(part3,file3Descr,path,list);
//			Collection<Part> parts = request.getParts();
			JSONObject obj = new JSONObject();
			obj.put("success", true);
			obj.put("photoPathList", list);
			String json = obj.toString();
			String callback = request.getParameter("callback");
			if (StringUtils.isNotBlank(callback)) {
				json = callback + "(" + json + ")";
				response.setHeader("Content-type",
						"application/x-javascript;charset=utf-8");
			} else {
				response.setContentType("text/json;charset=utf-8");
			}
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			JSONObject obj = new JSONObject();
			obj.put("success", false);
			obj.put("photoPathList", null);
			String json = obj.toString();
			String callback = request.getParameter("callback");
			if (StringUtils.isNotBlank(callback)) {
				json = callback + "(" + json + ")";
				response.setHeader("Content-type",
						"application/x-javascript;charset=utf-8");
			} else {
				response.setContentType("text/json;charset=utf-8");
			}
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
