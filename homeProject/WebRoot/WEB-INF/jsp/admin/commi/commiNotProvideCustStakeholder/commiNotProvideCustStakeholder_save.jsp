<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>指定客户--增加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_commiCycle.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#custCode").openComponent_customer({
			showValue:"${commiNotProvideCustStakeholder.custCode}",
			showLabel:"${commiNotProvideCustStakeholder.custDescr}",
		});
		$("#role").openComponent_roll({
			showValue:"${commiNotProvideCustStakeholder.role}",
			showLabel:"${commiNotProvideCustStakeholder.roleDescr}",
		});
		if("${commiNotProvideCustStakeholder.m_umState }"=="V"){
			disabledForm("dataForm");
		}
		$("#saveBtn").on("click", function() {
			var custCode=$("#custCode").val();
			if(custCode==""){
				art.dialog({
					content : "请选择客户编号！",
				});
				return;
			}
			var url="${ctx}/admin/commiNotProvideCustStakeholder/";
			if("${commiNotProvideCustStakeholder.m_umState }"=="A"){
				url+="doSave";
			}else if("${commiNotProvideCustStakeholder.m_umState }"=="M"){
				url+="doUpdate";
			}
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : url,
				type : "post",
				data : datas,
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
		});
	});
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<c:if test="${commiNotProvideCustStakeholder.m_umState !='V'}">
							<button type="button" class="btn btn-system" id="saveBtn">
								<span>保存</span>
							</button>
						</c:if>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" /> <input
						type="hidden" name="m_umState" value="${commiNotProvideCustStakeholder.m_umState }" />
					<input type="hidden" name="pk" value="${commiNotProvideCustStakeholder.pk }" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li>
								<label>客户编号</label> 
								<input type="text" id="custCode"
							      name="custCode" value="${commiNotProvideCustStakeholder.custCode }" />
							</li>
							<li>
								<label>角色</label> 
								<input type="text" id="role"
							      name="role" value="${commiNotProvideCustStakeholder.role }" />
							</li>
						</div>
						<div class="validate-group row">
							<li>
								<label>停发类型</label>
								<house:xtdm id="type" dictCode="COMMINPCSTYPE" value="${commiNotProvideCustStakeholder.type }"></house:xtdm>
							</li>
							<li>
								<label>补发月份</label>
								<input type="text" id="reProvideMon" name="reProvideMon" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${commiNotProvideCustStakeholder.reProvideMon }" />
							</li>
						</div>
						<div class="validate-group row" style="height:150px">
							<li class="form-validate"><label class="control-textarea">说明</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:100px; " />${commiNotProvideCustStakeholder.remarks}</textarea>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
