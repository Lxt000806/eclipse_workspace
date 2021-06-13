package com.house.framework.log;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import com.house.framework.commons.utils.SpringContextHolder;

@SuppressWarnings("unchecked")
public class OperAppender extends JDBCAppender {

	/**
	 * override the flushbuffer() to do the exactly db log insert.
	 */
	@SuppressWarnings("rawtypes")
	public void flushBuffer() {
		String sql;
		removes.ensureCapacity(buffer.size());
		for (Iterator i = buffer.iterator(); i.hasNext();) {
			try {
				LoggingEvent logEvent = (LoggingEvent) i.next();
				if (logEvent instanceof LogEvent) {
					LogEvent logevent = (LogEvent) logEvent;
					sql = getLogStatement(logevent);
					
					removes.add(logEvent);
					
					execute(sql, logevent);
				} else {
					// do nothing;
				}
				
			} catch (SQLException e) {
				errorHandler.error("Failed to excute sql", e,
						ErrorCode.FLUSH_FAILURE);
			}
		}

		buffer.removeAll(removes);
		removes.clear();
	}

	protected String getLogStatement(LogEvent event) {
		return "insert into TSYS_OPER_LOG (ADD_TIME, CLAZZ, JAVA_FUN, IP, OPR_CONTENT, MODEL_CODE, CZYBH, MODEL_TYPE) VALUES (cast(? as datetime),?,?,?,?,?,?,?)";
	}

	protected void execute(String sql, final LogEvent event) throws SQLException {
		JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate", JdbcTemplate.class);
		jdbcTemplate.execute(sql, new  PreparedStatementCallback<Object>(){
			
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				if (null == event.getMsgObj().getTime()) {
					event.getMsgObj().setTime(new Date());
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timeString = sdf.format(event.getMsgObj().getTime());
				ps.setString(1, timeString);
				
				if (null == event.getMsgObj().getClazz()) {
					ps.setNull(2, java.sql.Types.VARCHAR);
				} else {
					String clazz = event.getMsgObj().getClazz();
					if(clazz.startsWith("class "))					
						clazz = clazz.substring(6);
					ps.setString(2, clazz);
				}
				
				if (null == event.getMsgObj().getJavaFun()) {
					ps.setNull(3, java.sql.Types.VARCHAR);
				} else {
					ps.setString(3, event.getMsgObj().getJavaFun());
				}
				
				if (null == event.getMsgObj().getIp()) {
					ps.setNull(4, java.sql.Types.VARCHAR);
				} else {
					ps.setString(4, event.getMsgObj().getIp());
				}
				
				ps.setString(5, event.getMsgObj().getLogcontent());
				if (null == event.getMsgObj().getModelCode()) {
					ps.setNull(6, java.sql.Types.VARCHAR);
				} else {
					ps.setString(6, event.getMsgObj().getModelCode());
				}
				ps.setString(7, event.getMsgObj().getCzybh());
				ps.setLong(8, event.getMsgObj().getModelType());
				
				ps.execute();
				
				return null;
			}
			
		});
	}
}
