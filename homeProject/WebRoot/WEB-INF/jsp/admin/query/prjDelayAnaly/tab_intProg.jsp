<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

    $(function () {

        $("#cupSpl").openComponent_supplier({
            showValue: '${custIntProg.cupspl}',
            showLabel: '${custIntProg.cupspldescr}',
            readonly: true
        });
        $("#intSpl").openComponent_supplier({
            showValue: '${custIntProg.intspl}',
            showLabel: '${custIntProg.intspldescr}',
            readonly: true
        });

    });

</script>

<div class="panel panel-info">
    <div class="panel-body">
        <form action="" method="post" id="dataForm" class="form-search">
            <div class="col-sm-6">
                <ul class="ul-form">
                    <li>
                        <label>集成测量申报日期</label>
                        <input type="text" id="measureAppDate" name="measureAppDate"
                               class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.measureappdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>集成测量达标日期</label>
                        <input type="text" id="dealDate" name="dealDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.dealdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>集成设计完成日期</label>
                        <input type="text" id="intDesignDate" name="intDesignDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.intdesigndate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>集成下单日期</label>
                        <input type="text" id="intAppDate" name="intAppDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.intappdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>集成供应商</label>
                        <input type="text" id="intSpl" name="intSpl"
                               value="${custIntProg.intspl}" disabled/>
                    </li>
                    <li>
                        <label>集成出货日期</label>
                        <input type="text" id="intSendDate" name="intSendDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.intsenddate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>集成安装日期</label>
                        <input type="text" id="intInstallDate" name="intInstallDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.intinstalldate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li><label>集成交付日期</label>
                        <input type="text" id="intDeliverDate" name="intDeliverDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.intdeliverdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                </ul>
            </div>
            <div class="col-sm-6">
                <ul class="ul-form">
                    <li>
                        <label>橱柜测量申报日期</label>
                        <input type="text" id="measureCupAppDate" name="measureCupAppDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.measurecupappdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>橱柜测量达标日期</label>
                        <input type="text" id="cupDealDate" name="cupDealDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.cupdealdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>橱柜设计完成日期</label>
                        <input type="text" id="cupDesignDate" name="cupDesignDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.cupdesigndate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>橱柜下单日期</label>
                        <input type="text" id="cupAppDate" name="cupAppDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.cupappdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>橱柜供应商</label>
                        <input type="text" id="cupSpl" name="cupSpl"
                               value="${custIntProg.cupspl}" disabled/>
                    </li>
                    <li>
                        <label>橱柜出货日期</label>
                        <input type="text" id="cupSendDate" name="cupSendDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.cupsenddate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>橱柜安装日期</label>
                        <input type="text" id="cupInstallDate" name="cupInstallDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.cupinstalldate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                    <li>
                        <label>橱柜交付日期</label>
                        <input type="text" id="cupDeliverDate" name="cupDeliverDate" class="i-date"
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                               value="<fmt:formatDate value='${custIntProg.cupdeliverdate}' pattern='yyyy-MM-dd'/>" disabled/>
                    </li>
                </ul>
            </div>
        </form>
    </div>
</div>
