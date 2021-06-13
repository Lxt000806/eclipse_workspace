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
		<div class="body-box-list">  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="m_umState" value="${wareHouse.m_umState}"/>
					<input type="hidden" name="code" value="${wareHouse.code}"/>
					<input type="hidden" id="haveSelect" name="haveSelect" value="${wareHouse.haveSelect}"><!-- 已经选中的数据 -->
					<input type="hidden" id="haveFromKwPk" name="haveFromKwPk" value="${wareHouse.haveFromKwPk}">
					<ul class="ul-form">
						<div class="row">
							<li>
								<label for="itCode">材料编号</label>
								<input type="text" id="itCode" name="itCode" style="width: 160px;"/>
							</li>
							<li>
								<label for="itemDescr">材料名称</label>
								<input type="text" id="itemDescr" name="itemDescr" style="width: 160px;"/>
							</li>
							<li>
								<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
								<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
</body>	
<script>
// 全局保存选择的数据
var selectRows = [];
var dialog_id;
$(function() {
	$("#itCode").openComponent_item();

	var postData = $("#page_form").jsonForm();
	
	Global.JqGrid.initJqGrid("dataTable", {
		multiselect : true,/* 多选 */
		url: "${ctx}/admin/whPosiBal/goItemJqGrid",
		postData:postData ,
		height: $(document).height() - $("#content-list").offset().top - 45,
		rowNum : 10000000,
		styleUI: "Bootstrap", 
		colModel: [
			{name: "itcode", index: "itcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 240, label: "材料名称", sortable: true, align: "left"},
			{name: "qtycal", index: "qtycal", width: 80, label: "数量", sortable: true, align: "right"},
			{name: "fromkw", index: "fromkw", width: 80, label: "库位编号", sortable: true, align: "left", hidden:true},
			{name: "fromkwdescr", index: "fromkwdescr", width: 80, label: "库位名称", sortable: true, align: "left", hidden:true},
		]
	});

	if ("R" != "${wareHouse.m_umState}") {
		dialog_id="dialog_whPosiItemSave";
		jQuery("#dataTable").setGridParam().showCol("fromkwdescr").trigger("reloadGrid");
	} else {
		dialog_id="dialog_notUpItemSave";
	}

	//保存	
	$("#saveBtn").on("click",function(){
     	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
     	if(ids.length==0){
     		Global.Dialog.infoDialog("请选择材料");	
     		return;
     	}
 		$.each(ids,function(k,id){
 			var row = $("#dataTable").jqGrid("getRowData", id);
 			// 不显示已选择的值
 			$("#haveSelect").val($("#haveSelect").val()+","+row.itcode);
 			$("#haveFromKwPk").val($("#haveFromKwPk").val()+","+row.fromkw);
 			selectRows.push(row);
 		});
 		goto_query();
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

	var titleCloseEle = parent.$("div[aria-describedby="+dialog_id+"]").children(".ui-dialog-titlebar");
	$(titleCloseEle[0]).children("button").remove();

	var childBtn=$(titleCloseEle).children("button");
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
		+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
	$(titleCloseEle[0]).children("button").on("click", doClose);
});
function doClose(){
	Global.Dialog.returnData = selectRows;
	closeWin();
}
</script>
</html>
