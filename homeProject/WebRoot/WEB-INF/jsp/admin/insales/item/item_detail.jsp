<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    	<title>Item明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">

$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});


</script>
</head>
<body>
<div class="body-box-form">
<div class="content-form">
	<!--panelBar-->
	<div class="panelBar">
		<ul>
			<li id="closeBut">
				<a href="javascript:void(0)" class="a2" onclick="closeWin(false)">
					<span>关闭</span>
				</a>
			</li>
			<li class="line"></li>
		</ul>
		<div class="clear_float"> </div>
	</div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm" enctype="multipart/form-data">
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>编号</td>
							<td class="td-value">
${item.code}							</td>
							<td class="td-label"><span class="required">*</span>名称</td>
							<td class="td-value">
${item.descr}							</td>
						</tr>
						<tr>	
							<td class="td-label">助记码</td>
							<td class="td-value">
${item.remCode}							</td>
							<td class="td-label"><span class="required">*</span>材料类型1</td>
							<td class="td-value">
${item.itemType1}							</td>
						</tr>
						<tr>	
							<td class="td-label">材料类型2</td>
							<td class="td-value">
${item.itemType2}							</td>
							<td class="td-label">材料类型3</td>
							<td class="td-value">
${item.itemType3}							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>条码</td>
							<td class="td-value">
${item.barCode}							</td>
							<td class="td-label">品牌</td>
							<td class="td-value">
${item.sqlCode}							</td>
						</tr>
						<tr>	
							<td class="td-label">供应商代码</td>
							<td class="td-value">
${item.supplCode}							</td>
							<td class="td-label">型号</td>
							<td class="td-value">
${item.model}							</td>
						</tr>
						<tr>	
							<td class="td-label">规格</td>
							<td class="td-value">
${item.itemSize}							</td>
							<td class="td-label">规格说明</td>
							<td class="td-value">
${item.sizeDesc}							</td>
						</tr>
						<tr>	
							<td class="td-label">颜色</td>
							<td class="td-value">
${item.color}							</td>
							<td class="td-label">单位</td>
							<td class="td-value">
${item.uom}							</td>
						</tr>
						<tr>	
							<td class="td-label">进价</td>
							<td class="td-value">
${item.cost}							</td>
							<td class="td-label">市场价</td>
							<td class="td-value">
${item.marketPrice}							</td>
						</tr>
						<tr>	
							<td class="td-label">现价</td>
							<td class="td-value">
${item.price}							</td>
							<td class="td-label">是否固定价</td>
							<td class="td-value">
${item.isFixPrice}							</td>
						</tr>
						<tr>	
							<td class="td-label">提成类型</td>
							<td class="td-value">
${item.commiType}							</td>
							<td class="td-label">提成比例</td>
							<td class="td-value">
${item.commiPerc}							</td>
						</tr>
						<tr>	
							<td class="td-label">材料描述</td>
							<td class="td-value">
${item.remark}							</td>
							<td class="td-label">LastUpdate</td>
							<td class="td-value">
${item.lastUpdate}							</td>
						</tr>
						<tr>	
							<td class="td-label">LastUpdatedBy</td>
							<td class="td-value">
${item.lastUpdatedBy}							</td>
							<td class="td-label">Expired</td>
							<td class="td-value">
${item.expired}							</td>
						</tr>
						<tr>	
							<td class="td-label">ActionLog</td>
							<td class="td-value">
${item.actionLog}							</td>
							<td class="td-label">显示顺序</td>
							<td class="td-value">
${item.dispSeq}							</td>
						</tr>
						<tr>	
							<td class="td-label">移动平均成本</td>
							<td class="td-value">
${item.avgCost}							</td>
							<td class="td-label">所有库存数量</td>
							<td class="td-value">
${item.allQty}							</td>
						</tr>
						<tr>	
							<td class="td-label">项目经理结算价</td>
							<td class="td-value">
${item.projectCost}							</td>
							<td class="td-label">是否套餐材料</td>
							<td class="td-value">
${item.isSetItem}							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>

