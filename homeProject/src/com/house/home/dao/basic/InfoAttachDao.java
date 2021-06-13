package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.InfoAttach;

@SuppressWarnings("serial")
@Repository
public class InfoAttachDao extends BaseDao {

	/**
	 * InfoAttach分页信息
	 * 
	 * @param page
	 * @param infoAttach
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InfoAttach infoAttach) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tInfoAttach a where 1=1 ";

    	if (infoAttach.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(infoAttach.getPk());
		}
    	if (StringUtils.isNotBlank(infoAttach.getInfoNum())) {
			sql += " and a.InfoNum=? ";
			list.add(infoAttach.getInfoNum());
		}
    	if (StringUtils.isNotBlank(infoAttach.getFileName())) {
			sql += " and a.FileName=? ";
			list.add(infoAttach.getFileName());
		}
    	if (StringUtils.isNotBlank(infoAttach.getFtpPath())) {
			sql += " and a.FtpPath=? ";
			list.add(infoAttach.getFtpPath());
		}
    	if (infoAttach.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(infoAttach.getDispSeq());
		}
    	if (infoAttach.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(infoAttach.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(infoAttach.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(infoAttach.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(infoAttach.getExpired()) || "F".equals(infoAttach.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(infoAttach.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(infoAttach.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> getByInfoNum(String num) {
		String sql = "select pk,InfoNum,FileName,FtpPath from tInfoAttach where infoNum=? order by DispSeq";
		
		return this.findBySql(sql.toLowerCase(), new Object[]{num});
	}

}

