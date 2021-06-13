<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>防水施工面积确认--确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
function save(){
	$.ajax({
		url : "${ctx}/admin/waterAreaCfm/doConfirm",
		type : "post",
		data : $("#dataForm").jsonForm(),
		dataType : "json",
		cache : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : "保存数据出错~"
			});
		},
		success : function(obj) {
			if (obj.rs) {
				art.dialog({
					content : obj.msg,
					time : 1000,
					beforeunload : function() {
						closeWin();
					}
				});
			} else {
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content : obj.msg,
					width : 200
				});
			}
		}
	});
}
//校验函数
$(function() {
	var m_umState='${map.m_umState}';
	$("#custType").attr("disabled",true);
	$("#custCode").openComponent_customer({
		showValue:"${map.custcode}",
		showLabel:"${map.custdescr}",
		readonly:true
	});
	$("#workerCode").openComponent_worker({
		showValue:"${map.workercode}",
		showLabel:"${map.workername}",
		readonly:true
	});
	if(m_umState=="goView"){
		$("input").attr("disabled",true);
		$("select").attr("disabled",true);
		$("#saveBtn").attr("disabled",true);
	}
});

</script>
<style type="text/css">

</style>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system"
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
						<div class="row">
							<li><label>客户编号</label> <input type="text"
								id="custCode" name="custCode" />
							</li>
							<li><label>客户类型</label> <house:dict
										id="custType" dictCode=""
										sql="select code ,code +' ' + desc1 descr from tcustType where expired='F'"
										sqlValueKey="code" sqlLableKey="descr"
										value="${map.custtype.trim()}">
									</house:dict>
							</li>
						</div>
						<div class="row">
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:451px;" value="${map.address}" readonly/>
							</li>
						</div>
						<div class="row">
							<li><label>工人编号</label> <input type="text"
								id="workerCode" name="workerCode" />
							</li>
							<li>
								<label>工人申报面积</label> 
								<input type="number" id="waterArea" name="waterArea" value="${map.waterarea }"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label class="control-textarea">确认说明</label>
								<textarea id="confirmRemarks" name="confirmRemarks" style="width:451px;" rows="2">${map.confirmremarks }</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
