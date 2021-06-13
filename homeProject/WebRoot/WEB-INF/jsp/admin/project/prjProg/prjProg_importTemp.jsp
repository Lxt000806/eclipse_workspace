<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>从模板导入</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("addDetailDataTable",{
				url:"${ctx}/admin/gcjdmb/goJqGridProgTempDt",
				postData:{
					no:$("#no").val(),
					prjItems:$("#prjItems").val(),
					custCode:"${custCode}"
				},
				height:400,
	        	styleUI: "Bootstrap",
				multiselect: true,
				rowNum:100000,  
    			pager :'1',
				colModel : [
					{name: "consday", index: "consday", width: 130, label: "施工天数", sortable: true, align: "left",hidden:true},
					{name: "prjitem", index: "prjitem", width: 130, label: "施工项目", sortable: true, align: "left",hidden:true},
					{name: "note", index: "note", width: 130, label: "施工项目", sortable: true, align: "left"},
					{name: "planbegin", index: "planbegin", width: 140, label: "计划开始日（第N日）", sortable: true, align: "right"},
					{name: "planend", index: "planend", width: 80, label: "计划结束日", sortable: true, align: "right"},
					{name: "spaceday", index: "spaceday", width: 70, label: "间隔天数", sortable: true, align: "right"},
					{name: "lastupdate", index: "lastupdate", width: 85, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "操作人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作代码", sortable: true, align: "left"},
					{name: "prjitem", index: "prjitem", width: 80, label: "prjitem", sortable: true, align: "left", hidden:true},
					{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left", hidden:true},
					{name: "tempno", index: "tempno", width: 80, label: "tempno", sortable: true, align: "left", hidden:true}
	            ],
			});
			//全选
			$("#selAll").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",true);
			});
			//全不选
			$("#selNone").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",false);
			});
		});
		function save(){
			var ids=$("#addDetailDataTable").jqGrid("getGridParam", "selarrrow");
			var prjItems=$("#prjItems").val();
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			$.each(ids,function(i,id){
				var rowData = $("#addDetailDataTable").jqGrid("getRowData", id);
				selectRows.push(rowData);
				if(prjItems != ""){
					prjItems += ",";
				}
				prjItems += rowData.prjitem;
			}); 
			$("#prjItems").val(prjItems);
    		art.dialog({content: "添加成功！",width: 200,time:800});
    		$("#addDetailDataTable").jqGrid("setGridParam",{
	    		postData:{prjItems:prjItems,no:$("#no").val()},
	    		page:1,
	    		sortname:''
    		}).trigger("reloadGrid");
		}
		function closeAndReturn(){
			Global.Dialog.returnData = selectRows;
	    	closeWin();
		}
		function tableSelected(flag){
			selectAll("addDetailDataTable", flag);
		}
		
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
	 		<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="selAll" type="button" class="btn btn-system" >全选</button>
	    				<button id="selNone" type="button" class="btn btn-system" >全不选</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeAndReturn()">关闭</button>
					</div>
					<input hidden="true" id="dataTableExists_selectRowAll"
							name="dataTableExists_selectRowAll" value="">
					<input hidden="true" id="prjItems"
							name="prjItems" value="${prjItems}">
					<input hidden="true" id="no"
					name="no" value="${tempNo}">			
				</div>
			</div> 
			<table id="addDetailDataTable"></table>
			<div id="addDetailDataTablePager"></div>
		</div>
	</body>
</html>
