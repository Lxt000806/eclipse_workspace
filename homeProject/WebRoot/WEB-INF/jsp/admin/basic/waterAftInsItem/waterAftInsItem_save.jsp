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
					<input type="hidden" name="m_umState" id="m_umState" value="${waterAftInsItem.m_umState }" /> 
					<ul class="ul-form">
						<li class="form-validate">
							<label><span class="required">*</span>编号</label> 
							<input type="text" id="no" name="no" style="width:160px;" placeholder="保存时生成" value="${waterAftInsItem.no}" readonly="true"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 160px;"></select>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>材料类型2</label>
							<select id="itemType2" name="itemType2" style="width: 160px;"></select>
						</li>
						<li>
							<label class="control-textarea">说明</label>
							<textarea id="remarks" name="remarks" rows="2">${waterAftInsItem.remarks}</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">水电后期安装材料配置明细</a></li>
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
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
		if("M"==="${waterAftInsItem.m_umState}"){
			$("#code").attr("readonly",true);
			Global.LinkSelect.setSelect({firstSelect:"itemType1",firstValue:"${waterAftInsItem.itemType1}",secondSelect:"itemType2",secondValue:"${waterAftInsItem.itemType2}"});
			$("#itemType1").attr("disabled",true);
			$("#itemType2").attr("disabled",true);
		}else if("V"==="${waterAftInsItem.m_umState}"){
			Global.LinkSelect.setSelect({firstSelect:"itemType1",firstValue:"${waterAftInsItem.itemType1}",secondSelect:"itemType2",secondValue:"${waterAftInsItem.itemType2}"});
			$("#itemType1").attr("disabled",true);
			$("#itemType2").attr("disabled",true);
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
				itemType2:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
			}
		});

		var gridOption1 = {
			url : "${ctx}/admin/waterAftInsItem/goDetailJqGrid",
			postData : {
				no : "${waterAftInsItem.no}"
			},
			sortable : true,
			height : 350,
			rowNum : 10000000,
			colModel : [ 
				{name : "descr",index : "descr",width : 120,label : "材料",sortable : true,align : "left"}, 
				{name : "uom",index : "uom",width : 100,label : "单位",sortable : true,align : "left", hidden:true}, 
				{name : "uomdescr",index : "uomdescr",width : 100,label : "单位",sortable : true,align : "left"}, 
				{name : "lastupdate",index : "lastupdate",width : 120,label : "最后更新时间",sortable : true,align : "left",formatter : formatTime,hidden : true}, 
				{name : "lastupdatedby",index : "lastupdatedby",width : 101,label : "最后更新人员",sortable : true,align : "left",hidden : true}, 
				{name : "expired",index : "expired",width : 74,label : "是否过期",sortable : true,align : "left",hidden : true}, 
				{name : "actionlog",index : "actionlog",width : 76,label : "操作日志",sortable : true,align : "left",hidden : true} ],
		};
		Global.JqGrid.initJqGrid("dataTable_detail", gridOption1);
	});

	function d_add() {
		if($("#itemType2").val()==''){
			art.dialog({
				content : "请选择材料类型2！",
				width : 200
			});
			return false;
		}
		Global.Dialog.showDialog("detailAdd", {
			title : "水电后期安装材料明细--增加",
			url : "${ctx}/admin/waterAftInsItem/goDetailAdd",
			postData : {
				m_umState : "A"
			},
			height : 300,
			width : 400,
			returnFun : function(v) {
				var json = {
					descr : v[0].descr,
					uom :v[0].uom,
					uomdescr : v[0].uomDescr,
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
			title : "水电后期安装材料明细--编辑",
			url : "${ctx}/admin/waterAftInsItem/goDetailUpdate",
			postData : {
				m_umState : "M",
				descr:ret.descr,
				uom:ret.uom.trim(),
			},
			height : 300,
			width : 400,
			returnFun : function(v) {
				var json = {
					descr : v[0].descr,
					uom : v[0].uom,
					uomdescr : v[0].uomDescr,
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
		if (!id) {
			art.dialog({
				content : "请选择一条记录！",
				width : 200
			});
			return false;
		}
		Global.Dialog.showDialog("detailView", {
			title : "水电后期安装材料明细--查看",
			url : "${ctx}/admin/waterAftInsItem/goDetailView",
			postData : {
				m_umState : "V",
				descr:ret.descr,
				uom:ret.uom.trim(),
			},
			height : 300,
			width : 400,
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
	        param.detailJson=JSON.stringify(json);
			if (param.datas.length == 0) {
				art.dialog({
					content : "请先添加水电后期安装材料明细信息",
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
			url : "${ctx}/admin/waterAftInsItem/doSaveForProc",
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
