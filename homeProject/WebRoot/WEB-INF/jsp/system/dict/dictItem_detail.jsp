<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看字典元素</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<ul class="ul-form">
					<div class="validate-group row">
						<div class="col-sm-6">
						<li class="form-validate">
							<label>元素文本</label>
							<input type="text" style="width:160px;" id="itemLabel" name="itemLabel" value="${dictItem.itemLabel }"/>
						</li>
						</div>
						<div class="col-sm-6">
						<li class="form-validate">
							<label>元素编码</label>
							<input type="text" style="width:160px;" id="itemCode" name="itemCode" value="${dictItem.itemCode }" readonly="readonly"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-6">
						<li class="form-validate">
							<label>元素值</label>
							<input type="text" style="width:160px;" id="itemValue" name="itemValue" value="${dictItem.itemValue }"/>
						</li>
						</div>
						<div class="col-sm-6">
						<li class="form-validate">
							<label>排列顺序</label>
							<input type="text" style="width:160px;" id="orderNo" name="orderNo" value="${dictItem.orderNo }"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-6">
						<li class="form-validate">
							<label>创建时间</label>
							<fmt:formatDate value="${dictItem.genTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</li>
						</div>
						<div class="col-sm-6">
						<li class="form-validate">
							<label>修改时间</label>
							<fmt:formatDate value="${dictItem.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</li>
						</div>
					</div>
					<div class="validate-group row">
						<div class="col-sm-12">
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remark" name="remark">${dictItem.remark }</textarea>
						</li>
						</div>
					</div>
					</ul>
				</form>
			</div>
		</div>
</div>
</body>
</html>
