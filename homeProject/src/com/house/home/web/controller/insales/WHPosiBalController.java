package com.house.home.web.controller.insales;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.bean.insales.WHPosiBalBean;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.insales.WareHousePosi;
import com.house.home.service.basic.ItemService;
import com.house.home.service.insales.WHPosiBalService;
import com.house.home.service.insales.WareHousePosiService;

@Controller
@RequestMapping("/admin/whPosiBal")
public class WHPosiBalController extends BaseController{
	@Autowired
	private  WHPosiBalService whPosiBalService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private WareHousePosiService wareHousePosiService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {
		// 传入操作员编号
		wareHouse.setCzybh(getUserContext(request).getCzybh());
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whPosiBalService.findPageByInnerSql(page, wareHouse);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description:  批量移动分页信息
	 */
	@RequestMapping("/goMoveJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getMoveJqGrid(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whPosiBalService.findMovePageBySql(page, wareHouse);
		return new WebPage<Map<String,Object>>(page);
	}
	
	// 查询材料分页信息
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whPosiBalService.findItemPageBySql(page, wareHouse);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/whPosiBal/whPosiBal_list");
	}
	
	@RequestMapping("/goUp")
	public ModelAndView goUp(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {
		// 当数据都存在时，获取数据到Win
		if (null != wareHouse.getPk()&&StringUtils.isNotBlank(wareHouse.getCode())
				&&StringUtils.isNotBlank(wareHouse.getItCode())) {
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			whPosiBalService.findDataBySql(page, wareHouse);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("qtycal", 0);
			map.put("qtyno", 0);
			// 当返回数据不为空时
			if (page.getTotalCount()>0) {
				map = page.getResult().get(0);
				map.put("qtyno",  (int)Double.parseDouble(map.get("qtyno").toString()));//先通过double对object解包，再通过强制转换成int类型，丢弃浮点数的小数部分
				map.put("qtycal", (int)Double.parseDouble(map.get("qtycal").toString()));
			}
			return new ModelAndView("admin/insales/whPosiBal/whPosiBal_win")
				.addObject("whPosiBal", map)
				.addObject("wareHouse", wareHouse);
		}
		return new ModelAndView("admin/insales/whPosiBal/whPosiBal_win");
	}
	
	@RequestMapping("/goDown")
	public ModelAndView goDown(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {
		// 当数据都存在时，获取数据到Win
		if (null != wareHouse.getPk()&&StringUtils.isNotBlank(wareHouse.getCode())
				&&StringUtils.isNotBlank(wareHouse.getItCode())) {
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			whPosiBalService.findDataBySql(page, wareHouse);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("qtycal", 0);
			map.put("qtyno", 0);
			// 当返回数据不为空时
			if (page.getTotalCount()>0) {
				map = page.getResult().get(0);
				map.put("qtyno", (int)Double.parseDouble(map.get("qtyno").toString()));//先通过double对object解包，再通过强制转换成int类型，丢弃浮点数的小数部分
				map.put("qtycal", (int)Double.parseDouble(map.get("qtycal").toString()));
			}
			return new ModelAndView("admin/insales/whPosiBal/whPosiBal_win")
				.addObject("whPosiBal", map)
				.addObject("wareHouse", wareHouse);
		}
		return new ModelAndView("admin/insales/whPosiBal/whPosiBal_win");
	}
	
	@RequestMapping("/goMoveList")
	public ModelAndView goMoveList(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {

		return new ModelAndView("admin/insales/whPosiBal/whPosiBal_moveList")
			.addObject("wareHouse", wareHouse);
	}
	
	// 未上架材料明细增加 
	@RequestMapping("/goNotUpItemSave")
	public ModelAndView goNotUpItemSave(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {

		return new ModelAndView("admin/insales/whPosiBal/whPosiBal_notUpItemSave")
			.addObject("wareHouse", wareHouse);
	}
	
	//  导入Excel
	@RequestMapping("/goImportExcel")
	public ModelAndView goImportExcel(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {

		return new ModelAndView("admin/insales/whPosiBal/whPosiBal_importExcel")
			.addObject("wareHouse", wareHouse);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,WareHouse wareHouse){
		logger.debug("上下架保存");
		try {
			wareHouse.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "无数据");
				return;
			}
			Result result = this.whPosiBalService.doSave(wareHouse);
			if (result.isSuccess()){
				ServletUtils.outPrint(request, response, true, "保存成功", null, true);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	//  读取Excel文件
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<WHPosiBalBean> eUtils=new ExcelImportUtils<WHPosiBalBean>();// 导入EXCEL导入工具, 产出指定pojo 列表
		String code="";
		DiskFileItemFactory fac = new DiskFileItemFactory();// 创建FileItem 对象的工厂——环境
		ServletFileUpload upload = new ServletFileUpload(fac);// 核心操作类
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);// 解析请求正文内容
		Iterator it = fileList.iterator();// 迭代器
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// 如果是普通表单参数
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();
			if ("code".equals(fieldName)){
				code = fieldValue;
			}
			if ("file".equals(fieldName)){
				in=obit.getInputStream();
			}
		}
		// 必须包含的列名
		titleList.add("产品");
		titleList.add("原库位");
		titleList.add("目的库位");
		titleList.add("数量");
		
		try {
			List<WHPosiBalBean> result=eUtils.importExcel(in, WHPosiBalBean.class,titleList);// 必须包含的标题列表，null则无限制
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			int i=0;
			for(WHPosiBalBean whPosiBalBean:result){
				i++;
				if(StringUtils.isNotBlank(whPosiBalBean.getError())){
					map.put("success", false);
					map.put("returnInfo", whPosiBalBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				// 检查数据是否在tItemWHBal表中存在
				Boolean hasItem = whPosiBalService.hasItem(code,whPosiBalBean.getItCode());
				if (!hasItem) {
					continue;
				}
				// 将数据存入map中
				Map<String,Object> data=new HashMap<String, Object>();
				// 获取Descr
				Item item = itemService.get(Item.class, whPosiBalBean.getItCode());
				WareHousePosi fromWhPosi = wareHousePosiService.get(WareHousePosi.class, whPosiBalBean.getFromKwPk());
				WareHousePosi toWhPosi = wareHousePosiService.get(WareHousePosi.class, whPosiBalBean.getToKwPk());
				
				if(!StringUtils.isNotBlank(whPosiBalBean.getItCode())
						||(null == whPosiBalBean.getFromKwPk()&&null == whPosiBalBean.getToKwPk())
						||null==item) {
					data.put("isinvalid", "1");
				}else{
					data.put("isinvalid", "0");// 有效
				}
				
				if("1".equals(data.get("isinvalid"))){
					data.put("isinvaliddescr", "无效,存在异常数据");
					map.put("hasInvalid", true);
				}else{
					data.put("isinvaliddescr", "有效");
					// 获取已上架数量和未上架数量的map
					WareHouse wareHouse = new WareHouse();
					if (StringUtils.isNotBlank(code)) {
						wareHouse.setCode(code);
					} else {
						map.put("success", false);
						map.put("returnInfo", "无仓库编号");
						map.put("hasInvalid", true);
						return map;
					}
					wareHouse.setItCode(whPosiBalBean.getItCode());
					wareHouse.setPk(whPosiBalBean.getFromKwPk());
					Page<Map<String,Object>> page = this.newPageForJqGrid(request);
					whPosiBalService.findMovePageBySql(page, wareHouse);
					if (page.getTotalCount() > 0) {// 如果存在数据
						Map<String, Object> qtyMap = page.getResult().get(0);
						// 合并两个map
						data.putAll(qtyMap);
					} else {
						data.put("qtyno", "0");
						data.put("qtycal", "0");
					}
					
					data.put("itcode", whPosiBalBean.getItCode());
					data.put("fromkwpk", whPosiBalBean.getFromKwPk());
					data.put("tokwpk", whPosiBalBean.getToKwPk());
					if (null != item.getDescr()) {
						data.put("itemdescr", item.getDescr());
					}
					if (null != fromWhPosi) {
						data.put("fromkw", fromWhPosi.getDesc1());
					}
					if (null != toWhPosi) {
						data.put("tokw", toWhPosi.getDesc1());
					}
					if(null != whPosiBalBean.getQtyMove()){
						data.put("qtymove",Math.round(whPosiBalBean.getQtyMove()));
					}else{
						data.put("qtymove",0);
					}
				}
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
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
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WareHouse wareHouse){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		wareHouse.setCzybh(getUserContext(request).getCzybh());
		whPosiBalService.findPageByInnerSql(page, wareHouse);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"库位调整_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	//  检查pk是否存在
	@RequestMapping("/checkToWkPk")
	public void checkToWkPk(HttpServletRequest request,HttpServletResponse response,
			String code, Integer pk) {
		try {
			if (StringUtils.isEmpty(code)) {
				ServletUtils.outPrintFail(request, response, "无效code");
			}
			boolean hasPk = this.whPosiBalService.checkPk(code, pk);
			if (hasPk) {
				ServletUtils.outPrintSuccess(request, response, "成功");
			} else {
				ServletUtils.outPrintFail(request, response, "失败");
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "失败");
		}
	}
	
	// ajax获取上下架数量
	@RequestMapping("/getCal")
	public void getCal(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 当数据都存在时，获取数据到Win
			if (null != wareHouse.getPk()
					&& StringUtils.isNotBlank(wareHouse.getItCode())) {
				Page<Map<String, Object>> page = this.newPageForJqGrid(request);
				whPosiBalService.findDataBySql(page, wareHouse);
				map.put("qtycal", 0);
				map.put("qtyno", 0);
				// 当返回数据不为空时
				if (page.getTotalCount() > 0) {
					map = page.getResult().get(0);
					map.put("qtyno", (int)Double.parseDouble(map.get("qtyno").toString()));//先通过double对object解包，再通过强制转换成int类型，丢弃浮点数的小数部分
					map.put("qtycal", (int)Double.parseDouble(map.get("qtycal").toString()));
				}
				ServletUtils.outPrintFail(request, response, map);
			}
		} catch (Exception e) {
			map.put("qtycal", "pk、itCode不能为空");
			map.put("qtyno", "pk、itCode不能为空");
			ServletUtils.outPrintFail(request, response, map);
		}
	}
	
}
