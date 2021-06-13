<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
</head>

<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form action="" method="post" id="page_form" class="form-search">
                <house:token></house:token>
                <input type="hidden" name="code" value="${assetType.code}"/>
                <input type="hidden" id="expired" name="expired" value="${assetType.expired}"/>
                <ul class="ul-form">
					<div class="validate-group row">
					    <li class="form-validate">
					        <label><span class="required">*</span>名称</label>
					        <input type="text" id="descr" name="descr" value="${assetType.descr}" readonly="readonly"/>
					    </li>
					    <li class="form-validate">
					        <label><span class="required">*</span>助记码</label>
					        <input type="text" id="remCode" name="remCode" value="${assetType.remCode}" readonly="readonly"/>
					    </li>
					    <li class="form-validate">
					        <label><span class="required">*</span>折旧方法</label>
					        <house:xtdm id="deprType" dictCode="ASSETDEPRTYPE" value="${assetType.deprType}" disabled="true"></house:xtdm>
					    </li>
					    <li>
					        <label>过期</label>
					        <input type="checkbox" ${assetType.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)" disabled="disabled"/>
					    </li>
					</div>
                </ul>
            </form>
        </div>
    </div>
</div>
</body>
<script>

</script>
</html>
