/**
 * 
 */
package xunke.demo20210328;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author HY
 *
 */
public class StudentReader {

	/**
	 * �����ȡ����
	 */
	public static void main(String[] args) {
		
		ObjectInputStream ois = null;
		try {
			/*
			 * ObjectInputStream ����������
			 * FileInputStream �ļ�������
			 * */
			ois = new ObjectInputStream(new FileInputStream("d:/data.bin"));
			Student stu = (Student)ois.readObject();
			System.out.println(stu);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
