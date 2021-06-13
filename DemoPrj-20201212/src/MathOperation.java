import java.util.Scanner;

/**
 * 写一个输入两位数，实现加减乘除的程序。并和同学比较一下各种的功能、实现方法的异同等等。
 * 写出自己程序存在的缺陷，以及比别人做的好的地方。
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
		
		System.out.print("请输入两个数：");
		a = sc.nextDouble();
		b = sc.nextDouble();
		
		System.out.println("加法结果:"+add(a,b));
		System.out.println("减法结果:"+subtract(a,b));
		System.out.println("乘法结果:"+multiply(a,b));
		System.out.println("除法结果:"+divide(a,b));
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
