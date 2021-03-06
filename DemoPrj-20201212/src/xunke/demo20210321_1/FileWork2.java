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
public class FileWork2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File dir = new File("d:\\devtools");
		if(dir.isDirectory()) {
			File[] files = dir.listFiles(); //得到一个文件数组
			
			/**
			 *  %-25s 左对齐，长度25的字符串
			 *  %4.2f 保留两位小数点有效字符，长度为4 
			 */
			for(File f:files)
				System.out.printf("%-25s%10s%18s %-30s\n",
						new Date(f.lastModified()).toLocaleString(),
						f.isDirectory()?"<DIR>":" ",
						f.isFile()?String.valueOf(f.length()):" ",
						f.getName());
			
		}
		else
			System.out.println("不是目录，无法列目录！");

	}

}
