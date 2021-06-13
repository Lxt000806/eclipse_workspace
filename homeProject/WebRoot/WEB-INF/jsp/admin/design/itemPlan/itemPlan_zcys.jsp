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
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}" type="text/javascript"></script>
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
.form-search .ul-form li label {
    width: 115px;
    margin-right: 0px;
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
var isService=0;
var tableId="dataTable";
var hasEdit=false;//是否修改

//获取对应区域名称总价
function getSumByFixarea(rowData,descr){
	var sum=0;
	$.each(rowData,function(k,v){
		if(v.fixareadescr==descr) sum+=parseFloat(v.lineamount);
	});
	return sum;
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
function changeAmount(){
   var lineamount=myRound($("#dataTable").getCol('lineamount',false,'sum')); 
   var  discAmount=parseFloat($("#discAmount").val()); 
   if(discAmount){
   		if(lineamount>=0)  $("#amount").val(lineamount-discAmount);
   } else{
   		$("#amount").val(lineamount);
   }
   var manageFee_Main=myRound(($("#befAmount").val()*${customer.mainFeePer}-discAmount*${customer.mainDiscPer})*${customer.manageFeeMainPer});  //主材管理费：Round((主材费*系数-主材优惠*系数)*主材管理费系数,0)
   $("#manageFee_Main").val(manageFee_Main); 
}
//刷新服务性总价
function loadMainServFee(){
	 var mainServFee=myRound(parseFloat($("#serviceDataTable").getCol('lineamount',false,'sum')));
	 var manageFee_Serv=myRound(mainServFee*${customer.mainServFeePer}*${customer.manageFeeServPer});//服务性产品管理费：Round((服务性产品费*服务性产品费系数*服务性管理费系数,0)
	 $("#mainServFee").val(mainServFee);
	 $("#manageFee_Serv").val(manageFee_Serv);
}
//计算所有费用并刷新
function loadFee(rowData){
    var befAmount=parseFloat($("#dataTable").getCol('lineamount',false,'sum'));
    var discAmount=parseFloat($("#discAmount").val());
    var amount=befAmount-discAmount;
    var fee=0;
    var manageFee_Main=myRound((befAmount*${customer.mainFeePer}-discAmount*${customer.mainDiscPer})*${customer.manageFeeMainPer});//Round((主材费*主材费系数-主材优惠*主材优惠系数)*主材管理费系数,0)
    $.each(rowData,function(k,v){
 	  	if(v.commitype=='2') fee+=parseFloat(v.lineamount);
    });
	$("#befAmount").val(myRound(befAmount,2));
	$("#amount").val(myRound(amount,2));
	$("#fee").val(myRound(fee,2));
	$("#manageFee_Main").val(manageFee_Main);
}
function add(isInsert){
	console.log("${customer.isInitSign}"!=1);
	if ("${customer.mustImportTemp}"=="1"){
		var recordData=$('#'+tableId).jqGrid('getGridParam','records');
		if(($("#mainTempNo").val()=="" || recordData==0) && isService!='1' && "${customer.isInitSign}"!=1 ){
			art.dialog({
				content: "请先从模板导入预算"
			});
			return;
		}
	}
 	var posi = $('#'+tableId).jqGrid('getGridParam', 'selrow')-1;
 	var ret = selectDataTableRow(tableId);
 	if(isInsert&&!ret){
	 	art.dialog({
			content: "请选择一条记录"
		});
		return;	
 	}
 	var itemType2Str="";
 	var mainTempNo="";
 	if ($("#mainTempNo").val()!=""){
		var smainTempNo=$("#mainTempNo").val().trim();
		smainTempNo=smainTempNo.split("|");
		mainTempNo=smainTempNo[0];
	}
 	if(ret) itemType2Str+="&itemType2="+ret.itemtype2;
 	var url="${ctx}/admin/itemPlan/goYsQuickSave?custCode=${customer.code}&itemType1=ZC&isOutSet=${customer.isOutSet}&custType=${customer.custType}&isService="+isService+itemType2Str+"&mainTempNo="+mainTempNo;
 	if(ret){
 		url="${ctx}/admin/itemPlan/goYsQuickSave?custCode=${customer.code}&itemType1=ZC&isOutSet=${customer.isOutSet}&custType=${customer.custType}&isService="+isService+"&fixAreaPk="+ret.fixareapk+itemType2Str+"&mainTempNo="+mainTempNo;
	}
 	url+="&mustImportTemp="+"${customer.mustImportTemp}",
 	
	Global.Dialog.showDialog("quickSave",{
		title:"主材预算--快速新增",
		url:url,
		height:750,
		width:1380,
		resizable:false, 
		returnFun: function(data){	
			var arr=$('#'+tableId).jqGrid("getRowData");
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
				v.canmodiqty='1';
				v.canmodiqtydescr='是';
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
		    	$('#'+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params},page:1}).trigger("reloadGrid");  		 
			}
	});
}

