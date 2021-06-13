import java.util.Arrays;
import java.util.Scanner;

/**
 * 菲波那契数列是指这样的数列: 数列的第一个和第二个数都为1，接下来每个数都等于前面2个数之和。
 * 给出一个正整数k，要求菲波那契数列中第k个数是多少。
 * 输入
 * 输入一行，包含一个正整数k。（1 <= k <= 46）
 * 输出
 * 输出一行，包含一个正整数，表示菲波那契数列中第k个数的大小
 * 样例输入：19
 * 样例输出：4181
 */

/**
 * @author HY
 *
 */
public class Array {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int arr[] = new int[46];
		int k;
		
		Arrays.fill(arr, 0,2,1); //用fill()方法填充arr[0]到arr[2]之间的数组元素,赋值为1
		System.out.println("请输入需要计算的第k个数（1 <= k <= 46）：");
		Scanner scanner = new Scanner(System.in);
		k = scanner.nextInt();
		
		for(int i=2;i<=k-1;i++) {
			arr[i] = arr[i-2] + arr[i-1]; //计算每个数等于前两个数之和
		}
		
		System.out.println(arr[k-1]); //由于数组下标0开始,第k个数下标为k-1

	}

}
