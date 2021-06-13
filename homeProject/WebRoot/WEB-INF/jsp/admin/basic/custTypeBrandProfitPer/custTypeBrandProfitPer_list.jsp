<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>品牌利润率配置</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${custTypeBrandProfitPer.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>客户类型</label>
						<house:dict id="custType" dictCode="" sql="select code ,code+' '+desc1 descr from tcustType where expired='F' " 
						sqlValueKey="Code" sqlLableKey="Descr"  value="${custTypeBrandProfitPer.custType}"></house:dict>
					</li>
					<li>	
						<label>品牌</label>
						<input type="text" id="brandCode" name="brandCode"   value="${custTypeBrandProfitPer.brandCode}" />
					</li>	
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${custTypeBrandProfitPer.expired}" 
							onclick="hideExpired(this)" ${custTypeBrandProfitPer.expired!='T' ?'checked':'' }/>
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
				<house:authorize authCode="CUSTTYPEBRANDPROFITPER_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEBRANDPROFITPER_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEBRANDPROFITPER_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEBRANDPROFITPER_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTTYPEBRANDPROFITPER_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>输出到Excel</span>
					</button>
				</house:authorize>
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
		url: "${ctx}/admin/custTypeBrandProfitPer/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name : "pk",index : "pk",width : 125,label:"pk",sortable : true,align : "left",hidden:true},
			{name : "custtypedescr", index:"custtypedescr", width:90, label:"客户类型", sortable:true, align:"left"},
    		{name : "branddescr", index:"branddescr", width:125, label:"品牌", sortable:true, align:"left"},
    		{name : "profitper", index:"profitper", width:70, label:"利润率", sortable:true, align:"right"},
    		{name : "lastupdate",index : "lastupdate",width : 120,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left"},
     		{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
    		{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"}
		],
		ondblClickRow: function(){
			view();
		}
	});
	$("#brandCode").openComponent_brand();
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "品牌利润率配置——新增",
			url : "${ctx}/admin/custTypeBrandProfitPer/goSave",
			height : 300,
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
			title:"品牌利润率配置——编辑",
			url:"${ctx}/admin/custTypeBrandProfitPer/goUpdate",
			postData:{
				id:ret.pk,
			},
			height : 300,
			width : 445,
			returnFun:goto_query
		});
	});
	
	$("#delete").on("click",function(){
		var ret = selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		art.dialog({
			content:"您确定要删除该信息吗？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url : "${ctx}/admin/custTypeBrandProfitPer/doDelete",
					data : {deleteIds:ret.pk},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
						art.dialog({
							content: "删除该信息出现异常"
						});
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
								content: obj.msg
							});
						}
					}
				});
				return true;
			},
			cancel: function () {
				return true;
			}
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
		title:"品牌利润率配置——查看",
		url:"${ctx}/admin/custTypeBrandProfitPer/goView",
		postData:{
			id:ret.pk,
		},
		height : 300,
		width : 445,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/custTypeBrandProfitPer/doExcel";
	doExcelAll(url);
}
</script>
</body>
</html>
