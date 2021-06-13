/**
 * 
 */
package xunke.demo20201226;

/**
 * @author HY
 *
 */
public class StringDemo {

	/**
	 * @param args
	 */
	public static void main(java.lang.String[] args) {


		String str = " abcdefg ";
		System.out.println(str.length());
		System.out.println(str.trim().length()); //将左右空格去除,求字符串长度

		String str2 = "abcxyzmn";
		System.out.println(str2.startsWith("abc")); //true ,是否以"abc"开头
		System.out.println(str2.startsWith("xyz", 3)); // 从字符数组下标3开始，是否以"xyz"开头
		System.out.println(str2.endsWith("zmn")); //是否以"zmn"结尾
		
		System.out.println(str2.charAt(0)); //通过下标找字符
		System.out.println(str2.indexOf('m')); //通过字符找下标(第一次出现m的位置)
		System.out.println(str2.indexOf('m', 5)); //从字符数组下标5开始，寻找m的位置
		System.out.println(str2.lastIndexOf('m')); //从后往前,找到最后面一个m停止
		
		if(!str2.contains("kkk")) //装饰方法：为了好用，其本质还是indexOf()
			System.out.println("没有kkk的存在");
		
		System.out.println(str2.substring(3,5)); //开始范围(包含),结束范围(不包含)切割
		System.out.println(str2.concat("xyz")); //字符串拼接
		System.out.println(str2+"xyz"); //语法糖现象
		
		System.out.println(str.equalsIgnoreCase("abcdEfg")); //忽略大小写的比较是否相等
		System.out.println("abcd".compareTo("abcd")); //比较大小;相等返回0 , 小于返回-1 , 大于返回1
		
		String str3 = "a"+"b"+"c"+"d"; //涉及到大量的字符串运算,会产生大量垃圾对象;我们用StringBuffer对象解决
		StringBuffer sb = new StringBuffer(); //字符串缓冲,可变字符串对象
		sb.append("a").append("b").append("c").append("d") //链式编程;消除中间结果,提升效率
		  .deleteCharAt(sb.length()-1); //删除最后一个字符
		
		StringBuilder sb2 = new StringBuilder(); //与StringBuffer的API相同,但速度更快,推荐使用
	}

}
