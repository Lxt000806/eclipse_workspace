/**
 * 
 */
package xunke.demo20201225;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//�����ʹ��д��룬�ṩ�˶Ի������͵�֧��
		System.out.println(Integer.parseInt("12")+1); //���ַ���ת����Int����
		System.out.println("12"+1); //����µ��ַ���"121"
		System.out.println(Integer.toBinaryString(123)); //ת���ɶ�����
		System.out.println(Integer.toOctalString(123)); //ת���ɰ˽���
		System.out.println(Integer.toHexString(123)); //ת����ʮ������
		
		System.out.println(Character.isDigit('9')); //�ж��Ƿ�Ϊ�����ַ�
		System.out.println(Character.isDigit('a')); 
		
		
		Integer b = new Integer(13); //װ�����,��������->������
		int m = b.intValue(); //�������,������->��������
		
		Integer k = 13; //�Զ�װ�䣬����jdk�汾1.5����
		int w = k; //�Զ�����
		
//		double m = 2.5;
//		Double n = new Double(3.2);
		
		String str = new String("abcdefg");
		System.out.println(str.charAt(1)); //�����ַ���������ֵΪ1���ַ�
		str.replace('a', 'x'); //��a���滻Ϊx
		
		String str2 = new String("abcdefg");
		System.out.println(str==str2); //false �Ƚϵ����������ñ����Ƿ�ͬʱָ��һ������
		System.out.println(str.equals(str2)); //true �Ƚϵ����������ñ�����ָ�����������Ƿ����
		
		//��java�У��ַ�������һ���������Ͳ������޸��ˣ�������������ַ����Ĳ�����
		//�κ��ַ������޸ģ���������һ���µ��ַ�������
		//�����ַ��������ܸ��þ͸�����
		String str3 = "xyz";
		String str4 = "xyz";
		System.out.println(str3==str4); //true

	}

}
