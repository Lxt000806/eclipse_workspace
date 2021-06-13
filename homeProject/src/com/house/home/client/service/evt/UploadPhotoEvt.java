package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UploadPhotoEvt extends BaseEvt{
	
	@NotEmpty(message="图片不能为空")
	private String photoContent;
	@NotNull(message="类型不能为空")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPhotoContent() {
		return photoContent;
	}

	public void setPhotoContent(String photoContent) {
		this.photoContent = photoContent;
	}

}
