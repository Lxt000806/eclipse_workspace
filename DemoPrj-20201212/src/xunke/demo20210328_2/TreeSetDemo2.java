/**
 * 
 */
package xunke.demo20210328_2;

import java.util.TreeSet;

/**
 * @author HY
 *
 */
public class TreeSetDemo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TreeSet<Apple> appleSet = new TreeSet<>();
		/*一个java类如果没有经过处理，是无法放在TreeSet中进行排序的*/
		appleSet.add(new Apple("red",100));
		appleSet.add(new Apple("red",101));
		appleSet.add(new Apple("red",102));
		System.out.println(appleSet.size());
		
		for(Apple apple:appleSet) {
			System.out.println(apple);
		}
	}

}
