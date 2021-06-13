<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改ItemPreMeasure</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<style type="text/css">
#dataTablePager{
	width: auto !important;
}
</style>
<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemPreMeasure/goJqGridDetail?id=${preAppNo}",
		height:120,
		colModel : [
		  {name : 'ItemCode',index : 'ItemCode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemcodedescr',index : 'itemcodedescr',width : 200,label:'材料名称',align : "left"},
		  {name : 'fixareadescr',index : 'fixareadescr',width : 200,label:'装修区域',align : "left"},
		  {name : 'Qty',index : 'Qty',width : 60,label:'数量',align : "right",sum:true},
		  {name : 'uom',index : 'uom',width : 60,label:'单位',align : "left"},
		  {name : 'Remarks',index : 'Remarks',width : 310,label:'备注',align : "left"}
          ]
	});
/* 	var height=300;
	if("${photoList }".length>2){//图片集合不为空时显示图片div，并且调小表格高度
		$("#photoDiv").css("display","block");
		height=180;
	}else{
		$("#photoDiv").css("display","none");
	}
	$('div.ui-jqgrid-bdiv').css("height",height); */
	//保存操作
	$("#saveBtn").on("click",function(){
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/supplierItemPreMeasure/doUpdate',
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
						beforeunload: function () {
		    				closeWin();
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
function photoDownload(){
	var number = Global.JqGrid.allToJson("dataTable_material", "photoname").datas.length;
	if(number <= 0 ){
		art.dialog({
			content:"该记录没有图片",
			time:3000
		});
		return;
	}
	window.open("${ctx}/admin/itemPreApp/downLoad?no="+"${preAppNo}");
}
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system">保存</button>
	      <button id="photoBut" type="button" class="btn btn-system" onclick="photoDownload()">图片下载</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
           		<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<input type="hidden" name="pk" id="pk" value="${itemPreMeasure.pk}"/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width: 160px;" value="${itemPreMeasure.address}" readonly="readonly">
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectManDescr" name="projectManDescr" style="width: 160px;" value="${itemPreMeasure.projectManDescr}" readonly="readonly">
					</li>
					<li>
						<label>电话</label>
						<input type="text" id="phone" name="phone" style="width: 160px;" value="${itemPreMeasure.phone}" readonly="readonly">
					</li>
					<li>
						<label class="control-textarea">测量数据</label>
						<textarea id="measureRemark" name="measureRemark" maxlength="1000">${itemPreMeasure.measureRemark}</textarea>
					</li>
				</ul>
           	</form>
         </div>
     </div>
	<div id="content-list">
		<table id= "dataTable"></table> 
		<div id="dataTablePager"></div>
	</div> 
	<%-- <div style="padding-bottom: 50px;height: 160px;overflow: auto;" id="photoDiv">
		<c:forEach items="${photoList }" var="item" varStatus="st">
			<img id="measure_${st.index}" src="${photoPath }${item.photoName }" onload="AutoResizeImage(150,150,'measure_${st.index}');"
			 height="200" width="200">
			<a href="${ctx}/admin/supplierItemPreMeasure/doDownLoad?urlString=${photoPath }${item.photoName }&fileName=${item.photoName }">下载</a>
		</c:forEach>
	</div> --%>
	<div class="container-fluid" style="height: 200px;">
		<ul class="nav nav-tabs">
			<li class="active"><a
				href="#itemPreAppManage_tabView_materialPhoto" data-toggle="tab">材料图片</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="itemPreAppManage_tabView_materialPhoto"
				class="tab-pane fade active in">
				<jsp:include page="itemPreMeasure_materialPhoto.jsp">
					<jsp:param value="${preAppNo}" name="no" />
				</jsp:include>
			</div>
		</div>
	</div>
</div>
</body>
</html>
