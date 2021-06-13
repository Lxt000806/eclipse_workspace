<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格供应商生成*/
	var gridOption ={
			url:"${ctx}/admin/purchase/goPurchaseFeeDetailGrid",
			postData:{no:'${purchase.no }',generateType:'1'}, 
			height:150,
			colModel : [
				{name: "supplfeetypedescr", index: "supplfeetypedescr", width: 80, label: "费用类型",  align: "left"},
				{name: "generatetype", index: "generatetype", width: 80, label: "生成方式",  align: "left", hidden: true},
				{name: "generatetypedescr", index: "generatetypedescr", width: 80, label: "生成方式",  align: "left"},
				{name: "supplfeetype", index: "supplfeetype", width: 70, label: "费用类型",  align: "left", hidden: true},
				{name: "amount", index: "amount", width: 70, label: "金额",sum :true},
				{name: "purchfeestatus", index: "purchfeestatus", width: 70, label: "状态",sum :true},
				{name: "remarks", index: "remarks", width: 220, label: "备注", align: "left"}
			], 
			beforeSelectRow:function(rowId, e){
				setTimeout(function(){
					relocate(rowId,"dataTable_otherCost");
				},10);
			}
 	};
    //初始化送货申请明细
	Global.JqGrid.initEditJqGrid("dataTable_otherCost",gridOption);
	      
    /**初始化表格系统生成*/
    var gridOption2 ={
		url:"${ctx}/admin/purchase/goPurchaseFeeDetailGrid",
		postData:{no:'${purchase.no }',generateType:'2'},
		height:150,
		colModel : [
			{name: "supplfeetypedescr", index: "supplfeetypedescr", width: 80, label: "费用类型",  align: "left"},
			{name: "generatetype", index: "generatetype", width: 80, label: "生成方式",  align: "left", hidden: true},
			{name: "generatetypedescr", index: "generatetypedescr", width: 80, label: "生成方式",  align: "left"},
			{name: "supplfeetype", index: "supplfeetype", width: 70, label: "费用类型",  align: "left", hidden: true},
			{name: "amount", index: "amount", width: 70, label: "金额", align: "right",sum :true},
			{name: "remarks", index: "remarks", width: 220, label: "备注", align: "left"}
		], 
		beforeSelectRow:function(rowId, e){
			setTimeout(function(){
				relocate(rowId,"dataTable_otherCost2");
			},10);
		}
	};
      //初始化送货申请明细
   Global.JqGrid.initEditJqGrid("dataTable_otherCost2",gridOption2);

}); 
 </script>
 <div style="width:1150px;">
			<div style="width: 550px;height: 270px;float: left;">
				<div>
					<table id="dataTable_otherCost"></table>
				</div>
				<!--标签页内容 -->	
			</div>
			<div style="width: 550px;height: 270px;float: left;margin-left: 10px;" >
				<div>
					<table id="dataTable_otherCost2"></table>
				</div>
				<!--标签页内容 -->
				
			</div>
			</div>
		</div>
