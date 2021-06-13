package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.CustContractTempUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustContractTemp;
import com.house.home.service.basic.CustContractTempService;
import com.house.home.service.basic.XtcsService;

@Controller
@RequestMapping("/admin/custContractTemp")
public class CustContractTempController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustContractTempController.class);

	@Autowired
	private CustContractTempService custContractTempService;
	@Autowired
	private XtcsService xtcsService;

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
			HttpServletResponse response, CustContractTemp custContractTemp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custContractTempService.findPageBySql(page, custContractTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustContractTemp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_list");
	}
	/**
	 * CustContractTemp查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_code");
	}
	/**
	 * 跳转到新增CustContractTemp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CustContractTemp页面");
		CustContractTemp custContractTemp = null;
		String docAttMaxSize = xtcsService.getQzById("DocAttMaxSz");
		
		if (StringUtils.isNotBlank(id)){
			custContractTemp = custContractTempService.get(CustContractTemp.class, Integer.parseInt(id));
			custContractTemp.setPk(null);
		}else{
			custContractTemp = new CustContractTemp();
		}
		custContractTemp.setM_umState("A");
		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_save")
			.addObject("custContractTemp", custContractTemp)
			.addObject("docAttMaxSize",docAttMaxSize)
			.addObject("custContractTempFileName", new JSONObject());
	}
	/**
	 * 跳转到修改CustContractTemp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer id){
		logger.debug("跳转到修改custContractTemp页面");
		CustContractTemp custContractTemp = null;
		String docAttMaxSize = xtcsService.getQzById("DocAttMaxSz");
		custContractTemp = custContractTempService.get(CustContractTemp.class, id);
		if (StringUtils.isNotBlank(custContractTemp.getBuilderCode())){
			Builder builder = custContractTempService.get(Builder.class, custContractTemp.getBuilderCode());
			custContractTemp.setBuilderDescr(builder.getDescr());
		}
		
		Map<String, Object> custContractTempFileName = custContractTempService.getCustContractTempFileName(custContractTemp.getPk());
		JSONObject jsonData = new JSONObject();
		if(custContractTempFileName != null){
			jsonData = JSONObject.fromObject(custContractTempFileName);
		}
		custContractTemp.setM_umState("M");
		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_save")
			.addObject("custContractTemp", custContractTemp)
			.addObject("docAttMaxSize",docAttMaxSize)
			.addObject("custContractTempFileName", jsonData);
	}
	
	/**
	 * 跳转到查看CustContractTemp页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer id){
		logger.debug("跳转到查看custContractTemp页面");
		CustContractTemp custContractTemp = custContractTempService.get(CustContractTemp.class, id);
		if (StringUtils.isNotBlank(custContractTemp.getBuilderCode())){
			Builder builder = custContractTempService.get(Builder.class, custContractTemp.getBuilderCode());
			custContractTemp.setBuilderDescr(builder.getDescr());
		}
		Map<String, Object> custContractTempFileName = custContractTempService.getCustContractTempFileName(custContractTemp.getPk());
		JSONObject jsonData = new JSONObject();
		if(custContractTempFileName != null){
			jsonData = JSONObject.fromObject(custContractTempFileName);
		}
		custContractTemp.setM_umState("V");
		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_save")
				.addObject("custContractTemp", custContractTemp)
				.addObject("custContractTempFileName", jsonData);
	}
	
	/**
	 * 查看归档合同
	 * @param request
	 * @param response
	 * @param CustContractTemp
	 * @return
	 */
	@RequestMapping("/goViewFiled")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			CustContractTemp custContractTemp){
		logger.debug("跳转到查看归档合同页面");

		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_viewFiled")
				.addObject("custContractTemp", custContractTemp);
	}
	
	/**
	 * 跳转到复制CustContractTemp页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			Integer id){
		logger.debug("跳转到复制custContractTemp页面");
		String docAttMaxSize = xtcsService.getQzById("DocAttMaxSz");
		CustContractTemp custContractTemp = custContractTempService.get(CustContractTemp.class, id);
		if (StringUtils.isNotBlank(custContractTemp.getBuilderCode())){
			Builder builder = custContractTempService.get(Builder.class, custContractTemp.getBuilderCode());
			custContractTemp.setBuilderDescr(builder.getDescr());
		}

		custContractTemp.setM_umState("C");
		custContractTemp.setFileName(null);
		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_save")
				.addObject("custContractTemp", custContractTemp)
				.addObject("docAttMaxSize",docAttMaxSize)
				.addObject("custContractTempFileName", new JSONObject());
	}
	
	/**
	 * 跳转到版本更新CustContractTemp页面
	 * @return
	 */
	@RequestMapping("/goUpdateVersion")
	public ModelAndView goUpdateVersion(HttpServletRequest request, HttpServletResponse response, 
			Integer id){
		logger.debug("跳转到版本更新custContractTemp页面");
		String docAttMaxSize = xtcsService.getQzById("DocAttMaxSz");
		CustContractTemp custContractTemp = custContractTempService.get(CustContractTemp.class, id);
		if (StringUtils.isNotBlank(custContractTemp.getBuilderCode())){
			Builder builder = custContractTempService.get(Builder.class, custContractTemp.getBuilderCode());
			custContractTemp.setBuilderDescr(builder.getDescr());
		}
		Map<String, Object> custContractTempFileName = custContractTempService.getCustContractTempFileName(custContractTemp.getPk());
		JSONObject jsonData = new JSONObject();
		if(custContractTempFileName != null){
			jsonData = JSONObject.fromObject(custContractTempFileName);
		}
		custContractTemp.setM_umState("U");
		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_save")
				.addObject("custContractTemp", custContractTemp)
				.addObject("docAttMaxSize",docAttMaxSize)
				.addObject("custContractTempFileName", jsonData);
	}
	
	/**
	 * 添加CustContractTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustContractTemp custContractTemp){
		logger.debug("添加custContractTemp开始");
		try {	
			custContractTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			if("C".equals(custContractTemp.getM_umState())){
				custContractTemp.setPk(null);
			}
			Result result = this.custContractTempService.doSaveProc(custContractTemp);
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
	 * 修改CustContractTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustContractTemp custContractTemp){
		logger.debug("修改CustContractTemp开始");
		try{
			custContractTemp.setLastUpdate(new Date());
			custContractTemp.setLastUpdatedBy(getUserContext(request).getCzybh());
			custContractTemp.setActionLog("EDIT");
			this.custContractTempService.update(custContractTemp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustContractTemp失败");
		}
	}
	
	/**
	 * 删除CustContractTemp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CustContractTemp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustContractTemp编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustContractTemp custContractTemp = custContractTempService.get(CustContractTemp.class, Integer.parseInt(deleteId));
				if(custContractTemp == null)
					continue;
				custContractTemp.setExpired("T");
				custContractTempService.update(custContractTemp);
			}
		}
		logger.debug("删除CustContractTemp IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CustContractTemp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustContractTemp custContractTemp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custContractTempService.findPageBySql(page, custContractTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"合同模板_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 查询goDetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustContractTemp custContractTemp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custContractTempService.goDetailJqGrid(page, custContractTemp);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 跳转到新增明细页面
	 * @return
	 */
	@RequestMapping("/goAddDetail")
	public ModelAndView goAddDetail(HttpServletRequest request, HttpServletResponse response, CustContractTemp custContractTemp){
		logger.debug("跳转到新增明细页面");
		return new ModelAndView("admin/basic/custContractTemp/custContractTemp_detail_add")
				.addObject("custContractTemp", custContractTemp);
	}
	
	/**
	 * 上传模板文件
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uploadCustContractTemp")
	public void uploadCustContractTemp(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			// 解析参数 
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String m_umState = multipartFormData.getParameter("m_umState"),
				   pk = multipartFormData.getParameter("pk");

			FileItem fileItem = multipartFormData.getFileItem();
			
			// 文档管理附件命名规则
			CustContractTempUploadRule custContractTempUploadRule = new CustContractTempUploadRule(fileItem.getName());

			// 调用上传工具将文件上传到服务器
			Result result = FileUploadUtils.upload(fileItem.getInputStream(), 
					custContractTempUploadRule.getFileName(),custContractTempUploadRule.getFilePath());
			
			if (!Result.SUCCESS_CODE.equals(result.getCode())) {
				logger.error("上传文件失败，原文件名:{}",fileItem.getName());
				ServletUtils.outPrintFail(request, response, "上传文件失败");
				return;
			}
			
			String fileName = custContractTempUploadRule.getFullName();
			
			if("M".equals(m_umState) || "U".equals(m_umState)){
				if(StringUtils.isNotBlank(fileName)){
					CustContractTemp custContractTemp = custContractTempService.get(CustContractTemp.class, Integer.parseInt(pk));
					custContractTemp.setFileName(fileName);
					custContractTempService.update(custContractTemp);
				}
			}
			
			// 返回文件全名
			ServletUtils.outPrintSuccess(request, response, custContractTempUploadRule.getFullName());
			
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response,CustContractTemp custContractTemp){
		try{ 
			String fileName1 = request.getParameter("fileName1");
			
			String fullName = "";
			if(custContractTemp.getPk() != null){
				custContractTemp = custContractTempService.get(CustContractTemp.class, custContractTemp.getPk());
			} else {
				ServletUtils.outPrintFail(request, response, "下载失败");
				return;
			}
			if(StringUtils.isNotBlank(custContractTemp.getFileName())){
				fullName = custContractTemp.getFileName();
			}
			
			String url= FileUploadUtils.DOWNLOAD_URL + fullName;
			String[] fileType = StringUtils.split(fullName, ".");
			
			// 调用下载工具
			ServletUtils.download(request, response, url, fileName1+"."+fileType[fileType.length-1]);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doDelFile")
	public void doDelFile(HttpServletRequest request,HttpServletResponse response, Integer pk){
		logger.debug("删除附件开始");
		try{
			CustContractTemp custContractTemp = new CustContractTemp();	
			if(pk != null){
				custContractTemp = custContractTempService.get(CustContractTemp.class, pk );
			}
			String fullName = custContractTemp.getFileName();
			custContractTemp.setLastUpdate(new Date());
			custContractTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());			
			
			FileUploadUtils.deleteFile(fullName);
			custContractTemp.setFileName(null);
			custContractTempService.update(custContractTemp);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除文档失败");
		}
	}
}
