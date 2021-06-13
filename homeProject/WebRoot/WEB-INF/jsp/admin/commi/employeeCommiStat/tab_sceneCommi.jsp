<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //设计提成
		Global.JqGrid.initJqGrid("dataTable_sceneCommi",{
			url:"${ctx}/admin/commiCustStakeholder/goJqGrid",           
			postData:{beginMon:"${employee.beginMon}",endMon:"${employee.endMon}",empCode:"${employee.number}",role:"63"},
			rowNum: 100000000,
			height:400,
			colModel : [
			    {name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left"},
				{name: "perfamount", index: "perfamount", width: 70, label: "业绩金额", sortable: true, align: "right"},
				{name: "weightper", index: "weightper", width: 45, label: "权重", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 55, label: "提成点", sortable: true, align: "right"},
				{name: "subsidyper", index: "subsidyper", width: 55, label: "补贴点", sortable: true, align: "right"},
				{name: "multiple", index: "multiple", width: 45, label: "倍数", sortable: true, align: "right"},
				{name: "commiamount", index: "commiamount", width: 65, label: "提成金额", sortable: true, align: "right"},
				{name: "commiprovideper", index: "commiprovideper", width: 65, label: "提成比例", sortable: true, align: "right"},
				{name: "subsidyprovideper", index: "subsidyprovideper", width: 65, label: "补贴比例", sortable: true, align: "right"},
				{name: "multipleprovideper", index: "multipleprovideper", width: 65, label: "倍数比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 65, label: "本次应发", sortable: true, align: "right",sum:true},
				{name: "adjustamount", index: "adjustamount", width: 65, label: "调整金额", sortable: true, align: "right"},
				{name: "realprovideamount", index: "realprovideamount", width: 65, label: "本次实发", sortable: true, align: "right",sum:true},
				{name: "totalrealprovideamount", index: "totalrealprovideamount", width: 65, label: "累计已发", sortable: true, align: "right",sum:true},
				{name: "commiexprremarks", index: "commiexprremarks", width: 200, label: "提成公式", sortable: true, align: "left"},
			    {name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
			    {name: "signcommimon", index: "signcommimon", width: 90, label: "签单提成月份", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},
            ],
		});
});
</script>
<div class="body-box-list">
	<div class="panel panel-system">
	</div>
	<div class="pageContent">
		<table id="dataTable_sceneCommi"></table>
	</div>
</div>
