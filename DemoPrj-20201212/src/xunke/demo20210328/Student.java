/**
 * 
 */
package xunke.demo20210328;

import java.io.Serializable;

/**
 * java����ת���ɶ����ƣ���Ҫ������ȫ��ʵ�����л�,ϵͳ�Դ������Զ�ʵ�����л�
 * Serializable���л�����һ��������0101�����ƽ������ݴ���Ĺ���
 *
 *
 */
public class Student implements Serializable{//java����ת���ɶ����ƣ���Ҫ������ȫ��ʵ�����л�

	private Integer stuNo;
	private String stuName;
	private transient Book book; //transient��ʱ�ģ�����Ҫ�־û��ģ�����Ҫ���̵�
	
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
