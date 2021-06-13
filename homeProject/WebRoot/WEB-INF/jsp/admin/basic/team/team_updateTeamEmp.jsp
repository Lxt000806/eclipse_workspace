<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑团队成员</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    $("#number").openComponent_employee();
});

function updateTeamEmp(){
    var datas = $("#dataForm").serialize();
    $.ajax({
            url : "${ctx}/admin/team/doAddTeamEmp",
            type : "post",
            data : datas,
            dataType : "json",
            cache : false,
            error : function(obj) {
                showAjaxHtml({
                    "hidden" : false,
                    "msg" : "保存数据出错~"
                });
            },
            success : function(obj) {
                Global.Dialog.returnData = obj;
                closeWin();
            }
        });
}
</script>
</head>
<body>
    <form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="saveBtn" onclick="updateTeamEmp()">
                            <span>保存</span>
                        </button>
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
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>员工编号</label>
                                <input type="text" id="number" maxlength="10" name="number" style="width:150px;" value="${emp.number}" />
                            </li>
                            <!-- 
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>姓名</label>
                                <input type="text" id="namechi" name="namechi" style="width:150px;" />
                            </li>
                             -->
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
