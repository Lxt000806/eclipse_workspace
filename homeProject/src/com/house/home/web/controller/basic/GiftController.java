package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.house.home.entity.basic.Gift;
import com.house.home.service.basic.GiftService;

@Controller
@RequestMapping("/admin/gift")
public class GiftController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GiftController.class);

	@Autowired
	private GiftService giftService;

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
			HttpServletResponse response, Gift gift) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftService.findPageBySql(page, gift);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Gift列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/gift/gift_list");
	}
	/**
	 * Gift查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,Gift gift) throws Exception {
      
		
		return new ModelAndView("admin/basic/gift/gift_code").addObject("gift", gift);
	}
	/**
	 * 跳转到新增Gift页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增Gift页面");
		Gift gift = null;
		if (StringUtils.isNotBlank(id)){
			gift = giftService.get(Gift.class, Integer.parseInt(id));
			gift.setPk(null);
		}else{
			gift = new Gift();
		}
		gift.setM_umState("A");
		gift.setIsProvideDiscToken("0");
		return new ModelAndView("admin/basic/gift/gift_save")
			.addObject("gift", gift);
	}
	
	/**
	 * 跳转到复制页面
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
    @RequestMapping("/goCopy")
    public ModelAndView goCopy(HttpServletRequest request,
            HttpServletResponse response, int id) {
        
        logger.debug("跳转到复制Gift页面");
        
        Gift gift = giftService.get(Gift.class, id);
        
        gift = gift == null ? new Gift() : gift;
        gift.setM_umState("C");
        
        return new ModelAndView("admin/basic/gift/gift_copy").addObject("gift", gift);
    }
    
    /**
     * 执行复制操作
     * 
     * @param request
     * @param response
     * @param gift
     */
    @RequestMapping("/doCopy")
    public void doCopy(HttpServletRequest request,
            HttpServletResponse response, Gift gift) {
        
        logger.debug("复制Gift开始");
        
        try {
            gift.setLastUpdatedBy(getUserContext(request).getCzybh());
            Result result = giftService.doSaveProc(gift);
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, "保存成功");
            } else {
                ServletUtils.outPrintFail(request, response, "错误信息：" + result.getInfo());
            }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存失败");
        }
    }
	
	/**
	 * 跳转到修改Gift页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改Gift页面");
		Gift gift = null;
		if (StringUtils.isNotBlank(id)){
			gift = giftService.get(Gift.class, Integer.parseInt(id));
		}else{
			gift = new Gift();
		}
		gift.setM_umState("M");
		return new ModelAndView("admin/basic/gift/gift_save")
			.addObject("gift", gift);
	}
	
	/**
	 * 设定产品线
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/goSetCustType")
    public ModelAndView goSetCustType(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("admin/basic/gift/gift_setCustType");
    }
    
    /**
     * 选择产品线
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/goSelectCustType")
    public ModelAndView goSelectCustType(HttpServletRequest request, HttpServletResponse response,
            String giftPkString) {
        return new ModelAndView("admin/basic/gift/gift_setCustType_select")
                .addObject("giftPkString", giftPkString);
    }
    
    /**
     * 保存设定的产品线
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/doSetCustType")
    public void doSetCustType(HttpServletRequest request, HttpServletResponse response,
            @RequestParam Map<String, String> params) {
        
        try {
            
            params.put("czybh", getUserContext(request).getCzybh());
            giftService.doSetCustType(params);
            
            ServletUtils.outPrintSuccess(request, response);
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, e.getMessage());
            e.printStackTrace();
        }
    }
	
	/**
	 * 跳转到查看Gift页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看Gift页面");
		Gift gift = giftService.get(Gift.class, Integer.parseInt(id));
		gift.setM_umState("V");
		return new ModelAndView("admin/basic/gift/gift_save")
				.addObject("gift", gift);
	}
	/**
	 * 添加Gift
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Gift gift){
		logger.debug("添加Gift开始");
		try {	
			gift.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.giftService.doSaveProc(gift);
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
	 * 修改Gift
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Gift gift){
		logger.debug("修改Gift开始");
		try{
			gift.setLastUpdate(new Date());
			gift.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.giftService.update(gift);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Gift失败");
		}
	}
	
	/**
	 * 删除Gift
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除Gift开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "Gift编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Gift gift = giftService.get(Gift.class, Integer.parseInt(deleteId));
				if(gift == null)
					continue;
				gift.setExpired("T");
				giftService.update(gift);
			}
		}
		logger.debug("删除Gift IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *Gift导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Gift gift){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		giftService.findPageBySql(page, gift);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"赠送项目管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 查询goCustTypeJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCustTypeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCustTypeJqGrid(HttpServletRequest request,
			HttpServletResponse response, Gift gift) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftService.goCustTypeJqGrid(page, gift);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询goItemJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, Gift gift) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftService.goItemJqGrid(page, gift);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到查看Gift页面
	 * @return
	 */
	@RequestMapping("/goCustTypeAdd")
	public ModelAndView goCustTypeAdd(HttpServletRequest request, HttpServletResponse response, 
			Gift gift){
		if("A".equals(gift.getM_umState())){
			if(StringUtils.isEmpty(gift.getCustTypes())){
				gift.setCustTypes("-1");
			}
			return new ModelAndView("admin/basic/gift/gift_custType_batchAdd")
			.addObject("gift", gift);
		}else{
			return new ModelAndView("admin/basic/gift/gift_custType_add")
			.addObject("gift", gift);
		}
		
	}
	/**
	 * 跳转到查看Gift页面
	 * @return
	 */
	@RequestMapping("/goItemAdd")
	public ModelAndView goItemAdd(HttpServletRequest request, HttpServletResponse response, 
			Gift gift){
		return new ModelAndView("admin/basic/gift/gift_item_add")
				.addObject("gift", gift);
	}
}
