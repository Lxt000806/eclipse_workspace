<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/perfCycle/goClzjJqGrid",
			postData:{pk:"${perfCycle.pk}"},
			rowNum:10000000,
			height:155,
			sortable: true,
			colModel : [
				{name: "no", index: "no", width: 100, label: "增减单号", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 95, label: "材料增减状态", sortable: true, align: "left"},
				{name: "isservicedescr", index: "isservicedescr", width: 112, label: "是否服务性产品", sortable: true, align: "left"},
				{name: "iscupboarddescr", index: "iscupboarddescr", width: 83, label: "是否橱柜", sortable: true, align: "left"},
				{name: "date", index: "date", width: 130, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
				{name: "befamount", index: "befamount", width: 85, label: "优惠前金额", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "discamount", index: "discamount", width: 85, label: "优惠金额", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "amount", index: "amount", width: 85, label: "实际总价", sortable: true, align: "right",sorttype:"number", sum: true},
				{name: "managefee", index: "managefee", width: 85, label: "管理费", sortable: true, align: "right",sorttype:"number"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 103, label: "申请人", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 106, label: "审核人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 107, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
				{name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left",hidden:true},
				{name: "isservice", index: "isservice", width: 112, label: "是否服务性产品", sortable: true, align: "left",hidden:true},
				{name: "iscupboard", index: "iscupboard", width: 83, label: "是否橱柜", sortable: true, align: "left",hidden:true},
				{name: "disccost", index: "disccost", width: 83, label: "优惠分摊成本", sortable: true, align: "left",hidden:true},
				
			],
			gridComplete:function(){
				if($("#m_umState").val()=="A"){
					var clzjRecord=$("#clzjDataTable").jqGrid("getGridParam", "records");
					$("#openComponent_customer_custCode").next().unbind("click",alertFun);
					if(clzjRecord>0){
						$("#openComponent_customer_custCode").next().attr("data-disabled",true);
						$("#openComponent_customer_custCode").next().click(alertFun);
					}else{
						$("#openComponent_customer_custCode").next().attr("data-disabled",false);
					}
				}
			} 
 		};
	    Global.JqGrid.initJqGrid("clzjDataTable",gridOption);
	    $("#gbox_clzjDataTable").css("width","1420px");
	    $("#gbox_clzjDataTable").children().eq(2).css("width","1420px");
});

function c_add(){
	if($("#isAddAllInfo").val()==""){
		art.dialog({
			content:"常规变更不能为空！"
		});				
		return ;
	}
	var chgNos = $("#clzjDataTable").getCol("no", false).join(",");
	var chgStakeholder = $("#gxrDataTable").getCol("empcode", false).join("/");
	Global.Dialog.showDialog("goClzjAdd",{
		title:"选择材料增减单",
		url:"${ctx}/admin/perfCycle/goClzjAdd",
		postData:{chgNos:chgNos,custCode:$("#custCode").val(),isAddAllInfo:$("#isAddAllInfo").val(),chgStakeholder:chgStakeholder},
		height:600,
		width:1200,
		returnFun:function(data){
			if(data.length > 0){
				var ids = $("#clzjDataTable").jqGrid("getDataIDs");
				$.each(data, function(i,rowData){
					$("#clzjDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
				});	
				calcItemChg();//计算材料增减金额
				calcChgDisc();//计算增减单的优惠
				calcChgDeduction();//计算增减生成的基础单项扣减、材料单品扣减
				if($("#m_umState").val()=="A")perfTypeDisabled();//设置业绩类型不可编辑
				$("#isAddAllInfo").attr("disabled",true);
				if ($("#isAddAllInfo").val()=="0"&&chgStakeholder==""){
					var rowData = $("#clzjDataTable").jqGrid("getRowData", 1);
					$("#baseChgNo").val(" ");
					$("#chgNo").val(rowData.no);
					doQuery('gxrDataTable');
				};
				getChgMainProPer();
			}
		}
	});
}
function c_del(){
	var id = $("#clzjDataTable").jqGrid("getGridParam","selrow");
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
			Global.JqGrid.delRowData("clzjDataTable",id);
			calcItemChg();//计算材料增减金额
			calcChgDisc();//计算增减单的优惠
			calcChgDeduction();//计算增减生成的基础单项扣减、材料单品扣减
			if($("#m_umState").val()=="A")perfTypeEnable();//设置业绩类型可编辑
			getChgMainProPer();
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
					class="btn btn-system viewFlag" onclick="c_add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" 
					class="btn btn-system viewFlag" onclick="c_del()">
					<span>删除 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="clzjDataTable"></table>
	</div>
</div>



