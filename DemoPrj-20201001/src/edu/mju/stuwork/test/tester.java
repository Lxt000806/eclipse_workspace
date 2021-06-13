package edu.mju.stuwork.test;
import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.service.StudentService;
import edu.mju.stuwork.service.StudentServiceImpl;
public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(DBUtils.getConn());

		StudentService stuService = new StudentServiceImpl();
		for(Student stu:stuService.loadStus())
			System.out.println(stu);
	}

}
