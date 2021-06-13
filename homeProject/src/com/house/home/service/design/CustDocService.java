package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustDoc;
import com.house.home.entity.design.Customer;

public interface CustDocService extends BaseService{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,CustDoc custDoc,UserContext uc );
	
	public Page<Map<String, Object>> findDocPageBySql(
			Page<Map<String, Object>> page,CustDoc custDoc,String docType1 );
	
	public Page<Map<String, Object>> findChgPageBySql(
			Page<Map<String, Object>> page,CustDoc custDoc,String docType1,UserContext uc );
	
	public Page<Map<String, Object>> goDocChgConByJqGrid(
			Page<Map<String, Object>> page,CustDoc custDoc);
	
	public void doDeleteDoc(String custCode,String docName);

	public void doEditDescr(int pk,String descr,String czybh);
	
	
	public Map<String, Object> getMinDocType2(String docType1);
	
	public Map<String , Object> getDocType2Info(String docType2);
	
	public void doSaveDesignPic(String custCode,String lastUpdatedBy);
	
	/**
	 * 更新设计图纸上传流程信息
	 * 
	 * @param custCode 客户编号
	 * @param status 状态
	 * @param submitCZY 提交人
	 * @param confirmCZY 审核人
	 * @param confirmRemarks 审核内容
	 * @param LastUpdatedby 最后修改人
	 * @param isFullColorDraw 全景效果图->效果图类型
	 * @param drawQty 效果图数量
	 */
    public void updateDesignPic(String custCode, String status,
            String submitCZY, String confirmCZY, String confirmRemarks,
            String LastUpdatedby, String isFullColorDraw, Integer drawQty, Integer draw3dQty);
	
	public void updateCustDocStatus(String custCode,String LastUpdatedby);
	
	public void doRetConfirm(String custCode,String status,String submitCZY,String confirmCZY
			,String confirmRemarks,String LastUpdatedby);
	/**
	 * 是否有管理套内面积权限
	 * 
	 * @param custDoc
	 * @return
	 */
	public List<Map<String, Object>> hasManageAreaRight(String czy);
	/**
	 * 修改套内面积
	 * @param customer
	 */
	public void updateInnerArea(Customer customer);
	
	public boolean getIsAllowChg(String custCode);

	public boolean getIsAllowCommi(String custCode);
	
	/**
	 * 图纸变更审核通过
	 * 
	 * @param custDoc
	 * @author 张海洋
	 */
	public void doConfirmPass(CustDoc custDoc);
	
	/**
	 * 图纸变更审核退回
	 * 
	 * @param custDoc
	 * @author 张海洋
	 */
	public void doConfirmBack(CustDoc custDoc);

	public void doFinishChg(CustDoc custDoc,String lastUpdatedBy);
	/**
	 * 根据文件类型2更新文件时，记录客户跟踪表
	 * @author	created by zb
	 * @date	2020-1-2--下午5:34:16
	 * @param custCode
	 * @param upDocType2
	 * @param userContext
	 */
	public void updateCustCon(String custCode, String upDocType2, UserContext userContext);
	/**
	 * 过期文档
	 * @author	created by zb
	 * @date	2020-1-3--下午2:33:02
	 * @param deleteId
	 * @param userContext 
	 */
	public void doExpiredDoc(String deleteId, UserContext userContext);
	
	public void doDrawNoChg(CustDoc custDoc,String lastUpdatedBy);
	
	public boolean getIsAllowAdd(String custCode);
}
