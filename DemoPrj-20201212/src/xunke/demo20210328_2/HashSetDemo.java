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
		stringSet.add("abcd");//�ظ�Ԫ���ǼӲ���ȥ��
		System.out.println(stringSet.size());
		
		for(String str:stringSet)
			System.out.println(str);
		
		
	}

}
