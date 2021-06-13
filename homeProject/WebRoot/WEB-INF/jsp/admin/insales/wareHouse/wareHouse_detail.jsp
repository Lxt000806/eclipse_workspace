<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>仓库信息--查看</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#basicInformationTab" data-toggle="tab">基本信息</a></li>
            <li id="limitItemType2Li" style="display:none"><a href="#limitItemType2Tab" data-toggle="tab">配送品类</a></li>
            <li id="limitRegionLi" style="display:none"><a href="#limitRegionTab" data-toggle="tab">配送区域</a></li>
        </ul>
        <div class="tab-content">
            <div id="basicInformationTab" class="tab-pane fade in active">
                <div class="panel panel-info">
                    <div class="panel-body">
                        <form action="" method="post" id="dataForm" class="form-search">
                            <ul class="ul-form">
                                <li class="form-validate">
                                    <label style="width:120px">仓库编号</label>
                                    <input type="text" id="code" name="code" value="${wareHouse.code}"/>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">仓库名称</label>
                                    <input type="text" id="desc1" name="desc1" value="${wareHouse.desc1}"/>
                                </li>
                                <li>
                                    <label style="width:120px">仓库类型</label>
                                    <house:dict id="wareType" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='WARETYPE' "
                                                sqlValueKey="cbm" sqlLableKey="note" value="${wareHouse.wareType}"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">是否管理货位</label>
                                    <house:dict id="isManagePosi" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' "
                                                sqlValueKey="cbm" sqlLableKey="note" value="${wareHouse.isManagePosi}"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">地址</label>
                                    <input type="text" id="address" name="address" value="${wareHouse.address}" style="width: 470px"/>
                                </li>
                                <li>
                                    <label style="width:120px">是否计算仓储费</label>
                                    <house:dict id="isWHFee" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' "
                                                sqlValueKey="cbm" sqlLableKey="note" value="${wareHouse.isWHFee}"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">仓储费类型</label>
                                    <house:xtdm id="whFeeType" dictCode="WHFEETYPE" value="${wareHouse.whFeeType}"></house:xtdm>
                                </li>
                                <li>
                                    <label style="width:120px">仓储费比率</label>
                                    <input type="text" id="wHFeePer" name="wHFeePer" value="${wareHouse.wHFeePer}"/>
                                </li>
                                <li>
                                    <label style="width:120px">仓储费成本类型</label>
                                    <house:xtdm id="whFeeCostType" dictCode="WHFEECOSTTYPE" value="${wareHouse.whFeeCostType}"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">配送方式</label>
                                    <house:xtdm id="delivType" dictCode="DELIVTYPE" value="${wareHouse.delivType}"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">配送费成本类型</label>
                                    <house:xtdm id="sendFeeCostType" dictCode="WHFEECOSTTYPE" value="${wareHouse.sendFeeCostType}"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">材料类型1</label>
                                    <house:dict id="itemType1" dictCode="" sql="select code Code,code+' '+descr Descr from tItemType1 where expired = 'F' "
                                                sqlValueKey="Code" sqlLableKey="Descr" value="${wareHouse.itemType1}"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">代管供应商</label>
                                    <input type="text" id="supplCode" name="supplCode"/>
                                </li>
                                <li>
                                    <label style="width:120px">分供货商拆单</label>
                                    <house:xtdm id="isSupplOrder" dictCode="YESNO" value="${wareHouse.isSupplOrder}" disabled="true"></house:xtdm>
                                </li>
                                <li>
                                    <label style="width:120px">限定配送品类</label>
                                    <house:xtdm id="limitItemType2" dictCode="YESNO" value="${wareHouse.limitItemType2}" disabled="true"></house:xtdm>
                                </li>
                                <li>
                                    <label style="width:120px">限定配送区域</label>
                                    <house:xtdm id="limitRegion" dictCode="YESNO" value="${wareHouse.limitRegion}" disabled="true"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">归属公司</label>
                                    <house:dict id="taxPayeeCode" dictCode="" sql="select code Code,rtrim(code)+' '+descr Descr from tTaxPayee where Expired = 'F' "
                                                sqlValueKey="Code" sqlLableKey="Descr" value="${wareHouse.taxPayeeCode.trim()}"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">最后更新人员</label>
                                    <input type="text" id="lastUpdatedBy" name="lastUpdatedBy" value="${wareHouse.lastUpdatedBy}"/>
                                </li>
                                <li>
                                    <label style="width:120px">最后更新日期</label>
                                    <input type="text" id="lastUpdate" name="lastUpdate" value="${wareHouse.lastUpdate}"/>
                                </li>
                                <li>
                                    <label>过期</label>
                                    <input type="checkbox" id="expired_show" name="expired_show" value="${wareHouse.expired}"
                                           onclick="checkExpired(this)" ${wareHouse.expired=="T"?"checked":""}/>
                                </li>
                            </ul>
                        </form>
                    </div>
                </div>
            </div>
            <div id="limitItemType2Tab" class="tab-pane fade">
                <jsp:include page="tab_limitItemType2.jsp"></jsp:include>
            </div>
            <div id="limitRegionTab" class="tab-pane fade">
                <jsp:include page="tab_limitRegion.jsp"></jsp:include>
            </div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $("#supplCode").openComponent_supplier({
            showValue: "${wareHouse.supplCode}",
            readonly: true
        })
        $("#openComponent_supplier_supplCode").blur()
    
        if ("${wareHouse.limitItemType2}" === "1")
            $("#limitItemType2Li").show()
        
        if ("${wareHouse.limitRegion}" === "1")
            $("#limitRegionLi").show()
    })
</script>
</body>
</html>
