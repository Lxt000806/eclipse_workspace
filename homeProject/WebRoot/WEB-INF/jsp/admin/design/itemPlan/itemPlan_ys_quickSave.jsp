<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>材料增减--快速新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
var leftOffset;
var fixAreaPk=0;
var intProdPk=0;
var isAddFixArea=false;
function saveData(){
	var ids = $("#itemChgDetailDataTable").getDataIDs();
	//var ret = selectDataTableRow('intProdDataTable');
	var gridDatas = [];
	$.each(ids,function(k,id){
   		var row = $("#itemChgDetailDataTable").jqGrid('getRowData', id);
   		//预算管理
   		row.remark=row.remarks;
   		row.uom=row.uomdescr;
  		if('${itemPlan.itemType1}'=='JC'){
  		//预算管理是否橱柜内部判断
  		// row.iscupboard=ret.IsCupboard;
  			 row.iscupboarddescr=row.iscupboard=='1'?'是':'否';
  		} 
  		
  		gridDatas.push(row);
  		
  	});
  	Global.Dialog.returnData = gridDatas;
}
function doSave(){
	if($("#itemChgDetailDataTable").jqGrid('getGridParam','records')>0)	saveData();
	 closeWin();
}
function closeAddUi(){
	if($("#itemChgDetailDataTable").jqGrid('getGridParam','records')>0){
		art.dialog({
			content: "是否保存被更改的数据?",
			ok: function () {
	      		saveData();
	    		closeWin();
    		},
    		cancel:function(){
   				 closeWin();
    		}
		});
		return;
	}
	closeWin();
}
function reloadIntProd(){
	intProdPk=0;
	$("#intProdPk").val(intProdPk);
	$("#intProdDescr").val("");
	$("#intProdDataTable").jqGrid("setGridParam",{postData:{'fixAreaPk':fixAreaPk},page:1}).trigger("reloadGrid");
}

  function search() {
     if ($("#clearPrice").is(':checked')){
       document.getElementById("iframe").contentWindow.document.getElementById("listFrame_q").src ="${ctx}/admin/custTypeItem/goItemSelect?code="+ $("#code").val() +"&descr="+$("#itemDescr").val()+"&itemType1=${itemPlan.itemType1}&custType=${itemPlan.custType}&custCode=${itemPlan.custCode}";
     }else{
       document.getElementById("iframe").contentWindow.document.getElementById("listFrame_q").src = "${ctx}/admin/item/goItemSelect?code=" + $("#code").val() + "&descr=" + $("#itemDescr").val() + "&itemType1=${itemPlan.itemType1}&custType=${itemPlan.custType}&custCode=${itemPlan.custCode}";
     }
      document.getElementById("itemSetFrame").contentWindow.goto_query1($("#code").val(),$("#itemDescr").val(),this);
   }
  
function addIntProd(){
	Global.Dialog.showDialog("intProdAdd",{
		  title:"集成成品--增加",
		  url:"${ctx}/admin/intProd/goSaveWithCupboard?custCode=${itemPlan.custCode}&itemType1=${itemPlan.itemType1}&isService=${itemPlan.isService}&fixAreaPk="+fixAreaPk,
		  height: 350,
		  width:400,
		  returnFun: reloadIntProd
	},reloadIntProd);
}
function  updateIntProd(){
	var ret = selectDataTableRow('intProdDataTable');
    if (ret) {
     	Global.Dialog.showDialog("intProdUpdate",{
		   title:"集成成品--编辑",
		   url:"${ctx}/admin/intProd/goUpdateWithCupboard?custCode=${itemPlan.custCode}&itemType1=${itemPlan.itemType1}&isService=${itemPlan.isService}&descr="+ret.Descr+"&fixAreaPk="+fixAreaPk+"&isCupboard="+ret.IsCupboard+"&pk="+ret.PK,
		    height: 350,
		  	width:400,
		   returnFun: reloadIntProd
		},reloadIntProd);
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }

}
function goto_query(table,param){
	fixAreaPk=0;
	$("#fixAreaPk").val(fixAreaPk);
	$("#fixAreaDescr").val("");
	$("#"+table).jqGrid("setGridParam",{postData:param,page:1}).trigger("reloadGrid");
}

