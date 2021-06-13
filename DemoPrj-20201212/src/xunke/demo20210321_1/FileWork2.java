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
public class FileWork2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File dir = new File("d:\\devtools");
		if(dir.isDirectory()) {
			File[] files = dir.listFiles(); //�õ�һ���ļ�����
			
			/**
			 *  %-25s ����룬����25���ַ���
			 *  %4.2f ������λС������Ч�ַ�������Ϊ4 
			 */
			for(File f:files)
				System.out.printf("%-25s%10s%18s %-30s\n",
						new Date(f.lastModified()).toLocaleString(),
						f.isDirectory()?"<DIR>":" ",
						f.isFile()?String.valueOf(f.length()):" ",
						f.getName());
			
		}
		else
			System.out.println("����Ŀ¼���޷���Ŀ¼��");

	}

}