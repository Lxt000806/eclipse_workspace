package com.house.home.client.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.bean.Result;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.SignInUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.bean.basic.PosiBean;
import com.house.home.client.service.evt.BaseEvt;
import com.house.home.client.service.evt.BaseGetEvt;
import com.house.home.client.service.evt.GetPosiInfoEvt;
import com.house.home.client.service.evt.GetSignInType2ListEvt;
import com.house.home.client.service.evt.PrjItemEvt;
import com.house.home.client.service.evt.SignInEvt;
import com.house.home.client.service.evt.SignInQueryEvt;
import com.house.home.client.service.evt.SignPlaceEvt;
import com.house.home.client.service.evt.UpdatePosiInfoEvt;
import com.house.home.client.service.evt.WorkType12Evt;
import com.house.home.client.service.evt.getPosiDistanceEvt;
import com.house.home.client.service.resp.BaseNumberResp;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CompanyResp;
import com.house.home.client.service.resp.DesignPicPrgResp;
import com.house.home.client.service.resp.GetSignInType1ListResp;
import com.house.home.client.service.resp.GetSignInType2ListResp;
import com.house.home.client.service.resp.PosiDistanceResp;
import com.house.home.client.service.resp.PosiInfoResp;
import com.house.home.client.service.resp.PrjItemResp;
import com.house.home.client.service.resp.SignInQueryResp;
import com.house.home.client.service.resp.SignPlaceResp;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.client.service.resp.WorkType12Resp;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.Company;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.basic.SignPlace;
import com.house.home.entity.basic.Technology;
import com.house.home.entity.design.CustDoc;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.query.SignInPic;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.CompanyService;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.basic.SignInService;
import com.house.home.service.design.CustDocService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.WorkType12Service;

/**
 * 地图签到相关的接口
 * @author 
 *
 */
