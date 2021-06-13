package com.house.home.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.home.client.service.evt.QualityCheckBoardEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.QualityCheckBoardResp;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.project.CustWorker;
import com.house.home.service.project.SignDetailService;
import com.house.home.service.query.QualityCheckBoardService;

@RequestMapping("/client/qualityCheckBoard")
@Controller
public class ClientQualityCheckBoardController extends ClientBaseController{
	@Autowired
	private QualityCheckBoardService qualityCheckBoardService;
	@Autowired
	private SignDetailService signDetailService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getQualityCheckBoardList")
	public void getQualityCheckBoardList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		QualityCheckBoardEvt evt=new QualityCheckBoardEvt();
		BasePageQueryResp<QualityCheckBoardResp> respon=new BasePageQueryResp<QualityCheckBoardResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (QualityCheckBoardEvt)JSONObject.toBean(json,QualityCheckBoardEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(10);
			CustWorker custWorker = new CustWorker();
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setWorkType12Dept(evt.getWorkType12Dept());
			custWorker.setWorkerClassify(evt.getWorkerClassify());
			qualityCheckBoardService.findPageBySql(page, custWorker);
			List<QualityCheckBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), QualityCheckBoardResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getQualityCheckBoardDetailList")
	public void getQualityCheckBoardDetailList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		QualityCheckBoardEvt evt=new QualityCheckBoardEvt();
		BasePageQueryResp<QualityCheckBoardResp> respon=new BasePageQueryResp<QualityCheckBoardResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			evt = (QualityCheckBoardEvt)JSONObject.toBean(json,QualityCheckBoardEvt.class);
			interfaceLog.setRequestContent(json.toString());
			//对象字段合法性验证
			Errors errors = new DirectFieldBindingResult(evt, "evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Page page = new Page();
			page.setPageNo(evt.getPageNo());
			page.setPageSize(10);
			CustWorker custWorker = new CustWorker();
			custWorker.setWorkType12(evt.getWorkType12());
			custWorker.setWorkType12Dept(evt.getWorkType12Dept());
			custWorker.setWorkerClassify(evt.getWorkerClassify());
			custWorker.setSourceType(evt.getSourceType());
			qualityCheckBoardService.findDetailBySql(page, custWorker);
			List<QualityCheckBoardResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), QualityCheckBoardResp.class);
			if ("4".equals(custWorker.getSourceType())) {
				if(listBean != null && listBean.size() > 0){
					for(int i = 0;i < listBean.size();i++){
						List<Map<String, Object>> photoList =  new ArrayList<Map<String,Object>>();
						List<Map<String, Object>> allPhotoList =  new ArrayList<Map<String,Object>>();
						allPhotoList = signDetailService.getWorkSignInPic(listBean.get(i).getNo(), 0);
						if(allPhotoList == null){
							allPhotoList = new ArrayList<Map<String,Object>>();
						}
						for(int j = 0;j < allPhotoList.size();j++){
							String url = OssConfigure.cdnAccessUrl+"/workSignPic/"+allPhotoList.get(j).get("CustCode").toString()+"/";
							allPhotoList.get(j).put("src", url+allPhotoList.get(j).get("src").toString());
							if(j < 3) {
								Map<String, Object> photo = new HashMap<String, Object>();
								photo.putAll(allPhotoList.get(j));
								photoList.add(photo);
							}
						}
						if(allPhotoList.size()>0){
							listBean.get(i).setTotalNum(allPhotoList.get(0).get("totalNum").toString());
						}
						listBean.get(i).setPhotos(photoList);
						listBean.get(i).setAllPhotos(allPhotoList);
					}
				}
			}
			respon.setDatas(listBean);
			respon.setHasNext(page.getTotalPages()>page.getPageNo()?true:false);
			respon.setRecordSum(page.getTotalCount());
			respon.setTotalPage(page.getTotalPages());
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
