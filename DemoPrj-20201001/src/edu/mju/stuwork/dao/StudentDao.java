package edu.mju.stuwork.dao;

import java.util.List;

import edu.mju.stuwork.domain.Student;

/**
 * dao:data access object
 * 负责学生实体的所有的增删改查操作(持久化操作)
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
