package xunke.demo20210314;

public class Annoymous {

	void work() {
		
		//局部变量级别内部类，该类有名字，就可以创建多个实例
		class M{
			public void showInfo() {
				System.out.println("m is working now!");
			}
		}
		
		//可创建多个实例
		M a = new M();
		M a1 = new M();
		
		
		//构建了一个一次性使用的类，该类是Object的子类，该类没有名字，就只能用一次
		new Object() {
			public void run() {
				System.out.println("匿名类在执行");
			}
		}.run(); //直接调用内部的run()方法
		
		new B() {//new class ??? extends B()
			public void show() {
				System.out.println("show方法的重写！");
			}
		}.show();
		
		
		
	}
}

class B{
	public void show() {
		System.out.println("class b is showing");
	}
}