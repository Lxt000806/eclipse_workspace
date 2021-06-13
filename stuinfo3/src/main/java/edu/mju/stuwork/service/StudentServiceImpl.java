/**
 * 
 */
package edu.mju.stuwork.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.stuwork.dao.StudentDao;
import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.utils.Page;

/**
 * @author joeyang ong
 *
 */

@Service
public class StudentServiceImpl implements StudentService {
	
    @Autowired
	private StudentDao stuDao = null;

	/* (non-Javadoc)
	 * @see edu.mju.stuwork.service.StudentService#regStu(edu.mju.stuwork.domain.Student)
	 */
	@Override
	public void regStu(Student stu) {
		stuDao.addStu(stu);

	}

	@Override
	public List<Student> loadStus() {
		return stuDao.loadStus();
	}

	@Override
	public void removeStu(int stuNo) {
		stuDao.delStu(stuNo);
	}

	@Override
	public Student getStuByNo(int stuNo) {
		return stuDao.getStuByNo(stuNo);
	}

	@Override
	public void updateStu(Student stu) {
		stuDao.updateStu(stu);
	}

	@Override
	public byte[] getStuPicByNo(int stuNo) {
		
		Map picMap = stuDao.getStuPicByNo(stuNo);
		
		if(picMap!=null){
			return (byte[])picMap.get("imgBytes");
		}
		
		return null;
		
	}

	@Override
	public List<Student> loadStus(StudentQueryHelper helper) {
		return stuDao.loadStusByCondition(helper);
	}

	@Override
	public Page loadPagedStus(StudentQueryHelper helper, Page page) {
	
		page.setTotalRecNum(stuDao.cntStuByCondition(helper));
		page.setPageContent(stuDao.loadScopedStusByCondition(helper, page.getStartIndex(), page.getPageSize()));
		
		return page;
		
	}

}
