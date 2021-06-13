<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<!DOCTYPE html>
<html>
<head>
	<title>ItemPlan列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
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
    text-align: left;
    padding-left: 1px;
}
.form-search label {
    margin-left: 1px;
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
var editRow;
var itemdescr;
var hasEdit=false;//是否修改
//获取对应区域名称总价
function getSumByFixarea(rowData,descr){
	var sum=0;
	$.each(rowData,function(k,v){
		if(v.fixareadescr==descr) sum+=parseFloat(v.lineamount);
	});
	return sum;
}
function changeAmount(){
   var befLineAmount=parseFloat($("#befLineAmount").val()); 
   var discAmount=parseFloat($("#discAmount").val()); 
   $("#amount").val(befLineAmount-discAmount);
   var manageFee_Int= myRound((befLineAmount*${customer.integrateFeePer}-discAmount*${customer.integrateDiscPer})*${customer.manageFeeIntPer}); 
$("#manageFee_Int").val(manageFee_Int);
}
function changeCupboardAmount(){
   var cupboardFee=parseFloat($("#cupboardFee").val()); 
   var cupboardDisc=parseFloat($("#cupboardDisc").val());
   var manageFee_Cup=myRound((cupboardFee*${customer.cupboardFeePer}-cupboardDisc*${customer.cupBoardDiscPer})*${customer.manageFeeCupPer});
   $("#cupboardAmount").val(cupboardFee-cupboardDisc);
   $("#manageFee_Cup").val(manageFee_Cup);
 
}
//更新表格汇总信息
function reloadSummary(table){
	 var beflineamount=$("#"+table).getCol('beflineamount',false,'sum');   
	 var tmplineamount=$("#"+table).getCol('tmplineamount',false,'sum');   
	 var processcost=$("#"+table).getCol('processcost',false,'sum');   
	 var lineamount=$("#"+table).getCol('lineamount',false,'sum');
	 $("#"+table).footerData('set', { "beflineamount": beflineamount }); 
	 $("#"+table).footerData('set', { "tmplineamount": tmplineamount }); 
	 $("#"+table).footerData('set', { "processcost": processcost }); 
	 $("#"+table).footerData('set', { "lineamount": lineamount });    

}
//计算所有费用并刷新
function loadFee(rowData){
	 var befLineAmount=0;
	 var cupboardFee=0;
	 var discAmount=parseFloat($("#discAmount").val()); 
	 var cupboardDisc=parseFloat($("#cupboardDisc").val()); 
	 $.each(rowData,function(k,v){
	 	if(v.iscupboard=='0') befLineAmount+=parseFloat(v.lineamount);   //计算集成优惠前金额及总价
	 	if(v.iscupboard=='1') cupboardFee+=parseFloat(v.lineamount); //计橱柜优惠前金额
	 });
	 var amount=befLineAmount-discAmount;
	 var cupboardAmount=cupboardFee-cupboardDisc;
	 var manageFee_Cup=myRound((cupboardFee*${customer.cupboardFeePer}-cupboardDisc*${customer.cupBoardDiscPer})*${customer.manageFeeCupPer});
	 var manageFee_Int= myRound((befLineAmount*${customer.integrateFeePer}-discAmount*${customer.integrateDiscPer})*${customer.manageFeeIntPer}); 
	 $("#manageFee_Int").val(manageFee_Int);
	 $("#befLineAmount").val(befLineAmount);
	 $("#amount").val(amount);
	 $("#cupboardFee").val(cupboardFee);
	 $("#cupboardAmount").val(cupboardAmount);
	 $("#manageFee_Cup").val(manageFee_Cup);
}
function add(isInsert){
	var posi = $('#dataTable').jqGrid('getGridParam', 'selrow')-1;
 	var ret = selectDataTableRow();
   	if(isInsert&&!ret){
	 	art.dialog({
			content: "请选择一条记录"
		});
		return;	
 	}
	 var itemType2Str="";
 	if(ret) itemType2Str+="&itemType2="+ret.itemtype2;
 	var url="${ctx}/admin/itemPlan/goYsQuickSave?custCode=${customer.code}&itemType1=JC&isOutSet=${customer.isOutSet}&custType=${customer.custType}&isService=0"+itemType2Str;
 	if(ret) url="${ctx}/admin/itemPlan/goYsQuickSave?custCode=${customer.code}&itemType1=JC&isOutSet=${customer.isOutSet}&custType=${customer.custType}&isService=0&fixAreaPk="+ret.fixareapk+itemType2Str;
 	Global.Dialog.showDialog("quickSave",{
	title:"集成预算--快速新增",
  	url:url,
	height:750,
	width:1380,
	resizable:false, 
	returnFun: function(data){		
		var arr=$("#dataTable").jqGrid("getRowData");
		var brr=[];
		var crr=[];
			if(isInsert){
	 			for(var i=0;i<posi;i++){
	 				brr.push(arr[i]);
	 			}
	 			for(var i=posi;i<arr.length;i++){
	 				crr.push(arr[i]);
	 			}
			}
			//默认提成
			$.each(data,function(k,v){
				hasEdit=true;
				v.lastupdate=getNowFormatDate();
				v.lastupdatedby='${customer.lastUpdatedBy}';
				v.actionlog="ADD";
				//v.ordername=v.fixareadescr;
				//v.tempunitprice=v.unitprice;
				//v.tempmaterial=v.material;
				if(isInsert){
					brr.push(v);
				}else{
				   arr.push(v);
				}
			})
			if(isInsert){
				var postArr=brr.concat(crr);
			}else{
				var postArr=arr;
			}
		  	var params=JSON.stringify(postArr);
		 	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params},page:1}).trigger("reloadGrid");  
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
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	Global.Dialog.showDialog("itemPlanCopy",{
		  title:"搜寻--客户编号",
		  url:"${ctx}/admin/itemPlan/goCopy?itemType1=JC&custCode=${customer.code}&isService=0&&custType=${customer.custType}",
		  height: 600,
		  width:1000,
		  returnFun: function(data){
		  		$.each(data,function(k,v){
		  			 hasEdit=true;
		  			 $("#dataTable").addRowData(k+1, v, "last");
		  		})

		  }
	});
}

function view(){
	var rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
    if (rowId) {
	    Global.Dialog.showDialog("ys_detailView",{
			title:"集成预算--查看",
			url:"${ctx}/admin/itemPlan/goItemPlan_ys_detailView?itemType1=JC&custCode=${customer.code}&isOutSet=${customer.isOutSet}&rowId="+rowId+"&isService=0",
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
	var rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
    if (rowId) {
	    var itemCode=$("#dataTable").getCell(rowId,"itemcode");
	    var pk=$("#dataTable").getCell(rowId,"pk");
	    Global.Dialog.showDialog("ys_detailViewEdit",{
			  title:"集成预算--编辑",
			  url:"${ctx}/admin/itemPlan/goItemPlan_ys_detailViewEdit?itemType1=JC&custCode=${customer.code}&isOutSet=${customer.isOutSet}&rowId="+rowId+"&isService=0&itemCode="+itemCode+"&pk="+pk,
			  height:600,
			  width:1000,
			  resizable:false, 
			  returnFun: function(data){
			      if(data.fixareapk) {
			     	hasEdit=true;
			     	data.lastupdate=getNowFormatDate();
				    data.lastupdatedby='${customer.lastUpdatedBy}';
				    data.actionlog='EDIT';
				    $("#dataTable").setRowData(rowId,data);
				   	var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
				  	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params},page:1}).trigger("reloadGrid");  
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
	var rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
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
			    $("#dataTable").delRowData(rowId);
			    var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
				$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params},page:1}).trigger("reloadGrid"); 
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
function updatePrice(){
	Global.Dialog.showDialog("itemChgUpdatePrice",{
		 title:"调整单价",
		 url:"${ctx}/admin/itemPlan/goUpdatePrice",
		 height: 600,
		 width:1000,
		 resizable:false, 
		 returnFun: function(data){
		  	var ids=$("#dataTable").jqGrid("getDataIDs");
		  	var fixareapk;
		  	var sum;
		  	var baseFeeComp=0;//综合费
            $.each(ids,function(k,v){
                var rowData = $("#dataTable").jqGrid('getRowData',v);
                if(!fixareapk){
               	//初始分组
	                fixareapk=rowData.fixareapk;
	                sum=0;
                }
               	var lineamount=myRound(rowData.qty*rowData.tempunitprice*data.unitPrice)+myRound(rowData.qty*rowData.tempmaterial*data.material);
               	$("#dataTable").setCell(v,'unitprice',myRound(rowData.tempunitprice*data.unitPrice));
             	$("#dataTable").setCell(v,'sumunitprice',myRound(rowData.qty*rowData.tempunitprice*data.unitPrice));
              	$("#dataTable").setCell(v,'material',myRound(rowData.tempmaterial*data.material));
             	$("#dataTable").setCell(v,'summaterial',myRound(rowData.qty*rowData.tempmaterial*data.material));
                $("#dataTable").setCell(v,'lineamount',lineamount);
                if(rowData.fixareadescr=="综合项目") baseFeeComp+=lineamount;
                sum+=lineamount;
                if(fixareapk!=rowData.fixareapk){
                 	//新的分组
                	 setGridFootSum(v-1,'lineamount',sum-lineamount);
                 	fixareapk=rowData.fixareapk;
                	 sum=lineamount;
                }
            })
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
		  	  ///loadFee(rowData,baseFeeComp);	
		}
	});
}
function clearDetail(sContent,stitle,sMustImportTemp){
	if (!sContent){
		sContent= '是否清除【${customer.code}-${customer.address}】的集成预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域及集成成品<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
	}
	art.dialog({
		 content:sContent,
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
		 	hasEdit=true;
		 	var ischecked=$("#clearDetail").is(':checked');
		 	$("#dataTable").jqGrid('clearGridData');
		 	if(ischecked){
	 			var datas=$("#dataForm").jsonForm();
				datas.isService=0;
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
			return true;
		}
	});
}
/**初始化表格*/
$(function(){
	var  isCellEdit=true;
	if ('${customer.status}'=='4'||'${customer.status}'=='5' || "${contractStatus}" == "2" || "${contractStatus}" == "3" || "${contractStatus}" == "4"){
		$("#saveBtn").attr("disabled","disabled");
		$("#addBtn").attr("disabled","disabled");
		$("#insertBtn").attr("disabled","disabled");
		$("#delBtn").attr("disabled","disabled");
		$("#editBtn").attr("disabled","disabled");
		$("#clearBtn").attr("disabled","disabled");
		$("#importBtn").attr("disabled","disabled");
		$("#copy").attr("disabled","disabled");
		isCellEdit=false;	
	}
	$("#status_NAME").attr("disabled","disabled");
	$('*','#dataForm').bind('focus',function(){data_iRow=0;}); 
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemPlan/goItemPlan_RZJqGrid?custCode=${customer.code}&itemType1=JC",
			height:$(document).height()-$("#content-list").offset().top-70,
			cellEdit:isCellEdit,
			cellsubmit:'clientArray',
			rowNum:1000,
			colModel : [
			{name: "pk", index: "pk", width: 122, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "fixareapk", index: "fixareapk", width: 100, label: "区域名称", sortable: true, align: "left", hidden: true},
			{name: "fixareadescr", index: "fixareadescr", width: 80, label: "区域名称", sortable: true, align: "left"},
			{name: "isfixprice", index: "isfixprice", width: 122, label: "是否固定价格", sortable: true, align: "left", hidden: true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
			{name: "intproddescr", index: "intproddescr", width: 80, label: "集成成品", sortable: true, align: "left"},
			{name: "iscupboarddescr", index: "iscupboarddescr", width: 65, label: "是否橱柜", sortable: true, align: "left"},
			{name: "iscupboard", index: "iscupboard", width: 68, label: "是否橱柜", sortable: true, align: "left", hidden: true},
			{name: "itemcode", index: "itemcode", width: 68, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable: true, align: "left",editable:true,edittype:'text',
					editoptions:{ dataEvents:[{type:'focus',fn:function(e){ $(e.target).openComponent_itemDescr({condition:{'itemType1':"JC".trim(),'containCode':"1",custType:"${customer.custType}"},onSelect:onSelect});}}]}},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "projectqty", index: "projectqty", width: 78, label: "预估施工量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "uom", index: "uom", width: 40, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 65, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable: true, align: "right", sum: true},
			{name: "markup", index: "markup", width: 45, label: "折扣", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,minValue:0}},
			{name: "tmplineamount", index: "tmplineamount", width: 60, label: "小计", sortable: true, align: "right", sum: true},
			{name: "processcost", index: "processcost", width: 65, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "lineamount", index: "lineamount", width: 60, label: "总价", sortable: true, align: "right", sum: true},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 75, label: "套餐外材料", sortable: true, align: "left", hidden: true},
			{name: "remark", index: "remark", width: 140, label: "材料描述", sortable: true, align: "left",editable:true,edittype:'textarea'},
			{name: "giftdescr", index: "giftdescr", width: 80, label: "赠送项目", sortable: true, align: "left"},
			{name: "isgift", index: "isgift", width: 0.5, label: "是否赠送项目", sortable: true, align: "left"},
			{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 80, label: "修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
			{name: "dispseq", index: "dispseq", width: 84, label: "dispseq", sortable: true, align: "left", hidden: true},
			{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
            {name: "isoutset", index: "isoutset", width: 122, label: "是否套餐外", sortable: true, align: "left", hidden: true},
          	{name: "isservice", index: "isservice", width: 122, label: "是否服务性产品", sortable: true, align: "left",formatter:function(){return 0}, hidden: true},
           	{name: "cost", index: "cost", width: 122, label: "成本", sortable: true, align: "right", hidden: true},
            {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable: true, align: "left"},
       	    {name: "oldprojectcost", index: "oldprojectcost", width: 90, label: "原项目经理结算价", sortable:false, align: "right", hidden: true},
		    {name: "projectcost", index: "projectcost", width: 90, label: "升级项目经理结算价", sortable:false, align: "right", hidden: true},
            ],
			gridComplete:function(){
			   if($("#dataTable").jqGrid('getGridParam','records')==0) $("#copy").show();
				else $("#copy").hide();
				var rowData = $("#dataTable").jqGrid('getRowData');
				loadFee(rowData);
				var ids=$("#dataTable").jqGrid("getDataIDs");
             $.each(ids,function(k,v){
            	var isfixprice=$("#dataTable").getCell(v,"isfixprice");
            	var isoutset=$("#dataTable").getCell(v,"isoutset");
   
            	/* if(!(isfixprice=='0'&&${customer.isOutSet}=='1')){
				$("#dataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
				} */
				if(!(isfixprice=='0'&&isoutset=='1')){
					$("#dataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
				}
				 //套餐材料，不允许直接在材料名称里修改
				if(isoutset!="1")  $("#dataTable").jqGrid('setCell', k+1, 'itemdescr', '', 'not-editable-cell');
            })
			},
            afterSaveCell:function(id,name,val,iRow,iCol){
            	  hasEdit=true;
                  var rowData = $("#dataTable").jqGrid('getRowData',id);
                  var processcost=parseFloat(rowData.processcost);
                  if(name=='itemdescr')
	              $("#dataTable").setCell(id,'itemdescr',itemdescr);
                  switch (name){
                	case 'qty':
                	$("#dataTable").setCell(id,'beflineamount',myRound(val*rowData.unitprice,4));
                	$("#dataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100));
                	$("#dataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100)+processcost));
                	break;
                	case 'unitprice':
                	$("#dataTable").setCell(id,'beflineamount',myRound(val*rowData.qty,4));
                	$("#dataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.qty,4)*rowData.markup/100));
                	$("#dataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.qty,4)*rowData.markup/100)+processcost));
                	break;
                	case 'markup':
                	$("#dataTable").setCell(id,'tmplineamount',myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100));
                	$("#dataTable").setCell(id,'lineamount',myRound(myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100)+processcost));
                	break;
                	case 'processcost':
                	$("#dataTable").setCell(id,'lineamount',myRound(parseFloat(rowData.tmplineamount)+processcost));
                	break;
                }
                $("#dataTable").setCell(id,'lastupdatedby','${customer.lastUpdatedBy}');
                $("#dataTable").setCell(id,'actionlog','EDIT');
                $("#dataTable").setCell(id,'lastupdate',getNowFormatDate());
                //获取新的表格数据
                 var rowData = $("#dataTable").jqGrid('getRowData');
                 reloadSummary("dataTable");
    			 loadFee(rowData);
                 
            },
            beforeSelectRow:function(id){
	             $(".search-suggest").hide();
	             itemdescr=$('#dataTable').getCell(id,"itemdescr");
	             data_iCol=0;
	         	 setTimeout(function(){
		           relocate(id);
		          },100)
          	},
            beforeEditCell:function(rowid,cellname,value,iRow,iCol){
	            data_iRow=iRow;
	            data_iCol=iCol;
       		}
		
	  });
				
});

function doSave(rowData){
	var datas=$("#dataForm").jsonForm();
	datas.detailJson=JSON.stringify(rowData);
	datas.isService=0;
	var url="${ctx}/admin/itemPlan/doClysSave";
	if('${customer.isOutSet}'=='2') url="${ctx}/admin/itemPlan/doClysTCSave";
	$.ajax({
		url:url,
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
				});
	    	}		  				
		}
	});
}
function save(){
	if (parseFloat($('#amount').val())<0||parseFloat($('#cupboardAmount').val())<0){
		art.dialog({
				content:'集成或橱柜实际总价小于0,不允许保存！'
			});
		return ;
	}
	var rowData=$('#dataTable').jqGrid("getRowData");
	var x=0;
	var sErr="";
	$.each(rowData,function(k,v){
		if(v.qty==0&&v.processcost!=0){
			x=1;
			return false;		
		} 
		if(Math.abs(myRound(parseFloat(v.qty*v.unitprice)*v.markup/100+parseFloat(v.processcost))-parseFloat(v.lineamount))>1){
			sErr+=(k+1).toString()+','
		} 
	})
	if(sErr!=""){
		sErr=sErr.substring(0, sErr.lastIndexOf(','));
		art.dialog({
				content:'第'+sErr+'行总价计算出错'
		});
		return ;
	}
	if(x==1){
		art.dialog({
			content:'存在数量为0，其它费用不为0的材料，是否确定保存？',
			lock: true,
			width: 260,
			height: 100,
			ok: function () {
				doSave(rowData);
				return;
			},
			cancel: function () {
				return ;
				}
			});
	}else{
		doSave(rowData);
	}
}
//导入excel
/* function goImportExcel(){
   Global.Dialog.showDialog("itemPlan_importExcel",{
		  title:"集成预算明细--从excel导入",
		  url:"${ctx}/admin/itemPlan/goItemPlan_importExcel?itemType1=JC&custCode=${customer.code}&custType=${customer.custType}&isService=0&isCupboard=0",
		  height:600,
		  width:1000,
		  returnFun: function(data){
		  		$.each(data,function(k,v){
		  			hasEdit=true;
		  			v.iscommi="1";
		  			v.isservice=0;
		  			v.lastupdate=getNowFormatDate();
		  			v.lastupdatedby='${customer.lastUpdatedBy}';
		  			v.actionlog="ADD";
		  			$("#dataTable").addRowData($("#dataTable").jqGrid('getGridParam','records')+1, v, "last");
		  		})

		  }
	});
} */
//导入excel
function goImportExcel(){
	var sMustImportTemp="0" ,stitle="集成预算明细--从excel导入";
	var sContent='导入前,需要清除【${customer.code}-${customer.address}】的集成预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域及集成成品<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
	var rowData=$('#dataTable').jqGrid("getRowData");
	art.dialog({
  		content:'<input type="radio" checked="checked" id="excelSelect" name="Select" />从Excel导入<br/><input type="radio" id="bakSelect" name="Select" />从备份导入<br/> ',
		lock: true,
	 	width: 200,
	 	height: 100,
	 	title:"选择导入方式",
		ok: function () {
			if($("#bakSelect").is(':checked')){	
				sMustImportTemp="1";
				stitle="集成预算明细--从备份导入"	
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
function goImportTab(stitle,sMustImportTemp ){
	Global.Dialog.showDialog("itemPlan_importExcel",{
	  	title:stitle,
	  	url:"${ctx}/admin/itemPlan/goItemPlan_importExcel?itemType1=JC&custCode=${customer.code}&custType=${customer.custType}&isService=0&isCupboard=0"+"&mustImportTemp="+sMustImportTemp,
	  	height:600,
	  	width:1000,
	    returnFun: function(data){
	  		$.each(data,function(k,v){
	  			hasEdit=true;
	  			v.iscommi="1";
	  			v.isservice=0;
	  			v.lastupdate=getNowFormatDate();
	  			v.lastupdatedby='${customer.lastUpdatedBy}';
	  			v.actionlog="ADD";
	  			$("#dataTable").addRowData($("#dataTable").jqGrid('getGridParam','records')+1, v, "last");
	  		})
		}
	});
}
function goTop(){
	var rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
	var replaceId=parseInt(rowId)-1;
	if(rowId){
		if(rowId>1){
				var ret1= $("#dataTable").jqGrid('getRowData', rowId);
				var ret2= $("#dataTable").jqGrid('getRowData', replaceId);
			    replace(rowId,replaceId);
			    scrollToPosi(replaceId);
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
function goBottom(){
	var rowId =$("#dataTable").jqGrid('getGridParam', 'selrow');
	var replaceId=parseInt(rowId)+1;
	var rowNum=$("#dataTable").jqGrid('getGridParam','records');
	if(rowId){
		if(rowId<rowNum){
			//是否按照材料类型2分类
			var ret1= $("#dataTable").jqGrid('getRowData', rowId);
			var ret2= $("#dataTable").jqGrid('getRowData', replaceId);
		    replace(rowId,replaceId);
		    scrollToPosi(replaceId);
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
function init(){
	if('${customer.isOutSet}'=='2'){
		$("#dataTable").setGridParam().showCol("isoutsetdescr").trigger("reloadGrid");
	} 
	/* if('${customer.isOutSet}'=='1'){
		$("#manageFeeCupLi").hide();
	} */
}
window.onfocus = function() { 
	var colModel=$("#dataTable")[0].p.colModel;
	var index;
		$.each(colModel,function(k,v){
		 	if(v.name=="qty"){
			 	index=k;
			 	return false;
		 	} 
		})
 	if(data_iCol==index&&editRow){
 		$("#dataTable").jqGrid("editCell",data_iRow,data_iCol,true);
 	}	
}; 
window.onblur = function() { 
	editRow=data_iRow;	
};
function onSelect(json){
    var rowid = $( "#dataTable" ).getGridParam( "selrow" );
    var rowData = $("#dataTable").jqGrid('getRowData',rowid);
    var unitprice=0;
    if(rowData.isoutset!='0') unitprice=json.price;
    $("#dataTable").jqGrid("saveCell",rowid,data_iCol);
    //$("#dataTable").setRowData(rowid,{'itemcode':json.code,'itemdescr':json.descr,'sqlcodedescr':json.sqldescr,'uom':json.uomdescr,'itemtype2descr':json.itemtype2descr,'itemtype3descr':json.itemtype3descr,'marketprice':json.marketprice,'unitprice':unitprice,'beflineamount':myRound(rowData.qty*unitprice,4),'tmplineamount':myRound(rowData.qty*unitprice*rowData.markup/100),'lineamount':myRound(parseFloat(rowData.qty*unitprice*rowData.markup/100)+parseFloat(rowData.processcost)),'cost':json.cost,'remark':json.remark});
	//折扣改为100%
	$("#dataTable").setRowData(rowid,{'itemcode':json.code,'itemdescr':json.descr,'sqlcodedescr':json.sqldescr,
		  'uom':json.uomdescr,'itemtype2descr':json.itemtype2descr,'itemtype3descr':json.itemtype3descr,
		  'marketprice':json.marketprice,'unitprice':unitprice,'beflineamount':myRound(rowData.qty*unitprice,4),
		  'tmplineamount':myRound(rowData.qty*unitprice),
		  'lineamount':myRound(myRound(rowData.qty*unitprice)+parseFloat(rowData.processcost)),
		  'cost':json.cost,'remark':json.remark,'markup':100});
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

//输出到Excel或备份
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
					doExcelNow('${customer.address}'+'-集成预算','dataTable', 'dataForm');
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
</script>
</head>
<body onload="init()">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBtn" type="button" class="btn btn-system" onclick="save()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
			<%-- 		<house:token></house:token> --%>
					<input type="hidden" id="expired" name="expired" value="${itemPlan.expired}" /> <input type="hidden"
						name="jsonString" value="" /> <input type="hidden" name="custType" value="${customer.custType }" /> <input
						type="hidden" name="lastUpdatedBy" value="${customer.lastUpdatedBy }" /> <input type="hidden"
						name="custCode" value="${customer.code }" /> <input type="hidden" name="itemType1" value="JC" /> <input
						type="hidden" name="isService" value="0" />
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
							readonly="readonly" />
						</li>
						<li hidden="true"><label>客户状态</label> <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS"
								selectedValue="${customer.status }"></house:xtdmMulit>
						</li>
						<li  hidden="true"><label>客户名称</label> <input type="text" id="descr" name="descr" value="${customer.descr}" />
						</li>
							<li  hidden="true"><label>业务员</label> <input type="text" id="businessMan" name="businessMan"
							value="${customer.businessManDescr}" readonly="readonly" />
						</li>
						<li><label>集成优惠前金额</label> <input type="text" id="befLineAmount" name="befLineAmount"
							readonly="readonly" />
						</li>
						<li><label style="color: blue;">集成优惠金额</label> <input type="text" id="discAmount" name="discAmount"
							value="${customer.integrateDisc}" onblur="changeAmount()" />
						</li>
						<li><label>集成实际总价</label> <input type="text" id="amount" name="amount" readonly="readonly" />
						</li>
						<li><label>集成管理费</label> <input type="text" id="manageFee_Int"
							name="manageFee_Int" readonly="readonly" />
						</li>
						<li><label>橱柜优惠前金额</label> <input type="text" id="cupboardFee" name="cupboardFee"
							readonly="readonly" />
						</li>
						
						<li><label style="color: blue;">橱柜优惠金额</label> <input type="text" id="cupboardDisc" name="cupboardDisc"
							value="${customer.cupBoardDisc}" onblur="changeCupboardAmount()" />
						</li>
						<li><label>橱柜实际总价</label> <input type="text" id="cupboardAmount" name="cupboardAmount"
							readonly="readonly" />
						</li>
						<li id="manageFeeCupLi"><label>橱柜管理费</label> <input type="text" id="manageFee_Cup"
							name="manageFee_Cup" readonly="readonly" />
						</li>	
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button id="addBtn" type="button" class="btn btn-system " onclick="add()">快速新增</button>
				<button id="insertBtn" type="button" class="btn btn-system " onclick="add(true)">插入</button>
				<button id="delBtn" type="button" class="btn btn-system " onclick="del()">删除</button>
				<button type="button" class="btn btn-system " onclick="view()">查看</button>
				<button id="editBtn" type="button" class="btn btn-system " onclick="edit()">编辑</button>
				<button type="button" class="btn btn-system " onclick="goTop()">向上</button>
				<button type="button" class="btn btn-system " onclick="goBottom()">向下</button>
				<button id="clearBtn" type="button" class="btn btn-system " onclick="clearDetail()">清空预算</button>
				<button type="button" class="btn btn-system " onclick="doExcel()">导出</button>
				<button id="importBtn" type="button" class="btn btn-system " onclick="goImportExcel()">导入</button>
				<button type="button" id="copy" class="btn btn-system " onclick="copy()" style="display: none">复制预算</button>
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


