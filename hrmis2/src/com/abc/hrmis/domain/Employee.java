/**
 * 
 */
package com.abc.hrmis.domain;

import java.text.ParseException;
import java.util.Date;

import com.abc.hrmis.utils.Constants;

/**
 * 员工类
 * @author HY
 *
 */
public class Employee extends ValueObject implements Comparable<Employee> {

	/** 工号 */
	private String payrollNo;
	/** 电话号码 */
	private String telephoneCode;
	/** lastname */
	private String lastname;
	/** firstname */
	private String firstname;
	/** initial */
	private String initial;
	/** 部门编号 */
	private int deptNo;
	/** 职务 */
	private String jobTitle;
	/** 入司时间 */
	private Date hiringDate;

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String payrollNo) {
		super();
		this.payrollNo = payrollNo;
	}
	public String getPayrollNo() {
		return payrollNo;
	}

	public void setPayrollNo(String payrollNo) {
		this.payrollNo = payrollNo;
	}

	public String getTelephoneCode() {
		return telephoneCode;
	}

	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	@Override
	public int compareTo(Employee otherEmp) {
		// TODO Auto-generated method stub
		return this.payrollNo.compareTo(otherEmp.payrollNo);
	}

	/**
	 * 读取员工信息串，生成员工对象
	 * @param empInfoStr
	 * @return
	 */
	public static Employee parseEmpByStr(String empInfoStr) {
		String[] sections = empInfoStr.split("\\:"); //根据:进行分隔，生成字符串列表
		Employee emp = new Employee();
		
		emp.setPayrollNo(sections[0]);
		emp.setTelephoneCode(sections[1]);
		emp.setLastname(sections[2]);
		emp.setFirstname(sections[3]);
		emp.setInitial(sections[4]);
		emp.setDeptNo(Integer.parseInt(sections[5]));
		emp.setJobTitle(sections[6]);
		
		try {
			Date hiringDate=Constants.SDF.parse(sections[7]);
			emp.setHiringDate(hiringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}
	
	
	/**
	 * 员工全属性输出
	 */
	public void empOut() {
		System.out.println(this); //打印toString()方法
	}
	
	/**
	 * 员工全属性格式化输出
	 */
	public void empFormattedOut() {
		System.out.printf("%-13s%-12s%-3s%-5s%-13s%d %-18s%-12s\n",this.lastname
											  ,this.firstname
											  ,this.initial
											  ,this.payrollNo
											  ,this.telephoneCode
											  ,this.deptNo
											  ,this.jobTitle
											  ,Constants.SDF.format(this.hiringDate));
	}
	
	/**
	 * 员工部分属性输出
	 */
	public void empShortOut() {
		System.out.println(String.format("%s:%s:%s", this.lastname,this.firstname,this.telephoneCode));
	}
	
	/**
	 * 员工部分属性格式化输出
	 */
	public void empFormattedShortOut() {
		System.out.println(String.format("%-13s%-12s%-13s", this.lastname,this.firstname,this.telephoneCode));
	}
	
	
	@Override
	public String toString() {
		
		return String.format("%s:%s:%s:%s:%s:%d:%s:%s",this.payrollNo
													  ,this.telephoneCode
													  ,this.lastname
													  ,this.firstname
													  ,this.initial
													  ,this.deptNo
													  ,this.jobTitle
													  ,Constants.SDF.format(this.hiringDate));
	}
}
