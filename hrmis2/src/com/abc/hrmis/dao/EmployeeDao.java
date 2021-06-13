/**
 * 
 */
package com.abc.hrmis.dao;

import java.util.List;
import java.util.Set;

import com.abc.hrmis.domain.Employee;

/**
 * Dao: Data Access object 其往往负责某种业务实体的增删改查操作
 * @author HY
 *
 */
public interface EmployeeDao {

	List<Employee> loadEmps();
	Set<Employee> loadSortedEmps();
	List<Employee> getEmpsByKey(String keyword);
	Employee getEmpByNo(String payrollNo);
	void addEmp(Employee emp);
	void delEmp(String payrollNo);
	void updateEmp(Employee emp);
	
}
