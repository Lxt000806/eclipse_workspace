<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_tcmx_tcmx",{
		height:230,
		rowNum: 10000,
		colModel : [
		    {name: "custcode", index: "custcode", width: 93, label: "客户编号", sortable: true, align: "left", count: true},
			{name: "descr", index: "descr", width: 98, label: "提成规则", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 91, label: "提成类型", sortable: true, align: "left"},
			{name: "roledescr", index: "roledescr", width: 83, label: "角色", sortable: true, align: "left"},
			{name: "empcode", index: "empcode", width: 75, label: "员工编号", sortable: true, align: "left"},
			{name: "empname", index: "empname", width: 80, label: "员工姓名", sortable: true, align: "left"},
			{name: "perioddescr", index: "perioddescr", width: 80, label: "提成阶段", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 80, label: "提成金额", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 180, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 146, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 89, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 75, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 92, label: "操作", sortable: true, align: "left"}
        ],
        grouping: true,
        groupingView: {
            groupField: ["custcode"],
            groupColumnShow: [true],
            groupText: ["<b>客户编号：{0}({1})</b>"],
            groupOrder: ["asc"],
            groupCollapse: false
        }
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_tcmx_ygtcmx",{
		height:230,
		rowNum: 10000,
		colModel : [
		    {name: "empcode", index: "empcode", width: 66, label: "员工编号", sortable: true, align: "left", count: true},
			{name: "empname", index: "empname", width: 67, label: "员工姓名", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 73, label: "客户编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 90, label: "提成规则", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 71, label: "提成类型", sortable: true, align: "left"},
			{name: "roledescr", index: "roledescr", width: 63, label: "角色", sortable: true, align: "left"},
			{name: "perioddescr", index: "perioddescr", width: 70, label: "提成阶段", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 70, label: "提成金额", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 147, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 67, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 73, label: "操作", sortable: true, align: "left"}
        ],
        grouping: true,
        groupingView: {
            groupField: ["empcode"],
            groupColumnShow: [true],
            groupText: ["<b>员工编号：{0}({1})</b>"],
            groupOrder: ["asc"],
            groupCollapse: false
        }
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_tcmx_zctcmx",{
		height:230,
		rowNum: 10000,
		colModel : [
		    {name: "empcode", index: "empcode", width: 66, label: "员工编号", sortable: true, align: "left", count: true},
			{name: "empname", index: "empname", width: 66, label: "员工姓名", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 73, label: "客户编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 90, label: "提成规则", sortable: true, align: "left"},
			{name: "roledescr", index: "roledescr", width: 63, label: "角色", sortable: true, align: "left"},
			{name: "itemrefpk", index: "itemrefpk", width: 68, label: "材料需求", sortable: true, align: "left"},
			{name: "perioddescr", index: "perioddescr", width: 70, label: "提成阶段", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 70, label: "提成金额", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 68, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"}
        ],
        grouping: true,
        groupingView: {
            groupField: ["empcode"],
            groupColumnShow: [true],
            groupText: ["<b>员工编号：{0}({1})</b>"],
            groupOrder: ["asc"],
            groupCollapse: false
        }
	});
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable_tcmx_main",{
		url:"${ctx}/admin/customerXx/goJqGrid_tcmx_main",
		postData:{custCode:'${customer.code}'},
		height:150,
		rowNum: 10000,
		colModel : [
		    {name: "no", index: "no", width: 100, label: "批次号", sortable: true, align: "left",count: true},
			{name: "typedescr", index: "typedescr", width: 100, label: "类型", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 100, label: "状态", sortable: true, align: "left"},
			{name: "mon", index: "mon", width: 140, label: "月份", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 150, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 100, label: "操作", sortable: true, align: "left"}
           ],
           onSelectRow : function(id) {
           	var row = selectDataTableRow("dataTable_tcmx_main");
           	$("#dataTable_tcmx_tcmx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_tcmx_tcmx?calNo="+row.no+"&custCode=${customer.code}"});
           	$("#dataTable_tcmx_tcmx").jqGrid().trigger('reloadGrid');
           	$("#dataTable_tcmx_ygtcmx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_tcmx_tcmx?calNo="+row.no+"&custCode=${customer.code}"});
           	$("#dataTable_tcmx_ygtcmx").jqGrid().trigger('reloadGrid');
           	$("#dataTable_tcmx_zctcmx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_tcmx_zctcmx?calNo="+row.no+"&custCode=${customer.code}"});
           	$("#dataTable_tcmx_zctcmx").jqGrid().trigger('reloadGrid');
           }
	});
});
function doExcelTcmx(){
	if ($("#id_tcmx>ul li a[href='#tab_tcmx_tcmx']").parent().hasClass("active")){
		doExcelNow('提成明细','dataTable_tcmx_tcmx','page_form_excel');
	}
	else if ($("#id_tcmx>ul li a[href='#tab_tcmx_ygtcmx']").parent().hasClass("active")){
		doExcelNow('员工提成明细','dataTable_tcmx_ygtcmx','page_form_excel');
	}
	else if ($("#id_tcmx>ul li a[href='#tab_tcmx_zctcmx']").parent().hasClass("active")){
		doExcelNow('主材提成明细','dataTable_tcmx_zctcmx','page_form_excel');
	}
}
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_tcmx_main"></table>
	</div>
	<div class="container-fluid" id="id_tcmx">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_tcmx_tcmx" data-toggle="tab">提成明细</a></li>
	        <li class=""><a href="#tab_tcmx_ygtcmx" data-toggle="tab">员工提成明细</a></li>
	        <li class=""><a href="#tab_tcmx_zctcmx" data-toggle="tab">主材提成明细</a></li>
	        <a href="javascript:void(0)" onclick="doExcelTcmx()" style="position: relative;left: 10px;top: 4px;font-size: 13px;">【导出excel】</a>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_tcmx_tcmx" class="tab-pane fade in active"> 
	         	<div class="body-box-list">
					<div class="pageContent" style="padding-top: 10px;">
						<table id="dataTable_tcmx_tcmx"></table>
					</div>
				</div>
	        </div>  
	        <div id="tab_tcmx_ygtcmx" class="tab-pane fade"> 
	         	<div class="body-box-list">
					<div class="pageContent" style="padding-top: 10px;">
						<table id="dataTable_tcmx_ygtcmx"></table>
					</div>
				</div>
	        </div>
	        <div id="tab_tcmx_zctcmx" class="tab-pane fade"> 
	         	<div class="body-box-list">
					<div class="pageContent" style="padding-top: 10px;">
						<table id="dataTable_tcmx_zctcmx"></table>
					</div>
				</div>
	        </div>  
	    </div>  
	</div>
</div>
