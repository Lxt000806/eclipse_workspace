package com.house.home.web.controller.design;

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
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.ItemPlanTempDetail;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.design.ItemPlanTempDetailService;

@Controller
@RequestMapping("/admin/itemPlanTempDetail")
public class ItemPlanTempDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPlanTempDetailController.class);

	@Autowired
	private ItemPlanTempDetailService itemPlanTempDetailService;
	
	@Autowired
	private CustTypeService custTypeService;

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
			HttpServletResponse response, ItemPlanTempDetail itemPlanTempDetail) throws Exception {
	    
	    if (StringUtils.isNotBlank(itemPlanTempDetail.getCustType())) {
            CustType custType = custTypeService.get(CustType.class, itemPlanTempDetail.getCustType());
            if (custType != null) {
                itemPlanTempDetail.setCanUseComItem(custType.getCanUseComItem());
            }
        }
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanTempDetailService.findPageBySql(page, itemPlanTempDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemPlanTempDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/itemPlanTempDetail/itemPlanTempDetail_list");
	}
	/**
	 * ItemPlanTempDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/itemPlanTempDetail/itemPlanTempDetail_code");
	}
	/**
	 * 跳转到新增ItemPlanTempDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemPlanTempDetail页面");
		ItemPlanTempDetail itemPlanTempDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemPlanTempDetail = itemPlanTempDetailService.get(ItemPlanTempDetail.class, Integer.parseInt(id));
			itemPlanTempDetail.setPk(null);
		}else{
			itemPlanTempDetail = new ItemPlanTempDetail();
		}
		
		return new ModelAndView("admin/design/itemPlanTempDetail/itemPlanTempDetail_save")
			.addObject("itemPlanTempDetail", itemPlanTempDetail);
	}
	/**
	 * 跳转到修改ItemPlanTempDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemPlanTempDetail页面");
		ItemPlanTempDetail itemPlanTempDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemPlanTempDetail = itemPlanTempDetailService.get(ItemPlanTempDetail.class, Integer.parseInt(id));
		}else{
			itemPlanTempDetail = new ItemPlanTempDetail();
		}
		
		return new ModelAndView("admin/design/itemPlanTempDetail/itemPlanTempDetail_update")
			.addObject("itemPlanTempDetail", itemPlanTempDetail);
	}
	
	/**
	 * 跳转到查看ItemPlanTempDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemPlanTempDetail页面");
		ItemPlanTempDetail itemPlanTempDetail = itemPlanTempDetailService.get(ItemPlanTempDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/itemPlanTempDetail/itemPlanTempDetail_detail")
				.addObject("itemPlanTempDetail", itemPlanTempDetail);
	}
	/**
	 * 添加ItemPlanTempDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemPlanTempDetail itemPlanTempDetail){
		logger.debug("添加ItemPlanTempDetail开始");
		try{
			this.itemPlanTempDetailService.save(itemPlanTempDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemPlanTempDetail失败");
		}
	}
	
	/**
	 * 修改ItemPlanTempDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemPlanTempDetail itemPlanTempDetail){
		logger.debug("修改ItemPlanTempDetail开始");
		try{
			itemPlanTempDetail.setLastUpdate(new Date());
			itemPlanTempDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPlanTempDetailService.update(itemPlanTempDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemPlanTempDetail失败");
		}
	}
	
	/**
	 * 删除ItemPlanTempDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemPlanTempDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemPlanTempDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemPlanTempDetail itemPlanTempDetail = itemPlanTempDetailService.get(ItemPlanTempDetail.class, Integer.parseInt(deleteId));
				if(itemPlanTempDetail == null)
					continue;
				itemPlanTempDetail.setExpired("T");
				itemPlanTempDetailService.update(itemPlanTempDetail);
			}
		}
		logger.debug("删除ItemPlanTempDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}


}
