/**
 * 
 */
package xunke.demo20210314_1;

import java.io.IOException;

/**
 *   
 *1.JAVAϵͳ�������飺
 *   �����쳣�����ѡ����һ���쳣�࣬��������ʵ����
 *   �����쳣���������Ӧ������
 *   �׳��ö����JVM
 *   ��ϵͳ�׳��쳣���֮�����佫�������У�
 *   
 *2.JVM�յ��쳣���󣬾��ǵõ��쳣�����������2���£�
 *   ֹͣ�������У���ӡ�쳣������ϸ��Ϣ������̨
 *
 */
public class Tester1 {

	/**
	 *  1.һ�������ڵ��쳣��Ҫô�׳���Ҫô����
	 *  2.ϵͳĬ���׳�����ʱ�쳣��RuntimeException����error
	 * 
	 */
	public static void main(String[] args) throws IOException {
		
		int a = 10;
		int b = 2;
		int c =divide(a,b);
		Integer.parseInt("abc");
		
		System.out.println(c);

	}
	
	private static int divide(int a,int b) throws IOException{
		
		int c = 1;
		//�ֶ��쳣���Լ�ѡ�ͣ�new��������ݣ��Լ��ף�
		if(a<10)
			throw new IOException("����a����С��10");
		
		//�Զ��쳣��ϵͳ�쳣�����ѡ�ͣ�ϵͳnew��ϵͳ������ݣ�ϵͳ�������׳���
		try {
			c = a/b;
		}catch(Exception e) {
			System.out.println("�쳣�Ѿ�������");
			c = 0;
		}
		
		return c;
	}

}

