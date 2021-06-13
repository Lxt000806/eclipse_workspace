/**
 * 
 */
package xunke.demo20210224;

/**
 * @author HY
 *
 */
public class B extends Abstractdemo {

	/* (non-Javadoc)
	 * @see xunke.demo20210224.Abstractdemo#method3()
	 */
	@Override
	void method3() {//重写方法3，远程修补；称为实现implement
		System.out.println("b method3 is here");

	}

	/**
	 * 子类继承抽象类，即使子类书写了大量的自有方法，但还是不能忽视体内存在未完成的方法
	 * 子类还是残缺的，这个残缺是父类带来的，所以子类还是抽象类
	 * 子类重写父类方法，访问修饰符只能更开放，不能更严格
	 */
}
