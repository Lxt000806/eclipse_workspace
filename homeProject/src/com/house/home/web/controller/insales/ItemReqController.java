package com.house.home.web.controller.insales;

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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.ItemReq;
import com.house.home.service.insales.ItemReqService;

@Controller
@RequestMapping("/admin/itemReq")
public class ItemReqController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemReqController.class);

	@Autowired
	private ItemReqService itemReqService;

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
			HttpServletResponse response, ItemReq itemReq) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReqService.findPageBySql(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSoftNotAppQueryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSoftNotAppQueryJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemReq itemReq) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReqService.findSoftNotAppQueryPageBySql(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询预算导入JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goImportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getImportJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemReq itemReq,String arr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReqService.findImportPageBySql(page, itemReq,arr);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * ItemReq列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemReq/itemReq_list");
	}
	/**
	 * ItemReq查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemReq/itemReq_code");
	}
	/**
	 * 跳转到新增ItemReq页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemReq页面");
		ItemReq itemReq = null;
		if (StringUtils.isNotBlank(id)){
			itemReq = itemReqService.get(ItemReq.class, Integer.parseInt(id));
			itemReq.setPk(null);
		}else{
			itemReq = new ItemReq();
		}
		
		return new ModelAndView("admin/insales/itemReq/itemReq_save")
			.addObject("itemReq", itemReq);
	}
	/**
	 * 跳转到修改ItemReq页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemReq页面");
		ItemReq itemReq = null;
		if (StringUtils.isNotBlank(id)){
			itemReq = itemReqService.get(ItemReq.class, Integer.parseInt(id));
		}else{
			itemReq = new ItemReq();
		}
		
		return new ModelAndView("admin/insales/itemReq/itemReq_update")
			.addObject("itemReq", itemReq);
	}
	
	/**
	 * 跳转到查看ItemReq页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemReq页面");
		ItemReq itemReq = itemReqService.get(ItemReq.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/itemReq/itemReq_detail")
				.addObject("itemReq", itemReq);
	}
	/**
	 * 添加ItemReq
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemReq itemReq){
		logger.debug("添加ItemReq开始");
		try{
			this.itemReqService.save(itemReq);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemReq失败");
		}
	}
	
	/**
	 * 修改ItemReq
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemReq itemReq){
		logger.debug("修改ItemReq开始");
		try{
			itemReq.setLastUpdate(new Date());
			itemReq.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemReqService.update(itemReq);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemReq失败");
		}
	}
	
	/**
	 * 删除ItemReq
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemReq开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemReq编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemReq itemReq = itemReqService.get(ItemReq.class, Integer.parseInt(deleteId));
				if(itemReq == null)
					continue;
				itemReq.setExpired("T");
				itemReqService.update(itemReq);
			}
		}
		logger.debug("删除ItemReq IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemReq导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemReq itemReq){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemReqService.findPageBySql(page, itemReq);
	}
	
	/**
	 * 已有项新增
	 * @param request
	 * @param response
	 * @param itemReq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getItemReqList")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemReqList(HttpServletRequest request,
			HttpServletResponse response, ItemReq itemReq) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReqService.findItemReqList(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}

}
