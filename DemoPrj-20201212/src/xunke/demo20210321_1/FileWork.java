/**
 * 
 */
package xunke.demo20210321_1;

import java.io.File;
import java.util.Date;

/**
 * @author HY
 *
 */
public class FileWork {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * �ļ���ַ�� ��б��\ ��Ҫд����
		 *          ��б��/ ֻ��Ҫһ��
		 */
		File file = new File("d:\\records.txt");
		
		System.out.println(file.length()); //�ļ��ĳ���
		System.out.println(file.isFile()); //�Ƿ�Ϊ�ļ�����
		System.out.println(file.isDirectory()); //�Ƿ�ΪĿ¼
		System.out.println(new Date(file.lastModified()).toLocaleString()); //���һ���޸��ļ�������
		
		File dir = new File("d:\\devtools");
		if(dir.isDirectory()) {
			String[] names = dir.list(); //Ŀ¼��������ļ��б�������һ���ַ�������
			for(String n:names)
				System.out.println(n); //��foreachѭ�����
			
		}
		else
			System.out.println("����Ŀ¼���޷���Ŀ¼��");

	}

}