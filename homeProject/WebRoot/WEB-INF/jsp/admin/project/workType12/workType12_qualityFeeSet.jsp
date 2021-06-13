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
    <title>质保金配置</title>
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
                url : "${ctx}/admin/workType12/goQualityJqGrid",
                postData : postData,
                height : $(document).height() - $("#content-list").offset().top - 36,
                styleUI : 'Bootstrap',
                rowNum : -1,
                colModel : [
                	{name:"worktype12", index:"worktype12", width:80, label:"工种分类12", sortable:true, align:"left", hidden: true},
                	{name:"workerclassify", index:"workerclassify", width:80, label:"工人分类", sortable:true, align:"left", hidden: true},
                    {name:"workerclassifydescr", index:"workerclassifydescr", width:80, label:"工人分类", sortable:true, align:"left"},
                    {name:"signqualityfee", index:"signqualityfee", width:120, label:"签约质保金总额", sortable:true, align:"right"},
                    {name:"qualityfeebegin", index:"qualityfeebegin", width:120, label:"质保金起扣点", sortable:true, align:"right"},
                    {name:"perqualityfee", index:"perqualityfee", width:130, label:"签约每工地扣质保金", sortable:true, align:"right"},
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
                    title : "质保金配置--新增",
                    url : "${ctx}/admin/workType12/goQltAdd",
                    postData : {code:"${workType12.code}"},
                    height : 300,
                    width : 433,
                    returnFun : goto_query
                });
            });
            $("#updateItem").on("click", function () {
                doUpdate();
            });
            $("#deleteItem").on("click", function () {
            	var ret = selectDataTableRow();
	            if(!ret){
	                art.dialog({
	                    content:"请选择一条记录"
	                });
	                return;
	            }
		        art.dialog({
					 content:'您确定要删除吗？',
					 lock: true,
					 ok: function () {
				        $.ajax({
							url : "${ctx}/admin/workType12/doDeleteQlt",
							data : {code:"${workType12.code}",workerClassify:ret.workerclassify},
							type: 'post',
							cache: false,
							error: function(){
						        art.dialog({
									content: '删除出现异常'
								});
						    },
						    success: function(obj){
						    	if(obj.rs){
						    		art.dialog({
										content: '删除成功',
										time: 1000,
										beforeunload: function () {
											goto_query();
									    }
									});
						    	}else{
						    		art.dialog({
										content: '删除失败'
									});
						    	}
							}
						});
				    	return true;
					},
					cancel: function () {
						return true;
					}
				});
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
                title : "质保金配置--编辑",
                url : "${ctx}/admin/workType12/goQltUpdate",
                postData : {m_umState:"M",map:JSON.stringify(ret)},
                height : 300,
                width : 433,
                returnFun : goto_query
            });
        }
    </script>
</body>
</html>
