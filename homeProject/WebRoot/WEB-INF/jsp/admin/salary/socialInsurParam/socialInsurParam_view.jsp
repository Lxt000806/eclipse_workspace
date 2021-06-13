<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>社保公积金参数管理--查看</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <style>
        .form-search .ul-form li label {
            width: 120px;
        }
    </style>
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
            <form role="form" action="" method="post" id="dataForm" class="form-search" target="targetFrame">
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li>
                            <label>参数名称</label>
                            <input style="width:470px;" type="text" id="descr" name="descr" value="${socialInsurParam.descr}" readonly/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>养老保险最低基数</label>
                            <input type="text" id="edmInsurBaseMin" name="edmInsurBaseMin" value="${socialInsurParam.edmInsurBaseMin}" readonly/>
                        </li>
                        <li>
                            <label>养老保险最高基数</label>
                            <input type="text" id="edmInsurBaseMax" name="edmInsurBaseMax" value="${socialInsurParam.edmInsurBaseMax}" readonly/>
                        </li>
                        <li>
                            <label>养老保险个人费率</label>
                            <input type="text" id="edmInsurIndRate" name="edmInsurIndRate" value="${socialInsurParam.edmInsurIndRate}" readonly/>
                        </li>
                        <li>
                            <label>养老保险单位费率</label>
                            <input type="text" id="edmInsurCmpRate" name="edmInsurCmpRate" value="${socialInsurParam.edmInsurCmpRate}" readonly/>
                        </li>
                        <li>
                            <label>失业保险个人费率</label>
                            <input type="text" id="uneInsurIndRate" name="uneInsurIndRate" value="${socialInsurParam.uneInsurIndRate}" readonly/>
                        </li>
                        <li>
                            <label>失业保险单位费率</label>
                            <input type="text" id="uneInsurCmpRate" name="uneInsurCmpRate" value="${socialInsurParam.uneInsurCmpRate}" readonly/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>医保最低基数</label>
                            <input type="text" id="medInsurBaseMin" name="medInsurBaseMin" value="${socialInsurParam.medInsurBaseMin}" readonly/>
                        </li>
                        <li>
                            <label>医保最高基数</label>
                            <input type="text" id="medInsurBaseMax" name="medInsurBaseMax" value="${socialInsurParam.medInsurBaseMax}" readonly/>
                        </li>
                        <li>
                            <label>医保个人费率</label>
                            <input type="text" id="medInsurIndRate" name="medInsurIndRate" value="${socialInsurParam.medInsurIndRate}" readonly/>
                        </li>
                        <li>
                            <label>医保单位费率</label>
                            <input type="text" id="medInsurCmpRate" name="medInsurCmpRate" value="${socialInsurParam.medInsurCmpRate}" readonly/>
                        </li>
                        <li>
                            <label>生育保险单位费率</label>
                            <input type="text" id="birthInsurCmpRate" name="birthInsurCmpRate" value="${socialInsurParam.birthInsurCmpRate}" readonly/>
                        </li>
                        <li class="form-validate">
                            <label>生育保险最低基数</label>
                            <input type="text" id="birthInsurBaseMin" name="birthInsurBaseMin" value="${socialInsurParam.birthInsurBaseMin}"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label>公积金最低基数</label>
                            <input type="text" id="houFundBaseMin" name="houFundBaseMin" value="${socialInsurParam.houFundBaseMin}" readonly/>
                        </li>
                        <li>
                            <label>公积金最高基数</label>
                            <input type="text" id="houFundBaseMax" name="houFundBaseMax" value="${socialInsurParam.houFundBaseMax}" readonly/>
                        </li>
                        <li>
                            <label>公积金个人费率</label>
                            <input type="text" id="houFundIndRate" name="houFundIndRate" value="${socialInsurParam.houFundIndRate}" readonly/>
                        </li>
                        <li>
                            <label>公积金单位费率</label>
                            <input type="text" id="houFundCmpRate" name="houFundCmpRate" value="${socialInsurParam.houFundCmpRate}" readonly/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label>工伤保险基数</label>
                            <input type="text" id="injuryInsurBaseMin" name="injuryInsurBaseMin" value="${socialInsurParam.injuryInsurBaseMin}" readonly="true"/>
                        </li>
                        <li class="form-validate">
                            <label>工伤保险费率</label>
                            <input type="text" id="injuryInsurRate" name="injuryInsurRate" value="${socialInsurParam.injuryInsurRate}" readonly="true"/>
                        </li>
                    </div>
                    <div>
                        <hr style="margin: 10px 0 20px 0">
                    </div>
                    <div class="validate-group row">
                        <li>
                            <label class="control-textarea">备注</label>
                            <textarea type="text" id="remarks" name="remarks" readonly>${socialInsurParam.remarks}</textarea>
                        </li>
                        <li>
                            <label>过期</label>
                            <input type="checkbox" name="expiredCheckbox" disabled ${socialInsurParam.expired == 'T' ? 'checked' : ''}/>
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
