package xunke.demo20210328_1;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListDemo {
	public static void main(String[] args) {
		
		//集合类型中只能存放对象，基本类型会自动装箱成为类类型
		/*
		    因为ArrayList可以存放各种数据类型，在取出调用的时候存在风险，
		    故要使用泛型，使其只能存放一种数据类型
		 */
		ArrayList<Object> al = new ArrayList<Object>();
		al.add(12);
		al.add(12.5);
		al.add(true);
		al.add("abcd");
		al.add(0, "kkk");
		al.remove(1);
		
		//泛型类
		ArrayList<String> stringList = new ArrayList<>();
		ArrayList<Integer> intList = new ArrayList<>();
		
		//1.常规遍历法
		for(int i=0;i<al.size();i++)
			System.out.println(al.get(i));//相当于al[i]
		
		//2.集合类型专用遍历法
		for(Object obj:al)
			System.out.println(obj);
		
		//3.迭代体(iterator)遍历法
		Iterator itr = al.iterator();
		while(itr.hasNext())
			System.out.println(itr.next());
		
		/*
		    size()  			获得arraylist中内部的实际包含元素的数量
			add()  			         追加到最后一个
			add(index,obj)  	插入数据到index所指定的下标位置
			remove(index)       删除指定index位置的元素
			remove(index)       删除与该对象匹配的元素（依靠equals匹配）
			contain(obj)     	查看是否存在该元素
			indexOf(obj)    	查看元素出现的索引位（从头开始查）
			lastIndexOf(obj)    查看元素出现的索引位（从末尾开始查）
			isEmpty()       	查看ArrayList是否存在有效元素
			subList()   		创造子List
			toArray()   		把List转数组
			trimToSize   		缩小容量到实际元素大小
		*/
	}
}
