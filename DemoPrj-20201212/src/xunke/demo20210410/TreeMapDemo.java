/**
 * 
 */
package xunke.demo20210410;

import java.util.TreeMap;

/**
 * @author HY
 *
 */
public class TreeMapDemo {

	public static void main(String[] args) {
		//集合类型里只能放对象
		TreeMap<Integer,String> stuMap = new TreeMap<>();
		
		stuMap.put(12, "nike");
		stuMap.put(1, "nike");
		stuMap.put(1, "nike");
		stuMap.put(102, "nike");
		stuMap.put(2, "nike");
		
		for(int id:stuMap.keySet())
			System.out.println(id+"->"+stuMap.get(id));
	}
	
	
}
