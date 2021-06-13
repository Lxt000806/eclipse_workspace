<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
<title>结算报价</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
.checkColor {
	background-color: yellow;
}
.panel-body {
    padding: 0px 1px 1px;
    padding-top: 1px;
    padding-right: 1px;
    padding-bottom: 1px;
    padding-left: 1px;
}
.form-search .ul-form li {
    min-height: 34px;
    max-height: 40px;
    padding-left: 1px;
}
.form-search .ul-form li input {
    width: 130px;
}
.btn-panel{
    padding: 0px 0px 1px;
}
.panel {
    margin-bottom: 5px;
}
</style>
<script type="text/javascript">
var hasEdit=false;//是否修改
function add(isInsert){
	var posi = $('#dataTable').jqGrid('getGridParam', 'selrow')-1;
 	var ret = selectDataTableRow();
 	if(isInsert&&!ret){
	 	art.dialog({
			content: "请选择一条记录"
		});
		return;	
 	}
 	var baseItemType1Str="";
 	if(ret) baseItemType1Str+="&baseItemType1="+ret.baseitemtype1;
 	var url="${ctx}/admin/baseCheckItemPlan/goQuickSave?custCode=${map.code}&itemType1=JZ"+baseItemType1Str;
	Global.Dialog.showDialog("quickSave",{
		  title:"基础结算项目预算--快速新增",
		  url:url,
		  height:750,
		  width:1380,
		  resizable:false, 
		  returnFun: function(data){
			  var arr=$("#dataTable").jqGrid("getRowData");
			  var brr=[];
			  //默认提成
			  $.each(data,function(k,v){
				 hasEdit=true;
				 v.lastupdate=getNowFormatDate();
				 v.lastupdatedby='${map.lastUpdatedBy}';
				 v.actionlog="ADD";
				 v.expired="F";
				 if(isInsert&&v.worktype12==ret.worktype12){
					arr.splice(posi++,0,v);	
				 }else{
				 	brr.push(v);
				 }
			 });
	  		 var totalArr=arr.concat(brr);
	  		 $.each(totalArr, function(i,r){
					r['dispseq']=i;//设置顺序
			 });
	  		 var params=JSON.stringify(totalArr); 
	  		 $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");
		}
	});
}

