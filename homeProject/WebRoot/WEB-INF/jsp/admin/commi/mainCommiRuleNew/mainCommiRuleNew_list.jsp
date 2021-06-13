<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新主材提成规则</title>
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
		var url ="${ctx}/admin/mainCommiRuleNew/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/mainCommiRuleNew/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"no", index:"no", label:"编号", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"commitype", index:"commitype", label:"提成类型编码", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"commitypedescr", index:"commitypedescr", label:"提成类型", width:80, sortable: true, align:"left",}, 
				{name:"fromprofit", index:"fromprofit", label:"毛利率从（大等于）", sortable: true, width:125,align:"right",}, 
				{name:"toprofit", index:"toprofit", label:"毛利率至（小于）", sortable: true, width:120,align:"right",}, 
				{name:"isupgitem", index:"isupgitem", label:"是否升级材料", sortable: true, width:60,align:"left",hidden:true}, 
				{name:"isupgitemdescr", index:"isupgitemdescr", label:"是否升级材料", sortable: true, width:90,align:"left",}, 
				{name:"commiperc", index:"commiperc", label:"提成比例", sortable: true, width:80,align:"right",}, 
				{name:"remarks", index:"remarks", label:"备注", sortable: true, width:250, align:"left", }, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"新主材提成规则——新增",
			postData:{},
			url:"${ctx}/admin/mainCommiRuleNew/goSave",
			height:570,
			width:1055,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function update(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行编辑",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"新主材提成规则——编辑",
			postData:{no: ret.no},
			url:"${ctx}/admin/mainCommiRuleNew/goUpdate",
			height:570,
			width:1055,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function view(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行查看",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"新主材提成规则——查看",
			postData:{no: ret.no},
			url:"${ctx}/admin/mainCommiRuleNew/goView",
			height:570,
			width:1055,
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
				<ul class="ul-form">
					<li class="form-validate">
						<label>提成类型</label>
						<house:xtdm id="commiType" dictCode="COMMITYPE" ></house:xtdm>                     
					</li>
					<li class="form-validate">
						<label>毛利率从</label>
						<input type="text" id="fromProfit" name="fromProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')"/>
					</li>
					<li class="form-validate">
						<label>毛利率至</label>
						<input type="text" id="toProfit" name="toProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')"/>
					</li>
					<li class="form-validate">
						<label>提成比例</label>
						<input type="text" id="commiPerc" name="commiPerc" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')"/>
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="MAINCOMMIRULENEW_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="MAINCOMMIRULENEW_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="MAINCOMMIRULENEW_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="MAINCOMMIRULENEW_EXCEL">
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
