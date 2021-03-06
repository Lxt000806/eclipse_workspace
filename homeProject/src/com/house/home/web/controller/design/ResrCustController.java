package com.house.home.web.controller.design;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.BuilderCacheManager;
import com.house.framework.commons.cache.XtdmCacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DescrUtil;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.NoteUtil;
import com.house.framework.commons.utils.Reflections;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.ExcelTask;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustExcelFailed;
import com.house.home.entity.design.ResrCustLog;
import com.house.home.entity.design.ResrCustPool;
import com.house.home.entity.workflow.Department;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ExcelTaskService;
import com.house.home.service.design.CustTagService;
import com.house.home.service.design.ResrCustLogService;
import com.house.home.service.design.ResrCustService;
import com.house.home.web.controller.insales.PurchaseController;

@Controller
@RequestMapping("/admin/ResrCust")
public class ResrCustController extends BaseController {
	
	private static final Logger logger =LoggerFactory.getLogger(PurchaseController.class);

	
	@Autowired
	private ResrCustService resrCustService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private ResrCustLogService resrCustLogService;
	@Autowired
	private ExcelTaskService excelTaskService;
	@Autowired
	private CustTagService custTagService;
	
	/**
	 * resrCust??????
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,ResrCust resrCust) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		if(czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0223", "??????????????????") && "2".equals(resrCust.getType())){
			resrCust.setCustRight("3");
		}
		
		if(czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0214","????????????????????????")){
			resrCust.setHasCreateAuth("1");
		}
		if(czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0214","????????????????????????")){
			resrCust.setHasConAuth("1");
		}
		resrCustService.findPageBySql(page, resrCust);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ????????????
	 * 
	 * */
	@RequestMapping("/goResrLogJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goResrLogJqGrid(HttpServletRequest request,
			HttpServletResponse response,ResrCustLog resrCustLog) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		resrCustLog.setLastUpdatedBy(getUserContext(request).getCzybh());
		resrCustLogService.findResrLogPageBySql(page, resrCustLog);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ?????????????????????
	 * 
	 * */
	@RequestMapping("/goUpdateCrtJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goUpdateCrtJqGrid(HttpServletRequest request,
			HttpServletResponse response,ResrCust resrCust) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		resrCustService.goUpdateCrtJqGrid(page, resrCust);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("goFailedExcelJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFailedExcelJqGrid(HttpServletRequest request,
			HttpServletResponse response,ResrCustExcelFailed resrCustExcelFailed) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		resrCustService.goFailedExcelJqGrid(page, resrCustExcelFailed);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *ResrCust??????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("??????????????????????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		Czybm czybm=czybmService.get(Czybm.class, resrCust.getCzybh());
		resrCust.setCustRight(czybm.getCustRight());
		resrCust.setDepartment2(czybm.getDepartment2());
		resrCust.setIsLead(czybm.getIsLead());
		
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "resrCustSet");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		String colModel = FileHelper.readTxtFile(filePath+fileName);
		
		DateTime dateTime = new DateTime();
		System.out.println(dateTime.withMillisOfDay(43200000).toString("MM/dd/yyyy HH:mm:ss"));
		System.out.println(dateTime.dayOfMonth().withMaximumValue().toString("MM/dd/yyyy HH:mm:ss"));
		System.out.println(dateTime.plusDays(45).plusMonths(1).dayOfWeek()
		  .withMaximumValue().toString("MM/dd/yyyy HH:mm:ss"));
		
