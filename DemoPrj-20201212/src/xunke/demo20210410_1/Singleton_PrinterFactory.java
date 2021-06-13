/**
 * 
 */
package xunke.demo20210410_1;

/**
 * 打印机工厂
 * @author HY
 *
 */
public class Singleton_PrinterFactory {
	
	private static final Singleton_PrinterFactory FACTORY = new Singleton_PrinterFactory();
	
	public static Singleton_PrinterFactory getInstance() {
		return FACTORY;
	}

	//将本类的构造方法设为私有的
	private Singleton_PrinterFactory() {
		System.out.print("构建打印机工厂.......");
		try {
			Thread.sleep(3000);//延迟3秒
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("....ok!");
	}
}