@RequestMapping("/client/signIn")
@Controller
public class ClientSignInController extends ClientBaseController{
	@Autowired
	private SignInService signInService;
	@Autowired
	private BuilderService builderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private WorkType12Service workType12Service;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private PersonMessageService personMessageService;
	@Autowired
	private CustDocService custDocService;
	/**
	 * 查看地图签到列表接口
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getSignInList")
	public void getSignInList(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignInQueryEvt evt=new SignInQueryEvt();
		BasePageQueryResp<SignInQueryResp> respon=new BasePageQueryResp<SignInQueryResp>();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("crtDate")) 
			json.put("crtDate", sdf.parse(json.get("crtDate").toString()));
			evt = (SignInQueryEvt)JSONObject.toBean(json,SignInQueryEvt.class);
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
			page.setPageSize(evt.getPageSize());
			SignIn signIn = new SignIn();
			signIn.setCustCode(evt.getCustCode());
			signIn.setSignCzy(evt.getSignCzy());
			signIn.setCrtDate(evt.getCrtDate());
			signInService.findPageBySql(page, signIn);
			List<SignInQueryResp> listBean = BeanConvertUtil.mapToBeanList(page.getResult(), SignInQueryResp.class);
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
	/**
	 * 保存地图签到接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saveSignIn")
	public void saveSignIn(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignInEvt evt=new SignInEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			json.put("address", request.getParameter("address"));
			evt = (SignInEvt)JSONObject.toBean(json,SignInEvt.class);
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
			if(StringUtils.isBlank(evt.getErrPosi())){
				//第一次定位判断是否在范围内
				if (null != evt.getSignPlacePK() && "0".equals(evt.getCustCode())) { //当公司打卡地点不为空时，根据公司地点判断离距离 add by zb on 20190522
					evt.setCustCode(""); //为"0"的话设为空，此处设为‘0’主要是跳过实体类的检查
					SignPlace signPlace = signInService.get(SignPlace.class, evt.getSignPlacePK());
					Double limitDistance = signPlace.getLimitDistance()==null?200:signPlace.getLimitDistance();
					if(signPlace.getLongitudetppc()!=null&&signPlace.getLongitudetppc().longValue()!=0){
						if(PosiBean.getDistance(signPlace.getLatitudetppc(), signPlace.getLongitudetppc(), 
								evt.getLatitude(), evt.getLongitude()) > limitDistance){
							if("2".equals(evt.getAppType())){
								respon.setReturnCode("400001");
								respon.setReturnInfo("对不起,您当前地点离打卡地点位置过远,不允许签到");
								returnJson(respon,response,msg,respon,request,interfaceLog);
								return;
							}
							respon.setReturnCode("100006");
							respon.setReturnInfo("当前地点离打卡地点位置过远,是否继续签到?");
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return;
						}
					}
				} else {
					Customer customer=customerService.get(Customer.class,evt.getCustCode());
					if(customer.getBuilderCode()!=null){
						Builder builder=builderService.get(Builder.class,customer.getBuilderCode());
						Integer offset = (builder == null || builder.getOffset() == null || builder.getOffset() == 0) ? PosiBean.LIMIT_DISTANCE : builder.getOffset();
						if(builder != null && builder.getLongitude()!=null&&builder.getLongitude().longValue()!=0){
							if(PosiBean.getDistance(builder.getLatitude(), builder.getLongitude(), evt.getLatitude(), evt.getLongitude())>offset){
								if("2".equals(evt.getAppType())){
									respon.setReturnCode("400001");
									respon.setReturnInfo("对不起,您当前地点离楼盘位置过远,不允许签到");
									returnJson(respon,response,msg,respon,request,interfaceLog);
									return;
								}
								respon.setReturnCode("100006");
								respon.setReturnInfo("当前地点离楼盘位置过远,是否继续签到?");
								returnJson(respon,response,msg,respon,request,interfaceLog);
								return;
							}
						}
					}
				}
			}

			//项目经理app,签到时控制施工节点存在选择列表中
			if(!"07".equals(evt.getSignInType2()) && "2".equals(evt.getAppType()) && StringUtils.isNotBlank(evt.getPrjItem())) {
				List<Map<String, Object>> prjItemList = signInService.getPrjItemList(evt.getCustCode(), evt.getPrjItem());
				if(prjItemList == null || prjItemList.size() <= 0) {
					respon.setReturnCode("400001");
					respon.setReturnInfo("请选择施工节点");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
		
			if("2".equals(evt.getAppType())&&"05".equals(evt.getSignInType2())){
				if(signInService.existsFirstPass(evt.getCustCode(),evt.getPrjItem())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该节点初检已通过无需再进行初检！");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
				if(StringUtil.isEmpty(evt.getIsPass())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("请选择初检是否通过！");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}else{ // 初检通过根据是否标记完工更新结束时间
					PrjItem1 prjItem1 = signInService.get(PrjItem1.class, evt.getPrjItem());
					if("1".equals(prjItem1.getIsFirstComplete()) && StringUtils.isNotBlank(prjItem1.getWorktype12())){
						signInService.updateEndDate(evt.getCustCode(),prjItem1.getCode(),evt.getSignCzy());
					}
				}
			}
			SignIn signIn = new SignIn();
			signIn.setCustCode("0".equals(evt.getCustCode())?"":evt.getCustCode());//为"0"的话设为空，此处设为‘0’主要是跳过实体类的检查
			signIn.setSignCzy(evt.getSignCzy());
			signIn.setCrtDate(new Date());
			signIn.setLongitude(evt.getLongitude());
			signIn.setLatitude(evt.getLatitude());
			signIn.setAddress(evt.getAddress());
			signIn.setErrPosi("0");
			signIn.setSignInType2(evt.getSignInType2());
			signIn.setPrjItem(evt.getPrjItem());
			signIn.setIsPass(evt.getIsPass());
			signIn.setRemarks(evt.getRemarks());
			signIn.setSignPlacePK(evt.getSignPlacePK());
			signIn.setDockRemark(evt.getDockRemark());
			signIn.setWorkType12(evt.getWorkType12());
			if("1".equals(evt.getErrPosi())) signIn.setErrPosi("1");
			boolean setPrjItemFlag = false;
			if(StringUtils.isNotBlank(signIn.getSignInType2())){
				String no = signInService.getSeqNo("tSignIn");
				signIn.setNo(no);
				String str = evt.getPhotoString();
				if (StringUtils.isNotBlank(str)){
					if ("07".equals(signIn.getSignInType2())) {
						String[] arr = str.split(";");
						for (String photo : arr) {
							JSONObject photoObject = JSONObject.fromObject("{"+photo+"}");
							SignInPic signInPic = new SignInPic();
							signInPic.setPhotoName((String) photoObject.get("src"));
							signInPic.setNo(no);
							signInPic.setLastUpdate(new Date());
							signInPic.setLastUpdatedBy(getUserContext(request).getCzybh());
							signInPic.setActionLog("ADD");
							signInPic.setExpired("F");
							signInPic.setTechCode((String) photoObject.get("techCode"));
							signInPic.setIsSendYun("1");
							signInPic.setSendDate(new Date());
							// 设置施工节点为工种所对应节点 add by zzr 2019/05/24
							if(!setPrjItemFlag){
								Technology technology = this.signInService.get(Technology.class, signInPic.getTechCode());
								if(technology != null){
									WorkType12 workType12 = this.signInService.get(WorkType12.class, technology.getWorkType12());
									if(workType12 != null){
										setPrjItemFlag = true;
										signIn.setPrjItem(workType12.getPrjItem());
									}
								}
							}
							this.signInService.save(signInPic);
						}
					} else {
						String[] arr = str.split(",");
						for (String photoName: arr){
							SignInPic signInPic = new SignInPic();
							signInPic.setPhotoName(photoName);
							signInPic.setNo(no);
							signInPic.setLastUpdate(new Date());
							signInPic.setLastUpdatedBy(getUserContext(request).getCzybh());
							signInPic.setActionLog("ADD");
							signInPic.setExpired("F");
							signInPic.setIsSendYun("1");
							signInPic.setSendDate(new Date());
							signInService.save(signInPic);
							signInPic = null;
						}
					}
				}
			}
			signInService.save(signIn);
			
			// 图纸无变更
			if("0".equals(evt.getDrawChange())) {
				CustDoc custDoc = new CustDoc();
				custDoc.setCustCode(evt.getCustCode());
				custDocService.doDrawNoChg(custDoc,evt.getSignCzy());
			}
			PersonMessage pm = new PersonMessage();
			pm.setMsgType("1");
			pm.setMsgRelCustCode(evt.getCustCode());
			pm.setPrjItem(evt.getPrjItem());
			pm.setProgmsgType("9");
			PersonMessage personMessage = personMessageService.getPersonMessageByCondition(pm);
			if(personMessage != null){
				personMessage.setRcvStatus("1");
				personMessage.setRcvDate(new Date());
				personMessage.setDealNo(signIn.getPk()+"");
				personMessageService.update(personMessage);
			}
	
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 获取当天地图签到次数
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSignInCount")
	public void getSignInCount(HttpServletRequest request, HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json = new JSONObject();
		SignInQueryEvt evt=new SignInQueryEvt();
		BaseNumberResp respon=new BaseNumberResp();
		
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json = StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("crtDate")) 
			json.put("crtDate", sdf.parse(json.get("crtDate").toString()));
			evt = (SignInQueryEvt)JSONObject.toBean(json,SignInQueryEvt.class);
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
			SignIn signIn = new SignIn();
			signIn.setCustCode(evt.getCustCode());
			signIn.setSignCzy(evt.getSignCzy());
			respon.setNumber(signInService.getSignCountNow(signIn));
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 获取项目名称的位置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPosiInfo")
	public  void getPosiInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		GetPosiInfoEvt evt=new GetPosiInfoEvt();
		BasePageQueryResp<PosiInfoResp> respon=new BasePageQueryResp<PosiInfoResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetPosiInfoEvt) JSONObject.toBean(json, GetPosiInfoEvt.class);
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
			StringBuffer buffer=null;
	        HttpURLConnection httpConn;
		    String strURL=new PosiBean(evt.getCity(),evt.getCity_limit(),evt.getAddress()).getUrl();
		    URL url=new URL(strURL);
		    httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.connect();  
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
			String line;
			buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
			        buffer.append(line);
			    }
			reader.close();
			httpConn.disconnect();
			JSONObject jsonobject = JSONObject.fromObject(buffer.toString());
			JSONArray jsonArray = JSONArray.fromObject(jsonobject.get("results"));  
			List<Map<String,Object>> mapListJson = (List)jsonArray;
			if(!"0".equals(jsonobject.get("status").toString())){
			respon.setReturnCode("100001");
			respon.setReturnInfo(jsonobject.get("message").toString());
			returnJson(respon,response,msg,respon,request,interfaceLog);
			return;
				}
				
			List<PosiInfoResp> listBean = BeanConvertUtil.mapToBeanList(mapListJson, PosiInfoResp.class);
			respon.setDatas(listBean);
			respon.setHasNext(false);
			respon.setRecordSum((new Long(mapListJson.size())));
			respon.setTotalPage((new Long(1)));
			returnJson(respon,response,msg,respon,request,interfaceLog);
				
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 更新导入数据位置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePosiInfo")
	public  void updatePosiInfo(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		UpdatePosiInfoEvt evt=new UpdatePosiInfoEvt();
		BaseResponse respon=new BaseResponse();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		List<Builder> builders=builderService.findByNoExpired("where longitude='0' ");
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(UpdatePosiInfoEvt) JSONObject.toBean(json, UpdatePosiInfoEvt.class);
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
		} catch (Exception e) {
			// TODO: handle exception
		}
		for(Builder builder:builders){
			StringBuffer buffer=null;
	        HttpURLConnection httpConn;
			try {
				  String strURL=new PosiBean(evt.getCity(),evt.getCity_limit(),URLEncoder.encode(builder.getDescr(),"utf-8")).getUrl();
				  URL url=new URL(strURL);
				  httpConn = (HttpURLConnection)url.openConnection();
				  httpConn.setRequestMethod("POST");
			      httpConn.connect();  
			      BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
			      String line;
			      buffer = new StringBuffer();
			      while ((line = reader.readLine()) != null) {
			        buffer.append(line);
			      }
			      reader.close();
			      httpConn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				super.exceptionHandle(respon,response,msg,request,interfaceLog);
			}
			JSONObject jsonobject = JSONObject.fromObject(buffer.toString());
			JSONArray jsonArray = JSONArray.fromObject(jsonobject.get("results"));  
			List<Map<String,Object>> mapListJson = (List)jsonArray;
			if("0".equals(jsonobject.get("status").toString())){
				if(mapListJson.size()>0){
					if(mapListJson.get(0).get("location")!=null){
						builder.setLatitude(Double.parseDouble(((JSONObject)mapListJson.get(0).get("location")).get("lat").toString()));
						builder.setLongitude(Double.parseDouble(((JSONObject)mapListJson.get(0).get("location")).get("lng").toString()));
						builderService.update(builder);
					}
					
				}
			}else{
				try {
					respon.setReturnCode("100001");
					respon.setReturnInfo(jsonobject.get("message").toString());
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				} catch (Exception e) {
					e.printStackTrace();
					super.exceptionHandle(respon,response,msg,request,interfaceLog);
				}
				
			}
			
			
		}
		try {
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取位置距离
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPosiDistance")
	public  void getPosiDistance(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		getPosiDistanceEvt evt=new getPosiDistanceEvt();
		PosiDistanceResp respon=new PosiDistanceResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(getPosiDistanceEvt) JSONObject.toBean(json, getPosiDistanceEvt.class);
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
			respon.setDistance(PosiBean.getDistance(evt.getStartLat(),evt.getStartLng(),evt.getEndLat(),evt.getEndLng()));
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getSignInType1List")
	public  void getSignInType1List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		BaseEvt evt=new BaseEvt();
		BasePageQueryResp<GetSignInType1ListResp> respon=new BasePageQueryResp<GetSignInType1ListResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseEvt) JSONObject.toBean(json, BaseEvt.class);
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
			List<Map<String, Object>> list = signInService.getSignInType1List();
			respon.setDatas(BeanConvertUtil.mapToBeanList(list, GetSignInType1ListResp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getSignInType2List")
	public  void getSignInType2List(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		GetSignInType2ListEvt evt=new GetSignInType2ListEvt();
		BasePageQueryResp<GetSignInType2ListResp> respon=new BasePageQueryResp<GetSignInType2ListResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(GetSignInType2ListEvt) JSONObject.toBean(json, GetSignInType2ListEvt.class);
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
			List<Map<String, Object>> list = signInService.getSignInType2List(evt.getSignInType1());
			respon.setDatas(BeanConvertUtil.mapToBeanList(list, GetSignInType2ListResp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 获取是否上传图片模板为是的工种
	 * @author	created by zb
	 * @date	2019-5-1--下午3:07:56
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getTechWorkType12")
	public  void getTechWorkType12(HttpServletRequest request,HttpServletResponse response){
		WorkType12Evt evt = new WorkType12Evt();
		BasePageQueryResp<WorkType12Resp> respon=new BasePageQueryResp<WorkType12Resp>();
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkType12Evt) JSONObject.toBean(json, WorkType12Evt.class);
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
			List<Map<String, Object>> list = workType12Service.getTechWorkType12();
			respon.setDatas(BeanConvertUtil.mapToBeanList(list, WorkType12Resp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 工艺列表
	 * @author	created by zb
	 * @date	2019-5-2
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getTechPhotoList")
	public void getTechPhotoList(HttpServletRequest request,HttpServletResponse response){
		WorkType12Evt evt = new WorkType12Evt();
		BasePageQueryResp<WorkType12Resp> respon=new BasePageQueryResp<WorkType12Resp>();
		
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkType12Evt) JSONObject.toBean(json, WorkType12Evt.class);
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
			WorkType12 workType12 = new WorkType12();
			workType12.setCode(evt.getCode());
			workType12.setSourceType(evt.getSourceType());
			workType12.setCustCode(evt.getCustCode());
			Page page = new Page();
			workType12Service.getTechBySql(page, workType12);
			respon.setDatas(BeanConvertUtil.mapToBeanList(page.getResult(), WorkType12Resp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 根据所在地点按顺序获取公司列表（公司签到）
	 * @author	created by zb
	 * @date	2019-5-21--下午2:57:37
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCompanyList")
	public void getCompanyList(HttpServletRequest request,HttpServletResponse response){
		SignPlaceEvt evt = new SignPlaceEvt();
		BasePageQueryResp<CompanyResp> respon=new BasePageQueryResp<CompanyResp>();
		
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(SignPlaceEvt) JSONObject.toBean(json, SignPlaceEvt.class);
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
			Company company = new Company();
			company.setLongitude(evt.getLongitudetppc());
			company.setLatitude(evt.getLatitudetppc());
			Page page = new Page();
			companyService.findCmpListOrderDistanceBySql(page, company);
			respon.setDatas(BeanConvertUtil.mapToBeanList(page.getResult(), CompanyResp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	/**
	 * 获取打卡地点（公司签到）
	 * @author	created by zb
	 * @date	2019-5-20--下午3:24:33
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getSignPlaceList")
	public void getSignPlaceList(HttpServletRequest request,HttpServletResponse response){
		SignPlaceEvt evt = new SignPlaceEvt();
		BasePageQueryResp<SignPlaceResp> respon=new BasePageQueryResp<SignPlaceResp>();
		
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(SignPlaceEvt) JSONObject.toBean(json, SignPlaceEvt.class);
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
			Company company = new Company();
			company.setCode(evt.getCmpCode());
			company.setLatitude(evt.getLatitudetppc());
			company.setLongitude(evt.getLongitudetppc());
			Page page = new Page();
			companyService.findSignPlaceOrderDistancePageBySql(page, company);
			respon.setDatas(BeanConvertUtil.mapToBeanList(page.getResult(), SignPlaceResp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getPrjItemList")
	public  void getPrjItemList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		PrjItemEvt evt=new PrjItemEvt();
		BasePageQueryResp<PrjItemResp> respon=new BasePageQueryResp<PrjItemResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjItemEvt) JSONObject.toBean(json, PrjItemEvt.class);
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
			List<Map<String, Object>> list = signInService.getPrjItemList(evt.getCustCode(), null);
			
			for(int i = 0;i < list.size();i++){
				System.out.println(list.get(i));
			}
			respon.setDatas(BeanConvertUtil.mapToBeanList(list, PrjItemResp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/uploadSignInPhoto")
	public void uploadSignInPhoto(HttpServletRequest request, HttpServletResponse response) {

		StringBuilder msg = new StringBuilder();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String fileNameNew = "";
			String firstFileName = "";
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> photoPathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
            	firstFileName = attatchment.getName().substring(
            			attatchment.getName().lastIndexOf("\\") + 1);
				String formatName = firstFileName
						.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
				fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
				SignInUploadRule rule =
                        new SignInUploadRule(fileNameNew);
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                		rule.getFileName(),rule.getFilePath());
                fileNameList.add(fileNameNew);
                photoPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
            }
			respon.setPhotoPathList(photoPathList);
			respon.setPhotoNameList(fileNameList);
			returnJson(respon,response,msg,respon,request,null);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon, response, msg, request,null);
		}
	}
	@RequestMapping("/delSignInPic")
	public void delSignInPic(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new  JSONObject();
		BaseGetEvt evt=new BaseGetEvt();
		BaseResponse respon=new BaseResponse();
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(BaseGetEvt) JSONObject.toBean(json, BaseGetEvt.class);
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
			String[] arr = evt.getId().split(",");
			int i = 0;
			for(String str:arr){
				SignInPic photo=signInService.getPicByName(str);
				SignInUploadRule rule = new SignInUploadRule(str);
				if(photo!=null){
					signInService.delete(photo);
					FileUploadUtils.deleteFile(rule.getOriginalPath());
					i++;
				}
			}
			if (i==0){
				respon.setReturnCode("300102");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getWorkType12List")
	public  void getWorkType12List(HttpServletRequest request,HttpServletResponse response){
		WorkType12Evt evt = new WorkType12Evt();
		BasePageQueryResp<WorkType12Resp> respon=new BasePageQueryResp<WorkType12Resp>();
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(WorkType12Evt) JSONObject.toBean(json, WorkType12Evt.class);
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
			List<Map<String, Object>> list = workType12Service.getWorkType12List();
			respon.setDatas(BeanConvertUtil.mapToBeanList(list, WorkType12Resp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	
	@RequestMapping("/getPrjProgList")
	public  void getPrjProgList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		PrjItemEvt evt=new PrjItemEvt();
		BasePageQueryResp<PrjItemResp> respon=new BasePageQueryResp<PrjItemResp>();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjItemEvt) JSONObject.toBean(json, PrjItemEvt.class);
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
			List<Map<String, Object>> list = signInService.getPrjProgList(evt.getCustCode());
			respon.setDatas(BeanConvertUtil.mapToBeanList(list, PrjItemResp.class));
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
	@RequestMapping("/existsDesignPicPrgChange")
	public void existsDesignPicPrgChange(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg = new StringBuilder();
		JSONObject json=new JSONObject();
		PrjItemEvt evt=new PrjItemEvt();
		DesignPicPrgResp respon=new DesignPicPrgResp();
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun = Thread.currentThread().getStackTrace()[1].getMethodName();
		InterfaceLog interfaceLog = new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(PrjItemEvt) JSONObject.toBean(json, PrjItemEvt.class);
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
			Boolean existsDesignPicPrgChange = signInService.existsDesignPicPrgChange(evt.getCustCode());
			respon.setExistsDesignPicPrgChange(existsDesignPicPrgChange);
			returnJson(respon,response,msg,respon,request,interfaceLog);	
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
