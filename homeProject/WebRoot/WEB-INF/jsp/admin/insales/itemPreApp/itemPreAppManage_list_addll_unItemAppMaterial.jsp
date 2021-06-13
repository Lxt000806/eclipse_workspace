<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
	UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	String costRight = uc.getCostRight();
%>
<!DOCTYPE html>
<html>
<head>
	<title>新增领料 未领材料页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
		function goto_query(){
			$("#dataTable").jqGrid("setGridParam", {
				url:"${ctx}/admin/itemPreApp/goJqGirdUnItemAppMaterial",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		$(function(){
			$("#whCode").openComponent_wareHouse({
				showValue: "${data.whCode}",
				showLabel: "${data.whCodeDescr}"
			});
			$("#supplCode").openComponent_supplier({
				showValue: "${data.supplCode}",
				showLabel: "${data.supplCodeDescr}"
			});
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
			Global.LinkSelect.setSelect({
				firstSelect:"itemType1",
				firstValue:"${data.itemType1}",
				secondSelect:"itemType2",
				secondValue:"${data.itemType2}"
			});
			$("#itemType1").attr("disabled", true);							
			Global.JqGrid.initJqGrid("dataTable", {
				height:330,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/itemPreApp/goJqGirdUnItemAppMaterial",
				postData:{
					custCode:$("#custCode").val(),
					itemType1: $("#itemType1").val(),
					itemType2: $("#itemType2").val(),
					whCode: $("#whCode").val(),
					supplCode: $("#supplCode").val()
				},
				colModel:[
					{name:"fixareadescr", index:"fixareadescr", width:70, label:"区域", sortable:true, align:"left"},
					{name:"itemdescr", index:"itemdescr", width:284, label:"材料名称", sortable:true, align:"left"},
					{name:"qty", index:"qty", width:50, label:"数量", sortable:true, align:"right"},
					{name:"uomdescr", index:"uomdescr", width:60, label:"单位", sortable:true, align:"left"},
					{name:"remark", index:"remark", width:173, label:"备注", sortable:true, align:"left"}
				]
			});
   			
		});
	</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
	   				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="custCode" name="custCode" value="${data.custCode}"/>
				<ul class="ul-form">
					<li>
						<label>仓库</label>
						<input type="text" id="whCode" name="whCode"/>
					</li>
					<li>
						<label>供应商</label>
						<input type="text" id="supplCode" name="supplCode"/>
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>材料类型2</label>
						<select id="itemType2" name="itemType2"></select>
					</li>					
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query()"  >查询</button>
					</li>
				</ul>
			</form>
		</div>
	  	
		<div class="clear_float"></div>
		
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


