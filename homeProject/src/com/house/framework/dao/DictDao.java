package com.house.framework.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Dict;

/**
 * 字典dao
 *
 */
@Repository
@SuppressWarnings({ "unchecked", "serial" })
public class DictDao extends BaseDao{
	
	/**
	 * 根据字典名称获取字典对象
	 * @param dictName
	 * @return
	 */
	public Dict getByDictName(String dictName){
		String hql = "from Dict t where t.dictName = ?";
		List<Dict> list = this.find(hql, dictName);
		if(list == null || list.size() < 1)
			return null;
		return list.get(0);
	}
	
	/**
	 * 根据字典元素获取字典对象
	 * @param dictCode
	 * @return
	 */
	public Dict getByDictCode(String dictCode){
		String hql = "from Dict t where t.dictCode = ?";
		List<Dict> list = this.find(hql, dictCode);
		if(list == null || list.size() < 1)
			return null;
		return list.get(0);
	}
	
	/**
	 * 根据字典类型和状态获取字典列表
	 * @param dictType  字典类型
	 * @param status  状态
	 * @return
	 */
	public List<Dict> getByDictType(String dictType, String status){
		List<String> list = new ArrayList<String>();
		String hql = "from Dict t where 1=1";
		if(StringUtils.isNotBlank(dictType)){
			hql += " and t.dictType = ?";
			list.add(dictType.trim());
		}
		if(StringUtils.isNotBlank(status)){
			hql += " and t.status = ?";
			list.add(status.trim());
		}
		return this.find(hql, list.toArray());
	}
	
	@SuppressWarnings("rawtypes")
	public Page findPage(Page page, Dict dict){
		List<Object> list = new ArrayList<Object>();
		
		String hql ="from Dict t where 1=1";
		
		if(StringUtils.isNotBlank(dict.getDictName())){
			hql += " and t.dictName like ?";
			list.add("%"+dict.getDictName().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(dict.getDictCode())){
			hql += " and t.dictCode like ?";
			list.add("%"+dict.getDictCode().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(dict.getStatus())){
			hql += " and t.status = ? ";
			list.add(dict.getStatus().trim());
		}
		
		if(StringUtils.isNotBlank(dict.getDictType())){
			hql += " and t.dictType = ? ";
			list.add(dict.getDictType().trim());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			hql += " order by t." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			hql += " order by t.dictId";
		}
		
		return this.findPage(page, hql, list.toArray());
	}
}
