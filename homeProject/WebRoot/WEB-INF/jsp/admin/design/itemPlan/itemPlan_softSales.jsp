<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>软装销售</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_discToken.js?v=${v}" type="text/javascript"></script>
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
.bootstrap-tagsinput {
    min-width:130px;
    height:24px;
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
	   $("#discAmount").val(0);
   }
   callAdornment(); 
   var manageFee_Soft=myRound(($("#befAmount").val()*${customer.softFeePer}-discAmount*${customer.softDiscPer})*${customer.manageFeeSoftPer});//Round((软装费*软装费系数-软装优惠*软装优惠系数)*软装管理费系数,0)
   $("#manageFee_Soft").val(manageFee_Soft);
	 
}
//计算所有费用并刷新
function loadFee(rowData){
	 var befAmount=parseFloat($("#dataTable").getCol('lineamount',false,'sum'));
	 var discAmount=parseFloat($("#discAmount").val()); 
	 var amount=befAmount-discAmount;
	 var fee=parseFloat($("#fee").val()); 
	 var softFee_WallPaper=0;
	 var softFee_Curtain=0;
	 var softFee_Light=0;
	 var softFee_Furniture=0;
	 $.each(rowData,function(k,v){
	 	//if(v.commitype=='2') fee+=parseFloat(v.lineamount);
	 	if('${customer.softFee_WallPaper}'&&'${customer.softFee_WallPaper}'.indexOf(v.itemtype2.trim())!=-1) softFee_WallPaper+=parseFloat(v.lineamount);
	    if('${customer.softFee_Curtain}'&&'${customer.softFee_Curtain}'.indexOf(v.itemtype2.trim())!=-1) softFee_Curtain+=parseFloat(v.lineamount);
	    if('${customer.softFee_Light}'&&'${customer.softFee_Light}'.indexOf(v.itemtype2.trim())!=-1) softFee_Light+=parseFloat(v.lineamount);
	    if('${customer.softFee_Furniture}'&&'${customer.softFee_Furniture}'.indexOf(v.itemtype2.trim())!=-1) softFee_Furniture+=parseFloat(v.lineamount);
	   
	 });
	var manageFee_Soft=myRound((befAmount*${customer.softFeePer}-discAmount*${customer.softDiscPer})*${customer.manageFeeSoftPer});//Round((软装费*软装费系数-软装优惠*软装优惠系数)*软装管理费系数,0)
	$("#manageFee_Soft").val(manageFee_Soft);
	 
	 $("#befAmount").val(befAmount);
	 $("#amount").val(amount);
	 //$("#fee").val(fee);
	 $("#softFee_WallPaper").val(softFee_WallPaper);
	 $("#softFee_Curtain").val(softFee_Curtain);
	 $("#softFee_Light").val(softFee_Light);
	 $("#softFee_Furniture").val(softFee_Furniture);
	 callAdornment();
}
function add(isInsert){
	if($('#code').val()==""){
		art.dialog({
			content: "请先添加客户"
		});
		return;	
	}
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
		 var url="${ctx}/admin/itemPlan/goYsQuickSave?custCode="+$("#code").val()+"&itemType1=RZ&isOutSet=${customer.isOutSet}&custType=${customer.custType}&isService=0"+itemType2Str;
		 if(ret) url="${ctx}/admin/itemPlan/goYsQuickSave?custCode="+$("#code").val()+"&itemType1=RZ&isOutSet=${customer.isOutSet}&custType=${customer.custType}&isService=0&fixAreaPk="+ret.fixareapk+itemType2Str;
		 Global.Dialog.showDialog("quickSave",{
			 title:"软装预算--快速新增",
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
	  		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'itemtype2descr'},page:1}).trigger("reloadGrid");  	 
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
function view(){
	var rowId = $("#dataTable").jqGrid('getGridParam', 'selrow');
    if (rowId) {
	    Global.Dialog.showDialog("ys_detailView",{
			  title:"软装预算--查看",
			  url:"${ctx}/admin/itemPlan/goItemPlan_ys_detailView?itemType1=RZ&custCode="+$("#code").val()+"&isOutSet=${customer.isOutSet}&rowId="+rowId+"&isService=0",
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
	    var itemSetNo=$("#dataTable").getCell(rowId,"itemsetno").trim();
	    if(itemSetNo){
	     	art.dialog({
				content: "套餐包材料不允许编辑！"
			});
			return;
	     }
	     Global.Dialog.showDialog("ys_detailViewEdit",{
			  title:"软装预算--编辑",
			  url:"${ctx}/admin/itemPlan/goItemPlan_ys_detailViewEdit?itemType1=RZ&custCode="+$("#code").val()+"&isOutSet=${customer.isOutSet}&rowId="+rowId+"&isService=0&itemCode="+itemCode+"&pk="+pk,
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
				  	 $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'itemtype2descr'},page:1}).trigger("reloadGrid");  
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
					$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'itemtype2descr'},page:1}).trigger("reloadGrid");  
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
	if($('#code').val()==""){
		art.dialog({
			content: "请先添加客户"
		});
		return;	
	}
	if (!sContent){
		sContent= '是否清除【'+$("#code").val()+'-'+$("#address").val()+'】的软装预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
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
		 	$("#referenceDetailDataTable").jqGrid('clearGridData');
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
	$("#custCode").openComponent_customer({condition:{status:"1,2,3",'disabledEle': 'status_NAME',custType:"2"},callBack: getData});
	var isCellEdit=true;
	showCustBtn();
	if ('${customer.status}'=='4'||'${customer.status}'=='5' || "${wfProcStatus}" == "1"){
		$("#saveBtn").attr("disabled","disabled");
		$("#addBtn").attr("disabled","disabled");
		$("#insertBtn").attr("disabled","disabled");
		$("#delBtn").attr("disabled","disabled");
		$("#editBtn").attr("disabled","disabled");
		$("#clearBtn").attr("disabled","disabled");
		$("#importBtn").attr("disabled","disabled");
		$("#copy").attr("disabled","disabled");
		$("#addCustBtn").hide();
		$("#updateCustBtn").hide();
		$("#selectCustBtn").hide();
		$("#discTokenSelBtn").hide();
		$("#discTokenDelBtn").hide();
		
		document.getElementById("address_lb").style.width="100px";
		document.getElementById("amount_lb").style.width="80px";
		isCellEdit=false;	
	}else{
		$("#discTokenViewBtn").hide();
		document.getElementById("amount_lb").style.width="50px";
	}
	$("#status_NAME").attr("disabled","disabled");
	$('*','#dataForm').bind('focus',function(){data_iRow=0;}); 
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemPlan/goItemPlan_RZJqGrid?custCode="+$("#code").val()+"&itemType1=RZ",
		height:$(document).height()-$("#content-list").offset().top-70,
		cellEdit:isCellEdit,
		cellsubmit:'clientArray',
		rowNum:1000,
		colModel : [
		{name: "pk", index: "pk", width: 122, label: "编号", sortable: true, align: "left", hidden: true},
		{name: "fixareapk", index: "fixareapk", width: 122, label: "区域名称", sortable: true, align: "left", hidden: true},
		{name: "fixareadescr", index: "fixareadescr", width: 122, label: "区域名称", sortable: true, align: "left"},
		{name: "isfixprice", index: "isfixprice", width: 122, label: "是否固定价格", sortable: true, align: "left", hidden: true},
		{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
		{name: "itemdescr", index: "itemdescr", width: 220, label: "材料名称", sortable: true, align: "left",cellattr: addCellAttr,editable:true,edittype:'text',editoptions:{ dataEvents:[{type:'focus',fn:function(e){ $(e.target).openComponent_itemDescr({condition:{'itemType1':"RZ".trim(),'containCode':"1"},onSelect:onSelect});}}]}},
		{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		{name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left"},
		{name: "projectqty", index: "projectqty", width: 85, label: "预估施工量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
		{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		{name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable: true, align: "right", sum: true},
		{name: "cost", index: "cost", width: 90, label: "成本", sortable: true, align: "right", sum: true, hidden: true},
		{name: "markup", index: "markup", width: 68, label: "折扣", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,minValue:1}},
		{name: "tmplineamount", index: "tmplineamount", width: 90, label: "小计", sortable: true, align: "right", sum: true},
		{name: "processcost", index: "processcost", width: 90, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
		{name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "right", sum: true},
		{name: "remark", index: "remark", width: 140, label: "材料描述", sortable: true, align: "left",editable:true,edittype:'textarea'},
		{name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		{name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left"},
		{name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable: true, align: "left"},
		{name: "giftdescr", index: "giftdescr", width: 80, label: "赠送项目", sortable: true, align: "left"},
		{name: "isgift", index: "isgift", width: 0.5, label: "是否赠送项目", sortable: true, align: "left"},
		{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
		{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
		{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
		{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
		{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
		{name: "dispseq", index: "dispseq", width: 84, label: "dispseq", sortable: true, align: "left", hidden: true},
		{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
        {name: "isoutset", index: "isoutset", width: 122, label: "区域名称", sortable: true, align: "left", hidden: true},
        {name: "isservice", index: "isservice", width: 122, label: "是否服务性产品", sortable: true, align: "left",formatter:function(){return 0}, hidden: true},
        {name: "oldprojectcost", index: "oldprojectcost", width: 90, label: "原项目经理结算价", sortable:false, align: "right",hidden: true},
        {name: "projectcost", index: "projectcost", width: 90, label: "升级项目经理结算价", sortable:false, align: "right",hidden: true},
    ],
    grouping: true, // 是否分组,默认为true
    groupingView: {
      groupField: ['itemtype2descr'], // 按照哪一列进行分组
      groupColumnShow: [false], // 是否显示分组列
      groupText: ['<b>材料类型2：{0}({1})</b>'], // 表头显示的数据以及相应的数据量
      groupCollapse: false, // 加载数据时是否只显示分组的组信息
      groupDataSorted: true, // 分组中的数据是否排序
      groupOrder: ['asc'], // 分组后的排序
      groupSummary: [false], // 是否显示汇总.如果为true需要在colModel中进行配置
      summaryType: 'max', // 运算类型，可以为max,sum,avg</div>
      summaryTpl: '<b>Max: {0}</b>',
      showSummaryOnHide: true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
    },
	gridComplete:function(){
	    if($("#dataTable").jqGrid('getGridParam','records')==0) $("#copy").show();
		else $("#copy").hide();
		var rowData = $("#dataTable").jqGrid('getRowData');
		loadFee(rowData);
		var ids=$("#dataTable").jqGrid("getDataIDs");
        $.each(ids,function(k,v){
          	var isfixprice=$("#dataTable").getCell(v,"isfixprice");
          	var isoutset=$("#dataTable").getCell(v,"isoutset");
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
              	$("#dataTable").setCell(id,'lineamount',myRound(myRound(rowData.tmplineamount)+processcost));
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
		Global.JqGrid.initJqGrid("referenceDetailDataTable",{
			url:"${ctx}/admin/itemPlan/goItemPlan_RZYJJqGrid?custCode="+$("#code").val(),
			height:$(document).height()-$("#content-list").offset().top-32,
			styleUI: 'Bootstrap',
			footerrow:false,
			rowNum:10000,
			colModel : [
			 {name: "fixareadescr", index: "fixareadescr", width: 137, label: "区域名称", sortable: true, align: "left"},
			 {name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
			 {name: "itemdescr", index: "itemdescr", width: 240, label: "材料名称", sortable: true, align: "left"},
			 {name: "lineamount", index: "lineamount", width: 90, label: "总价", sortable: true, align: "right", sum: true},
			 {name: "befprofitlevel", index: "befprofitlevel", width: 142, label: "折扣前星级", sortable: true, align: "right", sum: true}, 
			 {name: "profitlevel", index: "profitlevel", width: 142, label: "星级", sortable: true, align: "right", sum: true}, 
			 {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true}
           ],
           grouping: true, // 是否分组,默认为false
           groupingView: {
             groupField: ['itemtype2descr'], // 按照哪一列进行分组
             groupColumnShow: [false], // 是否显示分组列
             groupText: ['<b>材料类型2：{0}({1})</b>'], // 表头显示的数据以及相应的数据量
             groupCollapse: false, // 加载数据时是否只显示分组的组信息
             groupDataSorted: true, // 分组中的数据是否排序
             groupOrder: ['asc'], // 分组后的排序
             groupSummary: [false], // 是否显示汇总.如果为true需要在colModel中进行配置
             summaryType: 'max', // 运算类型，可以为max,sum,avg</div>
             summaryTpl: '<b>Max: {0}</b>',
             showSummaryOnHide: true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
           }
		});	
		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			if(e.currentTarget.id){
		    	$(".btn-panel").hide();
	    	} else{
		    	$(".btn-panel").show();
	    	} 
		});
});

function doSave(rowData){
	var datas=$("#dataForm").jsonForm();
	datas.custCode=$("#code").val();
	datas.detailJson=JSON.stringify(rowData);
	datas.isService=0;
	var url="${ctx}/admin/itemPlan/doClysSave";
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
	if($('#code').val()==""){
		art.dialog({
			content: "请先添加客户"
		});
		return;	
	}
	if (parseFloat($('#amount').val())<0){
		art.dialog({
				content:'实际总价不能小于0！'
			});
		return ;
	}
	var rowData=$('#dataTable').jqGrid("getRowData");
	var isAddAllInfo = $("#isAddAllInfo").val();
	var planSendDate = $("#planSendDate").val();
	if(isAddAllInfo == "0" && planSendDate == ""){
		art.dialog({
			content:"软装销售,预计发货日期不能为空"
		});
		return ;
	}
	var x=0;
	var sErr="";
	$.each(rowData,function(k,v){
		if(v.qty==0&&v.processcost!=0){
			x=1;
			return false;		
		}
		if(Math.abs(myRound(myRound(v.qty*v.unitprice)*v.markup/100+parseFloat(v.processcost))-parseFloat(v.lineamount))>1){
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
function goImportExcel(){
	if($('#code').val()==""){
		art.dialog({
			content: "请先添加客户"
		});
		return;	
	}
	var sMustImportTemp="0" ,stitle="软装预算明细--从excel导入";
	var sContent='导入前,需要清除【$("#code").val()-$("#address").val()】的软装预算<br/><input type="checkbox" id="clearDetail" />同时清空装修区域<br/>注：勾选此项将直接删除,无须进行保存操作即生效！ ';
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
				stitle="软装预算明细--从备份导入"	
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
	  	url:"${ctx}/admin/itemPlan/goItemPlan_importExcel?itemType1=RZ&custCode="+$("#code").val()+"&custType=${customer.custType}&isService=0&isCupboard=0"+"&mustImportTemp="+sMustImportTemp,
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

function callAdornment(){
	if(!$("#fee").val()) $("#fee").val(0);
	var softFee_Adornment=parseFloat($("#befAmount").val())+parseFloat($("#fee").val())-parseFloat($("#discAmount").val())-parseFloat($("#softFee_WallPaper").val())- parseFloat($("#softFee_Curtain").val())-parseFloat($("#softFee_Light").val())-parseFloat($("#softFee_Furniture").val());
	 $("#softFee_Adornment").val(softFee_Adornment);
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
function copy(){
	if($('#code').val()==""){
		art.dialog({
			content: "请先添加客户"
		});
		return;	
	}
	Global.Dialog.showDialog("itemPlanCopy",{
		  title:"搜寻--客户编号",
		  url:"${ctx}/admin/itemPlan/goCopy?itemType1=RZ&custCode="+$("#code").val()+"&isService=0&&custType=${customer.custType}",
		  height: 600,
		  width:1000,
		  returnFun: function(data){
	  		 $.each(data,function(k,v){
	  			 hasEdit=true;
	  			 $("#dataTable").addRowData(k+1, v, "last");
	  		 })
	  		 var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
		  	 $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'itemtype2descr'},page:1}).trigger("reloadGrid");  
		  }
	});
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
function addCellAttr(rowId, val, rawObject, cm, rdata){
	return rawObject.expired == 'T' ? " style='color:red'" : "";
}
function onSelect(json){
   var rowid = $( "#dataTable" ).getGridParam( "selrow" );
   var rowData = $("#dataTable").jqGrid('getRowData',rowid);
   var unitprice=0;
   if(rowData.isoutset!='0') unitprice=json.price;
   $("#dataTable").jqGrid("saveCell",rowid,data_iCol);
   //折扣改为100%
   $("#dataTable").setRowData(rowid,{'itemcode':json.code,'itemdescr':json.descr,
   		'sqlcodedescr':json.sqldescr,'uom':json.uomdescr,'itemtype2descr':json.itemtype2descr,
   		'itemtype3descr':json.itemtype3descr,'marketprice':json.marketprice,'unitprice':unitprice,
   		'beflineamount':myRound(rowData.qty*unitprice,4),'tmplineamount':myRound(rowData.qty*unitprice),
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
					doExcelNow('${customer.address}'+'-软装预算','dataTable', 'dataForm');
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
function updateDiscount() {
    var itemSetNoList = Global.JqGrid.allToJson("dataTable","itemsetno");
	Global.Dialog.showDialog("itemChgUpdateDiscount", {
    	title: "批量折扣调整",
        url: "${ctx}/admin/itemPlan/goUpdateDiscountRz",
        postData:{itemSetNoList:JSON.stringify(itemSetNoList.fieldJson)},
        height: 600,
        width: 1000,
        resizable: false,
        returnFun: function (data) { 
     		var tableDatas = Global.JqGrid.allToJson("dataTable");
     		var total = 0;
     		var disc=0.0;
     		for(var i=0;i<tableDatas.datas.length;i++){
     			console.log(tableDatas.datas[i]);
     			if($.trim(tableDatas.datas[i].itemsetno )== $.trim(data[0])){
     		 		total+=myRound(tableDatas.datas[i].beflineamount,2);
     		 	}
     		}
			if (data.length != null) {
				var ids = $("#dataTable").jqGrid("getDataIDs");
				var lastIds="";
				var itemSetCount=0;
				var lastLineAmount;
	            $.each(ids, function (k, v) {
					var rowData = $("#dataTable").jqGrid('getRowData', v);
					if (!rowData.reqpk && $.trim(data[0]) == $.trim(rowData.itemsetno) ) {
						 var lineAmount = myRound(myRound(myRound(rowData.qty * rowData.unitprice, 4) * myRound(data[1]/total,4)) + parseFloat(rowData.processcost));
						$("#dataTable").setCell(v, 'markup', myRound(data[1]/total,4)*100);
						$("#dataTable").setCell(v, 'tmplineamount', myRound(myRound(rowData.qty * rowData.unitprice, 4) * myRound(data[1]/total,4)));
						$("#dataTable").setCell(v, 'lineamount', lineAmount);
						lastIds=v;
						itemSetCount += lineAmount;
						lastLineAmount = lineAmount;
					}
				});
				if(itemSetCount - data[1] != 0){
					var rowDatas = $("#dataTable").jqGrid('getRowData', lastIds);
					$("#dataTable").setCell(lastIds, 'lineamount', lastLineAmount - (itemSetCount - data[1]));
					$("#dataTable").setCell(lastIds, 'tmplineamount', 
						myRound(myRound(rowDatas.qty * rowDatas.unitprice, 4) * myRound((lastLineAmount - (itemSetCount - data[1]))/rowDatas.beflineamount ,4)));
					$("#dataTable").setCell(lastIds, 'markup', myRound(myRound((lastLineAmount - (itemSetCount - data[1]))/rowDatas.beflineamount ,4)*100,4));
				}

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
} 
function custAdd(m_umState){
	var url="${ctx}/admin/itemPlan/goCustSave";
	var title="软装销售管理--新增客户";
	if($.trim(m_umState)=="M"){
		url="${ctx}/admin/itemPlan/goCustSave?id="+$("#code").val();
		title="软装销售管理--编辑客户";	
	}
	Global.Dialog.showDialog("itemPlan_custSave",{			
		title:title,
		url:url,
		height: 400,
		width:800,
		returnFun: function(data){
		    if(data.code){
		    	$("#code").val(data.code);
				$("#address").val(data.address);
				$("#designMan").val(data.designmandescr);
				$("#businessMan").val(data.businessmandescr);
				$("#area").val(data.area);
				showCustBtn();
		    }
	  	}
	});
};

function selectCust(){
	 $("#openComponent_customer_custCode").next().click();  
};

function getData(data){
	if (!data) return;
	$("#code").val(data.code),
	$("#address").val(data.address),
	$("#designMan").val(data.designmandescr);
	$("#businessMan").val(data.businessmandescr);
	$("#area").val(data.area);
	showCustBtn();
	getDiscTokenNo();
	$("#dataTable").jqGrid("setGridParam",{page:1,url:"${ctx}/admin/itemPlan/goItemPlan_RZJqGrid?custCode="+data.code+"&itemType1=RZ",}).trigger("reloadGrid"); 
}
function showCustBtn(){
    if($.trim($("#code").val())==""){
    	$("#addCustBtn").show();
		$("#updateCustBtn").hide();
    }else{
    	$("#addCustBtn").hide();
		$("#updateCustBtn").show();	
    }
} 

//优惠券选择
function discTokenSel(){
	if($("#code").val()==""){
		art.dialog({
			content:"请选择客户号"
		}); 
		return;
	}	
	$("#tokenNoSelect").openComponent_discToken({
		condition:{'itemType1':'RZ',custCode:$("#code").val(),containOldCustCode:'1',status:'2', containHasSelect:"1"},
		callBack:function(data){  
		 	if(data){
		 		$("#discTokenNo").val(data.no); 
		 		$("#discTokenAmount").val(data.amount); 
		 	};
	    }
	 });
	$("#openComponent_discToken_tokenNoSelect").next().click();
}

//优惠券查看
function discTokenView(){
	var hasSelectNo =$("#discTokenNo").val() ;
    Global.Dialog.showDialog("discToken",{
		title: "优惠券信息查看",
		url: "${ctx}/admin/discTokenQuery/goDiscTokenSelect",
		postData: {
		    hasSelectNo: hasSelectNo,
            m_umState:"V"
	    },
		height: 458,
		width: 660,
     });  
}

//优惠券删除
function discTokenDel(){
	$("#discTokenNo").val("");
	$("#discTokenAmount").val("0.0");
} 

function getDiscTokenNo(){
	$.ajax({
		url:"${ctx}/admin/discTokenQuery/getDiscTokenNo",
		type:"post",
		data:{useCustCode:$("#code").val(), status:"3", useStep :"1"},
		cache:false,
		dataType: "json",
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			
		},
		success:function(obj){
			if(obj && obj.no){
				$("#discTokenNo").val(obj.no);
				$("#discTokenAmount").val(obj.amount);
			}else{
				$("#discTokenNo").val("");
				$("#discTokenAmount").val("0.0");
			}	
			if(obj.wfProcStatus && obj.wfProcStatus =="1"){
				$("#saveBtn").attr("disabled","disabled");
				$("#addBtn").attr("disabled","disabled");
				$("#insertBtn").attr("disabled","disabled");
				$("#delBtn").attr("disabled","disabled");
				$("#editBtn").attr("disabled","disabled");
				$("#clearBtn").attr("disabled","disabled");
				$("#importBtn").attr("disabled","disabled");
				$("#copy").attr("disabled","disabled");
			} else {
				$("#saveBtn").removeAttr("disabled","disabled");
				$("#addBtn").removeAttr("disabled","disabled");
				$("#insertBtn").removeAttr("disabled","disabled");
				$("#delBtn").removeAttr("disabled","disabled");
				$("#editBtn").removeAttr("disabled","disabled");
				$("#clearBtn").removeAttr("disabled","disabled");
				$("#importBtn").removeAttr("disabled","disabled");
				$("#copy").removeAttr("disabled","disabled");
			}
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
					<button id="saveBtn"  type="button" class="btn btn-system" onclick="save()">保存</button>
       				<button type="button" class="btn btn-system " onclick="updateDiscount()">批量调整折扣</button>
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					 <input type="hidden" id="expired" name="expired" value="${itemPlan.expired}" />
					 <input type="hidden" name="jsonString" value="" />
					 <input type="hidden" name="custType" value="${customer.custType }" />
					 <input type="hidden" name="isAddAllInfo" id = "isAddAllInfo" value="${customer.isAddAllInfo }" />
					 <input type="hidden" name="lastUpdatedBy" value="${customer.lastUpdatedBy }" /> 
					 <input type="hidden" name="itemType1" value="RZ" /> <input type="hidden" name="isService" value="0" />
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="code" name="code" value="${customer.code}" readonly="readonly" />
							 <button type="button" class="btn btn-system" id="addCustBtn"  onclick="custAdd('A')" style="font-size: 12px;margin-left: -5px;width: 35px;">新增</button>
            			     <button type="button" class="btn btn-system" id="updateCustBtn" onclick="custAdd('M')" style="font-size: 12px;margin-left: -5px;width: 35px;" hidden="true">编辑</button>
							 <button type="button" class="btn btn-system" id="selectCustBtn" onclick="selectCust()" style="font-size: 12px;margin-left: -1px;width: 35px;">选择</button>
						</li>
						<li ><label id="address_lb"  style="width:25px;">楼盘</label> <input type="text" id="address" name="address" value="${customer.address}"
							readonly="readonly" />
						</li>	
						<li  hidden="true"><label>客户状态</label> <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS"
								selectedValue="${customer.status }"></house:xtdmMulit>
						</li>
						<li  hidden="true"><label>客户名称</label> <input type="text" id="descr" name="descr" value="${customer.descr}" />
						</li>
						<li><label>业务员</label> <input type="text" id="businessMan" name="businessMan"
							value="${customer.businessManDescr}" readonly="readonly" />
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
						<li><label >优惠前金额</label> <input type="text" id="befAmount" name="befAmount" readonly="readonly" />
						</li>
						<li><label style="color: blue;">优惠金额</label> <input type="number" id="discAmount" name="discAmount"
							value="${customer.softDisc}" onblur="changeAmount()" />
						</li>
						<li id="discTokenNo_li">
							 <label>优惠券</label> 
							 <input type="text" id="discTokenAmount" name="discTokenAmount" value="${customer.discTokenAmount}" readonly="readonly" />
							 <input type="hidden" id="discTokenNo" name="discTokenNo" value="${customer.discTokenNo}" readonly="readonly" />
							 <button type="button" class="btn btn-system" id="discTokenSelBtn"  onclick="discTokenSel()" style="font-size: 12px;margin-top: -4px; margin-left: -5px;width: 30px;">选择</button>
            			     <button type="button" class="btn btn-system" id="discTokenDelBtn" onclick="discTokenDel()" style="font-size: 12px;margin-top: -4px; margin-left: -5px; width: 30px;" >删除</button>
            			     <button type="button" class="btn btn-system" id="discTokenViewBtn" onclick="discTokenView()" style="font-size: 12px;margin-top: -4px; margin-left: -5px; width: 30px;" >查看</button>
            			     
				        </li>
						<li hidden="true">
            				 <input type="text" id="tokenNoSelect" name="tokenNoSelect" />
        				</li>
						<li><label id="amount_lb" style="width:50px;margin-right: -5px;">实际总价</label> <input type="text" id="amount" name="amount" readonly="readonly" />
						</li>
						<li><label style="color: blue;">其它费用</label> <input type="number" id="fee" name="fee" value="${customer.softOther}"
							onblur="callAdornment()" />
						</li>
						<li><label>灯具款</label> <input type="text" id="softFee_Light" name="softFee_Light"
							readonly="readonly" />
						</li>
						
						<li><label>壁纸款</label> <input type="text" id="softFee_WallPaper" name="softFee_WallPaper"
							readonly="readonly" />
						</li>
						<li><label>家具款</label> <input type="text" id="softFee_Furniture" name="softFee_Furniture"
							readonly="readonly" />
						</li>
						
						<li><label>窗帘款</label> <input type="text" id="softFee_Curtain" name="softFee_Curtain"
							readonly="readonly" />
						</li>
						<li><label>装饰品及其它款</label> <input type="text" id="softFee_Adornment" name="softFee_Adornment"
							readonly="readonly" />
						</li>
						<li ><label>软装管理费</label> <input type="text" id="manageFee_Soft"
							name="manageFee_Soft" readonly="readonly" />
						</li>
						<li class="form-validate">
							<label>
							<c:if test="${customer.isAddAllInfo == '0' }">
								<span class="required">*</span>
							</c:if>
							预计发货日期</label>
							<input type="text" id="planSendDate" name="planSendDate" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${customer.planSendDateSoft}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li><label class="control-textarea">备注</label> <textarea id="softPlanRemark" name="softPlanRemark">${customer.softPlanRemark}</textarea>
						</li>
						<li hidden="true" >
							<input type="hidden" id="custCode" name="custCode" value="${customer.code}"/>
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
		<!--标签页内容 -->
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_mainDetail" data-toggle="tab">主项目</a></li>
				<li class=""><a id="reference" href="#tab_referenceDetail" data-toggle="tab">参考业绩</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_mainDetail" class="tab-pane fade in active">
					<div id="content-list">
						<table id="dataTable"></table>
					</div>
				</div>
				<div id="tab_referenceDetail" class="tab-pane fade ">
					<div id="content-list">
						<table id="referenceDetailDataTable"></table>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>


