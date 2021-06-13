<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>提成明细--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#role").openComponent_roll({
			showValue:"${commiCustStakeholder.role}",
			showLabel:"${commiCustStakeholder.roleDescr}",
		});
		$("#empCode").openComponent_roll({
			showValue:"${commiCustStakeholder.empCode}",
			showLabel:"${commiCustStakeholder.empName}",
		});
		disabledForm("dataForm");
	});
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>楼盘</label> 
								<input type="text" id="address"
							      name="address" value="${commiCustStakeholder.address }" />
							</li>
							<li class="form-validate">
								<label>员工</label> 
								<input type="text" id="empCode"
							      name="empCode" value="${commiCustStakeholder.empCode }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>角色</label> 
								<input type="text" id="role"
							      name="role" value="${commiCustStakeholder.role }" />
							</li>
							<li class="form-validate">
								<label>业绩金额</label>
								<input type="text" id="perfAmount" name="perfAmount" 
									value="${commiCustStakeholder.perfAmount }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>权重</label> 
								<input type="text" id="weightPer"
							      name="weightPer" value="${commiCustStakeholder.weightPer }" />
							</li>
							<li class="form-validate">
								<label>提成点</label>
								<input type="text" id="commiPer" name="commiPer" 
									value="${commiCustStakeholder.commiPer }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>补贴点</label> 
								<input type="text" id="subsidyPer"
							      name="subsidyPer" value="${commiCustStakeholder.subsidyPer }" />
							</li>
							<li class="form-validate">
								<label>倍数</label>
								<input type="text" id="multiple" name="multiple" 
									value="${commiCustStakeholder.multiple }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>提成金额</label> 
								<input type="text" id="commiAmount"
							      name="commiAmount" value="${commiCustStakeholder.commiAmount }" />
							</li>
							<li class="form-validate">
								<label>提成比例</label>
								<input type="text" id="commiProvidePer" name="commiProvidePer" 
									value="${commiCustStakeholder.commiProvidePer }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>补贴比例</label>
								<input type="text" id="subsidyProvidePer" name="subsidyProvidePer" 
									value="${commiCustStakeholder.subsidyProvidePer }" />
							</li>
							<li class="form-validate">
								<label>倍数比例</label>
								<input type="text" id="multipleProvidePer" name="multipleProvidePer" 
									value="${commiCustStakeholder.multipleProvidePer }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>本次应发</label>
								<input type="text" id="shouldProvideAmount" name="shouldProvideAmount" 
									value="${commiCustStakeholder.shouldProvideAmount }" />
							</li>
							<li class="form-validate">
								<label>调整金额</label>
								<input type="text" id="adjustAmount" name="adjustAmount" 
									value="${commiCustStakeholder.adjustAmount }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>本次实发</label>
								<input type="text" id="realProvideAmount" name="realProvideAmount" 
									value="${commiCustStakeholder.realProvideAmount }" />
							</li>
							<li class="form-validate">
								<label >累计已发</label>
								<input type="text" id="totalRealProvideAmount" name="totalRealProvideAmount" 
									value="${commiCustStakeholder.totalRealProvideAmount }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>右边销售基数</label>
								<input type="text" id="rightCardinal" name="rightCardinal" 
									value="${commiCustStakeholder.rightCardinal }" />
							</li>
							<li class="form-validate">
								<label>右边提成点</label>
								<input type="text" id="rightCommiPer" name="rightCommiPer" 
									value="${commiCustStakeholder.rightCommiPer }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>提成公式</label>
								<input type="text" id="commiExpr" name="commiExpr" style="width:451px"
									value="${commiCustStakeholder.commiExpr }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>提成公式说明</label>
								<input type="text" id="commiExprRemarks" name="commiExprRemarks" style="width:451px"
									value="${commiCustStakeholder.commiExprRemarks }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>数值替换提成公式</label> 
								<input type="text" id="commiExprWithNum" style="width:451px"
							      name="commiExprWithNum" value="${commiCustStakeholder.commiExprWithNum }" />
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
