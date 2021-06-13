<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_custWorker.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="exportData" id="exportData">
					<input type="hidden" name="jsonString" value="" /> 
					<input type="hidden" name="m_umState" id="m_umState" value="${itemSendNode.m_umState }" /> 
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>编号</label> 
							<input type="text" id="code" name="code" style="width:160px;" value="${itemSendNode.code}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>名称</label> 
							<input type="text" id="descr" name="descr" style="width:160px;" value="${itemSendNode.descr}">
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>类型</label>
							<house:xtdm id="type" dictCode="SENDNODETYPE" value="${itemSendNode.type}"></house:xtdm>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>适用施工分类</label>
							<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" value="${itemSendNode.workerClassify}"></house:xtdm>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 160px;"></select>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">材料配送节点明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button" id="d_addBtn"
										class="btn btn-system viewFlag" onclick="d_add()" >
										<span>新增 </span>
									</button>
									<button style="align:left" type="button" id="d_updateBtn"
										class="btn btn-system viewFlag" onclick="d_update()">
										<span>编辑 </span>
									</button>
									<button style="align:left" type="button" id="d_delBtn"
										class="btn btn-system viewFlag" onclick="d_del()">
										<span>删除 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system " onclick="d_view()">
										<span>查看 </span>
									</button>
								</div>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable_detail"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
		if("M"==="${itemSendNode.m_umState}"){
			$("#code").attr("readonly",true);
			Global.LinkSelect.setSelect({firstSelect:"itemType1",firstValue:"${itemSendNode.itemType1}"});
		}else if("V"==="${itemSendNode.m_umState}"){
			Global.LinkSelect.setSelect({firstSelect:"itemType1",firstValue:"${itemSendNode.itemType1}"});
			$("#saveBtn").hide();
			$("#d_addBtn").hide();
			$("#d_updateBtn").hide();
			$("#d_delBtn").hide();
		}
		$("#page_form").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				code:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				descr:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				type:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				workerClassify:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
				itemType1:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
			}
		});

		var gridOption1 = {
			url : "${ctx}/admin/itemSendNode/goDetailJqGrid",
			postData : {
				code : "${itemSendNode.code}"
			},
			sortable : true,
			height : 350,
			rowNum : 10000000,
			colModel : [ 
			{name : "confitemtype",index : "confitemtype",width : 120,label : "施工材料分类",sortable : true,align : "left",hidden : true}, 
			{name : "confitemtypedescr",index : "confitemtypedescr",width : 100,label : "施工材料分类",sortable : true,align : "left"}, 
			{name : "beginnode",index : "beginnode",width : 180,label : "起始节点",sortable : true,align : "left",hidden:true}, 
			{name : "beginnodedescr",index : "beginnodedescr",width : 180,label : "起始节点",sortable : true,align : "left"}, 
			{name : "begindatetype",index : "begindatetype",width : 80,label : "起始时间类型",sortable : true,align : "left",hidden:true}, 
			{name : "begindatetypedescr",index : "begindatetypedescr",width : 120,label : "起始时间类型",sortable : true,align : "left"}, 
			{name : "beginadddays",index : "beginadddays",width : 100,label : "起始增加天数",sortable : true,align : "right"}, 
			{name : "endnode",index : "endnode",width : 180,label : "结束节点",sortable : true,align : "left",hidden:true}, 
			{name : "endnodedescr",index : "endnodedescr",width : 180,label : "结束节点",sortable : true,align : "left"}, 
			{name : "enddatetype",index : "enddatetype",width : 80,label : "结束时间类型",sortable : true,align : "left",hidden:true}, 
			{name : "enddatetypedescr",index : "enddatetypedescr",width : 120,label : "结束时间类型",sortable : true,align : "left"}, 
			{name : "endadddays",index : "endadddays",width : 100,label : "结束增加天数",sortable : true,align : "right"}, 
			{name : "paynum",index : "paynum",width : 100,label : "付款期数",sortable : true,align : "right"}, 
			{name : "lastupdate",index : "lastupdate",width : 120,label : "最后更新时间",sortable : true,align : "left",formatter : formatTime,hidden : true}, 
			{name : "lastupdatedby",index : "lastupdatedby",width : 101,label : "最后更新人员",sortable : true,align : "left",hidden : true}, 
			{name : "expired",index : "expired",width : 74,label : "是否过期",sortable : true,align : "left",hidden : true}, 
			{name : "actionlog",index : "actionlog",width : 76,label : "操作日志",sortable : true,align : "left",hidden : true} ],
		};
		Global.JqGrid.initJqGrid("dataTable_detail", gridOption1);
	});

	function d_add() {
		Global.Dialog.showDialog("detailAdd", {
			title : "材料配送节点明细--增加",
			url : "${ctx}/admin/itemSendNode/goDetailAdd",
			postData : {
				m_umState : "A"
			},
			height : 400,
			width : 720,
			returnFun : function(v) {
				var json = {
					confitemtype : v[0].confItemType,
					confitemtypedescr : v[0].confItemTypeDescr,
					beginnode : v[0].beginNode,
					beginnodedescr : v[0].beginNodeDescr,
					begindatetype : v[0].beginDateType,
					begindatetypedescr : v[0].beginDateTypeDescr,
					beginadddays : v[0].beginAddDays,
					endnode : v[0].endNode,
					endnodedescr : v[0].endNodeDescr,
					enddatetype : v[0].endDateType,
					enddatetypedescr : v[0].endDateTypeDescr,
					endadddays :v[0].endAddDays,
					paynum: v[0].payNum,
					expired : "F",
					actionlog : "ADD",
					lastupdate : new Date(),
					lastupdatedby : "${sessionScope.USER_CONTEXT_KEY.czybh}"
				};
				Global.JqGrid.addRowData("dataTable_detail", json);
			}
		});
	}
	function d_update() {
		var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow");
		var ret = $("#dataTable_detail").jqGrid('getRowData', id);
		if (!id) {
			art.dialog({
				content : "请选择一条记录！",
				width : 200
			});
			return false;
		}
		Global.Dialog.showDialog("detailUpdate", {
			title : "材料配送节点明细--编辑",
			url : "${ctx}/admin/itemSendNode/goDetailUpdate",
			postData : {
				m_umState : "M",
				confItemType:ret.confitemtype,
				beginNode:ret.beginnode,
				beginDateType:ret.begindatetype,
				beginAddDays:ret.beginadddays,
				endNode:ret.endnode,
				endDateType:ret.enddatetype,
				endAddDays:ret.endadddays,
				payNum: ret.paynum
			},
			height : 400,
			width : 720,
			returnFun : function(v) {
				var json = {
					confitemtype : v[0].confItemType,
					confitemtypedescr : v[0].confItemTypeDescr,
					beginnode : v[0].beginNode,
					beginnodedescr : v[0].beginNodeDescr,
					begindatetype : v[0].beginDateType,
					begindatetypedescr : v[0].beginDateTypeDescr,
					beginadddays : v[0].beginAddDays,
					endnode : v[0].endNode,
					endnodedescr : v[0].endNodeDescr,
					enddatetype : v[0].endDateType,
					enddatetypedescr : v[0].endDateTypeDescr,
					endadddays :v[0].endAddDays,
					paynum: v[0].payNum,
					expired : "F",
					actionlog : "EDIT",
					lastupdate : new Date(),
					lastupdatedby : "${sessionScope.USER_CONTEXT_KEY.czybh}",
					remark : v[0].remarks
				};
				$("#dataTable_detail").setRowData(id, json);
			}
		});
	}

	function d_view() {
		var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow");
		var ret = $("#dataTable_detail").jqGrid('getRowData', id);
		console.log(ret.enddatetype);
		if (!id) {
			art.dialog({
				content : "请选择一条记录！",
				width : 200
			});
			return false;
		}
		Global.Dialog.showDialog("detailView", {
			title : "材料配送节点明细--查看",
			url : "${ctx}/admin/itemSendNode/goDetailView",
			postData : {
				m_umState : "V",
				confItemType:ret.confitemtype,
				beginNode:ret.beginnode,
				beginDateType:ret.begindatetype,
				beginAddDays:ret.beginadddays,
				endNode:ret.endnode,
				endDateType:ret.enddatetype,
				endAddDays:ret.endadddays,
				payNum:ret.paynum
			},
			height : 400,
			width : 720,
		});
	}
	//删除
	function d_del() {
		var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow");
		if (!id) {
			art.dialog({
				content : "请选择一条记录进行删除操作！"
			});
			return false;
		}
		art.dialog({
			content : "是否确认要删除",
			lock : true,
			ok : function() {
				Global.JqGrid.delRowData("dataTable_detail", id);
			},
			cancel : function() {
				return true;
			}
		});
	}
	
	
		$("#saveBtn").on("click", function() {
			$("#page_form").bootstrapValidator("validate");
			if (!$("#page_form").data("bootstrapValidator").isValid())
				return;

			var datas = $("#page_form").jsonForm();
			var param = Global.JqGrid.allToJson("dataTable_detail");
			
			//处理json key 大小写与存储过程不一样
			var json = JSON.parse(param.detailJson);
			/* for(var i = 0; i < json.length; i++) {
	            json[i]["baseCheckItemCode"] = json[i]["basecheckitemcode"];
	            delete json[i]["basecheckitemcode"];  
	            json[i]["baseCheckItemDescr"] = json[i]["basecheckitemdescr"];
	            delete json[i]["basecheckitemdescr"];  
	            json[i]["offerPri"] = json[i]["offerpri"];
	            delete json[i]["offerpri"];  
	        } */
	        param.detailJson=JSON.stringify(json);
	        
			if (param.datas.length == 0) {
				art.dialog({
					content : "请先添加材料配送没电明细信息",
					width : 220
				});
				return;
			}
			/* 将datas（json）合并到param（json）中 */
			$.extend(param, datas);
			doSave(param);
		});
	
	function doSave(param) {
		$.ajax({
			url : "${ctx}/admin/itemSendNode/doSaveForProc",
			type : "post",
			data : param,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
					});
				}
			}
		});
	}
</script>
</html>
