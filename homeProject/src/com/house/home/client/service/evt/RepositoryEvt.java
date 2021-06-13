package com.house.home.client.service.evt;

public class RepositoryEvt extends BaseQueryEvt {
	private Integer pk;
	private String czybh;
	private String searchText;
	private Integer docPk;
	private String workType12;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public Integer getDocPk() {
		return docPk;
	}
	public void setDocPk(Integer docPk) {
		this.docPk = docPk;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
}
