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
	<title>新主材提成规则匹配材料新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'ZC',
								secondSelect:'itemType2',
								secondValue:'${map.itemtype2 }',
								thirdSelect:'itemType3',
								thirdValue:'${map.itemtype3 }',});
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

	var datas=$("#dataForm").jsonForm();
	
	var itemType2 = $("#itemType2").val();
	var itemType3 = $("#itemType3").val();
	if(itemType2 == ""){
		art.dialog({
			content:"材料类型2不能为空",
		});
		return;
	}
	if(itemType3 != ""){
		datas.itemType3Descr = $("#itemType3").find("option:selected").text().split($("#itemType3").val())[1];
	} else {
		datas.itemType3Descr = "";
	}
	
	datas.itemType2Descr = $("#itemType2").find("option:selected").text().split($("#itemType2").val())[1];
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
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li hidden="true">	
									<label>材料类型1</label>
									<select id="itemType1" name="itemType1"></select>
								</li>
								<li class="form-validate">
									<label>材料类型2</label>
									<select id="itemType2" name="itemType2"></select>
								</li>
								<li class="form-validate">
									<label>材料类型3</label>
									<select id="itemType3" name="itemType3" ></select>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
