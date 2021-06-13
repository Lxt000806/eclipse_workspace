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
	 * ���ģʽ��
	 * ��ǰ�˾�����γ��ԣ��ܽ����һ����֮��Ч�Ĵ�������ķ�����
	 * ��ߴ���Ч�ʣ���߿ɸ�����
	 * �ܹ���������ʦ���Ĵ���
	 * 
	 * �������ģʽ��
	 * 1.����ģʽ���������Ӧ�ó���Χ������ֻ��һ��ʵ��
	 *          ���ó��ϣ�
	 *                 ����һ������ɱ��߰�����Ҫ���Ĵ�����CPUʱ���Լ��ڴ���Դ
	 *                 ����֮��û�в��죨��״̬����û�б�Ҫ�����������
	 * 
	 * һ���࣬û��ʵ�����ԣ�����֮��û�в��죬���ǰ��������������״̬���࣬
	 * ��������Ķ�����Ա��㷺���ã�
	 * 
	 * 2.����ģʽ��
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(System.in);
		String phoneType = null;
		
		while(true) {
			//��������
			PhoneFactory factory = PhoneFactory.getInstance();
			
			System.out.print("�������ֻ����ͣ�");
			phoneType = scanner.next();
			try {
				Callable device = factory.makePhone(phoneType);
				device.call();
			}catch(UnSupportPhoneTypeException e) {
				System.out.println("��������֧��"+phoneType+"���͵��ֻ�����");
			}
			
		}
		
	}

}
