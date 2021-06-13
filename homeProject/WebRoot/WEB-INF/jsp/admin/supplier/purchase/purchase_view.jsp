<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
	<body >
		<div class="body-box-form">
  			<div class="content-form">
  				<!-- panelBar -->
  				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
  			<!-- edit-form -->
  			<div class="infoBox" id="infoBoxDiv"></div>
         <div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
  				<house:token></house:token>
  				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
					<ul class="ul-form">
						<li>
							<label>采购单号</label>
							<input type="text" id="no" name="no" style="width:160px;"  value="${purchase.no }" readonly="readonly">                                             
						</li>
						<li>
							<label>采购类型</label>
							<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
						</li>
						<li>
							<label>采购单状态</label>
							<house:xtdm id="status" dictCode="PURCHSTATUS" value="${purchase.status}"  disabled="true"></house:xtdm>	
						</li>
						<li>
							<label>下单员</label>
							<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }" readonly="readonly"/> 
						</li>
						<li>
							<label>采购日期</label>
							<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" disabled="disable"/>																								
						</li>
						<li>
							<label>到货日期</label>
							<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${purchase.arriveDate}' pattern='yyyy-MM-dd'/>" disabled="1"/>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 160px;" disabled="disabled" value="${purchase.itemType1 }"></select>
						</li>
						<li>
							<label>供应商编号</label>
							<input type="text" id="supplier" name="supplier"  style="width:160px;" value="${purchase.supplier }" readonly="readonly"/>
						</li>
						<li>
							<label>仓库编号</label>
							<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }" readonly="readonly"/>
						</li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="2">${purchase.remarks }</textarea>
						</li>
  					</ul>
  				</form>
  				</div>
  				</div>
  				
			<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">采购明细单</a></li>
	   	 </ul>
			<div id="tabs-1">
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
  		</div>
  <script type="text/javascript">
$("#tabs").tabs();
		$("#whcode").openComponent_wareHouse();
		$("#whcode").setComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}',readonly: true});
		$("#applyMan").openComponent_employee();
		$("#applyMan").setComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}',readonly: true});
		$("#supplier").openComponent_supplier();
		$("#supplier").setComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
		var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
	};
		Global.LinkSelect.setSelect(dataSet);
	
		Global.JqGrid.initJqGrid("dataTable",{
			styleUI: 'Bootstrap', 
			url:"${ctx}/admin/purchaseDetail/goJqGrid",
			postData:{puno:$.trim($("#no").val())},
		    rowNum:10000000,
			height:260,
			
			colModel : [
			{name:'itcode', index:'itcode', width:80, label:'产品编号', sortable:true ,align:"left"},
			{name:'itdescr', index:'itdescr', width:200, label:'材料名称', sortable:true ,align:"left" ,count:true},
			{name:'sqlcodedescr', index:'sqlcodedescr', width:80, label:'品牌', sortable:true ,align:"left"},
			{name:'color', index:'color', width:80, label:'颜色', sortable:true ,align:"left"},	
			{name:'allqty', index:'allqty', width:80, label:'当前库存', sortable:true ,align:"left" ,sum:true},
			{name:'qtycal',	index:'qtycal',width:80, label:'采购数量', 	sortable:true,align:"left" ,sum:true},
			{name:'arrivqty', index:'arrivqty', width:60, label:'已到货', sortable:true ,align:"left",sum:true},
			{name:'unidescr', index:'unidescr', width:60, label:'单位', sortable:true ,align:"left"},
			{name:'remarks',index:'remarks',width:150, label:'备注', 	sortable:true,align:"left"},
            ]
		});
});

 </script>		
  		
  		
  		
	</body>
</html>
