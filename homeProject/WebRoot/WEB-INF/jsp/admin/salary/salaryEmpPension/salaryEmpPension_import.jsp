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
	<title>工资补贴从excel导入</title>
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
			{name: "salaryemp", index: "salaryemp", width: 149, label: "工号", sortable: true, align: "left"},
			{name: "salaryempname", index: "salaryempname", width: 149, label: "员工姓名", sortable: true, align: "left"},
			{name: "type", index: "type", width: 90, label: "补贴科目PK", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 90, label: "补贴科目", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 90, label: "金额", sortable: true, align: "right"},
			{name: "beginmon", index: "beginmon", width: 90, label: "开始月份", sortable: true, align: "left"},
			{name: "endmon", index: "endmon", width: 90, label: "结束月份", sortable: true, align: "left"},
		],
	}; 
	
	Global.JqGrid.initJqGrid("modelDataTable", {
		height: $(document).height() - $("#content-list").offset().top - 70,
		colModel: [
			{name: "salaryemp", index: "salaryemp", width: 149, label: "工号", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 90, label: "补贴科目", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 90, label: "金额", sortable: true, align: "right"},
			{name: "beginmon", index: "beginmon", width: 90, label: "开始月份", sortable: true, align: "left"},
        ]
	});
	
	$("#modelDataTable").addRowData(1, {
		"salaryemp": "04100",
		"typedescr": "职务补贴",
		"amount": "500",
		"beginmon": "202104",
	}, "last");

	Global.JqGrid.initJqGrid("dataTable",gridOption);
});

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
		var formData = new FormData();
		formData.append("file", document.getElementById("file").files[0]);
        $.ajax({
			url: "${ctx}/admin/salaryEmpPension/loadExcel",
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
	
	Global.Form.submit("page_form","${ctx}/admin/salaryEmpPension/doImportSalaryEmpPension",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					closeWin(true);
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
				<form action="" method="post" id="page_form" class="form-search" hidden="true">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
					</ul>
				</form>
			</div>
			<div class="container-fluid">  
				<div class="btn-panel pull-left">
					<div class="btn-group-sm">
						<button type="button" class="btn btn-system " onclick="loadData()">加载数据</button>
						<button type="button" class="btn btn-system " onclick="importData()">导入数据</button>
						<button type="button" class="btn btn-system " onclick="doExcelNow('工资补贴管理导入模板','modelDataTable')"
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
