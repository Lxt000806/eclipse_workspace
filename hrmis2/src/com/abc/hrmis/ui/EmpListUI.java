/**
 * 
 */
package com.abc.hrmis.ui;

import java.util.List;

import com.abc.hrmis.dao.EmployeeDao;
import com.abc.hrmis.dao.EmployeeDaoTxtImpl;
import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.utils.SysUtils;

/**
 * 员工信息全属性列表界面
 * @author HY
 *
 */
public class EmpListUI implements BaseUI {

	/* (non-Javadoc)
	 * @see com.abc.hrmis.ui.BaseUI#setup()
	 */
	@Override
	public void setup() {
		
		EmployeeDao empDao = new EmployeeDaoTxtImpl();
		List<Employee> empList = empDao.loadEmps();
		
		for(Employee emp:empList)
			emp.empOut();
		
		SysUtils.pause("\nPress Enter to continue...");
	}

}
