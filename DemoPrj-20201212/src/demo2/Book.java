/**
 * 
 */
package demo2;

import xunke.demo20201219.Car;

/**
 * @author HY
 *
 */
public class Book {
	String name;
	int price;
	static int count = 0;

	public Book(String name,int price) {
		
		this.name = "["+name+"]";
		this.price = price;
		Book.count++; //ͨ�����������ʾ�̬����
	}
	
	public Book() {	
		this("���ݽṹ",43);
	}
	
	public Book(String name) {
		this(name,50);
	}
	
	public Book(int price) {
		this("���ݽṹ",price);
	}
	
	void sell() {
		System.out.println("һ�����ƽ���"+name+"��ֵΪ"+price+"���鼮������");
	}
	
	public static void showInfo() {
		System.out.println("���ܹ�����"+Book.count+"���鼮");
	}
}
