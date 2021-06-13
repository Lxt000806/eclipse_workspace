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

		$("#isWaterItemCtrl_li").hide()

		function getCustData(data){
			if(!data){
				return;
			}
		    //$("#isWaterItemCtrl").val(data.iswateritemctrl);	
		    $("#isWaterItemCtrl_li").hide();
		    $("#workType12").val("");	
			$("#projectMan").val(data.projectmandescr);
			$("#address").val(data.address);
			$("#projectMan").val(data.projectmandescr);

		    $("#dataForm").data("bootstrapValidator").updateStatus("workType12", "NOT_VALIDATED").validateField("workType12");
			$("#workerCode").openComponent_newWorker({callBack:function(){validateRefresh_workerCode();},condition:{workType12:$.trim($("#workType12").val()),isSign:"1",custCode:$.trim($("#custCode").val()),normDay:$("#constructDay").val()}});
			validateRefresh_custCode();
		}
		$("#custCode").openComponent_customer({callBack:getCustData,condition:{custWorkerCustStatus:"1,2,3,5"}});	
		$("#workerCode").openComponent_newWorker({callBack:function(){validateRefresh_workerCode();},condition:{workType12:$.trim($("#workType12").val()),isSign:"1",custCode:$.trim($("#custCode").val()),normDay:$("#constructDay").val()}});
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {    
				workType12:{  
					validators: {  
						notEmpty: {  
							message: "工种不能为空"  
						}
					}  
				},
				status:{  
					validators: {  
						notEmpty: {  
							message: "是否停工不能为空"  
						}
					}  
				},
				comeDate:{  
					validators: {  
						notEmpty: {  
							message: "进场时间不能为空"  
						}
					}  
				},
				openComponent_customer_custCode:{  
			        validators: {  
			            notEmpty: {  
			           		 message: "客户编号不能为空"  
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
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		$("#planEnd").removeAttr("disabled","true");
		$("#isSysArrange").removeAttr("disabled","true");
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
			url:"${ctx}/admin/custWorker/doSave",
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
		                   .updateStatus("comeDate", "NOT_VALIDATED",null)  
		                   .validateField("comeDate");
	}
	function validateRefresh_workerCode(){
		$("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_newWorker_workerCode", "NOT_VALIDATED",null)  
		                   .validateField("openComponent_newWorker_workerCode") ;
	}
	function validateRefresh_custCode(){
		$("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_customer_custCode", "NOT_VALIDATED",null)  
		                   .validateField("openComponent_customer_custCode") ;
	}
	function getConsDay(){
		$("#workerCode").openComponent_newWorker({callBack:function(){validateRefresh_workerCode();},condition:{workType12:$.trim($("#workType12").val()),isSign:"1",custCode:$.trim($("#custCode").val()),normDay:$("#constructDay").val()}});
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
		    	if($.trim($("#custCode").val())==""){
		    		return;
		    	}
		    	$("#constructDay").val(obj);	
				var comeDate=new Date($("#comeDate").val()); 
				if(comeDate!="" && comeDate!=null){
					addDate(obj);
				}
		   	} 
		});
		if($.trim($("#workType12").val())=="02"){
			$("#isWaterItemCtrl_li").show()
		}else{
			$("#isWaterItemCtrl_li").hide()
		}
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
	function getMinDate(){
		var minDate = new Date("${custWorker.comeDateFrom}");
	    return formatDate(minDate);
	}
	function getMaxDate(){
		var maxDate = new Date("${custWorker.comeDateTo}");
		return formatDate(maxDate);
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
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="checkArrFlag" id="checkArrFlag" value=""/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>工种</label>
								<house:dict id="workType12" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
								    sql="select a.* from tWorkType12 a
								         where a.expired ='F' and (
								             (select PrjRole from tCZYBM where CZYBH='${czybm}') is null
								             or (select PrjRole from tCZYBM where CZYBH='${czybm}') = '')
								             or Code in (select WorkType12 From tprjroleworktype12 pr
								                 where pr.prjrole = (select PrjRole from tCZYBM where CZYBH='${czybm}')
								             or pr.prjrole = '')
								         order by a.DispSeq" onchange="getConsDay()"></house:dict>
							</li>
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>工人编号</label>
								<input type="text" id="workerCode" name="workerCode"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>进场时间</label>
								<input type="text" id="comeDate" name="comeDate" onblur="changePlanEnd()" 
									 class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd' ,minDate: '#F{getMinDate()}' ,maxDate: '#F{getMaxDate()}'})"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>是否系统安排</label>
								<house:xtdm  id="isSysArrange" dictCode="YESNO" style="width:160px;" value="0" disabled="true"></house:xtdm>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>计划完工时间</label>
								<input type="text" id="planEnd" name="planEnd" class="i-date" style="width:160px;"
										disabled="true" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>是否停工</label>
								<house:xtdm  id="status" dictCode="CUSTWKSTATUS" style="width:160px;" value="1" ></house:xtdm>
							</li>
							<li  class="form-validate">
								<label>计划工期</label>
								<input type="text" id="constructDay" name="constructDay" style="width:160px;" value="" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>实际完工时间</label>
								<input type="text" id="endDate" name="endDate" onchange="validateRefresh()" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li  class="form-validate" id="isWaterItemCtrl_li">
								<label style="width:110px;margin-left:0px"><span class="required" >*</span>水电材料发包工人</label>
								<house:xtdm  id="isWaterItemCtrl" dictCode="YESNO" style="width:160px;"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2"></textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
</html>
