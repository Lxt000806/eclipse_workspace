package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.DocFolder;

public interface DocFolderService extends BaseService {
	
	
	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, DocFolder docFolder);

	/**
	 * 加载所有的文档目录
	 * @return
	 */
	public List<DocFolder> getAll();
	
	/**
	 * 根据子目录信息
	 * @param parentPk 
	 * @return
	 */
	public List<DocFolder> getSubDocFolder(Integer parentPk);

	/**
	 * 保存
	 * @param docFolder 
	 * @return
	 */
	public Result doSaveProc(DocFolder docFolder);
	
	/**
	 * 判断目录下是否有文档
	 * @param folderPK 
	 * @return
	 */
	public boolean hasDocByFolderPK(Integer folderPK);

}
