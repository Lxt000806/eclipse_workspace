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
	<title>工地工人安排</title>
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
	<script src="${resourceRoot}/pub/component_newWorker.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function() {
		if("${isWaterCtrlMan}" == "false"){
			$("#isWaterItemCtrl").attr("disabled","disabled");	
			console.log(111);
		}
		console.log("${isWaterCtrlMan}");
		$("#workerCode").openComponent_newWorker({callBack:function(){validateRefresh();},showValue:"${custWorker.workerCode}",showLabel:"${worker.nameChi}",condition:{workType12:$.trim($("#workType12").val()),isSign:"1",custCode:$.trim($("#custCode").val()),normDay:$("#constructDay").val()}});
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {  
				status:{  
					validators: {  
						notEmpty: {  
							message: "是否停工不能为空"  
						}
					}  
				},  
			    openComponent_newWorker_workerCode:{  
			        validators: {  
			            notEmpty: {  
			           		 message: "工人编号不能为空"  
			            }
			        }  
			    },
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	
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
		
		$("#saveBtn").on("click", function(){
			doSave();
		});
	});
	function doSave(checkArrFlag){
		$("#isWaterItemCtrl").removeAttr("disabled","disabled");
		$("#planEnd").removeAttr("disabled","true");
		$("#planEnd").removeAttr("disabled","true");
		$("#isSysArrange").removeAttr("disabled","true");
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		if(checkArrFlag){
			$("#checkArrFlag").val("0");
		}else{
			$("#checkArrFlag").val("1");
		}
		var datas = $("#dataForm").serialize();
		var custSceneDesi=$.trim($("#custSceneDesi").val());
		var isWaterItemCtrl=$.trim($("#isWaterItemCtrl").val());
		var workType12=$.trim($("#workType12").val());
		
		if(isWaterItemCtrl=="" && workType12=="02"){
			art.dialog({
				content:"水电材料发包工人必填",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/custWorker/doUpdate",
			type: "post",
			data:datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
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
		    		$("#isWaterItemCtrl").attr("disabled","disabled");
		    		$("#_form_token_uniq_id").val(obj.token.token);
					var options = {
						content: obj.msg,
						width: 200
		    		};
		    		if(obj.msg == "该工地已安排同一工人！"){
		    			options.ok = function(){
		    				doSave(true);
		    			};
		    			options.cancel = function(){};
		    			options.content = "该工地已安排同一工人,是否继续？";
		    		}
		    		art.dialog(options);
		    	}
		    }
		});
	}
	function validateRefresh(){
		$("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_newWorker_workerCode", "NOT_VALIDATED",null)  
		                   .validateField("openComponent_newWorker_workerCode")
		                   .updateStatus("comeDate", "NOT_VALIDATED",null)  
		                   .validateField("comeDate")  ;
	}
	function getConsDay(){
		$.ajax({
			url:"${ctx}/admin/custWorker/getConstructDay",
			type: "post",
			data: {custCode:$.trim($("#custCode").val()),workType12:$.trim($("#workType12").val())},
			dataType: "text",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	$("#constructDay").val(obj);	
				var comeDate=new Date($("#comeDate").val()); 
				if(comeDate!="" && comeDate!=null){
					addDate(obj);
				}
		   	} 
		});
	}
	(function getConsDay(){
		$.ajax({
			url:"${ctx}/admin/custWorker/getConstructDay",
			type: "post",
			data: {custCode:"${custWorker.custCode }",workType12:"${custWorker.workType12 }"},
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
	})();
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
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="workerDetail">
						<span>工人汇总分析</span>
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
					<input type="hidden" id="apppk" name="apppk" value="${apppk }"/>
					<input type="hidden" name="checkArrFlag" id="checkArrFlag" value=""/>
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
							<li class="form-validate">
								<label><span class="required">*</span>工人编号</label>
								<input type="text" id="workerCode" name="workerCode" value="${custWorker.workerCode}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>进场时间</label>
								<input type="text" id="comeDate" name="comeDate" onchange="validateRefresh()" required data-bv-notempty-message="进场时间不能为空" 
								class="i-date" style="width:160px;" onblur="changePlanEnd()" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${custWorker.comeDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
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
										value="<fmt:formatDate value='${custWorker.planEnd}' pattern='yyyy-MM-dd'/>"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>是否停工</label>
								<house:xtdm  id="status" dictCode="CUSTWKSTATUS" style="width:160px;" value="${custWorker.status }"></house:xtdm>
							</li>
							<li  class="form-validate">
								<label>计划工期</label>
								<input type="text" id="constructDay" name="constructDay" style="width:160px;" value="${custWorker.constructDay }" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>实际完工时间</label>
								<input type="text" id="endDate" name="endDate" onchange="validateRefresh()" class="i-date" style="width:160px;"  
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${custWorker.endDate}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li  class="form-validate" id="isWaterItemCtrl_li">
								<label style="width:110px;margin-left:0px"><span class="required" >*</span>水电材料发包工人</label>
								<house:xtdm  id="isWaterItemCtrl" dictCode="YESNO"   style="width:160px;" value="${isWaterItemCtrl }"></house:xtdm>
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
