<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>薪酬结算企业管理--查看</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <input type="hidden" id="expired" name="expired" value="${salarySettleCmp.expired}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>企业名称</label>
                            <input style="width:448px;" type="text" id="descr" name="descr" value="${salarySettleCmp.descr}" readonly/>
                        </li>
                        <li class="form-validate">
                        	<label>银行</label> 
							<house:xtdm id="bankType" dictCode="SALBANKTYPE" value="${salarySettleCmp.bankType}"> </house:xtdm>
						</li>
						<li class="form-validate">
							<label>户名</label> 
							<input type="text" id="actName" name="actName" value="${salarySettleCmp.actName}"/>
						</li>
						<li class="form-validate">
							<label>卡号</label> 
							<input type="text" id="cardId" name="cardId" value="${salarySettleCmp.cardId}"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" />
						</li>
                        <li>
                            <label class="control-textarea">备注</label>
                            <textarea type="text" id="remarks" name="remarks" readonly>${salarySettleCmp.remarks}</textarea>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox" disabled
                                   ${salarySettleCmp.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">


</script>
</body>
</html>
