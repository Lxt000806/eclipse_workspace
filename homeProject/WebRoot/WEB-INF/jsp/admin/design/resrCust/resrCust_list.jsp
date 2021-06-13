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
	<title>资源客户管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/xtdm/sourcesOrChannels", "source", "netChanel");
	$("#businessMan").openComponent_employee();	
	$("#crtCzy").openComponent_employee();	
	$("#builderCode").openComponent_builder();	
	$("#businessManDept").openComponent_department();
	$("#shareCzy").openComponent_employee();	
	var postData =$("#page_form").jsonForm();
	var type = "${resrCust.type}";
	postData.custResStat = "0,1,3,5";
	var oldColModel=[
 			{name:'address',	index:'address',	width:180,	label:'楼盘地址',	sortable:true,align:"left",frozen:true},
			{name:'descr',	index:'descr',	width:90,	label:'客户姓名',	sortable:true,align:"left",frozen:true,formatter:descrBtn},
			{name:'mobile1',	index:'mobile1',	width:110,	label:'手机号',	sortable:true,align:"left"},
			{name:'custresstatdescr',	index:'custresstatdescr',	width:95,	label:'资源客户状态',	sortable:true,align:"left"},
			{name:'recentlycondate', index:'recentlycondate', width:120, label:'最近联系时间', sortable:true, align:"left", formatter: formatDate},
			{name:'recentlycondatedescr', index:'recentlycondatedescr', width:120, label:'recentlycondatedescr', sortable:true,align:"left", hidden: true},
			{name:'taginfo',	index:'taginfo',	width:120,	label:'客户标签',	sortable:true,align:"left",formatter:fmtTags},
			{name:'remarks',    index:'remarks',    width:300,  label:'跟踪内容',   sortable:true,align:"left"},
			{name:'remark',	index:'remark',	width:150,	label:'备注',	sortable:true,align:"left"},
			{name:'businessmandescr',	index:'businessmandescr',	width:90,	label:'跟单人',	sortable:true,align:"left",},
			{name:'designmandescr',	index:'designmandescr',	width:95,	label:'设计师',	sortable:true,align:"left"},
			{name:'nextcondate',	index:'nextcondate',	width:120,	label:'下次联系时间',	sortable:true,align:"left",formatter: formatTime},
			{name:'custkinddescr',	index:'custkinddescr',	width:70,	label:'客户分类',	sortable:true,align:"left"},
			{name:'constructtypedescr',	index:'constructtypedescr',	width:70,	label:'客户类别',	sortable:true,align:"left"},
			{name:'netchaneldescr',	index:'netchaneldescr',	width:70,	label:'渠道',	sortable:true,align:"left"},
			{name:'crtdate',	index:'crtdate',	width:120,	label:'创建日期',	sortable:true,align:"left",formatter: formatTime},
			{name:'dispatchdate',	index:'dispatchdate',	width:120,	label:'派单日期',	sortable:true,align:"left",formatter: formatTime},
			{name:'custstatusdescr',	index:'custstatusdescr',	width:95,	label:'意向客户状态',	sortable:true,align:"left"},
			{name:'measuredate',	index:'measuredate',	width:90,	label:'量房时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'visitdate',	    index:'visitdate',	width:90,	label:'到店时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'setdate',	index:'setdate',	width:90,	label:'下定时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'signdate',	index:'signdate',	width:90,	label:'签订时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'shareczydescr',	index:'shareczydescr',	width:90,	label:'共享人',	sortable:true,align:"left",},
			{name:'custaddress',	index:'custaddress',	width:180,	label:'意向楼盘',	sortable:true,align:"left"},
			{name:'crtczydescr',	index:'crtczydescr',	width:70,	label:'创建人',	sortable:true,align:"left"},
			{name:'crtczydept',	index:'crtczydept',	width:80,	label:'创建人部门',	sortable:true,align:"left"},
			{name:'regiondescr',	index:'regiondescr',	width:60,	label:'区域',	sortable:true,align:"left"},
			{name:'housetypedescr',	index:'housetypedescr',	width:80,	label:'楼盘性质',	sortable:true,align:"left"},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:95,	label:'最后操作人员',	sortable:true,align:"left",hidden:type =="1"?true:false},
			{name:'lastupdate',	index:'lastupdate',	width:150,	label:'最后修改时间',	sortable:true,align:"left",formatter: formatTime},
			{name:'expired',	index:'expired',	width:60,	label:'过期',	sortable:true,align:"left",},
			{name:'actionlog',	index:'actionlog',	width:80,	label:'操作日志',	sortable:true,align:"left",hidden:type =="1"?true:false},
			{name:'custresstat',	index:'custresstat',	width:90,	label:'custresstat',	sortable:true,align:"left",hidden:true},
			{name:'businessman',	index:'businessman',	width:90,	label:'businessman',	sortable:true,align:"left",hidden:true},
			{name:'righttype',	index:'righttype',	width:90,	label:'righttype',	sortable:true,align:"left" ,hidden:true},
			{name:'buildercode',	index:'buildercode',	width:90,	label:'buildercode',	sortable:true,align:"left" ,hidden:true },
			{name:'code',	index:'code',	width:90,	label:'code',	sortable:true,align:"left" , hidden:true},
			{name:'pooltype',	index:'pooltype',	width:90,	label:'线索池类型',	sortable:true,align:"left" ,hidden: true },
			{name:'dispatchrule',	index:'dispatchrule',	width:90,	label:'派单规则',	sortable:true,align:"left" , hidden: true},
		];
	 var colModel = '${colModel}'?eval('('+'${colModel}'+')'):oldColModel;;
 	 
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
 	var gridOption={
		url:"${ctx}/admin/ResrCust/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap',
		multiselect:true, 
		colModel : colModel,
		gridComplete:function(){
           	$(".ui-jqgrid-bdiv").scrollTop(0);
        	frozenHeightReset("dataTable");
        },
        beforeSelectRow: function (rowid, e) {
        	//选中列的index
	        var i = $.jgrid.getCellIndex($(e.target).closest('td')[0]);
	        //所有列
	        var cm = $(this).jqGrid('getGridParam', 'colModel');
	        //表格所有id
	        var ids = $("#dataTable").getDataIDs();
	        //当前行是否被勾选
	        var selChecked=$("tr#"+rowid+".jqgrow.ui-row-ltr td[aria-describedby=dataTable_rn]").parent().is(".success");
	       	//去掉其他未勾选的行
	    	for(var j=0;j<ids.length;j++){
		    	var isChecked=$("#jqg_dataTable_"+ids[j]+"").is(":checked");//是否被勾选
		    	if(!isChecked){
					 $(" tr#"+ids[j]+".jqgrow.ui-row-ltr.selectone ").removeClass("selectone");
		    	}
		    } 
	        //当前行没有被勾选
	        if(!selChecked){
	        	var style="success";
	        	if(cm[i].name != 'cb'){
	        		style="selectone";
	        	}
    			$("tr#"+rowid+".jqgrow.ui-row-ltr ").addClass(style);
         	}
			return (cm[i].name === 'cb');//列名为勾选框才返回true
		},
	}; 
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	$("#dataTable").jqGrid("setFrozenColumns");
	window.drag = function() {
		$.jgrid.gridUnload("dataTable");
		Global.JqGrid.initJqGrid("dataTable", $.extend(gridOption, {sortable: true}));
	};
	window.dragSave = function() {
		var colModel = $("#dataTable").jqGrid('getGridParam','colModel');
		var datas = {
			"jsonString": JSON.stringify(colModel)
		};
		$.ajax({
			url:'${ctx}/admin/ResrCust/doSaveCols',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    			window.location.reload();
		    	}
		    }
		 });
	};
	
	window.dragReset= function() {
		art.dialog({
			content:"是否重新设置列?",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/ResrCust/doResetCols",
					type: "post",
					//data: datas,
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		window.location.reload();
				    	}
				    }
				 });
			},
			cancel: function () {
					return true;
			}
		});	
	};
		 
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"资源客户——新增",
			url:"${ctx}/admin/ResrCust/goSave",
			height:580,
			width:1250,
			returnFun:goto_query
		});
	});
	
	$("#addNetCust").on("click",function(){
		Global.Dialog.showDialog("addNetCust",{
			title:"资源客户——新增网络客户",
			url:"${ctx}/admin/ResrCust/goAddNetCust",
			height:580,
			width:1250,
			returnFun:goto_query
		});
	});
	
	$("#dispatch").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
			if(ret.custresstat=="3"){
				art.dialog({
					content:"存在【转意向】状态的记录，不能进行派单",
				});
				return false;
			}
		}
		if(ids.length>0){
			Global.Dialog.showDialog("dispatch",{
				title:"资源客户——派单",
				url:"${ctx}/admin/ResrCust/goDispatch",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:600,
				width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#changeBusiness").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
		}
		if(ids.length>0){
			Global.Dialog.showDialog("changeBusiness",{
				title:"资源客户——转让客户",
				url:"${ctx}/admin/ResrCust/goCustChangeBusiness",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:600,
				width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#custTeamChangeBusiness").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
		}
		if(ids.length>0){
			Global.Dialog.showDialog("custTeamChangeBusiness",{
				title:"资源客户——转让客户",
				url:"${ctx}/admin/ResrCust/goCustTeamChangeBusiness",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:600,
				width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#giveUp").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
		}
		if(ids.length>0){
			Global.Dialog.showDialog("giveUp",{
				title:"资源客户——放弃客户",
				url:"${ctx}/admin/ResrCust/goCustGiveUp",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:600,
				width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#custTeamGiveUp").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
		}
		if(ids.length>0){
			Global.Dialog.showDialog("giveUp",{
				title:"资源客户——放弃客户",
				url:"${ctx}/admin/ResrCust/goCustTeamGiveUp",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:600,
				width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#cancel").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
	 	var errData = false;
		
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
				var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
				codes+=ret.code+",";
				if(ret.dispatchrule != "0"){
					errData = true;
				}
			}
			if(i == ids.length){
				if(errData == true){
					art.dialog({
						content:"存在线索池配置为非'手动派单'，不允许操作",
					});
					return;
				}
				Global.Dialog.showDialog("giveUp",{
					title:"资源客户——注销客户",
					url:"${ctx}/admin/ResrCust/goCustCancel",
					postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
					height:600,
					width:800,
					returnFun:goto_query
				});
			}
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#custTeamCancel").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
	 	var errData = false;
	 	
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
			codes+=ret.code+",";
			if(ret.dispatchrule != "0"){
				errData = true;
			}
		}
		
		if(ids.length>0){
			if(errData == true){
				art.dialog({
					content:"存在线索池配置为非'手动派单'，不允许操作",
				});
				return;
			}
			Global.Dialog.showDialog("giveUp",{
				title:"资源客户——注销客户",
				url:"${ctx}/admin/ResrCust/goCustTeamCancel",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:600,
				width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#shareCust").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
		}
		if(ids.length>0){
			Global.Dialog.showDialog("shareCust",{
				title:"资源客户——共享客户",
				url:"${ctx}/admin/ResrCust/goShareCust",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:700,
				width:1000,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#setCustTag").on("click",function(){
		var czybh = $.trim("${resrCust.czybh }");
		var custRight = $.trim("${resrCust.custRight }");
		var type = $.trim("${resrCust.type }");
	 	var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	var codes="";
		for(var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			codes+=ret.code+",";
		}
		if(ids.length>0){
			Global.Dialog.showDialog("giveUp",{
				title:"资源客户——设置客户标签",
				url:"${ctx}/admin/ResrCust/goCustTag",
				postData:{codes:codes,czybh:czybh,custRight:custRight,type:type},
				height:600,
				width:800,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条或多条记录",
			});
		}
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		
		if(ret){
			if(ret.dispatchrule != "0" && ret.dispatchrule != "" && ret.custresstat == "4"){
				art.dialog({
					content:"线索池配置非'手动派单'，注销状态不允许编辑"
				});
				return;
			}
			Global.Dialog.showDialog("Update",{
				title:"资源客户——编辑",
				url:"${ctx}/admin/ResrCust/goUpdate",
				postData:{code:ret.code,builderCode:ret.buildercode,type:"${resrCust.type }"},
				height:650,
				width:1250,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"资源客户——查看",
				url:"${ctx}/admin/ResrCust/goView",
				postData:{code:ret.code,builderCode:ret.buildercode,type:"${resrCust.type }"},
				height:window.screen.height-130,
		 		width:1300,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#teamView").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("TeamView",{
				title:"团队客户——查看",
				url:"${ctx}/admin/ResrCust/goCustTeamView",
				postData:{code:ret.code,builderCode:ret.buildercode,type:"${resrCust.type }"},
				height:window.screen.height-130,
		 		width:1300,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	
	$("#del").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("mobileView",{
				title:"资源客户——查看联系方式",
				url:"${ctx}/admin/ResrCust/goDelete",//虽然是doDelete，但做的是查看资源客户联系方式
				postData:{code:ret.code,builderCode:ret.buildercode},
				height:230,
				width:500,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#viewLog").on("click",function(){
		Global.Dialog.showDialog("mobileView",{
			title:"资源客户——查看日志",
			url:"${ctx}/admin/ResrCust/goViewLog",
			height:750,
			width:1100,
			returnFun:goto_query
		});
	});
	
	$("#con").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("con",{
				title:"资源客户——编辑接触记录",
				url:"${ctx}/admin/ResrCust/goCon",
				postData:{code:ret.code},
				height:600,
				width:1150,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#addCon").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("con",{
				title:"资源客户——添加接触记录",
				url:"${ctx}/admin/ResrCust/goAddCon",
				postData:{code:ret.code},
				height:700,
				width:1050,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#updateCrtCzy").on("click",function(){
		Global.Dialog.showDialog("con",{
			title:"团队客户管理——修改创建人",
			url:"${ctx}/admin/ResrCust/goUpdateCrtCzy",
			height:800,
			width:1100,
			returnFun:goto_query
		}); 
	}); 
	
	$("#addFromExcel").on("click",function(){
		Global.Dialog.showDialog("addFromExcel",{
			title:"资源客户——从Excel导入",
			url:"${ctx}/admin/ResrCust/goAddFromExcel",
			height:470,
			width:800,
			returnFun:goto_query
		});
	});
	
	$("#transIntention").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("transIntention",{
				title:"资源客户——转意向",
				url:"${ctx}/admin/customer/goSave",
				postData:{resrCustCode:ret.code},
	 			height: 600,
			    width:1200,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#addCustCode").on("click",function(){
		var ret = selectDataTableRow();
		var czybh = $.trim("${resrCust.czybh }");
		if(ret){
			Global.Dialog.showDialog("Update",{
				title:"资源客户——关联意向客户",
				url:"${ctx}/admin/ResrCust/addCustCode",
				postData:{code:ret.code,builderCode:ret.buildercode,type:"${resrCust.type }"},
				height:520,
				width:700,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	onCollapse(44); 
});

function doExcel(){
	var url = "${ctx}/admin/ResrCust/doExcel";
	doExcelAll(url);
}

function clearForm(){
 	$("#page_form input[type='text']").val('');
	$("#page_form select[id!=resrType]").val('');
	$("#openComponent_builder_builderCode").val('');
	$("#openComponent_employee_businessMan").val('');
	$("#custTag").val("");
	$("#resrCustPoolNo").val("");
	$.fn.zTree.getZTreeObj("tree_custTag").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_resrCustPoolNo").checkAllNodes(false);
} 

function descrBtn(v,x,r){
	return "<a href='#' onclick='view("+x.rowId+");'>"+v+"</a>";
}   	

function fmtTags(v,x,r){
	var tagsArr=[];
	var htmlTag="";
	if(v!=""){
		tagsArr=v.split(",");
		for(var i=0;i<tagsArr.length;i++){
			var tag=tagsArr[i].split("/");
			htmlTag+="<span style='border-radius:5px;padding:3px;background:"+tag[1]+"'><span style='color:white'>"+tag[0]+"</span></span>&nbsp;";
		}
	}
	return htmlTag;
}   

function view(id){
	var ret = $("#dataTable").jqGrid('getRowData', id);
	if(!ret){
		art.dialog({
			content:"请选择一条数据",
		});
		return;
	}
	if("${resrCust.type }"=="1"){
		Global.Dialog.showDialog("View",{
			title:"资源客户——查看",
			url:"${ctx}/admin/ResrCust/goView",
			postData:{code:ret.code,builderCode:ret.buildercode,type:"${resrCust.type }"},
			height:window.screen.height-130,
	 		width:1300,
			returnFun:goto_query
		});
	}else{
		Global.Dialog.showDialog("TeamView",{
			title:"团队客户——查看",
			url:"${ctx}/admin/ResrCust/goCustTeamView",
			postData:{code:ret.code,builderCode:ret.buildercode,type:"${resrCust.type }"},
			height:window.screen.height-130,
	 		width:1300,
			returnFun:goto_query
		});
	}
}
function clearContactDate(tag) {
	if (tag) {
		$("#notContLastUpdateType").val("");
	} else {
		$("#recentlyConcat").val("");
	}
}
function changeResrType(){
	var resrType=$("#resrType").val();
	if(resrType=="0"){
		$("#dispatch,#cancel,#changeBusiness,#giveUp,#addFromExcel,#save,#addNetCust").removeClass("hidden");
	}else{
		$("#dispatch,#cancel,#changeBusiness,#giveUp,#addFromExcel,#save,#addNetCust").addClass("hidden");
	}
	goto_query();
}
</script>
</head>
	<body>
		<div class="body-box-list " style="overflow-x:hidden">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="czybh" value="${resrCust.czybh}" />
					<input type="hidden" id="expired" name="expired" value="${resrCust.expired }" />
					<input type="hidden" name="custRight" value="${resrCust.custRight}" /> 
					<input type="hidden" name="type" value="${resrCust.type}" /> 
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"/>
							</li>
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" />
							</li>
							<li>
								<label>手机号</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;" />
							</li>
							<c:if test="${resrCust.type =='2'}">
								<li>
									<label>创建人</label>
									<input type="text" id="crtCzy" name="crtCzy" style="width:160px;"/>
								</li>
							</c:if>
							<li>
								<label>跟踪人</label>
								<input type="text" id="businessMan" name="businessMan" style="width:160px;"/>
							</li>
							<c:if test="${resrCust.type=='2'}">
								<li>
									<label>跟踪人部门</label>
									<input type="text" id="businessManDept" name="businessManDept" style="width:160px;"/>
								</li>
							</c:if>
							<li>
								<label>资源客户状态</label>
								<house:xtdmMulit id="custResStat" dictCode="CUSTRESSTAT" selectedValue="0,1,3,5"></house:xtdmMulit>                     
							</li>
							<li>
								<label>派单日期从</label>
								<input type="text" id="dispatchDateFrom" name="dispatchDateFrom" class="i-date" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dispatchDateTo" name="dispatchDateTo" class="i-date" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label>客户类别</label>
								<house:xtdm id="constructType" dictCode="CUSTCLASS" ></house:xtdm>                     
							</li>
							<li>
								<label>客户来源</label>
								<select id="source" name="source"></select>
							</li>
							<li>
	                            <label>渠道</label>
	                            <select id="netChanel" name="netChanel"></select>
	                        </li>
							<li>
								<label>客户分类</label>
								<house:xtdmMulit id="custKind" dictCode="CUSTKIND"></house:xtdmMulit>                     
							</li>
                            <li>
                            	<label>最近联系时间</label> 
                            	<house:xtdm id="recentlyConcat" dictCode="RECENTLYCONCAT" unShowValue="1" onchange="clearContactDate(true)"></house:xtdm>
                            </li>
                            <li>
                            	<label>最近未联系</label> 
                            	<house:xtdm id="notContLastUpdateType" dictCode="RECENTNOCONCAT" onchange="clearContactDate(false)"></house:xtdm>
                            </li>
                            <li>
								<label>已设置标签</label>
								<house:xtdm id="haveCustTag" dictCode="YESNO" ></house:xtdm>
							</li>
							<li>
								<label>客户标签</label>
								<house:MulitSelectCustTag id="custTag" ></house:MulitSelectCustTag>
							</li>
							<c:if test="${resrCust.type=='1'}">
	                            <li>
	                                <label>外部订单编号</label>
	                                <input type="text" id="extraOrderNo" name="extraOrderNo"/>
	                            </li>
                            </c:if>
							<li id="loadMore" >
								<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
								<input type="checkbox" id="expired_show" name="expired_show" value="${resrCust.expired }" onclick="hideExpired(this)" ${resrCust.expired!='T'?'checked':'' }/>隐藏过期&nbsp;
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
							</li>
							<div  class="collapse" id="collapse" >
								<ul>
									<c:if test="${resrCust.type=='2'}">
			                            <li>
			                                <label>外部订单编号</label>
			                                <input type="text" id="extraOrderNo" name="extraOrderNo"/>
			                            </li>
		                            </c:if>
									<li>
										<label>项目名称</label>
										<input type="text" id="builderCode" name="builderCode" style="width:160px;"/>
									</li>
									<li>
										<label>装修状态</label>
										<house:xtdm id="status" dictCode="RESRCUSTSTS" ></house:xtdm>                     
									</li>
									<li>
										<label>下次联系时间从</label>
										<input type="text" id="nextConDateFrom" name="nextConDateFrom" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>至</label>
										<input type="text" id="nextConDateTo" name="nextConDateTo" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>创建日期从</label>
										<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
									</li>
									<li>
										<label>至</label>
										<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
									</li>
									<li>
										<label>量房时间从</label>
										<input type="text" id="measureDateFrom" name="measureDateFrom" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>至</label>
										<input type="text" id="measureDateTo" name="measureDateTo" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>到店时间从</label>
										<input type="text" id="visitDateFrom" name="visitDateFrom" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>至</label>
										<input type="text" id="visitDateTo" name="visitDateTo" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>下定时间从</label>
										<input type="text" id="setDateFrom" name="setDateFrom" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>至</label>
										<input type="text" id="setDateTo" name="setDateTo" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>签订时间从</label>
										<input type="text" id="signDateFrom" name="signDateFrom" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<li>
										<label>至</label>
										<input type="text" id="signDateTo" name="signDateTo" class="i-date" 
												onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
									</li>
									<c:if test="${resrCust.type=='1'}">
										<li>
			                            	<label>资源类型</label> 
				                            <select id="resrType" name="resrType" onchange="changeResrType()">
				                            	<option value="0">我的客户</option>
				                            	<option value="1">他人共享客户</option>
				                            </select>
			                            </li>
									</c:if>
									<c:if test="${resrCust.type=='2'}">
			                            <li>
											<label>共享人</label>
											<input type="text" id="shareCzy" name="shareCzy" style="width:160px;"/>
										</li>
		                            </c:if>
		                            <li>
										<label>线索池</label>
										<house:DictMulitSelect id="resrCustPoolNo" dictCode="" 
										sql="select a.No Code,a.Descr from tResrCustPool a
									   	 where (exists (select 1 from tResrcustPoolEmp in_a where in_a.ResrCustPoolNo = a.No and in_a.CZYBH = '${resrCust.czybh }')
									   	 or exists (select 1 from tCZYBM in_b where in_b.DefaultPoolNo = a.No and in_b.CZYBH = '${resrCust.czybh }')
									     or a.Descr = '默认线索池') and expired = 'F' or (a.ReceiveRule='0' and expired = 'F') "
										sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
									</li>
									<li class="search-group-shrink" >
										<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
										<input type="checkbox" id="expired_show" name="expired_show"
												value="${resrCust.expired}" onclick="hideExpired(this)"
												${resrCust.expired!='T'?'checked':'' }/><p>隐藏过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
										<button type="button" class="btn  btn-sm btn-system" onclick="goto_query()">查询</button>
										<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
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
    			  <div class="btn-group-sm">
    			  		<c:if test="${resrCust.type =='1'}">
    			  			<house:authorize authCode="RESRCUST_SAVE">
								<button type="button" class="btn btn-system " id="save" ><span>新增</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_ADDNETCUST">
								<button type="button" class="btn btn-system " id="addNetCust"><span>新增网络客户</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_ADDFROMEXCEL">
								<button type="button" class="btn btn-system " id="addFromExcel"><span>从Excel导入</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_UPDATE">
								<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_CHANGEBUSNIESS">
								<button type="button" class="btn btn-system "  id="changeBusiness"><span>转让客户</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_GIVEUP">
								<button type="button" class="btn btn-system "  id="giveUp"><span>放弃客户</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_CUSTCANCEL">
								<button type="button" class="btn btn-system "  id="cancel"><span>注销客户</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_DISPATCH">
								<button type="button" class="btn btn-system "  id="dispatch"><span>派单</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_TRANSINTENTION">
								<button type="button" class="btn btn-system "  id="transIntention"><span>转意向</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_SETCUSTTAG">
								<button type="button" class="btn btn-system "  id="setCustTag"><span>设置客户标签</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_CON">
								<button type="button" class="btn btn-system "  id="con"><span>编辑接触记录</span> 
							</house:authorize>
							<button type="button" class="btn btn-system "  id="addCon" ><span>添加接触记录</span>
							<house:authorize authCode="RESRCUST_VIEW">
								<button type="button" class="btn btn-system "  id="view"><span>查看</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_DELETE">
								<button type="button" class="btn btn-system "  id="del"><span>查看联系方式</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_VIEWLOG">
								<button type="button" class="btn btn-system "  id="viewLog"><span>查看日志</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_EXPORT">
								<button type="button" class="btn btn-system "  onclick="doExcel()" ><span>导出excel</span>
							</house:authorize>
    			  		</c:if>
    			  		<c:if test="${resrCust.type =='2'}">
    			  			<house:authorize authCode="TEAMCUST_UPDATE">
								<button type="button" class="btn btn-system "  id="update"><span>编辑</span> 
							</house:authorize>
    			  			<house:authorize authCode="TEAMCUST_CHANGEBUSINESS">
								<button type="button" class="btn btn-system "  id="custTeamChangeBusiness"><span>转让客户</span> 
							</house:authorize>
							<house:authorize authCode="TEAMCUST_GIVEUP">
								<button type="button" class="btn btn-system "  id="custTeamGiveUp"><span>放弃客户</span> 
							</house:authorize>
							<house:authorize authCode="TEAMCUST_CUSTCANCEL">
								<button type="button" class="btn btn-system "  id="custTeamCancel"><span>注销客户</span> 
							</house:authorize>
							<house:authorize authCode="TEAMCUST_SETCUSTTAG">
								<button type="button" class="btn btn-system "  id="setCustTag"><span>设置客户标签</span> 
							</house:authorize>
							<house:authorize authCode="TEAMCUST_ADDCUSTCON">
								<button type="button" class="btn btn-system " id="addCon"><span>添加接触记录</span>
							</house:authorize>
							<house:authorize authCode="TEAMCUST_UPDATECRTCZY">
								<button type="button" class="btn btn-system "  id="updateCrtCzy"><span>修改创建人</span> 
							</house:authorize>
							<house:authorize authCode="TEAMCUST_ADDCUSTCODE">
								<button type="button" class="btn btn-system "  id="addCustCode"><span>关联意向客户</span> 
							</house:authorize>
							<house:authorize authCode="TEAMCUST_SHARECUST">
								<button type="button" class="btn btn-system "  id="shareCust"><span>共享客户</span> 
							</house:authorize>
							<house:authorize authCode="TEAMCUST_VIEW">
								<button type="button" class="btn btn-system "  id="teamView"><span>查看</span> 
							</house:authorize>
							<house:authorize authCode="RESRCUST_EXPORT">
								<button type="button" class="btn btn-system "  onclick="doExcel()" ><span>导出excel</span>
							</house:authorize>
    			  		</c:if>
						<button type="button" class="btn btn-system" onclick="drag()">设置列</button>
		                <button type="button" class="btn btn-system" onclick="dragSave()">保存列</button>
		                <button type="button" class="btn btn-system" onclick="dragReset()">恢复列</button>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
