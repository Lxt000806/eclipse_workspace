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
	<title>文档管理主页</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}

	</style>
<script type="text/javascript"> 
	var zTree;
	var setting = {
			check: {
				enable: false,
				nocheckInherit: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: function(event, treeId, treeNode){
					$("#folderPK").val(treeNode.id);
					$("#authNode").val(treeNode.nodeType);
					goto_query();
				},
				onExpand:function(event, treeId, treeNode){
					$("#folderPK").val(treeNode.id);
					$("#authNode").val(treeNode.nodeType);
					if(treeNode.open == true){
						goto_query();
					}
				},
			}
		};
$(function(){
	var zNodes = ${node };
	$.fn.zTree.init($("#tree"), setting, zNodes);
	zTree = $.fn.zTree.getZTreeObj("tree");
	console.log($("#tree_1_switch"));
	if($("#tree_1_switch") && $("#tree_1_switch").length > 0 ){
		$("#tree_1_switch").click();
		$("#tree_1_span").click();
	}
	
	var postData = $("#page_form").jsonForm();

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url : "${ctx}/admin/doc/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "pk", index: "pk", width: 130, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "docname", index: "docname", width: 130, label: "文档名称", sortable: true, align: "left"
				,formatter:function(cellvalue, options, rowObject){
        			if(rowObject==null || !rowObject.docname){
          				return '';
          			}
        			return "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"goView('"
        					+rowObject.pk+"')\">"+rowObject.docname+"</a>";
	    	},},
			{name: "doccode", index: "doccode", width: 120, label: "文档编号", sortable: true, align: "left"},
			{name: "docversion", index: "docversion", width: 70, label: "版本号", sortable: true, align: "left"},
			{name: "foldername", index: "foldername", width: 60, label: "目录", sortable: true, align: "left",},
			{name: "attpks", index: "attpks", width: 300, label: "附件", sortable: true, align: "left"
				,formatter:function(cellvalue, options, rowObject){
        			if(rowObject==null || !rowObject.docname){
          				return '';
          			}
        			return formatStr(rowObject);
	    	},},
			{name: "isforregulardescr", index: "isforregulardescr", width: 70, label: "员工可看", sortable: true, align: "left"},
			{name: "enabledate", index: "enabledate", width: 75, label: "生效时间", sortable: true, align: "left",formatter:formatDate},
			{name: "expireddate", index: "expireddate", width: 75, label: "失效时间", sortable: true, align: "left",formatter:formatDate},
			{name: "drawupczydescr", index: "drawupczydescr", width: 60, label: "拟制人", sortable: true, align: "left",},
			{name: "drawupdate", index: "drawupdate", width: 75, label: "拟制时间", sortable: true, align: "left",formatter:formatDate},
			{name: "confirmczydescr", index: "confirmczydescr", width: 60, label: "审核人", sortable: true, align: "left",},
			{name: "confirmdate", index: "confirmdate", width: 75, label: "审核时间", sortable: true, align: "left",formatter:formatDate},
			{name: "approveczydescr", index: "approveczydescr", width: 60, label: "批准人", sortable: true, align: "left"},
			{name: "approvedate", index: "approvedate", width: 75, label: "批准时间", sortable: true, align: "left",formatter:formatDate},
			{name: "lastupdate", index: "lastupdate", width: 75, label: "更新时间", sortable: true, align: "left", formatter:formatDate},
			{name: "isneednoticedescr", index: "isneednoticedescr", width: 68, label: "消息通告", sortable: true, align: "left"},
			{name: "downloadcnt", index: "downloadcnt", width: 83, label: "下载次数", sortable: true, align: "right"},
		],
		gridComplete:function(){
			$("#dataTable").setSelection("1",false);
		}
	});
	
	$("#save").on("click",function(){
		if($("#authNode").val() != "" && $("#authNode").val() == "false"){
			art.dialog({
				content:"你没有该目录的权限,无法新增",
			});
			return;
		}

		Global.Dialog.showDialog("goSave",{
			title:"档案管理——新增",
			postData:{folderPK:$("#folderPK").val()},
			url:"${ctx}/admin/doc/goSave",
			height:720,
			width:1230,
			returnFun:goto_query
		});		
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行编辑",
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goUpdate",{
			title:"档案管理——编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/doc/goUpdate",
			height:720,
			width:1230,
			returnFun:goto_query
		});		
	});
	
	$("#updateVersion").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行编辑",
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goUpdateVersion",{
			title:"档案管理——版本更新",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/doc/goUpdateVersion",
			height:720,
			width:1230,
			returnFun:goto_query
		});		
	});
	
	$("#viewFiledDoc").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行查看",
			});
			
			return;
		}
		
		Global.Dialog.showDialog("viewFiledDoc",{
			title:"档案管理——查看归档文档",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/doc/goViewFiledDoc",
			height:740,
			width:1150,
			returnFun:goto_query
		});		
	});
	
	$("#filedDoc").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行归档操作",
			});
			
			return;
		}
		
		art.dialog({
			content:"是否确定进行归档操作",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/doc/doFile",
					type: "post",
					data: {pk:ret.pk},
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
				    		$("#_form_token_uniq_id").val(obj.token.token);
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
	});
	
	$("#deleteDoc").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行删除操作",
			});
			
			return;
		}
		
		art.dialog({
			content:"是否确定删除该文档",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/doc/doDelete",
					type: "post",
					data: {pk:ret.pk},
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
				    		$("#_form_token_uniq_id").val(obj.token.token);
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
	});
	
});

