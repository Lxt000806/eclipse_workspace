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
		 * HashMap是以HashSet作为key管理机制的map实现
		 * 一个java类如果想作为hashmap的key，必须同时重写hashCode和equals方法，
		 * 并保持一定的契约关系
		 */
		System.out.println(map.size());
		System.out.println(map.get(new Apple("red",100)));
		
		
		/**
		 * TreeMap是以TreeSet作为key管理机制的map实现
		 * 一个java类如果想作为Treemap的key，就必须实现Comparable接口
		 */
		TreeMap<Apple,String> stuMap = new TreeMap<>();
		stuMap.put(new Apple("red",100),"david");
		stuMap.put(new Apple("red",100),"mary");
		
		System.out.println(stuMap.size());
		System.out.println(stuMap.get(new Apple("red",100)));
	}

}
