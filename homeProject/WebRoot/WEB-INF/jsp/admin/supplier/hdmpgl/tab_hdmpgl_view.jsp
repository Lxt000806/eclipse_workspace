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
	<title>活动门票管理查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_activity.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
	$(function() {
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					address:{  
						validators: {  
							notEmpty: {  
								message: '楼盘不能为空'  
							}
						}  
					},
					descr:{  
						validators: {  
							notEmpty: {  
								message: '业主姓名不能为空'  
							}
						}  
					},
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

$(function(){
	var hasAuthByCzybh=$.trim($("#hasAuthByCzybh").val());
	var provideCZY=$.trim($("#provideCZY").val());
	var czybh=$.trim($("#czybh").val());
	if(czybh!=provideCZY&&hasAuthByCzybh!='true'){
		document.getElementById('phone').setAttribute('type','password') ;
	}
	
	if('${hdmpgl.provideType}'=='2'){
		$("#provideType option[value='2']").attr("selected","selected");
		$("#businessMan").openComponent_employee({readonly:true});	
		$("#designMan").openComponent_employee({readonly:true});
	}else{
		$("#busiManName").attr("readonly","readonly");
		$("#busiManPhone").attr("readonly","readonly");
		$("#businessMan").openComponent_employee({showValue:'${hdmpgl.businessMan }',showLabel:'${hdmpgl.businessDescr }'} );
		$("#designMan").openComponent_employee( {showValue:'${hdmpgl.designMan }',showLabel:'${hdmpgl.designDescr }'});
	}

	$("#provideCZY").openComponent_employee({showValue:'${hdmpgl.provideCZY }',showLabel:'${hdmpgl.provideDescr }',readonly:true});
	$("#actNo").openComponent_activity({showValue:'${hdmpgl.actNo }',showLabel:'${hdmpgl.actDescr }',readonly:true});
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/supplierHdmpgl/doUpdate',
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
		
});
function changeType(){
	var provideType=$.trim($("#provideType").val());
	if(provideType=='2'){
		$("#businessMan").openComponent_employee({readonly:true});	
		$("#designMan").openComponent_employee({readonly:true});
		$("#busiManName").removeAttr("readonly","readonly");
		$("#busiManPhone").removeAttr("readonly","readonly");
		$("#businessMan").val('');
		$("#openComponent_employee_businessMan").val('');
		$("#designMan").val('');
		$("#openComponent_employee_designMan").val('');
	}else{
		$("#businessMan").openComponent_employee( );	
		$("#designMan").openComponent_employee( );
		$("#openComponent_employee_businessMan").removeAttr("readonly",true);
		$("#openComponent_employee_designMan").removeAttr("readonly",true);
		$("#busiManName").attr("readonly","readonly");
		$("#busiManPhone").attr("readonly","readonly");
		$("#busiManName").val("");
		$("#busiManPhone").val("");
	}
	
}

</script>
</head>
	<body>
<div class="body-box-form">
		<div>  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="pk" id="pk"  value="${hdmpgl.pk }"  />
					<input type="hidden" name="hasAuthByCzybh" id="hasAuthByCzybh"  value="${hasAuthByCzybh}"  />
					<input type="hidden" name="czylb" id="czylb"  value="${czylb}"  />
					<input type="hidden" name="czybh" id="czybh"  value="${czybh}"  />
						<ul class="ul-form">
							<div class="validate-group row" >
							<li>
								<label>活动编号</label>
								<input type="text" id="actNo" name="actNo" style="width:160px;" value="${hdmpgl.actNo }" readonly="readonly"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="status" dictCode="TICKETSTATUS" value="${hdmpgl.status }" disabled="true"></house:xtdm>                     
							</li>
							</div>
							<div class="validate-group row" >
							<li>
								<label>门票号</label>
								<input type="text" id="ticketNo" name="ticketNo" style="width:160px;" value="${hdmpgl.ticketNo }" readonly="true"/>
							</li>
							<li > 
								<label>发放时间</label>
								<input type="text" id="provideDate" name="provideDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${hdmpgl.provideDate}' pattern='yyyy-MM-dd hh:MM:ss'/>" disabled="true"/>
							</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
								<label><span class="required">*</span>业主姓名</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${hdmpgl.descr }"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${hdmpgl.address }"/>
							</li>
							</div>
							<div class="validate-group row" >
							<li class="form-validate">
								<label> 业主电话</label>
								<input type="text" id="phone" name="phone" style="width:160px;" value="${hdmpgl.phone }"/>
							</li>
							<li > 
								<label>签到时间</label>
								<input type="text" id="signDate" name="signDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${hdmpgl.signDate}' pattern='yyyy-MM-dd hh:MM:ss'/>" disabled="true"/>
							</li>	
							</div>
							<div class="validate-group row" >
							<li>
								<label>发放类型</label>
 								<select id="provideType" name="provideType" onchange="changeType()" style="width: 160px;"  disabled="true">
 									<option value="1">1 有家发放</option>
 									<option value="2">2 供应商发放</option>
 									</select>
							</li>
							<li> 
								<label>发放操作员</label>
								<input type="text" id="provideCZY" name="provideCZY" style="width:160px;" value="${hdmpgl.provideCZY }"/>
							</li>
							</div>
							<div class="validate-group row" >
								<li >
									<label>业务员</label>
									<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${hdmpgl.businessMan }"/>
								</li>
								<li >
									<label>业务员姓名</label>
									<input type="text" id="busiManName" name="busiManName" style="width:160px;" value="${hdmpgl.busiManName }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li > 
									<label>设计师</label>
									<input type="text" id="designMan" name="designMan" style="width:160px;" value="${hdmpgl.designMan }"/>
								</li>
								<li > 
									<label>业务员电话</label>
									<input type="text" id="busiManPhone" name="busiManPhone" style="width:160px;" value="${hdmpgl.busiManPhone }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li >
									<label>购买品类意向</label>
									<house:xtdmMulit id="planSupplType" dictCode="ACTSPLTYPE" selectedValue="${hdmpgl.planSupplType}"></house:xtdmMulit>                     
								</li>
							</div>
						</ul>	
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
