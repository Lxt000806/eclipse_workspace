<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>印章管理</title>
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
	td{
		vertical-align:middle !important;
	}
</style>
<script type="text/javascript">
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/orgSeal/goJqGrid",
			postData:{orgId:"${orgSeal.orgId}"},
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"center",hidden:true}, 
				{name:"url", index:"url", label:"印章", width:100, sortable: true, align:"center",formatter:sealBtn}, 
				{name:"sealid", index:"sealid", label:"印章Id", width:250, sortable: true, align:"center"},
				{name:"type", index:"type", label:"模板类型", width:90, sortable: true, align:"center"},
				{name:"central", index:"central", label:"中心图案类型", width:90, sortable: true, align:"center"},
				{name:"color", index:"color", label:"颜色", width:70, sortable: true, align:"center"},
				{name:"qtext", index:"qtext", label:"下玄文", width:80, sortable: true, align:"center"},
				{name:"htext", index:"htext", label:"横向文", width:80, sortable: true, align:"center"},
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:120, align:"center",formatter:formatTime}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"center"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"center"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"center"}, 
			]
		});
		
	});
	
	function sealBtn(v,x,r){
		return "<img style='cursor:pointer' title='点击查看大图' width='80px' height='80px' src='"
			+v+"' onclick='viewSealPic("+x.rowId+")'></img>";
	}  
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"印章管理——新增",
			postData:{orgId:"${orgSeal.orgId}"},
			url:"${ctx}/admin/orgSeal/goSave",
			height:325,
			width:400,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function update(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("save",{
			title:"印章管理——编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/orgSeal/goUpdate",
			height:325,
			width:400,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function view(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("save",{
			title:"印章管理——查看",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/orgSeal/goView",
			height:325,
			width:400,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function viewSealPic(id){
		var ret = $("#dataTable").jqGrid('getRowData', id);
		Global.Dialog.showDialog("viewSealPic",{ 
	  		title:"查看印章图片",
	  		url:"${ctx}/admin/taxPayeeESign/goViewSealPic",
	  		postData: {
	  		    orgId:"${orgSeal.orgId}",sealId:ret.sealid
	  		},
	  		height:600,
	  		width:600,
	  		returnFun:goto_query
	    });	
	}
	
	function delDetail(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行删除操作",
			});
			return;
		} 
		art.dialog({
			content:"是否确定删除该记录？",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/orgSeal/doDelete?pk="+ret.pk,
					type:"post",
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content: obj.msg,
						});
						if(obj.rs){
							$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
						}
					}
				});
			},
			cancel: function () {
				return true;
			}
		});
	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="save()">
					新增
				</button>
				<button type="button" class="btn btn-system" onclick="update()">
					编辑
				</button>
				<button type="button" class="btn btn-system" onclick="view()">
					查看
				</button>
				<button type="button" class="btn btn-system" onclick="delDetail()">
					删除
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
