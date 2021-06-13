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
	if($("#tree_1_switch") && $("#tree_1_switch").length > 0 ){
		$("#tree_1_switch").click();
		$("#tree_1_span").click();
	}
	var postData = $("#page_form").jsonForm();

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url : "${ctx}/admin/guideTopic/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "pk", index: "pk", width: 130, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "topic", index: "topic", width: 200, label: "主题", sortable: true, align: "left",
				formatter:function(cellvalue, options, rowObject){
        			if(rowObject==null || !rowObject.topic){
          				return '';
          			}
        			return "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"goView('"
        					+rowObject.pk+"')\">"+rowObject.topic+"</a>";
	    		},
	    	},
			{name: "foldername", index: "foldername", width: 130, label: "所属类目", sortable: true, align: "left"},
			{name: "content", index: "content", width: 300, label: "内容", sortable: true, align: "left",
	    		formatter:function(cellvalue, options, rowObject){
        			if(rowObject==null || !rowObject.content){
          				return '';
          			}
        			var content = rowObject.content.replace(/<[^>]+>/g," ");
        			content = content.replace(/&nbsp;/g," ");
        			if(content.length > 50){
	        			return content.substring(0,30)+"...";
        			} else {
        				return content;
        			}
	    		},
			},
			{name: "visitcnt", index: "visitcnt", width: 80, label: "查看次数", sortable: true, align: "right",},			
			{name: "createczydescr", index: "createczydescr", width: 80, label: "创建人", sortable: true, align: "left",},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left",formatter:formatTime},

		],
		gridComplete:function(){
			$("#dataTable").setSelection("1",false);
		}
	});
	
	$("#save").on("click",function(){
		if($("#authNode").val() != "" && $("#authNode").val() == "false"){
			art.dialog({
				content:"你没有该类目的权限,无法新增",
			});
			return;
		}
		Global.Dialog.showDialog("goSave",{
			title:"问答管理——新增",
			postData:{folderPK:$("#folderPK").val()},
			url:"${ctx}/admin/guideTopic/goSave",
			height:650,
			width:1130,
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
			title:"问答管理——编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/guideTopic/goUpdate",
			height:650,
			width:1130,
			returnFun:goto_query
		});		
	});
	
	$("#deleteTopic").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行删除操作",
			});
			
			return;
		}
		
		art.dialog({
			content:"是否确定删除该主题",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/guideTopic/doDelete",
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
		title:"问答管理——查看",
		postData:{pk:pk},
		url:"${ctx}/admin/guideTopic/goView",
		height:650,
		width:1130,
		returnFun:goto_query
	});		
}

function changeDateName(){
	if($.trim($("#dateType").val()) == ""){
		$("#dateName").html("时间从");		
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

function clearForm(){
	$("#queryCondition").val("");
	$("#dateType").val("enable");
	$("#dateFrom").val("");
	$("#dateTo").val("");
	changeDateName();
}

function doExcel(){
	var url = "${ctx}/admin/guideTopic/doExcel";
	doExcelAll(url);
}

</script>
</head>
	<body>
		<div class="body-box-list" style="width:17%;float:left;">
			<div style="background-color: rgb(237,245,247);height:24px;text-align: center;line-height: 24px;font-weight: 900">类目</div>
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
							<label style="width:60px">查询</label>
							<input type="text" id="queryCondition" name="queryCondition" placeholder="主题/内容/关键字"/>
						</li>
						<li class="search-group">
							<input type="checkbox" id="subdirectory_chg" name="subdirectory_chg"
								 onclick="chgCheckBox(this)" checked/><p>包含子类目文件</p>
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
					<house:authorize authCode="GUIDETOPIC_SAVE">
						<button type="button" class="btn btn-system" id="save">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="GUIDETOPIC_UPDATE">
						<button type="button" class="btn btn-system" id="update">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="GUIDETOPIC_DELETE">
						<button type="button" class="btn btn-system" id="deleteTopic">
							<span>删除</span>
						</button>
					</house:authorize>
					<house:authorize authCode="GUIDETOPIC_EXCEL">
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
	function doExcel(){
		var url = "${ctx}/admin/guideTopic/doExcel";
		doExcelAll(url);
	}
	</script>
	
</html>
