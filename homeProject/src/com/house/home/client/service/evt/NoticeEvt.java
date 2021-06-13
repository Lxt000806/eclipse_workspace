package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

import com.house.home.client.service.evt.BaseEvt;

public class NoticeEvt extends BaseEvt{
	@NotEmpty(message="手机别名不能为空")
	private String id;
	@NotEmpty(message="标题不能为空")
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
