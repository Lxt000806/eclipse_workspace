/**
 * 
 */
package com.abc.hrmis.domain;

import java.text.ParseException;
import java.util.Date;

import com.abc.hrmis.utils.Constants;

/**
 * Ա����
 * @author HY
 *
 */
public class Employee extends ValueObject implements Comparable<Employee> {

	/** ���� */
	private String payrollNo;
	/** �绰���� */
	private String telephoneCode;
	/** lastname */
	private String lastname;
	/** firstname */
	private String firstname;
	/** initial */
	private String initial;
	/** ���ű�� */
	private int deptNo;
	/** ְ�� */
	private String jobTitle;
	/** ��˾ʱ�� */
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
	 * ��ȡԱ����Ϣ��������Ա������
	 * @param empInfoStr
	 * @return
	 */
	public static Employee parseEmpByStr(String empInfoStr) {
		String[] sections = empInfoStr.split("\\:"); //����:���зָ��������ַ����б�
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
	 * Ա��ȫ�������
	 */
	public void empOut() {
		System.out.println(this); //��ӡtoString()����
	}
	
	/**
	 * Ա��ȫ���Ը�ʽ�����
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
	 * Ա�������������
	 */
	public void empShortOut() {
		System.out.println(String.format("%s:%s:%s", this.lastname,this.firstname,this.telephoneCode));
	}
	
	/**
	 * Ա���������Ը�ʽ�����
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
