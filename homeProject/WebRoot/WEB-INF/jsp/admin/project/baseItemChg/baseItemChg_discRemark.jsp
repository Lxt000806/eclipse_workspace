<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>优惠说明</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
            	<ul class="ul-form" style="height:200px">
            		<li>
            			<label>客户编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${customer.code}" readonly="readonly"/>
            		</li>
            		<li>
            			<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address}" readonly="readonly"/>
            		</li>
            		<li>
            			<label class="control-textarea" style ="margin-top:-120px">优惠说明</label>
						<textarea id="discRemark" name="discRemark" readonly="readonly" rows="10" >${customer.discRemark}</textarea>
            		</li>
            	</ul>
            </form>
         </div>
     </div>
            		
</div>
</body>
</html>

