<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>配送管理--新增</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var excelName="send";
		/**初始化表格*/
		$(function(){
			 var m_umState="${itemSendBatch.m_umState}";
			 $("#driverCode").openComponent_driver({
				 showValue:'${itemSendBatch.driverCode}',
				 showLabel:'${itemSendBatch.driverCodeDescr}',
				 callBack:function(data){//检查司机是否有两天前订单未完成
				 			console.log(data);
				 		    $.ajax({
                                url : '${ctx}/admin/itemSendBatch/checkDriver',
                                type : 'post',
                                data : {
                                     'driverCode' : data['Code']
                                },
                                async:false,
                                dataType : 'json',
                                cache : false,
                                error : function(list) {
                                     showAjaxHtml({
                                           "hidden" : false,
                                           "msg" : '保存数据出错~'
                                     });
                                },
                                success : function(list) {
                                    if(list.length>0){
                                    	$("#tip").show();
                                    }else{
                                    	$("#tip").hide();
                                    }
                                }   
                           });
				 }
			 });
			 $("#openComponent_driver_driverCode").attr("readonly",true);
			 $("#appCZY").openComponent_employee({
				 showValue:'${itemSendBatch.appCZY}',
				 showLabel:'${itemSendBatch.appCZYDescr}',
				 readonly:true
			 });
			 $("#followMan").openComponent_employee({
				 showValue:'${itemSendBatch.followMan}',
				 showLabel:'${itemSendBatch.followManDescr}',
			 });
			 $("#openComponent_employee_appCZY").attr("readonly",true);
			 if(m_umState=="V"){
			 	$("#driverCode").setComponent_driver({readonly:true});
			 	$("#expired").attr("disabled",true);
			 	$("#remarks").attr("readonly",true);
			 	$("#saveBut").attr("disabled",true);
			 	$("#r_add").attr("disabled",true);
			 	$("#r_delete").attr("disabled",true);
			 	$("#r_sendFee").attr("disabled",true);
			 	$("#r_carryInfo").attr("disabled",true);
			 	$("#r_crtCarryFee").attr("disabled",true);
			 	$("#r_return").attr("disabled",true);
			 	$("#r_cancel").attr("disabled",true);
			 	$("#s_add").attr("disabled",true);
			 	$("#s_delete").attr("disabled",true);
			 	$("#s_sendFee").attr("disabled",true);
			 	$("#s_carryInfo").attr("disabled",true);
			 	$("#s_crtCarryFee").attr("disabled",true);
			 	$("#s_return").attr("disabled",true);
			 }else if(m_umState=="A" || m_umState=="M"){
			 	$("#r_return").attr("disabled",true);
			 	$("#r_cancel").attr("disabled",true);
			 	$("#r_sendFee").attr("disabled",true);
			 	$("#r_carryInfo").attr("disabled",true);
			 	$("#r_crtCarryFee").attr("disabled",true);
			 	$("#s_return").attr("disabled",true);
			 	$("#s_sendFee").attr("disabled",true);
			 	$("#s_carryInfo").attr("disabled",true);
			 	$("#s_crtCarryFee").attr("disabled",true);
			 }else if(m_umState=="F"){
			 	$("#driverCode").setComponent_driver({readonly:true});
			 	$("#r_cancel").attr("disabled",true);
			 	$("#r_add").attr("disabled",true);
			 	$("#r_delete").attr("disabled",true);
			 	$("#r_return").attr("disabled",true);
			 	$("#s_add").attr("disabled",true);
			 	$("#s_delete").attr("disabled",true);
			 	$("#s_return").attr("disabled",true);
			 }else if(m_umState=="R"){
			 	$("#driverCode").setComponent_driver({readonly:true});
			 	$("#r_add").attr("disabled",true);
			 	$("#r_delete").attr("disabled",true);
			 	$("#r_sendFee").attr("disabled",true);
			 	$("#r_carryInfo").attr("disabled",true);
			 	$("#r_crtCarryFee").attr("disabled",true);
			 	$("#s_add").attr("disabled",true);
			 	$("#s_delete").attr("disabled",true);
			 	$("#s_sendFee").attr("disabled",true);
			 	$("#s_carryInfo").attr("disabled",true);
			 	$("#s_crtCarryFee").attr("disabled",true);
			 } 
			 $("#openComponent_driver_driverCode").attr("readonly",true);
		});
		
		//保存
		function save(){
			var driverCode=$("#driverCode").val();
			if(driverCode==""){
				art.dialog({content: "请选择司机！",width: 200});
				return;
			}
			var s_records = $("#sendDetailDataTable").jqGrid('getGridParam', 'records'); //获取发货明细数据总条数
			var r_records = $("#returnDetailDataTable").jqGrid('getGridParam', 'records'); //获取退货明细数据总条数
			var expired= $("#expired").prop('checked');
			if(expired==true && (s_records>0 || r_records>0) ){
				art.dialog({content: "只允许无明细的配送单进行过期操作！",width: 300});
				return;
			}
			$.ajax({
				url : '${ctx}/admin/itemSendBatch/doSave',
				type : 'post',
				data : {
					'itemReturnJson' : JSON.stringify(Global.JqGrid.allToJson("returnDetailDataTable")),
					'itemAppSendJson' : JSON.stringify(Global.JqGrid.allToJson("sendDetailDataTable")),
					'itemAppSendBackJson' : JSON.stringify(Global.JqGrid.allToJson("sendBackDataTable")),
					'm_umState':'${itemSendBatch.m_umState}','no':$("#no").val(),'driverCode':$("#driverCode").val(),
					'status':$("#status").val(),'appCZY':$("#appCZY").val(),'expired':$("#expired").val(),
					'remarks':$("#remarks").val(),'followMan':$("#followMan").val()
				},
				async : false,
				dataType : 'json',
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : '保存数据出错~'
					});
				},
				success : function(ret) {
					if(ret.rs==true){
						art.dialog({
							content: ret.msg,
							time: 1000,
							beforeunload: function () {
			    				closeWin();
						    }
						});
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
			    		art.dialog({
							content: ret.msg,
							width: 200
						});
					}
				}
		 });
		}
		function sendDetail(){
			excelName="send";	
		}
		function returnDetail(){
			excelName="return";		
		}
		function doExcel(){
			if(excelName=="send"){
				doExcelNow('配送管理（发货明细）','sendDetailDataTable','send_form');
			}else if (excelName="return"){
				doExcelNow('配送管理（退货明细）','returnDetailDataTable','return_form');
			}
		}
		function addClose(){
			var isCancel=$("#isCancel").val();
			if(isCancel=="1"){
				art.dialog({
					 content:"已做撤销到货操作,需要进行保存才能修改退货单状态，是否保存",
					 lock: true,
					 width: 450,
					 ok: function () {
						save();
					 },
					 cancel: function () {
						closeWin(false);
					 }
				}); 
			}else{
				closeWin(false);
			}
			
		}
	</script>
	<style>
	</style>
	</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="saveBut" type="button" class="btn btn-system"
						onclick="save()">保存</button>
					<button id="excelBut" type="button" class="btn btn-system"
						onclick="doExcel()">导出Excel</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="addClose()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /><input
					type="hidden" name="isExitTip" id="isExitTip" value="0" /> <input
					type="hidden" name="m_umState" id="m_umState" />
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>批次号</label> <input type="text" id="no" name="no"
							value="${itemSendBatch.no}" readonly="readonly" />
						</li>
						<li><label>生成日期</label> <input type="text" id="date"
							name="date" class="i-date"
							value="<fmt:formatDate value='${itemSendBatch.date }' pattern='yyyy-MM-dd HH:mm:ss'/>"
							readonly="readonly" />
						</li>
						<li><label>状态</label> <house:xtdm id="status"
								dictCode="SENDBATCHSTATUS" value="${itemSendBatch.status}"
								disabled="true"></house:xtdm>
						</li>
						<li><label>申请人</label> <input type="text" id="appCZY"
							name="appCZY" style="width:160px;"
							value="${itemSendBatch.appCZY }" readonly="readonly" />
						</li>
					</div>
					<div class="validate-group row">
						<div>
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${itemSendBatch.remarks
								}</textarea></li>
						</div>
						<div>
							<div class="validate-group row">
								<li><label>司机</label> <input type="text" id="driverCode"
									name="driverCode" style="width:160px;"
									value="${itemSendBatch.driverCode }" />
								</li>
								<li><p id="tip" hidden>
										<font color="red">该司机有两天前的配送任务未完成！</font>
									</p>
								</li>
							</div>
							<div class="validate-group row">
								<li><label>跟车员</label> <input type="text" id="followMan"
									name="followMan" style="width:160px;"
									value="${itemSendBatch.followMan }" />
								</li>
							</div>
						</div>
					</div>
					<c:if test="${itemSendBatch.m_umState!='A' }">
						<div class="validate-group row">
							<li class="form-validate"><label>过期</label> <input
								type="checkbox" id="expired" name="expired"
								value="${itemSendBatch.expired }" ${itemSendBatch.expired!='F'
								?'checked':'' } 
								onclick="checkExpired(this)">
							</li>
						</div>
					</c:if>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabSendDetail" class="active" onclick="sendDetail()"><a
				href="#tab_SendDetail" data-toggle="tab">送货明细</a>
			</li>
			<li id="tabReturnDetail" class="" onclick="returnDetail()"><a
				href="#tab_ReturnDetail" data-toggle="tab">退货明细</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_SendDetail" class="tab-pane fade in active">
				<jsp:include page="itemSendBatch_sendDetail.jsp"></jsp:include>
			</div>
			<div id="tab_ReturnDetail" class="tab-pane fade ">
				<jsp:include page="itemSendBatch_returnDetail.jsp"></jsp:include>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
