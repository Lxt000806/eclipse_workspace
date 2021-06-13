<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	
});
</script>
<style>
#page_form_hhhtxx_htxx .ul-form li {
	width: 330px;
}
#page_form_hhhtxx_htxx .ul-form li label {
    width: 130px;
}
</style>
<div class="body-box-list">
	<div class="edit-form">
		<form role="form" class="form-search" id="page_form_hhhtxx_htxx"  action="" method="post" target="targetFrame">
            	<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
					<ul class="ul-form">
						<div class="col-sm-3">
						<li>
						<label>工程造价</label>
						<input type="text" id="contractfee" name="contractfee" style="width:160px;" value="${customerMap.contractfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>管理费</label>
						<input type="text" id="managefee" name="managefee" style="width:160px;" value="${customerMap.managefee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>设计费</label>
						<input type="text" id="designfee" name="designfee" style="width:160px;" value="${customerMap.designfee}" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>税金</label>
						<input type="text" id="tax" name="tax" style="width:160px;" value="${customerMap.tax }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>预估毛利额</label>
						<input type="text" id="ygmle" name="ygmle" style="width:160px;" value="${customerMap.ygmle }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>量房费</label>
						<input type="text" id="measurefee" name="measurefee" style="width:160px;" value="${customerMap.measurefee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>制图费</label>
						<input type="text" id="drawfee" name="drawfee" style="width:160px;" value="${customerMap.drawfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>效果图费</label>
						<input type="text" id="colordrawfee" name="colordrawfee" style="width:160px;" value="${customerMap.colordrawfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>综合毛利率</label>
						<input type="text" id="zhmll" name="zhmll" style="width:160px;" value="${customerMap.zhmll }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>主材费</label>
						<input type="text" id="mainfee" name="mainfee" style="width:160px;" value="${customerMap.mainfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>主材优惠</label>
						<input type="text" id="maindisc" name="maindisc" style="width:160px;" value="${customerMap.maindisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>主材费变更</label>
						<input type="text" id="chgmainfee" name="chgmainfee" style="width:160px;" value="${customerMap.chgmainfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>设计毛利/平方</label>
						<input type="text" id="dwsjf" name="dwsjf" style="width:160px;" value="${customerMap.dwsjf }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>软装费</label>
						<input type="text" id="softfee" name="softfee" style="width:160px;" value="${customerMap.softfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>软装优惠</label>
						<input type="text" id="softdisc" name="softdisc" style="width:160px;" value="${customerMap.softdisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>软装费变更</label>
						<input type="text" id="chgsoftfee" name="chgsoftfee" style="width:160px;" value="${customerMap.chgsoftfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>基础毛利/平方</label>
						<input type="text" id="dwjzf" name="dwjzf" style="width:160px;" value="${customerMap.dwjzf }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>集成费</label>
						<input type="text" id="integratefee" name="integratefee" style="width:160px;" value="${customerMap.integratefee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>集成优惠</label>
						<input type="text" id="integratedisc" name="integratedisc" style="width:160px;" value="${customerMap.integratedisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>集成费变更</label>
						<input type="text" id="chgintfee" name="chgintfee" style="width:160px;" value="${customerMap.chgintfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>主材毛利/平方</label>
						<input type="text" id="dwzcf" name="dwzcf" style="width:160px;" value="${customerMap.dwzcf }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>橱柜费</label>
						<input type="text" id="cupboardfee" name="cupboardfee" style="width:160px;" value="${customerMap.cupboardfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>橱柜优惠</label>
						<input type="text" id="cupboarddisc" name="cupboarddisc" style="width:160px;" value="${customerMap.cupboarddisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>橱柜费变更</label>
						<input type="text" id="chgcupfee" name="chgcupfee" style="width:160px;" value="${customerMap.chgcupfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>软装毛利/平方</label>
						<input type="text" id="dwrzf" name="dwrzf" style="width:160px;" value="${customerMap.dwrzf }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>基础费</label>
						<input type="text" id="basefee" name="basefee" style="width:160px;" value="${customerMap.basefee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>基础协议优惠</label>
						<input type="text" id="basedisc" name="basedisc" style="width:160px;" value="${customerMap.basedisc }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>基础费变更</label>
						<input type="text" id="chgbasefee" name="chgbasefee" style="width:160px;" value="${customerMap.chgbasefee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>集成毛利/平方</label>
						<input type="text" id="dwjcf" name="dwjcf" style="width:160px;" value="${customerMap.dwjcf }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>基础直接费</label>
						<input type="text" id="basefee_dirct" name="basefee_dirct" style="width:160px;" value="${customerMap.basefee_dirct }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>基础综合费</label>
						<input type="text" id="basefee_comp" name="basefee_comp" style="width:160px;" value="${customerMap.basefee_comp }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>计划签合同</label>
						<input type="text" style="width:160px;" id="plansign" name="plansign" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerMap.plansign }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>橱柜毛利/平方</label>
						<input type="text" id="dwcgf" name="dwcgf" style="width:160px;" value="${customerMap.dwcgf }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>服务产品费</label>
						<input type="text" id="mainservfee" name="mainservfee" style="width:160px;" value="${customerMap.mainservfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>服务产品变更</label>
						<input type="text" id="chgmainservfee" name="chgmainservfee" style="width:160px;" value="${customerMap.chgmainservfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>合同签订时间</label>
						<input type="text" style="width:160px;" id="signdate" name="signdate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerMap.signdate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>合计毛利/平方</label>
						<input type="text" id="dwhjf" name="dwhjf" style="width:160px;" value="${customerMap.dwhjf }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>物料人工耗用</label>
						<input type="text" id="materialfee" name="materialfee" style="width:160px;" value="${customerMap.materialfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>主材费(按比例提成)</label>
						<input type="text" id="mainfee_per" name="mainfee_per" style="width:160px;" value="${customerMap.mainfee_per }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-6">
						<li>
						<label>主材变更(按比例提成)</label>
						<input type="text" id="chgmainfee_per" name="chgmainfee_per" style="width:160px;" value="${customerMap.chgmainfee_per }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>实物赠送</label>
						<input type="text" id="gift" name="gift" style="width:160px;" value="${customerMap.gift }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>软装其他费用</label>
						<input type="text" id="softother" name="softother" style="width:160px;" value="${customerMap.softother }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-6">
						<li>
						<label>业绩扣除数</label>
						<input type="text" id="achievded" name="achievded" style="width:160px;" value="${customerMap.achievded }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>设计费变更</label>
						<input type="text" id="chgdesignfee" name="chgdesignfee" style="width:160px;" value="${customerMap.chgdesignfee }" readonly="readonly"/>
						</li>
						</div>
						
						<div class="col-sm-3">
						<li>
						<label>管理费变更</label>
						<input type="text" id="chgmanagefee" name="chgmanagefee" style="width:160px;" value="${customerMap.chgmanagefee }" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-3">
							<li>
								<label>实物赠送变更</label>
								<input type="text" id="chggift" name="chggift" style="width:160px;" value="${customerMap.chggift }" readonly="readonly"/>
							<li>
						</div>
						<c:if test="${customer.isInitSign=='0'}">
							<div class="col-sm-3">
								<li>
									<label>软装券</label>
									<input type="text" id="softtokenno" name="softtokenno" style="width:160px;" value="${customerMap.softtokenno }" readonly="readonly"/>
								</li>
							</div>
							<div class="col-sm-3">
								<li>
									<label>软装券金额</label>
									<input type="text" id="softTokenAmount" name="softTokenAmount" style="width:160px;" value="${customerMap.softtokenamount }" readonly="readonly"/>
								</li>
							</div>
						</c:if>
					</ul>
		</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#page_form_hhhtxx_htxx input[type='text']").each(function(){
		if ($(this).val() && !isNaN($(this).val())){
			$(this).val(parseFloat($(this).val()));
		}
	});
});
</script>
