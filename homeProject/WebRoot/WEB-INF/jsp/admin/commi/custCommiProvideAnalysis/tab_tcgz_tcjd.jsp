<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_tcgz_tcjd",{
			url:"${ctx}/admin/commiCustomer/goJqGrid",
			postData: $("#page_form").jsonForm(),
			rowNum: 100000000,
			height:400,
			colModel : [
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},
			    {name: "signcommimon", index: "signcommino", width: 80, label: "签单月份", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "invalidmon", index: "invalidno", width: 80, label: "作废月份", sortable: true, align: "left"},
				{name: "secondpaymon", index: "secondpayno", width: 100, label: "二期款到账月份", sortable: true, align: "left"},
				{name: "thirdpaymon", index: "thirdpayno", width: 100, label: "三期款到账月份", sortable: true, align: "left"},
				{name: "checkmon", index: "checkno", width: 80, label: "结算月份", sortable: true, align: "left"},
				{name: "lastupdatemon", index: "lastupdateno", width: 100, label: "最后修改月份", sortable: true, align: "left"},
				{name: "invalidtypedescr", index: "invalidtypedescr", width: 70, label: "作废原因", sortable: true, align: "left"},
				{name: "invalidperfpk", index: "invalidperfpk", width: 80, label: "作废业绩pk", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
            ]
		});
});

</script>
<div class="body-box-list">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form_tcgz_tcjd" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
		</div>
	</div>
	<div class="pageContent" >
		<table id="dataTable_tcgz_tcjd"></table>
	</div>
</div>