		return new ModelAndView("admin/design/resrCust/resrCust_list")
			.addObject("resrCust",resrCust).addObject("colModel", colModel);
	}
	
	/**
	 *???????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("?????????????????????");
		Employee businessMan=employeeService.get(Employee.class, this.getUserContext(request).getEmnum());
		Employee crtCzy=businessMan;
		Department department=employeeService.get(Department.class, crtCzy.getDepartment());
		resrCust.setCrtCZYDept(crtCzy.getDepartment());
		resrCust.setCrtCzyDeptDescr(department.getDesc2());
		resrCust.setCrtDate(new Date());
		resrCust.setBusinessMan(this.getUserContext(request).getCzybh());
		resrCust.setBusinessManDescr(businessMan.getNameChi());
		resrCust.setDepartment2(businessMan.getDepartment2());
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDefPoolNo(resrCustService.getDefPoolNoByCzybm(this.getUserContext(request).getCzybh()));
		
		return new ModelAndView("admin/design/resrCust/resrCust_save")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *???????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goAddNetCust")
	public ModelAndView goAddNetCust(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("?????????????????????????????????");
		Employee businessMan=employeeService.get(Employee.class, this.getUserContext(request).getEmnum());
		Employee crtCzy=businessMan;
		Department department=employeeService.get(Department.class, crtCzy.getDepartment());
		resrCust.setCrtCZYDept(crtCzy.getDepartment());
		resrCust.setCrtCzyDeptDescr(department.getDesc2());
		resrCust.setCrtDate(new Date());
		resrCust.setBusinessMan(this.getUserContext(request).getCzybh());
		resrCust.setBusinessManDescr(businessMan.getNameChi());
		resrCust.setDepartment2(businessMan.getDepartment2());
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDefPoolNo(resrCustService.getDefPoolNoByCzybm(this.getUserContext(request).getCzybh()));

		return new ModelAndView("admin/design/resrCust/resrCust_addNetCust")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *???????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goAddCon")
	public ModelAndView goAddCon(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("?????????????????????????????????");
		Xtcs xtcs= employeeService.get(Xtcs.class,"ConRemMinLen");
		Employee businessMan=employeeService.get(Employee.class, this.getUserContext(request).getEmnum());
		resrCust.setBusinessMan(this.getUserContext(request).getCzybh());
		resrCust.setBusinessManDescr(businessMan.getNameChi());
		resrCust.setLastUpdate(new Date());
		return new ModelAndView("admin/design/resrCust/resrCust_addCon")
			.addObject("resrCust",resrCust).addObject("minLength", xtcs.getQz())
			.addObject("ossCdnAccessUrl", OssConfigure.cdnAccessUrl + "/custCallRecord");
	}
	
	/**
	 *?????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("?????????????????????");
		Builder builder=null;
		String czybh=this.getUserContext(request).getCzybh();
		ResrCust resrCust =resrCustService.get(ResrCust.class, rc.getCode());
		Employee businessMan=employeeService.get(Employee.class, resrCust.getBusinessMan());
		if(StringUtils.isNotBlank(resrCust.getCrtCzy())){
			Employee crtCzy=employeeService.get(Employee.class, resrCust.getCrtCzy());
			Department department=employeeService.get(Department.class, crtCzy.getDepartment());
			resrCust.setCrtCZYDept(crtCzy.getDepartment());
			resrCust.setCrtCzyDeptDescr(department.getDesc2());
			resrCust.setCrtCzyDescr(crtCzy.getNameChi());
		}
		builder = builderService.get(Builder.class, resrCust.getBuilderCode());
		resrCust.setBusinessManDescr(businessMan==null?"":businessMan.getNameChi());
		resrCust.setBuilderDescr(builder==null?null:builder.getDescr());
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDepartment2(businessMan==null?"":businessMan.getDepartment2());
		resrCust.setType(rc.getType());
		resrCust.setHasMobileAuth("1");
		
		//????????????????????????????????????????????????????????????????????????????????????????????????
		if("2".equals(resrCust.getType()) && !czybh.equals(resrCust.getCrtCzy()) && !czybh.equals(resrCust.getBusinessMan()) 
				&& !czybmService.hasGNQXByCzybh(czybh,"0223","????????????????????????")){
			if(StringUtils.isNotBlank(resrCust.getMobile1()) && resrCust.getMobile1().length() >= 4){
				resrCust.setMobile1(resrCust.getMobile1().substring(0, 3)+"****"+resrCust.getMobile1().substring(resrCust.getMobile1().length()-4, resrCust.getMobile1().length()));
			}
			resrCust.setHasMobileAuth("0");
		}

		ResrCustPool resrCustPool = new ResrCustPool();
		if(StringUtils.isNotBlank(resrCust.getResrCustPoolNo())){
			resrCustPool = this.resrCustService.get(ResrCustPool.class, resrCust.getResrCustPoolNo());
			if(resrCustPool != null && "1".equals(resrCustPool.getIsVirtualPhone())
					&& !this.getUserContext(request).getCzybh().equals(resrCust.getCrtCzy())){
				if(resrCust.getMobile1().length()<=4){
					resrCust.setMobile1("****");
				}else{
					resrCust.setMobile1(resrCust.getMobile1().substring(0, 3)+"****"+resrCust.getMobile1().substring(resrCust.getMobile1().length()-4, resrCust.getMobile1().length()));
				}
			}
		}
			
		// ??????????????????????????????????????????????????????????????????????????????????????????????????????
		String hiddenCustResStatus = "";
		if ("0,1,2,4".indexOf(resrCust.getCustResStat()) > -1) {
            hiddenCustResStatus = "3,5,6,7,8";
        }
		
		return new ModelAndView("admin/design/resrCust/resrCust_update")
			.addObject("resrCust",resrCust)
			.addObject("hiddenCustResStatus", hiddenCustResStatus);
	}
	
	/**
	 *?????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goDispatch")
	public ModelAndView goDispatch(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("?????????????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDispatchDate(new Date());
		return new ModelAndView("admin/design/resrCust/resrCust_dispatch")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *?????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping(value={"/goCustCancel","/goCustTeamCancel"})
	public ModelAndView goCustCancel(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("?????????????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/design/resrCust/resrCust_cancel")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *?????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goCustTag")
	public ModelAndView goCustTag(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("?????????????????????????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		String htmlStr="";
		List<Map<String, Object>> tagGroupList=custTagService.getCustTagGroup();
		for (int i = 0; i < tagGroupList.size(); i++) {
			Map<String, Object> tagGroupMap=tagGroupList.get(i);
			List<Map<String, Object>> tagList=custTagService.getCustTagByGroupPk(
					Integer.parseInt(tagGroupMap.get("TagGroupPk").toString()));
			String groupInfo=tagGroupMap.get("Color").toString()+","+tagGroupMap.get("IsMulti").toString();
			htmlStr+="<input type='hidden' value='"+groupInfo+"' id='"+tagGroupMap.get("TagGroupPk").toString()+"'>";
			htmlStr+="<div class='tagGroup'><div class='rowBox'><span>"+tagGroupMap.get("TagGroupDescr").toString()+"</span><div>" +
			"<span style='margin:10px'>????????????</span><select name='batchPlan' style='width:100px;border-radius:3px' value='1' onchange='changeBatchPlan(this)'>	" +
			"<option value='1'>????????????</option><option value='2'>??????????????????</option><option value='3'>??????????????????</option></select></div></div>";
			for (int j = 0; j < tagList.size(); j++) {
				Map<String, Object> tagMap=tagList.get(j);
				htmlStr+="<span class='tag' name='tagSpan' style='background-color:#f5f5f5;border:1px solid #e6e5e5;color:#aba5a5' onclick='selTag(this)'><span>"+tagMap.get("tagDescr").toString()+"</span><input type='hidden' value='"+tagMap.get("tagPk").toString()+"'></span>";
			}
			htmlStr+="</div>";
		}
		return new ModelAndView("admin/design/resrCust/resrCust_setCustTag")
			.addObject("resrCust",resrCust)
			.addObject("custNum", resrCust.getCodes().split(",").length)
			.addObject("htmlStr", htmlStr);
	}
	
	/**
	 *???????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goShareCust")
	public ModelAndView goShareCust(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("???????????????????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setShareDate(new Date());
		return new ModelAndView("admin/design/resrCust/resrCust_shareCust")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *??????????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goUpdateShareCzy")
	public ModelAndView goUpdateShareCzy(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("??????????????????????????????");
		return new ModelAndView("admin/design/resrCust/resrCust_shareCust_update")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *????????????Excel????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goAddFromExcel")
	public ModelAndView goAddFromExcel(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("????????????Excel????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		
		resrCust.setDefPoolNo(resrCustService.getDefPoolNoByCzybm(this.getUserContext(request).getCzybh()));
		return new ModelAndView("admin/design/resrCust/resrCust_addFromExcel")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *????????????Excel????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goAddEmp")
	public ModelAndView goAddEmp(HttpServletRequest request ,
			HttpServletResponse response , String numbers,String descrs) throws Exception{
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/design/resrCust/resrCust_addEmp")
			.addObject("numbers",numbers).addObject("descrs", descrs);
	}
	
	/**
	 *???????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping(value={"/goCustTeamChangeBusiness","/goCustChangeBusiness"})
	public ModelAndView goCustTeamChangeBusiness(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("???????????????????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDispatchDate(new Date());
		return new ModelAndView("admin/design/resrCust/resrCust_changeBusiness")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *???????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping(value={"/goCustGiveUp","/goCustTeamGiveUp"})
	public ModelAndView goCustTeamChangeGiveUp(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust) throws Exception{
		logger.debug("???????????????????????????");
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDispatchDate(new Date());
		return new ModelAndView("admin/design/resrCust/resrCust_giveUp")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *?????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping(value={"/goView","/goCustTeamView"})
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("?????????????????????");
		Builder builder=null;
		Customer customer=null;
		String czybh=this.getUserContext(request).getCzybh();
		String custCode=resrCustService.getMapperCustCode(rc.getCode());
		if(null!=custCode){
			customer=resrCustService.get(Customer.class, custCode);
		}
		ResrCust resrCust =resrCustService.get(ResrCust.class, rc.getCode());
		Employee businessMan=employeeService.get(Employee.class, resrCust.getBusinessMan());
		if(StringUtils.isNotBlank(resrCust.getCrtCzy())){
			Employee crtCzy=employeeService.get(Employee.class, resrCust.getCrtCzy());
			resrCust.setCrtCZYDept(crtCzy.getDepartment());
			resrCust.setCrtCzyDescr(crtCzy.getNameChi());
			Department department=employeeService.get(Department.class, crtCzy.getDepartment());
			resrCust.setCrtCzyDeptDescr(department.getDesc2());
		}
		builder = builderService.get(Builder.class, resrCust.getBuilderCode());
		resrCust.setBuilderCode(rc.getBuilderCode());
		resrCust.setBusinessManDescr(businessMan==null?"":businessMan.getNameChi());
		resrCust.setBuilderDescr(builder==null?null:builder.getDescr());
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDepartment2(businessMan==null?"":businessMan.getDepartment2());
		resrCust.setType(rc.getType());
		
		//????????????????????????????????????????????????????????????????????????????????????????????????
		if("2".equals(resrCust.getType()) && !czybh.equals(resrCust.getCrtCzy()) && !czybh.equals(resrCust.getBusinessMan())
				&& !czybmService.hasGNQXByCzybh(czybh,"0223","????????????????????????")){
			if(resrCust.getMobile1().length()<=4){
				resrCust.setMobile1("****");
			}else{
				resrCust.setMobile1(resrCust.getMobile1().substring(0, 3)+"****"+resrCust.getMobile1().substring(resrCust.getMobile1().length()-4, resrCust.getMobile1().length()));
			}
		}
		ResrCustPool resrCustPool = new ResrCustPool();
		if(StringUtils.isNotBlank(resrCust.getResrCustPoolNo())){
			resrCustPool = this.resrCustService.get(ResrCustPool.class, resrCust.getResrCustPoolNo());
			if(resrCustPool != null && "1".equals(resrCustPool.getIsVirtualPhone())
					&& !this.getUserContext(request).getCzybh().equals(resrCust.getCrtCzy())){
				if(resrCust.getMobile1().length()<=4){
					resrCust.setMobile1("****");
				}else{
					resrCust.setMobile1(resrCust.getMobile1().substring(0, 3)+"****"+resrCust.getMobile1().substring(resrCust.getMobile1().length()-4, resrCust.getMobile1().length()));
				}
			}
		}
		return new ModelAndView("admin/design/resrCust/resrCust_detail")
			.addObject("resrCust",resrCust).addObject("customer", customer)
			.addObject("ossCdnAccessUrl", OssConfigure.cdnAccessUrl + "/custCallRecord");
	}
	
	/**
	 *ResrCust????????????????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goDelete")
	public ModelAndView goMobileView(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("?????????????????????");
		Employee employee=null;
		Builder builder=null;
		ResrCust resrCust= resrCustService.get(ResrCust.class, rc.getCode());
		resrCust.setBuilderCode(rc.getBuilderCode());
		employee = employeeService.get(Employee.class, resrCust.getBusinessMan());
		builder = builderService.get(Builder.class, resrCust.getBuilderCode());
		resrCust.setBusinessManDescr(employee==null?null:employee.getNameChi());
		resrCust.setBuilderDescr(builder==null?null:builder.getDescr());
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		
		ResrCustPool resrCustPool = new ResrCustPool();
		if(StringUtils.isNotBlank(resrCust.getResrCustPoolNo())){
			resrCustPool = this.resrCustService.get(ResrCustPool.class, resrCust.getResrCustPoolNo());
			if(resrCustPool != null && "1".equals(resrCustPool.getIsVirtualPhone())){
				if(resrCust.getMobile1().length()<=4){
					resrCust.setMobile1("****");
				}else{
					resrCust.setMobile1(resrCust.getMobile1().substring(0, 3)+"****"+resrCust.getMobile1().substring(resrCust.getMobile1().length()-4, resrCust.getMobile1().length()));
				}
			}
		}
		
		return new ModelAndView("admin/design/resrCust/resrCust_mobileDetail")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 *ResrCust????????????????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goViewLog")
	public ModelAndView goViewLog(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("?????????????????????");
		return new ModelAndView("admin/design/resrCust/resrCust_viewLog")
			.addObject("resrCust",rc);
	}
	
	/**
	 *???????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goCon")
	public ModelAndView goCon(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("?????????????????????");
		return new ModelAndView("admin/design/resrCust/resrCust_con")
			.addObject("resrCust",rc)
			.addObject("ossCdnAccessUrl", OssConfigure.cdnAccessUrl + "/custCallRecord");
	}
	
	/**
	 *????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goUpdateCrtCzy")
	public ModelAndView goUpdateCrtCzy(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("??????????????????????????????");
		return new ModelAndView("admin/design/resrCust/resrCust_updateCrtCzy")
			.addObject("resrCust",rc);
	}
	
	/**
	 *???????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goViewTask")
	public ModelAndView goViewTask(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("???????????????????????????");
		rc.setDateFrom(new Date());
		rc.setDateTo(new Date());
		return new ModelAndView("admin/design/resrCust/resrCust_viewTask")
			.addObject("resrCust",rc);
	}
	
	/**
	 *?????????????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goFailedTask")
	public ModelAndView goFailedTask(HttpServletRequest request ,
			HttpServletResponse response , ResrCustExcelFailed resrCustExcelFailed) throws Exception{
		logger.debug("?????????????????????????????????");
		return new ModelAndView("admin/design/resrCust/resrCust_viewFailedTask")
			.addObject("resrCustExcelFailed",resrCustExcelFailed);
	}
	
	/**
	 * ???????????????CustCon??????
	 * @return
	 */
	@RequestMapping("/goUpdateCon")
	public ModelAndView goUpdateCon(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????CustCon??????");
		CustCon custCon = null;
		if (StringUtils.isNotBlank(id)){
			custCon = resrCustService.get(CustCon.class, Integer.parseInt(id));
		}else{
			custCon = new CustCon();
		}
		if (StringUtils.isNotBlank(custCon.getConMan())){
			Employee employee = employeeService.get(Employee.class, custCon.getConMan());
			if (employee!=null){
				custCon.setConManDescr(employee.getNameChi());
			}
		}
		return new ModelAndView("admin/design/resrCust/resrCust_updateCon")
			.addObject("custCon", custCon);
	}
	
	/**
	 * ??????CustCon
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdateCon")
	public void doUpdateCon(HttpServletRequest request, HttpServletResponse response, CustCon cc){
		logger.debug("??????CustCon??????");
		try{
			CustCon custCon = resrCustService.get(CustCon.class, cc.getPk());
			custCon.setRemarks(cc.getRemarks());
			custCon.setConDate(cc.getConDate());
			custCon.setConMan(cc.getConMan());
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCon.setActionLog("EDIT");
			custCon.setConWay(cc.getConWay());
			custCon.setNextConDate(cc.getNextConDate());
			resrCustService.update(custCon);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustCon??????");
		}
	}
	
	/**
	 *ResrCust??????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust){
		logger.debug("??????????????????????????????");
		try{
			String resrCustCode = resrCustService.getSeqNo("tResrCust");
			resrCust.setCode(resrCustCode);
			resrCust.setExpired("F");
			resrCust.setActionLog("Add");
			resrCust.setLastUpdate(new Date());
			resrCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			resrCust.setCrtDate(new Date());
			resrCust.setPublicResrLevel("0");

			//????????????????????????????????????????????????????????????????????????????????????????????????
			Employee businessMan=resrCustService.get(Employee.class,resrCust.getBusinessMan());
			if(businessMan!=null){
				if(StringUtils.isNotBlank(businessMan.getDepartment3())){
					Department2 department2=resrCustService.get(Department2.class,businessMan.getDepartment2());
					resrCust.setDepartment(department2.getDepartment());
				}else{
					resrCust.setDepartment(businessMan.getDepartment());
				}
			}else{
				if(StringUtils.isNotBlank(resrCust.getResrCustPoolNo())){
					ResrCustPool resrCustPool = getPoolType(request, response, resrCust);
					if(resrCustPool == null || "0".equals(resrCustPool.getDispatchRule())){
						ServletUtils.outPrintFail(request, response, "?????????????????????");
						return;
					}
				} else {
					ServletUtils.outPrintFail(request, response, "?????????????????????");
					return;
				}
			}
			resrCust.setValidDispatchCount(0);
			resrCust.setNoValidCount(0);
			this.resrCustService.save(resrCust);
			
			ResrCustLog resrCustLog=new ResrCustLog();
			resrCustLog.setResrCode(resrCust.getCode());
			resrCustLog.setActionLog("Add");
			resrCustLog.setAddress(resrCust.getAddress());
			resrCustLog.setBuilderCode(resrCust.getBuilderCode());
			resrCustLog.setBusinessMan(resrCust.getBusinessMan());
			resrCustLog.setDescr(resrCust.getDescr());
			resrCustLog.setExpired("F");
			resrCustLog.setGender(resrCust.getGender());
			resrCustLog.setStatus(resrCust.getStatus());
			resrCustLog.setMobile1(resrCust.getMobile1());
			resrCustLog.setLastUpdate(new Date());
			resrCustLog.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.resrCustLogService.save(resrCustLog);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	/**
	 *ResrCust??????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request ,
			HttpServletResponse response , ResrCust newResrCust){
		logger.debug("??????????????????????????????");
		ResrCust oldResrCust =resrCustService.get(ResrCust.class, newResrCust.getCode());
		Employee lastUpdatedBy=employeeService.get(Employee.class,this.getUserContext(request).getEmnum() );
		try{
			newResrCust.setLastUpdate(new Date());
			newResrCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			newResrCust.setActionLog("Edit");
			newResrCust.setCrtCzy(oldResrCust.getCrtCzy());
			newResrCust.setCrtCZYDept(oldResrCust.getCrtCZYDept());
			newResrCust.setPublicResrLevel(oldResrCust.getPublicResrLevel());
			newResrCust.setDepartment(oldResrCust.getDepartment());
			newResrCust.setCancelRsn(oldResrCust.getCancelRsn());
			newResrCust.setCancelRemarks(oldResrCust.getCancelRemarks());
			newResrCust.setCancelDate(oldResrCust.getCancelDate());
			newResrCust.setShareDate(oldResrCust.getShareDate());
			newResrCust.setShareCzy(oldResrCust.getShareCzy());
			newResrCust.setValidDispatchCount(oldResrCust.getValidDispatchCount());
			newResrCust.setNoValidCount(oldResrCust.getNoValidCount());
			if(StringUtils.isBlank(newResrCust.getResrCustPoolNo())){
				newResrCust.setResrCustPoolNo(oldResrCust.getResrCustPoolNo());
			}
			if(newResrCust.getMobile1().split("\\*").length > 1){
				newResrCust.setMobile1(oldResrCust.getMobile1());
			}
			
			if("0".equals(newResrCust.getHasMobileAuth())){
				newResrCust.setMobile1(oldResrCust.getMobile1());
			}
			
			if(StringUtils.isBlank(newResrCust.getExpired())){
				newResrCust.setExpired("F");
			}
			this.resrCustService.update(newResrCust);
			
			ResrCustLog resrCustLog=new ResrCustLog();
			resrCustLog.setResrCode(newResrCust.getCode());
			resrCustLog.setActionLog("EDIT");
			resrCustLog.setAddress(newResrCust.getAddress());
			resrCustLog.setBuilderCode(newResrCust.getBuilderCode());
			resrCustLog.setBusinessMan(newResrCust.getBusinessMan());
			resrCustLog.setDescr(newResrCust.getDescr());
			resrCustLog.setExpired("F");
			resrCustLog.setGender(newResrCust.getGender());
			resrCustLog.setStatus(newResrCust.getStatus());
			resrCustLog.setMobile1(newResrCust.getMobile1());
			resrCustLog.setLastUpdate(new Date());
			resrCustLog.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.resrCustLogService.save(resrCustLog);
			
			//????????????????????????
			CustCon custCon=new CustCon();
			String custConRemarks="?????????????????????\n";//???????????????
			String ignoreArr[]={"actionLog","dispatchDate","lastUpdate","department2","czybh",
								"crtDate","lastUpdatedBy","expired","department","publicResrLevel"};//??????????????????
			Map<String, List<Object>> diffMap=Reflections.compareFields(oldResrCust, newResrCust, ignoreArr);//?????????????????????????????????????????????????????????
			if(diffMap!=null){
				for(Map.Entry<String, List<Object>> entry:diffMap.entrySet()){//???????????????????????????map?????????????????????
					String descr=DescrUtil.getDescr(newResrCust, entry.getKey());//????????????????????????????????????????????????
					String newValue=entry.getValue().get(1).toString();//???????????????
					String newNote=NoteUtil.getNoteByValue(newResrCust,entry.getKey(), newValue); //??????????????????????????????????????????
					custConRemarks+=descr+" : "+newNote+"???\n";
				}
			}
			custCon.setRemarks(custConRemarks);
			custCon.setType("2");
			custCon.setConDate(new Date());
			custCon.setConMan(lastUpdatedBy.getNumber());
			custCon.setResrCustCode(newResrCust.getCode());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custCon.setCustCode("");
			this.resrCustLogService.save(custCon);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	/**
	 *??????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doDispatch")
	public void doDispatch(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc){
		logger.debug("??????");
		try{
			String codes[]=rc.getCodes().split(",");
			for(int i=0;i<codes.length;i++){
				ResrCust resrCust =resrCustService.get(ResrCust.class, codes[i]);
				resrCust.setBusinessMan(rc.getBusinessMan());
				resrCust.setDispatchDate(rc.getDispatchDate());
				resrCust.setLastUpdate(new Date());
				resrCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				resrCust.setActionLog("Edit");
				resrCust.setExpired(resrCust.getExpired());
				if("2".equals(resrCust.getCustResStat())){// ????????????????????????????????????????????? modify by xzy Date:2021???2???23???17:06:13
					resrCust.setCustResStat("0");
				} else {
					resrCust.setCustResStat("1");
				}
				
				//????????????????????????????????????????????????????????????????????????????????????????????????
				Employee businessMan=resrCustService.get(Employee.class,resrCust.getBusinessMan());
				if(StringUtils.isNotBlank(businessMan.getDepartment3())){
					Department2 department2=resrCustService.get(Department2.class,businessMan.getDepartment2());
					resrCust.setDepartment(department2.getDepartment());
				}else{
					resrCust.setDepartment(businessMan.getDepartment());
				}
				resrCust.setPublicResrLevel("0");
				
				this.resrCustService.update(resrCust);
				
				Employee lastUpdatedBy=employeeService.get(Employee.class,this.getUserContext(request).getEmnum() );
				CustCon custCon=new CustCon();
				custCon.setRemarks("?????????"+businessMan.getNameChi());
				custCon.setType("2");
				custCon.setConDate(new Date());
				custCon.setConMan(lastUpdatedBy.getNumber());
				custCon.setResrCustCode(resrCust.getCode());
				custCon.setExpired("F");
				custCon.setActionLog("ADD");
				custCon.setLastUpdate(new Date());
				custCon.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custCon.setCustCode("");
				this.resrCustLogService.save(custCon);
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 *????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doCustTeamChangeBusiness")
	public void doCustTeamChangeBusiness(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc){
		logger.debug("????????????");
		try{
			
			rc.setCzybh(getUserContext(request).getCzybh());
			Result result = resrCustService.doCustTeamChangeBusiness(rc);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 *????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doCustTeamGiveUp")
	public void doCustTeamGiveUp(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc){
		logger.debug("????????????");
		try{
			rc.setCzybh(getUserContext(request).getCzybh());
			Result result = resrCustService.doCustTeamGiveUp(rc);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 *????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doCustCancel")
	public void doCustCancel(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc){
		logger.debug("????????????");
		try{
			rc.setCzybh(getUserContext(request).getCzybh());
			Result result = resrCustService.doCancelCust(rc);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ??????CustCon
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSaveCon")
	public void doSaveCon(HttpServletRequest request, HttpServletResponse response, CustCon custCon){
		logger.debug("??????CustCon??????");
		try{
			custCon.setCustCode("");
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			this.resrCustService.save(custCon);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustCon??????");
		}
	}
	
	/**
	 * ???????????????
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doUpdateCrtCzy")
	public void doUpdateCrtCzy(HttpServletRequest request, HttpServletResponse response, ResrCust resrCust){
		logger.debug("???????????????");
		try{
			resrCust.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.resrCustService.doUpdateCrtCzy(resrCust);
			
			Employee crtCzy=employeeService.get(Employee.class,resrCust.getCrtCzy());
			String codes[]=resrCust.getCodes().split(",");
			//????????????????????????????????????
			for(int i=0;i<codes.length;i++){
				ResrCust rc =resrCustService.get(ResrCust.class, codes[i]);
				rc.setCrtCzy(resrCust.getCrtCzy());
				rc.setLastUpdate(new Date());
				rc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				rc.setActionLog("Edit");
				this.resrCustService.update(rc);
				
				CustCon custCon=new CustCon();
				custCon.setRemarks("??????????????????"+crtCzy.getNameChi());
				custCon.setType("2");
				custCon.setConDate(new Date());
				custCon.setConMan(this.getUserContext(request).getEmnum());
				custCon.setResrCustCode(rc.getCode());
				custCon.setExpired("F");
				custCon.setActionLog("ADD");
				custCon.setLastUpdate(new Date());
				custCon.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custCon.setCustCode("");
				this.resrCustService.save(custCon);
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "???????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goConJqGrid(HttpServletRequest request,
			HttpServletResponse response,ResrCust resrCust) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		if(czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0214","????????????????????????")){
			resrCust.setHasConAuth("1");
		}
		resrCustService.goConJqGrid(page, resrCust);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**???????????????????????????????????????????????????
	 * @param customer
	 * @param rule
	 * @return
	 */
	public String checkAddress(ResrCust resrCust){
		String result = "";
		if (resrCust!=null){
			String str = resrCust.getBuilderCode();
			if (StringUtils.isNotBlank(str)){
				Builder builder = builderService.get(Builder.class, str);
				if (builder!=null){
					if ("2".equals(builder.getAddressType())){
						//?????????????????????????????????
						int i = resrCust.getAddress().indexOf(builder.getDescr());
						if (i!=0){
							result = "?????????????????????????????????";
						}
					}else if("3".equals(builder.getAddressType())){
						//??????????????????????????????????????????????????????????????????#???#?????????
						int i = resrCust.getAddress().indexOf(builder.getDescr());
						if (i==0){
							String regex = "^[0-9a-zA-Z]+#[0-9a-zA-Z]+$";
							String ss = resrCust.getAddress().substring(builder.getDescr().length());
							Pattern pattern = Pattern.compile(regex);
							Matcher m = pattern.matcher(ss);
							if(!m.find()){
								result = "??????????????????????????????????????????????????????????????????#???#?????????";
							}
						}else{
							result = "??????????????????????????????????????????????????????????????????#???#?????????";
						}
					}
				}
			}
		}
		return result;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			ResrCust resrCust){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		if(czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0214","????????????????????????")){
			resrCust.setHasCreateAuth("1");
		}
		if(czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0214","????????????????????????")){
			resrCust.setHasConAuth("1");
		}
		resrCustService.findPageBySql(page, resrCust);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"?????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * excel????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadExcel")
	@ResponseBody
	public void loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		ExcelImportUtils<ResrCust> eUtils=new ExcelImportUtils<ResrCust>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		String importPlan="";
		String resrCustPoolNo="";
		String fileName="";
		String emps="";
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
				// ??????????????? ????????????
				String fieldName = obit.getFieldName();
				if ("importPlan".equals(fieldName)){
					importPlan=obit.getString();
				}
				
				if ("resrCustPoolNo".equals(fieldName)){
					resrCustPoolNo = obit.getString();
				}
				if ("emps".equals(fieldName)){
					emps=obit.getString();
				}
				
				if ("file".equals(fieldName)){
					fileName=obit.getName();
					in=obit.getInputStream();
				}
		}
		try {
			if("1".equals(importPlan)){//???????????????
				emps=uc.getCzybh();
			}else if("2".equals(importPlan)){//???????????????
				emps="";
			}
			List<?> result=eUtils.importExcelNew(in,ResrCust.class,titleList);
			ExcelTask excelTask=new ExcelTask(request.getServletPath(),"1", "1", fileName, importPlan, new Date(), getUserContext(request).getCzybh(), "F", "ADD",emps,resrCustPoolNo);
			excelTaskService.save(excelTask);
			excelTask.setPk(excelTaskService.getMaxExcelTaskPk());//???????????????????????????pk
			excelTaskService.addExcelList(result, excelTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *???????????????
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSaveCols")
	public void doSaveCols(HttpServletRequest request, 
			HttpServletResponse response, ResrCust resrCust){
		String colModel = request.getParameter("jsonString");
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "resrCustSet");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		if (FileHelper.strToFile(filePath, fileName, arryCols.toString())){
			ServletUtils.outPrintSuccess(request, response, "???????????????");
		}else{
			ServletUtils.outPrintFail(request, response, "???????????????");
		}
		
	}
	
	@RequestMapping("/doResetCols")
	public void doResetCols(HttpServletRequest request, 
			HttpServletResponse response,ResrCust resrCust){
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "resrCustSet");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		
		File file = new File(filePath+"\\"+fileName);
		
		if(file.exists()){
			file.delete();
		}
		ServletUtils.outPrintSuccess(request, response, "???????????????");
	}
	
	/**
	 *?????????????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/addCustCode")
	public ModelAndView addCustCode(HttpServletRequest request ,
			HttpServletResponse response , ResrCust rc) throws Exception{
		logger.debug("?????????????????????????????????");
		Builder builder=null;
		ResrCust resrCust =resrCustService.get(ResrCust.class, rc.getCode());
		Employee businessMan=employeeService.get(Employee.class, resrCust.getBusinessMan());
		if(StringUtils.isNotBlank(resrCust.getCrtCzy())){
			Employee crtCzy=employeeService.get(Employee.class, resrCust.getCrtCzy());
			Department department=employeeService.get(Department.class, crtCzy.getDepartment());
			resrCust.setCrtCZYDept(crtCzy.getDepartment());
			resrCust.setCrtCzyDeptDescr(department.getDesc2());
			resrCust.setCrtCzyDescr(crtCzy.getNameChi());
		}
		builder = builderService.get(Builder.class, resrCust.getBuilderCode());
		resrCust.setBuilderCode(rc.getBuilderCode());
		resrCust.setBusinessManDescr(businessMan==null?"":businessMan.getNameChi());
		resrCust.setBuilderDescr(builder==null?null:builder.getDescr());
		resrCust.setCzybh(this.getUserContext(request).getCzybh());
		resrCust.setDepartment2(businessMan==null?"":businessMan.getDepartment2());
		resrCust.setType(rc.getType());
		return new ModelAndView("admin/design/resrCust/resrCust_addCustCode")
			.addObject("resrCust",resrCust);
	}
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hasCustCode")
	@ResponseBody
	public String hasCustCode(HttpServletRequest request,
			HttpServletResponse response,String code) throws Exception {
		return resrCustService.hasCustCode(code);
	}
	
	/**
	 *????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doAddCustCode")
	public void doAddCustCode(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust){
		logger.debug("????????????????????????");
		String custResStat=resrCust.getCustResStat();
		try{
			Customer customer = resrCustService.get(Customer.class, resrCust.getCustCode());
			if("1".equals(customer.getStatus())){
				custResStat="3";
			}else if("2".equals(customer.getStatus())){
				custResStat="5";
			}else if("3".equals(customer.getStatus())){
				custResStat="6";
			}else if("4".equals(customer.getStatus())){
				custResStat="7";
			}else if("5".equals(customer.getStatus())){
				custResStat="8";
			}
			resrCust.setLastUpdatedBy(getUserContext(request).getCzybh());
			resrCust.setCustResStat(custResStat);
			this.resrCustService.doAddCustCode(resrCust) ;
			
			CustCon custCon=new CustCon();
			custCon.setRemarks("??????????????????");
			custCon.setType("2");
			custCon.setConDate(new Date());
			custCon.setConMan(this.getUserContext(request).getEmnum());
			custCon.setResrCustCode(resrCust.getCode());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custCon.setCustCode(resrCust.getCustCode());
			this.resrCustService.save(custCon);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 *????????????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doSetCustTag")
	public void doSetCustTag(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust){
		logger.debug("????????????????????????");
		try{
			resrCust.setCzybh(getUserContext(request).getCzybh());
			Result result =custTagService.doBatchSetCustTag(resrCust);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,"???????????????"+result.getInfo());
			}
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 *??????????????????
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doShareCust")
	public void doShareCust(HttpServletRequest request ,
			HttpServletResponse response , ResrCust resrCust){
		logger.debug("??????????????????");
		try{
			resrCust.setCzybh(getUserContext(request).getCzybh());
			Result result =resrCustService.doShareCust(resrCust);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,"???????????????"+result.getInfo());
			}
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ???????????????????????????
	 * 
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sourceByAuthority/{type}/{pCode}")
	@ResponseBody
	public JSONObject getSourceByAuthority(@PathVariable Integer type,
			@PathVariable String pCode, HttpServletRequest request) {
		UserContext uc = (UserContext) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT_KEY);
		List<Map<String, Object>> regionList = this.resrCustService.findSourceByAuthority(type, pCode, uc);
		return this.out(regionList, true);
	}
	
	@RequestMapping("/getPoolType")
	@ResponseBody
	public ResrCustPool getPoolType(HttpServletRequest request, HttpServletResponse response,ResrCust resrCust) {
		ResrCustPool resrCustPool = new ResrCustPool();
		if(StringUtils.isNotBlank(resrCust.getResrCustPoolNo())){
			resrCustPool = resrCustService.get(ResrCustPool.class, resrCust.getResrCustPoolNo());
			if(resrCustPool != null){
				return resrCustPool;
			}
		}
		return new ResrCustPool();
	}
}
