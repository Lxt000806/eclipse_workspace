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
	<title>切割费配置管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>

</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${cutFeeSet.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>切割类型</label> 
						<house:xtdm id="cutType" dictCode="CUTTYPE" style="width:160px;"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${cutFeeSet.expired}" 
							onclick="hideExpired(this)" ${cutFeeSet.expired!='T' ?'checked':'' }/>
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
				<house:authorize authCode="CUTFEESET_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUTFEESET_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUTFEESET_DELETE">
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUTFEESET_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUTFEESET_EXCEL">
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
		url: "${ctx}/admin/cutFeeSet/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
			{name:"cuttype", index:"cuttype", width:80, label:"切割类型", sortable:true, align:"left",hidden:true},
			{name:"cuttypedescr", index:"cuttypedescr", width:80, label:"切割类型", sortable:true, align:"left"},
			{name:"size", index:"size", width:80, label:"瓷砖尺寸", sortable:true, align:"right"},
			{name:"cutfee", index:"cutfee", width:85, label:"客户加工费", sortable:true, align:"right"},
			{name:"supplcutfee", index:"supplcutfee", width:100, label:"供应商加工费", sortable:true, align:"right"},
			{name:"factcutfee", index:"factcutfee", width:100, label:"工厂加工费", sortable:true, align:"right"},
			{name:"allowmodifydescr", index:"allowmodifydescr", width:110, label:"是否允许修改单价", sortable:true, align:"right"},
			{name:"allowmodify", index:"allowmodify", width:100, label:"是否允许修改单价", sortable:true, align:"right",hidden:true},
    		{name : "lastupdate",index : "lastupdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left"},
     		{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
    		{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"},
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "切割费配置管理——新增",
			url : "${ctx}/admin/cutFeeSet/goSave",
			height : 368,
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
			title:"切割费配置管理——编辑",
			url:"${ctx}/admin/cutFeeSet/goUpdate",
			postData:{
				m_umState:"M",
				cutType:ret.cuttype,
				size:ret.size,
				cutFee:ret.cutfee,
				supplCutFee:ret.supplcutfee,
				factCutFee:ret.factcutfee,
				allowModify:ret.allowmodify,
				expired:ret.expired,
			},
			height:368,
			width:445,
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
					url : "${ctx}/admin/cutFeeSet/doDelete",
					data : {cutType:ret.cuttype,size:ret.size},
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
		title:"切割费配置管理——查看",
		url:"${ctx}/admin/cutFeeSet/goView",
		postData:{
			m_umState:"V",
			cutType:ret.cuttype,
			size:ret.size,
			cutFee:ret.cutfee,
			expired:ret.expired,
		},
		height:368,
		width:445,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/cutFeeSet/doExcel";
	doExcelAll(url);
}
</script>
</body>
</html>
