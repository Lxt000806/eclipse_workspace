<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
    <title>项目经理提成领取--新增</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
var selectRows = [];
$(function(){
    //初始化表格
    Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProvide/goJqGrid_toPrjProDetail",
		rowNum:10000000,
		postData:{
			allDetailInfo:$("#allDetailInfo").val(),provideRemark:$('#provideRemark').val()
		},
		height:$(document).height()-$("#content-list").offset().top-110,
		multiselect:true,
        styleUI: 'Bootstrap',
        sortable: true,
		colModel :  [
			{name: "custcode", index: "custcode", width: 60, label: "编号", sortable: true, align: "left",hidden:true},
			{name: "ischecked", index: "ischecked", width: 80, label: "是否结帐", sortable: true, align: "left",hidden:true},
			{name: "projectman", index: "projectman", width: 80, label: "项目经理", sortable: true, align: "left"},
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
			{name: "zjfee", index: "zjfee", width: 70, label: "直接费", sortable: true, align: "right", sum: true},
			{name: "basectrlamt", index: "basectrlamt", width: 60, label: "发包", sortable: true, align: "right", sum: true},
			{name: "maincoopfee", index: "maincoopfee", width: 90, label: "主材配合费", sortable: true, align: "right", sum: true},
			{name: "withhold", index: "withhold", width: 60, label: "预扣", sortable: true, align:"right", sum: true},
			{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right", sum: true},
			{name: "recvfee", index: "recvfee", width: 60, label: "已领", sortable: true, align: "right", sum: true},
			{name: "recvfeefixduty", index: "recvfeefixduty", width: 70, label: "已领(定责)", sortable: true, align: "right", sum: true},
			{name: "qualityfee", index: "qualityfee", width: 70, label: "质保金", sortable: true, align: "right", sum: true},
			{name: "accidentfee", index: "accidentfee", width: 80, label: "扣意外险", sortable: true, align: "right", sum: true},
			{name: "mustamount", index: "mustamount", width: 80, label: "应发金额", sortable: true, align: "right", sum: true},
			{name: "realamount", index: "realamount", width: 80, label: "实发金额", sortable: true, align: "right", sum: true},
			{name: "provideremark", index: "provideremark", width: 100, label: "发放备注", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 80, label: "custcode", sortable: true, align: "left", hidden: true},
			{name: "jsye", index: "jsye", width: 100, label: "项目经理结算余额", sortable: true, align:"right", sum: true},
			{name: "ysper", index: "ysper", width: 100, label: "占预算百分比", sortable: true, align: "left"},
			{name: "fbper", index: "fbper", width: 100, label: "占发包百分比", sortable: true, align: "left"}
		]
	});
    //全选
    $("#selAll").on("click",function(){
    	Global.JqGrid.jqGridSelectAll("dataTable",true);
     });
    //全不选
    $("#selNone").on("click",function(){
        Global.JqGrid.jqGridSelectAll("dataTable",false);
    });
    //保存    
    $("#saveBtn").on("click",function(){
      	var allDetailInfo=$("#allDetailInfo").val();
   		var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
   		if(ids.length==0){
   		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
   		return;
   		}
		$.each(ids,function(k,id){
			var row = $("#dataTable").jqGrid('getRowData', id);
			selectRows.push(row);
			if(allDetailInfo != ""){
					allDetailInfo += ",";
				}
			allDetailInfo += row.custcode;
		});
		$("#allDetailInfo").val(allDetailInfo);
	      	 goto_query();
	});
	//改写窗口右上角关闭按钮事件
	var titleCloseEle = parent.$("div[aria-describedby=dialog_savePrj]").children(".ui-dialog-titlebar");
	console.log($(titleCloseEle).attr("id")+"dodod");
	$(titleCloseEle[0]).css('background-color', 'red');
	$(titleCloseEle[0]).children("button").remove();
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
								+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
    $(titleCloseEle[0]).children("button").on("click", saveClose); 
});
function saveClose(){
	Global.Dialog.returnData = selectRows;
	closeWin();
}
//引入结算备注
function addRemarks(obj){
	if ($(obj).is(':checked')) {
		$('#provideRemark').val('1');
		goto_query();
	} else {
		$('#provideRemark').val('0');
		goto_query();
	}
}
</script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="saveClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action=""
					method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" /> 
					<input type="hidden" id="allDetailInfo" name="allDetailInfo" value="${prjCheck.allDetailInfo}">	
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address"/>
						</li>
						<li class="search-group-shrink">
						    <input type="checkbox"  id="provideRemark" name="provideRemark" onclick="addRemarks(this)"/>   
							<P>引入结算备注</p>
							<button type="summit" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
					</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
</body>
</html>
