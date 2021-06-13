/**
 * 
 */
package edu.mju.stuwork.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.utils.MybatisUtils;

/**
 * @author joeyang ong
 *
 */
public class StudentDaoMybatisImpl implements StudentDao {

	/* (non-Javadoc)
	 * @see edu.mju.stuwork.dao.StudentDao#addStudent(edu.mju.stuwork.domain.Student)
	 */
	@Override
	public void addStudent(Student stu) {
		
		SqlSession session = MybatisUtils.getSession();
		
		// 演示新增操作		
		session.insert("edu.mju.stuwork.StudentMapper.addStudent", stu);
		session.commit();
				
		MybatisUtils.closeSession(session);
		

	}

	/* (non-Javadoc)
	 * @see edu.mju.stuwork.dao.StudentDao#loadStus()
	 */
	@Override
	public List<Student> loadStus() {

		SqlSession session = MybatisUtils.getSession();
		
		List<Student>  stuList = session.selectList("edu.mju.stuwork.StudentMapper.loadAll");
		
		session.commit();
	    MybatisUtils.closeSession(session);
	    
		return stuList;
	}

	/* (non-Javadoc)
	 * @see edu.mju.stuwork.dao.StudentDao#delStudent(int)
	 */
	@Override
	public void delStudent(int stuNo) {
        SqlSession session = MybatisUtils.getSession();
		
		// 演示删除操作		
		session.delete("edu.mju.stuwork.StudentMapper.delStudent", stuNo);
		session.commit();
				
		MybatisUtils.closeSession(session);
	}

	/* (non-Javadoc)
	 * @see edu.mju.stuwork.dao.StudentDao#getStuByNo(int)
	 */
	@Override
	public Student getStuByNo(int stuNo) {

		SqlSession session = MybatisUtils.getSession();
		
		Student stu = session.selectOne("edu.mju.stuwork.StudentMapper.getStuByNo",stuNo);
		
		session.commit();		
		
		return stu;
	}

	@Override
	public void updateStu(Student stu) {
        SqlSession session = MybatisUtils.getSession();
		
		session.update("edu.mju.stuwork.StudentMapper.updateStu",stu);
		session.commit();
		
		MybatisUtils.closeSession(session);
	}

}
