/**
 * 
 */
package xunke.demo20210410;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author HY
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HashMap<Apple,String> map = new HashMap<>();
		map.put(new Apple("red",100),"david");
		map.put(new Apple("red",100),"david");
		
		/**
		 * HashMap����HashSet��Ϊkey������Ƶ�mapʵ��
		 * һ��java���������Ϊhashmap��key������ͬʱ��дhashCode��equals������
		 * ������һ������Լ��ϵ
		 */
		System.out.println(map.size());
		System.out.println(map.get(new Apple("red",100)));
		
		
		/**
		 * TreeMap����TreeSet��Ϊkey������Ƶ�mapʵ��
		 * һ��java���������ΪTreemap��key���ͱ���ʵ��Comparable�ӿ�
		 */
		TreeMap<Apple,String> stuMap = new TreeMap<>();
		stuMap.put(new Apple("red",100),"david");
		stuMap.put(new Apple("red",100),"mary");
		
		System.out.println(stuMap.size());
		System.out.println(stuMap.get(new Apple("red",100)));
	}

}
