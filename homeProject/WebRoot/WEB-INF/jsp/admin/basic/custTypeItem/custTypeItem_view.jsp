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
	<title>材料升级查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
function isExpired(obj){
	if ($(obj).is(":checked")){
		$("#expired").val("T");
	}else{
		$("#expired").val("F");
	}
}
$(function(){
	function getData(data){
		if(!data) return;
		$("#price").val(data.cost);
		if(data.projectcost==null){
			$("#projectCost").val(data.projectCost);
		}else{
			$("#projectCost").val(data.projectcost);
		}
	}
	
	$("#itemCode").openComponent_item({callBack:getData,showValue:"${custTypeItem.itemCode}",showLabel:"${item.descr}"});

	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/custTypeItem/doUpdate",
			type: "post",
			data: datas,
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
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		 });
	});
});
function validateRefresh(){
		$("#dataForm").data("bootstrapValidator")
		                   .updateStatus("openComponent_builder_builderCode", "NOT_VALIDATED",null)  
		                   .validateField("openComponent_builder_builderCode")
		                   .updateStatus("crtDate", "NOT_VALIDATED",null)  
		                   .validateField("crtDate")  ;
}

function validateRefresh_choice(){
	 $("#dataForm").data("bootstrapValidator")
                 .updateStatus("openComponent_builder_builderCode", "NOT_VALIDATED",null)  
                 .validateField("openComponent_builder_builderCode");                    
}

function fillData(ret){
	validateRefresh_choice();
}

</script>
</head>
<body>
	<div class="body-box-form">
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
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired" name="expired" value="${custTypeItem.expired}" />
					<input type="hidden" id="pk" name="pk" value="${custTypeItem.pk} "/>
					<ul class="ul-form">
						<div class="validate-group row" >
							<li class="form-validate">
								<label>材料编号</label>
								<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${custTypeItem.itemCode}" />
							</li>
							<li class="form-validate">
								<label>客户类型</label>
								<house:xtdm id="custType" dictCode="CUSTTYPE"  value="${custTypeItem.custType}" ></house:xtdm>
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>升级价</label>
								<input type="text" id="price" name="price" value="${custTypeItem.price} "/>
							</li>
							<li class="form-validate">
								<label>原材料单价</label>
								<input type="text" id="oldPrice" name="oldPrice" style="width:160px;" value="${custTypeItem.oldPrice}" readonly="true"/>
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>升级结算价</label>
								<input type="text" id="projectCost" name="projectCost" value="${custTypeItem.projectCost}" />
							</li>
							<li class="form-validate">
								<label>原项目经理结算价</label>
								<input type="text" id="oldProjectCost" name="oldProjectCost" style="width:160px;"value="${custTypeItem.oldProjectCost}" readonly="true"/>
							</li>
						</div>	
						<div class="validate-group row" >
							<li class="form-validate">
								<label>优惠额度计算方式</label>
								<house:xtdm id="discAmtCalcType" dictCode="GIFTDACALCTYPE" value="${custTypeItem.discAmtCalcType }"></house:xtdm>
							</li>
							<li class="form-validate">
								<label>适用装修区域</label>
								<house:xtdm id="fixAreaType" dictCode="SetItemFixArea" value="${custTypeItem.fixAreaType }"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row" >
							<li>
								<label class="control-textarea">描述</label>
								<textarea id="remark" name="remark" rows="2">${custTypeItem.remark }</textarea>
	 						</li>
	 						<li>
								<label class="control-textarea">原备注</label>
								<textarea id="oldRemars" name="oldRemars" rows="2">${custTypeItem.oldRemars}</textarea>
	 						</li>
 						</div>
 						<div class="validate-group row" >
	 						<li class="search-group">					
								<input type="checkbox"  id="expired_edit" name="expired_edit"
								 value="${custTypeItem.expired }" 
								 ${custTypeItem.expired=='F'?'':'checked' } /><p>过期</p>
							</li>
						</div>	
					</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
