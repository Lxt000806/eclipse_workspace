/**
 * 
 */
package com.abc.hrmis.ui;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.abc.hrmis.dao.EmployeeDao;
import com.abc.hrmis.dao.EmployeeDaoTxtImpl;
import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.Constants;
import com.abc.hrmis.utils.SysUtils;

/**
 * 员工信息新增界面
 * @author HY
 *
 */
public class EmpAddUI implements BaseUI {

	EmployeeDao empdao = new EmployeeDaoTxtImpl();
	
	@Override
	public void setup() {
		
		Employee emp = new Employee();
		while(true) {
			emp.setPayrollNo(this.getPayrollNo());
			emp.setTelephoneCode(this.getPhoneCode());
			emp.setLastname(this.getLastName());
			emp.setFirstname(this.getFirstName());
			emp.setInitial(this.getInitial());
			emp.setDeptNo(this.getDeptNo());
			emp.setJobTitle(this.getJobTitle());
			emp.setHiringDate(this.getHiringDate());
			
			empdao.addEmp(emp);
			System.out.println("Record Saved ");
			
			System.out.print("Add another employee record? (y)es or (n)o,");
			String entry = SysUtils.getEntry(true);
		    if(!SysUtils.isBlankStr(entry)  && entry.toUpperCase().charAt(0)=='Y') 
		    	continue;
		    else
		    	break;
		}
		
		SysUtils.pause("\nPress Enter to continue...");
	}
	
	/**
	 * 员工工号的录入
	 * @return
	 */
	public String getPayrollNo() {
		
        String payrollNo=null;
		
		while(true){
			
			System.out.print("Enter employee 3 digit payroll number:");
			
			try{
			  //1. 空输入检测
		  	  payrollNo = SysUtils.getEntry();
		  	  //2. 长度检测
		  	  if(payrollNo.trim().length()!=3) {
		  		  SysUtils.pause("Payroll number's length must be 3 characters!");
		  	      continue;
		  	  }
		  	  else
		  		  Integer.parseInt(payrollNo.trim()); //全数字检测
		  	  //3.是否已经存在
		  	  EmployeeDao empDao = new EmployeeDaoTxtImpl();
		  	  Employee emp = empDao.getEmpByNo(payrollNo);
		  	  
		  	  if(emp!=null){
		  		  SysUtils.pause("The payrollNo existed now, please enter the another one!");
		  	      continue;
		  	  }
		  	  
		  	  break;
		  	  
			}catch(BlankEntryException e){
				SysUtils.pause("No payroll number entered – try again");	
			}catch(NumberFormatException e){
				SysUtils.pause("Payroll number can contain only numerical characters");
			}
			
		}
		
		return payrollNo;
				
		
	}
	
	/**
	 * 员工电话号码的录入
	 * @return
	 */
	public String getPhoneCode() {
		String phoneCode = null;
		String pattern="0[2-8]-[1-9][0-9]{7}"; //正则表达式匹配模板
		
		while(true){
			try{
				
			  System.out.print("Enter Phone Number (02-12345678):");	
			  phoneCode=SysUtils.getEntry();
			  
			  boolean checkResult = Pattern.matches(pattern, phoneCode);  
			  
			  if(!checkResult){
				  System.out.println("Invalid phone number – try again");
				  continue;
			  }
			  
			  break;
			  
			}catch(BlankEntryException e){
			  SysUtils.pause("No phone number entered – try again");	
			}
		}
		
		return phoneCode;
		
	}
	
	/**
	 * 员工lastname的录入
	 * @return
	 */
	public String getLastName() {
		String entry = null;
		String pattern="[a-zA-Z ]{1,}";
		
        while(true){
			
			System.out.print("Enter Last Name:");
			try{
			  entry = SysUtils.getEntry();
			  if(!Pattern.matches(pattern, entry))
				  System.out.println("Last name can contain only alphabetical characters and spaces ");
			  else
				  break;
			}catch(BlankEntryException e){
			  System.out.println("No last name entered – try again");
			}
			
		}
		
		return entry;
	}
	
	/**
	 * 员工firstname的录入
	 * @return
	 */
	public String getFirstName() {
		String entry = null;
		String pattern="[a-zA-Z ]{1,}";
		
		while(true){
			
			System.out.print("Enter first Name:");
			try{
			  entry = SysUtils.getEntry();
			  if(!Pattern.matches(pattern, entry))
				  System.out.println("First name can contain only alphabetical characters and spaces ");
			  else
				  break;
			}catch(BlankEntryException e){
			  System.out.println("No First name entered – try again");
			}
			
		}
		
		return entry;
	}
	
	/**
	 * 员工initial的录入
	 * @return
	 */
	public String getInitial() {
		
		String entry = null;
		String pattern="[a-zA-Z ]{1,}";
		
		while(true){
			
			System.out.print("Enter Middle Init:");
			try{
				entry = SysUtils.getEntry();
				if(!Pattern.matches(pattern, entry))
					System.out.println("Middle Init can contain only alphabetical characters and spaces ");
				else
					break;
			}catch(BlankEntryException e){
				System.out.println("No Middle Init entered – try again");
			}
			
		}
		
		return entry;
	}
	
	/**
	 * 部门编号的录入
	 * @return
	 */
	public int getDeptNo() {
		
		String entry = null;
		String pattern="[0-9]{1,}";
		
		while(true){
			
			System.out.print("Enter Dept #:");
			try{
			  entry = SysUtils.getEntry();
			  if(!Pattern.matches(pattern, entry))
				  System.out.println(" Dept # can contain only digits with no spaces");
			  else
				  break;
			}catch(BlankEntryException e){
			  System.out.println("No Dept # entered – try again");
			}
			
		}
		
		return Integer.parseInt(entry);
	}
	
	/**
	 * 员工职务的录入
	 * @return
	 */
	public String getJobTitle() {
		
		String entry = null;
		String pattern="[a-zA-Z ]{1,}";
		
		while(true){
			
			System.out.print("Enter Job Title:");
			try{
			  entry = SysUtils.getEntry();
			  if(!Pattern.matches(pattern, entry))
				  System.out.println("First name can contain only alphabetical characters and spaces ");
			  else
				  break;
			}catch(BlankEntryException e){
			  System.out.println("No Job title entered – try again");
			}
			
		}
		
		return entry;
		
	}
	
	/**
	 * 员工入司时间的录入
	 * @return
	 */
	public Date getHiringDate() {
		
		String entry = null;
		Date date = null;
		
		while(true){
			
			System.out.print("Enter Date Hired (dd-mm-yyyy):");
			try{
				entry = SysUtils.getEntry();
				Constants.SDF.setLenient(false);//容错开关，字符串输入不规范会报错
				date = Constants.SDF.parse(entry);
				break;
			}catch(BlankEntryException e){
				System.out.println("No date hired entered – try again");
			}catch(ParseException e){
				System.out.println("Invalid Date Hired");	
			}
			
		}
		
		return date;
		
	}

}
