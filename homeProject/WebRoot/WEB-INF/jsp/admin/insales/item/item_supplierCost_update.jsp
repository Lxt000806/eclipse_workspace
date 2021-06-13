<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>供应商供货价--编辑</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_supplier.js?v=${v}"></script>
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
                <input type="hidden" id="suppldescr" name="suppldescr" value="${map.suppldescr}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>材料编号</label>
                            <input type="text" id="itemcode" name="itemcode" value="${map.workercode}" placeholder="自动生成" readonly/>
                        </li>
                        <li>
                            <label><span class="required">*</span>供应商</label>
                            <input type="text" id="supplcode" name="supplcode"/>
                        </li>
                        <li class="form-validate">
                            <label><span class="required">*</span>供货价</label>
                            <input type="text" id="cost" name="cost" value="${map.cost}"/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $("#supplcode").openComponent_supplier({
            showLabel: "${map.suppldescr}",
            showValue: "${map.supplcode}",
            callBack: function(supplier) {
                $("#suppldescr").val(supplier.Descr)
            }
        })
        
        $("#page_form").bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                cost: {
                    validators: {
                        notEmpty: {message: '供货价不能为空'},
                        numeric: {
                            message: '供货价需为数字'
                        }
                    }
                },
            }
        })
    })

    function save() {
        var bv = $("#page_form").data('bootstrapValidator')
        bv.validate()
        if (!bv.isValid()) return
    
        var data = $("#page_form").jsonForm()
        
        if (!data.supplcode) {
            art.dialog({content: "请选择供应商！"})
            return
        }
        
        Global.Dialog.returnData = data
        closeWin()
    }

</script>
</body>
</html>
