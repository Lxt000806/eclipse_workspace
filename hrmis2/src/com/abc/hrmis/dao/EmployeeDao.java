/**
 * 
 */
package com.abc.hrmis.dao;

import java.util.List;
import java.util.Set;

import com.abc.hrmis.domain.Employee;

/**
 * Dao: Data Access object ����������ĳ��ҵ��ʵ�����ɾ�Ĳ����
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
