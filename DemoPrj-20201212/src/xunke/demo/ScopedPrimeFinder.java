/**
  * ��ĳ��Χ������ֻ�ܱ�1����������������
 * 1.��ʼ��Χ�ͽ�����Χ����¼��
 * 2.ÿ�����10��
 * 3.Ȼ��ͳ������������������
 * 
 */
package xunke.demo;

import java.util.Scanner;

/**
 * ĳ����������
 * @author HY
 *
 */
public class ScopedPrimeFinder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		System.out.println(isPrime(123));
//		System.out.println(isPrime(23));
//		System.out.println(isPrime(231));
		int begin,end;
		int cnt=0; //����������
		long sum = 0L; //�����ĺ�
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("��ʼ��Χ��");
		begin = scanner.nextInt();
		System.out.print("������Χ��");
		end = scanner.nextInt();
		
		for(int num=begin;num<=end;num++) {
			if(isAdvPrime(num)) {
				System.out.print(num+"\t");
				sum += num;
				cnt++;
				if(cnt%10==0) System.out.println();
			}
		}
		
		System.out.printf("\n\nprime count:%d,prime sum:%d\n",cnt,sum);

	}
	
	/**
	  *   �����ж�
	 * @param num �����Ե�����
	 * 
	 * @return 
	 *   true ������
	 *   false ��������
	 */
	static boolean isPrime(int num) {
		
		//��������
		if(num<=1) return false;
		if(num==2) return true;
		
		//���洦��
		int i;
		for(i=2;i<=num-1;i++)
			if(num%i==0) break;
		
		return i==num;
	}
	
	/**
	 * �����жϣ�����������Ӧ�汾��
	 * @param num �����ԵĲ���
	 * @return
	 *    true ������
	 *    false ��������
	 */
	static boolean isAdvPrime(int num) {
		
		//��������
		if(num<=1) return false;
		if(num==2) return true;
		
		//���洦��
		int top = (int)Math.sqrt(num);
		int i;
		for(i=2;i<=top;i++)
			if(num%i==0) break;
		
		return i==top+1;
	}

}
