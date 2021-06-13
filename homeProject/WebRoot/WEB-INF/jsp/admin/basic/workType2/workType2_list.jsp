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
	<title>工种分类2管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${workType2.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>工种编码</label> 
						<input type="text" id="code" name="code" style="width:160px;" value=""/>
					</li>
					<li>
						<label>工种名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;" value=""/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${workType2.expired}" 
							onclick="hideExpired(this)" ${workType2.expired!='T' ?'checked':'' }/>
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
				<button type="button" class="btn btn-system" id="copy">
					<span>复制</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="WORKTYPE2_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/workType2/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name:"code", index:"code", width:100, label:"工种分类2编号", sortable:true, align:"left"},
			{name:"descr", index:"descr", width:100, label:"工种分类2名称", sortable:true, align:"left"},
			{name:"dispseq", index:"dispseq", width:70, label:"显示顺序", sortable:true, align:"right"},
			{name:"worktype1", index:"worktype1", width:100, label:"工种分类1编号", sortable:true, align:"left"},
			{name:"worktype1descr", index:"worktype1descr", width:100, label:"工种分类1名称", sortable:true, align:"left"},
			{name:"caltype", index:"caltype", width:100, label:"统计类型编码", sortable:true, align:"left", hidden:true},
			{name:"caltypedescr", index:"caltypedescr", width:70, label:"统计类型", sortable:true, align:"left"},
    		{name:"prjitem", index:"prjitem", width:100, label:"施工项目编码", sortable:true, align:"left", hidden:true},
    		{name:"prjitemdescr", index:"prjitemdescr", width:130, label:"对应施工阶段", sortable:true, align:"left"},
    		{name:"iscalprojectcost", index:"iscalprojectcost", width:170, label:"是否计算项目经理成本编码", sortable:true, align:"left", hidden:true},
    		{name:"iscalprojectcostdescr", index:"iscalprojectcostdescr", width:150, label:"是否计算项目经理成本", sortable:true, align:"left"},
    		{name:"salary", index:"salary", width:50, label:"工资", sortable:true, align:"right"},
    		{name:"worktype12", index:"worktype12", width:110, label:"工种分类12编码", sortable:true, align:"left", hidden:true},
    		{name:"worktype12descr", index:"worktype12descr", width:80, label:"工种分类12", sortable:true, align:"left"},
    		{name:"isconfirmtwo", index:"isconfirmtwo", width:140, label:"工资是否二次审核编码", sortable:true, align:"left", hidden:true},
    		{name:"isconfirmtwodescr", index:"isconfirmtwodescr", width:120, label:"工资是否二次审核", sortable:true, align:"left"},
    		{name:"isprjapp", index:"isprjapp", width:120, label:"是否项目经理申请编码", sortable:true, align:"left", hidden:true},
    		{name:"isprjappdescr", index:"isprjappdescr", width:120, label:"是否项目经理申请", sortable:true, align:"left"},
    	    {name: "denyofferworktype2descr", index:"denyofferworktype2descr", width:150, label:"工资申请后不允许领料", sortable:true, align:"left"},
    	    {name: "salaryctrltypedescr", index:"salaryctrltypedescr", width:100, label:"工资控制类型", sortable:true, align:"left"},
    		{name: "salaryctrltype", index:"salaryctrltype", width:100, label:"工资控制类型", sortable:true, align:"left",hidden:true},
    		{name: "denyofferworktype2", index:"denyofferworktype2", width:120, label:"工资申请后不允许领料", sortable:true, align:"left",hidden:true},
    		{name : "lastupdate",index : "lastupdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left"},
     		{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
    		{name : "actionlog",index : "actionlog",width : 50,label:"操作",sortable : true,align : "left"},
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title : "工种分类2——新增",
			url : "${ctx}/admin/workType2/goSave",
			height : 580,
			width : 445,
			returnFun : goto_query
		});
	});
	
	$("#copy").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("copy",{
			title:"工种分类2——复制",
			url:"${ctx}/admin/workType2/goSave",
			postData:{
				code:ret.code.trim(),
				descr:ret.descr,
				workType1:ret.worktype1.trim(),
				dispSeq:ret.dispseq,
				calType:ret.caltype,
				prjItem:ret.prjitem,
				isCalProjectCost:ret.iscalprojectcost,
				salary:ret.salary,
				workType12:ret.worktype12.trim(),
				isConfirmTwo:ret.isconfirmtwo,
				isPrjApp:ret.isprjapp,
				denyOfferWorkType2:ret.denyofferworktype2,
				m_umState:"C",
				salaryCtrlType:ret.salaryctrltype
			},
			height : 580,
			width:445,
			returnFun:goto_query
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
			title:"工种分类2——编辑",
			url:"${ctx}/admin/workType2/goUpdate",
			postData:{
				code:ret.code.trim(),
				descr:ret.descr,
				workType1:ret.worktype1.trim(),
				dispSeq:ret.dispseq,
				calType:ret.caltype,
				prjItem:ret.prjitem,
				isCalProjectCost:ret.iscalprojectcost,
				salary:ret.salary,
				workType12:ret.worktype12.trim(),
				isConfirmTwo:ret.isconfirmtwo,
				denyOfferWorkType2:ret.denyofferworktype2,
				denyOfferWorkType2Descr:ret.denyofferworktype2descr,
				isPrjApp:ret.isprjapp,
				expired:ret.expired,
				m_umState:"M",
				salaryCtrlType:ret.salaryctrltype
			},
			height:612,
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
		title:"工种分类2——查看",
		url:"${ctx}/admin/workType2/goView",
		postData:{
			code:ret.code.trim(),
			descr:ret.descr,
			workType1:ret.worktype1.trim(),
			dispSeq:ret.dispseq,
			calType:ret.caltype,
			prjItem:ret.prjitem,
			isCalProjectCost:ret.iscalprojectcost,
			salary:ret.salary,
			workType12:ret.worktype12.trim(),
			isConfirmTwo:ret.isconfirmtwo,
			denyOfferWorkType2:ret.denyofferworktype2,
			denyOfferWorkType2Descr:ret.denyofferworktype2descr,
			isPrjApp:ret.isprjapp,
			expired:ret.expired,
			m_umState:"V",
			salaryCtrlType:ret.salaryctrltype
		},
		height:612,
		width:445,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/workType2/doExcel";
	doExcelAll(url);
}
</script>
</html>
