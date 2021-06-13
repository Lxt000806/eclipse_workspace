<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基础项目结算预算--快速新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
var leftOffset;
var isAddFixArea=false;
function search(){
	document.getElementById("iframe").contentWindow.document.getElementById("listFrame_q").src=
		"${ctx}/admin/baseCheckItem/goItemSelect?descr="+$("#itemDescr").val();
}

function doSave(){
	if($("#baseItemPlanDetailDataTable").jqGrid('getGridParam','records')>0)	saveData();
}
function saveData(){
	var ids = $("#baseItemPlanDetailDataTable").getDataIDs();
	var gridDatas = [];
	$.each(ids,function(k,id){
 		var row = $("#baseItemPlanDetailDataTable").jqGrid('getRowData', id);
 		if(row.totalofferpri==""){
 			row.totalofferpri=0;
 		}
 		if(row.totalmaterial==""){
 			row.totalmaterial=0;
 		}
 		if(row.totalprjofferpri==""){
 			row.totalprjofferpri=0;
 		}
 		if(row.totalprjmaterial==""){
 			row.totalprjmaterial=0;
 		}
 		row.total=parseFloat(row.totalofferpri)+parseFloat(row.totalmaterial);
 		row.prjtotal=parseFloat(row.totalprjofferpri)+parseFloat(row.totalprjmaterial);
 		gridDatas.push(row);
    });
	Global.Dialog.returnData = gridDatas;
	closeWin();
}
function closeAddUi(){
	 if($("#baseItemPlanDetailDataTable").jqGrid('getGridParam','records')>0){
		 art.dialog({
				content: "是否保存被更改的数据?",
				ok: function () {
	      			saveData();
	    		},
	    		cancel:function(){
	   				 closeWin();
	    		}
		});
		return;
	 }
		closeWin();
}
$(document).ready(function(){
	$('input','#page_form').unbind('keydown'); 
	$.ajax({
		url:'${ctx}/admin/fixArea/doAddRegular_FixAreaFixArea?custCode=${baseItemPlan.custCode}',
		cache: false,
	    success: function(obj){
	  		if(obj&&obj.ret!=1){
		  		art.dialog({
					content: obj.errmsg
				});
				return;
		  	}
	    }
	 }); 
	 
    Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/fixArea/goJqGrid?custCode=${baseCheckItemPlan.custCode}&itemType1=${baseCheckItemPlan.itemType1}",
		height:280,
		rownumbers:false,
		rowNum:10000,
		styleUI: 'Bootstrap',
		colModel : [
	       {name : 'Descr',index : 'Descr',width : 155,label:'<h7>装修区域</h7><br/><input type="text" placeholder="按回车时搜索" class="form-control" onkeydown="enterClick(window.event,\'fixAreaSearch\',this)">',sortable : false,align : "left"},
	       {name : 'PK',index : 'PK',width : 100,label:'区域编号',sortable : false,align : "center",hidden:true},
	       {name : 'DispSeq',index : 'DispSeq',width : 100,label:'序号',sortable : false,align : "center",hidden:true}       
        ],
        gridComplete:function(){
           	if('${baseItemPlan.fixAreaPk}'){
	           	var  rowData=$("#dataTable").jqGrid('getRowData');
	           	$.each(rowData,function(k,v){
	           	 	if(v.PK=='${baseItemPlan.fixAreaPk}'){
	           	 	   $("#dataTable").setSelection(k+1, true);
	           	 	   scrollToPosi(k+1);
	           	 	   return false;
	           	 	}
	           	 })
           	}
           	if(isAddFixArea){
            	isAddFixArea=false;
                var ids=$("#dataTable").jqGrid("getDataIDs");
            	$("#dataTable").setSelection(ids.length, true);
            	scrollToPosi(ids.length);
            }
        },
        onSelectRow: function (rowid, status) {
        	 var itemType1='${baseItemPlan.itemType1}'.trim();
        	 var rowData = $("#dataTable").jqGrid('getRowData',rowid);
        	 var fixAreaPk=parseInt(rowData.PK);
        	 $("#fixAreaPk").val(fixAreaPk);
        	 $("#fixAreaDescr").val(rowData.Descr);
        }
	});
   	Global.JqGrid.initJqGrid("baseItemPlanDetailDataTable",{
		height:233,
		cellEdit:true,
		cellsubmit:'clientArray',
		styleUI: 'Bootstrap',
		rowNum:10000,
		colModel : [
			{name: "ordername", index: "ordername", width: 98, label: "排序", sortable: true, align: "left", hidden: true},
			{name: "deleteBtn", index: "deleteBtn", width: 55, label: "删除", sortable: false, align: "left", formatter:deleteBtn},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left", hidden: true},
			{name: "fixareadescr", index: "fixareadescr", width: 98, label: "区域", sortable: true, align: "left"},
			{name: "code", index: "code", width: 84, label: "基础结算项目编号", sortable: true, align: "left", hidden: true},
			{name: "baseitemtype1", index: "baseitemtype1", width: 84, label: "baseitemtype1", sortable: true, align: "left", hidden: true},
			{name: "baseitemtype2", index: "baseitemtype2", width: 84, label: "baseitemtype2", sortable: true, align: "left", hidden: true},
			{name: "descr", index: "descr", width: 120, label: "基础结算项目", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 87, label: "工种类型12", sortable: true, align: "left", hidden: true},
			{name: "worktype12", index: "worktype12", width: 88, label: "工种类型12", sortable: true, align: "left", hidden: true},
			{name: "worktype1descr", index: "worktype1descr", width: 87, label: "工种类型1", sortable: true, align: "left", hidden: true},
			{name: "worktype1", index: "worktype1", width: 88, label: "工种类型1", sortable: true, align: "left",hidden:true},
			{name: "issubsidyitem", index: "issubsidyitem", width: 85, label: "发包补贴项目", sortable: false, align: "left",hidden:true},
			{name: "issubsidyitemdescr", index: "issubsidyitemdescr", width: 90, label: "发包补贴项目", sortable: true, align: "left"},
			{name: "offerpri", index: "offerpri", width: 88, label: "人工单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "material", index: "material", width: 86, label: "材料单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "totalofferpri", index: "totalofferpri", width: 83, label: "人工总价", sortable: true, align: "right", sum: true},
			{name: "totalmaterial", index: "totalmaterial", width: 80, label: "材料总价", sortable: true, align: "right", sum: true},
			{name: "totalsetofferpri", index: "totalsetofferpri", width: 83, label: "套餐内人工总价", sortable: true, align: "right",hidden:true},
			{name: "totalsetmaterial", index: "totalsetmaterial", width: 80, label: "套餐内材料总价", sortable: true, align: "right",hidden:true},
			{name: "totalindiofferpri", index: "totalindiofferpri", width: 83, label: "个性化人工总价", sortable: true, align: "right", hidden:true},
			{name: "totalindimaterial", index: "totalindimaterial", width: 80, label: "个性化材料总价", sortable: true, align: "right", hidden:true},
			{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right", sum: true,hidden:true},
			{name: "prjofferpri", index: "prjofferpri", width: 120, label: "项目经理人工单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "prjmaterial", index: "prjmaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "totalprjofferpri", index: "totalprjofferpri", width: 120, label: "项目经理人工总价", sortable: true, align: "right", sum: true},
			{name: "totalprjmaterial", index: "totalprjmaterial", width: 120, label: "项目经理材料总价", sortable: true, align: "right", sum: true},
			{name: "prjtotal", index: "prjtotal", width: 100, label: "项目经理总价", sortable: true, align: "right",sum: true,hidden:true},
			{name: "remark", index: "remark", width: 219, label: "备注", sortable: true, align: "left",editable:true},
			{name: "lastupdate", index: "lastupdate", width: 141, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left", hidden: true},
			{name: "dispseq", index: "dispseq", width: 68, label: "顺序", sortable: true, align: "left", hidden: true},
			{name: "type", index: "type", width: 120, label: "项目类型", sortable: true, align: "right", hidden: true},
			{name: "typedescr", index: "typedescr", width: 120, label: "项目类型", sortable: true, align: "right", hidden: true},
			{name: "tempofferpri", index: "tempofferpri", width: 88, label: "人工单价", sortable: true, align: "right", hidden: true},
			{name: "tempmaterial", index: "tempmaterial", width: 86, label: "材料单价", sortable: true, align: "right", hidden: true},
			{name: "tempprjofferpri", index: "tempprjofferpri", width: 120, label: "项目经理人工单价", sortable: true, align: "right", hidden: true},
			{name: "tempprjmaterial", index: "tempprjmaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right", hidden: true},
        ], 
        gridComplete:function(){
	         var ids=$("#baseItemPlanDetailDataTable").jqGrid("getDataIDs");
	         if( ($.trim($("#m_umState").val())=="A")){
	         	   $.each(ids,function(k,v){
						$("#baseItemPlanDetailDataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
						$("#baseItemPlanDetailDataTable").jqGrid('setCell', v, 'material', '', 'not-editable-cell');	
				
           	 		});
			 }  	
	        var lastTr = $("#baseItemPlanDetailDataTable tr:last");
		   	lastTr.focus();
         },
         beforeSaveCell: function (id, name, val, iRow, iCol) {
    		var rowData = $("#baseItemPlanDetailDataTable").jqGrid("getRowData", id);
      		if ((name=="qty"||name=="offerpri"||name=="material"||name=="prjofferpri"||name=="prjmaterial")&& val<0){
   				art.dialog({
                 	content: "请输入大于等于0的数！"
               	});
   				return "0";
      		}
    	 },
         afterSaveCell:function(id,name,val,iRow,iCol){
              var rowData = $("#baseItemPlanDetailDataTable").jqGrid('getRowData',id);
              var type=rowData.type;
              switch (name){
              	case 'qty':
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalofferpri',myRound(val*rowData.offerpri));
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalmaterial',myRound(val*rowData.material));
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalprjofferpri',myRound(val*rowData.prjofferpri));
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalprjmaterial',myRound(val*rowData.prjmaterial));
               		if(type=="1"){
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalsetofferpri',myRound(val*rowData.offerpri));
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalsetmaterial',myRound(val*rowData.material));
               		}else if(type=="2"){
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalindiofferpri',myRound(val*rowData.offerpri));
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalindimaterial',myRound(val*rowData.material));
               		}
               	break;
               	case 'offerpri':
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalofferpri',myRound(val*rowData.qty));
               		if(type=="1"){
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalsetofferpri',myRound(val*rowData.qty));
               		}else if(type=="2"){
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalindiofferpri',myRound(val*rowData.qty));
               		}
               	break;
               	case 'material':
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalmaterial',myRound(val*rowData.qty));
               		if(type=="1"){
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalsetmaterial',myRound(val*rowData.qty));
               		}else if(type=="2"){
               			$("#baseItemPlanDetailDataTable").setCell(id,'totalindimaterial',myRound(val*rowData.qty));
               		}
               	break;
              	case 'prjofferpri':
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalprjofferpri',myRound(val*rowData.qty));
               	break;	
               	case 'prjmaterial':
               		$("#baseItemPlanDetailDataTable").setCell(id,'totalprjmaterial',myRound(val*rowData.qty));
               	break;
              }
              var totalofferpri=$("#baseItemPlanDetailDataTable").getCol('totalofferpri',false,'sum');   
              $("#baseItemPlanDetailDataTable").footerData('set', { "totalofferpri": totalofferpri });
              var totalmaterial=$("#baseItemPlanDetailDataTable").getCol('totalmaterial',false,'sum');   
              $("#baseItemPlanDetailDataTable").footerData('set', { "totalmaterial": totalmaterial });     
              var totalprjofferpri=$("#baseItemPlanDetailDataTable").getCol('totalprjofferpri',false,'sum');   
              $("#baseItemPlanDetailDataTable").footerData('set', { "totalprjofferpri": totalprjofferpri });     
              var totalprjmaterial=$("#baseItemPlanDetailDataTable").getCol('totalprjmaterial',false,'sum');   
              $("#baseItemPlanDetailDataTable").footerData('set', { "totalprjmaterial": totalprjmaterial });            
           },
           beforeSelectRow:function(id){
	            setTimeout(function(){
	         	   relocate(id,'baseItemPlanDetailDataTable');
		        },100)
          }
          
		});
   }); 
function goto_query(table,param){
	$("#"+table).jqGrid("setGridParam",{postData:param,page:1}).trigger("reloadGrid");
}
function  update(e){
	var ret = selectDataTableRow();
    if (ret) {
    	var selectDescr=ret.Descr
    	var descr=e.value;
		if(selectDescr=='水电项目'||selectDescr=='土建项目'||selectDescr=='安装项目'||selectDescr=='综合项目'||selectDescr=='全房'){
			art.dialog({
				time: 1000,
				content: selectDescr+"区域不能修改"
			});	
			return;
		}
		$.ajax({
			url:'${ctx}/admin/fixArea/updateFixArea?custCode=${baseItemPlan.custCode}&itemType1=${baseItemPlan.itemType1}&isService=0&descr='+descr+'&pk='+ret.PK,
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
	if(descr=='水电项目'||descr=='土建项目'||descr=='安装项目'||descr=='综合项目'||descr=='全房'){
		art.dialog({
			content: "不能在材料预算中新增"+descr+"区域"
		});
		return;
	}
	
	$.ajax({
		url:'${ctx}/admin/fixArea/addtFixArea?custCode=${baseItemPlan.custCode}&itemType1=${baseItemPlan.itemType1}&isService=0&descr='+descr,
		cache: false,
	    success: function(obj){
	  		if(obj){	
	  			art.dialog({
					content: "装修区域已存在,不能新增！"
				});
	  		}else{
	  			e.value='';
	  			isAddFixArea=true;
	  			return goto_query("dataTable",{"descr":''});
	  		}	
	    }
	 });
}
function insert(e){
	var ret = selectDataTableRow();
    if (ret) {
       var descr=e.value;
	if(descr=='水电项目'||descr=='土建项目'||descr=='安装项目'||descr=='综合项目'||descr=='全房'){
		art.dialog({
			content: "不能在材料预算中插入"+descr
		});
		return;
	}
	$.ajax({
		url:'${ctx}/admin/fixArea/insertFixArea?custCode=${baseItemPlan.custCode}&itemType1=${baseItemPlan.itemType1}&isService=0&descr='+descr+'&dispSeq='+ret.DispSeq,
		cache: false,
	    success: function(obj){
	  		if(obj){	
	  			art.dialog({
				content: "装修区域已存在,不能插入！"
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
function delete_(){
	var ret = selectDataTableRow();
    if (ret) {
    	var itemType1='${baseItemPlan.itemType1}'.trim();
      	var descr=ret.Descr;
      	var strmsg,warn;
      	strmsg="删除该区域将会删除该区域下的基础预算";
      	warn="确定要删除["+descr+"]装修区域?<br/>"+strmsg;
      	if(descr=="全房"||descr=="土建项目"||descr=="水电项目"||descr=="安装项目"||descr=="综合项目"||descr=="全房"){
	      	art.dialog({
				content: descr+"区域不能删除！"
			});
			return;
     	}
		art.dialog({
			content: warn,
	  		ok: function () {
	      	$.ajax({
				url:'${ctx}/admin/fixArea/deleteFixArea?itemType1=JZ&pk='+ret.PK,
				cache: false,
			    success: function(obj){
			  		if(obj&&obj.ret!=1){
			  		art.dialog({
						content: obj.errmsg
					});
						return;
			  		}
			  		if(top.$("#iframe_itemPlan_jcys")[0]){
			        	var topFrame="#iframe_itemPlan_jcys";
			        }else {
			          var topFrame="#iframe_itemPlan_jcys_confirm";
			        }
			  		var dataTable=$(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable"));
			  		var arr= dataTable.jqGrid("getRowData");
			  		var brr=$("#baseItemPlanDetailDataTable").jqGrid("getRowData");
			  		$.each(arr,function(k,v){
			  			if(v.fixareadescr==descr)
			  			dataTable.delRowData(k+1);
			  		})
			  		$.each(brr,function(k,v){
			  			if(v.fixareadescr==descr)
			  			$("#baseItemPlanDetailDataTable").delRowData(k+1);
			  		})
			  		return goto_query("dataTable",{"descr":''});			
				}
			});
   		},
    	cancel:function(){
    		}
		});
    }else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function deleteBtn(v,x,r){
	return "<button type='button' class='btn btn-system btn-xs' onclick='deleteRow("+x.rowId+");'>删除</button>";
}   		 

function deleteRow(rowId){
	var nextId=$("#baseItemPlanDetailDataTable tr[id="+rowId+"]").next().attr("id");
	var preId=$("#baseItemPlanDetailDataTable tr[id="+rowId+"]").prev().attr("id");
    $('#baseItemPlanDetailDataTable').delRowData(rowId);
    if($("#baseItemPlanDetailDataTable").jqGrid('getGridParam','records')>0){
        if(nextId){
            scrollToPosi(nextId,"baseItemPlanDetailDataTable"); 
    	   relocate(nextId,'baseItemPlanDetailDataTable');
    	  $("#baseItemPlanDetailDataTable tr[id="+nextId+"] button").focus();
    	} else{
    		relocate(preId,'baseItemPlanDetailDataTable');
    	 	$("#baseItemPlanDetailDataTable tr[id="+preId+"] button").focus();
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
</script>
</head>
<body>
	<div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="${baseItemPlan.expired}" /> <input type="hidden"
					id="fixAreaPk" name="fixAreaPk" /> <input type="hidden" id="intProdPk" name="intProdPk" /> <input
					type="hidden" id="fixAreaDescr" name="fixAreaDescr" /> <input type="hidden" id="intProdDescr"
					name="intProdDescr" /> <input type="hidden" id="rowNo" name="rowNo" value="1" />
					<input type="hidden" name="m_umState" id="m_umState" value="${baseItemPlan.m_umState}"/>
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
						<button onclick="closeAddUi();" class="btn btn-system btn-sm " type="button">退出</button></li>
					<li><label>基础项目名称</label> <input type="text" id="itemDescr" name="itemDescr" style="width:160px;"
						value="" onkeydown="enterClick(window.event,'item')" /></li>
					<li>
						<button onclick="search();" class="btn btn-system btn-sm " type="button">查询</button></li>
				</ul>
			</form>
		</div>
		<div style="width:1330px;">
			<div style="width: 155px;height: 310px;float: left;">
				<div>
					<table id="dataTable"></table>
				</div>
			</div>
			<div style="width: 1170px;height: 310px;float: left;margin-left: 1px;" id="id_detail">
				<ul class="nav nav-sm nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">基础结算项目</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tab1" class="tab-pane fade in active" style="height: 300px;">
						<iframe id="iframe"
							style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
							src="${ctx }/admin/baseCheckItemPlan/goBaseCheckItem?baseItemType1=${baseCheckItemPlan.baseItemType1}"></iframe>
					</div>
				</div>
			</div>
		</div>
		<div style="width: 1330px;height: 258px;float: left;margin-top: 20px">
			<div style="width: 1330px">
				<table id="baseItemPlanDetailDataTable"></table>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
