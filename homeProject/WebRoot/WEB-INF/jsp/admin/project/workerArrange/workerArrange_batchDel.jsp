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
	<title>批量删除</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
    		<div class="panel-body" >
      			<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="delBtn">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<button type="button" class="btn btn-system " id="removeSelectedRow" onclick="removeSelectedRow()">
                        <span>移除选中行</span>
                    </button>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
		</div>
	</div>
</body>
	<script type="text/javascript">
		$(function() {
		
			Global.JqGrid.initJqGrid("dataTable",{
				url: "${ctx}/admin/workerArrange/goJqGridList",
				postData: {
				    pks: "${pks}",
				    expired: "${expired}"
				},
				styleUI: 'Bootstrap', 
				rowNum: 1000,
				colModel: [
					{name: "pk", index: "pk", width: 80, label: "编号", sortable: true, align: "left"},
					{name: "workername", index: "workername", width: 70, label: "工人", sortable: true, align: "left"},
					{name: "worktype12descr", index: "worktype12descr", width: 70, label: "工种", sortable: true, align: "left"},
					{name: "date", index: "date", width: 80, label: "日期", sortable: true, align: "left", formatter: formatDate},
					{name: "daytypedescr", index: "daytypedescr", width: 50, label: "班次", sortable: true, align: "left"},
					{name: "no", index: "no", width: 80, label: "预约编号", sortable: true, align: "left"},
					{name: "booked", index: "booked", width: 60, label: "已预约", sortable: true, align: "left", formatter: function(value) {return value === 1 ? '是' : '否';}},
				]
			})
			
			$("#delBtn").on("click", function() {
			
			     var data = $("#dataTable").getRowData()
			     if (data.length === 0) {
			         art.dialog({content: '没有需要删除的记录'})
			         return
			     }
			     
			     var allPk = ""
			     for (i = 0; i < data.length; i++) {
			         allPk += data[i].pk + ","
			     }
			
				 $.ajax({
					url: "${ctx}/admin/workerArrange/doBatchDel",
					type: 'post',
					data: {pks: allPk},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '删除数据出错'});
				    },
				    success: function(obj){
				    	if (obj.rs) {
				    		art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function() { closeWin(); }
							});
				    	} else {
				    		art.dialog({
								content: obj.msg,
							});
				    	}
				    }
				 }); 
			});
			
		})
		
		function removeSelectedRow() {
		    var rowid = $("#dataTable").getGridParam("selrow")
		    $("#dataTable").delRowData(rowid)
		}
	</script>
</html>
