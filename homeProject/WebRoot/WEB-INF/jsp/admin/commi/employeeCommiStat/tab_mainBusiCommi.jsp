<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_mainBusiCommi",{
			url:"${ctx}/admin/mainBusiCommi/goJqGrid",
			postData:{beginMon:"${employee.beginMon}",endMon:"${employee.endMon}",empCode:"${employee.number}"},
			rowNum: 100000000,
			height:350,
			colModel : [
			    {name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
			    {name: "custtypedescr", index: "custtypedescr", width: 120, label: "客户类型", sortable: true, align: "left"},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left"},
				{name: "busitype", index: "busitype", width: 80, label: "业务类型", sortable: true, align: "left",hidden:true},
				{name: "busitypedescr", index: "busitypedescr", width: 80, label: "业务类型", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 65, label: "员工", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 90, label: "部门", sortable: true, align: "left"},
				{name: "itemchgno", index: "itemchgno", width: 70, label: "单号", sortable: true, align: "left",hidden:true },
				{name: "chgno", index: "chgno", width: 70, label: "单号", sortable: true, align: "left"},
				{name: "isservicedescr", index: "isservicedescr", width: 80, label: "服务性产品", sortable: true, align: "left",},
				{name: "totalamount", index: "totalamount", width: 70, label: "总提成额", sortable: true, align: "right"},
				{name: "shouldprovideper", index: "shouldprovideper", width: 70, label: "应发比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 80, label: "应发提成额", sortable: true, align: "right",sum:true},
				{name: "totalprovideamount", index: "totalprovideamount", width: 100, label: "累计已发提成额", sortable: true, align: "right",sum:true},
			    {name: "thisamount", index: "thisamount", width: 80, label: "本次提成额", sortable: true, align: "right",sum:true},
			    {name: "iscompletedescr", index: "iscompletedescr", width: 70, label: "计算结束", sortable: true, align: "left",},
				{name: "isdesignsaledescr", index: "isdesignsaledescr", width: 80, label: "设计师销售", sortable: true, align: "left",},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 90, label: "签订日期", sortable: true, align: "left", formatter: formatDate},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true,},
            ],
		});	
});

</script>
<div class="body-box-list">
	<div class="pageContent" >
		<table id="dataTable_mainBusiCommi"></table>
	</div>
</div>
