<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_sjftc",{
			url:"${ctx}/admin/commiCustStakeholder/goDesignFeeJqGrid",
			postData:{commiNo:$("#no").val()},
			rowNum: 100000000,
			height:400,
			colModel : [
			    {name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left",formatter:sjftc_custBtn,frozen:true},
				{name: "empname", index: "empname", width: 65, label: "员工", sortable: true, align: "left",frozen:true},
				{name: "dept1descr", index: "dept1descr", width: 90, label: "一级部门", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 90, label: "二级部门", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 65, label: "角色", sortable: true, align: "left",frozen:true},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left",frozen:true},
				{name: "weightper", index: "weightper", width: 50, label: "权重", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 60, label: "提成点", sortable: true, align: "right"},
				{name: "commiamount", index: "commiamount", width: 70, label: "提成金额", sortable: true, align: "right"},
				{name: "commiprovideper", index: "commiprovideper", width: 70, label: "提成比例", sortable: true, align: "right"},
				{name: "shouldprovideamount", index: "shouldprovideamount", width: 70, label: "本次应发", sortable: true, align: "right"},
				{name: "adjustamount", index: "adjustamount", width: 70, label: "调整金额", sortable: true, align: "right"},
				{name: "realprovideamount", index: "realprovideamount", width: 70, label: "本次实发", sortable: true, align: "right"},
				{name: "totalrealprovideamount", index: "totalrealprovideamount", width: 70, label: "累计已发", sortable: true, align: "right"},
				{name: "designfee", index: "designfee", width: 70, label: "设计费", sortable: true, align: "right"},
				{name: "drawfee", index: "drawfee", width: 70, label: "绘图费", sortable: true, align: "right"},
				{name: "colordrawfee", index: "colordrawfee", width: 70, label: "效果图费", sortable: true, align: "right"},
				{name: "stddrawfee", index: "stddrawfee", width: 100, label: "公司标准绘图费", sortable: true, align: "right"},
				{name: "stdcolordrawfee", index: "stdcolordrawfee", width: 100, label: "公司标准效果图费", sortable: true, align: "right"},
				{name: "commiexprremarks", index: "commiexprremarks", width: 200, label: "提成公式", sortable: true, align: "left"},
			    {name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true,},
            ],
            /* gridComplete:function(){
	           	$(".ui-jqgrid-bdiv").scrollTop(0);
	        	frozenHeightReset("dataTable_sjftc");
	        }, */
		});
		//$("#dataTable_sjftc").jqGrid("setFrozenColumns");
});	

function sjftc_custBtn(v,x,r){
	return "<a href='#' onclick='sjftc_viewCust("+x.rowId+");'>"+v+"</a>";
} 

function sjftc_viewCust(id){
	var ret = $("#dataTable_sjftc").jqGrid('getRowData', id);
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

function sjftc_adj(){
	var ret =selectDataTableRow("dataTable_sjftc");
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("adj",{
		title:"业绩提成--调整金额",
		url:"${ctx}/admin/commiCustStakeholder/goAdjAmount",
		postData:{pk:ret.pk},
		height: 400,
	 	width:700,
		returnFun:function(){
			goto_query("dataTable_sjftc");
		}
	});
}

function sjftc_view(id){
	var ret = $("#dataTable_sjftc").jqGrid('getRowData', id);
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"业绩提成-查看",
		postData:{pk:ret.pk,sigmCommiNo:"${commiCycle.no}"},
		url:"${ctx}/admin/commiCustStakeholder/goView",
		height: 600,
	 	width:700,
	});
}

</script>
<div class="body-box-list">
	<!-- <div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form_yjtc" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system viewFlag"
					onclick="sjftc_adj()">
					<span>调整金额</span>
				</button>
			</div>
		</div>
	</div> -->
	<div class="pageContent">
		<table id="dataTable_sjftc"></table>
	</div>
</div>
