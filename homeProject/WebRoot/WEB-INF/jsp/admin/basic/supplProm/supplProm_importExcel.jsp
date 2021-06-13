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
							onclick="doExcelNow('供应商促销管理导入模板','modelDataTable')">
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
	<script type="text/javascript">
		// 判断是否存在无效的数据
		var hasInvalid=true;
		//tab分页
		$(document).ready(function() {  
		     //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				height:$(document).height()-$("#content-list").offset().top-40,
				rowNum:100000,
				colModel : [
					{name: "isinvaliddescr", index: "isinvaliddescr", width: 120, label: "是否有效数据", sortable: true, align: "left"},
					{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
					{name: "itemtype3", index: "itemtype3", width: 120, label: "品牌", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 80, label: "品名", sortable: true, align: "left"},
					{name: "model", index: "model", width: 80, label: "型号", sortable: true, align: "left"},
					{name: "uom", index: "uom", width: 80, label: "单位", sortable: true, align: "left"},
					{name: "itemsize", index: "itemsize", width: 80, label: "规格", sortable: true, align: "left"},
					{name: "unitprice", index: "unitprice", width: 80, label: "有家直供价", sortable: true, align: "right"},
					{name: "promprice", index: "promprice", width: 80, label: "活动价", sortable: true, align: "right"},
					{name: "cost", index: "cost", width: 80, label: "常规成本", sortable: true, align: "right"},
					{name: "promcost", index: "promcost", width: 80, label: "活动成本", sortable: true, align: "right"},
					// {name: "grossprofit", index: "grossprofit", width: 80, label: "活动毛利", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 160, label: "备注", sortable: true, align: "left"},
		        ]
			});
			//初始化excel模板表格
			Global.JqGrid.initJqGrid("modelDataTable",{
				colModel : [
					{name: "itemtype3", index: "itemtype3", width: 120, label: "品牌", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 80, label: "品名", sortable: true, align: "left"},
					{name: "model", index: "model", width: 80, label: "型号", sortable: true, align: "left"},
					{name: "uom", index: "uom", width: 80, label: "单位", sortable: true, align: "left"},
					{name: "itemsize", index: "itemsize", width: 80, label: "规格", sortable: true, align: "left"},
					{name: "unitprice", index: "unitprice", width: 80, label: "有家直供价", sortable: true, align: "right"},
					{name: "promprice", index: "promprice", width: 80, label: "活动价", sortable: true, align: "right"},
					{name: "cost", index: "cost", width: 80, label: "常规成本", sortable: true, align: "right"},
					{name: "promcost", index: "promcost", width: 80, label: "活动成本", sortable: true, align: "right"},
					{name: "grossprofit", index: "grossprofit", width: 80, label: "活动毛利", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 160, label: "备注", sortable: true, align: "left"},
		        ]
			});
			$("#modelDataTable").addRowData(1, {
				"itemtype3": "材料类型3名称",
				"itemdescr": "材料名称",
				"model": "仿毛灰",
				"uom": "件",
				"itemsize": "5*5",
				"unitprice": 16,
				"promprice": 10,
				"cost": 10,
				"promcost": 8,
				"grossprofit": null,
				"remarks": "说明",
			}, "last");
			$("#modelDataTable").addRowData(1, {
				"itemtype3": "",
				"itemdescr": "",
				"model": "",
				"uom": "",
				"itemsize": "",
				"unitprice": null,
				"promprice": null,
				"cost": null,
				"promcost": null,
				"grossprofit": null,
				"remarks": "",
			}, "last");
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
				$.ajax({
					url: "${ctx}/admin/supplProm/loadExcel",
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
							$("#dataTable").clearGridData();
							
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
			var returnData=$("#dataTable").jqGrid("getRowData");
			for (var i = returnData.length - 1; i >= 0; i--) {
				if ("1" == returnData[i].isinvalid) {
					art.dialog({
						content: "存在无效的数据，无法导入！"
					});
					return false;
				}
			}
			Global.Dialog.returnData = returnData;
			closeWin();
		}
	</script>
</body>
</html>
