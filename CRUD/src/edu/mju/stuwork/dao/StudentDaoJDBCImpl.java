/**
 * 
 */
package edu.mju.stuwork.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.utils.DBUtils;

/**
 * @author HY
 *
 */
public class StudentDaoJDBCImpl implements StudentDao {

	private static final String SQL_ADD = "insert into tbl_student value(?,?,?,?)";
	private static final String SQL_REMOVE_BYID = "delete from tbl_student where stu_no=?";
	private static final String SQL_LOAD_STUS = "select * from tbl_student order by stu_no desc";
	private static final String SQL_GET_STUBYID = "select * from tbl_student where stu_no=?";
	private static final String SQL_UPDATE_STU = "update tbl_student set stu_name=?,stu_age=?,stu_mark=? where stu_no=?";
	
	/* (non-Javadoc)
	 * @see com.abc.dao.StudentDao#addStu(com.abc.domain.Student)
	 */
	@Override
	public void addStu(Student stu) {
		
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		
		try {
			//对象转成记录，存放到关系型数据库
			pstmt = conn.prepareStatement(SQL_ADD);
			pstmt.setInt(1, stu.getStuNo());
			pstmt.setString(2, stu.getStuName());
			pstmt.setInt(3, stu.getStuAge());
			pstmt.setDouble(4, stu.getStuMark());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.releaseRes(conn,pstmt,null);
		}
		

	}

	@Override
	public void delStu(int stuNo) {
		
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(SQL_REMOVE_BYID);
			pstmt.setInt(1, stuNo);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.releaseRes(conn,pstmt,null);
		}
		
	}

	@Override
	public List<Student> loadStus() {
		
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		List<Student> stuList= new ArrayList<Student>();
		
		try {
			pstmt = conn.prepareStatement(SQL_LOAD_STUS);
			rest = pstmt.executeQuery(); //装满记录的容器，叫做结果集
			
			//读写指针向下移动一行
			while(rest.next()) {
				//解析记录封装成对象
				Student stu = new Student();
				stu.setStuNo(rest.getInt("stu_no"));
				stu.setStuName(rest.getString("stu_name"));
				stu.setStuAge(rest.getInt("stu_age"));
				stu.setStuMark(rest.getDouble("stu_mark"));
				
				stuList.add(stu);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.releaseRes(conn,pstmt,rest);
		}
		
		return stuList;
	}

	@Override
	public Student getStuByNo(int stuNo) {

		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		Student stu = null;
		
		try {
			pstmt = conn.prepareStatement(SQL_GET_STUBYID);
			pstmt.setInt(1, stuNo);
			rest = pstmt.executeQuery(); //装满记录的容器，叫做结果集
			
			//读写指针向下移动一行
			if(rest.next()) {
				//解析记录封装成对象
				stu = new Student();
				stu.setStuNo(rest.getInt("stu_no"));
				stu.setStuName(rest.getString("stu_name"));
				stu.setStuAge(rest.getInt("stu_age"));
				stu.setStuMark(rest.getDouble("stu_mark"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.releaseRes(conn,pstmt,rest);
		}
		
		return stu;
	}

	@Override
	public void updateStu(Student stu) {
		
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		
		try {
			//对象转成记录，存放到关系型数据库
			pstmt = conn.prepareStatement(SQL_UPDATE_STU);			
			pstmt.setString(1, stu.getStuName());
			pstmt.setInt(2, stu.getStuAge());
			pstmt.setDouble(3, stu.getStuMark());
			pstmt.setInt(4, stu.getStuNo());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.releaseRes(conn,pstmt,null);
		}
		
	}

}
