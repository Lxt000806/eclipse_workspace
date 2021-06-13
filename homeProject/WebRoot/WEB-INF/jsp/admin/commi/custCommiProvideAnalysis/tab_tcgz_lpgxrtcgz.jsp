<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_tcgz_lpgxrtcgz",{
			url:"${ctx}/admin/commiCustStakeholderRule/goJqGrid",
			postData: $("#page_form").jsonForm(),
			rowNum: 100000000,
			height:400,
			colModel : [
				{name: "signcommimon", index: "signcommimon", width: 85, label: "签单提成月份", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 70, label: "员工", sortable: true, align: "left"},
				{name: "dept1descr", index: "dept1descr", width: 90, label: "一级部门", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 90, label: "二级部门", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 70, label: "角色", sortable: true, align: "left"},
				{name: "weightper", index: "weightper", width: 70, label: "权重", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 70, label: "提成点", sortable: true, align: "right"},
				{name: "subsidyper", index: "subsidyper", width: 70, label: "补贴点", sortable: true, align: "right"},
				{name: "multiple", index: "multiple", width: 60, label: "倍数", sortable: true, align: "right"},
				{name: "rightcommiper", index:"rightcommiper", label:"右边提成点", width:80, sortable: true, align:"right",}, 
				{name: "precommiexpr", index: "precommiexpr", width: 90, label: "预发提成公式", sortable: true, align: "left"},
				{name: "checkcommiexpr", index: "checkcommiexpr", width: 90, label: "结算提成公式", sortable: true, align: "left"},
				{name: "checkcommitypedescr", index: "checkcommitypedescr", width: 90, label: "结算提成类型", sortable: true, align: "left"},
				{name: "checkcommiper", index: "checkcommiper", width: 80, label: "结算提成点", sortable: true, align: "right"},
				{name: "checkfloatrule", index: "checkfloatrule", width: 90, label: "结算浮动规则", sortable: true, align: "left",formatter:lpgxrtcgz_ruleBtn},
				{name: "oldstakeholderdescr", index: "oldstakeholderdescr", width: 80, label: "原干系人1", sortable: true, align: "left"},
				{name: "oldstakeholderdescr2", index: "oldstakeholderdescr2", width: 80, label: "原干系人2", sortable: true, align: "left"},
				{name: "commiprovideper", index: "commiprovideper", width: 90, label: "提成发放比例", sortable: true, align: "right"},
				{name: "subsidyprovideper", index: "subsidyprovideper", width: 90, label: "补贴发放比例", sortable: true, align: "right"},
				{name: "multipleprovideper", index: "multipleprovideper", width: 90, label: "倍数发放比例", sortable: true, align: "right"},
				{name: "totalprovideamount", index: "totalprovideamount", width: 90, label: "累计发放金额", sortable: true, align: "right"},
				{name: "isbearagaincommidescr", index: "isbearagaincommidescr", width: 90, label: "是否承担翻单提成", sortable: true, align: "left"},
				{name: "ismodifieddescr", index: "ismodifieddescr", width: 85, label: "是否手动修改", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "crtmon", index: "crtmon", width: 70, label: "生成月份", sortable: true, align: "left"},
				{name: "lastupdatemon", index: "lastupdatemon", width: 85, label: "最后修改月份", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},
            ],
		});
});

function lpgxrtcgz_ruleBtn(v,x,r){	
	v = v == null ? "" : v;
	return "<a href='#' onclick='lpgxrtcgz_viewRule("+x.rowId+");'>"+v+"</a>";
} 

function lpgxrtcgz_viewRule(id){
	var ret = $("#dataTable_tcgz_lpgxrtcgz").jqGrid('getRowData', id);
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("lpgxrtcgz_viewRule",{
		title:"查看结算浮动规则",
		postData:{pk:ret.pk},
		url:"${ctx}/admin/commiCustStakeholderRule/goViewRule",
		height: 450,
	 	width:700,
	});
}

function lpgxrtcgz_view(){
	var ret =selectDataTableRow("dataTable_tcgz_lpgxrtcgz");
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("lpgxrtcgz_view",{
		title:"楼盘干系人提成规则-查看",
		postData:{pk:ret.pk,sigmCommiNo:"${commiCycle.no}"},
		url:"${ctx}/admin/commiCustStakeholderRule/goView",
		height: 650,
	 	width:700,
		returnFun:function(){
			goto_query("dataTable_tcgz_lpgxrtcgz");
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
					onclick="lpgxrtcgz_view()">
					<span>查看</span>
				</button>
			</div>
		</div>
	</div>
	<div class="pageContent" >
		<table id="dataTable_tcgz_lpgxrtcgz"></table>
	</div>
</div>
