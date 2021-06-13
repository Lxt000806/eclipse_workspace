<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>礼品出库记账新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  		<div class="content-form">
  			<div class="panel panel-system" >
			    <div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<!-- edit-form -->
  			<div class="panel panel-info" >  
				<div class="panel-body">
			  		<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>编号</label>
								<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" value="${giftCheckOut.no }" readonly="readonly"/>                                             
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>模板名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"/>                                             
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>材料类型1</label>
								<select id="itemType1" name="itemType1"></select>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2"></textarea>
	 						</li>
						</ul>
	  				</form>
  				</div>
  			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " id="fastAdd" >
						<span>快速新增</span>
					</button>
					<button type="button" class="btn btn-system " id="update" >
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system " id="delDetail">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system " id="view" >
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system " onclick="doExcelNow('材料报价模板')" title="导出当前excel数据" >
						<span>导出excel</span>
					</button>
				</div>
			</div>	
			<ul class="nav nav-tabs" >
		      	<li class="active"><a data-toggle="tab">主项目</a></li>
		   	 </ul>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>	
  		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$("#tabs").tabs();
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				descr:{  
					validators: {  
						notEmpty: {  
							message: '模板名称不能为空'  
						}
					}  
				},
				itemType1:{  
					validators: {  
						notEmpty: {  
							message: '材料类型1不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		
	});
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");

	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: "Bootstrap", 
		colModel : [
			{name: "pk", index: "pk", width: 70, label: "序号", sortable: true, align: "left", hidden: true},
			{name: "dispseq", index: "dispseq", width: 70, label: "序号", sortable: true, align: "left",hidden: true },
			{name: "no", index: "no", width: 80, label: "模板主键", sortable: true, align: "left", hidden: true},
			{name: "itemcode", index: "itemcode", width: 88, label: "材料编号", sortable: true, align: "left"},
			{name: "itemcodedescr", index: "itemcodedescr", width: 210, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 106, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 82, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 82, label: "actionlog", sortable: true, align: "left",hidden:true}
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	//新增
	$("#add").on("click",function(){
		var itemType1 = $.trim($("#itemType1").val());
		if(itemType1 ==""){
			art.dialog({content: "请选择材料类型1",width: 200});
			return false;
		}
		var Ids =$("#dataTable").getDataIDs();
		Global.Dialog.showDialog("add",{
			title:"材料报价模板明细——新增",
			url:"${ctx}/admin/itemPlanTemp/goAdd",
			postData:{itemType1:itemType1},
			height: 250,
			width:750,
			returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
					  	var json = {
							itemcode:v.itemCode,
							itemcodedescr:v.descr,
							qty:v.qty,
							lastupdate:new Date(),
						  	lastupdatedby:"${lastUpdatedBy }",
						  	expired:"F",
						  	actionlog:"Add",
						  	dispseq:Ids.length+1,
						  };
						Global.JqGrid.addRowData("dataTable",json);
					});
					$("#itemType1").attr("disabled","true");
				}
			} 
		});
	});
	
	$("#fastAdd").on("click",function(){
		var itemType1 = $.trim($("#itemType1").val());
		if(itemType1 ==""){
			art.dialog({content: "请选择材料类型1",width: 200});
			return false;
		}
		var Ids =$("#dataTable").getDataIDs();
		Global.Dialog.showDialog("fastAdd",{
			title:"材料报价模板明细——新增",
			url:"${ctx}/admin/itemPlanTemp/goFastAdd",
			postData:{itemType1:itemType1},
			height: 250,
			width:750,
			returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
					  	var json = {
							itemcode:v.itemCode,
							itemcodedescr:v.descr,
							qty:v.qty,
							lastupdate:new Date(),
						  	lastupdatedby:"${lastUpdatedBy }",
						  	expired:"F",
						  	actionlog:"Add",
						  	dispseq:Ids.length+v.dispSeq,
						  };
						Global.JqGrid.addRowData("dataTable",json);
					});
					$("#itemType1").attr("disabled","true");
				}
			} 
		});
	});
	
	$("#update").on("click",function(){
		var itemType1 = $.trim($("#itemType1").val());
		var ret = selectDataTableRow();
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		console.log(ret);
		if(itemType1 ==""){
			art.dialog({content: "请选择材料类型1",width: 200});
			return false;
		}
		Global.Dialog.showDialog("update",{
			title:"材料报价模板明细——新增",
			url:"${ctx}/admin/itemPlanTemp/goAddUpdate",
			postData:{itemType1:itemType1,itemDescr:ret.itemcodedescr,
				itemCode:ret.itemcode,qty:ret.qty},
			height: 250,
			width:750,
				returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
						  	var json = {
								itemcode:v.itemCode,
								itemcodedescr:v.descr,
								qty:v.qty,
								lastupdate:new Date(),
							  	lastupdatedby:"${lastUpdatedBy }",
							  	expired:"F",
							  	actionlog:"Add",
							  };
			   				$("#dataTable").setRowData(rowId,json);
					  });
				  }
			  } 
		 });
	});
	
	$("#view").on("click",function(){
		var itemType1 = $.trim($("#itemType1").val());
		var ret = selectDataTableRow();
		if(itemType1 ==""){
			art.dialog({content: "请选择材料类型1",width: 200});
			return false;
		}
		Global.Dialog.showDialog("update",{
			title:"材料报价模板明细——查看",
			url:"${ctx}/admin/itemPlanTemp/goAddView",
			postData:{itemType1:itemType1,itemDescr:ret.itemcodedescr,
				itemCode:ret.itemcode,qty:ret.qty},
			height: 250,
			width:750,
				returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
						  	var json = {
								itemcode:v.itemCode,
								itemcodedescr:v.descr,
								qty:v.qty,
								lastupdate:new Date(),
							  	lastupdatedby:"${lastUpdatedBy }",
							  	expired:"F",
							  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
				  }
			  } 
		 });
	});
	
	//删除
	$("#delDetail").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable",id);
		var Ids =$("#dataTable").getDataIDs();
		for(var i=0;i<Ids.length;i++){
			$("#dataTable").setCell(Ids[i], 'dispseq',i+1);
		}
		if(Ids.length==0){
			$("#itemType1").removeAttr("disabled","true");
		}
	});
	
	//保存
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var param= Global.JqGrid.allToJson("dataTable");
		var Ids =$("#dataTable").getDataIDs();
		if(Ids==null||Ids==""){
			art.dialog({
				content:"明细表为空，不能保存",
			});
			return false;
		}
		Global.Form.submit("dataForm","${ctx}/admin/itemPlanTemp/doSave",param,function(ret){
			alert(ret);
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

</script>
  </body>
</html>

















