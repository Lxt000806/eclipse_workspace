<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>修改CustLoan</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 //校验函数
$(function() {	
	$("#custCode").openComponent_customer({showLabel:"${custLoan.custDescr}",showValue:"${custLoan.custCode}",disabled:true});
});
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label><span class="required">*</span>客户编号</label> <input type="text"
								id="custCode" name="custCode" value="${custLoan.custCode}" />
							</li>
							<li class="form-validate"><label style="color:#777777">楼盘</label> <input type="text" id="address"
								name="address" value="${custLoan.address}" readonly="readonly" />
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label style="color:#777777">客户状态</label> <house:xtdm id="statusDescr"
									dictCode="CUSTOMERSTATUS" value="${custLoan.statusDescr }" disabled="true"></house:xtdm>
							</li>
							<li class="form-validate"><label>协议提交日期</label> <input type="text" id="agreeDate" name="agreeDate"
								class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custLoan.agreeDate }' pattern='yyyy-MM-dd'/>" />
							</li>
						</div>
						<div class="validate-group">

							<li class="form-validate"><label>贷款银行</label> <input type="text" id="bank" name="bank"
								value="${custLoan.bank}" />
							</li>
							<li class="form-validate"><label>贷款总额</label> <input type="text" id="amount" name="amount"
								style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${custLoan.amount}" />
								<span>万元</span>
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label> 首次放款时间</label> <input type="text" id="firstDate"
								name="firstDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custLoan.firstDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate"><label>首次放款金额</label> <input type="text" id="firstAmount"
								name="firstAmount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${custLoan.firstAmount}" />
								<span>万元</span>
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label> 二次放款时间</label> <input type="text" id="secondDate"
								name="secondDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custLoan.secondDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li class="form-validate"><label>二次放款金额</label> <input type="text" id="secondAmount"
								name="secondAmount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${custLoan.secondAmount}" />
								<span>万元</span>
							</li>
						</div>
						<li class="form-validate"><label class="control-textarea">已签约待放款</label> <textarea id="signRemark"
								name="signRemark">${custLoan.signRemark}</textarea>
						</li>
						<li class="form-validate"><label class="control-textarea">退件拒批</label> <textarea id="confuseRemark"
								name="confuseRemark">${custLoan.confuseRemark}</textarea>
						</li>
						<li class="form-validate"><label class="control-textarea">需跟踪</label> <textarea id="followRemark"
								name="followRemark">${custLoan.followRemark}</textarea>
						</li>
						<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="Remark"
								name="Remark">${custLoan.remark}</textarea>
						</li>
						<li class="form-validate" hidden="true">
								<label>过期</label>
								<input type="checkbox" id="expired" name="expired" value="${custLoan.expired }" onclick="checkExpired(this)" ${custLoan.expired=='T'?'checked':'' } >
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

