package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Doc;

public interface DocService extends BaseService{

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, Doc doc);
	
	public List<Map<String, Object>> getAuthNode(String czybm,boolean hasAuthByCzybh);
	
	public boolean checkDocNameUnique(String docName, Integer pk);

	public boolean checkDocCodeUnique(String docName, Integer pk);

	public void doSave(Doc doc, JSONArray attrDatas);
	
	public void doCopyDocAttr(Doc doc, Integer pk);
	
	public boolean getHasFiledDoc(String docCode);
	
	public List<Map<String, Object>> getDocAtt(Integer pk);
	
	public void doSaveFile(Doc doc, Map<String, Object> map);
	
	public Integer getDocAttachmentPk(String attName, Integer docPk);
}
