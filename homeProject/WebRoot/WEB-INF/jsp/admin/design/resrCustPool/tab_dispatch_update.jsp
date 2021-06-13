<%@ page import="com.house.framework.web.login.UserContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system" onclick='execute("${resrCustPoolRule.m_umState}")'>保存</button>
                <button type="button" class="btn btn-system" onclick='closeWin(false)'>关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form_1" class="form-search" target="targetFrame">
                <house:token></house:token>
                <input type="hidden" id="pk" name="pk" value="${resrCustPoolRule.pk}"/>
                <input type="hidden" id="resrCustPoolNo" name="resrCustPoolNo" value="${resrCustPoolRule.resrCustPoolNo}"/>
                <input type="hidden" id="ruleClass" name="ruleClass" value="${resrCustPoolRule.ruleClass}"/>
                <input type="hidden" id="dispSeq" name="dispSeq" value="${resrCustPoolRule.dispSeq}"/>
                <input type="hidden" id="expired" name="expired" value="${resrCustPoolRule.expired}"/>
                <div class="validate-group row">
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>名称</label>
                            <input type="text" id="descr" name="descr" value="${resrCustPoolRule.descr}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>资源范围</label>
                            <house:xtdm id="scope" dictCode="POOLRULESCOPE" value="${resrCustPoolRule.scope}"
                                        onchange="changeScope(this)"></house:xtdm>
                        </li>
                    </ul>
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label class="control-textarea"><span class="required">*</span>SQL</label>
                            <textarea id="sql" name="sql" rows="3">${resrCustPoolRule.sql}</textarea>
                        </li>
                    </ul>
                    <ul class="ul-form">
                        <li class="form-validate">
                            <label><span class="required">*</span>类型</label>
                            <house:xtdm id="type" dictCode="POOLRULETYPE" value="${resrCustPoolRule.type}"
                                        onchange="changeType(this)"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>分配线索池编号</label>
                            <house:dict id="toResrCustPoolNo" dictCode="" value="${resrCustPoolRule.toResrCustPoolNo}"
                                        sql="select a.Descr SQL_LABEL_KEY, a.No SQL_VALUE_KEY
											 from tResrCustPool a
											 where a.No <> '${resrCustPoolRule.resrCustPoolNo}'"></house:dict>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="#tab_dispatch_member" data-toggle="tab">分配成员</a>
        </li>
    </ul>
    <div class="tab-content">
        <div id="tab_dispatch_member" class="tab-pane fade in active">
            <jsp:include page="tab_dispatch_member.jsp"></jsp:include>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function() {
        $("#page_form_1").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                descr: {
                    validators: {
                        notEmpty: {message: '名称不能为空'}
                    }
                },
                scope: {
                    validators: {
                        notEmpty: {message: '资源范围不能为空'}
                    }
                },
                sql: {
                    validators: {
                        notEmpty: {message: 'SQL不能为空'},
                    }
                },
                type: {
                    validators: {
                        notEmpty: {message: '类型不能为空'},
                    }
                },
                toResrCustPoolNo: {
                    validators: {
                        notEmpty: {message: '分配线索池编号不能为空'},
                    }
                }
            }
        });
        
        changeScope($("#scope").get(0));
        changeType($("#type").get(0));
    });
    
    function changeScope(obj) {
        var sql = $("#sql");
        
        switch (obj.value) {
            case "1":
                sql.parent().hide();
                break;
            case "2":
                sql.parent().show();
                break;
            default:
                sql.parent().hide();
        }
    }
    
    function changeType(obj) {
        var toResrCustPoolNo = $("#toResrCustPoolNo");
        var dispatchMemberTab = $("#tab_dispatch_member");
    
        switch (obj.value) {
            case "1":
                toResrCustPoolNo.parent().hide();
                dispatchMemberTab.addClass("active");
                break;
            case "2":
                toResrCustPoolNo.parent().show();
                dispatchMemberTab.removeClass("active");
                break;
            default:
                toResrCustPoolNo.parent().hide();
                dispatchMemberTab.removeClass("active");
        }
    }

    function execute(action) {
        switch (action) {
            case "A":
                save();
                break;
            case "M":
                update();
                break;
            default:
        }
    }
    
    function convertPropNames(obj) {
	    var lowerCaseNames = ["lastupdate", "lastupdatedby", "actionlog", "resrcustnumber", "dispseq"];
	    var camelCaseNames = ["lastUpdate", "lastUpdatedBy", "actionLog", "resrCustNumber", "dispSeq"];
	
	    for (var prop in obj) {
	        var index = lowerCaseNames.indexOf(prop);
	        if (index >= 0) {
	            obj[camelCaseNames[index]] = obj[prop];
	            delete obj[prop];
	        }
	    }
	}

    function save() {
        var bv1 = $("#page_form_1").data('bootstrapValidator');
        var bv2 = $("#page_form_2").data('bootstrapValidator');
        bv1.validate();
        bv2.validate();
        if (!bv1.isValid() || !bv2.isValid()) return;
        
        var form1Data = $("#page_form_1").jsonForm();
        var form2Data = $("#page_form_2").jsonForm();
        var params = {};
        $.extend(params, form1Data, form2Data);
        
        var grid = getDispatchMemberGrid();
        var gridData = grid.getRowData();
        
        if (params.type === "1" && params.dispatchCZYScope === "3") {
            if (gridData.length) {
                for (var i = 0; i < gridData.length; i++)
                    convertPropNames(gridData[i]);
                
                params.dispatchMembers = gridData;
            } else {
                art.dialog({content: "池到人且自定义成员时，成员列表不能为空！"});
                return;
            }
        }

        $.ajax({
            url: "${ctx}/admin/resrCustPool/dispatch/doAdd",
            type: "POST",
            data: params,
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin(true);
                        }
                    });
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token);
                    art.dialog({content: obj.msg});
                }
            }
        });
    }

    function update() {
        var bv1 = $("#page_form_1").data('bootstrapValidator');
        var bv2 = $("#page_form_2").data('bootstrapValidator');
        bv1.validate();
        bv2.validate();
        if (!bv1.isValid() || !bv2.isValid()) return;
        
        var form1Data = $("#page_form_1").jsonForm();
        var form2Data = $("#page_form_2").jsonForm();
        var params = {};
        $.extend(params, form1Data, form2Data);
        
        var grid = getDispatchMemberGrid();
        var gridData = grid.getRowData();
        
        if (params.type === "1" && params.dispatchCZYScope === "3") {
            if (gridData.length) {
                for (var i = 0; i < gridData.length; i++)
                    convertPropNames(gridData[i]);
                
                params.dispatchMembers = gridData;
                params.deletedMemberPks = deletedMemberPks.join(",");
            } else {
                art.dialog({content: "池到人且自定义成员时，成员列表不能为空！"});
                return;
            }
        }

        $.ajax({
            url: "${ctx}/admin/resrCustPool/dispatch/doUpdate",
            type: "POST",
            data: params,
            dataType: "json",
            error: function(obj) {
                showAjaxHtml({
                    "hidden": false,
                    "msg": "保存数据出错~"
                });
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin(true);
                        }
                    });
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token);
                    art.dialog({content: obj.msg});
                }
            }
        });
    }

</script>
</body>
</html>
