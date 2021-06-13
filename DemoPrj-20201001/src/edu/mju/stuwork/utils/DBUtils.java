package edu.mju.stuwork.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 数据库工具类(utility)
 * @author HY
 *
 */
public class DBUtils {
	
	/**
	 * @使用Oracle连接
	 *  private static final String CONN_URL = "jdbc:oracle:thin:@DESKTOP-AO2KITI:1521:orcl";
	 *  private static final String USER = "CASE";
	 *  private static final String PWD = "123456";
	 */
	 
	 private static final String CONN_URL = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
	 private static final String USER = "HY";
	 private static final String PWD = "123456";
    	
	/**
	 * 获得连接
	 * @return
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");//oracle.jdbc.driver.OracleDriver
			conn = DriverManager.getConnection(CONN_URL,USER,PWD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
		
	/**
	 * 释放资源
	 * @param conn
	 * @param pstmt
	 * @param rset
	 */
	public static void releaseRes(Connection conn,PreparedStatement pstmt,ResultSet rset){
		
			try {
				if(rset!=null) rset.close();
				if(pstmt!=null) pstmt.cancel();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
