<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_yjtc",{
			url:"${ctx}/admin/commiCustStakeholder/goJqGrid",
			postData:{commiNo:$("#no").val()},
			rowNum: 100000000,
			height:400,
			colModel : [
			    {name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left",frozen:true,formatter:hisBtn},
				{name: "empname", index: "empname", width: 60, label: "员工", sortable: true, align: "left",frozen:true,formatter:empBtn},
				{name: "department1descr", index: "department1descr", width: 75, label: "一级部门", sortable: true, align: "left",frozen:true,},
				{name: "department2descr", index: "department2descr", width: 75, label: "二级部门", sortable: true, align: "left",frozen:true,},
				{name: "roledescr", index: "roledescr", width: 65, label: "角色", sortable: true, align: "left",frozen:true},
			    {name: "typedescr", index: "typedescr", width: 80, label: "提成类型", sortable: true, align: "left",frozen:true},
				{name: "perfamount", index: "perfamount", width: 80, label: "业绩金额", sortable: true, align: "right"},
				{name: "weightper", index: "weightper", width: 45, label: "权重", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 55, label: "提成点", sortable: true, align: "right"},
				{name: "subsidyper", index: "subsidyper", width: 55, label: "补贴点", sortable: true, align: "right"},
				{name: "multiple", index: "multiple", width: 45, label: "倍数", sortable: true, align: "right"},
				{name: "rightcardinal", index: "rightcardinal", width: 90, label: "右边销售基数", sortable: true, align: "right"},
				{name: "rightcommiper", index: "rightcommiper", width: 90, label: "右边提成点", sortable: true, align: "right"},
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
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},
            ],
            gridComplete:function(){
	           	$(".ui-jqgrid-bdiv").scrollTop(0);
	        	frozenHeightReset("dataTable_yjtc");
	        },
		});
		$("#dataTable_yjtc").jqGrid("setFrozenColumns");
});

function empBtn(v,x,r){
	return "<a href='#' onclick='yjtc_view("+x.rowId+");'>"+v+"</a>";
} 

function hisBtn(v,x,r){
	return "<a href='#' onclick='yjtc_his("+x.rowId+");'>"+v+"</a>";
}  	

function yjtc_adj(){
	var ret =selectDataTableRow("dataTable_yjtc");
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
			goto_query("dataTable_yjtc");
		}
	});
}

function yjtc_view(id){
	var ret = $("#dataTable_yjtc").jqGrid('getRowData', id);
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

function yjtc_his(id){
	var ret = $("#dataTable_yjtc").jqGrid('getRowData', id);
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("ViewHisCommi",{
		title:"查看历史业绩提成",
		postData:{pk:ret.pk,mon:"${commiCycle.mon}"},
		url:"${ctx}/admin/commiCustStakeholder/goViewHisCommi",
		height: 600,
	 	width:1200,
	});
}

function doQueryType(){
	$("#dataTable_yjtc").jqGrid(
	"setGridParam",{
		datatype:"json",
		postData:{commiNo:$("#no").val(),type:$("#type").val()},
		page:1,
		url:"${ctx}/admin/commiCustStakeholder/goJqGrid",
	}
	).trigger("reloadGrid"); 
}
</script>
<div class="body-box-list">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form_yjtc" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system viewFlag"
					onclick="yjtc_adj()">
					<span>调整金额</span>
				</button>
				<select style="width:160px; border-radius:5px" onchange="doQueryType()" id="type">
					<option value="">选择提成类型...</option>
					<option value="1">签单业绩提成</option>
					<option value="2">二期提成</option>
					<option value="3">增减业绩提成</option>
					<option value="4">退单扣提成</option>
					<option value="5">重签扣提成</option>
					<option value="6">结算业绩提成</option>
					<option value="7">补发提成</option>
					<option value="8">设计师承担现场设计师提成</option>
				</select>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<table id="dataTable_yjtc"></table>
	</div>
</div>
