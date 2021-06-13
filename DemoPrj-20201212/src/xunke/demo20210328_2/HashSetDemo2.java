/**
 * 
 */
package xunke.demo20210328_2;

import java.util.HashSet;

/**
 * @author HY
 *
 */
public class HashSetDemo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HashSet<Apple> appleSet = new HashSet<>();
		
		/*
		 * 重复元素可以插入HashSet中，无法正常工作；
		 * 这是因为每个对象调用的hashcode()是object里的方法，返回的是对象在内存中的地址；每个值都不一样，所以重复元素可以插入
		 * 这个时候我们要重写object里的hashCode()方法，使得HashSet能正常工作
		*/
		
		/*
		  hashcode和equals 重写规范：
			1.hashCode不同，equals一定要不同
			2.hashCode相同，equals可以不同
			如果一个对象最终要和hashSet打交道的时候，就必须同时重写hashCode和equals方法，
			且必须遵守以上约定，否则会重写失败；
		*/
		appleSet.add(new Apple("red",1000));
		appleSet.add(new Apple("red",1000));
		
		System.out.println(appleSet.size());
		
		//hashCode()方法，每个java对象的值都不同
		System.out.println(new Apple("red",1000).hashCode());
	}

}
