<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
</head>

<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system " onclick="save()">
                    <span>保存</span>
                </button>
                <button type="button" class="btn btn-system " onclick="closeWin(false)">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>

    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
                <house:token></house:token>
                <ul class="ul-form">
                    <div class="row">
                        <div class="col-sm-4">
                            <li>
                                <label>班组编号</label>
                                <input type="text" id="code" name="code" value="${worker.code}" readonly/>
                            </li>
                            <li>
                                <label>姓名</label>
                                <input type="text" id="nameChi" name="nameChi" value="${worker.nameChi}" readonly/>
                            </li>
                            <li>
                                <label>劳务公司</label>
                                <house:dict id="laborCmpCode" dictCode="" value="${worker.laborCmpCode.trim()}"
                                    sql="select rtrim(Code) SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tLaborCompny where Expired = 'F'"></house:dict>
                            </li>
                        </div>
                    </div>
                </ul>
            </form>
        </div>
    </div>

</div>
</body>
<script>

    function save() {
        $.ajax({
            url: "${ctx}/admin/worker/doUpdateLaborCmp",
            type: "post",
            data: $("#page_form").jsonForm(),
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "保存数据出错"})
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
</html>
