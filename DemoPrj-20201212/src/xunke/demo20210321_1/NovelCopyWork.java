/**
 * 
 */
package xunke.demo20210321_1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author HY
 *
 */
public class NovelCopyWork {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("d:/novel.txt")));
		    writer = new PrintWriter(new FileOutputStream("d:\\novel-bak.txt"));
		    String entry = null;
		    int times = 0;
		    
		    while((entry = reader.readLine())!=null) {
		    	writer.println(entry);
		    	times++;
		    }
		    System.out.println("copy the novel is ok!total lines:"+(times-1));
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writer.flush();
			writer.close();
		}

	}

}
