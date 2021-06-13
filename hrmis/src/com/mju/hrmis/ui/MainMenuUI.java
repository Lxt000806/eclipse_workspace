package com.mju.hrmis.ui;

import com.mju.hrmis.exception.BlankEntryException;
import com.mju.hrmis.utils.SysUtils;

/**
 * 主菜单界面
 * @author HY
 *
 */
public class MainMenuUI implements BaseUI {

	private static final String MENU_INFO;
	
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("讯通科技 - Employee Information - Main Menu\n")
		  .append("=====================================================\n\n") 
		  .append("1 - Print All Current Records\n")
		  .append("2 – Print All Current Records (formatted)\n")
		  .append("3 – Print Names and Phone Numbers\r\n")
		  .append("4 – Print Names and Phone Numbers (formatted)\n")
		  .append("5 - Search for specific Record(s)\n")
		  .append("6 - Add New Records\n")
		  .append("7 – Delete Records\n\n")
		  .append("Q - Quit\n\n")
		  .append("Your Selection:");
		
		  MENU_INFO = sb.toString();
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub

		boolean isContinued  = true;
		String entry = null;
		
		while(isContinued) {
			
			showMenu();
			try {
				entry = SysUtils.getEntry();
			}catch(BlankEntryException e) {
				System.out.println("\nNo selection entered.Press Enter to continue...\n");
			}
		}
		
	}
	
	/**
	 * 打印菜单
	 */
	private void showMenu() {
		System.out.println(MENU_INFO);
	}

}
