<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基础预算--快速新增</title>
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
		"${ctx}/admin/baseItem/goItemSelect?descr="+$("#itemDescr").val()+"&isOutSet=${baseItemPlan.isOutSet}&custType=${baseItemPlan.custType}&canUseComBaseItem=${baseItemPlan.canUseComBaseItem}&custCode=${baseItemPlan.custCode}";
}

function doSave(){
	if($("#baseItemPlanDetailDataTable").jqGrid('getGridParam','records')>0) saveData();
}
function saveData(){
	var ids = $("#baseItemPlanDetailDataTable").getDataIDs();
	var hasTC=false;
	var gridDatas = [];
	$.each(ids,function(k,id){
 		var row = $("#baseItemPlanDetailDataTable").jqGrid('getRowData', id);
 		row.sumunitprice=myRound(row.qty*row.unitprice);
 		row.summaterial=myRound(row.qty*row.material);
 		if((row.category=="5"||row.category=="6")&&row.baseitemsetno==""){
 			hasTC=true;
 		}else{
 			gridDatas.push(row);
 		} 
    	
    });
    if(hasTC){
 		art.dialog({
			content: "存在套餐内材料,该保存会自动删除包含套餐内材料记录，是否继续？",
			ok: function () {
      			Global.Dialog.returnData = gridDatas;
   				closeWin();
    		}
		});
    }else{
		Global.Dialog.returnData = gridDatas;
		closeWin();
    }  		
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
		url:"${ctx}/admin/fixArea/goJqGrid?custCode=${baseItemPlan.custCode}&itemType1=${baseItemPlan.itemType1}",
		height:250,
		rownumbers:false,
		rowNum:10000,
		styleUI: 'Bootstrap',
		colModel : [
	       {name : 'Descr',index : 'Descr',width : 155,label:'<h7>装修区域</h7><br/><input type="text" placeholder="按回车时搜索" class="form-control" onkeydown="enterClick(window.event,\'fixAreaSearch\',this)">',sortable : false,align : "left"},
	       {name : 'PK',index : 'PK',width : 100,label:'区域编号',sortable : false,align : "center",hidden:true},
	       {name : 'DispSeq',index : 'DispSeq',width : 100,label:'序号',sortable : false,align : "center",hidden:true},
	       {name: 'PrePlanAreaPK', index: 'PrePlanAreaPK', width: 100, label: "空间pk", sortable: true, align: "left", hidden: true},
		   {name: 'preplanareadescr', index: 'preplanareadescr', width: 100, label: "名称", sortable: true, align: "left", hidden: true}      
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
        	 $("#prePlanAreaPK").val(rowData.PrePlanAreaPK);
         	 $("#prePlanAreaDescr").val(rowData.preplanareadescr);
        }
	});
   	Global.JqGrid.initJqGrid("baseItemPlanDetailDataTable",{
		height:233,
		cellEdit:true,
		cellsubmit:'clientArray',
		styleUI: 'Bootstrap',
		rowNum:10000,
		colModel : [
			{name: "deleteBtn", index: "deleteBtn", width: 55, label: "删除", sortable: false, align: "left", formatter:deleteBtn},
			{name: "baseitemcode", index: "baseitemcode", width: 98, label: "项目编号", sortable: true, align: "left",hidden:true},
			{name: "categorydescr", index: "categorydescr", width: 88, label: "项目类型", sortable: true, align: "left",hidden:true},
			{name: "category", index: "category", width: 88, label: "项目类型", sortable: true, align: "left",hidden:true},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "fixareadescr", index: "fixareadescr", width: 98, label: "区域", sortable: true, align: "left",count:true},
			{name: "baseitemdescr", index: "baseitemdescr", width: 331, label: "基础项目", sortable: true, align: "left"},
			{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left",hidden:true},
			{name: "basealgorithm", index: "basealgorithm", width: 70, label: "算法", sortable:false, align: "left", hidden: true},
			{name: "basealgorithmdescr", index: "basealgorithmdescr", width: 100, label: "算法", sortable: true, align: "left",editable:true,
				edittype:'select',
				editoptions:{ 
		  			dataUrl: '${ctx}/admin/baseItem/getBaseAlgorithm',
					postData: function(){
						var ret = selectDataTableRow("baseItemPlanDetailDataTable");
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
	  						algorithClick(e);
	  					}
		  			}, 
		  		
		  		]}
	 		},
			{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "uom", index: "uom", width: 55, label: "单位", sortable: true, align: "left"},
			{name: "uomdescr", index: "uomdescr", width: 55, label: "单位", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 80, label: "市场价", sortable: true, align: "right",hidden:true},
			{name: "unitprice", index: "unitprice", width: 80, label: "人工单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
			{name: "baseitemsetno", index: "baseitemsetno", width: 60, label: "套餐包", sortable: true, align: "left"},
            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left",hidden: true},
            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},	
			{name: "remark", index: "remark", width: 414, label: "备注", sortable: true, align: "left",editable:true},
			{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
			{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left", hidden: true},
			{name: "isgift", index: "isgift", width: 80, label: "是否赠送项目", sortable: true, align: "left", hidden: true},
			{name: "giftpk", index: "giftpk", width: 68, label: "赠送项目编号", sortable: true, align: "left", hidden: true},
			{name: "giftdescr", index: "giftdescr", width: 68, label: "赠送项目", sortable: true, align: "left", hidden: true},
			{name: "isoutset", index: "isoutset", width: 68, label: "是否套餐外项目", sortable: true, align: "left", hidden: true},
			{name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left", hidden: true},
			{name: "preplanareapk", index: "preplanareapk", width: 80, label: "空间pk", sortable: true, align: "left", hidden: true},
			{name: "autoqty", index: "autoqty", width: 80, label: "系统算量", sortable: true, align: "right", hidden: true},
        ], 
        gridComplete:function(){
	         var ids=$("#baseItemPlanDetailDataTable").jqGrid("getDataIDs");
	         if( ($.trim($("#m_umState").val())=="A")){
	         	   $.each(ids,function(k,v){
						if(v.isfixprice=='1'){
							$("#baseItemPlanDetailDataTable").jqGrid('setCell', v, 'unitprice', '', 'not-editable-cell');
							$("#baseItemPlanDetailDataTable").jqGrid('setCell', v, 'material', '', 'not-editable-cell');
						}
           	 		});
			 }  	
	        /*  scrollToPosi(ids.length,"baseItemPlanDetailDataTable"); */
	        var lastTr = $("#baseItemPlanDetailDataTable tr:last");
		   	lastTr.focus();
         },
         beforeSaveCell: function (id, name, val, iRow, iCol) {
    		var rowData = $("#baseItemPlanDetailDataTable").jqGrid("getRowData", id);
      		if ((name=="qty"||name=="unitprice"||name=="material")  && "${baseItemPlan.custTypeType}"=="2" && (rowData.category!="4" &&rowData.category!="2")  && val<0){
   				art.dialog({
                 	content: "套餐类客户只有基础项目是套餐类减项和套餐内主项目的,才允许预算为负数！"
               	});
   				return "0";
      		}
      	
    	 },
         afterSaveCell:function(id,name,val,iRow,iCol){
              var rowData = $("#baseItemPlanDetailDataTable").jqGrid('getRowData',id);
              switch (name){
              	case 'qty':
               		$("#baseItemPlanDetailDataTable").setCell(id,'lineamount',myRound(val*rowData.unitprice)+myRound(val*rowData.material));
               	break;
               	case 'unitprice':
               		$("#baseItemPlanDetailDataTable").setCell(id,'lineamount',myRound(val*rowData.qty)+myRound(rowData.qty*rowData.material));
               	break;
               	case 'material':
               		$("#baseItemPlanDetailDataTable").setCell(id,'lineamount',myRound(rowData.unitprice*rowData.qty)+myRound(rowData.qty*val));
               	break;
              		
              }
              var lineamount=$("#baseItemPlanDetailDataTable").getCol('lineamount',false,'sum');   
              $("#baseItemPlanDetailDataTable").footerData('set', { "lineamount": lineamount });       
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
function fixAreaAdd(m_umState){
    var url="${ctx}/admin/fixArea/goFixAreaItemPlan?";
    var ret = selectDataTableRow();
	var title="新增";
	if(m_umState== "A"){
		url+="custCode=${baseItemPlan.custCode}&itemType1=${baseItemPlan.itemType1}&isService=0&m_umState="+m_umState
 	}else if(m_umState=="M"){
 		url+="pk="+ret.PK+"&m_umState="+m_umState
 		title="编辑";
 	}else{
 		console.log(m_umState== "I");
 		url+="pk="+ret.PK+"&m_umState="+m_umState
 		title="插入";
 	}
	url+="&mustImportTemp="+"${baseItemPlan.mustImportTemp}";
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
	var ret= $("#baseItemPlanDetailDataTable").jqGrid('getRowData', rowId);
	var nextId=$("#baseItemPlanDetailDataTable tr[id="+rowId+"]").next().attr("id");
	var preId=$("#baseItemPlanDetailDataTable tr[id="+rowId+"]").prev().attr("id");
	if(ret.ismainitem=="1"&&ret.baseitemsetno!=""){
		var ids = $("#baseItemPlanDetailDataTable").getDataIDs();
        $.each(ids,function(k,id){
		 	var row = $("#baseItemPlanDetailDataTable").jqGrid('getRowData', id);
		 	if(row.baseitemsetno==ret.baseitemsetno){
  				 $("#baseItemPlanDetailDataTable").jqGrid('delRowData',id);
  			}
 		});	
  	}else if(ret.ismainitem=="0"&&ret.baseIitemsetno!=""){
   		art.dialog({
   			content:"套餐包套餐内项目不能删除！"
   		});
   		return ;		
    }else{
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

function algorithClick(e){
	var rowid = $("#baseItemPlanDetailDataTable").getGridParam("selrow");
    var rowData = $("#baseItemPlanDetailDataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#baseItemPlanDetailDataTable").setRowData($(e.target).attr("rowid"), {
    	basealgorithm:$(e.target).val(),
    });
    changeAlgorithm($(e.target).attr("rowid"));
}

function changeAlgorithm(id){
	var ret = $("#baseItemPlanDetailDataTable").jqGrid('getRowData', id);
	var datas=$("#page_form").jsonForm();
	datas.custCode="${baseItemPlan.custCode}";
	datas.baseItemCode=ret.baseitemcode;
	datas.baseAlgorithm=ret.basealgorithm;
	datas.prePlanAreaPK=ret.preplanareapk; 
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
				$("#baseItemPlanDetailDataTable").setRowData(id,{
					'qty':qty, 'autoqty':qty,
					'sumunitprice':myRound(qty*ret.unitprice),
					'summaterial':myRound(qty*ret.material),
			  		'lineamount':myRound(qty*ret.unitprice)+myRound(qty*ret.material),
			  	});
			}
	});	
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
					<input type="hidden" id="prePlanAreaPK" name="prePlanAreaPK" />
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
				<!--标签页内容 -->
				<div style="padding:0px 0px 2px 6px;border: 1px solid #DDDDDD">
					<div class="panel panel-system" style="margin-left:-8px">
						<div class="panel-body">
							<div class="btn-group-xs" style="background-color: rgb(244,244,244)">
						    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;margin-left: 3px;" onclick="fixAreaAdd('A')">新增</button>
						    	<button type="button" id="insertBtn" class="btn btn-system" style="padding:1px; margin-left: 3px;" onclick="fixAreaAdd('I')">插入</button>
						    	<button type="button" id="editBtn" class="btn btn-system" style="padding:1px; margin-left: 3px;" onclick="fixAreaAdd('M')">编辑</button>
						    	<button type="button" id="deleteBtn" class="btn btn-system" style="padding:1px; margin-left: 3px;" onclick="delete_()">删除</button>
						    	
							</div>
						</div>
					</div>
					
					<%--<ul class="nav nav-sm nav-tabs">
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
				--%>
				</div>
			</div>
			<div style="width: 1170px;height: 310px;float: left;margin-left: 1px;" id="id_detail">
				<ul class="nav nav-sm nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">基础项目</a>
					</li>
					<li class=""><a href="#tab2" data-toggle="tab">基础项目模板</a>
					</li>
					<li class=""><a href="#tab3" data-toggle="tab">赠送项目</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tab1" class="tab-pane fade in active" style="height: 300px;">
						<iframe id="iframe"
							style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
							src="${ctx }/admin/itemPlan/goBaseItem?isOutSet=${baseItemPlan.isOutSet}&baseItemType1=${baseItemPlan.baseItemType1}&custType=${baseItemPlan.custType}&canUseComBaseItem=${baseItemPlan.canUseComBaseItem}&custCode=${baseItemPlan.custCode}"></iframe>
					</div>
					<div id="tab2" class="tab-pane fade" style="height: 300px;border: none ">
						<iframe id="itemIframe"
							style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
							src="${ctx }/admin/itemPlan/goBaseItemTemp?custType=${baseItemPlan.custType}&canUseComBaseItem=${baseItemPlan.canUseComBaseItem}"></iframe>
					</div>
					<div id="tab3" class="tab-pane fade" style="height: 300px;border: none ">
						<iframe id="giftframe"
							style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:1px solid #DDDDDD"
							src="${ctx }/admin/itemPlan/goGift?custCode=${baseItemPlan.custCode}&itemType1=JZ&isOutSet=${baseItemPlan.isOutSet}&baseItemType1=${baseItemPlan.baseItemType1}&custType=${baseItemPlan.custType}&canUseComBaseItem=${baseItemPlan.canUseComBaseItem}"></iframe>
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
