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
	 *  对象写盘操作
	 */
	public static void main(String[] args) {
		
		Student stu = new Student();
		stu.setStuNo(123);
		stu.setStuName("mary");

		Book book = new Book("JAVAEE 学习");
		stu.setBook(book);
		
		ObjectOutputStream oos = null;
		/*
		 * ObjectOutputStream  对象输出流：对象转换成二进制
		 * FileOutputStream 文件输出流：以二进制方式存储到文件中
		 */
		try {
			oos = new ObjectOutputStream(new FileOutputStream("d:\\data.bin")); //对象变成二进制,二进制变成文件
			oos.writeObject(stu); //将对象进行二进制写盘
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
