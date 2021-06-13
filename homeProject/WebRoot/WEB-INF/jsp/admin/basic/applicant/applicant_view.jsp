<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>查看应聘人员信息</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-body">
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:100px;">序号</label> <input type="text"
                                style="width:160px;" value="${applicant.pk}" readonly="readonly" />
                            </li>
                            <li class="form-validate"><label style="width:100px;">年龄</label> <input type="text"
                                style="width:160px;" value="${applicant.age == null ? '' : applicant.age}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>姓名</label>
                                <input type="text" id="nameChi" name="nameChi" style="width:160px;" value="${applicant.nameChi}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">状态</label>
                                <house:dict dictCode="" id="status" sql="select cbm, note from tXTDM where ID = 'APPLICANTSTS'"
                                    sqlLableKey="note" sqlValueKey="cbm" value="${applicant.status}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>性别</label>
                                <select id="gender" name="gender">
                                    <option value="M" ${applicant.gender == 'M' ? 'selected' : ''}>男</option>
                                    <option value="F" ${applicant.gender == 'F' ? 'selected' : ''}>女</option>
                                </select>
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">籍贯</label>
                                <input type="text" style="width:160px;" value="${applicant.birtPlace}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">身份证号</label>
                                <input type="text" id="idNum" name="idNum" readonly="readonly" style="width:160px;" value="${applicant.idNum}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">文化程度</label>
                                <house:dict dictCode="" id="edu" sql="select cbm, note from tXTDM where ID = 'EDU'"
                                    sqlLableKey="note" sqlValueKey="cbm" value="${applicant.edu}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">出生日期</label>
                                <input type="text" style="width:160px;" readonly="readonly"
                                    value="<fmt:formatDate value='${applicant.birth}' type='DATE' pattern='yyyy-MM-dd' />" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">毕业院校</label>
                                <input type="text"style="width:160px;" value="${applicant.school}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>电话</label>
                                <input type="text" style="width:160px;" value="${applicant.phone}" readonly="readonly"/>
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">专业</label>
                                <input type="text"style="width:160px;" value="${applicant.schDept}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">家庭住址</label>
                                <input type="text" style="width:450px;" value="${applicant.address}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">应聘部门</label>
                                <input type="text" style="width:160px;" value="${applicant.deptDescr}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">一级部门</label>
                                <input type="text" style="width:160px;" value="${applicant.dept1Descr}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>面试岗位</label>
                                <input type="text"  value="${applicant.positionDescr}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">途径</label>
                                <input type="text"  value="${applicant.source}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>面试时间</label>
                                <input type="text"  readonly="readonly"
                                    value="<fmt:formatDate value='${applicant.appDate}' type='DATE' pattern='yyyy-MM-dd' />" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">预入时间</label>
                                <input type="text"  readonly="readonly"
                                    value="<fmt:formatDate value='${applicant.comeInDate}' type='DATE' pattern='yyyy-MM-dd' />" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">备注</label>
                                <textarea type="text"  readonly="readonly" style="width:450px;" rows="3">${applicant.remarks}</textarea>
                            </li>
                            <li>
                                <label style="width:100px;">过期</label>
                                <input type="checkbox" ${applicant.expired.equals('T') ? 'checked':''} />
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
</form>
</body>
</html>
