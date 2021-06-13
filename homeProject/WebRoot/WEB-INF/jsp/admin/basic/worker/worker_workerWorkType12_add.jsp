<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<table id="dataTable_detail"></table>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {

		var gridOption1 = {
			url : "${ctx}/admin/worker/goWorkType12JqGrid",
			postData : {
				workType12Strings : "${worker.workType12Strings}"
			},
			multiselect:true,
			sortable : true,
			height : 440,
			rowNum : 10000000,
			colModel : [ 
			{name : "code",index : "code",width : 120,label : "工种分类12编号",sortable : true,align : "left"}, 
			{name : "worktype12descr",index : "worktype12descr",width : 120,label : "工种分类12描述",sortable : true,align : "left"}, 
			{name : "lastupdate",index : "lastupdate",width : 120,label : "最后更新时间",sortable : true,align : "left",formatter : formatTime,hidden : true}, 
			{name : "lastupdatedby",index : "lastupdatedby",width : 101,label : "最后更新人员",sortable : true,align : "left",hidden : true}, 
			{name : "expired",index : "expired",width : 74,label : "是否过期",sortable : true,align : "left",hidden : true}, 
			{name : "actionlog",index : "actionlog",width : 76,label : "操作日志",sortable : true,align : "left",hidden : true} ],
		};
		Global.JqGrid.initJqGrid("dataTable_detail", gridOption1);
		
		$("#saveBtn").on("click", function() {
			var selectRows = [];
			var rowIds = $("#dataTable_detail").jqGrid('getGridParam', 'selarrrow');
			console.log(rowIds);
			$.each(rowIds , function(i,val){     
                  var rowData = $("#dataTable_detail").jqGrid('getRowData',val);
                  selectRows.push(rowData);
         	}); 
         	console.log(selectRows);
			Global.Dialog.returnData = selectRows;
			closeWin();
		});
	});

	
	
	
	
</script>
</html>
