/**
 * 
 */
package xunke.demo20210224;

/**
 * JAVA的大量的基础类，比如String、Integer均无法被继承，他们都是final类
 * 一个类不能既是抽象类又是final类
 */
//final修饰一个类，这个类就是最终类，不能再扩展了，不能有子类
public final class Finaldemo {
	
	/**
	 * final static 一起协作来表示常量
	 * 在JAVA中，常量全部大写，单词和单词之间使用下划线来连接
	 */
	final static int X = 10; //final修饰属性，这个属性只能被赋值一次，不能再修改，成为常量
	
	final void method1() {
		//final修饰方法，这个方法就不能被重写
		//可以保证不被子类重写
	}
	
	void method2(final int x) {
		final int y = 30;
		//只能被赋值一次
		
	}
}

/**final修饰的几种部件：
 * 1.修饰类
 * 2.修饰属性
 * 3.修饰方法
 * 4.修饰形式参数
 * 5.修饰局部变量
 */

