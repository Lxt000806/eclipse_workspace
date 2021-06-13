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
    <title>图片模板配置</title>
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
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
</head>
<body>
    <form action="" method="post" id="page_form" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="saveBut">
                            <span>保存</span>
                        </button>
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
                <ul class="ul-form">
                    <div class="validate-group row">
                        <li class="form-validate">
                            <label style="width:100px;">编号</label>
                            <input type="text" id="code" maxlength="10" name="code"
                                style="width:160px;" value="${workType12.code}" readonly="readonly" />
                        </li>
                        <li class="form-validate">
                            <label style="width:100px;">名称</label>
                            <input type="text" id="descr" maxlength="10" name="descr"
                                style="width:160px;" value="${workType12.descr}" readonly="readonly" />
                        </li>
                        <li>
                            <label for="isTechnology">是否上传图片模板</label>
                            <house:xtdm id="isTechnology" dictCode="YESNO" value="${workType12.isTechnology==''?0:workType12.isTechnology}"/>
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
            $("#isTechnology option[value='']").remove();
            var postData = $("#page_form").jsonForm();
            Global.JqGrid.initJqGrid("dataTable", {
                url : "${ctx}/admin/workType12/getTechnology",
                postData : postData,
                height : $(document).height() - $("#content-list").offset().top - 36,
                styleUI : 'Bootstrap',
                rowNum : -1,
                colModel : [
                    {name:"code", index:"code", width:80, label:"工艺编号", sortable:true, align:"left", hidden: true},
                    {name:"descr", index:"descr", width:120, label:"工艺名称", sortable:true, align:"left"},
                    {name:"disseq", index:"disseq", width:80, label:"显示顺序", sortable:true, align:"right"},
                    {name:"sourcetype", index:"sourcetype", width:80, label:"来源类型", sortable:true, align:"left", hidden:true},
                    {name:"sourcetypedescr", index:"sourcetypedescr", width:80, label:"来源类型", sortable:true, align:"left"},
                    {name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime},
                    {name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left"},
                    {name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left"},
                    {name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"}
                ],
                ondblClickRow: function(){
                    doUpdate();
                }
            });
            $("#saveBut").on("click", function () {
                Global.Form.submit("page_form", "${ctx}/admin/workType12/doIsTechUpdate", null, function(ret){
                    if(ret.rs==true){
                        art.dialog({
                            content: ret.msg,
                            time: 700,
                            beforeunload: function () {
                                closeWin();
                            }
                        });
                    }else{
                        $("#_form_token_uniq_id").val(ret.token.token);
                        art.dialog({
                            content: ret.msg,
                        });
                    }
                }); 
            });
            $("#addItem").on("click", function () {
                Global.Dialog.showDialog("addItem", {
                    id:"addItem",
                    title : "工种分类12--新增工艺信息",
                    url : "${ctx}/admin/workType12/goTechItem",
                    postData : {code:"${workType12.code}"},
                    height : 251,
                    width : 433,
                    returnFun : goto_query
                });
            });
            $("#updateItem").on("click", function () {
                doUpdate();
            });
            $("#deleteItem").on("click", function () {
                var url = "${ctx}/admin/workType12/doTechDelete";
                beforeDel(url,"code","删除该工艺信息");
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
                title : "工种分类12--更新工艺信息",
                url : "${ctx}/admin/workType12/goTechItem",
                postData : {m_umState:"M",code:"${workType12.code}",techCode:ret.code},
                height : 251,
                width : 433,
                returnFun : goto_query
            });
        }
    </script>
</body>
</html>
