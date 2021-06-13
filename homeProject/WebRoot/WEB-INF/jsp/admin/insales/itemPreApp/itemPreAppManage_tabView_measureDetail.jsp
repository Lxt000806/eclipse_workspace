<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String fromFlag = request.getParameter("fromFlag"); 
	String pk = request.getParameter("pk"); 
%>
<!DOCTYPE html>
<html>
	<head>
	    <title>测量单详情</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			function openComponentDisabled(id){	   		
				$("#"+id).attr("readonly", true);
				$("#"+id).next().attr("data-disabled", true);	   		
			}
			$(function(){
				if($("#fromFlag_measureDetail").val()=="M" || $("#fromFlag_measureDetail").val()=="V"){
					$("#measureDetail_panelButtons").attr("hidden", true);
					$("#measureDetail_panelInfo").removeClass("panel");
				 	$.ajax({
						url:"${ctx}/admin/itemPreApp/getMeasureDetail?pk="+parseInt($("#pk_measureDetail").val().trim()),
						type: "post",
						dataType: "json",
						cache: false,
						error: function(obj){			    		
							art.dialog({
								content: "访问出错,请重试",
								time: 3000,
								beforeunload: function () {
								}
							});
						},
						success: function(obj){			
							$("#supplCode").openComponent_supplier({
								showLabel:obj.supplerDescr,
								showValue:obj.supplCode,
								condition:{
									preAppNo:"${itemPreMeasure.preAppNo}",
									itemRight:"${itemPreMeasure.itemRightForSupplier}"
								}
							});
							openComponentDisabled("openComponent_supplier_supplCode");
							$("#appCzy").openComponent_czybm({
								showLabel:obj.appCzyDescr,
								showValue:obj.appCzy
							});		
							openComponentDisabled("openComponent_czybm_appCzy");
							$("#confirmCzy").openComponent_czybm({
								showLabel:obj.confirmCzyDescr,
								showValue:obj.confirmCzy
							});	
							openComponentDisabled("openComponent_czybm_confirmCzy");
							$("#completeCzy").openComponent_czybm({
								showLabel:obj.completeCzyDescr,
								showValue:obj.completeCzy
							});	
							openComponentDisabled("openComponent_czybm_completeCzy");
							
							$("#clhxd_tr").removeAttr("hidden");
							$("#cancelReason_tr").removeAttr("hidden");
							if($("#fromFlag_measureDetail").val()=="M"){
								$("input").attr("readonly", true);
								$("#measureRemark").attr("readonly", true);
								$("#remarks").attr("readonly", true);
							}
						}
					});	 
				}else{
					$("#supplCode").openComponent_supplier({
						showLabel:"${itemPreMeasure.supplerDescr}",
						showValue:"${itemPreMeasure.supplCode}",
						condition:{
							preAppNo:"${itemPreMeasure.preAppNo}",
							itemRight:"${itemPreMeasure.itemRightForSupplier}"
						}
					});
					openComponentDisabled("openComponent_supplier_supplCode");
					$("#appCzy").openComponent_czybm({
						showLabel:"${itemPreMeasure.appCzyDescr}",
						showValue:"${itemPreMeasure.appCzy}"
					});		
					openComponentDisabled("openComponent_czybm_appCzy");
					$("#confirmCzy").openComponent_czybm({
						showLabel:"${itemPreMeasure.confirmCzyDescr}",
						showValue:"${itemPreMeasure.confirmCzy}"
					});	
					openComponentDisabled("openComponent_czybm_confirmCzy");
					$("#completeCzy").openComponent_czybm({
						showLabel:"${itemPreMeasure.completeCzyDescr}",
						showValue:"${itemPreMeasure.completeCzy}"
					});	
					openComponentDisabled("openComponent_czybm_completeCzy");
				}
				$("select[id!=isSend]").attr("disabled", true);
				$("#status").attr("disabled", true);
			});
		</script>
	</head>
	<body>
		<div class="body-box-form">
			<div id="measureDetail_panelButtons" class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
			</div>
			<div id="measureDetail_panelInfo" class="panel panel-info" >  
				<div class="panel-body">
					<input type="hidden" id="fromFlag_measureDetail" value="<%=fromFlag %>"/>
					<input type="hidden" id="pk_measureDetail" value="<%=pk %>"/>
					<form action="" method="post" id="dataForm" enctype="multipart/form-data" class="form-search">
						<house:token></house:token>
						<ul class="ul-form">
							<li>
								<label>领料预申请单号</label>
								<input type="text" id="preAppNo" name="preAppNo" value="${itemPreMeasure.preAppNo}"/>
							</li>
							<li>	
								<label>申请人</label>
								<input type="text" id="appCzy" name="appCzy" value="${itemPreMeasure.appCzy}"/>
							</li>
							<li>
								<label>接收人</label>
								<input type="text" id="confirmCzy" name="confirmCzy" value="${itemPreMeasure.confirmCzy}"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="MEASURESTATUS" value="${itemPreMeasure.status }" ></house:xtdm>
							</li>
							<li>
								<label>供应商</label>
								<input type="text" id="supplCode" name="supplCode" value="${itemPreMeasure.supplCode}"/>
							</li>
							<li>
								<label>下单时间</label>
								<input type="text" id="completeDate" name="completeDate" value="<fmt:formatDate value='${itemPreMeasure.completeDate }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" id="date" name="date" value="<fmt:formatDate value='${itemPreMeasure.date }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li>
								<label>接收时间</label>
								<input type="text" id="confirmDate" name="confirmDate" value="<fmt:formatDate value='${itemPreMeasure.confirmDate }' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li>
								<label>下单人</label>
								<input type="text" id="completeCzy" name="completeCzy" value="${itemPreMeasure.completeCzy}"/>
							</li>
		
							<div id="clhxd_tr" hidden>	
								<li>			
									<label>延误原因</label>
									<house:xtdm id="delayReson" dictCode="APPDELAYRESON" value="${itemPreMeasure.delayReson }" ></house:xtdm>
								</li>
								<li>			
									<label>是否通知项目经理</label>
									<house:xtdm id="isSend" dictCode="YESNO" value="1"></house:xtdm>
								<li>
							</div>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks">${itemPreMeasure.remarks }</textarea>
							</li>
							<li>
								<label class="control-textarea">测量数据</label>
								<textarea id="measureRemark" >${itemPreMeasure.measureRemark }</textarea>
							</li>
							<li id="cancelReason_tr" hidden>	
								<label class="control-textarea">取消原因</label>
								<textarea id="cancelRemark">${itemPreMeasure.cancelRemark }</textarea>
							</li>
							<ul hidden>
								<label>pk</label>
								<input type="text" id="pk" name="pk" value="${itemPreMeasure.pk}"/>
					
							</ul>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>

