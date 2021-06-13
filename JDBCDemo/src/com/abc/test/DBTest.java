/**
 * 
 */
package com.abc.test;

import java.sql.Connection;
import java.util.List;

import com.abc.dao.StudentDao;
import com.abc.dao.StudentDaoJDBCImpl;
import com.abc.domain.Student;
import com.abc.utils.DBUtils;

/**
 * @author HY
 *
 */
public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//测试是否连接数据库成功
//		Connection conn = DBUtils.getConn();		
//		System.out.println(conn);
		
		//增加操作
//		Student stu = new Student();
//		stu.setStuNo(125);
//		stu.setStuName("david");
//		stu.setStuAge(13);
//		stu.setStuMark(76.5);
//		
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		stuDao.addStu(stu);
		
		//删除学生
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		stuDao.delStu(124);
		
		//查询所有
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		List<Student> stuList = stuDao.loadStus();
//		
//		for(Student stu:stuList)
//			System.out.println(stu);
		
		
		//根据主键查询学生信息
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		System.out.println(stuDao.getStuByNo(125));
		
		//更新操作
		StudentDao stuDao = new StudentDaoJDBCImpl();
		Student stu = stuDao.getStuByNo(125);
		stu.setStuName("王有才");
		stu.setStuAge(stu.getStuAge()+1);
		
		stuDao.updateStu(stu);

	}

}
