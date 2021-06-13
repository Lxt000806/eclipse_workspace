/**
 * 
 */
package com.abc.hrmis.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.utils.Constants;

/**
 * @author HY
 *
 */
public class EmployeeDaoTxtImpl implements EmployeeDao {

	/* (non-Javadoc)
	 * @see com.abc.hrmis.dao.EmployeeDao#loadEmps()
	 */
	@Override
	public List<Employee> loadEmps() {
		
		BufferedReader reader = null;
		String empInfoStr = null;
		List<Employee> empList = new ArrayList<>();
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.DATE_FILE_PATH)));
			
			while((empInfoStr=reader.readLine())!=null)
				empList.add(Employee.parseEmpByStr(empInfoStr));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return empList;
	}

	@Override
	public Set<Employee> loadSortedEmps() {
		
		return new TreeSet<Employee>(this.loadEmps());
	}
	
	@Override
	public List<Employee> getEmpsByKey(String keyword) {
		List<Employee> empList = new ArrayList<>();;
		
		for(Employee emp:this.loadEmps()) {
			if(emp.toString().toUpperCase().contains(keyword.toUpperCase())) {
				empList.add(emp);
			}
		}
		return empList;
	}

	@Override
	public Employee getEmpByNo(String payrollNo) {
		List<Employee> empList = this.loadEmps();
		for(Employee emp:empList){
			if(emp.getPayrollNo().equals(payrollNo)) {
				return emp;
			}
		}
		return null;
	}

	@Override
	public void addEmp(Employee emp) {
	
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(Constants.DATE_FILE_PATH,true));
			writer.println(emp);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			writer.flush();
			writer.close();
		}
		
	}

	@Override
	public void delEmp(String payrollNo) {
		List<Employee> empList = this.loadEmps();
//		empList.remove(new Employee(payrollNo));
		
		for(int i=0;i<empList.size();i++) {
			if(empList.get(i).toString().equals(this.getEmpByNo(payrollNo).toString())) {
				empList.remove(i);
			}
		}
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(Constants.DATE_FILE_PATH));
			for(Employee emp:empList)
				writer.println(emp);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			writer.flush();
			writer.close();
		}
		
	}

	@Override
	public void updateEmp(Employee newEmp) {

		List<Employee> empList = this.loadEmps();
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(new FileOutputStream(Constants.DATE_FILE_PATH));
			for(Employee emp:empList) {
				if(emp.getPayrollNo().equals(newEmp.getPayrollNo())) {
					
					writer.println(newEmp);									
				}else 
					writer.println(emp);
				
			}
						
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			writer.flush();
			writer.close();
		}
		
		
	}

}
