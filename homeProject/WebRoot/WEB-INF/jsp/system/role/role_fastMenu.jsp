<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>快捷菜单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
			<style type="text/css">
		.rowBox{
			display: -webkit-flex; /* Safari */
  			flex-direction: row ;
  			flex-wrap: wrap;
  			justify-content: space-between;
  			margin-bottom: 10px;
		}
	</style>
	<script type="text/javascript">
	var initSelIds = [];
	var zTree;
	var setting = {
		check: {
			enable: false,
			nocheckInherit: true,
			chkboxType: { "Y": "p", "N": "ps" } 
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback:{
			onClick:addFastMenu,
		},
		view: {
			dblClickExpand: false
		}
	};
	
	var zNodes =${nodes};
	
	$(document).ready(function(){
		document.execCommand("BackgroundImageCache",false,true);//IE6缓存背景图片
		$.fn.zTree.init($("#tree"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("tree");
		$document = $(document);
		$('div.panelBar1', $document).panelBar();
	});
	
	function addFastMenu(event, treeId, treeNode){
		if(treeNode.isParent){
			zTree.expandNode(treeNode, !treeNode.open, false, true);
			return;
		}
		var ids=$("#dataTable").jqGrid("getDataIDs");
		var newId=1;
		var rets = $("#dataTable").jqGrid("getRowData");//资源数组
		for(i in rets){
			if(rets[i].menu_id==treeNode.id){
				art.dialog({
					content: "菜单["+treeNode.name+"]已存在"
				});
				return;
			}
		}
		if(ids.length>0){
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		var json ={
			menu_id:treeNode.id,
			menu_name:treeNode.name,
			dispseq:newId,
			lastupdate:new Date(),
			lastupdatedby:"${czybh}"
		}
		$("#dataTable").jqGrid("addRowData", newId, json);
	}
	
	//向上
	function up(){
		var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)-1;
		if(rowId){
			if(rowId>1){
			    var ret1 = $("#dataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#dataTable").jqGrid("getRowData", replaceId);
				replace(rowId,replaceId,"dataTable");
				scrollToPosi(replaceId,"dataTable");
				$("#dataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#dataTable").setCell(replaceId,"dispseq",ret2.dispseq);
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	
	//向下
	function down(){
		var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)+1;
		var rowNum = $("#dataTable").jqGrid("getGridParam","records");
		if(rowId){
			if(rowId<rowNum){
				var ret1 = $("#dataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#dataTable").jqGrid("getRowData", replaceId);
				scrollToPosi(replaceId,"dataTable");
				replace(rowId,replaceId,"dataTable");
				$("#dataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#dataTable").setCell(replaceId,"dispseq",ret2.dispseq);
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	
	function del(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({
				content: "请选择一条记录进行删除操作"
			});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	
	function doSave() {
		var rets = $("#dataTable").jqGrid("getRowData");
		var params = {"detailJson": JSON.stringify(rets)};
		Global.Form.submit("dataForm","${ctx}/admin/role/doSaveFastMenu",params,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: ret.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
	    		art.dialog({
					content: ret.msg,
					width: 200
				});
			}
		});
	}
	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/role/goFastMenuJqGrid",
			postData:{roleId:"${roleId}"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			rowNum:100000000,
			colModel : [
				{name:'menu_id',	index:'menu_id',	width:80,	label:'菜单编号',	sortable:true,align:"left",hidden:true},
				{name:'menu_name',	index:'menu_name',	width:200,	label:'菜单名称',	sortable:true,align:"left",},
				{name:'dispseq',	index:'dispseq',	width:80,	label:'显示顺序',	sortable:true,align:"left",},
				{name:'lastupdatedby',	index:'lastupdatedby',	width:95,	label:'最后操作人员',	sortable:true,align:"left",hidden:"${resrCust.type}" =="1"?true:false},
				{name:'lastupdate',	index:'lastupdate',	width:150,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
			],
			loadonce:true
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBut"
						onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="rowBox">
			<div style="width:40%;height:500px;float:left;overflow:auto;">
				<ul id="tree" class="ztree" style="width:350px; overflow:auto;"></ul>
			</div>
			<div class="body-box-list" style="margin-top: 0px;width:60%">
				<ul class="nav nav-tabs" role="tablist">
				    <li role="presentation" class="active"><a href="#first" aria-controls="first" role="tab" data-toggle="tab">快捷菜单</a></li>
				</ul>
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="first">
				    	<div class="panel panel-system">
						<div class="panel-body">
							<form role="form" class="form-search" id="dataForm" method="post">
								<input type="hidden" name="jsonString" value="" /> 
								<input type="hidden" name="roleId" value="${roleId }" /> 
							</form>
							<div class="btn-group-xs">
								<button style="align:left" type="button" id="del"
									class="btn btn-system " onclick="del()">
									<span>删除 </span>
								</button>
								<button style="align:left" type="button" class="btn btn-system "
									 onclick="up()">
									<span>向上 </span>
								</button>
								<button style="align:left" type="button" class="btn btn-system "
									 onclick="down()">
									<span>向下 </span>
								</button>
							</div>
						</div>
					</div>
					<div class="clear_float"></div>
						<!--query-form-->
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
