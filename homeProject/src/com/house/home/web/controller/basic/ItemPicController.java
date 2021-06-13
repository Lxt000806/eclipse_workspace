package com.house.home.web.controller.basic;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ItemPic;
import com.house.home.service.basic.ItemPicService;


@Controller
@RequestMapping("/admin/itemPic")
public class ItemPicController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPicController.class);

	@Autowired
	private ItemPicService itemPicService;

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
			HttpServletResponse response, ItemPic itemPic) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPicService.findPageBySql(page, itemPic);
		return new WebPage<Map<String,Object>>(page);
	}
	
}
