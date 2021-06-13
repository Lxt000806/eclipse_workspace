package com.house.home.client.controller.mall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.fileUpload.impl.WorkerAvatarPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.client.controller.ClientBaseController;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.client.service.evt.MallAppletsEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.GetShowBuildsResp;
import com.house.home.client.service.resp.MallAppletsWorkerResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.ShowService;
import com.house.home.service.mall.MallAppletsService;

@RequestMapping("/client/mallApplets")
@Controller
public class ClientMallAppletsController extends ClientBaseController {

	@Autowired
	private MallAppletsService mallAppletsService;
	@Autowired
	private ShowService showService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerList")
	public void getWorkerList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		MallAppletsEvt evt=new MallAppletsEvt();
		BasePageQueryResp<MallAppletsWorkerResp> respon=new BasePageQueryResp<MallAppletsWorkerResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(MallAppletsEvt) JSONObject.toBean(json, MallAppletsEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon, response, msg, respon, request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			mallAppletsService.getWorkerList(page, evt);
			List<MallAppletsWorkerResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(),MallAppletsWorkerResp.class);
			for (int i = 0; i < listBean.size(); i++) {
				listBean.get(i).setAvatarPic(OssConfigure.cdnAccessUrl+"/"+listBean.get(i).getAvatarPic());
			}
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages() > page.getPageNo() ? true : false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon, response, msg, respon, request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBuildingSites")
	public void getBuildingSites(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		GetShowBuildsEvt evt=new GetShowBuildsEvt();
		BasePageQueryResp<GetShowBuildsResp> respon=new BasePageQueryResp<GetShowBuildsResp>();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetShowBuildsEvt) JSONObject.toBean(json, GetShowBuildsEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(evt.getPageSize());
			showService.getShowBuilds(page, evt);
			List<GetShowBuildsResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), GetShowBuildsResp.class);
			if(listBean != null && listBean.size() > 0){
				for(int i = 0;i < listBean.size();i++){
					List<Map<String, Object>> photoList = this.showService.getPrjProgConfirmPhoto(listBean.get(i).getPrjProgConfirmNo(),0);
					if(photoList == null){
						photoList = new ArrayList<Map<String,Object>>();
					}
					for(int j = 0;j < photoList.size();j++){
						if("1".equals(photoList.get(j).get("isSendYun").toString())){
							photoList.get(j).put("src", OssConfigure.cdnAccessUrl+"/prjProgNew/"+photoList.get(j).get("src").toString().substring(0, 5)+"/"+photoList.get(j).get("src").toString());
						}else{
							String src = getPrjProgPhotoDownloadPath(request, photoList.get(j).get("src").toString());
							photoList.get(j).put("src", src+photoList.get(j).get("src").toString());
						}
					}
					listBean.get(i).setPhotos(photoList);
				}
			}
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			respon.setPageNo(evt.getPageNo());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
