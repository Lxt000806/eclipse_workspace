<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>WareHouse明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
var hasInvalid=true;
//tab分页
$(document).ready(function() {  

	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-72,
		styleUI: 'Bootstrap',
		rowNum:10000,
		colModel : [
				{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
		   		{name: "isinvaliddescr", index: "isinvaliddescr", width: 113, label: "数据是否有效", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 137, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "amount", index: "amount", width: 100, label: "付款金额", sortable: true, align: "right", sum: true},
				{name: "rcvactdescr", index: "rcvactdescr", width: 115, label: "收款账户", sortable: true, align: "left"},
				{name: "rcvact", index: "rcvact", width: 115, label: "收款账户", sortable: true, align: "left",hidden:true},
				{name: "posdescr", index: "posdescr", width: 144, label: "POS机", sortable: true, align: "left"},
				{name: "poscode", index: "poscode", width: 144, label: "POS机", sortable: true, align: "left",hidden:true},
				{name: "procedurefee", index: "procedurefee", width: 78, label: "手续费", sortable: true, align: "right"},
				{name: "payno", index: "payno", width: 129, label: "收款单号", sortable: true, align: "left"},
				{name: "type", index: "type", width: 70, label: "类型", sortable: true, align: "left",hidden:true},
				{name: "typedescr", index: "typedescr", width: 70, label: "类型", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 230, label: "备注", sortable: true, align: "left"},
    	]  
	});
    //初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		        {name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 137, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "amount", index: "amount", width: 100, label: "付款金额", sortable: true, align: "right"},
				{name: "rcvact", index: "rcvact", width: 115, label: "收款账户", sortable: true, align: "left"},
				{name: "poscode", index: "poscode", width: 144, label: "POS机", sortable: true, align: "left"},
				{name: "procedurefee", index: "procedurefee", width: 78, label: "手续费", sortable: true, align: "right"},
				{name: "payno", index: "payno", width: 129, label: "收款单号", sortable: true, align: "left"},
				{name: "type", index: "type", width: 70, label: "类型", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 230, label: "备注", sortable: true, align: "left"},
           ]
	});
	
	$("#modelDataTable").addRowData(1, {
	    "custcode":"CT000001",
	    "date":"2020-11-21",
	    "amount":"5000",
	    "rcvact":"01",
	    "poscode":"12",
	    "procedurefee":10,
	    "payno":"20200422",
	    "type":"1",
	    "remarks":"remarks"
	}, "last");
  
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
             url: "${ctx}/admin/custPay/loadExcel",
             type: "POST",
             data: formData,
             contentType: false,
             processData: false,
             success: function (data) {
                 if(data.hasInvalid) hasInvalid=true;
                 else hasInvalid=false;
                 if (data.success == false) {
                    art.dialog({content: data.returnInfo});   
                 }else{
                     var grid = $("#dataTable");
                     grid.clearGridData();
                     $.each(data.datas, function(index, value) {
                         grid.addRowData(index + 1, value, "last");
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
	if($("#dataTable").jqGrid('getGridParam','records')==0){
			art.dialog({
				content: "请先载入要进行批量导入的数据！"
		});
		return;
	}
	if(hasInvalid){
		art.dialog({
			content: "存在无效的数据，无法导入！"
		});
		return;
	}
	var isinvalid= Global.JqGrid.allToJson("dataTable","isinvalid");
	var arr= isinvalid.fieldJson.split(",");
	for(var i=0;i<arr.length;i++){
		if(arr[i]=="0"){
			art.dialog({content: "存在无效的数据，无法导入！"});
			return;
		}
	}
	var param=Global.JqGrid.allToJson("dataTable");  //JSON.stringify($('#dataTable').jqGrid("getRowData")) ;                       
	Global.Form.submit("page_form","${ctx}/admin/custPay/doSaveBatch",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload: function() { closeWin(false) }
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
		<div class="btn-panel pull-left">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system"
				    onclick="doExcelNow('付款信息导入模板','modelDataTable')">下载模板</button>
				<button type="button" class="btn btn-system" onclick="loadData()">加载数据</button>
				<button type="button" class="btn btn-system" onclick="importData()">导入数据</button>
			</div>
		</div>
		<div class="query-form" style="padding: 0px;border: none">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<div class="form-group">
					<input type="file" style="position:relative;top:2px;left:20px;" id="file" name="file"
					    accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
				</div>
			</form>

		</div>
		<div class="pageContent">
			<!--panelBar-->
			<div id="content-list">
				<table id="dataTable"></table>

			</div>
			<div style="display: none">
				<table id="modelDataTable"></table>
				<div id="modelDataTable"></div>
			</div>
		</div>
	</div>
</body>
</html>
