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
	* ģ��D�ľ��崦������У�������ģ��C�Ľӿ�        
	*/       
	public static String operate(String s1, String s2) {              
		// s1 ���滻��Ŀ�괮       
		// s2 ԭ�� 
		if(s1.length()>s2.length())
			return moduleC.operate(s1, s2);  
		else
			return moduleC.operate(s2, s1);  
		
	}

}
