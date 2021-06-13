<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>基装增减--快速新增</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
  	<script type="text/javascript">
    var fixAreaPk = 0;
    var intProdPk = 0;
    var leftOffset;
    var isAddFixArea = false;
    var clickFlag=false;
    function saveData() {
      	Global.Dialog.returnData = $("#baseItemChgDetailDataTable").jqGrid("getRowData");
    }
    function doSave() {
      	if ($("#baseItemChgDetailDataTable").jqGrid("getGridParam", "records") > 0)  saveData();
      	closeWin();
    }
    function closeAddUi() {
      	if ($("#baseItemChgDetailDataTable").jqGrid("getGridParam", "records") > 0) {
        	art.dialog({
          		content: "是否保存被更改的数据?",
          		ok: function () {
            		saveData();
            		closeWin();
          		},
          		cancel: function () {
            		closeWin();
          		}
        	});
        	return;
      	}
      	closeWin();
    }
    function reloadIntProd() {
      	intProdPk = 0;
      	$("#intProdPk").val(intProdPk);
      	$("#intProdDescr").val("");
    }
    function goto_query(table, param) {
      	fixAreaPk = 0;
      	$("#fixAreaPk").val(fixAreaPk);
      	$("#fixAreaDescr").val("");
      	$("#" + table).jqGrid("setGridParam", {postData: param, page: 1}).trigger("reloadGrid");
    }
    function update(e) {
      	var ret = selectDataTableRow();
      	if (ret) {
        	var descr = e.value;
        	if (descr == "水电项目" || descr == "土建项目" || descr == "安装项目" || descr == "综合项目") {
          		art.dialog({
            		content: descr + "区域不能修改"
          		});
          		return;
        	}
        	$.ajax({
	          	url: "${ctx}/admin/fixArea/updateFixArea?custCode=${baseItemChg.custCode}&itemType1=JZ&isService=0&descr=" + descr + "&pk=" + ret.PK,
	          	cache: false,
	          	success: function (obj) {
		            if (obj) {
		              	art.dialog({
		                	content: "装修区域已存在！"
		              	});
		            } else {
		              	e.value = "";
		              	return goto_query("dataTable", {"descr": ""});
		            }
	          	}
       		});

     	} else {
        	art.dialog({
          	content: "请选择一条记录"
        	});
      	}
    }
    function add(e) {
      	var descr = e.value;
      	if (!descr) return;
      	$.ajax({
        	url: "${ctx}/admin/fixArea/addtFixArea?custCode=${baseItemChg.custCode}&itemType1=JZ&isService=0&descr=" + descr,
        	cache: false,
        	success: function (obj) {
          		if (obj) {
            		art.dialog({
              			content: "装修区域已存在,不能新增！"
            		});
          		} else {
            		e.value = "";
            		isAddFixArea = true;
            		return goto_query("dataTable", {"descr": ""});
          		}

        	}
      	});
    }
    function insert(e) {
      	var ret = selectDataTableRow();
      	if (ret) {
	        var descr = e.value;
	        $.ajax({
	          	url: "${ctx}/admin/fixArea/insertFixArea?custCode=${baseItemChg.custCode}&itemType1=JZ&isService=0&descr=" + descr + "&dispSeq=" + ret.DispSeq,
	          	cache: false,
	          	success: function (obj) {
		            if (obj) {
		              	art.dialog({
		                	content: "装修区域已存在,不能插入！"
		              	});
		            } else {
		              	e.value = "";
		              	return goto_query("dataTable", {"descr": ""});
		            }
	          	}
	        });
     	} else {
        	art.dialog({
          		content: "请选择一条记录"
        	});
      	}
    }
    function delete_() {
      	var ret = selectDataTableRow();
      	if (ret) {
        	var descr = ret.Descr;
        	if (descr == "水电项目" || descr == "土建项目" || descr == "安装项目" || descr == "综合项目") {
          		art.dialog({
            		content: descr + "区域不能删除"
          		});
          		return;
        	}else{
        		art.dialog({
            		content: "合同施工状态不允许删除区域"
          		});
          		return;
        	}
        	/*
        	var strmsg, warn;
        	strmsg = "删除该区域将会删除该区域下的基础预算";
        	warn = "确定要删除[" + descr + "]装修区域?<br/>" + strmsg;
        	art.dialog({
          		content: warn,
          		ok: function () {
            		$.ajax({
              			url: "${ctx}/admin/fixArea/deleteFixArea?pk=" + ret.PK,
              			cache: false,
              			success: function (obj) {
                			if (obj && obj.ret != 1) {
                  				art.dialog({
                    				content: obj.errmsg
                  				});
                  				return;
                			}
                			return goto_query("dataTable", {"descr": ""});
              			}
            		});
          		},
          		cancel: function () {

          		}
        	});
        	*/
      	} else {
        	art.dialog({
          		content: "请选择一条记录"
        	});
      	}
    }
    function deleteBtn(v, x, r) {
      	//return "<button type=\"button\" class=\"btn btn-system btn-xs\" onclick=\"deleteRow('" + x.rowId + "');\">删除</button>";
    	return "<button type='button' class='btn btn-system btn-xs' onclick='deleteRow("+x.rowId+");'>删除</button>";
    }
    function deleteRow(rowId) {
    	var ret= $("#baseItemChgDetailDataTable").jqGrid('getRowData', rowId);
      	var nextId = $("#baseItemChgDetailDataTable tr[id=" + rowId + "]").next().attr("id");
      	var preId = $("#baseItemChgDetailDataTable tr[id=" + rowId + "]").prev().attr("id");
      	if(ret.ismainitem=="1"&&ret.baseitemsetno!=""){
			var ids = $("#baseItemChgDetailDataTable").getDataIDs();
	        $.each(ids,function(k,id){
			 	var row = $("#baseItemChgDetailDataTable").jqGrid('getRowData', id);
			 	if(row.baseitemsetno==ret.baseitemsetno){
	  				 $("#baseItemChgDetailDataTable").jqGrid('delRowData',id);
	  			}
  			});	
   		}else if(ret.ismainitem=="0"&&ret.baseIitemsetno!=""){
    		art.dialog({
    			content:"套餐包套餐内项目不能删除！"
    		});
    		return ;		
   		 }else{
   		 	$("#baseItemChgDetailDataTable").delRowData(rowId);
	      	if ($("#baseItemChgDetailDataTable").jqGrid("getGridParam", "records") > 0) {
	        	if (nextId) {
	          		relocate(nextId, "baseItemChgDetailDataTable");
	          		$("#baseItemChgDetailDataTable tr[id=" + nextId + "] button").focus();
	        	} else {
	          		relocate(preId, "baseItemChgDetailDataTable");
	          		$("#baseItemChgDetailDataTable tr[id=" + preId + "] button").focus();
	        	}
	      	}
   		 }
      	
    }
    
    function enterClick(e, type, d) {
      	if (e.keyCode == 13) {
        	switch (type) {
          	case "item":
            	search();
            	break;
          	case "fixAreaSearch":
            	goto_query("dataTable", {"descr": d.value});
            	break;
          	case "fixAreaAdd":
            	add(d);
            	break;
          	case "fixAreaEdit":
            	update(d);
            	break;
          	case "fixAreaInsert":
            	insert(d);
            	break;
        	}
      	}
    }
    function showValue(e) {
      	var ret = selectDataTableRow();
      	if (ret) {
        	e.value = ret.Descr;
      	}
    }
    function scale(opt) {
      	switch (opt) {
	        case "big":
	          	top.$("#dialog_quickSave").parent().css({"width": "1380px", "left": leftOffset});
	          	$("#" + opt).hide();
	          	$("#small").show();
	          	break;
	        default:
	          	leftOffset = top.$("#dialog_quickSave").parent().css("left");
	          	top.$("#dialog_quickSave").parent().css({"width": "60%", "left": "40%"});
	          	$("#" + opt).hide();
	          	$("#big").show();
	          	break;
      	}
    }
    
    function fixAreaAdd(){
    	//  url: "${ctx}/admin/fixArea/addtFixArea?custCode=${baseItemChg.custCode}&itemType1=JZ&isService=0&descr=" + descr,

        var datas={custCode:"${baseItemChg.custCode}",itemType1:"JZ",isService:"0"};

    	Global.Dialog.showDialog("goFixAreaAdd",{
    		title:"装修区域——新增",
    		postData:datas,
    		url:"${ctx}/admin/baseItemChg/goFixAreaAdd",
    		height:260,
    		width:580,
    		returnFun:function(){
    			goto_query("dataTable", {"descr": ''});	
    		}
    	});		
    }

    function fixAreaEdit(){

    	var ret = selectDataTableRow();

       	if(!ret){
       		art.dialog({
       			content:"请选择一条记录进行编辑",
       		});
       		return;
       	}
        var datas={custCode:"${baseItemChg.custCode}",itemType1:"JZ",isService:"0",fixAreaPk:ret.PK};

    	Global.Dialog.showDialog("goFixAreaEdit",{
    		title:"装修区域——编辑",
    		postData:datas,
    		url:"${ctx}/admin/baseItemChg/goFixAreaEdit",
    		height:260,
    		width:580,
    		returnFun:function(){
    			goto_query("dataTable", {"descr": ''});	
    		}
    	});		
    }

    function fixAreaInsert(){
        var ret = selectDataTableRow();

       	if(!ret){
       		art.dialog({
       			content:"请选择插入位置",
       		});
       		return;
       	}
       	
    	var datas={custCode:"${baseItemChg.custCode}",itemType1:"jZ",isService:"0",dispSeq:ret.DispSeq};
        
    	Global.Dialog.showDialog("goFixAreaInsert",{
    		title:"装修区域——插入",
    		postData:datas,
    		url:"${ctx}/admin/baseItemChg/goFixAreaInsert",
    		height:260,
    		width:580,
    		returnFun:function(){
    			goto_query("dataTable", {"descr": ''});	
    		}
    	});		
    }
 	</script>
