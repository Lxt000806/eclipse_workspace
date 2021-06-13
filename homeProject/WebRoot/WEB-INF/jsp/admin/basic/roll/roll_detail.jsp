<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加角色</title>
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
                    <input type="hidden" name="jsonString" value="" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>角色编码</label>
                                <input type="text" style="width:160px;" value="${roll['Code']}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>角色名称</label>
                                <input type="text" style="width:160px;" value="${roll['Descr']}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">下级角色</label>
                                <input type="text" style="width:160px;" value="${roll['ChildCodeDescr']}"  readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">默认一级部门</label>
                                <input type="text" style="width:160px;" value="${roll['Department1Descr']}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">默认二级部门</label>
                                <input type="text" style="width:160px;" value="${roll['Department2Descr']}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">默认三级部门</label>
                                <input type="text" style="width:160px;" value="${roll['Department3Descr']}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">是否销售角色</label>
                                <input type="text" style="width:160px;" value="${roll['IsSale']}" readonly="readonly" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">是否过期</label>
                                <input type="checkbox" ${roll['expired'] == 'T' ? 'checked':''} />
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
</form>
</body>
</html>
