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
	<title>材料升级列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 

function doExcel(){
	var url = "${ctx}/admin/custTypeItem/doExcel";
	doExcelAll(url);
}
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");

	$("#itemCode").openComponent_item({condition:{isCustTypeItem:'1'}});
	$("#supplCode").openComponent_supplier();
	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custTypeItem/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: "Bootstrap", 
		colModel : [
			{name:"PK",	index:"PK",	width:75,	label:"编号",	sortable:true,align:"left" ,hidden:true},
			{name:"ItemCode",	index:"ItemCode",	width:75,	label:"材料编号",	sortable:true,align:"left" ,},
			{name:"itemdescr",	index:"itemdescr",	width:120,	label:"材料名称",	sortable:true,align:"left" ,},
			{name:"itemtype1descr",	index:"itemtype1descr",	width:75,	label:"材料类型1",	sortable:true,align:"left" ,},
			{name:"CustType",	index:"CustType",	width:75,	label:"客户类型",	sortable:true,align:"left" ,hidden:true},
			{name:"custtypedescr",	index:"custtypedescr",	width:75,	label:"客户类型",	sortable:true,align:"left" ,},
			{name:"Price",	index:"Price",	width:75,	label:"升级价",	sortable:true,align:"right" ,},
			{name:"ProjectCost",	index:"ProjectCost",	width:140,	label:"升级项目经理结算价",	sortable:true,align:"right" ,},
			{name:"discamtcalctypedescr", index: "discamtcalctypedescr" ,width: 120, label: "优惠额度计算方式", sortable: true, align: "left"},
			{name:"fixareatypedescr", index: "fixareatypedescr" ,width: 120, label: "适用装修区域", sortable: true, align: "left"},
			{name:"Remark",	index:"Remark",	width:125,	label:"描述",	sortable:true,align:"left" , },
			{name:"LastUpdate",	index:"LastUpdate",	width:105,	label:"最后修改时间",	sortable:true,align:"left" ,formatter: formatTime},
			{name:"LastUpdatedBy",	index:"LastUpdatedBy",	width:110,	label:"最后修改人员",	sortable:true,align:"left" ,},
			{name:"Expired",	index:"Expired",	width:75,	label:"是否过期",	sortable:true,align:"left" ,},
			
		],
	});
	//价格权限
	/* if("${costRight}"=="0"){
		jQuery("#dataTable").setGridParam().hideCol("Price").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("ProjectCost").trigger("reloadGrid");
	} */
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"套餐材料信息管理——新增",
			url:"${ctx}/admin/custTypeItem/goSave",
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#batchAddNoExcel").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"套餐材料信息管理——批量新增",
			url:"${ctx}/admin/custTypeItem/goBatchAddNoExcel",
			height:600,
			width:1100,
			returnFun:goto_query
		});
	});
	
	$("#batchAdd").on("click",function(){
		Global.Dialog.showDialog("batchAdd",{
			title:"套餐材料信息管理——批量导入",
			url:"${ctx}/admin/custTypeItem/goBatchAdd",
			height:600,
			width:1100,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("update",{
			title:"套餐材料信息管理——编辑",
			url:"${ctx}/admin/custTypeItem/goUpdate",
			postData:{id:ret.PK},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#copy").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("update",{
			title:"套餐材料信息管理——复制",
			url:"${ctx}/admin/custTypeItem/goCopy",
			postData:{id:ret.PK},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"套餐材料信息管理——查看",
			url:"${ctx}/admin/custTypeItem/goView",
			postData:{id:ret.PK},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#batchDeal").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("batchDeal",{
			title:"套餐材料信息管理——批量过期",
			url:"${ctx}/admin/custTypeItem/goBatchDeal",
			postData:{id:ret.PK},
			height:600,
			width:1100,
			returnFun:goto_query
		});
	});
	
	
});

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired"  name="expired" value="${custTypeItem.expired }"/>
					
					<input type="hidden" id="costRight" name="costRight"  value="${costRight }" />
					<ul class="ul-form">
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 160px;"  ></select>
						</li>
						<li>
							<label>供应商</label>
							<input type="text" id="supplCode" name="supplCode" style="width:160px;" />
						</li>
						<li>
							<label>客户类型</label>
							<house:DictMulitSelect id="custType" dictCode="" sql="select code ,desc1 descr from tcustType where expired='F'  " 
							sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
						</li>
						<li>
							<label>材料编号</label>
							<input type="text" id="itemCode" name="itemCode" style="width:160px;" />
						</li>
						<li class="search-group-shrink" >
							<input type="checkbox"  id="expired_show" name="expired_show"
									 value="${custTypeItem.expired }" onclick="hideExpired(this)" 
									 ${custTypeItem!='T'?'checked':'' } /><p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
   			  <div class="btn-group-sm"  >
				<house:authorize authCode="CUSTTYPEITEM_SAVE">
					<button type="button" class="btn btn-system " id="save"><span>新增</span> 
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEITEM_BATCHADDNOEXCEL">
					<button type="button" class="btn btn-system " id="batchAddNoExcel"><span>批量新增</span> 
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEITEM_COPY">
					<button type="button" class="btn btn-system " id="copy"><span>复制</span> 
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEITEM_BATCHADD">
					<button type="button" class="btn btn-system "  id="batchAdd"><span>批量导入</span> 
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEITEM_UPDATE">	
					<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEITEM_VIEW">	
					<button type="button" class="btn btn-system "  id="view"><span>查看</span> 
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEITEM_CUSTTYPEITEM_BATCHDEAL">	
					<button type="button" class="btn btn-system "  id="batchDeal"><span>批量过期</span> 
					</button>
				</house:authorize>
					<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
					<span>导出excel</span>
				</div>
			</div>
					<!-- panelBar End -->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
	</body>	
</html>
