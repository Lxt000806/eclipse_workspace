package com.house.home.client.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.client.service.evt.CustWorkerAppEvt;
import com.house.home.client.service.resp.BasePageQueryResp;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.service.resp.CustWorkerAppResp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.WorkType12;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustWorkerAppService;	
import com.house.home.service.project.FixDutyService;

import java.util.List;
@RequestMapping("/client/workerApp")
@Controller
public class ClientCustWorkerAppController extends ClientBaseController {

	@Autowired
	private CustWorkerAppService custWorkerAppService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private FixDutyService fixDutyService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getWorkerAppList")
	public void getWorkerAppList(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		CustWorkerAppEvt evt=new CustWorkerAppEvt();
		BasePageQueryResp<CustWorkerAppResp> respon=new BasePageQueryResp<CustWorkerAppResp>();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkerAppEvt) JSONObject.toBean(json, CustWorkerAppEvt.class);
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
			custWorkerAppService.getWorkerAppList(page, evt);
			List<CustWorkerAppResp> listBean=BeanConvertUtil.mapToBeanList(page.getResult(), CustWorkerAppResp.class);
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

	@RequestMapping("/addWorkerApp")
	public void addWorkerApp(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		CustWorkerAppEvt evt=new CustWorkerAppEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("appComeDate")) 
			json.put("appComeDate", sdf.parse(json.get("appComeDate").toString()));
			evt=(CustWorkerAppEvt) JSONObject.toBean(json, CustWorkerAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
/*			if("0".equals(evt.getFromInfoAdd())&&custWorkerAppService.judgeProgTemp(evt) && !custWorkerAppService.existWorkApp(evt) && !"10".equals(evt.getWorkType12())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("该楼盘是第一次申请工人<br/>必须通过消息中心-施工提醒中的记录进行申请!" );
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return ;
			}*/
			
			Customer customer = customerService.get(Customer.class, evt.getCustCode());
			if(customer != null && !"1".equals(customer.getConstructStatus().trim())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("非正在施工工地不允许申请工人");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			
			//有控制主材备齐的工地，如果材料未备齐不允许申请 add by cjm 2019-11-04
			CustType custType = customerService.get(CustType.class, customer.getCustType());
			if(customer!=null&&custType!=null&&"0".equals(customer.getMainItemOk())&&"1".equals(custType.getCtrlMainItemOK())){
				respon.setReturnCode("400001");
				respon.setReturnInfo("主材未备齐不允许申请工人");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			//草签状态不允许申请水电工人   add by cjm 2019-11-01
//			if("02".equals(evt.getWorkType12())&&"1".equals(customer.getIsInitSign())){
//				respon.setReturnCode("400001");
//				respon.setReturnInfo("草签状态不允许申请水电工人");
//				returnJson(respon,response,msg,respon,request,interfaceLog);
//				return;
//			}
			
			/**
			 * 工地存在工人提交的的定责单时不允许申请工人
			 */
			Map<String,Object> fixDutyMap = fixDutyService.getFixDutyByCustCode(evt.getCustCode());
			if(fixDutyMap!=null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("工地存在工人提交的的定责单,不允许申请工人!");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			
			/**
			 * 某些客户类型申请工人时,需要判断上一阶段工人是否安排
			 */
			Map<String, Object> befWorkType12Map = custWorkerAppService.judgeBefWorkType12(evt.getCustCode(), evt.getWorkType12());
			if(befWorkType12Map != null){
				if("0".equals(befWorkType12Map.get("result").toString())){
					respon.setReturnCode("400001");
					respon.setReturnInfo(befWorkType12Map.get("Descr").toString()+"工种还未安排,无法申请");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
			}else{
				respon.setReturnCode("400001");
				respon.setReturnInfo("新增工人异常,请重试" );
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return ;
			}

			boolean checkPayInfoFlag = true;//由于防水判断款项是动态,增加判断标志 add by zzr 2018/05/08
			if("11".equals(evt.getWorkType12())){
				Map<String,Object> map = custWorkerAppService.specialReqForApply(evt);
				if( map != null){
					String tips = "";
					/**
					 * 防水班组申请取消找平阶段验收控制
					 * remove by zzr 2018/04/26
					 */
/*					String cmpCode = custWorkerAppService.get(Xtcs.class,"CmpnyCode").getQz().trim();
					if(!"02".equals(cmpCode) && "0".equals(map.get("isConfirm").toString())){
						tips += "找平防水阶段还未验收通过;<br/>";
					}*/
					
					/**
					 * 取消瓷砖没下单不能申请防水工人控制
					 * remove by zzr 2018/09/14
					 */
					
					if("1".equals(map.get("hasReq").toString()) && "0".equals(map.get("hasItemApp").toString())){
						tips += "存在瓷砖需求,但是瓷砖未下单;<br/>";
					}else if("0".equals(map.get("hasReq").toString())){
						checkPayInfoFlag = true;//当无瓷砖需求，判断款项是否交齐
					}else{
						checkPayInfoFlag = false; //有瓷砖需求，只要存在瓷砖发货或者审核通过的领料单，可以申请防水班组
					}
/*					if(tips.length() > 0){
						respon.setReturnCode("400001");
						respon.setReturnInfo(tips);
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return ;
					}*/
				}else{
					respon.setReturnCode("400001");
					respon.setReturnInfo("新增异常" );
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
			}
			/**
			 * 控制成品工人一天申请一次
			 * add by zzr 2018/05/07
			 */
			if("09".equals(evt.getWorkType12())){
				Map<String, Object> map = custWorkerAppService.specialReqForApply(evt);
				if(map != null){
					respon.setReturnCode("400001");
					respon.setReturnInfo("今天已申请成品工人,无法重复申请");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
			}

			WorkType12 cwa = custWorkerAppService.get(WorkType12.class, evt.getWorkType12());

			if("0".equals(evt.getCheckPayFlag())&& cwa.getPayNum() != null && checkPayInfoFlag){
				int moneyCtrl = 5000;
				Map<String,Object> payType = custWorkerAppService.getCustPayType(evt.getCustCode());
				if(Integer.parseInt(cwa.getPayNum().trim()) > 1&&"0.0".equals(payType.get("FourPay").toString())){
					cwa.setPayNum(Integer.parseInt(cwa.getPayNum().trim())-1+"");
				}
				Map<String,Object> isSign = custWorkerAppService.isSignEmp(getUserContext(request).getCzybh().trim());
				if("1".equals(isSign.get("IsSupvr").toString().trim())){
					moneyCtrl = 5000;
				}
				Map<String,Object> map = custWorkerAppService.checkCustPay(evt,cwa.getPayNum());
				/*Double zjzje = customerService.getCustomerZjzjeByCode(evt.getCustCode());
				Double pay = Double.parseDouble(String.valueOf(map.get("pay")));
				Double spay = Double.parseDouble(String.valueOf(map.get("spay")));*/
				Double shouldBanlance = Double.parseDouble(String.valueOf(map.get("shouldBanlance").toString()));
				// ("1".equals(cwa.getPayNum()) && spay - pay > moneyCtrl ) || ( ( spay + zjzje ) - pay > moneyCtrl ) 
				if(shouldBanlance > moneyCtrl){
					if(cwa.getMustPay() != null && "1".equals(cwa.getMustPay().trim())){
						respon.setReturnCode("400001");					
						//(("1".equals(cwa.getPayNum()) && spay - pay > moneyCtrl) ? (spay-pay):(( spay + zjzje ) - pay))
						respon.setReturnInfo("客户"+cwa.getPayNum()+"期款未交齐,差"+shouldBanlance+"元,不能保存" );
					}else{					
						respon.setReturnCode("400002");					
						//(("1".equals(cwa.getPayNum()) && spay - pay > moneyCtrl) ? (spay-pay):(( spay + zjzje ) - pay))
						respon.setReturnInfo("客户"+cwa.getPayNum()+"期款未交齐,差"+shouldBanlance+"元,是否继续保存" );
					}
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
			}
			
			Date today = DateUtil.startOfTheDay(new Date());
			Date minDay = DateUtil.addDate(today, cwa.getAppMinDay()==null?0:cwa.getAppMinDay());
			Date maxDay = DateUtil.addDate(today, cwa.getAppMaxDay()==null?0:cwa.getAppMaxDay());
			if( evt.getAppComeDate().getTime() < minDay.getTime() || evt.getAppComeDate().getTime() > maxDay.getTime()){
				respon.setReturnCode("400001");
				respon.setReturnInfo("申请进场时间必须在"+DateUtil.format(minDay,"yyyy-MM-dd")+"到"+DateUtil.format(maxDay,"yyyy-MM-dd")+"之间");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return ;
			}
			
			try{
				evt.setProjectMan(getUserContext(request).getCzybh());
				if(custWorkerAppService.addWorkerApp(evt)){
					respon.setReturnInfo("新增工人申请成功");
				}else{
					respon.setReturnCode("400001");
					respon.setReturnInfo(evt.getAddress()+" 存在草稿、申请或已安排未完工的单子" );
				}
			}catch (Exception e){
				respon.setReturnCode("400001");
				respon.setReturnInfo("新增工人申请失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				e.printStackTrace();
				return ;
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/getWorkerAppDetail")
	public void getWorkerAppDetail(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		CustWorkerAppEvt evt=new CustWorkerAppEvt();
		CustWorkerAppResp respon=new CustWorkerAppResp();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			evt=(CustWorkerAppEvt) JSONObject.toBean(json, CustWorkerAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}
			Map<String,Object> map = custWorkerAppService.getWorkerAppByPk(evt.getPk());
			if(map == null){
				respon.setReturnCode("400001");
				respon.setReturnInfo("获取信息出错,请重试");
			}else{
				BeanConvertUtil.mapToBean(map, respon);
			}
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}

	@RequestMapping("/updateWorkerApp")
	public void updateWorkerApp(HttpServletRequest request,HttpServletResponse response){
		StringBuilder msg=new StringBuilder();
		JSONObject json=new JSONObject();
		
		CustWorkerAppEvt evt=new CustWorkerAppEvt();
		BaseResponse respon=new BaseResponse();
		
		String clazz=Thread.currentThread().getStackTrace()[1].getClassName();
		String javaFun=Thread.currentThread().getStackTrace()[1].getMethodName();
		
		InterfaceLog interfaceLog=new InterfaceLog(clazz, javaFun);
		interfaceLog.setRequestIp(request.getRemoteAddr());
		interfaceLog.setRequestTime(new Date());
		
		try {
			json=StringUtil.queryStringToJSONObject(request);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(json.containsKey("appComeDate")) 
			json.put("appComeDate", sdf.parse(json.get("appComeDate").toString()));
			evt=(CustWorkerAppEvt) JSONObject.toBean(json, CustWorkerAppEvt.class);
			interfaceLog.setRequestContent(json.toString());
			Errors errors=new DirectFieldBindingResult(evt,"evt");
			springValidator.validate(evt, errors);
			if(errors.hasErrors()){
				respon.setReturnCode("400001");
				respon.setReturnInfo(ServletUtils.convertErrors2Msg(errors));
				returnJson(respon,response,msg,respon,request,interfaceLog);
				return;
			}

			if((evt.getOpSign() != null && evt.getOpSign().equals("1"))){
/*				if(custWorkerAppService.judgeProgTemp(evt) && !custWorkerAppService.existWorkApp(evt) && !"10".equals(evt.getWorkType12())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("该楼盘是第一次申请工人<br/>必须通过消息中心-施工提醒中的记录进行申请!" );
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}*/

				/**
				 * 某些客户类型申请工人时,需要判断上一阶段工人是否安排
				 */
				Map<String, Object> befWorkType12Map = custWorkerAppService.judgeBefWorkType12(evt.getCustCode(), evt.getWorkType12());
				if(befWorkType12Map != null){
					if("0".equals(befWorkType12Map.get("result").toString())){
						respon.setReturnCode("400001");
						respon.setReturnInfo(befWorkType12Map.get("Descr").toString()+"工种还未安排,无法申请");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return ;
					}
				}else{
					respon.setReturnCode("400001");
					respon.setReturnInfo("新增工人异常,请重试" );
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
				boolean checkPayInfoFlag = true;//由于防水判断款项是动态,增加判断标志 add by zzr 2018/05/08
				if("11".equals(evt.getWorkType12())){
					Map<String,Object> map = custWorkerAppService.specialReqForApply(evt);
					if( map != null){
						String tips = "";
						/**
						 * 防水班组申请取消找平阶段验收控制
						 * remove by zzr 2018/04/26
						 */
/*						String cmpCode = custWorkerAppService.get(Xtcs.class,"CmpnyCode").getQz().trim();
						if(!"02".equals(cmpCode) && "0".equals(map.get("isConfirm").toString())){
							tips += "找平防水阶段还未验收通过;<br/>";
						}*/
						
						/**
						 * 取消瓷砖没下单不能申请防水工人控制
						 * remove by zzr 2018/09/14
						 */
						
						if("1".equals(map.get("hasReq").toString()) && "0".equals(map.get("hasItemApp").toString())){
							tips += "存在瓷砖需求,但是瓷砖未下单;<br/>";
						}else if("0".equals(map.get("hasReq").toString())){
							checkPayInfoFlag = true;//当无瓷砖需求，判断款项是否交齐
						}else{
							checkPayInfoFlag = false; //有瓷砖需求，只要存在瓷砖发货或者审核通过的领料单，可以申请防水班组
						}
/*						if(tips.length() > 0){
							respon.setReturnCode("400001");
							respon.setReturnInfo(tips);
							returnJson(respon,response,msg,respon,request,interfaceLog);
							return ;
						}*/
					}else{
						respon.setReturnCode("400001");
						respon.setReturnInfo("新增异常" );
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return ;
					}
				}

				/**
				 * 控制成品工人一天申请一次
				 * add by zzr 2018/05/07
				 */
				if("09".equals(evt.getWorkType12())){
					Map<String, Object> map = custWorkerAppService.specialReqForApply(evt);
					if(map != null){
						respon.setReturnCode("400001");
						respon.setReturnInfo("今天已申请成品工人,无法重复申请");
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return ;
					}
				}
				
				WorkType12 cwa = custWorkerAppService.get(WorkType12.class, evt.getWorkType12());
				
				if("0".equals(evt.getCheckPayFlag())&& cwa.getPayNum() != null && checkPayInfoFlag){
					int moneyCtrl = 5000;
					Map<String,Object> payType = custWorkerAppService.getCustPayType(evt.getCustCode());
					if(Integer.parseInt(cwa.getPayNum().trim()) > 1&&"0.0".equals(payType.get("FourPay").toString())){
						cwa.setPayNum(Integer.parseInt(cwa.getPayNum().trim())-1+"");
					}
					Map<String,Object> isSign = custWorkerAppService.isSignEmp(getUserContext(request).getCzybh().trim());
					if("1".equals(isSign.get("IsSupvr").toString().trim())){
						moneyCtrl = 5000;
					}
					Map<String,Object> map = custWorkerAppService.checkCustPay(evt,cwa.getPayNum());

/*					Double zjzje = customerService.getCustomerZjzjeByCode(evt.getCustCode());
					Double pay = Double.parseDouble(String.valueOf(map.get("pay")));
					Double spay = Double.parseDouble(String.valueOf(map.get("spay")));*/
					Double shouldBanlance = Double.parseDouble(String.valueOf(map.get("shouldBanlance").toString()));
					// ("1".equals(cwa.getPayNum()) && ( spay ) - pay > moneyCtrl ) || ( ( spay + zjzje ) - pay > moneyCtrl ) 
					if(shouldBanlance > moneyCtrl){
						if(cwa.getMustPay() != null && "1".equals(cwa.getMustPay().trim())){
							respon.setReturnCode("400001");					
							//(("1".equals(cwa.getPayNum()) && ( spay ) - pay > moneyCtrl) ? (spay-pay):(( spay + zjzje ) - pay))
							respon.setReturnInfo("客户"+cwa.getPayNum()+"期款未交齐,差"+shouldBanlance+"元,不能保存" );
						}else{					
							respon.setReturnCode("400002");					
							//(("1".equals(cwa.getPayNum()) && ( spay ) - pay > moneyCtrl) ? (spay-pay):(( spay + zjzje ) - pay))
							respon.setReturnInfo("客户"+cwa.getPayNum()+"期款未交齐,差"+shouldBanlance+"元,是否继续保存" );
						}
						returnJson(respon,response,msg,respon,request,interfaceLog);
						return ;
					}
				}
				
				Date today = DateUtil.startOfTheDay(new Date());
				Date minDay = DateUtil.addDate(today, cwa.getAppMinDay()==null?0:cwa.getAppMinDay());
				Date maxDay = DateUtil.addDate(today, cwa.getAppMaxDay()==null?0:cwa.getAppMaxDay());
				if( evt.getAppComeDate().getTime() < minDay.getTime() || evt.getAppComeDate().getTime() > maxDay.getTime()){
					respon.setReturnCode("400001");
					respon.setReturnInfo("申请进场时间必须在"+DateUtil.format(minDay,"yyyy-MM-dd")+"到"+DateUtil.format(maxDay,"yyyy-MM-dd")+"之间");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return ;
				}
				
				CustWorkerApp custWorkerApp = custWorkerAppService.get(CustWorkerApp.class, evt.getPk());			
				Customer customer = customerService.get(Customer.class, evt.getCustCode());
				if(customer != null && !customer.getCode().trim().equals(custWorkerApp.getCustCode().trim()) && !"1".equals(customer.getConstructStatus().trim())){
					respon.setReturnCode("400001");
					respon.setReturnInfo("非正在施工工地不允许申请工人");
					returnJson(respon,response,msg,respon,request,interfaceLog);
					return;
				}
			}
			
			try{
				evt.setProjectMan(getUserContext(request).getCzybh());
				if(custWorkerAppService.updateWorkerApp(evt)){
					respon.setReturnInfo("修改工人申请成功");
				}else{
					respon.setReturnCode("400001");
					respon.setReturnInfo(evt.getAddress()+" 存在草稿、申请或已安排未完工的单子" );
				}
			}catch (Exception e){
				respon.setReturnCode("400001");
				respon.setReturnInfo("修改工人申请失败");
				returnJson(respon,response,msg,respon,request,interfaceLog);
				e.printStackTrace();
				return ;
			}
			
			returnJson(respon,response,msg,respon,request,interfaceLog);
		} catch (Exception e) {
			e.printStackTrace();
			super.exceptionHandle(respon,response,msg,request,interfaceLog);
		}
	}
}
