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
 * Ա����Ϣ��������
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
	 * Ա�����ŵ�¼��
	 * @return
	 */
	public String getPayrollNo() {
		
        String payrollNo=null;
		
		while(true){
			
			System.out.print("Enter employee 3 digit payroll number:");
			
			try{
			  //1. ��������
		  	  payrollNo = SysUtils.getEntry();
		  	  //2. ���ȼ��
		  	  if(payrollNo.trim().length()!=3) {
		  		  SysUtils.pause("Payroll number's length must be 3 characters!");
		  	      continue;
		  	  }
		  	  else
		  		  Integer.parseInt(payrollNo.trim()); //ȫ���ּ��
		  	  //3.�Ƿ��Ѿ�����
		  	  EmployeeDao empDao = new EmployeeDaoTxtImpl();
		  	  Employee emp = empDao.getEmpByNo(payrollNo);
		  	  
		  	  if(emp!=null){
		  		  SysUtils.pause("The payrollNo existed now, please enter the another one!");
		  	      continue;
		  	  }
		  	  
		  	  break;
		  	  
			}catch(BlankEntryException e){
				SysUtils.pause("No payroll number entered �C try again");	
			}catch(NumberFormatException e){
				SysUtils.pause("Payroll number can contain only numerical characters");
			}
			
		}
		
		return payrollNo;
				
		
	}
	
	/**
	 * Ա���绰�����¼��
	 * @return
	 */
	public String getPhoneCode() {
		String phoneCode = null;
		String pattern="0[2-8]-[1-9][0-9]{7}"; //������ʽƥ��ģ��
		
		while(true){
			try{
				
			  System.out.print("Enter Phone Number (02-12345678):");	
			  phoneCode=SysUtils.getEntry();
			  
			  boolean checkResult = Pattern.matches(pattern, phoneCode);  
			  
			  if(!checkResult){
				  System.out.println("Invalid phone number �C try again");
				  continue;
			  }
			  
			  break;
			  
			}catch(BlankEntryException e){
			  SysUtils.pause("No phone number entered �C try again");	
			}
		}
		
		return phoneCode;
		
	}
	
	/**
	 * Ա��lastname��¼��
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
			  System.out.println("No last name entered �C try again");
			}
			
		}
		
		return entry;
	}
	
	/**
	 * Ա��firstname��¼��
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
			  System.out.println("No First name entered �C try again");
			}
			
		}
		
		return entry;
	}
	
	/**
	 * Ա��initial��¼��
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
				System.out.println("No Middle Init entered �C try again");
			}
			
		}
		
		return entry;
	}
	
	/**
	 * ���ű�ŵ�¼��
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
			  System.out.println("No Dept # entered �C try again");
			}
			
		}
		
		return Integer.parseInt(entry);
	}
	
	/**
	 * Ա��ְ���¼��
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
			  System.out.println("No Job title entered �C try again");
			}
			
		}
		
		return entry;
		
	}
	
	/**
	 * Ա����˾ʱ���¼��
	 * @return
	 */
	public Date getHiringDate() {
		
		String entry = null;
		Date date = null;
		
		while(true){
			
			System.out.print("Enter Date Hired (dd-mm-yyyy):");
			try{
				entry = SysUtils.getEntry();
				Constants.SDF.setLenient(false);//�ݴ��أ��ַ������벻�淶�ᱨ��
				date = Constants.SDF.parse(entry);
				break;
			}catch(BlankEntryException e){
				System.out.println("No date hired entered �C try again");
			}catch(ParseException e){
				System.out.println("Invalid Date Hired");	
			}
			
		}
		
		return date;
		
	}

}
