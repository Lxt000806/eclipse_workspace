/**
 * 
 */
package demo.junit.integration;

/**
 * @author HY
 *
 */
public class ModuleC {

	/*** 实现把 s1 中包含的 s2 替换后的内容返回的功能        
	* @param s1 字符串1
	* @param s2 字符串2        
	* @param 返回处理的结果        
	*/       
	public String operate(String s1, String s2) {      
		String str = s1.replace(s2,"");
		return str;      
	}

}
