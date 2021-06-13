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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>付款规则管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/payRule/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/payRule/goJqGrid",
			//postData:{status:"*"},
            styleUI: 'Bootstrap',   
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  	{name: "No", index: "No", width: 85, label: "编号", sortable: true, align: "left"},
			  	{name: "custtypedescr", index: "custtypedescr", width: 85, label: "客户类型", sortable: true, align: "left"},
			  	{name: "paytypedescr", index: "paytypedescr", width: 95, label: "付款方式", sortable: true, align: "left"},
			  	{name: "contractfeereptypedescr", index: "contractfeereptypedescr", width: 140, label: "合同造价表类型", sortable: true, align: "left"},
			  	{name: "designfeedescr", index: "designfeedescr", width: 95, label: "设计费类型", sortable: true, align: "left"},
			  	{name: "LastUpdate", index: "LastUpdate", width: 120, label: "最后修改时间", sortable: true, align: "left",formatter:formatTime},
			  	{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 85, label: "最后修改人", sortable: true, align: "left"},
			  	{name: "Expired", index: "Expired", width: 85, label: "是否过期", sortable: true, align: "left"},
			  	{name: "ActionLog", index: "ActionLog", width: 85, label: "操作标志", sortable: true, align: "left"},
            ]
		});
		
		$("#save").on("click",function(){
			Global.Dialog.showDialog("Save",{
				title:"付款规则——增加",
				url:"${ctx}/admin/payRule/goSave",
				//postData:{deliveType:'1',purType:''},
				postData:{m_umState:"A"},
				height:700,
				width:1000,
				returnFun:goto_query
			});
		});
		
		$("#update").on("click",function(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"付款规则——编辑",
				url:"${ctx}/admin/payRule/goUpdate",
				postData:{no:ret.No},
				height:700,
				width:1000,
				returnFun:goto_query
			});
		});
		
		$("#copy").on("click",function(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"付款规则——复制",
				url:"${ctx}/admin/payRule/goSave",
				postData:{no:ret.No},
				height:700,
				width:1000,
				returnFun:goto_query
			});
		});
		
		$("#view").on("click",function(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"付款规则——查看",
				url:"${ctx}/admin/payRule/goView",
				postData:{no:ret.No},
				height:700,
				width:1000,
				returnFun:goto_query
			});
		}); 
	});
	</script>
</head>
<body>
	<div class="body-box-list">
        <div class="query-form">  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired"  name="expired"/>
				<ul class="ul-form">
					<li>
						<label>规则编号</label>
						<input type="text" id="no" name="no"/>
					</li>
					<li class="form-validate">
						<label>客户类型</label>
						<house:custType id="custType" ></house:custType>
					</li>
					
					<li class="search-group-shrink">			
						<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>   
					    <P>隐藏过期记录</p>														
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel">
		    	<div class="btn-group-sm">
					<house:authorize authCode="PAYRULE_ADD">
						<button type="button" class="btn btn-system" id="save">新增</button>
					</house:authorize>
					<house:authorize authCode="PAYRULE_ADD">
						<button type="button" class="btn btn-system" id="copy">复制</button>
					</house:authorize>
					<house:authorize authCode="PAYRULE_UPDATE">
						<button type="button" class="btn btn-system" id="update">编辑</button>
					</house:authorize>
					<house:authorize authCode="PAYRULE_VIEW">
						<button type="button" class="btn btn-system" id="view">查看</button>
					</house:authorize>
					<house:authorize authCode="PAYRULE_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()">输出到excel</button>
					</house:authorize>
		    	</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


