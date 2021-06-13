<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/itemAppSend/goSendDetailQryJqGrid",
			height:280,
			postData:{sendStatus:"0"},
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 98, label: "发货单号", sortable: true, align: "left"},
				{name: "iano", index: "iano", width: 92, label: "领料单号", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 73, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 146, label: "材料名称", sortable: true, align: "left"},
				{name: "sendqty", index: "sendqty", width: 66, label: "数量", sortable: true, align: "right"},
				{name: "costamount", index: "costamount", width: 80, label: "成本总价", sortable: true, align: "right",hidden:${itemSendBatch.costRight}=="1"?false:true,sum: true},
				{name: "uomdescr", index: "uomdescr", width: 62, label: "单位", sortable: true, align: "left"},
				{name: "totalperweight", index: "totalperweight", width: 71, label: "总重量", sortable: true, align: "right", sum: true},
				{name: "remarks", index: "remarks", width: 189, label: "领料备注", sortable: true, align: "left"},
				{name: "sendtype", index: "sendtype", width: 79, label: "发货类型", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "date", index: "date", width: 120, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "custcode", index: "custcode", width: 78, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 171, label: "楼盘地址（送货地址）", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 80, label: "一级区域", sortable: true, align: "left"},
				{name: "takeaddress", index: "takeaddress", width: 136, label: "取货地址", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 72, label: "司机", sortable: true, align: "left"},
				{name: "followmandescr", index: "followmandescr", width: 72, label: "跟车员", sortable: true, align: "left"},
				{name: "sendbatchno", index: "sendbatchno", width: 82, label: "批次号", sortable: true, align: "left"},
				{name: "sendstatusdescr", index: "sendstatusdescr", width: 81, label: "送货状态", sortable: true, align: "left"},
				{name: "psremarks", index: "psremarks", width: 121, label: "配送说明", sortable: true, align: "left"},
				{name: "supplierdescr", index: "supplierdescr", width: 100, label: "供应商名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 78, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 93, label: "材料类型2", sortable: true, align: "left"},
				{name: "whdescr", index: "whdescr", width: 103, label: "仓库名称", sortable: true, align: "left"},
				{name: "driverremark", index: "driverremark", width: 132, label: "司机发货反馈", sortable: true, align: "left"},
				{name: "arrivedate", index: "arrivedate", width: 120, label: "到货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "arriveaddress", index: "arriveaddress", width: 139, label: "到货地址", sortable: true, align: "left"},
				{name: "isconfirmdescr", index: "isconfirmdescr", width: 96, label: "项目经理确认", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "确认日期", sortable: true, align: "left", formatter: formatTime},
				{name: "projectmanremark", index: "projectmanremark", width: 145, label: "项目经理备注", sortable: true, align: "left"},
				{name: "autotransfee", index: "autotransfee", width: 90, label: "车费(自动)", sortable: true, align: "right"},
				{name: "autocarryfee", index: "autocarryfee", width: 90, label: "搬运费(自动)", sortable: true, align: "right"},
				{name: "transfee", index: "transfee", width: 68, label: "车费", sortable: true, align: "right"},
				{name: "carryfee", index: "carryfee", width: 71, label: "搬运费", sortable: true, align: "right"},
				{name: "transfeeadj", index: "transfeeadj", width:75 , label: "车费补贴", sortable: true, align: "right", sum: true},
				{name: "manysendremarks", index: "manysendremarks", width: 114, label: "多次配送说明", sortable: true, align: "left"},
				{name: "carrytypedescr", index: "carrytypedescr", width: 79, label: "搬运类型", sortable: true, align: "left"},
				{name: "carrytype", index: "carrytype", width: 79, label: "搬运类型", sortable: true, align: "left",hidden:true},
				{name: "floor", index: "floor", width: 66, label: "楼层", sortable: true, align: "right"}
			],
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
					<li><label>生成日期从</label> <input type="text" id="createDateFrom"
						name="createDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="createDateTo"
						name="createDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>到货日期从</label> <input type="text" id="arriveDateFrom"
						name="arriveDateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="arriveDateTo"
						name="arriveDateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
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



