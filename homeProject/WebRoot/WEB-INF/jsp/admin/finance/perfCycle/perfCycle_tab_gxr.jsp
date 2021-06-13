<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/perfCycle/goGxrJqGrid",
			postData:{pk:$("#pk").val(),custCode:$("#custCode").val(),m_umState:$("#m_umState").val(),
					  isAddAllInfo:$("#isAddAllInfo").val(),baseChgNo:$("#baseChgNo").val()},
			rowNum:10000000,
			height:189,
			sortable: true,
			colModel : [
				{name: "pk", index: "pk", width: 96, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 96, label: "客户编号", sortable: true, align: "left",hidden:true},
				{name: "perfpk", index: "perfpk", width: 96, label: "业绩pk", sortable: true, align: "left",hidden:true},
				{name: "roledescr", index: "roledescr", width: 96, label: "角色", sortable: true, align: "left"},
				{name: "empcode", index: "empcode", width: 101, label: "员工编号", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 110, label: "员工姓名", sortable: true, align: "left"},
				{name: "perfper", index: "perfper", width: 80, label: "业绩占比", sortable: true, align: "right",sorttype:"number"},
				{name: "dept1descr", index: "dept1descr", width: 118, label: "一级部门", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 121, label: "二级部门", sortable: true, align: "left"},
				{name: "dept3descr", index: "dept3descr", width: 124, label: "三级部门", sortable: true, align: "left"},
				{name: "leadercode", index: "leadercode", width: 102, label: "部门领导编号", sortable: true, align: "left"},
				{name: "leadername", index: "leadername", width: 111, label: "部门领导姓名", sortable: true, align: "left"},
				{name: "iscalcdeptperfdescr", index: "iscalcdeptperfdescr", width: 143, label: "是否计算部门业绩", sortable: true, align: "left"},
				{name: "iscalcpersonperfdescr", index: "iscalcpersonperfdescr", width: 143, label: "是否计算个人业绩", sortable: true, align: "left"},
				{name: "busidrcdescr", index: "busidrcdescr", width: 90, label: "业务主任", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 115, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 102, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 98, label: "操作", sortable: true, align: "left"},
				{name: "department1", index: "department1", width: 118, label: "一级部门", sortable: true, align: "left",hidden:true},
				{name: "department2", index: "department2", width: 121, label: "二级部门", sortable: true, align: "left",hidden:true},
				{name: "department3", index: "department3", width: 124, label: "三级部门", sortable: true, align: "left",hidden:true},
				{name: "iscalcdeptperf", index: "iscalcdeptperf", width: 143, label: "是否计算部门业绩", sortable: true, align: "left",hidden:true},
				{name: "iscalcpersonperf", index: "iscalcpersonperf", width: 143, label: "是否计算个人业绩", sortable: true, align: "left",hidden:true},
				{name: "busidrc", index: "busidrc", width: 90, label: "业务主任", sortable: true, align: "left",hidden:true},
				{name: "role", index: "role", width: 96, label: "角色", sortable: true, align: "left",hidden:true},
			], 
			loadonce:true,
			gridComplete:function(){
				if($("#m_umState").val()=="A"){
					var gxrRecord=$("#gxrDataTable").jqGrid("getGridParam", "records");
					$("#openComponent_customer_custCode").next().unbind("click",alertFun);
					if(gxrRecord>0){
						$("#openComponent_customer_custCode").next().attr("data-disabled",true);
						$("#openComponent_customer_custCode").next().click(alertFun);
					}else{
						$("#openComponent_customer_custCode").next().attr("data-disabled",false);
					}
				}
			}
 		};
	    Global.JqGrid.initJqGrid("gxrDataTable",gridOption);
});

