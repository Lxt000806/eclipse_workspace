package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.DocFolder;


@SuppressWarnings("serial")
@Repository
public class DocFolderDao extends BaseDao {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DocFolderDao.class);
	
	@SuppressWarnings(value="unchecked")
	public List<DocFolder> getAll(){
		String hql = "from DocFolder ";
		return this.find(hql);
	}
	
	@SuppressWarnings(value="unchecked")
	public List<DocFolder> getSubDocFolder(Integer parentPk){
		if(null == parentPk)
			parentPk = 0;
		String hql = "from DocFolder where parentPk = ? ";
		return this.find(hql, new Object[]{parentPk});
	}
	
	/**
	 * 保存
	 * @param docFolder
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(DocFolder docFolder) {
		Assert.notNull(docFolder);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDocFolder(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, docFolder.getM_umState());
			call.setInt(2, docFolder.getPk()==null?0:docFolder.getPk());
			call.setInt(3, docFolder.getParentPk()==null?0:docFolder.getParentPk());
			call.setString(4, docFolder.getFolderName());
			call.setString(5, docFolder.getFolderCode());
			call.setString(6, docFolder.getAdminCzy());
			call.setString(7, docFolder.getAuthType());
			call.setString(8, docFolder.getIsAuthWorker());
			call.setString(9, docFolder.getAuthWorkerTypes());
			call.setString(10, docFolder.getDocFolderViewRole());
			call.setString(11, docFolder.getLastUpdatedBy());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, docFolder.getIsAuthPM());
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
			result.setInfo(e.getMessage());
		}finally {
			DbUtil.close(null, call, conn);
		}
		return result;	
	}
	public boolean hasDocByFolderPK(Integer folderPK) {
		if (folderPK == null){
			return false;
		}
		String sql =" select 1 from tDoc where folderPK= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {folderPK});
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DocFolder docFolder) {	
		List<Object> list = new ArrayList<Object>();
		
		String sql ="";
		if(docFolder.isHasAuthByCzybh()){
			sql = " select * from (select dbo.fGetDocFolderNames(a.Path) folderNames,a.PK ,a.ParentPK ,a.FolderName , a.FolderCode, a.Path " +
					" from tDocFolder a " +
					") a" +
					" where 1=1 " ;
		} else {
			sql = "select * from (" +
					"	select dbo.fGetDocFolderNames(d.Path) folderNames, d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path " +
					"	from tDocFolder a" +
					" 	inner join tDocFolder d on (d.Path like a.Path + '/%' and a.AuthType <> '1' ) or d.Path = a.Path" +
					" 	where '%,'+rtrim(a.AdminCZY)+',%' like ? " +
					" 	group by d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path    " +
					") a where 1=1 ";
			list.add("%,"+docFolder.getCzybm().trim()+",%");
		}
		if(StringUtils.isNotBlank(docFolder.getFolderName())){
			sql+=" and a.folderNames like ?";
			list.add("%"+docFolder.getFolderName()+"%");
		}
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
