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
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
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
						<span>保存</span>
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
								<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" readonly="readonly"/>                                             
							</li>
							<li class="form-validate">
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width:160px" style="width: 166px;" >
								</select>
							</li>	
							<li>
								<label><span class="required">*</span>单据状态</label>
								<house:xtdm id="status" dictCode="PURAPPSTAT"  value="0" disabled='true'></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label>仓库编号</label>
								<input type="text" id="whCode" name="whCode" style="width:160px;"/> 
							</li>
						</div>
						<div class="validate-group row" >
							<li>
								<label>申请人</label>
								<input type="text" id="appCZY" name="appCZY" style="width:160px;"/> 
							</li>
							<li>
								<label><span class="required">*</span>申请日期</label>
								<input type="text" id="appDate" name="appDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" disabled="true"
										value="<fmt:formatDate value='${purchaseApp.appDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remark" name="remark" rows="2"></textarea>
  							</li>
						</div>
  					</ul>	
  				</form>
  			</div>
  		</div>	
		<div class="btn-panel" >
    		<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="add" >
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<button type="button" class="btn btn-system" id="delDetail">
					<span>删除</span>
				</button>
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
				<button type="button" class="btn btn-system" id="orderAnalyse">
					<span>从订货分析导入</span>
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
	$("#whCode").openComponent_wareHouse();	
	$("#appCZY").openComponent_employee({showValue:"${czybh}",showLabel:"${employee.nameChi}",readonly:true});	

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

var editingCellReferenceClosure = function() {}

$(function(){
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden: true},
			{name:"itemcode", index:"itemcode", width:80, label:"材料编号", sortable:true ,align:"left",},
			{name: "itemtype2descr", index: "itemtype2descr", width: 72, label: "材料类型2", sortable: true, align: "left"},
			{name:"itemdescr", index:"itemdescr", width:180, label:"材料名称", sortable:true ,align:"left",},
			{name:"qty", index:"qty", width:80, label:"采购数量", sortable:true ,align:"right",editable:true,	editrules: {number:true,required:true},},
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
		beforeSelectRow:function(id){
			setTimeout(function(){
  				relocate(id);
			},10)
		},
		beforeEditCell: function(rowid, cellname, value, iRow, iCol) {
			editingCellReferenceClosure = function() {
				$("#dataTable").saveCell(iRow, iCol)
			}
		}
	};
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);

	$("#dataTable").sortableRows({
		start: function() { editingCellReferenceClosure() }
	})

	$("#add").on("click", function(){
		var itemType1 = $("#itemType1").val();
		if(itemType1 == ""){
			art.dialog({
				content:"请选择材料类型1",
			});
			return;
		}
		var itemCodes = "";
		var detailJson = Global.JqGrid.allToJson("dataTable","itemcode");
		Global.Dialog.showDialog("add",{
			title:"采购明细申请——新增",
			url:"${ctx}/admin/purchaseApp/goAdd",
			postData: {itemType1: itemType1, itemCodes: detailJson.fieldJson},
			height: 310,
			width: 790,
			returnFun:function(data){
				if(data){
					var json = {
						itemcode: data.itemCode,
						itemtype2descr: data.ItemType2Descr,
						itemdescr: data.itemDescr,
						qty: data.qty,
						remark: data.remark,
						purchonway: data.PurQty,
						send7: data.Send7,
						send30: data.Send30,
						send60: data.Send60,
						uomdescr: data.UomDescr,
						useqty: data.UseQty,
						ddqrwfhqty: data.DdqrwfhQty,
						lastupdate: new Date(),
						lastupdatedby: "${czybh }",
						expired: "F",
						actionlog: "ADD",
					}
					Global.JqGrid.addRowData("dataTable",json);
					$("#itemType1").attr("disabled",true);
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
		var detailJson = Global.JqGrid.allToJson("dataTable","itemcode");
		Global.Dialog.showDialog("AddUpdate",{
			title:"采购明细申请——编辑",
			url:"${ctx}/admin/purchaseApp/goAddUpdate?map="+encodeURIComponent(JSON.stringify(ret)),
			postData: {itemType1: itemType1, itemCodes: detailJson.fieldJson},
			height: 310,
			width: 790,
			returnFun:function(data){
				if(data){
					var json = {
						itemcode: data.itemCode,
						itemtype2descr: data.ItemType2Descr,
						itemdescr: data.itemDescr,
						qty: data.qty,
						remark: data.remark,
						purchonway: data.PurQty,
						send7: data.Send7,
						send30: data.Send30,
						send60: data.Send60,
						uomdescr: data.UomDescr,
						useqty: data.UseQty,
						ddqrwfhqty: data.DdqrwfhQty,
						lastupdate: new Date(),
						lastupdatedby: "${czybh }",
						expired: "F",
						actionlog: "ADD",
					}
			   		$("#dataTable").setRowData(rowId,json);
			   		$("#itemType1").attr("disabled",true);
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
	
	$("#orderAnalyse").on("click", function(){
		var itemType1 = $("#itemType1").val();
		var whCode = $("#whCode").val();
		var ret = selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		var codes = Global.JqGrid.allToJson("dataTable","itemcode");
		
		if(itemType1==""){
			art.dialog({
				content:"请选择材料类型1",
			})
			return;
		}
		
		Global.Dialog.showDialog("orderAnalyse",{
			title:"采购明细申请——订货分析",
			url:"${ctx}/admin/purchaseApp/goOrderAnalyse",
			postData: {itemType1: itemType1, whCode: whCode,itemCodes: codes.fieldJson},
			height:730,
			width:1260,
			returnFun:function (data){
				$.each(data,function(k,v){
					var json = {
						itemcode: v.itcode,
						itemtype2descr: v.itemtype2descr,
						itemdescr: v.itdescr,
						qty: v.sugorderqty,
						uomdescr: v.uomdescr,
						remark: v.remark,
						purchonway: v.purchonway,
						useqty: v.useqty,
						send7: v.send7,
						send30: v.send30,
						send60: v.send60,
						ddqrwfhqty: v.ddqrwfhqty,
						lastupdate: new Date(),
						lastupdatedby: "${czybh }",
						expired: "F",
						actionlog: "EDIT",
					};
					Global.JqGrid.addRowData("dataTable",json);
				});
				$("#itemType1").attr("disabled",true);
			}
		});
	});
	
	$("#delDetail").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		
		art.dialog({
			 content:"是删除该记录？",
			 lock: true,
			 width: 100,
			 height: 80,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				var param= Global.JqGrid.allToJson("dataTable",null,null,true);
				if(param["detailJson"] == "[]"){
					$("#itemType1").removeAttr("disabled",true);
				}
			},
			cancel: function () {
				return true;
			}
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
		Global.Form.submit("dataForm","${ctx}/admin/purchaseApp/doSave",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
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

















