<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>员工信息同步</title>
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
			url:"${ctx}/admin/salaryEmp/goEmpSyncJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			sortable: true,
			multiselect: true,
			postData:{chgType:"1"},
			rowNum:100000000,
			colModel : [
				{name: "empcode", index: "empcode", width: 60, label: "工号", sortable: true, align: "left"},
				{name: "newempname", index: "newempname", width: 70, label: "姓名", sortable: true, align: "left"},
				{name: "newdepartment1descr", index: "newdepartment1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
				{name: "newdepartment2descr", index: "newdepartment2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
				{name: "newregulardate", index: "newregulardate", width: 80, label: "转正时间", sortable: true, align: "left", formatter: formatDate},
				{name: "newleavedate", index: "newleavedate", width: 80, label: "离职时间", sortable: true, align: "left", formatter: formatDate}, 
				{name: "newconsigncmpdescr", index: "newconsigncmpdescr", width: 70, label: "签约公司", sortable: true, align: "left"},
				{name: "newpositiondescr", index: "newpositiondescr", width: 70, label: "职位", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 70, label: "姓名", sortable: true, align: "left"},
				{name: "department1descr", index: "department1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
				{name: "regulardate", index: "regulardate", width: 80, label: "转正时间", sortable: true, align: "left", formatter: formatDate},
				{name: "leavedate", index: "leavedate", width: 80, label: "离职时间", sortable: true, align: "left", formatter: formatDate}, 
				{name: "consigncmpdescr", index: "consigncmpdescr", width: 70, label: "签约公司", sortable: true, align: "left"},
				{name: "positiondescr", index: "positiondescr", width: 70, label: "职位", sortable: true, align: "left"},
			],
			gridComplete:function(){
				var ids = $("#dataTable").jqGrid("getDataIDs");
				for(var i in ids){
					var rowData = $("#dataTable").jqGrid('getRowData', ids[i]);
					if(rowData.empname!=rowData.newempname){
						$("#dataTable tbody #"+ids[i]+" td[aria-describedby=dataTable_newempname]").css("background","#f1c9c9");
					}
					if(rowData.department1descr!=rowData.newdepartment1descr){
						$("#dataTable tbody #"+ids[i]+" td[aria-describedby=dataTable_newdepartment1descr]").css("background","#f1c9c9");
					}
					if(rowData.department2descr!=rowData.newdepartment2descr){
						$("#dataTable tbody #"+ids[i]+" td[aria-describedby=dataTable_newdepartment2descr]").css("background","#f1c9c9");
					}
					if(rowData.regulardate!=rowData.newregulardate){
						$("#dataTable tbody #"+ids[i]+" td[aria-describedby=dataTable_newregulardate]").css("background","#f1c9c9");
					}
					if(rowData.leavedate!=rowData.newleavedate){
						$("#dataTable tbody #"+ids[i]+" td[aria-describedby=dataTable_newleavedate]").css("background","#f1c9c9");
					} 
					if(rowData.consigncmpdescr!=rowData.newconsigncmpdescr){
						$("#dataTable tbody #"+ids[i]+" td[aria-describedby=dataTable_newconsigncmpdescr]").css("background","#f1c9c9");
					}
					if(rowData.positiondescr!=rowData.newpositiondescr){
						$("#dataTable tbody #"+ids[i]+" td[aria-describedby=dataTable_newpositiondescr]").css("background","#f1c9c9");
					}
				}
			},
		});
		
		$("#dataTable").jqGrid('setGroupHeaders', {
	  	useColSpanStyle: true, 
		groupHeaders:[
				{startColumnName: 'newempname', numberOfColumns: 7, titleText: "变动后"},
				{startColumnName: 'empname', numberOfColumns: 7, titleText: "变动前"},
		],
		}); 
	});
	
	function doEmpSync(){
			var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择要同步的员工！"
				});				
				return ;
			}
			var empCodes="";
			$.each(ids,function(i,id){
				var rowData = $("#dataTable").getRowData(id);
				if(empCodes!=""){
					empCodes+=",";
				}
				empCodes+=rowData.empcode;
			});
			$.ajax({
				url:"${ctx}/admin/salaryEmp/doEmpSync",
				type:"post",
				data:{empCodes:empCodes},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				},
				success:function(obj){
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				}
			}); 
		}
	</script>
</head>
<body>
	<div class="body-box-list">
			<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-sm btn-system" onclick="doEmpSync()" >批量同步处理</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<!-- <span style="color:red">注：如果变动前转正日期不为空，则不进行同步</span> -->
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>变更类型</label>
						<select id="chgType" name="chgType">
			                <option value="1">全部</option>
			                <option value="2">姓名变更</option>
			                <option value="3">转正</option>
			                <option value="4">离职</option> 
			                <option value="5">合同签约公司变更</option>
			                <option value="6">一级部门变更</option>
			                <option value="7">二级部门变更</option>
			                <option value="8">职位变更</option>
			            </select>
		            </li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
