/**
 * 
 */
package xunke.demo20210328_2;

import java.util.HashSet;

/**
 * @author HY
 *
 */
public class HashSetDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HashSet<String> stringSet = new HashSet<>();

		stringSet.add("abcd");
		stringSet.add("abcd");//重复元素是加不进去的
		System.out.println(stringSet.size());
		
		for(String str:stringSet)
			System.out.println(str);
		
		
	}

}
