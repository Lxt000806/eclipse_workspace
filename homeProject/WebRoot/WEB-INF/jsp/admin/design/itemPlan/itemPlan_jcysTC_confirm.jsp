<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>ItemPlan基础预算套餐审核</title>
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
var data_iRow;
var data_iCol;
var hasEdit=false;//是否修改
//获取对应区域名称总价
function getSumByFixarea(rowData,descr){
	var sum=0;
	$.each(rowData,function(k,v){
		if(v.fixareadescr==descr) sum+=parseFloat(v.lineamount);
	});
	return sum;
}
//计算所有费用并刷新
function loadFee(rowData,baseFeeComp){
	if(!baseFeeComp)
 	var baseFeeComp=0;//综合费
 	var amount=parseFloat($("#dataTable").getCol('lineamount',false,'sum'));
	var baseFeeDirct=amount;//直接费
 	var setMinus=0;//主套餐内减项
 	var setAdd=0;//套餐外增项
 	var longFee=0;//远程费
 	var mainSetFee=0;
 	var dNoManageFee=0 ;//不计管理费
  	$.each(rowData,function(k,v){
		if(v.category==2) mainSetFee+=parseFloat(v.lineamount);
		else if(v.category==4) setMinus+=parseFloat(v.lineamount);
		else setAdd+=parseFloat(v.lineamount);
		if('${customer.longFeeCode}'.indexOf(v.baseitemcode)!=-1) 
			longFee+=parseFloat(v.lineamount);	
		if (v.iscalmangefee=="0"){
			dNoManageFee = dNoManageFee + parseFloat(v.lineamount);
		}					
	});
  	if("${custType.setAddPer}" == 0){
		dNoManageFee = 0;
	}
	var manageFeeBase=myRound((baseFeeDirct*${customer.baseFeeDirctPer}+baseFeeComp*${customer.baseFeeCompPer}
					+${customer.area}*${customer.areaPer}+mainSetFee*${customer.mainSetFeePer}+setMinus*${customer.setMinusPer}
					+setAdd*${customer.setAddPer}+longFee*${customer.longFeePer}-dNoManageFee)*${customer.manageFeeBasePer});//基础管理费
	var manageFee=myRound(manageFeeBase+${customer.manageFeeMain}*${customer.containMain}+
	${customer.manageFeeInt}*${customer.containInt}
				+${customer.manageFeeServ}*${customer.containMainServ}
				+${customer.manageFeeSoft}*${customer.containSoft}+${customer.manageFeeCup}*${customer.containCup});//管理费
	var baseFee=amount+manageFee;//基础总金额
	$("#baseFeeComp").val(baseFeeComp);
	$("#baseFeeDirct").val(baseFeeDirct);
	$("#manageFeeBase").val(manageFeeBase);
	$("#manageFee").val(manageFee);
	$("#baseFee").val(baseFee);
	$("#setMinus").val(setMinus);
	$("#setAdd").val(setAdd);
	$("#longFee").val(longFee);
	$("#mainSetFee").val(mainSetFee);
	$("#noManageFee").val(myRound(dNoManageFee));
}
function add(isInsert){
	if ("${custType.mustImportTemp}"=="1"&& (!$("#isInitSign").is(':checked'))){
		var recordData=$("#dataTable").jqGrid('getGridParam','records');
		if($("#baseTempNo").val()==""||recordData==0){
			art.dialog({
				content: "请先从模板导入预算"
			});
			return;
		}
	}
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
	var url="${ctx}/admin/itemPlan/goJzysQuickSave?custCode=${customer.code}&itemType1=JZ&isOutSet=2&custTypeType=2&m_umState=C&custType=${customer.custType}&canUseComBaseItem=${custType.canUseComBaseItem}"+baseItemType1Str;
	if(ret) url="${ctx}/admin/itemPlan/goJzysQuickSave?custCode=${customer.code}&isOutSet=2&itemType1=JZ&custTypeType=2&m_umState=C&custType=${customer.custType}&canUseComBaseItem=${custType.canUseComBaseItem}&fixAreaPk="+ret.fixareapk+baseItemType1Str;
	url+="&mustImportTemp"+"${custType.mustImportTemp}";
	Global.Dialog.showDialog("quickSave",{
		 title:"基础预算--快速新增",
		 url:url,
		 height:750,
		 width:1360,
		 resizable:false, 
		 returnFun: function(data){	
			 var arr=$("#dataTable").jqGrid("getRowData");
			 var brr=[];
			 //默认提成
			 $.each(data,function(k,v){
				 hasEdit=true;
				 v.lastupdate=getNowFormatDate();
				 v.lastupdatedby='${customer.lastUpdatedBy}';
				 v.actionlog="ADD";
				 v.ordername=v.fixareadescr;
				 v.tempunitprice=v.unitprice;
				 v.tempmaterial=v.material;
			 	 if(v.isoutset=="0"){
					v.isoutsetdescr="否";
				 }else{
				 	v.isoutset="1";
					v.isoutsetdescr="是";
				 }
				 if(v.ismainitem=="0"){
					v.ismainitemdescr="否";
				 }else{
				 	v.ismainitem="1";
					v.ismainitemdescr="是";
				 };
				 if(isInsert&&v.fixareapk==ret.fixareapk){
					arr.splice(posi++,0,v);	
				 }else{
				  brr.push(v);
				 }
				 if(v.fixareadescr=="全房"||v.fixareadescr=="水电项目"||v.fixareadescr=="土建项目"||v.fixareadescr=="安装项目"||v.fixareadescr=="综合项目") v.isgroup=1;
				 else v.isgroup=0;
			 })	
			 var params=JSON.stringify(arr.concat(brr));
			 $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk,isgroup'},page:1}).trigger("reloadGrid");  		 
		 }
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
    	Global.Dialog.showDialog("itemPlanUpdate",{
		   title:"修改ItemPlan",
		   url:"${ctx}/admin/itemPlan/goUpdate?id="+ret.PK,
		   height:600,
		   width:1000,
		   returnFun: goto_query
		});
    }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function copy(){
	if ("${custType.mustImportTemp}"=="1"&& (!$("#isInitSign").is(':checked'))){
		art.dialog({
			content: "请从模板导入预算"
		});
		return;
	}
	Global.Dialog.showDialog("itemPlanCopy",{
		title:"搜寻--客户编号",
		url:"${ctx}/admin/itemPlan/goCopy?itemType1=JZ&custCode=${customer.code}&isService=0&custType=${customer.custType}",
		height: 600,
		width:1000,
		returnFun: function(data){
		  	if(data.length){
			  	hasEdit=true;
			  	var params=JSON.stringify(data);
			  	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk,isgroup'},page:1}).trigger("reloadGrid");  
		  	}		
		}
	});
}

function view(){
	var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
    if (rowId) {
	    Global.Dialog.showDialog("jcys_detailView",{
			  title:"基础预算--查看",
			  url:"${ctx}/admin/itemPlan/goItemPlan_jcys_detailView?itemType1=JZ&custCode=${customer.code}&m_umState=C&rowId="+rowId,
			  height:600,
			  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function edit(){
	var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
	var rowData = $("#dataTable").jqGrid('getRowData',rowId);
    if(rowData.isoutset==0) {
    	art.dialog({
			content: "只能对套餐外材料进行编辑"
		});
     	return ;
  	}          
    if (rowId) {
	    Global.Dialog.showDialog("jcys_detailViewEdit",{
			  title:"基础预算--编辑",
			  url:"${ctx}/admin/itemPlan/goItemPlan_jcys_detailViewEdit?itemType1=JZ&custCode=${customer.code}&custTypeType=2&m_umState=C&custType=${customer.custType}&canUseComBaseItem=${custType.canUseComBaseItem}&rowId="+rowId,
			  height:600,
			  width:1000,
			  resizable:false, 
			  returnFun: function(data){
				  if(data.fixareapk) {
				  	hasEdit=true;
				  	data.lastupdate=getNowFormatDate();
				    data.lastupdatedby='${customer.lastUpdatedBy}';
				    data.actionlog='EDIT';
				    data.ordername=data.fixareadescr;
				    $('#dataTable').setRowData(rowId,data);
				   	var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
				  	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk,isgroup'},page:1}).trigger("reloadGrid");  
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
    	var ret = selectDataTableRow();
    	if(ret.isrequired=="1" ){
    		art.dialog({
    			content:"必选项不能删除！"
    		});
    		return ;
    	}
    	if(ret.ismainitem=="0"&&ret.baseIitemsetno!=""){
    		art.dialog({
    			content:"套餐包套餐内项目不能删除！"
    		});
    		return ;
    	}
    	art.dialog({
			 content:'确定删除该记录吗？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
			 	hasEdit=true;
		     	//var ret = selectDataTableRow();
			    var rowNum=$("#dataTable").jqGrid('getGridParam','records');
			    $('#dataTable').delRowData(rowId);
			    var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
				$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk,isgroup'},page:1}).trigger("reloadGrid");  
			    setTimeout(function(){moveToNext(rowId,rowNum);$("#delBtn").focus();},100);
			},
			cancel: function () {
				return true;
			}
		});
    }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function updatePrice(){
	art.dialog({
		 content:'该操作只会对套餐外材料进行单价调整,是否继续？',
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
		 Global.Dialog.showDialog("itemChgUpdatePrice",{
			 title:"调整单价",
			 url:"${ctx}/admin/itemPlan/goUpdatePrice?m_umState=C",
			 height: 300,
		  	 width:400,
			 resizable:false, 
			 returnFun: function(data){
			  	var ids=$("#dataTable").jqGrid("getDataIDs");
			  	var fixareapk;
			  	var sum;
			  	var count=0;
	            $.each(ids,function(k,v){
	                var rowData = $("#dataTable").jqGrid('getRowData',v);
	                //套餐外材料处理
	                if(rowData.isoutset==1) {
		                if(!fixareapk){
			               	//初始分组
			                 fixareapk=rowData.fixareapk;
			                 sum=0;
		            	}
		               	if(data.unitPrice&&rowData.allowpricerise=="1"){
					  		var unitprice=myRound(rowData.tempunitprice*data.unitPrice);
						}else{
						  	 var unitprice=rowData.unitprice;
						}
						if(data.material&&rowData.allowpricerise=="1"){ 	
						  	 var material=myRound(rowData.tempmaterial*data.material);
					    }else{
						  	  var material=rowData.material;
					    }
			            var lineamount=myRound(rowData.qty*unitprice)+myRound(rowData.qty*material);
			            $("#dataTable").setCell(v,'unitprice',unitprice);
			            $("#dataTable").setCell(v,'sumunitprice',myRound(rowData.qty*unitprice));
			            $("#dataTable").setCell(v,'material',material);
			            $("#dataTable").setCell(v,'summaterial',myRound(rowData.qty*material));
			            $("#dataTable").setCell(v,'lineamount',lineamount);
		                sum+=lineamount;
		                if(fixareapk!=rowData.fixareapk){
			                 //新的分组
			                 setGridFootSum(v-1,'lineamount',sum-lineamount);
			                 fixareapk=rowData.fixareapk;
			                 sum=lineamount;
		               	}
		                if(unitprice==rowData.tempunitprice&&material==rowData.tempmaterial){
		       		 		$("#dataTable").setCell(v,'count',0);
		              	}else{
		             	 	$("#dataTable").setCell(v,'count',1);
		             		 count++;
		              	} 
                	}
                })
	            $("#count").text(count);
	            isShowModifyLable();
	            var rowNo=$("#dataTable").jqGrid('getGridParam','records');
	            //最后的分组赋值
	            setGridFootSum(rowNo,'lineamount',sum);
	            var sumunitprice=myRound($("#dataTable").getCol('sumunitprice',false,'sum'));  
	            var summaterial=myRound($("#dataTable").getCol('summaterial',false,'sum'));   
	            var lineamount=myRound($("#dataTable").getCol('lineamount',false,'sum'));  
	            $("#dataTable").footerData('set', { "sumunitprice": sumunitprice }); 
	            $("#dataTable").footerData('set', { "summaterial": summaterial});    
	            $("#dataTable").footerData('set', { "lineamount": lineamount });    
			    var rowData = $("#dataTable").jqGrid('getRowData');
			  	loadFee(rowData);
			}
		});
	},
	cancel: function () {
		return true;
	}
	});
}
function clearDetail(sContent,stitle,sMustImportTemp,sType){
	if (!sContent){
		sContent= '是否清除【${customer.code}-${customer.address}】的基础预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
	}
	art.dialog({
		 content:sContent,
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
		 	hasEdit=true;
		 	var ischecked=$("#clearDetail").is(':checked');
		 	$("#baseTempNo").val("");
		 	$("#dataTable").jqGrid('clearGridData');
		 	if(ischecked){
	 			var datas=$("#page_form").jsonForm();
				datas.isClearFixArea=1;
				$.ajax({
					url:'${ctx}/admin/itemPlan/doClear',
					type:"post",
		            dataType:"json",
		            async:false,
		            data:datas,
					cache: false,
				    success: function(obj){
				  		if(obj.rs){
				    		art.dialog({
								content: obj.msg,
								time: 1000,	
							});
							if (sMustImportTemp){
							 	goImportTab(stitle,sMustImportTemp);
							} 
							if(sType=="3"){
								updateInitSign("0");
							} 	
				    	}else{
							$("#_form_token_uniq_id").val(obj.token.token);
							art.dialog({
								content: obj.msg,
								width: 200
							});
				    	}  				
					}
				});
		 	}else{
		 		if (sMustImportTemp){
					goImportTab(stitle,sMustImportTemp);
				} 
		 	}	
		},
	    cancel: function () {
			if(sType=="3"){
				$('#isInitSign').val('1');
				$("#isInitSign").prop('checked',true);
			} 	 
			
	    }
		
	});
}
/**初始化表格*/
$(function(){
	var  isCellEdit=true;
	if ('${customer.status}'=='4'||'${customer.status}'=='5'  || "${contractStatus}" == "2" || "${contractStatus}" == "3" || "${contractStatus}" == "4"){
		$("#saveBtn").attr("disabled","disabled");
		$("#addBtn").attr("disabled","disabled");
		$("#insertBtn").attr("disabled","disabled");
		$("#delBtn").attr("disabled","disabled");
		$("#editBtn").attr("disabled","disabled");
		$("#clearBtn").attr("disabled","disabled");
		$("#importBtn").attr("disabled","disabled");
		$("#copy").attr("disabled","disabled");
		$("#updatePriceBtn").attr("disabled","disabled");
		$("#baseBatchTempBtn").attr("disabled","disabled");
		$("#baseFromTempBtn").attr("disabled","disabled");
		$("#regenLoad").attr("disabled","disabled");
		$("#isInitSign").attr("disabled","disabled");
		$("#copyOutItem").attr("disabled","disabled");
		isCellEdit=false;
	}
	if("${customer.isInitSign}"=="1"){
		$("#isInitSign").prop("checked", true);
	}
	$("#status_NAME").attr("disabled","disabled");
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemPlan/goBaseItemPlanJqGrid?custCode=${customer.code}",
		height:$(document).height()-$("#content-list").offset().top-70,
		cellEdit:isCellEdit,
		cellsubmit:'clientArray',
		rowNum:10000,
		colModel : [
			{name: "ordername", index: "ordername", width: 107, label: "排序名称", sortable: true, align: "left"},
			{name: "ischeck", index: "ischeck", width: 84, label: "审核标识", sortable: false, align: "left",hidden:true},
			{name: "checkBtn", index: "checkBtn", width: 84, label: "审核标识", sortable: false, align: "center", formatter:checkBtn,np:true},
			{name: "category", index: "category", width: 107, label: "项目分类", sortable: true, align: "left",hidden:true},
			{name: "baseitemdescr", index: "baseitemdescr", width: 273, label: "基础项目名称", sortable: true, align: "left"},
			{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left",hidden:true},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "categorydescr", index: "categorydescr", width: 88, label: "项目类型", sortable: true, align: "left"},
			{name: "basealgorithm", index: "basealgorithm", width: 70, label: "算法", sortable:false, align: "left", hidden: true},
			{name: "basealgorithmdescr", index: "basealgorithmdescr", width: 80, label: "算法", sortable: false, align: "left",editable:true,
				edittype:'select',
				editoptions:{ 
		  			dataUrl: '${ctx}/admin/baseItem/getBaseAlgorithm',
					postData: function(){
						var ret = selectDataTableRow("dataTable");
						if(ret){
							return {
								code: ret.baseitemcode
							};
						}
						return {code: ""};
					},
					buildSelect: function(e){
						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
						for(var i = 0; lists && i < lists.length; i++){
							html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
						}
						return "<select style=\"font-family:宋体;font-size:12px\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e);
	  					}
		  			}, 
		  		]}
	 		},
			{name: "qty", index: "qty", width: 79, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 83, label: "人工单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "tempunitprice", index: "unitprice", width: 83, label: "临时人工单价", sortable: true, align: "left", hidden: true},
			{name: "tempmaterial", index: "material", width: 80, label: "临时材料单价", sortable: true, align: "left", hidden: true},
			{name: "sumunitprice", index: "sumunitprice", width: 80, label: "人工总价", sortable: true, align: "right", sum: true},
			{name: "summaterial", index: "summaterial", width: 80, label: "材料总价", sortable: true, align: "right", sum: true},
			{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "isoutset", index: "isoutset", width: 40, label: "套餐外材料", sortable: true, align: "left", hidden: true},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 40, label: "套外", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 267, label: "备注", sortable: true, align: "left",editable:true,edittype:'textarea'},
			{name: "autoqty", index: "autoqty", width: 80, label: "系统算量", sortable:false, align: "right"},
		    {name: "giftdescr", index: "giftdescr", width: 80, label: "赠送项目", sortable: true, align: "left"},
			{name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left", hidden: true},
	        {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},
			{name: "baseitemcode", index: "baseitemcode", width: 100, label: "基础项目编号", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域名称", sortable: true, align: "left"},
			{name: "isrequired", index: "isrequired", width: 68, label: "必选项", sortable: true, align: "left", hidden: true},
       		{name: "canreplace", index: "canreplace", width: 68, label: "可替换", sortable: true, align: "left", hidden: true},
       		{name: "canmodiqty", index: "canmodiqty", width: 78, label: "数量可修改", sortable: true, align: "left", hidden: true},
			{name: "isrequireddescr", index: "isrequireddescr", width: 68, label: "必选项", sortable: true, align: "left"},
       		{name: "canreplacedescr", index: "canreplacedescr", width: 68, label: "可替换", sortable: true, align: "left"},
       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
       		{name: "baseitemsetno", index: "baseitemsetno", width: 68, label: "套餐包", sortable: true, align: "left"},
			{name: "isgift", index: "isgift", width: 0.5, label: "是否赠送项目", sortable: true, align: "left"},
			{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
       		{name: "tempdtpk", index: "tempdtpk", width: 0.5, label: "模板pk", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 141, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
			{name: "cost", index: "cost", width: 90, label: "市场价", sortable: true, align: "right", hidden: true},
			{name: "count", index: "count", width: 90, label: "单价修改数", sortable: true, align: "left", hidden: true},
			{name: "qtyupdatecount", index: "qtyupdatecount", width: 90, label: "数量修改数", sortable: true, align: "left", hidden: true},
			{name: "isgroup", index: "isgroup", width: 90, label: "分组", sortable: true, align: "left",formatter:getIsGroup,hidden:true},
			{name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left", hidden: true},
        	{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
        	{name: "prjtype", index: "prjtype", width: 68, label: "施工类型", sortable: true, align: "left", hidden: true},
       	    {name: "preplanareapk", index: "preplanareapk", width: 68, label: "空间pk", sortable: true, align: "left", hidden: true},
       	   	{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left", hidden: true},
       	 
        ],
        grouping : true , // 是否分组,默认为false
		groupingView : {
			groupField : [ 'ordername' ], // 按照哪一列进行分组
			groupColumnShow : [ false ], // 是否显示分组列
			groupText : ['<b>{0}({1})</b>' ], // 表头显示的数据以及相应的数据量
			groupCollapse : false , // 加载数据时是否只显示分组的组信息
			groupDataSorted : true , // 分组中的数据是否排序
			groupOrder : [ 'asc' ], // 分组后的排序
			groupSummary : [ true ], // 是否显示汇总.如果为true需要在colModel中进行配置
			summaryType : 'max' , // 运算类型，可以为max,sum,avg</div>
			summaryTpl : '<b>Max: {0}</b>' ,
			showSummaryOnHide : true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
		},
		gridComplete:function(){
		   var rowData = $("#dataTable").jqGrid('getRowData');
		   loadFee(rowData);	
		   if($("#dataTable").jqGrid('getGridParam','records')==0){
				$("#copy").show();
				$("#count").text(0);
		   } 
		   else{
				$("#copy").hide();
				$.each(rowData,function(k,v){
					if(v.qty==v.autoqty&&v.qtyupdatecount&&v.basealgorithm&&v.baseitemsetno==""){ 
	               		$("#dataTable").setCell(k+1,'qtyupdatecount');
	               	} 
	               	else if(v.qty!=v.autoqty&&!v.qtyupdatecount&&v.basealgorithm&&v.baseitemsetno==""){ 
	                	$("#dataTable").setCell(k+1,'qtyupdatecount',1);
	               	} 	
					//新增时固定价不允许修改，非固定价允许修改
					/* if(v.canmodiqty=='0'){
						$("#dataTable").jqGrid('setCell', k+1, 'qty', '', 'not-editable-cell');
					} */
					if(v.canmodiqty=='0'||v.isoutset=='0'||v.tempdtpk>0){
						 $("#dataTable").jqGrid('setCell', k+1, 'basealgorithmdescr', '', 'not-editable-cell');
					} 
				 	//审核标记黄色
	  				if(v.ischeck=="1") $("#dataTable tbody:first tr#"+(k+1)).find("td").addClass("checkColor");
				}) 
				var rowData2 = $("#dataTable").jqGrid('getRowData');
				loadOperate(rowData2);	
			}
			
		},
		beforeSaveCell: function (id, name, val, iRow, iCol) {
    		var rowData = $("#dataTable").jqGrid("getRowData", id);
      		if ((name=="qty"||name=="unitprice"||name=="material") && rowData.category!="4"&& rowData.category!="2"   && val<0){
   				art.dialog({
                 	content: "套餐类客户只有基础项目是套餐类减项和主套餐项目的,才允许预算为负数！"
               	});
   				return "0";
      		}
    	},
        afterSaveCell:function(id,name,val,iRow,iCol){
            hasEdit=true;
            var rowData = $("#dataTable").jqGrid('getRowData',id);
            var oldLineamount=myRound($("#dataTable").getCol('lineamount',false,'sum'));
            var summary= getGridFootSum(id,'lineamount');
             var nums=$("#count").text(), qtyUpdateNums=$("#qtyUpdateCount").text();
            switch (name){
               	case 'qty':
	               	$("#dataTable").setCell(id,'lineamount',myRound(val*rowData.unitprice)+myRound(val*rowData.material));
	               	$("#dataTable").setCell(id,'sumunitprice',myRound(val*rowData.unitprice));
	               	$("#dataTable").setCell(id,'summaterial',myRound(val*rowData.material));
	               	if(val==rowData.autoqty&&rowData.qtyupdatecount&&rowData.basealgorithm&&rowData.baseitemsetno==""){
			               	$("#dataTable").setCell(id,'qtyupdatecount');
			               	$("#qtyUpdateCount").text(--qtyUpdateNums);
	               	} 
	               	else if(val!=rowData.autoqty&&!rowData.qtyupdatecount&&rowData.basealgorithm&&rowData.baseitemsetno==""){
	                	$("#dataTable").setCell(id,'qtyupdatecount',1);
	                	$("#qtyUpdateCount").text(++qtyUpdateNums);
	               	
	               	} 
	               	isShowModifyLable();
	               	break;
               	case 'unitprice':
	               	$("#dataTable").setCell(id,'lineamount',myRound(val*rowData.qty)+myRound(rowData.qty*rowData.material));
	               	$("#dataTable").setCell(id,'sumunitprice',myRound(val*rowData.qty));
	               	$("#dataTable").setCell(id,'summaterial',myRound(rowData.qty*rowData.material));
	               	if(val==rowData.tempunitprice&&rowData.material==rowData.tempmaterial&&rowData.count){
	               		$("#dataTable").setCell(id,'count');
	               		$("#count").text(--nums);
	               	} 
	               	else if((val!=rowData.tempunitprice||rowData.material!=rowData.tempmaterial)&&!rowData.count){
	                	$("#dataTable").setCell(id,'count',1);
	                	$("#count").text(++nums);
	               	
	               	} 
	               	isShowModifyLable();	
	               	break;
               	case 'material':
	               	$("#dataTable").setCell(id,'lineamount',myRound(rowData.unitprice*rowData.qty)+myRound(rowData.qty*val));
	               	$("#dataTable").setCell(id,'sumunitprice',myRound(rowData.unitprice*rowData.qty));
	               	$("#dataTable").setCell(id,'summaterial',myRound(rowData.qty*val));
	               	if(val==rowData.tempmaterial&&rowData.unitprice==rowData.tempunitprice&&rowData.count){
	               		$("#dataTable").setCell(id,'count');
	               		$("#count").text(--nums);
	               	} 
	               	else if((val!=rowData.tempmaterial||rowData.unitprice!=rowData.tempunitprice)&&!rowData.count){
	                	$("#dataTable").setCell(id,'count',1);
	                	$("#count").text(++nums);
	               	
	               	} 
	               	isShowModifyLable();
	               	break;
            }
            var lineamount=myRound($("#dataTable").getCol('lineamount',false,'sum'));
            var sumunitprice=$("#dataTable").getCol('sumunitprice',false,'sum');   
            var summaterial=$("#dataTable").getCol('summaterial',false,'sum');      
            $("#dataTable").footerData('set', { "lineamount": lineamount });    
  			$("#dataTable").footerData('set', { "sumunitprice":sumunitprice });    
  			$("#dataTable").footerData('set', { "summaterial": summaterial });
  			setGridFootSum(id,'lineamount',summary+lineamount-oldLineamount);
  			loadFee($("#dataTable").jqGrid('getRowData'));
  			$("#dataTable").setCell(id,'lastupdatedby','${customer.lastUpdatedBy}');
            $("#dataTable").setCell(id,'actionlog','EDIT');
            $("#dataTable").setCell(id,'lastupdate',getNowFormatDate());
    			 /*var newData=$("#dataTable").jqGrid('getRowData');
    			 $("#dataTable").jqGrid('clearGridData');  //清空表格
				 $("#dataTable").jqGrid('setGridParam',{  // 重新加载数据
     			 datatype:'local',
     			 data : newData,   //  newdata 是符合格式要求的需要重新加载的数据 
    			 page:1
				}).trigger("reloadGrid");*/
                 
            },
            beforeSelectRow:function(id){
         	 	setTimeout(function(){
	           		relocate(id);
	         	 },100)
          	},
			beforeEditCell:function(rowid,cellname,value,iRow,iCol){
            data_iRow=iRow;
            data_iCol=iCol;
       	}
		
		});
			Global.JqGrid.initJqGrid("containBaseItemDataTable",{
			url:"${ctx}/admin/containBaseItem/goJqGrid?custType=${customer.custType}",
			rowNum:1000,
			colModel : [
				{name: "baseitemcode", index: "baseitemcode", width: 107, label: "基础项目编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 107, label: "基础项目名称", sortable: true, align: "left"},
            ]
		});
});

function save(){
	var datas=$("#page_form").jsonForm();
	if ($("#baseTempNo").val()!=""){
		var sBaseTempNo=$("#baseTempNo").val().trim();
		sBaseTempNo=sBaseTempNo.split("|");
		datas.baseTempNo=sBaseTempNo[0];
	}
	var containBaseItems=$("#containBaseItemDataTable").jqGrid("getRowData");
	var rowDatas=$("#dataTable").jqGrid("getRowData");
	var str="";
	for(var i=0;i<containBaseItems.length;i++){
		for(var j=0;j<rowDatas.length;j++){
			if(containBaseItems[i].baseitemcode==rowDatas[j].baseitemcode) break;
			if(j==rowDatas.length-1) str+="【"+containBaseItems[i].descr+"】"; 
			
		}
	}
	if(str){
		art.dialog({
				content: str+'为必报项目，请先进行添加，然后再保存!'
		});
			return;	
	}
	if($("#cannotModifyQtyCount").text()>0){
		art.dialog({
			content: '存在数量不可修改项，数量不等于系统算量,请重新生成数量'
		});
		return;		
	}
	if(rowDatas){
		var sErr="";
		$.each(rowDatas,function(k,v){
			//判断总价是否相等
			if(Math.abs(myRound(parseFloat(v.qty*v.unitprice)+parseFloat(v.qty*v.material)-parseFloat(v.lineamount)))>1){
				sErr+='【'+v.fixareadescr+v.baseitemdescr+'】'+',';
			} 
		}); 
		
		if(sErr!=""){
			sErr=sErr.substring(0, sErr.lastIndexOf(','));
			art.dialog({
					content:sErr+'总价计算出错'
			});
			return ;
		}
	}		
	datas.detailJson=JSON.stringify(rowDatas);
	$.ajax({
		url:'${ctx}/admin/itemPlan/doTCSave',
		type:"post",
           dataType:"json",
           async:false,
           data:datas,
		cache: false,
	    success: function(obj){
	  		if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				Global.Dialog.closeDialog();
				    }
				});
	    	}else{
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}			  				  				
		}
	});	
}
//导入excel
function goImportExcel(){
	var sMustImportTemp="0" ,stitle="基础材料预算明细--从excel导入";
	var rowData=$('#dataTable').jqGrid("getRowData");
	var sContent='导入前,需要清除【${customer.code}-${customer.address}】的基础预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
	if ("${custType.mustImportTemp}"=="1"&& (!$("#isInitSign").is(':checked'))){
		sMustImportTemp="1";
		stitle="基础材料预算明细--从备份导入"
		if(rowData&&rowData.length>0){
			clearDetail(sContent,stitle,sMustImportTemp);
		}else{
			goImportTab(stitle,sMustImportTemp)
		}	
	}else{
		art.dialog({
	  		content:'<input type="radio" checked="checked" id="excelSelect" name="Select" />从Excel导入<br/><input type="radio" id="bakSelect" name="Select" />从备份导入<br/> ',
			lock: true,
		 	width: 200,
	 		height: 100,
		 	title:"选择导入方式",
			ok: function () {
				if($("#bakSelect").is(':checked')){	
					sMustImportTemp="1";
					stitle="基础材料预算明细--从备份导入"	
				}
				if(rowData&&rowData.length>0){
					clearDetail(sContent,stitle,sMustImportTemp);
				}else{
					goImportTab(stitle,sMustImportTemp)
				}		
			},
			cancel: function () {
				return true;
			}
		});
	}
}
function goImportTab(stitle,sMustImportTemp,isOutSet){
	if ("${custType.mustImportTemp}"=="1" && isOutSet=="1"){
		var recordData=$("#dataTable").jqGrid('getGridParam','records');
		if($("#baseTempNo").val()==""||recordData==0){
			art.dialog({
				content: "请先从模板导入预算"
			});
			return;
		}
	}
	var rowData = $("#dataTable").jqGrid("getRowData");
	Global.Dialog.showDialog("itemPlan_importExcel_jcys",{
	  	title:stitle,
	  	url:"${ctx}/admin/itemPlan/goItemPlan_importExcel_jcys",
		postData:{custCode:"${customer.code}",itemType1:"JZ",custType:"${customer.custType}",
			m_umState:"A",mustImportTemp:sMustImportTemp,isOutSet:isOutSet
		}, 
	  	height:600,
	  	width:1000,
	  	returnFun: function(data){
	  			var hasBaseItemPlan=false;
	  			var newData=[]; 
		  		$.each(data,function(k,v){
		  			if(isOutSet=="1" && rowData){
		 				$.each(rowData,function(i,e){
		 					if(v.fixareadescr==e.fixareadescr && v.baseitemcode==e.baseitemcode && 
		 							((v.qty>=0 && e.qty>=0) || (v.qty<0 && e.qty<0)) ){
		 						e.qty=v.qty,
		 						e.sumunitprice=v.sumunitprice,
		 						e.lineamount=v.lineamount,
		 						// e.autoqty=v.qty,
		 				  		e.isservice='${itemChg.isService}';
		 			 			e.lastupdate=getNowFormatDate();
		 			 			e.lastupdatedby='${customer.lastUpdatedBy}';
		 			 			e.actionlog="EDIT";
		 			 			hasBaseItemPlan=true;
		 			 			return false;
		 					}
		 				})
		 			}
		  			hasEdit=true;
		 			if(hasBaseItemPlan){
		 				hasBaseItemPlan=false
		 				return true;
		 			}
		  			v.iscommi="1";
		  			v.isservice='${itemChg.isService}';
		  			v.lastupdate=getNowFormatDate();
		  			v.lastupdatedby='${customer.lastUpdatedBy}';
		  			v.actionlog="ADD";
		 				v.ordername=v.fixareadescr;
		 				if(v.fixareadescr=="全房"||v.fixareadescr=="水电项目"||v.fixareadescr=="土建项目"||v.fixareadescr=="安装项目"||v.fixareadescr=="综合项目") v.isgroup=1;
					else v.isgroup=0;
		 			newData.push(v);	
		  			//$("#dataTable").addRowData($("#dataTable").jqGrid('getGridParam','records')+1, v, "last");
		  		})
				//var params = JSON.stringify($("#dataTable").jqGrid("getRowData").concat(data));
		  		var params = JSON.stringify(rowData.concat(newData));
		  		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk,isgroup'},page:1}).trigger("reloadGrid");  
		 }
	});
}
function goTop(table){
	if(!table)  table="dataTable";
	var rowId = $("#"+table).jqGrid('getGridParam', 'selrow');
	var replaceId=parseInt(rowId)-1;
	if(rowId){
		if(rowId>1){
				var ret1= $("#"+table).jqGrid('getRowData', rowId);
				var ret2= $("#"+table).jqGrid('getRowData', replaceId);
				if(ret1.fixareadescr==ret2.fixareadescr){
				   replace(rowId,replaceId);
				   $("#dataTable").setCell(rowId,'ordername',ret1.fixareadescr);
				   $("#dataTable").setCell(replaceId,'ordername',ret1.fixareadescr);
				   scrollToPosi(replaceId,table,ret1.fixareadescr,true);
				} 
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
function goBottom(table){
	if(!table)  table="dataTable";
	var rowId = $("#"+table).jqGrid('getGridParam', 'selrow');
	var replaceId=parseInt(rowId)+1;
	var rowNum=$("#"+table).jqGrid('getGridParam','records');
	if(rowId){
		if(rowId<rowNum){
			//是否按照材料类型2分类
			var ret1= $("#"+table).jqGrid('getRowData', rowId);
			var ret2= $("#"+table).jqGrid('getRowData', replaceId);
			if(ret1.fixareadescr==ret2.fixareadescr){
			   replace(rowId,replaceId);
			   $("#dataTable").setCell(rowId,'ordername',ret1.fixareadescr);
			   $("#dataTable").setCell(replaceId,'ordername',ret1.fixareadescr);
			   scrollToPosi(replaceId,table,ret1.fixareadescr,true);
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
		  title:"查看修改明细",
		  url:"${ctx}/admin/itemPlan/goItemPlan_priceUpdateView",
		  height:600,
		  width:1000,
		  postData:{custCode:"${customer.code}"}
	});
}
function loadOperate(rowData){
	var count=0; qtyUpdateCount=0; cannotModifyQtyCount=0
	$.each(rowData,function(k,v){
		if(v.count) count++;
		if(v.qtyupdatecount) qtyUpdateCount++;
		if(v.qtyupdatecount && v.canmodiqty=="0" && v.isoutset=="0") cannotModifyQtyCount++;
	})
	$("#count").text(count);
	$("#qtyUpdateCount").text(qtyUpdateCount);
	$("#cannotModifyQtyCount").text(cannotModifyQtyCount);
	
	loadDifPrePlanAreaCount(rowData);
	isShowModifyLable();
	
}
function isShowModifyLable(){	
	 if ($("#qtyUpdateCount").text()>0){
	 	$("#qtyModify_show").show();
	 }else{
	 	$("#qtyModify_show").hide();
	 }
	 if ($("#count").text()>0){
	 	$("#priceModify_show").show();
	 }else{
	 	$("#priceModify_show").hide(); 	
	 }
	 if ($("#difPrePlanAreaCount").text()>0){
	 	$("#difPrePlanArea_show").show();
	 }else{
	 	$("#difPrePlanArea_show").hide();
	 }
	 if($("#cannotModifyQtyCount").text()>0){
		$("#cannotModifyQty_show").show();
	 }else{
		$("#cannotModifyQty_show").hide(); 
	 }
}
function loadDifPrePlanAreaCount(rowData){
	var difPrePlanAreaCount=0,samePrePlanAreaCount=0;
	if('${arryPrePlanAreaDescr}'){
		var arryPrePlanAreaDescr =JSON.parse('${arryPrePlanAreaDescr}');
		if(arryPrePlanAreaDescr.length>0){
			for(var i=0;i<arryPrePlanAreaDescr.length;i++){
				$.each(rowData,function(k,v){
					  if(arryPrePlanAreaDescr[i] == v.fixareadescr){
				            samePrePlanAreaCount++;
				           return false;
				      }
				}); 		
		    };
		    difPrePlanAreaCount=arryPrePlanAreaDescr.length-samePrePlanAreaCount; 
		}
	 }
	 $("#difPrePlanAreaCount").text(difPrePlanAreaCount);
}

function checkBtn(v,x,r){
	if(r.ischeck=="1") return "<input type='checkbox' checked onclick='checkRow("+x.rowId+",this)' />";
	else return "<input type='checkbox' onclick='checkRow("+x.rowId+",this)' />";
}
function checkRow(id,e){
	if($(e).is(':checked')) {
		$("#dataTable").setCell(id,'ischeck',"1");
		$(e).parent().parent().find("td").addClass("checkColor");
	}else{
		$("#dataTable").setCell(id,'ischeck',"0");
		$(e).parent().parent().find("td").removeClass("checkColor");
	}
}
document.addEventListener('visibilitychange', handleVisibilityChange, false);
function handleVisibilityChange(){
	 var colModel=$("#dataTable")[0].p.colModel;
	 var index;
	 $.each(colModel,function(k,v){
 	 if(v.name=="qty"){
	 	index=k;
	 	return false;
 	 } 
 })
 if(!document.hidden&&data_iCol==index)
 	$("#dataTable").jqGrid("editCell",data_iRow,data_iCol,true);
 }
function getIsGroup(v,x,r){
	if(r.fixareadescr=="全房"||r.fixareadescr=="水电项目"||r.fixareadescr=="土建项目"||r.fixareadescr=="安装项目"||r.fixareadescr=="综合项目") return 1;
	else return 0;
}
function closeWin(){
 	if(hasEdit){
 		art.dialog({
	        content: '是否保存被更改的数据？',
	        lock: true,
	        width: 260,
	        height: 100,
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
function goBaseBatchTemp(){
    Global.Dialog.showDialog("itemPlan_baseBatchTemp",{
	title:"基础预算--批量报价导入",
	url:"${ctx}/admin/itemPlan/goBaseBatchTemp?custType=${customer.custType}&custCode=${customer.code}",
	height:600,
	width:1000,
	returnFun: function(data){
 		if(data.length){
	 		hasEdit=true;
			var params=JSON.stringify(data);
			$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk'},page:1}).trigger("reloadGrid");  
		}else{
			$("#dataTable").jqGrid('clearGridData');
		}
	}
	});
}
//基础预算从模板导入导入
function goBaseFromTemp(){
	    Global.Dialog.showDialog("baseitemPlan_baseBatchTemp",{
		title:"基础预算--从模板导入",
		url:"${ctx}/admin/itemPlan/goBaseFromTemp?custType=${customer.custType}&custCode=${customer.code}&m_umState=C",
		height:250,
		width:400,
		returnFun: function(data){
		   	if(data.length){
		   		hasEdit=true;
		  		var params=JSON.stringify(data);
		  		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk'},page:1}).trigger("reloadGrid");  
		  	}
	
		}
	});
}
//重新生成
 function loadRegen(){
  	var rowData=$('#dataTable').jqGrid("getRowData");
	var datas=$("#dataForm").jsonForm();
	datas.custCode="${customer.code}";
	datas.detailJson=JSON.stringify(rowData);
   	art.dialog({
		content:'<input type="checkbox" id="checkCanreplace" />可替换项重新生成：勾选可替换项将被覆盖<br/> '
				+'<input type="checkbox" id="checkCanmodiQty" />重置可修改数量项目<br/> '
				+' 此操作将生成新加空间预算项目,原有预算重新计算数量，<br/>是否确定要重新生成 ？',
		ok: function () {
			var isCheckCanreplace=$("#checkCanreplace").is(':checked');
		 	if(isCheckCanreplace){
		 		datas.isRegenCanreplace='1';
		 	}else{
		 		datas.isRegenCanreplace='0';
		 	}
		 	var isCheckCanreplace=$("#checkCanmodiQty").is(':checked');
		 	if(isCheckCanreplace){
		 		datas.isRegenCanmodiQty='1';
		 	}else{
		 		datas.isRegenCanmodiQty='0';
		 	}
			$.ajax({
				url:"${ctx}/admin/itemPlan/doRegenBasePlanFromPrePlanTemp",
				type:"post",
		        dataType:"json",
		        data:datas,
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
			    },
		      success: function(obj){
				    if(obj.rs){
				  		hasEdit=true;
				  		var params=obj.datas;
				  		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'fixareapk'},page:1}).trigger("reloadGrid");    
				  		art.dialog({
							content: obj.msg,
							time: 1000,
						});  
			  		}else{
			  			art.dialog({
							content: obj.msg
						});
			  		}
			    }
		 	});
		},
 		cancel:function(){
 		}
	});
	  
} 
//输出到Excel并备份
function doExcel(){
  	var rowData=$('#dataTable').jqGrid("getRowData");
  	if(rowData&&rowData.length>0){
	  	art.dialog({
	  		content:'<input type="radio" checked="checked" id="excelSelect" name="Select" />导出到Excel<br/><input type="radio" id="bakSelect" name="Select" />导出到备份<br/> ',
			 title: '选择导出方式',
			lock: true,
		 	width: 300,
		 	height: 120,
			ok: function () {
				if($("#excelSelect").is(':checked')){
					doExcelNow('${customer.address}'+'-基础预算','dataTable', 'dataForm');	
				}else{
					art.dialog({
						content: '<label class="control-textarea">录入备注：</label> <textarea  style="height:100px; " id="bakRemark" name="bakRemark"></textarea>',
					    title: '导出备份--录入备注',
						width: 300,
			 			height: 100,
			 			ok: function (){
			 				var datas=$("#dataForm").jsonForm();
							datas.detailJson=JSON.stringify(rowData);
							datas.remark=$("#bakRemark").val();
							 $.ajax({
									url:"${ctx}/admin/itemPlan/doPlanBak",
									type:"post",
							        dataType:"json",
							        data:datas,
									cache: false,
									error: function(obj){
										showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
								    },
							      success: function(obj){
									    if(obj.rs){
									  		art.dialog({
												content: obj.msg,
												time: 1000,
											});  
								  		}else{
								  			art.dialog({
												content: obj.msg
											});
								  		}
								    }
							 	}); 	 
			 			}
		 			
					}); 
				}
			},
			cancel: function () {
				return true;
			}
		});
 	
  	}else{
  		art.dialog({
			content:"无数据不能导出"
		});  
  		
  	}  
}
function changeIsInitSign(obj){
	if ($(obj).is(':checked')){//草签
		updateInitSign("1");
				
	}else{ //非草签
		if($.trim($("#baseTempNo").val())==''){
			var sContent= '去除草签标记，需要清除【${customer.code}-${customer.address}】的基础预算<br/><input type="checkbox" checked="checked" id="clearDetail" hidden="true"/>同时清空装修区域<br/>注：点击确定,无须进行保存操作即生效！ ';
			clearDetail(sContent,'','','3');
		}else{
			updateInitSign("0");
		}	
	}
}
//修改草签标记
function updateInitSign(sIsInitSign){
	$.ajax({
		url:'${ctx}/admin/itemPlan/doUpdateInitSign',
		type: 'post',
		data:{custCode:"${customer.code}",isInitSign:sIsInitSign},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
				if (sIsInitSign=="1"){
					$('#isInitSign').val('1'); 
				}else{
					$('#isInitSign').val('0'); 
				}	
	    	}else{
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content: obj.msg,
					width: 200
				});
				if (sIsInitSign=="1"){
					$('#isInitSign').val('0'); 
					$("#isInitSign").prop("checked",false);
				}else{
				    $('#isInitSign').val('1');
				    $("#isInitSign").prop("checked",true);
				}	
				return;
	    	}
	    }
	});	
} 
function algorithmClick(e){
	var rowid = $("#dataTable").getGridParam("selrow");
    var rowData = $("#dataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#dataTable").setRowData($(e.target).attr("rowid"), {
    	basealgorithm:$(e.target).val(),
    });
    changeAlgorithm($(e.target).attr("rowid"));
}

function changeAlgorithm(id){
	var ret = $("#dataTable").jqGrid('getRowData', id);
	var datas=$("#page_form").jsonForm();
	datas.custCode="${customer.code}";
	datas.baseItemCode=ret.baseitemcode;
	datas.baseAlgorithm=ret.basealgorithm;
	datas.prePlanAreaPK=ret.preplanareapk; 
	datas.tempDtPk=ret.tempdtpk; 
	$.ajax({
		url:"${ctx}/admin/baseItemPlan/getBaseItemPlanAutoQty",
			type:"post",
	        dataType:"json",
	        async:false,
	        data:datas,
			cache: false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				var qty = 0;
				if (obj){
					qty = obj; 	
				}
				var summary=getGridFootSum(id,'lineamount');
				var oldLineamount=myRound($("#dataTable").getCol('lineamount',false,'sum'));
				$("#dataTable").setRowData(id,{
					'qty':qty, 'autoqty':qty,
					'sumunitprice':myRound(qty*ret.unitprice),
					'summaterial':myRound(qty*ret.material),
			  		'lineamount':myRound(qty*ret.unitprice)+myRound(qty*ret.material),
			  	});
			    var lineamount=myRound($("#dataTable").getCol('lineamount',false,'sum'));
	            setGridFootSum(id,'lineamount',summary+lineamount-oldLineamount);	
			}
	});	
}
</script> 
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system " onclick="save()">保存</button>
					<button type="button" id="saveBtn" class="btn btn-system " onclick="goPriceUpdateView()">查看单价修改</button>
					<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
					<span id="priceModify_show" hidden="true" style="font-size: 12px;color: red;margin-left:5px">单价被修改条数：<span id="count"></span>条</span>
					<span id="qtyModify_show"  hidden="true" style="font-size: 12px;color: red;margin-left:5px">数量与系统算量不符条数：<span id="qtyUpdateCount"></span>条</span>
					<span id="difPrePlanArea_show"  hidden="true"  style="font-size: 12px;color: red;margin-left:5px">无报价空间：<span id="difPrePlanAreaCount"></span>个</span>
					<span id="cannotModifyQty_show" hidden="true"  style="font-size: 12px;color: red;margin-left:5px">空间信息发生变化，请重新生成预算数量<span hidden="true" id="cannotModifyQtyCount"></span></span>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
				<%-- 	<house:token></house:token> --%>
					<input type="hidden" name="jsonString" value="" /> <input type="hidden" name="custType"
						value="${customer.custType }" /> <input type="hidden" name="lastUpdatedBy"
						value="${customer.lastUpdatedBy }" /> <input type="hidden" name="custCode" value="${customer.code }" />
					<input type="hidden" name="itemType1" value="JZ" /> <input type="hidden" name="isService" value="0" />
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="code" name="code" value="${customer.code}"
							readonly="readonly" />
						</li>
						<li><label>楼盘</label> <input type="text" id="address" name="address" value="${customer.address}"
							readonly="readonly" />
						</li>
						<li><label>设计师</label> <input type="text" id="designMan" name="designMan"
							value="${customer.designManDescr}" readonly="readonly" />
						</li>
						<li><label>户型</label> <input type="text" id="layout" name="layout" value="${customer.layout}"
							readonly="readonly" />
						</li>
						<li><label>面积</label> <input type="text" id="area" name="area" value="${customer.area}"
							onkeyup="value=value.replace(/[^\-?\d]/g,'')" readonly="readonly"/>
						</li>
						<li hidden="true"><label>业务员</label> <input type="text" id="businessMan" name="businessMan"
							value="${customer.businessManDescr}" readonly="readonly" />
						</li>
						<li hidden="true"><label>客户状态</label> <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS"
								selectedValue="${customer.status }"></house:xtdmMulit>
						</li>
						<li><label>客户名称</label> <input type="text" id="descr" name="descr" value="${customer.descr}" />
						</li>
						<li><label>管理费</label> <input type="text" id="manageFee" name="manageFee" readonly="readonly" />
						</li>
						<li><label>基础管理费</label> <input type="text" id="manageFeeBase" name="manageFeeBase"
							readonly="readonly" />
						</li>
						<li><label>直接费</label> <input type="text" id="baseFeeDirct" name="baseFeeDirct" readonly="readonly" />
						</li>
						<li><label>综合费用</label> <input type="text" id="baseFeeComp" name="baseFeeComp" readonly="readonly" />
						</li>
						<li><label>基础总金额</label> <input type="text" id="baseFee" name="baseFee" readonly="readonly" />
						</li>
						<li><label>主套餐费</label> <input type="text" id="mainSetFee" name="mainSetFee" readonly="readonly" />
						</li>
						<li><label>远程费</label> <input type="text" id="longFee" name="longFee" readonly="readonly" />
						</li>
						<li><label>套餐外基础增项</label> <input type="text" id="setAdd" name="setAdd" readonly="readonly" />
						</li>
						<li><label>套餐内减项</label> <input type="text" id="setMinus" name="setMinus" readonly="readonly" />
						</li>
						<li>	
							<label>不计管理费金额</label>
							<input type="text" id="noManageFee" name="noManageFee" readonly="readonly" />
						</li>
						<li><label>模板</label> <input type="text"  width: 130px;  id="baseTempNo" name="baseTempNo" value="${customer.baseTempNo}" readonly="readonly" />
						</li>
						<li class="search-group-shrink"  style="margin-left:80px">
							<input type="checkbox" id="isInitSign" name="isInitSign" value="${customer.isInitSign}",
								onclick="changeIsInitSign(this)" }/>
							<p>草签(无需详细报价,需重签)</p>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button id="addBtn"  type="button" class="btn btn-system " onclick="add()" >快速新增</button>
				<button id="insertBtn" type="button" class="btn btn-system " onclick="add(true)">插入</button>
				<button id="editBtn" type="button" class="btn btn-system " onclick="edit()">编辑</button>
				<button id="delBtn" type="button" class="btn btn-system " onclick="del()">删除</button>
				<button type="button" class="btn btn-system " onclick="goTop()">向上</button>
				<button type="button" class="btn btn-system " onclick="goBottom()">向下</button>
				<button id="updatePriceBtn" type="button" class="btn btn-system " onclick="updatePrice()">批量调整单价</button>
				<button id="baseFromTempBtn"  type="button" class="btn btn-system " onclick="goBaseFromTemp()">导入模板</button>
				<button id="importBtn" type="button" class="btn btn-system " onclick="goImportExcel()">导入备份</button>
				<button type="button" id="copyOutItem" class="btn btn-system " onclick="goImportTab('复制套外项目','1','1')">复制套外项目</button>
				<button id="baseBatchTempBtn"  type="button" class="btn btn-system " onclick="goBaseBatchTemp()" style="display: none">批量报价导入</button>
				<button type="button" id="regenLoad" class="btn btn-system " onclick="loadRegen()" >重新生成</button>
				<button  type="button" id="copy" class="btn btn-system " onclick="copy()" style="display: none">复制预算</button>
				<button id="clearBtn" type="button" class="btn btn-system " onclick="clearDetail()">清空预算</button>
				<button type="button" class="btn btn-system " onclick="view()">查看</button>
				<button type="button" class="btn btn-system " onclick="doExcel()">导出</button>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
			<div style="display: none">
				<table id="containBaseItemDataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>


