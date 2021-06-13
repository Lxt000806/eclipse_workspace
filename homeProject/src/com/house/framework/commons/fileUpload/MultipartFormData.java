package com.house.framework.commons.fileUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.house.framework.web.servlet.ContentCachingRequestWrapper;

public class MultipartFormData {

	private final Map<String, String[]> parameters;
    private final List<FileItem> fileItems;
	
    public MultipartFormData(HttpServletRequest request) throws Exception {
    	this.parameters = new HashMap<String, String[]>();
    	this.fileItems = new ArrayList<FileItem>();
    	
    	parseRequest(request);
    }
    
    @SuppressWarnings("unchecked")
	private void parseRequest(HttpServletRequest request) throws Exception {
    	
    	ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
    	parameters.putAll(requestWrapper.getParameterMap());
    	
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		
        List<FileItem> items = upload.parseRequest(requestWrapper);
        for (FileItem item : items) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                if (parameters.containsKey(fieldName)) {
                    String[] values = parameters.get(fieldName);
                    String[] newValues = new String[values.length + 1];
                    System.arraycopy(values, 0, newValues, 0, values.length);
                    newValues[values.length] = item.getString("UTF-8");

                    parameters.put(fieldName, newValues);
                } else {
                    parameters.put(fieldName, new String[]{item.getString("UTF-8")});
                }
            } else {
            	fileItems.add(item);
            }
        }
    }
    
    public String getParameter(String name) {
    	String[] parameterValues = parameters.get(name);

        if (parameterValues == null)
            return null;
        else if (parameterValues.length == 0)
        	return "";
        else
        	return parameterValues[0];
    }
    
    public String[] getParameterValues(String name) {
    	return parameters.get(name);
    }
    
    public Map<String, String[]> getParameterMap() {
    	return parameters;
    }
    
    public FileItem getFileItem() {
    	return fileItems.size() > 0 ? fileItems.get(0) : null;
    }
    
    public List<FileItem> getFileItems() {
    	return fileItems;
    }
	
}
