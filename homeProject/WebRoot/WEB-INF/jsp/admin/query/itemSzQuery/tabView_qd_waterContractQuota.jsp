<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!-- 水电发包定额 -->
<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_waterContractQuota_qd",{
		url:"${ctx}/admin/itemSzQuery/goWaterContractQuotaJqGrid",
		postData:{custCode:$("#code").val()},
        autowidth: false,
        height:410,
		width:1259, 
		styleUI: "Bootstrap",
		rowNum: 10000,
		colModel : [
			{name: "FixAreaDescr", index: "FixAreaDescr", width: 80, label: "区域", sortable: true, align: "left"},
			{name: "BaseCheckItemCode", index: "BaseCheckItemCode", width: 80, label: "项目编号", sortable: true, align: "left"},
			{name: "BaseCheckItemDescr", index: "BaseCheckItemDescr", width: 120, label: "项目名称", sortable: true, align: "left"},
			{name: "Qty", index: "Qty", width: 60, label: "数量", sortable: true, align: "right"},
			{name: "UomDescr", index: "UomDescr", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 60, label: "单价", sortable: true, align: "right"},
			{name: "totalamount", index: "totalamount", width: 60, label: "总价", sortable: true, align: "right", sum: true},
			{name: "Remark", index: "Remark", width: 200, label: "备注", sortable: true, align: "left"},
        ]
	});
});
</script>

<div style="width:100%;height:20px;background:#EEEEEE">户型信息</div>

<div class="panel-info" >  
	<div class="panel-body">
		<form action="" method="post" id="dataForm"  class="form-search">
			<ul id="jcszxxUl" class="ul-form">
				<li>
					<label>卧室</label>
					<input type="text" value="${fixAreaTypeCountMap.ws}" readonly/>
				</li>
				<li>
					<label>卫生间</label>
					<input type="text" value="${fixAreaTypeCountMap.wsj}" readonly/>
				</li>
			</ul>
		</form>
	</div>
</div>
<div style="width:100%;height:20px;background:#EEEEEE">定额明细</div>
<table id="dataTable_waterContractQuota_qd"></table>




















