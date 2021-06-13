<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>配送品类--编辑</title>
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
                <input type="hidden" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
                <input type="hidden" id="expired" name="expired" value="${map.expired}"/>
                <input type="hidden" name="actionlog" value="EDIT">
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label><span class="required">*</span>材料类型1</label>
                            <select type="text" id="itemtype1" name="itemtype1"></select>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>材料类型2</label>
                            <select type="text" id="itemtype2" name="itemtype2"></select>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                   ${map.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
    
        Global.LinkSelect.initSelect("${ctx}/admin/item/itemType", "itemtype1", "itemtype2")
        Global.LinkSelect.setSelect({
            firstSelect: 'itemtype1',
            firstValue: '${map.itemtype1}',
            secondSelect: 'itemtype2',
            secondValue: '${map.itemtype2}'
        })
        
        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                itemtype2: {
                    validators: {
                        notEmpty: {message: '材料类型2不能为空'}
                    }
                },
            }
        })
    })

    function save() {
        var bv = $("#page_form").data('bootstrapValidator')
        bv.revalidateField("itemtype2")
        bv.validate()
        if (!bv.isValid()) return
    
        var data = $("#page_form").jsonForm()
        var itemtype2descr = $("#itemtype2").get()[0].selectedOptions[0].innerText
        data.itemtype2descr = itemtype2descr.substring(itemtype2descr.indexOf(" ") + 1)
        data.lastupdate = Date.now()
        
        Global.Dialog.returnData = data
        closeWin()
    }

</script>
</body>
</html>
