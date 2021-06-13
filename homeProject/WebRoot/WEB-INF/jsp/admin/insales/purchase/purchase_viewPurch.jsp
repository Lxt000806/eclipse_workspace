<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
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


</head>
<body>
	<div class="body-box-form">
		<div class="content-form">
			<!-- panelBar -->
			<div class="panelBar" style="width:943.5px">
				<ul>
					<li>
						<a href="javascript:void(0)" class="a1" id="arrDetail">
							<span>到货明细</span>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)" class="a1" id="purchGZ">
							<span>采购跟踪明细</span>
						</a>
					</li>
					
					<li id="closeBut" onclick="closeWin(false)" >
						<a href="javascript:void(0)" class="a2">
							<span>关闭</span>
						</a>
					</li>
						<li style="float:right">
							<td class="td-label"  >最后更新时间</td>
							<td class="td-value">
								<input type="text" id="lastUpdate" name="lastUpdate" style="width:137px;" readonly="readonly" value="${purchase.lastUpdate }"/> 
							</td>
						</li>
					<li style="float:right">
						<td class="td-label"  >最后更新人员</td>
						<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:137px;" readonly="readonly" value="${purchase.lastUpdatedBy }"/> 
						</td>
					</li>
					
					<li class="line"></li>
				</ul>
				<div class="clear_float"> </div>
			</div>
			<div class="infoBox" id="infoBoxDiv"></div>
			<div class="edit-form" id="edit-form">
				<form action="" method="post" id="page_form">
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
					<table cellspacing="0" cellpadding="0" width="100%">
						<col width="72">
						<col width="150">
						<col width="72">
						<col width="150">
						<tbody>
							<tr>
								<td class="td-label"><span class="required">*</span> 采购订单号</td>
								<td class="td-value">
									<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }" readonly="readonly"/>
								</td>
								<td class="td-label">采购类型</td>
								<td class="td-value">
									<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
								</td>
							</tr>
							<tr>
								<td class="td-label">单据状态</td>
								<td class="td-value">
									<house:xtdmMulit id="status" dictCode="PURCHSTATUS"  selectedValue="${purchase.status}" ></house:xtdmMulit>                     
								</td>
								<td class="td-label">下单员</td>
								<td class="td-value">
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }"/> 
								</td>
							</tr>
							<tr>
								<td class="td-label">采购日期</td>
								<td class="td-value">
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" />
								</td>
								<td class="td-label">到货日期</td>
								<td>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="${purchase.arriveDate }" />
								</td>
							</tr>
							<tr>
								<td class="td-label">材料类型1</td>
								<td class="td-value">
									<select id="itemType1" name="itemType1" style="width: 166px;" disabled="disabled"  >
									</select>
								</td>
								<td class="td-label">其他费用</td>
								<td class="td-value">
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost }" />
								</td>
							</tr>
							<tr>
								<td class="td-label">客户编号</td>
								<td class="td-value" > 
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchase.custCode }"/>
								</td>
								<td class="td-label">其他费用调整</td>
								<td class="td-value">
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonl' />
								</td>
							</tr>
							<tr>
								<td class="td-label">供应商编号</td>
								<td class="td-value"> 
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
								</td>
								<td class="td-label">材料总价</td>
								<td class="td-value">
									<input type="text" id="amount" name="amount"  style="width:160px;" value="${purchase.amount }" readonly= 'readonl' />
								</td>
							</tr>
							<tr>
								<td class="td-label">仓库编号</td>
								<td class="td-value" > 
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
								</td>
								<td class="td-label">配送地点</td>
								<td class="td-value">
									<house:xtdm id="delivType" dictCode="PURCHDELIVTYPE"  value="1" disabled='true'></house:xtdm>                     

								</td>
							</tr>
							
							<tr>
								<td class="td-label">是否结账</td>
								<td class="td-value">
									<house:xtdm id="isCheckOut" dictCode="YESNO"  value="0" disabled='true'></house:xtdm>                     
								</td>
								
								<td class="td-label">已付定金</td>
								<td class="td-value">
									<input type="text" id="firstAmount" name="firstAmount"  style="width:160px;" value="${purchase.firstAmount }" readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">结账单号</td>
								<td class="td-value" > 
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;" value="${purchase.checkOutNo }" readonly='readonly'/>
								</td>
								<td class="td-label">已付到货款</td>
								<td class="td-value" > 
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">销售单号</td>
								<td class="td-value" > 
									<input type="text" id="SINo" name="SINo" style="width:160px;" value="${purchase.sino }"/>
								</td>
								<td class="td-label"> 应收余款</td>
								<td class="td-value">
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;" value="${purchase.remainAmount }"  readonly='readonly'/>
								</td>
							</tr>
							<tr>
								<td class="td-label">原采购单号</td>
								<td class="td-value" > 
									<input type="text" id="oldPUNo" name="oldPUNo" style="width:160px;" value="${purchase.oldPUNo }"/>
								</td>
								<td class="td-label" id="dsa">备注</td>
								<td class="td-value" colspan="1">
								<textarea id="remarks" name="remarks" rows="3">${purchase.remarks }</textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			
			<div id="tabs">
			<ul>
			<li>
				<a href="#tabs-1">采购单明细</a></li>
			</ul>
			<div class="panelBar">
 			   <ul>
		   		 	<li>
						<a href="javascript:void(0)" class="a1" id="view">
							<span>查看</span>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)" class="a3" onclick="doExcelNow('采购明细单')" title="导出当前excel数据" >
							<span>导出excel</span>
						</a>
					</li>
                </ul>		   
			</div>	
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
	</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
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
		if('${purchase.type}'=='R'||'${purchase.whcode}'=='1000'){
			$("#arrDetail").css("display","none");
		}
		if('${purchase.type}'=='S'){
			$("tbody tr:eq(10) td:lt(2)").css("display","none");
		}
	
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
		var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
	};
		Global.LinkSelect.setSelect(dataSet);
	
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/purchaseDetail/goViewJqGrid",
			postData:{puno:$.trim($("#no").val()),status:$.trim($("#status").val())},
		    rowNum:10000000,
			height:155,
			colModel : [
				{name:'pk', index:'pk', width:80, label:'pk', sortable:true ,align:"left",hidden:true},
				{name:'no', index:'no', width:80, label:'no', sortable:true ,align:"left",hidden:true},
				{name:'puno', index:'puno', width:80, label:'puno', sortable:true ,align:"left",hidden:true},
				{name:'itcode', index:'itcode', width:70, label:'材料编号', sortable:true ,align:"left"},
				{name:'itdescr', index:'itdescr', width:200, label:'材料名称', sortable:true ,align:"left",count:true},
				{name:'sqlcodedescr', index:'sqlcodedescr', width:60, label:'品牌', sortable:true ,align:"left"},
				{name:'color', index:'color', width:50, label:'颜色', sortable:true ,align:"left"},	
				{name:'allqty', index:'allqty', width:80, label:'当前库存量', sortable:true ,align:"left",sum:true},
				{name:'qtycal',	index:'qtycal',width:60, label:'采购数量',sortable : true,align : "left",sum:true},
				{name:'useqty',	index:'useqty',width:60, label:'可用量', sortable : true,align : "left", },
				{name:'purqty',	index:'purqty',width:60, label:'在途采购量',sortable : true,align : "left",sum:true},
				{name:'arrivqty',	index:'arrivqty',width:60, label:'已到货数量',sortable : true,align : "left",sum:true},
				{name:'unidescr', index:'unidescr', width:40, label:'单位', sortable:true ,align:"left"},
				{name:'unitprice', index:'unitprice', width:40, label:'单价', sortable : true,align : "left"},
				{name:'projectcost', index:'projectcost', width:120, label:'项目经理结算总价', sortable:true ,align:"left",sum:true},
				{name:'amount', index:'amount', width:60, label:'总价', sortable:true ,align:"left",sum:true},
				{name:'remarks',index:'remarks',width:150, label:'备注', 	sortable:true,align:"left"},
				{name:'sino',index:'sino',width:150, label:'sino', 	sortable:true,align:"left",hidden:true},
		  ],
		});
	
	//延期
	$("#purchGZ").on("click",function(){
		var ret = selectDataTableRow();
        	  if (ret) {	
            	Global.Dialog.showDialog("purchGZ",{ 
             	  title:"采购跟踪明细",
             	  url:"${ctx}/admin/purchase/goViewGZ",
             	  postData:{no:'${purchase.no }'},
             	  height: 500,
             	  width:800,
             	  returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
	});
	
	//
	$("#arrDetail").on("click",function(){
		var ret = selectDataTableRow();
        	  if (ret) {	
            	Global.Dialog.showDialog("arrDetail",{ 
             	  title:"采购跟踪明细",
             	  url:"${ctx}/admin/purchase/goViewArrDetail?no="+ret.no,
             	 // postData:{no:'${purchase.no }'},
             	  height: 650,
             	  width:900,
             	  returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
	});
	
	//查看
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"查看采购信息",
				url:"${ctx}/admin/purchase/goAddView",
				postData:{puno:ret.puno,itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr},
				height:700,
				width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	});
	if('${purchase.type}'=='R'){
		document.getElementById('jqgh_dataTable_qtycal').innerHTML="退回数量";
	}
});

 </script>		
  		
  		
  		
	</body>
</html>











