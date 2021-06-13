package com.house.framework.service;

import java.util.List;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.DictItem;

public interface DictItemService {

	/**
	 * 保存方法
	 * @param dict
	 */
	public void save(DictItem dictItem);
	
	/**
	 * 更新菜单
	 * @param menu
	 */
	public void update(DictItem dictItem);
	
	/**
	 * 删除菜单
	 * @param menuId
	 */
	public void delete(Long dictItemId);
	
	/**
	 * 批量删除字典元素
	 * @param dictItemIds
	 */
	public void delete(List<Long> dictItemIds);
	
	/**
	 * 获取字典元素对象
	 * @param dictItemId
	 * @return
	 */
	public DictItem get(Long dictItemId);
	
	/**
	 * 根据字典元素编码获取字典元素
	 * @param dictId   所属字典ID
	 * @param itemCode 元素编码
	 * @return
	 */
	public DictItem getByDictIdAndItemCode(Long dictId, String itemCode);
	
	/**
	 * 根据字典元素文本和字典ID获取字典元素对象
	 * @param dictId   所属字典ID
	 * @param itemLabel 字典元素文本
	 * @return
	 */
	public DictItem getByDictIdAndLabel(Long dictId, String itemLabel);
	
	/**
	 * 修改字典编码状态
	 * @param dictItemId
	 * @param status
	 */
	public void setStatus(Long dictItemId, String status);
	
	/**
	 * 分页查询
	 * @param page
	 * @param dict
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page findPage(Page page, DictItem dictItem);
	
	/**
	 * 根据字典编码获取该字典所有在用字典元素
	 * @param dictCode  字典编码
	 * @return
	 */
	public List<DictItem> getByDictCode(String dictCode);
	
	/**
	 * 根据字典编码获取该字典所有在用字典值
	 * @param dictCode  字典编码
	 * @return
	 */
	public List<Integer> getByDictItem(String dictCode);
	/**
	 * 根据字典ID获取所有的在用字典元素列表
	 * @param dictId
	 * @return
	 */
	public List<DictItem> getByDictId(Long dictId,String status);
	
	/**
	 * 获取下一个排序号
	 * @param dictId
	 * @return
	 */
	public int getNextNum(Long dictId);
	
	/**
	 * 获取所有的字典元素
	 * @return
	 */
	public List<DictItem> getAll();
	/**
	 * 获取字典元素
	 */
	public DictItem getDictItemByDictCode(String dictCode);
}
