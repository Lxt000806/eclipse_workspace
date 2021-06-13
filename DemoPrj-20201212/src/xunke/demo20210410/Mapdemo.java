/**
 * 
 */
package xunke.demo20210410;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author HY
 *
 */
public class Mapdemo {

	/**
	 * Map接口是映射   key value键值对形式：
	 * some key map to value
	 * 
	 * 对应子接口：
	 *    HashMap, TreeMap
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map m;
		
		//HashMap里面分别是key和value的数据形式
		HashMap<String,String> stuMap = new HashMap<>();
			
		//存数值
		stuMap.put("s001","mary");//保存一个键值对
		stuMap.put("s002","david");
		
		//取数值
		System.out.println(stuMap.get("s001"));
		System.out.println(stuMap.get("s002"));
		
		//若没有则取默认值
		System.out.println(stuMap.getOrDefault("s003", "jenny"));
		
		System.out.println(stuMap.size());
		System.out.println(stuMap.isEmpty());
		System.out.println(stuMap.containsKey("s002"));
		System.out.println(stuMap.containsValue("mary"));
		
		//修改操作，相同的key后面会覆盖前面的
		stuMap.put("s002","kate");
		
		//删除操作
		stuMap.remove("s001");
		stuMap.remove("s001","mary"); //高精度删除，同时满足才能删除
		//如果某个key在map中没有找到对应的value值，则返回空
		
		//不同的key可以指向相同的value
		String stuname="hunter";
		stuMap.put("s004", stuname);
		stuMap.put("s005", stuname);
		
		//map的遍历
		//HashMap的key是使用Set来管理的，是无序且不能重复
		Set<String> keyList = stuMap.keySet();//返回Map所有的key所构成的集合
		for(String key:keyList)
			System.out.println(key+"->"+stuMap.get(key));
		
		
	}

}
