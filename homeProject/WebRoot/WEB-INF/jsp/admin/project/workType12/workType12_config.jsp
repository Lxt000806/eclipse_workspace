<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>配置工种施工需准备材料</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    Global.JqGrid.initJqGrid("dataTable", {
            url : "${ctx}/admin/workType12/getWorkType12Item?id=" + "${workType12.code}",
            height : $(document).height() - $("#content-list").offset().top - 100,
            styleUI : 'Bootstrap',
            rowNum:-1,
            colModel : [
                    {name:"pk", index:"pk", hidden:true},
                    {name:"itemType1Name", index:"itemType1Name", width:100, label:"材料类型1", sortable:true, align:"left"},
                    {name: "itemType2Name", index: "itemType2Name", width: 100, label: "材料类型2", sortable: true, align: "left"},
                    {name: "itemType3Name", index: "itemType3Name", width: 100, label: "材料类型3", sortable: true, align: "left"},
                    {name: "appType", index: "appType", width:90, label: "申请类型", sortable: true, align:"left"},
                    {name: "lastAppDay", index: "lastAppDay", width:170, label: "最后申请日与今天差几天", sortable: true, align:"right"},
                    {name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
                    {name: "lastUpdate", index: "lastUpdate", width: 120, label: "最后更新时间", sortable: true, align: "right", formatter:formatTime},
                    {name: "lastUpdatedBy", index: "lastUpdatedBy", width: 60, label: "修改人", sortable: true, align: "left"},
                    {name: "actionLog", index: "actactionLogionlog", width: 60, label: "操作", sortable: true, align: "left"}
            ]
    });
});

//更新工种施工需准备材料
function goUpdateWorkType12Item(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content:"请选择一条记录"
        });
        return;
    }
    Global.Dialog.showDialog("updateBefWorkType12", {
            id:"updateBefWorkType12",
            title : "工种分类12--编辑工种施工需准备材料",
            url : "${ctx}/admin/workType12/goUpdateWorkType12Item?pk=" + ret.pk,
            height : 400,
            width : 500,
            returnFun : goto_query
     });
}

//删除指定的工种施工需准备材料
function deleteWorkType12Item(pk){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content:"请选择一条记录"
        });
        return;
    }
    art.dialog(
        {
            content:"是否删除施工需准备材料"
        },
	    function(){
	        var xhr = new XMLHttpRequest();
	        var url = "${ctx}/admin/workType12/doDeleteWorkType12Item?pk=" + ret.pk;
	        xhr.open("get", url, true);
	        xhr.onload = function(){
	            if(xhr.responseText === "success"){
		                $("#dataTable").jqGrid('setGridParam').trigger("reloadGrid"); //重新载入    
	            }else{
	                art.dialog({
	                    content:"删除工种施工需准备材料失败，请重试"
	                });
	            }
	        }
	        xhr.send(null);
	    },
	    true
	 );
}

//跳转到新增工种施工准备材料页面
function goAddWorkType12Item(){
    Global.Dialog.showDialog("addWorkType12Item", {
            id:"addWorkType12Item",
            title : "工种分类12--新增施工需准备的材料",
            url : "${ctx}/admin/workType12/goAddWorkType12Item?id=${workType12.code}",
            height : 400,
            width : 500,
            returnFun : goto_query
        });
}
</script>
</head>
<body>
    <form action="" method="post" id="dataForm" class="form-search">
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
            <div class="panel panel-info">
                <div class="panel-body">
                    <input type="hidden" name="jsonString" value="" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:100px;">编号</label>
                            <input type="text" id="WorkType12Code" maxlength="10" name="WorkType12Code"
                                style="width:160px;" value="${workType12.code}"
                                readonly="readonly" />
                            </li>
                        </div>
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:100px;">名称</label>
                            <input type="text" id="descr" maxlength="10" name="descr"
                                style="width:160px;" value="${workType12.descr}"
                                readonly="readonly" />
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
                            <button type="button" class="btn btn-system " onclick="goAddWorkType12Item()">新增</button>
                            <button type="button" class="btn btn-system " onclick="goUpdateWorkType12Item()">编辑</button>
                            <button type="button" class="btn btn-system " onclick="deleteWorkType12Item()">删除</button>
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
</body>
</html>
