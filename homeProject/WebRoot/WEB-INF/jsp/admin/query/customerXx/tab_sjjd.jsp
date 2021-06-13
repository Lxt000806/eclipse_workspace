<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.query-form .ul-form li,.edit-form .ul-form li {
	width: 350px;
}
</style>
<script type="text/javascript">
<!--
$(function(){
	$("#page_form_sjjd").setTable();
});
//-->
</script>
<div class="body-box-list">
	<div class="edit-form">
		<form action="" method="post" id="page_form_sjjd" >
			<table cellpadding="0" cellspacing="0" width="100%">
					<col  width="200" />
					<col  width="40%"/>
					<col  width="200"/>
					<col  width="40%"/>
				<tbody>
					<tr class="td-btn">
						<td class="td-label">计划下定</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planSet" name="planSet" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planSet }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">下定时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="setDate" name="setDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.setDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
					<tr class="td-btn">
						<td class="td-label">计划量房</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planMeasure" name="planMeasure" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planMeasure }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">量房时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="measureDate" name="measureDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.measureDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
					<tr class="td-btn">
						<td class="td-label">计划平面</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planPlane" name="planPlane" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planPlane }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">平面时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planeDate" name="planeDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planeDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
					<tr class="td-btn">
						<td class="td-label">计划立面</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planFacade" name="planFacade" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planFacade }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">立面时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="facadeDate" name="facadeDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.facadeDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
					<tr class="td-btn">
						<td class="td-label">计划报价</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planPrice" name="planPrice" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planPrice }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">报价时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="priceDate" name="priceDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.priceDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
					<tr class="td-btn">
						<td class="td-label">计划效果图</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planDraw" name="planDraw" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planDraw }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">效果图时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="drawDate" name="drawDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.drawDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
					<tr class="td-btn">
						<td class="td-label">计划放样</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planPrev" name="planPrev" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planPrev }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">放样时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="prevDate" name="prevDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.prevDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
					<tr class="td-btn">
						<td class="td-label">计划签合同</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="planSign" name="planSign" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.planSign }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
						<td class="td-label">合同签订时间</td>
						<td class="td-value">
						<input type="text" style="width:160px;" id="signDate" name="signDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.signDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
