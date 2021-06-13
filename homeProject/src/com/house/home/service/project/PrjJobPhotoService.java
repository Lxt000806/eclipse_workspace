package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjJobPhoto;

public interface PrjJobPhotoService extends BaseService {

	/**PrjJobPhoto分页信息
	 * @param page
	 * @param prjJobPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjJobPhoto prjJobPhoto);

	public List<String> getPhotoListByPrjJobNo(String id, String type);
	
	public void deleteByPrjJobNo(String no,String photoName);
}
