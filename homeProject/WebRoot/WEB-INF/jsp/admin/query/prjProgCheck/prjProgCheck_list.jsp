<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工地巡检_分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function View(){
	var ret = selectDataTableRow();
    if (ret) {    	
      Global.Dialog.showDialog("prjProgCheckView",{
		  title:"整改验收确认",
		  url:"${ctx}/admin/prjProgCheck/goView?id="+ret.no,		  	
		  height:700,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/prjProgCheck/doExcelcheckgdxj";
	var tableId = 'dataTable';
	  if ($("#statistcsMethod").val() == '1') {		//  content-list-groupByTgfx 是表工地停工原因分析
		tableId='dataTable';
	} else if ($("#statistcsMethod").val() == '2'){
		tableId='dataTableGroupBycheckman';
	} else if ($("#statistcsMethod").val() == '3'){
		tableId='dataTableGroupByPrjman';
	}else if ($("#statistcsMethod").val() == '4'){
		tableId='dataTableGroupByDep2';
	}
	doExcelAll(url, tableId);
}    

function goto_query(){
	var tableId ;	
	if($.trim($("#beginDate").val())==''){
			art.dialog({content: "巡检日期从不能为空",width: 200});
			return false;
	} 
	if($.trim($("#endDate").val())==''){
			art.dialog({content: "巡检日期至不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#beginDate").val()));
     var dateEnd = Date.parse($.trim($("#endDate").val()));

     if ($("#statistcsMethod").val() == '1') {		//  content-list-groupByTgfx 是表工地停工原因分析
		tableId='dataTable';
	} else if ($("#statistcsMethod").val() == '2'){
		tableId='dataTableGroupBycheckman';
	} else if ($("#statistcsMethod").val() == '3'){
		tableId='dataTableGroupByPrjman';
	}else if ($("#statistcsMethod").val() == '4'){
		tableId='dataTableGroupByDep2';
	}		
	$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/prjProgCheck/prjProgCheckMx",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
	change();
}
	
		
/**初始化表格*/
$(function(){
$("#projectMan").openComponent_employee({showValue:"${prjProgCheck.custCode}"});
$("#checkMan").openComponent_employee();
	Global.JqGrid.initJqGrid("dataTable",{    					     //明细
	//	url:"${ctx}/admin/prjProgCheck/prjProgCheckMx",
		height:380,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "address", index: "address", width: 147, label: "楼盘地址", sortable: true, align: "left",count :true},
				{name: "projectmandescr", index: "projectmandescr", width: 74, label: "项目经理", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 73, label: "部门", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 87, label: "实际开工时间", sortable: true, align: "left", formatter: formatDate},
				{name: "nowspeeddescr", index: "nowspeeddescr", width: 130, label: "当前进度", sortable: true, align: "left",hidden:true},
				{name: "prjitemdescr", index: "prjitemdescr", width: 150, label: "巡检进度", sortable: true, align: "left"},
				{name: "isupprjprogdescr", index: "isupprjprogdescr", width: 100, label: "更新进度", sortable: true, align: "left"},
				{name: "buildstatusdescr", index: "buildstatusdescr", width: 74, label: "工地状态", sortable: true, align: "left"},
				{name: "ismodifydescr", index: "ismodifydescr", width: 72, label: "是否整改", sortable: true, align: "left"},
				{name: "safepromdescr", index: "safepromdescr", width: 70, label: "安全问题", sortable: true, align: "left"},
				{name: "visualpromdescr", index: "visualpromdescr", width: 71, label: "形象问题", sortable: true, align: "left"},
				{name: "artpromdescr", index: "artpromdescr", width: 71, label: "工艺问题", sortable: true, align: "left"},
				{name: "ciganum", index: "ciganum", width: 71, label: "烟头个数", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 185, label: "备注", sortable: true, align: "left"},
				{name: "modifytime", index: "modifytime", width: 83, label: "整改时限", sortable: true, align: "right"},
				{name: "remainmodifytime", index: "remainmodifytime", width: 90, label: "剩余整改时限", sortable: true, align: "right"},
				{name: "modifycompletedescr", index: "modifycompletedescr", width: 74, label: "整改完成", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 64, label: "验收人", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 72, label: "巡检人员", sortable: true, align: "left"},
				{name: "date", index: "date", width: 138, label: "巡检日期", sortable: true, align: "left", formatter: formatTime},
				{name: "gpsaddress", index: "gpsaddress", width: 240, label: "定位地点", sortable: true, align: "left"},
				{name: "compremark", index: "compremark", width: 120, label: "完成说明", sortable: true, align: "left"},
				{name: "compczydescr", index: "compczydescr", width: 63, label: "整改人", sortable: true, align: "left"},
				{name: "compdate", index: "compdate", width: 138, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
				{name: "custcode", index: "custcode", width: 92, label: "客户编号", sortable: true, align: "left"},
				{name: "no", index: "no", width: 83, label: "编号", sortable: true, align: "left"}
	          ],	               		         
	});
    Global.JqGrid.initJqGrid("dataTableGroupBycheckman",{   		//巡检人
		//url:"${ctx}/admin/prjProgCheck/prjProgCheckPatrolman",
		height:415,
		styleUI: 'Bootstrap',
		loadonce:true,
		colModel : [
				{name: "appczy", index: "appczy", width: 90, label: "巡检人编号", sortable: true, align: "left",count : true},
				{name: "namechi", index: "namechi", width: 91, label: "巡检人", sortable: true, align: "left"},
				{name: "postiondescr", index: "postiondescr", width: 90, label: "职位", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 85, label: "部门", sortable: true, align: "left"},
				{name: "checkcount", index: "checkcount", width: 100, label: "巡检次数", sortable: true, align: "right", sum: true},
				{name: "lpcount", index: "lpcount", width: 79, label: "巡检套数", sortable: true, align: "right", sum: true},
           		{name: "cqdate", index: "cqdate", width: 80, label: "巡检出勤天数", sortable: true, align: "right", sum: true},
				{name: "modifycount", index: "modifycount", width: 100, label: "整改次数", sortable: true, align: "right", sum: true},
				{name: "modifycompcount", index: "modifycompcount", width: 100, label: "整改完成次数", sortable: true, align: "right", sum: true},
				{name: "modifyper", index: "modifyper", width: 100, label: "整改占比", sortable: true, align: "right",avg:true, formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "modifyintimecount", index: "modifyintimecount", width: 128, label: "整改及时完成次数", sortable: true, align: "right", sum: true},
				{name: "modifyintimeper", index: "modifyintimeper", width: 80, label: "整改及时率", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "modifycompper", index: "modifycompper", width: 80, label: "整改完成率", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
           		{name: "confirmcount", index: "confirmcount", width: 80, label: "验收套数", sortable: true, align: "right", sum: true},
           		{name: "confirmcqdate", index: "confirmcqdate", width: 80, label: "验收出勤天数", sortable: true, align: "right", sum: true}
           ],              
        	rowNum:100000,  
			pager :'1',            
            gridComplete:function(){
				var delayAvg=0;
				var sumOnDo=0;
				var sumTotal=0;
				//整改占比的总平均
				var onDo = Global.JqGrid.allToJson("dataTableGroupBycheckman","checkcount");//巡检次数
				var total = Global.JqGrid.allToJson("dataTableGroupBycheckman","modifycount");//整改次数"
				arr = onDo.fieldJson.split(",");	
				arry = total.fieldJson.split(",");
				for(var i = 0;i < arry.length; i++){
					sumOnDo=sumOnDo+parseInt(arr[i]);
					sumTotal=sumTotal+parseInt(arry[i]);
				}
				if(isNaN(sumTotal*100/sumOnDo)){
					$("#dataTableGroupBycheckman").footerData("set",{"modifyper":(0).toFixed(1)+"%"},false);
				}else{
					$("#dataTableGroupBycheckman").footerData("set",{"modifyper":(sumTotal*100/sumOnDo).toFixed(1)+"%"},false);
				}
		   	  //整改占比的总平均
		   	 
		   	  // 整改及时率
		   	   var onDo = Global.JqGrid.allToJson("dataTableGroupBycheckman","modifyintimecount"); //整改及时完成次数
			   arr = onDo.fieldJson.split(",");	
			   var sumOnDo=0;		
			   for(var i = 0;i < arry.length; i++){
				   sumOnDo=sumOnDo+parseInt(arr[i]);
				}
				if(isNaN(sumOnDo*100/sumTotal)){
					$("#dataTableGroupBycheckman").footerData("set",{"modifyintimeper":0+"%"},false);
				}else{
					$("#dataTableGroupBycheckman").footerData("set",{"modifyintimeper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
				}
		   	   
		   	  //整改及时率
		 	  
		 	  // 整改完成率
		   	   var onDo = Global.JqGrid.allToJson("dataTableGroupBycheckman","modifycompcount"); //整改完成次数
			   arr = onDo.fieldJson.split(",");	
			   var sumOnDo=0;		
			   for(var i = 0;i < arry.length; i++){
				   sumOnDo=sumOnDo+parseInt(arr[i]);
				}
				if(isNaN(sumOnDo*100/sumTotal)){
					$("#dataTableGroupBycheckman").footerData("set",{"modifycompper":0+"%"},false);
				}else{
					$("#dataTableGroupBycheckman").footerData("set",{"modifycompper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
				}
		   	  //整改完成率
		  },
				
	}); 
	Global.JqGrid.initJqGrid("dataTableGroupByPrjman",{   			//项目经理
	//	url:"${ctx}/admin/prjProgCheck/prjProgCheckPrjman",
		height:415,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "namechi", index: "namechi", width: 90, label: "项目经理", sortable: true, align: "left",count : true},
				{name: "projectman", index: "projectman", width: 90, label: "项目经理编号", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 90, label: "部门", sortable: true, align: "left"},
				{name: "custcount", index: "custcount", width: 90, label: "在建工地数", sortable: true, align: "right", sum: true},
				{name: "checkcount", index: "checkcount", width: 80, label: "巡检次数", sortable: true, align: "right", sum: true},
				{name: "checkper", index: "checkper", width: 90, label: "巡检覆盖率", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "modifycount", index: "modifycount", width: 80, label: "整改次数", sortable: true, align: "right", sum: true},
				{name: "modifyper", index: "modifyper", width: 80, label: "整改占比", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "modifyintimecount", index: "modifyintimecount", width: 128, label: "整改及时完成次数", sortable: true, align: "right", sum: true},
				{name: "modifyintimeper", index: "modifyintimeper", width: 90, label: "整改及时率", sortable: true, sum : true,align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "modifycompcount", index: "modifycompcount", width: 90, label: "整改完成次数", sortable: true, align: "right", sum: true},
				{name: "modifycompper", index: "modifycompper", width: 90, label: "整改完成率", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
	       		{name: "sumupprjprog", index: "sumupprjprog", width: 130, label: "巡检更新进度数量", sortable: true, align: "right", sum: true},
	       		{name: "nobuilder", index: "nobuilder", width: 110, label: "未报备停工数", sortable: true, align: "right", sum: true}
	       ],	       
        	rowNum:100000,  
			pager :'1', 
	        gridComplete:function(){
				var delayAvg=0;
				var sumOnDo=0;
				var sumTotal=0;
				//巡检覆盖率
				var onDo = Global.JqGrid.allToJson("dataTableGroupByPrjman","checkcount");//巡检次数
				var total = Global.JqGrid.allToJson("dataTableGroupByPrjman","custcount");//在建工地数"
				arr  = onDo.fieldJson.split(",");	
				arry = total.fieldJson.split(",");
				for(var i = 0;i < arry.length; i++){
					sumOnDo=sumOnDo+parseInt(arr[i]);
					sumTotal=sumTotal+parseInt(arry[i]);
				}
				if (isNaN(sumOnDo*100/sumTotal)){
					$("#dataTableGroupByPrjman").footerData("set",{"checkper":(0).toFixed(1)+"%"},false);
				}else{
		   	  	 	$("#dataTableGroupByPrjman").footerData("set",{"checkper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
		   	 	}//巡检覆盖率
		   	 
		   	  // 整改占比
		   	   var onDo = Global.JqGrid.allToJson("dataTableGroupByPrjman","modifycount"); //整改次数
			   arr = onDo.fieldJson.split(",");	
			   var sumTotal=0;		
			   for(var i = 0;i < arry.length; i++){
				   sumTotal=sumTotal+parseInt(arr[i]);
				}
				if (isNaN(sumOnDo*100/sumTotal)){
					$("#dataTableGroupByPrjman").footerData("set",{"checkper":(0).toFixed(1)+"%"},false);
				}else{
		   	  	 	$("#dataTableGroupByPrjman").footerData("set",{"checkper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
		   	 	}
		   	 	if (isNaN(sumTotal*100/sumOnDo)){
					$("#dataTableGroupByPrjman").footerData("set",{"modifyper":(0).toFixed(1)+"%"},false);
				}else{
		   	  	 	$("#dataTableGroupByPrjman").footerData("set",{"modifyper":(sumTotal*100/sumOnDo).toFixed(1)+"%"},false);
		   	 	}
		   	   
		   	  //整改占比
		 	  
		 	  // 整改及时率
		   	   var total = Global.JqGrid.allToJson("dataTableGroupByPrjman","modifyintimecount"); //整改及时完成次数
			   arr = total.fieldJson.split(",");	
			   var sumOnDo=0;		
			   for(var i = 0;i < arry.length; i++){
				   sumOnDo=sumOnDo+parseInt(arr[i]);
				}
			   if(isNaN(sumTotal)){
					$("#dataTableGroupByPrjman").footerData("set",{"modifyintimeper":0+"%"},false);
				}else{
					$("#dataTableGroupByPrjman").footerData("set",{"modifyintimeper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
				}
		   	  //整改及时率
		   	  
		 	  // 整改完成率
		   	   var onDo = Global.JqGrid.allToJson("dataTableGroupByPrjman","modifycompcount"); //整改完成次数
			   arr = onDo.fieldJson.split(",");	
			   var sumOnDo=0;		
			   for(var i = 0;i < arr.length; i++){
				   sumOnDo=sumOnDo+parseInt(arr[i]);
				}
				if(isNaN(sumTotal)){
					$("#dataTableGroupByPrjman").footerData("set",{"modifycompper":0+"%"},false);
				}else{
					$("#dataTableGroupByPrjman").footerData("set",{"modifycompper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
				}
		   	  //整改完成率
		   	  }
	});
	Global.JqGrid.initJqGrid("dataTableGroupByDep2",{   			//部门
		//url:"${ctx}/admin/prjProgCheck/prjProgCheckDepartment2",
		height:415,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "dept1descr", index: "dept1descr", width: 80, label: "工程部", sortable: true, align: "left",count: true},
				{name: "dept2descr", index: "dept2descr", width: 80, label: "部门", sortable: true, align: "left"},
				{name: "custcount", index: "custcount", width: 80, label: "在建工地数", sortable: true, align: "right", sum: true},
				{name: "checkcount", index: "checkcount", width: 80, label: "巡检次数", sortable: true, align: "right", sum: true},
				{name: "lpcount", index: "lpcount", width: 80, label: "巡检套数", sortable: true, align: "right", sum: true},
				{name: "modifycount", index: "modifycount", width: 80, label: "整改次数", sortable: true, align: "right", sum: true},
				{name: "modifyper", index: "modifyper", width: 80, label: "整改占比", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "safepromnotdo", index: "safepromnotdo", width: 110, label: "安全问题（未做）", sortable: true, align: "right"},
				{name: "safepromfail", index: "safepromfail", width: 118, label: "安全问题（不合格）", sortable: true, align: "right"},
				{name: "visualpromfail", index: "visualpromfail", width: 80, label: "形象问题", sortable: true, align: "right"},
				{name: "artpromfail", index: "artpromfail", width: 80, label: "工艺问题", sortable: true, align: "right"},
				{name: "modifyintimecount", index: "modifyintimecount", width: 128, label: "整改及时完成次数", sortable: true, align: "right", sum: true},
				{name: "modifyintimeper", index: "modifyintimeper", width: 80, label: "整改及时率", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "modifycompcount", index: "modifycompcount", width: 85, label: "整改完成次数", sortable: true, align: "right", sum: true},
				{name: "modifycompper", index: "modifycompper", width: 80, label: "整改完成率", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:" ", thousandsSeparator: " ", decimalPlaces: 0, suffix: "%"}},
				{name: "nobuilder", index: "nobuilder", width: 110, label: "未报备停工数", sortable: true, align: "right", sum: true}
	       ],	     
        	rowNum:100000,  
			pager :'1', 
	        gridComplete:function(){
				var delayAvg=0;
				var sumOnDo=0;
				var sumTotal=0;
				//整改占比
				var onDo = Global.JqGrid.allToJson("dataTableGroupByDep2","checkcount");//巡检次数
				var total = Global.JqGrid.allToJson("dataTableGroupByDep2","modifycount");//整改次数"
				arr = onDo.fieldJson.split(",");	
				arry = total.fieldJson.split(",");
				for(var i = 0;i < arry.length; i++){
					sumOnDo=sumOnDo+parseInt(arr[i]);
					sumTotal=sumTotal+parseInt(arry[i]);
				}
				if(isNaN(sumTotal*100/sumOnDo)){
					$("#dataTableGroupByDep2").footerData("set",{"modifyper":(0).toFixed(1)+"%"},false);
				}else{
					$("#dataTableGroupByDep2").footerData("set",{"modifyper":(sumTotal*100/sumOnDo).toFixed(1)+"%"},false);
				}
		   	   
		   	  //整改占比
		   	
		   	  // 整改及时率
		   	   var onDo = Global.JqGrid.allToJson("dataTableGroupByDep2","modifyintimecount"); //整改及时完成次数
			   arr = onDo.fieldJson.split(",");	
			   var sumOnDo=0;		
			   for(var i = 0;i < arry.length; i++){
				   sumOnDo=sumOnDo+parseInt(arr[i]);
				}
				if(isNaN(sumTotal)){
					$("#dataTableGroupByDep2").footerData("set",{"modifyintimeper":0+"%"},false);
				}else{
					$("#dataTableGroupByDep2").footerData("set",{"modifyintimeper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
				}
		   	  //整改及时率
		 	  
		 	  // 整改完成率
		   	   var onDo = Global.JqGrid.allToJson("dataTableGroupByDep2","modifycompcount"); //整改完成次数
			   arr = onDo.fieldJson.split(",");	
			   var sumOnDo=0;		
			   for(var i = 0;i < arry.length; i++){
				   sumOnDo=sumOnDo+parseInt(arr[i]);
				}
				if(isNaN(sumTotal)){
					$("#dataTableGroupByDep2").footerData("set",{"modifycompper":0+"%"},false);
				}else{
					$("#dataTableGroupByDep2").footerData("set",{"modifycompper":(sumOnDo*100/sumTotal).toFixed(1)+"%"},false);
				}
		   	  //整改完成率
		  },
	});
	$("#content-list").hide();
	$("#content-list-groupByPrjman").hide(); 
	$("#content-list-groupByDep2").hide(); 
	$("#btnview").hide();
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#openComponent_employee_projectMan").val('');
	$("#openComponent_employee_checkMan").val('');
	$("#Department2").val('');
	$("#Department2Check").val('');
	$("#address").val('');
	$("#isModify").val('');
	$("#isModifyComplete").val('');
} 

function change(){ 
	var tableId;									//  content-list  <!-- 明细 -->
	if ($("#statistcsMethod").val() == '1') {		//  content-list-groupBycheckman <!-- 巡检人 -->
		tableId = 'dataTable';						//  content-list-groupByPrjman   <!-- 项目经理 -->
		$("#content-list").show();					//  content-list-groupByDep2     <!-- 部门 -->
		$("#content-list-groupBycheckman").hide();
		$("#content-list-groupByPrjman").hide();
		$("#content-list-groupByDep2").hide();
		$("#btnview").show();
	} else if ($("#statistcsMethod").val() == '2'){
		tableId = 'groupBycheckman';
		$("#content-list").hide();
		$("#content-list-groupBycheckman").show();
		$("#content-list-groupByPrjman").hide();
		$("#content-list-groupByDep2").hide();
		$("#btnview").hide();
	} else if ($("#statistcsMethod").val() == '3'){
		tableId = 'groupByPrjman';
		$("#content-list").hide();
		$("#content-list-groupBycheckman").hide();
		$("#content-list-groupByPrjman").show();
		$("#content-list-groupByDep2").hide();
		$("#btnview").hide();
	} else if ($("#statistcsMethod").val() == '4'){
		tableId = 'groupByDep2';
		$("#content-list").hide();
		$("#content-list-groupBycheckman").hide();
		$("#content-list-groupByPrjman").hide();
		$("#content-list-groupByDep2").show();
		$("#btnview").hide();
	}	
}

function updateIncludeErrPosi(o) {
    if (o.checked)
        $("#includeErrPosi").val("1")
    else
        $("#includeErrPosi").val("0")
}

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" id="includeErrPosi" name="includeErrPosi" value="0" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						
						<li>
							<label >巡检日期</label>
							<input type="text" id="beginDate" name="beginDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${prjProgCheck.beginDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label >至</label>
							<input type="text" id="endDate" name="endDate" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${prjProgCheck.endDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>工程部</label>
							<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
						</li>
						<li>
							<label>巡检人部门</label>
							<house:department2 id="Department2Check" dictCode="03"  depType="3"  ></house:department2>
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address"   />
						</li>
						<li>
							<label>项目经理</label>
							<input type="text" id="projectMan" name="projectMan"  />
						</li>
						<li>
							<label>巡检人</label>
							<input type="text" id="checkMan" name="checkMan"   />
						</li>
						<li>	
							<label >是否整改</label>
								<house:xtdm  id="isModify" dictCode="YESNO"    ></house:xtdm>
							</label>
						</li>
						<li>	
							<label >更新进度</label>
								<house:xtdm  id="isUpPrjProg" dictCode="YESNO"    ></house:xtdm>
							</label>
						</li>
						<li>	
							<label >整改完成</label>
								<house:xtdm  id="isModifyComplete" dictCode="YESNO"  ></house:xtdm>
							</label>
						</li>
						<li>	
							<label >工地状态</label>
							<house:xtdmMulit id="buildStatus" dictCode="BUILDSTATUS" ></house:xtdmMulit>                     
						</li>
						<li> 
						    <label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" >
								<option value="2">按巡检人</option>
								<option value="1">明细</option>
								<option value="3">按项目经理</option>
								<option value="4">按部门</option>
							</select>
						</li>
						<li>
						    <label>包含位置异常</label>
						    <input type="checkbox" name="includeErrPosiTag" onclick="updateIncludeErrPosi(this)"/>
						</li>
					</ul>		
					<ul class="ul-form">
					<li id="loadMore" >
						<button type="button"  class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
					</li>					
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
		     	    <house:authorize authCode="PRJPROGCHECK_VIEW">
						<button type="button" id = "btnview" class="btn btn-system "  onclick="View()">查看</button>
					</house:authorize>
	                    <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</div>
			<div id="content-list">               <!-- 明细 -->
				<table id= "dataTable"></table>  
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupBycheckman"> <!-- 巡检人 -->
				<table id= "dataTableGroupBycheckman"></table> 
				<div id="dataTableGroupBycheckman"></div>
			</div>
			<div id="content-list-groupByPrjman">    <!-- 项目经理 -->
				<table id= "dataTableGroupByPrjman"></table> 
				<div id="dataTableGroupByPrjman"></div>
			</div>
			<div id="content-list-groupByDep2"> <!-- 部门 -->
				<table id= "dataTableGroupByDep2"></table> 
				<div id="dataTableGroupByDep2"></div>
			</div>
		</div> 
	</div>
</body>
</html>


