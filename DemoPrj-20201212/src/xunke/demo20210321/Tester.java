/**
 * 
 */
package xunke.demo20210321;

/**
 * @author HY
 *
 */
public class Tester {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String phoneCode = "15960099851";
		
		try {
			checkPhone(phoneCode);
			System.out.println("您的手机号码是有效中国移动手机号码，请查收20元美团优惠券二维码");
		}catch(InvalidCMPhoneCodeException e){
			System.out.println(e.getMessage());
		}finally {
			System.out.println("感谢您使用本系统！");
		}	

	}
	
	/**
	 * 手机号码检测
	 * @param phoneCode
	 */
	private static void checkPhone(String phoneCode) {
		
		//长度检测
		if(phoneCode.trim().length()!=11)
			throw new InvalidCMPhoneCodeException("手机毫毛长度不是11位，请检查！");
		
		//全数字检测
		try {
			Long.parseLong(phoneCode);
		}catch(NumberFormatException e) {
			throw new InvalidCMPhoneCodeException("手机号码中含有非数字字符，请检查！");
		}
		
		//运营商检测
		int header = Integer.parseInt(phoneCode.trim().substring(0,3));
		if(header<135 || header>139)
			throw new InvalidCMPhoneCodeException("手机号码不是中国移动手机号码，请检查！");
		
	}

}
