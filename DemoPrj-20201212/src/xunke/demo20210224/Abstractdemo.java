package xunke.demo20210224;

//被动抽象类（必须用抽象来修饰）
public abstract class Abstractdemo {

	void method1() {
		System.out.println("hello world");
	}
	
	void method2() {//空实现
		
	}
	
	//有一个抽象方法的类，就是抽象类
	abstract void method3();
	
	/**
	 * 抽象类因为存在抽象方法，导致无法实例化，其资产无法盘活
	 * 抽象类渴望被继承，父类希望子类能够完成自己尚未完成的方法，从而盘活父类资产
	 */
}