function update(){
	var ret = selectDataTableRow(tableId);
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
	if ("${customer.mustImportTemp}"=="1"){
		var recordData=$('#'+tableId).jqGrid('getGridParam','records');
		if(($("#mainTempNo").val()==""||recordData==0)&&isService!='1' && "${customer.isInitSign}"!=1 ){
			art.dialog({
				content: "请先从模板导入预算"
			});
			return;
		}
	}
	Global.Dialog.showDialog("itemPlanCopy",{
		  title:"搜寻--客户编号",
		  url:"${ctx}/admin/itemPlan/goCopy?itemType1=ZC&custCode=${customer.code}&isService="+isService+"&custType=${customer.custType}",
		  height: 600,
		  width:1000,
		  returnFun: function(data){
		  		$.each(data,function(k,v){
		  			hasEdit=true;
		  			$("#"+tableId).addRowData(k+1, v, "last");
		  		})
		  }
	});
}
function view(){
	var rowId = $('#'+tableId).jqGrid('getGridParam', 'selrow');
    if (rowId) {
       var itemCode=$('#'+tableId).getCell(rowId,"itemcode");
       var pk=$('#'+tableId).getCell(rowId,"pk");
       Global.Dialog.showDialog("ys_detailView",{
		  title:"主材预算--查看",
		  url:"${ctx}/admin/itemPlan/goItemPlan_ys_detailView?itemType1=ZC&custCode=${customer.code}&isOutSet=${customer.isOutSet}&rowId="+rowId+"&isService="+isService+"&itemCode="+itemCode+"&pk="+pk,
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
	var rowId = $('#'+tableId).jqGrid('getGridParam', 'selrow');
    if (rowId) {
	     var itemCode=$('#'+tableId).getCell(rowId,"itemcode");
	     var pk=$('#'+tableId).getCell(rowId,"pk");
	     Global.Dialog.showDialog("ys_detailViewEdit",{
			  title:"主材预算--编辑",
			  url:"${ctx}/admin/itemPlan/goItemPlan_ys_detailViewEdit?itemType1=ZC&custCode=${customer.code}&isOutSet=${customer.isOutSet}&rowId="+rowId+"&isService="+isService+"&itemCode="+itemCode+"&pk="+pk,
			  height:600,
			  width:1000,
			  resizable:false, 
			  returnFun: function(data){
			      if(data.fixareapk) {
			      	data.lastupdate=getNowFormatDate();
					data.lastupdatedby='${customer.lastUpdatedBy}';
			      	hasEdit=true;
				    $('#'+tableId).setRowData(rowId,data);
				   	var params=JSON.stringify($('#'+tableId).jqGrid("getRowData"));
				  	$('#'+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params},page:1}).trigger("reloadGrid");  
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
	var rowId = $('#'+tableId).jqGrid('getGridParam', 'selrow');
    if (rowId) {
        art.dialog({
			 content:'确定删除该记录吗？',
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
			 	 hasEdit=true;
		     	 var ret = selectDataTableRow(tableId);
			     var rowNum=$('#'+tableId).jqGrid('getGridParam','records');
			     $('#'+tableId).delRowData(rowId);
			     var params=JSON.stringify($('#'+tableId).jqGrid("getRowData"));
				 $('#'+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params},page:1}).trigger("reloadGrid");  
				 setTimeout(function(){moveToNext(rowId,rowNum,tableId);$("#delBtn").focus();},100);
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

function checkbox(cellvalue, options, rowObject) {
    return '<input type="checkbox" class="ibox" onclick="updateSelectAllCheckbox(event)"/>'
}

function updateSelectAllCheckbox(event) {
    var grid = $(event.target).parents("table")
    var tableId = grid.prop("id")
    var th = $("#" + tableId + "_checkbox")
    
    if (!event.target.checked) {
        $("input", th).prop("checked", false)
    } else {
        var checkedAll = true
        
        $("td input.ibox", grid).each(function(index, element) {
            if (!element.checked) {
                checkedAll = false
                return
            }
        })
        
        if (checkedAll) $("input", th).prop("checked", true)
    }
}

function setSelectAllCheckbox(tableId) {
    var th = $("#" + tableId + "_checkbox")
    th.off("click")
    th.prop("title", "")
    
    $("input", th).on("click", function(event) {
        $("td input.ibox", $("#" + tableId)).each(function(index, element) {
            element.checked = event.target.checked
        })
    })
}

function batchDelete() {
    var grid = $('#' + tableId)    
    var selectedRows = $('td input.ibox:checked', grid)
    var ids = []
    
    selectedRows.each(function(index, element) {
        var elementId = $(element).parent().parent().prop('id')
        var rowid = parseInt(elementId.substring(elementId.lastIndexOf('_') + 1))
        ids.unshift(rowid)
    })
    
    if (!ids.length) {
        art.dialog({content: "没有要删除的记录"})
        return
    }
    
    hasEdit = true

    for (var i = 0; i < ids.length; i++) {
        grid.delRowData(ids[i])
    }
    
    $('input', $("#" + tableId + "_checkbox")).prop('checked', false)
    
    var params = JSON.stringify(grid.getRowData())
    
    $('#' + tableId).setGridParam({
        url: "${ctx}/admin/itemPlan/goTmpJqGrid",
        postData: {params: params},
        page: 1
    }).trigger("reloadGrid")
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
		sContent= '是否清除【${customer.code}-${customer.address}】的主材预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
	}
	art.dialog({
		 content:sContent,
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
			 hasEdit=true;
			 var ischecked=$("#clearDetail").is(':checked');
			 $('#'+tableId).jqGrid('clearGridData');
			 $("#mainTempNo").val("");
			 if(ischecked){
	 			var datas=$("#dataForm").jsonForm();
				datas.isService=isService;
				datas.isClearFixArea=1;
					$.ajax({
							url:'${ctx}/admin/itemPlan/doClear',
							type:"post",
				            dataType:"json",
				            async:false,
				            data:datas,
							cache: false,
						    success: function(obj){
						    	$("#_form_token_uniq_id").val("");
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
function loadOperate(){	
	var qtyModifyCount=0; markupModifyCount=0;cannotModifyQtyCount=0;
	var rowData = $("#dataTable").jqGrid('getRowData');
	var rowData2 = $("#serviceDataTable").jqGrid('getRowData');
	if (rowData){
		$.each(rowData,function(k,v){
			if(v.qtymodifycount) qtyModifyCount++;
			if(v.markupmodifycount) markupModifyCount++;
			if(v.qtymodifycount && v.canmodiqty=="0" && v.isoutset=="0") cannotModifyQtyCount++;
			
		})
	}
	if (rowData2){
		$.each(rowData2,function(k,v){
			if(v.qtymodifycount) qtyModifyCount++;
			if(v.markupmodifycount) markupModifyCount++;
			if(v.qtymodifycount && v.canmodiqty=="0" && isoutset=="0") cannotModifyQtyCount++;
		})
	}
	$("#qtyModifyCount").text(qtyModifyCount);
	$("#markupModifyCount").text(markupModifyCount);
	$("#cannotModifyQtyCount").text(cannotModifyQtyCount);
	if(rowData||rowData2){
		loadDifPrePlanAreaCount(rowData,rowData2);
	}else{
		$("#difPrePlanAreaCount").text(0);
	}
	
	isShowModifyLable();	
}

function isShowModifyLable(){	
	 if ($("#qtyModifyCount").text()>0){
	 	$("#qtyModify_show").show();
	 }else{
	 	$("#qtyModify_show").hide();
	 }
	 if ($("#markupModifyCount").text()>0){
	 	$("#markupModify_show").show();
	 }else{
	 	$("#markupModify_show").hide();
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
function loadDifPrePlanAreaCount(rowDataZC,rowDataSer){
	var difPrePlanAreaCount=0,flagDif=true;
	if('${arryPrePlanAreaDescr}'){
		var arryPrePlanAreaDescr =JSON.parse('${arryPrePlanAreaDescr}');
		if(arryPrePlanAreaDescr.length>0){
			for(var i=0;i<arryPrePlanAreaDescr.length;i++){
				if (rowDataZC){
					$.each(rowDataZC,function(k,v){
						if(arryPrePlanAreaDescr[i] == v.preplanareadescr){ 
				           flagDif=false;
				           return false;
				      	}
					});
				}
				if (rowDataSer){
					if(!flagDif){
						$.each(rowDataSer,function(k,v){
							if(arryPrePlanAreaDescr[i] == v.preplanareadescr){
						         flagDif=false;
						        return false;
							 }
						}); 
					}
				}
				if(flagDif){ 
					difPrePlanAreaCount++; 
				}else{
					flagDif=true;
				}		
		    };  
		}
	 }
	 $("#difPrePlanAreaCount").text(difPrePlanAreaCount);
}
function goModifyDetailView(){
 	Global.Dialog.showDialog("itemPlan_priceUpdateView",{
		  title:"查看修改明细",
		  url:"${ctx}/admin/itemPlan/goItemPlan_modifyDetailView",
		  height:600,
		  width:1000,
		  postData:{custCode:"${customer.code}"}
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
		$("#importBtn").attr("disabled","disabled");
		$("#quickLoad").attr("disabled","disabled");
		$("#copyService").attr("disabled","disabled");
		$("#regenLoad").attr("disabled","disabled");
		$("#fromTemLoad").attr("disabled","disabled");
		isCellEdit=false;	
	}
	$("#status_NAME").attr("disabled","disabled");
	$('*','#dataForm').bind('focus',function(){data_iRow=0;}); 
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemPlan/goItemPlanJqGrid?custCode=${customer.code}&itemType1=ZC&isService=0",
			height:$(document).height()-$("#content-list").offset().top-70,
			cellEdit:isCellEdit,
			cellsubmit:'clientArray',
			rowNum:1000,
			colModel : [
				{name: "pk", index: "pk", width: 122, label: "编号", sortable:false, align: "left", hidden: true},
				{name: "checkbox", index: "checkbox", width: 40, label: "<input type='checkbox'/>", sortable:false, align: "center", title: false, formatter: checkbox},
				{name: "iscommi", index: "iscommi", width: 84, label: "审核标识", sortable: false, align: "left",formatter:function(){return 1;}, hidden: true},
				{name: "fixareapk", index: "fixareapk", width: 90, label: "区域名称", sortable:false, align: "left", hidden: true},
				{name: "fixareadescr", index: "fixareadescr", width: 82, label: "区域名称", sortable:false, align: "left"},
				{name: "isfixprice", index: "isfixprice", width: 122, label: "是否固定价格", sortable:false, align: "left", hidden: true},
				{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable:false, align: "left", hidden: true},
				{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable:false, align: "left", hidden: true},
				{name: "itemcode", index: "itemcode", width: 65, label: "材料编号", sortable:false, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable:false, align: "left",editable:true,edittype:'text',
							editoptions:{
								dataEvents:[{
									type:'focus',
									fn:function(e){
										var rowid = $("#"+tableId).getGridParam( "selrow" );
									    var rowData = $("#"+tableId).jqGrid('getRowData',rowid);
									    if(rowData.isoutset=='1'){
									    	$(e.target).openComponent_itemDescr({condition:{'itemType1':"ZC",'containCode':"1",custType:"${customer.custType}"},onSelect:onSelect})
									    }else{
									    	$(e.target).openComponent_custTypeItemDescr({condition:{'itemType1':"ZC",custType:"${customer.custType}",isDescrWithPrice:"1",prePlanAreaPk:rowData.preplanareapk },onSelect:onSelect});
									    }   		
							        }
								}]
							}
				},
				{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
				{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
				{name: "algorithm", index: "algorithm", width: 0.5, label: "算法编号", sortable:false, align: "left"},
				{name: "algorithmdescr", index: "algorithmdescr", width: 80, label: "算法", sortable: false, align: "left",editable:true,
					edittype:'select',
					editoptions:{ 
			  			dataUrl: '${ctx}/admin/item/getAlgorithm',
						postData: function(){
							var ret = selectDataTableRow(tableId);
							if(ret){
								return {
									code: ret.itemcode
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
			  		  ]
				  	}
		 		},
				{name: "projectqty", index: "projectqty", width: 80, label: "预估施工量", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
				{name: "qty", index: "qty", width: 70, label: "数量", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
				{name: "autoqty", index: "autoqty", width: 70, label: "系统算量", sortable:false, align: "right"},
				{name: "uom", index: "uom", width: 40, label: "单位", sortable:false, align: "left"},
				{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
				{name: "beflineamount", index: "beflineamount", width: 85, label: "折扣前金额", sortable:false, align: "right", sum: true},
				{name: "markup", index: "markup", width: 60, label: "折扣", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,minValue:0} },
				{name: "tmplineamount", index: "tmplineamount", width: 80, label: "小计", sortable:false, align: "right", sum: true},
				{name: "processcost", index: "processcost", width: 80, label: "其他费用", sortable:false, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
				{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable:false, align: "right", sum: true},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 40, label: "套外", sortable:false, align: "left", hidden: true},
				{name: "remark", index: "remark", width: 140, label: "材料描述", sortable:false, align: "left",editable:true,edittype:'textarea'},
				{name: "cuttype", index: "cuttype",label: "切割类型编号",  width: 0.5,sortable:false, align: "left"},
				{name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left",editable:true,edittype:'select',
					editoptions:{ 
			  			dataUrl: '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
						postData: function(){
							var ret = selectDataTableRow(tableId);
							if(ret){
								return {
									itemCode: ret.itemcode
								};
							}
							return {code: ""};
						},
						buildSelect: function(e){
							var lists = JSON.parse(e);
							var html = "<option value=\"\" ></option>";
							for(var i = 0; lists && i < lists.length; i++){
								html += "<option value=\""+ lists[i].Code +"\">" + lists[i].fd + "</option>"
							}
							return "<select style=\"font-family:宋体;font-size:12px\"> " + html + "</select>";
						},
			  			dataEvents:[{
		  					type:'change',
		  					fn:function(e){ 
		  						cutTypeClick(e);
		  					}
			  			}, 
			  		
			  		]}
		 		},
		 		{name: "giftdescr", index: "giftdescr", width: 80, label: "赠送项目", sortable: true, align: "left"},
				{name: "isgift", index: "isgift", width: 0.5, label: "是否赠送项目", sortable: true, align: "left"},
				{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
				{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
				{name: "supplpromitempk", index: "supplpromitempk", width: 0.5, label: "供应商促销pk", sortable:false, align: "left"},
				{name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
				{name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
				{name: "canmodiqty", index: "canmodiqty", width: 78, label: "数量可修改", sortable: true, align: "left", hidden: true},
	       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable:false, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable:false, align: "left"},
				{name: "expired", index: "expired", width: 77, label: "是否过期", sortable:false, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable:false, align: "left"},
				{name: "dispseq", index: "dispseq", width: 84, label: "dispseq", sortable:false, align: "left", hidden: true},
				{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable:false, align: "left", hidden: true},
				{name: "isoutset", index: "isoutset", width: 122, label: "是否套餐外", sortable:false, align: "left",hidden:true},
				{name: "isservice", index: "isservice", width: 122, label: "是否服务性产品", sortable:false, align: "left",formatter:function(){return 0}, hidden: true},
				{name: "cost", index: "cost", width: 122, label: "成本", sortable:false, align: "right",hidden: true},
	            {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable:false, align: "left"},
	            {name: "preplanareapk", index: "preplanareapk", width: 60, label: "空间pk", sortable:false, align: "left", hidden: true},
	       		{name: "doorpk", index: "doorpk", width: 65, label: "门窗Pk",  width: 0.5,sortable:false, align: "left"},
	       		{name: "tempdtpk", index: "tempdtpk",  label: "模板pk",  width: 0.01,sortable:false, align: "left"},
	       		{name: "qtymodifycount", index: "qtymodifycount", width: 84, label: "数量被修改", sortable:false, align: "left", hidden: true},
			    {name: "markupmodifycount", index: "markupmodifycount", width: 122, label: "折扣被修改", sortable:false, align: "left", hidden: true},
			    {name: "oldprojectcost", index: "oldprojectcost", width: 90, label: "原项目经理结算价", sortable:false, align: "right",hidden: true},
			    {name: "projectcost", index: "projectcost", width: 90, label: "升级项目经理结算价", sortable:false, align: "right",hidden: true},
            ],
			gridComplete:function(){
				if(isService==0){
					if($("#dataTable").jqGrid('getGridParam','records')==0) $("#copy").show();
					else $("#copy").hide();
				}
				var rowData = $("#dataTable").jqGrid('getRowData');
				loadFee(rowData);
				var ids=$("#dataTable").jqGrid("getDataIDs");
            	$.each(rowData,function(k,v){
            		if(v.qty==v.autoqty&&v.qtymodifycount&&v.algorithm){ // &&v.algorithm!="18"
	               		$("#dataTable").setCell(k+1,'qtymodifycount'); 
	               	}else if(v.qty!=v.autoqty&&!v.qtymodifycount&&v.algorithm){ //&&v.algorithm!="18"
	                	$("#dataTable").setCell(k+1,'qtymodifycount',1);
	               	} 
	               	if(v.markup==100&&v.markupmodifycount){
	               		$("#dataTable").setCell(k+1,'markupmodifycount');
	               	} else if(v.markup!=100&&!v.markupmodifycount){
	                	$("#dataTable").setCell(k+1,'markupmodifycount',1);
	               	}
	               	

	            	/* if(!(isfixprice=='0'&&${customer.isOutSet}=='1')){
						$("#dataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
					} */
					if(!(v.isfixprice=='0'&& v.isoutset=='1')){
						$("#dataTable").jqGrid('setCell', k+1, 'unitprice', '', 'not-editable-cell');
					}
					// 不可修改数量
					if(v.canmodiqty=='0'){
						$("#dataTable").jqGrid('setCell', k+1, 'qty', '', 'not-editable-cell');
					}
					
				 	// 套餐材料，不允许直接在材料名称里修改   
					// if(v.isoutset!="1")  $("#dataTable").jqGrid('setCell', k+1, 'itemdescr', '', 'not-editable-cell');
           		 })
           		 loadOperate();
			},
            afterSaveCell:function(id,name,val,iRow,iCol){
            	hasEdit=true;
                var rowData = $("#dataTable").jqGrid('getRowData',id);
                var processcost=parseFloat(rowData.processcost);
                var qtymodifynums=$("#qtyModifyCount").text(), markupmodifynums=$("#markupModifyCount").text();
                if(name=='itemdescr')
	            	$("#dataTable").setCell(id,'itemdescr',itemdescr);
                switch (name){
                	case 'qty':
	                	$("#dataTable").setCell(id,'beflineamount',myRound(val*rowData.unitprice,4));
	                	$("#dataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100));
	                	$("#dataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100)+processcost));
	                	if(val==rowData.autoqty&&rowData.qtymodifycount&&rowData.algorithm){  //&&rowData.algorithm!="18"
		               		$("#dataTable").setCell(id,'qtymodifycount');
			               	$("#qtyModifyCount").text(--qtymodifynums);
		               	}else if(val!=rowData.autoqty&&!rowData.qtymodifycount&&rowData.algorithm){ //&&rowData.algorithm!="18"
		                	$("#dataTable").setCell(id,'qtymodifycount',1);
				            $("#qtyModifyCount").text(++qtymodifynums);
		               	} 
		                if(rowData.cuttype!='' && rowData.isoutset=='1'){
		                	changeAlgorithm(id,'2');	
		                }	
		               	isShowModifyLable();
                		break;
                	case 'unitprice':
	                	$("#dataTable").setCell(id,'beflineamount',myRound(val*rowData.qty,4));
	                	$("#dataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.qty,4)*rowData.markup/100));
	                	$("#dataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.qty,4)*rowData.markup/100)+processcost));
	                	break;
	                	case 'markup':
	                	$("#dataTable").setCell(id,'tmplineamount',myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100));
	                	$("#dataTable").setCell(id,'lineamount',myRound(myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100)+processcost));
	                	if(val==100&&rowData.markupmodifycount){
		               		$("#dataTable").setCell(id,'markupmodifycount');
			            	$("#markupModifyCount").text(--markupmodifynums);
		               	} else if(val!=100&&!rowData.markupmodifycount){
		               		$("#dataTable").setCell(id,'markupmodifycount',1);
			            	$("#markupModifyCount").text(++markupmodifynums);
		               	} 
		               	isShowModifyLable();
	                	break;
                	case 'processcost':
	                	$("#dataTable").setCell(id,'lineamount',myRound(parseFloat(rowData.tmplineamount)+processcost));
	                	break;
                	case 'algorithmper':
		            	if(rowData.algorithm != '' ){
		            		changeAlgorithm(id, '1');		
			               	isShowModifyLable();	
		                }
		        		break;
	        		case 'algorithmdeduct':
			        	if(rowData.algorithm !='' ){
		            		changeAlgorithm(id, '1');		
			               	isShowModifyLable();	
		                }
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
	            //itemdescr=$('#dataTable').getCell(id,"itemdescr");
	            data_iCol=0;
                setTimeout(relocate, 100, id)
		         
          	},
           	beforeEditCell:function(rowid,cellname,value,iRow,iCol){
	            data_iRow=iRow;
	            data_iCol=iCol;
	            itemdescr=$('#dataTable').getCell(rowid,"itemdescr");
	       }
	});
	Global.JqGrid.initJqGrid("serviceDataTable",{
		url:"${ctx}/admin/itemPlan/goItemPlanJqGrid?custCode=${customer.code}&itemType1=ZC&isService=1",
		height:526-$("#content-list2").offset().top-140,
		cellEdit:isCellEdit,
		cellsubmit:'clientArray',
		rowNum:1000,
		colModel : [
		    {name: "checkbox", index: "checkbox", width: 40, label: "<input type='checkbox'/>", sortable:false, align: "center", title: false, formatter: checkbox},
			{name: "pk", index: "pk", width: 122, label: "编号", sortable:false, align: "left", hidden: true},
			{name: "iscommi", index: "iscommi", width: 84, label: "是否提成", sortable: false, align: "left",formatter:loadCommi,hidden: true},
			{name: "checkBtn", index: "checkBtn", width: 84, label: "是否提成", sortable: false, align: "center", formatter:checkBtn,np:true},
			{name: "fixareapk", index: "fixareapk", width: 82, label: "区域名称", sortable:false, align: "left", hidden: true},
			{name: "fixareadescr", index: "fixareadescr", width: 82, label: "区域名称", sortable:false, align: "left"},
			{name: "isfixprice", index: "isfixprice", width: 122, label: "是否固定价格", sortable:false, align: "left", hidden: true},
			{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable:false, align: "left", hidden: true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable:false, align: "left", hidden: true},
			{name: "itemcode", index: "itemcode", width: 65, label: "材料编号", sortable:false, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable:false, align: "left",editable:true,edittype:'text',
				editoptions:{
					dataEvents:[{
						type:'focus',
						fn:function(e){
							var rowid = $("#"+tableId).getGridParam( "selrow" );
						    var rowData = $("#"+tableId).jqGrid('getRowData',rowid);
						    if(rowData.isoutset=='1'){
						    	$(e.target).openComponent_itemDescr({condition:{'itemType1':"ZC",'containCode':"1",custType:"${customer.custType}"},onSelect:onSelect})
						    }else{
						    	$(e.target).openComponent_custTypeItemDescr({condition:{'itemType1':"ZC",custType:"${customer.custType}",isDescrWithPrice:"1"},onSelect:onSelect});
						    }   		
				        }
					}]
				}
			},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
			{name: "algorithm", index: "algorithm", width: 0.5, label: "算法编号", sortable:false, align: "left"},
			{name: "algorithmdescr", index: "algorithmdescr", width: 80, label: "算法", sortable: true, align: "left",editable:true,
				edittype:'select',
				editoptions:{ 
		  			dataUrl: '${ctx}/admin/item/getAlgorithm',
					postData: function(){
						var ret = selectDataTableRow(tableId);
						if(ret){
							return {
								code: ret.itemcode
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
			{name: "projectqty", index: "projectqty", width: 80, label: "预估施工量", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "qty", index: "qty", width: 70, label: "数量", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "autoqty", index: "autoqty", width: 80, label: "系统算量", sortable:false, align: "right"},
			{name: "uom", index: "uom", width: 50, label: "单位", sortable:false, align: "left"},
			{name: "unitprice", index: "unitprice", width: 70, label: "单价", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable:false, align: "right", sum: true},
			{name: "markup", index: "markup", width: 60, label: "折扣", sortable:false, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,minValue:0}},
			{name: "tmplineamount", index: "tmplineamount", width: 80, label: "小计", sortable:false, align: "right", sum: true},
			{name: "processcost", index: "processcost", width: 80, label: "其他费用", sortable:false, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable:false, align: "right", sum: true},
			{name: "isoutsetdescr", index: "isoutsetdescr", width: 40, label: "套外", sortable:false, align: "left", hidden: true},
			{name: "remark", index: "remark", width: 140, label: "材料描述", sortable:false, align: "left",editable:true,edittype:'textarea'},
			{name: "cuttype", index: "cuttype",width: 0.5,label: "切割类型编号",  sortable:false, align: "left"},
			{name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left",editable:true,edittype:'select',
				editoptions:{ 
		  			dataUrl: '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
					postData: function(){
						var ret = selectDataTableRow(tableId);
						if(ret){
							return {
								itemCode: ret.itemcode
							};
						}
						return {code: ""};
					},
					buildSelect: function(e){
						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
						for(var i = 0; lists && i < lists.length; i++){
							html += "<option value=\""+ lists[i].Code +"\">" + lists[i].fd + "</option>"
						}
						return "<select style=\"font-family:宋体;font-size:12px\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						cutTypeClick(e);
	  					}
		  			}, 
		  		
		  		]}
	 		},
	 		{name: "giftdescr", index: "giftdescr", width: 80, label: "赠送项目", sortable: true, align: "left"},
			{name: "isgift", index: "isgift", width: 0.5, label: "是否赠送项目", sortable: true, align: "left"},
			{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
			{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
			{name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "canmodiqty", index: "canmodiqty", width: 78, label: "数量可修改", sortable: true, align: "left", hidden: true},
       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable:false, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable:false, align: "left"},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable:false, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable:false, align: "left"},
			{name: "dispseq", index: "dispseq", width: 84, label: "dispseq", sortable:false, align: "left", hidden: true},
		    {name: "isoutset", index: "isoutset", width: 122, label: "是否套餐外", sortable:false, align: "left", hidden: true},
	        {name: "isservice", index: "isservice", width: 122, label: "是否服务性产品", sortable:false, align: "left",formatter:function(){return 1}, hidden: true},
	        {name: "cost", index: "cost", width: 122, label: "成本", sortable:false, align: "right", hidden: true},
	        {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable:false, align: "left"},
	        {name: "preplanareapk", index: "preplanareapk", width:0.5, label: "空间pk", sortable:false, align: "left"},
       		{name: "doorpk", index: "doorpk", width: 0.5, label: "doorpk", sortable:false, align: "left"},
       		{name: "tempdtpk", index: "tempdtpk",  label: "模板pk",  width: 0.01,sortable:false, align: "left"},
       		{name: "qtymodifycount", index: "qtymodifycount", width: 84, label: "数量被修改", sortable:false, align: "left", hidden: true},
		    {name: "markupmodifycount", index: "markupmodifycount", width: 122, label: "折扣被修改", sortable:false, align: "left", hidden: true},
		    {name: "oldprojectcost", index: "oldprojectcost", width: 90, label: "原项目经理结算价", sortable:false, align: "right", hidden: true},
		    {name: "projectcost", index: "projectcost", width: 90, label: "升级项目经理结算价", sortable:false, align: "right", hidden: true},
        ],
		gridComplete:function(){
			if(isService==1){
				if($("#serviceDataTable").jqGrid('getGridParam','records')==0) $("#copyService").show();
				else $("#copyService").hide();
			}
			loadMainServFee();
			/* var ids=$("#serviceDataTable").jqGrid("getDataIDs"); */
			var rowData = $("#serviceDataTable").jqGrid('getRowData');
             $.each(rowData,function(k,v){
             	if(v.qty==v.autoqty&&v.qtymodifycount&&v.algorithm){ //&&v.algorithm!="18"
	               		$("#serviceDataTable").setCell(k+1,'qtymodifycount');
               	}else if(v.qty!=v.autoqty&&!v.qtymodifycount&&v.algorithm){// &&v.algorithm!="18"
                	$("#serviceDataTable").setCell(k+1,'qtymodifycount',1);
               	} 	
               	if(v.markup==100&&v.markupmodifycount){
	               		$("#serviceDataTable").setCell(k+1,'markupmodifycount');
               	} else if(v.markup!=100&&!v.markupmodifycount){
                	$("#serviceDataTable").setCell(k+1,'markupmodifycount',1);
               	} 	
            	if(v.iscommi=="1") $("#serviceDataTable tbody:first tr#"+(k+1)).find("td").addClass("checkColor");
				if(!(v.isfixprice=='0'&&v.isoutset=='1')){
					//$("#serviceDataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
					$("#serviceDataTable").jqGrid('setCell', k+1, 'unitprice', '', 'not-editable-cell');
				}
				
				// 不可修改数量
				if(v.canmodiqty=='0'){
					$("#serviceDataTable").jqGrid('setCell', k+1, 'qty', '', 'not-editable-cell');
				}
				 //套餐材料，不允许直接在材料名称里修改
				// if(v.isoutset!="1")  $("#serviceDataTable").jqGrid('setCell', k+1, 'itemdescr', '', 'not-editable-cell');
            })
            loadOperate();

		},
        afterSaveCell:function(id,name,val,iRow,iCol){
           	hasEdit=true;
            var rowData = $("#serviceDataTable").jqGrid('getRowData',id);
            var processcost=parseFloat(rowData.processcost);
            var qtymodifynums=$("#qtyModifyCount").text(), markupmodifynums=$("#markupModifyCount").text();
            if(name=='itemdescr')
	            $("#serviceDataTable").setCell(id,'itemdescr',itemdescr);
            switch (name){
            	case 'qty':
	            	$("#serviceDataTable").setCell(id,'beflineamount',myRound(val*rowData.unitprice,4));
	            	$("#serviceDataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100));
	            	$("#serviceDataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100)+processcost));
	            	if(val==val.autoqty&&rowData.qtymodifycount&&rowData.algorithm){ //&&rowData.algorithm!="18"
		               		$("#serviceDataTable").setCell(id,'qtymodifycount');
			               	$("#qtyModifyCount").text(--qtymodifynums);
	               	}else if(val!=rowData.autoqty&&!rowData.qtymodifycount&&rowData.algorithm){// &&rowData.algorithm!="18"
	                	$("#serviceDataTable").setCell(id,'qtymodifycount',1);
			             $("#qtyModifyCount").text(++qtymodifynums);
	               	} 	
	                if(rowData.cuttype!='' && rowData.isoutset=='1'){
	                	changeAlgorithm(id,'2');	
	                }
	            	isShowModifyLable();
	            	break;
            	case 'unitprice':
	            	$("#serviceDataTable").setCell(id,'beflineamount',myRound(val*rowData.qty,4));
	            	$("#serviceDataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.qty,4)*rowData.markup/100));
	            	$("#serviceDataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.qty,4)*rowData.markup/100)+processcost));
	            	break;
            	case 'markup':
	            	$("#serviceDataTable").setCell(id,'tmplineamount',myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100));
	            	$("#serviceDataTable").setCell(id,'lineamount',myRound(myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100)+processcost));
	            	if(val==100&&rowData.markupmodifycount){
	               		$("#serviceDataTable").setCell(id,'markupmodifycount');
		            	$("#markupModifyCount").text(--markupmodifynums);
	               	} else if(val!=100&&!rowData.markupmodifycount){
	               		$("#serviceDataTable").setCell(id,'markupmodifycount',1);
		            	$("#markupModifyCount").text(++markupmodifynums);
	               	} 
	               	isShowModifyLable();	
	            	break;
            	case 'processcost':
	            	$("#serviceDataTable").setCell(id,'lineamount',myRound(parseFloat(rowData.tmplineamount)+processcost));
	            	break;
            	case 'algorithmper':
	            	if(rowData.algorithm != '' ){
	            		changeAlgorithm(id, '1');		
		               	isShowModifyLable();	
	                }
	        		break;
	        	case 'algorithmdeduct':
		        	if(rowData.algorithm != '' ){
	            		changeAlgorithm(id, '1');		
		               	isShowModifyLable();	
	                }
	        		break;
            }
            reloadSummary("serviceDataTable");
            loadMainServFee();
		},
        beforeSelectRow:function(id){
	       	$(".search-suggest").hide();
	       	data_iCol=0;
	        /* itemdescr=$('#serviceDataTable').getCell(id,"itemdescr"); */
            setTimeout(relocate, 100, id, 'serviceDataTable')
        },
        beforeEditCell:function(rowid,cellname,value,iRow,iCol){
            data_iRow=iRow;
            data_iCol=iCol;
            itemdescr=$('#serviceDataTable').getCell(rowid,"itemdescr");
        }
		
	});
	Global.JqGrid.initJqGrid("preDataTable",{
		url:"${ctx}/admin/prePlan/goJqGrid?custCode=${customer.code}",
		height:526-$("#content-list2").offset().top-140,
		rowNum:1000,
		colModel : [
			{name: "No", index: "No", width: 122, label: "编号", sortable:false, align: "left", hidden: true},
        ]
	});
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		if(e.currentTarget.id){
	    	isService=1;
	    	tableId="serviceDataTable";
	    	$("#copy").hide();
	    	if($("#"+tableId).jqGrid('getGridParam','records')==0) $("#copyService").show();
    	} else{
    		isService=0;
	    	tableId="dataTable";
	    	$("#copyService").hide();
	    	if($("#"+tableId).jqGrid('getGridParam','records')==0) $("#copy").show();
    	} 
	});
	
	$("#supplProm").on("click",function(){
		var rowId = $("#dataTable").jqGrid('getGridParam','selrow');
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
 		Global.Dialog.showDialog("SupplProm",{
			title:"主材预算——供应商促销",
			url:"${ctx}/admin/itemChg/goSupplProm",
			postData:{cost:ret.cost,itemCode:ret.itemcode},
			height:800,
			width:1050,
			returnFun: function (data) {
				if(data){
					if(!ret.cost > data.promcost){
						art.dialog({
							content:"当前成本小于活动成本，修改失败",
						});
						return;
					}
					$("#dataTable").setCell(rowId, 'remark',ret.remark + " 参与活动："+data[0].actdescr+",活动价： "+data[0].promprice);
					//修改表明细数据
					$("#dataTable").setCell(rowId, 'supplpromitempk',data[0].pk );
					$("#dataTable").setCell(rowId, 'supplpromdescr',data[0].actdescr);
					$("#dataTable").setCell(rowId, 'cost',data[0].promcost);
					if(ret.unitprice == 0 ){
						return;
					}
					var markUp = 1.0;
					if (ret.isoutset == "1"){ //套餐外材料 才修改折扣
						markUp = myRound(data[0].promprice/ret.unitprice,4);
						if(markUp>1){
							markUp = 1.0;
						}
					}
					$("#dataTable").setCell(rowId, 'markup',markUp*100);
					$("#dataTable").setCell(rowId, 'tmplineamount', myRound(myRound(ret.qty * ret.unitprice, 4) * markUp));
					var GridLineAmount = myRound(parseFloat(myRound(ret.qty * ret.unitprice, 4) * markUp) + parseFloat(ret.processcost));
					$("#dataTable").setCell(rowId, 'lineamount', GridLineAmount);
					
					var tmplineamount = myRound($("#dataTable").getCol('tmplineamount', false, 'sum'));
					var lineamount = myRound($("#dataTable").getCol('lineamount', false, 'sum'));
					var discAmount = parseFloat($("#discAmount").val());
					$("#dataTable").footerData('set', {"tmplineamount": tmplineamount});
					$("#dataTable").footerData('set', {"lineamount": lineamount});
					$("#befAmount").val(lineamount);
		            if (discAmount) {
						if (lineamount >= 0)  $("#amount").val(lineamount - discAmount);
						else $("#amount").val(lineamount + discAmount);
		            } else {
		            	$("#amount").val(lineamount);
		            }
				}
			}
		});
 	});
    
    setSelectAllCheckbox("dataTable")
    setSelectAllCheckbox("serviceDataTable")
	
}); // end of $(function() {})

function doSave(rowData,servRowData){
	var datas=$("#dataForm").jsonForm();
	if ($("#mainTempNo").val()!=""){
		var smainTempNo=$("#mainTempNo").val().trim();
		smainTempNo=smainTempNo.split("|");
		datas.mainTempNo=smainTempNo[0];
	}
	datas.detailJson=JSON.stringify(rowData.concat(servRowData));
	datas.isService=isService;
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
						width: 200
					});
		    	}
		  				
			}
		});
}
function save(){
	if (parseFloat($('#amount').val())<0){
		art.dialog({
				content:'主材实际总价不能小于0！'
			});
		return ;
	}
	var rowData=$('#dataTable').jqGrid("getRowData");
	var servRowData=$('#serviceDataTable').jqGrid("getRowData");
	var x=y=0;
	var sErrZC="", sErrServ="";
	$.each(rowData,function(k,v){
		if(v.qty==0&&v.processcost!=0){
			x=1;
			return false;
		}
		if(Math.abs(myRound(parseFloat(v.qty*v.unitprice)*v.markup/100+parseFloat(v.processcost))-parseFloat(v.lineamount))>1){
			sErrZC+=(k+1).toString()+','
		}
		//v.lineamount!=myRound(parseFloat(v.qty*unitprice)+parseFloat(rowData.processcost)),
	});
	$.each(servRowData,function(k,v){
		if(v.qty==0&&v.processcost!=0){
			y=1;
			return false;
		}
		if(Math.abs(myRound(parseFloat(v.qty*v.unitprice)*v.markup/100+parseFloat(v.processcost))-parseFloat(v.lineamount))>1){
			sErrServ+=(k+1).toString()+','
		}	
	});
	if($("#cannotModifyQtyCount").text()>0){
		art.dialog({
			content: '存在数量不可修改项，数量不等于系统算量,请重新生成数量'
		});
		return;		
	}
	if(sErrZC!=""){
		sErrZC=sErrZC.substring(0, sErrZC.lastIndexOf(','));
		art.dialog({
				content:'主材第'+sErrZC+'行总价计算出错'
			});
		return ;
	}
	if(sErrServ!=""){
		sErrServ=sErrServ.substring(0, sErrServ.lastIndexOf(','));
		art.dialog({
				content:'服务性产品第'+sErrServ+'行总价计算出错'
		});
		return ;
	}
	if(x==0){
		if(y==0){
			doSave(rowData,servRowData);
		}else{
			art.dialog({
				content:'服务性产品存在数量为0，其它费用不为0的材料，是否确定保存？',
				lock: true,
				width: 260,
				height: 100,
				ok: function () {
					doSave(rowData,servRowData);
					return;
				},
				cancel: function () {
					return ;
				}
			});
		}
	}else{
		if(y==0){
			art.dialog({
				 content:'主材报价存在数量为0，其它费用不为0的材料，是否确定保存？',
				 lock: true,
				 width: 260,
				 height: 100,
				 ok: function () {
					doSave(rowData,servRowData);
					return;
				 },
				 cancel: function () {
					return ;
				}
			});
		}else{
		 	art.dialog({
				content:'主材报价存在数量为0，其它费用不为0的材料，是否确定保存？',
				lock: true,
				width: 260,
				height: 100,
				ok: function () {
					art.dialog({
						content:'服务性产品存在数量为0，其它费用不为0的材料，是否确定保存？',
						lock: true,
						width: 260,
					    height: 100,
						ok: function () {
							doSave(rowData,servRowData);
							return;
						},
						cancel: function () {
							return ;
						}
					});
				},
				cancel: function () {
					return ;
				}
			});
		}
	}	
}

//导入excel
/* function goImportExcel(){
    Global.Dialog.showDialog("itemPlan_importExcel",{
		title:"主材预算明细--从excel导入",
		url:"${ctx}/admin/itemPlan/goItemPlan_importExcel?itemType1=ZC&custCode=${customer.code}&custType=${customer.custType}&isService="+isService+"&isCupboard=0",
		height:600,
		width:1000,
		returnFun: function(data){
			$.each(data,function(k,v){
				hasEdit=true;
				v.iscommi="1";
				v.isservice=isService;
				v.lastupdate=getNowFormatDate();
				v.lastupdatedby='${customer.lastUpdatedBy}';
				v.actionlog="ADD";
				$('#'+tableId).addRowData($('#'+tableId).jqGrid('getGridParam','records')+1, v, "last");
			})
		}
	});
} */
//导入excel
function goImportExcel(){
	var sMustImportTemp="0" ,stitle="主材预算明细--从excel导入";
	var rowData=$('#'+tableId).jqGrid("getRowData");
	var sContent='导入前,需要清除【${customer.code}-${customer.address}】的主材预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
	art.dialog({
  		content:'<input type="radio" checked="checked" id="excelSelect" name="Select" />从Excel导入<br/><input type="radio" id="bakSelect" name="Select" />从备份导入<br/> ',
		lock: true,
	 	width: 200,
	 	height: 100,
	 	title:"选择导入方式",
		ok: function () {
			if($("#bakSelect").is(':checked')){	
				sMustImportTemp="1";
				stitle="主材预算明细--从备份导入"	
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
	Global.Dialog.showDialog("itemPlan_importExcel_jcys",{
	  	title:stitle,
	  	url:"${ctx}/admin/itemPlan/goItemPlan_importExcel?itemType1=ZC&custCode=${customer.code}&custType=${customer.custType}&isService="+isService+"&isCupboard=0"+"&mustImportTemp="+sMustImportTemp,
	  	height:600,
	  	width:1000,
	  	returnFun: function(data){
			$.each(data,function(k,v){
				hasEdit=true;
				v.iscommi="1";
				v.isservice=isService;
				v.lastupdate=getNowFormatDate();
				v.lastupdatedby='${customer.lastUpdatedBy}';
				v.actionlog="ADD";
				$('#'+tableId).addRowData($('#'+tableId).jqGrid('getGridParam','records')+1, v, "last");
			})
		}
	});
}


//输出到Excel或备份
function exportExcel(){
  	var title,rowData;
	if(isService==0) title='-主材预算';
	else title='-服务性产品';
    var rowData=$('#'+tableId).jqGrid("getRowData");
  	if(rowData&&rowData.length>0){
	  	art.dialog({
	  		content:'<input type="radio" checked="checked" id="excelSelect" name="Select" />导出到Excel<br/><input type="radio" id="bakSelect" name="Select" />导出到备份<br/> ',
			title: '选择导出方式',
			lock: true,
		 	width: 300,
		 	height: 120,
			ok: function () {
				if($("#excelSelect").is(':checked')){
					doExcelNow('${customer.address}'+title,tableId,'dataForm');
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

function goTop(table){
	if(isService==0)  table="dataTable";
	else table="serviceDataTable";
	var rowId = $("#"+table).jqGrid('getGridParam', 'selrow');
	var replaceId=parseInt(rowId)-1;
	if(rowId){
		if(rowId>1){
				var ret1= $("#"+table).jqGrid('getRowData', rowId);
				var ret2= $("#"+table).jqGrid('getRowData', replaceId);
			    replace(rowId,replaceId,table);
			    scrollToPosi(replaceId,table);
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
function goBottom(table){
	if(isService==0)  table="dataTable";
	else table="serviceDataTable";
	var rowId = $("#"+table).jqGrid('getGridParam', 'selrow');
	var replaceId=parseInt(rowId)+1;
	var rowNum=$("#"+table).jqGrid('getGridParam','records');
	if(rowId){
		if(rowId<rowNum){
			//是否按照材料类型2分类
				var ret1= $("#"+table).jqGrid('getRowData', rowId);
				var ret2= $("#"+table).jqGrid('getRowData', replaceId);
			    replace(rowId,replaceId,table);
			    scrollToPosi(replaceId,table);
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
function loadQuickly(){
	var rowNo=$("#preDataTable").jqGrid('getGridParam','records');
	if(rowNo<1){
	 	art.dialog({
				content: "此客户不存在预报价单，无法进行快速预算导入！"
		});
		return ;	
  	} 
   	art.dialog({
		content:"此操作将清除此客户的主材和服务性产品预算，并且不可恢复，是否确定？",
		    ok: function () {
			$.ajax({
				url:"${ctx}/admin/itemPlan/doCopyPlanPre?custCode=${customer.code}",
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
		      success: function(obj){
				    if(obj.rs){
				    hasEdit=true;
				    $('#dataTable').clearGridData();
				    $('#serviceDataTable').clearGridData();
				    $.each(JSON.parse(obj.datas),function(k,v){
			  			if(v.isservice==1) $('#serviceDataTable').addRowData($('#serviceDataTable').jqGrid('getGridParam','records')+1, v, "last");
			  			else $('#dataTable').addRowData($('#dataTable').jqGrid('getGridParam','records')+1, v, "last");
			  		})
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
//从模板导入
function loadFromTem(){
	    Global.Dialog.showDialog("itemPlan_baseBatchTemp",{
		title:"主材预算--选择模板",
		url:"${ctx}/admin/itemPlan/goPrePlanTemp?custCode=${customer.code}&custType=${customer.custType}",
		height:250,
		width:400,
		returnFun: function(data){
		 	if(data.length){
		 		hasEdit=true;
			    $('#dataTable').clearGridData();
			    $('#serviceDataTable').clearGridData();
			    $.each(data,function(k,v){
		  			if(v.isservice==1) $('#serviceDataTable').addRowData($('#serviceDataTable').jqGrid('getGridParam','records')+1, v, "last");
		  			else $('#dataTable').addRowData($('#dataTable').jqGrid('getGridParam','records')+1, v, "last");
		  		})
			}
		}
	});
} 
//重新生成
function loadRegen(){
	var rowData=$('#dataTable').jqGrid("getRowData");
	var servRowData=$('#serviceDataTable').jqGrid("getRowData");
	var datas=$("#dataForm").jsonForm();
	datas.isService=isService;
	datas.detailJson=JSON.stringify(rowData.concat(servRowData));
   	art.dialog({
		content:"此操作将生成新加空间预算项目,原有预算重新计算数量，是否确定要重新生成？",
			height: 50,
		  	width:250,
		    ok: function () {
			$.ajax({
				url:"${ctx}/admin/itemPlan/doRegenFromPrePlanTemp",
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
					    $('#dataTable').clearGridData();
					    $('#serviceDataTable').clearGridData();
					    $.each(JSON.parse(obj.datas),function(k,v){
				  			if(v.isservice==1) $('#serviceDataTable').addRowData($('#serviceDataTable').jqGrid('getGridParam','records')+1, v, "last");
				  			else $('#dataTable').addRowData($('#dataTable').jqGrid('getGridParam','records')+1, v, "last");
				  		})
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
function init(){
	if('${customer.isOutSet}'=='2'){
		$("#dataTable").setGridParam().showCol("isoutsetdescr").trigger("reloadGrid");
		$("#serviceDataTable").setGridParam().showCol("isoutsetdescr").trigger("reloadGrid");
/* 		$("#dataTable").setGridParam().hideCol("preplanareadescr").trigger("reloadGrid");
		$("#dataTable").setGridParam().hideCol("autoqty").trigger("reloadGrid");
		$("#dataTable").setGridParam().hideCol("algorithmdescr").trigger("reloadGrid");	
		$("#serviceDataTable").setGridParam().hideCol("preplanareadescr").trigger("reloadGrid");
		$("#serviceDataTable").setGridParam().hideCol("autoqty").trigger("reloadGrid");
		$("#serviceDataTable").setGridParam().hideCol("algorithmdescr").trigger("reloadGrid"); */
		/* $("#mainTempNo_show").hide();	 */
	} 
	if('${customer.isOutSet}'=='1'){
		$("#quickLoad").show();
		/* $("#fromTemLoad").show();	
		$("#regenLoad").show(); */
	}    
}


function loadCommi(v,x,r){
	//新增默认提成
	if(v==undefined) return "1";
	else return v;
}
function checkBtn(v,x,r){
	if(r.iscommi=="1"||r.iscommi==undefined) return "<input type='checkbox' checked onclick='checkRow("+x.rowId+",this)' />";
	else return "<input type='checkbox' onclick='checkRow("+x.rowId+",this)' />";
}
function checkRow(id,e){
	if($(e).is(':checked')) {
		$("#serviceDataTable").setCell(id,'iscommi',"1");
		$(e).parent().parent().find("td").addClass("checkColor");
	}else{
		$("#serviceDataTable").setCell(id,'iscommi',"0");
		$(e).parent().parent().find("td").removeClass("checkColor");
	}
}
window.onfocus = function() { 
	var colModel=$("#"+tableId)[0].p.colModel;
	var index;
	$.each(colModel,function(k,v){
		if(v.name=="qty"){
		 	index=k;
		 	return false;
		} 
		
	})
	if(data_iCol==index&&editRow){
		$("#"+tableId).jqGrid("editCell",data_iRow,data_iCol,true);
	
	}
 	
}; 
window.onblur = function() { 
	editRow=data_iRow;	
};
function onSelect(json){
    var rowid = $("#"+tableId).getGridParam( "selrow" );
    var rowData = $("#"+tableId).jqGrid('getRowData',rowid);
    //var unitprice=0;
    //if(rowData.isoutset!='0') 
    var unitprice=json.price;
    var isSuitAlgorithm=true;
    $("#"+tableId).jqGrid("saveCell",rowid,data_iCol);
    if (rowData.algorithm){
    	isSuitAlgorithm=false;
	    //加载算法
		$.ajax({
			url : '${ctx}/admin/item/getAlgorithm',
			type : 'post',
			data : {
				'code' :json.code,
			},
			async:false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '加载算法数据出错~'
				});
				
			},
			success : function(obj) {
				if(obj.length==0){
					isSuitAlgorithm=false;
				}else{
	                $.each(obj, function(i, o){    
	                	if (o.Code==rowData.algorithm)
	                		isSuitAlgorithm=true;
	                });    
	            }
	            if(isSuitAlgorithm){
	            	getQtyByAlgorithm(json,rowData);
	    		}else{
	    			rowData.qty=0.0;
	    			rowData.algorithm='';
	    			rowData.algorithmdescr='';
	    			rowData.cuttype='';
	    			rowData.cuttypedescr='';
	    		}
			}
		});
	}
    if(rowData.isoutset=='0'){
    	json.descr= json.descr.substring(0, json.descr.lastIndexOf(","));
    }
    //$("#"+tableId).setRowData(rowid,{'itemcode':json.code,'itemdescr':json.descr,'sqlcodedescr':json.sqldescr,'uom':json.uomdescr,'itemtype2descr':json.itemtype2descr,'itemtype3descr':json.itemtype3descr,'marketprice':json.marketprice,'unitprice':unitprice,'beflineamount':myRound(rowData.qty*unitprice,4),'tmplineamount':myRound(rowData.qty*unitprice*rowData.markup/100),'lineamount':myRound(parseFloat(rowData.qty*unitprice*rowData.markup/100)+parseFloat(rowData.processcost)),'cost':json.cost,'remark':json.remark});
  	//选择材料后折扣改为100%
  	//选择材料后折扣改为100%
	$("#"+tableId).setRowData(rowid,{'itemcode':json.code,'itemdescr':json.descr,'sqlcodedescr':json.sqldescr,
  		'uom':json.uomdescr,'itemtype2descr':json.itemtype2descr,'itemtype3descr':json.itemtype3descr,
  		'marketprice':json.marketprice,'unitprice':unitprice,'beflineamount':myRound(rowData.qty*unitprice,4),
  		'tmplineamount':myRound(rowData.qty*unitprice),
  		'lineamount':myRound(parseFloat(rowData.qty*unitprice)+parseFloat(rowData.processcost)),
  		'cost':json.cost,'remark':json.remark,'markup':100,
  		'qty':rowData.qty,'projectqty':rowData.projectqty,'processcost':rowData.processcost,'autoqty':rowData.autoqty,
  		 custtypeitempk:json.pk,algorithm:rowData.algorithm,algorithmdescr:rowData.algorithmdescr
  	});
  	
}
//根据算法计算对应数量
function getQtyByAlgorithm(json,rowData){
	var datas=$("#dataForm").jsonForm();
	datas.custCode="${customer.code}";
	datas.itemCode=json.code;
	datas.algorithm=rowData.algorithm;
	datas.prePlanAreaPK=rowData.preplanareapk;
	datas.isService=isService;	
	datas.isOutSet=rowData.isoutset;
	datas.algorithmdeDuct=rowData.algorithmdeduct;
	datas.algorithmPer=rowData.algorithmper;
	//var zcRowData=$('#dataTable').jqGrid("getRowData");
	//var servRowData=$('#serviceDataTable').jqGrid("getRowData");
	datas.detailJson=getDetailGridDataJSON();
	$.ajax({
		url:"${ctx}/admin/itemPlan/getPrePlanQty",
			type:"post",
	        dataType:"json",
	        async:false,
	        data:datas,
			cache: false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					rowData.qty=obj.qty;
					rowData.projectqty=obj.projectqty;
					rowData.processcost=obj.processcost;
					rowData.autoqty=obj.qty;		
				}
			}
	});	

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
function FmatterPercen (cellvalue, options, rowObject){ 
	return cellvalue+"%";
}

function algorithmClick(e){
	var rowid = $('#'+tableId).getGridParam("selrow");
    var rowData = $('#'+tableId).jqGrid('getRowData', $(e.target).attr("rowid"));
    $('#'+tableId).setRowData($(e.target).attr("rowid"), {
    	algorithm:$(e.target).val(),
    });
    changeAlgorithm($(e.target).attr("rowid"),'1');
}

function cutTypeClick(e){
	var rowid = $('#'+tableId).getGridParam("selrow");
    var rowData = $('#'+tableId).jqGrid('getRowData', $(e.target).attr("rowid"));
    $('#'+tableId).setRowData($(e.target).attr("rowid"), {
    	cuttype:$(e.target).val(),
    });
    // 选择切割方式,套餐内默认不自动计算其它费用
	if(rowData.isoutset=='0'){
		return;
	}
    changeAlgorithm($(e.target).attr("rowid"),'2');
}

//根据算法变化计算其他费用 id表格行，type调用类型  1算法变动调用，2切割类型变动调用
function changeAlgorithm(id,type){
	var ret = $('#'+tableId).jqGrid('getRowData', id);
	var datas=$("#dataForm").jsonForm();
	datas.custCode="${customer.code}";
	datas.itemCode=ret.itemcode;
	datas.algorithm=ret.algorithm;
	datas.prePlanAreaPK=ret.preplanareapk;
	datas.isService=ret.isService;	
	datas.cutType=ret.cuttype;  
	datas.detailJson=getDetailGridDataJSON();
	datas.doorPK=ret.doorpk;
	datas.qty=ret.qty;
	datas.type=type;
	datas.isOutSet=ret.isoutset;
	datas.type=type;
	datas.isOutSet=ret.isoutset;
	datas.algorithmDeduct=ret.algorithmdeduct;
	datas.algorithmPer=ret.algorithmper;
	datas.tempDtPk=ret.tempdtpk; 
	$.ajax({
		url:"${ctx}/admin/itemPlan/getPrePlanQty",
			type:"post",
	        dataType:"json",
	        async:false,
	        data:datas,
			cache: false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					if(type=='2'){
						$('#'+tableId).setRowData(id,{
							'processcost':obj.processcost,
					  		'lineamount': myRound(myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(obj.processcost))
						});	
					}else{
						$('#'+tableId).setRowData(id,{
							'qty':obj.qty,'projectqty':obj.projectqty,'processcost':obj.processcost,'autoqty':obj.qty,
							'beflineamount':myRound(obj.qty*ret.unitprice,4),
					  		'tmplineamount':myRound(myRound(obj.qty*ret.unitprice,4)*ret.markup/100),
					  		'lineamount': myRound(myRound(myRound(obj.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(obj.processcost))
						});	
					}	
				}
			}
	});	
}

//获取表格json数据，在根据算法时只需要算法是3门、材料名称带门的材料、35、瓷砖踢脚线
function getDetailGridDataJSON(){
	var zcRowData=$('#dataTable').jqGrid("getRowData");
	var servRowData=$('#serviceDataTable').jqGrid("getRowData");
	var gridData =[];
	$.each(zcRowData,function(k,v){
		if(v.algorithm=='3' || v.itemdescr.indexOf("门") != -1 || v.algorithm=='35' ) {
			gridData.push(v);
		}	
	});
	$.each(servRowData,function(k,v){
			if(v.algorithm=='3' || v.itemdescr.indexOf("门") != -1 ||v.algorithm=='35') {
				gridData.push(v);
			}	
	});
	return JSON.stringify(gridData); 
}

</script>
</head> 
<body onload="init()">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBtn" type="button"  class="btn btn-system" onclick="save()">保存</button>
					<button type="button" id="saveBtn" class="btn btn-system " onclick="goModifyDetailView()">查看修改明细</button>
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					<span id="qtyModify_show" hidden="true"  style="font-size: 12px;color: red;margin-left:5px">数量与系统算量不符条数：<span id="qtyModifyCount"></span>条</span>
					<span id="markupModify_show" hidden="true" style="font-size: 12px;color: red;margin-left:5px">折扣修改条数：<span id="markupModifyCount"></span>条</span>
					<span id="difPrePlanArea_show"  hidden="true"  style="font-size: 12px;color: red;margin-left:5px">无报价空间：<span id="difPrePlanAreaCount"></span>个</span>
					<span id="cannotModifyQty_show" hidden="true"  style="font-size: 12px;color: red;margin-left:5px">空间信息发生变化，请重新生成预算数量<span hidden="true" id="cannotModifyQtyCount"></span></span>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<%-- <house:token></house:token> --%>
					<input type="hidden" id="expired" name="expired" value="${itemPlan.expired}" /> <input type="hidden"
						name="jsonString" value="" /> <input type="hidden" name="custType" value="${customer.custType }" /> <input
						type="hidden" name="lastUpdatedBy" value="${customer.lastUpdatedBy }" /> <input type="hidden"
						name="custCode" value="${customer.code }" /> <input type="hidden" name="itemType1" value="ZC" /> <input
						type="hidden" name="isService" />
							<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<ul class="ul-form">
						<li  ><label >客户编号</label> <input type="text" id="code" name="code" value="${customer.code}"
							readonly="readonly"  />
						</li>
						<li><label>楼盘</label> <input type="text" id="address" name="address" value="${customer.address}"
							readonly="readonly" />
						</li>
						<li><label>设计师</label> <input type="text" id="designMan" name="designMan"
							value="${customer.designManDescr}" readonly="readonly" />
						</li>
						<li hidden="true"><label>业务员</label> <input type="text" id="businessMan" name="businessMan"
							value="${customer.businessManDescr}" readonly="readonly" />
						</li>
						<li><label>户型</label> <input type="text" id="layout" name="layout" value="${customer.layout}"
							readonly="readonly" />
						</li>
						<li><label>面积</label> <input type="text" id="area" name="area" value="${customer.area}"
							readonly="readonly" />
						</li>
						<li hidden="true"><label>客户名称</label> <input type="text" id="descr" name="descr" value="${customer.descr}" />
						</li>
						<li hidden="true"><label>客户状态</label> <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS"
								selectedValue="${customer.status }"></house:xtdmMulit>
						</li>
						
						<li><label>主材优惠前金额</label> <input type="text" id="befAmount" name="befAmount" readonly="readonly" />
						</li>
						<li><label style="color: blue;">主材优惠金额</label> <input type="text" id="discAmount" name="discAmount"
							value="${customer.mainDisc}" onblur="changeAmount()" />
						</li>
						
						<li><label>主材实际总价</label> <input type="text" id="amount" name="amount" readonly="readonly" />
						</li>
						<li><label >主材费(按比例提成)</label> <input type="text" id="fee" name="fee" readonly="readonly"
							 />
						</li>
						<li id="manageFeeMainLi"><label>主材管理费</label> <input type="text" id="manageFee_Main"
							name="manageFee_Main" readonly="readonly" />
						</li>
						<li><label>服务性产品总价</label> <input type="text" id="mainServFee" name="mainServFee"
							value="${customer.mainServFee }" readonly="readonly" />
						</li>
						<li id="manageFeeServLi"><label>服务性产品管理费</label> <input type="text" id="manageFee_Serv"
							name="manageFee_Serv" value="${customer.manageFeeServ }" readonly="readonly" />
						</li>
						<li id="mainTempNo_show"><label>模板</label> <input type="text"  width: 130px;  id="mainTempNo" name="mainTempNo" value="${customer.mainTempNo}" readonly="readonly"/>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel" style="top: 1px">
			<div class="btn-group-sm">
				<button id="addBtn" type="button" class="btn btn-system " onclick="add()">快速新增</button>
				<button id="insertBtn" type="button" class="btn btn-system " onclick="add(true)">插入</button>
				<button id="editBtn" type="button" class="btn btn-system " onclick="edit()">编辑</button>
				<button id="delBtn" type="button" class="btn btn-system " onclick="batchDelete()">删除</button>
				<button type="button" class="btn btn-system " onclick="goTop()">向上</button>
				<button type="button" class="btn btn-system " onclick="goBottom()">向下</button>
				<button type="button" class="btn btn-system " id="supplProm">供应商促销</button>
				<button type="button" id="fromTemLoad" class="btn btn-system " onclick="loadFromTem()" >导入模板</button>
				<button id="importBtn" type="button" class="btn btn-system " onclick="goImportExcel()">导入备份</button>
				<button type="button" id="regenLoad" class="btn btn-system " onclick="loadRegen()"  >重新生成</button>
				<button id="clearBtn" type="button" class="btn btn-system " onclick="clearDetail()">清空预算</button>
				<button id="copy" type="button"  class="btn btn-system " onclick="copy()" style="display: none">复制预算</button>
				<button id="copyService" type="button"  class="btn btn-system " onclick="copy()" style="display: none">复制预算</button>
				<button type="button" id="quickLoad" class="btn btn-system " onclick="loadQuickly()" style="display: none">快速预算导入</button>
				<button type="button" class="btn btn-system " onclick="view()">查看</button>
				<button type="button" class="btn btn-system " onclick="exportExcel()">导出</button>	
			</div>
		</div>
		<div  class="container-fluid" id="id_detail">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab1" data-toggle="tab">主材报价</a></li>
				<li class=""><a id="custRight_show" href="#tab2" data-toggle="tab">服务性产品</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab1" class="tab-pane fade in active">
					<div id="content-list">
						<table id="dataTable"></table>
					</div>
				</div>
				<div id="tab2" class="tab-pane fade ">
					<div id="content-list2">
						<table id="serviceDataTable"></table>
					</div>
				</div>
				<div style="display: none">
					<table id="preDataTable"></table>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>


