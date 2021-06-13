<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
	</style>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="doSave" >
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWindows()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin-bottom: 10px;">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>单据标题</label> 
								<input type="text" id="title" name="title" style="width:180px;"
									value="${title }" readonly />
							</li>
							<li class="form-validate">
								<label>当前执行人</label> 
								<input type="text" id="operDescr" name="operDescr" style="width:180px;"
									value="${operDescr }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label><span class="required">*</span>转交执行人</label> 
								<input type="text" id="newOperator" name="newOperator" style="width:180px;"/>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function() {
		$("#newOperator").openComponent_czybm();
		$("#openComponent_czybm_newOperator").attr("readonly",true);
		
		var component = document.getElementById('openComponent_czybm_newOperator');
		component.style.width="155px";
		
		$("#doSave").on("click",function(){
			if($("#newOperator").val() == ""){
				art.dialog({
					content:"请选择转交执行人",
				});
				return;
			}
			$.ajax({
				url:"${ctx}/admin/wfProcInst/doUpdateOperator",
				type:"post",
				data:{taskId:"${taskId}",newOperator:$("#newOperator").val(),processInstanceId:"${processInstanceId}"},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				},
				success:function(obj){
					if(obj.rs){
						art.dialog({
							content: "操作成功",
							time: 1000,
							beforeunload:function(){
								Global.Dialog.returnData = true;
								closeWin(true);
							}
						});
					}else{
						art.dialog({
							content: obj.msg,
							time: 1000,
						});
					}
				}
			});
		});
	});
	
	function closeWindows(){
		Global.Dialog.returnData = false;
		closeWin();
	}
	</script>
</html>
