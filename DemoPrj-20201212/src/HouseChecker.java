import java.util.Scanner;

/**
 * 某程序员开始工作，年薪N万，他希望在中关村公馆买一套60平米的房子，现在价格是200万，假设房子价格以每年百分之K增长，
 * 并且该程序员未来年薪不变，且不吃不喝，不用交税，每年所得N万全都积攒起来，问第几年能够买下这套房子？（第一年年薪N万，房价200万）
 * 输入一行，包含两个正整数N（10 <= N <= 50）, K（1 <= K <= 20），中间用单个空格隔开。
 * 输出如果在第20年或者之前就能买下这套房子，则输出一个整数M，表示最早需要在第M年能买下，否则输出Impossible。
 * 样例输入：50 10
 * 样例输出：8
 */

/**
 * 
 * @author HY
 *
 */
public class HouseChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int salary,rate,deposit;
		int year = 1;
		double price = 200;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("请输入年薪和房价增长率(中间用单个空格隔开)：");
		salary = scanner.nextInt();
		rate = scanner.nextInt();
		
		if(salary>50 || salary<10) System.out.println("输入的年薪超出范围！！！");
		else if (rate>20 || rate<1) System.out.println("输入的房价增长率超出范围！！！");
		else {
			deposit = salary;
			while(deposit<price) {
				//System.out.printf("第%d年,存款:%d,房价:%f\n",year,deposit,price);
				year++;
				price = price * (1+rate/100.0);
				deposit = deposit + salary;
				if(year>20) {
					System.out.println("Impossible");
					break;
				}
			}
			if(year<=20) System.out.println(year);
		}
		

	}

}