function view(){
	var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
    if (rowId) {
	    Global.Dialog.showDialog("jcys_detailView",{
			  title:"基础结算项目预算--查看",
			  url:"${ctx}/admin/baseCheckItemPlan/goUpdate?itemType1=JZ&custCode=${map.code}&m_umState=V&rowId="+rowId,
		  	  height:400,
			  width:1000
		});
    }else{
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function edit(){
	var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
    if (rowId) {
	    Global.Dialog.showDialog("update",{
		   title:"基础结算项目预算--编辑",
		   url:"${ctx}/admin/baseCheckItemPlan/goUpdate?itemType1=JZ&custCode=${map.code}&rowId="+rowId,
		   height:400,
		   width:1000,
		   resizable:false, 
		   returnFun: function(data){
		   	   hasEdit=true;
			   if(data.fixareapk) {
			       $('#dataTable').setRowData(rowId,data);
			   	   var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
			  	   $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");
			   }
		  }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function del(){
	var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
    if (rowId) {
	   	art.dialog({
			 content:'确定删除该记录吗？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
			 	hasEdit=true;
		     	var ret = selectDataTableRow();
			    var rowNum=$("#dataTable").jqGrid('getGridParam','records');
			    $('#dataTable').delRowData(rowId);
			    var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
				$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");  
			    setTimeout(function(){moveToNext(rowId,rowNum);$("#delBtn").focus();},100);
			},
			cancel: function () {
				return true;
			}
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
	var canEdit=true;
	if('${map.status}'!='4' || '${map.m_umState}'=='V'){
		$(".onlyview").attr("disabled",true);
		canEdit=false;
	}
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/baseCheckItemPlan/goDetailJqGrid?custCode=${map.code}",
		height:$(document).height()-$("#content-list").offset().top-70,
		cellEdit:canEdit,
		cellsubmit:'clientArray',
		rowNum:10000,
		colModel : [
			{name: "fapk", index: "fapk", width: 0.1, label: "基础项目类型1", sortable: true, align: "left"},
			{name: "bidescr", index: "bidescr", width: 0.1, label: "工种", sortable: true, align: "left"},
			{name: "ordername", index: "ordername", width: 107, label: "排序", sortable: true, align: "left"},
			{name: "worktype1descr", index: "worktype1descr", width: 107, label: "工种类型1", sortable: true, align: "left",hidden:true},
			{name: "worktype12", index: "worktype12", width: 84, label: "工种类型12", sortable: true, align: "left",hidden:true},
			{name: "worktype12descr", index: "worktype12descr", width: 84, label: "工种类型12", sortable: true, align: "left",hidden:true},
			{name: "baseitemtype1", index: "baseitemtype1", width: 120, label: "基装类型1", sortable: true, align: "left",hidden:true},
			{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域", sortable: true, align: "left"},
			{name: "code", index: "basecheckitemcode", width: 70, label: "项目编号", sortable: true, align: "left"},
			{name: "descr", index: "basecheckitemdescr", width: 270, label: "项目名称", sortable: true, align: "left"},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "qty", index: "qty", width: 55, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "uom", index: "uom", width: 55, label: "单位", sortable: true, align: "left"},
			{name: "type", index: "type", width: 55, label: "项目类型", sortable: false, align: "left",hidden:true},
			{name: "typedescr", index: "typedescr", width: 85, label: "项目类型", sortable: true, align: "left",editable: true, edittype:"select", editoptions:{value:"0: ;1:常规套餐项目;2:个性化项目"}},
			{name: "offerpri", index: "offerpri", width: 72, label: "人工单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "material", index: "material", width: 72, label: "材料单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "totalofferpri", index: "totalofferpri", width: 72, label: "人工总价", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "totalmaterial", index: "totalmaterial", width: 72, label: "材料总价", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "totalsetofferpri", index: "totalsetofferpri", width: 83, label: "套餐内人工总价", sortable: true, align: "right", sum: true},
			{name: "totalsetmaterial", index: "totalsetmaterial", width: 80, label: "套餐内材料总价", sortable: true, align: "right", sum: true},
			{name: "totalindiofferpri", index: "totalindiofferpri", width: 83, label: "个性化人工总价", sortable: true, align: "right", sum: true},
			{name: "totalindimaterial", index: "totalindimaterial", width: 80, label: "个性化材料总价", sortable: true, align: "right", sum: true},
			{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "prjofferpri", index: "prjofferpri", width: 95, label: "经理人工单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "prjmaterial", index: "prjmaterial", width: 95, label: "经理材料单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "totalprjofferpri", index: "totalprjofferpri", width: 95, label: "经理人工总价", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "totalprjmaterial", index: "totalprjmaterial", width: 95, label: "经理材料总价", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "prjtotal", index: "prjtotal", width: 70, label: "经理总价", sortable: true, align: "right",sum: true,summaryType:'sum'},
			{name: "issubsidyitem", index: "issubsidyitem", width: 85, label: "发包补贴项目", sortable: false, align: "left",hidden:true},
			{name: "issubsidyitemdescr", index: "issubsidyitemdescr", width: 90, label: "发包补贴项目", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 267, label: "备注", sortable: true, align: "left",editable:true,edittype:'textarea'},
			{name: "lastupdate", index: "lastupdate", width: 141, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
			{name: "dispseq", index: "dispseq", width: 68, label: "顺序", sortable: true, align: "left", hidden: true},
            {name: "basereqpk", index: "basereqpk", width: 68, label: "basereqpk", sortable: true, align: "left", hidden: true},
            {name: "tempofferpri", index: "tempofferpri", width: 88, label: "人工单价", sortable: true, align: "right", hidden: true},
			{name: "tempmaterial", index: "tempmaterial", width: 86, label: "材料单价", sortable: true, align: "right", hidden: true},
			{name: "tempprjofferpri", index: "tempprjofferpri", width: 120, label: "项目经理人工单价", sortable: true, align: "right", hidden: true},
			{name: "tempprjmaterial", index: "tempprjmaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right", hidden: true},
       	],
        grouping : true , // 是否分组,默认为false
		groupingView : {
			groupField : [ 'ordername'], // 按照哪一列进行分组
			groupColumnShow : [ false], // 是否显示分组列
			groupText : ['<b>{0}({1})</b>'], // 表头显示的数据以及相应的数据量
			groupCollapse : false , // 加载数据时是否只显示分组的组信息
			groupDataSorted : false , // 分组中的数据是否排序
			groupOrder : [ 'asc'], // 分组后的排序
			groupSummary : [ true], // 是否显示汇总.如果为true需要在colModel中进行配置
			summaryType : 'max' , // 运算类型，可以为max,sum,avg</div>
			summaryTpl : '<b>Max: {0}</b>' ,
			showSummaryOnHide : true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
		},
		beforeSaveCell: function (id, name, val, iRow, iCol) {
    		var rowData = $("#dataTable").jqGrid("getRowData", id);
      		if ((name=="qty"||name=="offerpri"||name=="material"||name=="prjofferpri"||name=="prjmaterial")&& val<0){
   				art.dialog({
                 	content: "请输入大于等于0的数！"
               	});
   				return "0";
      		}
    	 },
         afterSaveCell:function(id,name,val,iRow,iCol){
              var rowData = $("#dataTable").jqGrid('getRowData',id);
              rowData.lastupdate=new Date();
              rowData.lastupdatedby="${USER_CONTEXT_KEY.czybh}";
              rowData.actionlog="EDIT"; 
              $("#dataTable").setRowData(id, rowData);
              hasEdit=true;
              var oldTotalofferpri=myRound($("#dataTable").getCol('totalofferpri',false,'sum'));
              var oldTotalmaterial=myRound($("#dataTable").getCol('totalmaterial',false,'sum'));
              var oldTotalprjofferpri=myRound($("#dataTable").getCol('totalprjofferpri',false,'sum'));
              var oldTotalprjmaterial=myRound($("#dataTable").getCol('totalprjmaterial',false,'sum'));
              var oldTotal=myRound($("#dataTable").getCol('total',false,'sum'));
              var oldPrjtotal=myRound($("#dataTable").getCol('prjtotal',false,'sum'));
              var oldTotalsetofferpri=myRound($("#dataTable").getCol('totalsetofferpri',false,'sum'));
              var oldTotalsetmaterial=myRound($("#dataTable").getCol('totalsetmaterial',false,'sum'));
              var oldTotalindiofferpri=myRound($("#dataTable").getCol('totalindiofferpri',false,'sum'));
              var oldTotalindimaterial=myRound($("#dataTable").getCol('totalindimaterial',false,'sum'));
              var summaryTotalofferpri=getGridFootSum(id,'totalofferpri');
              var summaryTotalmaterial=getGridFootSum(id,'totalmaterial');
              var summaryTotalprjofferpri=getGridFootSum(id,'totalprjofferpri');
              var summaryTotalprjmaterial=getGridFootSum(id,'totalprjmaterial');
              var summaryTotal=getGridFootSum(id,'total');
              var summaryPrjtotal=getGridFootSum(id,'prjtotal');
              var summaryTotalsetofferpri=getGridFootSum(id,'totalsetofferpri');
              var summaryTotalsetmaterial=getGridFootSum(id,'totalsetmaterial');
              var summaryTotalindiofferpri=getGridFootSum(id,'totalindiofferpri');
              var summaryTotalindimaterial=getGridFootSum(id,'totalindimaterial');
              var type=rowData.type;
              switch (name){
              	case 'qty':
               		$("#dataTable").setCell(id,'totalofferpri',myRound(val*rowData.offerpri));
               		$("#dataTable").setCell(id,'totalmaterial',myRound(val*rowData.material));
               		$("#dataTable").setCell(id,'totalprjofferpri',myRound(val*rowData.prjofferpri));
               		$("#dataTable").setCell(id,'totalprjmaterial',myRound(val*rowData.prjmaterial));
               		$("#dataTable").setCell(id,'total',parseFloat(myRound(val*rowData.offerpri))+parseFloat(myRound(val*rowData.material)));
               		$("#dataTable").setCell(id,'prjtotal',parseFloat(myRound(val*rowData.prjofferpri))+parseFloat(myRound(val*rowData.prjmaterial)));
               		if(type=="1"){
               			$("#dataTable").setCell(id,'totalsetofferpri',myRound(val*rowData.offerpri));
               			$("#dataTable").setCell(id,'totalsetmaterial',myRound(val*rowData.material));
               		}else if(type=="2"){
               			$("#dataTable").setCell(id,'totalindiofferpri',myRound(val*rowData.offerpri));
               			$("#dataTable").setCell(id,'totalindimaterial',myRound(val*rowData.material));
               		}
               	break;
               	case 'offerpri':
               		$("#dataTable").setCell(id,'totalofferpri',myRound(val*rowData.qty));
               		$("#dataTable").setCell(id,'total',parseFloat(myRound(val*rowData.qty))+parseFloat(myRound(rowData.material*rowData.qty)));
               		if(type=="1"){
               			$("#dataTable").setCell(id,'totalsetofferpri',myRound(val*rowData.qty));
               		}else if(type=="2"){
               			$("#dataTable").setCell(id,'totalindiofferpri',myRound(val*rowData.qty));
               		}
               	break;
               	case 'material':
               		$("#dataTable").setCell(id,'totalmaterial',myRound(val*rowData.qty));
               		$("#dataTable").setCell(id,'total',parseFloat(myRound(val*rowData.qty))+parseFloat(myRound(rowData.offerpri*rowData.qty)));
               		if(type=="1"){
               			$("#dataTable").setCell(id,'totalsetmaterial',myRound(val*rowData.qty));
               		}else if(type=="2"){
               			$("#dataTable").setCell(id,'totalindimaterial',myRound(val*rowData.qty));
               		}
               	break;
              	case 'prjofferpri':
               		$("#dataTable").setCell(id,'totalprjofferpri',myRound(val*rowData.qty));
               		$("#dataTable").setCell(id,'prjtotal',parseFloat(myRound(val*rowData.qty))+parseFloat(myRound(rowData.prjmaterial*rowData.qty)));
               	break;	
               	case 'prjmaterial':
               		$("#dataTable").setCell(id,'totalprjmaterial',myRound(val*rowData.qty));
               		$("#dataTable").setCell(id,'prjtotal',parseFloat(myRound(val*rowData.qty))+parseFloat(myRound(rowData.prjofferpri*rowData.qty)));
               	break;
               	case 'typedescr':
               		if(val==1){
               			$("#dataTable").setCell(id,'type',val);
               			$("#dataTable").setCell(id,'totalsetofferpri',myRound(rowData.qty*rowData.offerpri));
               			$("#dataTable").setCell(id,'totalsetmaterial',myRound(rowData.qty*rowData.material));
               			$("#dataTable").setCell(id,'totalindiofferpri',0);
               			$("#dataTable").setCell(id,'totalindimaterial',0);
               		}else if(val==2){
               			$("#dataTable").setCell(id,'type',val);
               			$("#dataTable").setCell(id,'totalindiofferpri',myRound(rowData.qty*rowData.offerpri));
               			$("#dataTable").setCell(id,'totalindimaterial',myRound(rowData.qty*rowData.material));
               			$("#dataTable").setCell(id,'totalsetofferpri',0);
               			$("#dataTable").setCell(id,'totalsetmaterial',0);
               		}else{
               			$("#dataTable").setCell(id,'totalindiofferpri',0);
               			$("#dataTable").setCell(id,'totalindimaterial',0);
               			$("#dataTable").setCell(id,'totalsetofferpri',0);
               			$("#dataTable").setCell(id,'totalsetmaterial',0);
               		}
               	break;
              }
              var newTotalofferpri=myRound($("#dataTable").getCol('totalofferpri',false,'sum'));
              var newTotalmaterial=myRound($("#dataTable").getCol('totalmaterial',false,'sum'));
              var newTotalprjofferpri=myRound($("#dataTable").getCol('totalprjofferpri',false,'sum'));
              var newTotalprjmaterial=myRound($("#dataTable").getCol('totalprjmaterial',false,'sum'));
              var newTotal=myRound($("#dataTable").getCol('total',false,'sum'));
              var newPrjtotal=myRound($("#dataTable").getCol('prjtotal',false,'sum'));
              var newTotalsetofferpri=myRound($("#dataTable").getCol('totalsetofferpri',false,'sum'));
              var newTotalsetmaterial=myRound($("#dataTable").getCol('totalsetmaterial',false,'sum'));
              var newTotalindiofferpri=myRound($("#dataTable").getCol('totalindiofferpri',false,'sum'));
              var newTotalindimaterial=myRound($("#dataTable").getCol('totalindimaterial',false,'sum'));
              setGridFootSum(id,'totalofferpri',summaryTotalofferpri+newTotalofferpri-oldTotalofferpri);
              setGridFootSum(id,'totalmaterial',summaryTotalmaterial+newTotalmaterial-oldTotalmaterial);
              setGridFootSum(id,'totalprjofferpri',summaryTotalprjofferpri+newTotalprjofferpri-oldTotalprjofferpri);
              setGridFootSum(id,'totalprjmaterial',summaryTotalprjmaterial+newTotalprjmaterial-oldTotalprjmaterial);
              setGridFootSum(id,'total',summaryTotal+newTotal-oldTotal);
              setGridFootSum(id,'prjtotal',summaryPrjtotal+newPrjtotal-oldPrjtotal);
              setGridFootSum(id,'totalsetofferpri',summaryTotalsetofferpri+newTotalsetofferpri-oldTotalsetofferpri);
              setGridFootSum(id,'totalsetmaterial',summaryTotalsetmaterial+newTotalsetmaterial-oldTotalsetmaterial);
              setGridFootSum(id,'totalindiofferpri',summaryTotalindiofferpri+newTotalindiofferpri-oldTotalindiofferpri);
              setGridFootSum(id,'totalindimaterial',summaryTotalindimaterial+newTotalindimaterial-oldTotalindimaterial);
              var totalofferpri=$("#dataTable").getCol('totalofferpri',false,'sum');   
              $("#dataTable").footerData('set', { "totalofferpri": totalofferpri });
              var totalmaterial=$("#dataTable").getCol('totalmaterial',false,'sum');   
              $("#dataTable").footerData('set', { "totalmaterial": totalmaterial });     
              var totalprjofferpri=$("#dataTable").getCol('totalprjofferpri',false,'sum');   
              $("#dataTable").footerData('set', { "totalprjofferpri": totalprjofferpri });     
              var totalprjmaterial=$("#dataTable").getCol('totalprjmaterial',false,'sum');   
              $("#dataTable").footerData('set', { "totalprjmaterial": totalprjmaterial });
              var total=$("#dataTable").getCol('total',false,'sum');   
              $("#dataTable").footerData('set', { "total": total });
              var prjtotal=$("#dataTable").getCol('prjtotal',false,'sum');   
              $("#dataTable").footerData('set', { "prjtotal": prjtotal });     
              var totalsetofferpri=$("#dataTable").getCol('totalsetofferpri',false,'sum');   
              $("#dataTable").footerData('set', { "totalsetofferpri": totalsetofferpri });
              var totalsetmaterial=$("#dataTable").getCol('totalsetmaterial',false,'sum');   
              $("#dataTable").footerData('set', { "totalsetmaterial": totalsetmaterial });
              var totalindiofferpri=$("#dataTable").getCol('totalindiofferpri',false,'sum');   
              $("#dataTable").footerData('set', { "totalindiofferpri": totalindiofferpri });
              var totalindimaterial=$("#dataTable").getCol('totalindimaterial',false,'sum');   
              $("#dataTable").footerData('set', { "totalindimaterial": totalindimaterial });    
              setTotalPri();
           },
           gridComplete:function(){
           		setTotalPri();
           },
           beforeSelectRow:function(id){
        	    setTimeout(function(){
           			relocate(id);
          	    },100);
           },
	});
});


function save(){
	var baseCheckItemPlanJson = Global.JqGrid.allToJson("dataTable");
	var param = {"baseCheckItemPlanJson": JSON.stringify(baseCheckItemPlanJson)};
	Global.Form.submit("dataForm","${ctx}/admin/baseCheckItemPlan/doSave",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000,
				beforeunload: function () {
				    closeWin();
				}
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content: ret.msg,
					width: 200
				});
			}
	});
}
function goTop(){
	var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
	var replaceId = parseInt(rowId)-1;
	if(rowId){
		if(rowId>1){
			var ret1 = $("#dataTable").jqGrid("getRowData", rowId);
			var ret2 = $("#dataTable").jqGrid("getRowData", replaceId);
			if(ret1.worktype12descr==ret2.worktype12descr){
				hasEdit=true;
				scrollToPosi(replaceId,"dataTable",ret1.worktype12descr,true);
				replace(rowId,replaceId,"dataTable");
				$("#dataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#dataTable").setCell(replaceId,"dispseq",ret2.dispseq);
			}
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
function goBottom(){
	var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
	var replaceId = parseInt(rowId)+1;
	var rowNum = $("#dataTable").jqGrid("getGridParam","records");
	if(rowId){
		if(rowId<rowNum){
			hasEdit=true;
			var ret1 = $("#dataTable").jqGrid("getRowData", rowId);
			var ret2 = $("#dataTable").jqGrid("getRowData", replaceId);
			if(ret1.worktype12descr==ret2.worktype12descr){
				scrollToPosi(replaceId,"dataTable",ret1.worktype12descr,true);
				replace(rowId,replaceId,"dataTable");
				$("#dataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#dataTable").setCell(replaceId,"dispseq",ret2.dispseq);
			}
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
function goPriceUpdateView(){
 	Global.Dialog.showDialog("itemPlan_priceUpdateView",{
		 title:"查看单价修改",
		 url:"${ctx}/admin/itemPlan/goItemPlan_priceUpdateView",
		 height:600,
		 width:1000
	});
}
function doCloseWin(){
 	if(hasEdit && '${map.m_umState}'!='V'){
 		art.dialog({
       		content: '是否保存被更改的数据？',
	        lock: true,
	        ok: function () {
	       		save();
	       		
	        },
		    cancel: function () {
	      		Global.Dialog.closeDialog(false);
		    }
	   	});
 	}else{
 		Global.Dialog.closeDialog(false);
 	}	
} 
function setTotalPri(){
	var footerData=$("#dataTable").footerData();
    $("#offerPri").val(footerData.totalofferpri);
    $("#material").val(footerData.totalmaterial);
    $("#prjOfferPri").val(footerData.totalprjofferpri);
    $("#prjMaterial").val(footerData.totalprjmaterial);
    $("#setOfferPri").val(footerData.totalsetofferpri);
    $("#setMaterial").val(footerData.totalsetmaterial);
    $("#indiOfferPri").val(footerData.totalindiofferpri);
    $("#indiMaterial").val(footerData.totalindimaterial);
}
function importFromCust(){
	art.dialog({
       		content: '将清空现有数据，是否确定从客户报价导入？',
	        lock: true,
	        ok: function () {
	       			$.ajax({
							url : "${ctx}/admin/baseCheckItemPlan/importFromCust",
							type : "post",
							data : {custCode:"${map.code}"},
							dataType : "json",
							cache : false,
							error : function(obj) {
								showAjaxHtml({
									"hidden" : false,
									"msg" : "保存数据出错~"
								});
							},
							success : function(obj) {
								if(obj.length<1){
									art.dialog({
					       				content: "该客户没有未导入的报价！",
					       			});
					     		  return;
								}
								hasEdit=true;
								$("#dataTable").jqGrid("clearGridData");
							    $.each(obj, function(i,r){
									r['dispseq']=i;//设置顺序
									r['lastupdatedby']='${map.lastUpdatedBy}';
								});
						  		var params=JSON.stringify(obj); 
						  		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");
							}
						});
	        },
		    cancel: function () {
		    }
	   	});
}
function updatePrice(){
	Global.Dialog.showDialog("itemChgUpdatePrice",{
		  title:"调整单价",
		  url:"${ctx}/admin/baseCheckItemPlan/goUpdatePrice",
		  height: 320,
		  width:400,
		  resizable:false, 
		  returnFun: function(data){
		  	  var ids=$("#dataTable").jqGrid("getDataIDs");
		  	  var offerpri=0,totalofferpri=0,material=0,totalmaterial=0,total=0;
		  	  var prjofferpri=0,totalprjofferpri=0,prjmaterial=0,totalprjmaterial=0,prjtotal=0;
		  	  var totalsetofferpri=0,totalsetmaterial=0,totalindiofferpri=0,totalindimaterial=0;
              $.each(ids,function(k,v){
	              var rowData = $("#dataTable").jqGrid('getRowData',v);
	              if($.trim(rowData.worktype12)!=data.workType12){
	            	  return;
	              }
				  if(data.offerpriPer){
				  	 offerpri=myRound(rowData.tempofferpri*data.offerpriPer,2); 
				  }else{
				  	 offerpri=rowData.offerpri;
				  }
				  totalofferpri=myRound(offerpri*rowData.qty);
				  
				  if(data.materialPer){ 
				  	 material=myRound(rowData.tempmaterial*data.materialPer,2); 
				  }else{
				  	 material=rowData.material;
				  } 
				  totalmaterial=myRound(material*rowData.qty);   
				  
				  total=myRound(rowData.qty*offerpri)+myRound(rowData.qty*material);
				  
				  if(data.prjofferpriPer){ 
				  	  prjofferpri=myRound(rowData.tempprjofferpri*data.prjofferpriPer,2); 
				  }else{
				  	  prjofferpri=rowData.prjofferpri;
				  }
			      totalprjofferpri=myRound(prjofferpri*rowData.qty);
				  
				  if(data.prjmaterialPer){
				  	  prjmaterial=myRound(rowData.tempprjmaterial*data.prjmaterialPer,2); 
				  }else{
				  	  prjmaterial=rowData.prjmaterial;
				  } 
				  totalprjmaterial=myRound(prjmaterial*rowData.qty);   
				  
				  var prjtotal=myRound(rowData.qty*prjofferpri)+myRound(rowData.qty*prjmaterial);
				  if($.trim(rowData.type)=="1"){
					    totalsetofferpri=totalofferpri; 
					    totalsetmaterial=totalmaterial;
					    totalindiofferpri=0;
	           		    totalindimaterial=0;
	           	  }else if($.trim(rowData.type)=="2"){
	           		    totalindiofferpri=totalofferpri;
	           		    totalindimaterial=totalmaterial;
	           		    totalsetofferpri=0; 
					    totalsetmaterial=0;	    
	           	  }else{
		           		totalsetofferpri=0; 
					    totalsetmaterial=0;
					    totalindiofferpri=0;
	           		    totalindimaterial=0;
	           	  }
	              $("#dataTable").setCell(v,'offerpri',offerpri);
	              $("#dataTable").setCell(v,'totalofferpri',totalofferpri);
	              $("#dataTable").setCell(v,'material',material);
	              $("#dataTable").setCell(v,'totalmaterial',totalmaterial); 
	              $("#dataTable").setCell(v,'total',total);
	              
	              $("#dataTable").setCell(v,'prjofferpri',prjofferpri);
	              $("#dataTable").setCell(v,'totalprjofferpri',totalprjofferpri);
	              $("#dataTable").setCell(v,'prjmaterial',prjmaterial);
	              $("#dataTable").setCell(v,'totalprjmaterial',totalprjmaterial);
	              $("#dataTable").setCell(v,'prjtotal',prjtotal);
	              
	              $("#dataTable").setCell(v,'totalsetofferpri',totalsetofferpri); 
	              $("#dataTable").setCell(v,'totalsetmaterial',totalsetmaterial); 
	              $("#dataTable").setCell(v,'totalindiofferpri',totalindiofferpri);
	              $("#dataTable").setCell(v,'totalindimaterial',totalindimaterial);
              });
              var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
		  	  $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");
		  }
	});
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system onlyview" onclick="save()">保存</button>
					<button type="button" class="btn btn-system " onclick="doCloseWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" /> 
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode" value="${map.code}"
							readonly="readonly" />
						</li>
						<li ><label>客户名称</label> <input type="text" id="descr" name="descr" value="${map.descr}" />
						</li>
						<li><label>楼盘</label> <input type="text" id="address" name="address" value="${map.address}"
							readonly="readonly" />
						</li>
						<li><label>设计师</label> <input type="text" id="designMan" name="designMan"
							value="${map.designManDescr}" readonly="readonly" />
						</li>
						<li><label>户型</label> <input type="text" id="layout" name="layout" value="${map.layoutDescr}"
							readonly="readonly" />
						</li>
						<li><label>面积</label> <input type="text" id="area" name="area" value="${map.area}"
							  readonly="readonly" /> 
						</li>
						<li><label>人工总价</label> <input type="text" id="offerPri" name="offerPri" 
							readonly="readonly" />
						</li>
						<li><label>材料总价</label> <input type="text" id="material" name="material" 
							readonly="readonly" />
						</li>
						<li><label>项目经理人工总价</label> <input type="text" id="prjOfferPri" name="prjOfferPri"
							readonly="readonly" />
						</li>
						
						<li><label>项目经理材料总价</label> <input type="text" id="prjMaterial" name="prjMaterial" 
							readonly="readonly" />
						</li>
						 <li><label>套餐内人工总价</label> <input type="text" id="setOfferPri" name="itemOfferPri" 
							readonly="readonly" />
						</li>
						<li><label>套餐内材料总价</label> <input type="text" id="setMaterial" name="itemMaterial" 
							readonly="readonly" />
						</li>
						<li><label>个性化人工总价</label> <input type="text" id="indiOfferPri" name="indiOfferPri" 
							readonly="readonly" />
						</li>
						<li><label>个性化材料总价</label> <input type="text" id="indiMaterial" name="indiMaterial" 
							readonly="readonly" />
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button id="addBtn"  type="button" class="btn btn-system onlyview" onclick="add()" >快速新增</button>
				<button id="insertBtn" type="button" class="btn btn-system onlyview" onclick="add(true)">插入</button>
				<button id="delBtn" type="button" class="btn btn-system onlyview" onclick="del()">删除</button>
				<button type="button" class="btn btn-system " onclick="view()">查看</button>
				<button id="editBtn" type="button" class="btn btn-system onlyview" onclick="edit()">编辑</button>
				<button type="button" class="btn btn-system" onclick="doExcelNow('结算报价','dataTable', 'dataForm')">导出excel</button>
				<button type="button" class="btn btn-system onlyview" onclick="goTop()">向上</button>
				<button type="button" class="btn btn-system onlyview" onclick="goBottom()">向下</button>
				<button id="importBtn" type="button" class="btn btn-system onlyview" onclick="importFromCust()">从客户报价导入</button>
				<button id="updatePriceBtn" type="button" class="btn btn-system onlyview" onclick="updatePrice()">批量调整单价</button>
				
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>


