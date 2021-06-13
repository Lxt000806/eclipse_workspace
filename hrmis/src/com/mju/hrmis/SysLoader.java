/**
 * 
 */
package com.mju.hrmis;

import com.mju.hrmis.ui.BaseUI;
import com.mju.hrmis.ui.MainMenuUI;

/**
 * @author HY
 *
 */
public class SysLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BaseUI ui = new MainMenuUI();
		ui.setup();

	}

}
