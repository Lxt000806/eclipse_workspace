package com.house.home.web.controller.basic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemProm;
import com.house.home.entity.finance.PerfCycle;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.project.BaseItemChg;
import com.house.home.service.basic.ItemPromService;
import com.house.home.service.basic.ItemService;

@Controller
@RequestMapping("/admin/itemProm")
public class ItemPromController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPromController.class);

	@Autowired
	private ItemPromService itemPromService;
	@Autowired
	private ItemService itemService;

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
			HttpServletResponse response, ItemProm itemProm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPromService.findPageBySql(page, itemProm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemProm列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/itemProm/itemProm_list");
	}
	/**
	 * ItemProm查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/itemProm/itemProm_code");
	}
	/**
	 * 跳转到新增ItemProm页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemProm页面");
		ItemProm itemProm = null;
		if (StringUtils.isNotBlank(id)){
			itemProm = itemPromService.get(ItemProm.class, Integer.parseInt(id));
			itemProm.setPk(null);
		}else{
			itemProm = new ItemProm();
		}
		
		return new ModelAndView("admin/basic/itemProm/itemProm_save")
			.addObject("itemProm", itemProm);
	}
	/**
	 * 跳转到修改ItemProm页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改ItemProm页面");
		Map<String, Object> itemProm=itemPromService.findListByPk(pk).get(0);
		itemProm.put("m_umState", "M");
		return new ModelAndView("admin/basic/itemProm/itemProm_update")
			.addObject("itemProm", itemProm);
	}
	
	/**
	 * 跳转到查看ItemProm页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看ItemProm页面");
		Map<String, Object> itemProm=itemPromService.findListByPk(pk).get(0);
		itemProm.put("m_umState", "V");
		return new ModelAndView("admin/basic/itemProm/itemProm_update")
			.addObject("itemProm", itemProm);
	}
	/**
	 * 添加ItemProm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemProm itemProm){
		logger.debug("添加ItemProm开始");
		try{
			itemProm.setExpired("F");
			itemProm.setActionLog("ADD");
			itemProm.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemProm.setLastUpdate(new Date());
			this.itemPromService.save(itemProm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemProm失败");
		}
	}
	
	/**
	 * 修改ItemProm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemProm itemProm){
		logger.debug("修改ItemProm开始");
		try{
			itemProm.setActionLog("EDIT");
			itemProm.setLastUpdate(new Date());
			itemProm.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPromService.update(itemProm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemProm失败");
		}
	}
	
	/**
	 * 删除ItemProm
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemProm开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemProm编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemProm itemProm = itemPromService.get(ItemProm.class, Integer.parseInt(deleteId));
				if(itemProm == null)
					continue;
				itemPromService.delete(itemProm);
			}
		}
		logger.debug("删除ItemProm IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemProm导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemProm itemProm){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPromService.findPageBySql(page, itemProm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料促销管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 是否已存在促销记录
	 * @param request
	 * @param response
	 * @param no
	 */
	@ResponseBody
	@RequestMapping("/checkIsExists")
	public String checkIsExists(HttpServletRequest request, HttpServletResponse response, ItemProm itemProm){
		logger.debug("是否已存在促销记录");
		List<Map<String, Object>> list=this.itemPromService.checkIsExists(itemProm);
		if(list.size()>0){
			return "1";
		}
		return "0";
	}
	/**
	 * 材料是否存在
	 * @param request
	 * @param response
	 * @param no
	 */
	@ResponseBody
	@RequestMapping("/checkIsItemCode")
	public String checkIsItemCode(HttpServletRequest request, HttpServletResponse response, ItemProm itemProm){
		logger.debug("材料是否存在");
		List<Map<String, Object>> list=this.itemPromService.checkIsItemCode(itemProm);
		if(list.size()>0){
			return "1";
		}
		return "0";
	}
	/**
	 * 更新促销价
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/updatePrice")
	public void updatePrice(HttpServletRequest request, HttpServletResponse response, ItemProm itemProm){
		logger.debug("更新促销价");
		itemProm.setLastUpdatedBy(getUserContext(request).getCzybh());
		this.itemPromService.updatePrice(itemProm);
	}
	/**
	 * 跳转到查看材料促销查询页面
	 * @return
	 */
	@RequestMapping("/goItemQuery")
	public ModelAndView goItemQuery(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到材料促销查询页面");
		return new ModelAndView("admin/basic/itemProm/itemProm_itemQuery");
	}
	/**
	 * 查询itemJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemProm itemProm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPromService.itemQuery(page, itemProm, getUserContext(request).getItemRight());
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到导入excel页面
	 * @return
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到导入excel页面");
		return new ModelAndView("admin/basic/itemProm/itemProm_import");
	}
	/**
	 * 
	 * 加载导入信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/loadImport")
	@ResponseBody
	public Map<String, Object> loadImport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ExcelImportUtils<ItemProm> eUtils = new ExcelImportUtils<ItemProm>();
		boolean hasInvalid = false;
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// 如果是普通 表单参数
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();
			if ("file".equals(fieldName)){
				in=obit.getInputStream();
			}
		}
		
		try {
			List<Map<String,Object>> result = eUtils.importExcel(in);
			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
			
			int i = 1;
			for(Map<String,Object> itemProm:result){
				Map<String,Object> data = new HashMap<String, Object>();
				String itemcode = (String) itemProm.get("材料编号");
				String promprice = (String) itemProm.get("促销价");
				String promcost = (String) itemProm.get("促销进价");
				String begindate = (String) itemProm.get("促销开始日期");
				String enddate = (String) itemProm.get("促销结束日期");
				String isinvalid, isinvaliddescr;
				if (StringUtils.isBlank(itemcode) || StringUtils.isBlank(promprice) || StringUtils.isBlank(promcost) || StringUtils.isBlank(begindate)|| StringUtils.isBlank(enddate)){
					isinvalid = "1";
					isinvaliddescr = "无效,存在异常数据";
				}else{
					isinvalid = "0";
					isinvaliddescr = "有效";
				}
				List<Map<String, Object>> itemDataList=null;
				//验证材料是否有效
				if (StringUtils.isNotBlank(itemcode)){
					itemDataList=itemPromService.findItemData(itemcode);
					if (itemDataList!=null && itemDataList.size()>0){
						if ("0".equals(isinvalid) && !"F".equals(itemDataList.get(0).get("expired").toString())){
							isinvalid = "2";
							isinvaliddescr = "无效的材料编号";
						}
					}else{
						if ("0".equals(isinvalid)){
							isinvalid = "2";
							isinvaliddescr = "无效的材料编号";
						}
					}
				}
				data.put("isinvalid", isinvalid);
				data.put("isinvaliddescr", isinvaliddescr);
				data.put("itemcode", itemcode);
				data.put("promprice", promprice);
				data.put("promcost",promcost);
				data.put("begindate", begindate);
				data.put("enddate", enddate);
				if(itemDataList!=null && itemDataList.size()>0){
					data.putAll(itemDataList.get(0));//合并map
				}
				if (!"0".equals(isinvalid)){
					hasInvalid = true;
				}
				datas.add(data);
				i++;
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
			map.put("hasInvalid", hasInvalid);
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "当前操作使用了错误类型的值,请检查数值列是否包含非法字符!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * 材料促销管理导入excel
	 * @param request
	 * @param response
	 * @param no
	 */
	@ResponseBody
	@RequestMapping("/doImport")
	public Map<String, Object> doImport(HttpServletRequest request, HttpServletResponse response, ItemProm itemProm){
		logger.debug("材料促销管理导入excel");
		itemProm.setLastUpdatedBy(getUserContext(request).getCzybh());
		return this.itemPromService.importExcel(itemProm);
	}
}
