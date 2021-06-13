<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>个人薪酬查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
<META HTTP-EQUIV="expires" CONTENT="0"/>
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}
		.SelectBG{
			color:red;
			background-color:white!important;
		}
	</style>
<script type="text/javascript">
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:50,
		colModel : [
			{name:"label", index:"label", width:150, label:"项目", sortable:true ,align:"center",},
			{name:"value", index:"value", width:200, label:"值", sortable:true ,align:"center",formatter:diyFormat},
		],
		gridComplete:function(){
			$("#1").find("td").addClass("SelectBG");
			$("#1").find("td").css("color","black");
		},
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBG");
		},
	}; 
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	function diyFormat(c, o, r){
		if ($.trim(c) == "null" || c == null){
			return "";
		}
		return c;
	}
	
	query();
});

function query(){
	$("#dataTable").jqGrid("clearGridData");
	
	var mon = $("#mon").val();
	$.ajax({
		url:"${ctx}/admin/personalCommiQuery/goJqGrid",
		type: "post",
		data: {mon: mon},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			if(obj.length>0){
				var showTabs = "";
				for(var key in obj[0]){
					if(obj[0][key] == 0 && key != "合计提成"){
						continue;
					}
					var map = {};
					map["label"] = key;
					map["value"] = obj[0][key];
					showTabs += key+",";
					$("#dataTable").addRowData(1,map,"last");
				}
				$("#showTabs").val(showTabs);
			}
		}
	});
}

function view() {
	var rows = $("#dataTable").jqGrid("getRowData");
	if(rows[4].value == 0){
		art.dialog({
			content: "本月无提成明细记录"
		});
		return;
	}
	Global.Dialog.showDialog("employeeCommiStatView",{
		title:"我的提成--查看明细",
		url:"${ctx}/admin/employeeCommiStat/goViewPersonCommiDtl", 
		height:620,
	    width:1100,
		postData:{
			beginMon:$("#mon").val(),
			endMon:$("#mon").val(),
			number:"${sessionScope.USER_CONTEXT_KEY.czybh}",
			showTabs:$("#showTabs").val()
		},
		returnFun: goto_query
	});
}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" name="showTabs" id = "showTabs" value=""/>
				<ul class="ul-form" style="padding-top: 15px">
					<li class="form-validate">
						<label>月份</label>
						<house:dict id="mon" dictCode="" sql=" select mon,mon descr from tCommiCycle where Status = '2' group by mon"  
							 sqlValueKey="mon" sqlLableKey="descr" value="${mon }" onchange="query();"></house:dict>
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system" onclick="query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
               	   <house:authorize authCode="PERSONALCOMMIQUERY_VIEW">
               	        <button id="btnView" type="button" class="btn btn-system " onclick="view()">查看明细</button>	
					</house:authorize>	
	                <house:authorize authCode="PERSONALCOMMIQUERY_EXCEL">
	                     <button type="button" class="btn btn-system " onclick="doExcelNow('我的提成')">导出excel</button>
					</house:authorize>
				</div>
		</div> 
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
			<div id="readding" style="position: absolute; left:45%;top: 50%;width: 80px; height: 30px; background: rgb(217,237,247);text-align: center;line-height: 30px;border: solid 1px rgb(240,240,240)" hidden="true">读取中...</div>
		</div>
	</div>
</body>
</html>
