/**
 * 
 */
package xunke.demo20210328_2;

import java.util.TreeSet;

/**
 * @author HY
 *
 */
public class TreeSetDemo {
	public static void main(String[] args) {
		
		TreeSet<Integer> intSet = new TreeSet<>();
		intSet.add(10);
		intSet.add(8);
		intSet.add(12);
		intSet.add(112);
		intSet.add(2);
		
		System.out.println(intSet.size());
		for(int a:intSet) {
			System.out.println(a);
		}
	}
}
