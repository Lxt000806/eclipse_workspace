<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购入库</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
</head>
<script type="text/javascript">
$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchaseApp.itemType1}',
	};
	Global.LinkSelect.setSelect(dataSet);
});
</script>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>审核通过</span>
					</button>
					<button type="button" class="btn btn-system" id="confirmCancel">
						<span>审核取消</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
  		<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>采购单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${purchaseApp.no }" readonly="readonly"/>                                             
							</li>
							<li class="form-validate">
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width:160px" style="width: 166px;" value="${purchaseApp.itemType1 }" disabled="true">
								</select>
							</li>	
							<li>
								<label>单据状态</label>
								<house:xtdm id="status" dictCode="PURAPPSTAT"  value="1" disabled="true"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label>仓库编号</label>
								<input type="text" id="whCode" name="whCode" style="width:160px;"/> 
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label>申请人</label>
								<input type="text" id="appCZY" name="appCZY" style="width:160px;"/> 
							</li>
							<li>
								<label>申请日期</label>
								<input type="text" id="appDate" name="appDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" disabled="true"
										value="<fmt:formatDate value='${purchaseApp.appDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remark" name="remark" rows="2" readonly="true">${purchaseApp.remark }</textarea>
  							</li>
						</div>
  					</ul>	
  				</form>
  			</div>
  		</div>	
		<div class="btn-panel" >
    		<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
				<button type="button" class="btn btn-system" onclick="doExcelNow('采购申请明细单', null, 'dataForm')" title="导出当前excel数据" >
					<span>导出excel</span>
				</button>
				<button type="button" class="btn btn-system" id="orderAnalyse" onclick="showSendQty(this)">
					<span>显示发货量</span>
				</button>
			</div>
		</div>	
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">采购申请明细</a></li>
		    </ul> 
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>	
		</div>	
  	</div> 
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
$(function() {
	$("#whCode").openComponent_wareHouse({showValue:"${purchaseApp.whCode}", showLabel:"${wareHouse.desc1}", readonly: true});	
	$("#appCZY").openComponent_employee({showValue:"${purchaseApp.appCZY}", showLabel:"${employee.nameChi}",readonly:true});	

	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});

