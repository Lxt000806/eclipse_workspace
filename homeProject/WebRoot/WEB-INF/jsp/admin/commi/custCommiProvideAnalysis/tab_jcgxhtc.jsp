<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_jcgxhtc",{
			url:"${ctx}/admin/commiCustStakeholder/goBasePersonalJqGrid",
			postData: $("#page_form").jsonForm(),
			rowNum: 100000000,
			height:400,
			colModel : [
			  	{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",},
				{name: "empname", index: "empname", width: 65, label: "员工", sortable: true, align: "left",},
			    {name: "dept1descr", index: "dept1descr", width: 90, label: "一级部门", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 90, label: "二级部门", sortable: true, align: "left"},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left"},
			    {name: "busitypedescr", index: "busitypedescr", width: 80, label: "业务类型", sortable: true, align: "left"},
				{name: "isprovidedescr", index: "isprovidedescr", width: 80, label: "是否发放", sortable: true, align: "left"},
				{name: "chgno", index: "chgno", width: 80, label: "增减单号", sortable: true, align: "left"},
				{name: "baseitemtype1descr", index: "baseitemtype1descr", width: 80, label: "基装类型1", sortable: true, align: "left",},
				{name: "weightper", index: "weightper", width: 50, label: "权重", sortable: true, align: "right"},
				{name: "amount", index: "amount", width: 70, label: "销售金额", sortable: true, align: "right"},
				{name: "perfper", index: "perfper", width: 70, label: "业绩比例", sortable: true, align: "right"},
				{name: "perfamount", index: "perfamount", width: 70, label: "业绩金额", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 60, label: "提成点", sortable: true, align: "right"},
				{name: "commiamount", index: "commiamount", width: 70, label: "提成金额", sortable: true, align: "right"},
				{name: "commiprovideper", index: "commiprovideper", width: 70, label: "提成比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 70, label: "本次应发", sortable: true, align: "right"},
				{name: "adjustamount", index: "adjustamount", width: 70, label: "调整金额", sortable: true, align: "right"},
				{name: "realprovideamount", index: "realprovideamount", width: 70, label: "本次实发", sortable: true, align: "right",sum:true},
				{name: "totalrealprovideamount", index: "totalrealprovideamount", width: 70, label: "累计已发", sortable: true, align: "right"},
				{name: "isfirstprovidedescr", index: "isfirstprovidedescr", width: 70, label: "首次发放", sortable: true, align: "right"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "mon", index: "mon", width: 70, label: "提成月份", sortable: true, align: "left"},
				{name: "signcommimon", index: "signcommimon", width: 90, label: "签单提成月份", sortable: true, align: "left"},
				{name: "reprovidemon", index: "reprovidemon", width: 90, label: "补发提成月份", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},           
           ],
		});
});	

</script>
<div class="body-box-list">
	<div class="pageContent">
		<table id="dataTable_jcgxhtc"></table>
	</div>
</div>
