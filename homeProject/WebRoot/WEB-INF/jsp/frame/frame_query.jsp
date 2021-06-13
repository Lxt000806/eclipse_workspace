<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/frame/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-80,
			styleUI: 'Bootstrap',
			rowNum: 10000,
			isSelectOne: false,
			colModel : [
				{name : 'number',index : 'number',width : 100,label:'编号',sortable : true,align : "left",hidden: true},
				{name : 'infotitle',index : 'infotitle',width : 500,label:'主题',sortable : true,align : "left", formatter: formatTitleViewBtn},
				{name : 'department1descr',index : 'department1descr',width : 100,label:'发布部门',sortable : true,align : "left"},
				{name : 'sendczydescr',index : 'sendczydescr',width : 95,label:'发布人',sortable : true,align : "left"},
				{name : 'confirmdate',index : 'confirmdate',width : 120,label:'发布日期',sortable : true,align : "left",formatter:formatTime}
			],
			ondblClickRow:function(rowid,iRow,iCol,e){
				goTitleView(rowid);
			}
		});
		onCollapse(0,'collapse_main');
	});

	function formatTitleViewBtn(v,x,r){
		return "<a href='#' onclick='goTitleView("+x.rowId+");'>"+v+"</a>";
	}
	
	function goTitleView(rowid){
		var ret = $("#dataTable").jqGrid("getRowData", rowid);
		if (ret) {
			Global.Dialog.showDialog("information_detail",{
				title:"查看消息",
				url:"${ctx}/admin/information/goDetail?id="+ret.number,
				height:600,
				width:1000,
				returnFun: goto_query
			});
		} else {
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}   
	</script>
</head>
<body>
	<div class="body-box-form" >
		<div class="panel panel-system">
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
		      </div>
		    </div>
		</div>
		<div class="panel panel-info">  
	        <div class="panel-body">
	            <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<ul class="ul-form">
						<li class="col-sm-4">
							<label>主题</label>
							<input type="text" id="infoTitle" name="infoTitle" style="width:160px;"  value="${information.infoTitle }" />
						</li>
						<li class="col-sm-4">
							<label>内容</label>
							<input type="text" id="infoText" name="infoText" style="width:160px;"  value="${information.infoText }" />
						</li>
						<li class="col-sm-4">
							<label>发布人</label>
							<input type="text" id="sendCzy" name="sendCzy" style="width:160px;" value="${information.sendCzy }" />
						</li>
						<li class="col-sm-4">
							<label>发布日期从</label>
							<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${information.dateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li class="col-sm-4">
							<label>到</label>
							<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${information.dateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li class="col-sm-4">
							<label>发布部门</label>
							<house:department1 id="department1" value="${information.department1 }"></house:department1>
						</li>
						<li class="col-sm-4">
							<label>读取状态</label>
							<house:xtdm id="readStatus" dictCode="INFOREADSTATUS" value="${information.readStatus }"></house:xtdm>
						</li>
						<li class="col-sm-4">
							<button class="btn btn-sm btn-link"></button>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id= "dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


