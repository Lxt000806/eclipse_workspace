package xunke.demo20210328_1;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListDemo {
	public static void main(String[] args) {
		
		//����������ֻ�ܴ�Ŷ��󣬻������ͻ��Զ�װ���Ϊ������
		/*
		    ��ΪArrayList���Դ�Ÿ����������ͣ���ȡ�����õ�ʱ����ڷ��գ�
		    ��Ҫʹ�÷��ͣ�ʹ��ֻ�ܴ��һ����������
		 */
		ArrayList<Object> al = new ArrayList<Object>();
		al.add(12);
		al.add(12.5);
		al.add(true);
		al.add("abcd");
		al.add(0, "kkk");
		al.remove(1);
		
		//������
		ArrayList<String> stringList = new ArrayList<>();
		ArrayList<Integer> intList = new ArrayList<>();
		
		//1.���������
		for(int i=0;i<al.size();i++)
			System.out.println(al.get(i));//�൱��al[i]
		
		//2.��������ר�ñ�����
		for(Object obj:al)
			System.out.println(obj);
		
		//3.������(iterator)������
		Iterator itr = al.iterator();
		while(itr.hasNext())
			System.out.println(itr.next());
		
		/*
		    size()  			���arraylist���ڲ���ʵ�ʰ���Ԫ�ص�����
			add()  			         ׷�ӵ����һ��
			add(index,obj)  	�������ݵ�index��ָ�����±�λ��
			remove(index)       ɾ��ָ��indexλ�õ�Ԫ��
			remove(index)       ɾ����ö���ƥ���Ԫ�أ�����equalsƥ�䣩
			contain(obj)     	�鿴�Ƿ���ڸ�Ԫ��
			indexOf(obj)    	�鿴Ԫ�س��ֵ�����λ����ͷ��ʼ�飩
			lastIndexOf(obj)    �鿴Ԫ�س��ֵ�����λ����ĩβ��ʼ�飩
			isEmpty()       	�鿴ArrayList�Ƿ������ЧԪ��
			subList()   		������List
			toArray()   		��Listת����
			trimToSize   		��С������ʵ��Ԫ�ش�С
		*/
	}
}
