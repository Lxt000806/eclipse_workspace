<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>集成测量_分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function View(){
	var ret = selectDataTableRow();
    if (ret) {    	
      Global.Dialog.showDialog("prjProgCheckView",{
		  title:"任务管理——查看",
		  url:"${ctx}/admin/prjJob/goView?id="+ret.no,		  	
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
	var url = "${ctx}/admin/prjJob/doExceljccl";
	var tableId = 'dataTable'+'dataTable'.footerData;
	if ($("#statistcsMethod").val() == '1') {		//  content-list-groupByTgfx 是表工地停工原因分析
		tableId='dataTable';
	} else if ($("#statistcsMethod").val() == '2'){
		tableId='dataTableGroupByNoMx';
	} else if ($("#statistcsMethod").val() == '3'){
		tableId='dataTableGroupByPrjman';
	}else if ($("#statistcsMethod").val() == '4'){
		tableId='dataTableGroupByDep2';
	} else if ($("#statistcsMethod").val() == '5'){
		tableId='dataTableGroupByJCman';
	}else if ($("#statistcsMethod").val() == '6'){
		tableId='dataTableGroupByJCDep2';
	}	
	doExcelAll(url, tableId);
}    

function goto_query(){
	var tableId ;	
	if($.trim($("#appDateform").val())==''){
			art.dialog({content: "申请日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#appDateto").val())==''){
			art.dialog({content: "申请日期至不能为空",width: 200});
			return false;
	}
    var dateStart = Date.parse($.trim($("#beginDate").val()));
    var dateEnd = Date.parse($.trim($("#endDate").val()));

    if ($("#statistcsMethod").val() == '1') {		//  content-list-groupByTgfx 是表工地停工原因分析
		tableId='dataTable';
	} else if ($("#statistcsMethod").val() == '2'){
		tableId='dataTableGroupByNoMx';
	} else if ($("#statistcsMethod").val() == '3'){
		tableId='dataTableGroupByPrjman';
	}else if ($("#statistcsMethod").val() == '4'){
		tableId='dataTableGroupByDep2';
	} else if ($("#statistcsMethod").val() == '5'){
		tableId='dataTableGroupByJCman';
	}else if ($("#statistcsMethod").val() == '6'){
		tableId='dataTableGroupByJCDep2';
	}		
	$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/prjJob/prjJobApply",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
	change();
}
	
		
/**初始化表格*/
$(function(){
$("#ProjectMan").openComponent_employee({showValue:"${prjJob.custCode}"});
$("#JCDesigner").openComponent_employee();
$("#intDesigner").openComponent_employee();
$("#cupDesigner").openComponent_employee();
	Global.JqGrid.initJqGrid("dataTable",{    					     //申报明细
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "no", index: "no", width: 73, label: "编号", sortable: true, align: "left",count:true},
				{name: "statusdescr", index: "statusdescr", width: 65, label: "状态", sortable: true, align: "left"},
				{name: "address", index: "address", width: 136, label: "楼盘地址", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 91, label: "客户类型", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 76, label: "材料类型1", sortable: true, align: "left"},
				{name: "jobtypedescr", index: "jobtypedescr", width: 75, label: "任务类型", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 83, label: "开工日期", sortable: true, align: "left", formatter: formatDate},
				{name: "date", index: "date", width: 135, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "apptime", index: "apptime", width: 75, label: "申报天数", sortable: true, align: "right" },
				{name: "appczydescr", index: "appczydescr", width: 64, label: "申请人", sortable: true, align: "left"},
				{name: "prjdepartment2", index: "prjdepartment2", width: 88, label: "申请人部门", sortable: true, align: "left"},
				{name: "phone", index: "phone", width: 107, label: "电话", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 141, label: "备注", sortable: true, align: "left"},
				{name: "dealczy", index: "dealczy", width: 84, label: "处理人编码", sortable: true, align: "left"},
				{name: "dealczydescr", index: "dealczydescr", width: 71, label: "处理人", sortable: true, align: "left"},
				{name: "jcdepartment2", index: "jcdepartment2", width: 86, label: "处理人部门", sortable: true, align: "left"},
				{name: "plandate", index: "plandate", width: 98, label: "计划处理时间", sortable: true, align: "left", formatter: formatDate},
				{name: "dealdate", index: "dealdate", width: 135, label: "实际处理时间", sortable: true, align: "left", formatter: formatTime},
				{name: "measuretime", index: "measuretime", width: 72, label: "测量天数", sortable: true, align: "right"},
				{name: "enddescr", index: "enddescr", width: 73, label: "处理结果", sortable: true, align: "left"},
				{name: "warbranddescr", index: "warbranddescr", width: 88, label: "衣柜", sortable: true, align: "left"},
				{name: "cupbranddescr", index: "cupbranddescr", width: 89, label: "橱柜", sortable: true, align: "left"},
				{name: "dealremark", index: "dealremark", width: 103, label: "处理说明", sortable: true, align: "left"}
	          ],	
	            loadonce: true,
				rowNum:100000,  
				pager :'1', 
                gridComplete:function(){
				var sumapptime=0;  //申报天数
				var summeasuretime=0; // 测量天数
				var dealdate = Global.JqGrid.allToJson("dataTable","dealdate");
				var date = Global.JqGrid.allToJson("dataTable","date");
				$("#dataTable").footerData("set",{"date":  "平均申报天数"},false);
			    $("#dataTable").footerData("set",{"dealdate":"平均测量时间"},false);
			    var apptime = Global.JqGrid.allToJson("dataTable","apptime");
				var measuretime = Global.JqGrid.allToJson("dataTable","measuretime");
				var count=0;
				arr = apptime.fieldJson.split(",");	
				arry = measuretime.fieldJson.split(",");
				for(var i = 0;i < arr.length; i++){
					sumapptime=sumapptime+parseInt(arr[i]);					
				}
				for (var i = 0; i < arry.length ; i++){
					if (!isNaN(parseInt(arry[i]))){
					summeasuretime=summeasuretime+parseInt(arry[i]);
					count=count+1;
					}
				}
				summeasuretime=summeasuretime/count;
				if(isNaN(summeasuretime/count)){
					summeasuretime=0;					
				}
				sumapptime=sumapptime/arr.length;
				if (isNaN(sumapptime/count)){
					sumapptime=0;
				}
			    $("#dataTable").footerData("set",{"apptime":Math.floor(sumapptime)},false);
		   	    $("#dataTable").footerData("set",{"measuretime":Math.floor(summeasuretime)},false);
		  },
	});
    Global.JqGrid.initJqGrid("dataTableGroupByNoMx",{   		//未申报明细
		height:400,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "address", index: "address", width: 180, label: "楼盘地址", sortable: true, align: "left",count:true},
				{name: "custtypedescr", index: "custtypedescr", width: 91, label: "客户类型", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 86, label: "开工日期", sortable: true, align: "left", formatter: formatDate},
				{name: "havebegindate", index: "havebegindate", width: 85, label: "已施工天数", sortable: true, align: "right"},
				{name: "projectmandescr", index: "projectmandescr", width: 75, label: "项目经理", sortable: true, align: "left"},
				{name: "projectmandept", index: "projectmandept", width: 92, label: "部门", sortable: true, align: "left"},
				{name: "comedate", index: "comedate", width: 120, label: "饰面工人进场时间", sortable: true, align: "left", formatter: formatDate},
				{name: "intdesignerdescr", index: "intdesignerdescr", width: 86, label: "集成设计师", sortable: true, align: "left"},
				{name: "cupdesignerdescr", index: "cupdesignerdescr", width: 86, label: "橱柜设计师", sortable: true, align: "left"}
           ],   
           	loadonce: true,
        	rowNum:100000,  
			pager :'1',           
				
	}); 
	Global.JqGrid.initJqGrid("dataTableGroupByPrjman",{   			//项目经理
		height:$(document).height()-$("#content-list").offset().top-550,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "namechi", index: "namechi", width: 84, label: "项目经理", sortable: true, align: "left",count:true},
				{name: "prjdepartment2", index: "prjdepartment2", width: 100, label: "项目经理部门", sortable: true, align: "left"},
				{name: "noappnum", index: "noappnum", width: 108, label: "到点未申报套数", sortable: true, align: "right"},
				{name: "appnum", index: "appnum", width: 100, label: "申报套数", sortable: true, align: "right", sum: true},
				{name: "ontimenum", index: "ontimenum", width: 100, label: "及时申报套数", sortable: true, align: "right", sum: true},
				{name: "ontimeper", index: "ontimeper", width: 140, label: "及时申报率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
				{name: "avgtime", index: "avgtime", width: 100, label: "平均申报天数", sortable: true, align: "right"},
				{name: "nomeasurecondition", index: "nomeasurecondition", width: 145, label: "不具备测量条件套数", sortable: true, align: "right", sum: true},
				{name: "passper", index: "passper", width: 90, label: "申报合格率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
				{name: "alltime", index: "alltime", width: 74, label: "alltime", sortable: true, align: "left", sum: true,hidden:true}
	       ],
	        loadonce: true,
        	rowNum:100000,  
			pager :'1', 
            gridComplete:function(){
			var sumappnum=0;  
			var sumontimenum=0; 
			var sumnomeasurecondition=0; 
			var sumalltime=0;
			var appnum = Global.JqGrid.allToJson("dataTableGroupByPrjman","appnum"); // 申报套数
			var ontimenum = Global.JqGrid.allToJson("dataTableGroupByPrjman","ontimenum");// 及时申报套数
			var nomeasurecondition = Global.JqGrid.allToJson("dataTableGroupByPrjman","nomeasurecondition");// 不具备测量条件套数	
			var alltime = Global.JqGrid.allToJson("dataTableGroupByPrjman","alltime");// alltime		
			var ontimeper = Global.JqGrid.allToJson("dataTableGroupByPrjman","ontimeper");// 及时申报率	
			arr = appnum.fieldJson.split(",");	
			arry = ontimenum.fieldJson.split(",");
			arry1 = nomeasurecondition.fieldJson.split(",");	
			arry2 = alltime.fieldJson.split(",");	
			for(var i = 0;i < arr.length; i++){			
				sumappnum=sumappnum+parseInt(arr[i]);					
				sumontimenum=sumontimenum+parseInt(arry[i]);
				sumnomeasurecondition=sumnomeasurecondition+parseInt(arry1[i]);
				sumalltime=sumalltime+parseInt(arry2[i]);		
			}		
		   sumontimenum=sumontimenum*100/sumappnum;
		   passper=(sumappnum-sumnomeasurecondition)*100/sumappnum;
		   if(isNaN(sumontimenum)){
		   		sumontimenum=0;
		   }
		   if(isNaN(passper)){
		   		passper=0;
		   }		
	   	   $("#dataTableGroupByPrjman").footerData("set",{"ontimeper":(sumontimenum).toFixed(1)+"%"},false);
	   	   $("#dataTableGroupByPrjman").footerData("set",{"passper":(passper).toFixed(1)+"%"},false);
	   	   $("#dataTableGroupByPrjman").footerData("set",{"avgtime":myRound((sumalltime/sumappnum),0)},false);
	  },
	       
	});
	Global.JqGrid.initJqGrid("dataTableGroupByDep2",{   			//工程部
		height:$(document).height()-$("#content-list").offset().top-1000,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "namechi", index: "namechi", width: 84, label: "项目经理", sortable: true, align: "left",hidden:true},
				{name: "prjdepartment2", index: "prjdepartment2", width: 100, label: "项目经理部门", sortable: true, align: "left",sun:true},
				{name: "noappnum", index: "noappnum", width: 108, label: "到点未申报套数", sortable: true, align: "right"},
				{name: "appnum", index: "appnum", width: 100, label: "申报套数", sortable: true, align: "right", sum: true},
				{name: "ontimenum", index: "ontimenum", width: 100, label: "及时申报套数", sortable: true, align: "right", sum: true},
				{name: "ontimeper", index: "ontimeper", width: 140, label: "及时申报率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
				{name: "avgtime", index: "avgtime", width: 100, label: "平均申报天数", sortable: true, align: "right"},
				{name: "nomeasurecondition", index: "nomeasurecondition", width: 145, label: "不具备测量条件套数", sortable: true, align: "right", sum: true},
				{name: "passper", index: "passper", width: 90, label: "申报合格率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
				{name: "alltime", index: "alltime", width: 74, label: "alltime", sortable: true, align: "left", sum: true,hidden:true}
	       ],
	        loadonce: true,
        	rowNum:100000,  
			pager :'1', 
	        gridComplete:function(){
			var sumappnum=0;  
			var sumontimenum=0; 
			var sumnomeasurecondition=0; 
			var sumalltime=0;
			var appnum = Global.JqGrid.allToJson("dataTableGroupByDep2","appnum"); // 申报套数
			var ontimenum = Global.JqGrid.allToJson("dataTableGroupByDep2","ontimenum");// 及时申报套数
			var nomeasurecondition = Global.JqGrid.allToJson("dataTableGroupByDep2","nomeasurecondition");// 不具备测量条件套数	
			var alltime = Global.JqGrid.allToJson("dataTableGroupByDep2","alltime");// alltime					
			arr = appnum.fieldJson.split(",");	
			arry = ontimenum.fieldJson.split(",");
			arry1 = nomeasurecondition.fieldJson.split(",");	
			arry2 = alltime.fieldJson.split(",");	
			for(var i = 0;i < arr.length; i++){			
				sumappnum=sumappnum+parseInt(arr[i]);					
				sumontimenum=sumontimenum+parseInt(arry[i]);
				sumnomeasurecondition=sumnomeasurecondition+parseInt(arry1[i]);
				sumalltime=sumalltime+parseInt(arry2[i]);		
			}		
	   	   sumontimenum=sumontimenum*100/sumappnum;
		   passper=(sumappnum-sumnomeasurecondition)*100/sumappnum;
		   if(isNaN(sumontimenum)){
		   		sumontimenum=0;
		   }
		   if(isNaN(passper)){
		   		passper=0;
		   }
	   	   $("#dataTableGroupByDep2").footerData("set",{"ontimeper":(sumontimenum).toFixed(1)+"%"},false);
	   	   $("#dataTableGroupByDep2").footerData("set",{"passper":(passper).toFixed(1)+"%"},false);
	   	   $("#dataTableGroupByDep2").footerData("set",{"avgtime":myRound((sumalltime/sumappnum),0)},false);
	  },
	});
	Global.JqGrid.initJqGrid("dataTableGroupByJCman",{   		//集成设计师
		height:$(document).height()-$("#content-list").offset().top-1450,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "namechi", index: "namechi", width: 84, label: "集成设计师", sortable: true, align: "left",count:true},
				{name: "jctdepartment2", index: "jctdepartment2", width: 100, label: "部门", sortable: true, align: "left"},
				{name: "reordernum", index: "reordernum", width: 100, label: "接单套数", sortable: true, align: "right", sum: true},
				{name: "nomeasurecondition", index: "nomeasurecondition", width: 148, label: "不具备测量条件套数", sortable: true, align: "right", sum: true},
				{name: "unfinishnum", index: "unfinishnum", width: 140, label: "未完成测量套数", sortable: true, align: "right", sum: true},
				{name: "finishnum", index: "finishnum", width: 122, label: "已完成测量套数", sortable: true, align: "right", sum: true},
				{name: "ontimenum", index: "ontimenum", width: 145, label: "及时测量套数", sortable: true, align: "right", sum: true},
				{name: "ontimeper", index: "ontimeper", width: 90, label: "及时测量率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
				{name: "avgtime", index: "avgtime", width: 100, label: "平均测量天数", sortable: true, align: "right"},
				{name: "alltime", index: "alltime", width: 71, label: "alltime", sortable: true, align: "left", sum: true,hidden:true}
           ],   
           	loadonce: true,
        	rowNum:100000,  
			pager :'1',           
            gridComplete:function(){
			var sumontimenum=0;  
			var sumreordernum=0; 
			var sumalltime=0; 
			var ontimenum = Global.JqGrid.allToJson("dataTableGroupByJCman","ontimenum"); // 及时测量套数
			var reordernum = Global.JqGrid.allToJson("dataTableGroupByJCman","reordernum");// 接单套数
			var alltime = Global.JqGrid.allToJson("dataTableGroupByJCman","alltime");// alltime					

			arr = ontimenum.fieldJson.split(",");	
			arry = reordernum.fieldJson.split(",");
			arry1 = alltime.fieldJson.split(",");		
			for(var i = 0;i < arr.length; i++){			
				sumontimenum=sumontimenum+parseInt(arr[i]);					
				sumreordernum=sumreordernum+parseInt(arry[i]);
				if (!isNaN(parseInt(arry1[i]))){
					sumalltime=sumalltime+parseInt(arry1[i]);
				}
			}		
			var avgontimeper=0;
			var avgtime=0
			if (isNaN(sumontimenum*100/sumreordernum)){
				avgontimeper =0;
			}else{
				avgontimeper=sumontimenum*100/sumreordernum;
			}
			if (isNaN(sumalltime/sumreordernum)){
				avgtime =0;
			}else{
				avgtime=sumalltime/sumreordernum;
			}
			
	   	   $("#dataTableGroupByJCman").footerData("set",{"ontimeper":avgontimeper.toFixed(1)+"%"},false);
	   	   $("#dataTableGroupByJCman").footerData("set",{"avgtime":myRound((avgtime),0)},false);
	  },
				
	}); 
	Global.JqGrid.initJqGrid("dataTableGroupByJCDep2",{   			//集成部
		height:$(document).height()-$("#content-list").offset().top-1880,
		styleUI: 'Bootstrap',
		colModel : [
				{name: "namechi", index: "namechi", width: 84, label: "集成设计师", sortable: true, align: "left",hidden:true},
				{name: "jctdepartment2", index: "jctdepartment2", width: 100, label: "部门", sortable: true, align: "left",count:true},
				{name: "reordernum", index: "reordernum", width: 100, label: "接单套数", sortable: true, align: "right", sum: true},
				{name: "nomeasurecondition", index: "nomeasurecondition", width: 148, label: "不具备测量条件套数", sortable: true, align: "right", sum: true},
				{name: "unfinishnum", index: "unfinishnum", width: 140, label: "未完成测量套数", sortable: true, align: "right", sum: true},
				{name: "finishnum", index: "finishnum", width: 122, label: "已完成测量套数", sortable: true, align: "right", sum: true},
				{name: "ontimenum", index: "ontimenum", width: 145, label: "及时测量套数", sortable: true, align: "right", sum: true},
				{name: "ontimeper", index: "ontimeper", width: 90, label: "及时测量率", sortable: true, align: "right",formatter:"number",formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 1, suffix: "%"}},
				{name: "avgtime", index: "avgtime", width: 100, label: "平均测量天数", sortable: true, align: "right"},
				{name: "alltime", index: "alltime", width: 71, label: "alltime", sortable: true, align: "left", sum: true,hidden:true}
	       ],
	        loadonce: true,	  
	        gridComplete:function(){
			var sumontimenum=0;  
			var sumreordernum=0; 
			var sumalltime=0; 
			var ontimenum = Global.JqGrid.allToJson("dataTableGroupByJCDep2","ontimenum"); // 及时测量套数
			var reordernum = Global.JqGrid.allToJson("dataTableGroupByJCDep2","reordernum");// 接单套数
			var alltime = Global.JqGrid.allToJson("dataTableGroupByJCDep2","alltime");// alltime					

			arr = ontimenum.fieldJson.split(",");	
			arry = reordernum.fieldJson.split(",");
			arry1 = alltime.fieldJson.split(",");		
			for(var i = 0;i < arr.length; i++){			
				sumontimenum=sumontimenum+parseInt(arr[i]);					
				sumreordernum=sumreordernum+parseInt(arry[i]);
				if (!isNaN(parseInt(arry1[i]))){
					sumalltime=sumalltime+parseInt(arry1[i]);
				}
			}		
			var avgontimeper=0;
			var avgtime=0
			if (isNaN(sumontimenum*100/sumreordernum)){
				avgontimeper =0;
			}else{
				avgontimeper=sumontimenum*100/sumreordernum;
			}
			if (isNaN(sumalltime/sumreordernum)){
				avgtime =0;
			}else{
				avgtime=sumalltime/sumreordernum;
			}
			
	   	   $("#dataTableGroupByJCDep2").footerData("set",{"ontimeper":avgontimeper.toFixed(1)+"%"},false);
	   	   $("#dataTableGroupByJCDep2").footerData("set",{"avgtime":myRound((avgtime),0)},false);
	  },      
	});
	$("#content-list").hide();
	$("#content-list-groupByNoMx").hide(); 
	$("#content-list-groupByDep2").hide(); 
	$("#content-list-groupByJCman").hide(); 
	$("#content-list-groupByJCDep2").hide();
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
	if ($("#statistcsMethod").val() == '1') {		//  content-list-groupByNoMx <!-- 未申报明细 -->
		tableId = 'dataTable';						//  content-list-groupByPrjman   <!-- 项目经理 -->
		$("#content-list").show();					//  content-list-groupByDep2     <!-- 部门 -->
		$("#content-list-groupByNoMx").hide();  //  content-list-groupByJCman   <!-- 集成设计师 -->
		$("#content-list-groupByPrjman").hide();    //  content-list-groupByJCDep2     <!-- 集成部-->
		$("#content-list-groupByDep2").hide();
		$("#content-list-groupByJCman").hide();   
		$("#content-list-groupByJCDep2").hide();
		$("#btnview").show();
	} else if ($("#statistcsMethod").val() == '2'){
		tableId = 'groupBycheckman';
		$("#content-list").hide();
		$("#content-list-groupByNoMx").show();
		$("#content-list-groupByPrjman").hide();
		$("#content-list-groupByDep2").hide();
		$("#content-list-groupByJCman").hide();   
		$("#content-list-groupByJCDep2").hide();
		$("#btnview").hide();
	} else if ($("#statistcsMethod").val() == '3'){
		tableId = 'groupByPrjman';
		$("#content-list").hide();
		$("#content-list-groupByNoMx").hide();
		$("#content-list-groupByPrjman").show();
		$("#content-list-groupByDep2").hide();
		$("#content-list-groupByJCman").hide();   
		$("#content-list-groupByJCDep2").hide();
		$("#btnview").hide();
	} else if ($("#statistcsMethod").val() == '4'){
		tableId = 'groupByDep2';
		$("#content-list").hide();
		$("#content-list-groupByNoMx").hide();
		$("#content-list-groupByPrjman").hide();
		$("#content-list-groupByDep2").show();
		$("#content-list-groupByJCman").hide();   
		$("#content-list-groupByJCDep2").hide();
		$("#btnview").hide();
	}	else if ($("#statistcsMethod").val() == '5'){
		tableId = 'groupByPrjman';
		$("#content-list").hide();
		$("#content-list-groupByNoMx").hide();
		$("#content-list-groupByPrjman").hide();
		$("#content-list-groupByDep2").hide();
		$("#content-list-groupByJCman").show();   
		$("#content-list-groupByJCDep2").hide();
		$("#btnview").hide();
	} else if ($("#statistcsMethod").val() == '6'){
		tableId = 'groupByDep2';
		$("#content-list").hide();
		$("#content-list-groupByNoMx").hide();
		$("#content-list-groupByPrjman").hide();
		$("#content-list-groupByDep2").hide();
		$("#content-list-groupByJCman").hide();   
		$("#content-list-groupByJCDep2").show();
		$("#btnview").hide();
	}	
}
function statistcsMethodChange(){
	if($("#statistcsMethod").val() == "1"){
		$("#dealOverTime").parent().removeAttr("hidden");
	}else{
		$("#dealOverTime").parent().attr("hidden", true);
	}
	
	if($("#statistcsMethod").val() == "2"){
		$("#noApplyDetailDiv").removeAttr("hidden");
		$("#JCDesigner").parent().attr("hidden", true);
	}else{
		$("#noApplyDetailDiv").attr("hidden", true);
		$("#JCDesigner").parent().removeAttr("hidden");
	}
}
function dealOverTimeChange(){
	if($("#dealOverTime").val() == "T"){
		$("#dealOverTime").val("F");
	}else{
		$("#dealOverTime").val("T");
	}
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li> 
						    <label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" onchange="statistcsMethodChange()">
								<option value="3">按项目经理</option>
								<option value="1">申报明细</option>
								<option value="2">未申报明细</option>
								<option value="4">按工程部</option>
								<option value="5">按集成设计师</option>
								<option value="6">按集成部</option>
							</select>
						</li>	
						<li>
							<label >申请日期</label>
							<input type="text" id="appDateform" name="appDateform" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${prjJob.appDateform}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label >至</label>
							<input type="text" id="appDateto" name="appDateto" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${prjJob.appDateto}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>项目经理</label>
							<input type="text" id="ProjectMan" name="ProjectMan"  />
						</li>
						<li>
							<label>工程部</label>
							<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
						</li>
						<li>
							<label>处理人</label>
							<input type="text" id="JCDesigner" name="JCDesigner"  />
						</li>						
						<li>
							<label>集成部</label>
							<house:department2 id="Department2jc" dictCode="15"  depType=""  ></house:department2>
						</li>
						<li>
							<label>客户类型</label>
								<!--<house:custTypeMulit id="custType" selectedValue="1,3,4,5,8,10"></house:custTypeMulit>-->
								<house:custTypeMulit id="custType" selectedValue="${customer.custType}"></house:custTypeMulit>
						</li>	
						<li>
							<label>状态</label>
								<house:xtdmMulit id="status" dictCode="PRJJOBSTATUS"   selectedValue="2,3,4"></house:xtdmMulit>
						</li>	
						<li>
							<label>任务类型</label>
							<house:dict id="jobType" dictCode="" 
								sql="select rtrim(Code) Code, rtrim(Descr) Descr from tJobType where Expired='F'and itemtype1='JC'" 
								sqlValueKey="Code" sqlLableKey="Descr" value="01" >
							</house:dict>
						</li>	
						<div id="noApplyDetailDiv" hidden>
							<li>
								<label>开工日期晚于</label>
								<input type="text" id="confirmDateLate" name="confirmDateLate" class="i-date"  
									   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									   value="<fmt:formatDate value='${prjJob.confirmDateLate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>集成设计师</label>
								<input type="text" id="intDesigner" name="intDesigner"  />
							</li>
							<li>
								<label>橱柜设计师</label>
								<input type="text" id="cupDesigner" name="cupDesigner"  />
							</li>	
						</div>
						<li hidden>
							<label></label>
							<input type="checkbox" id="dealOverTime" name="dealOverTime" onclick="dealOverTimeChange()" value="F"/><span>只显示未及时处理</span>
						</li>
					</ul>		
					<ul class="ul-form">
					<li id="loadMore" >
						<button type="button"  class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
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
			<div id="content-list">               <!-- 申报明细 -->
				<table id= "dataTable"></table>  
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupByNoMx"> <!-- 未申报明细 -->
				<table id= "dataTableGroupByNoMx"></table> 
				<div id="dataTableGroupByNoMx"></div>
			</div>
			<div id="content-list-groupByPrjman">    <!-- 项目经理 -->
				<table id= "dataTableGroupByPrjman"></table> 
				<div id="dataTableGroupByPrjman"></div>
			</div>
			<div id="content-list-groupByDep2"> <!-- 工程部 -->
				<table id= "dataTableGroupByDep2"></table> 
				<div id="dataTableGroupByDep2"></div>
			</div>
			<div id="content-list-groupByJCman">    <!-- 集成设计师 -->
				<table id= "dataTableGroupByJCman"></table> 
				<div id="dataTableGroupByJCman"></div>
			</div>
			<div id="content-list-groupByJCDep2"> <!-- 集成部 -->
				<table id= "dataTableGroupByJCDep2"></table> 
				<div id="dataTableGroupByJCDep2"></div>
			</div>
		</div> 
	</div>
</body>
</html>


