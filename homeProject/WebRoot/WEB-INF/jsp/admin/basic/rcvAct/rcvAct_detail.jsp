<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>收款账户明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}"></script>
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
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh }"/>
				<input type="hidden" id="expired" name="expired" value="${rcvAct.expired }"/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li>
							<label>账户编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${rcvAct.code }" disabled="disabled"/>
						</li>
						<li>
							<label>最后更新时间</label>
							<input type="text" id="lastUpdate" name="lastUpdate" class="i-date" style="width:160px;" 
								value="<fmt:formatDate value='${rcvAct.lastUpdate}' pattern='yyyy-MM-dd'/>" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row">
						<li>
							<label>账户名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${rcvAct.descr }" disabled="disabled"/>
						</li>
						<li>
							<label>最后更新人员</label>
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;" value="${rcvAct.lastUpdatedBy }" 
								disabled="disabled"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate"><label>收款账号</label> <input type="text" id="cardId"
							name="cardId" style="width:160px;" value="${rcvAct.cardId }" disabled="disabled"/>
						</li>
						<div class="validate-group row">
							<li><label>收款单位</label> <house:dict id="payeeCode"
									dictCode=""
									sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
									sqlLableKey="descr" sqlValueKey="code" value="${rcvAct.payeeCode }" disabled="true"></house:dict>
							</li>
						</div>
					</div>
					<div class="validate-group row">
						<li>
							<label>银行</label> 
							<house:dict id="bankCode" dictCode="" 
								sql="select Code,Code+' '+Descr Descr from tBank where Expired = 'F'" 
								sqlValueKey="Code" sqlLableKey="Descr" value="${rcvAct.bankCode}"/>
						</li>
					</div>
					<div class="validate-group row">
                        <li>
                            <label>管理员</label>
                            <input id="admin" name="admin" />
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>转账收款</label>
                            <house:xtdm id="allowTrans" dictCode="YESNO" value="${rcvAct.allowTrans}" disabled="true"></house:xtdm>
                        </li>
                    </div>
					<div class="validate-group row">
						<li>
							<label>过期</label>
							<input type="checkbox" id="expired" name="expired" value="${rcvAct.expired }",
								onclick="checkExpired(this)" ${rcvAct.expired=="T"?"checked":"" } disabled="disabled"/>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
</div>
<script>
    $(function() {
        $("#admin").openComponent_employee({
	        showValue: "${rcvAct.admin}",
	        readonly: true
	    })
	    
	    $("#openComponent_employee_admin").blur()
    })
</script>
</body>
</html>

