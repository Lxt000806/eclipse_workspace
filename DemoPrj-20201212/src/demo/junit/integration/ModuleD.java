/**
 * 
 */
package demo.junit.integration;

/**
 * @author HY
 *
 */
public class ModuleD {

	
	private static ModuleC moduleC = new ModuleC();              
	public void setModuleC(ModuleC moduleC) {              
		this.moduleC = moduleC;       
	}       
	/**        
	* 模块D的具体处理操作中，调用了模块C的接口        
	*/       
	public static String operate(String s1, String s2) {              
		// s1 待替换的目标串       
		// s2 原串 
		if(s1.length()>s2.length())
			return moduleC.operate(s1, s2);  
		else
			return moduleC.operate(s2, s1);  
		
	}

}
