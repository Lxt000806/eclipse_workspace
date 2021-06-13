<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_jctc",{
			url:"${ctx}/admin/intBusiCommi/goJqGrid",
			postData: $("#page_form").jsonForm(),
			rowNum: 100000000,
			height:400,
			colModel : [
			    {name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",formatter:jctc_custBtn},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left"},
				{name: "busitypedescr", index: "busitypedescr", width: 80, label: "业务类型", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 65, label: "员工", sortable: true, align: "left"},
				{name: "dept1descr", index: "dept1descr", width: 90, label: "一级部门", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 90, label: "二级部门", sortable: true, align: "left"},
				{name: "itemchgno", index: "itemchgno", width: 70, label: "单号", sortable: true, align: "left",hidden:true },
				{name: "chgno", index: "chgno", width: 75, label: "单号", sortable: true, align: "left",formatter:jctc_chgBtn },
				{name: "iscupboarddescr", index: "iscupboarddescr", width: 70, label: "是否橱柜", sortable: true, align: "left",},
				{name: "saleamount", index: "saleamount", width: 70, label: "销售额", sortable: true, align: "right"},
				{name: "perfper", index: "perfper", width: 70, label: "业绩比例", sortable: true, align: "right"},
				{name: "perfamount", index: "perfamount", width: 70, label: "业绩金额", sortable: true, align: "right"},
				{name: "totalamount", index: "totalamount", width: 70, label: "总提成额", sortable: true, align: "right"},
				{name: "shouldprovideper", index: "shouldprovideper", width: 70, label: "应发比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 80, label: "应发提成额", sortable: true, align: "right"},
				{name: "totalprovideamount", index: "totalprovideamount", width: 100, label: "累计已发提成额", sortable: true, align: "right",formatter:jctc_hisBtn},
			    {name: "thisamount", index: "thisamount", width: 80, label: "本次提成额", sortable: true, align: "right",sum:true},
			    {name: "iscompletedescr", index: "iscompletedescr", width: 70, label: "计算结束", sortable: true, align: "left",},
				{name: "isdesignsaledescr", index: "isdesignsaledescr", width: 80, label: "设计师销售", sortable: true, align: "left",},
				{name: "isfirstprovidedescr", index: "isfirstprovidedescr", width: 70, label: "首次发放", sortable: true, align: "right"},
				{name: "mon", index: "mon", width: 70, label: "提成月份", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 90, label: "签订日期", sortable: true, align: "left", formatter: formatDate},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true,},
            ],
		});
});

function jctc_custBtn(v,x,r){
	return "<a href='#' onclick='jctc_viewCust("+x.rowId+");'>"+v+"</a>";
} 

function jctc_chgBtn(v,x,r){
	return "<a href='#' onclick='jctc_viewChg("+x.rowId+");'>"+v+"</a>";
}  
   	
function jctc_hisBtn(v,x,r){
	return "<a href='#' onclick='jctc_viewHis("+x.rowId+");'>"+v+"</a>";
} 

function jctc_viewChg(id){
	var ret = $("#dataTable_jctc").jqGrid('getRowData', id);
	if (ret) {
		Global.Dialog.showDialog("viewChg",{
			title:"查看材料明细",
			url:"${ctx}/admin/intBusiCommi/goBase?chgNo="+ret.itemchgno,
			height:800,
			width:1300
		});
	}else{
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function jctc_viewCust(id){
	var ret = $("#dataTable_jctc").jqGrid('getRowData', id);
	if (ret) {
        Global.Dialog.showDialog("customerXxView",{
		  title:"查看客户",
		  url:"${ctx}/admin/customerXx/goDetail?id="+ret.custcode,
		  height:750,
		  width:1430
		});
    }else{
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function jctc_viewHis(id){
	var ret = $("#dataTable_jctc").jqGrid('getRowData', id);
	if (ret) {
		Global.Dialog.showDialog("viewHis",{
			title:"查看历史提成",
			url:"${ctx}/admin/intBusiCommi/goHis",
			postData:{custCode:ret.custcode,crtMon:ret.mon},
			height:600,
			width:1200
		});
	}else{
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function jctc_viewBase(){
	Global.Dialog.showDialog("viewBase",{
		title:"材料明细查询",
		url:"${ctx}/admin/intBusiCommi/goBase?crtMon="+$("#month").val(),
		height:800,
		width:1300
	});
}

function jctc_viewSum(){
	Global.Dialog.showDialog("viewSum",{
		title:"提成汇总查询",
		url:"${ctx}/admin/intBusiCommi/goSum?crtMon="+$("#month").val(),
		height:600,
		width:1000
	});
}

function jctc_viewStakeholder(){
	var ret = selectDataTableRow("dataTable_jctc");
	if (ret) {
		Global.Dialog.showDialog("viewStakeholder",{
			title:"查看相关干系人",
			url:"${ctx}/admin/intBusiCommi/goStakeholder",
			postData:{pk:ret.pk},
			height:600,
			width:900
		});
	}else{
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

</script>
<div class="body-box-list">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form_jctc" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system "
					onclick="jctc_viewSum()">
					<span>提成汇总查询</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="jctc_viewBase()">
					<span>材料明细查询</span>
				</button>
			</div>
		</div>
	</div>
	<div class="pageContent" >
		<table id="dataTable_jctc"></table>
	</div>
</div>
