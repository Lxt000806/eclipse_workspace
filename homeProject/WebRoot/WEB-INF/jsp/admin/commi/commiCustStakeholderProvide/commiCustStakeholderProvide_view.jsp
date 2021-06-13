<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>提成发放比例--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_commiCycle.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#custCode").openComponent_customer({
			showValue:"${commiCustStakeholderProvide.custCode}",
			showLabel:"${commiCustStakeholderProvide.custDescr}",
		});
		$("#role").openComponent_roll({
			showValue:"${commiCustStakeholderProvide.role}",
			showLabel:"${commiCustStakeholderProvide.roleDescr}",
		});
		$("#commiNo").openComponent_commiCycle({
			showValue:"${commiCustStakeholderProvide.commiNo}",
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
					<input type="hidden" name="jsonString" value="" /> <input
						type="hidden" name="m_umState" value="${commiCustStakeholderProvide.m_umState }" />
					<input type="hidden" name="pk" value="${commiCustStakeholderProvide.pk }" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>周期编号</label> 
								<input type="text" id="commiNo"
							      name="commiNo" value="${commiCustStakeholderProvide.commiNo }" />
							</li>
							<li class="form-validate">
								<label>客户编号</label> 
								<input type="text" id="custCode"
							      name="custCode" value="${commiCustStakeholderProvide.custCode }" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>角色</label> 
								<input type="text" id="role"
							      name="role" value="${commiCustStakeholderProvide.role }" />
							</li>
							<li class="form-validate">
								<label>提成发放比例</label>
								<input type="text" id="commiProvidePer" name="commiProvidePer" 
									value="${commiCustStakeholderProvide.commiProvidePer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate">
								<label>补贴发放比例</label>
								<input type="text" id="subsidyProvidePer" name="subsidyProvidePer" 
									value="${commiCustStakeholderProvide.subsidyProvidePer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
							<li class="form-validate">
								<label>倍数发放比例</label>
								<input type="text" id="multipleProvidePer" name="multipleProvidePer" 
									value="${commiCustStakeholderProvide.multipleProvidePer }" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"/>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
