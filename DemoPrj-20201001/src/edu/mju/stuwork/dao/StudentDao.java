package edu.mju.stuwork.dao;

import java.util.List;

import edu.mju.stuwork.domain.Student;

/**
 * dao:data access object
 * ����ѧ��ʵ������е���ɾ�Ĳ����(�־û�����)
 * @author HY
 *
 */
public interface StudentDao {
	void addStudent(Student stu);
	List<Student> loadStus();
	void delStudent(int stuNo);
	Student getStuByNo(int stuNo);
	void updateStu(Student stu);
}
