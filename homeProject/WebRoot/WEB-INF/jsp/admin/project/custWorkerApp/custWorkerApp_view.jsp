<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>工程部工人申请查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	if($.trim("${custWorkerApp.workType12 }")=="02"){
		$("#isWaterItemCtrl_li").show();
	}else{
		$("#isWaterItemCtrl_li").hide();
	}
	
	$("#viewItem").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("viewBef",{
			title:"查材料到货情况",
			url:"${ctx}/admin/CustWorkerApp/goViewItem",
			postData:{custCode:"${custWorkerApp.custCode }",workType12:"${custWorkerApp.workType12 }"},
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#viewBef").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("viewBef",{
			title:"查看上工种验收情况",
			url:"${ctx}/admin/CustWorkerApp/goViewBef",
			postData:{custCode:"${custWorkerApp.custCode }",workType12:"${custWorkerApp.workType12 }"},
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#needZF").attr("disabled",true);
	if($.trim("${custWorkerApp.workType12}")=="01" && $.trim("${custWorkerApp.status }")=="2"){
		$.ajax({
			url:'${ctx}/admin/CustWorkerApp/isNeedZF',
			type: 'post',
			data: {custCode:'${custWorkerApp.custCode }'},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs==true){
		    		$("#needZF").val("1");
		    	}else{
		    		$("#needZF").val("0");
		    	}
		   	} 
		 });
	}else{
		$("#zf_div").hide();
	}
	selectDelayRemark();
	$("#viewZF").on("click",function(){
		var custCode = $.trim($("#custCode").val());
		var prjnormday = $.trim($("#prjnormday").val());
			Global.Dialog.showDialog("viewZF",{
				title:"查看砌墙瓷砖",
				url:"${ctx}/admin/CustWorkerApp/goViewZF",
				postData:{custCode:custCode},
				height:700,
				width:1050,
				returnFun:goto_query
			});
	});
	
	$("#viewBefTask").on("click", function(){
		var custCode = $.trim($("#custCode").val());
		var workType12 = $.trim($("#workType12").val());
		Global.Dialog.showDialog("viewBefTask",{
			title:"查看前置任务",
			url:"${ctx}/admin/CustWorkerApp/goViewBefTask",
			postData:{custCode:custCode,workType12:workType12},
			height:700,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var custCode = $.trim($("#custCode").val());
		var prjnormday = $.trim($("#prjnormday").val());
		var workType12 = $.trim($("#workType12").val());
			Global.Dialog.showDialog("View",{
				title:"工作量——查看",
				url:"${ctx}/admin/CustWorkerApp/goViewWorker",
				postData:{custCode:custCode,prjnormday:prjnormday,workType12:workType12},
				height:700,
				width:1050,
				returnFun:goto_query
			});
	});
	
	$("#workerCode").openComponent_worker({showValue:'${custWorkerApp.workerCode}',showLabel:'${custWorkerApp.workerDescr}',condition:{workType12:"${custWorkerApp.workType12 }"}});
	$("#custCode").openComponent_customer({showValue:'${custWorkerApp.custCode}',showLabel:'${custWorkerApp.custDescr}',readonly:'true'});
});
function selectDelayRemark(){
	var comeDate=$("#comeDate").val();
	var appComeDate=$("#appComeDate").val();
	if(comeDate>appComeDate){
		$("#comeDelayType").parent().parent().removeClass("hidden");
		$("#comeDelayType").focus();
		isDelay=true;
	}else{
		$("#comeDelayType").parent().parent().addClass("hidden");
		$("#comeDelayType").val("");
		isDelay=false;
	}
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="content-form">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
			<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
							<li hidden="true">
								<label>pk</label>
								<input type="text" id="pk" name="pk" style="width:160px;" value="${custWorkerApp.pk }" />
							</li>
							<div class="validate-group row">							
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custWorkerApp.custCode }" />
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custWorkerApp.address }" readonly="true"/>
							</li>
							</div>
							<div class="validate-group row">
							<li>
								<label>工种</label>
								<house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${custWorkerApp.workType12 }" disabled="true"></house:DataSelect>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm  id="status" dictCode="WORKERAPPSTS"   style="width:160px;" value="${custWorkerApp.status }" disabled="true"></house:xtdm>
							</li>
							</div>
							<div class="validate-group row">
							<li>
								<label>申请进场时间</label>
								<input type="text" id="appComeDate" name="appComeDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.appComeDate}' pattern='yyyy-MM-dd'/>" disabled="true" />
							</li>
							<li>
								<label>申请时间</label>
								<input type="text" id="appDate" name="appDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.appDate}' pattern='yyyy-MM-dd'/>" disabled="true" />
							</li>
							</div>
							<div class="validate-group row">
							<li>
								<label><span class="required">*</span>安排进场时间</label>
								<input type="text" id="comeDate" name="comeDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.comeDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label><span class="required">*</span>工人编号</label>
								<input type="text" id="workerCode" name="workerCode" style="width:160px;" value="${custWorkerApp.workerCode }" />
							</li>
							</div>
							<div class="row hidden" >	
								<li>
									<label><span class="required">*</span>延误原因</label>
									<house:xtdm  id="comeDelayType" dictCode="CUSTWKDELAYTYPE" style="width:160px;" value="${custWorker.comeDelayType }"></house:xtdm>
								</li>						
							</div>
							<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>是否系统安排</label>
								<house:xtdm  id="isSysArrange" dictCode="YESNO"   style="width:160px;" value="${custWorker.isSysArrange }" disabled="true"></house:xtdm>
							</li>
							<li  class="form-validate">
								<label>工期</label>
								<input type="text" id="constructDay" name="constructDay" style="width:110px;" value="${custWorker.constructDay }" readonly="true"/>
								<button type="button" class="btn btn-system " style="width:35px;height:24px;font-size: 12px;margin-top:-3px" 
										id="view" >
									<span>查看</span>
								</button>
							</li>
							</div>
							<div class="validate-group row">
							<li class="form-validate">
								<label>计划完工时间</label>
								<input type="text" style="width:160px;" id="planEnd" name="planEnd" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorker.planEnd}' pattern='yyyy-MM-dd'/>"   disabled="disabled"/>
							
							</li>
							<li  class="form-validate">
								<label><span class="required">*</span>完工时间</label>
								<input type="text" id="endDate" name="endDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorker.endDate}' pattern='yyyy-MM-dd '/>" />
							</li>
							</div>
							<div class="validate-group row">
							<li>
								<label>施工状态</label>
								<house:xtdm  id="constructStatus" dictCode="CUSTWKSTATUS"   style="width:160px;" value="${custWorkerApp.constructStatus }" ></house:xtdm>
							</li>
							<li  class="form-validate">
								<label>安排次数</label>
								<input type="text" id="arrTimes" name="arrTimes" style="width:160px;" value="${arrTimes }" readonly="true"/>
							</li>
							</div>
							<div class="validate-group row" id="zf_div" >
								<li  class="form-validate">
									<label>需找方</label>
									<house:xtdm  id="needZF" dictCode="YESNO" style="width:110px"></house:xtdm>
									<button type="button" class="btn btn-system " style="width:35px;height:24px;font-size: 12px;margin-top:-3px" 
											id="viewZF" >
										<span>查看</span>
									</button>
									<%--<button type="button" class="btn btn-system" data-disabled="false" 
								    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;
								    			width:26px;height:24px;padding-top:2.5px" id="viewZF">
								    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
								    </button>
								--%></li>
							</div>
							<div class="validate-group row">
								<li  class="form-validate" id="isWaterItemCtrl_li">
									<label style="width:110px;margin-left:0px"><span class="required" >*</span>水电材料发包工人</label>
									<house:xtdm  id="isWaterItemCtrl" dictCode="YESNO"   style="width:160px;" value="${isWaterItemCtrl }"></house:xtdm>
								</li>
								<li  class="form-validate">
									<label>材料到货</label>
									<input type="text" id="itemArrive" name="itemArrive" 
										style="width:110px" value="${itemArrive }" readonly="true" />
									<button type="button" class="btn btn-system " style="width:35px;height:24px;font-size: 12px;margin-top:-3px" 
											id="viewItem" >
										<span>查看</span>
									</button>
									<%--<button type="button" class="btn btn-system" data-disabled="false" 
								    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;
								    			width:26px;height:24px;padding-top:2.5px" id="viewItem">
								    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
								    </button>
								--%></li>
								<li  class="form-validate">
									<label>款项已齐</label>
									<input type="text" id="hasPaid" name="hasPaid" style="width:160px" value="${moneyCtrl }" readonly="true" />
								</li>
							</div>
							<div class="validate-group row">
								<li  class="form-validate">
									<label>上工种初检验收</label>
									<input type="text" id="befWorkType12ConfString" name="befWorkType12ConfString" 
											style="width:110px" value="${befWorkType12ConfString }" readonly="true" />
									<button type="button" class="btn btn-system " style="width:35px;height:24px;font-size: 12px;margin-top:-3px" 
											id="viewBef" >
										<span>查看</span>
									</button>
									<%--<button type="button" class="btn btn-system" data-disabled="false" id="viewBef"
								    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;
								    			height:24px;padding-top:2.5px">
								    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
								    </button>	
								--%></li>
								<li  class="form-validate">
									<label>前置任务</label>
									<input type="text" id="befWorkType12ConfString" name="befWorkType12ConfString" 
											style="width:110px" value="${befTaskCompleted }" readonly="true" />
									<button type="button" class="btn btn-system " style="width:35px;height:24px;font-size: 12px;margin-top:-3px" 
											id="viewBefTask" >
										<span>查看</span>
									</button>
									<%--<button type="button" class="btn btn-system" data-disabled="false" id="viewBefTask"
								    		style ="border-top-left-radius:0;border-bottom-left-radius:0;margin-left:-7px;margin-top: -5px;width:26px;
								    			height:24px;padding-top:2.5px">
								    	<span class="glyphicon glyphicon-search" style="margin-left: 0px;margin-top: -10px;padding-top:0;line-height: 20px"></span>
								    </button>
								--%></li>
							</div>
							<li>
								<label>是否周末施工</label>
								<house:xtdm id="isHoliConstruct" dictCode="YESNO" style="width:160px;" value="${isHoliConstruct}" disabled="true"/>
							</li>
							<li>
								<label class="control-textarea"> 备注</label>
								<textarea id="remarks" name="remarks" rows="2" readonly="true">${custWorkerApp.remarks }</textarea>
							</li>
						</ul>	
				</form>
				</div>
				</div>
			</div>
		</div>
	</body>	
</html>
