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
		 * �ظ�Ԫ�ؿ��Բ���HashSet�У��޷�����������
		 * ������Ϊÿ��������õ�hashcode()��object��ķ��������ص��Ƕ������ڴ��еĵ�ַ��ÿ��ֵ����һ���������ظ�Ԫ�ؿ��Բ���
		 * ���ʱ������Ҫ��дobject���hashCode()������ʹ��HashSet����������
		*/
		
		/*
		  hashcode��equals ��д�淶��
			1.hashCode��ͬ��equalsһ��Ҫ��ͬ
			2.hashCode��ͬ��equals���Բ�ͬ
			���һ����������Ҫ��hashSet�򽻵���ʱ�򣬾ͱ���ͬʱ��дhashCode��equals������
			�ұ�����������Լ�����������дʧ�ܣ�
		*/
		appleSet.add(new Apple("red",1000));
		appleSet.add(new Apple("red",1000));
		
		System.out.println(appleSet.size());
		
		//hashCode()������ÿ��java�����ֵ����ͬ
		System.out.println(new Apple("red",1000).hashCode());
	}

}
