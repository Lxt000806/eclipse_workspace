<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_uom.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#buyer1").openComponent_employee({showValue : '${item.buyer1}',showLabel : '${item.buyer1Descr}'});
	$("#buyer2").openComponent_employee({showValue : '${item.buyer2}',showLabel : '${item.buyer2Descr}'});
	calPerfPer();
});
</script>

<div class="body-box-list" style="margin-top: 0px;">
    <div class="panel-body">
        <div class="validate-group row">
            <!-- 第一列 -->
            <div class="col-sm-4">
                <ul class="ul-form">
                    <li class="form-validate">
                        <label>买手1</label>
                        <input type="text" id="buyer1" name="buyer1" value="${item.buyer1}"/>
                    </li>
                    <li>
                        <label>买手2</label>
                        <input type="text" id="buyer2" name="buyer2" value="${item.buyer2}"/>
                    </li>
                    <li class="form-validate">
                        <label></span>是否上样 </label>
                        <house:xtdm id="hasSample" dictCode="YESNO" value="${item.hasSample}"></house:xtdm>
                    </li>
                    <li>
                        <label>上样时间</label>
                        <input type="text" id="crtDate" name="crtDate" class="i-date"
                               onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${item.crtDate}' pattern='yyyy-MM-dd'/>"/>
                    </li>
                    <li>
                        <label>是否清库存产品</label>
                        <house:xtdm id="isClearInv" dictCode="YESNO" value="${item.isClearInv}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>是否实际材料</label>
                        <house:xtdm id="isActualItem" dictCode="YESNO" value="${item.isActualItem}"
                                    onchange="changeIsActualItem()"></house:xtdm>
                    </li>
                    <li>
                        <label>库存材料编号</label>
                        <input type="text" id="wareHouseItemCode" name="wareHouseItemCode"
                               value="${item.wareHouseItemCode}"/>
                    </li>
                    <li class="form-validate">
                        <label></span>含税价 </label>
                        <house:xtdm id="isContainTax" dictCode="YESNO" value="${item.isContainTax}"></house:xtdm>
                    </li>
                    <li>
                        <label>客户类型</label>
                        <house:dict id="custType" dictCode=""
                                    sql="select code ,code +' ' + desc1 descr from tcustType where expired='F'"
                                    sqlValueKey="code" sqlLableKey="descr" value="${item.custType}">
                        </house:dict>
                    </li>
                    <li>
                        <label>客户类型组</label>
                        <house:dict id="custTypeGroupNo" dictCode="" value="${item.custTypeGroupNo}"
                                    sql="select No SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tCustTypeGroup where Expired = 'F'"></house:dict>
                    </li>
                    <li class="form-validate">
                        <label>是否套餐材料</label>
                        <house:xtdm id="isSetItem" dictCode="YESNO" value="${item.isSetItem}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>提成类型</label>
                        <house:xtdm id="commiType" dictCode="COMMITYPE" value="${item.commiType}"
                                    onchange="commiTypeChange()"></house:xtdm>
                    </li>
                </ul>
            </div>
            <!-- 第二列 -->
            <div class="col-sm-4">
                <ul class="ul-form">
                    <li class="form-validate">
                        <label><span class="required">*</span>提成比例</label>
                        <input type="text" id="commiPerc" name="commiPerc"
                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${item.commiPerc}"/>
                        <span id="commiUom_show">元</span>
                    </li>
                    <li class="form-validate">
                        <label>业绩比例</label>
                        <input type="text" id="perfPer1" name="perfPer1" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.perfPer}" readonly="readonly"/>
                    </li>
                    <li class="form-validate">
                        <label></span>安装费类型 </label>
                        <house:xtdm id="installFeeType" dictCode="INSTALLFEETYPE"
                                    value="${item.installFeeType}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>安装费单价</label>
                        <input type="text" id="installFee" name="installFee"
                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${item.installFee}"/>
                    </li>
                    <li class="form-validate">
                        <label></span>仓储费类型 </label>
                        <house:xtdm id="whFeeType" dictCode="WHCALTYPE" value="${item.whFeeType}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>仓储费</label>
                        <input type="text" id="wHFee" name="wHFee" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.wHFee}"/>
                    </li>
                    <li class="form-validate" id="isProm_show" hidden>
                        <label></span>是否促销 </label>
                        <house:xtdm id="isProm" dictCode="YESNO" value="${item.isProm}"></house:xtdm>
                    </li>
                    <li class="form-validate" id="oldPrice_show" hidden>
                        <label>原售价</label>
                        <input type="text" id="oldPrice" name="oldPrice" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.oldPrice}"/>
                    </li>
                    <li class="form-validate" id="oldCost_show" hidden>
                        <label>原进价</label>
                        <input type="text" id="oldCost" name="oldCost" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.oldCost}"/>
                    </li>
                    <li>
                        <label>附加成本</label>
                        <input type="text" id="additionalCost" name="additionalCost" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.additionalCost}"/>
                    </li>
                    <li class="form-validate">
                        <label>灯头数</label>
                        <input type="text" id="lampNum" name="lampNum" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.lampNum}" readonly="readonly"/>
                    </li>
                    <li>
                        <label>参考毛利率</label>
                        <input type="text" id="perfPer" name="perfPer" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="0"/>
                    </li>
                </ul>
            </div>
            <!-- 第三列 -->
            <div class="col-sm-4">
                <ul class="ul-form">
                    <!-- 具体字段 -->
                    <li class="form-validate">
                        <label>总库存数量</label>
                        <input type="text" style="width:160px;" id="allQty" name="allQty" value="${item.allQty}"
                               readonly="readonly" placeholder="不填"/>
                    </li>
                    
                    
                </ul>
            </div>
        </div>
    </div>
</div>


<div class="pageContent">
	<div id="content-list">
		<table id="dataTable_itemSoft"></table>
		<div id="dataTable_itemSoftPager"></div>
	</div>
</div>

