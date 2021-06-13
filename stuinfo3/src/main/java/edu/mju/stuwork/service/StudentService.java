/**
 * 
 */
package edu.mju.stuwork.service;

import java.util.List;

import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.utils.Page;

/**
 * @author joeyang ong
 *
 */
public interface StudentService {
	
	void regStu(Student stu);
	List<Student> loadStus();
	List<Student> loadStus(StudentQueryHelper helper);
	void removeStu(int stuNo);
	Student getStuByNo(int stuNo);
	void updateStu(Student stu);
	byte[] getStuPicByNo(int stuNo);
	Page loadPagedStus(StudentQueryHelper helper,Page page);

}
