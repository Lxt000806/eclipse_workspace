<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>

</style>
<div class="body-box-list">
            <br/>
            <div class="validate-group row">
                <!-- ${endProfPer}是一个Map<String, Object> -->
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>客户名称</label> <input type="text" id="custDescr"
                    name="custDescr" value="${endProfPer.Descr}" style="width:160px;" value="0" 
                    readonly="readonly" placeholder="输入客户编号自动生成" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>楼盘地址</label> <input type="text" id="custAddress" name="custAddress" 
                        style="width:160px;" value="${endProfPer.Address}" 
                        readonly="readonly" placeholder="输入客户编号自动生成" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>主材毛利率</label> <input type="text" id="mainProPer"
                    name="mainProPer" style="width:160px;" value="${endProfPer.MainProPer}"
                    data-bv-numeric data-bv-numeric-message="主材毛利率只能是数值"
                    onblur="if(this.value === ''){this.value='0.5'}" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>服务型产品毛利率</label> <input type="text" id="servProper"
                    name="servProper" style="width:160px;" value="${endProfPer.ServProPer}"
                    data-bv-numeric data-bv-numeric-message="服务性产品毛利率只能是数值"
                    onblur="if(this.value === ''){this.value='0.13'}" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>集成橱柜毛利率</label> <input type="text" id="intProPer"
                    name="intProPer" style="width:160px;" value="${endProfPer.IntProPer}"
                    data-bv-numeric data-bv-numeric-message="集成橱柜毛利率只能是数值"
                    onblur="if(this.value === ''){this.value='0.28'}" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>软装毛利率</label> <input type="text" id="softProPer"
                    name="softProPer" style="width:160px;" value="${endProfPer.SoftProPer}"
                    data-bv-numeric data-bv-numeric-message="软装毛利率只能是数值"
                    onblur="if(this.value === ''){this.value='0.55'}" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>窗帘毛利率</label> <input type="text" id="curtainProPer"
                    name="curtainProPer" style="width:160px;" value="${endProfPer.CurtainProPer}"
                    data-bv-numeric data-bv-numeric-message="窗帘毛利率只能是数值"
                    onblur="if(this.value === ''){this.value='0.55'}" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>家具毛利率</label> <input type="text" id="furnitureProPer"
                    name="furnitureProPer" style="width:160px;" value="${endProfPer.FurnitureProPer}"
                    data-bv-numeric data-bv-numeric-message="家具毛利率只能是数值"
                    onblur="if(this.value === ''){this.value='0.55'}" />
                </li>
            </div>
</div>
