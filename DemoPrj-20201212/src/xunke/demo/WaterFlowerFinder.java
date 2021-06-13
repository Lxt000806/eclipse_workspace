/**
 * 求某长度的水仙花数
 * 水仙花数是指一个n位数（n>3）,它的每个位上的数字的n次幂之和等于它本身。
 * 例如：1^3+5^3+3^3 = 153
 
 * 位数：3
 * 输出：153,370,371,407
 * 位数：5
 * 输出：54748,92727,93084
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
//		System.out.println(r.nextInt(10)); //产生0-9之间的随机整数
//		System.out.println(r.nextDouble()); //产生0-1之间的随机小数
//		System.out.println(r.nextBoolean()); //产生0-1之间的随机真假值

		int size; //n位数
		int begin,end; //开始范围和结束范围
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("请输入数字的长度:");
		size = scanner.nextInt();
		
		//例如，三位数的开始范围和结束范围分别是100,999
		begin = (int)Math.pow(10, size-1); //10^(size-1)
		end = (int)Math.pow(10, size)-1; //(10^size)-1
		
		for(int num=begin;num<=end;num++) {
			if(isFlowerNum(num))
				System.out.println(num);
		}
	}
	
	/**
	 * 水仙花判断（长度自适应版本）
	 * @param num
	 * @return
	 */
	static boolean isFlowerNum(int num) {
		
		int len = cntIntLength(num);
		int sum = 0;
		int temp = num;
		
		while(temp>0) {
			sum = sum + (int)Math.pow(temp%10,len); //它的每个位上的数字的n次幂之和
			temp = temp/10;
		}
		
		return sum==num; //例如：1^3+5^3+3^3 = 153
	}
	
	/**
	 * 判断某个整数的长度
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
