<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>材料应送货楼盘明细</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemShouldSend/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}				
/**初始化表格*/
$(function(){
	$("#supplierCode").openComponent_supplier({
		condition:{
		    itemRight:"${customer.itemRight}"
		}
	});
	$("#builderCode").openComponent_builder();
	$("#whCode").openComponent_wareHouse();
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	var postData=$("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-90,
		colModel : [
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
			{name: "no", index: "no", width: 70, label: "领料单号", sortable: true, align: "left"},
			{name: "confitemtype", index: "confitemtype", width: 80, label: "施工材料分类", sortable: true, align: "left",hidden:true},
		 	{name: "confitemtypedescr", index: "confitemtypedescr", width: 100, label: "施工材料分类", sortable: true, align: "left"},
		 	{name: "confirmdate", index: "confirmdate", width: 120, label: "审核时间", sortable: true, align: "left",formatter: formatTime},
			{name: "shouldsenddate", index: "shouldsenddate", width: 90, label: "应送货时间", sortable: true, align: "left",formatter: formatDate},
			{name: "shouldsendnode", index: "shouldsendnode", width: 80, label: "应送货节点", sortable: true, align: "left"},
			{name: "nodedatetype", index: "nodedatetype", width: 70, label: "节点类型", sortable: true, align: "left"},
			{name: "nodetriggerdate", index: "nodetriggerdate", width: 90, label: "节点触发时间", sortable: true, align: "left",formatter: formatDate},
			{name: "shouldbanlance", index: "shouldbalance", width: 60, label: "差额", sortable: true, align: "right"},
			{name: "delivtypedescr", index: "delivtypedescr", width: 90, label: "配送方式", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
			{name: "arrivedate", index: "arrivedate", width: 90, label: "预计到货时间", sortable: true, align: "left",formatter: formatDate},
			{name: "splremark", index: "splremark", width: 200, label: "供应商备注", sortable: true, align: "left"},
			{name: "maindescr", index: "maindescr", width: 80, label: "主材管家", sortable: true, align: "left"},
	    ],    
	});
});

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("detail",{
		  title:"查看明细",
		  url:"${ctx}/admin/itemShouldSend/goDetail",
		  postData:{
		  	no:ret.no,
		  	confItemType:ret.confitemtype
		  },
		  height:700,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("update",{
		  title:"编辑明细",
		  url:"${ctx}/admin/itemShouldSend/goUpdate",
		  postData:{no:ret.no},
		  height:300,
		  width:700,
		  returnFun:goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function doExcel(){
	var url = "${ctx}/admin/itemShouldSend/doExcel";
	doExcelAll(url);
}

function itemType1Change(){
	$("#supplierCode").openComponent_supplier({
		condition:{
		    itemRight:"${customer.itemRight}",
		    itemType1:$("#itemType1").val()
		}
	});
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>片区</label>
						<house:DictMulitSelect id="region" dictCode="" sql="SELECT Code,Descr FROM tRegion WHERE 1=1 and expired='F' " sqlLableKey="Descr" sqlValueKey="Code" selectedValue="${prjConfirmApp.prjItem }"></house:DictMulitSelect>
					</li>
					<li>
						<label>项目名称</label>
						<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${customer.builderCode }" />
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
					</li>
					<li>
						<label>触发截止时间</label>
						<input type="text" id="endDate" name="endDate" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${customer.endDate}" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" style="width:160px" style="width:166px;" onchange="itemType1Change()"></select>
					</li>
					<li>
						<label>统计方式</label> 
						<select id="statistcsMethod" name="statistcsMethod">
							<option value="1">起始节点</option>
							<option value="2">终止节点</option>
						</select>
					</li>
					<li>
						<label>发货类型</label>
						<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${customer.sendType }"></house:xtdm>
					</li>
					<li>
						<label>仓库</label>
						<input type="text" id="whCode" name="whCode"  style="width:160px;" value="${customer.whCode }" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
                    <li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
                       <label>应送货节点</label>
                       <house:dict id="prjItem" dictCode=""
                           sql="select Code SQL_VALUE_KEY, Code+' '+Descr SQL_LABEL_KEY from tPrjItem1 where Expired = 'F' order by Seq"></house:dict>
                    </li>
                    <li>
						<label>供应商编号</label>
						<input type="text" id="supplierCode" name="supplierCode"/>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="ITEMSHOULDSEND_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">编辑</button>
				</house:authorize>
				<house:authorize authCode="ITEMSHOULDSEND_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="ITEMSHOULDSEND_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


