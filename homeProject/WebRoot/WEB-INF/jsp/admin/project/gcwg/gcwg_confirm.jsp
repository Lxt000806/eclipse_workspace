<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工程完工确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function save(){
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/gcwg/doFinishConfirm",
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
						closeFinish();
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
}

function changeEndCode(){
	if ($("#endCode").val()=="3"){
		$("#div_realDesignFee").hide();
		$("#realDesignFee").val("0");
	}else{
		$("#div_realDesignFee").show();
		$("#realDesignFee").val("0");
	}
}

function closeFinish(){
	var dialogId = "gcwgFinish";
	top.$("#dialog_"+dialogId).dialog("option","isCallBack",true);
	top.$("#dialog_"+dialogId).dialog("close");
}
</script>

</head>
    
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">确认完工</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">取消</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
        	<font color="red">软装和主材存在需求数量<>发货数量，详见列表：</font>
    	</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</div>
<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
	<house:token></house:token>
	<input type="hidden" id="code" name="code" value="${customer.code }" />
	<input type="hidden" id="status" name="status" value="${customer.status }" />
	<input type="hidden" id="endCode" name="endCode" value="${customer.endCode }" />
	<input type="hidden" id="endDate" name="endDate" value="<fmt:formatDate value='${customer.endDate }' pattern='yyyy-MM-dd'/>" />
	<input type="hidden" id="realDesignFee" name="realDesignFee" value="${customer.realDesignFee }" />
	<input type="hidden" id="endRemark" name="endRemark" value="${customer.endRemark }" />
</form>
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/gcwg/goFinishConfirmJqGrid",
		postData:{code:"${customer.code}"},
		rowNum:10000,
		height:$(document).height()-$("#content-list").offset().top-120,
		colModel : [
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 80, label: "材料编码", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 300, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "需求数量", sortable: true, align: "right", sum: true},
			{name: "sendqty", index: "sendqty", width: 80, label: "发货数量", sortable: true, align: "right", sum: true}
        ],
        gridComplete:function(){
        	var qty = $("#dataTable").getCol("qty",false,"sum");
        	var sendqty = $("#dataTable").getCol("sendqty",false,"sum");
            $("#dataTable").footerData("set", { "qty": myRound(qty,2) });
            $("#dataTable").footerData("set", { "sendqty": myRound(sendqty,2) });
        }
	});
});
</script>
</body>
</html>
