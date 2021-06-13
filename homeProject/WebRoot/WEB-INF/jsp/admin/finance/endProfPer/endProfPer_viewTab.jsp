<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="body-box-list">
            <br/>
            <div class="validate-group row">
                <!-- ${endProfPer}是一个Map<String, Object> -->
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>客户名称</label> <input type="text" value="${endProfPer.Descr}"
                        style="width:160px;" value="0" readonly="readonly" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>楼盘地址</label> <input type="text"
                        style="width:160px;" value="${endProfPer.Address}" readonly="readonly" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>主材毛利率</label> <input type="text"
                        style="width:160px;" value="${endProfPer.MainProPer}" readonly="readonly" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>服务型产品毛利率</label> <input type="text"
                        style="width:160px;" value="${endProfPer.ServProPer}" readonly="readonly" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>集成橱柜毛利率</label> <input type="text"
                        style="width:160px;" value="${endProfPer.IntProPer}" readonly="readonly" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>软装毛利率</label> <input type="text"
                        style="width:160px;" value="${endProfPer.SoftProPer}" readonly="readonly" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>窗帘毛利率</label> <input type="text"
                        style="width:160px;" value="${endProfPer.CurtainProPer}" readonly="readonly" />
                </li>
            </div>
            <div class="validate-group row">
                <li class="form-validate"><label style="width:200px;"><span
                        class="required">*</span>家具毛利率</label> <input type="text"
                        style="width:160px;" value="${endProfPer.FurnitureProPer}" readonly="readonly" />
                </li>
            </div>
</div>
