<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>

	<style type="text/css">
		.form-search .ul-form li label {
			width: 140px;
		}
		.btn {
		    padding: 2px 33px;
		    font-size: 12px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="doClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token><!-- 按钮只能点一次 -->
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label><span class="required">*</span>销售单号</label>
								<input type="text" id="no" name="no" style="width:160px;" value="${salesInvoice.no}" readonly />
							</li>
						</div>
						<div class="row">
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${salesInvoice.custcode}" readonly />
							</li>
						</div>
						<div class="row">
							<li>
								<label>客户名称</label> 
								<input type="text" id="custName" name="custName" style="width:160px;" value="${salesInvoice.custdescr}" readonly />
							</li>
						</div>
						<div class="row">
							<li>
								<label style="margin-top: 5px;">授权未收款可发货</label>
							</li>
							<div class="btn-group" id="isAuthorized" data-toggle="buttons" style="margin-left: 6px;">
								<label class="btn btn-default ${salesInvoice.isauthorized=='1'?'active':''}">
									<input type="radio" name="isAuthorized" value="1" 
										 />是
								</label>
								<label class="btn btn-default ${salesInvoice.isauthorized=='0'?'active':''}">
									<input type="radio" name="isAuthorized" value="0" 
										 />否
								</label>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" defer>
$(function() {
	parent.$("#iframe_sendGoodsPermit").attr("height","98%");

});

function doClose(){
	closeWin();
}

function doSave(){
	var datas = $("#page_form").jsonForm();
	datas.isAuthorized = $("#isAuthorized .active").children().val();
	if ("1" == datas.isAuthorized) {
		datas.authorizer = "${czybh.emnum}";
	}
	saveAjax(datas);
}

function saveAjax(datas) {
	$.ajax({
		url:"${ctx}/admin/salesInvoice/doAuthorizeSave",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
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
}
</script>
</html>
