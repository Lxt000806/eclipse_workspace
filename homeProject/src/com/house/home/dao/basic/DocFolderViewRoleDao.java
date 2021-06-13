package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.home.entity.basic.DocFolderViewRole;


@SuppressWarnings("serial")
@Repository
public class DocFolderViewRoleDao extends BaseDao {

	public List<Map<String, Object>> getDocFolderViewRole(DocFolderViewRole docFolderViewRole){	
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.ROLE_ID, a.ROLE_CODE,a.ROLE_NAME, 0 pId from TSYS_ROLE a" 	
				   + " where 1=1 ";
		if (StringUtils.isNotBlank(docFolderViewRole.getUnSelected())) {
			sql += " and a.ROLE_ID not in(select * from fStrToTable('"+docFolderViewRole.getUnSelected()+"',',')) ";
		}
		if (StringUtils.isNotBlank(docFolderViewRole.getSelected())) {
			sql += " and a.ROLE_ID  in(select * from fStrToTable('"+docFolderViewRole.getSelected()+"',',')) ";
		}
		return this.findBySql(sql, list.toArray());	
	}
	
	public List<Map<String, Object>> getDocFolderViewRoleByFolderPK(Integer folderPK){
		if(null == folderPK)
			return null;
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.PK, a.FolderPK, a.RolePK, b.ROLE_CODE, b.ROLE_NAME from tDocFolderViewRole a " 
				+" left join TSYS_ROLE b  on a.RolePK = b.ROLE_ID "
				+" where folderPK = ? ";
		list.add(folderPK);
		return this.findBySql(sql, list.toArray());	
	}	
}
