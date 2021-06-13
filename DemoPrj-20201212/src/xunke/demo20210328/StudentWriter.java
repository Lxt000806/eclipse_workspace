/**
 * 
 */
package xunke.demo20210328;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author HY
 *
 */
public class StudentWriter {

	/**
	 *  ����д�̲���
	 */
	public static void main(String[] args) {
		
		Student stu = new Student();
		stu.setStuNo(123);
		stu.setStuName("mary");

		Book book = new Book("JAVAEE ѧϰ");
		stu.setBook(book);
		
		ObjectOutputStream oos = null;
		/*
		 * ObjectOutputStream  ���������������ת���ɶ�����
		 * FileOutputStream �ļ���������Զ����Ʒ�ʽ�洢���ļ���
		 */
		try {
			oos = new ObjectOutputStream(new FileOutputStream("d:\\data.bin")); //�����ɶ�����,�����Ʊ���ļ�
			oos.writeObject(stu); //��������ж�����д��
			System.out.println("save is ok!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				oos.flush();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
