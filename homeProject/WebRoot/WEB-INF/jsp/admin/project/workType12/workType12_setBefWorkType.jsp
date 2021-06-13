<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>设置上一工种</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function(){
    Global.JqGrid.initJqGrid("dataTable", {
            url : "${ctx}/admin/workType12/getBefWorkType12?id=" + "${workType12.code}",
            height : $(document).height() - $("#content-list").offset().top - 100,
            styleUI : 'Bootstrap',
            rowNum:-1,
            colModel : [
                    {name:"BefWorkType12", index:"BefWorkType12", width:100, label:"上一工种编号", sortable:true, align:"left"},
                    {name: "Descr", index: "descr", width: 110, label: "上一工种", sortable: true, align: "left"},
                    {name: "isEval", index: "isEval", width: 80, label: "是否评价", sortable: true, align: "left"},
                    {name: "isNext", index: "isNext", width: 100, label: "是否相邻工种", sortable: true, align: "left"},
                    {name: "WorkerArrCtrlDescr", index: "WorkerArrCtrlDescr", width: 120, label: "安排工人控制完成", sortable: true, align: "left"}
            ]
    });
});

//更新上一工种
function goUpdateBefWorkType12(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content:"请选择一条记录"
        });
        return;
    }
    Global.Dialog.showDialog("updateBefWorkType12", {
            id:"updateBefWorkType12",
            title : "工种分类12--编辑上一工种",
            url : "${ctx}/admin/workType12/goUpdateBefWorkType?id=${workType12.code}&befWorkType12=" + ret.BefWorkType12,
            height : 300,
            width : 400,
            returnFun : goto_query
     });
}



//删除指定的上一工种
function deleteBefWorkType(befWorTypeName, workType12, befWorkTypeId){
    var ret = selectDataTableRow();
    if(ret){
        art.dialog({
            content:"是否删除上一工种：" + ret.Descr
        },
        function(){
            var xhr = new XMLHttpRequest();
            var url = "${ctx}/admin/workType12/doDeleteBefWorkType?workType12Code=${workType12.code}" + "&befWorkType12Code=" + ret.BefWorkType12;
            xhr.open("get", url, true);
            xhr.onload = function(){
                if(xhr.responseText === "success"){
                       $("#dataTable").jqGrid("setGridParam").trigger("reloadGrid"); //重新载入
                }else{
                    art.dialog({
                        content:"删除上一工种：" + befWorTypeName + "失败，请重试"
                    });
                }
            }
            xhr.send(null);
        },
        true);
    }else{
        art.dialog({
            content : "请选择一条记录"
        });
    }
}

//新增上一工种
function goAddBefWorkType12(){
    Global.Dialog.showDialog("addBefWorkType12", {
            id:"addBefWorkType12",
            title : "工种分类12--新增上一工种",
            url : "${ctx}/admin/workType12/goAddBefWorkType?id=${workType12.code}",
            height : 300,
            width : 400,
            returnFun : goto_query
        });
}
</script>
<style type="text/css">
#gbox_dataTable{
    border:none;
}
</style>
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
            <div>
                <ul class="ul-form">
                    <div class="btn-panel">
                        <div class="btn-group-sm">
                            <button type="button" class="btn btn-system " onclick="goAddBefWorkType12()">新增</button>
                            <button type="button" class="btn btn-system " onclick="goUpdateBefWorkType12()">编辑</button>
                            <button type="button" class="btn btn-system " onclick="deleteBefWorkType()">删除</button>
                        </div>
                    </div>
                    <div class="tab-content">
                        <div id="content-list">
                            <table id="dataTable"></table>
                        </div>
                    </div>
                </ul>
            </div>
      </div>
    </form>
</body>
</html>
