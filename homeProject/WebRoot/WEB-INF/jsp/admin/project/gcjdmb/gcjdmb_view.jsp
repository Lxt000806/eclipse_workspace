<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>工程进度模板明细页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
		
			function EditValid(str, closeFlag){
				if(!str || str.trim() == ""){
					art.dialog({
						content:"请输入完整的信息",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});
					return true;
				}
				return false;
			}
			function ajaxPost(url, data, successFun){
			 	$.ajax({
					url:url,
					type:"post",
			    	data:data,
					dataType:"json",
					cache:false,
					async:false,
					error:function(obj){			    		
						art.dialog({
							content:"访问出错,请重试",
							time:3000,
							beforeunload: function () {}
						});
					},
					success:successFun
				});	
			}
			
			function save(){
				
				if($("#type").val() == ""){
					art.dialog({
						content:"请选择类型"
					});
					return;
				}
				
				if($("#m_umState").val() == "A"){
					if(EditValid($("#descr").val())){
						$("#isSaveOk").val("0");
						return;
					}
				}
				
				if($("#type").val() == "2"){
					var errorRows = hasErrorForTempDetail();
					if(errorRows.length>0){
						art.dialog({
							content:"模版明细表第"+errorRows.join(",")+"行的上一节点不存在，请确认！"
						});
						return;
					}
					
					var repeatRow = hasRepeatRows();
					if(repeatRow.length>0){
						art.dialog({
							content:"模版明细表节点重复不允许保存！"
						});
						return;
					}
				}
				
				var tempDtJson=Global.JqGrid.allToJson("dataTable_tempDetail");
				
				var tempAlarmJson=Global.JqGrid.allToJson("dataTable_tipEvent");

				var param = {
					progTempDt:JSON.stringify(tempDtJson.datas),
					progTempAlarm:JSON.stringify(tempAlarmJson.datas)
				};
			
				Global.Form.submit("dataForm", "${ctx}/admin/gcjdmb/doSavePrjProgTemp", param, function(ret){
					console.log(ret);
					if(ret.rs==true){
						$("#isTipExit").val(ret.datas.isTipExit);
						$("#isSaveOk").val(ret.datas.isSaveOk);
						exit();
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
						if(ret.datas.isTipExit && ret.datas.isTipExit != ""){
							$("#isTipExit").val(ret.datas.isTipExit);
						}
						if(ret.datas.isSaveOk && ret.datas.isSaveOk != ""){
							$("#isSaveOk").val(ret.datas.isSaveOk);
						}
						art.dialog({
							content:ret.datas.msg
						});
					} 
				});
			}
	 		
			
			function doExcel(){
				var nowShow = $("#gcjdmbUI li[class='active']").children().attr("id");
				
				if(nowShow == "a_tipEvent"){
					doExcelNow("提醒事项", "dataTable_tipEvent", "dataForm");
				}else{
					doExcelNow("模板明细", "dataTable_tempDetail", "dataForm");
				}
			}
			
			$(function(){
	   			$("#no").attr("readonly", true);
	   			
	   			$("#descr").blur(function(){
	   				if($("#oldDescr").val() != $("#descr").val()){
	   					$("#isTipExit").val("1");
	   				}
	   			});
	   			
	   			$("#remarks").blur(function(){
	   				if($("#oldRemarks").val() != $("#remarks").val()){
	   					$("#isTipExit").val("1");
	   				}
	   			});
	   			
	   			if($("#m_umState").val() == "A"){
	   				$("#expired_show").attr("disabled", true);
	   			}
	   			
	   			if($("#m_umState").val() !== "A"){
	   				$("#type").attr("disabled", true);
	   			}
	   			
	   			if($("#m_umState").val() == "V"){
	   				$("#saveBut").attr("disabled", true);
	   				$("#tempDetailAddBtn").attr("disabled", true);
	   				$("#tempDetailFastAddBtn").attr("disabled", true);
	   				$("#tempDetailCopyBtn").attr("disabled", true);
	   				$("#tempDetailUpdateBtn").attr("disabled", true);
	   				$("#tempDetailDelBtn").attr("disabled", true);
	   				$("#tempDetailUpBtn").attr("disabled", true);
	   				$("#tempDetailDownBtn").attr("disabled", true);
	   				
	   				$("#tipEventAddBtn").attr("disabled", true);
	   				$("#tipEventFastAddBtn").attr("disabled", true);
	   				$("#tipEventCopyBtn").attr("disabled", true);
	   				$("#tipEventUpdateBtn").attr("disabled", true);
	   				$("#tipEventDelBtn").attr("disabled", true);
	   			}
			});
			function changeExpired(obj){
				checkExpired(obj);
				$("#isTipExit").val("1");
			}
			function exit(){
				if($("#isTipExit").val() == "1" && $("#m_umState").val() == "A" && !$("#saveBut").attr("disabled")){
					art.dialog({
						content:"是否保存被更改的数据?",
		    			button: [{
							value: "取消",
							callback:function(){}
						}, {
				            value: "否",
				            callback: function () {
		    					closeWin();
				            }
				        }],
				        ok:function(){
							save();
							if($("#isSaveOk").val("1")){
								closeWin();
							}
				        }
					});
				}else{
					closeWin();
				}
			}
			
			function befChangeType(){
				if($("#dataTable_tempDetail").getGridParam("reccount")>0){
					art.dialog({
						content:"模版明细存在数据，不允许变更模板类型！"
					});
				}
			}
			
			function hasErrorForTempDetail(){
				var errorRowIds = [];
				var rowIds = $("#dataTable_tempDetail").getDataIDs();
				for(var i=0; i<=rowIds.length; i++){
					var flg = false
					var ret1 = $("#dataTable_tempDetail").jqGrid("getRowData", rowIds[i]);
					for(var j=0; j<=rowIds.length; j++){
						var ret2 = $("#dataTable_tempDetail").jqGrid("getRowData", rowIds[j]);
						if((ret1.befprjitem==ret2.prjitem)||!ret1.befprjitem){
							flg = true;
							break;
						}
					}
					if(!flg){
						errorRowIds.push(rowIds[i]);
					}
				}
				return errorRowIds;
			}
			
			function hasRepeatRows(){
				var errorRowIds = [];
				var rowIds = $("#dataTable_tempDetail").getDataIDs();
				for(var i=0; i<=rowIds.length; i++){
					var flg = false
					var ret1 = $("#dataTable_tempDetail").jqGrid("getRowData", rowIds[i]);
					for(var j=i+1; j<=rowIds.length; j++){
						var ret2 = $("#dataTable_tempDetail").jqGrid("getRowData", rowIds[j]);
						if(ret1.prjitem==ret2.prjitem){
							flg = true;
							break;
						}
					}
					if(flg){
						errorRowIds.push(rowIds[i]);
					}
				}
				return errorRowIds;
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="excelBut" type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="exit()">关闭</button>
					</div>
				</div>
			</div>
			<div id="itemAppFromDiv" class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value="" />
						<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }" />
						<input type="hidden" id="nowPk" name="nowPk" value="${data.nowPk }" />
						<input type="hidden" id="nowPkTipEvent" name="nowPkTipEvent" value="${data.nowPkTipEvent }" />
						<input type="hidden" id="expired" name="expired" value="${data.expired=='T'?'T':'F' }"/>
						<input type="hidden" id="isTipExit" name="isTipExit" value="0" />
						<input type="hidden" id="oldDescr" name="oldDescr" value="${data.descr }" />
						<input type="hidden" id="oldRemarks" name="oldRemarks" value="${data.remarks }" />
						<input type="hidden" id="isSaveOk" name="isSaveOk" value="0"/>
 						<ul class="ul-form">
							<li>
								<label>模板编号</label>
								<input type="text" id="no" name="no" value="${data.no }"/>
							</li>
							<li>
								<label>模板名称</label>
								<input type="text" id="descr" name="descr" value="${data.descr}"/>
							</li>
							<li>
								<label>备注</label>
								<input type="text" id="remarks" name="remarks" value="${data.remarks }"/>
							</li>	
							<li onClick="befChangeType()">
								<label>类型</label>
								<house:xtdm id="type" dictCode="PROGTEMPTYPE"  value="${data.type }" ></house:xtdm>
							</li>
							<li><label>客户类型</label> <house:dict id="custType" dictCode=""
									sql="select code ,code+' '+desc1 descr from tcustType where expired='F'"
									sqlValueKey="Code" sqlLableKey="Descr" value="${data.custType}"
									>
								</house:dict>
							</li>
							<li class="search-group-shrink">
								<input type="checkbox" id="expired_show" name="expired_show"
										value="${data.expired}" onclick="changeExpired(this)"
										${data.expired=='T'?'checked=\'checked\'':'' }/><p>过期</p>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid">  
				<ul id="gcjdmbUI" class="nav nav-tabs" >
				    <li class="active"><a id="a_tempDetail" href="#tabView_gcjdmb_view_tempDetail" data-toggle="tab">模板明细</a></li> 
				    <li><a id="a_tipEvent" href="#tabView_gcjdmb_view_tipEvent" data-toggle="tab">提醒事项</a></li>  
				</ul>  
			    <div class="tab-content">  
					<div id="tabView_gcjdmb_view_tempDetail"  class="tab-pane fade in active"> 
			         	<jsp:include page="tabView_gcjdmb_view_tempDetail.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_gcjdmb_view_tipEvent"  class="tab-pane fade"> 
			         	<jsp:include page="tabView_gcjdmb_view_tipEvent.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
				</div>	
			</div>	
		</div>
	</body>
</html>


