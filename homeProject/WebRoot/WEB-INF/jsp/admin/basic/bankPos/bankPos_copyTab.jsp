<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(function(){
});
</script>
<style>

</style>
<div class="body-box-list">
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>Pos机名称</label> <input type="text" id="descr"
					name="descr" style="width:160px;" value="${bankPos.Descr}" required
					data-bv-notempty-message="Pos机名称不能为空" />
				</li>
			</div>
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>收款账号</label> <house:dict id="rcvAct" dictCode=""
						sql="select Code, rtrim(Code)+' '+Descr fd from tRcvAct order by Code "
						sqlValueKey="Code" sqlLableKey="fd" value="${bankPos.RcvAct}">
					</house:dict>
				</li>
			</div>
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>Pos机终端号</label> <input type="text" id="posId"
					name="posId" style="width:160px;" value="${bankPos.PosID}" required
					data-bv-notempty-message="Pos机终端号不能为空" />
				</li>
			</div>
			<div class="validate-group row">
			<li ><label style="width:200px;">商户号</label>  <input type="text" id="compCode" name="compCode"
						style="width:160px;" value="${bankPos.CompCode}" />
					</li>
			</div>
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>商家名称</label> <input type="text" id="compName"
					name="compName" style="width:160px;" value="${bankPos.CompName}"
					required data-bv-notempty-message="商家名称不能为空" />
				</li>
			</div>
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>最低手续费</label> <input type="text" id="minFee"
					name="minFee" style="width:160px;" value="${bankPos.MinFee}"
					required data-bv-notempty-message="最低手续费不能为空" />
				</li>
			</div>
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>封顶手续费</label> <input type="text" id="maxFee"
					name="maxFee" style="width:160px;" value="${bankPos.MaxFee}"
					required data-bv-notempty-message="封顶手续费不能为空" />
				</li>
			</div>
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>手续费百分比例</label> <input type="text" id="feePerc"
					name="feePerc" style="width:160px;" value="${bankPos.FeePerc}"
					required data-bv-notempty-message="手续费百分比不能为空" />
				</li>
			</div>
			<div class="validate-group row">
				<li class="form-validate"><label style="width:200px;"><span
						class="required">*</span>收单服务费百分比</label> <input type="text"
					id="acquireFeePerc" name="acquireFeePerc" style="width:160px;"
					value="${bankPos.AcquireFeePerc}" required
					data-bv-notempty-message="收单服务费百分比不能为空" />
				</li>
			</div>
			<div class="row">
				<li>
					<label for="cardAttr" style="width:200px;">卡性质</label>
					<house:xtdm id="cardAttr" dictCode="CARDATTR" style="width:160px;" value="${bankPos.CardAttr}"/>
				</li>
			</div>
			<div class="row">
				<li>
					<label style="width:200px;">卡类型</label>
					<house:xtdm id="cardType" dictCode="POSCARDTYPE" style="width:160px;" value="${bankPos.CardType}"/>
				</li>
			</div>
</div>
