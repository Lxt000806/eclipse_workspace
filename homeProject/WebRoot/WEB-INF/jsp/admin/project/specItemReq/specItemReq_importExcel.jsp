<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
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
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<div class="form-group" >
						<input type="file" id="file" name="file" style="width: 100%;" 
							accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
					</div>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<div class="pageContent">
				<div class="btn-panel">
					<div class="btn-group-sm" style="padding-top: 5px;">
						<button type="button" class="btn btn-system" id="loadData" onclick="loadData()">
							<span>加载数据</span>
						</button>
						<button type="button" class="btn btn-system" id="importData" onclick="importData()">
							<span>导入数据</span>
						</button>
						<button type="button" class="btn btn-system" id="outModelData" 
							onclick="doExcelNow('集成拆单导入模板','modelDataTable')">
							<span>下载模板</span>
						</button>
					</div>
				</div>
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
$(document).ready(function() {  
     //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-40,
		rowNum:10000,
		colModel : [
			{name: "isinvaliddescr", index: "isinvaliddescr", width: 130, label: "是否有效数据", sortable: true, align: "left"},
			{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
			{name: "iscupboard", index: "iscupboard", width: 80, label: "是否橱柜", sortable: true, align: "left",hidden:true},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 80, label: "是否橱柜", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 90, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品pk", sortable: true, align: "left",hidden:true},
			{name: "intprodpkdescr", index: "intprodpkdescr", width: 80, label: "成品名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right"},
			{name: "appqty", index: "appqty", width: 90, label: "已申请数量", sortable: true, align: "right"},
			{name: "movecost", index: "movecost", width: 60, label: "移动平均成本", sortable: true, align: "right",hidden:true},
			{name: "price", index: "price", width: 60, label: "单价", sortable: true, align: "right",hidden:true},
			{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right",hidden:true},
			{name: "totalcost", index: "totalcost", width: 90, label: "成本总价", sortable: true, align: "right",hidden:true},
			{name: "remark", index: "remark", width: 200, label: "描述", sortable: true, align: "left"},
			{name: "isexist", index: "isexist", width: 90, label: "是否下单", sortable: true, align: "right",hidden:true},
        ]
      
	});

	//初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		colModel : [
			{name: "productname", index: "productname", width: 80, label: "成品名称", sortable: true, align: "left"},
			{name: "item", index: "item", width: 80, label: "项目", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "采购数量", sortable: true, align: "right"},
			{name: "remarks", index: "remarks", width: 266, label: "备注", sortable: true, align: "left"},
        ]
	});
	$("#modelDataTable").addRowData(1, {"item":"材料1", "itemcode":"000000"}, "last");
	return false;  
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
		formData.append("itemCodes","${itemCodes}");
		formData.append("isCupboard","${isCupboard}");
		formData.append("custCode", "${custCode}");
		$.ajax({
			url: "${ctx}/admin/specItemReq/loadExcel",
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
	
	// 重复的数据做删除操作
	var returnData=$("#dataTable").jqGrid("getRowData");
	/*for (var i = returnData.length - 1; i >= 0; i--) {
		if ("1" == returnData[i].isinvalid) {
			returnData.splice(i, 1);
		} else if ("1" == returnData[i].isexist) {// 如果数量小于已下单数量，将数量改成已下单数量，并做提醒
			if (returnData[i].qty < returnData[i].appqty) {
				returnData[i].qty = returnData[i].appqty;
				returnData[i].selectBG = 1;
			}
		}
	}*/

	Global.Dialog.returnData = returnData;
	closeWin();
}
</script>
</html>
