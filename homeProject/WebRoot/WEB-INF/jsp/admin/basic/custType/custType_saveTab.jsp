<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(function(){
	$("#type").change(function(){
		var type=$(this).val();
		if(type=="1"){
			$("#prjCtrlType").removeProp("disabled",true);
		}else if(type=="2"){
			$("#prjCtrlType").val("2").prop("disabled",true);
		}else{
			$("#prjCtrlType").val("").prop("disabled",true);
		}
	});
});
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
		<li><label style="width:145px;"><span
				class="required">*</span>说明</label>
			<input type="text" id="desc1" name="desc1" style="width:160px;"
			/>
		</li>
		<li><label style="width:145px;">客户类型分类</label> <house:xtdm
				id="type" dictCode="CUSTTYPESORT"></house:xtdm>
		</li>
		<li><label style="width:145px;">是否默认统计</label> <house:xtdm
				id="isDefaultStatic" dictCode="YESNO"
				></house:xtdm>
		</li>
		<li><label style="width:145px;">是否添加完整客户材料</label> <house:xtdm
				id="isAddAllInfo" dictCode="YESNO" ></house:xtdm>
		</li>
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">公司名称</label> <input type="text"
			id="cmpnyName" name="cmpnyName" style="width:160px;"
			 />
		</li>
		<li><label style="width:145px;">公司全称</label> <input type="text"
			id="cmpnyFullName" name="cmpnyFullName" style="width:160px;"
			 />
		</li>
		<li><label style="width:145px;">公司地址</label> <input type="text"
			id="cmpnyAddress" name="cmpnyAddress" style="width:495px;"
			/>
		</li>
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">logo文件名</label> <input
			type="text" id="logoFile" name="logoFile" style="width:160px;"
			 />
		</li>
		<li><label style="width:145px;">合同设计费类型</label> <house:xtdm
				id="contractFeeRepType" dictCode="DESIGNFEETYPE"
				></house:xtdm>
		</li>
		<li><label style="width:145px;">税金公式</label> <input type="text" 
				id="taxExpr" name="taxExpr" style="width:495px;"/>
		</li>
	</div>
	<div class="validate-group row">
		<li><label style="width:145px;">基础预算必须导入模板</label> <house:xtdm
				id="mustImportTemp" dictCode="YESNO" value="0"></house:xtdm>
		</li>
		<li><label style="width:145px;">套内面积系数</label> <input
			type="number" id="innerAreaPer" name="innerAreaPer"
			style="width:160px;" value="0" />
		</li>
		<li><label style="width:145px;">是否确认材料</label> <house:xtdm
				id="confirmItem" dictCode="YESNO"></house:xtdm>
		</li>
		<li><label style="width:145px;">项目经理验收</label> <house:xtdm
				id="isPrjConfirm" dictCode="YESNO"></house:xtdm>
		</li>
	</div>
	<div class="validate-group row">
		<div class="col-xs-6">
			<li><label style="width:145px;">面积系数</label> <input type="number"
				id="areaPer" name="areaPer" style="width:160px;" value="0"/>
			</li>
			<li>
				<label style="width:145px;">可报公用材料</label> 
				<house:xtdm id="canUseComItem" dictCode="YESNO" value="${custType.canUseComItem}"></house:xtdm>
			</li>
			<li><label style="width:145px;">显示顺序</label> <input type="number"
				id="dispSeq" name="dispSeq" value="${custType.dispSeq}"  onkeyup="value=value.replace(/[^\-?\d]/g,'')"/>
			</li>
			<li><label style="width:145px;">工地直播</label> <house:xtdm
				id="isPushPub" dictCode="YESNO" value="1"></house:xtdm>
			</li>
			<li><label style="width:145px;">工人分类</label> <house:xtdm
				id="workerClassify" dictCode="WORKERCLASSIFY" ></house:xtdm>
			</li>
			<li>
				<label style="width:145px;">工程款收款单位</label>
				<house:dict id="payeeCode" dictCode="" 
					sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
					sqlLableKey="descr" sqlValueKey="code"></house:dict>
			</li>
			<li>
				<label style="width:145px;">设计费收款单位</label>
				<house:dict id="designPayeeCode" dictCode="" 
					sql=" select rtrim(Code) code,rtrim(Code)+' '+rtrim(Descr) descr from tTaxPayee where Expired = 'F' "
					sqlLableKey="descr" sqlValueKey="code"></house:dict>
			</li>
			<li>
				<label style="width:145px;" for="designRiskFund">设计师风控基金</label>
				<input type="text" id="designRiskFund" name="designRiskFund" style="width:160px;" 
					onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
					onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
					value="0"/>
			</li>
			<li>
				<label style="width:145px;" for="prjManRiskFund">项目经理风控基金</label>
				<input type="text" id="prjManRiskFund" name="prjManRiskFund" style="width:160px;" 
					onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
					onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
					value="0"/>
			</li>
			<li>
				<label style="width:145px;"><span class="required">*</span>签约报价流程</label>
				<house:xtdm id="signQuoteType" dictCode="CTSNQTTYPE" value=""></house:xtdm>
			</li>
			<li style="max-height: 120px;">
				<label class="control-textarea" style="top: -35px;width:145px;">预算打印基础物料说明</label>
				<textarea id="itemRemark" name="itemRemark" style="height: 50px;width: 495px;"></textarea>
			</li>
			<li>
                <label style="width:145px;"><span>生成税务信息</span></label>
                <house:xtdm id="genTaxInfo" dictCode="YESNO" value="0"></house:xtdm>
            </li>
		</div>
		
		<div class="col-xs-6">
			<li><label style="width:145px;">是否防水饰面补贴</label> <house:xtdm
				id="isFacingSubsidy" dictCode="YESNO" ></house:xtdm>
			</li>
			<li><label style="width:145px;">可报公用基础项目</label> 
			<house:xtdm id="canUseComBaseItem" dictCode="YESNO" ></house:xtdm>
			</li>
			<li><label style="width:145px;">装修类型</label> <house:xtdm
				id="isPartDecorate" dictCode="DECTYPE" ></house:xtdm>
			</li>
			<li>
				<label style="width:145px;">材料配合费取消时间</label>
				<input type="text" style="width:160px;" id="matCoopFeeCancelDate" name="matCoopFeeCancelDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custType.matCoopFeeCancelDate }' pattern='yyyy-MM-dd'/>"  />
			</li>
			<li>
				<label style="width:145px;">设计师最高优惠公式</label> 
				<input type="text" id="designerMaxDiscAmtExpr" name="designerMaxDiscAmtExpr" style="width:495px;"/>
			</li>
			<li>
				<label style="width:145px;">总监最高优惠公式</label> 
				<input type="text" id="directorMaxDiscAmtExpr" name="directorMaxDiscAmtExpr" style="width:495px;"/>
			</li>
			<li style="max-height: 120px;">
				<label class="control-textarea" style="top: -35px;width:145px;">预算打印基础报价说明</label>
				<textarea id="pricRemark" name="pricRemark" style="height: 50px;width: 495px;"></textarea>
			</li>
			<li><label style="width:145px;">水电后期安装材料申请</label> <house:xtdm
				id="isWaterAftInsItemApp" dictCode="YESNO" ></house:xtdm>
			</li>
			<li><label style="width:145px;">基础解单</label> <house:xtdm
				id="baseSpec" dictCode="YESNO" ></house:xtdm>
			</li>
		</div>
	</div>
</div>
