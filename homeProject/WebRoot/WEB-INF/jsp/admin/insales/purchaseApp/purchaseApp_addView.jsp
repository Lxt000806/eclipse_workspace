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
	<title>采购明细新增</title>
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
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	$("#itemCode").openComponent_item({
	    condition:{
	        itemType1:"${purchaseApp.itemType1}",
	        readonly:"1",
	        hiddenFields: "expired"
	    },
	    showLabel:"${item.descr}",
	    showValue:"${map.itemcode}",
	    readonly:true
	});
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			role:{
				validators:{
					notEmpty:{
						message:"角色从不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function doSave(){
	var datas=$("#dataForm").jsonForm();
	Global.Dialog.returnData = datas;
	closeWin();
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="itemType1" id="itemType1" value="${purchaseApp.itemType1 }"/>
						<input type="hidden" name="itemDescr" id="itemDescr" value="${map.itemdescr}"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>材料编号</label>
									<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${map.remark }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>数量</label>
									<input type="text" id="qty" name="qty" style="width:160px;" value="${map.qty }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remark" name="remark" rows="2">${map.remark }</textarea>
	  							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
