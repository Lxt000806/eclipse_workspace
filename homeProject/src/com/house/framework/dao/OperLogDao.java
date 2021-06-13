package com.house.framework.dao;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.CommonUtils;
import com.house.framework.log.LoggerObject;

@SuppressWarnings("serial")
@Repository
public class OperLogDao extends BaseDao {
	
	private static final Logger logger = LoggerFactory.getLogger(OperLogDao.class);
	
	/**
	 * 日志分页信息
	 * @param page
	 * @param LoggerObject
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page findPage(Page page, LoggerObject logObj,String logId, String startTime,String endTime){
		/***参数列表***/
		List<Object> list = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer("");
		sql.append("select t.id ID,t.clazz CLAZZ,t.ip IP,t.opr_content OPRCONTENT,t.java_fun JAVA_FUN, ");
		sql.append("convert(varchar(20),t.add_time,120) ADDTIME,");
		sql.append("t.model_code MODEL_CODE,");
		sql.append("(select u.czybh from tczybm u where u.czybh = t.czybh) USERNAME ");
		sql.append("from TSYS_OPER_LOG t where MODEL_TYPE = 0");
		
		/**日志明细**/
		if(!CommonUtils.isNullOrEmpty(logId)){
			sql.append(" and t.id = ?");
			list.add(CommonUtils.trim(logId));
		}	
		/**操作时间起**/
		if(!CommonUtils.isNullOrEmpty(startTime)){
			sql.append(" and t.add_time > ");
			sql.append(" cast(? as datetime) ");
			list.add(CommonUtils.trim(startTime));
		}
		/**操作时间止**/
		if(!CommonUtils.isNullOrEmpty(endTime)){
			sql.append(" and t.add_time < ");
			sql.append(" cast(? as datetime) ");
			list.add(CommonUtils.trim(endTime));
		}
		/**判断对象是否为空**/
		if(!CommonUtils.isNull(logObj)){
			
			/**模块ID**/
//			if(logObj.getModelId()>0){
//				sql.append(" and t.model_id = ?");
//				list.add(logObj.getModelId());
//			}
			/**用户ID**/
//			if(logObj.getUserid()>0){
//				sql.append(" and t.user_id = ?");
//				list.add(logObj.getUserid());
//			}
			/**操作ip**/
			if(!CommonUtils.isNullOrEmpty(logObj.getIp())){
				sql.append(" and t.ip like ?");
				list.add("%"+CommonUtils.trim(logObj.getIp())+"%");
			}
			/**操作描述**/
			if(!CommonUtils.isNullOrEmpty(logObj.getLogcontent())){
				sql.append(" and t.opr_content like ?");
				list.add("%"+logObj.getLogcontent()+"%");
			}
			/**类对象**/
			if(!CommonUtils.isNullOrEmpty(logObj.getClazz())){
				sql.append(" and t.clazz like ?");
				list.add("%"+CommonUtils.trim(logObj.getClazz())+"%");
			}
			if(!CommonUtils.isNullOrEmpty(logObj.getModelCode())){
				sql.append(" and t.model_code = ?");
				list.add(CommonUtils.trim(logObj.getModelCode()));
			}
		}
		
		sql.append(" order by t.id desc ");
		/**日志列表SQL语句**/
		logger.debug(sql.toString());
		
		return this.findPageBySql(page, sql.toString(),list.toArray());
	}
	
	/**
	 * Wap平台日志分页信息
	 * @param page
	 * @param LoggerObject
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPageWap(Page page, LoggerObject logObj,String logId, String startTime,String endTime){
		/***参数列表***/
		List<Object> list = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer("");
		sql.append("select t.id ID,t.clazz CLAZZ,t.ip IP,t.opr_content OPRCONTENT,t.java_fun JAVA_FUN, ");
		sql.append("convert(varchar(20),t.add_time,120) ADDTIME,");
		sql.append("t.model_code MODEL_CODE,");
		sql.append("t.user_id USER_ID");
		sql.append("from TSYS_OPER_LOG t where MODEL_TYPE = 1");
		
		/**日志明细**/
		if(!CommonUtils.isNullOrEmpty(logId)){
			sql.append(" and t.id = ?");
			list.add(CommonUtils.trim(logId));
		}	
		/**操作时间起**/
		if(!CommonUtils.isNullOrEmpty(startTime)){
			sql.append(" and t.add_time > ");
			sql.append(" cast(? as datetime) ");
			list.add(CommonUtils.trim(startTime));
		}
		/**操作时间止**/
		if(!CommonUtils.isNullOrEmpty(endTime)){
			sql.append(" and t.add_time < ");
			sql.append(" cast(? as datetime) ");
			list.add(CommonUtils.trim(endTime));
		}
		/**判断对象是否为空**/
		if(!CommonUtils.isNull(logObj)){
			
			/**用户ID**/
//			if(logObj.getUserid()>0){
//				sql.append(" and t.user_id = ?");
//				list.add(logObj.getUserid());
//			}
			/**操作ip**/
			if(!CommonUtils.isNullOrEmpty(logObj.getIp())){
				sql.append(" and t.ip like ?");
				list.add("%"+CommonUtils.trim(logObj.getIp())+"%");
			}
			/**操作描述**/
			if(!CommonUtils.isNullOrEmpty(logObj.getLogcontent())){
				sql.append(" and t.opr_content like ?");
				list.add("%"+logObj.getLogcontent()+"%");
			}
			/**类对象**/
			if(!CommonUtils.isNullOrEmpty(logObj.getClazz())){
				sql.append(" and t.clazz like ?");
				list.add("%"+CommonUtils.trim(logObj.getClazz())+"%");
			}
			if(!CommonUtils.isNullOrEmpty(logObj.getModelCode())){
				sql.append(" and t.model_code = ?");
				list.add(CommonUtils.trim(logObj.getModelCode()));
			}
		}
		
		sql.append(" order by t.id desc ");
		/**日志列表SQL语句**/
		logger.debug(sql.toString());
		
		return this.findPageBySql(page, sql.toString(),list.toArray());
	}
	
	
}
