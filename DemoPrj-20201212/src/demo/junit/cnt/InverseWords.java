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
	 * ´Ó¼üÅÌÊäÈë»ñÈ¡×Ö·û´®
	 * @return str
	 */
	public static String getEntry() {
		Scanner scanner = new Scanner(System.in);

        System.out.print("ÇëÊäÈë×Ö·û´®£º");
        String str = scanner.nextLine();
        
		return str;
	}
	
	/**
	 * ½«×Ö·û´®°´ÕÕ¿Õ¸ñ·Ö¸î£¬µ¹Ğò´òÓ¡Êä³ö
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
