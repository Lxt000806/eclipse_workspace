<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_tcgz_tcgxr",{
			url:"${ctx}/admin/commiStakeholder/goJqGrid",
			postData: $("#page_form").jsonForm(),
			rowNum: 100000000,
			height:400,
			colModel : [
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left",hidden:true},
			    {name: "empcode", index: "empcode", width: 80, label: "员工编号", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 80, label: "员工姓名", sortable: true, align: "left"},
				{name: "dept1descr", index: "dept1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 70, label: "角色", sortable: true, align: "left"},
				{name: "effectivecardinal", index: "effectivecardinal", width: 70, label: "考核基数", sortable: true, align: "right"},
				{name: "commiper", index: "commiper", width: 80, label: "提成点", sortable: true, align: "right"},
				{name: "subsidyper", index: "subsidyper", width: 80, label: "补贴点", sortable: true, align: "right"},
				{name: "multiple", index: "multiple", width: 60, label: "倍数", sortable: true, align: "right"},
				{name: "designagainsubsidyper", index: "designagainsubsidyper", width: 90, label: "设计师翻单补贴点", sortable: true, align: "right"},
				{name: "recordcommiper", index: "recordcommiper", width: 90, label: "录单提成点", sortable: true, align: "right"},
				{name: "checkcommitypedescr", index: "checkcommitypedescr", width: 85, label: "结算提成类型", sortable: true, align: "left"},
				{name: "checkcommiper", index: "checkcommiper", width: 80, label: "结算提成点", sortable: true, align: "right"},
				{name: "isbearagaincommidescr", index: "isbearagaincommidescr", width: 90, label: "承担翻单提成", sortable: true, align: "left"},
				{name: "checkfloatruledescr", index: "checkfloatruledescr", width: 100, label: "结算提成浮动规则", sortable: true, align: "left"},
				{name: "ismodifieddescr", index: "ismodifieddescr", width: 80, label: "是否手动修改", sortable: true, align: "left"},
				{name: "commimon", index: "commimon", width: 80, label: "提成月份", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 61, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 71, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
            ]
		});
});

function tcgxr_add(){
	Global.Dialog.showDialog("add",{
		title:"提成干系人--增加",
		url:"${ctx}/admin/commiStakeholder/goSave",
		postData:{commiNo:"${commiCycle.no}"},
		height: 600,
	 	width:700,
		returnFun:function(){
			goto_query("dataTable_tcgz_tcgxr");
		}
	});
}

function tcgxr_update(){
	var ret =selectDataTableRow("dataTable_tcgz_tcgxr");
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("update",{
		title:"提成干系人--编辑",
		postData:{pk:ret.pk,commiNo:"${commiCycle.no}"},
		url:"${ctx}/admin/commiStakeholder/goUpdate",
		height: 600,
	 	width:700,
		returnFun:function(){
			goto_query("dataTable_tcgz_tcgxr");
		}
	});
}
	
function tcgxr_view(){
	var ret =selectDataTableRow("dataTable_tcgz_tcgxr");
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	Global.Dialog.showDialog("tcgxr_view",{
		title:"提成干系人-查看",
		postData:{pk:ret.pk,commiNo:"${commiCycle.no}"},
		url:"${ctx}/admin/commiStakeholder/goView",
		height: 600,
	 	width:700,
		returnFun:function(){
			goto_query("dataTable_tcgz_tcgxr");
		}
	});
}

function tcgxr_del() {
    var ret = selectDataTableRow("dataTable_tcgz_tcgxr");
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	if(ret.ismodifieddescr == "否"){
		art.dialog({
			content: "非手动修改，无法删除！"
		});
		return;
	}
	art.dialog({
		content:"您确定要删除该信息吗？",
		lock: true,
		ok: function () {
			$.ajax({
				url : "${ctx}/admin/commiStakeholder/doDelete",
				data : {deleteIds:ret.pk},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
					art.dialog({
						content: "删除该信息出现异常"
					});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								goto_query("dataTable_tcgz_tcgxr");
							}
						});
					}else{
						art.dialog({
							content: obj.msg
						});
					}
				}
			});
			return true;
		},
		cancel: function () {
			return true;
		}
	});
}

</script>
<div class="body-box-list">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form_tcgz_tcgxr" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system "
					onclick="tcgxr_view()">
					<span>查看</span>
				</button>
			</div>
		</div>
	</div>
	<div class="pageContent" >
		<table id="dataTable_tcgz_tcgxr"></table>
	</div>
</div>
