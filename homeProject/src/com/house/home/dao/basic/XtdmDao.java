package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Xtdm;

@SuppressWarnings("serial")
@Repository
public class XtdmDao extends BaseDao {

	/**
	 * 系统代码分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Xtdm xtdm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tXtdm a where 1=1 ";

		if (StringUtils.isNotBlank(xtdm.getId())) {
			sql += " and a.Id=? ";
			list.add(xtdm.getId());
		}
		
		if (StringUtils.isNotBlank(xtdm.getIdnote())) {
            sql += " and a.IDNOTE like ? ";
            list.add("%" + xtdm.getIdnote() + "%");
        }
		
		if (StringUtils.isNotBlank(xtdm.getNote())) {
            sql += " and a.NOTE like ? ";
            list.add("%" + xtdm.getNote() + "%");
        }
		
        if (StringUtils.isBlank(xtdm.getExpired()) || "F".equals(xtdm.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**根据id,cbm,ibm查询系统代码
	 * @param id
	 * @param cbm
	 * @param ibm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Xtdm getByThree(String id, String cbm, Integer ibm) {
		String hql = "from Xtdm a where a.id=? and a.cbm=? and a.ibm=? ";
		List<Xtdm> list = this.find(hql, new Object[]{id,cbm,ibm});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**根据id和cbm查询系统代码
	 * @param id
	 * @param cbm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Xtdm getByIdAndCbm(String id, String cbm) {
		String hql = "from Xtdm a where a.id=? and a.cbm=? ";
		List<Xtdm> list = this.find(hql, new Object[]{id,cbm});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**根据id查询系统代码
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Xtdm> getById(String id) {
		/*String hql = "from Xtdm a where a.id=? order by a.ibm"; 新增dispSeq字段，替换掉原来的ibm排序 modify by zb on 20200410*/
		String hql = "from Xtdm a where a.id=? order by a.dispSeq";
		List<Xtdm> list = this.find(hql, new Object[]{id});
		return list;
	}

	public Map<String, Object> getFtpData() {
		String sql = "select a.ftpAddr,b.ftpUser,c.ftpPwd,d.ftpPort "
		+"from (select QZ ftpAddr from tXTCS where ID='FTPINNADDR') a,"
		+"(select QZ ftpUser from tXTCS where ID='FTPINNUSER') b,"
		+"(select QZ ftpPwd from tXTCS where ID='FTPINNPWD') c,"
		+"(select QZ ftpPort from tXTCS where ID='FTPPORT') d";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase());
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**根据id和note查询系统代码
	 * @param id
	 * @param cbm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Xtdm getByIdAndNote(String id, String note) {
		String hql = "from Xtdm a where a.id=? and a.note=? ";
		List<Xtdm> list = this.find(hql, new Object[]{id,note});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

    public List<Map<String, Object>> getSources() {
        String sql = "select CBM id, NOTE name "
                + "   from tXTDM "
                + "   where ID = 'CUSTOMERSOURCE' and Expired = 'F' "
                + "   order by IBM";

        return findBySql(sql);
    }

    public List<Map<String, Object>> getChannels(String code) {
        String sql = "select Code id, Descr name "
                + "   from tCustNetCnl "
                + "   where Expired = 'F' and CustSource = ? "
                + "   order by cast(Code as int)";
        
        return findBySql(sql, code);
    }
}
