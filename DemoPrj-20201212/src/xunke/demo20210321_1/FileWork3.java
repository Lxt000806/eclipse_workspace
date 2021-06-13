/**
 * 
 */
package xunke.demo20210321_1;

import java.io.File;
import java.io.IOException;

/**
 * @author HY
 *
 */
public class FileWork3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File file = new File("d:/kkk.txt");
		
		if(!file.exists())
			try {
				file.createNewFile(); //新建文件
				System.out.println("file is created ok");
			} catch (IOException e) {
				e.printStackTrace();
			}
		else {
			file.delete(); //删除文件
			System.out.println("file is deleted is ok");
		}
			

	}

}
