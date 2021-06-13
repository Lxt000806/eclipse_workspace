package edu.mju.stuwork.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.mju.stuwork.domain.Student;
import edu.mju.stuwork.utils.DBUtils;

public class StudentDaoJDBCImpl implements StudentDao {

	private static final String SQL_ADD
	   ="insert into tbl_student values(?,?,?)";
	private static final String SQL_LOAD_STUS
	   ="select * from tbl_student order by stu_no desc";
	private static final String SQL_DEL_STU_BYNO
	   ="delete from tbl_student where stu_no=?";
	private static final String SQL_GET_STU_BYNO
	   ="select * from tbl_student where stu_no=?";
	private static final String SQL_UPDATE_STU
       ="update tbl_student set stu_name=?,stu_mark=? where stu_no=?";

	@Override
	public void addStudent(Student stu) {
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(SQL_ADD);
			pstmt.setInt(1,stu.getStuNo());
			pstmt.setString(2,stu.getStuName());
			pstmt.setDouble(3,stu.getStuMark());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.releaseRes(conn, pstmt, null);
		}
	}
	@Override
	public List<Student> loadStus() {
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Student> stuList = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(SQL_LOAD_STUS);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Student stu = new Student();
				stu.setStuNo(rset.getInt("stu_no"));
				stu.setStuName(rset.getString("stu_name"));
				stu.setStuMark(rset.getDouble("stu_mark"));
				stuList.add(stu);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.releaseRes(conn, pstmt, rset);
		}
		return stuList;
	}
	@Override
	public void delStudent(int stuNo) {
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(SQL_DEL_STU_BYNO);
			pstmt.setInt(1,stuNo);		
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.releaseRes(conn, pstmt, null);
		}
		
	}
	@Override
	public Student getStuByNo(int stuNo) {
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Student stu = null;

		try {
			pstmt = conn.prepareStatement(SQL_GET_STU_BYNO);
			pstmt.setInt(1,stuNo);		
			rset = pstmt.executeQuery();
			
			if(rset.next()){
				stu = new Student();
				stu.setStuNo(rset.getInt("stu_no"));
				stu.setStuName(rset.getString("stu_name"));
				stu.setStuMark(rset.getDouble("stu_mark"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.releaseRes(conn, pstmt, rset);
		}
		return stu;
	}
	@Override
	public void updateStu(Student stu) {
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(SQL_UPDATE_STU);	
			pstmt.setString(1, stu.getStuName());
			pstmt.setDouble(2, stu.getStuMark());
			pstmt.setInt(3, stu.getStuNo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    DBUtils.releaseRes(conn, pstmt, null);	
		}		
		
	}

}
