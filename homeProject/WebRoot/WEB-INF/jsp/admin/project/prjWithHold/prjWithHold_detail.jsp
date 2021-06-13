 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加CustLoan</title>
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
	Global.LinkSelect.initSelect("${ctx}/admin/workType1/workTypeAll","workType1","workType2");
	Global.LinkSelect.setSelect({firstSelect:'workType1',
								firstValue:'${prjWithHold.workType1}',
								secondSelect:'workType2',
								secondValue:'${prjWithHold.workType2}'
	});														
	$("#custCode").openComponent_customer({showLabel:"${prjWithHold.custDescr}",showValue:"${prjWithHold.custCode}" ,readonly:"true"});
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
							<li class="form-validate"><label style="color:#777777"><span class="required">*</span>客户编号</label> <input type="text"
								id="custCode" name="custCode" value="${prjWithHold.custCode}" />
							</li>
							<li class="form-validate"><label style="color:#777777">楼盘</label> <input type="text" id="address"
								name="address" value="${prjWithHold.address}" readonly="readonly" />
							</li>
						</div>
						
						<div class="validate-group">
							<li class="form-validate"><label>工种分类1</label> 
								<select id="workType1" name="workType1"  ></select>
							</li>
							<li class="form-validate"><label>工种分类2</label> 
								<select id="workType2" name="workType2"  ></select>
							</li>
						</div>
						<div class="validate-group">
							<li class="validate-group"><label>类型</label>
									<house:xtdm  id="type" dictCode="PrjWithHoldType"   value="${prjWithHold.type}"></house:xtdm>
							</li>
							<li class="form-validate"><label>金额</label> <input type="text" id="amount"
								name="amount"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${prjWithHold.amount}" />
							</li>
						</div>
						<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="remarks"
								name="remarks">${prjWithHold.remarks}</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

