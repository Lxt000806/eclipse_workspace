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
	<title>材料报价模板新增明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
	var selectRows = [];
$(function(){
	$("#itemCode").openComponent_item({condition:{itemType1:"${itemPlanTemp.itemType1 }",disabledEle:"itemType1"}});
	
	var titleCloseEle = parent.$("div[aria-describedby=dialog_fastAdd]").children(".ui-dialog-titlebar");
	$(titleCloseEle[0]).children("button").remove();
	var childBtn=$(titleCloseEle).children("button");
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
								+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
	$(titleCloseEle[0]).children("button").on("click", exitPage); 
	
	$("#saveBtn").on("click",function(){
		var qty = $.trim($("#qty").val());
		var itemCode = $.trim($("#itemCode").val());
		if(qty==""){
			art.dialog({
				content:"数量不能为空",
			});
			return;
		}
		if(itemCode==""){
			art.dialog({
				content:"材料编号不能为空",
			});
			return;
		}
		var datas=$("#page_form").jsonForm();
		datas.descr=$("#openComponent_item_itemCode").val().split("|")[1];
		datas.dispSeq = selectRows.length+1;
	 	selectRows.push(datas);

	 	$("#openComponent_item_itemCode").val("");
	 	$("#itemCode").val("");
	 	$("#qty").val("");
	 	
	});
});
	function exitPage(){
		var arr = [];
		var arrTemp = [];
		$.each(selectRows,function(k,v){
				arr.push(v);
		});
		Global.Dialog.returnData = arr;
		closeWin();
	}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="exitPage()">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label><span class="required">*</span>材料编号</label>
								<input type="text" id="itemCode" name="itemCode" style="width:160px;"/>
							</li>
							<li>
								<label><span class="required">*</span>数量</label>
								<input type="text" id="qty" name="qty" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
