<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>施工任务执行分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function doExcel(){
		doExcelNow("施工任务执行分析", "dataTable");
	}
	
	function view(){
		var ret = selectDataTableRow("dataTable");
		if(ret){
        	Global.Dialog.showDialog("prjTaskExecAnalyView",{
        	  title:"施工任务执行分析--查看",
        	  url:"${ctx}/admin/prjTaskExecAnaly/goView",
        	  postData:{
        	  	dateFrom:$("#dateFrom").val(),
        	  	dateTo:$("#dateTo").val(),
        	  	rcvCZY:ret.rcvczy
        	  },
        	  height: 550,
        	  width: 1100,
        	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{
			postData:$("#page_form").jsonForm(),
			page:1,
			sortname:'',
		}).trigger("reloadGrid");
	}
	
	$(function(){
		var postData = $("#page_form").jsonForm();
		$.extend(postData, {
			department1:"${data.department1 }",
			department2:"${data.department2 }",
		});
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/prjTaskExecAnaly/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-120,
			colModel : [
				{name : 'rcvczy',index : 'rcvczy',width : 120,label:'任务执行人',sortable : true,align : "left",hidden:true},
				{name : 'brcvczy',index : 'brcvczy',width : 120,label:'任务执行人',sortable : true,align : "left"},
			  	{name : 'unexectask',index : 'unexectask',width : 120,label:'待执行任务数',sortable : true,align : "left", sum:true,sortorder: "asc"},
			  	{name : 'delaytask',index : 'delaytask',width : 120,label:'超时任务数',sortable : true,align : "left", sum:true},
            	{name : 'delaynotrriggertask',index : 'delaynotrriggertask',width : 120,label:'延期未触发数',sortable : true,align : "left", sum:true},
            ],
		});
	});
	
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');

		$("#department1").val('');
	    $("#department1"+"_NAME").val('');
	    $.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	    $("#department2").val('');
	    $("#department2"+"_NAME").val('');
	    $.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	    $.fn.zTree.init($("#tree_department2"), {}, []);
	}
</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" name="exportData" id="exportData">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
					<li>
						<label>一级部门</label>
						<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
						sqlLableKey="desc1" sqlValueKey="code" selectedValue="${data.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
					</li>
					<li>
						<label>二级部门</label>
						<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where department1 = '${data.department1 }' and Expired='F' " 
						sqlLableKey="desc1" sqlValueKey="code" selectedValue="${data.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
					</li>
					<li>		
						<label>触发时间从</label>
							<input type="text" id="dateFrom"
							name="dateFrom" class="i-date" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${data.dateFrom}' pattern='yyyy-MM-dd'/>" />								
						</label>
					</li>
					<li>
						<label>至</label>
							<input type="text" id="dateTo"
							name="dateTo" class="i-date" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${data.dateTo}' pattern='yyyy-MM-dd'/>" />								

						</label>
					</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>	
					</ul>		
				</form>
			</div>
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="PRJTASKEXECANALY_VIEW">
						<button type="button" id="btnview" class="btn btn-system"  onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="PRJTASKEXECANALY_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div> 
			<div id="content-list">
				<table id= "dataTable"></table>  
				<div id= "dataTablePager"></div>  
			</div>
		</div>
	</body>
</html>
