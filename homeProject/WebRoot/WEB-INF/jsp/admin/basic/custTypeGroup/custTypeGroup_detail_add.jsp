<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>客户类型分组明细--新增</title>
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
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label><span class="required">*</span>客户类型</label>
                            <house:custType id="custtype"></house:custType>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function() {
        
        $("#page_form").bootstrapValidator({
		    message: 'This value is not valid',
		    fields: {
		        custtype: {
		            validators: {
		                notEmpty: {message: '客户类型不能为空'}
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
        var custtypedescr = $("#custtype").get()[0].selectedOptions[0].innerText
        data.custtypedescr = custtypedescr.substring(custtypedescr.indexOf(" "))
        
        Global.Dialog.returnData = data
        closeWin()
    }

</script>
</body>
</html>
