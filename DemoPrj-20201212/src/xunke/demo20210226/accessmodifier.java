/**
 * 
 */
package xunke.demo20210226;

/**
 * access modifier 访问修饰符：
 * public  公用
 * protected 本类、同包子类、异包子类可见
 * package  包级别，本类和同包类可见
 * private  只有本类可见
 * 
 * 封装（ encapsulation）
 *
 * 本类、同包子类、同包非子类、异包子类、异包非子类
 * 
 * 1.从继承角度谈论访问修饰符
 * 2.从引用角度谈论访问修饰符
 */
public class accessmodifier {

	/**
	 * @param args
	 */
	private int money; //本类可见
	String sleepingLocation; //包级别，本类和同包类可见
	protected int age; //本类、同包子类、异包子类可见
	public String name; //公用

}
