<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

</body>
</html><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>外部员工信息--查看</title>
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
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>员工编号</label>
                            <input type="text" id="number" name="number" value="${employee.number}" readonly/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>姓名</label>
                            <input type="text" id="nameChi" name="nameChi" value="${employee.nameChi}" readonly/>
                        </li>
                        <li>
                            <label>性别</label>
                            <house:xtdm id="gender" dictCode="GENDER" value="${employee.gender}" disabled="true"></house:xtdm>
                        </li>
                        <li>
                            <label>身份证号</label>
                            <input type="text" id="idnum" name="idnum" value="${employee.idnum}" readonly/>
                        </li>
                        <li>
                            <label>电话</label>
                            <input type="text" id="phone" name="phone" value="${employee.phone}" readonly/>
                        </li>
                        <li>
                            <label>银行卡号</label>
                            <input type="text" id="cardId" name="cardId" value="${employee.cardId}" readonly/>
                        </li>
                        <li>
                            <label>开户行</label>
                            <input type="text" id="bank" name="bank" value="${employee.bank}" readonly/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>所属部门</label>
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
                        <li>
                            <label>状态</label>
                            <house:xtdm id="status" dictCode="EMPSTS" value="${employee.status}"
                                disabled="true"></house:xtdm>
                        </li>
                        <li>
                            <label>加入日期</label>
                            <input type="text" id="joinDate" name="joinDate" class="i-date" disabled
                                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                   value="<fmt:formatDate value='${employee.joinDate}' pattern='yyyy-MM-dd'/>"/>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" ${employee.expired == 'T' ? 'checked' : ''} disabled="disabled"/>
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
            showLabel: "${employee.departmentDescr}",
            showValue: "${employee.department}",
            condition: {isEmp: "1", isOutChannel: "1"},
            callBack: function(data) {
                var path = data.path
                var pathArr = path.split("/")
                var department1 = ""
                var department2 = ""
                var department3 = ""
                if (pathArr.length == 1) {
                    department1 = getCodeByDept("tDepartment1", pathArr[0])
                } else if (pathArr.length == 2) {
                    department1 = getCodeByDept("tDepartment1", pathArr[0])
                    department2 = getCodeByDept("tDepartment2", pathArr[1])
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
        
        $("#openComponent_department_department").blur()
        $("#openComponent_department_department").attr("readonly", true)

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

</script>
</body>
</html>
