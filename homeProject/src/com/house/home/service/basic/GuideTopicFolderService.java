package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.GuideTopicFolder;

public interface GuideTopicFolderService extends BaseService {

	/**
	 * 加载所有的问题类目表
	 * @return
	 */
	public List<GuideTopicFolder> getAll();
	
	/**
	 * 根据子目录信息
	 * @param parentPk 
	 * @return
	 */
	public List<GuideTopicFolder> getSubGuideTopicFolder(Integer parentPk);

	/**
	 * 保存
	 * @param guideTopicFolder 
	 * @return
	 */
	public Result doSaveProc(GuideTopicFolder guideTopicFolder);
	
	/**
	 * 判断问题类目下是否有问答表
	 * @param parentId 
	 * @return
	 */
	public boolean hasGuideTopicByFolderPK(Integer folderPK);
	
	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, GuideTopicFolder guideTopicFolder);
	
}
