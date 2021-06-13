/**
 * 
 */
package com.abc.hrmis.ui;

import java.util.Set;
import java.util.TreeSet;

import com.abc.hrmis.dao.EmployeeDao;
import com.abc.hrmis.dao.EmployeeDaoTxtImpl;
import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.utils.SysUtils;

/**
 * 员工信息全属性格式化输出列表界面
 * @author HY
 *
 */
public class EmpFormattedListUI implements BaseUI {

	/* (non-Javadoc)
	 * @see com.abc.hrmis.ui.BaseUI#setup()
	 */
	@Override
	public void setup() {
		
		EmployeeDao empDao = new EmployeeDaoTxtImpl();
		Set<Employee> empSet = empDao.loadSortedEmps();

		for(Employee emp:empSet)
			emp.empFormattedOut();
		
		SysUtils.pause("\nPress Enter to continue...");
	}

}
