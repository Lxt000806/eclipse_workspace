/**
 * 
 */
package demo.junit.cnt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author HY
 *
 */
public class CntCharNum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  
        String str = getEntry();
        Map<String,Integer> map = getArray(str);
        printResult(map);
        
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
	 * �÷������տո����ַ�����ͳ����������ͬ���ʵĸ��������ؼ�ֵ����ʽMap
	 * @param Words
	 * @return Map
	 */
	public static Map<String, Integer> getArray(String str) {
		String[] wordArray = str.split(" ");
        Map<String,Integer> map = new HashMap<String, Integer>();
        
        for(int i=0;i<wordArray.length;i++) {
        	if(!map.containsKey(wordArray[i])) {
        		map.put(wordArray[i],1);
        	}else {
        		int currentNum=map.get(wordArray[i])+1;
        		map.put(wordArray[i],currentNum);
        	}
        }
        
        return map;
	}
	
	/**
	 * ��ӡ����ظ����ʵĸ���
	 */
	public static void printResult(Map<String, Integer> map) {
		Set<String> keyList = map.keySet();
        for(String key:keyList)
        	System.out.println(key+"�ַ�����"+map.get(key)+"��");
	}
}
