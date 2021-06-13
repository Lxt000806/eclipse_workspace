package edu.mju.stuwork.service;

import java.util.List;
import edu.mju.stuwork.dao.StudentDao;
import edu.mju.stuwork.dao.StudentDaoJDBCImpl;
import edu.mju.stuwork.dao.StudentDaoMybatisImpl;
import edu.mju.stuwork.domain.Student;

public class StudentServiceImpl implements StudentService {

	@Override
	public void regStudent(Student stu) {
		
		StudentDao stuDao = new StudentDaoMybatisImpl();
		stuDao.addStudent(stu);

	}
	
	@Override
    public List<Student> loadStus() {		
		StudentDao stuDao = new StudentDaoJDBCImpl();
		return stuDao.loadStus();
		
	}

	@Override
	public void removeStu(int stuNo) {
		StudentDao stuDao = new StudentDaoMybatisImpl();
		stuDao.delStudent(stuNo);
		
	}

	@Override
	public Student getStuByNo(int stuNo) {
		StudentDao stuDao = new StudentDaoJDBCImpl();
		return stuDao.getStuByNo(stuNo);
	}

	@Override
	public void updateStu(Student stu) {
		StudentDao stuDao = new StudentDaoMybatisImpl();
		stuDao.updateStu(stu);
		
	}

}
