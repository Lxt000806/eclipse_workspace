<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">

</script>
<style>
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
    input[type="number"]{
        -moz-appearance: textfield;
    }
    .col-xs-6 {
    	padding: 0px;
    	width: 676px;
    }
</style>
<div class="body-box-list"><br>
	<div class="validate-group row">
		<div class="col-xs-6">
			<li><label style="width:145px;">套餐主材是否发包</label> <house:xtdm
				id="isSetMainCtrl" dictCode="YESNO" value="${custType.isSetMainCtrl }"
				></house:xtdm>
			</li>
			<li>
				<label style="width:145px;">防水补贴</label>
				<input type="number" id="" name="waterCtrlPri" style="width:160px;" 
					value="${custType.waterCtrlPri }"/>
			</li>
			<li><label style="width:145px;">水电材料发包工人</label> <house:xtdm
					id="isWaterItemCtrl" dictCode="YESNO" value="${custType.isWaterItemCtrl }"></house:xtdm>
			</li>
			<li><label style="width:145px;">是否参与业绩计算</label> <house:xtdm
				id="isCalcPerf" dictCode="YESNO" value="${custType.isCalcPerf }" ></house:xtdm>
			</li>
			<li><label style="width:145px;">达标首付比例</label> <input
				type="number" id="firstPayPer" name="firstPayPer"
				style="width:160px;" value="${custType.firstPayPer }" />
			</li>
			<li><label style="width:145px;">套餐内包含集成</label> <house:xtdm
					id="setContainInt" dictCode="YESNO"
					value="${custType.setContainInt }"></house:xtdm>
			</li>
			<li><label style="width:145px;">基础业绩比例</label> <input
				type="number" id="basePerfPer" name="basePerfPer"
				style="width:160px;" value="${custType.basePerfPer }" />
			</li>
			<li><label style="width:145px;">业绩是否扣基础协议优惠</label> <house:xtdm
					id="isCalcBaseDisc" dictCode="YESNO"
					value="${custType.isCalcBaseDisc }"></house:xtdm>
			</li>
			<li><label style="width:145px;">每平方米材料封顶业绩</label> <input
				type="number" id="maxMaterPerfPer" name="maxMaterPerfPer"
				style="width:160px;" value="${custType.maxMaterPerfPer }"/>
			</li>
			<li><label style="width:145px;">集成套餐内业绩</label> <input
				type="number" id="intPerfAmount_Set" name="intPerfAmount_Set"
				style="width:160px;" value="${custType.intPerfAmount_Set }"/>
			</li>
			<li><label style="width:145px;">橱柜套餐内业绩</label> <input
				type="number" id="cupPerfAmount_Set" name="cupPerfAmount_Set"
				style="width:160px;" value="${custType.cupPerfAmount_Set }"/>
			</li>
			<li><label style="width:145px;">集成套餐内销售额</label> <input type="number"
				id="intSaleAmount_Set" name="intSaleAmount_Set" value="${custType.intSaleAmount_Set }"/>
			</li>
			<li><label style="width:145px;">集成套内金额计算方式</label> <house:xtdm
				id="intSaleAmountCalcType_Set" dictCode="INTAMTCALCTYPE" value="${custType.intSaleAmountCalcType_Set }"></house:xtdm>
			</li>
			<li><label style="width:145px;">橱柜套餐内销售额</label> <input type="number"
					id="cupSaleAmount_Set" name="cupSaleAmount_Set"  value="${custType.cupSaleAmount_Set }"/>
			</li>
			<li><label style="width:145px;">发包方式</label> <house:xtdm
				id="prjCtrlType" dictCode="CustPrjCtrlType" value="${custType.prjCtrlType }"></house:xtdm>
			</li>
			<li>
				<label for="kitchenStdArea" style="width:145px;">厨房（发包）标准面积</label>
				<input type="text" id="kitchenStdArea" name="kitchenStdArea" style="width:160px;" 
					onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
					onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
					value="${custType.kitchenStdArea }"/>
			</li>
			<li>
				<label for="toiletStdArea" style="width:145px;">卫生间（发包）标准面积</label>
				<input type="text" id="toiletStdArea" name="toiletStdArea" style="width:160px;" 
					onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
					onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
					value="${custType.toiletStdArea }"/>
			</li>
			<li>
				<label for="overAreaSubsidyPer" style="width:145px;">厨卫超面积每平米补贴</label>
				<input type="text" id="overAreaSubsidyPer" name="overAreaSubsidyPer" style="width:160px;" 
					onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
					onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
					value="${custType.overAreaSubsidyPer }"/>
			</li>
			<li><label style="width:145px;">是否参与提成计算</label> <house:xtdm
				id="isCalcCommi" dictCode="YESNO" value="${custType.isCalcCommi }"></house:xtdm>
			</li>
		</div>
		<div class="col-xs-6">
			<li><label style="width:145px;">发包价公式</label> <input type="text"
				id="ctrlExpr" name="ctrlExpr" style="width:495px;" value="${custType.ctrlExpr }"/>
			</li>
			<li><label style="width:145px;">指定发包价公式</label> <input
				type="text" id="setCtrlExpr" name="setCtrlExpr" style="width:495px;"
				 value="${custType.setCtrlExpr }"
				 />
			</li>
			<li><label style="width:145px;">材料金额计算公式</label> <input
				type="text" id="materialExpr" name="materialExpr"
				style="width:495px;" value="${custType.materialExpr }"/>
			</li>
			<li><label style="width:145px;">业绩公式</label> 
				<input type="text" id="perfExpr" name="perfExpr" style="width:495px;" value="${custType.perfExpr }"/>
			</li>
			<li><label style="width:145px;">增减业绩公式</label> <input type="text"
				id="perfExpr" name="chgPerfExpr" style="width:495px;"  value="${custType.chgPerfExpr }" />
			</li>
			<li style="max-height: 120px;">
				<label class="control-textarea" style="top: -35px;width:145px;">发包公式说明</label>
				<textarea id="ctrlExprRemarks" name="ctrlExprRemarks" style="height: 50px;width: 495px;">${custType.ctrlExprRemarks}</textarea>
			</li>
			<li style="max-height: 120px;">
				<label class="control-textarea" style="top: -35px;width:145px;">业绩公式说明</label>
				<textarea id="perfExprRemarks" name="perfExprRemarks" style="height: 50px;width: 495px;">${custType.perfExprRemarks}</textarea>
			</li>
			<li style="max-height: 120px;">
				<label class="control-textarea" style="top: -35px;width:145px;">增减业绩公式说明</label>
				<textarea id="chgPerfExprRemarks" name="chgPerfExprRemarks" style="height: 50px;width: 495px;">${custType.chgPerfExprRemarks}</textarea>
			</li>
		</div>
	</div>
</div>
