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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.row{
			margin: 0px;
		}
		.form-search .ul-form li label {
		    width: 60px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="selectAllBtn">
						<span>全选</span>
					</button>
					<button type="button" class="btn btn-system " id="selectNoneBtn">
						<span>不选</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBtn">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
	<script type="text/javascript">
		var selectRows = [];
		var m_umState = "${m_umState}";
		$(function() {
			$("#itCode").openComponent_item();
			Global.JqGrid.initJqGrid("dataTable", {
				multiselect : true,
				url: "${ctx}/admin/preItemAppSend/goSendDetailJqGrid",
				postData:{no: "${no}", pks: "${keys}"},
				height: $(document).height() - $("#content-list").offset().top - 40,
				rowNum : 10000000,
				styleUI: "Bootstrap", 
				colModel: [
					{name: "no", index: "no", width: 60, label: "批次号", sortable: true, align: "left", hidden: true},
					{name: "pk", index: "pk", width: 60, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "fixareadesc", index: "fixareadesc", width: 100, label: "区域", sortable: true, align: "left"},
					{name: "refpk", index: "refpk", width: 100, label: "领料明细编号", sortable: true, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
					{name: "sqlcode", index: "sqlcode", width: 100, label: "品牌编号", sortable: true, align: "left", hidden: true},
					{name: "sqldescr", index: "sqldescr", width: 70, label: "品牌", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 80, label: "领料数量", sortable: true, align: "right"},
					{name: "reqqty", index: "reqqty", width: 80, label: "需求数量", sortable: true, align: "right"},
					{name: "sendqtydescr", index: "sendqtydescr", width: 80, label: "发货数量", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"}
				]
			});
			//保存	
			$("#saveBtn").on("click",function(){
		     	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
		     	if(ids.length==0){
		     		Global.Dialog.infoDialog("请选择材料");	
		     		return;
		     	}
		     	$.each(ids,function(k,id){
		 			var row = $("#dataTable").jqGrid("getRowData", id);
		 			selectRows.push(row);
		 		});
		 		Global.Dialog.returnData = selectRows;
		 		closeWin();
		 	});
			//全选
			$("#selectAllBtn").on("click",function(){
				Global.JqGrid.jqGridSelectAll("dataTable",true);
			});
			//不选
			$("#selectNoneBtn").on("click",function(){
				Global.JqGrid.jqGridSelectAll("dataTable",false);
			});
			$("#closeBtn").on("click",function(){
				doClose();
			});
			// replaceCloseEvt("itemSave", doClose);//替换窗口右上角关闭事件
		});
		function doClose(){
			closeWin();
		}
	</script>
</body>	
</html>
