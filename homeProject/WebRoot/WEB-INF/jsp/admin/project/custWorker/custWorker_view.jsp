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
	<title>现场设计师安排</title>
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
		if($.trim("${custWorker.workType12 }")=="02"){
			$("#isWaterItemCtrl_li").show();
		}else{
			$("#isWaterItemCtrl_li").hide();
		}
		
		function getCustData(data){
			if(!data){
				return;
			}
			$("#projectMan").val(data.projectmandescr);
			$("#address").val(data.address);
			$("#projectMan").val(data.projectmandescr);
	
		}
		$("#custCode").openComponent_customer({callBack:getCustData,showValue:"${custWorker.custCode}",showLabel:"${customer.descr}",readonly:true});	
		$("#workerCode").openComponent_worker({showValue:"${custWorker.workerCode}",showLabel:"${worker.nameChi}"});	
		$("#workerDetail").on("click",function(){
			var ret = selectDataTableRow();
			Global.Dialog.showDialog("workerDetail",{
				title:"工人汇总分析",
				url:"${ctx}/admin/CustWorkerApp/goWorkerDetail",
				height:750,
				width:1000,
				returnFun:goto_query
			});
		});
		
		$("#viewLog").on("click",function(){
			var ret = selectDataTableRow();
			Global.Dialog.showDialog("viewLog",{
				title:"修改日志",
				url:"${ctx}/admin/custWorker/goViewLog",
				postData:{
					custCode:$("#custCode").val(),workType12:$("#workType12").val()
				},
				height:400,
				width:800,
				returnFun:goto_query
			});
		});
		
		getConsDay();
	});
	//和编辑统一内容 modify by zb on 20200325
	function getConsDay(){
		$.ajax({
			url:"${ctx}/admin/custWorker/getConstructDay",
			type: "post",
			data: {custCode:$.trim("${custWorker.custCode }"),workType12:$.trim("${custWorker.workType12 }")},
			dataType: "text",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				var comeDate=new Date($("#comeDate").val()); 
				if(comeDate!="" && comeDate!=null){
					changePlanEnd();
				}
		   	} 
		});
	}
	function changePlanEnd(){
		var constructDay = $.trim($("#constructDay").val());
		if(constructDay==""||constructDay==null){
			constructDay=0;
		}
		var  comeDate=new Date($("#comeDate").val());
		
		if(constructDay=="0"||constructDay==null){
			$("#planEnd").val("");
		}else if($("#comeDate").val()!=""){
			$("#planEnd").val(addDate(constructDay));
		}
	}
	function addDate(days){ 
		var d=new Date($("#comeDate").val()); 
		d.setDate(d.getDate()+parseInt(days)-1); 
		var m=d.getMonth()+1; 
		var day=d.getDate();
		if(m<10){
			m="0"+m;
		}
		if(day<10){
			day="0"+day;
		} 
		return d.getFullYear()+"-"+m+"-"+day; 
	} 
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
			    	<button type="button" class="btn btn-system" id="workerDetail">
						<span>工人汇总分析</span>
					</button>
					<button type="button" class="btn btn-system" id="viewLog">
						<span>修改日志</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="pk" name="pk" value="${custWorker.pk }"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custWorker.custCode }"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address}" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>工种</label>
								 <house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${custWorker.workType12 }" disabled="true"></house:DataSelect>
							</li>
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${employee.nameChi}" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label><span class="required">*</span>工人编号</label>
								<input type="text" id="workerCode" name="workerCode" value="${custWorker.workerCode}" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>进场时间</label>
								<input type="text" id="comeDate" name="comeDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorker.comeDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>是否系统安排</label>
								<house:xtdm  id="isSysArrange" dictCode="YESNO" style="width:160px;" value="${custWorker.isSysArrange }" disabled="true"></house:xtdm>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>计划完工时间</label>
								<input type="text" id="planEnd" name="planEnd" class="i-date" style="width:160px;"
										disabled="true" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${custWorker.planEnd}' pattern='yyyy-MM-dd '/>"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>是否停工</label>
								<house:xtdm  id="status" dictCode="CUSTWKSTATUS" style="width:160px;" value="${custWorker.status }" disabled="true"></house:xtdm>
							</li>
							<li  class="form-validate">
								<label>计划工期</label>
								<input type="text" id="constructDay" name="constructDay" style="width:160px;" value="${custWorker.constructDay }" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>实际完工时间</label>
								<input type="text" id="endDate" name="endDate" onchange="validateRefresh()" class="i-date" style="width:160px;" disabled="true"  
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${custWorker.endDate}' pattern='yyyy-MM-dd hh:mm:ss'/>"/>
							</li>
							<li  class="form-validate" id="isWaterItemCtrl_li">
								<label style="width:110px;margin-left:0px"><span class="required" >*</span>水电材料发包工人</label>
								<house:xtdm  id="isWaterItemCtrl" dictCode="YESNO"  disabled="true"  style="width:160px;" value="${isWaterItemCtrl }"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>报备完工时间</label>
								<input type="text" id="conPlanEnd" name="conPlanEnd" class="i-date" style="width:160px;"
										disabled="true" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${custWorker.conPlanEnd}' pattern='yyyy-MM-dd '/>"/>
							</li>
							<li  class="form-validate">
								<label>实际工期</label>
								<input type="text" id="actualDays" name="actualDays" style="width:160px;" value="${actualDays }" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${custWorker.remarks}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
</html>
