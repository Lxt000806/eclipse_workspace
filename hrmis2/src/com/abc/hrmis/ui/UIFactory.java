/**
 * 
 */
package com.abc.hrmis.ui;

/**
 * @author HY
 *
 */
public class UIFactory {

	private static final UIFactory ME = new UIFactory();
	
	public static UIFactory getInstance() {
		return ME;
	}
	
	private UIFactory() {
		
	}
	
	/**
	 * 根据UI类型，加载UI对象
	 * @param type
	 * @return
	 */
	public BaseUI getUI(UIType type) {
		
		BaseUI ui = null;
		
		if(type.equals(UIType.USER_MENU)) {
			ui = new UserMenuUI();
		}if(type.equals(UIType.LOGIN)) {
			ui = new UserLoginUI();
		}if(type.equals(UIType.REGISTER)) {
			ui = new UserRegisterUI();
		}if(type.equals(UIType.MAIN_MENU)) {
			ui = new MainMenuUI();
		}if(type.equals(UIType.LIST)) {
			ui = new EmpListUI();
		}if(type.equals(UIType.FORMATTED_LIST)) {
			ui = new EmpFormattedListUI();
		}if(type.equals(UIType.SHORT_LIST)) {
			ui = new EmpShortListUI();
		}if(type.equals(UIType.SHORT_FORMATTED_LIST)) {
			ui = new EmpFormattedShortListUI();
		}if(type.equals(UIType.SEARCH)) {
			ui = new EmpSearchUI();
		}if(type.equals(UIType.ADD)) {
			ui = new EmpAddUI();
		}if(type.equals(UIType.REMOVE)) {
			ui = new EmpRemoveUI();
		}if(type.equals(UIType.UPDATE)) {
			ui = new EmpUpdateUI();
		}if(type.equals(UIType.USER_UPDATE)) {
			ui = new UserUpdateUI();
		}
		
		
		return ui;
	}
}
