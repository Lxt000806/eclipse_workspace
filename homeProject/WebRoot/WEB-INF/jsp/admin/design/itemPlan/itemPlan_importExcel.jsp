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
<script src="${resourceRoot}/pub/component_itemPlanBak.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var hasInvalid=true;
function goto_query() {
     $("#dataTable").jqGrid("setGridParam", {url:"${ctx}/admin/itemPlan/goPlanBakJqGrid",postData: $("#page_form").jsonForm(), page: 1}).trigger("reloadGrid");
}
//tab分页
$(document).ready(function() {
	if ("${itemPlan.mustImportTemp}"=="1"){
		$("#file").attr("disabled","disabled");
		$("#btnloadData").hide();
		$("#file_view").hide();
		$("#btnExcel").hide();	
	}else{
		$("#planBakNo_view").hide();		
	}
	$("#planBakNo").openComponent_itemPlanBak({
    	condition: {'custCode': '${itemPlan.custCode}', 'custType': '${itemPlan.custType}','itemType1':'${itemPlan.itemType1}'},
    	callBack: function (data) {
    		$("#mainTempNo").val(data.maintempno);
    		$("#mainTempDescr").val(data.maintempdescr);
        	goto_query();
     	}  
    });     
	var itemType1='${itemPlan.itemType1}'.trim();

    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-72,
		rowNum:10000,
		colModel : [
			{name: "isinvaliddescr", index: "isinvaliddescr", width: 100, label: "是否有效数据", sortable: true, align: "left"},
			{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
			{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 149, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left",hidden:true},
			{name: "fixareapk", index: "fixareapk", width: 137, label: "区域名称", sortable: true, align: "left", hidden: true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
			{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
			{name: "algorithm", index: "algorithm", width: 70, label: "算法编号", sortable:false, align: "left", hidden: true},
			{name: "algorithmdescr", index: "algorithmdescr", width: 85, label: "算法", sortable:false, align: "left"},
			{name: "projectqty", index: "projectqty", width: 76, label: "预估施工量", sortable: true, align: "left", sum: true},
			{name: "qty", index: "qty", width: 76, label: "数量", sortable: true, align: "left", sum: true},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "left"},
			{name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable: true, align: "left", sum: true},
			{name: "markup", index: "markup", width: 68, label: "折扣", sortable: true, align: "left"},
			{name: "tmplineamount", index: "tmplineamount", width: 90, label: "小计", sortable: true, align: "left", sum: true},
			{name: "processcost", index: "processcost", width: 90, label: "其他费用", sortable: true, align: "left", sum: true},
			{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "left", sum: true},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 85, label: "套外", sortable: true, align: "left"},
			{name: "isoutset", index: "isoutset", width: 85, label: "套餐外材料", sortable: true, align: "left", hidden: true},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 85, label: "是否橱柜", sortable: true, align: "left", hidden: true},
			{name: "iscupboard", index: "iscupboard", width: 85, label: "是否橱柜", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 76, label: "成本", sortable: true, align: "left", sum: true,hidden: true},
			{name: "remark", index: "remark", width: 187, label: "材料描述", sortable: true, align: "left"},
			{name: "isservice", index: "isservice", width: 20, label: "是否服务性产品", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
			{name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
			{name: "custtypeitempk", index: "custtypeitempk", width: 110, label: "套餐材料信息编号", sortable: true, align: "left"},
			{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
		    {name: "preplanareapk", index: "preplanareapk", width: 68, label: "空间pk", sortable: true, align: "left", hidden: true},
		    {name: "doorpk", index: "doorpk", width: 68, label: "门窗Pk", sortable: true, align: "left", hidden: true},
		   	{name: "tempdtpk", index: "tempdtpk", width: 68, label: "模板pk", sortable: true, align: "left", hidden: true},
		   	{name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left"},
			{name: "cuttype", index: "cuttype", width: 65, label: "切割类型",sortable:false, align: "left",hidden: true},
			{name: "supplpromitempk", index: "supplpromitempk", width: 0.5, label: "供应商促销pk", sortable:false, align: "left"},
			{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
			{name: "giftdescr", index: "giftdescr", width: 0.5, label: "赠送项目", sortable: true, align: "left"},
			{name: "oldprojectcost", index: "oldprojectcost", width: 90, label: "原项目经理结算价", sortable:false, align: "right",hidden: true},
		    {name: "projectcost", index: "projectcost", width: 90, label: "升级项目经理结算价", sortable:false, align: "right",hidden: true},
			{name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',hidden: true},
			{name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",hidden: true},
        	{name: "autoqty", index: "autoqty", width: 80, label: "系统算量", sortable: true, align: "left", hidden: true},
        	{name: "canmodiqty", index: "canmodiqty", width: 78, label: "数量可修改", sortable: true, align: "left", hidden: true},
       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
		
       ]  
	});
	//初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 149, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left",hidden:true},
			{name: "fixareapk", index: "fixareapk", width: 137, label: "区域名称", sortable: true, align: "left", hidden: true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
			{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
			{name: "algorithm", index: "algorithm", width: 70, label: "算法", sortable:false, align: "left", hidden: true},
			{name: "algorithmdescr", index: "algorithmdescr", width: 85, label: "算法", sortable:false, align: "left", hidden: true},
			{name: "projectqty", index: "projectqty", width: 76, label: "预估施工量", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 76, label: "数量", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "left"},
			{name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable: true, align: "left"},
			{name: "markup", index: "markup", width: 68, label: "折扣", sortable: true, align: "left"},
			{name: "tmplineamount", index: "tmplineamount", width: 90, label: "小计", sortable: true, align: "left"},
			{name: "processcost", index: "processcost", width: 90, label: "其他费用", sortable: true, align: "left"},
			{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "left"},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 85, label: "套外", sortable: true, align: "left"},
			{name: "isoutset", index: "isoutset", width: 85, label: "套餐外材料", sortable: true, align: "left", hidden: true},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 85, label: "是否橱柜", sortable: true, align: "left", hidden: true},
			{name: "iscupboard", index: "iscupboard", width: 85, label: "是否橱柜", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 76, label: "成本", sortable: true, align: "left", hidden: true},
			{name: "remark", index: "remark", width: 187, label: "材料描述", sortable: true, align: "left"},
			{name: "isservice", index: "isservice", width: 20, label: "是否服务性产品", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
			{name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
			{name: "custtypeitempk", index: "custtypeitempk", width: 110, label: "套餐材料信息编号", sortable: true, align: "left"},
			{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
		    {name: "preplanareapk", index: "preplanareapk", width: 68, label: "空间pk", sortable: true, align: "left", hidden: true},
		    {name: "doorpk", index: "doorpk", width: 68, label: "门窗Pk", sortable: true, align: "left", hidden: true},
		   	{name: "tempdtpk", index: "tempdtpk", width: 68, label: "模板pk", sortable: true, align: "left", hidden: true},
		   	{name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left"},
			{name: "cuttype", index: "cuttype", width: 65, label: "切割类型",sortable:false, align: "left",hidden: true},
         	{name: "supplpromitempk", index: "supplpromitempk", width: 0.5, label: "供应商促销pk", sortable:false, align: "left"},
			{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
			{name: "canmodiqty", index: "canmodiqty", width: 78, label: "数量可修改", sortable: true, align: "left", hidden: true},
       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
         ]
	});
	if('${itemPlan.itemType1}'=='ZC'){
		 $("#dataTable").setGridParam().showCol("algorithmdescr").trigger("reloadGrid");
		 $("#modelDataTable").setGridParam().showCol("algorithmdescr").trigger("reloadGrid");
		 $("#dataTable").setGridParam().showCol("preplanareadescr").trigger("reloadGrid");
		 $("#modelDataTable").setGridParam().showCol("preplanareadescr").trigger("reloadGrid");	
		 $("#dataTable").setGridParam().showCol("algorithmper").trigger("reloadGrid");
		 $("#dataTable").setGridParam().showCol("algorithmdeduct").trigger("reloadGrid");
		 
	}	
	if('${itemPlan.itemType1}'=='JC') $("#dataTable").setGridParam().showCol("intproddescr").trigger("reloadGrid");
	if('${itemPlan.itemType1}'=='RZ') $("#dataTable").setGridParam().showCol("itemsetdescr").trigger("reloadGrid");
	$("#modelDataTable").addRowData(1, {"fixareadescr":"主卧","intproddescr":"主卧衣柜","iscupboarddescr":"否","itemcode":"073455","projectqty":0,"qty":0,"unitprice":0,"markup":100,"processcost":0}, "last");
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
        formData.append("itemType1",'${itemPlan.itemType1}');
        formData.append("custCode",'${itemPlan.custCode}');
        formData.append("isService",'${itemPlan.isService}');
        formData.append("isCupboard",'${itemPlan.isCupboard}');
        $.ajax({
                url: "${ctx}/admin/itemPlan/loadYsExcel",
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
				content: "请先载入要进行批量导入的预算数据！"
		});
		return;
	}
	/* if(hasInvalid){
		art.dialog({
			content: "存在无效的数据，无法导入！"
		});
		return;
	} */
	var isinvalid= Global.JqGrid.allToJson("dataTable","isinvalid");
	arr= isinvalid.fieldJson.split(",");
	var s=0;		
	for(var i=0;i<arr.length;i++){
		if(arr[i]!="0"){
			art.dialog({
					content: "存在无效的数据，无法导入！"
			});
			return;
		}
	}
	if('${itemPlan.itemType1}'=='ZC'){
		var sMainTempNo=$("#mainTempNo").val();
		var sMainTempDescr=$("#mainTempDescr").val();
		if (sMainTempNo!=''){
			var datas = $("#page_form").jsonForm();
			$.ajax({
				url:'${ctx}/admin/itemPlan/doUpdateTempNo',
				type: 'post',
				data: datas,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    	
			    	}else{
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content: obj.msg,
							width: 200
						});
						return;
			    	}
			    }
			});	
			parent.document.getElementById("iframe_itemPlan_ys").contentWindow.document.getElementById("mainTempNo").value= sMainTempNo+'|'+sMainTempDescr;	
		}
	}
	var returnData=$("#dataTable").jqGrid("getRowData");
	Global.Dialog.returnData = returnData;
	closeWin();
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="btn-panel pull-left">
			<div class="btn-group-sm">
				<button id="btnloadData" type="button" class="btn btn-system " onclick="loadData()">加载数据</button>
				<button type="button" class="btn btn-system " onclick="importData()">导入数据</button>
				<button id="btnExcel" type="button" class="btn btn-system " onclick="doExcelNow('预算导入模板','modelDataTable')"
					style="margin-right: 15px">下载模板</button>
			</div>
		</div>
		<div class="query-form" style="padding: 0px;border: none">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="custCode" name="custCode" value="${itemPlan.custCode}" />
				<input type="hidden" id="itemType1" name="itemType1" value="${itemPlan.itemType1}" />
				<input type="hidden" id="isService" name="isService" value="${itemPlan.isService}" />
				<input type="hidden"  id="mainTempNo" name="mainTempNo"  />	
				<input type="hidden"  id="mainTempDescr" name="mainTempDescr"  />
				<input type="hidden" id="code" name="code" value="${itemPlan.custCode}" />
				<div id="file_view" class="form-group">
					<label for="inputfile"></label> <input type="file" style="position: relative;top: -12px;" id="file"
						name="file"
						accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
				</div>
				<ul id="planBakNo_view" class="ul-form">
					<li class="form-validate"><label>选择备份编号</label> <input type="text" style="width:160px;" id="planBakNo"
						name="planBakNo" readonly="readonly" />
					</li>
				</ul>
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
