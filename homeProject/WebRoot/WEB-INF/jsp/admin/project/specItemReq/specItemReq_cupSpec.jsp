<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
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
		.SelectBG{
          	background-color:#FF7575!important;
        }  
	</style>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="intLastupdatedBy" id="intLastupdatedBy" value="${specItemReq.intLastupdatedBy}">
					<input type="hidden" name="intLastupdate" id="intLastupdate" value="<fmt:formatDate value='${specItemReq.intLastupdate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
					<input type="hidden" name="isSelfMetal_Int" id="isSelfMetal_Int" value="${specItemReq.isSelfMetal_Int}">
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${specItemReq.address}" readonly="true" />
						</li>
						<li>
							<label>客户类型</label>
							<house:xtdm id="custType" dictCode="CUSTTYPE" style="width:160px;" value="${specItemReq.custType}"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>橱柜地柜延米</label>
							<input type="text" id="cupDownHigh" name="cupDownHigh" style="width:160px;" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${specItemReq.cupDownHigh}" />
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>橱柜吊柜延米</label>
							<input type="text" id="cupUpHigh" name="cupUpHigh" style="width:160px;" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${specItemReq.cupUpHigh}"/>
						</li>
						<li>
							<label>集成灶</label>
							<house:xtdm id="hasStove" dictCode="HAVENO" style="width:160px;" value="${specItemReq.hasStove}"/>
						</li>
						<li>
							<label>自购五金（橱柜）</label>
							<house:xtdm id="isSelfMetal_Cup" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfMetal_Cup}"/>
						</li>
						<li>
							<label>自购水槽</label>
							<house:xtdm id="isSelfSink" dictCode="YESNO" style="width:160px;" value="${specItemReq.isSelfSink}"/>
						</li>
						<!-- <li>
							<label>最后修改人</label>
							<input type="text" id="cupLastupdatedBy" name="cupLastupdatedBy" style="width:160px;"/>
						</li>
						<li>
							<label>最后修改日期</label>
							<input type="text" id="cupLastUpdate" name="cupLastUpdate" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${specItemReq.cupLastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true"/>
						</li> -->
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#specItemReq_tabView_cup" data-toggle="tab">橱柜解单数据</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="specItemReq_tabView_cup"  class="tab-pane fade in active"> 
		         	<div class="body-box-list">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="importExcel">
									<span>导入Excel</span>
								</button>
								<button type="button" class="btn btn-system" id="quicklySave">
									<span>快速新增</span>
								</button>
								<button type="button" class="btn btn-system" id="detailSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="detailUpdate" onclick="update()">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="delete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="outExcel" onclick="doExcelNow('橱柜拆单主表明细')">
									<span>导出Excel</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
							<!-- <div id="dataTablePager"></div> -->
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {

	$("#custCode").openComponent_customer({
		readonly:true,
		showValue:"${specItemReq.custCode}",
		showLabel:"${specItemReq.custName}",
	});
	$("#cupLastupdatedBy").openComponent_employee({
		readonly:true,
		showValue:"${specItemReq.cupLastupdatedBy}",
		showLabel:"${specItemReq.cupLastupdatedByDescr}"
	});
	$("#custType").prop("disabled",true);

	var gridOption = {
		url:"${ctx}/admin/specItemReq/goDetailJqGrid",
		postData:{custCode:"${specItemReq.custCode}",iscupboard:"1"},
		sortable: true,
		cellEdit: true,// 编辑行需要先打开cellEdit
		cellsubmit:"clientArray",// 当单元格发生变化后不直接发送请求、"remote"默认直接发送请求
		height : $(document).height()-$("#content-list").offset().top - 65,
		rowNum : 10000000,
		colModel : [
			{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "iscupboard", index: "iscupboard", width: 80, label: "是否橱柜", sortable: true, align: "left",hidden:true},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 80, label: "是否橱柜", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 90, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "itemtype1descr", index: "itemtype1descr", width: 90, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品pk", sortable: true, align: "left",hidden:true},
			{name: "intprodpkdescr", index: "intprodpkdescr", width: 80, label: "成品名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right",sum:true,editable:true, editrules: {number:true, required:true}},
			{name: "appqty", index: "appqty", width: 90, label: "已申请数量", sortable: true, align: "right"},
			{name: "movecost", index: "movecost", width: 60, label: "移动平均成本", sortable: true, align: "right",hidden:true},
			{name: "price", index: "price", width: 60, label: "单价", sortable: true, align: "right"},
			{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right"},
			{name: "totalcost", index: "totalcost", width: 90, label: "成本总价", sortable: true, align: "right",sum:true},
			{name: "remark", index: "remark", width: 200, label: "描述", sortable: true, align: "left",editable:true},
			{name: "selectBG", index: "selectBG", width: 60, label: "错误标记", sortable: true, align: "right",hidden:true},
			{name : "lastupdate",index : "lastupdate",width : 125,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime, hidden:true},
      		{name : "lastupdatedby",index : "lastupdatedby",width : 60,label:"修改人",sortable : true,align : "left", hidden:true},
     		{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left", hidden:true},
    		{name : "actionlog",index : "actionlog",width : 50,label:"操作",sortable : true,align : "left", hidden:true},
			{name: "isexist", index: "isexist", width: 90, label: "是否下单", sortable: true, align: "right",hidden:true},
		],
		loadonce: true,
		loadComplete: function(){
			originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
		},
		gridComplete:function(){
	   		var ids = $("#dataTable").getDataIDs();
	   	 	for(var i=0;i<ids.length;i++){
				var rowData = $("#dataTable").getRowData(ids[i]);
				if(rowData.selectBG==1){
					// 导入已下单的数据时，进行提示
					$('#'+ids[i]).find("td[aria-describedby='dataTable_qty']").addClass("SelectBG");
				}                   
	 		}
			var qtySum = $("#dataTable").getCol("qty",false,"sum");
			$("#dataTable").footerData("set",{"qty":myRound(qtySum,2)});
			var totalcostSum = $("#dataTable").getCol("totalcost",false,"sum");
			$("#dataTable").footerData("set",{"totalcost":myRound(totalcostSum,2)});
		},
		onCellSelect:function(rowid){// 单击之后选择整列
			$("#dataTable").jqGrid("setSelection",rowid);
		},
		afterSaveCell : function(rowid,name,val,iRow,iCol) {
			if ("qty" == name) {
				var appqtyVal = $("#dataTable").jqGrid("getCell",rowid,iCol+1);
				if (parseFloat(val) < parseFloat(appqtyVal)) {
					art.dialog({content: "数量必须大于或等于已下单数量"+appqtyVal,width: 220});
					$("#dataTable").restoreCell(iRow,iCol);
				} else{
					var costVal = $("#dataTable").jqGrid("getCell",rowid,iCol+4);
					$("#dataTable").jqGrid("setRowData",rowid,{totalcost:Math.round(parseFloat(val) * parseFloat(costVal) * 10000)/10000});
					var qtySum = $("#dataTable").getCol("qty",false,"sum");
					$("#dataTable").footerData("set",{"qty":myRound(qtySum,2)});
					var totalcostSum = $("#dataTable").getCol("totalcost",false,"sum");
					$("#dataTable").footerData("set",{"totalcost":myRound(totalcostSum,2)});
				}
			}
		},
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	if ("1" != $.trim("${czybm.costRight}")) {
		jQuery("#dataTable").setGridParam()
			.hideCol("price").hideCol("cost").hideCol("totalcost")
			.trigger("reloadGrid");
	}

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			cupDownHigh:{  
				validators: {  
					notEmpty: {  
						message: "橱柜地柜延米不能为空"  
					}
				}  
			},
			cupUpHigh:{  
				validators: {  
					notEmpty: {  
						message: "橱柜吊柜延米不能为空"  
					}
				}  
			},
		},
		submitButtons : "input[type='submit']"
	});
	
	originalData = $("#page_form").serialize();
	/* 关闭按钮绑定数据是否更改校验 */
	$("#closeBut").on("click",function(){
		doClose();
	});
	
	// 先删除窗口的关闭按钮，再去添加关闭事件
	var titleCloseEle = parent.$("div[aria-describedby=dialog_cupSpec]").children(".ui-dialog-titlebar");
	$(titleCloseEle[0]).children("button").remove();	
	var childBtn=$(titleCloseEle).children("button");
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
		+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
	$(titleCloseEle[0]).children("button").on("click", function(){
		doClose();
	});

	/* 保存 */
	$("#saveBtn").on("click",function(){
		doSave();
	});

	// 导入Excel
	$("#importExcel").on("click",function(){
		var itemCodes = Global.JqGrid.allToJson("dataTable","itemcode");
		var keys = itemCodes.keys;//获取itemCodes的数组
		
		Global.Dialog.showDialog("importExcel",{
			title:"橱柜拆单——导入Excel",
			url:"${ctx}/admin/specItemReq/goImportExcel",
			postData:{
				isCupboard:"1",
				keys:keys,
				custCode:"${specItemReq.custCode}",
			},
			height:650,
			width:1000,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						$.extend(v,{
							lastupdate:new Date(),
							lastupdatedby:$("#lastUpdatedBy").val(),
							actionlog:"ADD",
							expired:"F"
						});
					  	Global.JqGrid.addRowData("dataTable",v);
					});
				}
			}
		});
	});

	// 明细新增
	$("#detailSave").on("click",function(){
		var itemCodes = Global.JqGrid.allToJson("dataTable","itemcode");
		var keys = itemCodes.keys;//获取itemCodes的数组
		Global.Dialog.showDialog("detailSave",{
			title:"橱柜拆单——新增",
			url:"${ctx}/admin/specItemReq/goDetailSave",
			postData:{
				m_umState:"A",
				isCupboard:"1",
				itemType2:"018",
				keys:keys,
				custCode:"${specItemReq.custCode}",
				custName:"${specItemReq.custName}",
			},
			height:385,
			width:450,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							iscupboard:1,
							iscupboarddescr:"是",
							itemtype1:v.itemType1,
							intprodpk:v.intprodpk,
							intprodpkdescr:v.intprodpkdescr,
							itemcode:v.itemCode,
							itemdescr:v.itemDescr,
							qty:v.qty,
							movecost:v.moveCost,
							price:v.price,
							cost:v.cost,
							totalcost:v.totalCost,
							remark:v.remark,
							appqty:v.appQty,
							isexist:v.isExist,
							lastupdate:new Date(),
							lastupdatedby:$("#lastUpdatedBy").val(),
							actionlog:"ADD",
							expired:"F"
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					});
				}
			}
		});
	});
	// 快速新增
	$("#quicklySave").on("click",function(){
		var itemCodes = Global.JqGrid.allToJson("dataTable","itemcode");
		var keys = itemCodes.keys;//获取itemCodes的数组
		Global.Dialog.showDialog("quicklySave",{
			title:"橱柜拆单——快速新增",
			url:"${ctx}/admin/specItemReq/goQuicklySave",
			postData:{ 
				itemType1:"JC",
				itemType2:"018",
				custCode:"${specItemReq.custCode}",
				itemCodes:keys
			},
			height:730,
			width:1200,
			returnFun:function(data){
				if(data){
					$.each(data,function(i,rowData){
						$.extend(rowData,{
							iscupboard:1,
							iscupboarddescr:"是",
							itemtype1:"JC",
							movecost:rowData.avgcost,
							remark:"",
							lastupdate:new Date(),
							lastupdatedby:$("#lastUpdatedBy").val(),
							actionlog:"ADD",
							expired:"F"
						});
						Global.JqGrid.addRowData("dataTable", rowData);
					});
				}
			}
		});
	});

	/* 明细删除 */
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		var ret=selectDataTableRow();
		if ("1" == ret.isexist) {
			art.dialog({content: "该材料已下单，不允许删除",width: 220});
			return;
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
			},
			cancel: function () {
				return true;
			}
		});
		
	});
	
});

