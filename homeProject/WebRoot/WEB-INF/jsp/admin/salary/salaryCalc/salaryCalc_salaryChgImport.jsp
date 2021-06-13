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
	<title>薪酬调整导入</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
var hasInvalid = true;

$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:50,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left", hidden:true},
			{name:"isinvaliddescr", index:"isinvaliddescr", width:120, label:"错误信息", sortable:true ,align:"left", },
			{name:"salaryemp", index:"salaryemp", width:80, label:"工号", sortable:true ,align:"left",},
			{name:"idnum", index:"idnum", width:120, label:"身份证号", sortable:true ,align:"left",},
			{name:"salaryitem", index:"salaryitem", width:80, label:"薪酬项目", sortable:true ,align:"left",},
			{name:"salaryitemdescr", index:"salaryitemdescr", width:80, label:"薪酬项目名称", sortable:true ,align:"left",},
			{name:"adjustamount", index:"adjustamount", width:80, label:"金额", sortable:true ,align:"left",},
			{name:"remarks", index:"remarks", width:200, label:"备注", sortable:true ,align:"left",},
		],
	}; 
	
	Global.JqGrid.initJqGrid("modelDataTable", {
		height: $(document).height() - $("#content-list").offset().top - 70,
		colModel: [
			{name: "idnum", index: "idnum", width: 149, label: "身份证号", sortable: true, align: "left"},
			{name: "adjustamount", index: "adjustamount", width: 90, label: "金额", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 85, label: "备注", sortable: true, align: "left"},
        ]
	});
	
	$("#modelDataTable").addRowData(1, {
		"idnum": "350500000000000006",
		"adjustamount": "500",
		"remarks": "填写备注",
	}, "last");
	function checkBtn(v,x,r){
		if(r.isshow == "1"){
			return "<input type='checkbox' checked onclick='checkRow("+x.rowId+",this)' />";
		} else {
			return "<input type='checkbox' onclick='checkRow("+x.rowId+",this)' />";
		}
	}
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});

function checkRow(id,e){
	if($(e).is(':checked')){
		$("#dataTable").setCell(id, 'isshow', "1");
	} else {
		
		$("#dataTable").setCell(id, 'isshow', "0");
	}
}

function check() {
	var fileName = $("#file").val();
	var reg = /^.+\.(?:xls|xlsx)$/i;
	if (fileName.length == 0) {
		art.dialog({
			content: "请选择需要导入的excel文件！"
		});
		return false;
	} else if (fileName.match(reg) == null) {
		art.dialog({
			content: "文件格式不正确！请导入正确的excel文件！"
		});
		return false;
	}
	return true;
}
    
function loadData() {
	if (check()) {
		var salaryItem = $.trim($("#salaryItem").val());
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
		if(salaryItem == ""){
			art.dialog({
				content:"请选择调整的薪酬项目",
			});
			return;
		}
		var salaryItemDescr = $("#salaryItem").find("option:selected").text();
		var formData = new FormData();
		formData.append("file", document.getElementById("file").files[0]);
		formData.append("salaryItem", salaryItem);
		formData.append("salaryScheme", salaryScheme);
		formData.append("salaryMon", salaryMon);
        $.ajax({
			url: "${ctx}/admin/salaryCalc/loadExcel",
			type: "POST",
			data: formData,
			contentType: false,
			processData: false,
			success: function (data) {
				if (data.hasInvalid) hasInvalid = true;
				else hasInvalid = false;
				
				if (data.success == false) {
					art.dialog({
						content: data.returnInfo
					});
				} else {	
					$("#dataTable").clearGridData();
					$.each(data.datas, function (k, v) {
						v.salaryitem = salaryItem;
						v.salaryitemdescr = salaryItemDescr;
						$("#dataTable").addRowData(k + 1, v, "last");
					})
				}
			},
			error: function () {
				art.dialog({
					content: "上传文件失败!"
				});
			}
		});
	}
}


function importData(){
	var param= Global.JqGrid.allToJson("dataTable");
	if(param.datas.length==0){
		art.dialog({
			content:"请先加载数据",
		});
		return;
	}
	
	if(hasInvalid){
		art.dialog({
			content:"存在无效数据请核对信息",
		});
		return;
	}
	
	Global.Form.submit("page_form","${ctx}/admin/salaryCalc/doImportSalaryChg",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					closeWin();
				}
			});				
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				width:200
			});
		}
	});
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li class="form-validate">
							<label style="width:80px">计算月份</label>
							<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon where status <> '3'"  
								 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMon }" disabled="true"></house:dict>
						</li>
						<li class="form-validate">
							<label style="width:80px">薪酬方案</label>
							<house:dict id="salaryScheme" dictCode="" sql=" select pk, descr from tsalaryScheme "  
								 sqlValueKey="pk" sqlLableKey="descr" value="${salaryData.salaryScheme }" disabled="true"></house:dict>
						</li>
						<li class="form-validate">
							<label style="width:80px"><span class="required">*</span>薪酬项目</label>
							<house:dict id="salaryItem" dictCode="" sql=" select code, descr from tSalaryItem where expired ='F' and IsAdjustable = '1'"  
								 sqlValueKey="code" sqlLableKey="descr" ></house:dict>
						</li>
					</ul>
				</form>
			</div>
			<div class="container-fluid">  
				<div class="btn-panel pull-left">
					<div class="btn-group-sm">
						<button type="button" class="btn btn-system " onclick="loadData()">加载数据</button>
						<button type="button" class="btn btn-system " onclick="importData()">导入数据</button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('薪酬调整导入模板','modelDataTable')"
							style="margin-right: 15px">下载模板
 						</button>
					</div>
				</div>
				<div class="query-form" style="padding: 0px;border: none">
					<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<div class="form-group">
							<label for="inputfile"></label>
							<input type="file" style="position: relative;top: -12px;" id="file" name="file"
									accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
						</div>
					</form>
				</div>
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system" >
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
								<table id="dataTablePager"></table>
							</div>
						    <div style="display: none">
						      <table id="modelDataTable"></table>
						      <div id="modelDataTable"></div>
						    </div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
