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
		<li><label style="width:145px;">基础综合费系数</label> <input
			type="number" id="baseFeeCompPer" name="baseFeeCompPer"
			style="width:160px;" value="${custType.baseFeeCompPer }" />
		</li>
		<li><label style="width:145px;">基础直接费系数</label> <input
			type="number" id="baseFeeDirctPer" name="baseFeeDirctPer"
			style="width:160px;" value="${custType.baseFeeDirctPer }" />
		</li>
		<li><label style="width:145px;">主套餐费系数</label> <input
			type="number" id="mainSetFeePer" name="mainSetFeePer"
			style="width:160px;" value="${custType.mainSetFeePer }" />
		</li>
		<li><label style="width:145px;">套餐内减项系数</label> <input
			type="number" id="setMinusPer" name="setMinusPer"
			style="width:160px;" value="${custType.setMinusPer }" />
		</li>
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">套餐外基础增项系数</label> <input
			type="number" id="setAddPer" name="setAddPer" style="width:160px;"
			value="${custType.setAddPer }" />
		</li>
		<li><label style="width:145px;">远程费系数</label> <input
			type="number" id="longFeePer" name="longFeePer" style="width:160px;"
			value="${custType.longFeePer }" />
		</li>
		<li><label style="width:145px;">基础管理费系数</label> <input
			type="number" id="manageFeeBasePer" name="manageFeeBasePer"
			style="width:160px;" value="${custType.manageFeeBasePer }" />
		</li>
		<li><label style="width:145px;">主材费系数</label> <input
			type="number" id="mainFeePer" name="mainFeePer" style="width:160px;"
			value="${custType.mainFeePer }" />
		</li>
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">主材优惠系数</label> <input
			type="number" id="mainDiscPer" name="mainDiscPer"
			style="width:160px;" value="${custType.mainDiscPer }" />
		</li>
		<li><label style="width:145px;">主材管理费系数</label> <input
			type="number" id="manageFeeMainPer" name="manageFeeMainPer"
			style="width:160px;" value="${custType.manageFeeMainPer }" />
		</li>
		<li><label style="width:145px;">服务性产品费（主材）系数</label> <input
			type="number" id="mainServFeePer" name="mainServFeePer"
			style="width:160px;" value="${custType.mainServFeePer }" />
		</li>
		<li><label style="width:145px;">服务性产品管理费系数</label> <input
			type="number" id="manageFeeServPer" name="manageFeeServPer"
			style="width:160px;" value="${custType.manageFeeServPer }" />
		</li>
		
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">软装费系数</label> <input
			type="number" id="softFeePer" name="softFeePer" style="width:160px;"
			value="${custType.softFeePer }" />
		</li>
		<li><label style="width:145px;">软装优惠系数</label> <input
			type="number" id="softDiscPer" name="softDiscPer"
			style="width:160px;" value="${custType.softDiscPer }" />
		</li> 
		<li><label style="width:145px;">软装管理费系数</label> <input
			type="number" id="manageFeeSoftPer" name="manageFeeSoftPer"
			style="width:160px;" value="${custType.manageFeeSoftPer }" />
		</li>
		<li><label style="width:145px;">集成费系数</label> <input
			type="number" id="integrateFeePer" name="integrateFeePer"
			style="width:160px;" value="${custType.integrateFeePer }" />
		</li>
		
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">集成优惠系数</label> <input
			type="number" id="integrateDiscPer" name="integrateDiscPer"
			style="width:160px;" value="${custType.integrateDiscPer }" />
		</li>
		<li><label style="width:145px;">集成管理费系数</label> <input
			type="number" id="manageFeeIntPer" name="manageFeeIntPer"
			style="width:160px;" value="${custType.manageFeeIntPer }" />
		</li>
		<li><label style="width:145px;">橱柜费系数</label> <input
			type="number" id="cupboardFeePer" name="cupboardFeePer"
			style="width:160px;" value="${custType.cupboardFeePer }" />
		</li>
		<li><label style="width:145px;">橱柜优惠系数</label> <input
			type="number" id="cupBoardDiscPer" name="cupBoardDiscPer"
			style="width:160px;" value="${custType.cupBoardDiscPer }" />
		</li>
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">橱柜管理费系数</label> <input
			type="number" id="manageFeeCupPer" name="manageFeeCupPer"
			style="width:160px;" value="${custType.manageFeeCupPer }" />
		</li>
		<li><label style="width:145px;">增减管理费系数</label> <input
			type="number" id="chgManageFeePer" name="chgManageFeePer"
			style="width:160px;" value="${custType.chgManageFeePer }" />
		</li>
	</div>
</div>
