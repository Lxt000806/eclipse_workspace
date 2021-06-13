/**
 * 
 */
package xunke.demo20210307;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * 一个java文件中可以有多个类，但只有一个类是公有的且和文件名相同
	 * 一个类的访问修饰符，要么是public，要么是包级别的
	 * 抽象类不能实例化
	 * 子类引用变量不能指向父类对象（无中生有）
	 * 
	 */
	public static void main(String[] args) {
		
		Computer pc = new Computer();
		
		pc.setUSBDevice(new WebCam("罗技"));
		pc.setUSBDevice(new ProtableHD("希捷",2));

		pc.shutdown();
	}

}
