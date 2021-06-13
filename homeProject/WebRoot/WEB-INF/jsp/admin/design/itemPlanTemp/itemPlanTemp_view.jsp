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
						<input type="hidden" name="m_umState" id="m_umState" value="V"/>
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
								<li>
									<label>编号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${itemPlanTemp.no }" readonly="readonly"/>                                             
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>模板名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${itemPlanTemp.descr }"/>                                             
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label><span class="required">*</span>材料类型1</label>
									<select id="itemType1" name="itemType1" disabled="true"></select>
								</li>
							</div>
							<div class="validate-group row" >
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2"></textarea>
		 						</li>
	 						</div>
							<div class="validate-group row" >
		 						<li>
									<label>过期</label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${itemPlanTemp.expired }",
										onclick="checkExpired(this)" ${itemPlanTemp.expired=="T"?"checked":"" }/>
								</li>
							</div>
						</ul>
	  				</form>
  				</div>
  			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
    				<button type="button" class="btn btn-system " id="view" >
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system " onclick="doExcelNow('材料暴击模板')" title="导出当前excel数据" >
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
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${itemPlanTemp.itemType1 }',
								});
	
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: "Bootstrap", 
		url:"${ctx}/admin/itemPlanTemp/goDetailJqGrid",
		postData:{no:"${itemPlanTemp.no }"},
		colModel : [
			{name: "pk", index: "pk", width: 70, label: "序号", sortable: true, align: "left", hidden: true},
			{name: "dispseq", index: "dispseq", width: 70, label: "序号", sortable: true, align: "left",hidden: true },
			{name: "no", index: "no", width: 80, label: "模板主键", sortable: true, align: "left", hidden: true},
			{name: "itemcode", index: "itemcode", width: 88, label: "材料编号", sortable: true, align: "left"},
			{name: "itemcodedescr", index: "itemcodedescr", width: 210, label: "材料名称", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 106, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 82, label: "是否过期", sortable: true, align: "left"}
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	$("#view").on("click",function(){
		var itemType1 = $.trim($("#itemType1").val());
		var ret = selectDataTableRow();
		if(itemType1 ==""){
			art.dialog({content: "请选择材料类型1",width: 200});
			return false;
		}
		Global.Dialog.showDialog("view",{
			title:"材料报价模板明细——查看",
			url:"${ctx}/admin/itemPlanTemp/goAddView",
			postData:{itemType1:itemType1,itemDescr:ret.itemcodedescr,
				itemCode:ret.itemcode,qty:ret.qty},
			height: 380,
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
});
</script>
</body>
</html>

















