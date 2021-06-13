<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>固定资产管理——计提折旧查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	$(function(){
		$("#department").openComponent_department();	
		
		var postData = $("#page_form").jsonForm();

		/**初始化表格*/
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/asset/getChgDetailBySql",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: "Bootstrap", 
			colModel : [
			  {name : "assetcode",index : "assetcode",width : 75,label:"资产编号",sortable : true,align : "left",count:true},
			  {name : "descr",index : "descr",width : 75,label:"资产名称",sortable : true,align : "left"},
			  {name : "model",index : "model",width : 125,label:"规格型号",sortable : true,align : "left",	},
			  {name : "typedescr",index : "typedescr",width : 75,label:"变动类型",sortable : true,align : "left",	},
			  {name : "originalvalue",index : "originalvalue",width : 75,label:"原值",sortable : true,align : "right",sum:true},
			  {name : "totaldepramount",index : "totaldepramount",width : 75,label:"累计折旧",sortable : true,align : "right",sum:true},
			  {name : "netvalue",index : "netvalue",width : 75,label:"净值",sortable : true,align : "right",sum:true},
			  {name : "date",index : "date",width : 95,label:"变动日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "begindate",index : "begindate",width : 95,label:"开始使用日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "chgamount",index : "chgamount",width : 75,label:"调整金额",sortable : true,align : "right",sum:true	},
			  {name : "befvalue",index : "befvalue",width : 95,label:"变动前内容",sortable : true,align : "left"},
			  {name : "aftvalue",index : "aftvalue",width : 95,label:"变动后内容",sortable : true,align : "left"},
			  {name : "remarks",index : "remarks",width : 175,label:"变动原因",sortable : true,align : "left"},
			  {name : "aftaddress",index : "aftaddress",width : 155,label:"新存放地址",sortable : true,align : "left"},
			  {name : "chgdescr",index : "chgdescr",width : 85,label:"操作人",sortable : true,align : "left"},
			],
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin(true)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>变动类型</label>
						<house:xtdm id="chgType" dictCode="ASSETCHGTYPE"></house:xtdm>
					</li>
					<li>
						<label>部门编号</label>
						<input type="text" id="department" name="department" style="width:160px;"/>
					</li>
					<li>
						<label>资产类别</label>
						<house:dict id="assetType" dictCode="" 
							sql="select Code,code+' '+descr Descr from tAssetType where expired = 'F' "  
							sqlValueKey="Code" sqlLableKey="Descr" >
						</house:dict>   
					</li>
					<li>
						<label>变动日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
							style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${asset.dateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" 
							style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${asset.dateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
