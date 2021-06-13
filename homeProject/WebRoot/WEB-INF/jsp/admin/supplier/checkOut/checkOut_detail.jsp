<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看结算</title>
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
			      	<button type="button" class="btn btn-system" id="addOthercost">其他费用查看</button>
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
		      {name : 'secondamount',index : 'secondamount',width : 70,label:'secondamount',sortable : true,align : "left",hidden:true}
	  	      ]
	};
	
	//初始化采购单结算
	Global.JqGrid.initJqGrid("splCheckOutDataTable",gridOption);
	
	//新增
	$("#addDetailExists").on("click",function(){
		var splCode = $.trim($("#splCode").val());
		if(splCode==''){
			art.dialog({content: "请选择供应商编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("splCheckOutDataTable","no");
		Global.Dialog.showDialog("addDetailExists",{
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
								  secondamount: v.secondamount
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
	//输出到Excel
	//$("#detailExcel").on("click",function(){
	//	Global.JqGrid.exportExcel("splCheckOutDataTable","${ctx}/admin/purchase/export","采购单结算导出","targetForm");
	//});
	$('#address').bind('keypress',function(event){
        if(event.keyCode=="13"){
       		goto_query();
           	return false;
        }
    });
    
     //其他费用录入
    $("#addOthercost").on("click",function(){
		var rowData = selectDataTableRow('splCheckOutDataTable');
		if(!rowData){
			art.dialog({content: "请选择一条记录进行操作！",width: 200});
			return false;
		}
    	Global.Dialog.showDialog("addOthercost",{
			 title: "其他费用查看",
			 url: "${ctx}/admin/supplierCheckOut/goAddOtherCost",
			 postData:{No: rowData.no,splStatus: rowData.splstatus,splcode:"${splCheckOut.splCode}",
			 			address:rowData.address,m_umState:"V"},
			 height: 550,
			 width: 700,
    	});
    });
});
</script>
</body>
</html>