function goView(pk){
	
	Global.Dialog.showDialog("goView",{
		title:"档案管理——查看",
		postData:{pk:pk},
		url:"${ctx}/admin/doc/goView",
		height:540,
		width:750,
		returnFun:goto_query
	});		
}

function changeDateName(){
	if($.trim($("#dateType").val()) == ""){
		$("#dateName").html("最后修改时间从");		
	} else {
		$("#dateName").html($("#dateType option:selected").text()+"从");
	}
}

function chgCheckBox(obj){
	if ($(obj).is(':checked')){
		$('#subdirectory').val('1');
	}else{
		$('#subdirectory').val('');
	}
} 

function formatStr(rowObject){
	var attpks;
	var formatStr = ""
	if(rowObject && rowObject.attpks && rowObject.attpks != ""){
		attpks = rowObject.attpks.split(",");
		for(var i = 0; i < attpks.length; i++){
			if(formatStr == ""){
				formatStr = "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"downloadFile('"
					+attpks[i]+"','"+rowObject.pk+"')\">"+attpks[i]+"</a>";
			} else {
				formatStr += ",<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"downloadFile('"
					+attpks[i]+"','"+rowObject.pk+"')\">"+attpks[i]+"</a>";
			}
		}
	}
	return formatStr;
}

function downloadFile(docName, pk){
	console.log(docName);
	console.log(pk);
	window.open("${ctx}/admin/doc/download?attName="+ docName +"&docPK="+pk);
}

function chgDateTo(){
	var dateFrom = $("#dateFrom").val();
	var dateTo= $("#dateTo").val();
	
	if(dateFrom != "" && dateTo != "" && dateFrom >dateTo){
		$("#dateTo").val("");
	}
}

function clearForm(){
	$("#queryCondition").val("");
	$("#dateType").val("enable");
	$("#dateFrom").val("");
	$("#dateTo").val("");
	changeDateName();
}

function doExcel(){
	var url = "${ctx}/admin/doc/doExcel";
	doExcelAll(url);
}
</script>
</head>
	<body>
		<div class="body-box-list" style="width:17%;float:left;">
			<div style="background-color: rgb(237,245,247);height:24px;text-align: center;line-height: 24px;font-weight: 900">目录</div>
			<ul id="tree" class="ztree" style="width:350px; overflow:auto;"></ul>
		</div>
		<div class="body-box-list" style="width:83%;float:right;border-left:1px solid rgb(224,231,234)">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="folderPK" name="folderPK" value="" />
					<input type="hidden" id="status" name="status" value="1"/>
					<input type="hidden" id="authNode" name="authNode" value=""/>
					<input type="hidden" id="subdirectory" name="subdirectory" value="1" />
					<input type="hidden" id="baseUrl" name="baseUrl" value="${baseUrl }" />
					<ul class="ul-form">
						<li>
							<label style="width:60px">时间类型</label>
							<select id="dateType" name="dateType" onchange="changeDateName()"  style="width:120px">
								<option value="enable" selected>生效时间</option>
								<option value="expired">失效时间</option>
								<option value="drawUp">拟制时间</option>
								<option value="confirm">审核时间</option>
								<option value="approve">批准时间</option>
								<option value="create">创建时间</option>
							</select>
						</li>
						<li>
							<label style="width:60px" id="dateName">生效时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
								style="width:120px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',onpicking:chgDateTo()})"/>
						</li>
						<li>
							<label style="width:30px">至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
								style="width:120px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:$('#dateFrom').val()})"/>
						</li>
						<li>
							<label style="width:60px">查询</label>
							<input type="text" id="queryCondition" name="queryCondition" placeholder="名称/编号/关键字"/>
						</li>
						<li class="search-group">
							<input type="checkbox" id="subdirectory_chg" name="subdirectory_chg"
								 onclick="chgCheckBox(this)" checked/><p>包含子目录文件</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent" style="width:83%;float:right;border-left:1px solid rgb(224,231,234)">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="DOC_SAVE">
						<button type="button" class="btn btn-system" id="save">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="DOC_UPDATE">
						<button type="button" class="btn btn-system" id="update">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="DOC_UPDATEVERSION">
						<button type="button" class="btn btn-system" id="updateVersion">
							<span>版本更新</span>
						</button>
					</house:authorize>
					<house:authorize authCode="DOC_FILE">
						<button type="button" class="btn btn-system" id="filedDoc">
							<span>归档</span>
						</button>
					</house:authorize>
					<house:authorize authCode="DOC_DELETE">
						<button type="button" class="btn btn-system" id="deleteDoc">
							<span>删除</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system" id="viewFiledDoc">
						<span>查看已归档文档</span>
					</button>
					<house:authorize authCode="DOC_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
	<script type="text/javascript">
	/*function doExcel(){
		var url = "${ctx}/admin/doc/doExcel";
		doExcelAll(url);
	}*/
	</script>
</html>