function g_add(){
	var custCode=$("#custCode").val();
	var ids=$("#gxrDataTable").jqGrid("getDataIDs");
	var newId=1;
	var isModified=$("#isModified").val();
	if(isModified=="0"){
		art.dialog({
			content: "人工修改标记为否，无法进行此操作！",
		});
		return;
	}
	if(ids.length>0){
		newId=parseInt(ids[ids.length-1],0)+1;
	} 
	if(custCode==""){
		art.dialog({
			content: "请先选择客户编号！",
		});
		return;
	}
	Global.Dialog.showDialog("goGxrAdd",{
		title:"干系人--增加",
		url:"${ctx}/admin/perfCycle/goGxrAdd?m_umState=A",
		height:420,
		width:700,
		returnFun:function(v){
			var json = {
				roledescr : v.roleDescr,empcode : v.empCode,empname : v.empName,
				perfper : v.perfPer,dept1descr : v.dept1Descr,dept2descr : v.dept2Descr,
				dept3descr : v.dept3Descr,leadercode : v.leaderCode,leadername : v.leaderName,
				iscalcdeptperfdescr : v.isCalcDeptPerfDescr,iscalcpersonperfdescr : v.isCalcPersonPerfDescr,
				remarks : v.remarks,lastupdate : new Date(),lastupdatedby : '${perfCycle.lastUpdatedBy}',
				expired : 'F',actionlog : 'ADD',department1 : v.department1,busidrcdescr : v.busiDrcDescr,
				department2 : v.department2,department3 : v.department3,iscalcdeptperf : v.isCalcDeptPerf,
				iscalcpersonperf : v.isCalcPersonPerf,busidrc : v.busiDrc,role:v.role,pk:(ids.length+1)*-1,
				custcode:$("#custCode").val(),perfpk:$("#m_umState").val()=="A"?"0":$("#pk").val()
			};
			$("#gxrDataTable").jqGrid("addRowData", newId, json);
		}
	});
}
function g_copy(){
	var id = $("#gxrDataTable").jqGrid('getGridParam', 'selrow');
	var ret =selectDataTableRow("gxrDataTable");
	var ids=$("#gxrDataTable").jqGrid("getDataIDs");
	var newId=1;
	var isModified=$("#isModified").val();
	if(isModified=="0"){
		art.dialog({
			content: "人工修改标记为否，无法进行此操作！",
		});
		return;
	}
	if(ids.length>0){
		newId=parseInt(ids[ids.length-1],0)+1;
	} 
	if(!ret){
		art.dialog({
			content: "请选择一条记录！",
		});
		return;
	}
	Global.Dialog.showDialog("goGxrCopy",{
		title:"干系人--复制",
		url:"${ctx}/admin/perfCycle/goGxrAdd?m_umState=C",
		postData:{
			roleDescr: ret.roledescr,empCode : ret.empcode,empName : ret.empname,
			perfPer : ret.perfper,dept1Descr : ret.dept1descr,dept2Descr : ret.dept2descr,
			dept3Descr : ret.dept3descr,leaderCode : ret.leadercode,leaderName : ret.leadername,
			isCalcDeptPerfDescr : ret.iscalcdeptperfdescr,isCalcPersonPerfDescr : ret.iscalcpersonperfdescr,
			remarks : ret.remarks,lastUpdate : ret.lastupdate,lastUpdatedBy : ret.lastupdatedby,
			expired : ret.expired,actionlog : 'EDIT',department1 : ret.department1,busiDrcDescr : ret.busidrcdescr,
			department2 : ret.department2,department3 : ret.department3,isCalcDeptPerf : ret.iscalcdeptperf,
			isCalcPersonPerf : ret.iscalcpersonperf,busiDrc : ret.busidrc,role:ret.role
		},
		height:420,
		width:700,
		returnFun:function(v){
			var json = {
				roledescr : v.roleDescr,empcode : v.empCode,empname : v.empName,
				perfper : v.perfPer,dept1descr : v.dept1Descr,dept2descr : v.dept2Descr,
				dept3descr : v.dept3Descr,leadercode : v.leaderCode,leadername : v.leaderName,
				iscalcdeptperfdescr : v.isCalcDeptPerfDescr,iscalcpersonperfdescr : v.isCalcPersonPerfDescr,
				remarks : v.remarks,lastupdate : new Date(),lastupdatedby : '${perfCycle.lastUpdatedBy}',
				expired : 'F',actionlog : 'ADD',department1 : v.department1,busidrcdescr : v.busiDrcDescr,
				department2 : v.department2,department3 : v.department3,iscalcdeptperf : v.isCalcDeptPerf,
				iscalcpersonperf : v.isCalcPersonPerf,busidrc : v.busiDrc,role:v.role,pk:(ids.length+1)*-1,
				custcode:$("#custCode").val(),perfpk:$("#m_umState").val()=="A"?"0":$("#pk").val()
			};
			$("#gxrDataTable").jqGrid("addRowData", newId, json);
		}
	});
}
function g_update(){
	var id = $("#gxrDataTable").jqGrid('getGridParam', 'selrow');
	var ret =selectDataTableRow("gxrDataTable");
	var isModified=$("#isModified").val();
	if(isModified=="0"){
		art.dialog({
			content: "人工修改标记为否，无法进行此操作！",
		});
		return;
	}
	if(!ret){
		art.dialog({
			content: "请选择一条记录！",
		});
		return;
	}
	Global.Dialog.showDialog("goGxrUpdate",{
		title:"干系人--修改",
		url:"${ctx}/admin/perfCycle/goGxrAdd?m_umState=M",
		postData:{
			roleDescr: ret.roledescr,empCode : ret.empcode,empName : ret.empname,
			perfPer : ret.perfper,dept1Descr : ret.dept1descr,dept2Descr : ret.dept2descr,
			dept3Descr : ret.dept3descr,leaderCode : ret.leadercode,leaderName : ret.leadername,
			isCalcDeptPerfDescr : ret.iscalcdeptperfdescr,isCalcPersonPerfDescr : ret.iscalcpersonperfdescr,
			remarks : ret.remarks,lastUpdate : ret.lastupdate,lastUpdatedBy : ret.lastupdatedby,
			expired : ret.expired,actionlog : 'EDIT',department1 : ret.department1,busiDrcDescr : ret.busidrcdescr,
			department2 : ret.department2,department3 : ret.department3,isCalcDeptPerf : ret.iscalcdeptperf,
			isCalcPersonPerf : ret.iscalcpersonperf,busiDrc : ret.busidrc,role:ret.role,pk:ret.pk
		},
		height:420,
		width:700,
		returnFun:function(v){
			var json = {
				roledescr : v.roleDescr,empcode : v.empCode,empname : v.empName,
				perfper : v.perfPer,dept1descr : v.dept1Descr,dept2descr : v.dept2Descr,
				dept3descr : v.dept3Descr,leadercode : v.leaderCode,leadername : v.leaderName,
				iscalcdeptperfdescr : v.isCalcDeptPerfDescr,iscalcpersonperfdescr : v.isCalcPersonPerfDescr,
				remarks : v.remarks,lastupdate : new Date(),lastupdatedby : '${perfCycle.lastUpdatedBy}',
				expired : 'F',actionlog : 'EDIT',department1 : v.department1,busidrcdescr : v.busiDrcDescr,
				department2 : v.department2,department3 : v.department3,iscalcdeptperf : v.isCalcDeptPerf,
				iscalcpersonperf : v.isCalcPersonPerf,busidrc : v.busiDrc,role:v.role,pk:v.pk,
				custcode:$("#custCode").val(),perfpk:$("#m_umState").val()=="A"?"0":$("#pk").val()
			};
			$("#gxrDataTable").setRowData(id, json);
		}
	});
}
function g_view(){
	var id = $("#gxrDataTable").jqGrid('getGridParam', 'selrow');
	var ret =selectDataTableRow("gxrDataTable");
	if(!ret){
		art.dialog({
			content: "请选择一条记录！",
		});
		return;
	}
	Global.Dialog.showDialog("goGxrUpdate",{
		title:"干系人--查看",
		url:"${ctx}/admin/perfCycle/goGxrAdd?m_umState=V",
		postData:{
			roleDescr: ret.roledescr,empCode : ret.empcode,empName : ret.empname,
			perfPer : ret.perfper,dept1Descr : ret.dept1descr,dept2Descr : ret.dept2descr,
			dept3Descr : ret.dept3descr,leaderCode : ret.leadercode,leaderName : ret.leadername,
			isCalcDeptPerfDescr : ret.iscalcdeptperfdescr,isCalcPersonPerfDescr : ret.iscalcpersonperfdescr,
			remarks : ret.remarks,lastUpdate : ret.lastupdate,lastUpdatedBy : ret.lastupdatedby,
			expired : ret.expired,actionlog : 'EDIT',department1 : ret.department1,busiDrcDescr : ret.busidrcdescr,
			department2 : ret.department2,department3 : ret.department3,isCalcDeptPerf : ret.iscalcdeptperf,
			isCalcPersonPerf : ret.iscalcpersonperf,busiDrc : ret.busidrc,role:ret.role
		},
		height:420,
		width:700,
	});
}
function g_del(){
	var id = $("#gxrDataTable").jqGrid("getGridParam","selrow");
	var isModified=$("#isModified").val();
	if(!id){
		art.dialog({
			content: "请选择一条记录！"
		});
		return;
	}
	if(isModified=="0"){
		art.dialog({
			content: "人工修改标记为否，无法进行此操作！"
		});
		return;
	}
	art.dialog({
		content:"是否确认要删除该条记录？",
		lock: true,
		ok: function () {
			Global.JqGrid.delRowData("gxrDataTable",id);
		},
		cancel: function () {
			return true;
		}
	}); 
}


</script>
<div class="body-box-list" style="margin-top: 0px;">
		<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="g_add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="g_copy()">
					<span>复制</span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="g_update()">
					<span>编辑 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="g_del()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system " onclick="g_view()">
					<span>查看 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="gxrDataTable"></table>
	</div>
</div>



