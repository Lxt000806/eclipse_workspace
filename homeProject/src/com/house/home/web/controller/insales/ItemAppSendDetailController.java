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
import com.house.home.entity.insales.ItemAppSendDetail;
import com.house.home.service.insales.ItemAppSendDetailService;

@Controller
@RequestMapping("/admin/itemAppSendDetail")
public class ItemAppSendDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemAppSendDetailController.class);

	@Autowired
	private ItemAppSendDetailService itemAppSendDetailService;

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
			HttpServletResponse response, ItemAppSendDetail itemAppSendDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSendDetailService.findPageBySql(page, itemAppSendDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据-分批发货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_fhmx")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_fhmx(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSendDetailService.findPageByNo(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemAppSendDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemAppSendDetail/itemAppSendDetail_list");
	}
	/**
	 * ItemAppSendDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemAppSendDetail/itemAppSendDetail_code");
	}
	/**
	 * 跳转到新增ItemAppSendDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemAppSendDetail页面");
		ItemAppSendDetail itemAppSendDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemAppSendDetail = itemAppSendDetailService.get(ItemAppSendDetail.class, Integer.parseInt(id));
			itemAppSendDetail.setPk(null);
		}else{
			itemAppSendDetail = new ItemAppSendDetail();
		}
		
		return new ModelAndView("admin/insales/itemAppSendDetail/itemAppSendDetail_save")
			.addObject("itemAppSendDetail", itemAppSendDetail);
	}
	/**
	 * 跳转到修改ItemAppSendDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemAppSendDetail页面");
		ItemAppSendDetail itemAppSendDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemAppSendDetail = itemAppSendDetailService.get(ItemAppSendDetail.class, Integer.parseInt(id));
		}else{
			itemAppSendDetail = new ItemAppSendDetail();
		}
		
		return new ModelAndView("admin/insales/itemAppSendDetail/itemAppSendDetail_update")
			.addObject("itemAppSendDetail", itemAppSendDetail);
	}
	
	/**
	 * 跳转到查看ItemAppSendDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemAppSendDetail页面");
		ItemAppSendDetail itemAppSendDetail = itemAppSendDetailService.get(ItemAppSendDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/insales/itemAppSendDetail/itemAppSendDetail_detail")
				.addObject("itemAppSendDetail", itemAppSendDetail);
	}
	/**
	 * 添加ItemAppSendDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemAppSendDetail itemAppSendDetail){
		logger.debug("添加ItemAppSendDetail开始");
		try{
			this.itemAppSendDetailService.save(itemAppSendDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemAppSendDetail失败");
		}
	}
	
	/**
	 * 修改ItemAppSendDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemAppSendDetail itemAppSendDetail){
		logger.debug("修改ItemAppSendDetail开始");
		try{
			itemAppSendDetail.setLastUpdate(new Date());
			itemAppSendDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemAppSendDetailService.update(itemAppSendDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemAppSendDetail失败");
		}
	}
	
	/**
	 * 删除ItemAppSendDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemAppSendDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemAppSendDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemAppSendDetail itemAppSendDetail = itemAppSendDetailService.get(ItemAppSendDetail.class, Integer.parseInt(deleteId));
				if(itemAppSendDetail == null)
					continue;
				itemAppSendDetail.setExpired("T");
				itemAppSendDetailService.update(itemAppSendDetail);
			}
		}
		logger.debug("删除ItemAppSendDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemAppSendDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemAppSendDetail itemAppSendDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppSendDetailService.findPageBySql(page, itemAppSendDetail);
	}
	/**
	 * 跳转到发货详情ItemAppSendDetail页面
	 * @return
	 */
	@RequestMapping("/goSendInfo")
	public ModelAndView goSendInfo(HttpServletRequest request, HttpServletResponse response, 
			 ItemAppSendDetail itemAppSendDetail){
		logger.debug("跳转到查看ItemAppSendDetail页面");
		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_sendDetail_sendInfo")
				.addObject("itemAppSendDetail", itemAppSendDetail);
	}
	/**
	 * 查询goSendDetailJqGrid表格数据 发货详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSendDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSendDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppSendDetail itemAppSendDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSendDetailService.findSendDetailByNo(page, itemAppSendDetail);
		return new WebPage<Map<String,Object>>(page);
	}
}
