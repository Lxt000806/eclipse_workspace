package com.house.home.bean.basic;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

public class PosiBean implements Serializable {
	public static final String POSI_AK="rSteCOout6FLc8OPkH5SrIiRwfPUGWCC";
	public static final String REQUEST_URL="http://api.map.baidu.com/place/v2/search?output=json";
	public static final String DEFAULT_CITY="福州";
	public static final int LIMIT_DISTANCE=1000;
	private String url;
	private String city;
	private String city_limit;
	private String address;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCity_limit() {
		return city_limit;
	}
	public void setCity_limit(String city_limit) {
		this.city_limit = city_limit;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public PosiBean(String city, String city_limit, String address) {
		super();
		this.url=REQUEST_URL+"&ak="+POSI_AK;
		this.city = city;
		this.city_limit = city_limit;
		this.address = address;
		 if(StringUtils.isNotBlank(city)){
	        	this.url+="&region="+city;
	        }else{
	        	this.url+="&region="+DEFAULT_CITY;
	        }
			if(StringUtils.isNotBlank(address)){
	        	this.url+="&query="+address;
	        }
	        if("1".equals(city_limit)){
	        	this.url+="&city_limit=true";
	        }
	}
	  public static Double getDistance(double startLat, double startLng, double endLat, double endLng){
	        double pk = 180 / Math.PI;
	        double lat1 = startLat / pk;
	        double lon1 = startLng / pk;
	        double lat2 = endLat / pk;
	        double lon2 = endLng / pk;
	        double R = 6371.004;
	        
	        //两点间距离 m，如果想要米的话，结果*1000就可以了
	        double dis =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
	       // NumberFormat nFormat = NumberFormat.getNumberInstance();  //数字格式化对象
	       // nFormat.setMaximumFractionDigits(2);//保留2位小数
	        dis *= 1000;
	        DecimalFormat df=new DecimalFormat("#.00");  
	        return  Double.parseDouble(df.format(dis));
//	        if(dis < 1){               //当小于1千米的时候用,用米做单位保留一位小数
//	         nFormat.setMaximumFractionDigits(1);    //已可以设置为0，这样跟百度地图APP中计算的一样 
//	         dis *= 1000;
//	         
//	         return nFormat.format(dis);
//	        }else{
//	         nFormat.setMaximumFractionDigits(2);
//	         return nFormat.format(dis);
//	        }
	    }
	  
	  public static void main(String[] args){
		  System.out.println(getDistance(26.0768d, 119.289351d, 26.086273d, 119.284309d));
	  }
}
