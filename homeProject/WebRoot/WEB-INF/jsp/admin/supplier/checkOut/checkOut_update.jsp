<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改结算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<input type="hidden" name="payType" id="payType" value="${splCheckOut.payType }"/>
					<ul class="ul-form">
						<li>
							<label><span class="required">*</span>结算流水</label>
							<input type="text" id="no" name="no" style="width:160px;" value="${splCheckOut.no}" placeholder="保存自动生成" readonly="readonly"/>
						</li>
						<li>
							<label><span class="required">*</span>结算日期</label>
							<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${splCheckOut.date}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label><span class="required">*</span>结算单状态</label>
							<house:xtdm id="status" dictCode="SPLCKOTSTATUS" value="${splCheckOut.status}" disabled="true"></house:xtdm>
						</li>
						<li>
							<label>审核日期</label>
							<input type="text" id="confirmDate" name="confirmDate" class="i-date" style="width:160px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${splCheckOut.confirmDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</li>
						<li>
							<label><span class="required">*</span>供应商编号</label>
							<input type="text" id="splCode" name="splCode" style="width:160px;" value="${splCheckOut.splCode}"/>
						</li>
						<li>			
							<label>供应商名称</label>
							<input type="text" id="splCodeDescr" name="splCodeDescr" style="width:160px;" value="${splCheckOut.splCodeDescr}" readonly="readonly" />	
						</li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remark" name="remark">${splCheckOut.remark}</textarea>
						</li>
					</ul>
            	</form>
            </div>
         </div>
     <div class="container-fluid" id="id_detail">
	    <ul class="nav nav-tabs" >
	        <li class="active"><a href="#tabs-1" data-toggle="tab">主项目</a></li>
	    </ul>
	    <div class="tab-content">
    		<div id="#tabs-1" class="tab-pane fade in active"> 
	         	<div class="btn-panel" style="padding-top: 5px;">
			      <div class="btn-group-sm">
			      	<button type="button" class="btn btn-system" id="addDetailExistsCheck">新增</button>
			      	<button type="button" class="btn btn-system" id="viewDetailExists">查看</button>
			      	<button type="button" class="btn btn-system" id="delDetail">删除</button>
			      	<button type="button" class="btn btn-system" id="addOthercost">其他费用录入</button>
			      	<button type="button" class="btn btn-system" id="detailExcel" onclick="doExcelNow('采购单结算','splCheckOutDataTable','dataForm')">输出到Excel</button>
			      </div>
			    </div>
			    <div class="query-form">  
                    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
						<ul class="ul-form">
							<li>
								<label style="width: 30px;">楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"/>
							</li>				
							<li>
								<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
							</li>
						</ul>
					</form>
				</div>
				<div id="content-list" style="margin-top: -9px;">
					<table id="splCheckOutDataTable"></table>
				</div>
	        </div>
	    </div>
	 </div>
