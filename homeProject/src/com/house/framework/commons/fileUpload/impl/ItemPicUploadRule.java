package com.house.framework.commons.fileUpload.impl;

import org.apache.commons.lang3.StringUtils;

public class ItemPicUploadRule extends AbstractFileUploadRule {
	
	/**
	 * 一级目录
	 */
	public static final String FIRST_LEVEL_PATH = "itemImage/";
	
	public ItemPicUploadRule(String originalName,String itemType1, String itemType2,
			String itemType3, String  itemCode, Integer picNum) {
		
		this.originalName = originalName;
		
		String detailPath = "";
		if (StringUtils.isNotBlank(itemType1)){
			detailPath = itemType1.trim()+'/'; 
		}
		if (StringUtils.isNotBlank(itemType2)){
			detailPath = detailPath + itemType2.trim() +'/';
		}
		if (StringUtils.isNotBlank(itemType3)){
			detailPath = detailPath + itemType3.trim() +'/';
		}
		
		this.filePath = FIRST_LEVEL_PATH + detailPath ;
		
		this.fileName = itemCode + "_" + Integer.toString(picNum) + "." + this.getExtension();
	}
	
	
	
}
