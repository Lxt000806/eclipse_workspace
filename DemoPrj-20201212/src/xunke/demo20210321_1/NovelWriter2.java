/**
 * 
 */
package xunke.demo20210321_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 */
public class NovelWriter2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream("d:/novel.txt",true));//true表示可以接着上次追加append
			String entry;
			
			System.out.println("--- 小说编辑器v1.0 ----\n");
			int lines=1;
			System.out.print("["+lines+"]");
			while(!(entry = reader.readLine()).equalsIgnoreCase("end")) {
				writer.println(entry);
				lines++;
				System.out.print("["+lines+"]");
			}
			
			System.out.println("\n 本次编写结束，共计编写"+(lines-1)+"行");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}finally {
			writer.flush(); //清空流，发送数据快速到达目的地
			writer.close();
		}
		
		
	}

}
