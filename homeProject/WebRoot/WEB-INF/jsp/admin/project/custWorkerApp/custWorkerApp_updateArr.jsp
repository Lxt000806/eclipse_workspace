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
	<title>工程部工人申请编辑安排</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_newWorker.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
var isDelay=false;
$(function() {
	function getData(){
		$("#page_form").data('bootstrapValidator')  
                   .updateStatus('openComponent_newWorker_workerCode', 'NOT_VALIDATED',null)  
                   .validateField('openComponent_newWorker_workerCode'); 
	}
	
	$("#workerCode").openComponent_newWorker({showValue:'${custWorkerApp.workerCode}',showLabel:'${custWorkerApp.workerDescr}',
		condition:{m_umState:"E", custCode:'${custWorkerApp.custCode }',normDay:'${prjnormday}',prjRegionCode:"${prjRegion}"
		,workType12Query:$.trim('${custWorkerApp.workType12 }')}
		,callBack:getData});
		
	$("#page_form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			openComponent_newWorker_workerCode:{  
				validators: {  
					remote: {
			            message: '',
			            url: '${ctx}/admin/worker/getWorker',
			            data: getValidateVal,  
			            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			        } 
				} ,
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
			
	if("${isWaterCtrlMan}" == "false"){
			$("#isWaterItemCtrl").attr("disabled","disabled");	
	}
		
	(function changeEndD(){
		var constructDay = $.trim($("#constructDay").val());
		if(constructDay==""||constructDay==null){
			constructDay=0;
		}
		var  comeDate=new Date($("#comeDate").val());
		
		if(constructDay=='0'||constructDay==null){
			$("#planEnd").val('');
		}else if($("#comeDate").val()!=''){
			$("#planEnd").val(addDate(constructDay));
		}
	})();
});
$(function(){	
	if($.trim("${itemArrive }")=="满足"&& $.trim("${befWorkType12ConfString }")=="满足" && $.trim("${moneyCtrl }")=="满足"){
		$("#notify").hide();
	}else{
		$("#notify").show();
	}
	
	if($.trim("${custWorkerApp.workType12 }")=="02"){
		$("#isWaterItemCtrl_li").show();
	}else{
		$("#isWaterItemCtrl_li").hide();
	}
	selectDelayRemark();
	$("#needZF").attr("disabled",true);
	if($.trim("${custWorkerApp.workType12}")=="01"){
		$.ajax({
			url:'${ctx}/admin/CustWorkerApp/isNeedZF',
			type: 'post',
			data: {custCode:'${custWorkerApp.custCode }'},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success:function(obj){
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
	
	$("#viewZF").on("click",function(){
		var custCode = $.trim($("#custCode").val());
		var prjnormday = $.trim($("#prjnormday").val());
			Global.Dialog.showDialog("viewZF",{
				title:"砌墙瓷砖——查看",
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
	
	function getConstructDay(data){
		$.ajax({
			url:'${ctx}/admin/CustWorkerApp/getConstructDay',
			type: 'post',
			data: {custCode:'${custWorkerApp.custCode }',workerCode:data.Code},
			//dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	$("#constructDay").val(obj);
		    	if(obj==0){
		    		$("#planEnd").val('');
		    	}else if($("#comeDate").val()!=''){
		    		$("#planEnd").val(addDate(obj));
		    	}
		    	
		   	} 
		 });
	}

$("#custCode").openComponent_customer({showValue:'${custWorkerApp.custCode}',showLabel:'${custWorkerApp.custDescr}',readonly:'true'});

$("#hasStove").prop("disabled", true);
$("#isSelfMetal_Cup").prop("disabled", true);
$("#isSelfMetal_Int").prop("disabled", true);
$("#isSelfSink").prop("disabled", true);
switch ($.trim("${custWorkerApp.workType12}")) {
	case "17":
		$("#hasStove_Li").prop("hidden", true);
		$("#isSelfMetal_Cup_Li").prop("hidden", true);
		$("#isSelfSink_Li").prop("hidden", true);
		break;
	case "18":
		$("#isSelfMetal_Int_Li").prop("hidden", true);
		break;
	default:
		$("#isSelfMetal_Int_Li").prop("hidden", true);
		$("#hasStove_Li").prop("hidden", true);
		$("#isSelfMetal_Cup_Li").prop("hidden", true);
		$("#isSelfSink_Li").prop("hidden", true);
		break;
}

	$("#saveBtn").on("click",function(){
		var isWaterItemCtrl=$.trim($("#isWaterItemCtrl").val());
		var workType12=$.trim($("#workType12").val());
		if(isWaterItemCtrl == "" && workType12=="02"){
			art.dialog({
				content:"水电材料发包工人必填",
			});
			return;
		}
		if(isDelay && $("#comeDelayType").val()==""){
			art.dialog({
				content:'请填写延误原因',
			});
			return false;
		}
		$("#appComeDate").removeAttr("disabled");
		$("#appDate").removeAttr("disabled");
		$("#planEnd").removeAttr("disabled");
		$("#isWaterItemCtrl").removeAttr("disabled");
	
		$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
		var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/CustWorkerApp/doUpdateArr',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
			}
		});
	});
	
	$("#view").on("click",function(){
		var custCode = $.trim($("#custCode").val());
		var prjnormday = $.trim($("#prjnormday").val());
		var workType12 = $.trim($("#workType12").val());
			Global.Dialog.showDialog("View",{
				title:"工作量——查看",
				url:"${ctx}/admin/CustWorkerApp/goViewWorker",
				postData:{custCode:custCode,prjnormday:prjnormday, workType12: workType12},
				height:700,
				width:1050,
				returnFun:goto_query
			});
	});
	
});
function validateRefresh(){
	 $('#page_form').data('bootstrapValidator')  
                    .updateStatus('comeDate', 'NOT_VALIDATED',null)  
                    .validateField('comeDate') 
}
function changePlanEnd(){
	var constructDay = $.trim($("#constructDay").val());
	if(constructDay==""||constructDay==null){
		constructDay=0;
	}
	var  comeDate=new Date($("#comeDate").val());
	
	if(constructDay=='0'||constructDay==null){
		$("#planEnd").val('');
	}else if($("#comeDate").val()!=''){
		$("#planEnd").val(addDate(constructDay));
	}
}
function addDate(days){ 
   var d=new Date($("#comeDate").val()); 
   d.setDate(d.getDate()+parseInt(days)-1); 
   var m=d.getMonth()+1; 
   var day=d.getDate();
   if(m<10){
   	m='0'+m;
   }
    if(day<10){
   	day='0'+day;
   } 
   return d.getFullYear()+'-'+m+'-'+day; 
} 
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
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
			<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			  		<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li hidden="true">
								<label>pk</label>
								<input type="text" id="pk" name="pk" style="width:160px;" value="${custWorkerApp.pk }" />
							</li>
							<li hidden="true">
								<label>prjnormday</label>
								<input type="text" id="prjnormday" name="prjnormday" style="width:160px;" value="${prjnormday}" />
							</li>
					 	<div class="validate-group row" >							
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custWorkerApp.custCode }" />
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custWorkerApp.address }" readonly="true"/>
							</li>
						</div>
					 	<div class="validate-group row" >							
							<li>
								<label>工种</label>
								<house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${custWorkerApp.workType12 }" disabled="true"></house:DataSelect>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm  id="status" dictCode="WORKERAPPSTS"   style="width:px;" value="2" disabled="true"></house:xtdm>
							</li>
						</div>
					 	<div class="validate-group row" >									<li>
								<label>申请进场时间</label>
								<input type="text" style="width:160px;" id="appComeDate" name="appComeDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.appComeDate}' pattern='yyyy-MM-dd'/>" readonly="readonly"  disabled="disabled"/>
							</li>
							<li>
								<label>申请时间</label>
								<input type="text" id="appDate" name="appDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.appDate}' pattern='yyyy-MM-dd '/>" disabled="true" />
							</li>
						</div>
					 	<div class="validate-group row" >			
							<li class="form-validate">
								<label><span class="required">*</span>安排进场时间</label>
								<input type="text" id="comeDate" name="comeDate" onblur="changePlanEnd();selectDelayRemark()"  onchange="validateRefresh()" required data-bv-notempty-message="进场时间不能为空" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.comeDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate">
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
					 	<div class="validate-group row" >			
							<li >
								<label>是否系统安排</label>
								<house:xtdm  id="isSysArrange" dictCode="YESNO"   style="width:160px;" value="${custWorker.isSysArrange }" disabled="true"></house:xtdm>
							</li>
							<li >
								<label>工期</label>
								<input type="text" id="constructDay" name="constructDay" onblur="changePlanEnd()" style="width:110px;" value="${workTypeConDay }" readonly="true" />
								<button type="button" class="btn btn-system " style="width:35px;height:24px;font-size: 12px;margin-top:-3px" 
										id="view" >
									<span>查看</span>
								</button>
							</li>
							<%--<div class="btn-group-xs" >
							<button type="button" class="btn btn-system " id="view" >
								<span>查看</span>
							</button>
							</div>
						--%></div>
					 	<div class="validate-group row" >			
							<li>
								<label>计划完工时间</label>
								<input type="text" style="width:160px;" id="planEnd" name="planEnd" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorker.planEnd}' pattern='yyyy-MM-dd'/>"   disabled="disabled"/>
							</li>
							<li  >
								<label>完工时间</label>
								<input type="text" id="endDate" name="endDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorker.endDate}' pattern='yyyy-MM-dd '/>" />
							</li>
						</div>
					 	<div class="validate-group row" >			
							<li>
								<label>施工状态</label>
								<house:xtdm  id="constructStatus" dictCode="CUSTWKSTATUS"   style="width:160px;" value="${custWorkerApp.constructStatus }" ></house:xtdm>
							</li>
							<li >
								<label>安排次数</label>
								<input type="text" id="arrTimes" name="arrTimes" style="width:160px;" value="${arrTimes }" readonly="true" />
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
					 	<div class="validate-group row" >	
					 		<li id="hasStove_Li">
								<label for="hasStove">集成灶</label>
								<house:xtdm id="hasStove" dictCode="HAVENO" style="width:160px;" value="${specItemReq.hasStove}"/>
							</li>
							<li id="isSelfMetal_Cup_Li">
								<label for="isSelfMetal_Cup">自购五金（橱柜）</label>
								<house:xtdm id="isSelfMetal_Cup" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfMetal_Cup}"/>
							</li>
							<li id="isSelfMetal_Int_Li">
								<label for="isSelfMetal_Int">自购五金（衣柜）</label>
								<house:xtdm id="isSelfMetal_Int" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfMetal_Int}"/>
							</li>
							<li id="isSelfSink_Li">
								<label for="isSelfSink">自购水槽</label>
								<house:xtdm id="isSelfSink" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfSink}"/>
							</li>
							<li>
								<label>是否周末施工</label>
								<house:xtdm id="isHoliConstruct" dictCode="YESNO" style="width:160px;" value="${isHoliConstruct}" disabled="true"/>
							</li>	
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2" readonly="true">${custWorkerApp.remarks }</textarea>
							</li>
						</div>	
						</ul>	
				</form>
				</div>
				</div>
			</div>
		</div>
	</body>	
</html>
