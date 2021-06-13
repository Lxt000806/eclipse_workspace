<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var CostRight="${CostRight}";
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/itemAppSend/goSendDetailMngJqGrid",
			height:280,
			postData:{sendStatus:"0"},
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 95, label: "发货单号", sortable: true, align: "left"},
				{name: "iano", index: "iano", width: 85, label: "领料单号", sortable: true, align: "left"},
				{name: "sendtype", index: "sendtype", width: 72, label: "发货类型", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "date", index: "date", width: 120, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "address", index: "address", width: 150, label: "楼盘地址（送货地址）", sortable: true, align: "left"},
				{name: "takeaddress", index: "takeaddress", width: 136, label: "取货地址", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 65, label: "司机", sortable: true, align: "left"},
				{name: "sendbatchno", index: "sendbatchno", width: 75, label: "批次号", sortable: true, align: "left"},
				{name: "sendbatchremarks", index: "sendbatchremarks", width: 100, label: "批次备注", sortable: true, align: "left"},
				{name: "sendstatus", index: "sendstatus", width: 75, label: "送货状态", sortable: true, align: "left",hidden:true},
				{name: "sendstatusdescr", index: "sendstatusdescr", width: 75, label: "送货状态", sortable: true, align: "left"},
				{name: "supplierdescr", index: "supplierdescr", width: 100, label: "供应商名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left"},
				{name: "whdescr", index: "whdescr", width: 96, label: "仓库名称", sortable: true, align: "left"},
				{name: "driverremark", index: "driverremark", width: 121, label: "司机发货反馈", sortable: true, align: "left"},
				{name: "arrivedate", index: "arrivedate", width: 78, label: "到货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "arriveaddress", index: "arriveaddress", width: 83, label: "到货地址", sortable: true, align: "left"},
				{name: "isconfirmdescr", index: "isconfirmdescr", width: 96, label: "项目经理确认", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "确认日期", sortable: true, align: "left", formatter: formatTime},
				{name: "projectmanremark", index: "projectmanremark", width: 102, label: "项目经理备注", sortable: true, align: "left"},
				{name: "region", index: "region", width: 73, label: "配送区域", sortable: true, align: "left",hidden:true},
				{name: "regiondescr", index: "regiondescr", width: 73, label: "配送区域", sortable: true, align: "left"},
				{name: "transfee", index: "transfee", width: 75, label: "配送费", sortable: true, align: "right"},
				{name: "carryfee", index: "carryfee", width: 75, label: "搬运费", sortable: true, align: "right"},
				{name: "transfeeadj", index: "transfeeadj", width:75 , label: "车费补贴", sortable: true, align: "right", sum: true},
				{name: "manysendremarks", index: "manysendremarks", width: 114, label: "多次配送说明", sortable: true, align: "left"},
				{name: "transfeeadjcount", index: "transfeeadjcount", width:110 , label: "车费补贴次数", sortable: true, align: "right", sum: true},
				{name: "allcost", index: "allcost", width: 80, label: "成本总价", sortable: true, align: "right", hidden: CostRight=='1' ? false : true},
				{name: "carrytypedescr", index: "carrytypedescr", width: 79, label: "搬运类型", sortable: true, align: "left"},
				{name: "carrytype", index: "carrytype", width: 79, label: "搬运类型", sortable: true, align: "left",hidden:true},
				{name: "floor", index: "floor", width: 66, label: "楼层", sortable: true, align: "right"}

			],
		        beforeSaveCell:function(rowId,name,val,iRow,iCol){
		    		
		    	},
		        ondblClickRow:function(){
					sendInfo();
	            },	
 		};
       //初始化发货明细
	   Global.JqGrid.initJqGrid("sendDetailDataTable",gridOption);
	   Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
	   $("#iaNo").openComponent_itemApp();
	   $("#driverCode").openComponent_driver();
});
	function send_query(){
		var data=$("#send_form").jsonForm();
		$("#sendDetailDataTable").jqGrid("setGridParam",{postData:data}).trigger("reloadGrid");
	}
	function s_clearForm(){
		$("#send_form input[type='text']").val("");
		$("#itemType1").val("");
		$("#sendStatus").val("");
		$.fn.zTree.getZTreeObj("tree_sendStatus").checkAllNodes(false);
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
			<form role="form" class="form-search" id="send_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>发货单号</label> <input type="text" id="no" name="no"/>
					</li>
					<li><label>领料单号</label> <input type="text" id="iaNo"
						name="iaNo"/>
					</li>
					<li><label>批次号</label> <input type="text" id="sendBatchNo" name="sendBatchNo"
						 />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" />
					</li>
					<li><label>司机</label> <input type="text" id="driverCode"
						name="driverCode" style="width:160px;" value="${itemSendBatch.driverCode }" />
					</li>
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1" style="width:160px;"></select>
					</li>
					 <li><label>送货状态</label> <house:xtdmMulit id="sendStatus"
                                     dictCode="APPSENDSTATUS" selectedValue="0"></house:xtdmMulit>
                           </li>
					<li><label>发货日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>生成日期从 </label>
						<input type="text" style="width:160px;" id="createDateFrom" name="createDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="createDateTo" name="createDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
						<li class="search-group">
						<button type="button" id="s_qr" class="btn  btn-sm btn-system "
							onclick="send_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="s_clearForm();">清空</button></li>
				</ul>
			</form>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="sendDetailDataTable"></table>
		<div id="sendDetailDataTablePager"></div>
	</div>
</div>