$(function(){
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		url:"${ctx}/admin/purchaseApp/goDetailJqGrid",
		postData:{no: "${purchaseApp.no}"},
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden: true},
			{name:"itemcode", index:"itemcode", width:80, label:"材料编号", sortable:true ,align:"left",},
			{name: "itemtype2descr", index: "itemtype2descr", width: 72, label: "材料类型2", sortable: true, align: "left"},
			{name:"itemdescr", index:"itemdescr", width:180, label:"材料名称", sortable:true ,align:"left",},
			{name:"qty", index:"qty", width:80, label:"采购数量", sortable:true ,align:"right",},
			{name: "uomdescr", index: "uomdescr", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"},
			{name: "purchonway", index: "purchonway", width: 100, label: "未到货采购量", sortable: true, align: "right",hidden: true},
			{name: "useqty", index: "useqty", width: 100, label: "可用数量", sortable: true, align: "right",hidden: true},
			{name: "send7", index: "send7", width: 100, label: "近7天发货", sortable: true, align: "right",hidden: true},
			{name: "send30", index: "send30", width: 100, label: "近30天发货", sortable: true, align: "right",hidden: true},
			{name: "send60", index: "send60", width: 100, label: "近60天发货", sortable: true, align: "right",hidden: true},
			{name: "ddqrwfhqty", index: "ddqrwfhqty", width: 110, label: "到点确认需求量", sortable: true, align: "right",hidden: true},
			{name:"lastupdate", index:"lastupdate", width:80, label:"最后修改时间", sortable:true ,align:"left",formatter: formatDate},
			{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最后修改人", sortable:true ,align:"left",},
			{name:"expired", index:"expired", width:80, label:"是否过期", sortable:true ,align:"left",},
			{name:"actionlog", index:"actionlog", width:80, label:"操作类型", sortable:true ,align:"left",},
		
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	$("#add").on("click", function(){
		var itemType1 = $("#itemType1").val();
		if(itemType1 == ""){
			art.dialog({
				content:"请选择材料类型1",
			});
			return;
		}
		
		Global.Dialog.showDialog("add",{
			title:"采购明细申请——新增",
			url:"${ctx}/admin/purchaseApp/goAdd",
			postData: {itemType1: itemType1},
			height: 310,
			width: 790,
			returnFun:function(data){
				if(data){
					var json = {
						itemcode: data.itemCode,
						itemdescr: data.itemDescr,
						qty: data.qty,
						remark: data.remark,
						lastupdate: new Date(),
						lastupdatedby: "${czybh }",
						expired: "F",
						actionlog: "ADD",
					}
					Global.JqGrid.addRowData("dataTable",json);
				}
			}
		});
	});
	
	$("#update").on("click", function(){
		var itemType1 = $("#itemType1").val();
		var ret = selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(itemType1 == ""){
			art.dialog({
				content:"请选择材料类型1",
			});
			return;
		}
		if(!ret){
			art.dialog({
				content:"请选择一条明细进行编辑",
			});
			return
		}
		Global.Dialog.showDialog("AddUpdate",{
			title:"采购明细申请——编辑",
			url:"${ctx}/admin/purchaseApp/goAddUpdate?map="+encodeURIComponent(JSON.stringify(ret)),
			postData: {itemType1: itemType1},
			height: 310,
			width: 790,
			returnFun:function(data){
				if(data){
					var json = {
						itemcode: data.itemCode,
						itemdescr: data.itemDescr,
						qty: data.qty,
						remark: data.remark,
						lastupdate: new Date(),
						lastupdatedby: "${czybh }",
						expired: "F",
						actionlog: "EDIT",
					}
			   		$("#dataTable").setRowData(rowId,json);
				}
			}
		});
	});
	
	$("#view").on("click", function(){
		var itemType1 = $("#itemType1").val();
		var ret = selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({
				content:"请选择一条明细进行查看",
			});
			return
		}
		Global.Dialog.showDialog("addView",{
			title:"采购明细申请——查看",
			url:"${ctx}/admin/purchaseApp/goAddView?map="+encodeURIComponent(JSON.stringify(ret)),
			postData: {itemType1: itemType1},
			height: 310,
			width: 790,
			returnFun:false
		});
	});
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    
		var param= Global.JqGrid.allToJson("dataTable",null,null,true);
		if(param["detailJson"] == "[]"){
			art.dialog({
				content:"明细数据,无法保存",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/purchaseApp/doConfirm",
			type: "post",
			data: {no: "${purchaseApp.no}"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin(true);
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
	});	
	
	$("#confirmCancel").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
		if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    
		var param= Global.JqGrid.allToJson("dataTable",null,null,true);
		if(param["detailJson"] == "[]"){
			art.dialog({
				content:"明细数据,无法保存",
			});
			return;
		}
		$.ajax({
			url:"${ctx}/admin/purchaseApp/doConfirmCancel",
			type: "post",
			data: {no: "${purchaseApp.no}"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin(true);
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
	});	
});

function showSendQty(){
	var param= Global.JqGrid.allToJson("dataTable",null,null,true);
	$("#dataTable").setGridParam().showCol("purchonway");
	$("#dataTable").setGridParam().showCol("useqty");
	$("#dataTable").setGridParam().showCol("send7");
	$("#dataTable").setGridParam().showCol("send30");
	$("#dataTable").setGridParam().showCol("send60");
	$("#dataTable").setGridParam().showCol("ddqrwfhqty");
	$.ajax({
		url:"${ctx}/admin/purchaseApp/getItemSendQty",
		type:'post',
		data:{detailJson: param.detailJson},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '出错~'});
		},
		success:function(obj){
			
			if(obj && obj.length >0 ){
				$("#dataTable").jqGrid("clearGridData");
				$.each(obj,function(k,v){
					Global.JqGrid.addRowData("dataTable",v);
				});
			}
		}
	});
}

</script>
  </body>
</html>
