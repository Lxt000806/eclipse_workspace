package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemPreAppDetail;

public interface ItemPreAppDetailService extends BaseService {

	/**ItemPreAppDetail分页信息
	 * @param page
	 * @param itemPreAppDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreAppDetail itemPreAppDetail);

	/**根据批次号获取ItemPreAppDetail分页信息
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page, String id, String supplCode);

	public List<ItemPreAppDetail> findByNo(String no);
	
	public List<Map<String,Object>> findSqlByNo(String no);

	public Map<String,Object> getByPk(int pk);

	/**领料，有需求，添加材料时，同区域相同材料存在申请状态的材料增减项单
	 * @param no 预领料单编号
	 * @return 材料名称
	 */
	public String existsChange(String no);
	
	public List<Map<String, Object>> existsOutSetItem(String no);
	
	public List<Map<String, Object>> existsInSetItem(String no);
	
}
