package com.house.home.web.controller.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjProgCheckService;
import com.house.home.service.query.DdzhfxService;

@Controller
@RequestMapping("/admin/ddzhfx")
public class DdzhfxController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DdzhfxController.class);

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DdzhfxService ddzhfxService;
	
	@Autowired
	private CustTypeService custTypeService;
	
	protected List<String> ExceltitleList;
	
	/**
	 * 订单转化分析列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Customer customer=new Customer();
		customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(customer.getBeginDate());    
		calendar.add(Calendar.MONTH, -5);//当前时间前六个月的时间    
		customer.setBeginDate(calendar.getTime());
		customer.setEndDate(DateUtil.endOfTheMonth(new Date()));	
		Map<String, Object>  listbmqx= ddzhfxService.getbmqx(this.getUserContext(request).getCzybh());//获取操作员的部门权限
		customer.setCustRight(listbmqx.get("CustRight").toString());
		if (customer.getCustType() == null){
			List<CustType> listCustType = custTypeService.findByDefaultStatic(); //默认需要统计的客户类型
			String defaultStaticCustType = "";
			for (CustType custType: listCustType) {
				defaultStaticCustType = defaultStaticCustType + "," + custType.getCode();
			}
			if (!defaultStaticCustType.equals("")) {
				defaultStaticCustType = defaultStaticCustType.substring(1);
			}
			customer.setCustType(defaultStaticCustType);
		}
		return new ModelAndView("admin/query/ddzhfx/ddzhfx_list").addObject("customer", customer).
				addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	/**
	 * 订单转化分析    统计方式按明细	//
	 * @throws Exception
	 */
	@RequestMapping("/DdzhfxSelect")
	@ResponseBody
	public WebPage<Map<String,Object>> prjProgCheckMx(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (customer.getBeginDate()==null){
			customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			customer.setEndDate(new Date());	
		}
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		if("1".equals(customer.getRole())){
			customer.setRole("00");
		}else if ("2".equals(customer.getRole())){
			customer.setRole("01");
		}
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		customer.setBeginDate(new java.sql.Timestamp(customer.getBeginDate().getTime()));
		customer.setEndDate(new java.sql.Timestamp(customer.getEndDate().getTime()));
		ddzhfxService.findPageBySqlTJFS(page, customer,orderBy,direction);//TJFS 统计方式
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doExcelcheckDdzhfx")
	public void doExcelchecklktj(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		if (customer.getBeginDate()==null){
			customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			customer.setEndDate(new Date());	
		}
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		if("1".equals(customer.getRole())){
			customer.setRole("00");
		}else if ("2".equals(customer.getRole())){
			customer.setRole("01");
		}
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		customer.setBeginDate(new java.sql.Timestamp(customer.getBeginDate().getTime()));
		customer.setEndDate(new java.sql.Timestamp(customer.getEndDate().getTime()));
		ddzhfxService.findPageBySqlTJFS(page, customer, orderBy, direction);
		Calendar cal = Calendar.getInstance();
		cal.setTime(customer.getBeginDate());
		int month = cal.get(Calendar.MONTH); 
		/*
		 * 获取输出excel时的标题，
		 * month:月份 statistcsMethod:统计方式
		 * */
		getExcelList(request,month,customer.getStatistcsMethod());
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"订单转化分析_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	
	/*打印的标题
	 *根据月份不同，循环改变标题名称
	 *传入 month:月份 statistcsMethod:统计方式
	 * */
	protected void getExcelList(HttpServletRequest request,Integer month,String statistcsMethod){
		titleList = new ArrayList<String>();
		columnList = new ArrayList<String>();
		sumList = new ArrayList<String>();
		int m=0;		
		/*按月份统计*/
		int signcountMonth=month;			//合同数
		int sumcontractfeeMonth=month;		//合同额
		int convrateMonth=month;		    //转化率
		/*按一级部门和二级部门统计*/
		int signMonth=month;				// 合同
		int signperMonth=month;				// 合同比例
		int designcountMonth=month;			// 纯设计
		int designperMonth=month;			// 纯设计比例
		int returncountMonth=month;			// 退订
		int returnperMonth=month;			// 退订比例
		/*按一级部门和二级部门统计，累计部分*/
		int totalsigncountMonth=month+1;	// 合同
		int totalsignperMonth=month+1;		// 合同比例
		int totaldesigncountMonth=month+1;	// 纯设计
		int totaldesignperMonth=month+1;	// 纯设计比例
		int totalreturncountMonth=month+1;	// 退订
		int totalreturnperMonth=month+1;	// 退订比例
		
		boolean hasSum = false;
		String jsonString = request.getParameter("jsonString");
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString); 
		String colModel = (String) jo.get("colModel");
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		for (int i = 0; i < arryCols.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryCols.getJSONObject(i);
            String name = String.valueOf(jsonObject.get("name"));
            Boolean sum = (Boolean) jsonObject.get("sum");
            Boolean hidden = jsonObject.get("hidden")==null?false:(Boolean)jsonObject.get("hidden");
            if (!"rn".equals(name) && !"cb".equals(name)){
            	if (hidden.booleanValue()==false){
            		
            		if("1".equals(statistcsMethod)||"2".equals(statistcsMethod)||"3".equals(statistcsMethod)||"4".equals(statistcsMethod)){
            			//如果列名称是月份的时候，循环改变标题名称，在标题前加上月份
                		if("合同数".equals((String) jsonObject.get("label"))){
                			m=m+1;	//m作用：除去第一次出现的合同数，在第二个起的才给标题加上月份
                		}
                		/*按月份*/
                		if (m>1 && "合同数".equals((String) jsonObject.get("label"))){
                			Month(signcountMonth,titleList,"|合同数");
                			signcountMonth=signcountMonth+1;
                		}else if (m>1 && "合同额".equals((String) jsonObject.get("label"))){
                			Month(sumcontractfeeMonth,titleList,"|合同额");
                			sumcontractfeeMonth=sumcontractfeeMonth+1;
                		}else if (m>1 && "转化率".equals((String) jsonObject.get("label"))){
                			Month(convrateMonth,titleList,"|转化率");
                			convrateMonth=convrateMonth+1;
                		}/*按部门*/
                		else {
                			titleList.add((String) jsonObject.get("label"));
    					}
            		}else {
            			if ("合同".equals((String) jsonObject.get("label"))){
                			if (m!=0 && m%2==0){ 
                				/*m=0的时候:统计的月份数，不做累计                				 
                				 *当m>0时:奇数是修改月份的标题，偶数时是修改累计的标题
                				 *奇偶性判断:是因为月份和累计的标题名称相同，通过m的奇偶性来判断是做月份还是做累计的标题修改
                				 * */
                				Month(totalsigncountMonth,titleList,"累计|合同");
                				totalsigncountMonth=totalsigncountMonth+1;
                			}else{
                				Month(signMonth,titleList,"|合同");
                				signMonth=signMonth+1;
                			}
                		}else if ("合同比例".equals((String) jsonObject.get("label"))){
                			if (m!=0 && m%2==0){
                				Month(totalsignperMonth,titleList,"累计|合同比例");
                				totalsignperMonth=totalsignperMonth+1;
                			}else{
                				Month(signperMonth,titleList,"|合同比例");
                				signperMonth=signperMonth+1;
                			}
                		}else if ("纯设计".equals((String) jsonObject.get("label"))){
                			if (m!=0 && m%2==0){
                				Month(totaldesigncountMonth,titleList,"累计|纯设计");
                				totaldesigncountMonth=totaldesigncountMonth+1;
                			}else{
                				Month(designcountMonth,titleList,"|纯设计");
                				designcountMonth=designcountMonth+1;
                			}
                		}else if ("纯设计比例".equals((String) jsonObject.get("label"))){
                			if (m!=0 && m%2==0){
                				Month(totaldesignperMonth,titleList,"累计|纯设计比例");
                				totaldesignperMonth=totaldesignperMonth+1;
                			}else{
                				Month(designperMonth,titleList,"|纯设计比例");
                				designperMonth=designperMonth+1;
                			}
                		}else if ("退订".equals((String) jsonObject.get("label"))){
                			if (m!=0 && m%2==0){
                				Month(totalreturncountMonth,titleList,"累计|退订");
                				totalreturncountMonth=totalreturncountMonth+1;
                			}else{
                				Month(returncountMonth,titleList,"|退订");
                				returncountMonth=returncountMonth+1;
                			}
                		}else if ("退订比例".equals((String) jsonObject.get("label"))){
                			if (m!=0 && m%2==0){
                				Month(totalreturnperMonth,titleList,"累计|退订比例");
                				totalreturnperMonth=totalreturnperMonth+1;
                    			m=m+1;
                			}else{
                				Month(returnperMonth,titleList,"|退订比例");
                				returnperMonth=returnperMonth+1;
                    			m=m+1;
                			}
                		}
                		else {
                			titleList.add((String) jsonObject.get("label"));
    					}
					}
            		columnList.add(name);
            		if (sum!=null && sum==true){
            			sumList.add(name);
            			hasSum = true;
            		}else{
            			sumList.add("");
            		}
            	}
            }
        }
		if (hasSum==false){
			sumList = new ArrayList<String>();
		}
	}
		
	public List<String> Month(Integer month,List<String> title,String titleName) {
		String titleString= titleName.substring(0,2); //累计的时候，标题不加月份
		if (month>=12){
			month=month-11;
		}else{
			month=month+1;
		}
		if ("累计".equals(titleString)){ //累计的时候，标题不加月份
			titleList.add(titleName);    
		}else{
			titleList.add(month+"月份"+titleName);
		}
		return titleList;
	}
}
