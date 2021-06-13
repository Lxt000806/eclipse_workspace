/**
 * 
 */
package com.abc.domain;

/**
 * @author HY
 *
 */
public class Student {

	private int stuNo;
	private String stuName;
	private int stuAge;
	private Double stuMark;

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

	public int getStuAge() {
		return stuAge;
	}

	public void setStuAge(int stuAge) {
		this.stuAge = stuAge;
	}

	public Double getStuMark() {
		return stuMark;
	}

	public void setStuMark(Double stuMark) {
		this.stuMark = stuMark;
	}

	@Override
	public String toString() {
		return "Student [stuNo=" + stuNo + ", stuName=" + stuName + ", stuAge=" + stuAge + ", stuMark=" + stuMark + "]";
	}

	
}
