package xunke.demo20210307_1;

public class A {

	//实例属性的简单初始化
	int x = 20;
	static int y = 30;
	
	static {
		/**
		 * 静态块没有名字，在类加载的时候自动运行,完成静态变量的复杂初始化
		 * 静态块只会运行一次，因为类只会加载一次
		 * 静态块中无法访问实例属性，只能调用静态属性和方法
		 */
		y++;
		System.out.println("static block is invoked now!");
	}
	
	static {
		/**
		 * 一个类的静态块可以有多个，当类被加载时，依次运行所有
		 */
		System.out.println("static block is invoked now!");
	}
	
	public A(){
		/**
		 * 构造方法，每次创建对象的时候都要调用一次
		 */
		//实例属性的复杂初始化,在构造方法里书写
		x++;
		x--;
		x = x-2;
		System.out.println("construct A is invoked now！");
	}
	
}
