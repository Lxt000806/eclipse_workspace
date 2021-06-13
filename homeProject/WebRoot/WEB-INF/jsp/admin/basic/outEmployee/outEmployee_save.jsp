<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>外部员工信息--新增</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
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
                <input type="hidden" name="isLead" value="0"/>
                <input type="hidden" name="leadLevel" value="0"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>员工编号</label>
                            <input type="text" id="number" name="number" placeholder="保存时自动生成" readonly/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>姓名</label>
                            <input type="text" id="nameChi" name="nameChi"/>
                        </li>
                        <li>
                            <label>性别</label>
                            <house:xtdm id="gender" dictCode="GENDER" value="M"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>身份证号</label>
                            <input type="text" id="idnum" name="idnum"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>电话</label>
                            <input type="text" id="phone" name="phone"/>
                        </li>
                        <li>
                            <label>银行卡号</label>
                            <input type="text" id="cardId" name="cardId"/>
                        </li>
                        <li>
                            <label>开户行</label>
                            <input type="text" id="bank" name="bank"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>所属部门</label>
                            <input type="text" id="department" name="department"/>
                        </li>
                        <li>
                            <label>一级部门</label>
                            <select id="department1" name="department1" disabled></select>
                        </li>
                        <li>
                            <label>二级部门</label>
                            <select id="department2" name="department2" disabled></select>
                        </li>
                        <li>
                            <label>三级部门</label>
                            <select id="department3" name="department3" disabled></select>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label>状态</label>
                            <house:xtdm id="status" dictCode="EMPSTS" value="ACT" disabled="true"></house:xtdm>
                        </li>
                        <li>
                            <label><span class="required">*</span>加入日期</label>
                            <input type="text" id="joinDate" name="joinDate" class="i-date"
                                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                   value="<fmt:formatDate value='${employee.joinDate}' pattern='yyyy-MM-dd'/>"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType", "department1", "department2", "department3")

        $("#department").openComponent_department({
            condition: {isEmp: "1", isOutChannel: "1"},
            callBack: function(data) {
                var path = data.path
                var pathArr = path.split("/")
                var department1 = ""
                var department2 = ""
                var department3 = ""
                
                if (pathArr.length == 1) {
                    department1 = getCodeByDept("tDepartment1", pathArr[0])
                    $("#department2").attr("value", "")
                    $("#department3").attr("value", "")
                } else if (pathArr.length == 2) {
                    department1 = getCodeByDept("tDepartment1", pathArr[0])
                    department2 = getCodeByDept("tDepartment2", pathArr[1])
                    $("#department3").attr("value", "")
                } else if (pathArr.length == 3) {
                    department1 = getCodeByDept("tDepartment1", pathArr[0])
                    department2 = getCodeByDept("tDepartment2", pathArr[1])
                    department3 = getCodeByDept("tDepartment3", pathArr[2])
                }

                Global.LinkSelect.setSelect({
                    firstSelect: "department1",
                    firstValue: department1,
                    secondSelect: "department2",
                    secondValue: department2,
                    thirdSelect: "department3",
                    thirdValue: department3,
                })
            }
        })
        
        $("#openComponent_department_department").attr("readonly", true)

        /*
        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                nameChi: {
                    validators: {
                        notEmpty: {message: "姓名不能为空"}
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {message: "手机号不能为空"},
                        numeric: {message: "只能输入数字"}
                    }
                }
            }
        })
        */
        
    })

    //根据所属部门编号获取一二三级部门编号
    function getCodeByDept(tableName, department) {
        var code = ""
        $.ajax({
            url: "${ctx}/admin/employee/getCodeByDept",
            type: 'post',
            data: {tableName: tableName, department: department},
            cache: false,
            async: false,
            error: function(obj) {

            },
            success: function(obj) {
                if (obj != "") {
                    code = obj
                }
            }
        })
        return code
    }

    function changeStatus() {
        var status = $("#status")
        var reTurnDate = $("#reTurnDate")
        var chager = $("#chager")
        var leaveDate = $("#leaveDate")
        var chagel = $("#chagel")

        switch (status.val()) {
            case "ACT":
                reTurnDate.attr("disabled", true)
                chager.css("color", "#777777")
                leaveDate.attr("disabled", true)
                chagel.css("color", "#777777")
                break
            case "SUS":
                reTurnDate.attr("disabled", true)
                chager.css("color", "#777777")
                leaveDate.attr("disabled", false)
                chagel.css("color", "#000000")
                break
            case "OTH":
                reTurnDate.attr("disabled", true)
                chager.css("color", "#777777")
                leaveDate.attr("disabled", false)
                chagel.css("color", "#000000")
                break
            case "WRT":
                reTurnDate.attr("disabled", false)
                chager.css("color", "#000000")
                leaveDate.attr("disabled", false)
                chagel.css("color", "#000000")
                break
            default:
                reTurnDate.attr("disabled", true)
                chager.css("color", "#777777")
                leaveDate.attr("disabled", true)
                chagel.css("color", "#777777")
        }
    }

    function save() {

        if (!$("#nameChi").val()) {
            art.dialog({content: "请输入姓名"})
            return
        }

        if (!/(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/.test($("#idnum").val())) {
            art.dialog({content: "身份证号码格式有误"})
            return
        }

        if (!/^\d{1,11}$/.test($("#phone").val())) {
            art.dialog({content: "电话号码格式有误"})
            return
        }
        
        if ($("#cardId").val()) {
            if (!/^\d{12,20}$/.test($("#cardId").val())) {
                art.dialog({content: "银行卡号码格式有误"})
                return
            }
            
            if (!$("#bank").val()) {
                art.dialog({content: "请输入开户行"})
                return
            }
        }
        
        if (!$("#department").val()) {
            art.dialog({content: "请选择员工所属部门"})
            return
        }

        if (!$("#status").val()) {
            art.dialog({content: "请选择员工状态"})
            return
        }

        var data = $("#page_form").jsonForm()

        $.ajax({
            url: "${ctx}/admin/employee/existsNameChiExceptNumber",
            type: "post",
            data: {nameChi: $("#nameChi").val(), number: ''},
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "保存出错"})
            },
            success: function(existed) {
                if (existed) {
                    art.dialog({
                        content: '存在相同的中文名记录，确定保存吗？',
                        lock: true,
                        ok: function() { doSave(data) },
                        cancel: function() {}
                    })
                } else {
                    doSave(data)
                }
            }
        })

    }

    function doSave(data) {
        $.ajax({
            url: "${ctx}/admin/outEmployee/doSave",
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