function  update(e){
	var ret = selectDataTableRow();
    if (ret) {
    	var descr=e.value;
		if(descr=='水电项目'||descr=='土建项目'||descr=='安装项目'||descr=='综合项目'){
			art.dialog({
				content: "不能在材料预算中将装修区域修改为"+descr
			});
			return;
		}
		$.ajax({
			url:'${ctx}/admin/fixArea/updateFixArea?custCode=${itemPlan.custCode}&itemType1=${itemPlan.itemType1}&isService=${itemPlan.isService}&descr='+descr+'&pk='+ret.PK,
			cache: false,
		    success: function(obj){
		  		if(obj){	
		  			art.dialog({
						content: "装修区域已存在！"
					});
		  		}else{
		  			e.value='';
		  			return goto_query("dataTable",{"descr":''});
		  		}
		  		
		    }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function add(e){
	var descr=e.value;
	if(!descr) return;
	if(descr=='水电项目'||descr=='土建项目'||descr=='安装项目'||descr=='综合项目'){
		art.dialog({
			content: "不能在材料预算中新增"+descr+"区域"
		});
		return;
	}
	var datas={custCode:"${itemPlan.custCode}",itemType1:"${itemPlan.itemType1}",isService:"${itemPlan.isService}",descr:descr};
    var url="${ctx}/admin/fixArea/doSave";
    if("${itemPlan.mainTempNo}"&&"${itemPlan.itemType1}"==="ZC"){
	    Global.Dialog.showDialog("select_prePlanArea",{
			title:"选择空间",
			url:"${ctx}/admin/prePlanArea/goCode?custCode=${itemPlan.custCode}",
			height:500,
			width:550,
			returnFun: function(data){
			  	if(data){
				 	datas.prePlanAreaPK=data.pk;
				}
				isAddFixArea=true;
			 	doSavefixArea(url,datas,e);	
			},
	  	});  
    }else{
    	isAddFixArea=true;
    	doSavefixArea(url,datas,e);
    } 
}
function insert(e){
	var ret = selectDataTableRow();
    if (ret) {
	    var descr=e.value;
		if(descr=='水电项目'||descr=='土建项目'||descr=='安装项目'||descr=='综合项目'){
			art.dialog({
				content: "不能在材料预算中插入"+descr
			});
			return;
		}
		var datas={custCode:"${itemPlan.custCode}",itemType1:"${itemPlan.itemType1}",isService:"${itemPlan.isService}",descr:descr,dispSeq:ret.DispSeq};
        var url="${ctx}/admin/fixArea/doInsertFixArea";
        if("${itemPlan.mainTempNo}"&&"${itemPlan.itemType1}"==="ZC"){
       		 Global.Dialog.showDialog("select_prePlanArea",{
				  title:"选择空间",
				  url:"${ctx}/admin/prePlanArea/goCode?custCode=${itemPlan.custCode}",
				  height:500,
				  width:550,
				  returnFun: function(data){
					  if(data){
					  	 datas.prePlanAreaPK=data.pk;
					  }
					   doSavefixArea(url,datas,e);
				  }, 
			   });  
        }else{
        	 doSavefixArea(url,datas,e);
        }
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function doSavefixArea(url,datas,e){
	$.ajax({
		url:url,
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
				});
				e.value = '';
				return goto_query("dataTable", {"descr": ''});
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
function delete_(){
	var ret = selectDataTableRow();
    if (ret) {
		var itemType1='${itemPlan.itemType1}'.trim();
		var descr=ret.Descr;
		var strmsg,warn;
		if(itemType1=='ZC') strmsg="删除该区域将会删除该区域下的主材预算";
		if(itemType1=='RZ') strmsg="删除该区域将会删除该区域下的软装预算";
		if(itemType1=='JC') strmsg="删除该区域将会删除该区域下的集成成品、集成橱柜预算";
		warn="确定要删除["+descr+"]装修区域?<br/>"+strmsg;
		art.dialog({
			content: warn,
			ok: function () {
	     		$.ajax({
					url:'${ctx}/admin/fixArea/deleteFixArea?pk='+ret.PK,
					cache: false,
		    		success: function(obj){
		    			console.log(obj);
			  			if(obj&&obj.ret!=1){
					  		art.dialog({
								content: obj.errmsg
							});
							return;
			  			}
				        var topFrame="#iframe_itemPlan_ys";
				  		if ('${itemPlan.isService}'=='1'){
				  		    var dataTable=$(top.$(topFrame)[0].contentWindow.document.getElementById("serviceDataTable"));
				  		}else{
				  			var dataTable=$(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable"));
				  		} 
				  		var arr= dataTable.jqGrid("getRowData");
				  		var brr=$("#itemChgDetailDataTable").jqGrid("getRowData");
				  		$.each(arr,function(k,v){
				  			if(v.fixareadescr==descr)
				  			dataTable.delRowData(k+1);
				  		})
				  		$.each(brr,function(k,v){
				  			if(v.fixareadescr==descr)
				  			$("#itemChgDetailDataTable").delRowData(k+1);
				  		})
				  		return goto_query("dataTable",{"descr":''});
		  				
		   		 	}
		 		});
	    	},
	   		cancel:function(){
	   
	   		}
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

//tab分页
$(document).ready(function() { 
    $('input','#page_form').unbind('keydown');   
	if('${itemPlan.itemType1}'.trim()=='JC'){
		$("#intProdDiv").show();
		$("#itemTemp").show();
		$("#id_detail").css('width','1040px');
	}
	if('${itemPlan.itemType1}'.trim()=='RZ'){
		$("#itemSet").show();
	}
	var areaHeight=210;
	if('${itemPlan.itemType1}'.trim()=='ZC'){
	 	areaHeight=250;
	 	$("#reloadBtn").addClass("btn-block");
	 	$("#reloadBtn").show();
	}	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/fixArea/goJqGrid?custCode=${itemPlan.custCode}&itemType1=${itemPlan.itemType1}&isService=${itemPlan.isService}",
		height:areaHeight,
		rownumbers:false,
		rowNum:10000,
		styleUI: 'Bootstrap',
		colModel : [
	        {name : 'Descr',index : 'Descr',width : 130,label:'<h7>装修区域</h7><br/><input type="text" placeholder="按回车时搜索" class="form-control" onkeydown="enterClick(window.event,\'fixAreaSearch\',this)">',sortable : false,align : "left"},
	        {name : 'PK',index : 'PK',width : 100,label:'区域编号',sortable : false,align : "center",hidden:true},
	        {name : 'DispSeq',index : 'DispSeq',width : 100,label:'序号',sortable : false,align : "center",hidden:true},
	        {name: "PrePlanAreaPK", index: "PrePlanAreaPK", width: 100, label: "空间pk", sortable: true, align: "left", hidden: true},
			{name: "preplanareadescr", index: "preplanareadescr", width: 100, label: "名称", sortable: true, align: "left", hidden: true},
			{name: "custtypeitemfixarea", index: "custtypeitemfixarea", width: 100, label: "套餐材料适用区域", sortable: true, align: "left", hidden: true}  
	        
        ],
        gridComplete:function(){
            if('${itemPlan.isOutSet}'=='2'&&'${itemPlan.itemType1}'.trim()!='RZ') $("#clearPriceTd").css("display","block"); 
           	if('${itemPlan.fixAreaPk}'){
	           	var  rowData=$("#dataTable").jqGrid('getRowData');
	           	$.each(rowData,function(k,v){
		           	if(v.PK=='${itemPlan.fixAreaPk}'){
		           		$("#dataTable").setSelection(k+1, true);
		           	 	scrollToPosi(k+1);
		           	 	if('${itemPlan.itemType1}'.trim()=='JC'){
		           	   		 setTimeout(function(){$("#intProdDataTable").jqGrid("setGridParam",{postData:{'fixAreaPk':rowData.PK},page:1}).trigger("reloadGrid");},200);
		           	 	}
		           	 	return false;
		           	 }
	           	 })
           	}else{
            	if('${itemPlan.itemType1}'.trim()=='JC'){
                	var rowData = $("#dataTable").jqGrid('getRowData','1');
            	 	setTimeout(function(){$("#intProdDataTable").jqGrid("setGridParam",{postData:{'fixAreaPk':rowData.PK},page:1}).trigger("reloadGrid");},200);

             	}
           	}
           	if(isAddFixArea){
            	isAddFixArea=false;
                var ids=$("#dataTable").jqGrid("getDataIDs");
            	$("#dataTable").setSelection(ids.length, true);
            	scrollToPosi(ids.length);
            }
		},
        onSelectRow: function (rowid, status) {
         	 var itemType1='${itemPlan.itemType1}'.trim();
         	 var rowData = $("#dataTable").jqGrid('getRowData',rowid);
         	 fixAreaPk=parseInt(rowData.PK);
         	 $("#fixAreaPk").val(fixAreaPk);
         	 $("#fixAreaDescr").val(rowData.Descr);
         	 $("#prePlanAreaPK").val(rowData.PrePlanAreaPK);
         	 $("#prePlanAreaDescr").val(rowData.preplanareadescr);
         	 
         	 if(itemType1=='JC'){
         	    $("#jqgh_intProdDataTable_Descr").text(rowData.Descr+"的集成成品");
         	 	$("#intProdDataTable").jqGrid("setGridParam",{postData:{'fixAreaPk':rowData.PK},page:1}).trigger("reloadGrid");
         	 }  
        },
	});
	Global.JqGrid.initJqGrid("intProdDataTable",{
		url:"${ctx}/admin/intProd/goJqGrid",
		height:270,
		rownumbers:false,
		rowNum:10000,
		styleUI: 'Bootstrap',
		colModel : [
	       {name : 'Descr',index : 'Descr',width : 120,label:'集成成品',sortable : false,align : "left"},
	       {name : 'IsCupboard',index : 'IsCupboard',width : 100,label:'是否橱柜',sortable : false,align : "center",hidden:true},
	       {name : 'PK',index : 'PK',width : 100,label:'编号',sortable : false,align : "center",hidden:true} 
	    ],
        onSelectRow: function (rowid, status) {
      	 	var rowData = $("#intProdDataTable").jqGrid('getRowData',rowid);
      	 	intProdPk=parseInt(rowData.PK);
      		 $("#intProdPk").val(intProdPk);
      		 $("#intProdDescr").val(rowData.Descr);
      	 	$("#isCupboard").val(rowData.IsCupboard);
        },
	});
	Global.JqGrid.initJqGrid("itemChgDetailDataTable",{
		height:233,
		cellEdit:true,
		cellsubmit:'clientArray',
		styleUI: 'Bootstrap',
		rowNum:10000,
		colModel : [
			{name: "deleteBtn", index: "deleteBtn", width: 70, label: "删除", sortable: false, align: "left", formatter:deleteBtn},
			{name: "fixareapk", index: "fixareapk", width: 80, label: "区域编号", sortable: true, align: "left", hidden: true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品编号", sortable: true, align: "left", hidden: true},
			{name: "preplanareapk", index: "preplanareapk", width: 80, label: "区域编号", sortable: true, align: "left", hidden: true},
			{name: "fixareadescr", index: "fixareadescr", width: 90, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
			{name: "itemdescr", index: "itemdescr", width: 160, label: "材料名称", sortable: true, align: "left"},
			{name: "algorithm", index: "algorithm", width: 85, label: "算法", sortable:false, align: "left",editable: true, hidden: true},
			{name: "algorithmdescr", index: "algorithmdescr", width: 85, label: "算法", sortable: true, align: "left",editable:true, hidden: true,
				edittype:'select',
				editoptions:{ 
		  			dataUrl: '${ctx}/admin/item/getAlgorithm',
					postData: function(){
						var ret = selectDataTableRow("itemChgDetailDataTable");
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
						return "<select style=\"font-family:宋体;font-size:16px\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e);
	  					}
		  			}, 
		  		
		  		]}
	 		},
			{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "projectqty", index: "projectqty", width: 60, label: "预估数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 50, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
			{name: "markup", index: "markup", width: 50, label: "折扣%", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,integer:false,minValue:1}},
			{name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right", sum: true},
			{name: "processcost", index: "processcost", width: 60, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 200, label: "材料描述", sortable: true, align: "left",editable:true,edittype:'textarea'},
			{name: "cuttype", index: "cuttype", width: 65, label: "切割类型",  width: 0.5,sortable:false, align: "left",hidden: true},
			{name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left",editable:true,edittype:'select',hidden: true,
				editoptions:{ 
		  			dataUrl: '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
					postData: function(){
						var ret = selectDataTableRow("itemChgDetailDataTable");
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
						return "<select style=\"font-family:宋体;font-size:16px\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						cutTypeClick(e);
	  					}
		  			}, 
		  		
		  		]}
	 		},
	 		{name: "itemcode", index: "itemcode", width: 272, label: "材料编号", sortable: true, align: "left", hidden: true},
			{name: "preplanareadescr", index: "preplanareadescr", width: 80, label: "空间名称", sortable: true, align: "left"},
			{name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}, hidden: true},
			{name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}, hidden: true},
			{name: "isfixprice", index: "isfixprice", width: 85, label: "是否固定价", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
			{name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},	
           	{name: "isoutsetdescr", index: "isoutsetdescr", width: 40, label: "套外", sortable: true, align: "left", hidden: true},
			{name: "isoutset", index: "isoutset", width: 20, label: "套餐外材料", sortable: true, align: "left", hidden: true},
           	{name: "iscupboard", index: "iscupboard", width: 90, label: "是否橱柜", sortable: true, align: "left", hidden: true},
           	{name: "custtypeitempk", index: "custtypeitempk", width: 85, label: "套餐材料信息编号", sortable: true, align: "left", hidden: true},
           	{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
           	{name: "isgift", index: "isgift", width: 20, label: "是否赠送项目", sortable: true, align: "left", hidden: true},
           	{name: "giftpk", index: "giftpk", width: 20, label: "赠送项目pk", sortable: true, align: "left", hidden: true},
			{name: "giftdescr", index: "giftdescr", width: 68, label: "赠送项目", sortable: true, align: "left", hidden: true},
			{name: "oldprojectcost", index: "oldprojectcost", width: 90, label: "原项目经理结算价", sortable:false, align: "right", hidden: true},
			{name: "projectcost", index: "projectcost", width: 90, label: "升级项目经理结算价", sortable:false, align: "right", hidden: true},
			{name: "autoqty", index: "autoqty", width: 80, label: "系统算量", sortable: true, align: "right", hidden: true},
		],
        gridComplete:function(){
            var ids=$("#itemChgDetailDataTable").jqGrid("getDataIDs");
            $.each(ids,function(k,v){
            	var isfixprice=$("#itemChgDetailDataTable").getCell(v,"isfixprice");
            	
            	if(isfixprice=='0'&&!$("#clearPrice").is(':checked')){
				
				}else{
					$("#itemChgDetailDataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
				}
            })
         
            if('${itemPlan.itemType1}'.trim()=='ZC'){
        		$("#itemChgDetailDataTable").jqGrid('showCol', "preplanareadescr");
        		$("#itemChgDetailDataTable").jqGrid('showCol', "cuttypedescr");
        		$("#itemChgDetailDataTable").jqGrid('showCol', "algorithmdescr")
        		$("#itemChgDetailDataTable").jqGrid('showCol', "algorithmper")
        		$("#itemChgDetailDataTable").jqGrid('showCol', "algorithmdeduct")
  
            }
            if('${itemPlan.itemType1}'.trim()=='JC'){
            	$("#itemChgDetailDataTable").jqGrid('showCol', "intproddescr")
            } 
 		    if('${itemPlan.itemType1}'.trim()=='RZ'){
 		    	$("#itemChgDetailDataTable").jqGrid('showCol', "itemsetdescr")	
 		    }	 	 
           //scrollToPosi(ids.length,"itemChgDetailDataTable"); 
           var lastTr = $("#itemChgDetailDataTable tr:last");
		   lastTr.focus();
		   
		   
          
        },
        afterSaveCell:function(id,name,val,iRow,iCol){
	        var rowData = $("#itemChgDetailDataTable").jqGrid('getRowData',id);
	        var processcost=parseFloat(rowData.processcost);
	   		switch (name){
	        	case 'qty':
		        	$("#itemChgDetailDataTable").setCell(id,'beflineamount',myRound(val*rowData.unitprice,4));
		        	$("#itemChgDetailDataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100));
		        	$("#itemChgDetailDataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.unitprice,4)*rowData.markup/100)+processcost));
		        	if(rowData.cuttype!='' && rowData.isoutset=='1'){
		                changeAlgorithm(id,'2');	
		            }	
	        	break;
	        	case 'unitprice':
		        	$("#itemChgDetailDataTable").setCell(id,'beflineamount',myRound(val*rowData.qty,4));
		        	$("#itemChgDetailDataTable").setCell(id,'tmplineamount',myRound(myRound(val*rowData.qty,4)*rowData.markup/100));
		        	$("#itemChgDetailDataTable").setCell(id,'lineamount',myRound(myRound(myRound(val*rowData.qty,4)*rowData.markup/100)+processcost));
	        	break;
	        	case 'markup':
		        	$("#itemChgDetailDataTable").setCell(id,'tmplineamount',myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100));
		        	$("#itemChgDetailDataTable").setCell(id,'lineamount',myRound(myRound(myRound(rowData.qty*rowData.unitprice,4)*val/100)+processcost));
		        	break;
	        	case 'processcost':
		        	$("#itemChgDetailDataTable").setCell(id,'lineamount',myRound(parseFloat(rowData.tmplineamount)+processcost));
		        	break;
	        	case 'algorithmper':
	             	if(rowData.algorithm != '' ){
	             		changeAlgorithm(id, 1 );			
	                 }
	         		break;
	     		case 'algorithmdeduct':
	     			console.log(rowData.algorithmdeduct);
	 	        	if(rowData.algorithm !='' ){
	 	        		changeAlgorithm(id, 1 );			
	                }
	         		break;
	        }
            var beflineamount=$("#itemChgDetailDataTable").getCol('beflineamount',false,'sum');   
            var tmplineamount=$("#itemChgDetailDataTable").getCol('tmplineamount',false,'sum');   
            var processcost=$("#itemChgDetailDataTable").getCol('processcost',false,'sum');   
            var lineamount=$("#itemChgDetailDataTable").getCol('lineamount',false,'sum');   
            $("#itemChgDetailDataTable").footerData('set', { "beflineamount": beflineamount }); 
            $("#itemChgDetailDataTable").footerData('set', { "tmplineamount": tmplineamount }); 
            $("#itemChgDetailDataTable").footerData('set', { "processcost": processcost }); 
            $("#itemChgDetailDataTable").footerData('set', { "lineamount": lineamount });    
               
        },
        beforeSelectRow:function(id){
           setTimeout(function(){
        	   relocate(id,'itemChgDetailDataTable');
           },100) 	
		}
	});
	
});
function deleteBtn(v,x,r){
	 return "<button type='button' class='btn btn-system btn-xs' onclick='deleteRow("+x.rowId+");'>删除</button>";
}
function deleteRow(rowId){
	var nextId=$("#itemChgDetailDataTable tr[id="+rowId+"]").next().attr("id");
	var preId=$("#itemChgDetailDataTable tr[id="+rowId+"]").prev().attr("id");
    $('#itemChgDetailDataTable').delRowData(rowId);
    if($("#itemChgDetailDataTable").jqGrid('getGridParam','records')>0){
    	if(nextId){
    	    relocate(nextId,'itemChgDetailDataTable');
    	    $("#itemChgDetailDataTable tr[id="+nextId+"] button").focus();
    	}else{
    		relocate(preId,'itemChgDetailDataTable');
    	 	$("#itemChgDetailDataTable tr[id="+preId+"] button").focus();
    	} 	
    }
}
function enterClick(e,type,d){
	if(e.keyCode==13){
		switch (type) {
		case 'item':
			search();
			break;
		case 'fixAreaSearch':
			goto_query('dataTable',{'descr':d.value});
			break;
		case 'fixAreaAdd':
			add(d);
			break;
		case 'fixAreaEdit':
			update(d);
			break;
		case 'fixAreaInsert':
			insert(d);
			break;				
		}
	} 
}
function showValue(e){
	var ret = selectDataTableRow();
	if(ret){
		e.value=ret.Descr;
	}	
}
function scale(opt){
	switch (opt) {
	case 'big':
		top.$("#dialog_quickSave").parent().css({"width":"1380px","left":leftOffset});
		$("#"+opt).hide();
		$("#small").show();
		break;
	default:
	    leftOffset=top.$("#dialog_quickSave").parent().css("left");
		top.$("#dialog_quickSave").parent().css({"width":"60%","left":"40%"});
		$("#"+opt).hide();
		$("#big").show();
		break;
	}
}
function reloadArea(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/fixArea/goReloadAreaJqGrid?custCode=${itemPlan.custCode}&itemType1=${itemPlan.itemType1}&isService=${itemPlan.isService}",page:1}).trigger("reloadGrid");    
}

function fixAreaAdd(m_umState){
    var url="${ctx}/admin/fixArea/goFixAreaItemPlan?";
    var ret = selectDataTableRow();
	var title="新增";
	if(m_umState== "A"){
		url+="custCode=${itemPlan.custCode}&itemType1=${itemPlan.itemType1}&isService=${itemPlan.isService}&m_umState="+m_umState
 	}else if(m_umState== "M"){
 		url+="pk="+ret.PK+"&m_umState="+m_umState
 		title="编辑";
 	}else{
 		url+="pk="+ret.PK+"&m_umState="+m_umState
 		title="插入";
 	}
	url+="&mustImportTemp="+"${itemPlan.mustImportTemp}";
	Global.Dialog.showDialog("itemPlanfixArea",{
		  title:title+"装修区域",
		  url:url,
		  height:230,
		  width:430, 
		  returnFun:function(){
				goto_query("dataTable", {"descr": ''});	
		  }
	});
}

function algorithmClick(e){
	var rowid = $("#itemChgDetailDataTable").getGridParam("selrow");
    var rowData = $("#itemChgDetailDataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#itemChgDetailDataTable").setRowData($(e.target).attr("rowid"), {
    	algorithm:$(e.target).val(),
    });
    changeAlgorithm($(e.target).attr("rowid"),'1');
}

function cutTypeClick(e){
	var rowid = $("#itemChgDetailDataTable").getGridParam("selrow");
    var rowData = $("#itemChgDetailDataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#itemChgDetailDataTable").setRowData($(e.target).attr("rowid"), {
    	cuttype:$(e.target).val(),
    });
    // 选择切割方式,套餐内默认不自动计算其它费用
 	if(rowData.isoutset=='0'){
		return;
	}
    changeAlgorithm($(e.target).attr("rowid"),'2');
}

//根据算法变化计算其他费用 id表格行，Type调用类型  1算法变动调用，2切割类型变动调用
function changeAlgorithm(id,type){
	var ret = $("#itemChgDetailDataTable").jqGrid('getRowData', id);
	var datas=$("#page_form").jsonForm();
	datas.custCode="${itemPlan.custCode}";
	datas.itemCode=ret.itemcode;
	datas.algorithm=ret.algorithm;
	datas.cutType=ret.cuttype; 
	datas.doorPK=ret.doorpk;
	datas.prePlanAreaPK=ret.preplanareapk;
	datas.detailJson=getDetailGridDataJSON();
	datas.qty=ret.qty;
	datas.type=type;
	datas.isOutSet=ret.isoutset;
	datas.algorithmDeduct=ret.algorithmdeduct;
	datas.algorithmPer=ret.algorithmper;
	console.log(ret.algorithmdeduct);
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
						$("#itemChgDetailDataTable").setRowData(id, {
							'processcost':obj.processcost,
							'lineamount': myRound(myRound(myRound(obj.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(obj.processcost))		      	      
						});
						
					}else{
						$("#itemChgDetailDataTable").setRowData(id, {
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

// 获取表格json数据，在根据算法时只需要算法是门和材料名称带门的材料
function getDetailGridDataJSON(){
  	var topFrame="#iframe_itemPlan_ys";
    var zcRowData= $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData");
    var servRowData= $(top.$(topFrame)[0].contentWindow.document.getElementById("serviceDataTable")).jqGrid("getRowData");
	var gridData =[];
	$.each(zcRowData,function(k,v){
		if(v.algorithm=='3' || v.itemdescr.indexOf("门") != -1 ) {
			gridData.push(v);
		}	
	});
	$.each(servRowData,function(k,v){
			if(v.algorithm=='3' || v.itemdescr.indexOf("门") != -1 ) {
				gridData.push(v);
			}	
	});
	return JSON.stringify(gridData); 
}

</script>
</head>

<body>
	<div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="${itemPlan.expired}" /> <input type="hidden"
					id="fixAreaPk" name="fixAreaPk" /> <input type="hidden" id="intProdPk" name="intProdPk" /> <input
					type="hidden" id="fixAreaDescr" name="fixAreaDescr" /> <input type="hidden" id="intProdDescr"
					name="intProdDescr" /> <input type="hidden" id="isCupboard" name="isCupboard" /> <input type="hidden"
					id="rowNo" name="rowNo" value="1" /> <input type="hidden" id="prePlanAreaPK" name="prePlanAreaPK" />
					<input type="hidden" id="prePlanAreaDescr" name="prePlanAreaDescr" />
				<ul class="ul-form">
					<li style="width: 30px;position: relative;">
						<button id="small" type="button" class="btn btn-default"
							style="width: 100%;position: absolute;left: -10px" onclick="scale('small')">
							<span class="glyphicon glyphicon-chevron-right" style="font-size: 5px"></span>
						</button>
						<button id="big" type="button" class="btn btn-default"
							style="width: 100%;position: absolute;left: -10px;display: none" onclick="scale('big')">
							<span class="glyphicon glyphicon-chevron-left" style="font-size: 5px"></span>
						</button></li>
					<li>

						<button onclick="doSave();" class="btn btn-system btn-sm " type="button">保存</button></li>
					<li>
						<button onclick="closeAddUi(false);" class="btn btn-system btn-sm " type="button">退出</button></li>
					<li id="clearPriceTd" style="display: none"><input type="checkbox" id="clearPrice" name="clearPrice"
						onclick="search()" />套餐及升级材料</li>
					<li><label>材料编码</label> <input type="text" id="code" name="code" value="${item.code}"
						onkeydown="enterClick(window.event,'item')" /></li>
					<li><label>材料名称</label> <input type="text" id="itemDescr" name="itemDescr" value="${item.descr}"
						onkeydown="enterClick(window.event,'item')" /></li>
					<li>
						<button onclick="search();" class="btn btn-system btn-sm " type="button">查询</button></li>
				</ul>
			</form>
		</div>

	</div>
	<div style="width:1330px">
		<div style="width: 155px;height: 310px;float: left;">
			<div>
				<table id="dataTable"></table>
			</div>
			<!--标签页内容 -->
			<div style="padding:0px 0px 2px 6px;border: 1px solid #DDDDDD">
				<c:if test="${itemPlan.itemType1!='ZC'}">
					<button id="reloadBtn" type="button" class="btn btn-xs btn-system  " onclick="reloadArea();" style="display: none">重新加载区域</button>
					<button type="button" class="btn btn-xs btn-system btn-block" onclick="delete_();">删除</button>
				</c:if>
				<c:if test="${itemPlan.itemType1=='ZC'}">
					<div class="panel panel-system" style="margin-left:-8px">
							<div class="panel-body">
								<div class="btn-group-xs" style="background-color: rgb(244,244,244)">
							    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:2px; margin-left: 3px" onclick="fixAreaAdd('A')">新增</button>
							    	<button type="button" id="insertBtn" class="btn btn-system" style="padding:2px; margin-left: 3px" onclick="fixAreaAdd('I')">插入</button>
							    	<button type="button" id="editBtn" class="btn btn-system" style="padding:2px; margin-left: 3px" onclick="fixAreaAdd('M')">编辑</button>
							    	<button type="button" class="btn btn-system" style="padding:2px; margin-left: 3px" onclick="delete_();">删除</button>
								</div>
							</div>
					</div>
				 </c:if>
				 <c:if test="${itemPlan.itemType1!='ZC'}">	
					<ul class="nav nav-sm nav-tabs">
						<li class="active"><a href="#menu1" data-toggle="tab">新增</a>
						</li>
						<li><a href="#menu2" data-toggle="tab">编辑</a>
						</li>
						<li><a href="#menu3" data-toggle="tab">插入</a>
						</li>
					</ul>
					<div class="tab-content">
						<div id="menu1" class="tab-pane fade in active">
							<input type="text" style="width: 140px" class="form-control" placeholder="按回车时新增"
								value="${fixArea.descr}" onkeydown="enterClick(window.event,'fixAreaAdd',this)" />
						</div>
						<div id="menu2" class="tab-pane fade ">
							<input type="text" style="width: 140px" class="form-control" placeholder="按回车时编辑"
								value="${fixArea.descr}" onkeydown="enterClick(window.event,'fixAreaEdit',this)"
								onfocus="showValue(this)" />
						</div>
						<div id="menu3" class="tab-pane fade">
							<input type="text" style="width: 140px" class="form-control" placeholder="按回车时插入"
								value="${fixArea.descr}" onkeydown="enterClick(window.event,'fixAreaInsert',this)" />
						</div>
					</div>
				 </c:if>	
			</div>
		</div>
		<div id="intProdDiv" style="width: 130px;height: 310px;float: left;display: none">
			<div>
				<table id="intProdDataTable"></table>
			</div>
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system " onclick="addIntProd();">新增</button>
					<button type="button" class="btn btn-system " onclick="updateIntProd();">编辑</button>
				</div>
			</div>
		</div>
		<div style="width: 1170px;height: 310px;float: left;margin-left: 1px;" id="id_detail">
			<ul class="nav nav-sm nav-tabs">
				<li class="active"><a href="#tab1" data-toggle="tab">材料</a>
				</li>
				<li id="itemTemp" class="" style="display: none"><a href="#tab2" data-toggle="tab">材料模板</a>
				</li>
				<li class=""><a href="#tab3" data-toggle="tab">材料批次</a>
				</li>
				<li id="itemSet" class="" style="display: none"><a href="#tab4" data-toggle="tab">套餐包</a>
				</li>
				<li  class="itemGift"><a href="#tab5" data-toggle="tab">赠送项目</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab1" class="tab-pane fade in active" style="height: 300px; ">
					<iframe id="iframe" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:none "
						src="${ctx }/admin/itemPlan/goItem?itemType1=${itemPlan.itemType1}&itemType2=${itemPlan.itemType2}&custType=${itemPlan.custType}&custCode=${itemPlan.custCode}"></iframe>
				</div>
				<div id="tab2" class="tab-pane fade " style="height: 300px; ">
					<iframe id="itemIframe" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:none"
						src="${ctx }/admin/itemPlan/goItemTemp?itemType1=${itemPlan.itemType1}&custCode=${itemPlan.custCode}&custType=${itemPlan.custType}"></iframe>
				</div>
				<div id="tab3" class="tab-pane fade" style="height: 300px;border: none ">
					<iframe style="width: 100%;height: 100%;border-top:none;border-left: 1px solid #DDDDDD;"
						src="${ctx }/admin/itemChg/goItemBatchDetailList?itemType1=${itemPlan.itemType1}&moduleCall=itemPlan&custCode=${itemPlan.custCode}"></iframe>
				</div>
				<div id="tab4" class="tab-pane fade" style="height: 300px;border: none ">
					<iframe id="itemSetFrame" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
						src="${ctx }/admin/itemChg/goItemSetDetailList?itemType1=${itemPlan.itemType1}&custType=${itemPlan.custType}&custCode=${itemPlan.custCode}"></iframe>
				</div>
				<div id="tab5" class="tab-pane fade" style="height: 300px;border: none ">
					<iframe id="itemGiftFrame" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
						src="${ctx }/admin/itemPlan/goGift?custCode=${itemPlan.custCode}&itemType1=${itemPlan.itemType1}&custType=${itemPlan.custType}&isService=${itemPlan.isService}"></iframe>
				</div>
			</div>
		</div>
	</div>
	<div style="width: 1330px;height: 258px;float: left;margin-top: 20px">
		<div style="width: 1330px">
			<table id="itemChgDetailDataTable"></table>

		</div>

	</div>
	</div>

	</div>
</body>
</html>
