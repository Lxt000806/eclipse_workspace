/**
 * 
 */
package xunke.demo20210410_1;

import java.util.Scanner;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * 设计模式：
	 * 是前人经过多次尝试，总结出来一套行之有效的处理问题的方法；
	 * 提高代码效率，提高可复用性
	 * 能够看懂（大师）的代码
	 * 
	 * 具体设计模式：
	 * 1.单例模式：这个类在应用程序范围将有且只有一个实例
	 *          适用场合：
	 *                 创建一个对象成本高昂，需要消耗大量的CPU时间以及内存资源
	 *                 对象之间没有差异（无状态），没有必要创建多个对象
	 * 
	 * 一个类，没有实例属性，对象之间没有差异，我们把这样的类叫做无状态的类，
	 * 这样的类的对象可以被广泛复用；
	 * 
	 * 2.工厂模式：
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(System.in);
		String phoneType = null;
		
		while(true) {
			//创建工厂
			PhoneFactory factory = PhoneFactory.getInstance();
			
			System.out.print("请输入手机类型：");
			phoneType = scanner.next();
			try {
				Callable device = factory.makePhone(phoneType);
				device.call();
			}catch(UnSupportPhoneTypeException e) {
				System.out.println("本工厂不支持"+phoneType+"类型的手机生产");
			}
			
		}
		
	}

}