</div>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/js/checkOut.js" type="text/javascript"></script>
<script type="text/javascript">
//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
				"date": {
					required: true
				},
				"status": {
					required: true
				},
				"splCode": {
					required: true
				},
				"remark": {
					validIllegalChar: true,
					maxlength: 200
				}
		}
	});
	$("#splCode").openComponent_supplier({valueOnly: true});
	$("#splCode").setComponent_supplier({showValue:'${splCheckOut.splCode}',readonly: true});
	
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/purchase/goPurchaseJqGrid",
		postData:{checkOutNo:$.trim($("#no").val())},
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:10000000,
		colModel : [
				  {name : 'checkoutno',index : 'checkoutno',width : 100,label:'checkoutno',sortable : true,align : "left",hidden:true},
		  		  {name : 'no',index : 'no',width : 85,label:'采购单号',sortable : true,align : "left"},
		  		  {name : 'appno',index : 'appno',width : 85,label:'领料单号',sortable : true,align : "left"},
		  		  {name : 'itemtype2',index : 'itemtype2',width : 85,label:'材料类型2',sortable : true,align : "left", hidden: true},
		  		  {name : 'itemtype2descr',index : 'itemtype2descr',width : 85,label:'材料类型2',sortable : true,align : "left"},
		  		  {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
		  		  {name : 'documentno',index : 'documentno',width : 70,label:'编号',sortable : true,align : "left"},
		  	      {name : 'date',index : 'date',width : 75,label:'采购日期',sortable : true,align : "left",formatter:formatTime},
		  	      {name : 'othercost',index : 'othercost',width : 75,label:'其他费用',sortable : true,align : "left"},
		  	      {name : 'othercostadj',index : 'othercostadj',width : 90,label:'其他费用调整',sortable : true,align : "left",hidden:true},
		  	      {name : 'splamount',index : 'splamount',width : 70,label:'对账金额',editable:true,editrules: {number:true,required:true},sortable : true,align : "left"},
			      {name : 'hjamount',index : 'hjamount',width : 70,label:'合计金额',sortable : true,align : "left"},
			      {name : 'amount',index : 'amount',width : 70,label:'材料金额',sortable : true,align : "left",hidden: ${costRight=='1'?'false':'true'}},
			      {name : 'projectamount',index : 'projectamount',width : 100,label:'项目经理结算价',sortable : true,align : "left",hidden: ${projectCostRight=='1'?'false':'true'}},
		  	      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left"},
				  {name : 'payremark',index : 'payremark',width : 150,label:'备注',sortable : true,align : "left",hidden:true},
		  	      {name : 'isservicedescr',index : 'isservicedescr',width : 90,label:'是否服务产品',sortable : true,align : "left"},
		  	      {name : 'issetitemdescr',index : 'issetitemdescr',width : 70,label:'套餐材料',sortable : true,align : "left"},
		  	      {name : 'type',index : 'type',width : 80,label:'采购单类型',sortable : true,align : "left",hidden:true},
		  	      {name : 'typedescr',index : 'typedescr',width : 80,label:'采购单类型',sortable : true,align : "left"},
				  {name : 'ischeckout',index : 'ischeckout',width : 70,label:'是否结帐',sortable : true,align : "left",hidden:true},
			      {name : 'firstamount',index : 'firstamount',width : 70,label:'已付定金',sortable : true,align : "left",hidden:true},
			      {name : 'secondamount',index : 'secondamount',width : 70,label:'secondamount',sortable : true,align : "left",hidden:true},
			      {name : 'splstatus',index : 'splstatus',width : 80,label:'供应商状态',sortable : true,align : "left",hidden:true},
			      {name : 'splstatusdescr',index : 'splstatusdescr',width : 80,label:'供应商状态',sortable : true,align : "left"},
			      {name : 'projectothercost',index : 'projectothercost',width : 70,label:'项目经理调整',sortable : true,align : "left",hidden:true},   
		  	],             
  	    beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){
	    	var rowData = $("#splCheckOutDataTable").jqGrid("getRowData",rowId);
	        rowData["hjamount"] = (parseFloat(rowData.splamount)+parseFloat(rowData.othercost)).toFixed(2); //+parseFloat(rowData.othercostadj)
	    	Global.JqGrid.updRowData("splCheckOutDataTable",rowId,rowData);
	    },
		beforeSelectRow:function(id){
			setTimeout(function(){
				relocate(id,"splCheckOutDataTable");
			},10);
		},
		onCellSelect:function(rowid,iCol,cellcontent,e){
			var rowData = $("#splCheckOutDataTable").jqGrid("getRowData",rowid);
			if(rowData.splstatus=="2"){
               	$("#splCheckOutDataTable").jqGrid("setCell", rowid, "splamount", "", "not-editable-cell");  
			}
		}
	};
	
	function gridComplete_str(){
		if (!flag_save) return;
		var ids = $("#splCheckOutDataTable").getDataIDs();
		if(!ids || ids.length == 0){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		var param = $.extend(Global.JqGrid.allToJson("splCheckOutDataTable","","checkseq"),param_old);
		Global.Form.submit("dataForm","${ctx}/admin/supplierCheckOut/doUpdate",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: ret.msg,
					time: 1000,
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
	//初始化采购单结算
	Global.JqGrid.initEditJqGrid("splCheckOutDataTable",gridOption);
	
	//新增
	$("#addDetailExistsCheck").on("click",function(){
		var splCode = $.trim($("#splCode").val());
		if(splCode==''){
			art.dialog({content: "请选择供应商编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("splCheckOutDataTable","no");
		Global.Dialog.showDialog("addDetailExistsCheck",{
			  title:"采购单结算-新增",
			  url:"${ctx}/admin/purchase/goPurchaseExists",
			  height: 670,
			  width:1200,
			  postData:{supplier: splCode,checkOutNo:'${splCheckOut.no}',unSelected: detailJson["fieldJson"]},
			  returnFun:function(data){
				  if(data){
					  var json_temp = [];
					  $.each(data,function(k,v){
						  var json = {
								  checkoutno: v.checkoutno,
								  no: v.no,
								  appno: v.appno,
								  itemtype2: v.itemtype2,
								  itemtype2descr: v.itemtype2descr,
								  isservicedescr: v.isservicedescr,
								  issetitemdescr: v.issetitemdescr,
								  address: v.address,
								  type: v.type,
								  typedescr: v.typedescr,
								  date: v.date,
								  othercost: v.othercost,
								  othercostadj: v.othercostadj,
								  splamount: v.splamount,
								  hjamount: v.hjamount,
								  remarks: v.remarks,
							      payremark: v.payremark,
								  ischeckout: '1',
								  documentno: v.documentno,
								  amount: v.amount,
								  projectamount: v.projectamount,
								  firstamount: v.firstamount,
								  secondamount: v.secondamount,
								  splstatus: v.splstatus,
								  splstatusdescr: v.splstatusdescr,
								  projectothercost: v.projectothercost,
						  };
						  json_temp.push(json);
					  });
					  Global.JqGrid.addRowData("splCheckOutDataTable",json_temp);
				  }
			  }
			});
	});
	//查看采购单
    $("#viewDetailExists").on("click",function(){
		var row = selectDataTableRow('splCheckOutDataTable');
		if(!row){
			art.dialog({content: "请选择一条记录进行查看操作！",width: 200});
			return false;
		}

        //查看窗口
    	Global.Dialog.showDialog("viewDetailExists",{
    	  title: "查看采购单",
    	  url: "${ctx}/admin/purchase/goView?id=" + row.no,
    	  height: 700,
    	  width: 1000,
    	  postData: row.no
    	});
    });
	//删除
	$("#delDetail").on("click",function(){
		var id = $("#splCheckOutDataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		var row = selectDataTableRow('splCheckOutDataTable');
		Global.JqGrid.delRowData("splCheckOutDataTable",id);
		deleteJsonDatas(row.no);
	});
	//其他费用录入
    $("#addOthercost").on("click",function(){
		var rowData = selectDataTableRow('splCheckOutDataTable');
		if(!rowData){
			art.dialog({content: "请选择一条记录进行操作！",width: 200});
			return false;
		}
    	Global.Dialog.showDialog("addOthercost",{
			 title: "其他费用录入",
			 url: "${ctx}/admin/supplierCheckOut/goAddOtherCost",
			 postData:{No: rowData.no,splStatus: rowData.splstatus,splcode:"${splCheckOut.splCode}",address:rowData.address},
			 height: 550,
			 width: 700,
			 returnFun:function(data){
				 if(data != "null" ){
				 	var rowId = $("#splCheckOutDataTable").jqGrid("getGridParam","selrow")
			        rowData["othercost"] = data;
			        rowData["hjamount"] = (parseFloat(rowData.splamount)+parseFloat(data)).toFixed(2);  //+parseFloat(rowData.othercostadj)
			    	console.log(rowId);
			    	console.log(data);
			    	console.log(rowData);
			    	Global.JqGrid.updRowData("splCheckOutDataTable",rowId,rowData);
				 }
			}
    	});
    });
	//保存操作
	$("#saveBtn").on("click",function(){
		if(!$("#dataForm").valid()) {return false;}//表单校验
		getJsonDatas();
		if(!json_datas || json_datas.length == 0){
			art.dialog({content: "明细表中无数据！",width: 200});
			return false;
		}
		var param = {};
		param.detailJson = JSON.stringify(json_datas);
		Global.Form.submit("dataForm","${ctx}/admin/supplierCheckOut/doUpdate",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: ret.msg,
					time: 1000,
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
	});
	$('#address').bind('keypress',function(event){
        if(event.keyCode=="13"){
       		goto_query();
           	return false;
        }
    });
});
</script>
</body>
</html>
