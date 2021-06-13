package edu.mju.stuwork.domain;
/**
 * 学生实体类
 * @author HY
 *
 */
public class Student {
	private int stuNo;
	private String stuName;
	private double stuMark;

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

	@Override
	public String toString() {
		return "Student [stuNo=" + stuNo + ", stuName=" + stuName + ", stuMark=" + stuMark + "]";
	}

}
