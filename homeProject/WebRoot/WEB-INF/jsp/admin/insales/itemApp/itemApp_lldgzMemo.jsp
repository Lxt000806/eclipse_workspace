<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料单跟踪备注页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<style type="text/css">
			.panelBar{
				height:46px;
			}
		</style>
		<script type="text/javascript">
			function doLldgzMemo(){
		
	        	$.ajax({
	    			url : "${ctx}/admin/itemApp/doLldgzMemo",
					data: $("#dataForm").jsonForm(),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "领料单跟踪备注出现异常"
						});
				    },
				    success: function(obj){
				    	art.dialog({
				    		content:obj.msg,
				    		ok:function(){
				    			if(obj.rs){
				    				closeWin();
				    			}
				    		}
				    	});
				    	
				    }
	    		});
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="doLldgzMemo()">保存</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm"  class="form-search">
						<input type="hidden" name="m_umState" id="m_umState" value="${data.m_umState}"/>
						<ul class="ul-form">
							<li>
								<label>领料单号</label>
								<input type="text" id="no" name="no" value="${data.no}" readonly/>
							</li>
							<li>
								<label class="control-textarea">领料单跟踪备注</label>
								<textarea id="followRemark" name="followRemark">${data.followRemark}</textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>


