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
		
		//�����Ƿ��������ݿ�ɹ�
//		Connection conn = DBUtils.getConn();		
//		System.out.println(conn);
		
		//���Ӳ���
//		Student stu = new Student();
//		stu.setStuNo(125);
//		stu.setStuName("david");
//		stu.setStuAge(13);
//		stu.setStuMark(76.5);
//		
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		stuDao.addStu(stu);
		
		//ɾ��ѧ��
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		stuDao.delStu(124);
		
		//��ѯ����
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		List<Student> stuList = stuDao.loadStus();
//		
//		for(Student stu:stuList)
//			System.out.println(stu);
		
		
		//����������ѯѧ����Ϣ
//		StudentDao stuDao = new StudentDaoJDBCImpl();
//		System.out.println(stuDao.getStuByNo(125));
		
		//���²���
		StudentDao stuDao = new StudentDaoJDBCImpl();
		Student stu = stuDao.getStuByNo(125);
		stu.setStuName("���в�");
		stu.setStuAge(stu.getStuAge()+1);
		
		stuDao.updateStu(stu);

	}

}
