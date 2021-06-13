/**
 * 
 */
package xunke.demo20210410_1;

/**
 * @author HY
 *
 */
public class PhoneFactory {

	//单例模式
	private static final PhoneFactory ME = new PhoneFactory();
	public static PhoneFactory getInstance() {
		return ME;
	}
	private PhoneFactory() {
		
		//构建IPhone12生产线
		System.out.print("构建IPhone12生产线.......");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("....ok!");
		
		//构建Mate40生产线
		System.out.print("构建Mate40生产线.......");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("....ok!");
		
	}
	
	//工厂模式
	public Callable makePhone(String phoneType) {
		
		Callable device = null;
		
		if("iphone12".equals(phoneType)) {
			device = new IPhone12();
		}else if("mate40".equals(phoneType)) {
			device = new Mate40();
		}else
			throw new UnSupportPhoneTypeException();
		
		return device;
	}
}
