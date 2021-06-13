<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Integer index = Integer.parseInt(request.getParameter("index"));
	String margin = (index % 2 == 0 ? "margin: 10px 0px 0px 1%" : "margin: 10px 1% 0px 1%"); 
%>
<div class="panel panel-system" style="<%=margin %>;">
	<div class="panel-body" >
		<div class="btn-group-xs">
			<strong>公司通知</strong>
			<div>
				<button type="button" class="btn btn-system "  onclick="goto_query_cmpNotice()" >
					<span>刷新</span>
				</button>
				<button class="btn btn-system" id="id_more" onclick="moreClickCmpNotice()">更多</button>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTable"></table>
			<div class="tableNull"><img src="${resourceRoot}/images/cmpNoticeNull.png"/></div>
		</div>
	</div> 
</div>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/frame/goJqGrid",
		height: 299,
		styleUI: 'Bootstrap',
		postData: {
			readStatus: "0"
		},
		rowNum: 10000,
		isSelectOne: false,
		colModel : [
			{name : 'number',index : 'number',width : 100,label:'编号',sortable : true,align : "left",hidden: true},
			{name : 'infotitle',index : 'infotitle',width : 290,label:'主题',sortable : true,align : "left", formatter: formatTitleViewBtn},
			{name : 'department1descr',index : 'department1descr',width : 80,label:'发布部门',sortable : true,align : "left"},
			{name : 'sendczydescr',index : 'sendczydescr',width : 65,label:'发布人',sortable : true,align : "left"},
			{name : 'confirmdate',index : 'confirmdate',width : 150,label:'发布日期',sortable : true,align : "left",formatter:formatTime}
		],
		ondblClickRow:function(rowid,iRow,iCol,e){
			goTitleView(rowid);
		},
		gridComplete: function() {
			tableNullTip("dataTable");
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
			returnFun: goto_query_cmpNotice
		});
	} else {
		art.dialog({
			content: "请选择一条记录"
		});
	}
}   

function moreClickCmpNotice(){
	Global.Dialog.showDialog("customerView",{
		title:"主页查询",
		url:"${ctx}/frame/goQuery",
		height:650,
		width:1000,
		returnFun: goto_query_cmpNotice
	});
}	

function goto_query_cmpNotice(retData){
	var tableId = "dataTable";
	tableNullTip(tableId, true);
	$("#"+tableId).jqGrid("setGridParam",{
		postData:{
			readStatus: "0"
		},
		page:1
	}).trigger("reloadGrid");
}
</script>
