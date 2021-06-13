<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>打卡地点</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp"%>
    <style type="text/css">
        .panel-info {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <form action="" method="post" id="page_form" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-info">
            <div class="panel-body">
                <house:token></house:token><!-- 按钮只能点一次 -->
                <input type="hidden" name="jsonString" value="" />
                <input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
                <input type="hidden" id="desc2" name="desc2" value="${company.desc2}"/>
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label style="width:100px;">编号</label>
                            <input type="text" id="code" name="code"
                                style="width:160px;" value="${company.code}" readonly="readonly" />
                        </li>
                        <li class="form-validate">
                            <label style="width:100px;">公司名</label>
                            <input type="text" id="cmpnyName" name="cmpnyName"
                                style="width:160px;" value="${company.cmpnyName}" readonly="readonly" />
                        </li>
                    </div>
                </ul>
            </div>
        </div>
        <div class="container-fluid" id="id_detail">
            <div>
                <ul class="ul-form">
                    <div class="btn-panel">
                        <div class="btn-group-sm">
                            <button type="button" class="btn btn-system " id="addItem">新增</button>
                            <button type="button" class="btn btn-system " id="updateItem">编辑</button>
                            <button type="button" class="btn btn-system " id="deleteItem">删除</button>
                        </div>
                    </div>
                    <div class="tab-content">
                        <div id="content-list">
                            <div id="tab_info" class="tab-pane fade in active">
                                <table id="dataTable"></table>
                            </div>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
    </form>
    <script type="text/javascript">
        $(function(){
            var postData = $("#page_form").jsonForm();
            Global.JqGrid.initJqGrid("dataTable", {
                url : "${ctx}/admin/company/getSignPlace",
                postData : postData,
                height : $(document).height() - $("#content-list").offset().top - 36,
                styleUI : 'Bootstrap',
                rowNum : -1,
                colModel : [
                    {name:"pk", index:"pk", width:80, label:"编号", sortable:true, align:"left", hidden: true},
                    {name:"descr", index:"descr", width:120, label:"打卡地点", sortable:true, align:"left"},
                    {name:"cmpcode", index:"cmpcode", width:80, label:"公司编号", sortable:true, align:"left"},
                    {name:"longitudetppc", index:"longitudetppc", width:80, label:"经度", sortable:true, align:"right"},
                    {name:"latitudetppc", index:"latitudetppc", width:80, label:"纬度", sortable:true, align:"right"},
                    {name:"limitdistance", index:"limitdistance", width:90, label:"签到限制距离", sortable:true, align:"right"},
                    {name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime},
                    {name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left"},
                    {name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left"},
                    {name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"}
                ],
                ondblClickRow: function(){
                    doUpdate();
                }
            });
            $("#addItem").on("click", function () {
                Global.Dialog.showDialog("addItem", {
                    id:"addItem",
                    title : "打卡地点--新增",
                    url : "${ctx}/admin/company/goSignItem",
                    postData : {cmpCode:"${company.code}",cmpCodeDescr:"${company.desc2}"},
                    height : 277,
                    width : 460,
                    returnFun : goto_query
                });
            });
            $("#updateItem").on("click", function () {
                doUpdate();
            });
            $("#deleteItem").on("click", function () {
                var url = "${ctx}/admin/company/doSignDelete";
                beforeDel(url,"pk","删除");
                returnFun: goto_query;
                return true;
            });
        });
        function doUpdate() {
            var ret = selectDataTableRow();
            if(!ret){
                art.dialog({
                    content:"请选择一条记录"
                });
                return;
            }
            Global.Dialog.showDialog("updateItem", {
                id:"updateItem",
                title : "打卡地点--更新",
                url : "${ctx}/admin/company/goSignItem",
                postData : {m_umState:"M",pK:ret.pk,cmpCode:"${company.code}",cmpCodeDescr:"${company.desc2}"},
                height : 300,
                width : 460,
                returnFun : goto_query
            });
        }
    </script>
</body>
</html>
