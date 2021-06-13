<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>仓库信息--增加</title>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="submit" class="btn btn-system" id="saveBut" onclick="save()">保存</button>
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
                                    <input type="text" id="code" name="code"/>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">仓库名称</label>
                                    <input type="text" id="desc1" name="desc1"/>
                                </li>
                                <li>
                                    <label style="width:120px">仓库类型</label>
                                    <house:dict id="wareType" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='WARETYPE' "
                                                value="1" sqlValueKey="cbm" sqlLableKey="note"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">是否管理货位</label>
                                    <house:dict id="isManagePosi" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' "
                                                value="0" sqlValueKey="cbm" sqlLableKey="note"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">地址</label>
                                    <input type="text" id="address" name="address" style="width: 470px"/>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">是否计算仓储费</label>
                                    <house:dict id="isWHFee" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' "
                                                value="0" sqlValueKey="cbm" sqlLableKey="note"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">仓储费类型</label>
                                    <house:xtdm id="whFeeType" dictCode="WHFEETYPE"></house:xtdm>
                                </li>
                                <li>
                                    <label style="width:120px">仓储费比率</label>
                                    <input type="text" id="wHFeePer" name="wHFeePer"/>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">仓储费成本类型</label>
                                    <house:xtdm id="whFeeCostType" dictCode="WHFEECOSTTYPE"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">配送方式</label>
                                    <house:xtdm id="delivType" dictCode="DELIVTYPE" value="1"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">配送费成本类型</label>
                                    <house:xtdm id="sendFeeCostType" dictCode="WHFEECOSTTYPE"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">材料类型1</label>
                                    <house:dict id="itemType1" dictCode="" sql="select code Code,code+' '+descr Descr from tItemType1 where expired = 'F' "
                                                sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
                                </li>
                                <li>
                                    <label style="width:120px">代管供应商</label>
                                    <input type="text" id="supplCode" name="supplCode"/>
                                </li>
                                <li>
                                    <label style="width:120px">分供货商拆单</label>
                                    <house:xtdm id="isSupplOrder" dictCode="YESNO" value="0"></house:xtdm>
                                </li>
                                <li>
                                    <label style="width:120px">限定配送品类</label>
                                    <house:xtdm id="limitItemType2" dictCode="YESNO" value="0" onchange="toggleTab(this)"></house:xtdm>
                                </li>
                                <li>
                                    <label style="width:120px">限定配送区域</label>
                                    <house:xtdm id="limitRegion" dictCode="YESNO" value="0" onchange="toggleTab(this)"></house:xtdm>
                                </li>
                                <li class="form-validate">
                                    <label style="width:120px">归属公司</label>
                                    <house:dict id="taxPayeeCode" dictCode="" sql="select code Code,rtrim(code)+' '+descr Descr from tTaxPayee where Expired = 'F' "
                                                sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
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
        $("#supplCode").openComponent_supplier()
    
        $("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {/*input状态样式图片*/
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                code: {
                    validators: {
                        notEmpty: {
                            message: '仓库编号不能为空'
                        }
                    }
                },
                desc1: {
                    validators: {
                        notEmpty: {
                            message: '仓库名称不能为空'
                        }
                    }
                },
                isWHFee: {
                    validators: {
                        notEmpty: {
                            message: '是否计算仓储费不能为空'
                        }
                    }
                },
                whFeeCostType: {
                    validators: {
                        notEmpty: {
                            message: '仓储费成本类型不能为空'
                        }
                    }
                },
                sendFeeCostType: {
                    validators: {
                        notEmpty: {
                            message: '配送费成本类型不能为空'
                        }
                    }
                },
            },
            submitButtons: 'input[type="submit"]'
        })
    })
    
    function toggleTab(element) {
        if (element.id === "limitItemType2") {
            var limitItemType2Li = $("#limitItemType2Li")
            
            if (element.value === "1")
                limitItemType2Li.show()
            else
                limitItemType2Li.hide()
                
        } else if (element.id === "limitRegion") {
            var limitRegionLi = $("#limitRegionLi")
            
            if (element.value === "1")
                limitRegionLi.show()
            else
                limitRegionLi.hide()
        }
    }

    function save() {
        $("#dataForm").bootstrapValidator("validate")
        if (!$("#dataForm").data("bootstrapValidator").isValid()) return
        if ($.trim($("#isWHFee").val()) == "1" && $.trim($("#whFeeType").val()) == "") {
            art.dialog({
                content: "仓储费类型不能为空！",
                width: 200
            })
            return false
        }
        var datas = $("#dataForm").jsonForm()
        
        var limitItemType2Grid = $("#limitItemType2Table")
        var limitItemType2Rows = limitItemType2Grid.getRowData()
        if (datas.limitItemType2 === "1") {
            var nonExpiredRowCount = 0
            
            for (var i = 0; i < limitItemType2Rows.length; i++) {
                if (limitItemType2Rows[i].expired === "F")
                    nonExpiredRowCount++
            }
            
            if (nonExpiredRowCount === 0) {
                art.dialog({content: "请添加非过期的限定配送品类"})
                return
            }
            
            if (hasDuplicates(limitItemType2Rows, function(o) { return o.itemtype2 })) {
                art.dialog({content: "不能保存相同的限定配送品类"})
                return
            }
        }
        
        datas.limitItemType2DetailJson = JSON.stringify(limitItemType2Rows)
        
        var limitRegionGrid = $("#limitRegionTable")
        var limitRegionRows = limitRegionGrid.getRowData()
        if (datas.limitRegion === "1") {
            var nonExpiredRowCount = 0
            
            for (var i = 0; i < limitRegionRows.length; i++) {
                if (limitRegionRows[i].expired === "F")
                    nonExpiredRowCount++
            }
            
            if (nonExpiredRowCount === 0) {
                art.dialog({content: "请添加非过期的限定配送区域"})
                return
            }
            
            if (hasDuplicates(limitRegionRows, function(o) { return o.regioncode })) {
                art.dialog({content: "不能保存相同的限定配送区域"})
                return
            }
            
        }
        
        datas.limitRegionDetailJson = JSON.stringify(limitRegionRows)
        
        $.ajax({
            url: "${ctx}/admin/wareHouse/doSave",
            type: "post",
            data: datas,
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                })
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin()
                        }
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({
                        content: obj.msg,
                        width: 200
                    })
                }
            }
        })
    }
    
    function hasDuplicates(stringArray, callback) {
        for (var i = 0; i < stringArray.length - 1; i++) {
            for (var j = i + 1; j < stringArray.length; j++) {
                if (typeof callback === "function") {
                    if (callback(stringArray[i]) === callback(stringArray[j])) return true
                } else {
                    if (stringArray[i] === stringArray[j]) return true
                }
            }
        }

        return false
    }
    
    function generateRowId(tableId) {
        var grid = $("#" + tableId)
        var ids = grid.getDataIDs()
        
        var maxId = 0
        for (var i = 0; i < ids.length; i++) {
            var temporaryId = parseInt(ids[i])
            maxId = maxId >= temporaryId ? maxId : temporaryId
        }

        return ++maxId
    }
</script>
</body>
</html>
