package com.house.home.web.controller.basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.fileUpload.impl.SignInUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.service.basic.SignInService;

@Controller
@RequestMapping("/admin/signIn")
public class SignInController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SignInController.class);

	@Autowired
	private SignInService signInService;

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
			HttpServletResponse response, SignIn signIn) throws Exception {
		Date date=new Date();
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		SimpleDateFormat sdf = new SimpleDateFormat(request.getParameter("dateFrom"));
		if(signIn.getDateFrom()==null ){
			signIn.setDateFrom(DateUtil.startOfTheMonth(new Date()));
			signIn.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		}
		signInService.findPageBySql(page, signIn);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * 按签到人汇总
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_CZY")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid_CZY(HttpServletRequest request,
			HttpServletResponse response, SignIn signIn) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		signInService.findPageBySql_CZY(page, signIn);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * 按服务类型查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_SignInType2")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_SignInType2(HttpServletRequest request, HttpServletResponse response, SignIn signIn) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		signInService.goJqGrid_SignInType2(page, signIn);
		
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGrid_DetailSignInType2")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_DetailSignInType2(HttpServletRequest request, HttpServletResponse response, SignIn signIn) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		signInService.goJqGrid_DetailSignInType2(page, signIn);
		
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGridSignInPicList")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridSignInPicList(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		List<Map<String, Object>> list = signInService.goJqGridSignInPicList(page, no);
		page.setResult(list);		
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SignIn列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList_cx")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, SignIn signIn) throws Exception {
		
		signIn.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		signIn.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		
		return new ModelAndView("admin/basic/signIn/signIn_list").addObject("signIn", signIn);
	}
	/**
	 * SignIn查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/signIn/signIn_code");
	}

	/**
	 * 跳转到查看SignIn页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			SignIn signIn){
		logger.debug("跳转到查看SignIn页面");
		SignIn si = signInService.get(SignIn.class, signIn.getPk());
		si.setCustAddress(signIn.getCustAddress());
		si.setDepartment1(signIn.getDepartment1());
		si.setDepartment2(signIn.getDepartment2());
		if(si.getCustScore()!= null ){
			si.setCustScoreSignIn(si.getCustScore().toString()+"星");
		}
		return new ModelAndView("admin/basic/signIn/signIn_detail")
				.addObject("signIn", si);
	}

	/**
	 * 跳转到查看SignIn页面
	 * @return
	 */
	@RequestMapping("/goDetailGroupBySignInType2")
	public ModelAndView goDetailGroupBySignInType2(HttpServletRequest request, HttpServletResponse response, SignIn signIn){
		logger.debug("跳转到查看SignIn页面(按服务类型汇总)");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("department1", signIn.getDepartment1());
		map.put("department2", signIn.getDepartment2());
		map.put("errPosi", signIn.getErrPosi());
		map.put("signCzy", signIn.getSignCzy());
		map.put("dateFrom", signIn.getDateFrom());
		map.put("dateTo", signIn.getDateTo());
		map.put("signInType2", signIn.getSignInType2());
		return new ModelAndView("admin/basic/signIn/signIn_detailGroupBySignInType2").addObject("signIn", map);
	}
	
    @RequestMapping("/goDetailGroupBySignCZY")
    public ModelAndView goDetailGroupBySignCZY(HttpServletRequest request,
            HttpServletResponse response, SignIn signIn) {

        return new ModelAndView("admin/basic/signIn/signIn_detailGroupBySignCZY")
                .addObject("signIn", signIn);
    }
	
	/**
	 * SignIn导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, SignIn signIn){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		signInService.findPageBySql(page, signIn);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人签到查询结果_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/getSignInPic")
	@ResponseBody
	public Map<String, Object> getSignInPic(HttpServletRequest request, HttpServletResponse response,String photoName){
		Map<String, Object> map = new HashMap<String, Object>();
		SignInUploadRule rule = new SignInUploadRule(photoName);
		map.put("photoUrl", FileUploadUtils.getFileUrl(rule.getFullName()));
		return map;
	}
}
