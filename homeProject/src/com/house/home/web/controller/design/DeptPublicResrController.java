package com.house.home.web.controller.design;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.design.ResrCustService;

@Controller
@RequestMapping("/admin/deptPublicResr")
public class DeptPublicResrController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DeptPublicResrController.class);

	@Autowired
	private ResrCustService resrCustService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ResrCust resrCust) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		resrCustService.findPageBySql(page,resrCust);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 部门公海列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,ResrCust resrCust) throws Exception {
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/design/deptPublicResr/deptPublicResr_list");
	}
	
	/**
	 *跳转到领用页面
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goReceive")
	public ModelAndView goDispatch(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("跳转到领用页面");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setCustRight(this.getUserContext(request).getCustRight());
		resrCust.setType("3");
		resrCust.setDispatchDate(new Date());
		return new ModelAndView("admin/design/deptPublicResr/deptPublicResr_receive")
			.addObject("resrCust",resrCust);
	}

	/**
	 * 领取
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doReceive")
	public void doDispatch(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc){
		logger.debug("领取");
		try{
			String codes[]=rc.getCodes().split(",");
			Integer receivedNum=resrCustService.getResrCustNum(rc.getBusinessMan());//已领数量
			Integer pubCustCtrl=Integer.parseInt(resrCustService.get(Xtcs.class,"PubCustCtrl").getQz());//限额
			Integer successNum=0;
			Integer failedNum=0;
			
			for(int i=0;i<codes.length;i++){
				
				ResrCust resrCust =resrCustService.get(ResrCust.class, codes[i]);
				resrCust.setBusinessMan(rc.getBusinessMan());
				resrCust.setDispatchDate(rc.getDispatchDate());
				resrCust.setLastUpdate(new Date());
				resrCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				resrCust.setActionLog("Edit");
				resrCust.setExpired(resrCust.getExpired());
				resrCust.setCustResStat("1");
				resrCust.setPublicResrLevel("0");
				
				CustCon custCon=new CustCon();
				custCon.setRemarks("领取");
				custCon.setType("2");
				custCon.setConDate(new Date());
				custCon.setConMan(this.getUserContext(request).getEmnum());
				custCon.setResrCustCode(resrCust.getCode());
				custCon.setExpired("F");
				custCon.setActionLog("ADD");
				custCon.setLastUpdate(new Date());
				custCon.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custCon.setCustCode("");
				
				//未超过限额保存，成功数量加1，否则失败数量加1
				if(i+1+receivedNum<=pubCustCtrl){
					this.resrCustService.update(resrCust);
					this.resrCustService.save(custCon);
					successNum++;
				}else{
					failedNum++;
				}
				
			}
			
			if(failedNum>0){
				ServletUtils.outPrintFail(request, response, "领取客户数量（非转意向）已经超过上限，<font color='orange'>"+successNum+"</font>个成功，<font color='red'>"+failedNum+"</font>个失败");
			}else{
				ServletUtils.outPrintSuccess(request, response,"领取成功");
			}
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "领取失败");
		}
	}
	
	/**
	 *跳转到接触日志
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goConLog")
	public ModelAndView goConLog(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("跳转到接触日志页面");
		return new ModelAndView("admin/design/deptPublicResr/deptPublicResr_conLog")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ResrCust resrCust){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		resrCustService.findPageBySql(page,resrCust);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"资源客户公海_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	@RequestMapping("/getCanReceive")
	@ResponseBody
	public boolean getCanReceive(HttpServletRequest request, HttpServletResponse response,ResrCust resrCust) {
		
		boolean res = resrCustService.getCanReceiveCust(resrCust.getCodes(), this.getUserContext(request).getCzybh());
		
		return res;
	}
	
}
