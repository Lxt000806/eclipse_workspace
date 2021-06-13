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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.panel-body .form-search .ul-form .row li button {
			width: auto;
			padding-left: 7px;
			padding-right: 7px;
		}
		.row{
			margin: 0px;
		}
		.table > tbody > tr > td >input {
			width: 100%;
			border: 0px;
			background: #ffffff00;
		}
		.SelectBG{
          	background-color:#FF7575!important;
        }  
	</style>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHousePosi.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false);">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label for="code">仓库</label>
								<input type="text" id="code" name="code" style="width:160px;" value=""/>
							</li>
							<li>
								<button type="button" class="btn btn-system btn-sm" id="FromKw">
									<span>批量选择原库位</span>
								</button>
							</li>
							<li>
								<button type="button" class="btn btn-system btn-sm" id="ToKw">
									<span>批量选择移动到库位</span>
								</button>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#itemDetail_tabView" data-toggle="tab">详情</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="itemDetail_tabView"  class="tab-pane fade in active"> 
					<div class="pageContent">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="notUpItemSave">
									<span>新增未上架材料</span>
								</button>
								<button type="button" class="btn btn-system" id="whPosiItemSave">
									<span>新增库位材料</span>
								</button>
								<button type="button" class="btn btn-system" id="itemDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="importExcel">
									<span>从Excel导入</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script>
