<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/workCostDetail/workTypeByAuthority","workType1","workType2");
	Global.LinkSelect.setSelect({firstSelect:'workType1',
								firstValue:"${workCon.workType1}",
								secondSelect:'workType2',
								secondValue:"${workCon.workType2}"
								});	
	$("#custCode").openComponent_customer({
		showLabel:"${workCon.custDescr}",
		showValue:"${workCon.custCode}"
	});
	$("#saveBtn").attr("disabled",true);
	disabledForm("dataForm");
});

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						<house:token></house:token>
						<ul class="ul-form">
							<li><label style="color:blue">客户编号</label> <input type="text" id="custCode"
								name="custCode" value="${workCon.custCode}" /> <span
								id="address"
								style="position: absolute;left:295px;width: 150px;top:3px;">${workCon.address}</span>
							</li>
							<li><label style="color:blue">工人姓名</label> <input type="text" id="workName"
								name="workName" value="${workCon.workName}" />
							</li>
							<li><label style="color:blue">工种分类1</label> <select id="workType1"
								name="workType1"></select>
							</li>
							<li><label style="color:blue">工种分类2</label> <select id="workType2"
								name="workType2"></select>
							</li>
							<li><label style="color:blue">金额</label> <input type="text" id="amount"
								name="amount" value="${workCon.amount}"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
							</li>
							<li><label style="color:blue">面积</label> <input type="text" id="area"
								name="area" value="${workCon.area}"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
							</li>
							<li><label style="color:blue">施工工期</label> <input type="text" id="constructDay"
								name="constructDay"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
								value="${workCon.constructDay}" />
							</li>
							<li><label class="control-textarea">备注</label> <textarea
									id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:60px; width:250px" />${workCon.remarks
								}</textarea>
							</li>
							<li><label>过期</label> <input type="checkbox"
									id="expired" name="expired" value="${workCon.expired }"
									${workCon.expired!='F' ?'checked':'' } onclick="checkExpired(this)">
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
