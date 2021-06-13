/**
 * 编写一个彩票号码生成器程序，能够随机生成一注36选7彩票号码。
 * 比如36选7， 一注就是从36个数字中随机抽取7个。 （选做：让这些号码从小到大排序。 使用Arrays.sort() 静态方法）
 */
package demo20210107;

import java.util.Arrays;
import java.util.Random;

/**
 * @author HY
 *
 */
public class LotteryNumberBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int a[] = new int[36];
		int b[] = new int[7];
		Random r = new Random();
		
		for(int i=0; i<36; i++) {
			 a[i] = i + 1; //初始化赋值
		}
		
		for (int i=0; i<7; i++) { //随机抽取7个数
			int index = r.nextInt(36-i); //获得随机数数组下标
			b[i] = a[index];
			swaps(a,index,35-i); //交换 a[index] 和 a[35-i] ,使a[index]这个数放到数组最后面,保证不重复取值	
		}
		
		Arrays.sort(b); // 进行排序
		
		System.out.println(Arrays.toString(b)); //输出数组结果
		
	}
	
	// 交换数组中下标a 和 下标b的数值
	public static void swaps(int array[],int a, int b){	
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	

}
