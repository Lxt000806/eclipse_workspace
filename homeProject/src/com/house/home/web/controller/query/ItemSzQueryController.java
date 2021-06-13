package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemSzQueryService;

@Controller
@RequestMapping("/admin/itemSzQuery")
public class ItemSzQueryController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ItemSzQueryController.class);
	@Autowired
	private ItemSzQueryService itemSzQueryService;
	
//--------------------------页面跳转相关	
	
	/**
	 * ItemSz列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/query/itemSzQuery/itemSzQuery_list");
	}
	
	/**
	 * 跳转到基础收支信息界面
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/goJcszxx","/goReturnPayJcszxx"})
	public ModelAndView goJcszxx(HttpServletRequest request,HttpServletResponse response,String code,String isWorkcost) throws Exception {
		logger.debug("跳转到基础收支信息页面");
		Map<String,Object> map = null;
		Map<String,Object> customerPayMap=new HashMap<String, Object>();
		Map<String,Object> fixAreaTypeCountMap=itemSzQueryService.getFixAreaTypeCount(code);
		List<Map<String,Object>>isRefCustCode=itemSzQueryService.isRefCustCode(code);
		List<Map<String,Object>>isRefCustCode_wc=itemSzQueryService.isRefCustCode_wc(code);
		try {
			Customer customer = itemSzQueryService.get(Customer.class, code);
			if(itemSzQueryService.get(CustType.class, customer.getCustType()).getPrjCtrlType().equals("1")){
				map = itemSzQueryService.getXmjljsxx(code);
			}else{
				map = itemSzQueryService.getJccbzcxx(code);
				Map<String,Object> szhzMap = itemSzQueryService.getSzhz(code);
				if(szhzMap != null){
					map.putAll(szhzMap);
				}
			}
			customerPayMap = itemSzQueryService.getCustPayPlan(code);
			map.put("costRight", getUserContext(request).getCostRight());
			map.put("isWaterItemCtrl", customer.getIsWaterItemCtrl()); //判断是否水电材料发包
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("admin/query/itemSzQuery/itemSzQuery_jcszxx")
		.addObject("customer", map).addObject("isWorkcost", isWorkcost)
		.addObject("customerPayMap",customerPayMap)
		.addObject("fixAreaTypeCountMap", fixAreaTypeCountMap)
		.addObject("isRefCustCode", isRefCustCode.size()).addObject("isRefCustCode_wc", isRefCustCode_wc.size());
	}
	
	/**
	 * 跳转到主材软装收支信息界面
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goZcrzszxx")
	public ModelAndView goZcrzszxx(HttpServletRequest request,HttpServletResponse response,String code) throws Exception {
		logger.debug("跳转到主材软装收支信息页面");
		Customer customer = itemSzQueryService.get(Customer.class, code);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", customer.getCode());
		map.put("costRight", getUserContext(request).getCostRight().trim());
		return new ModelAndView("admin/query/itemSzQuery/itemSzQuery_zcrzszxx").addObject("customer", map);
	}
	
//----------------------------表格查询相关
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
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemSzQueryService.findPageBySql(page,customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJcszxxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJcszxxJqGrid(HttpServletRequest request,HttpServletResponse response,String code) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goJcszxxJqGrid(code);
		page.setResult(list); 			
		if(list != null){
			if (!list.isEmpty()) {
				page.setTotalCount(list.size());
			} else {
				page.setTotalCount(0);
			}
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goYshfbdemxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYshfbdemxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String calType,String workType1Name,String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = null;
		if("1".equals(calType)){
			list = itemSzQueryService.goYshfbdemxJqGrid(page,workType1Name,custCode,workType2);
		}else{
			list = itemSzQueryService.goYshfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
		}
		page.setResult(list); 			
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goZjhfbdemxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goZjhfbdemxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String calType,String workType1Name,String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = null;
		if("1".equals(calType)){
			list = itemSzQueryService.goZjhfbdemxJqGrid(page,workType1Name,custCode,workType2);
		}else{
			list = itemSzQueryService.goZjhfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
		}
		page.setResult(list); 			
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goZfbdemxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goZfbdemxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String calType,String workType1Name,String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = null;
		if("1".equals(calType)){
			list = itemSzQueryService.goZfbdemxJqGrid(page,workType1Name,custCode,workType2);
		}else{
			list = itemSzQueryService.goZfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
		}
		page.setResult(list); 			
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

    @RequestMapping("/goRgcbmxJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goRgcbmxJqGrid(HttpServletRequest request,
            HttpServletResponse response, String costType, String workType1Name,
            String custCode, String workType2) {

        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        List<Map<String, Object>> list =
                itemSzQueryService.goRgcbmxJqGrid(page, costType, workType1Name, custCode, workType2);

        page.setResult(list);

        if (!list.isEmpty()) {
            page.setTotalCount(list.size());
        } else {
            page.setTotalCount(0);
        }

        return new WebPage<Map<String, Object>>(page);
    }
	
	@RequestMapping("/goShrgmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goShrgmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String workType1Name,String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goShrgmxJqGrid(page,workType1Name,custCode,workType2);
		page.setResult(list);
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goClcbmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goClcbmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String workType1Name,String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goClcbmxJqGrid(page,workType1Name,custCode,workType2);
		page.setResult(list); 	
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goShclmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goShclmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String workType1Name,String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goShclmxJqGrid(page,workType1Name,custCode,workType2);
		page.setResult(list); 	
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goLlmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLlmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String no,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goLlmxJqGrid(page,no,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goRgfymxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goRgfymxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goRgfymxJqGrid(page,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goKhfkJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goKhfkJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goKhfkJqGrid(page,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goYxxmjljlJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYxxmjljlJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goYxxmjljlJqGrid(page,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goYkmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYkmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goYkmxJqGrid(page,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJcbgJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJcbgJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goJcbgJqGrid(page,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJcclcbmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJcclcbmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goJcclcbmxJqGrid(page,custCode,workType2);
		page.setResult(list); 	
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goTcnclcbmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goTcnclcbmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode,String itemType1) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goTcnclcbmxJqGrid(page,custCode,itemType1);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJcxqJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJcxqJqGrid(HttpServletRequest request,HttpServletResponse response,
			String workType1,String baseItem,String custCode,String isOutSet) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goJcxqJqGrid(page,workType1,baseItem,custCode,isOutSet);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

    @RequestMapping("/goRgcbmxTcJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goRgcbmxTcJqGrid(HttpServletRequest request,
            HttpServletResponse response, String costType, String custCode, String workType2) {
        
        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        List<Map<String, Object>> list =
                itemSzQueryService.goRgcbmxTcJqGrid(page, costType, custCode,workType2);
        
        page.setResult(list);
        if (!list.isEmpty()) {
            page.setTotalCount(list.size());
        } else {
            page.setTotalCount(0);
        }
        
        return new WebPage<Map<String, Object>>(page);
    }

	@RequestMapping("/goRgfymxTcJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goRgfymxTcJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goRgfymxTcJqGrid(page,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goZcrzszxxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goZcrzszxxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goZcrzszxxJqGrid(custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goLldJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLldJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode,String type,String itemType2,String itemAppStatus) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goLldJqGrid(page,custCode,type,itemType2,itemAppStatus);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goLldLlmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLldLlmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String no,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goLldLlmxJqGrid(page,no,custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goClzjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goClzjJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode,String type) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goClzjJqGrid(page,custCode,type);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goClzjZjmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goClzjZjmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goClzjZjmxJqGrid(page,no);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	//人工费用明细
	@RequestMapping("/goRgfymxZcrzszxxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goRgfymxZcrzszxxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode,String itemType1,String laborFeeStatus) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goRgfymxZcrzszxxJqGrid(page,custCode,itemType1,laborFeeStatus);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goRzmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goRzmxJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goRzmxJqGrid(custCode);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goCgdJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCgdJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode,String itemType2,String purchaseStatus,String isCheckOut,String checkOutStatus) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goCgdJqGrid(page,custCode,itemType2,purchaseStatus,isCheckOut,checkOutStatus);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goCgdmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCgdJqGrid(HttpServletRequest request,HttpServletResponse response,
			String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goCgdmxJqGrid(page,no);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 水电发包定额
	 * @author	created by zb
	 * @date	2018-11-26--下午4:23:31
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWaterContractQuotaJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getWaterContractQuotaJqGrid(HttpServletRequest request,HttpServletResponse response,
			String custCode,String workType2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.findWaterContractQuotaJqGrid(page,custCode,workType2);
		page.setResult(list); 
		if (!list.isEmpty()) {
			page.setTotalCount(list.size());
		} else {
			page.setTotalCount(0);
		}
		return new WebPage<Map<String,Object>>(page);
	}
//--------------------------操作相关
	/**
	 * itemSzQuery导出Excel
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemSzQueryService.findPageBySql(page,customer,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础收支查询-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/getCustPayPlan")
	@ResponseBody
	public JSONObject getCustPayPlan(HttpServletRequest request, HttpServletResponse response, String custCode){
		Map<String,Object> map = itemSzQueryService.getCustPayPlan(custCode);
		Map<String,Object> zjzjeMap = itemSzQueryService.getZjzje(custCode);
		Map<String,Object> hasPayMap = itemSzQueryService.getHasPay(custCode);
		JSONObject jsonObject = null;
		if(map != null){
			jsonObject = new JSONObject();
	        Iterator<String> keySet =  map.keySet().iterator();  
	        while (keySet.hasNext()) {  
	        	String key = keySet.next();
	        	jsonObject.put(key, map.get(key));
	        } 
		}
		if(zjzjeMap != null){
			if(jsonObject == null ) jsonObject = new JSONObject();
	        jsonObject.put("zjzje",zjzjeMap.get("zjzje"));
		}
		if(hasPayMap != null){
			if(jsonObject == null ) jsonObject = new JSONObject();
	        jsonObject.put("Haspay", hasPayMap.get("Haspay"));
		}
		return jsonObject;
	}	
	/**
	 * itemSzQuery导出Excel
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doExcel_jcszxx")
    public void doExcel_jcszxx(HttpServletRequest request, HttpServletResponse response,
            String dataTableName, String custCode, String calType, String workType1Name,
            String workType2, String costType, String workType1, String baseItem, String no,
            String isOutSet) {
	    
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String title = "";
		List<Map<String,Object>> list = null;
		if("dataTable_jcszxx".equals(dataTableName)){
			title = "基础收支信息";
			list = itemSzQueryService.goJcszxxJqGrid(custCode);
		}else if("dataTable_yshfbdemx".equals(dataTableName)){
			title = "预算和发包定额明细";
			if("1".equals(calType)){
				list = itemSzQueryService.goYshfbdemxJqGrid(page,workType1Name,custCode,workType2);
			}else{
				list = itemSzQueryService.goYshfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
			}
		}else if("dataTable_zjhfbdemx".equals(dataTableName)){
			title = "增减和发包定额明细";
			if("1".equals(calType)){
				list = itemSzQueryService.goZjhfbdemxJqGrid(page,workType1Name,custCode,workType2);
			}else{
				list = itemSzQueryService.goZjhfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
			}
		}else if("dataTable_zfbdemx".equals(dataTableName)){
			title = "总发包定额明细";
			if("1".equals(calType)){
				list = itemSzQueryService.goZfbdemxJqGrid(page,workType1Name,custCode,workType2);
			}else{
				list = itemSzQueryService.goZfbdemxJqGrid_material(page,workType1Name,custCode,workType2);
			}
		}else if("dataTable_clcbmx".equals(dataTableName)){
			title = "材料成本明细";
			list = itemSzQueryService.goClcbmxJqGrid(page,workType1Name,custCode,workType2);
		}else if("dataTable_shclmx".equals(dataTableName) || "dataTable_shclmx_tc".equals(dataTableName)){
			title = "售后材料明细";
			list = itemSzQueryService.goShclmxJqGrid(page,workType1Name,custCode,workType2);
		}else if("dataTable_rgcbmx_qd".equals(dataTableName)){
			title = "人工成本明细";
			list = itemSzQueryService.goRgcbmxJqGrid(page, costType, workType1Name, custCode, workType2);
		}else if("dataTable_shrgmx_qd".equals(dataTableName) || "dataTable_shrgmx_tc".equals(dataTableName)){
			title = "售后人工明细";
			list = itemSzQueryService.goShrgmxJqGrid(page,workType1Name,custCode,workType2);
		}else if("dataTable_rgfymx_qd".equals(dataTableName)){
			title = "人工费用明细";
			list = itemSzQueryService.goRgfymxJqGrid(page,custCode);
		}else if("dataTable_khfkmx_qd".equals(dataTableName)){
			title = "客户付款明细";
			list = itemSzQueryService.goKhfkJqGrid(page,custCode);
		}else if("dataTable_yxxmjljl".equals(dataTableName)){
			title = "优秀项目经理奖励";
			list = itemSzQueryService.goYxxmjljlJqGrid(page,custCode);
		}else if("dataTable_ykmx_qd".equals(dataTableName)){
			title = "预扣明细";
			list = itemSzQueryService.goYkmxJqGrid(page,custCode);
		}else if("dataTable_jccbzcxx".equals(dataTableName)){
			title = "基础成本支出信息";
			list = itemSzQueryService.goJcszxxJqGrid(custCode); 
		}else if("dataTable_jcbg".equals(dataTableName)){
			title = "基础变更";
			list = itemSzQueryService.goJcbgJqGrid(page,custCode); 
		}else if("dataTable_jcclcbmx".equals(dataTableName)){
			title = "基础材料成本明细";
			list = itemSzQueryService.goJcclcbmxJqGrid(page,custCode,workType2); 
		}else if("dataTable_tcnzccbmx".equals(dataTableName)){
			title = "套餐内主材成本明细";
			list = itemSzQueryService.goTcnclcbmxJqGrid(page,custCode,"ZC"); 
		}else if("dataTable_tcnjccbmx".equals(dataTableName)){
			title = "套餐内集成成本明细";
			list = itemSzQueryService.goTcnclcbmxJqGrid(page,custCode,"JC"); 
		}else if("dataTable_rgcbmx_tc".equals(dataTableName)){
			title = "人工成本明细";
			list = itemSzQueryService.goRgcbmxTcJqGrid(page, costType, custCode, workType2); 
		}else if("dataTable_rgfymx_tc".equals(dataTableName)){
			title = "人工费用明细";
			list = itemSzQueryService.goRgfymxTcJqGrid(page,custCode); 
		}else if("dataTable_khfkmx_tc".equals(dataTableName)){
			title = "客户付款明细";
			list = itemSzQueryService.goKhfkJqGrid(page,custCode);
		}else if("dataTable_ykmx_tc".equals(dataTableName)){
			title = "预扣明细";
			list = itemSzQueryService.goYkmxJqGrid(page,custCode);
		}else if("dataTable_llmx_clcbmx".equals(dataTableName)){
			title = "材料成本明细";
			list = itemSzQueryService.goLlmxJqGrid(page,no,custCode);
		}else if("dataTable_llmx_tcnzccbmx".equals(dataTableName) || "dataTable_llmx_shclmx".equals(dataTableName) || "dataTable_llmx_shclmx_tc".equals(dataTableName)){
			title = "材料成本明细";
			list = itemSzQueryService.goLlmxJqGrid(page,no,custCode);
		}else if("dataTable_llmx_tcnjccbmx".equals(dataTableName)){
			title = "材料成本明细";
			list = itemSzQueryService.goLlmxJqGrid(page,no,custCode);
		}else if("dataTable_jcclcbmx".equals(dataTableName)){
			title = "基础材料成本明细";
			list = itemSzQueryService.goLlmxJqGrid(page,no,custCode);
		}else if("dataTable_tcnzccbmx".equals(dataTableName)){
			title = "套餐内主材成本明细";
			list = itemSzQueryService.goLlmxJqGrid(page,no,custCode);
		}else if("dataTable_jcxq".equals(dataTableName)){
			title = "基础需求";
			list = itemSzQueryService.goJcxqJqGrid(page,workType1,baseItem,custCode,isOutSet); 
		}else if("dataTable_waterContractQuota_qd".equals(dataTableName)){ //增加“水电发包定额”导出Excel add by zb
			title = "水电发包定额";
			list = itemSzQueryService.findWaterContractQuotaJqGrid(page,custCode,workType2);
		}else if("dataTable_tc_tcnzcjc".equals(dataTableName)){ 
			title = "套餐内主材奖惩";
			list = itemSzQueryService.goTcnzcjcJqGrid(page, custCode);
		}
		else if("dataTable_tc_sgbt".equals(dataTableName)){ 
			title = "施工补贴";
			list = itemSzQueryService.goSgbtJqGrid(page, custCode);
		}
		Customer customer = itemSzQueryService.get(Customer.class, custCode);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,list,customer.getAddress()+"-"+title+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	}	
	/**
	 * itemSzQuery导出Excel
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doExcel_zcrzszxx")
	public void doExcel_zcrzszxx(HttpServletRequest request, 
			HttpServletResponse response, String custCode,String firstTab,String secondTab,
			String itemAppStatus,String rzllItemType2,
			String laborFeeStatus,String rgfymxItemType1,
			String purchaseStatus,String isCheckOut,String checkOutStatus,String cgdItemType2,
			String no){
		String title = "";
		List<Map<String,Object>> list = null;
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		if("a_zcrzszxx".equals(firstTab)){
			list = itemSzQueryService.goZcrzszxxJqGrid(custCode);
			title = "主材软装收支信息";
		}else if("a_lld".equals(firstTab)){
			String type = "";
			if("lld_zcll".equals(secondTab)){
				type = "ZC";
				title = StringUtils.isNotBlank(no)?"主材领料明细":"主材领料";
			}else if("lld_fwll".equals(secondTab)){
				type = "FW";
				title = StringUtils.isNotBlank(no)?"服务领料明细":"服务领料";
			}else if("lld_jcll".equals(secondTab)){
				type = "JC";
				title = StringUtils.isNotBlank(no)?"集成领料明细":"集成领料";
			}else {
				type = "RZ";
				title = StringUtils.isNotBlank(no)?"软装领料明细":"软装领料";
			}
			list = StringUtils.isNotBlank(no)?itemSzQueryService.goLldLlmxJqGrid(page,no, custCode):itemSzQueryService.goLldJqGrid(page,custCode, type, rzllItemType2, itemAppStatus);
		}else if("a_clzj".equals(firstTab)){
			String type = "";
			if("clzj_zczj".equals(secondTab)){
				type = "ZC";
				title = StringUtils.isNotBlank(no)?"主材增减明细":"主材增减";
			}else if("clzj_fwxcpzj".equals(secondTab)){
				type = "FWXCP";
				title = StringUtils.isNotBlank(no)?"服务性产品增减明细":"服务性产品增减";
			}else if("clzj_jczj".equals(secondTab)){
				type = "JC";
				title = StringUtils.isNotBlank(no)?"集成增减明细":"集成增减";
			}else {
				type = "RZ";
				title =  StringUtils.isNotBlank(no)?"软装增减明细":"软装增减";
			}
			list = StringUtils.isNotBlank(no)?itemSzQueryService.goClzjZjmxJqGrid(page,no):itemSzQueryService.goClzjJqGrid(page,custCode, type);
		}else if("a_rgfymx".equals(firstTab)){
			list = itemSzQueryService.goRgfymxZcrzszxxJqGrid(page,custCode, rgfymxItemType1, laborFeeStatus);
			title = "人工费用明细";
		}else if("a_rzmx".equals(firstTab)){
			list = itemSzQueryService.goRzmxJqGrid(custCode);
			title = "软装明细";
		}else{
			list = StringUtils.isNotBlank(no)?itemSzQueryService.goCgdmxJqGrid(page,no):itemSzQueryService.goCgdJqGrid(page,custCode, cgdItemType2, purchaseStatus, isCheckOut, checkOutStatus);
			title = StringUtils.isNotBlank(no)?"采购单明细":"采购单";
		}

		Customer customer = itemSzQueryService.get(Customer.class, custCode);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,list,customer.getAddress()+"-"+title+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到查看发包公式
	 * custCode
	 * @return
	 */
	@RequestMapping("/goViewFbgs")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response,String custCode, String type){
		Map<String, Object>map=itemSzQueryService.getExprByCust(custCode, type);
		return new ModelAndView("admin/query/itemSzQuery/tabView_tc_ckfbgs").addObject("map", map);
	}
	/**
	 * 套餐内主材奖惩jqGrid
	 * @author cjg
	 * @date 2019-4-22
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goTcnzcjcJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goTcnzcjcJqGrid(HttpServletRequest request,HttpServletResponse response,String code) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goTcnzcjcJqGrid(page, code);
		page.setResult(list); 			
		if(list != null){
			if (!list.isEmpty()) {
				page.setTotalCount(list.size());
			} else {
				page.setTotalCount(0);
			}
		}
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 施工补贴jqGrid
	 * @author cjg
	 * @date 2020-7-13
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSgbtJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSgbtJqGrid(HttpServletRequest request,HttpServletResponse response,String code) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String,Object>> list = itemSzQueryService.goSgbtJqGrid(page, code);
		page.setResult(list); 			
		if(list != null){
			if (!list.isEmpty()) {
				page.setTotalCount(list.size());
			} else {
				page.setTotalCount(0);
			}
		}
		return new WebPage<Map<String,Object>>(page);
	}
}
