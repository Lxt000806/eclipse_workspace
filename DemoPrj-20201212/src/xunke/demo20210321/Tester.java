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
			System.out.println("�����ֻ���������Ч�й��ƶ��ֻ����룬�����20Ԫ�����Ż�ȯ��ά��");
		}catch(InvalidCMPhoneCodeException e){
			System.out.println(e.getMessage());
		}finally {
			System.out.println("��л��ʹ�ñ�ϵͳ��");
		}	

	}
	
	/**
	 * �ֻ�������
	 * @param phoneCode
	 */
	private static void checkPhone(String phoneCode) {
		
		//���ȼ��
		if(phoneCode.trim().length()!=11)
			throw new InvalidCMPhoneCodeException("�ֻ���ë���Ȳ���11λ�����飡");
		
		//ȫ���ּ��
		try {
			Long.parseLong(phoneCode);
		}catch(NumberFormatException e) {
			throw new InvalidCMPhoneCodeException("�ֻ������к��з������ַ������飡");
		}
		
		//��Ӫ�̼��
		int header = Integer.parseInt(phoneCode.trim().substring(0,3));
		if(header<135 || header>139)
			throw new InvalidCMPhoneCodeException("�ֻ����벻���й��ƶ��ֻ����룬���飡");
		
	}

}
