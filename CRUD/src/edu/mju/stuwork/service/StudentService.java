 package edu.mju.stuwork.service;

import java.util.List;

import edu.mju.stuwork.domain.Student;

public interface StudentService {

	void regStudent(Student stu);
	List<Student> loadStus();
	void removeStu(int stuNo);
	Student getStuByNo(int stuNo);
	void updateStu(Student stu);
}
