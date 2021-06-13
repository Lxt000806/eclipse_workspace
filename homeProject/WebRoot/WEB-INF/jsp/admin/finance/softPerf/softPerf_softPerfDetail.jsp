<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>软装销售明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(function() {
		if($.trim("${map.businessMan }")==""){
			$("#businessMan").val("");
		}
		if($.trim("${map.designMan }")==""){
			$("#designMan").val("");
		}
		if($.trim("${map.buyer }")==""){
			$("#buyer").val("");
		}
		if($.trim("${map.itemCode }")==""){
			$("#buyer").val("");
		}
	});
	</script>
</head>
	<body>
		<div class="body-box-list">
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
					<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
						<ul class="ul-form">
							<li>
								<label>客户名称</label>																					
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${map.custCode }|${map.custdescr }" readonly="true"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${map.address }" readonly="true"/>
							</li>
							<li>
								<label>发放类型</label>
								<input type="text" id="typeDescr" name="typeDescr" style="width:160px;" value="${map.typeDescr }" readonly="true"/>
							</li>
							<li>
								<label>领料单号</label>
								<input type="text" id="iaNo" name="iaNo" style="width:160px;" value="${map.iaNo }" readonly="true"/>
							</li>
							<li>
								<label>材料编号</label>
								<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${map.itemCode }|${map.itemDescr }" readonly="true"/>
							</li>
							<li>
								<label>材料分类</label>
								<input type="text" id="item12Descr" name="item12Descr" style="width:160px;" value="${map.item12Descr }" readonly="true"/>
							</li>
							<li>
								<label>材料类型2</label>
								<input type="text" id="item2Descr" name="item2Descr" style="width:160px;" value="${map.item2Descr }" readonly="true"/>
							</li>
							<li>
								<label>数量</label>
								<input type="text" id="qty" name="qty" style="width:160px;" value="${map.qty }" readonly="true"/>
							</li>
							<li>
								<label>单价</label>
								<input type="text" id="unitPrice" name="unitPrice" style="width:160px;" value="${map.unitPrice }" readonly="true"/>
							</li>
							<li>
								<label>折扣前金额</label>
								<input type="text" id="befLineAmount" name="befLineAmount" style="width:160px;" value="${map.befLineAmount }" readonly="true"/>
							</li>
							<li>
								<label>其他费用</label>
								<input type="text" id="processCost" name="processCost" style="width:160px;" value="${map.processCost }" readonly="true"/>
							</li>
							<li>
								<label>总价</label>
								<input type="text" id="lineAmount" name="lineAmount" style="width:160px;" value="${map.lineAmount }" readonly="true"/>
							</li>
							<li>
								<label>分摊优惠</label>
								<input type="text" id="discCost" name="discCost" style="width:160px;" value="${map.discCost }" readonly="true"/>
							</li>
							<li>
								<label>销售额</label>
								<input type="text" id="perfamount" name="perfamount" style="width:160px;" value="${map.perfamount }" readonly="true"/>
							</li>
							<li>
								<label>成本</label>
								<input type="text" id="cost" name="cost" style="width:160px;" value="${costRight=='1'?map.cost:'***' }" readonly="true"/>
							</li>
							<li>
								<label>总成本</label>
								<input type="text" id="costamount" name="costamount" style="width:160px;" value="${costRight=='1'?map.costamount:'***' }" readonly="true"/>
							</li>
							<li>
								<label>客户结算日期</label>
								<input type="text" id="checkOutDate" name="checkOutDate" style="width:160px;" value="${map.checkOutDate }" readonly="true"/>
							</li>
							<li>
								<label>领料审核日期</label>
								<input type="text" id="confirmDate" name="confirmDate" style="width:160px;" value="${map.confirmDate }" readonly="true"/>
							</li>
							<li>
								<label>下定日期</label>
								<input type="text" id="orderDate" name="orderDate" style="width:160px;" value="${map.orderdate }" readonly="true"/>
							</li>
							<li>
								<label>发放比例</label>
								<input type="text" id="per" name="per" style="width:160px;" value="${map.per }" readonly="true"/>
							</li>
							<li>
								<label>毛利率</label>
								<input type="text" id="profitPer" name="profitper" style="width:160px;" value="${costRight=='1'?map.profitPer:'***' }" readonly="true"/>
							</li>
							<li>
								<label>业务员</label>
								<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${map.businessMan }|${map.businessManDescr }" readonly="true"/>
							</li>
							<li>
								<label>设计师</label>
								<input type="text" id="designMan" name="designMan" style="width:160px;" value="${map.designMan }|${map.designManDescr }" readonly="true"/>
							</li>
							<li>
								<label>设计师部门</label>
								<input type="text" id="dept2Design" name="dept2Design" style="width:160px;" value="${map.dept2Design }" readonly="true"/>
							</li>
							<li>
								<label>买手</label>
								<input type="text" id="buyer" name="buyer" style="width:160px;" value="${map.buyer }|${map.buyerMan }" readonly="true"/>
							</li>
							<li>
								<label>买手部门</label>
								<input type="text" id="dept2Buyer" name="dept2Buyer" style="width:160px;" value="${map.dept2Buyer }" readonly="true"/>
							</li>
							<li>
								<label for="buyer2">买手2</label>
								<input type="text" id="buyer2" name="buyer2" value="${map.buyer2}|${map.buyer2Man}" readonly="true"/>
							</li>
							<li>
								<label>买手2部门</label>
								<input type="text" id="dept2Buyer2" name="dept2Buyer2" style="width:160px;" value="${map.dept2Buyer2 }" readonly="true"/>
							</li>
							<li>
								<label>设计师抽成点数</label>
								<input type="text" id="designManCommiPer" name="designManCommiPer" style="width:160px;" value="${map.designManCommiPer }" readonly="true"/>
							</li>
							<li>
								<label>设计师提成</label>
								<input type="text" id="designPer" name="designPer" style="width:160px;" value="${map.designPer }" readonly="true"/>
							</li>
							<li>
								<label>买手抽成点数</label>
								<input type="text" id="buyerCommiPer" name="buyerCommiPer" style="width:160px;" value="${map.buyerCommiPer }" readonly="true"/>
							</li>
							<li>
								<label>买手提成</label>
								<input type="text" id="buyerPer" name="buyerPer" style="width:160px;" value="${map.buyerPer }" readonly="true"/>
							</li>
							<li>
								<label>买手2抽成点数</label>
								<input type="text" id="buyer2CommiPer" name="buyer2CommiPer" style="width:160px;" value="${map.buyer2CommiPer }" readonly="true"/>
							</li>
							<li>
								<label>买手2提成</label>
								<input type="text" id="buyer2Per" name="buyer2Per" style="width:160px;" value="${map.buyer2Per }" readonly="true"/>
							</li>
							<li>
								<label>业务员抽成点数</label>
								<input type="text" id="businessManCommiPer" name="businessManCommiPer" style="width:160px;" value="${map.businessManCommiPer }" readonly="true"/>
							</li>
							<li>
								<label>业务员提成</label>
								<input type="text" id="businessManCommi" name="businessManCommi" style="width:160px;" value="${map.businessManCommi }" readonly="true"/>
							</li>
							<li>
								<label>翻单员抽成点数</label>
								<input type="text" id="againManCommiPer" name="againManCommiPer" style="width:160px;" value="${map.againManCommiPer }" readonly="true"/>
							</li>
							<li>
								<label>翻单员提成</label>
								<input type="text" id="againManCommi" name="againManCommi" style="width:160px;" value="${map.againManCommi }" readonly="true"/>
							</li>
							<li>
								<label>产品经理抽成点数</label>
								<input type="text" id="prodMgrCommiPer" name="prodMgrCommiPer" style="width:160px;" value="${map.prodMgrCommiPer }" readonly="true"/>
							</li>
							<li>
								<label>产品经理提成</label>
								<input type="text" id="prodMgrCommi" name="prodMgrCommi" style="width:160px;" value="${map.prodMgrCommi }" readonly="true"/>
							</li>
							<li>
								<label>材料需求备注</label>
								<input type="text" id="itemReqRemark" name="itemReqRemark" style="width:451px;" value="${map.itemReqRemark }" readonly="true"/>
							</li>
						</ul>
					</form>	
				</div>
			</div>
		</div>
	</body>	
</html>
