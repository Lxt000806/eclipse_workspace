<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>解单干系人</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var canAddJcSpecMan = false, canAddCgSpecMan = false;

//校验函数
$(function() {
	if('${map.jcSpecMan}'.trim())
	 	$("#jcSpecMan").openComponent_employee({showValue:"${map.jcSpecMan}",showLabel:"${map.jcSpecManDescr}",readonly:true});
	else  $("#jcSpecMan").openComponent_employee({showValue:"${map.jcSpecMan}",showLabel:"${map.jcSpecManDescr}",callBack:function(data){
	 		canAddJcSpecMan = true;
 	}});
	if('${map.cgSpecMan}'.trim())
		 $("#cgSpecMan").openComponent_employee({showValue:"${map.cgSpecMan}",showLabel:"${map.cgSpecManDescr}",readonly:true});
   	else  $("#cgSpecMan").openComponent_employee({showValue:"${map.cgSpecMan}",showLabel:"${map.cgSpecManDescr}",callBack:function(data){
   			canAddCgSpecMan = true;
   	}});
   	if(!'${map.jcSpecMan}'.trim()||!'${map.cgSpecMan}'.trim())	$("#saveBtn").removeAttr("disabled");
});
function save() {
	if (canAddJcSpecMan || canAddCgSpecMan) {
	
		var datas = $("#dataForm").jsonForm();
		if(!canAddJcSpecMan) datas.jcSpecMan = '';
		if(!canAddCgSpecMan) datas.cgSpecMan = '';
		
	  	$.ajax({
			url:'${ctx}/admin/itemPlan/doUpdateJCCustStakeholder',
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
	}else{
		art.dialog({
				content: "请选择相关干系人！"
		});
	}	
}

</script>

</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBtn" type="button" class="btn btn-system" disabled="disabled" onclick="save()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" id="code" name="code" value="${customer.code}" />
					<input type="hidden" id="module" name="module" value="specItemReq" />
					
					<ul class="ul-form">
						<li>
							<label>客户编号</label> 
							<input type="text" id="custCode" name="custCode" value="${customer.code}"
								disabled="disabled" />
						</li>
						<li>
							<label>客户名称</label> 
							<input type="text" id="descr" name="descr" value="${customer.descr}"
								disabled="disabled" />
						</li>
						<li>
							<label>楼盘</label> 
							<input type="text" id="address" name="address" value="${customer.address }"
								disabled="disabled" />
						</li>
						<li>
							<label>集成解单员</label> 
							<input type="text" id="jcSpecMan" name="jcSpecMan" />
						</li>
						<li>
							<label>橱柜解单员</label> 
							<input type="text" id="cgSpecMan" name="cgSpecMan" />
						</li>
						<li class="form-validate"><label>衣柜设计完成日期</label> 
							<input type="text" id="intDesignDate" name="intDesignDate" class="i-date" style="width:160px;"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${map.intDesignDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li class="form-validate"><label>橱柜设计完成日期</label> 
							<input type="text" id="cupDesignDate" name="cupDesignDate" class="i-date" style="width:160px;"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${map.cupDesignDate}' pattern='yyyy-MM-dd'/>" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
