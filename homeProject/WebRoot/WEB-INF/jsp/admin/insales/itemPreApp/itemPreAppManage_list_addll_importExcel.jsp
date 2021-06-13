<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>领料预申请导入Excel页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("itemAppImportDataTable",{
				height:430,
				styleUI:"Bootstrap",
				rowNum:10000,
				colModel:[
					{name: "isinvaliddescr", index: "isinvaliddescr", width: 145, label: "是否有效数据", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 92, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 139, label: "名称", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 120, label: "装修区域", sortable: true, align: "left"},
					{name: "intproddescr", index: "intproddescr", width: 120, label: "集成成品", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 73, label: "采购数量", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 266, label: "备注", sortable: true, align: "left"},
					{name: "cost", index: "cost", width: 74, label: "成本", sortable: true, align: "right", hidden:true},
					{name: "projectcost", index: "projectcost", width: 111, label: "项目经理结算价", sortable: true, align: "right", hidden:true},
					{name: "uomdescr", index: "uomdescr", width: 63, label: "单位", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 96, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 94, label: "材料类型2", sortable: true, align: "left"},
					{name: "itemtype3descr", index: "itemtype3descr", width: 93, label: "材料类型3", sortable: true, align: "left"},
					{name: "sqlcodedescr", index: "sqlcodedescr", width: 93, label: "品牌", sortable: true, align: "left"},
					{name: "sendtypedescr", index: "sendtypedescr", width: 93, label: "发货类型", sortable: true, align: "left"},
					{name: "color", index: "color", width: 70, label: "颜色", sortable: true, align: "left"},
					{name: "model", index: "model", width: 100, label: "型号", sortable: true, align: "left"},
					{name: "sizedesc", index: "sizedesc", width: 73, label: "规格", sortable: true, align: "left"},
					{name: "barcode", index: "barcode", width: 82, label: "条码", sortable: true, align: "left"},
					{name: "isfixpricedescr", index: "isfixpricedescr", width: 85, label: "是否固定价", sortable: true, align: "left"},
					{name: "price", index: "price", width: 68, label: "单价", sortable: true, align: "right"},
					
					{name: "no", index: "no", width: 68, label: "no", sortable: true, align: "right", hidden:true},
					{name: "reqpk", index: "reqpk", width: 68, label: "reqpk", sortable: true, align: "right", hidden:true},
					{name: "fixareapk", index: "fixareapk", width: 68, label: "fixareapk", sortable: true, align: "right", hidden:true},
					{name: "intprodpk", index: "intprodpk", width: 68, label: "intprodpk", sortable: true, align: "right", hidden:true},
					{name: "reqqty", index: "reqqty", width: 68, label: "reqqty", sortable: true, align: "right", hidden:true},
					{name: "sendedqty", index: "sendedqty", width: 68, label: "sendedqty", sortable: true, align: "right", hidden:true},
					{name: "declareqty", index: "declareqty", width: 68, label: "declareqty", sortable: true, align: "right", hidden:true},
					{name: "supplcodedescr", index: "supplcodedescr", width: 68, label: "supplcodedescr", sortable: true, align: "right", hidden:true},
					{name: "reqprocesscost", index: "reqprocesscost", width: 68, label: "reqprocesscost", sortable: true, align: "right", hidden:true},
					{name: "applyqty", index: "applyqty", width: 68, label: "applyqty", sortable: true, align: "right", hidden:true},
					{name: "confirmedqty", index: "confirmedqty", width: 68, label: "confirmedqty", sortable: true, align: "right", hidden:true},
					{name: "allcost", index: "allcost", width: 68, label: "allcost", sortable: true, align: "right", hidden:true},
					{name: "allprojectcost", index: "allprojectcost", width: 68, label: "allprojectcost", sortable: true, align: "right", hidden:true},
					{name: "weight", index: "weight", width: 68, label: "weight", sortable: true, align: "right", hidden:true},
					{name: "numdescr", index: "numdescr", width: 68, label: "numdescr", sortable: true, align: "right", hidden:true},
					{name: "sendtype", index: "sendtype", width: 68, label: "sendtype", sortable: true, align: "right", hidden:true},
					{name: "supplcode", index: "supplcode", width: 68, label: "supplcode", sortable: true, align: "right", hidden:true},
					{name: "whcode", index: "whcode", width: 68, label: "whcode", sortable: true, align: "right", hidden:true},
					{name: "productname", index: "productname", width: 68, label: "productname", sortable: true, align: "right", hidden:true}
					
				],
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#itemAppImportDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("itemAppImportDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("itemAppImportDataTable", v);
					});
				}
			});
			Global.JqGrid.initJqGrid("itemAppTempDataTable",{
				styleUI:"Bootstrap",
				colModel:[
					{name: "productname", index: "productname", width: 73, label: "成品名称", sortable: true, align: "left"},
					{name: "item", index: "item", width: 73, label: "项目", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 92, label: "材料编号", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 73, label: "采购数量", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 266, label: "备注", sortable: true, align: "left"}
				]
			});   
			$("#itemAppTempDataTable").addRowData(1, {
				"itemcode":"000000",
				"descr":"材料1"
			});   
		});
		function loadData(){
			if(check()){
				var formData = new FormData();
				formData.append("file", document.getElementById("file").files[0]);
				formData.append("itemType1", "${data.itemType1}");
				formData.append("itemType2", "${data.itemType2}");
				formData.append("itemCodes", "${data.itemCodes}");
				formData.append("no", "${data.no}");
				formData.append("custCode", "${data.custCode}");
				$.ajax({
					url: "${ctx}/admin/itemPreApp/loadExcel",
				  	type: "POST",
				  	data: formData,
				  	contentType: false,
				  	processData: false,
				  	success: function (data){
				  		if(data.success){
				  			if(!data.hasInvalid){
				  				$("#hasInvalid").val("F");
				  			}else{
				  				$("#hasInvalid").val("T");
				  			}
							$("#itemAppImportDataTable").clearGridData();
							$.each(data.datas, function (k, v) {
								$("#itemAppImportDataTable").addRowData(k + 1, v, "last");
							});
				  		}
				  		
			  			art.dialog({
			  				content:data.returnInfo
			  			});
				  	},
					error: function () {
						art.dialog({
							content: "上传文件失败!"
						});
					}
				});
			}
		}
		function importData(flag){
			if($("#hasInvalid").val() == "T"){
				art.dialog({
					content:"存在无效的数据，无法导入",
					ok:function(){
						closeWin(false);
					}
				});
				return;
			}
			var rets = Global.JqGrid.allToJson("itemAppImportDataTable");
			
			if(flag != true){
				var i = 0;
				for(;i < rets.datas.length;i++){
					if(rets.datas[i].productname != ""){
						break;
					}
				}
				if(i >= rets.datas.length){
					art.dialog({
						content : "成品名称全部为空,是否导入",
						ok : function(){
							importData(true);
						},
						cancel : function(){}
					});
					return;
				}
			}
    		Global.Dialog.returnData = rets;
    		closeWin();
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
		</script>
	</head>
	<body>
		<div class="body-box-form">
		    <div class="btn-panel pull-left">
		    	<div class="btn-group-sm">
					<button id="addDataBut" type="button" class="btn btn-system" onclick="loadData()">加载数据</button>	
					<button id="importDataBut" type="button" class="btn btn-system" onclick="importData()">导入数据</button>	
					<button type="button" class="btn btn-system " 
							onclick="doExcelNow('领料管理明细批量导入模板','itemAppTempDataTable')">
						下载模板
				    </button>
					<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)"
						    style="margin-right: 15px">
						关闭
					</button>	
				</div>
			</div>
			<div class="query-form" style="padding:0px;border: none;">
				<form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
     				<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="hasInvalid" name="hasInvalid" value="T"/>
					<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}"/>
					<div class="form-group">
						<label for="inputfile"></label>
						<input type="file" style="position: relative;top: -12px;" id="file" name="file"
							   accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
					</div>
				</form>
			</div>
			<table id="itemAppImportDataTable"></table>
			<div style="display:none">
				<table id="itemAppTempDataTable"></table>
			</div>
		</div>
	</body>
</html>
