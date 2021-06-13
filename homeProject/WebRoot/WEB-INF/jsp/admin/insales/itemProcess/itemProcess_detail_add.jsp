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
	<title>材料加工材料新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_itemTransform.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 

$(function() {
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true },
			{name:"no", index:"no", width:80, label:"编号", sortable:true ,align:"left",hidden:true},
			{name:"itemcode", index:"itemcode", width:80, label:"材料编号", sortable:true ,align:"left"},
			{name:"itemdescr", index:"itemdescr", width:180, label:"材料名称", sortable:true ,align:"left"},
			{name:"qty", index:"qty", width:80, label:"源材料数量", sortable:true ,align:"right"},
			{name:"uomdescr", index:"uomdescr", width:80, label:"单位", sortable:true ,align:"right"},
			{name:"itemcode", index:"itemcode", width:80, label:"材料编号", sortable:true ,align:"left",hidden:true},
			{name:"transformper", index:"transformper", width:80, label:"转换系数", sortable:true ,align:"left",hidden:true},
		],
	};
	Global.JqGrid.initJqGrid("itemProcessSourceDataTable",gridOption);
	
	$("#itemTransformNo").openComponent_itemTransform({
		showValue:"${itemProcessDetail.itemTransformNo}",
		showLabel:"${itemProcessDetail.itemTransformRemarks}",
		condition:{itemType1:'${itemProcessDetail.itemType1}'},
		callBack:getItemTransformDetail
	});

	if("${itemProcessDetail.m_umState}"!="A"){
		getSourceItem();
	} 
	
	if ("${itemProcessDetail.m_umState}"!="V") {
		$("#saveBtn").show();
	}
	
	$("#dataForm").bootstrapValidator({
		message :"This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating :"glyphicon glyphicon-refresh"
		},
		fields: {  
			qty:{  
				validators: {  
					notEmpty: {  
						message:"数量不能为空"  
					}
				},
				numeric: {
		        	message: '数量只能是数字' 
		        }
			},
			openComponent_itemTransform_itemTransformNo:{  
				validators: {  
					 remote: {
				           message: '',
				           url: '${ctx}/admin/itemTransform/getItemTransform',
				           data: getValidateVal,  
				           delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
				    },
					notEmpty: {  
						message:"转换编号不能为空"  
					}
				}  
			},
		},
		submitButtons :"input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
});

function getItemTransformDetail(data){
	console.log(data);
	if (data) {
		if(!data.itemcode) return;
		$("#itemCode").val(data.itemcode);
		$("#itemDescr").val(data.itemdescr);
		$("#cost").val(data.cost);
		$("#itemTransformRemarks").val(data.remarks);
		console.log(data.uomdescr);
		$("#uomDescr").val(data.uomdescr);
	
		validateRefresh('openComponent_itemTransform_itemTransformNo')
		getSourceItem();
	} else {
		$("#itemCode").val("");
		$("#itemDescr").val("");
		$("#cost").val("");
		$("#uomDescr").val("");
		$("#itemTransformRemarks").val("");
	}
}

function getSourceItemByProcessQty(){
	var rowData = $("#itemProcessSourceDataTable").jqGrid('getRowData');
	var processQty=$("#qty").val();
	if(!rowData){
		return;
	}
	if(!processQty){
		processQty=0.0;
	}
	var qty=0.0;
	$.each(rowData,function(k,v){
		qty=v.transformper *processQty
		$("#itemProcessSourceDataTable").setCell(k+1,'qty',qty);
	})
}

function getSourceItem(){
	$("#itemProcessSourceDataTable").jqGrid("setGridParam",{
			url:"${ctx}/admin/itemProcess/getSourceItemByTransform",
			postData:$("#dataForm").jsonForm(),
			page:1
	}).trigger("reloadGrid");
}

function save(){
	$("#dataForm").bootstrapValidator("validate");
	if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	
	var datas=$("#dataForm").jsonForm();
	
	if($("#itemProcessSourceDataTable").jqGrid('getGridParam','records')==0){
		art.dialog({
				content: "无源材料数据"
		});
		return;
	}
	var tableData=$("#itemProcessSourceDataTable").jqGrid("getRowData");
	datas.tableData=tableData;
	Global.Dialog.returnData = datas;
	closeWin();
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system" id="saveBtn" onclick="save()" style="display: none">
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
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<input type="hidden" id="itemTransformRemarks" name="itemTransformRemarks" value="${itemProcessDetail.itemTransformRemarks}" />
						<input type="hidden" id="uomDescr" name="uomDescr" value="${itemProcessDetail.uomDescr}" />
						
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>转换编号</label>
									<input type="text" id="itemTransformNo" name="itemTransformNo"  
										value="${itemProcessDetail.itemTransformNo}"  />
								</li>
								<li>
									<label>加工材料</label>
									<input type="text" id="itemCode" name="itemCode" 
										value="${itemProcessDetail.itemCode}" readonly="readonly" />
								</li>
								<li>
									<label>材料名称</label>
									<input type="text" id="itemDescr" name="itemDescr" 
										value="${itemProcessDetail.itemDescr}" readonly="readonly" />
								</li>
								
								<%--<li class="form-validate">
									<label>成本</label>
									<input type="text" id="cost" name="cost" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${itemProcessDetail.cost}"/>
								</li>
								--%>
								<li class="form-validate">
									<label><span class="required">*</span>数量</label>
									<input type="text" id="qty" name="qty" 
										onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
										value="${itemProcessDetail.qty}" onchange="getSourceItemByProcessQty()"/>
								</li>
							</div>
						</ul>	
					</form>
				</div>
			</div>
			
			<div class="container-fluid" >  
				 <ul class="nav nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">源材料明细</a></li>
				</ul>
				<div id="tab1" class="tab-pane fade in active">
					<div id="content-list">
						<table id="itemProcessSourceDataTable"></table>
					</div>
				</div>
				
			</div>
		</div>
	</body>	
</html>
