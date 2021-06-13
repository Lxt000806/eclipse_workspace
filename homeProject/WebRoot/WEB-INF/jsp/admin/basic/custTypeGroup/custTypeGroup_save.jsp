<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>客户类型组管理--新增</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="submit" class="btn btn-system" id="saveBut" onclick="perform('${custTypeGroup.m_umState}')">保存</button>
                <button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#basicInfoTab" data-toggle="tab">基本信息</a></li>
            <li><a href="#groupDetailTab" data-toggle="tab">客户类型分组明细</a></li>
        </ul>
        <div class="tab-content">
            <div id="basicInfoTab" class="tab-pane fade in active">
                <div class="panel panel-info">
                    <div class="panel-body">
                        <form action="" method="post" id="dataForm" class="form-search">
                            <input type="hidden" name="m_umState" value="${custTypeGroup.m_umState}"/>
                            <input type="hidden" name="expired" id="expired" value="${custTypeGroup.expired}"/>
                            <ul class="ul-form">
                                <div class="validate-group row">
	                                <li class="form-validate">
	                                    <label><span class="required">*</span>编号</label>
	                                    <input type="text" id="no" name="no" value="${custTypeGroup.no}"/>
	                                </li>
	                                <li class="form-validate">
	                                    <label><span class="required">*</span>名称</label>
	                                    <input type="text" id="descr" name="descr" style="width:280px" value="${custTypeGroup.descr}"/>
	                                </li>
	                                <li>
	                                    <label class="control-textarea">备注</label>
	                                    <textarea name="remarks" id="remarks" style="width:450px">${custTypeGroup.remarks}</textarea>
	                                </li>
	                                <li>
			                            <label>过期</label>
			                            <input type="checkbox" name="expiredCheckbox" id="expiredCheckbox"
			                                   ${custTypeGroup.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
			                        </li>
                                </div>
                            </ul>
                        </form>
                    </div>
                </div>
            </div>
            <div id="groupDetailTab" class="tab-pane fade">
                <jsp:include page="tab_groupDetail.jsp"></jsp:include>
            </div>
        </div>
    </div>
</div>
<script>
    $(function() {
    
        initializeGroupOperationPage("${custTypeGroup.m_umState}")

        $("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                no: {
                    validators: {
                        notEmpty: {message: '编号不能为空'},
                        stringLength: {min: 2, max: 10, message: '长度为2-10个字符'}
                    }
                },
                descr: {
                    validators: {
                        notEmpty: {message: '名称不能为空'}
                    }
                }
            }
        })
        
    })
    
    function initializeGroupOperationPage(action) {
        switch (action) {
            case "A":
                $("#expiredCheckbox").parent().hide()
                break
            case "C":
                $("#expiredCheckbox").parent().hide()
                break
            case "M":
                $("#no").prop("readonly", true)
                break
            case "V":
                $("#saveBut").hide()
                break
            default:
                break
        }
    }
    
    function perform(action) {
        switch (action) {
            case "A": save()
                break
            case "C": copy()
                break
            case "M": update()
                break
            case "V":
                break
            default:
                break
        }
    }

    function save() {
        var data = preValidate()
        if (!data) return
    
        $.ajax({
            url: "${ctx}/admin/custTypeGroup/doSave",
            type: "post",
            data: data,
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "保存数据出错！"})
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: closeWin
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({content: obj.msg})
                }
            }
        })
    }
    
    function copy() {
        var data = preValidate()
        if (!data) return
    
        $.ajax({
            url: "${ctx}/admin/custTypeGroup/doCopy",
            type: "post",
            data: data,
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "保存数据出错！"})
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: closeWin
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({content: obj.msg})
                }
            }
        })
    }
    
    function update() {
        var data = preValidate()
        if (!data) return
    
        $.ajax({
            url: "${ctx}/admin/custTypeGroup/doUpdate",
            type: "post",
            data: data,
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "保存数据出错！"})
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: closeWin
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({content: obj.msg})
                }
            }
        })
    }
    
    function preValidate() {
        $("#dataForm").bootstrapValidator("validate")
        if (!$("#dataForm").data("bootstrapValidator").isValid()) return
        
        var data = $("#dataForm").jsonForm()
        var groupDetailGrid = $("#groupDetailTable")
        var groupDetailRows = groupDetailGrid.getRowData()

        if (hasDuplicates(groupDetailRows, function(row) { return row.custtype })) {
            art.dialog({content: "同一分组不能保存相同的客户类型"})
            return null
        }

        data.detailJson = JSON.stringify(groupDetailRows)
        
        return data
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
