<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>配送区域编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#saveBtn").on("click", function() {
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/builder/doRegionUpdate",
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
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="code" value="${builder.code }" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate"><label >项目名称</label>
								<input type="text" style="width:160px;" id="descr" name="descr"
								value="${builder.Descr }" readonly="readonly" onchange="initTude()"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>配送区域</label> <house:dict
									id="sendRegion" dictCode=""
									sql="select a.No,a.No+' '+a.descr descr  from tSendRegion a where a.Expired='F' and a.sendType='2' or (a.sendType='' or a.sendType is null) "
									sqlValueKey="No" sqlLableKey="Descr"
									value="${builder.sendRegion}">
								</house:dict>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>供应商配送区域</label> <house:dict
									id="splSendRegion" dictCode=""
									sql="select a.No,a.No+' '+a.descr descr  from tSendRegion a where a.Expired='F' 
									    and a.sendType='1' or (a.sendType='' or a.sendType is null)  "
									sqlValueKey="No" sqlLableKey="Descr"
									value="${builder.splSendRegion}">
								</house:dict>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
