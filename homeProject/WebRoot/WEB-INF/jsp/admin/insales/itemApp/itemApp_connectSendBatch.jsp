<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>关联配送批次页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_itemSendBatch.js?v=${v}" type="text/javascript"></script>
		<style type="text/css">
			.panelBar{
				height:46px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$("#itemSendBatchNo").openComponent_itemSendBatch({
					width:1200,
					height:680,
					condition:{
						requestPage:"itemApp_connectSendBatch",
						custCode:$("#custCode").val()
					}
				});
				$("#openComponent_itemSendBatch_itemSendBatchNo").attr("readonly",true);
			});
			function doConnSendBatch(){
		
	        	$.ajax({
	    			url : "${ctx}/admin/itemApp/doConnSendBatch",
					data: {
						no:$("#no").val(),
						sendBatchNo:$("#itemSendBatchNo").val(),
						custCode:$("#custCode").val()
					},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "配送批次保存出现异常"
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
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="doConnSendBatch()">保存</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm"  class="form-search">
						<input type="hidden" name="m_umState" id="m_umState" value="${data.m_umState}"/>
						<input type="hidden" name="custCode" id="custCode" value="${data.custCode}"/>
						<ul class="ul-form">
							<li>
								<label>领料单号</label>
								<input type="text" id="no" name="no" value="${data.no}" readonly/>
							</li>
							<li>
								<label>配送批次</label>
								<input type="text" id="itemSendBatchNo" name="itemSendBatchNo" value="${data.itemSendBatchNo}"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>


