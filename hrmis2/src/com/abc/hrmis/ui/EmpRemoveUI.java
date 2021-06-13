/**
 * 
 */
package com.abc.hrmis.ui;

import com.abc.hrmis.dao.EmployeeDao;
import com.abc.hrmis.dao.EmployeeDaoTxtImpl;
import com.abc.hrmis.domain.Employee;
import com.abc.hrmis.exception.BlankEntryException;
import com.abc.hrmis.utils.SysUtils;

/**
 * 员工信息删除界面
 * @author HY
 *
 */
public class EmpRemoveUI implements BaseUI {

	/* (non-Javadoc)
	 * @see com.abc.hrmis.ui.BaseUI#setup()
	 */
	@Override
	public void setup() {
		EmployeeDao empdao = new EmployeeDaoTxtImpl();
		while(true) {			
			try {
				System.out.print("Enter employee’s 3 digit payroll number to enable file deletion:");
				
				String entry = SysUtils.getEntry();
				int num = Integer.parseInt(entry);
				Employee emp = empdao.getEmpByNo(entry);
				
				if(emp!=null) {
					emp.empOut();
					
					System.out.print("Confirm record deletion, (y)es or (n)o,");	
					
					if(SysUtils.getEntry().equalsIgnoreCase("y")) {
						empdao.delEmp(entry);
						
						System.out.println("Record deleted");
						System.out.print("Delete another? (y)es or (n)o,");
						
						if(SysUtils.getEntry().equalsIgnoreCase("y"))
							continue;
					}						
				}else {
					System.out.printf("Employee record for %s not found!\n\n",entry);
					continue;
				}
				
				break;
				
			}catch(BlankEntryException e) {
				SysUtils.pause("No payroll number entered – try again");
			}catch(NumberFormatException e) {
				SysUtils.pause("Payroll number can contain only numerical characters");
			}
			
		}
		
		SysUtils.pause("\nPress Enter to continue...");
	}

}
