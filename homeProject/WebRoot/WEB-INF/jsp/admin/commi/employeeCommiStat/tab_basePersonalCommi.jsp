<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_basePersonalCommi",{
			url:"${ctx}/admin/commiCustStakeholder/goBasePersonalJqGrid",
			postData:{beginMon:"${employee.beginMon}",endMon:"${employee.endMon}",empCode:"${employee.number}"},
			rowNum: 100000000,
			height:400,
			colModel : [
			    {name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",},
			    {name: "busitypedescr", index: "busitypedescr", width: 80, label: "业务类型", sortable: true, align: "left"},
				{name: "chgno", index: "chgno", width: 80, label: "增减单号", sortable: true, align: "left"},
				{name: "baseitemtype1descr", index: "baseitemtype1descr", width: 80, label: "基装类型1", sortable: true, align: "left",},
				{name: "weightper", index: "weightper", width: 50, label: "权重", sortable: true, align: "right"},
				{name: "amount", index: "amount", width: 70, label: "销售金额", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 60, label: "提成点", sortable: true, align: "right"},
				{name: "commiamount", index: "commiamount", width: 70, label: "提成金额", sortable: true, align: "right"},
				{name: "commiprovideper", index: "commiprovideper", width: 70, label: "提成比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 70, label: "本次应发", sortable: true, align: "right", sum: true},
				{name: "adjustamount", index: "adjustamount", width: 70, label: "调整金额", sortable: true, align: "right", sum: true},
				{name: "realprovideamount", index: "realprovideamount", width: 70, label: "本次实发", sortable: true, align: "right", sum: true},
				{name: "totalrealprovideamount", index: "totalrealprovideamount", width: 70, label: "累计已发", sortable: true, align: "right", sum: true},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
            ],
		});
});	

</script>
<div class="body-box-list">
	<div class="pageContent">
		<table id="dataTable_basePersonalCommi"></table>
	</div>
</div>
