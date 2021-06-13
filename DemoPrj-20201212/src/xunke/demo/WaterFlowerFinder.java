/**
 * ��ĳ���ȵ�ˮ�ɻ���
 * ˮ�ɻ�����ָһ��nλ����n>3��,����ÿ��λ�ϵ����ֵ�n����֮�͵���������
 * ���磺1^3+5^3+3^3 = 153
 
 * λ����3
 * �����153,370,371,407
 * λ����5
 * �����54748,92727,93084
 */
package xunke.demo;

import java.util.Scanner;

/**
 * @author HY
 *
 */
public class WaterFlowerFinder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Random r = new Random();
//		System.out.println(r.nextInt(10)); //����0-9֮����������
//		System.out.println(r.nextDouble()); //����0-1֮������С��
//		System.out.println(r.nextBoolean()); //����0-1֮���������ֵ

		int size; //nλ��
		int begin,end; //��ʼ��Χ�ͽ�����Χ
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("���������ֵĳ���:");
		size = scanner.nextInt();
		
		//���磬��λ���Ŀ�ʼ��Χ�ͽ�����Χ�ֱ���100,999
		begin = (int)Math.pow(10, size-1); //10^(size-1)
		end = (int)Math.pow(10, size)-1; //(10^size)-1
		
		for(int num=begin;num<=end;num++) {
			if(isFlowerNum(num))
				System.out.println(num);
		}
	}
	
	/**
	 * ˮ�ɻ��жϣ���������Ӧ�汾��
	 * @param num
	 * @return
	 */
	static boolean isFlowerNum(int num) {
		
		int len = cntIntLength(num);
		int sum = 0;
		int temp = num;
		
		while(temp>0) {
			sum = sum + (int)Math.pow(temp%10,len); //����ÿ��λ�ϵ����ֵ�n����֮��
			temp = temp/10;
		}
		
		return sum==num; //���磺1^3+5^3+3^3 = 153
	}
	
	/**
	 * �ж�ĳ�������ĳ���
	 * @param num
	 * @return 
	 */
	static int cntIntLength(int num) {
		int len = 0;
		while(num>0) {
			num = num/10;
			len++;
		}
		return len;
	}

}
