/**
 * 
 */
package com.abc.dao;

import java.util.List;

import com.abc.domain.Student;

/**
 * @author HY
 *
 */
public interface StudentDao {

	void addStu(Student stu);
	void delStu(int stuNo);
	List<Student> loadStus();
	Student getStuByNo(int stuNo);
	void updateStu(Student stu);
}