// 全局设置动态获取的qtyCal,qtyNo
var qtyCal1,qtyNo1;
$(function() {
	$("#code").openComponent_wareHouse({
		showValue:"${wareHouse.code}",
		showLabel:"${wareHouse.desc1}",
		condition:{
			code:"${wareHouse.code}",
			czybh:"${sessionScope.USER_CONTEXT_KEY.czybh}"
		}
	});

	/* 原始form数据(放在锁定组件后面，否则后面比较数据会出错) */
	var originalData = $("#page_form").jsonForm();
	
	var gridOption = {
		url:"${ctx}/admin/whPosiBal/goMoveJqGrid",// 通过sql获得已上架和未上架数量
		postData:{code:"${wareHouse.code}",itCode:"${wareHouse.itCode}",pk:"${wareHouse.pk}",qtyAll:"${wareHouse.qtyAll}"},
		height : $(document).height()-$("#content-list").offset().top - 40,
		rowNum : 10000000,
		cellEdit: true,// 编辑行需要先打开cellEdit
		cellsubmit:"clientArray",// 当单元格发生变化后不直接发送请求、"remote"默认直接发送请求
		styleUI: "Bootstrap",
		colModel : [
			{name: "code", index: "code", width: 80, label: "仓库编号", sortable: true, align: "left", hidden:true},
			{name: "itcode", index: "itcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 240, label: "材料名称", sortable: true, align: "left"},
			{name: "fromkw", index: "fromkw", width: 80, label: "原库位", sortable: true, align: "left", formatter:setOndblclick},
			{name: "fromkwpk", index: "fromkwpk", width: 80, label: "原库位pk", sortable: true, align: "left", hidden:true},
			{name: "tokw", index: "tokw", width: 80, label: "移动到库位", sortable: true, align: "left", formatter:setOndblclick},
			{name: "tokwpk", index: "tokwpk", width: 80, label: "移动到库位pk", sortable: true, align: "left", hidden:true},
			{name: "qtycal", index: "qtycal", width: 80, label: "已上架数量", sortable: true, align: "right"},
			{name: "qtyno", index: "qtyno", width: 80, label: "未上架数量", sortable: true, align: "right"},
			{name: "qtyall", index: "qtyall", width: 80, label: "产品余额", sortable: true, align: "right", hidden:true},
			{name: "qtymove", index: "qtymove", width: 80, label: "移动数量", sortable: true, align: "right", editable:true, editrules: {number:true, required:true}},
		],
		loadonce: true,
		loadComplete: function(){
			var jsonFirst = {
				code:"${wareHouse.code}",
				itcode:"${wareHouse.itCode}",
				itemdescr:"${wareHouse.itemDescr}",
				fromkw:"${wareHouse.whpDescr}",
				fromkwpk:"${wareHouse.pk}",
				tokw:"",
				qtyall:"${wareHouse.qtyAll}",
				qtymove:0
			};
			Global.JqGrid.updRowData("dataTable", 1, jsonFirst);// 设置其他数据
			originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
			changeCode();			
		},
		onCellSelect:function(rowid){
			$("#dataTable").jqGrid("setSelection",rowid);
		},
	};
	
	Global.JqGrid.initJqGrid("dataTable", gridOption);

	// 遍历设置FromKw
	$("#FromKw").on("click",function(){
		var ids = $("#dataTable").getDataIDs();

		Global.Dialog.showDialog("down",{
			title:"请选择库位编号",
			url:"${ctx}/admin/wareHousePosi/goCode",
			height: 600,
			width: 645,
			returnFun:function(returnData){
				if(returnData){
					var json = {
						fromkw:returnData.desc1,
						fromkwpk:returnData.pk,
					};
					// 获取所有id然后遍历设置数据
					for (var i in ids) {
						var row = $("#dataTable").jqGrid("getRowData", ids[i]);
						getCal(null, json.fromkwpk, row.itcode);
						json.qtycal = qtyCal1;
						console.log(qtyCal1);
						Global.JqGrid.updRowData("dataTable", ids[i], json);
					}
				}
			}
		});
	});

	$("#ToKw").on("click",function(){
		var ids = $("#dataTable").getDataIDs();

		Global.Dialog.showDialog("down",{
			title:"请选择库位编号",
			url:"${ctx}/admin/wareHousePosi/goCode",
			height: 600,
			width: 645,
			returnFun:function(returnData){
				if(returnData){
					var json = {
						tokw:returnData.desc1,
						tokwpk:returnData.pk,
					};
					for (var i in ids) {
						Global.JqGrid.updRowData("dataTable", ids[i], json);
					}
				}
			}
		});
	});

	$("#notUpItemSave").on("click",function(){
		var itcodes = Global.JqGrid.allToJson("dataTable","itcode");
		var keys = itcodes.keys;//获取itcode的数组

		Global.Dialog.showDialog("notUpItemSave", {
			title : "未上架材料明细--增加",
			url : "${ctx}/admin/whPosiBal/goNotUpItemSave",
			postData: {m_umState:"R", code: $("#code").val(), haveSelect:keys},
			height : 600,
			width : 800,
			cache:false,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							code:"${wareHouse.code}",
							itcode:v.itcode,
							itemdescr:v.itemdescr,
							fromkw:"",
							fromkwpk:0,
							tokw:"",
							tokwpk:0,
							qtycal:0,
							qtyno:v.qtycal,
							qtymove:0
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					  	changeCode();
				  	});
				}
			}
		});
	});

	$("#whPosiItemSave").on("click",function(){
		var itcodes = Global.JqGrid.allToJson("dataTable","itcode");
		var keys = itcodes.keys;
		var fromkwpks = Global.JqGrid.allToJson("dataTable", "fromkwpk");
		var keys2 = fromkwpks.keys;

		Global.Dialog.showDialog("whPosiItemSave", {
			title : "库位材料明细--增加",
			url : "${ctx}/admin/whPosiBal/goNotUpItemSave",
			postData: {m_umState:"V", code: $("#code").val(), haveSelect:keys, haveFromKwPk:keys2},
			height : 600,
			width : 800,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						getCal("${wareHouse.code}", v.fromkw, v.itcode);
						var json = {
							code:"${wareHouse.code}",
							itcode:v.itcode,
							itemdescr:v.itemdescr,
							fromkw:v.fromkwdescr,
							fromkwpk:v.fromkw,
							tokw:"",
							tokwpk:0,
							qtycal:v.qtycal,
							qtyno:qtyNo1,
							qtymove:0
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					  	changeCode();
				  	});
				}
			}
		});
	});

	$("#itemDelete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				// 删除后选择第一条数据
				var rowIds = $("#dataTable").jqGrid("getDataIDs");
				$("#dataTable").jqGrid("setSelection",rowIds[0]);
				changeCode();
			},
			cancel: function () {
				return true;
			}
		});
		
	});

	$("#importExcel").on("click",function(){
		var itcodes = Global.JqGrid.allToJson("dataTable","itcode");
		var keys = itcodes.keys;

		Global.Dialog.showDialog("importExcel", {
			title : "库位移动明细--从Excel导入",
			url : "${ctx}/admin/whPosiBal/goImportExcel",
			postData: {m_umState:"A", code: $("#code").val(), itCode:keys},
			height : 500,
			width : 700,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							code:"${wareHouse.code}",
							itcode:v.itcode,
							itemdescr:v.itemdescr,
							fromkw:v.fromkw,
							fromkwpk:v.fromkwpk,
							tokw:v.tokw,
							tokwpk:v.tokwpk,
							qtycal:v.qtycal,
							qtyno:v.qtyno,
							qtymove:v.qtymove
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					  	changeCode();
				  	});
				}
			}
		});
	});

	$("#saveBtn").on("click",function(){
		doSave();
	});
	
});
// 根据表格内是否还有数据对仓库表格输入框进行限制
function changeCode(){
	var ids = $("#dataTable").getDataIDs();
	$("#openComponent_wareHouse_code").prop("readonly",false).next().prop("disabled",false);
	if (ids.length != 0) {
		$("#openComponent_wareHouse_code").prop("readonly",true).next().prop("disabled",true);
	}
}

// 添加点击事件 --cellvalue：要被格式化的值；options：对数据进行格式化时的参数设置；rowObject：行数据；
function setOndblclick(cellvalue, options, rowObject){
	return "<input id='"+options.rowId+options.colModel.name+"'  ondblclick='goComponent_wareHouse(\""
		+options.rowId+"\",\""+options.colModel.name+"\",);' readonly='readonly' value='"+cellvalue+"'/>";
}

