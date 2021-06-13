<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
                        
	var postData = $("#page_form").jsonForm();
	postData.status="${giftApp.status}";
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/giftApp/goJqGrid",
		postData: postData,
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		colModel : [
		   {name : 'no',index : 'no',width : 80,label:'领用单号',sortable : true,align : "left"},
			  {name : 'type',index : 'type',width : 60,label:'领用类型',sortable : true,align : "left",hidden:true},
		      {name : 'typedescr',index : 'typedescr',width : 60,label:'领用类型',sortable : true,align : "left",count:true},
		      {name : 'outtype',index : 'outtype',width : 60,label:'出库类型',sortable : true,align : "left",hidden:true},
		      {name : 'outtypedescr',index : 'outtypedescr',width : 60,label:'出库类型',sortable : true,align : "left",count:true},
		      {name : 'custcode',index : 'custcode',width : 50,label:'客户编号',sortable : true,align : "left",hidden:true},
		      {name : 'custdescr',index : 'custdescr',width : 50,label:'客户名称',sortable : true,align : "left"},
		      {name : 'address',index : 'address',width : 120,label:'楼盘',sortable : true,align : "left"},
		      {name : 'setdate',index : 'setdate',width : 80,label:'下定时间',sortable : true,align : "left",formatter:formatDate},
	          {name : 'signdate',index : 'signdate',width : 80,label:'签订时间',sortable : true,align : "left",formatter:formatDate},
		      {name : 'useman',index : 'useman',width : 100,label:'领用人',sortable : true,align : "left",hidden:true},
		      {name : 'usemandescr',index : 'usemandescr',width :60,label:'领用人',sortable : true,align : "left"},
		      {name : 'phone',index : 'a.Phone',width : 100,label:'领用人电话',sortable : true,align : "left"},
		      {name : 'status',index : 'status',width : 100,label:'状态',sortable : true,align : "left",hidden:true},
		      {name : 'statusdescr',index : 'statusdescr',width : 60,label:'状态',sortable : true,align : "left"},
		      {name : 'sendtype',index : 'sendType',width : 100,label:'发货类型',sortable : true,align : "left",hidden:true},
		      {name : 'sendtypedescr',index : 'sendtype',width : 60,label:'发货类型',sortable : true,align : "left"},
		      {name : 'actno',index : 'actno',width : 100,label:'活动',sortable : true,align : "left",hidden:true},
		      {name : 'actdescr',index : 'actdescr',width : 100,label:'活动名称',sortable : true,align : "left"},
		      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商',sortable : true,align : "left",hidden:true},
		      {name : 'suppldescr',index : 'suppldescr',width : 100,label:'供应商',sortable : true,align : "left"},
		      {name : 'whcode',index : 'whcode',width : 100,label:'仓库编号',sortable : true,align : "left",hidden:true},
		      {name : 'whdescr',index : 'whdescr',width : 80,label:'仓库名称',sortable : true,align : "left"},
		      {name : 'puno',index : 'a.puno',width : 100,label:'采购单号',sortable : true,align : "left"},
		      {name : 'checkoutno',index : 'checkoutno',width : 100,label:'出库记账单号',sortable : true,align : "left"},
		      {name : 'checkoutstatus',index : 'checkoutstatus',width : 60,label:'出库记账状态',sortable : true,align : "left"},
		      {name : 'checkoutdocumentno',index : 'checkoutdocumentno',width : 100,label:'出库记账凭证号',sortable: true,align : "left"}
        
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
		 $("#custCode").openComponent_customer(); 
		 $("#useMan").openComponent_employee();
		 $("#actNo").openComponent_cmpactivity();

});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form   id="page_form" action="" method="post" class="form-search" >
				<input type="hidden" id="outType" name="outType" value="${giftApp.outType }" />
					<ul class="ul-form">
					
					    <li>
							<label>领用单号</label>
							<input type="text" id="no" name="no" style="width:160px;"  value="${giftApp.no }" />
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;" value="${giftApp.custCode}" />
						</li>
						<li>
							<label>领用人</label>
							<input type="text" id="useMan" name="useMan" style="width:160px;" value="${giftApp.useMan }" />
						</li>
						<li>
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="GIFTAPPSTATUS" selectedValue="${giftApp.status}"></house:xtdmMulit>
						</li>
						<li>
							<label>发货类型</label>
							<house:xtdm id="sendType" dictCode="GIFTAPPSENDTYPE"  value="${giftApp.sendType}"></house:xtdm>
						</li>					
						<li>
							<label>活动编号</label>
							<input type="text" id="actNo" name="actNo" style="width:160px;" value="${giftApp.actNo }" />
						</li>					
						<li>
							<label>发货时间从</label>
							<input type="text" style="width:160px;" id="sendDateFrom" name="sendDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.sendDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>		
						<li>
							<label>到</label>
						    <input type="text" style="width:160px;" id="sendDateTo" name="sendDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.sendDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>		
						<li class="search-group" >
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
						</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
		</div>
</body>
</html>


