/**
 * 
 */
package com.abc.hrmis.ui;

import java.util.Set;

import com.abc.hrmis.dao.EmployeeDao;
import com.abc.hrmis.dao.EmployeeDaoTxtImpl;
import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.utils.SysUtils;

/**
 * Ա����Ϣ�������Ը�ʽ�������������
 * @author HY
 *
 */
public class EmpFormattedShortListUI implements BaseUI {

	/* (non-Javadoc)
	 * @see com.abc.hrmis.ui.BaseUI#setup()
	 */
	@Override
	public void setup() {
		
		EmployeeDao empDao = new EmployeeDaoTxtImpl();
		Set<Employee> empSet = empDao.loadSortedEmps();

		for(Employee emp:empSet)
			emp.empFormattedShortOut();
		
		SysUtils.pause("\nPress Enter to continue...");
	}

}
