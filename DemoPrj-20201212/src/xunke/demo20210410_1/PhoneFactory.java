/**
 * 
 */
package xunke.demo20210410_1;

/**
 * @author HY
 *
 */
public class PhoneFactory {

	//����ģʽ
	private static final PhoneFactory ME = new PhoneFactory();
	public static PhoneFactory getInstance() {
		return ME;
	}
	private PhoneFactory() {
		
		//����IPhone12������
		System.out.print("����IPhone12������.......");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("....ok!");
		
		//����Mate40������
		System.out.print("����Mate40������.......");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("....ok!");
		
	}
	
	//����ģʽ
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
