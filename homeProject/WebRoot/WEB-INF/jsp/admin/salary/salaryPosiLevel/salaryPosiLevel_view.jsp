<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>薪酬岗位级别管理--查看</title>
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
                <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
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
                            <input type="text" id="descr" name="descr" value="${salaryPosiLevel.descr}" readonly/>
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
                            <input type="number" id="salary" name="salary" value="${salaryPosiLevel.salary}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>基本工资</label>
                            <input type="number" id="basicSalary" name="basicSalary" value="${salaryPosiLevel.basicSalary}" readonly/>
                        </li>
                        <li>
                            <label><span class="required">*</span>业绩指标</label>
                            <input type="number" id="minPerfAmount" name="minPerfAmount" value="${salaryPosiLevel.minPerfAmount}" readonly/>
                        </li>
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>序号</label>
                            <input type="number" id="dispSeq" name="dispSeq" value="${salaryPosiLevel.dispSeq}" readonly>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox" disabled
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
</script>
</body>
</html>
