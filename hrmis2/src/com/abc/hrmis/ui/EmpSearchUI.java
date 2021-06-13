/**
 * 
 */
package com.abc.hrmis.ui;

import java.util.List;
import java.util.Scanner;

import com.abc.hrmis.dao.EmployeeDao;
import com.abc.hrmis.dao.EmployeeDaoTxtImpl;
import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.SysUtils;

/**
 * 员工信息查询界面
 * @author HY
 *
 */
public class EmpSearchUI implements BaseUI {

	/* (non-Javadoc)
	 * @see com.abc.hrmis.ui.BaseUI#setup()
	 */
	@Override
	public void setup() {
		
		EmployeeDao empDao = new EmployeeDaoTxtImpl();
		
		while(true) {		
			try {
				System.out.print("Enter keyword:");
				String keyword = SysUtils.getEntry();
				
				List<Employee> empList = empDao.getEmpsByKey(keyword);
				if(empList!=null) {
					for(Employee emp:empList) {
						emp.empOut();
					}
				}else {
					System.out.println(String.format("Keyword – %s - not found", keyword));
				}
												
				break;	
				
			}catch(BlankEntryException e) {
				SysUtils.pause("No keyword entered – try again…");
			}
								
		}
		
		SysUtils.pause("\nPress Enter to continue...");	
		
	}

}
