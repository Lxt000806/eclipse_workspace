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
 * ���ķ��ࣺ
 * ������֣�1�������   2.������
 * �����ݸ�ʽ�֣�
 * 1.��������/�ֽ�������Ĭ��ģʽ�� xxxInputStream  xxxOutputStream
 * 2.�ַ���/�ı���(�Ƕ������������������֮��Ľ��) xxxReader  xxxWriter
 *
 */
public class NovelWriter {

	/**
	 * InputStreamReader���ֽ���ת��Ϊ�ı���������CPU����
	 * BufferedReader���Ч�ʣ��ɴ洢8000��
	 * PrintWriter���ı���ת��Ϊ�ֽ���
	 * FileOutPutStream�Ѷ���������д���ļ�
	 */
	public static void main(String[] args) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(new File("d:/novel.txt")));
			
			String entry;
			try {
				entry = reader.readLine(); //�ù�reader����̶�ȡһ��
				writer.println(entry);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			writer.flush(); //��������������ݿ��ٵ���Ŀ�ĵ�
			writer.close();
		}
		
		
	}

}