<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>导入劳务分包明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
		.row {
			margin: 0;
		}
		.col-xs-3,.col-xs-9 {
		    padding: 0px 10px;
		}
		.form-group {
			margin: 0;
		}
		.container-fluid {
			margin: 0 !important;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		    	<div class="row">
		    		<div class="col-xs-3">
				      	<div class="btn-group-xs" >
			      			<button type="button" class="btn btn-system" id="loadData" onclick="loadData()">
								<span>加载数据</span>
							</button>
							<button type="button" class="btn btn-system" id="importData" onclick="importData()">
								<span>导入数据</span>
							</button>
							<button type="button" class="btn btn-system" id="outModelData" 
								onclick="doExcelNow('开票明细导入模板','modelDataTable')">
								<span>下载模板</span>
							</button>
						</div>
		    		</div>
		    		<div class="col-xs-9">
						<div class="form-group" >
							<input type="file" id="file" name="file" style="width: 100%;" 
								accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
						</div>
		    		</div>
		    	</div>
			</div>
		</div>
		<div class="container-fluid" >  
			<div class="pageContent">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
				</form>
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
				<div style="display: none">
					<table id= "modelDataTable"></table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
// 判断是否存在无效的数据
var hasInvalid=true;
//tab分页
$(function(){
     //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-40,
		rowNum:10000,
		loadonce: true,
		colModel : [
			{name: "isinvaliddescr", index: "isinvaliddescr", width: 160, label: "是否有效数据", align: "left"},
			{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", align: "left",hidden:true},
			{name: "custcode", index: "custcode", width: 80, label: "客户编号", align: "left"},
			{name: "date", index: "date", width: 100, label: "劳务开票日期", align: "left", formatter: formatDate},
			{name: "amount", index: "amount", width: 100, label: "劳务开票金额", align: "right"},
			{name: "laborcompny", index: "laborcompny", width: 100, label: "劳务分包公司", align: "left"}
        ]
	});
	//初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		colModel : [
			{name: "custcode", index: "custcode", width: 80, label: "客户编号", align: "left"},
			{name: "date", index: "date", width: 80, label: "劳务开票日期", align: "left", formatter: formatDate},
			{name: "amount", index: "amount", width: 80, label: "劳务开票金额", align: "right"},
			{name: "laborcompny", index: "laborcompny", width: 80, label: "劳务分包公司", align: "left"}
        ]
	});
	$("#modelDataTable").addRowData(1, {
		"custcode": "CT000001",
		"date": "2019-12-10",
		"amount": "8176",
		"laborcompny": "九天建筑"
	}, "last");
	$("#deleteRow").on("click", function () {
		var ret=selectDataTableRow();
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
				var rowIds = $("#dataTable").jqGrid("getDataIDs");
				$("#dataTable").jqGrid("setSelection",rowIds[0]);
				if (hasInvalid) {
					var returnData=$("#dataTable").jqGrid("getRowData");
					hasInvalid=false;
					for (var i = returnData.length - 1; i >= 0; i--) {
						if ("1" == returnData[i].isinvalid) {
							hasInvalid=true;
						}
					}
				}
			},
			cancel: function () {
				return true;
			}
		});
	});
});
//加载文件验证
function check(){
	var fileName=$("#file").val();
	var reg=/^.+\.(?:xls|xlsx)$/i;
    if(fileName.length==0){
    	art.dialog({
			content: "请选择需要导入的excel文件！"
		});
        return false;
    }else if(fileName.match(reg)==null){
   		art.dialog({
			content: "文件格式不正确！请导入正确的excel文件！"
		});
		return false;
    }
    return true;
}
//加载excel
function loadData(){
	if(check()){
		var formData = new FormData();
		formData.append("file", document.getElementById("file").files[0]);
		$.ajax({
			url: "${ctx}/admin/custTax/loadExcel_laborInvoice", 
			type: "POST",
			data: formData,
			contentType: false,
			processData: false,
			success: function (data) {
				if(data.hasInvalid) hasInvalid=true;
				else hasInvalid=false;
				if (data.success == false) {
					art.dialog({
						content: data.returnInfo
					});         
				}else{     
					$("#dataTable").clearGridData();//清空表格
					$.each(data.datas,function(k,v){
						$("#dataTable").addRowData(k+1,v,"last");
					});
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
//导入数据
function importData(){
	if($("#dataTable").jqGrid("getGridParam","records")==0){
		art.dialog({
			content: "请先载入要进行批量导入的预算数据！"
		});
		return;
	}
	if(hasInvalid){
		art.dialog({
			content: "存在无效的数据，无法导入！"
		});
		return;
	}
	var param= Global.JqGrid.allToJson("dataTable");
	Global.Form.submit("page_form", "${ctx}/admin/custTax/doCustLaborInvoice", param, function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 700,
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
</script>
</html>
