/**
 * 
 */
package xunke.demo20210225;

import java.text.ParseException;
import java.text.SimpleDateFormat; //java文字包，主要做文字处理工作
import java.util.Calendar;
import java.util.Date;

/**
 * @author HY
 *
 */
public class Datedemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Date now = new Date(); //得到当前系统时间
		//过期方法@Deprecated,该方法可能在今后的JDK中不再被支持
		System.out.println(now.toLocaleString());  //本地时间字符串

		//时间是一个数字，是1970年1月1日到现在所经过的毫秒数
		System.out.println(now.getTime()); //获得时间长整数
		Date d = new Date(0); //java时间原点
		
		//转换器，时间转字符串
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 第DD天 第w周"); //HH:mm:ss 可自定义时间格式
		System.out.println(sdf.format(now));
		
		//字符串转时间
		String past = "2013-9-15 23:12:23";
		Date pastTime = null;
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf2.setLenient(false); //容错开关，字符串输入不规范会报错
		try {
			pastTime = sdf2.parse(past);  //吃进字符串，根据规范格式转成时间
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//时间部分细节的获取(date part)
		Calendar c = Calendar.getInstance(); //新建对象,单例模式
		c.setTime(now); //Calendar类是一个时间分析工具，其能够接受时间对象，执行时间分析
		System.out.println(c.get(Calendar.YEAR)); //年份
		System.out.println(c.get(Calendar.WEEK_OF_YEAR)); //今年第几周
		
		//时间的比较
		System.out.println(pastTime.before(now));
		System.out.println(pastTime.after(now));
		System.out.println(pastTime.compareTo(now));
	}

		
}
