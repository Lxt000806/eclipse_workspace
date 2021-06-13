<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
	    <title>查看消息</title>
	    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	    <META HTTP-EQUIV="expires" CONTENT="0" />
	    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	    <%@ include file="/commons/jsp/common.jsp" %>
	
		<style type="text/css">
		label{
		    width:140px !important;   
		}
		</style>
		
		<script type="text/javascript">
		$(function(){
		    //通过js选择器获取dataForm表单下的所有input后代，并添加disabled属性
		    document.querySelectorAll("#dataForm input")
		            .forEach(
			               function(input){
			                   input.disabled="disabled";
			              }
		             );
		             
		     $("#msgText").prop("readonly","readonly");
		     $("#remarks").prop("readonly","readonly");
		});
		
		function beforeDoRcv(){
			doRcv();
		}
		
		function doRcv(){
			$.ajax({
				url: "${ctx}/admin/personMessage/doRcv",
				type: "get",
				data: {
					pk: $("#pk").val()
				},
				dataType: "json",
				error: function(obj){			    		
					art.dialog({
						content:"访问出错,请重试!",
						time:3000,
						beforeunload: function () {}
					});
				},
				success: function (res){
					closeWin();
				} 
			});		
		}
		
		function goUpSendDate(){
			Global.Dialog.showDialog("goUpSendDate",{
				title:"延后执行",
				url:"${ctx}/admin/prjMsg/goUpSendDate?id="+$("#pk").val(),
				height:600,
				width:1000,
				returnFun: function(){
					setTimeout(function(){
						closeWin();
					}, 100);
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
						<c:if test="${personMessage.bRcvStatus  == '未读'}">
							<button type="button" class="btn btn-system" id="closeBut" onclick="beforeDoRcv()">
								<span>已读</span>
							</button>
						</c:if>
						<c:if test="${personMessage.bMsgType  == '施工提醒' && personMessage.bRcvStatus  == '未读'}">
							<button type="button" class="btn btn-system" id="closeBut" onclick="goUpSendDate()">
								<span>延后执行</span>
							</button>
						</c:if>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		
			<div class="panel panel-info" style="margin: 0px;">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="pk" id="pk" value="${personMessage.pk}" /> 
						<ul class="ul-form">
							<div class="validate-group">
								<li class="form-validate">
									<label><span class="required">*</span>标题</label>
									<input type="text" id="title" name="title" value="${personMessage.title}" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>接收状态</label>
									<input type="text" id="bRcvStatus" name="bRcvStatus" value="${personMessage.bRcvStatus}" />
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate">
									<label><span class="required">*</span>消息类型</label>
									<input type="text" id="bMsgType" name="bMsgType" value="${personMessage.bMsgType}" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>施工消息类型</label>
									<input type="text" id="progMsgTypeDescr" name="progMsgTypeDescr" value="${personMessage.progMsgTypeDescr}" />
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate">
									<label><span class="required">*</span>生成日期</label>
									<input type="text" id="crtDate" name="crtDate" value="${personMessage.crtDate}" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>发送日期</label>
									<input type="text" id="sendDate" name="sendDate" value="${personMessage.sendDate}" />
								</li>
							</div>
							<div class="validate-group">
								<li >
									<label style="top: -40px;" class="control-textarea"><span class="required">*</span>消息内容</label>
									<textarea type="text" id="msgText" name="msgText" style="width:491px" rows="4">${personMessage.msgText}</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
