<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>工程信息管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
var ids=0;
$(function(){
 	var oldColModel = [
 			{name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left", frozen: true},
			{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left", frozen: true},
			{name: "custaddress", index: "custaddress", width: 130, label: "楼盘", sortable: true, align: "left", frozen: true},
			{name : "relcustdescr",index : "relcustdescr",width : 90,label:"关系客户",sortable : true,align : "left"},
			{name: "客户名称", index: "客户名称", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "regioncode", index: "regioncode", width: 60, label: "区域code", sortable: true, align: "left", hidden: true},
			{name: "regiondescr", index: "regiondescr", width: 60, label: "区域", sortable: true, align: "left"},
			{name: "合同开工时间", index: "合同开工时间", width: 90, label: "合同开工时间", sortable: true, align: "left", formatter: formatDate},
			{name: "实际开工时间", index: "实际开工时间", width: 90, label: "实际开工时间", sortable: true, align: "left", formatter: formatDate},
			{name: "planspeeddescr", index: "planspeeddescr", width: 70, label: "计划进度", sortable: true, align: "left"},
			{name: "nowspeeddescr", index: "nowspeeddescr", width: 70, label: "实际进度", sortable: true, align: "left"},
			{name: "checktimes", index: "checktimes", width: 70, label: "巡检次数", sortable: true, align: "right"},
			{name: "设计师", index: "设计师", width: 60, label: "设计师", sortable: true, align: "left"},
			{name: "designmanphone", index: "designmanphone", width: 90, label: "设计师电话", sortable: true, align: "left"},
			{name: "dept2desc", index: "dept2desc", width: 60, label: "设计部", sortable: true, align: "left"},
			{name: "projectdescr", index: "projectdescr", width: 70, label: "项目经理", sortable: true, align: "left"},
			{name: "projectmanphone", index: "projectmanphone", width: 104, label: "项目经理电话", sortable: true, align: "left"},
			{name: "工程部1", index: "工程部1", width:65, label: "工程部1", sortable: true, align: "left"},
			{name: "department2descr", index: "department2descr", width: 65, label: "工程部2", sortable: true, align: "left"},
			{name: "巡检员", index: "巡检员", width: 60, label: "巡检员", sortable: true, align: "left"},
			{name: "mainmanagerdescr", index: "mainmanagerdescr", width: 75, label: "主材管家", sortable: true, align: "left"},
			{name: "mainmanagerphone", index: "mainmanagerphone", width: 90, label: "主材管家电话", sortable: true, align: "left"},
			{name: "jcdsman", index: "jcdsman", width: 90, label: "集成设计师1", sortable: true, align: "left",hidden:true},
			{name: "jcdsmnamechi", index: "jcdsmnamechi", width: 90, label: "集成设计师", sortable: true, align: "left"},
			{name: "jcdsmphone", index: "jcdsmphone", width: 135, label: "集成设计师电话", sortable: true, align: "left"},
			{name: "cgdsmcode", index: "cgdsmcode", width: 130, label: "橱柜设计师编号", sortable: true, align: "left",hidden:true},
			{name: "cgdsmnamechi", index: "cgdsmnamechi", width: 80, label: "橱柜设计师", sortable: true, align: "left"},
			{name: "empcode", index: "empcode", width: 80, label: "软装设计师1", sortable: true, align: "left",hidden:true},
			{name: "rzdsmnamechi", index: "rzdsmnamechi", width: 80, label: "软装设计师", sortable: true, align: "left"},
			{name: "rzdsmphone", index: "rzdsmphone", width: 111, label: "软装设计师电话", sortable: true, align: "left"},
			{name: "custscenedesidescr", index: "custscenedesidescr", width: 80, label: "现场设计师", sortable: true, align: "left"},
			{name: "设计费", index: "设计费", width: 90, label: "设计费", sortable: true, align: "right",sum:true},
			{name: "管理费预算", index: "管理费预算", width: 80, label: "管理费预算", sortable: true, align: "right", sum: true},
			{name: "集成费预算", index: "集成费预算", width: 80, label: "集成费预算", sortable: true, align: "right", sum: true},
			{name: "橱柜费预算", index: "橱柜费预算", width: 80, label: "橱柜费预算", sortable: true, align: "right", sum: true},
			{name: "直接费预算", index: "直接费预算", width: 80, label: "直接费预算", sortable: true, align: "right", sum: true},
			{name: "基础费预算", index: "基础费预算", width: 80, label: "基础费预算", sortable: true, align: "right", sum: true},
			{name: "主材预算", index: "主材预算", width: 90, label: "主材费预算", sortable: true, align: "right", sum: true},
			{name: "软装预算", index: "软装预算", width: 90, label: "软装预算", sortable: true, align: "right", sum: true},
			{name: "服务性产品", index: "服务性产品", width: 90, label: "服务性产品", sortable: true, align: "right", sum: true},
			{name: "basecost", index: "basecost", width: 90, label: "基础合同额", sortable: true, align: "right", sum: true},
			{name: "maincost", index: "maincost", width: 90, label: "主材合同额", sortable: true, align: "right", sum: true},
			{name: "softcost", index: "softcost", width: 90, label: "软装合同额", sortable: true, align: "right", sum: true},
			{name: "intecost", index: "intecost", width: 90, label: "集成合同额", sortable: true, align: "right", sum: true},
			{name: "工程总造价", index: "工程总造价", width: 90, label: "工程总造价", sortable: true, align: "right", sum: true},
			{name: "discremarks", index: "discremarks", width: 250, label: "优惠说明", sortable: true, align: "left"},
			{name: "施工方式", index: "施工方式", width: 90, label: "施工方式", sortable: true, align: "left"},
			{name: "户型", index: "户型", width: 90, label: "户型", sortable: true, align: "left"},
			{name: "面积", index: "面积", width: 90, label: "面积", sortable: true, align: "right", sum: true},
			{name: "开工令时间", index: "开工令时间", width: 95, label: "开工令时间", sortable: true, align: "left", formatter: formatTime},
			{name: "施工工期", index: "施工工期", width: 90, label: "施工工期", sortable: true, align: "left"},
			{name: "施工状态", index: "施工状态", width: 90, label: "施工状态", sortable: true, align: "left"},
			{name: "客户结算状态", index: "客户结算状态", width: 101, label: "客户结算状态", sortable: true, align: "left"},
			{name: "客户结算日期", index: "客户结算日期", width: 96, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime},
			{name: "havepay", index: "havepay", width: 90, label: "已付款", sortable: true, align: "right", sum: true},
			{name: "firstpay", index: "firstpay", width: 90, label: "首付款", sortable: true, align: "right", sum: true},
			{name: "secondpay", index: "secondpay", width: 90, label: "二批付款", sortable: true, align: "right", sum: true},
			{name: "thirdpay", index: "thirdpay", width: 90, label: "三批付款", sortable: true, align: "right", sum: true},
			{name: "custcountcost", index: "custcountcost", width: 100, label: "客户结算金额", sortable: true, align: "right", sum: true},
			{name: "prjmancost", index: "prjmancost", width: 120, label: "项目经理结算金额", sortable: true, align: "right", sum: true},
			{name: "consmancost", index: "consmancost", width: 120, label: "工程经理结算金额", sortable: true, align: "right", sum: true},
			{name: "fourpay", index: "fourpay", width: 90, label: "尾款", sortable: true, align: "right", sum: true},
			{name: "zjxhj", index: "zjxhj", width: 90, label: "增减项合计", sortable: true, align: "right", sum: true},
			{name: "jczj", index: "jczj", width: 90, label: "基础增减", sortable: true, align: "right", sum: true},
			{name: "竣工日期", index: "竣工日期", width: 95, label: "竣工日期", sortable: true, align: "left", formatter: formatTime},
			{name: "预计结算日期", index: "预计结算日期", width: 95, label: "预计结算日期", sortable: true, align: "left", formatter: formatTime},
			{name: "实际结算日期", index: "实际结算日期", width: 95, label: "实际结算日期", sortable: true, align: "left", formatter: formatTime},
			{name: "delayday", index: "delayday", width: 90, label: "拖延天数", sortable: true, align: "right", sum: true},
			{name: "intmsrdate", index: "intmsrdate", width: 101, label: "集成测量时间", sortable: true, align: "left", formatter: formatTime},
			{name: "intdlyday", index: "intdlyday", width: 101, label: "集成拖延天数", sortable: true, align: "right", sum: true},
			{name: "是否客诉", index: "是否客诉", width: 70, label: "是否客诉", sortable: true, align: "left"},
			{name: "客户渠道", index: "客户渠道", width: 100, label: "客户渠道", sortable: true, align: "left"},
			{name: "businessflowdescr", index: "businessflowdescr", width: 120, label: "业务员跟单员", sortable: true, align: "left"},
			{name: "派单时间", index: "派单时间", width: 95, label: "派单时间", sortable: true, align: "left",formatter: formatDate},
			{name: "片区", index: "片区", width: 100, label: "片区", sortable: true, align: "left"},
			{name: "havereturndescr", index: "havereturndescr", width: 70, label: "回执单", sortable: true, align: "left"},
			{name: "havecheckdescr", index: "havecheckdescr", width: 90, label: "交房验收单", sortable: true, align: "left"},
			{name: "havephotodescr", index: "havephotodescr", width: 80, label: "交房相片", sortable: true, align: "left"},
			{name: "preloftsman", index: "preloftsman", width: 71, label: "预放样员1", sortable: true, align: "left",hidden:true},
			{name: "preloftsmanname", index: "preloftsmanname", width: 71, label: "预放样员", sortable: true, align: "left"},
			{name: "realaddress", index: "realaddress", width: 140, label: "实际地址", sortable: true, align: "left", },
			{name: "toiletnum", index: "toiletnum", width: 80, label: "卫生间个数", sortable: true, align: "left", },
			{name: "bedroomnum", index: "bedroomnum", width: 75, label: "卧室个数", sortable: true, align: "left", },
			{name: "balconynum", index: "balconynum", width: 75, label: "阳台个数", sortable: true, align: "left", },
			{name: "kitchdoortypedescr", index: "kitchdoortypedescr", width: 75, label: "厨房类型", sortable: true, align: "left", },
			{name: "iswood", index: "iswood", width: 60, label: "有木作", sortable: true, align: "left", },
			{name: "balconytitle", index: "balconytitle", width: 80, label: "阳台贴墙面砖", sortable: true, align: "left", },
			{name: "isbuilderwall", index: "isbuilderwall", width: 60, label: "新砌墙", sortable: true, align: "left", },
			{name: "lastcustpaydate", index: "lastcustpaydate", width: 140, label: "最后付款时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastconfirmdate", index: "lastconfirmdate", width: 140, label: "最后领料审核时间", sortable: true, align: "left", formatter: formatTime},
			{name: "isinitsign", index: "isinitsign", width: 140, label: "草签标志", sortable: true, align: "left", hidden: true},
			{name: "isinitsigndescr", index: "isinitsigndescr", width: 50, label: "草签", sortable: true, align: "left"},
			{name: "最后修改时间", index: "最后修改时间", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "最后操作人员", index: "最后操作人员", width: 100, label: "最后操作人员", sortable: true, align: "left"},
			{name: "rzpk", index: "rzpk", width: 100, label: "rzpk", sortable: true, align: "left",hidden:true},
			{name: "status", index: "status", width: 100, label: "status", sortable: true, align: "left", hidden:true},
			{name: "cgpk", index: "cgpk", width: 70, label: "橱柜PK", sortable: true, align: "left",hidden:true },
			{name: "checkstatus", index: "checkstatus", width: 100, label: "status", sortable: true, align: "left", hidden:true},
		];
		var colModel = '${colModel}'?eval('('+'${colModel}'+')'):oldColModel;
        var tmpModel = [];
        $.each(oldColModel,function(m,n){
			  var flag = false;
			  $.each(colModel,function(k,v){
				  if (v.label==n.label){
					  flag = true;
					  return false;
				  }
			  });
			  if (flag==false){
				  colModel.push(n);
			  }
		});
        $.each(colModel,function(m,n){
			  $.each(oldColModel,function(k,v){
				  if (v.label==n.label){
					  tmpModel.push(v);
				  }
			  });
		});
        colModel = tmpModel;
        var gridOption = {
       		height:$(document).height()-$("#content-list").offset().top-100,
   			colModel: colModel,
   			//sortable: true,
            loadComplete: function(){
             	$('.ui-jqgrid-bdiv').scrollTop((ids-1)*($("#1").height()+1));
		        $("#dataTable").setSelection(ids);
          	  	frozenHeightReset("dataTable");
          	  	ids=0;
            },
            ondblClickRow: function(){
            	view();
            }
   		};
		Global.JqGrid.initJqGrid("dataTable", gridOption);
		$("#dataTable").jqGrid('setFrozenColumns');
	
	$("#projectMan").openComponent_employee();
	$("#checkMan").openComponent_employee();
	$("#builderCode").openComponent_builder();
	$("#custSceneDesi").openComponent_employee({condition: {position: '572'}});

	window.drag = function() {
		$.jgrid.gridUnload("dataTable");
		Global.JqGrid.initJqGrid("dataTable", $.extend(gridOption, {sortable: true,url: "${ctx}/admin/gcxxgl/goJqGrid",
			postData:$("#page_form").jsonForm(),page:1}));
	}
	window.dragSave = function() {
			var colModel = $("#dataTable").jqGrid('getGridParam','colModel');
			colModel.splice(0,1);
			var datas = {
				"jsonString": JSON.stringify(colModel)
			};
			$.ajax({
				url:'${ctx}/admin/gcxxgl/doSaveCols',
				type: 'post',
				data: datas,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		$.jgrid.gridUnload("dataTable");
			    		Global.JqGrid.initJqGrid("dataTable", $.extend(gridOption,{colModel: colModel,sortable: false,
			    			url: "${ctx}/admin/gcxxgl/goJqGrid",postData:$("#page_form").jsonForm(),page:1}));
			    		$("#dataTable").jqGrid('setFrozenColumns');
			    	}
			    }
			 });
		}

	$("#save").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		Global.Dialog.showDialog("Save",{
			title:"工程信息——录入",
			url:"${ctx}/admin/gcxxgl/goSave",
			postData:{code:ret.code,planPrjDescr:ret.planspeeddescr,nowPrjDescr:ret.nowspeeddescr,businessFlowDescr:ret.businessflowdescr,
					delayDay:ret.delayday,prjManCost:ret.prjmancost,consManCost:ret.consmancost,zjxhj:ret.zjxhj,jczj:ret.jczj,
					baseCost:ret.basecost,mainCost:ret.maincost,softCost:ret.softcost,inteCost:ret.intecost,preloftsMan:ret.preloftsman
					,department2:ret.department2descr,preloftsManDescr:ret.preloftsmandescr,havePay:ret.havepay,custCountCost:ret.custcountcost
					,status:ret.status},
			height:800,
			width:1300,
			returnFun:goto_query
		});
	});
	
	$("#insertPrjMan").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		if(ret.projectdescr != ""){
			art.dialog({
       			content: "已存在项目经理，不允许录入！",
       		});
       		return;
		}
		Global.Dialog.showDialog("InsertPrjMan",{
			title:"工程信息——录入项目经理",
			url:"${ctx}/admin/gcxxgl/goInsertPrjMan",
			postData:{code:ret.code},
			height:350,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#updatePrjMan").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		Global.Dialog.showDialog("UpdatePrjMan",{
			title:"工程信息——修改项目经理",
			url:"${ctx}/admin/gcxxgl/goUpdatePrjMan",
			postData:{code:ret.code},
			height:350,
			width:700,
			returnFun:goto_query
		});
	}); 
		
	$("#updatePhone").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		var rowid = $("#dataTable").jqGrid("getGridParam", "selrow");
		ids=rowid;
		Global.Dialog.showDialog("updatePhone",{
			title:"客户电话——编辑",
			url:"${ctx}/admin/gcxxgl/goUpdatePhone",
			postData:{no:ret.code},
			height:400,
			width:700,
			returnFun:query
		});
	}); 
		
	$("#updateDesign").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		var rowid = $("#dataTable").jqGrid("getGridParam", "selrow");
		ids=rowid;
		Global.Dialog.showDialog("updateDesign",{
			title:"设计师——编辑",
			url:"${ctx}/admin/gcxxgl/goUpdateDesign",
			postData:{no:ret.code,empCode:ret.empcode,softPK:ret.rzpk},
			height:400,
			width:700,
			returnFun:query
		});
	});
	
	$("#updateMainManager").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		var rowid = $("#dataTable").jqGrid("getGridParam", "selrow");
		ids=rowid;
		Global.Dialog.showDialog("updateMainManager",{
			title:"主材管家——编辑",
			url:"${ctx}/admin/gcxxgl/goUpdateMainManager",
			postData:{no:ret.code},
			height:400,
			width:700,
			returnFun:query
		});
	});
	
	$("#updateInteDesign").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		var rowid = $("#dataTable").jqGrid("getGridParam", "selrow");
		ids=rowid;
		Global.Dialog.showDialog("updateDesign",{
			title:"设计师——编辑",
			url:"${ctx}/admin/gcxxgl/goUpdateInteDesign",
			postData:{code:ret.code,jcdsMan:ret.jcdsman,
			CGDesignCode:ret.cgdsmcode,cgPk:ret.cgpk},
			height:400,
			width:700,
			returnFun:query
		});
	});	
	
	$("#updateRealAddress").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		  return;
       		
		}
		var rowid = $("#dataTable").jqGrid("getGridParam", "selrow");
		ids=rowid;
		Global.Dialog.showDialog("updateRealAddress",{
			title:"实际地址——编辑",
			url:"${ctx}/admin/gcxxgl/goUpdateRealAddress",
			postData:{no:ret.code},
			height:400,
			width:700,
			returnFun:query
		});
	});
	 
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		Global.Dialog.showDialog("Save",{
			title:"工程信息——查看",
			url:"${ctx}/admin/gcxxgl/goView",
			postData:{code:ret.code,custCountCost:ret.custcountcost,planPrjDescr:ret.planspeeddescr,nowPrjDescr:ret.nowspeeddescr,
					businessFlowDescr:ret.businessflowdescr,delayDay:ret.delayday,prjManCost:ret.prjmancost,
					consManCost:ret.consmancost,zjxhj:ret.zjxhj,jczj:ret.jczj,department2:ret.department2descr,
					 baseCost:ret.basecost,mainCost:ret.maincost,softCost:ret.softcost,inteCost:ret.intecost
					 ,preloftsMan:ret.preloftsman,havePay:ret.havepay
					},
			height:800,
			width:1300,
			returnFun:goto_query
		});
	}); 
		
	$("#batchUpdate").on("click",function(){
		Global.Dialog.showDialog("batchUpdate",{
			title:"工程信息——批量修改",
			url:"${ctx}/admin/gcxxgl/goBatchUpdate",
			postData:{deliveType:'1',purType:''},
			height:800,
			width:1050,
			returnFun:goto_query
		});
	}); 
		
	$("#prjCheck").on("click",function(){
		Global.Dialog.showDialog("prjCheck",{
			title:"工程信息——工地统计分析",
			url:"${ctx}/admin/gcxxgl/goGdtjfx",
			postData:{deliveType:'1',purType:''},
			height:800,
			width:1150,
			returnFun:goto_query
		});
	}); 
	
	$("#custCheckData").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(!ret){
			art.dialog({
       			content: "请选择一条记录"
       		});
       		return;
		}
		Global.Dialog.showDialog("prjCheck",{
			title:"工程信息——工地巡检数据",
			url:"${ctx}/admin/gcxxgl/goCustCheckData",
			postData:{custCode:ret.code},
			height:600,
			width:950,
			returnFun:goto_query
		});
	}); 
		
	window.goto_query = function(){
		ids=0;
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/gcxxgl/goJqGrid"}).trigger("reloadGrid");
	}
	window.query = function(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/gcxxgl/goJqGrid"}).trigger("reloadGrid");
	}		
	onCollapse(0);
	
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#openComponent_employee_projectMan").val('');
	$("#openComponent_employee_checkMan").val('');
	$("#openComponent_builder_builderCode").val('');
	$("#page_form select").val('');
	$("#checkStatus_NAME").val('');
	$("#status_NAME").val('');
	$("#constructStatus_NAME").val('');
	$("#custType_NAME").val('');
	$("#checkStatus").val('');
	$("#status").val('');
	$("#constructStatus").val('');
	$("#dateFrom1").val('');
	$("#arriveDateTo").val('');
	$("#arriveDateFrom").val('');
	$("#dateTo1").val('');
	$("#no").val('');
	$("#custType").val('');
	$("#itCode").val('');
	$("#sino").val('');
	$("#supplier").val('');
	$("#remarks").val('');
	$("#address").val('');
	$("#region").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_constructStatus").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_region").checkAllNodes(false);
} 
function view(){
		var ret= selectDataTableRow('dataTable');
		Global.Dialog.showDialog("Save",{
			title:"工程信息——查看",
			url:"${ctx}/admin/gcxxgl/goView",
			postData:{code:ret.code,custCountCost:ret.custcountcost,planPrjDescr:ret.planspeeddescr,nowPrjDescr:ret.nowspeeddescr,businessFlowDescr:ret.businessflowdescr,
					delayDay:ret.delayday,prjManCost:ret.prjmancost,consManCost:ret.consmancost,
					zjxhj:ret.zjxhj,jczj:ret.jczj,department2:ret.department2descr,
					baseCost:ret.basecost,mainCost:ret.maincost,softCost:ret.softcost,inteCost:ret.intecost,preloftsMan:ret.preloftsman,
					havePay:ret.havepay
					},
			height:800,
			width:1050,
			returnFun:goto_query
		});
}
function doExcel(){
	var url = "${ctx}/admin/gcxxgl/doExcel";
	doExcelAll(url);
}
/* function query(){
	ids=0;
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
}
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
} */
function gcjs(){
		var ret = selectDataTableRow('dataTable');
		if (ret) {
			if ($.trim(ret.status)!="5" || $.trim(ret.checkstatus)!="1"){
	    		art.dialog({
					content: "处于归档状态且未结算客户，才能进行结算操作!"
				});
	    		return;
	    	}
			$.ajax({
				url:"${ctx}/admin/gcxxgl/beforegcjs",
				type: "post",
				data: {custCode: ret.code},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if(obj.rs){
						Global.Dialog.showDialog("Check",{
							title:"工程信息——工程结算",
							url:"${ctx}/admin/gcxxgl/goCheck",
							postData:{custCode:ret.code},
							height:350,
							width:400,
							returnFun:goto_query
						});
			    	}else{
						art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
		
	}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }"/>
							</li>
							<li>
							   <label>一级区域</label>
							   <house:xtdmMulit id="region" dictCode="" sql="select code SQL_VALUE_KEY,descr SQL_LABEL_KEY  from tRegion a where a.expired='F' " ></house:xtdmMulit>
							</li>	
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${customer.projectMan }"/>
							</li>
							<li>
								<label>优惠说明</label>
								<input type="text" id="discRemark" name="discRemark" style="width:160px;" value="${customer.discRemark }"/>
							</li>
							<li>
								<label>实际开工时间从</label>
								<input type="text" id="beginDateFrom" name="beginDateFrom" class="i-date"  style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>到</label>
								<input type="text" id="beginDateTo" name="beginDateTo" class="i-date"  style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>客户电话</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${customer.mobile1 }"/>
							</li>
							<li>
								<label>客户类型</label>
								<house:custTypeMulit id="custType" selectedValue="${customer.custType }"></house:custTypeMulit>
							</li>
							<li id="loadMore" >
								<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
								<button type="button" class="btn btn-sm btn-system " onclick="query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
							<div  class="collapse " id="collapse" >
							<ul>
							<li>
								<label>巡检员</label>
								<input type="text" id="checkMan" name="checkMan" style="width:160px;" value="${customer.checkMan }"/>
							</li>
							<li>
								<label>实际结算时间从</label>
								<input type="text" id="checkOutDateFrom" name="checkOutDateFrom" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								 value="<fmt:formatDate value='${customer.checkOutDateFrom}' pattern='yyyy-MM-dd '/>" />		
							</li>
							<li>
								<label>到</label>
								<input type="text" id="checkOutDateTo" name="checkOutDateTo" class="i-date"  style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li >
								<label>工程部2</label>
								<house:dict id="department2" dictCode="" 
									sql="select a.Code,a.code+' '+a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
										left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and 
														e.expired='F'
										 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
									sqlValueKey="Code" sqlLableKey="Desc1"  >
								</house:dict>
							</li>
							<li>
								<label>客户状态</label>
								<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM 
								where ID='CUSTOMERSTATUS' and (CBM='4' or CBM = '5')  " selectedValue="4"></house:xtdmMulit>
							</li>
							<li>
								<label>项目名称</label>
								<input type="text" id="builderCode" name="builderCode" style="width:160px;" 
									value="${customer.builderCode }"/>
							</li>
							<li>
								<label>客户结算时间从</label>
								<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date"  style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>到</label>
								<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date"  style="width:160px;" 
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>施工状态</label>
								<house:xtdmMulit id="constructStatus" dictCode="CONSTRUCTSTATUS" 
												selectedValue="0,1,2,3"></house:xtdmMulit>
							</li>
							<li>
								<label>客户结算状态</label>
								<house:xtdmMulit id="checkStatus" dictCode="CheckStatus" selectedValue="${customer.checkStatus}"></house:xtdmMulit>
							</li>
							<li>
                                <label>现场设计师</label>
                                <input type="text" id="custSceneDesi" name="custSceneDesi"/>
                            </li>
                            <li>
                                <label>派单时间从</label>
                                <input type="text" id="sendJobDateFrom" name="sendJobDateFrom" class="i-date"
                                       onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                            </li>
                            <li>
                                <label>到</label>
                                <input type="text" id="sendJobDateTo" name="sendJobDateTo" class="i-date"
                                       onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                            </li>
							<li class="search-group-shrink" >
								<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse" >收起</button>
								<button type="button" class="btn  btn-sm btn-system " onclick="query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
							</ul>
						</div>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
				<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="gcxxgl_save">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="save"><span>工程信息录入</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_insertPrjMan">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="insertPrjMan"><span>录入项目经理</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_updatePrjMan">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="updatePrjMan"><span>修改项目经理</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_updatePhone">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="updatePhone"><span>修改客户电话</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_updateDesign">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="updateDesign"><span>修改设计师</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_updateMainManager">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="updateMainManager"><span>修改主材管家</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_updateinteDesign">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="updateInteDesign"><span>修改集成设计师</span>
							</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_updateRealAddress">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="updateRealAddress"><span>修改实际地址</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_view">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="view" onclick="view()"><span>查看</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_batchUpdate">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="batchUpdate"><span>批量修改</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="gcxxgl_gdtjfx">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="prjCheck"><span>工地统计分析</span>
								</button>
						</house:authorize>
						<house:authorize authCode="gcxxgl_custCheckData">
								<button type="button" class="btn btn-system" style="margin-top:5px" id="custCheckData"><span>工地巡检数据</span>
								</button>
						</house:authorize>
						<house:authorize authCode="gcxxgl_excel">
							<button type="button" class="btn btn-system" style="margin-top:5px" onclick="doExcel()" title="导出检索条件数据">
								<span>导出excel</span>
							</button>	
						</house:authorize>
						<button type="button" class="btn btn-system" style="margin-top:5px" onclick="drag()">设置列</button>
               			<button type="button" class="btn btn-system" style="margin-top:5px" onclick="dragSave()">保存列</button>
               			<house:authorize authCode="gcxxgl_check">
							<button type="button" class="btn btn-system" style="margin-top:5px" onclick="gcjs()"><span>工程结算</span></button>
						</house:authorize>
					</div>	
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
