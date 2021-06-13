package com.house.home.web.controller.salary;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Position;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.service.salary.SalaryEmpService;

@Controller
@RequestMapping("/admin/salaryEmp")
public class SalaryEmpController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryEmpController.class);

	@Autowired
	private SalaryEmpService salaryEmpService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, SalaryEmp salaryEmp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salaryEmpService.findPageBySql(page, salaryEmp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBankJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBankJqGrid(HttpServletRequest request,
			HttpServletResponse response, SalaryEmp salaryEmp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salaryEmpService.goBankJqGrid(page, salaryEmp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goEmpSyncJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goEmpSyncJqGrid(HttpServletRequest request,
			HttpServletResponse response, SalaryEmp salaryEmp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salaryEmpService.goEmpSyncJqGrid(page, salaryEmp);		
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getSalaryEmp")
	@ResponseBody
	public JSONObject getSalaryEmp(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		SalaryEmp salaryEmp = salaryEmpService.get(SalaryEmp.class, id);
		if(salaryEmp == null){
			return this.out("系统中不存在code="+id+"的员工信息", false);
		}
		return this.out(salaryEmp, true);
	}
	
	/**
	 * 员工列表code
	 * 
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response,SalaryEmp salaryEmp) throws Exception {
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_code").addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * SalaryEmp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_list");
	}

	/**
	 * 跳转到新增SalaryEmp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			SalaryEmp salaryEmp){
		logger.debug("跳转到新增SalaryEmp页面");
		salaryEmp.setCategory("1");
		salaryEmp.setM_umState("A");
		salaryEmp.setSalaryStatus("1");
		salaryEmp.setWeight1(0.0);
		salaryEmp.setWeight2(0.0);
		salaryEmp.setWeight3(0.0);
		salaryEmp.setWeight4(0.0);
		salaryEmp.setBasicSalarySetMode("2");
		salaryEmp.setWorkingDays(26);
		salaryEmp.setPosiSalary(0.0);
		salaryEmp.setSkillSubsidy(0.0);
		salaryEmp.setOtherBonuse(0.0);
		salaryEmp.setPerfBonuse(0.0);
		salaryEmp.setStarSubsidy(0.0);
		salaryEmp.setOtherSubsidy(0.0);
		salaryEmp.setIsBasicSalaryPayment("0");
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_save")
			.addObject("salaryEmp", salaryEmp);
	}
	/**
	 * 跳转到修改SalaryEmp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String empCode){
		logger.debug("跳转到修改SalaryEmp页面");
		SalaryEmp salaryEmp = salaryEmpService.get(SalaryEmp.class, empCode);
		salaryEmp.setM_umState("M");
		
		List<Map<String, Object>> cmpList =salaryEmpService.getPayCmp(empCode);
		if(cmpList.size()>0 && cmpList!=null){
			Map<String, Object> map =cmpList.get(0);
			salaryEmp.setPayCmp1(map.get("PayCmp1").toString());
			salaryEmp.setPayCmp2(map.get("PayCmp2").toString());
			salaryEmp.setPayCmp3(map.get("PayCmp3").toString());
			salaryEmp.setPayCmp4(map.get("PayCmp4").toString());
			salaryEmp.setWeight1(Double.parseDouble(map.get("Weight1").toString()));
			salaryEmp.setWeight2(Double.parseDouble(map.get("Weight2").toString()));
			salaryEmp.setWeight3(Double.parseDouble(map.get("Weight3").toString()));
			salaryEmp.setWeight4(Double.parseDouble(map.get("Weight4").toString()));
		}
		
		List<Map<String, Object>> schemeList =salaryEmpService.getSalaryScheme(empCode);
		if(schemeList.size()>0 && schemeList!=null){
			salaryEmp.setSalaryScheme(schemeList.get(0).get("Schemes").toString());
		}
		
		Position position=new Position();
		if(StringUtils.isNotBlank(salaryEmp.getPosition())){
			position=salaryEmpService.get(Position.class, salaryEmp.getPosition());
		}
		
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_save")
			.addObject("salaryEmp", salaryEmp).addObject("position", position);
	}
	
	/**
	 * 跳转到查看SalaryEmp页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String empCode){
		logger.debug("跳转到查看SalaryEmp页面");
		SalaryEmp salaryEmp = salaryEmpService.get(SalaryEmp.class, empCode);
		salaryEmp.setM_umState("V");
		
		List<Map<String, Object>> cmpList =salaryEmpService.getPayCmp(empCode);
		if(cmpList.size()>0 && cmpList!=null){
			Map<String, Object> map =cmpList.get(0);
			salaryEmp.setPayCmp1(map.get("PayCmp1").toString());
			salaryEmp.setPayCmp2(map.get("PayCmp2").toString());
			salaryEmp.setPayCmp3(map.get("PayCmp3").toString());
			salaryEmp.setPayCmp4(map.get("PayCmp4").toString());
			salaryEmp.setWeight1(Double.parseDouble(map.get("Weight1").toString()));
			salaryEmp.setWeight2(Double.parseDouble(map.get("Weight2").toString()));
			salaryEmp.setWeight3(Double.parseDouble(map.get("Weight3").toString()));
			salaryEmp.setWeight4(Double.parseDouble(map.get("Weight4").toString()));
		}
		
		List<Map<String, Object>> schemeList =salaryEmpService.getSalaryScheme(empCode);
		if(schemeList.size()>0 && schemeList!=null){
			salaryEmp.setSalaryScheme(schemeList.get(0).get("Schemes").toString());
		}
		Position position = new Position();
		if(StringUtils.isNotBlank(salaryEmp.getPosition())){
			position=salaryEmpService.get(Position.class, salaryEmp.getPosition());
		}
		
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_save")
				.addObject("salaryEmp", salaryEmp).addObject("position", position);
	}
	
	/**
	 * 跳转到新增银行卡信息页面
	 * @return
	 */
	@RequestMapping("/goAddBank")
	public ModelAndView goAddBank(HttpServletRequest request, HttpServletResponse response,SalaryEmp salaryEmp){
		logger.debug("跳转到新增SalaryEmp页面");
		String frameName="addBank";
		if("M".equals(salaryEmp.getM_umState())){
			frameName="updateBank";
		}else if("V".equals(salaryEmp.getM_umState())){
			frameName="viewBank";
		}else{
			salaryEmp.setActName(salaryEmp.getActName().replaceAll("\\d+", ""));//去除数字
		}
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_bank_add")
			.addObject("salaryEmp", salaryEmp).addObject("frameName", frameName);
	}
	
	/**
	 * 跳转查看银行卡信息页面
	 * @return
	 */
	@RequestMapping("/goViewBank")
	public ModelAndView goViewBank(HttpServletRequest request, HttpServletResponse response, 
			SalaryEmp salaryEmp){
		logger.debug("跳转查看银行卡信息页面");
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_viewBank")
			.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转查看发放方案页面
	 * @return
	 */
	@RequestMapping("/goViewPayMode")
	public ModelAndView goViewPayMode(HttpServletRequest request, HttpServletResponse response, 
			String empCode){
		logger.debug("跳转查看发放方案页面");
		SalaryEmp salaryEmp = salaryEmpService.get(SalaryEmp.class, empCode);
		List<Map<String, Object>> list =salaryEmpService.getPayCmp(empCode);
		if(list.size()>0){
			Map<String, Object> map =list.get(0);
			salaryEmp.setPayCmp1(map.get("PayCmp1").toString());
			salaryEmp.setPayCmp2(map.get("PayCmp2").toString());
			salaryEmp.setPayCmp3(map.get("PayCmp3").toString());
			salaryEmp.setPayCmp4(map.get("PayCmp4").toString());
			salaryEmp.setWeight1(Double.parseDouble(map.get("Weight1").toString()));
			salaryEmp.setWeight2(Double.parseDouble(map.get("Weight2").toString()));
			salaryEmp.setWeight3(Double.parseDouble(map.get("Weight3").toString()));
			salaryEmp.setWeight4(Double.parseDouble(map.get("Weight4").toString()));
		}
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_viewPayMode")
			.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 跳转员工信息同步页面
	 * @return
	 */
	@RequestMapping("/goEmpSync")
	public ModelAndView goEmpSync(HttpServletRequest request, HttpServletResponse response, 
			SalaryEmp salaryEmp){
		logger.debug("跳转到员工信息同步页面");
		return new ModelAndView("admin/salary/salaryEmp/salaryEmp_empSync")
			.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * 添加SalaryEmp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryEmp salaryEmp){
		logger.debug("添加SalaryEmp开始");
		try{
			salaryEmp.setLastUpdate(new Date());
			salaryEmp.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmp.setExpired("F");
			Result result = this.salaryEmpService.doSaveProc(salaryEmp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加SalaryEmp失败");
		}
	}
	
	/**
	 * 员工信息同步
	 * @param request
	 * @param response
	 * @param empCodes
	 */
	@RequestMapping("/doEmpSync")
	public void doEmpSync(HttpServletRequest request, HttpServletResponse response, String empCodes){
		logger.debug("员工信息同步");
		try{
			this.salaryEmpService.doEmpSync(empCodes);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "员工信息同步");
		}
	}

	/**
	 *SalaryEmp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SalaryEmp salaryEmp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		salaryEmpService.findPageBySql(page, salaryEmp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬人员档案_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	
	@ResponseBody
	@RequestMapping("/getIsFront")
	public String getIsFront(HttpServletRequest request, HttpServletResponse response, Integer pk){
		List<Map<String, Object>> list=salaryEmpService.getIsFront(pk);
		if(list.size()>0){
			return list.get(0).get("IsFront").toString();
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping("/getSalaryByLevel")
	public Map<String, Object> getSalaryByLevel(HttpServletRequest request, HttpServletResponse response, Integer pk){
		List<Map<String, Object>> list=salaryEmpService.getSalaryByLevel(pk);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 岗位类别，岗位级别联动
	 * 
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/posiByAuthority/{type}/{pCode}")
	@ResponseBody
	public JSONObject getPosiByAuthority(@PathVariable Integer type,
			@PathVariable String pCode, HttpServletRequest request) {
		UserContext uc = (UserContext) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT_KEY);
		List<Map<String, Object>> regionList = this.salaryEmpService.findPosiByAuthority(type, pCode, uc);
		return this.out(regionList, true);
	}
}
