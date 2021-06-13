/**
 * 
 */
package com.abc.hrmis.ui;

import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.SysUtils;

/**
 * 用户操作主菜单
 * @author HY
 *
 */
public class UserMenuUI implements BaseUI {

	//静态属性的复杂初始化，用静态块来完成；实例属性的复杂初始化，用构造方法来完成
	private static final String MENU;
	
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("讯通科技 - 欢迎使用人力资源管理系统 - User Menu\n")
		  .append("=====================================================\n\n") 
		  .append("1 – Login\n")
		  .append("2 – Register\n")
		  .append("Q – Quit\n\n")
		  .append("Your Selection:");
		
		  MENU = sb.toString();
	}
		
	@Override
	public void setup() {
		
		boolean isContinued = true;
		String entry = null;

		while(isContinued){
			showMenu();
			try{
				entry = SysUtils.getEntry();
				char choice = entry.toUpperCase().charAt(0);
				
				switch(choice){
				case '1':
					SysUtils.runUI(UIType.LOGIN);
					break;
				case '2':
					SysUtils.runUI(UIType.REGISTER);
					break;
				case 'Q':
					isContinued = false;
					break;
				default:
					SysUtils.pause("Invalid code! Press Enter to continue...");
				}
				
			}catch(BlankEntryException e){
				SysUtils.pause("\nNo selection entered. Press Enter to continue...\n");
			}
				
		}
		System.out.println("\nThank you for using HRMIS v1.0");

	}

	/**
	 * 打印菜单
	 */
	private void showMenu(){
		System.out.print(MENU);
	}
	
}
