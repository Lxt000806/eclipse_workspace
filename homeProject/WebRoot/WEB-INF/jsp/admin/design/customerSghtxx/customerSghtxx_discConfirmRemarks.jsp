<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>优惠审批说明</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_gift.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">	
			function save(){
	        	$.ajax({
	    			url : "${ctx}/admin/customerSghtxx/doDiscConfirmRemarks",
					data: $("#dataForm").jsonForm(),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "请求出现异常"
						});
				    },
				    success: function(obj){
				    	art.dialog({
				    		content:obj.msg,
				    		time:1000,
				    		beforeunload: function () {
					    		closeWin();
							}
				    	});
				    	
				    }
	    		});
			}
			$(function(){
				$("#giftPK").openComponent_gift({showValue:"${custGift.giftPK}",showLabel:"${custGift.giftDescr}",readonly:true});
			});
		</script>
	</head> 
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm"  class="form-search">
						<input type="hidden" id='pK 'name="pK"  value="${custGift.pK}"/>
						<ul class="ul-form">
						<li>
						<label>赠送项目</label>
							<input type="text" id="giftPK" name="giftPK" style="width:160px;"/>
						</li>
						<li>
							<label>类型</label>
							<house:xtdm  id="type" dictCode="GIFTTYPE" style="width:160px;" onchange="chgType()" value= "${custGift.type }"></house:xtdm>
						</li>
						<li>
							<label class="control-textarea">优惠审批说明</label>
							<textarea id="discConfirmRemarks" name="discConfirmRemarks" >${custGift.discConfirmRemarks}</textarea>
						</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>


