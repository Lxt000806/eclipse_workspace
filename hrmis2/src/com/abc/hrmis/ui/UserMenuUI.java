/**
 * 
 */
package com.abc.hrmis.ui;

import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.SysUtils;

/**
 * �û��������˵�
 * @author HY
 *
 */
public class UserMenuUI implements BaseUI {

	//��̬���Եĸ��ӳ�ʼ�����þ�̬������ɣ�ʵ�����Եĸ��ӳ�ʼ�����ù��췽�������
	private static final String MENU;
	
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("Ѷͨ�Ƽ� - ��ӭʹ��������Դ����ϵͳ - User Menu\n")
		  .append("=====================================================\n\n") 
		  .append("1 �C Login\n")
		  .append("2 �C Register\n")
		  .append("Q �C Quit\n\n")
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
	 * ��ӡ�˵�
	 */
	private void showMenu(){
		System.out.print(MENU);
	}
	
}