</head>
<body>
<div>
  	<div class="query-form">
	    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
	    <input type="hidden" id="expired" name="expired" value="${baseItemChg.expired}"/>
	    <input type="hidden" id="fixAreaPk" name="fixAreaPk"/>
	    <input type="hidden" id="intProdPk" name="intProdPk"/>
	    <input type="hidden" id="fixAreaDescr" name="fixAreaDescr"/>
	    <input type="hidden" id="intProdDescr" name="intProdDescr"/>
	    <input type="hidden" id="rowNo" name="rowNo" value="1"/>
	    <ul class="ul-form">
	    	<li style="width: 30px;position: relative;">
	          	<button id="small" type="button" class="btn btn-default" style="width: 100%;position: absolute;left: -10px"
	                  onclick="scale('small')">
	            <span class="glyphicon glyphicon-chevron-right" style="font-size: 5px"></span>
	          	</button>
	          	<button id="big" type="button" class="btn btn-default"
	                  style="width: 100%;position: absolute;left: -10px;display: none" onclick="scale('big')">
	            <span class="glyphicon glyphicon-chevron-left" style="font-size: 5px"></span>
	          	</button>
	        </li>
	        <li>
	          	<button onclick="doSave();" class="btn btn-system btn-sm" type="button">保存</button>
	        </li>
	        <li>
	          	<button onclick="closeAddUi();" class="btn btn-system btn-sm" type="button">退出</button>
	        </li>
	        <li>
	          	<label>项目名称</label>
	          	<input type="text" id="itemDescr" name="itemDescr" value="${item.descr}"
	                 onkeydown="enterClick(window.event,'item')"/>
	        </li>
	        <li>
	          	<button onclick="search();" class="btn btn-system btn-sm" type="button">查询</button>
	        </li>
		</ul>
		</form>
  	</div>
  
	<div style="width:1330px;">
    	<div style="width: 155px;height: 310px;float: left;">
      		<div>
        		<table id="dataTable"></table>
      		</div>
      		<!--标签页内容 -->
      		<div style="padding:0px 0px 2px 6px;border: 1px solid #DDDDDD;border-bottom:none;" >
	        	<%--<button type="button" class="btn btn-xs btn-system btn-block" onclick="delete_();" hidden="true">删除</button>
	        	
	        	--%><div class="panel panel-system" style="margin-left:-8px" >
					<div class="panel-body">
						<div class="btn-group-xs" style="background-color: rgb(244,244,244)">
					    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 5px"  onclick="fixAreaAdd()">新增</button>
					    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 0px"  onclick="fixAreaEdit()">编辑</button>
					    	<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 0px"  onclick="fixAreaInsert()">插入</button>
							<button type="button" id="saveBtn" class="btn btn-system" style="padding:1px;width:30px;margin-left: 0px"  onclick="delete_()">删除</button>
						</div>
					</div>
				</div>
	        	
	        	<ul class="nav nav-sm nav-tabs" hidden="true">
	          		<li class="active"><a href="#menu1" data-toggle="tab">新增</a></li>
	          		<li><a href="#menu2" data-toggle="tab">编辑</a></li>
	          		<li><a href="#menu3" data-toggle="tab">插入</a></li>
	        	</ul>
	        	<div class="tab-content" hidden="true">
	          		<div id="menu1" class="tab-pane fade in active">
	            		<input type="text" style="width: 140px" class="form-control" placeholder="按回车时新增" value="${fixArea.descr}"
	                   	onkeydown="enterClick(window.event,'fixAreaAdd',this)"/>
	          		</div>
	          		<div id="menu2" class="tab-pane fade">
	            		<input type="text" style="width: 140px" class="form-control" placeholder="按回车时编辑" value="${fixArea.descr}"
	                   	onkeydown="enterClick(window.event,'fixAreaEdit',this)" onfocus="showValue(this)"/>
	          		</div>
	          		<div id="menu3" class="tab-pane fade">
	            		<input type="text" style="width: 140px" class="form-control" placeholder="按回车时插入" value="${fixArea.descr}"
	                   	onkeydown="enterClick(window.event,'fixAreaInsert',this)"/>
	          		</div>
	        	</div>
	        	
      		</div>
    	</div>
    	<div style="width: 205px;height: 315px;float: left;padding-left: 2px;">
      		<iframe id="treeFrame" style="width: 100%;height: 100%;border-left: 1px solid #DDDDDD;border-top:none;"
                  src="${ctx}/admin/baseItemType1/goTree"></iframe>
    	</div>
    	<div style="width: 968px;height: 315px;float: left;padding-left: 2px;">
      		<table id="dataTableSelect"></table>
			<div id="dataTableSelectPager"></div>
    	</div>
  	</div>
  	<div style="width: 1330px;height: 258px;float: left;margin-top: 20px">
    	<div style="width: 1330px">
      		<table id="baseItemChgDetailDataTable"></table>
    	</div>
  	</div>
