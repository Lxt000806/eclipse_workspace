<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加干系人</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var canInsertMainPlanMan=false;
var canInsertMainBusinessMan=false;
var canInsertDeclareMan=false; 
//校验函数
$(function() {
	if('${customer.mainPlanMan}'.trim())
	 	$("#mainPlanMan").openComponent_employee({showValue:"${customer.mainPlanMan}",showLabel:"${customer.mainPlanManName}",readonly:true});
	else  $("#mainPlanMan").openComponent_employee({showValue:"${customer.mainPlanMan}",showLabel:"${customer.mainPlanManName}",callBack:function(data){
	 		canInsertMainPlanMan=true;
		}});
	if('${customer.mainBusinessMan}'.trim())
	 $("#mainBusinessMan").openComponent_employee({showValue:"${customer.mainBusinessMan}",showLabel:"${customer.mainBusinessManName}",readonly:true});
   	else  $("#mainBusinessMan").openComponent_employee({showValue:"${customer.mainBusinessMan}",showLabel:"${customer.mainBusinessManName}",readonly:true,callBack:function(data){
   			 canInsertMainBusinessMan=true; 
   	
   	}});
   	if('${customer.declareMan}'.trim())
	 $("#declareMan").openComponent_employee({showValue:"${customer.declareMan}",showLabel:"${customer.declareManName}",readonly:true}); 
	else  $("#declareMan").openComponent_employee({showValue:"${customer.declareMan}",showLabel:"${customer.declareManName}",callBack:function(data){
	 		canInsertDeclareMan=true;
	}});
   	if(!'${customer.mainPlanMan}'.trim()||!'${customer.mainBusinessMan}'.trim()||!'${customer.declareMan}'.trim())	$("#saveBtn").removeAttr("disabled");
});
function save(){
	if(canInsertMainPlanMan||canInsertMainBusinessMan||canInsertDeclareMan){
		var datas = $("#dataForm").jsonForm();
		if(!canInsertMainPlanMan) datas.mainPlanMan='';
		if(!canInsertMainBusinessMan) datas.mainBusinessMan='';
		if(!canInsertDeclareMan) datas.declareMan='';
  	$.ajax({
		url:'${ctx}/admin/itemPlan/doUpdateCustStakeholder',
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
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode" value="${customer.code}"
							disabled="disabled" />
						</li>
						<li><label>客户名称</label> <input type="text" id="descr" name="descr" value="${customer.descr}"
							disabled="disabled" />
						</li>
						<li><label>楼盘</label> <input type="text" id="address" name="address" value="${customer.address }"
							disabled="disabled" />
						</li>
						<li><label>主材预算员</label> <input type="text" id="mainPlanMan" name="mainPlanMan" />
						</li>
						<li><label style="color: gray;">主材管家</label> <input type="text" id="mainBusinessMan" name="mainBusinessMan" />
						</li>
						<li><label>主材报单员</label> <input type="text" id="declareMan" name="declareMan" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
