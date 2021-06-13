<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_tcgz_ffbl",{
			url:"${ctx}/admin/commiCustStakeholderProvide/goJqGrid",
			postData: $("#page_form").jsonForm(),
			rowNum: 100000000,
			height:400,
			colModel : [
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},
			    {name: "commino", index: "commino", width: 80, label: "周期编号", sortable: true, align: "left"},
			    {name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 80, label: "角色", sortable: true, align: "left"},
				{name: "commiprovideper", index: "commiprovideper", width: 90, label: "提成发放比例", sortable: true, align: "right"},
				{name: "subsidyprovideper", index: "subsidyprovideper", width: 90, label: "补贴发放比例", sortable: true, align: "right"},
				{name: "multipleprovideper", index: "multipleprovideper", width: 90, label: "倍数发放比例", sortable: true, align: "right"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
            ]
		});
});

function ffbl_view(){
	var ret =selectDataTableRow("dataTable_tcgz_ffbl");
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("ffbl_view",{
		title:"提成干系人-查看",
		postData:{pk:ret.pk},
		url:"${ctx}/admin/commiCustStakeholderProvide/goView",
		height: 400,
	 	width:700,
		returnFun:function(){
			goto_query("dataTable_tcgz_ffbl");
		}
	});
}

</script>
<div class="body-box-list">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form_tcgz_ffbl" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system "
					onclick="ffbl_view()">
					<span>查看</span>
				</button>
			</div>
		</div>
	</div>
	<div class="pageContent" >
		<table id="dataTable_tcgz_ffbl"></table>
	</div>
</div>
