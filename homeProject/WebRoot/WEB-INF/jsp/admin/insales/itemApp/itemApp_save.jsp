<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加ItemApp</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>
    
<body>
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panelBar">
			<ul>
				<li >
					<a href="javascript:void(0)" class="a1" id="saveBtn">
					   <span>保存</span>
					</a>	
				</li>
				<li id="closeBut" onclick="closeWin(false)">
					<a href="javascript:void(0)" class="a2">
						<span>关闭</span>
					</a>
				</li>
				<li class="line"></li>
			</ul>
			<div class="clear_float"> </div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="edit-form" id="edit-form">
			<form action="" method="post" id="dataForm">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>
							<td class="td-label"><span class="required">*</span>领料单号</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemApp.no}" placeholder="保存自动生成" readonly="readonly"/>
							</td>
							<td class="td-label">单据状态</td>
							<td class="td-value">
							<house:xtdm id="status" dictCode="ITEMAPPSTATUS" style="width:166px;" value="${itemApp.status}"></house:xtdm>
							</td>
						</tr>
						<tr>
							<td class="td-label">领料类型</td>
							<td class="td-value">
							<house:xtdm id="type" dictCode="ITEMAPPTYPE" value="${itemApp.type}"></house:xtdm>
							</td>
							<td class="td-label"><span class="required">*</span>客户编号</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${itemApp.custCode}" />
							</td>
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>项目经理</td>
							<td class="td-value">
							<input type="text" id="projectMan" name="projectMan" style="width:160px;"  value="${itemApp.projectMan}" />
							</td>							
							<td class="td-label"><span class="required">*</span>电话号码</td>
							<td class="td-value">
							<input type="text" id="phone" name="phone" style="width:160px;"  value="${itemApp.phone}" />
							</td>	
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>材料类型1</td>
							<td class="td-value">
							<select id="itemType1" name="itemType1" style="width:166px;"></select>
							</td>							
							<td class="td-label">材料类型2</td>
							<td class="td-value">
							<select id="itemType2" name="itemType2" style="width:166px;"></select>
							</td>
						</tr>
						<tr>	
							<td class="td-label">是否套餐材料</td>
							<td class="td-value">
							<house:xtdm id="isSetItem" dictCode="YESNO" value="${itemApp.isSetItem}" style="width:166px;"/>
							</td>
							<td class="td-label">是否服务性产品</td>
							<td class="td-value">
							<house:xtdm id="isService" dictCode="YESNO" value="${itemApp.isService}" style="width:166px;"/>	
							</td>
						</tr>
						<tr>
							<td class="td-label">配送方式</td>
							<td class="td-value">
							<house:xtdm id="delivType" dictCode="ITEMAPPSENDTYPE" value="${itemApp.delivType}" style="width:166px;"/>
							</td>
							<td class="td-label">其它费用（付供应商）</td>
							<td class="td-value">
							<input type="text" id="otherCost" name="otherCost" style="width:160px;"  value="${itemApp.otherCost}" />
							</td>							
						</tr>						
						<tr>
							<td class="td-label">备注</td>
							<td class="td-value" colspan="3">
							<textarea id="remarks" name="remarks" style="width:635px;"  value="${itemApp.remarks}" rows="3"></textarea>
							</td>
						</tr>		
					</tbody>
				</table>
			</form>
		</div>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">领料申请明细</a></li>
			</ul>
			<div id="tabs-1">
				<div class="pageContent">
					<div class="panelBar">
		            	<ul>
		                	<li>
		                    	<a href="javascript:void(0)" class="a1" id="addDetailExists">
							       <span>已有项新增</span>
								</a>	
		                    </li>
		                	<li>
								<a href="javascript:void(0)" class="a1" id="addDetailFast">
									<span>快速新增</span>
								</a>
							</li>
		                	<li>
								<a href="javascript:void(0)" class="a2" id="delDetail">
									<span>删除</span>
								</a>
							</li>						
							<li>
								<a href="javascript:void(0)" class="a3" id="detailExcel" title="导出检索条件数据">
									<span>输出到Excel</span>
								</a>
							</li>
		                	<li>
								<a href="javascript:void(0)" class="a1" id="showDetail">
									<span>查看已领材料</span>
								</a>
							</li>							
		                    <li class="line"></li>
		                </ul>
						<div class="clear_float"> </div>
					</div>			
					<div id="content-list">
						<table id= "itemAppDataTable"></table>
					</div>
				</div> 
			</div>
		</div>
	</div>
</div>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});

function save(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	
	//保存表单
	Global.Form.submit("dataForm","${ctx}/admin/itemApp/doSave",$("#dataForm").jsonForm(),function(ret){
    	if(obj.rs){
    		art.dialog({
				content: obj.msg,
				time: 1000,
				beforeunload: function () {
    				closeWin();
			    }
			});
    	}else{
			art.dialog({content: obj.msg,width: 200});
    	}
	});
}

