/**
  * 求某范围质数（只能被1和自身整除的数）
 * 1.开始范围和结束范围自行录入
 * 2.每行输出10个
 * 3.然后统计质数个数和质数和
 * 
 */
package xunke.demo;

import java.util.Scanner;

/**
 * 某质数查找器
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
		int cnt=0; //质数的数量
		long sum = 0L; //质数的和
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("开始范围：");
		begin = scanner.nextInt();
		System.out.print("结束范围：");
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
	  *   质数判断
	 * @param num 待测试的整数
	 * 
	 * @return 
	 *   true 是质数
	 *   false 不是质数
	 */
	static boolean isPrime(int num) {
		
		//特例处理
		if(num<=1) return false;
		if(num==2) return true;
		
		//常规处理
		int i;
		for(i=2;i<=num-1;i++)
			if(num%i==0) break;
		
		return i==num;
	}
	
	/**
	 * 质数判断（大数据自适应版本）
	 * @param num 待测试的参数
	 * @return
	 *    true 是质数
	 *    false 不是质数
	 */
	static boolean isAdvPrime(int num) {
		
		//特例处理
		if(num<=1) return false;
		if(num==2) return true;
		
		//常规处理
		int top = (int)Math.sqrt(num);
		int i;
		for(i=2;i<=top;i++)
			if(num%i==0) break;
		
		return i==top+1;
	}

}
