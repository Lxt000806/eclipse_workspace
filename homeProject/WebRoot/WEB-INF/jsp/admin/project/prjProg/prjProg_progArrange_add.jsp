<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>计划进度编排--新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
<body>
	<div class="body-box-form">
		<!-- panelBar -->
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
		<!-- edit-form -->
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<input type="hidden" name="m_umState" id="m_umState" value="A" />
					<ul class="ul-form">
						<li><label><span class="required">*</span>施工项目</label> <house:xtdm
								id="prjItem" dictCode="PRJITEM" style="width:160px;"
								onchange="getDescrByCode('prjItem','prjDescr')"></house:xtdm>
						</li>
						<li><label><span class="required">*</span>施工天数</label> <input type="text"
							style="width:160px;" id="consDay" name="consDay"
							onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
						</li>
						<li hidden="true"><label>施工项目名称</label> <input type="text"
							id="prjDescr" name="prjDescr" style="width:160px;"
							readonly="readonly" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#saveBtn").on("click", function() {
				var prjItems = $.trim("${prjItems}");
				var prjItem = $.trim($("#prjItem").val());
				var consDay = $.trim($("#consDay").val());
				if(prjItem==""){
					art.dialog({
						content : '请选择施工项目',
					});
					return;
				}
				if(consDay==""){
					art.dialog({
						content : '请选择施工天数',
					});
					return;
				}
				if (prjItems.indexOf(prjItem) != -1) {
					art.dialog({
						content : '该项目已存在，不能重复选择',
					});
					return;
				}
				var datas = $("#dataForm").jsonForm();
				Global.Dialog.returnData = datas;
				closeWin();
			});
		});
	</script>
</body>
</html>
