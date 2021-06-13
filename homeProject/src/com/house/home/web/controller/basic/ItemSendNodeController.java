package com.house.home.web.controller.basic;

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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ItemSendNode;
import com.house.home.entity.project.FixDuty;
import com.house.home.service.basic.ItemSendNodeService;

@Controller
@RequestMapping("/admin/itemSendNode")
public class ItemSendNodeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemSendNodeController.class);

	@Autowired
	private ItemSendNodeService itemSendNodeService;

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
			HttpServletResponse response, ItemSendNode itemSendNode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSendNodeService.findPageBySql(page, itemSendNode);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemSendNode itemSendNode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSendNodeService.findPageBySqlForDetail(page, itemSendNode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemSendNode列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_list");
	}
	/**
	 * ItemSendNode查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_code");
	}
	/**
	 * 跳转到新增ItemSendNode页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增ItemSendNode页面");
		ItemSendNode itemSendNode = new ItemSendNode();
		itemSendNode.setM_umState("A");
		
		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_save")
			.addObject("itemSendNode", itemSendNode);
	}
	
	
	/**
	 * 跳转到修改ItemSendNode页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到修改ItemSendNode页面");
		ItemSendNode itemSendNode = null;
		if (StringUtils.isNotBlank(code)){
			itemSendNode = itemSendNodeService.get(ItemSendNode.class, code);
		}else{
			itemSendNode = new ItemSendNode();
		}
		itemSendNode.setM_umState("M");
		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_save")
			.addObject("itemSendNode", itemSendNode);
	}
	
	/**
	 * 跳转到查看ItemSendNode页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String code){
		logger.debug("跳转到修改ItemSendNode页面");
		ItemSendNode itemSendNode = null;
		if (StringUtils.isNotBlank(code)){
			itemSendNode = itemSendNodeService.get(ItemSendNode.class, code);
		}else{
			itemSendNode = new ItemSendNode();
		}
		itemSendNode.setM_umState("V");
		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_save")
			.addObject("itemSendNode", itemSendNode);
	}
	

	@RequestMapping("/goDetailAdd")
	public ModelAndView goDetailAdd(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增ItemSendNodeDetail页面");
		ItemSendNode itemSendNode = new ItemSendNode();
		itemSendNode.setPayNum(0);
		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_detailSave")
				.addObject("itemSendNode", itemSendNode);
	}
	
	@RequestMapping("/goDetailUpdate")
	public ModelAndView goDetailUpdate(HttpServletRequest request, HttpServletResponse response, ItemSendNode itemSendNode){
		logger.debug("跳转到新增ItemSendNodeDetail页面");
		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_detailSave")
				.addObject("itemSendNode", itemSendNode);
	}
	
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request, HttpServletResponse response, ItemSendNode itemSendNode){
		logger.debug("跳转到新增ItemSendNodeDetail页面");
		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_detailSave")
				.addObject("itemSendNode", itemSendNode);
	}
	/**
	 * 添加ItemSendNode
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSaveForProc")
	public void doSaveForProc(HttpServletRequest request,HttpServletResponse response,ItemSendNode itemSendNode){
		logger.debug("保存");		
		try {	
			itemSendNode.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String xmlData = itemSendNode.getDetailJson();
			Result result = this.itemSendNodeService.saveForProc(itemSendNode,xmlData);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	
	/**
	 * 删除ItemSendNode
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemSendNode开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemSendNode编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemSendNode itemSendNode = itemSendNodeService.get(ItemSendNode.class, deleteId);
				if(itemSendNode == null)
					continue;
				itemSendNode.setExpired("T");
				itemSendNodeService.update(itemSendNode);
			}
		}
		logger.debug("删除ItemSendNode IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemSendNode导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemSendNode itemSendNode){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemSendNodeService.findPageBySql(page, itemSendNode);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料配送节点管理表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
