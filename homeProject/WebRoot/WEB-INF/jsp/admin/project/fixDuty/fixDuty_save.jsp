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
					<input type="hidden" name="dataChanged" value="0" /> 
					<input type="hidden" id="workType12" name="workType12" value="${fixDuty.workType12 }" /> 
					<input type="hidden" id="custCode" name="custCode" value="${fixDuty.custCode }" />
					<input type="hidden" id="appWorkerCode" name="appWorkerCode" value="${fixDuty.appWorkerCode }" />
					<input type="hidden" id="appManType" name="appManType" value="${fixDuty.appManType }" />
					<input type="hidden" id="custWkPk" name="custWkPk" value="${fixDuty.custWkPk }" />
					<ul class="ul-form">
						<li class="form-validate"><label>定责申请编号</label> <input
							type="text" id="no" name="no" style="width:160px;"
							readonly="readonly" value="${fixDuty.no}" placeholder="保存时生成" />
						</li>
						<li><label><span class="required">*</span>安排工人</label> <input
							type="text" id="custWorker" name="custWorker"
							style="width:160px;" readonly="readonly"
							value="${fixDuty.custWkPk}">
						</li>
						<li><label>楼盘地址</label> <input type="text" id="address"
							name="address" style="width:160px;" readonly="readonly"
							value="${fixDuty.address}">
						</li>
						<li><label>申请人</label> <input type="text" id="appCzy"
							name="appCzy" style="width:160px;" readonly="readonly"
							value="${fixDuty.appCzy}">
						</li>
						<li><label>人工金额</label> <input type="text" id="offerPrj"
							name="offerPrj" style="width:160px;" readonly="readonly"
							value="${fixDuty.offerPrj}" />
						</li>
						<li><label>材料金额</label> <input type="text" id="material"
							name="material" style="width:160px;" readonly="readonly"
							value="${fixDuty.material}">
						</li>
						<li><label class="control-textarea">备注</label> <textarea
								id="remarks" name="remarks" rows="4">${fixDuty.remarks }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">定责明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="d_add()">
										<span>新增 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="d_update()">
										<span>编辑 </span>
									</button>
									<button style="align:left" type="button"
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

		if ("A" == "${fixDuty.m_umStatus}") {
			$("#custWorker").openComponent_custWorker({
				showValue : "${fixDuty.workerName}",
				callBack : function(ret) {
					$("#address").val(ret.address);
					$("#workType12").val(ret.worktype12);
					$("#custCode").val(ret.custcode);
					$("#appWorkerCode").val(ret.workercode);
					$("#custWkPk").val(ret.pk);
				}
			});
		} else {
			$("#custWorker").openComponent_custWorker({
				showValue : "${fixDuty.workerName}",
				disabled : true
			});
		}

		var gridOption1 = {
			url : "${ctx}/admin/fixDuty/goDetailJqGrid",
			postData : {
				no : "${fixDuty.m_umStatus}" == "A" ? "保存时生成" : "${fixDuty.no}"
			},
			sortable : true,
			height : 230,
			rowNum : 10000000,
			colModel : [ 
			{name : "basecheckitemcode",index : "basecheckitemcode",width : 120,label : "基础结算项目",sortable : true,align : "left",hidden : true}, 
			{name : "basecheckitemdescr",index : "basecheckitemdescr",width : 280,label : "基础结算项目",sortable : true,align : "left"}, 
			{name : "qty",index : "qty",width : 80,label : "数量",sortable : true,align : "right"}, 
			{name : "offerpri",index : "offerpri",width : 70,label : "人工单价",sortable : true,align : "right"}, 
			{name : "material",index : "material",width : 70,label : "材料单价",sortable : true,align : "right"}, 
			{name : "allofferpri",index : "allofferpri",width : 70,label : "人工总价",sortable : true,align : "right",sum : true}, 
			{name : "allmaterial",index : "allmaterial",width : 70,label : "材料总价",sortable : true,align : "right",sum : true}, 
			{name : "remark",index : "remark",width : 200,label : "描述",sortable : true,align : "left"}, 
			{name : "lastupdate",index : "lastupdate",width : 120,label : "最后更新时间",sortable : true,align : "left",formatter : formatTime,hidden : true}, 
			{name : "lastupdatedby",index : "lastupdatedby",width : 101,label : "最后更新人员",sortable : true,align : "left",hidden : true}, 
			{name : "expired",index : "expired",width : 74,label : "是否过期",sortable : true,align : "left",hidden : true}, 
			{name : "actionlog",index : "actionlog",width : 76,label : "操作日志",sortable : true,align : "left",hidden : true} ],
		};
		Global.JqGrid.initJqGrid("dataTable_detail", gridOption1);
	});

	function d_add() {
		if ($("#address").val() == "") {
			art.dialog({
				content : "请先选择安排工人",
			});
			return;
		}
		Global.Dialog.showDialog("detailAdd", {
			title : "定责明细--增加",
			url : "${ctx}/admin/fixDuty/goDetailAdd",
			postData : {
				m_umState : "A",
				fromPage: "worker",
				workType12 : $("#workType12").val(),
				custCode : $("#custCode").val()
			},
			height : 400,
			width : 720,
			returnFun : function(v) {
				var json = {
					basecheckitemdescr : v[0].baseCheckItemDescr,
					basecheckitemcode : v[0].baseCheckItemCode,
					material : v[0].material,
					offerpri : v[0].offerPri,
					qty : v[0].qty,
					allofferpri : parseFloat(v[0].offerPri)* parseFloat(v[0].qty),
					allmaterial : parseFloat(v[0].material)* parseFloat(v[0].qty),
					expired : "F",
					actionlog : "ADD",
					lastupdate : new Date(),
					lastupdatedby : "${sessionScope.USER_CONTEXT_KEY.czybh}",
					remark : v[0].remarks
				};
				Global.JqGrid.addRowData("dataTable_detail", json);
				$("#offerPrj").val(
						$("#dataTable_detail").getCol("allofferpri", false,
								"sum"));
				$("#material").val(
						$("#dataTable_detail").getCol("allmaterial", false,
								"sum"));
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
			title : "定责明细--编辑",
			url : "${ctx}/admin/fixDuty/goDetailUpdate",
			postData : {
				m_umState : "M",
				fromPage: "worker",
				workType12 : $("#workType12").val(),
				custCode : $("#custCode").val(),
				baseItemCode: "",
                baseItemDescr: "",
				baseCheckItemCode : ret.basecheckitemcode,
				baseCheckItemDescr : ret.basecheckitemdescr,
				qty : ret.qty,
				offerPri : ret.offerpri,
				material : ret.material,
				remarks : ret.remark
			},
			height : 400,
			width : 720,
			returnFun : function(v) {
				var json = {
					basecheckitemdescr : v[0].baseCheckItemDescr,
					basecheckitemcode : v[0].baseCheckItemCode,
					material : v[0].material,
					offerpri : v[0].offerPri,
					qty : v[0].qty,
					allofferpri : parseFloat(v[0].offerPri)* parseFloat(v[0].qty),
					allmaterial : parseFloat(v[0].material)* parseFloat(v[0].qty),
					expired : "F",
					actionlog : "EDIT",
					lastupdate : new Date(),
					lastupdatedby : "${sessionScope.USER_CONTEXT_KEY.czybh}",
					remark : v[0].remarks
				};
				$("#dataTable_detail").setRowData(id, json);
				$("#dataChanged").val("1");
				var allOfferPri = $("#dataTable_detail").getCol("allofferpri",
						false, "sum");
				var allMaterial = $("#dataTable_detail").getCol("allmaterial",
						false, "sum");
				$("#dataTable_detail").footerData("set", {
					"allofferpri" : myRound(allOfferPri, 2)
				});
				$("#dataTable_detail").footerData("set", {
					"allmaterial" : myRound(allMaterial, 2)
				});
				$("#offerPrj").val(allOfferPri);
				$("#material").val(allMaterial);
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
			title : "定责明细--查看",
			url : "${ctx}/admin/fixDuty/goDetailView",
			postData : {
				m_umState : "V",
				fromPage: "worker",
				baseItemCode: "",
                baseItemDescr: "",
				baseCheckItemCode : ret.basecheckitemcode,
				baseCheckItemDescr : ret.basecheckitemdescr,
				qty : ret.qty,
				remarks : ret.remark
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
				$("#offerPrj").val(
						$("#dataTable_detail").getCol("allofferpri", false,
								"sum"));
				$("#material").val(
						$("#dataTable_detail").getCol("allmaterial", false,
								"sum"));
				$("#dataChanged").val("1");
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
			for(var i = 0; i < json.length; i++) {
	            json[i]["baseCheckItemCode"] = json[i]["basecheckitemcode"];
	            delete json[i]["basecheckitemcode"];  
	            json[i]["baseCheckItemDescr"] = json[i]["basecheckitemdescr"];
	            delete json[i]["basecheckitemdescr"];  
	            json[i]["offerPri"] = json[i]["offerpri"];
	            delete json[i]["offerpri"];  
	        }
	        param.detailJson=JSON.stringify(json);
	        
			if (param.datas.length == 0) {
				art.dialog({
					content : "请先添加定责明细信息",
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
			url : "${ctx}/admin/fixDuty/doSaveForProc",
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