</div>
<script type="text/javascript">
//基装项目名称
$(function(){
    var gridOption = {
		height:266,
		colModel : [
			{name: "code", index: "code", width: 100, label: "基装项目编号", sortable: true, align: "left", hidden: true},
			{name: "dispseq", index: "dispseq", width: 100, label: "dispseq", sortable: true, align: "left",hidden:true },
		    {name: "baseitemtype1", index: "baseitemtype1", width: 84, label: "baseitemtype1", sortable: true, align: "left", hidden: true},
			{name: "baseitemtype2", index: "baseitemtype2", width: 84, label: "baseitemtype2", sortable: true, align: "left", hidden: true},
			{name: "descr", index: "descr", width: 250, label: "基装项目名称", sortable: true, align: "left"},
			{name: "categorydescr", index: "categorydescr", width: 87, label: "项目类型", sortable: true, align: "left", hidden: ${baseItemChg.custTypeType=="2"?"false":"true"}},
			{name: "category", index: "category", width: 88, label: "项目类型", sortable: true, align: "left",hidden:true},
			{name: "marketprice", index: "marketprice", width: 78, label: "市场价", sortable: true, align: "right"},
			{name: "offerpri", index: "offerpri", width: 88, label: "人工单价", sortable: true, align: "right"},
			{name: "material", index: "material", width: 86, label: "材料单价", sortable: true, align: "right"},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 219, label: "备注", sortable: true, align: "left"},
	  		{name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "left", hidden: true},
	  		{name: "prjctrltype", index: "prjctrltype", width: 80, label: "发包方式", sortable: true, align: "left", hidden: true},
	  		{name: "offerctrl", index: "offerctrl", width: 80, label: "人工发包", sortable: true, align: "left", hidden: true},
	  		{name: "materialctrl", index: "materialctrl", width: 80, label: "材料发包", sortable: true, align: "left", hidden: true},
	  		{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
	  		{name: "isoutset", index: "isoutset", width: 80, label: "套餐外项目", sortable: true, align: "left",hidden: true },
	  		{name: "isbaseitemset", index: "isbaseitemset", width: 60, label: "套餐包", sortable: true, align: "left", hidden: true},
           	{name: "fixareatypedescr", index: "fixareatypedescr", width: 68, label: "空间类型", sortable: true, align: "left"},	
        ],
        ondblClickRow: function (rowid, status) {
       		if (clickFlag) {
       			return false;
   			}
		    clickFlag = true;
			var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
            var rowData = $("#dataTable").jqGrid("getRowData",rowId);
            var rowNo=parseInt($("#rowNo").val());
           	var fixAreaPk = rowData.PK;
           	var fixAreaDescr = rowData.Descr;
           	var fixareaseq = rowData.DispSeq;
           	var preplanareadescr = rowData.preplanareadescr;
           	var preplanareapk = rowData.PrePlanAreaPK;
           	if(!fixAreaDescr){
             	art.dialog({
					content: "请先选择区域!"
				});
            		return;
           	}
           	var rowData = $("#dataTableSelect").jqGrid("getRowData",rowid);
           	if(rowData.isbaseitemset=="1"){
             		$.ajax({
						url: "${ctx}/admin/itemPlan/doBaseItemSetAdd",
						type:"post",
				        dataType:"json",
				        data: {custCode:"${baseItemChg.custCode}", fixAreaPk:fixAreaPk,baseItemCode:rowData.code},
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
							clickFlag = false;
					    },
					    success: function(obj){
						    if(obj.rs){
						        var datas=JSON.parse(obj.datas);
						        rowData.fixareapk=fixAreaPk;
					           	rowData.fixareadescr=fixAreaDescr;
					           	rowData.baseitemdescr=rowData.descr;       	
					           	rowData.qty=0;
					           	rowData.unitprice=rowData.offerpri;
					           	rowData.lineamount=0;
					           	rowData.baseitemcode=rowData.code;
					           	rowData.remarks=rowData.remark;
					           	rowData.fixareaseq=fixareaseq;
					           	rowData.isoutset=rowData.isoutset;
					           	rowData.isoutsetdescr=rowData.isoutset=="1"?"是":"否";
					           	rowData.preplanareadescr = preplanareadescr;
					           	rowData.preplanareapk = preplanareapk;
					           	console.log(rowData.preplanareapk);
				             	if(datas){
				             		rowData.baseitemsetno=datas[0].baseitemsetno;
				             	}
				             	rowData.ismainitem='1';
				             	rowData.ismainitemdescr='是';
				             	$("#baseItemChgDetailDataTable").addRowData(rowNo, rowData, "last");
				             	rowNo=rowNo+1;
							    $.each(datas,function(k,v){
							    	 v.fixareaseq=fixareaseq;
	                				 $("#baseItemChgDetailDataTable").addRowData(rowNo, v, "last");
	                				 rowNo=rowNo+1;
						  		})
						  		$("#rowNo").val(rowNo);
						  		clickFlag = false;
					  		}else{
					  			art.dialog({
									content: obj.msg
								});
								clickFlag = false;
					  		}
						 }
				 	});	  
				}else{
					rowData.fixareapk=fixAreaPk;
		           	rowData.fixareadescr=fixAreaDescr;
		           	rowData.baseitemdescr=rowData.descr;       	
		           	rowData.qty=0;
		           	rowData.unitprice=rowData.offerpri;
		           	rowData.lineamount=0;
		           	rowData.baseitemcode=rowData.code;
		           	rowData.remarks=rowData.remark;
		           	rowData.fixareaseq=fixareaseq;
		           	rowData.isoutset=rowData.isoutset;
		           	rowData.isoutsetdescr=rowData.isoutset=="1"?"是":"否";
		           	rowData.ismainitem='1';
				    rowData.ismainitemdescr='是';
		           	rowData.preplanareadescr = preplanareadescr;
		           	rowData.preplanareapk= preplanareapk;
		           	$("#baseItemChgDetailDataTable").addRowData(rowNo, rowData, "last");	
		           	rowNo=rowNo+1;
		           	$("#rowNo").val(rowNo);
		           	clickFlag = false;
				} 
		},
		gridComplete:function (){
			dataTableCheckBox("dataTableSelect","descr");
			dataTableCheckBox("dataTableSelect","categorydescr");
			dataTableCheckBox("dataTableSelect","marketprice");
			dataTableCheckBox("dataTableSelect","offerpri");
			dataTableCheckBox("dataTableSelect","material");
			dataTableCheckBox("dataTableSelect","uom");
		}
	};
    Global.JqGrid.initJqGrid("dataTableSelect", gridOption);
    window.clickTree = function (treeNode) {
    	var baseItemType1="0000",baseItemType2="0000",descr=$("#itemDescr").val();
    	if (treeNode.isParent){
    		baseItemType1 = treeNode.menuIdStr;
    		baseItemType2 = "";
    	}else{
    		baseItemType1 = "";
    		baseItemType2 = treeNode.menuIdStr;
    	}
    	/*
    	$.jgrid.gridUnload("dataTableSelect");
    	Global.JqGrid.initJqGrid("dataTableSelect", $.extend(gridOption, {url: "${ctx}/admin/baseItem/goItemSelectJqGrid?baseItemType1="
    			+baseItemType1+"&baseItemType2="+baseItemType2+"&descr="+descr+"&custTypeType=${baseItemChg.custTypeType}"}));
    	*/
    	$("#dataTableSelect").jqGrid("setGridParam",{url:"${ctx}/admin/baseItem/goItemSelectJqGrid?baseItemType1="
        		+baseItemType1+"&baseItemType2="+baseItemType2+"&descr="+descr+"&custTypeType=${baseItemChg.custTypeType}&customerType=${baseItemChg.custType}&canUseComBaseItem=${baseItemChg.canUseComBaseItem}"}).trigger("reloadGrid");
    	if("${baseItemChg.custTypeType}"=="2")  $("#dataTableSelect").setGridParam().showCol("categorydescr").trigger("reloadGrid");
	}
    window.search = function () {
    	var descr=$("#itemDescr").val();
    	$.jgrid.gridUnload("dataTableSelect");
    	console.log("${baseItemChg.canUseComBaseItem}");
    	Global.JqGrid.initJqGrid("dataTableSelect", $.extend(gridOption, {url: "${ctx}/admin/baseItem/goItemSelectJqGrid?descr="+descr+"&custTypeType=${baseItemChg.custTypeType}&customerType=${baseItemChg.custType}&canUseComBaseItem=${baseItemChg.canUseComBaseItem}"}));
    	if("${baseItemChg.custTypeType}"=="2")  $("#dataTableSelect").setGridParam().showCol("categorydescr").trigger("reloadGrid");
    }
});
//装修区域
$(document).ready(function () {
	$("input", "#page_form").unbind("keydown");
  	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/fixArea/goJqGrid?custCode=${baseItemChg.custCode}&itemType1=JZ",
    	height: 245,
    	rownumbers: false,
    	rowNum: 10000,
    	colModel: [
      		{name: "Descr", index: "Descr", width: 155, label: "<h7 style = \"float:left;margin-top:3px\">装修区域</h7><input type=\"text\" placeholder=\"按回车时搜索\" style=\"width:80px;height:22px;float:right \" class=\"form-control\" onkeydown=\"enterClick(window.event,'fixAreaSearch',this)\">", sortable: false, align: "left"},
      		{name: "PK", index: "PK", width: 100, label: "区域编号", sortable: false, align: "center", hidden: true},
      		{name: "DispSeq", index: "DispSeq", width: 100, label: "序号", sortable: false, align: "center", hidden: true},
      		{name: "preplanareadescr", index: "preplanareadescr", width: 100, label: "空间", sortable: false, align: "center",hidden:true},
      		{name: "PrePlanAreaPK", index: "PrePlanAreaPK", width: 100, label: "空间pk", sortable: false, align: "center",hidden:true}
    	],
    	gridComplete: function () {
      		if ("${baseItemChg.fixAreaPk}") {
        		var rowData = $("#dataTable").jqGrid("getRowData");
        		$.each(rowData, function (k, v) {
          			if (v.PK == "${baseItemChg.fixAreaPk}") {
            			$("#dataTable").setSelection(k + 1, true);
            			scrollToPosi(k + 1);
            			return false;
          			}
        		});
      		}
      		if (isAddFixArea) {
        		isAddFixArea = false;
        		var ids = $("#dataTable").jqGrid("getDataIDs");
        		$("#dataTable").setSelection(ids.length, true);
        		scrollToPosi(ids.length);
      		}
    	},
    	onSelectRow: function (rowid, status) {
      		var rowData = $("#dataTable").jqGrid("getRowData", rowid);
      		fixAreaPk = parseInt(rowData.PK);
      		$("#fixAreaPk").val(fixAreaPk);
      		$("#fixAreaDescr").val(rowData.Descr);
    	}
  	});
  	
  	Global.JqGrid.initJqGrid("baseItemChgDetailDataTable", {
    	height: 233,
    	cellEdit: true,
    	cellsubmit: "clientArray",
    	rowNum: 10000,
		colModel : [
			{name: "deleteBtn", index: "deleteBtn", width: 84, label: "删除", sortable: false, align: "left", formatter: deleteBtn},
	  		{name: "fixareaseq", index: "fixareaseq", width: 80, label: "fixareaseq", sortable: true, align: "left", hidden: true},
	  		{name: "fixareapk", index: "fixareapk", width: 80, label: "区域编号", sortable: true, align: "left", hidden: true},
	  		{name: "fixareadescr", index: "fixareadescr", width: 200, label: "区域名称", sortable: true, align: "left"},
	  		{name: "preplanareapk", index: "preplanareapk", width: 120, label: "空间pk", sortable: true, align: "left",hidden:true},
	  		{name: "baseitemcode", index: "baseitemcode", width: 100, label: "基装项目编号", sortable: true, align: "left", hidden: true},
	  		{name: "baseitemdescr", index: "baseitemdescr", width: 250, label: "基装项目名称", sortable: true, align: "left"},
	  		{name: "algorithmdescr", index: "algorithmdescr", width: 100, label: "算法", sortable: true, align: "left",editable:true,
		  		edittype:'select',
		  		editoptions:{ 
		  			dataUrl: '${ctx}/admin/baseItem/getBaseAlgorithm',
					postData: function(){
						var ret = selectDataTableRow("baseItemChgDetailDataTable");
						console.log(ret);
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
						return "<select style=\"font-family:宋体;\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e)
	  					}
		  			}, 
		  		]}
	 	},
	 	 {name: "algorithm", index: "algorithm", width: 85, label: "算法编号", sortable:false, align: "left",hidden: true},
	  		{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right",editable:true,edittype:"text",editrules:{number:true,required:true}},
	  		{name: "uom", index: "uom", width: 50, label: "单位", sortable: true, align: "left"},
	  		{name: "unitprice", index: "unitprice", width: 80, label: "人工单价", sortable: true, align: "right"},
	  		{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "right"},
	  		{name: "lineamount", index: "lineamount", width: 100, label: "总价", sortable: true, align: "right", sum: true},
	  		{name: "baseitemsetno", index: "baseitemsetno", width: 60, label: "套餐包", sortable: true, align: "left"},
            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left",hidden: true},
            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},	
	  		{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left",editable:true,edittype:"textarea"},
	  		{name: "category", index: "category", width: 80, label: "项目类型", sortable: true, align: "left", hidden: true},
	  		{name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "left", hidden: true},
	  		{name: "prjctrltype", index: "prjctrltype", width: 80, label: "发包方式", sortable: true, align: "left", hidden: true},
	  		{name: "offerctrl", index: "offerctrl", width: 80, label: "人工发包", sortable: true, align: "left", hidden: true},
	  		{name: "materialctrl", index: "materialctrl", width: 80, label: "材料发包", sortable: true, align: "left", hidden: true},
	  		{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
	  		{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外项目", sortable: true, align: "left", hidden: true},
	  		{name: "isoutset", index: "isoutset", width: 80, label: "套餐外项目", sortable: true, align: "left",hidden: true },
	  		{name: "preplanareadescr", index: "preplanareadescr", width: 120, label: "空间", sortable: true, align: "left"},
		],
    	gridComplete: function () {
      		
    	},
    	beforeSaveCell: function (id, name, val, iRow, iCol) {
    		var rowData = $("#baseItemChgDetailDataTable").jqGrid("getRowData", id);
      		if (name=="qty" && "${baseItemChg.custTypeType}"=="2" && rowData.category!="4" && val<0){
   				art.dialog({
                 	content: "套餐类客户，只有基础项目是套餐类减项的才允许预算为负数！"
               	});
   				return "0";
      		}
    	},
    	afterSaveCell:function(id,name,val,iRow,iCol){
    		var rowData = $("#baseItemChgDetailDataTable").jqGrid("getRowData", id);
              switch (name){
              	case 'qty':
               		$("#baseItemChgDetailDataTable").setCell(id, "lineamount", 
               						myRound(myRound(val * parseFloat(rowData.unitprice)) + myRound(val * parseFloat(rowData.material), 0)));
               	break;		
              }
              var lineamount=$("#baseItemChgDetailDataTable").getCol('lineamount',false,'sum');   
              $("#baseItemChgDetailDataTable").footerData('set', { "lineamount": lineamount });       
          },
    	beforeSelectRow: function (id) {
      		setTimeout(function () {
        		relocate(id, "baseItemChgDetailDataTable");
      		}, 100);
    	}
  	});
});

