package com.house.framework.commons.utils;

import java.text.Collator;
import java.util.Comparator;
import java.util.Map;

public class ListCompareUtil implements Comparator {
	   private String compareStr;
	   private String order;
	   private Collator clt; 
	   public ListCompareUtil(String compareStr){
		    this.compareStr=compareStr;
		    this.clt=Collator.getInstance();
		   
		 }
	   public ListCompareUtil(String compareStr,String order){
	    this.compareStr=compareStr;
	    this.order=order;
	    this.clt=Collator.getInstance();
	 }
	 
	 @Override
	 public int compare(Object o1, Object o2) {
	  int flag=0;
	  int flag2=0;
	  Map map1=(Map)o1;
	  Map map2=(Map)o2;
		  try {
			
			  flag=Integer.parseInt( map1.get(compareStr).toString())>Integer.parseInt( map2.get(compareStr).toString())?1:Integer.parseInt( map1.get(compareStr).toString())<Integer.parseInt( map2.get(compareStr).toString())?-1:0;
			 
		  } catch (Exception e) {
			try {
				 flag=Double.parseDouble( map1.get(compareStr).toString())>Double.parseDouble( map2.get(compareStr).toString())?1:Double.parseDouble( map1.get(compareStr).toString())<Double.parseDouble( map2.get(compareStr).toString())?-1:0;
			} catch (Exception e2) {
				  flag=clt.compare(String.valueOf(map1.get(compareStr)), String.valueOf(map2.get(compareStr))); 
				  //flag=String.valueOf(map1.get(compareStr)).compareTo(String.valueOf(map2.get(compareStr)));
			}
		}
		 	

	  
	 if("desc".equalsIgnoreCase(order)){
		 flag=-flag;
	 }
	  
	  //升序
	  if(flag>0){
	   flag2=1;
	  }else if(flag<0){
	   flag2=-1;
	  }
	  
	  return flag2;
	 }
}
