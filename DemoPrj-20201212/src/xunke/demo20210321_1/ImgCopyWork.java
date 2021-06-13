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
			
			byte[] buffer = new byte[2000]; //��������һ�οɶ�ȡ2000��
			int size = 0;
			
			while((size=fis.read(buffer))!=-1) //buffer������-1, �����ж�ȡ������
				fos.write(buffer,0,size); //��buffer��д��size���ֽ���
			
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
