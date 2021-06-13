/**
 * 
 */
package edu.mju.stuwork.domain;

/**
 * 
 * —ß…˙¿‡
 * 
 * @author joeyang ong
 *
 */
public class Student extends ValueObject {
	
	private int stuNo;
	private String stuName;
	private double stuMark;
	private byte[] stuPic;
	private String stuSex;
	private String stuOrigin;
	private String[] stuHobby;
	
	public int getStuNo() {
		return stuNo;
	}
	public void setStuNo(int stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public double getStuMark() {
		return stuMark;
	}
	public void setStuMark(double stuMark) {
		this.stuMark = stuMark;
	}
		
	public byte[] getStuPic() {
		return stuPic;
	}
	public void setStuPic(byte[] stuPic) {
		this.stuPic = stuPic;
	}
	public String getStuSex() {
		return stuSex;
	}
	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}
	public String getStuOrigin() {
		return stuOrigin;
	}
	public void setStuOrigin(String stuOrigin) {
		this.stuOrigin = stuOrigin;
	}
	
	public String[] getStuHobby() {
		return stuHobby;
	}
	public void setStuHobby(String[] stuHobby) {
		this.stuHobby = stuHobby;
	}
	@Override
	public String toString() {
		return "Student [stuNo=" + stuNo + ", stuName=" + stuName + ", stuMark=" + stuMark + "]";
	}
	
	

}
