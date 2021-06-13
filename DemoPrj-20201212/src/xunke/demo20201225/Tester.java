/**
 * 
 */
package xunke.demo20201225;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//类类型带有代码，提供了对基本类型的支持
		System.out.println(Integer.parseInt("12")+1); //把字符串转换成Int类型
		System.out.println("12"+1); //输出新的字符串"121"
		System.out.println(Integer.toBinaryString(123)); //转换成二进制
		System.out.println(Integer.toOctalString(123)); //转换成八进制
		System.out.println(Integer.toHexString(123)); //转换成十六进制
		
		System.out.println(Character.isDigit('9')); //判断是否为数字字符
		System.out.println(Character.isDigit('a')); 
		
		
		Integer b = new Integer(13); //装箱操作,基本类型->类类型
		int m = b.intValue(); //拆箱操作,类类型->基本类型
		
		Integer k = 13; //自动装箱，必须jdk版本1.5以上
		int w = k; //自动拆箱
		
//		double m = 2.5;
//		Double n = new Double(3.2);
		
		String str = new String("abcdefg");
		System.out.println(str.charAt(1)); //返回字符数组索引值为1的字符
		str.replace('a', 'x'); //将a都替换为x
		
		String str2 = new String("abcdefg");
		System.out.println(str==str2); //false 比较的是两个引用变量是否同时指向一个对象
		System.out.println(str.equals(str2)); //true 比较的是两个引用变量所指向对象的内容是否相等
		
		//在java中，字符串对象一旦创建，就不能再修改了，这种现象叫做字符串的不变性
		//任何字符串的修改，都将产生一个新的字符串对象
		//所以字符串对象能复用就复用了
		String str3 = "xyz";
		String str4 = "xyz";
		System.out.println(str3==str4); //true

	}

}