function algorithmClick(e){
	var rowid = $("#baseItemChgDetailDataTable").getGridParam("selrow");
	console.log($(e.target).attr("rowid"));
    var rowData = $("#baseItemChgDetailDataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
    $("#baseItemChgDetailDataTable").setRowData($(e.target).attr("rowid"), {
    	algorithm:$(e.target).val(),
    });
    changeAlgorithm($(e.target).attr("rowid"),$(e.target).val());
}

//根据算法计算
function changeAlgorithm(id,val){
	var ret = selectDataTableRow("baseItemChgDetailDataTable");
	var datas=$("#page_form").jsonForm();
		datas.baseItemCode=ret.baseitemcode;
		datas.baseAlgorithm = val;
		datas.prePlanAreaPK=ret.preplanareapk;
		datas.custType = "";
		datas.custCode = "${baseItemChg.custCode}";
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
				$("#baseItemChgDetailDataTable").setRowData(id, {
					qty:obj
				});
				$("#baseItemChgDetailDataTable").setCell(id, "lineamount", 
      						myRound(myRound(myRound(obj ,4)* parseFloat(ret.unitprice)) + myRound(myRound(obj ,4) * parseFloat(ret.material), 0)));
				var lineamount=$("#baseItemChgDetailDataTable").getCol('lineamount',false,'sum');   
	              $("#baseItemChgDetailDataTable").footerData('set', { "lineamount": lineamount });  
			}
	});	
}
</script>
</body>
</html>
