<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>薪酬发放银行卡类型</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
<META HTTP-EQUIV="expires" CONTENT="0"/>
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}

	</style>
<script type="text/javascript">
	/**导出EXCEL*/
	function doExcel() {
		var url ="${ctx}/admin/salaryBankCardTypeCfg/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/salaryBankCardTypeCfg/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:100, sortable: true, align:"left",hidden:true}, 
				{name:"salarypaycmp", index:"salarypaycmp", label:"薪酬支付公司", width:100, sortable: true, align:"left",hidden:true}, 
				{name:"salarypaycmpdescr", index:"salarypaycmpdescr", label:"薪酬支付公司", width:100, sortable: true, align:"left",}, 
				{name:"salaryschemetypedescr", index:"salaryschemetypedescr", label:"薪酬方案", width:100, sortable: true, align:"left"}, 
				{name:"paymodedescr", index:"paymodedescr", label:"发放类型", sortable: true, width:100,align:"left",}, 
				{name:"bankcardtypedescr", index:"bankcardtypedescr", label:"银行卡类型", sortable: true, width:100,align:"left",}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"薪酬银行卡发放类型——新增",
			postData:{},
			url:"${ctx}/admin/salaryBankCardTypeCfg/goSave",
			height:330,
			width:490,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function update(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("update",{
			title:"薪酬银行卡发放类型——编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/salaryBankCardTypeCfg/goUpdate",
			height:330,
			width:490,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function view(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
		Global.Dialog.showDialog("view",{
			title:"薪酬银行卡发放类型——查看",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/salaryBankCardTypeCfg/goView",
			height:330,
			width:490,
	        resizable: true,
			returnFun:goto_query
		});	
	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" name="expired" Id = "expired" value=""/>
				<ul class="ul-form">
					<li class="form-validate">
						<label>公司</label>
						<house:dict id="salaryPayCmp" dictCode="" sql="select * from tConSignCmp where expired = 'F'" 
								sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
					</li>
					<li class="form-validate">
						<label>发放方式</label>
						<house:xtdm  id="payMode" dictCode="SALPAYMODE" style="width:160px;"></house:xtdm>
					</li>

					<li class="search-group">
						<input type="checkbox"  id="expired_show" name="expired_show"
								 onclick="hideExpired(this)" checked/><p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SALARYBANKCARDTYPECFG_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYBANKCARDTYPECFG_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYBANKCARDTYPECFG_VIEW">
					<button id="btnView" type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYBANKCARDTYPECFG_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						导出到Excel
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
