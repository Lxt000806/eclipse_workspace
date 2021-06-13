<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>供货管理——生产进度登记</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="closeBtn">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">
			<div class="query-form">
			    <form class="form-search" id="page_form" action="" method="post" target="targetFrame">
			    	<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value=""/>
					<ul class="ul-form">
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"/>
						</li>
                        <li>
                            <label>包含已出货</label>
                            <input type="checkbox" name="includeShipped" value="0" onchange="toggleIncludeShipped(this)"/>
                        </li>
						<li class="search-group">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="intProduceAdd">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="intProduceEdit">
					<span>修改</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
	
	<script>
		$(function(){
			var postData = $("#page_form").jsonForm();
			var gridOption ={
				url:"${ctx}/admin/supplierItemApp/goIntProduceJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI:"Bootstrap", 
				colModel:[
					{name:"pk", index:"pk", width:140, label:"编号", sortable:true, align:"left", hidden:true},
					{name:"address", index:"address", width:140, label:"楼盘", sortable:true, align:"left"},
					{name:"iscupboard", index:"iscupboard", width:70, label:"是否橱柜", sortable:true, align:"left", hidden:true},
					{name:"iscupboarddescr", index:"iscupboarddescr", width:70, label:"是否橱柜", sortable:true, align:"left"},
					{name:"setboarddate", index:"setboarddate", width:120, label:"定板时间", sortable:true, align:"left", formatter:formatDate},
					{name:"arrboarddate", index:"arrboarddate", width:120, label:"到板时间", sortable:true, align:"left", formatter:formatDate},
					{name:"openmaterialdate", index:"openmaterialdate", width:120, label:"开料时间", sortable:true, align:"left", formatter:formatDate},
					{name:"sealingsidedate", index:"sealingsidedate", width:120, label:"封边时间", sortable:true, align:"left", formatter:formatDate},
					{name:"exholedate", index:"exholedate", width:120, label:"排孔时间", sortable:true, align:"left", formatter:formatDate},
					{name:"packdate", index:"packdate", width:120, label:"包装时间", sortable:true, align:"left", formatter:formatDate},
					{name:"inwhdate", index:"inwhdate", width:120, label:"入库时间", sortable:true, align:"left", formatter:formatDate},
					{name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime},
                    {name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left"},
                    {name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left"},
                    {name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"}
				],
				ondblClickRow: function(){
					edit();
				}
			};
			Global.JqGrid.initJqGrid("dataTable",gridOption);

			$("#intProduceAdd").on("click",function(){
				Global.Dialog.showDialog("intProduceAdd",{
					title:"生产进度登记——新增",
					url:"${ctx}/admin/supplierItemApp/goIntProduceAdd",
					postData:{m_umState: "A"},
					height:375,
					width:705,
					returnFun:goto_query
				});
			});
			
			$("#intProduceEdit").on("click", function () {
				edit();
			});

			$("#closeBtn").on("click", function () {
				closeWin(false);
			});
		});

		function edit(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
		  			content: "请选择一条记录",
		  		});
		  		return;
			}
			Global.Dialog.showDialog("view",{
				title:"生产进度登记——修改",
				url:"${ctx}/admin/supplierItemApp/goIntProduceEdit",
				postData:{
					m_umState: "M",
					pk: ret.pk
				},
				height:375,
				width:705,
				returnFun:goto_query
			});
		}
		
        function toggleIncludeShipped(obj) {
            obj.value = obj.checked ? "1" : "0";
        }

	</script>
</body>	
</html>
