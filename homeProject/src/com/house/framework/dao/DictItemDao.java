package com.house.framework.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.DictItem;
import com.house.framework.commons.utils.DateUtil;

/**
 * 字典元素dao
 *
 */
@SuppressWarnings("serial")
@Repository
public class DictItemDao extends BaseDao{

	/**
	 * 查询分页
	 * @param page
	 * @param dict
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findPage(Page page, DictItem dictItem){
		String hql = "from DictItem t where 1=1 ";
		
		if(null!=dictItem.getDictId()){
			hql += " and t.dictId = ? ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			hql += " order by t." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			hql += " order by t.dictId, t.orderNo";
		}
		if(null!=dictItem.getDictId()){
			return this.findPage(page, hql, dictItem.getDictId());
		}else{
			return this.findPage(page, hql);
		}
	}
	
	/**
	 * 根据字典元素编码获取字典元素
	 * @param itemCode 元素编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DictItem getByDictIdAndItemCode(Long dictId, String itemCode){
		String hql = "from DictItem t where t.dictId = ? and t.itemCode = ?";
		List<DictItem> list = this.find(hql, new Object[]{dictId, itemCode});
		if(list == null || list.size() < 1)
			return null;
		return list.get(0);
	}
	
	/**
	 * 根据字典元素文本和字典ID获取字典元素对象
	 * @param dictId   所属字典ID
	 * @param itemLabel 字典元素文本
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DictItem getByDictIdAndLabel(Long dictId, String itemLabel) {
		String hql = "from DictItem t where t.dictId = ? and t.itemLabel = ?";
		List<DictItem> list = this.find(hql, new Object[]{dictId, itemLabel});
		if(list == null || list.size() < 1)
			return null;
		return list.get(0);
	}
	
	
	/**
	 * 根据字典编码获取该字典所有字典元素
	 * @param dictCode  字典编码
	 * @return
	 * @author wjs 2012-04-02
	 */
	public List<DictItem> getByDictCode(String dictCode) {
		String sql ="select t.*" +
					"  from tsys_dict_item t, tsys_dict d" +
					" where d.dict_code = ?" +
					"   and d.dict_id = t.dict_id" +
					" and d.status='1' order by t.order_no";
		return this.mapper(this.findBySql(sql, new Object[]{dictCode}));
	}

	/**
	 * 根据字典编码获取该字典有效的字典元素
	 * @param dictCode  字典编码
	 * @return
	 * @author wjs 2012-04-02
	 */
	public List<Integer> getByDictItem(String dictCode) {
		//readme   诸如select t.item_value此类语句必须加个大写的别名，如“ITEM_VALUE”，
		//因为mysql默认使用小写
		String sql ="select t.item_value ITEM_VALUE" +
					"  from tsys_dict_item t, tsys_dict d" +
					" where d.dict_code = ?" +
					"   and d.dict_id = t.dict_id" +
					" and d.status='1' and t.status='1' order by t.order_no";
		return this.mapperInteger(this.findBySql(sql, new Object[]{dictCode}));
	}
	/**
	 * 
	 *功能说明:转换Sql查找出来Map列表为 Integer类型字典值列表
	 *@param list
	 *@return List<Integer>
	 *
	 */
	public List<Integer> mapperInteger(List<Map<String, Object>> list){
		if(list == null || list.size() < 1)
			return null;
		List<Integer> rsList = new ArrayList<Integer>(list.size());
		DictItem dictItem = null;
		for(Map<String, Object> map : list){
			dictItem = this.mapper(map);
			if(dictItem != null){
				rsList.add(Integer.parseInt(dictItem.getItemValue()));
			}
		}
		return rsList;
	}
	/**
	 * 转换Sql查找出来Map列表为 字典元素列表
	 * @param list
	 * @return
	 */
	public List<DictItem> mapper(List<Map<String, Object>> list){
		if(list == null || list.size() < 1)
			return null;
		List<DictItem> rsList = new ArrayList<DictItem>(list.size());
		DictItem dictItem = null;
		for(Map<String, Object> map : list){
			dictItem = this.mapper(map);
			if(dictItem != null){
				rsList.add(dictItem);
			}
		}
		return rsList;
	}
	
	@SuppressWarnings("unchecked")
	public List<DictItem> getByDictId(Long dictId,String status){
		String hql = "from DictItem d where d.dictId = ? ";
		if(status!=null)
		{
			hql+=" and status='"+status+"'";
		}
		hql+=" order by d.orderNo";
		return this.find(hql, dictId);
	}
	
	/**
	 * 获取下一个排序号
	 * @param dictId
	 * @return
	 */
	public int getNextNum(Long dictId){
		if(dictId == null)
			dictId = 0L;
		int nextNum = 0;
		String sql = "select max(t.order_no) as NEXTNUM from TSYS_DICT_ITEM t where t.dict_id = ?";
		List<Map<String, Object>> list = this.findBySql(sql, dictId);
		if(list == null || list.size() < 0)
			return nextNum;
		Object obj = list.get(0).get("NEXTNUM");
		if(obj == null)
			return nextNum;
		try {
			nextNum = Integer.parseInt(obj.toString())+1;
		} catch (NumberFormatException e) {
			nextNum = 0;
			e.printStackTrace();
		}
		return nextNum;
	}
	
	
	/**
	 * 将sql查询出来的Map对象转换成DictItem对象
	 * @param map
	 * @return
	 */
	public DictItem mapper(Map<String, Object> map) {
		if(map == null){
			return null;
		}
		DictItem dictItem = new DictItem();
		
		Long itemId = null;
		Object itemID = map.get("ITEM_ID");
		if(itemID != null){
			Integer bd = (Integer)itemID;  //Integer可表示2的32次方-1,已经足够用,mysql中只能使用int作为自增字段类型
			itemId = bd.longValue();
		}
		dictItem.setItemId(itemId);
		
		Long dictId = null;
		Object dictID = map.get("DICT_ID");
		if(dictID != null){
			Integer bd = (Integer)dictID;
			dictId = bd.longValue();
		}
		dictItem.setDictId(dictId);
		
		Long orderNo = 0L;
		Object orderNO = map.get("ORDER_NO");
		if(orderNO != null){
			Integer bd = (Integer)orderNO;
			orderNo = bd.longValue();
		}
		dictItem.setOrderNo(orderNo);
		
		dictItem.setItemLabel((String)map.get("ITEM_LABEL"));
		dictItem.setItemCode((String)map.get("ITEM_CODE"));
		dictItem.setItemValue((String)map.get("ITEM_VALUE"));
		dictItem.setStatus((String)map.get("STATUS"));
		dictItem.setGenTime(DateUtil.timeStampToDate((Timestamp) map.get("GEN_TIME")));
		dictItem.setUpdateTime(DateUtil.timeStampToDate((Timestamp) map.get("UPDATE_TIME")));
		dictItem.setRemark((String)map.get("REMARK"));
		
		return 	dictItem;
	}
	/**
	 * 
	 *功能说明:以字典编码获取有效的字典对象
	 *@param dictCode
	 *@return DictItem
	 *
	 */
	@SuppressWarnings("unchecked")
	public DictItem getDictItemByDictCode(String dictCode){
		StringBuffer hql = new StringBuffer(" from DictItem d where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(dictCode!=null){
			hql.append(" and d.itemCode = ?");
			params.add(dictCode);
		}
		hql.append(" and d.status='1'");
		List<DictItem> dictList = this.find(hql.toString(), params.toArray());
		return dictList.get(0);
	}
	
}
