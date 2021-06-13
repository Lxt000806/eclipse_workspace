<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/itemReturn/goReturnDetailMngJqGrid",
			height:320,
			postData:{r_status:"2"},
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 85, label: "退货单号", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 85, label: "材料类型1", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 200, label: "楼盘地址（取货地址）", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 65, label: "申请人", sortable: true, align: "left"},
				{name: "phone", index: "phone", width: 85, label: "手机", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "getdate", index: "getdate", width: 120, label: "收货时间", sortable: true, align: "left", formatter: formatTime},
				{name: "completedate", index: "completedate", width: 120, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
				{name: "drivername", index: "drivername", width: 93, label: "司机", sortable: true, align: "left"},
				{name: "sendbatchno", index: "sendbatchno", width: 80, label: "批次号", sortable: true, align: "left"},
				{name: "status", index: "status", width: 80, label: "退货状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "退货状态", sortable: true, align: "left"},
				{name: "carrytypedescr", index: "carrytypedescr", width: 79, label: "搬运类型", sortable: true, align: "left"},
				{name: "carrytype", index: "carrytype", width: 79, label: "搬运类型", sortable: true, align: "left",hidden:true},
				{name: "floor", index: "floor", width: 66, label: "楼层", sortable: true, align: "right"}
			],
		        beforeSaveCell:function(rowId,name,val,iRow,iCol){
		    	},
		        ondblClickRow:function(){
					returnInfo();
	            },	
 		};
       //初始化退货明细
	   Global.JqGrid.initJqGrid("returnDetailDataTable",gridOption);
	   Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","r_itemType1");
	   $("#r_driverCode").openComponent_driver();
	   $("#returnDetailDataTablePager").width("auto");
});

	function r_clearForm(){
		$("#return_form input[type='text']").val("");
		$("#r_itemType1").val("");
		$("#r_status").val("");
		$.fn.zTree.getZTreeObj("tree_r_status").checkAllNodes(false);
	}
	function return_query(){
		var data=$("#return_form").jsonForm();
		$("#returnDetailDataTable").jqGrid("setGridParam",{postData:data}).trigger("reloadGrid");
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
			<form role="form" class="form-search" id="return_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>退货单号</label> <input type="text" id="r_no" name="r_no"/>
					</li>
					<li><label>批次号</label> <input type="text" id="r_sendBatchNo" name="r_sendBatchNo"
						 />
					</li>
					<li><label>楼盘</label> <input type="text" id="r_address"
						name="r_address" />
					</li>
					<li><label>司机</label> <input type="text" id="r_driverCode"
						name="r_driverCode" style="width:160px;" />
					</li>
					<li><label>材料类型1</label> <select id="r_itemType1"
						name="r_itemType1" style="width:160px;"></select>
					</li>
					 <li><label>退货状态</label> <house:xtdmMulit id="r_status"
                                     dictCode="ITEMRETSTATUS" selectedValue="2"></house:xtdmMulit>
                           </li>
					<li><label>申请日期从</label> <input type="text" id="r_dateFrom"
						name="r_dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="r_dateTo"
						name="r_dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
						<li class="search-group">
						<button type="button" id="r_qr" class="btn  btn-sm btn-system "
							onclick="return_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="r_clearForm();">清空</button></li>
				</ul>
			</form>
	<div class="clear_float"></div>
	<!--query-form-->
		<div id="content-list" >
			<table id="returnDetailDataTable"></table>
			<div id="returnDetailDataTablePager" ></div>
		</div>
</div>



