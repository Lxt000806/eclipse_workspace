/**
 * 
 */
package demo.junit.integration;

/**
 * @author HY
 *
 */
public class ModuleC {

	/*** ʵ�ְ� s1 �а����� s2 �滻������ݷ��صĹ���        
	* @param s1 �ַ���1
	* @param s2 �ַ���2        
	* @param ���ش���Ľ��        
	*/       
	public String operate(String s1, String s2) {      
		String str = s1.replace(s2,"");
		return str;      
	}

}
