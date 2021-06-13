/**
 * 
 */
package edu.mju.stuwork.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.service.StudentQueryHelper;

/**
 * @author joeyang ong
 *
 */
public interface StudentDao {
   void addStu(Student stu);
   List<Student> loadStus();
   List<Student> loadStusByCondition(StudentQueryHelper helper);
   void delStu(int stuNo);
   Student getStuByNo(int stuNo);
   void updateStu(Student stu);
   Map getStuPicByNo(int stuNo);
   
   /**
    * 查询在某查询条件组合下，总共的记录数量
    * @param helper
    * @return
    */
   long cntStuByCondition(StudentQueryHelper helper);
   List<Student> loadScopedStusByCondition(@Param("helper") StudentQueryHelper helper, 
								           @Param("begin") int beginIdx, 
								           @Param("size") int pageSize);
}
