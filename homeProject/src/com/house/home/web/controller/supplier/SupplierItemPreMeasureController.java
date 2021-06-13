package com.house.home.web.controller.supplier;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.service.insales.ItemPreAppDetailService;
import com.house.home.service.insales.ItemPreAppPhotoService;
import com.house.home.service.project.ItemPreMeasureService;

@Controller
@RequestMapping("/admin/supplierItemPreMeasure")
public class SupplierItemPreMeasureController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SupplierItemPreMeasureController.class);

	@Autowired
	private ItemPreMeasureService itemPreMeasureService;
	@Autowired
	private ItemPreAppPhotoService itemPreAppPhotoService;
	@Autowired
	private ItemPreAppDetailService itemPreAppDetailService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemPreMeasure itemPreMeasure) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageOrderBy("LastUpdate");
		page.setPageOrder("desc");
		itemPreMeasure.setSupplCode(this.getUserContext(request).getEmnum());
		itemPreMeasureService.findPageBySql(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ItemPreMeasure列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) throws Exception {
		if (itemPreMeasure.getStatus()==null){
			itemPreMeasure.setStatus("1,2,3");
		}
		return new ModelAndView("admin/supplier/itemPreMeasure/itemPreMeasure_list")
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	
	/**
	 * 跳转到查看itemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String preAppNo){
		logger.debug("跳转到查看itemPreMeasure页面");
		Map<String,Object> map = itemPreMeasureService.getByPk(pk);
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(map, itemPreMeasure);
		List<Map<String,Object>> list = itemPreAppPhotoService.getPhotoList(preAppNo);
		String photoPath = SystemConfig.getProperty("itemPreApp", "", "client");
		return new ModelAndView("admin/supplier/itemPreMeasure/itemPreMeasure_view")
			.addObject("photoList", list)
			.addObject("photoPath", photoPath)
			.addObject("pk", pk)
			.addObject("preAppNo", preAppNo)
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	/**
	 * 跳转到修改itemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String preAppNo){
		logger.debug("跳转到修改itemPreMeasure页面");
		Map<String,Object> map = itemPreMeasureService.getByPk(pk);
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(map, itemPreMeasure);
		List<Map<String,Object>> list = itemPreAppPhotoService.getPhotoList(preAppNo);
		String photoPath = SystemConfig.getProperty("itemPreApp", "", "client");
		return new ModelAndView("admin/supplier/itemPreMeasure/itemPreMeasure_update")
			.addObject("photoList", list)
			.addObject("photoPath", photoPath)
			.addObject("pk", pk)
			.addObject("preAppNo", preAppNo)
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	/**
	 * 跳转到退回itemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goBack")
	public ModelAndView goBack(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到退回itemPreMeasure页面");
		Map<String,Object> map = itemPreMeasureService.getByPk(pk);
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(map, itemPreMeasure);
		return new ModelAndView("admin/supplier/itemPreMeasure/itemPreMeasure_back")
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	/**
	 * 修改保存itemPreMeasure
	 * @return
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, 
			ItemPreMeasure itemPreMeasure){
		logger.debug("保存itemPreMeasure页面");
		try {
			ItemPreMeasure item = itemPreMeasureService.get(ItemPreMeasure.class, itemPreMeasure.getPk());
			if (item == null) {
				ServletUtils.outPrintFail(request, response, "没有找到工程测量单,无法进行修改操作!");
				return;
			}
			if (!"3".equalsIgnoreCase(item.getStatus().trim()) && // 3.接收
				!"4".equalsIgnoreCase(item.getStatus().trim())) { // 4.完成 
				ServletUtils.outPrintFail(request, response, "只有【接收】或【完成】的测量单才能进行修改操作!");
				return;
			}
			item.setMeasureRemark(itemPreMeasure.getMeasureRemark());
			item.setLastUpdate(new Date());
			item.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			item.setExpired("F");
			item.setActionLog("EDIT");
			this.itemPreMeasureService.update(item);
			ServletUtils.outPrintSuccess(request, response, "修改成功!", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法修改该工程测量单!");
		}
	}
	/**
	 * 跳转到接收itemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goReceive")
	public ModelAndView goReceive(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String preAppNo){
		logger.debug("跳转到接收ItemApp页面");
		List<Map<String,Object>> list = itemPreAppPhotoService.getPhotoList(preAppNo);
		String photoPath = SystemConfig.getProperty("itemPreApp", "", "client");
		return new ModelAndView("admin/supplier/itemPreMeasure/itemPreMeasure_receive")
			.addObject("photoList", list)
			.addObject("photoPath", photoPath)
			.addObject("pk", pk)
			.addObject("preAppNo", preAppNo);
	}
	
	/**
	 * 接收ItemPreMeasure
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doReceive")
	public void doReceive(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("接收ItemPreMeasure开始");
		
		if(pk==null){
			ServletUtils.outPrintFail(request, response, "测量编号不能为空,接收失败!");
			return;
		};
		
		try {
			ItemPreMeasure itemPreMeasure = itemPreMeasureService.get(ItemPreMeasure.class, pk);
			
			if (itemPreMeasure == null) {
				ServletUtils.outPrintFail(request, response, "没有找到工程测量单,无法进行接收操作!");
				return;
			}
			
			if (!"1".equalsIgnoreCase(itemPreMeasure.getStatus().trim()) && // 1.申请
				!"2".equalsIgnoreCase(itemPreMeasure.getStatus().trim())) { // 2.退回 
				ServletUtils.outPrintFail(request, response, "只有【申请】或【接收退回】的测量单才能进行接收操作!");
				return;
			}
			
			itemPreMeasure.setStatus("3"); // 3.已接收
			itemPreMeasure.setLastUpdate(new Date());
			itemPreMeasure.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemPreMeasure.setExpired("F");
			itemPreMeasure.setActionLog("EDIT");
			itemPreMeasure.setRecvDate(new Date());
			this.itemPreMeasureService.update(itemPreMeasure);
			ServletUtils.outPrintSuccess(request, response, "接收成功!", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法接收该工程测量单!");
		}
	}
	
	/**
	 * 跳转到完成itemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goFinish")
	public ModelAndView goFinish(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String preAppNo){
		logger.debug("跳转到完成ItemApp页面");
		ItemPreMeasure itemPreMeasure = itemPreMeasureService.get(ItemPreMeasure.class, pk);
		long la = DateUtil.dateDiffByDate(new Date(), itemPreMeasure.getDate());
		String unShowValue = "1,2,3";
		if (la>=3){
			unShowValue = "0,3";
		}else{
			itemPreMeasure.setDelayReson("0");
		}
		
		return new ModelAndView("admin/supplier/itemPreMeasure/itemPreMeasure_finish")
			.addObject("pk", pk)
			.addObject("preAppNo", preAppNo)
			.addObject("unShowValue", unShowValue)
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	
	/**
	 * 完成ItemPreMeasure
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doFinish")
	public void doFinish(HttpServletRequest request, HttpServletResponse response, 
			Integer pk, String measureRemark, String delayReson){
		logger.debug("完成ItemPreMeasure开始");
		
		if(pk==null){
			ServletUtils.outPrintFail(request, response, "测量编号不能为空,接收失败!");
			return;
		};
		
		try {
			ItemPreMeasure item = itemPreMeasureService.get(ItemPreMeasure.class, pk);
			
			if (item == null) {
				ServletUtils.outPrintFail(request, response, "没有找到工程测量单,无法进行接收操作!");
				return;
			}
			
			if (!"3".equalsIgnoreCase(item.getStatus().trim())) { // 3 接收
				ServletUtils.outPrintFail(request, response, "只有【接收】的测量单才能进行完成操作!");
				return;
			}
			
			item.setStatus("4"); // 4.完成
			item.setLastUpdate(new Date());
			item.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			item.setExpired("F");
			item.setActionLog("EDIT");
			item.setMeasureRemark(measureRemark);
			item.setDelayReson(delayReson);
			item.setConfirmCzy(this.getUserContext(request).getCzybh());
			item.setConfirmDate(new Date());
			item.setChgStatus("0");
			this.itemPreMeasureService.update(item);
			ServletUtils.outPrintSuccess(request, response, "完成测量成功!", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法完成该工程测量单!");
		}
	}
	
	/**
	 * 退回ItemPreMeasure
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doBack")
	public void doBack(HttpServletRequest request, HttpServletResponse response, Integer pk, String cancelRemark){
		logger.debug("退回ItemPreMeasure开始");
		if(pk==null){
			ServletUtils.outPrintFail(request, response, "itemPreMeasure编号不能为空,退回失败");
			return;
		};
		
		try {
			ItemPreMeasure itemPreMeasure = this.itemPreMeasureService.get(ItemPreMeasure.class, pk);
			if (itemPreMeasure == null) {
				ServletUtils.outPrintFail(request, response, "没有找到测量单,无法进行退回操作!");
				return;
			}
			
			if (!"3".equals(itemPreMeasure.getStatus().trim())) {
				ServletUtils.outPrintFail(request, response, "测量单未处于【接收】状态,无法进行退回操作!");
				return;
			}
			
			itemPreMeasure.setStatus("2"); // 2.接收退回
			itemPreMeasure.setLastUpdate(new Date());
			itemPreMeasure.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemPreMeasure.setExpired("F");
			itemPreMeasure.setActionLog("EDIT");
			itemPreMeasure.setCancelRemark(cancelRemark);
			this.itemPreMeasureService.update(itemPreMeasure);
			ServletUtils.outPrintSuccess(request, response, "退回成功", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法退回该测量单");
		}
	}
	
	/**
	 * 查询明细JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetail(HttpServletRequest request,	
			HttpServletResponse response, String id) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreAppDetailService.findPageByNo(page,id,this.getUserContext(request).getEmnum());
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * check下载
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/checkDownLoad")
	public void checkDownLoad(HttpServletRequest request, HttpServletResponse response, 
			String urlString){
		try{
			ServletUtils.checkImageFromNetByUrl(urlString);
			ServletUtils.outPrintSuccess(request, response, "图片存在！");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "图片不存在！");
		}
	}
	
	/**
	 * 下载
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doDownLoad")
	public void doDownLoad(HttpServletRequest request, HttpServletResponse response, 
			String urlString, String fileName){
		super.downLoad(request, response, urlString, fileName);
	}
	
	/**
	 * 发送提醒消息
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/sendMessage")
	public void sendMessage(HttpServletRequest request, HttpServletResponse response){
		try{
			if("1".equals(this.getUserContext(request).getCzylb())){
				return;
			}
			int count = itemPreMeasureService.getMessageCount(this.getUserContext(request).getEmnum());
			int count_pj = itemPreMeasureService.getPrjJobMessageCount(this.getUserContext(request).getEmnum());
			if (count>0 && count_pj<=0){
				ServletUtils.outPrintSuccess(request, response, "您有【"+count+"】条申请状态的测量单!");
			}else if(count_pj>0 && count<=0){
				ServletUtils.outPrintSuccess(request, response, "您有【"+count_pj+"】条申请状态的任务单!");
			}else if(count>0 && count_pj>0){
				ServletUtils.outPrintSuccess(request, response, "您有【"+count+"】条申请状态的测量单和【"+count_pj+"】条申请状态的任务单!");
			}else{
				ServletUtils.outPrintFail(request, response, "提醒消息发送失败!");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "提醒消息发送失败!");
		}
	}
	
}
