<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>付款规则添加规则</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
	.form-search .ul-form li label {
	    width: 120px;
	    margin-right: 0px;
	}
	</style>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	$(function() {
	
		(function setValue(){
			$("#payNum").val(eval(${detailXml}).paynum);
			$("#payExpr").val(eval(${detailXml}).payexpr);
			$("#isRcvDesignFee").val(eval(${detailXml}).isrcvdesignfee);
			$("#chgPer").val(eval(${detailXml}).chgper);
			$("#confirmPrjItem").val(eval(${detailXml}).confirmprjitem);
			$("#payRemark").val(eval(${detailXml}).payremark);
			$("#payTip").val(eval(${detailXml}).paytip);
			$("#tipType").val(eval(${detailXml}).tiptype);
            $("#workType12").val(eval(${detailXml}).worktype12);
            $("#tipAddDays").val(eval(${detailXml}).tipadddays);
            $("#isCalcCommi").val(eval(${detailXml}).iscalccommi);
			$("#isCalcCommiDescr").val(eval(${detailXml}).iscalccommidescr);
			$("#businessCommiProvidePer").val(eval(${detailXml}).businesscommiprovideper);
			$("#businessSubsidyProvidePer").val(eval(${detailXml}).businesssubsidyprovideper);
			$("#businessMultipleProvidePer").val(eval(${detailXml}).businessmultipleprovideper);
			$("#designCommiProvidePer").val(eval(${detailXml}).designcommiprovideper);
			$("#designSubsidyProvidePer").val(eval(${detailXml}).designsubsidyprovideper);
			$("#designMultipleProvidePer").val(eval(${detailXml}).designmultipleprovideper);
			$("#isCalcSceneDesignCommi").val(eval(${detailXml}).iscalcscenedesigncommi);
			$("#isCalcSceneDesignCommiDescr").val(eval(${detailXml}).iscalcscenedesigncommidescr);
			$("#sceneDesignProvidePer").val(eval(${detailXml}).scenedesignprovideper);
			$("#isCalcDesignFeeCommi").val(eval(${detailXml}).iscalcdesignfeecommi);
			$("#isCalcDesignFeeCommiDescr").val(eval(${detailXml}).iscalcdesignfeecommidescr);
			$("#isCalcDrawFeeCommi").val(eval(${detailXml}).iscalcdrawfeecommi);
			$("#isCalcDrawFeeCommiDescr").val(eval(${detailXml}).iscalcdrawfeecommidescr);
			$("#isCalcMainCommi").val(eval(${detailXml}).iscalcmaincommi);
			$("#isCalcMainCommiDescr").val(eval(${detailXml}).iscalcmaincommidescr);
			$("#businessMainCommiProvidePer").val(eval(${detailXml}).businessmaincommiprovideper);
			$("#isCalcIntCommi").val(eval(${detailXml}).iscalcintcommi);
			$("#isCalcIntCommiDescr").val(eval(${detailXml}).iscalcintcommidescr);
			$("#businessIntCommiProvidePer").val(eval(${detailXml}).businessintcommiprovideper);
			$("#isCalcSoftCommi").val(eval(${detailXml}).iscalcsoftcommi);
			$("#isCalcSoftCommiDescr").val(eval(${detailXml}).iscalcsoftcommidescr);
			$("#businessSoftCommiProvidePer").val(eval(${detailXml}).businesssoftcommiprovideper);
			$("#designFeeCommiProvidePer").val(eval(${detailXml}).designfeecommiprovideper);
			$("#drawFeeCommiProvidePer").val(eval(${detailXml}).drawfeecommiprovideper);
			$("#isCalcBasePersonalCommi").val(eval(${detailXml}).iscalcbasepersonalcommi);
			$("#basePersonalCommiProvidePer").val(eval(${detailXml}).basepersonalcommiprovideper);
			$("#newDesignCommiProvidePer").val(eval(${detailXml}).newdesigncommiprovideper); 
		})();
		
		switchTipType();
	
	});
	
    function switchTipType() {
        var tipType = $("#tipType");
        var workType12 = $("#workType12");
        var workType12Descr = $("#workType12Descr");
        
        if (tipType.val() === "1") {
            workType12.parent().hide();
            workType12.val("");
            workType12Descr.val("");
        } else if (tipType.val() === "2") {
            workType12.parent().show();
        } else {
            workType12.parent().hide();
            workType12.val("");
            workType12Descr.val("");
        }
    }
	
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
    			<div class="panel-body">
      				<div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="infoBox" id="infoBoxDiv"></div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>付款期数</label>
									<input type="text" id="payNum" name="payNum" style="width:160px;" value="${payNum }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>付款公式</label>
									<input type="text" id="payExpr" name="payExpr" style="width:450px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>是否收设计费</label>
									<house:xtdm id="isRcvDesignFee" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label>增减比例</label>
									<input type="text" id="chgPer" name="chgPer" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
                                <li class="form-validate">
                                    <label>缴款提醒类型</label>
                                    <house:xtdm id="tipType" dictCode="PAYRULETIPTYPE" onchange="switchTipType()"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label>提醒增加天数</label>
                                    <input type="text" id="tipAddDays" name="tipAddDays" value=""/>
                                </li>
                            </div>
                            <div class="validate-group row">
                                <li>
                                    <label>验收项目</label>
                                    <house:dict id="confirmPrjItem" dictCode=""
                                        sql="select Code, Code + ' ' + Descr Descr from tPrjItem1 where expired = 'F' order by cast(Code as int)"
                                        sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
                                </li>
                                <li class="form-validate">
                                    <label>提醒工种</label>
                                    <house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value=""></house:DataSelect>
                                </li>
                            </div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">付款说明</label>
									<textarea id="payRemark" name="payRemark" rows="2"></textarea>
	 							</li>
	 							<li>
                                    <label class="control-textarea">缴款提示</label>
                                    <textarea id="payTip" name="payTip" rows="2"></textarea>
                                </li>
							</div>
							<li class="form-validate">
									<label><span class="required">*</span>业绩提成标记</label>
									<house:xtdm id="isCalcCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务提成发放比例</label>
									<input type="text" id="businessCommiProvidePer" name="businessCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务补贴发放比例</label>
									<input type="text" id="businessSubsidyProvidePer" name="businessSubsidyProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务倍数发放比例</label>
									<input type="text" id="businessMultipleProvidePer" name="businessMultipleProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								
								<li class="form-validate">
									<label><span class="required">*</span>设计提成发放比例</label>
									<input type="text" id="designCommiProvidePer" name="designCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>新设计师发放比例</label>
									<input type="text" id="newDesignCommiProvidePer" name=""newDesignCommiProvidePer"" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>	
								<li class="form-validate">
									<label><span class="required">*</span>设计补贴发放比例</label>
									<input type="text" id="designSubsidyProvidePer" name="designSubsidyProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								
								<li class="form-validate">
									<label><span class="required">*</span>设计倍数发放比例</label>
									<input type="text" id="designMultipleProvidePer" name="designMultipleProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>现场设计师提成计算</label>
									<house:xtdm id="isCalcSceneDesignCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>现场设计师发放比例</label>
									<input type="text" id="sceneDesignProvidePer" name="sceneDesignProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>设计费提成计算</label>
									<house:xtdm id="isCalcDesignFeeCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>设计费提成发放比例</label>
									<input type="text" id="designFeeCommiProvidePer" name="designFeeCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span> 绘图费提成计算</label>
									<house:xtdm id="isCalcDrawFeeCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>绘图费提成发放比例</label>
									<input type="text" id="drawFeeCommiProvidePer" name="drawFeeCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>主材提成计算</label>
									<house:xtdm id="isCalcMainCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								
								<li class="form-validate">
									<label><span class="required">*</span>业务主材提成发放比例</label>
									<input type="text" id="businessMainCommiProvidePer" name="businessMainCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>集成提成计算</label>
									<house:xtdm id="isCalcIntCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务集成提成发放比例</label>
									<input type="text" id="businessIntCommiProvidePer" name="businessIntCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>软装提成计算</label>
									<house:xtdm id="isCalcSoftCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>业务软装提成发放比例</label>
									<input type="text" id="businessSoftCommiProvidePer" name="businessSoftCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>计算基础个性化项目提成</label>
									<house:xtdm id="isCalcBasePersonalCommi" dictCode="YESNO"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>基础个性化项目发放比例</label>
									<input type="text" id="basePersonalCommiProvidePer" name="basePersonalCommiProvidePer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="0" />
								</li>
						</ul>	
					</form>
				</div>
			</div> <!-- edit-form end -->
		</div>			
	</div>
</body>
</html>
