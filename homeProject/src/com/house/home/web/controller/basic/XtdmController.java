package com.house.home.web.controller.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Xtdm;
import com.house.home.service.basic.XtdmService;

@Controller
@RequestMapping("/admin/xtdm")
public class XtdmController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(XtdmController.class);

	@Autowired
	private XtdmService xtdmService;
	@Resource(name = "xtdmCacheManager")
	private ICacheManager xtdmCacheManager;
	
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
			HttpServletResponse response,Xtdm xtdm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		xtdmService.findPageBySql(page, xtdm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 系统代码列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/xtdm/xtdm_list");
	}
	
	/**
	 * 跳转到新增系统代码页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增系统代码页面");
		Xtdm xtdm = new Xtdm();
		
		return new ModelAndView("admin/basic/xtdm/xtdm_save")
			.addObject("xtdm", xtdm);
	}
	/**
	 * 跳转到修改系统代码页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id, String cbm, Integer ibm){
		logger.debug("跳转到修改系统代码页面");
		Xtdm xtdm = null;
		if (StringUtils.isNotBlank(id)){
			xtdm = xtdmService.getByThree(id,cbm,ibm);
		}else{
			xtdm = new Xtdm();
		}
		
		return new ModelAndView("admin/basic/xtdm/xtdm_update")
			.addObject("xtdm", xtdm);
	}
	
	/**
	 * 跳转到查看系统代码页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response,String id,String cbm,Integer ibm){
		logger.debug("跳转到查看系统代码页面");
		Xtdm xtdm = xtdmService.getByThree(id,cbm,ibm);
		
		return new ModelAndView("admin/basic/xtdm/xtdm_detail")
				.addObject("xtdm", xtdm);
	}
	/**
	 * 添加系统代码
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Xtdm xtdm){
		logger.debug("添加 系统代码开始");
		try{
			Xtdm xt = this.xtdmService.getByThree(xtdm.getId(), xtdm.getCbm(), xtdm.getIbm());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "系统代码对象ID,字母编码,数字编码重复");
				return;
			}
			this.xtdmService.save(xtdm);
			xtdmCacheManager.refresh();
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加系统代码失败");
		}
	}
	
	/**
	 * 修改系统代码
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Xtdm xtdm){
		logger.debug("修改系统代码开始");
		try{
			Xtdm xt = this.xtdmService.getByThree(xtdm.getId(), xtdm.getCbm(), xtdm.getIbm());
			if (xt!=null){
				this.xtdmService.update(xtdm);
				xtdmCacheManager.refresh();
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.xtdmService.save(xtdm);
				xtdmCacheManager.refresh();
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改系统代码失败");
		}
	}
	
	/**
	 * 删除系统代码
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除系统代码开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "系统代码编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				String[] str = deleteId.split("_");
				Xtdm xtdm = this.xtdmService.getByThree(str[0],str[1],Integer.parseInt(str[2]));
				if(xtdm == null)
					continue;
				this.xtdmService.delete(xtdm);
			}
		}
		xtdmCacheManager.refresh();
		logger.debug("删除系统代码 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
    @RequestMapping(value = "/sourcesOrChannels/{level}/{code}")
    @ResponseBody
    public JSONObject getSourcesOrChannels(@PathVariable int level, @PathVariable String code) {
        
        List<Map<String, Object>> results = xtdmService.getSourcesOrChannels(level, code);
        
        return out(results, true);
    }

}
