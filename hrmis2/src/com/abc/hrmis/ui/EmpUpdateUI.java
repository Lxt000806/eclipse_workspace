/**
 * 
 */
package com.abc.hrmis.ui;

import java.text.ParseException;
import java.util.Date;

import com.abc.hrmis.dao.EmployeeDao;
import com.abc.hrmis.dao.EmployeeDaoTxtImpl;
import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.Constants;
import com.abc.hrmis.utils.SysUtils;

/**
 * Ա����Ϣ�޸Ľ���
 * @author HY
 *
 */
public class EmpUpdateUI implements BaseUI {

	@Override
	public void setup() {
		EmployeeDao empdao = new EmployeeDaoTxtImpl();
		while(true) {
			try {
				
				System.out.print("Employee 3 digit payroll number:");
				Employee emp = empdao.getEmpByNo(SysUtils.getEntry());
				if(emp!=null) {
					emp.empOut();
				}else {
					SysUtils.pause("not found");continue;
				}
				
				this.printIndexMenu(emp.getPayrollNo());
				Employee newEmp = getUpdateEmp(emp);
								
				System.out.print("Are you sure update? (y)es or (n)o,");
				if(SysUtils.getEntry().equalsIgnoreCase("y")) {
					empdao.updateEmp(newEmp);
					System.out.println("update sucess!");
				}
				
				break;
			}catch(BlankEntryException e) {
				SysUtils.pause("No entered �C try again");
			}
		
		}
		
		SysUtils.pause("\nPress Enter to continue...");

	}
	
	/**
	 * ��ӡԱ���޸ĵ�ѡ�����
	 * @param payrollNo
	 */
	private void printIndexMenu(String payrollNo) {
		StringBuilder sb = new StringBuilder();
		sb.append(" ��ѡ��Ҫ�޸Ĳ�������Ӧ�����ֵ \n")
		  .append("=====================================================\n\n") 
		  .append("payrollNo:"+payrollNo+"\n\n")
		  .append("1 �C telephoneCode\n")
		  .append("2 �C lastname\r\n")
		  .append("3 �C firstname\n")
		  .append("4 - initial\n")
		  .append("5 - deptNo\n")
		  .append("6 �C jobTitle\n")
		  .append("7 - hiringDate\n")
		  .append("Q - Quit\n\n");
		
		System.out.print(sb);
	}
	
	/**
	 * ��ȡ�޸ĺ��Ա������
	 * @param emp
	 * @return
	 */
	private Employee getUpdateEmp(Employee emp) {
		boolean isContinued = true;
		String entry = null;

		while(isContinued){
			try{
				System.out.print("Your Selection:");			
				entry = SysUtils.getEntry();
				char choice = entry.toUpperCase().charAt(0);
				
				switch(choice){
				case '1':
					emp.setTelephoneCode(new EmpAddUI().getPhoneCode());
					break;
				case '2':
					emp.setLastname(new EmpAddUI().getLastName());
					break;
				case '3':
					emp.setFirstname(new EmpAddUI().getFirstName());
					break;
				case '4':
					emp.setInitial(new EmpAddUI().getInitial());
					break;
				case '5':
					emp.setDeptNo(new EmpAddUI().getDeptNo());
					break;
				case '6':
					emp.setJobTitle(new EmpAddUI().getJobTitle());
					break;
				case '7':
					emp.setHiringDate(new EmpAddUI().getHiringDate());
					break;
				case 'Q':
					isContinued = false;
					break;
				default:
					SysUtils.pause("Invalid code! Press Enter to continue...");
				}
				
			}catch(BlankEntryException e){
				SysUtils.pause("\nNo selection entered. Press Enter to continue...\n");
			}
				
		}
						
		return emp;
	}

}
