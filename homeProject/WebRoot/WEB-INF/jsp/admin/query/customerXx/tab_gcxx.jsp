<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(function(){
	//解决为null时显示NaN
	if("${customerProjectMap.iswaterctrl}".replace(/ /g,'').length==0){
		$("#iswaterctrldescr").val("");
	}
	if("${customerProjectMap.projectman}".replace(/ /g,'').length==0){
		$("#projectmandescr").val("");
	}
	if("${customerProjectMap.checkman}".replace(/ /g,'').length==0){
		$("#checkmandescr").val("");
	}
});
</script>
<div class="body-box-list">
	<div class="panel">  
	    <div class="panel-body" style="height: 450px;">
	    <form role="form" class="form-search" id="page_form_gcxx" >
			<ul class="ul-form">
					<div style="display: none">
					<li>
						<label>客户名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;" value="${customerProjectMap.descr }" readonly="readonly"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customerProjectMap.address }" readonly="readonly"/>
					</li>
					<li>
						<label>管理费预算</label>
						<input type="text" id="managefee" name="managefee" style="width:160px;" value="${customerProjectMap.managefee}" readonly="readonly"/>
					</li>
					<li>
						<label>设计师</label>
						<input type="text" id="designmandescr" name="designmandescr" style="width:160px;" value="${customerProjectMap.designmandescr }" readonly="readonly"/>
					</li>
					<li>
						<label>设计费</label>
						<input type="text" id="designfee" name="designfee" style="width:160px;" value="${customerProjectMap.designfee }" readonly="readonly"/>
					</li>
					<li>
						<label>主材合同额</label>
						<input type="text" id="zchtfee" name="zchtfee" style="width:160px;" value="${customerProjectMap.zchtfee}" readonly="readonly"/>
					</li>
					<li>
						<label>直接费预算</label>
						<input type="text" id="basefee_dirct" name="basefee_dirct" style="width:160px;" value="${customerProjectMap.basefee_dirct }" readonly="readonly"/>
					</li>
					<li>
						<label>基础合同额</label>
						<input type="text" id="jzhtfee" name="jzhtfee" style="width:160px;" value="${customerProjectMap.jzhtfee }" readonly="readonly"/>
					</li>
					<li>
						<label>服务性费用</label>
						<input type="text" id="mainservfee" name="mainservfee" style="width:160px;" value="${customerProjectMap.mainservfee}" readonly="readonly"/>
					</li>
					<li>
						<label>软装合同额</label>
						<input type="text" id="rzhtfee" name="rzhtfee" style="width:160px;" value="${customerProjectMap.rzhtfee }" readonly="readonly"/>
					</li>
					<li>
						<label>集成合同额</label>
						<input type="text" id="jchtfee" name="jchtfee" style="width:160px;" value="${customerProjectMap.jchtfee }" readonly="readonly"/>
					</li>
					<li>
						<label>户型</label>
						<input type="text" id="layoutdescr" name="layoutdescr" style="width:160px;" value="${customerProjectMap.layoutdescr}" readonly="readonly"/>
					</li>
					<li>
						<label>工程总造价</label>
						<input type="text" id="contractfee" name="contractfee" style="width:160px;" value="${customerProjectMap.contractfee }" readonly="readonly"/>
					</li>
					<li>
						<label>施工方式</label>
						<input type="text" id="constructtypedescr" name="constructtypedescr" style="width:160px;" value="${customerProjectMap.constructtypedescr }" readonly="readonly"/>
					</li>
					</div>
					<li>
						<label>当前实际进度</label>
						<input type="text" id="nowjd" name="nowjd" style="width:160px;" value="${customerProjectMap.nowjd}" readonly="readonly"/>
					</li>
					<li style="display: none;">
						<label>面积</label>
						<input type="text" id="area" name="area" style="width:160px;" value="${customerProjectMap.area }" readonly="readonly"/>
					</li>
					<li>
						<label>计划进度</label>
						<input type="text" id="planjd" name="planjd" style="width:160px;" value="${customerProjectMap.planjd }" readonly="readonly"/>
					</li>
					<div style="display: none">
					<li>
						<label>二批付款</label>
						<input type="text" id="secondpay" name="secondpay" style="width:160px;" value="${customerProjectMap.secondpay}" readonly="readonly"/>
					</li>
					<li>
						<label>已付款</label>
						<input type="text" id="haspay" name="haspay" style="width:160px;" value="${customerProjectMap.haspay }" readonly="readonly"/>
					</li>
					<li>
						<label>首付款</label>
						<input type="text" id="firstpay" name="firstpay" style="width:160px;" value="${customerProjectMap.firstpay }" readonly="readonly"/>
					</li>
					<li>
						<label>增减项合计</label>
						<input type="text" id="zjxhj" name="zjxhj" style="width:160px;" value="${customerProjectMap.zjxhj}" readonly="readonly"/>
					</li>
					<li>
						<label>三批付款</label>
						<input type="text" id="thirdpay" name="thirdpay" style="width:160px;" value="${customerProjectMap.thirdpay }" readonly="readonly"/>
					</li>
					<li>
						<label>尾款</label>
						<input type="text" id="fourpay" name="fourpay" style="width:160px;" value="${customerProjectMap.fourpay }" readonly="readonly"/>
					</li>
					</div>
					<li>
						<label>项目经理结算金额</label>
						<input type="text" id="xmjljsje" name="xmjljsje" style="width:160px;" value="${customerProjectMap.xmjljsje}" readonly="readonly"/>
					</li>
					<li style="display: none;">
						<label>基础增减</label>
						<input type="text" id="jzzj" name="jzzj" style="width:160px;" value="${customerProjectMap.jzzj }" readonly="readonly"/>
					</li>
					<li>
						<label>客户结算金额</label>
						<input type="text" id="khjsje" name="khjsje" style="width:160px;" value="${customerProjectMap.khjsje }" readonly="readonly"/>
					</li>
					<li style="display: none;">
						<label>客户来源</label>
						<input type="text" id="sourcedescr" name="sourcedescr" style="width:160px;" value="${customerProjectMap.sourcedescr}" readonly="readonly"/>
					</li>
					<li>
						<label>工程经理结算金额</label>
						<input type="text" id="gcjljsje" name="gcjljsje" style="width:160px;" value="${customerProjectMap.gcjljsje }" readonly="readonly"/>
					</li>
					<li>
						<label>业务跟单人员</label>
						<input type="text" id="businessmandescr" name="businessmandescr" style="width:160px;" value="${customerProjectMap.businessmandescr }" readonly="readonly"/>
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectmandescr" name="projectmandescr" style="width:160px;" value="${customerProjectMap.projectman}  ${customerProjectMap.projectmandescr }" readonly="readonly"/>
					</li>
					<li>
						<label>工程部</label>
						<input type="text" id="department2descr" name="department2descr" style="width:160px;" value="${customerProjectMap.department2descr }" readonly="readonly"/>
					</li>
					<li>
						<label>巡检员</label>
						<input type="text" id="checkmandescr" name="checkmandescr" style="width:160px;" value="${customerProjectMap.checkman}  ${customerProjectMap.checkmandescr}" readonly="readonly"/>
					</li>
					<li>
						<label>工程接收日期</label>
						<input type="text" style="width:160px;" id="consrcvdate" name="consrcvdate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.consrcvdate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>派单通知</label>
						<input type="text" style="width:160px;" id="sendjobdate" name="sendjobdate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.sendjobdate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>开工令时间</label>
						<input type="text" style="width:160px;" id="begincomdate" name="begincomdate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.begincomdate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>合同开工日期</label>
						<input type="text" style="width:160px;" id="signdate" name="signdate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.signdate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>实际开工日期</label>
						<input type="text" style="width:160px;" id="confirmbegin" name="confirmbegin" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.confirmbegin }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>施工工期</label>
						<input type="text" id="constructday" name="constructday" style="width:160px;" value="${customerProjectMap.constructday}" readonly="readonly"/>
						<span style="position: absolute;left:285px;width: 30px;top:3px;">天</span>
					</li>
					<li>
						<label>施工状态</label>
						<input type="text" id="constructstatusdescr" name="constructstatusdescr" style="width:160px;" value="${customerProjectMap.constructstatus }  ${customerProjectMap.constructstatusdescr }" readonly="readonly"/>
					</li>
					<li>
						<label>竣工日期</label>
						<input type="text" style="width:160px;" id="consenddate" name="consenddate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.consenddate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>拖期天数</label>
						<input type="text" id="delayday" name="delayday" style="width:160px;" value="${customerProjectMap.delayday}" readonly="readonly"/>
						<span style="position: absolute;left:285px;width: 30px;top:3px;">天</span>
					</li>
					<li>
						<label>预计结算日期</label>
						<input type="text" style="width:160px;" id="plancheckout" name="plancheckout" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.plancheckout }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>实际结算日期</label>
						<input type="text" style="width:160px;" id="checkoutdate" name="checkoutdate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customerProjectMap.checkoutdate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
					</li>
					<li>
						<label>是否客诉</label>
						<input type="text" id="iscomplaindescr" name="iscomplaindescr" style="width:160px;" value="${customerProjectMap.iscomplain}  ${customerProjectMap.iscomplaindescr}" readonly="readonly"/>
					</li>
					<li>
						<label>片区</label>
						<input type="text" id="consarea" name="consarea" style="width:160px;" value="${customerProjectMap.consarea }" readonly="readonly"/>
					</li>
					<li>
						<label>防水是否发包</label>
						<input type="text" id="iswaterctrldescr" name="iswaterctrldescr" style="width:160px;" value="${customerProjectMap.iswaterctrl}  ${customerProjectMap.iswaterctrldescr}" readonly="readonly"/>
					</li>
					<li>
						<label>水电发包工人</label>
						<input type="text" id="iswateritemctrldescr" name="iswateritemctrldescr" style="width:160px;" value="${customerProjectMap.iswateritemctrl}  ${customerProjectMap.iswateritemctrldescr}" readonly="readonly"/>
					</li>
				</ul>
					
				</tbody>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#page_form_gcxx input[type='text']").each(function(){
		if ($(this).val() && !isNaN($(this).val())){
			$(this).val(parseFloat($(this).val()));
		}
	});
});
</script>
