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
 * 流的分类：
 * 按方向分：1。输出流   2.输入流
 * 按内容格式分：
 * 1.二进制流/字节流（流默认模式） xxxInputStream  xxxOutputStream
 * 2.字符流/文本流(是二进制流经过编码过滤之后的结果) xxxReader  xxxWriter
 *
 */
public class NovelWriter {

	/**
	 * InputStreamReader把字节流转换为文本流，交给CPU处理
	 * BufferedReader提高效率，可存储8000个
	 * PrintWriter把文本流转换为字节流
	 * FileOutPutStream把二进制数据写入文件
	 */
	public static void main(String[] args) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(new File("d:/novel.txt")));
			
			String entry;
			try {
				entry = reader.readLine(); //用过reader向键盘读取一行
				writer.println(entry);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			writer.flush(); //清空流，发送数据快速到达目的地
			writer.close();
		}
		
		
	}

}
