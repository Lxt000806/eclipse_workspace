package com.house.framework.service;

import java.util.List;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Dict;

/**
 * 字典service
 *
 */
public interface DictService {
	
	/**
	 * 保存方法
	 * @param dict
	 */
	public void save(Dict dict);
	
	/**
	 * 更新菜单
	 * @param menu
	 */
	public void update(Dict dict);
	
	/**
	 * 删除菜单
	 * @param menuId
	 */
	public void delete(Long dictId);
	
	/**
	 * 批量删除字典
	 * @param dictIds
	 */
	public void delete(List<Long> dictIds);
	
	/**
	 * 获取字典对象
	 * @param dictId
	 * @return
	 */
	public Dict get(Long dictId);
	
	/**
	 * 根据字典名称获取字典对象
	 * @param dictName
	 * @return
	 */
	public Dict getByDictName(String dictName);
	
	/**
	 * 根据字典元素获取字典对象
	 * @param dictCode
	 * @return
	 */
	public Dict getByDictCode(String dictCode);
	
	/**
	 * 修改字典状态
	 * @param dictId
	 * @param status
	 */
	public void setStatus(Long dictId, String status);
	
	/**
	 * 分页查询
	 * @param page
	 * @param dict
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page findPage(Page page, Dict dict);
	
	/**
	 * 取所有在用字典
	 * @return
	 */
	public List<Dict> getAll(String status);
	
	/**
	 * 根据字典类型和状态获取字典列表
	 * @param dictType  字典类型
	 * @param status  状态
	 * @return
	 */
	public List<Dict> getByDictType(String dictType, String status);
	
}
