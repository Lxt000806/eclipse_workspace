<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>分摊查询页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	

<script type="text/javascript"> 
var preName = "";
var tableId = "";
var pageFormId = "page_form";
var jqgridGropMap = [];
var jqGridFrozenColModel = [];
function setJqGridGroupMap(groupMap){
	jqgridGropMap = groupMap;
}

function getJqGridGroupMap(){

	return jqgridGropMap;
}

function setJqGridFrozenColModel(frozenColModel){

	jqGridFrozenColModel = frozenColModel;
}

function getJqGridFrozenColModel(){

	return jqGridFrozenColModel;
}

$(function(){
	var subreportData = ${subreportData};
	
	if(subreportData.length == 0){
		art.dialog({
			content:"该方案当月未生成分账明细",
			time:2000,
			beforeunload:function(){
				closeWin();
			}
		});	
		return;
	}
	
	var gridOption = {
		height:480,
		rowNum:50,
		colModel : [
			{name:"empname", index:"empname", width:80, label:"员工姓名", sortable:true ,align:"left",},
			{name:"salaryemp", index:"salaryemp", width:80, label:"员工编号", sortable:true ,align:"left",},
			{name:"salarymon", index:"salarymon", width:80, label:"月份", sortable:true ,align:"left",},
			{name:"schemedescr", index:"salarymon", width:140, label:"薪酬方案", sortable:true ,align:"left",},
			{name:"amountpaid", index:"amountpaid", width:80, label:"已发金额", sortable:true ,align:"left",},
			{name:"actualamount", index:"actualamount", width:80, label:"实发金额", sortable:true ,align:"left",},
			{name:"actname", index:"actname", width:80, label:"户名", sortable:true ,align:"left",},
			{name:"cardid", index:"cardid", width:130, label:"卡号", sortable:true ,align:"left",},
			{name:"remarks", index:"remarks", width:180, label:"备注", sortable:true ,align:"left",},
		],
	};
			
	var gridOptionMap={};		
	if(subreportData.length > 0){
		var firstPagerId = "";
		for(var i = 0; i < subreportData.length; i++){
			var salaryScheme = $.trim("${salaryData.salaryScheme}"); 
			var salaryMon = $.trim("${salaryData.salaryMon}"); 
			var paymentDef = subreportData[i].PaymentDef;
			var descr = subreportData[i].Descr;
			var tabId = "tab_payment_"+paymentDef;
			var dataTableId = "dataTable"+paymentDef;
			var dataTablePageId = "dataTable"+paymentDef+"Pager";
			if(i == 0){
				$("#paymentDef").val(paymentDef);
				$("#tableId").val(dataTableId);
				firstPagerId = dataTablePageId;
				$("#tab_ul").append("<li class=\"active\" onclick=\"changeExcelInfo('"+dataTableId+"','"+paymentDef+"')\"><a href=\"#"+tabId+"\" data-toggle=\"tab\">"+descr+"</a></li>");
				$("#tab_content").append("<div id=\""+tabId+"\"  class=\"tab-pane fade in active\"><div id=\"content-list\"><table id=\""+dataTableId+"\"></table><table id=\""+dataTablePageId+"\" hidden=\"true\"></table></div></div>");
				tableId = dataTableId;
				preName = descr;
				getMainPageData(dataTableId, dataTablePageId, paymentDef);
			} else {
				$("#tab_ul").append("<li class=\"\" onclick=\"setPagerWidth('"+dataTableId+"','"+dataTablePageId+"','"+firstPagerId+"','"+paymentDef+"','"+descr+"')\"><a href=\"#"+tabId+"\" data-toggle=\"tab\">"+descr+"</a></li>");
				$("#tab_content").append("<div id=\""+tabId+"\"  class=\"tab-pane fade\"><div id=\"content-list\"><table id=\""+dataTableId+"\"></table><table id=\""+dataTablePageId+"\" hidden=\"true\"></table></div></div>");
			};
		};
	};
}); 

	function setPagerWidth(dataTableId,dataTablePageId, firstPagerId,paymentDef, descr){
		changeExcelInfo(dataTableId, paymentDef);
		if($("#"+"gbox_"+dataTableId).length>0){
			return;
		}
		
		getMainPageData(dataTableId, dataTablePageId, paymentDef, firstPagerId);
	}	

	function clickOpt(cellvalue, options, rowObject){
      	if(rowObject==null){
        	return '';
		}
		if(cellvalue == null){
			return 0;
		}
      	return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"view('"
      			+options.colModel.name+"','"+options.rowId+"','"+this.id+"')\">"+cellvalue+"</a>";
	}
   	
   	function view(name, rowId, dataTableId){
   		var rowData = $("#"+dataTableId).jqGrid("getRowData",rowId);
   		var describe = rowData[name+"_tip"];
   		
   		Global.Dialog.showDialog("salaryChgList",{
			title:"查看算法",
			postData:{calcDescr: describe,},
			url:"${ctx}/admin/salaryCalc/goViewCalcDescr",
			height:330,
			width:680,
	        resizable: true,
			returnFun:goto_query,
		});	
   	}
	
	function getMainPageData(dataTableId, dataTablePagerId, paymentDef, firstPagerId){
		var salaryScheme = $.trim("${salaryData.salaryScheme}"); 
		var salaryMon = $.trim("${salaryData.salaryMon}"); 
		var dept1Code = $("#dept1Code").val();
		var positionClass= $("#positionClass").val();
		var dateFrom = $("#dateFrom").val();
		var dateTo = $("#dateTo").val();
		var empStatus = $("#empStatus").val();
	
		var list = {};
		var colModel=[];
		
		$.ajax({
			url:"${ctx}/admin/salaryCalc/getPaymentDetailPageData",
			type: "post",
			data: {salaryScheme: salaryScheme, salaryMon: salaryMon, paymentDef: paymentDef,},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~",});
		    },
		    success: function(obj){
				if(obj && obj.length > 0 && paymentDef > 0){
					// 获取colmode
					colModel = getColModel(obj);					
					// 加载冻结列的jqgrid ，jqgird对多页签的冻结列支持有问题
					//initFlozenCol(dataTableId, colModel, getJqGridGroupMap(), getJqGridFrozenColModel());
					var firstPageWidth = 0;
					if(firstPagerId && firstPagerId != ""){
						firstPageWidth = document.getElementById(firstPagerId).style.width;
					}
					
					//加载jqgrid
					initJqGridCol(dataTableId, colModel);
					
					//遍历插入数据
					addJqgridData(dataTableId, obj, dataTablePagerId, firstPageWidth);
				} else {
					var jqGridOption = {
						height: $(document).height() - $("#content-list").offset().top-70,
						styleUI:"Bootstrap",
						colModel:getDefaultColModel(),
					};
					Global.JqGrid.initJqGrid(dataTableId, jqGridOption);
				};
		    },
		});
	}
	
	// jqgrid插入数据
	function addJqgridData(dataTableId, obj,dataTablePageId,firstPageWidth){
		var len = obj.length;
	   	/* $.each(obj, function (k, v) {
   			$("#"+dataTableId).addRowData(myRound(k)+1, v);
   			if(k == (len - 1)){
   				setFooterData(dataTableId);
   			}
   		}); */
   		
   		$("#"+dataTableId).addRowData('id', obj);
   		setFooterData(dataTableId);
   		
   		if(firstPageWidth != 0){
	   		document.getElementById(dataTablePageId).style.width = firstPageWidth;
   		};
	}
	
	//设置冻结的jqgrid
	function initFlozenCol(dataTableId, colModel, groupMap, frozenColModel){
		var groupHeaders = [];
		//冻结列跟普通列结合 冻结列放前面
		colModel = frozenColModel.concat(colModel);
		
		//遍历将多个双表头分组添加到jqGrid 的 colModel
		for(var groupKey in groupMap){
			var groupSet = {};
			groupSet.startColumnName = groupMap[groupKey][0].name;
			groupSet.numberOfColumns = groupMap[groupKey].length;
			groupSet.titleText = groupKey;
			groupHeaders.push(groupSet);
			
			colModel = colModel.concat(groupMap[groupKey]);
		}
		
		var jqGridOption = {
			height: $(document).height() - $("#content-list").offset().top-92,
			styleUI:"Bootstrap",
			colModel:colModel,
			gridComplete:function(){
	        	frozenHeightReset(dataTableId);
	        },
		};
		//初始化jqGrid
		Global.JqGrid.initJqGrid(dataTableId, jqGridOption);
		
		$("#"+dataTableId).jqGrid('setGroupHeaders', {
			useColSpanStyle: true, 
			groupHeaders:groupHeaders,
		});
		
		$("#"+dataTableId).jqGrid('setFrozenColumns');
	}
	
	// 初始化jqgrid
	function initJqGridCol(dataTableId, colModel){
		var jqGridOption = {
			height: 500,
			styleUI:"Bootstrap",
			colModel:colModel,
		};
		//初始化jqGrid
		Global.JqGrid.initJqGrid(dataTableId, jqGridOption);
	}
	
	// 表格默认的colModel，当没有数据的时候 显示这个
	function getDefaultColModel(){
		var defaultColModel =[
			{name:"pk", index:"pk", label:"pk", width:100, sortable: true, align:"left",hidden:true,}, 
			{name:"empcode", index:"empcode", label:"员工编号", width:100, sortable: true, align:"left",hidden:true,}, 
			{name:"empname", index:"empname", label:"员工姓名", width:80, sortable: true, align:"left",}, 
			{name:"dept1descr", index:"dept1descr", label:"小巴", sortable: true, width:80,align:"left",}, 
			{name:"dept2descr", index:"dept2descr", label:"部门", sortable: true, width:80,align:"left",}, 
			{name:"positionclassdescr", index:"positionclassdescr", label:"岗位", width:80, sortable: true, align:"left",}, 
			{name:"joindate", index:"joindate", label:"入职日期", width:100, sortable: true, align:"left",formatter:formatDate,}, 
			{name:"leavedate", index:"leavedate", label:"离职日期", width:100, sortable: true, align:"left",formatter:formatDate,}, 
			{name:"salarymon", index:"salarymon", label:"月份",width:60, sortable: true, align:"left",}, 
			{name:"basicsalary", index:"basicsalary", label:"基本工资", sortable: true, width:80, align:"left",},
		];
		
		return defaultColModel;
	}
	
	// 指定需要合计的表头
	function getSumCol(){
		var sumCol = ["工资","实发工资","个税","计税工资"];
		
		return sumCol;
	}
	
	// 设置页脚的合计
	function setFooterData(dataTableId){
		var sumCols = getSumCol();
		var value = 0;
		var sum = 0;
		if(sumCols.length > 0){
 	    	var footerData = {};
 	    	footerData["rn"] = "合计";
 	    	$.each(sumCols,function(k,v){
 	    		sum = 0;
    			var col = $("#"+dataTableId).getCol(v);
    			if(col.length > 0){
	    			for(var i = 0; i< col.length; i++){
	 	    			var suffix = col[i].split("\">");
	 	    			if(suffix.length>1){
	 	    				value = suffix[1].split("</a>")[0];
	 	    				sum+=myRound(value,2);
	 	    			} else {
	 	    				value = col[i];
	 	    				sum+=myRound(value,2);
	 	    			};
	    			}
	    			var map = {};
	    			map[v] = myRound(sum,2);
	    			$("#"+dataTableId).footerData("set",map,false);
    			};
    		});
    	};
	}
	
	// 根据返回的数据设置colModel
	function getColModel(obj){
		var colModel=[];
		var leftTitle = ["月份","姓名", "入职日期","工号", "转正日期", "所属公司", "二级部门", "状态", "月份",
						 "身份证号", "岗位类别", "财务编码", "离职日期", "类型","岗位级别","签约公司","一级部门","银行","卡号","户名","职位","备注"];
		var frozenTitle = [];
		var groupDescr = "";
		var titleGroup = [];
		var groupMap = {};
		var tipList = [];
		var frozenColModel =[];
		var sumCol =getSumCol();
		
		for(var key in obj[0]){
			if(key.split("_tip").length>1){
				tipList.push(key.split("_tip")[0]);
			};
		}
	
		for(var key in obj[0]){
			var position = "right";
			if(leftTitle.indexOf($.trim(key))>=0){
				position = "left";
			}
			
			var titleSplit =key.split("__");
			//判断是否需要按表头分组 
			if(titleSplit.length>1){
				//{"一级表头1":[{col1},{col2}],"一级表头2":[{col3},{col4}]}
				groupDescr = titleSplit[0];
				title = titleSplit[1];
				
				if(title.split("tip").length>1){
					//_tip字段的 隐藏掉不显示
					list = {name: key, index: key, width: 75, label: title, sortable: true, align: position,hidden:true,};
				}else {
					//判断是否存在对应的tip 有的话设置格式化为可点击
					if(tipList.indexOf($.trim(key))>=0){
						list = {name: key, index: key, width: 75, label: title, sortable: true, align: position,formatter:clickOpt,};
					} else {
						//判断是否默认隐藏字段
						if(title.split("hid").length>1){
							list = {name: key, index: key, width: 75, label: title, sortable: true, align: position,hidden:true,};
						} else {
							list = {name: key, index: key, width: 75, label: title, sortable: true, align: position,};
						};
					};
				}
				//将需要分组的列保存单独起来
				if(!groupMap[groupDescr]){
				
					titleGroup = [];
					titleGroup.push(list);
					groupMap[groupDescr] = titleGroup;
				} else {
				
					titleGroup = groupMap[groupDescr];
					titleGroup.push(list);
					groupMap[groupDescr] = titleGroup;
				};
			} else {
				
				title = key;

				list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,};
				
				if(key.split("tip").length>1){
					list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,hidden:true,};
				}
				if(key.split("hid").length>1){
					list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,hidden:true,};
				}
				
				//包含日期字 formatter
				if(key.split("日期").length>1){
					list = {name: title, index: title, width: 90, label: title, sortable: true, align: position,formatter:formatDate,};
				}
				
				//需要冻结列的单独保存起来
				if(frozenTitle.indexOf(key)>=0){
					if(key == "身份证号"|| key == "卡号" || key == "备注"){ // 单独设置行宽
						list = {name: title, index: title, width: 120, label: title, sortable: true, align: "left",frozen: true,};
					} else {
						list = {name: title, index: title, width: 70, label: title, sortable: true, align: position,frozen: true,};
					}
					
					frozenColModel.push(list);
					setJqGridFrozenColModel(frozenColModel);
					continue;
				}
				
				if(tipList.indexOf($.trim(key))>=0){
					list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,formatter:clickOpt,};
					if(sumCol.indexOf(key)>=0){
						list = {name: title, index: title, width: 120, label: title, sortable: true, align: position,formatter:clickOpt,sum:true,};
					};
				}
				
				if(key == "身份证号"|| key == "卡号"){
					list = {name: title, index: title, width: 120, label: title, sortable: true, align: position,frozen: true,};
				}
				if(key == "备注"){
					list = {name: title, index: title, width: 180, label: title, sortable: true, align: "left",frozen: true,};
				}
				colModel.push(list);
			};
		}
		setJqGridGroupMap(groupMap);
		return colModel;
	}

function changeExcelInfo(tableId, paymentDef){
	$("#paymentDef").val(paymentDef);
	$("#tableId").val(tableId);
}

function doExcel() {
	var tableId = $("#tableId").val();
	var url ="${ctx}/admin/salaryCalc/doPaymentExcel";
	doExcelAll(url,tableId);
}
</script>
</head>
<body >
	<div class="body-box-form" >
		<div class="content-form">
			<div class="panel panel-system" >
    			<div class="panel-body" >
     				<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
						<button type="button" class="btn btn-system " 
								onclick="doExcel()" title="导出当前excel数据" >
						<span>导出excel</span></button>
					</div>
				</div>
			</div>
		</div>
		<form hidden="true" role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden" name="salaryMon" id="salaryMon" value="${salaryData.salaryMon}"/>
			<input type="hidden" name="salaryScheme" id="salaryScheme" value="${salaryData.salaryScheme}" />
			<input type="hidden" name="paymentDef" id="paymentDef" value="" />
			<input type="hidden" name="tableId" id="tableId" value="" />
			<input type="hidden" name="jsonString" value="" />
		</form>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" id="tab_ul">  
		    </ul> 
		    <div class="tab-content" id="tab_content">  
		    </div>  
		</div>
	</div>
</body>
</html>
