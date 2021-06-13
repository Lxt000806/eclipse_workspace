package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjProgPhoto;

public interface PrjProgPhotoService extends BaseService {

	/**PrjProgPhoto分页信息
	 * @param page
	 * @param prjProgPhoto
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto);

	public Page<Map<String,Object>> findPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto);
	
	public Page<Map<String,Object>> findXjPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto);

	public Page<Map<String,Object>> findYsPrjPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto);

	public Page<Map<String,Object>> findPrjProgPhotoPageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto);
	
	public Page<Map<String,Object>> findConstructionPicturePageBySql(Page<Map<String,Object>> page, PrjProgPhoto prjProgPhoto);

	
	/**获取项目经理相片的绝对路径
	 * @param custCode
	 * @param prjItem
	 * @return
	 */
	public List<Map<String,Object>> getPhotoList(String custCode,String prjItem);

	public List<String> getPhotoListByTypeAndRefNo(String type, String no);

	public List<String> getPhotoListByRefNo(String id,String type);
	
	/**
	 * 根据图片名称获取图片
	 * @param id
	 * @return
	 */
	public PrjProgPhoto getByPhotoName(String id);

	public void updatePhotoPustStatus(PrjProgPhoto prjProgPhoto);
}
