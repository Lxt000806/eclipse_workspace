<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/perfCycle/goJczjJqGrid",
			postData:{pk:"${perfCycle.pk}"},
			rowNum:10000000,
			height:189,
			sortable: true,
			colModel : [
				{name: "no", index: "no", width: 100, label: "增减单号", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 112, label: "基装增减状态", sortable: true, align: "left"},
				{name: "date", index: "date", width: 130, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
				{name: "befamount", index: "befamount", width: 100, label: "优惠前金额", sortable: true, align: "right",sorttype:"number"},
				{name: "discamount", index: "discamount", width: 85, label: "优惠金额", sortable: true, align: "right",sorttype:"number"},
				{name: "amount", index: "amount", width: 87, label: "实际总价", sortable: true, align: "right",sorttype:"number"},
				{name: "managefee", index: "managefee", width: 85, label: "管理费", sortable: true, align: "right",sorttype:"number"},
				{name: "remarks", index: "remarks", width: 244, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"}
			],
			gridComplete:function(){
				if($("#m_umState").val()=="A"){
					var jczjRecord=$("#jczjDataTable").jqGrid("getGridParam", "records");
					$("#openComponent_customer_custCode").next().unbind("click",alertFun);
					if(jczjRecord>0){
						$("#openComponent_customer_custCode").next().attr("data-disabled",true);
						$("#openComponent_customer_custCode").next().click(alertFun);
					}else{
						$("#openComponent_customer_custCode").next().attr("data-disabled",false);
					}
				}
			} 
 		};
	    Global.JqGrid.initJqGrid("jczjDataTable",gridOption);
});

function j_add(){
	if($("#isAddAllInfo").val()==""){
		art.dialog({
			content:"常规变更不能为空！"
		});				
		return ;
	}
	var baseChgStakeholder = $("#gxrDataTable").getCol("empcode", false).join("/");
	var chgNos = $("#jczjDataTable").getCol("no", false).join(",");
	Global.Dialog.showDialog("goJczjAdd",{
		title:"选择基础增减单",
		url:"${ctx}/admin/perfCycle/goJczjAdd",
		postData:{chgNos:chgNos,custCode:$("#custCode").val(),isAddAllInfo:$("#isAddAllInfo").val(),baseChgStakeholder:baseChgStakeholder},
		height:600,
		width:1200,
		returnFun:function(data){
			if(data.length > 0){
				var ids = $("#jczjDataTable").jqGrid("getDataIDs");
				$.each(data, function(i,rowData){
					$("#jczjDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
				});
				calcBaseItemChg();//计算基础增减金额
				calcChgDisc();//计算增减单的优惠
				calcChgDeduction();//计算增减生成的基础单项扣减、材料单品扣减
				if($("#m_umState").val()=="A") perfTypeDisabled();//设置业绩类型不可编辑
				$("#isAddAllInfo").attr("disabled",true);
				if ($("#isAddAllInfo").val()=="0"&&baseChgStakeholder==""){
					var rowData = $("#jczjDataTable").jqGrid("getRowData", 1);
					$("#baseChgNo").val(rowData.no);
					$("#chgNo").val("");
					//baseChgStakeholder=rowData.basechgstakeholder;
					doQuery('gxrDataTable');
				};
				// getBaseChgSetAdd();
				getBasePersonalPlan();
			}
		}
	});
}
function j_del(){
	var id = $("#jczjDataTable").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({
			content: "请选择一条记录！"
		});
		return;
	}
	art.dialog({
		content:"是否确认要删除该条记录？",
		lock: true,
		ok: function () {
			Global.JqGrid.delRowData("jczjDataTable",id);
			calcBaseItemChg();//计算基础增减金额
			calcChgDisc();//计算增减单的优惠
			calcChgDeduction();//计算增减生成的基础单项扣减、材料单品扣减
			if($("#m_umState").val()=="A") perfTypeEnable();//设置业绩类型可编辑
			// getBaseChgSetAdd();
			console.log("xxx");
			getBasePersonalPlan();
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
					class="btn btn-system viewFlag" onclick="j_add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="j_del()">
					<span>删除 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="jczjDataTable"></table>
	</div>
</div>



