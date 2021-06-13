/**
 * 
 */
package xunke.demo20210328;

import java.io.Serializable;

/**
 * java对象转换成二进制，需要把属性全部实现序列化,系统自带的类自动实现序列化
 * Serializable序列化：把一个对象变成0101二进制进行数据传输的功能
 *
 *
 */
public class Student implements Serializable{//java对象转换成二进制，需要把属性全部实现序列化

	private Integer stuNo;
	private String stuName;
	private transient Book book; //transient临时的，不需要持久化的，不需要存盘的
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Integer getStuNo() {
		return stuNo;
	}
	public void setStuNo(Integer stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	@Override
	public String toString() {
		return "Student [stuNo=" + stuNo + ", stuName=" + stuName + ", book=" + book + "]";
	}
	
}