// 跳转选择库位信息界面
function goComponent_wareHouse(ids,name){
	Global.Dialog.showDialog("down",{
		title:"请选择库位编号",
		url:"${ctx}/admin/wareHousePosi/goCode",
		height: 600,
		width: 645,
		returnFun:function(returnData){
			if(returnData){
				var json;
				if ("fromkw" == name) {
					var row = $("#dataTable").jqGrid("getRowData", ids);
					getCal(null, returnData.pk, row.itcode);
					json = {
						fromkw:returnData.desc1,
						fromkwpk:returnData.pk,
						qtycal:qtyCal1
					};
				} else {
					json = {
						tokw:returnData.desc1,
						tokwpk:returnData.pk,
					};
				}
				Global.JqGrid.updRowData("dataTable", ids, json);
			}
		}
	});
}

// 根据材料和库位获取已上架数量和未上架数量
function getCal(code,pk,itCode){
	$.ajax({
		url:"${ctx}/admin/whPosiBal/getCal",
		type:"post",
		data:{code:code,pk:pk,itCode:itCode},
		dataType:"json",
		cache:false,
		async:false,// (默认: true) 默认设置下，所有请求均为异步请求。
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
	    },
		success:function(obj){
			qtyCal1 = obj.datas.qtycal;
			qtyNo1 = obj.datas.qtyno;
		}
	});
}

// 表格验证
function jqGridValidate(){
	var param= Global.JqGrid.allToJson("dataTable");
	var rowIds = $("#dataTable").jqGrid("getDataIDs");
	for ( var i in param.datas) {
		var data = param.datas[i];
		if (data.fromkwpk == "0") {
			data.fromkwpk = "";
		}
		if (data.tokwpk == "0") {
			data.tokwpk = "";
		}
		if (data.fromkwpk == "" && data.tokwpk == "") {
			art.dialog({
				content: "原库位和移动到库位不能同时为空",
				width: 200
			});
			$("#dataTable").jqGrid("setSelection",rowIds[i]);
			return false;
		}
		if (data.fromkwpk == data.tokwpk) {
			art.dialog({
				content: "原库位和移动到库位不能相同",
				width: 200
			});
			$("#dataTable").jqGrid("setSelection",rowIds[i]);
			return false;
		}
		if (data.qtymove==""||data.qtymove<=0) {
			art.dialog({
				content: "移动数量要大于0",
				width: 200
			});
			$("#dataTable").jqGrid("setSelection",rowIds[i]);
			return false;
		}
		if (data.tokwpk!="") {
			$.ajax({
				url:"${ctx}/admin/whPosiBal/checkToWkPk",
				type: "post",
				data: {code: $("#code").val(), pk:data.tokwpk},
				dataType: "json",
				// cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
			    },
				success: function(obj){
					if (!obj.rs) {
						showAjaxHtml({"hidden": false, "msg": "仓库中没有【"+$("#"+rowIds[i]+"tokw").val()+"】这个库位"});
						$("#dataTable").jqGrid("setSelection",rowIds[i]);
						return false;
					}
			    }
			});
		}
		if (data.fromkwpk == "") {
			if (eval(data.qtymove)>eval(data.qtyno)) {// eval()函数用于在不引用任何特定对象的情况下计算代码字符串。
				art.dialog({
					content: "移动数量不能大于未上架数量",
					width: 200
				});
				$("#dataTable").jqGrid("setSelection",rowIds[i]);
				return false;
			}
		} else {
			$.ajax({
				url:"${ctx}/admin/whPosiBal/checkToWkPk",
				type: "post",
				data: {code: $("#code").val(), pk:data.fromkwpk},
				dataType: "json",
				// cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
			    },
				success: function(obj){
					if (!obj.rs) {
						showAjaxHtml({"hidden": false, "msg": "仓库中没有【"+$("#"+rowIds[i]+"fromkw").val()+"】这个库位"});
						$("#dataTable").jqGrid("setSelection",rowIds[i]);
						return false;
					}
			    }
			});
			if (eval(data.qtymove)>eval(data.qtycal)) {
				art.dialog({
					content: "移动数量不能大于已上架数量",
					width: 200
				});
				$("#dataTable").jqGrid("setSelection",rowIds[i]);
				return false;
			}
		}
	}
	return true;
}

function doSave(){
	var param= Global.JqGrid.allToJson("dataTable");
	param.m_umState = "${wareHouse.m_umState}";

	if (param.datas.length == 0) {
		art.dialog({
			content: "详情表格数据为空",
			width: 200
		});
		return;
	}
	
	if (!jqGridValidate()) return;
	
	$.ajax({
		url:"${ctx}/admin/whPosiBal/doSave",
		type: "post",
		data: param,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
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
}
</script>
</html>
