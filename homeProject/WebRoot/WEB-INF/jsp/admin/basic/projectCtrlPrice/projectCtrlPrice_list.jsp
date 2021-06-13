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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>发包单价管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>

</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${projectCtrlPrice.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>编号</label>
						<input type="text" id="pK" name="pK" style="width:160px;"/>
					</li>
					<li>
						<label>客户类型</label> 
						<house:dict id="custType" dictCode="" sql="select rtrim(Code)+' '+Desc1 fd,Code from tCustType where Expired='F' order by Code" 
							sqlValueKey="Code" sqlLableKey="fd"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${projectCtrlPrice.expired}" 
							onclick="hideExpired(this)" ${projectCtrlPrice.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="PROJECTCTRLPRICE_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>输出到Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/projectCtrlPrice/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name:"pk", index:"pk", width:60, label:"编号", sortable:true, align:"left"},
			{name:"custtype", index:"custtype", width:80, label:"客户类型", sortable:true, align:"left",hidden:true},
			{name:"custtypedescr", index:"custtypedescr", width:80, label:"客户类型", sortable:true, align:"left"},
			{name:"fromarea", index:"fromarea", width:70, label:"面积从", sortable:true, align:"right"},
			{name:"toarea", index:"toarea", width:70, label:"面积到", sortable:true, align:"right"},
			{name:"price", index:"price", width:70, label:"单价", sortable:true, align:"right"},
			{name:"managefee", index:"managefee", width:60, label:"管理费", sortable:true, align:"right"},
			{name:"basequotaprice", index:"basequotaprice", width:90, label:"基础考核定额", sortable:true, align:"right"},
			{name:"minarea", index:"minarea", width:80, label:"保底面积", sortable:true, align:"right"},
    		{name : "lastupdate",index : "lastupdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left"},
     		{name : "expired",index : "expired",width : 70,label:"过期标志",sortable : true,align : "left"},
    		{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"},
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "发包单价管理——新增",
			url : "${ctx}/admin/projectCtrlPrice/goSave",
			height : 400,
			width : 445,
			returnFun : goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"发包单价管理——编辑",
			url:"${ctx}/admin/projectCtrlPrice/goUpdate",
			postData:{
				m_umState:"M",
				pK:ret.pk,
				custType:ret.custtype,
				fromArea:ret.fromarea,
				toArea:ret.toarea,
				price:ret.price,
				expired:ret.expired,
				manageFee:ret.managefee,
				baseQuotaPrice:ret.basequotaprice,
				minArea:ret.minarea,
			},
			height:435,
			width:445,
			returnFun:goto_query
		});
	});

});

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"发包单价管理——查看",
		url:"${ctx}/admin/projectCtrlPrice/goView",
		postData:{
			m_umState:"V",
			pK:ret.pk,
			custType:ret.custtype,
			fromArea:ret.fromarea,
			toArea:ret.toarea,
			price:ret.price,
			expired:ret.expired,
			manageFee:ret.managefee,
			baseQuotaPrice:ret.basequotaprice,
			minArea:ret.minarea,
		},
		height:435,
		width:445,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/projectCtrlPrice/doExcel";
	doExcelAll(url);
}
</script>
</body>
</html>
