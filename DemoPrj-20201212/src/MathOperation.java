import java.util.Scanner;

/**
 * дһ��������λ����ʵ�ּӼ��˳��ĳ��򡣲���ͬѧ�Ƚ�һ�¸��ֵĹ��ܡ�ʵ�ַ�������ͬ�ȵȡ�
 * д���Լ�������ڵ�ȱ�ݣ��Լ��ȱ������ĺõĵط���
 */

/**
 * @author HY
 *
 */
public class MathOperation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Double a,b;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("��������������");
		a = sc.nextDouble();
		b = sc.nextDouble();
		
		System.out.println("�ӷ����:"+add(a,b));
		System.out.println("�������:"+subtract(a,b));
		System.out.println("�˷����:"+multiply(a,b));
		System.out.println("�������:"+divide(a,b));
	}

	public static Double add(Double a,Double b) {
		return (a+b)/1.0;
	}
	
	public static Double subtract(Double a,Double b) {
		return (a-b)/1.0;
	}
	
	public static Double multiply(Double a,Double b) {
		return (a*b)/1.0;
	}
	
	public static Double divide(Double a,Double b) {
		return (a/b)/1.0;
	}
}
