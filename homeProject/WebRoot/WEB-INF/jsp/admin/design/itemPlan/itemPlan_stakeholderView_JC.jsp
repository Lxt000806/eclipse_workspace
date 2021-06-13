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
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var canInsertJCMan=false;
var canInsertCGDesignCode=false;
var canInsertJCZYDesignCode=false;
//校验函数
$(function() {
	if('${customer.jcMan}'.trim())
	 	$("#jcMan").openComponent_employee({showValue:"${customer.jcMan}",showLabel:"${customer.jcManName}",readonly:true});
	else  $("#jcMan").openComponent_employee({showValue:"${customer.jcMan}",showLabel:"${customer.jcManName}",callBack:function(data){
	 		canInsertJCMan=true;
 	}});
	if('${customer.CGDesignCode}'.trim())
		 $("#CGDesignCode").openComponent_employee({showValue:"${customer.CGDesignCode}",showLabel:"${customer.CGDesignerDescr}",readonly:true});
   	else  $("#CGDesignCode").openComponent_employee({showValue:"${customer.CGDesignCode}",showLabel:"${customer.CGDesignerDescr}",callBack:function(data){
   			canInsertCGDesignCode=true;
   	
   	}});
   	if('${customer.JCZYDesignCode}'.trim())
		 $("#JCZYDesignCode").openComponent_employee({showValue:"${customer.JCZYDesignCode}",showLabel:"${customer.JCZYDesignerDescr}",readonly:true});
   	else  $("#JCZYDesignCode").openComponent_employee({showValue:"${customer.JCZYDesignCode}",showLabel:"${customer.JCZYDesignerDescr}",callBack:function(data){
   			canInsertJCZYDesignCode=true;
   	
   	}});
   	if(!'${customer.jcMan}'.trim()||!'${customer.CGDesignCode}'.trim()||!'${customer.JCZYDesignCode}'.trim())	$("#saveBtn").removeAttr("disabled");
});
function save(){
	if(canInsertJCMan||canInsertCGDesignCode||canInsertJCZYDesignCode){
		var datas = $("#dataForm").jsonForm();
		if(!canInsertJCMan) datas.jcMan='';
		if(!canInsertCGDesignCode) datas.CGDesignCode='';
		if(!canInsertJCZYDesignCode) datas.JCZYDesignCode='';
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
						<li><label>集成设计师</label> <input type="text" id="jcMan" name="jcMan" />
						</li>
						<li><label>橱柜设计师</label> <input type="text" id="CGDesignCode" name="CGDesignCode" />
						</li>
						<li><label>集成专员</label> <input type="text" id="JCZYDesignCode" name="JCZYDesignCode" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