function doSave(){
	$("#page_form").bootstrapValidator("validate");
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;
	
	var datas = $("#page_form").jsonForm();
	var param= Global.JqGrid.allToJson("dataTable");
	if(param.datas.length == 0){
		art.dialog({content: "无法保存，请输入明细",width: 220});
		return;
	}
	// 将datas（json）合并到param（json）中
	$.extend(param,datas);
	param.m_umState = "C";
	$.ajax({
		url:"${ctx}/admin/specItemReq/doSave",
		type: "post",
		data: param,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
    	}
	});
	
}

function update(){
	var ret=selectDataTableRow();
	/* 选择数据的id */
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!ret){
		art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
		return false;
	}
	
	var itemCodes = Global.JqGrid.allToJson("dataTable","itemcode");
	var keys = itemCodes.keys;
	var index = keys.indexOf(ret.itemcode);
	if (index > -1) {
	    keys.splice(index, 1);
	}//去掉所选择的值
	
	Global.Dialog.showDialog("detailUpdate",{
		title:"橱柜拆单——编辑",
		url:"${ctx}/admin/specItemReq/goDetailSave",
		postData:{
			isCupboard:"1",
			m_umState:"M",
			itemType2:"018",
			keys:keys,
			custCode:"${specItemReq.custCode}",
			intProdPK:ret.intprodpk==0?"":ret.intprodpk,
			intProdPKDescr:ret.intprodpkdescr,
			itemCode:ret.itemcode,
			itemDescr:ret.itemdescr,
			qty:ret.qty,
			cost:ret.movecost,
			price:ret.price,
			itCost:ret.cost,
			remark:ret.remark,
			appQty:ret.appqty,
			isExist:ret.isexist,
		},
		height:385,
		width:450,
		returnFun : function(data){
			if(data){
				$.each(data,function(k,v){
					var json = {
						intprodpk:v.intprodpk,
						intprodpkdescr:v.intprodpkdescr,
						itemcode:v.itemCode,
						itemdescr:v.itemDescr,
						qty:v.qty,
						movecost:v.moveCost,
						price:v.price,
						cost:v.cost,
						totalcost:v.totalCost,
						remark:v.remark,
						appqty:v.appQty,
						isexist:v.isExist,
						lastupdate:new Date(),
						lastupdatedby:$("#lastUpdatedBy").val(),
						actionlog:"Edit",
						expired:"F",
				  	};
				   	$("#dataTable").setRowData(rowId,json);
				});
			}
		}
	});
}

function doClose() {
	var newData = $("#page_form").serialize();
	var param= Global.JqGrid.allToJson("dataTable");
	//去掉token
	var arr = originalData.split("&");
	arr.splice(0,1);
	originalData = arr.toString();
	arr = newData.split("&");
	arr.splice(0,1);
	newData = arr.toString();

	if (param.detailJson != originalDataTable||newData != originalData) {
		art.dialog({
			content: "数据已变动,是否保存？",
			width: 200,
			okValue: "确定",
			ok: function () {
				doSave();
			},
			cancelValue: "取消",
			cancel: function () {
				closeWin();
			}
		});
	} else {
		closeWin();
	}
}
</script>
</html>
