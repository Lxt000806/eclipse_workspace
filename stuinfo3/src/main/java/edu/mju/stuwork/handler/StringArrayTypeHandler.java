/**
 * 
 */
package edu.mju.stuwork.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @author joeyang ong
 *
 */
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#setNonNullParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameters, JdbcType jdbcType)
			throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		
		for(String param:parameters)
			sb.append(param).append("|");
		
		ps.setString(i, sb.toString());
       
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		
		String data = rs.getString(columnName);  //RD|CM|RN
		
		return data.split("\\|");
		
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, int)
	 */
	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		
		String data = rs.getString(columnIndex);
		
		return data.split("\\|");
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.CallableStatement, int)
	 */
	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		
		return cs.getString(columnIndex).split("\\|");
		
	}

}
