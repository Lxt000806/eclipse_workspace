<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
  
  	<title>查看礼品券信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
  </head>
  
  <body>
  	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" value="${giftToken.custCode }" />
						</li>
						<li>	
							<label>客户名称</label>
							<input type="text" id="custDescr" name="custDescr" value="${giftToken.custDescr }" />
						</li>
						<li>
							<label>礼品编号</label>
							<input type="text" id="itemCode" name="itemCode" value="${giftToken.itemCode }" />
						</li>
						<li>
							<label>礼品名称</label>
							<input type="text" id="itemDescr" name="itemDescr" value="${giftToken.itemDescr }" />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${giftToken.address }" />
						</li>
						<li>
							<label>券号</label>
							<input type="text" id="tokenNo" name="tokenNo" value="${giftToken.tokenNo }" />
						</li>
						<li>
							<label>数量</label>
							<input type="text" id="qty" name="qty" value="${giftToken.qty }" />
						</li>
						<li>
							<label>状态</label>
							<input type="text" id="status" name="status" value="${giftToken.status } ${giftToken.statusDescr}" />
						</li>
					</ul>		
				</form>
			</div>
  		</div>
  	</div>
  </body>
</html>
