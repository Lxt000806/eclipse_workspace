package com.house.home.serviceImpl.basic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.DocDao;
import com.house.home.entity.basic.Doc;
import com.house.home.service.basic.DocService;

@SuppressWarnings("serial")
@Service 
public class DocServiceImpl extends BaseServiceImpl implements DocService {
	@Autowired
	private  DocDao docDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Doc doc) {
		
		return docDao.findPageBySql(page, doc);
	}

	@Override
	public List<Map<String, Object>> getAuthNode(String czybm, boolean hasAuthByCzybh) {
		
		return docDao.getAuthNodeList(czybm, hasAuthByCzybh);
	}

	@Override
	public boolean checkDocNameUnique(String docName, Integer pk) {
		
		return docDao.checkDocNameUnique(docName, pk);
	}
	
	@Override
	public boolean checkDocCodeUnique(String docCode, Integer pk) {
		
		return docDao.checkDocCodeUnique(docCode, pk);
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	@Override
	public void doSave(Doc doc, JSONArray attrDatas) {
		// 文档保存
		this.save(doc);
		
		// 有附件保存附件
		if(attrDatas != null && attrDatas.size()>0){
			for (int i = 0; i < attrDatas.size(); i++) {
				docDao.doSaveFile(doc, (Map<String, Object>) attrDatas.get(i));
			}
		}
		
		// 是否消息通告 第一版先不做
		/*
		if("1".equals(doc.getIsNeedNotice())){
			docDao.doSaveNotice(doc);
		}
		*/
	}

	@Override
	public void doCopyDocAttr(Doc doc, Integer pk) {
		
		docDao.doCopyDocAttr(pk, doc);
	}

	@Override
	public boolean getHasFiledDoc(String docCode) {
		return docDao.getHasFiledDoc(docCode);
	}

	@Override
	public List<Map<String, Object>> getDocAtt(Integer pk) {
		return docDao.getDocAtt(pk);
	}

	@Override
	public void doSaveFile(Doc doc, Map<String, Object> map) {

		docDao.doSaveFile(doc, map);
	}

	@Override
	public Integer getDocAttachmentPk(String attName, Integer docPk) {
		
		return docDao.getDocAttachmentPk(attName, docPk);
	}


}
