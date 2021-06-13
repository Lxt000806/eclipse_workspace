<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>集成补货信息管理——查看同楼盘明细</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.row{
			margin: 0px;
		}
		.col-sm-3{
			padding: 0px;
		}
		.col-sm-6{
			padding: 0px;
		}
		.col-sm-9{
			padding: 0px;
		}
		.col-sm-12{
			padding: 0px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="container-fluid" > 
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div> 
		</div>
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/intReplenish/goHadJqGridByNo",
				postData:{no: "${no}"} ,
				height: $(document).height() - $("#content-list").offset().top - 36,
				styleUI: "Bootstrap", 
				rowNum: 100000,
				loadonce: true,
				colModel: [
					{name:"no",index:"no",width:80,label:"单号",sortable:true,align:"left"},
					{name:"address",index:"address",width:140,label:"楼盘",sortable:true,align:"left"},
					{name:"status",index:"status",width:60,label:"状态",sortable:true,align:"left", hidden:true},
					{name:"statusdescr",index:"statusdescr",width:60,label:"状态",sortable:true,align:"left"},
					{name:"iscupboard",index:"iscupboard",width:80,label:"是否橱柜",sortable:true,align:"left", hidden:true},
					{name:"iscupboarddescr",index:"iscupboarddescr",width:80,label:"是否橱柜",sortable:true,align:"left"},
					{name:"source",index:"source",width:80,label:"补货来源",sortable:true,align:"left", hidden:true},
					{name:"sourcedescr",index:"sourcedescr",width:80,label:"补货来源",sortable:true,align:"left",hidden:true},
					{name:"readydate",index:"readydate",width:100,label:"货齐时间",sortable:true,align:"left",formatter:formatDate,hidden:true},
					{name:"servicedate",index:"servicedate",width:100,label:"售中时间",sortable:true,align:"left",formatter:formatDate,hidden:true},
					{name:"finishdate",index:"finishdate",width:100,label:"完成时间",sortable:true,align:"left",formatter:formatDate,hidden:true},
					{name:"intspl", index:"intspl", width:80, label:"供应商", sortable:true, align:"left", hidden: true},
					{name:"intspldescr", index:"intspldescr", width:80, label:"供应商", sortable:true, align:"left"},
					{name:"type", index:"type", width:80, label:"补货类型", sortable:true, align:"left", hidden: true},
					{name:"typedescr", index:"typedescr", width:80, label:"补货类型", sortable:true, align:"left"},
					{name:"remarks", index:"remarks", width:160, label:"补件详情", sortable:true, align:"left"},
					{name:"date", index:"date", width:100, label:"补货时间", sortable:true, align:"left", formatter:formatDate},
					{name:"arrivedate", index:"arrivedate", width:100, label:"实际到货日期", sortable:true, align:"left", formatter:formatDate},
					{name:"arriveremarks", index:"arriveremarks", width:160, label:"到货备注", sortable:true, align:"left"},
					{name:"respartdescr", index:"respartdescr", width:80, label:"责任人", sortable:true, align:"left"},
				]
			});
		});
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
	</script>
</body>
</html>
