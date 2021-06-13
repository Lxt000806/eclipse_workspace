<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
    <form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="closeBut"
                            onclick="closeWin(false)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-body">
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:150px;"><span
                                    class="required">*</span>员工编号</label> <input type="text" id="empCode"
                                maxlength="10" name="empCode" style="width:150px;" disabled="true"
                                    value="${emp.number.concat('|').concat(emp.nameChi)}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>成绩</label>
                                <input type="text" id="score" name="score" style="width:150px;" disabled="true"
                                    value="${score.score}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>是否毕业</label>
                                <input type="text" style="width:150px;" disabled="true"
                                    value="${score.isPass.equals('0') ? '否' : '是'}">
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>是否补考</label>
                                <input type="text" style="width:150px;" disabled="true"
                                    value="${score.isMakeUp.equals('0') ? '否' : '是'}">
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;"><span class="required">*</span>补考成绩</label>
                                <input type="text" id="makeUpScore" name="makeUpScore" style="width:150px;" disabled="true"
                                    value="${score.makeUpScore}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:150px;">备注</label>
                                <textarea id="remark" name="remark" style="width:150px;" disabled="true">${socre.remark}</textarea>
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
