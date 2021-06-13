<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>发货通知</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/sendNotice/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
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
		multiselect:true, 
		height:$(document).height()-$("#content-list").offset().top-90,
		colModel : [
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 70, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "jobtype", index: "jobtype", width: 100, label: "项目任务", sortable: true, align: "left",hidden:true},
			{name: "jobtypedescr", index: "jobtypedescr", width: 100, label: "项目任务", sortable: true, align: "left"},
			{name: "shouldsendnode", index: "shouldsendnode", width: 80, label: "应送货节点", sortable: true, align: "left"},
			{name: "nodedatetype", index: "nodedatetype", width: 70, label: "节点类型", sortable: true, align: "left"},
			{name: "nodetriggerdate", index: "nodetriggerdate", width: 90, label: "节点触发时间", sortable: true, align: "left",formatter: formatDate},
			{name: "moneyinfull", index: "moneyinfull", width: 70, label: "款项到位", sortable: true, align: "left"},
			{name: "shouldbanlance", index: "shouldbanlance", width: 60, label: "差额", sortable: true, align: "right"},
			{name: "sendremarks", index: "sendremarks", width: 200, label: "发货备注", sortable: true, align: "left"},
			{name: "paynum", index: "paynum", width: 200, label: "付款期数", sortable: true, align: "left",hidden:true},
			
	    ],    
	});
});

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      	Global.Dialog.showDialog("view",{
		  title:"查看",
		  url:"${ctx}/admin/sendNotice/goView",
		  postData:{map:JSON.stringify(ret)},
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function notice(){
	var rets = selectDataTableRows();
    if (rets.length > 0) {
      Global.Dialog.showDialog("notice",{
		  title:"发送通知",
		  url:"${ctx}/admin/sendNotice/goNotice",
		  postData:{rets:JSON.stringify(rets)},
		  height:700,
		  width:1000,
		  returnFun:goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function addNotice() {
	Global.Dialog.showDialog("addNotice",{
	  title:"新增通知",
	  url:"${ctx}/admin/sendNotice/goAddNotice",
	  height:400,
	  width:600
	});
}

function doExcel(){
	var url = "${ctx}/admin/sendNotice/doExcel";
	doExcelAll(url);
}

function resfreshJobTypes() {
    $.ajax({
        url: "${ctx}/admin/prjJobManage/prjTypeByItemType1Auth/2/" + $("#itemType1").val(),
        dataType: "json",
        cache: false,
        success: setJobTypes,
        error: clearJobTypes
    });
}

function setJobTypes(data) {

    $("#jobType_NAME").val("");
    $("#jobType").val("");

    var treeObj = $.fn.zTree.getZTreeObj("tree_jobType");        
    var root = treeObj.getNodeByTId("tree_jobType_1");
    treeObj.removeChildNodes(root);
    
    for (i = 0; i < data.data.length; i++) {
        treeObj.addNodes(root, {id: data.data[i].id, name: data.data[i].id + " " + data.data[i].name});
    }
           
}

function clearJobTypes() {

    $("#jobType_NAME").val("");
    $("#jobType").val("");
    
    var treeObj = $.fn.zTree.getZTreeObj("tree_jobType");       
    var root = treeObj.getNodeByTId("tree_jobType_1");
    treeObj.removeChildNodes(root);
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
      					<label>材料类型1</label>
      					<select id="itemType1" name="itemType1" onchange="resfreshJobTypes()"></select>
       				</li>
       				<li>
       				    <label>项目任务</label>
       				    <house:DictMulitSelect id="jobType" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
       				        sql="select rtrim(Code) Code, Descr from tJobType where 1 = 2 order by Code"></house:DictMulitSelect>
       				</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
					</li>
					<li>
						<label>款项到位</label>
						<house:xtdm id="moneyInFull" dictCode="YESNO" value = "1"></house:xtdm>
					</li>
					<li>
						<label>统计方式</label> 
						<select id="statistcsMethod" name="statistcsMethod">
							<option value="1">起始节点</option>
							<option value="2">终止节点</option>
						</select>
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SENDNOTICE_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="SENDNOTICE_NOTICE">
					<button type="button" class="btn btn-system" onclick="notice()">发送通知</button>
				</house:authorize>
				<house:authorize authCode="SENDNOTICE_ADDNOTICE">
					<button type="button" class="btn btn-system" onclick="addNotice()">新增通知</button>
				</house:authorize>
				<house:authorize authCode="SENDNOTICE_EXCEL">
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


