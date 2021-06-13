/**
 * 
 */
package xunke.demo20210224;

/**
 * @author HY
 *
 */
//主动抽象类（主动添加抽象修饰符，避免类被实例化）
public abstract class Animal {

	void run () {
		System.out.println("Animal is running");
	}
	
	void sound() {
		System.out.println("Animal is sounding");
	}
	
	/**
	 * 任何一个JAVA类，无论是否有抽象方法，都可以用abstract来修饰
	 */
}
