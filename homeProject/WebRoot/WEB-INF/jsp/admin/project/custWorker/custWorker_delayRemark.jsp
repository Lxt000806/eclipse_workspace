<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.panel-info {
			margin: 0px;
		}
		.form-search .ul-form li label {
			width: 140px;
		}
	</style>
	<script type="text/javascript" defer>
		parent.$("#iframe_delayRemark").attr("height","98%");
		function doSave() {
			Global.Form.submit("page_form", "doDelayRemark", {}, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false);">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="pk" value="${custWorker.pk}">
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>安排进场延误原因</label>
								<house:xtdm  id="comeDelayType" dictCode="CUSTWKDELAYTYPE" style="width:160px;" 
									value="${custWorker.comeDelayType}"></house:xtdm>
							</li>
						</div>
						<div class="row">
							<li >
								<label>完工延误原因</label>
								<house:xtdm  id="endDelayType" dictCode="CUSTWKDELAYTYPE" style="width:160px;" 
									value="${custWorker.endDelayType}"></house:xtdm>
							</li>
						</div>
						<div class="row">
							<li >
								<label>签到延误原因</label>
								<house:xtdm  id="signDelayType" dictCode="CUSTWKDELAYTYPE" style="width:160px;" 
									value="${custWorker.signDelayType}"></house:xtdm>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>
</html>
