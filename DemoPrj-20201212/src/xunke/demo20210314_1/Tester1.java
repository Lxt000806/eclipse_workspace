/**
 * 
 */
package xunke.demo20210314_1;

import java.io.IOException;

/**
 *   
 *1.JAVA系统做的事情：
 *   根据异常情况，选择了一个异常类，创建它的实例；
 *   根据异常情况，填充对应的数据
 *   抛出该对象给JVM
 *   （系统抛出异常语句之后的语句将不再运行）
 *   
 *2.JVM收到异常对象，就是得到异常报告后，其做了2件事：
 *   停止程序运行；打印异常出错详细信息到控制台
 *
 */
public class Tester1 {

	/**
	 *  1.一个方法内的异常，要么抛出，要么捕获
	 *  2.系统默认抛出运行时异常（RuntimeException）和error
	 * 
	 */
	public static void main(String[] args) throws IOException {
		
		int a = 10;
		int b = 2;
		int c =divide(a,b);
		Integer.parseInt("abc");
		
		System.out.println(c);

	}
	
	private static int divide(int a,int b) throws IOException{
		
		int c = 1;
		//手动异常（自己选型，new，填充数据，自己抛）
		if(a<10)
			throw new IOException("参数a不能小于10");
		
		//自动异常（系统异常类进行选型，系统new，系统填充数据，系统帮我们抛出）
		try {
			c = a/b;
		}catch(Exception e) {
			System.out.println("异常已经被处理！");
			c = 0;
		}
		
		return c;
	}

}

