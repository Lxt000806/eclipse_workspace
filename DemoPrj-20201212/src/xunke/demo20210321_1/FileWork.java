/**
 * 
 */
package xunke.demo20210321_1;

import java.io.File;
import java.util.Date;

/**
 * @author HY
 *
 */
public class FileWork {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * 文件地址中 反斜杠\ 需要写两个
		 *          正斜杠/ 只需要一个
		 */
		File file = new File("d:\\records.txt");
		
		System.out.println(file.length()); //文件的长度
		System.out.println(file.isFile()); //是否为文件类型
		System.out.println(file.isDirectory()); //是否为目录
		System.out.println(new Date(file.lastModified()).toLocaleString()); //最后一次修改文件的日期
		
		File dir = new File("d:\\devtools");
		if(dir.isDirectory()) {
			String[] names = dir.list(); //目录里的所有文件列表，返回一个字符串数组
			for(String n:names)
				System.out.println(n); //用foreach循环输出
			
		}
		else
			System.out.println("不是目录，无法列目录！");

	}

}
