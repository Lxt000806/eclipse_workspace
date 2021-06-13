<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>材料毛利率--复制</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
    $(function() {
        //初始化一二级区域联动
        Global.LinkSelect.initSelect("${ctx}/admin/builder/regionCodeByAuthority","regionCode","regionCode2");
             Global.LinkSelect.setSelect({firstSelect:'regionCode',
                                firstValue:'${builder.RegionCode}',
                                secondSelect:'regionCode2',
                                secondValue:'${builder.RegionCode2}'
                                });
    });
</script>
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
                    <input type="hidden" name="jsonString" value="" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:185px;"><span
                                    class="required">*</span>客户编号</label> <input type="text" id="custCode"
                                maxlength="10" name="custCode" style="width:160px;"
                                value="${endProfPer.CustCode}" readonly="readonly" />
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
            <div class="container-fluid" id="id_detail">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab_info" data-toggle="tab">主项目</a>
                    </li>
                </ul>
                <ul class="ul-form">
                    <div class="tab-content">
                        <div id="tab_info" class="tab-pane fade in active">
                            <jsp:include page="endProfPer_viewTab.jsp"></jsp:include>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
    </form>
</body>
</html>
