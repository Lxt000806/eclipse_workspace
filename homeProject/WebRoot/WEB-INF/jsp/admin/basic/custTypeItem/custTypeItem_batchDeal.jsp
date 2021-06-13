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
		multiselect:true,
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
});

function save(){
	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
	var detailJson = Global.JqGrid.selectToJson("dataTable","PK");
	if(ids.length==0){
		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
		return;
	}
	art.dialog({
		content:"是否确定进行过期操作？",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/custTypeItem/doBatchDeal",
				type: "post",
				data: {pks: detailJson.fieldJson},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
			    				goto_query();
						    }
						});
			    	}else{
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			});
		},
		cancel: function () {
			return true;
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
						<button type="button" class="btn btn-system" id="saveBut" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
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
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
