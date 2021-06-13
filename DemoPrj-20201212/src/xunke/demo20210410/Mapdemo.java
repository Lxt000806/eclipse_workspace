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
	 * Map�ӿ���ӳ��   key value��ֵ����ʽ��
	 * some key map to value
	 * 
	 * ��Ӧ�ӽӿڣ�
	 *    HashMap, TreeMap
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map m;
		
		//HashMap����ֱ���key��value��������ʽ
		HashMap<String,String> stuMap = new HashMap<>();
			
		//����ֵ
		stuMap.put("s001","mary");//����һ����ֵ��
		stuMap.put("s002","david");
		
		//ȡ��ֵ
		System.out.println(stuMap.get("s001"));
		System.out.println(stuMap.get("s002"));
		
		//��û����ȡĬ��ֵ
		System.out.println(stuMap.getOrDefault("s003", "jenny"));
		
		System.out.println(stuMap.size());
		System.out.println(stuMap.isEmpty());
		System.out.println(stuMap.containsKey("s002"));
		System.out.println(stuMap.containsValue("mary"));
		
		//�޸Ĳ�������ͬ��key����Ḳ��ǰ���
		stuMap.put("s002","kate");
		
		//ɾ������
		stuMap.remove("s001");
		stuMap.remove("s001","mary"); //�߾���ɾ����ͬʱ�������ɾ��
		//���ĳ��key��map��û���ҵ���Ӧ��valueֵ���򷵻ؿ�
		
		//��ͬ��key����ָ����ͬ��value
		String stuname="hunter";
		stuMap.put("s004", stuname);
		stuMap.put("s005", stuname);
		
		//map�ı���
		//HashMap��key��ʹ��Set������ģ��������Ҳ����ظ�
		Set<String> keyList = stuMap.keySet();//����Map���е�key�����ɵļ���
		for(String key:keyList)
			System.out.println(key+"->"+stuMap.get(key));
		
		
	}

}
