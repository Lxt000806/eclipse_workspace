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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CzyMtRegion;
import com.house.home.entity.project.PrjWithHold;
import com.house.home.service.basic.CzyMtRegionService;


@Controller
@RequestMapping("/admin/czyMtRegion")
public class CzyMtRegionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CzyMtRegionController.class);

	@Autowired
	private CzyMtRegionService czyMtRegionService;


	/**
	 * 添加czyMtRegion
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CzyMtRegion czyMtRegion){
		logger.debug("添加czyMtRegion开始");
		try{
			if(czyMtRegionService.isHasRegion(czyMtRegion)){
				ServletUtils.outPrintFail(request, response, "不能添加重复区域！");
				return;
			}
			this.czyMtRegionService.save(czyMtRegion);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加麦田大区失败");
		}
	}
	
	
	/**
	 * 删除czyMtRegion
	 * @param request
	 * @param response
	 * @param roleId
	 */
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("删除czyMtRegion开始");
		if(StringUtils.isBlank(id)){
			ServletUtils.outPrintFail(request, response, "选择编号不能为空,删除失败");
			return;
		}else{
			CzyMtRegion czyMtRegion = czyMtRegionService.get(CzyMtRegion.class, Integer.parseInt(id));
			if(czyMtRegion==null){
				ServletUtils.outPrintFail(request, response, "选择记录不存在");
				return;
			}
			czyMtRegionService.delete(czyMtRegion);	
		}	
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	

}
