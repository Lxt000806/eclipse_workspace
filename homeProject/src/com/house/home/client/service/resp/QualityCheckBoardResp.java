package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;



public class QualityCheckBoardResp extends BaseResponse{
	private String waitApp;
	private String waitArrange;
	private String hadArranged;
	private String notOnTimeCome;
	private String overDate;
	private String todaySign;
	private String todayUnSign;
	private String waitCheck;
	private String address;
	private String prjManName;
	private Date appDate;
	private Date comeDate;
	private String workerName;
	private String phone;
	private Date planEnd;
	private Date lastSignDate;
	private Date crtDate;
	private Date completeDate;
	private Date prjPassDate;
	private Date date;
	private String remark;
	private String typeDescr;
	private String no;
	private List<Map<String, Object>> photos;
	private List<Map<String, Object>> allPhotos;
	private String totalNum;
	
	public String getWaitApp() {
		return waitApp;
	}
	public void setWaitApp(String waitApp) {
		this.waitApp = waitApp;
	}
	public String getWaitArrange() {
		return waitArrange;
	}
	public void setWaitArrange(String waitArrange) {
		this.waitArrange = waitArrange;
	}
	public String getHadArranged() {
		return hadArranged;
	}
	public void setHadArranged(String hadArranged) {
		this.hadArranged = hadArranged;
	}
	public String getNotOnTimeCome() {
		return notOnTimeCome;
	}
	public void setNotOnTimeCome(String notOnTimeCome) {
		this.notOnTimeCome = notOnTimeCome;
	}
	public String getOverDate() {
		return overDate;
	}
	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}
	public String getTodaySign() {
		return todaySign;
	}
	public void setTodaySign(String todaySign) {
		this.todaySign = todaySign;
	}
	public String getTodayUnSign() {
		return todayUnSign;
	}
	public void setTodayUnSign(String todayUnSign) {
		this.todayUnSign = todayUnSign;
	}
	public String getWaitCheck() {
		return waitCheck;
	}
	public void setWaitCheck(String waitCheck) {
		this.waitCheck = waitCheck;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPrjManName() {
		return prjManName;
	}
	public void setPrjManName(String prjManName) {
		this.prjManName = prjManName;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	public Date getLastSignDate() {
		return lastSignDate;
	}
	public void setLastSignDate(Date lastSignDate) {
		this.lastSignDate = lastSignDate;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public Date getPrjPassDate() {
		return prjPassDate;
	}
	public void setPrjPassDate(Date prjPassDate) {
		this.prjPassDate = prjPassDate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTypeDescr() {
		return typeDescr;
	}
	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public List<Map<String, Object>> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Map<String, Object>> photos) {
		this.photos = photos;
	}
	public List<Map<String, Object>> getAllPhotos() {
		return allPhotos;
	}
	public void setAllPhotos(List<Map<String, Object>> allPhotos) {
		this.allPhotos = allPhotos;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	
}
