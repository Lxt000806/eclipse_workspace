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
		Book.count++; //通过类名来访问静态属性
	}
	
	public Book() {	
		this("数据结构",43);
	}
	
	public Book(String name) {
		this(name,50);
	}
	
	public Book(int price) {
		this("数据结构",price);
	}
	
	void sell() {
		System.out.println("一本名称叫做"+name+"价值为"+price+"的书籍在售卖");
	}
	
	public static void showInfo() {
		System.out.println("您总共售卖"+Book.count+"本书籍");
	}
}
