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
	var itemType1='${itemChg.itemType1}'.trim();
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-72,
		styleUI: 'Bootstrap',
		rowNum:10000,
		colModel : [
			{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
		    {name: "isinvaliddescr", index: "isinvaliddescr", width: 113, label: "数据是否有效", sortable: true, align: "left"},
			{name: "barcode", index: "barcode", width: 100, label: "条码", sortable: true, align: "left"},
			{name: "remcode", index: "remcode", width: 80, label: "助记码", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 100, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype2", index: "itemtype2", width: 80, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemtype2descr", index: "itemtype2descr", width: 94, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3descr", index: "itemtype3descr", width: 92, label: "材料类型3", sortable: true, align: "left"},
			{name: "itemtype3", index: "itemtype3", width: 100, label: "材料类型3", sortable: true, align: "left",hidden:true},
			{name: "descr", index: "descr", width: 140, label: "材料名称", sortable: true, align: "left"},
			{name: "sqlcode", index: "sqlcode", width: 100, label: "品牌", sortable: true, align: "left",hidden:true},
			{name: "sqldescr", index: "sqldescr", width: 110, label: "品牌", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 100, label: "单位", sortable: true, align: "left",hidden:true},
			{name: "uomdescr", index: "uomdescr", width: 43, label: "单位", sortable: true, align: "left"},
			{name: "sizedesc", index: "sizedesc", width: 62, label: "规格", sortable: true, align: "left"},
			{name: "model", index: "model", width: 60, label: "型号", sortable: true, align: "left"},
			{name: "color", index: "color", width: 38, label: "颜色", sortable: true, align: "left"},
			{name: "supplcode", index: "supplcode", width: 69, label: "商家编码", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 111, label: "商家名称", sortable: true, align: "left"},
			{name: "price", index: "price", width: 60, label: "现价", sortable: true, align: "left"},
			{name: "marketprice", index: "marketprice", width: 57, label: "市场价", sortable: true, align: "left"},
			{name: "isfixprice", index: "isfixprice", width: 88, label: "是否固定价格", sortable: true, align: "left",hidden:true},
			{name: "isfixpricedescr", index: "isfixpricedescr", width: 80, label: "是否固定价格", sortable: true, align: "left"},
			{name: "cost", index: "cost", width: 54, label: "成本", sortable: true, align: "left"},
			{name: "projectcost", index: "projectcost", width: 91, label: "项目经理结算价", sortable: true, align: "left"},
			{name: "commitype", index: "commitype", width: 70, label: "提佣类型", sortable: true, align: "left",hidden:true},
			{name: "commitypedescr", index: "commitypedescr", width: 70, label: "提佣类型", sortable: true, align: "left"},
			{name: "commiperc", index: "commiperc", width: 63, label: "提佣比例", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 159, label: "材料描述", sortable: true, align: "left"},
			{name: "sendtypedescr", index: "sendtypedescr", width: 63, label: "发货类型", sortable: true, align: "left"},
			{name: "sendtype", index: "sendtype", width: 63, label: "发货类型", sortable: true, align: "left",hidden:true},
			{name: "whdescr", index: "whdescr", width: 79, label: "发货仓库", sortable: true, align: "left"},
			{name: "whcode", index: "whcode", width: 79, label: "发货仓库", sortable: true, align: "left",hidden:true},
			{name: "minqty", index: "minqty", width: 80, label: "最小库存量", sortable: true, align: "left"},
			{name: "hassample", index: "hassample", width: 75, label: "是否上样", sortable: true, align: "left",hidden:true},
			{name: "isinv", index: "isinv", width: 75, label: "是否库存管理", sortable: true, align: "left",hidden:true},
			{name: "hassampledescr", index: "hassampledescr", width: 75, label: "是否上样", sortable: true, align: "left"},
			{name: "installfeetype", index: "installfeetype", width: 72, label: "安装费类型", sortable: true, align: "left",hidden:true},
			{name: "installfeetypedescr", index: "installfeetypedescr", width: 72, label: "安装费类型", sortable: true, align: "left"},
			{name: "installfee", index: "installfee", width: 78, label: "安装费单价", sortable: true, align: "right"},
			{name: "buyer1", index: "buyer1", width: 70, label: "买手1", sortable: true, align: "left",hidden:true},
			{name: "buyer1descr", index: "buyer1descr", width: 70, label: "买手1", sortable: true, align: "left"},
			{name: "whfeetype", index: "whfeetype", width: 70, label: "仓储费类型", sortable: true, align: "left",hidden:true},
			{name: "whfeetypedescr", index: "whfeetypedescr", width: 70, label: "仓储费类型", sortable: true, align: "left"},
		 	{name: "whfee", index: "whfee", width: 70, label: "仓储费", sortable: true, align: "right"}, 
		 	{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
		 	{name: "custtype", index: "custtype", width: 70, label: "客户类型", sortable: true, align: "right",hidden:true}, 
    	]  
	});
    //初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "remcode", index: "remcode", width: 80, label: "助记码", sortable: true, align: "left"},
			{name: "barcode", index: "barcode", width: 100, label: "条码", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 57, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 94, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3descr", index: "itemtype3descr", width: 92, label: "材料类型3", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 140, label: "材料名称", sortable: true, align: "left"},
			{name: "sqldescr", index: "sqldescr", width: 110, label: "品牌", sortable: true, align: "left"},
			{name: "uomdescr", index: "uomdescr", width: 43, label: "单位", sortable: true, align: "left"},
			{name: "sizedesc", index: "sizedesc", width: 62, label: "规格", sortable: true, align: "left"},
			{name: "model", index: "model", width: 60, label: "型号", sortable: true, align: "left"},
			{name: "color", index: "color", width: 38, label: "颜色", sortable: true, align: "left"},
			{name: "supplcode", index: "supplcode", width: 69, label: "供应商编号", sortable: true, align: "left"},
			{name: "price", index: "price", width: 60, label: "现价", sortable: true, align: "left"},
			{name: "marketprice", index: "marketprice", width: 57, label: "市场价", sortable: true, align: "left"},
			{name: "isfixpricedescr", index: "isfixpricedescr", width: 80, label: "是否固定价格", sortable: true, align: "left"},
			{name: "cost", index: "cost", width: 54, label: "成本", sortable: true, align: "left"},
			{name: "projectcost", index: "projectcost", width: 91, label: "项目经理结算价", sortable: true, align: "left"},
			{name: "commitypedescr", index: "commitypedescr", width: 70, label: "提成类型", sortable: true, align: "left"},
			{name: "commiperc", index: "commiperc", width: 63, label: "提成比例", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 159, label: "材料描述", sortable: true, align: "left"},
			{name: "sendtypedescr", index: "sendtypedescr", width: 63, label: "发货类型", sortable: true, align: "left"},
			{name: "whdescr", index: "whdescr", width: 79, label: "发货仓库", sortable: true, align: "left"},
			{name: "minqty", index: "minqty", width: 63, label: "最小库存量", sortable: true, align: "left"},
			{name: "hassampledescr", index: "hassampledescr", width: 75, label: "是否上样", sortable: true, align: "left"},
         	{name: "installfeetypedescr", index: "installfeetypedescr", width: 72, label: "安装费类型", sortable: true, align: "left"},
			{name: "installfee", index: "installfee", width: 78, label: "安装费单价", sortable: true, align: "right"},
			{name: "buyer1descr", index: "buyer1descr", width: 70, label: "买手1", sortable: true, align: "left"},
			{name: "whfeetypedescr", index: "whfeetypedescr", width: 70, label: "仓储费类型", sortable: true, align: "left"},
			{name: "whfee", index: "whfee", width: 70, label: "仓储费", sortable: true, align: "right"},
			{name: "custtype", index: "custtype", width: 70, label: "客户类型", sortable: true, align: "left"},
			
           ]
	});
	$("#modelDataTable").addRowData(1, {"fixareadescr":"主卧","intproddescr":"主卧衣柜","itemcode":"073455","qty":0,"unitprice":0,"markup":100,"processcost":0}, "last");
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
        formData.append("itemType1",'${itemChg.itemType1}');
        formData.append("custCode",'${itemChg.custCode}');
        formData.append("isService",'${itemChg.isService}');
        formData.append("isCupboard",'${itemChg.isCupboard}');
        $.ajax({
             url: "${ctx}/admin/item/loadExcel",
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
	arr= isinvalid.fieldJson.split(",");
	var s=0;		
	for(var i=0;i<arr.length;i++){
		if(arr[i]=="0"){
			art.dialog({
					content: "存在无效的数据，无法导入！"
			});
			return;
		}
	}
	var param=Global.JqGrid.allToJson("dataTable");  //JSON.stringify($('#dataTable').jqGrid("getRowData")) ;                       
	Global.Form.submit("page_form","${ctx}/admin/item/doSaveBatch",param,function(ret){
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
		<div class="btn-panel pull-left">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="loadData()">加载数据</button>
				<button type="button" class="btn btn-system " onclick="importData()">导入数据</button>
				<button type="button" class="btn btn-system " onclick="doExcelNow('材料信息导入模板','modelDataTable')"
					style="margin-right: 15px">下载模板</button>
			</div>
		</div>
		<div class="query-form" style="padding: 0px;border: none">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<div class="form-group">
					<label for="inputfile"></label> <input type="file" style="position: relative;top: -12px;" id="file" name="file"
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
