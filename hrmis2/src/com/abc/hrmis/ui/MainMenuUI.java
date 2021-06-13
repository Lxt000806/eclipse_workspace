/**
 * 
 */
package com.abc.hrmis.ui;

import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.SysUtils;

/**
 * 主菜单界面
 * @author HY
 *
 */
public class MainMenuUI implements BaseUI {


	//静态属性的复杂初始化，用静态块来完成；实例属性的复杂初始化，用构造方法来完成
	private static final String MENU_INFO;
	
	static {
		
		StringBuilder sb = new StringBuilder();
		sb.append("讯通科技 - Employee Information - Main Menu\n")
		  .append("=====================================================\n") 
		  .append("User:"+SysUtils.getLoginedUserNo()+"\n\n")
		  .append("1 - Print All Current Records\n")
		  .append("2 – Print All Current Records (formatted)\n")
		  .append("3 – Print Names and Phone Numbers\r\n")
		  .append("4 – Print Names and Phone Numbers (formatted)\n")
		  .append("5 - Search for specific Record(s)\n")
		  .append("6 - Add New Records\n")
		  .append("7 – Delete Records\n")
		  .append("8 – Update Records\n")
		  .append("9 – Update User Records\n\n")
		  .append("Q - Quit\n\n")
		  .append("Your Selection:");
		
		  MENU_INFO = sb.toString();
	}
	/* (non-Javadoc)
	 * @see com.abc.hrmis.ui.BaseUI#setup()
	 */
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
					SysUtils.runUI(UIType.LIST);
					break;
				case '2':
					SysUtils.runUI(UIType.FORMATTED_LIST);
					break;
				case '3':
					SysUtils.runUI(UIType.SHORT_LIST);
					break;
				case '4':
					SysUtils.runUI(UIType.SHORT_FORMATTED_LIST);
					break;
				case '5':
					SysUtils.runUI(UIType.SEARCH);
					break;
				case '6':
					SysUtils.runUI(UIType.ADD);
					break;
				case '7':
					SysUtils.runUI(UIType.REMOVE);
					break;
				case '8':
					SysUtils.runUI(UIType.UPDATE);
					break;
				case '9':
					SysUtils.runUI(UIType.USER_UPDATE);	
					isContinued = false;
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
		System.exit(0);
	}
	
	/**
	 * 打印菜单
	 */
	private void showMenu(){
		System.out.print(MENU_INFO);
	}
	

}
