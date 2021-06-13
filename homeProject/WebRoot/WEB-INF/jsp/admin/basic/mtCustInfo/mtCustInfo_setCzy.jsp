<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>麦田客户管理——设置对接人</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/mtCustInfoAssign/goMtRegionJqGrid",
			postData:{faultType:'1'},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			multiselect: true,
			rowNum:100000,  
    		pager :'1',
			sortable: true,
			colModel : [
				{name: "regioncode", index: "regioncode", width: 88, label: "regioncode", sortable: true, align: "left",hidden:true},
				{name: "regiondescr", index: "regiondescr", width: 88, label: "麦田大区", sortable: true, align: "left"},
				{name: "region2descr", index: "region2descr", width: 149, label: "麦田区域", sortable: true, align: "left"},
				{name: "czy", index: "czy", width: 96, label: "对接人", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 80, label: "对接部门", sortable: true, align: "left"},
			],
		});
   		replaceCloseEvt("setCzy", closeWin);
	});
	function setCzy(){
		var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
		var codes="";
		if(ids.length == 0){
			art.dialog({
				content:"请选择设置区域！"
			});				
			return ;
		}
		$.each(ids,function(i,id){
			var rowData = $("#dataTable").jqGrid("getRowData", id);
			if(codes != ""){
				codes += ",";
			}
			codes += rowData.regioncode;
		}); 
		Global.Dialog.showDialog("goChooseCzy",{
			title:"麦田客户分配--选择对接人",
			url:"${ctx}/admin/mtCustInfoAssign/goChooseCzy",
			postData:{codes:codes},
			height: 300,
			width:400,
			returnFun: goto_query 
		});
	}
	</script>
</head>
<body>
	<div class="body-box-list">
				<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="setCzyBut"
						onclick="setCzy()">
						<span>设置对接人</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
