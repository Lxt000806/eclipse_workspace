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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.MtRegion;
import com.house.home.entity.basic.Uom;
import com.house.home.service.basic.MtRegionService;

@Controller
@RequestMapping("/admin/mtRegion")
public class MtRegionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MtRegionController.class);

	@Autowired
	private MtRegionService mtRegionService;

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
			HttpServletResponse response, MtRegion mtRegion) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mtRegionService.findPageBySql(page, mtRegion);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * MtRegion列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mtRegion/mtRegion_list");
	}
	/**
	 * MtRegion查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mtRegion/mtRegion_code");
	}
	/**
	 * 根据ID查询
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getMtRegion")
	@ResponseBody
	public JSONObject getSupplier(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		MtRegion mtRegion = mtRegionService.get(MtRegion.class, id);
		if(mtRegion== null){
			return this.out("系统中不存在regioncode="+id+"的麦田大区编号", false);
		}
		return this.out(mtRegion, true);
	}
	
	

}
