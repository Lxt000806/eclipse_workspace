<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>工种分类12查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
</head>
<body>
    <form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="closeBut"
                            onclick="closeWin(false)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-body">
                    <input type="hidden" name="jsonString" value="" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:200px;">编号</label>
                            <input type="text" id="WorkType12Code" maxlength="10" name="WorkType12Code"
                                style="width:160px;" value="${workType12.WorkType12Code}"
                                readonly="readonly" />
                            </li>
			                <li class="form-validate">
			                    <label style="width:200px;">名称</label>
			                    <input type="text" id="WorkType12Name" name="WorkType12Name" value="${workType12.WorkType12Name}"
			                        style="width:160px;" value="0" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">工种分类1名称</label>
			                    <input type="text" id="WorkType1Name" name="WorkType1Name" value="${workType12.WorkType1Name}"
			                        style="width:160px;" value="0" readonly="readonly" />
			                 </li>
			                 <li class="form-validate">
			                     <label style="width:200px;">人工工种分类2名称</label>
			                     <input type="text" id="OfferWorkType2Name" name="OfferWorkType2Name"
			             value="${workType12.OfferWorkType2Name}" style="width:160px;" value="0" readonly="readonly" />
			                 </li>
			                <li class="form-validate">
			                    <label style="width:200px;">需要工种分类2人工需求</label>
			                    <input type="text" id="NeedWorkType2ReqName" name="NeedWorkType2ReqName" style="width:160px;"
			                        value="${workType12.NeedWorkType2ReqName}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">上一同类工种</label>
			                    <input type="text" id="NeedWorkType2ReqName" name="NeedWorkType2ReqName" style="width:160px;"
			                        value="${workType12.BefSameWorkType12Name}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                     <label style="width:200px;">签约每工地扣质保金</label>
			                     <input type="text" id="PerQualityFee" name="PerQualityFee" style="width:160px;"
			                         value="${workType12.PerQualityFee}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                <label style="width:200px;">签约质保金总额</label>
			                <input type="text" id="SignQualityFee" name="SignQualityFee" style="width:160px;"
			                value="${workType12.SignQualityFee}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                <label style="width:200px;">质保金起扣点</label>
			                <input type="text" id="QualityFeeBegin" name="QualityFeeBegin" style="width:160px;"
			                    value="${workType12.QualityFeeBegin}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                <label style="width:200px;">付款期数</label>
			                <input type="text" id="PayNum" name="PayNum" style="width:160px;"
			                     value="${workType12.PayNum}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">是否付款才允许申请</label>
			                    <input type="text" id="MustPay" name="MustPay" style="width:160px;"
			                    value="${workType12.MustPay == '1' ? '是' : '否'}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">最少施工天数</label>
			                    <input type="text" id="MinDay" name="MinDay" style="width:160px;"
			                        value="${workType12.MinDay}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                <label style="width:200px;">最大施工天数</label>
			                <input type="text" id="MaxDay" name="MaxDay" style="width:160px;"
			                    value="${workType12.MaxDay}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">申请最小延后天数</label>
			                    <input type="text" id="AppMinDay" name="AppMinDay" style="width:160px;"
			                        value="${workType12.AppMinDay}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">申请最大延后天数</label>
			                    <input type="text" id="AppMaxDay" name="AppMaxDay" style="width:160px;"
			                    value="${workType12.AppMaxDay}" readonly="readonly" />
			                </li>
                            <li class="form-validate">
                                <label style="width:200px;">开始施工项目</label>
                                <input type="text" id="beginPrjItemName" name="beginPrjItemName" value="${workType12.beginPrjItemName}"
			                        style="width:160px;" value="0" readonly="readonly" />
                            </li>	                			                			                		                			                			                		                		                		                			                			                			                		                			                 			                 		                                           
			                <li class="form-validate">
			                    <label style="width:200px;">结束施工项目</label>
			                    <input type="text" id="PrjItemName" name="PrjItemName" value="${workType12.PrjItemName}"
			                        style="width:160px;" value="0" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">验收施工项目</label>
			                    <input type="text" id="ConfPrjItemName" name="ConfPrjItemName" style="width:160px;"
			                        value="${workType12.ConfPrjItemName}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
			                    <label style="width:200px;">显示顺序</label>
			                    <input type="text" id="DispSeq" name="DispSeq" style="width:160px;"
			                        value="${workType12.DispSeq}" readonly="readonly" />
			                </li>
			                <li class="form-validate">
                                <label style="width:200px;">最少上传图片数量</label>
                                <input type="text" id="minPhotoNum" name="minPhotoNum" style="width:160px;"
                                    value="${workType12.minPhotoNum}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">最多上传图片数量</label>
                                <input type="text" id="maxPhotoNum" name="maxPhotoNum" style="width:160px;"
                                    value="${workType12.maxPhotoNum}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">定额工资控制比例</label>
                                <input type="text" id="salaryCtrl" name="salaryCtrl" style="width:160px;"onkeyup="value=value.replace(/[^\?\d]/g,'')"
                                    value="${workType12.salaryCtrl}"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">工期考核启动工种</label>
                                <house:xtdm id="beginCheck" dictCode="YESNO" value="${workType12.BeginCheck}" disabled="true"></house:xtdm>
                            </li>
                            <li>
                                <label style="width:200px;">最低发放金额</label>
                                <input type="text" id="minSalaryProvideAmount" name="minSalaryProvideAmount" value="${workType12.MinSalaryProvideAmount}" readonly/>
                            </li>
                            <li class="form-validate">
			                    <label style="width:200px;">验收类型</label>
			                    <input type="text" id="confType" name="confType" style="width:160px;"
			                        value="${workType12.confType}" readonly="readonly" />
			                </li>
                            <li class="form-validate">
                                <label style="width:200px;">是否用于自装通</label>
                                <house:xtdm id="isRegisterMall" dictCode="YESNO" value="${workType12.IsRegisterMall}" disabled="true"></house:xtdm>
                            </li>
                            <li class="form-validate">
                                <label style="width:200px;">是否项目经理申请</label>
                                <house:xtdm id="isPrjApp" dictCode="YESNO" value="${workType12.IsPrjApp }" disabled="true"></house:xtdm>
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
