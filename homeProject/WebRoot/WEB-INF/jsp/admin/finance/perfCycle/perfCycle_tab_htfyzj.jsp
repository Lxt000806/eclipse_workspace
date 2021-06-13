<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/perfCycle/goHtfyzjJqGrid",
			postData:{pk:"${perfCycle.pk}"},
			rowNum:10000000,
			height:155,
			sortable: true,
			colModel : [
				{name: "pk", index: "pk", width: 75, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "chgtypedescr", index: "chgtypedescr", width: 115, label: "费用类型", sortable: true, align: "left"},
				{name: "chgamount", index: "chgamount", width: 106, label: "增减金额", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "statusdescr", index: "statusdescr", width: 87, label: "状态", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 91, label: "审核人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 133, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "appczydescr", index: "appczydescr", width: 79, label: "申请人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 142, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedbydescr", index: "lastupdatedbydescr", width: 85, label: "最后修改人", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 257, label: "备注", sortable: true, align: "left"},
				{name: "chgtype", index: "chgtype", width: 115, label: "费用类型", sortable: true, align: "left",hidden:true},
				{name: "isservice", index: "isservice", width: 115, label: "是否服务性产品", sortable: true, align: "left",hidden:true},
				{name: "iscupboard", index: "iscupboard", width: 115, label: "是否橱柜", sortable: true, align: "left",hidden:true},
				{name: "itemtype1", index: "itemtype1", width: 115, label: "材料类型1", sortable: true, align: "left",hidden:true},
				{name: "chgno", index: "chgno", width: 115, label: "增减单号", sortable: true, align: "left",hidden:true},
				
			], 
			gridComplete:function(){
				if($("#m_umState").val()=="A"){
					var htfyzjRecord=$("#htfyzjDataTable").jqGrid("getGridParam", "records");
					$("#openComponent_customer_custCode").next().unbind("click",alertFun);
					if(htfyzjRecord>0){
						$("#openComponent_customer_custCode").next().attr("data-disabled",true);
						$("#openComponent_customer_custCode").next().click(alertFun);
					}else{
						$("#openComponent_customer_custCode").next().attr("data-disabled",false);
					}
				}
			}
 		};
	    Global.JqGrid.initJqGrid("htfyzjDataTable",gridOption);
});

function h_add(){
	if($("#isAddAllInfo").val()==""){
		art.dialog({
			content:"常规变更不能为空！"
		});				
		return ;
	}
	var chgStakeholder = $("#gxrDataTable").getCol("empcode", false).join("/");
	var chgNos = $("#htfyzjDataTable").getCol("pk", false).join(",");
	Global.Dialog.showDialog("goHtfyzjAdd",{
		title:"选择合同费用增减单",
		url:"${ctx}/admin/perfCycle/goHtfyzjAdd",
		postData:{chgNos:chgNos,custCode:$("#custCode").val(),isAddAllInfo:$("#isAddAllInfo").val(),chgStakeholder:chgStakeholder},
		height:600,
		width:1200,
		returnFun:function(data){
			if(data.length > 0){
				var ids = $("#htfyzjDataTable").jqGrid("getDataIDs");
				$.each(data, function(i,rowData){
					$("#htfyzjDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
				});	
				calcConFeeChg();//计算管理费
				if($("#m_umState").val()=="A")perfTypeDisabled();//设置业绩类型不可编辑
				if ($("#isAddAllInfo").val()=="0"&&chgStakeholder==""){
					var rowData = $("#htfyzjDataTable").jqGrid("getRowData", 1);
					console.log(rowData.itemtype1);
					console.log(rowData.chgno);
					if(rowData.itemtype1=='JZ'){
						$("#baseChgNo").val(rowData.chgno);
						$("#chgNo").val(" ");
					}else{
						$("#baseChgNo").val(" ");
						$("#chgNo").val(rowData.chgno);	
					}
					doQuery('gxrDataTable');
				};
				$("#isAddAllInfo").attr("disabled",true);
			}
		}
	});
}
function h_del(){
	var id = $("#htfyzjDataTable").jqGrid("getGridParam","selrow");
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
			Global.JqGrid.delRowData("htfyzjDataTable",id);
			calcConFeeChg();//计算管理费
			if($("#m_umState").val()=="A")perfTypeEnable();//设置业绩类型可编辑
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
					class="btn btn-system viewFlag" onclick="h_add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="h_del()">
					<span>删除 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="htfyzjDataTable"></table>
	</div>
</div>



