/**
 * 
 */
package xunke.demo20210321_1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author HY
 *
 */
public class ImgCopyWork {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream("d:\\stream.jpg");
			fos = new FileOutputStream("d:/stream-bak.jpg");
			
			byte[] buffer = new byte[2000]; //缓冲器，一次可读取2000个
			int size = 0;
			
			while((size=fis.read(buffer))!=-1) //buffer不等于-1, 代表有读取到数据
				fos.write(buffer,0,size); //从buffer中写入size个字节数
			
			System.out.println("copy the image is ok");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
