package com.house.home.service.design;

import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ResrCustEvt;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustExcelFailed;

public interface ResrCustService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ResrCust resrCust);

	public Page<Map<String,Object>> findResrCustMapperPageBySql(Page<Map<String,Object>> page, ResrCust resrCust);

	/**
	 *判断地址重复 
	 * 
	 **/
	public ResrCust getByAddress(String address);
	
	public void doClientSave(ResrCustEvt evt);

	/**
	 * 获取映射的客户编号
	 * @author cjg
	 * @date 2019-12-26
	 * @param resrCustCode
	 * @return
	 */
	public String getMapperCustCode(String resrCustCode);
	
	/**
	 * 查询跟踪记录
	 * @author cjg
	 * @date 2019-12-26
	 * @param page
	 * @param resrCust
	 * @return
	 */
	public Page<Map<String, Object>> goConJqGrid(Page<Map<String, Object>> page, ResrCust resrCust);
	
	public void doClientUpdate(ResrCustEvt evt,ResrCust oldResrCust);
	
	public void doClientUpdateCustResStat(ResrCustEvt evt) ;
	
	public int getConRemMinLen();
	
	/**
	 * 修改创建人列表
	 * @author cjg
	 * @date 2020-2-24
	 * @param page
	 * @param resrCust
	 * @return
	 */
	public Page<Map<String, Object>> goUpdateCrtJqGrid(Page<Map<String, Object>> page, ResrCust resrCust);
	/**
	 * 修改创建人
	 * @author cjg
	 * @date 2020-2-24
	 * @param resrCust
	 */
	public void doUpdateCrtCzy(ResrCust resrCust) ;
	/**
	 * 查看任务列表
	 * @author cjg
	 * @date 2020-2-25
	 * @param page
	 * @param resrCustExcelFailed
	 * @return
	 */
	public Page<Map<String, Object>> goFailedExcelJqGrid(Page<Map<String, Object>> page, ResrCustExcelFailed resrCustExcelFailed);
	/**
	 * 是否关联意向客户
	 * @param code
	 * @return
	 */
	public String hasCustCode(String code);
	/**
	 * 关联意向客户保存
	 * @param resrCust
	 * @return
	 */
	public void doAddCustCode(ResrCust resrCust);
	/**
	 * 跟单员领取资源客户数
	 * @param code
	 * @return
	 */
	public Integer getResrCustNum(String code);

	public Result doCustTeamGiveUp(ResrCust rc);
	
	public Result doCancelCust(ResrCust rc);
	
	public Result doCustTeamChangeBusiness(ResrCust rc);
	
	public Map<String, Object> getResrCustDetail(String code);
	
	public List<Map<String, Object>> getResrCustTagList(String code, String czybh);
	
	public Result doShareCust(ResrCust resrCust);
	
	public List<Map<String, Object>> getCustNetCnlList();
	
	public List<Map<String, Object>> findSourceByAuthority(int type,String pCode, UserContext uc);
	
	public List<Map<String, Object>> getCustNetCnlListBySource(String source);

	public List<Map<String, Object>> getResrCustPoolList(ResrCustEvt evt);
	
	public String getDefaultResrCustPoolNo(ResrCustEvt evt);

	public String getDefPoolNoByCzybm(String czybh);
	
	public boolean getCanReceiveCust(String codes, String czybh);

}
