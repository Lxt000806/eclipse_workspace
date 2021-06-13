<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
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
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custcode"
							name="custcode" style="width:160px;" readonly="readonly"
							value="${map.custcode}">
						</li>
						<li><label>楼盘地址</label> <input type="text" id="address"
							name="address" style="width:160px;" readonly="readonly"
							value="${map.address}">
						</li>
						<li><label>操作员编号</label> <input type="text" id="appczy"
							name="appczy" style="width:160px;" readonly="readonly"
							value="${map.appczy}">
						</li>
						<li><label>状态</label> <input type="text" id="statusdescr"
							name="statusdescr" style="width:160px;" readonly="readonly"
							value="${map.statusdescr}">
						</li>
						<li><label>类型</label> <input type="text"
							id="typedescr" name="typedescr" style="width:160px;"
							readonly="readonly" value="${map.typedescr}">
						</li>
						<li><label>付款日期</label> <input type="text" id="date"
							name="date" style="width:160px;" readonly="readonly"
							value="${map.date}">
						</li>
						<li><label>交易总金额</label> <input type="text" id="tranamount"
							name="tranamount" style="width:160px;" readonly="readonly"
							value="${map.tranamount}">
						</li>

						<li><label>实际扣款金额</label> <input type="text" id="payamount"
							name="payamount" style="width:160px;" readonly="readonly"
							value="${map.payamount}" />
						</li>
						<li><label>收款账号</label> <input type="text" id="rcvact"
							name="rcvact" style="width:160px;" readonly="readonly"
							value="${map.rcvact}">
						</li>
						<li><label>手续费</label> <input type="text"
							id="procedurefee" name="procedurefee" style="width:160px;"
							readonly="readonly" value="${map.procedurefee}">
						</li>
						<li><label>登记日期</label> <input type="text" id="adddate"
							name="adddate" style="width:160px;" readonly="readonly"
							value="${map.adddate}">
						</li>
						<li><label>收款单号</label> <input type="text" id="payno"
							name="payno" style="width:160px;" readonly="readonly"
							value="${map.payno}">
						</li>
						<li><label>卡号</label> <input type="text" id="cardid"
							name="cardid" style="width:160px;" readonly="readonly"
							value="${map.cardid}">
						</li>
						<li><label>发卡行编码</label> <input type="text" id="bankcode"
							name="bankcode" style="width:160px;" readonly="readonly"
							value="${map.bankcode}">
						</li>
						<li><label>发卡行名称</label> <input type="text" id="bankname"
							name="bankname" style="width:160px;" readonly="readonly"
							value="${map.bankname}">
						</li>
						<li><label>凭证号</label> <input type="text" id="traceno"
							name="traceno" style="width:160px;" readonly="readonly"
							value="${map.traceno}">
						</li>
						<li><label>参考号</label> <input type="text" id="referno"
							name="referno" style="width:160px;" readonly="readonly"
							value="${map.referno}">
						</li>
						<li><label>客户签名</label> <input type="text" id="issigndescr"
							name="issigndescr" style="width:160px;" readonly="readonly"
							value="${map.issigndescr}">
						</li>
						<li><label>最后打印人员</label> <input type="text"
							id="printczy" name="printczy" style="width:160px;"
							readonly="readonly" value="${map.printczy}">
						</li>
						<li><label>最后打印时间</label> <input type="text"
							id="printdate" name="printdate"
							style="width:160px;" readonly="readonly"
							value="${map.printdate}">
						</li>
						<li><label>异常说明</label> <input type="text" id="exceptionremarks"
							name="exceptionremarks" style="width:451px;" readonly="readonly"
							value="${map.exceptionremarks}">
						</li>
						<li>
							<label>卡性质</label> 
							<house:xtdm id="cardAttr" dictCode="CARDATTR" style="width:160px;" value="${map.cardattr}"/>
						</li>
						<li>
							<label>付款方式</label> 
							<house:xtdm id="payType" dictCode="POSPAYTYPE" style="width:160px;" value="${map.paytype}"/>
						</li>
						<li  class="form-validate" style="max-height: 200px;">
							<label class="control-textarea" style="top: -90px;">重打说明</label>
							<textarea id="reprintRemarks" name="reprintRemarks" style="height: 112px;width: 451px;">${map.reprintremarks}</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"> 
</script>
</html>
