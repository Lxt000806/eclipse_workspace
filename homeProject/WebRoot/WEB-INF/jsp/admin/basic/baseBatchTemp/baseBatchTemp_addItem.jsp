<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加模板</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#baseItemCode").openComponent_baseItem({
		showValue:"${baseBatchTemp.baseItemCode}",
		showLabel:"${baseBatchTemp.baseItemDescr}",
		condition:{isOutSet:$("#isOutSet").val()},
		callBack:function(data){
			$("#baseItemDescr").val(data.descr);
		},
	});
	$("#openComponent_baseItem_baseItemCode").attr("readonly",true);
	if('${baseBatchTemp.m_umState}'=="V"){
		$("#saveBtn,#isOutSet,#qty").attr("disabled",true);
		$("#baseItemCode").setComponent_baseItem({readonly:true});
	}
	$("#saveBtn").on("click", function() {
		var isRepeated=false;
		var baseItemCode=$("#baseItemCode").val();
		var pk=$("#pk").val();
		var rows = $(top.$("#iframe_item")[0].contentWindow.document.getElementById("detailDataTable")).jqGrid("getRowData");
		if(rows.length>0){
			for(var i=0;i<rows.length;i++){
				if(rows[i].baseitemcode==baseItemCode && rows[i].pk!=pk){
					isRepeated=true;
				};
			}
		}
		if(isRepeated){
			art.dialog({
				content: "此材料已存在！",
			});
			return;
		}
		var datas = $("#dataForm").jsonForm();
		Global.Dialog.returnData = datas;
		closeWin(true);
	});
});
function changeSet(){
	$("#baseItemCode,#baseItemDescr,#openComponent_baseItem_baseItemCode").val("");
	$("#baseItemCode").openComponent_baseItem({
		condition:{isOutSet:$("#isOutSet").val()},
		callBack:function(data){
			$("#baseItemDescr").val(data.descr);
		},
	});
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						>保存</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						 <input type="hidden" id="isOutSetDescr" name="isOutSetDescr" value="${baseBatchTemp.m_umState=='A'?'否':baseBatchTemp.isOutSetDescr}" />
						 <input type="hidden" id="baseItemDescr" name="baseItemDescr" value="${baseBatchTemp.baseItemDescr}" />
						 <input type="hidden" id="pk" name="pk" value="${baseBatchTemp.pk}" />
					
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group">
								<li><label>是否套餐</label> <house:xtdm id="isOutSet"
                                     dictCode="YESNO" value="${baseBatchTemp.m_umState=='A'?'0':baseBatchTemp.isOutSet}" 
                                     onchange="getDescrByCode('isOutSet','isOutSetDescr');changeSet()"></house:xtdm>
                           		</li>
							</div>
							<div class="validate-group">
								<li><label></span>基础项目编号</label> <input type="text" id="baseItemCode"
									name="baseItemCode"
									value="${baseBatchTemp.baseItemCode}" />
								</li>
							</div>
							<div class="validate-group">
								<li><label>数量</label> <input type="text"
									id="qty" name="qty"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
									value="${baseBatchTemp.m_umState=='A'?0:baseBatchTemp.qty}" />
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
