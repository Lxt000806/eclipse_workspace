<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>班组成员--查看</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>

<div class="body-box-form">
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" action="" method="post" id="page_form" class="form-search" target="targetFrame">
                <input type="hidden" id="expired" name="expired" value="${map.expired}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>工人编号</label>
                            <input type="text" id="workercode" name="workercode" value="${map.workercode}" placeholder="自动生成" readonly/>
                        </li>
                        <li>
                            <label>成员编号</label>
                            <input type="text" id="code" name="code" value="${map.code}" placeholder="自动生成" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>成员姓名</label>
                            <input type="text" id="namechi" name="namechi" value="${map.namechi}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>手机号</label>
                            <input type="text" id="phone" name="phone" value="${map.phone}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>是否班组长</label>
                            <house:xtdm id="isheadman" dictCode="YESNO" value="${map.isheadman}" disabled="true"></house:xtdm>
                        </li>
                        <li class="form-validate">
                            <label>身份证号</label>
                            <input type="text" id="idnum" name="idnum" value="${map.idnum}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>卡号1</label>
                            <input type="text" id="cardid" name="cardid" value="${map.cardid}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>开户行及支行1</label>
                            <input type="text" id="bank" name="bank" value="${map.bank}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>卡号2</label>
                            <input type="text" id="cardid2" name="cardid2" value="${map.cardid2}" readonly/>
                        </li>
                        <li>
                            <label>开户行及支行2</label>
                            <input type="text" id="bank2" name="bank2" value="${map.bank2}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>工资分配比例</label>
                            <input type="text" id="salaryratio" name="salaryratio" value="${map.salaryratio}" readonly/>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox"
                                   ${map.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)" disabled/>
                        </li>
                    </div>
                </ul>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">

</script>
</body>
</html>
