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
					<ul class="ul-form">
						<li>
							<label>工人编号</label> 
							<input type="text" id="code" name="code" style="width:160px;"
								readonly="readonly" value="${worker.code}" />
						</li>
						<li>
							<label>工人姓名</label> 
							<input type="text" id="nameChi" name="nameChi" style="width:160px;"
								readonly="readonly" value="${worker.nameChi}" />
						</li>
						<li>
							<label>工种</label> 
							<input type="text" id="workType12Descr" name="workType12Descr"
								style="width:160px;" readonly="readonly"
								value="${worker.workType12Descr}">
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_workerWorkType12" data-toggle="tab">承接工种明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_workerWorkType12" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button" class="btn btn-system viewFlag" onclick="d_add()">
										<span>新增 </span>
									</button>
									<button style="align:left" type="button" class="btn btn-system viewFlag" onclick="d_del()">
										<span>删除 </span>
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

		var gridOption1 = {
			url : "${ctx}/admin/worker/goWorkerWorkType12JqGrid",
			postData : {
				workerCode : "${worker.code}"
			},
			sortable : true,
			height : 340,
			rowNum : 10000000,
			colModel : [ 
			{name : "code",index : "code",width : 120,label : "工种分类12编号",sortable : true,align : "left"}, 
			{name : "worktype12descr",index : "worktype12descr",width : 120,label : "工种分类12描述",sortable : true,align : "left"}, 
			{name : "lastupdate",index : "lastupdate",width : 120,label : "最后更新时间",sortable : true,align : "left",formatter : formatTime,hidden : true}, 
			{name : "lastupdatedby",index : "lastupdatedby",width : 101,label : "最后更新人员",sortable : true,align : "left",hidden : true}, 
			{name : "expired",index : "expired",width : 74,label : "是否过期",sortable : true,align : "left",hidden : true}, 
			{name : "actionlog",index : "actionlog",width : 76,label : "操作日志",sortable : true,align : "left",hidden : true} ],
		};
		Global.JqGrid.initJqGrid("dataTable_detail", gridOption1);
	});

	function d_add() {
		console.log(JSON.stringify($("#dataTable_detail").getCol("code",false)));
		Global.Dialog.showDialog("workerWorkType12Add", {
			title : "承接工种明细--增加",
			url : "${ctx}/admin/worker/goWorkerWorkType12Add",
			postData : {
				workType12Strings : $("#dataTable_detail").getCol("code",false).toString()
			},
			height : 600,
			width : 360,
			returnFun : function(v) {
				 for(var i=0;i<v.length;i++){
					var json = {
						code : v[i].code.trim(),
						worktype12descr : v[i].worktype12descr
					}
					Global.JqGrid.addRowData("dataTable_detail", json);
				} 
			}
		});
	}
	
	//删除
	function d_del() {
		var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow");
		var rowData = $("#dataTable_detail").jqGrid("getRowData", id);
		if (!id) {
			art.dialog({
				content : "请选择一条记录进行删除操作！"
			});
			return false;
		}
		art.dialog({
			content : "是否删除"+rowData.worktype12descr+"工种",
			lock : true,
			ok : function() {
				Global.JqGrid.delRowData("dataTable_detail", id);
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
        param.detailJson=JSON.stringify(json);
        
		if (param.datas.length == 0) {
			art.dialog({
				content : "请先添加承接工种明细信息",
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
			url : "${ctx}/admin/worker/doSaveWorkerWorkType12ForProc",
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
