<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<title>退货搬运费规则管理--查看</title>  
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script>
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
});
$(function(){
	var gridOption = {
		height:280,
		rowNum:10000000,
		url:"${ctx}/admin/ReturnCarryRule/goItemJqGrid",
		postData: {no:$.trim($("#no").val())},
		styleUI: "Bootstrap", 
		colModel : [
			{name: "no", index: "no", width: 84, label: "编号", sortable: true, align: "left"},
			{name: "itemtype2desc", index: "itemtype2desc", width: 79, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3desc", index: "itemtype3desc", width: 79, label: "材料类型3", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 79, label: "材料类型1", sortable: true, align: "left",hidden:true},
			{name: "itemtype2", index: "itemtype2", width: 79, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemtype3", index: "itemtype3", width: 172, label: "材料类型3", sortable: true, align: "left",hidden:true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 96, label: "最后修改人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 76, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 124, label: "最后修改时间", sortable: true, align: "left", formatter: formatDate}
			],
			loadonce: true,
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	$("#view").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		var param =$("#dataTable").jqGrid("getRowData",rowId);
		Global.Dialog.showDialog("ItemUpdate",{
			title:"匹配材料——查看",
		  	url:"${ctx}/admin/ReturnCarryRule/goItemView",
		  	postData:param,
		  	height: 480,
			width:750,
		});
	}); 
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
								<ul class="ul-form">	
					<div class="validate-group">
					<li>
						<label><span class="required">*</span>编号</label>
						<input type="text" id="no" name="no"  value="${returnCarryRule.no }" readonly="true"  placeholder="保存时生成" readonly="readonly"/>							
					</li>
					<li class="form-validate">
						<label ><span class="required">*</span>价格</label>
						<input type="text" id="price" name="price" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${returnCarryRule.price }" readonly="readonly"/>							
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>起始重量</label>
						<input type="text" id="beginValue" name="beginValue" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${returnCarryRule.beginValue }" readonly="readonly"/>
					</li>
					</div>
					<li class="form-validate">
						<label><span class="required">*</span>截止重量</label>
						<input type="text" id="endValue" name="endValue" onkeyup="this.value=this.value.replace(/\D/g,'')" 
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${returnCarryRule.endValue }" readonly="readonly"/>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>价格类型</label>
						<house:xtdm id="priceType" dictCode="PriceType" value="${returnCarryRule.priceType}" disabled="true"></house:xtdm>													
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>起始值</label>
						<input type="text" id="cardAmount" name="cardAmount"  value="${returnCarryRule.cardAmount }" readonly="readonly"/>											
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>递增值</label>
						<input type="text" id="incValue" name="incValue"  value="${returnCarryRule.incValue }" readonly="readonly"/>													
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>计算方式</label>
						<house:xtdm id="calType" dictCode="ReCarryCalType" value="${returnCarryRule.calType}" disabled="true"></house:xtdm>													
					</li>
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks" readonly="readonly">${returnCarryRule.remarks}</textarea>
					</li>
				</ul>			
			</form>
		</div>
	</div>
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_prjProvide" data-toggle="tab">匹配材料</a></li>  
	    </ul>  
	    <div class="tab-content">  
	    	<div id="tab_prjProvide" class="tab-pane fade in active">
	    		<div class="body-box-list"  style="margin-top: 0px;">
					<div class="panel panel-system" >
					    <div class="panel-body">
					      	<div class="btn-group-xs" >
								<button type="button" class="btn btn-system" id="view">
									<span>查看</span>
								</button>
							</div>
						</div>
					</div>
					<table id= "dataTable" style="overflow: auto;"></table>					
				</div>
			</div>
		</div>
	</div>
</div>		
</body>
</html>