//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
				"type": {
				validIllegalChar: true,
				maxlength: 1
				},
				"itemType1": {
				validIllegalChar: true,
				required: true,
				maxlength: 10
				},
				"custCode": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"status": {
				validIllegalChar: true,
				maxlength: 10
				},
				"appCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"date": {
				maxlength: 23
				},
				"confirmCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"confirmDate": {
				maxlength: 23
				},
				"sendCzy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"sendDate": {
				maxlength: 23
				},
				"sendType": {
				validIllegalChar: true,
				maxlength: 1
				},
				"supplCode": {
				validIllegalChar: true,
				maxlength: 20
				},
				"puno": {
				validIllegalChar: true,
				maxlength: 20
				},
				"whcode": {
				validIllegalChar: true,
				required: true,
				maxlength: 15
				},
				"remarks": {
				validIllegalChar: true,
				maxlength: 1000
				},
				"lastUpdate": {
				required: true,
				maxlength: 23
				},
				"lastUpdatedBy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"expired": {
				validIllegalChar: true,
				maxlength: 1
				},
				"actionLog": {
				validIllegalChar: true,
				maxlength: 10
				},
				"delivType": {
				validIllegalChar: true,
				maxlength: 1
				},
				"phone": {
				validIllegalChar: true,
				maxlength: 30
				},
				"oldNo": {
				validIllegalChar: true,
				maxlength: 20
				},
				"otherCost": {
				number: true,
				maxlength: 19
				},
				"otherCostAdj": {
				number: true,
				maxlength: 19
				},
				"isService": {
				digits: true,
				maxlength: 10
				},
				"itemType2": {
				validIllegalChar: true,
				maxlength: 10
				},
				"isCheckOut": {
				validIllegalChar: true,
				maxlength: 1
				},
				"whcheckOutNo": {
				validIllegalChar: true,
				maxlength: 20
				},
				"checkSeq": {
				digits: true,
				maxlength: 10
				},
				"prjCheckType": {
				validIllegalChar: true,
				maxlength: 1
				},
				"projectOtherCost": {
				number: true,
				maxlength: 19
				},
				"isSetItem": {
				validIllegalChar: true,
				maxlength: 1
				},
				"amount": {
				number: true,
				maxlength: 19
				},
				"projectAmount": {
				number: true,
				maxlength: 19
				},
				"supplCode" :{
					required: true
				}
		}
	});
	
	$("#supplCode").openComponent_supplier();
	$("#custCode").openComponent_customer({
		callBack:function(ret){
			if(ret.projectman){
				$("#projectMan").setComponent_employee({showValue:ret.projectman,showLabel:ret.projectmandescr});
			}
			if(ret.projectmanphone){
				$("#phone").val(ret.projectmanphone);
			}
		}
	});
	$("#projectMan").openComponent_employee({condition:{position:"94"},callBack:function(ret){
		$("#phone").val(ret.Phone);
	}});
	//初始化材料类型1、材料类型2
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2");
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType");
	
	$("#itemType1").on("change",function(){
		if($.trim($(this).val())!='JZ'){
			$("#addDetailExists").show();
			$("#addDetailFast").hide();
		}else{
			$("#addDetailExists").hide();
			$("#addDetailFast").show();
		}
		if($.trim($(this).val())=='JC'){
			$("#itemAppDataTable").showCol("intProdDescr");
		}else{
			$("#itemAppDataTable").hideCol("intProdDescr");
		}
	});
	
	//初始化tabs
	$("#tabs").tabs();
	
	var lastCellRowId;
	var gridOption = {
		//url:"${ctx}/admin/itemAppDetail/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		colModel : [
		  {name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left",hidden:true,hidden:true},
		  {name : 'reqpk',index : 'reqpk',width : 100,label:'reqpk',sortable : true,align : "left",hidden:true,hidden:true},
	      {name : 'no',index : 'no',width : 100,label:'批次号',sortable : true,align : "left",hidden:true},
	      {name : 'itemcode',index : 'itemcode',width : 100,label:'材料编号',sortable : true,align : "left"},
	      {name : 'itemdescr',index : 'itemdescr',width : 100,label:'材料名称',sortable : true,align : "left"},
	      {name : 'fixareapk',index : 'fixareapk',width : 100,label:'装修区域',sortable : true,align : "left",hidden:true},
	      {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'装修区域',sortable : true,align : "left"},
	      {name : 'intprodpk',index : 'intprodpk',width : 100,label:'集成成品',sortable : true,align : "left",hidden:true},
	      {name : 'intproddescr',index : 'intproddescr',width : 100,label:'集成成品',sortable : true,align : "left"},
	      {name : 'uom',index : 'uom',width : 100,label:'单位',sortable : true,align : "left",hidden:true},
	      {name : 'uomdescr',index : 'uomdescr',width : 100,label:'单位',sortable : true,align : "left"},
	      {name : 'qty',index : 'qty',width : 100,label:'采购数量',sortable : true,align : "left",editable:true,editrules: {number:true}},
	      {name : 'sendqty',index : 'sendqty',width : 100,label:'总共已发货数量',sortable : true,align : "left"},
	      {name : 'confirmedqty',index : 'confirmedqty',width : 100,label:'已审核数量',sortable : true,align : "left"},
	      {name : 'reqqty',index : 'reqqty',width : 100,label:'预算数量',sortable : true,align : "left"},
	      {name : 'cost',index : 'cost',width : 100,label:'成本单价',sortable : true,align : "left"},
	      {name : 'allcost',index : 'allcost',width : 100,label:'成本总价',sortable : true,align : "left"},
	      {name : 'projectcost',index : 'projectcost',width : 100,label:'项目经理结算价',sortable : true,align : "left"},
	      {name : 'allprojectcost',index : 'allprojectcost',width : 100,label:'项目经理结算总价',sortable : true,align : "left"},
	      {name : 'remarks',index : 'remarks',width : 100,label:'备注',sortable : true,align : "left"}
        ],
		beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){
	    	var rowData = $("#itemAppDataTable").jqGrid("getRowData",rowId);
	        rowData["allcost"] = (rowData.qty*rowData.cost).toFixed(2);
	    	rowData["allprojectcost"] = (rowData.qty*rowData.projectcost).toFixed(2);
	    	Global.JqGrid.updRowData("itemAppDataTable",rowId,rowData);
	    }
	};
	if($.trim($("#no").val()) != ''){
		$("#custCode").setComponent_customer({showValue:"${itemApp.custCode}",showLabel:"客户编号1"});
		$("#projectMan").setComponent_employee({showValue:"${itemApp.projectMan}",showLabel:"项目经理1"});
		$.extend(gridOption,{
			url:"${ctx}/admin/itemAppDetail/goJqGrid",
			postData:{no:$.trim($("#no").val()),custCode:"${itemApp.custCode}"}
		});
	}
	//初始化领料申请明细
	Global.JqGrid.initEditJqGrid("itemAppDataTable",gridOption);
	
	//已有项新增
	$("#addDetailExists").on("click",function(){
		var custCode = $("#custCode").val();
		if($.trim(custCode)==''){
			art.dialog({content: "请选择客户编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("itemAppDataTable","reqPK");
		Global.Dialog.showDialog("addDetailExists",{
			  title:"领料明细-新增",
			  url:"${ctx}/admin/itemAppDetail/goItemAppDetailExists",
			  height: 680,
			  width:1000,
			  postData:{custCode:custCode},
			  returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
								  itemcode:v.itemcode,itemdescr:v.itemdescr,reqpk:111,fixareapk:123,
								  fixareadescr:v.fixareadescr,intprodpk:432,intproddescr:v.intproddescr,
								  qty:0,sendqty:0,confirmedqty:0,reqqty:0,cost:0,allcost:0,projectcost:0,
								  allprojectcost:0,remarks:0
						  };
						  Global.JqGrid.addRowData("itemAppDataTable",json);
					  });
				  }
			  }
			});
	});
	//快速新增
	$("#addDetailFast").on("click",function(){
		var itemType1 = $.trim($("#itemType1").val()), itemType2 = $.trim($("#itemType2").val());
		if($.trim(itemType2) == ''){
			art.dialog({content: "请先选择材料类型2",width: 200});
			return false;
		}
		Global.Dialog.showDialog("addDetailFast",{
			  title:"领料单-新增",
			  url:"${ctx}/admin/itemAppDetail/goItemAppDetailFast",
			  postData:{itemType1:itemType1,itemType2:itemType2},
			  height: 680,
			  width:1000,
			  returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							  itemCode:v.code,itemDescr:v.descr,uom:v.uom,uomDescr:v.uomdescr
						  };
						  Global.JqGrid.addRowData("itemAppDataTable",json);
					  });
				  }
			  }
			});
	});
	//删除
	$("#delDetail").on("click",function(){
		var id = $("#itemAppDataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("itemAppDataTable",id);
	});
	//输出到Excel
	$("#detailExcel").on("click",function(){
		Global.JqGrid.exportExcel("itemAppDataTable","${ctx}/admin/itemAppDetail/export","领料单导出","targetForm");
	});
	//查看已领材料
	$("#showDetail").on("click",function(){
		
	});
	
	//保存操作
	$("#saveBtn").on("click",function(){
		if(!$("#dataForm").valid()) {return false;}//表单校验
		var ids = $("#itemAppDataTable").getDataIDs();
		if(!ids || ids.length == 0){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		var param = Global.JqGrid.allToJson("itemAppDataTable");
		Global.Form.submit("dataForm","${ctx}/admin/itemApp/doSave",param,function(ret){
			if(ret.rs==true){
				closeWin();
			}else{
				art.dialog({content: ret.msg,width: 200});
			}
		});
	});
	
});
</script>
</body>
</html>
