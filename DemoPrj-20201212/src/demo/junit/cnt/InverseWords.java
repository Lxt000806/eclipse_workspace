/**
 * 
 */
package demo.junit.cnt;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author HY
 *
 */
public class InverseWords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = getEntry();
		
		printInverseArray(str);
	}
	

	/**
	 * �Ӽ��������ȡ�ַ���
	 * @return str
	 */
	public static String getEntry() {
		Scanner scanner = new Scanner(System.in);

        System.out.print("�������ַ�����");
        String str = scanner.nextLine();
        
		return str;
	}
	
	/**
	 * ���ַ������տո�ָ�����ӡ���
	 * @param str
	 */
	public static void printInverseArray(String str) {
		String[] wordArray = str.split(" ");
        Map<String,Integer> map = new HashMap<String, Integer>();
        
        for(int i=wordArray.length-1;i>=0;i--) {
        	System.out.print(wordArray[i]+" ");
        }
        
	}

}
