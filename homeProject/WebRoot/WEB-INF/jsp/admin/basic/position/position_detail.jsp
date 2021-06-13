<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>职位明细</title>
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
                    <input type="hidden" id="expired" name="expired" value="${position.expired}" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:100px;"><span
                                    class="required">*</span>职位编号</label> <input type="text" id="code" name="code"
                                    style="width:160px;" value="${position.code}" disabled="disabled" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>职位名称</label>
                                <input type="text" id="desc2" name="desc2" style="width:160px;" value="${position.desc2}" disabled="disabled" />
                            </li>
                           	<li class="form-validate"><label><span class="required">*</span>类型</label> <house:xtdm
								id="type" dictCode="POSTIONTYPE" value="${position.type}"  headerLabel="请选择" onchange="changeType()"></house:xtdm>
							</li>
                            <li class="form-validate"><label><span class="required">*</span>是否监理</label> <house:xtdm
								id="isSign" dictCode="YESNO" value="${position.isSign}"  headerLabel="请选择" ></house:xtdm>
							</li>
                            <li>
                                <label style="width:100px;">过期</label>
                                <input type="checkbox" onclick="checkExpired(this)"
                                    ${position.expired.equals('T') ? 'checked':''} />
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
</form>
</body>
</html>

