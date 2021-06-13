package com.house.home.web.controller.insales;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.service.driver.ItemAppSendPhotoService;

@Controller
@RequestMapping("/admin/itemAppSendPhoto")
public class ItemAppSendPhotoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemAppSendPhotoController.class);

	@Autowired
	private ItemAppSendPhotoService itemAppSendPhotoService;
	
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
			HttpServletResponse response,ItemSendPhoto itemSendPhoto) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSendPhotoService.findPageBySql(page, itemSendPhoto);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPhotoList")
	@ResponseBody
	public List<Map<String, Object>> getPhotoList(HttpServletRequest request,
			HttpServletResponse response,ItemSendPhoto itemSendPhoto) throws Exception {
		List<Map<String, Object>> list=itemAppSendPhotoService.getPhotoList(itemSendPhoto.getSendNo(),itemSendPhoto.getType());
		return list;
	}

	@RequestMapping("/delPhotoList")
	@ResponseBody
	public void getPhotoList(HttpServletRequest request,
			HttpServletResponse response,String id) throws Exception {
		itemAppSendPhotoService.delPhotoList(id);
	}
}
