/**
 * 
 */
package edu.mju.stuwork.service;

/**
 * 
 * ²éÑ¯Ìõ¼þ´æ´¢Æ÷
 * 
 * @author joeyang ong
 *
 */
public class StudentQueryHelper {

	private String qryStuName;
	private Double qryBeginMark;
	private Double qryEndMark;
	private String qryStuSex;
	private String qryStuOrigin;

	public String getQryStuName() {
		return qryStuName;
	}

	public void setQryStuName(String qryStuName) {
		this.qryStuName = qryStuName;
	}

	public Double getQryBeginMark() {
		return qryBeginMark;
	}

	public void setQryBeginMark(Double qryBeginMark) {
		this.qryBeginMark = qryBeginMark;
	}

	public Double getQryEndMark() {
		return qryEndMark;
	}

	public void setQryEndMark(Double qryEndMark) {
		this.qryEndMark = qryEndMark;
	}	

	public String getQryStuSex() {
		return qryStuSex;
	}

	public void setQryStuSex(String qryStuSex) {
		this.qryStuSex = qryStuSex;
	}

	public String getQryStuOrigin() {
		return qryStuOrigin;
	}

	public void setQryStuOrigin(String qryStuOrigin) {
		this.qryStuOrigin = qryStuOrigin;
	}

	@Override
	public String toString() {
		return "StudentQueryHelper [qryStuName=" + qryStuName + ", qryBeginMark=" + qryBeginMark + ", qryEndMark="
				+ qryEndMark + "]";
	}
	
	

}
