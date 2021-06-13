<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>薪酬岗位级别管理--编辑</title>
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
                <button type="button" class="btn btn-system" onclick="save()">保存</button>
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <input type="hidden" id="expired" name="expired" value="${salaryPosiLevel.expired}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li hidden>
                            <label>岗位级别ID</label>
                            <input type="text" id="pk" name="pk" value="${salaryPosiLevel.pk}" readonly/>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>岗位级别</label>
                            <input type="text" id="descr" name="descr" value="${salaryPosiLevel.descr}"/>
                        </li>
                        <li>
                            <label><span class="required">*</span>岗位类别</label>
                            <house:dict id="posiClass" dictCode="" sqlValueKey="PK" sqlLableKey="Descr"
                                value="${salaryPosiLevel.posiClass}" disabled="true"
                                sql="select PK, cast(PK as nvarchar(11)) + ' ' + Descr Descr from tSalaryPosiClass where Expired = 'F'"></house:dict>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>工资</label>
                            <input type="number" id="salary" name="salary" value="${salaryPosiLevel.salary}"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>基本工资</label>
                            <input type="number" id="basicSalary" name="basicSalary" value="${salaryPosiLevel.basicSalary}"/>
                        </li>
                        <li>
                            <label><span class="required">*</span>业绩指标</label>
                            <input type="number" id="minPerfAmount" name="minPerfAmount" value="${salaryPosiLevel.minPerfAmount}"/>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>序号</label>
                            <input type="number" id="dispSeq" name="dispSeq" value="${salaryPosiLevel.dispSeq}">
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                   ${salaryPosiLevel.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {

    })

    function save() {

        if (!$("#descr").val()) {
            art.dialog({content: "请输入岗位级别名称"})
            return
        }

        var salary = $("#salary").val()
        if (!salary) {
            art.dialog({content: "请输入岗位工资"})
            return
        }
        
        var basicSalary = $("#basicSalary").val()
        if (!basicSalary) {
            art.dialog({content: "请输入岗位基本工资"})
            return
        }
        
        if (parseFloat(basicSalary) > parseFloat(salary)) {
            art.dialog({content: "基本工资不能高于工资"})
            return
        }
        
        if (!$("#minPerfAmount").val()) {
            art.dialog({content: "请输入岗位业绩指标"})
            return
        }

        var data = $("#page_form").jsonForm()

        $.ajax({
            url: "${ctx}/admin/salaryPosiLevel/doUpdate",
            type: "POST",
            data: data,
            dataType: "json",
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
                            closeWin(true)
                        }
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({content: obj.msg})
                }
            }
        })

    }

</script>
</body>
</html>
