<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>任务部门列表</title>
    <%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
    Global.Dialog.showDialog("jobDepartAdd",{
        title:"新增任务部门",
        url:"${ctx}/admin/jobDepart/goSave",
        height:350,
        width:400,
        returnFun: goto_query
     });
}

function update(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("jobDepartUpdate",{
          title:"修改任务部门",
          url:"${ctx}/admin/jobDepart/goUpdate?pk="+ret.pk,
          height:350,
          width:400,
          returnFun: goto_query
        });
    } else {
        art.dialog({
            content: "请选择一条记录"
        });
    }
}

function view(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("jobDepartView",{
          title:"查看任务部门",
          url:"${ctx}/admin/jobDepart/goView?pk="+ret.pk,
          height:350,
          width:400
        });
    } else {
        art.dialog({
            content: "请选择一条记录"
        });
    }
}

//导出EXCEL
function doExcel(){
    var url = "${ctx}/admin/jobDepart/doExcel";
    doExcelAll(url);
}

/**初始化表格*/
$(function(){
        //初始化表格
        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/jobDepart/goJqGrid",
            height:$(document).height()-$("#content-list").offset().top-100,
            styleUI:"Bootstrap",
            rowNum:100,
            colModel : [
              {name:'pk',index:'pk', width:50, label:"编号", sortable:true, align:"left"},
              {name:'jobtypedescr',index : 'jobtype',width:90,label:'任务类型',sortable : true,align : "left"},
              {name : 'projectdepartment2descr',index : 'projectdepartment2',width : 100,label:'项目经理部门2',sortable : true,align : "left"},
              {name : 'department2',index : 'department2',width : 100,label:'可选二级部门',sortable : true,align : "left"},
              {name : 'lastupdate',index : 'lastupdate',width : 120,label:'最后修改时间',sortable : true,align : "left", formatter:formatTime},
              {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
              {name : 'expired',index : 'Expired',width : 70,label:'过期标志',sortable : true,align : "left"},
              {name : 'actionlog',index : 'actionlog',width : 50,label:'操作',sortable : true,align : "left"}
            ]
        });
});
</script>
</head>
<body>
    <div class="body-box-list">                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
        <div class="query-form">
            <form action="" method="post" id="page_form" class="form-search">
                <input type="hidden" name="jsonString" value="" />
                <input type="hidden" id="expired" name="expired" value="F" />
                <ul class="ul-form">
                    <li>
                        <label>编号</label>
                        <input type="text" id="pk" name="pk" />
                    </li>
                    <li>
                        <label>项目经理部门2</label>
                        <house:dict id="projectDepartment2" dictCode="" sql=" select Code,Desc2 from tDepartment2 where expired = 'F' and depType = '3' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Desc2" />
                    </li>
                    <li>
                        <label>任务类型</label>
                        <house:dict id="jobType" dictCode="" sql=" select Code,Descr from tJobType where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Descr" />
                    </li>
                    <li>
                        <label>可选二级部门</label>
                        <house:dict id="department2" dictCode="" sql=" select Code,Desc2 from tDepartment2 where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Desc2" />
                    </li>
                    <li class="search-group">
                        <input type="checkbox" onclick="hideExpired(this)" checked="checked" />
                        <p>隐藏过期</p>
                        <button type="button" class="btn  btn-sm btn-system "
                            onclick="goto_query();">查询</button>
                        <button type="button" class="btn btn-sm btn-system "
                            onclick="clearForm();">清空</button></li>
                </ul>
            </form>
        </div>
        <!--query-form-->
        <div class="pageContent">
            <!--panelBar-->
            <div class="btn-panel">
                <div class="btn-group-sm">
                    <button type="button" class="btn btn-system " onclick="add()">新增</button>
                    <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                    <house:authorize authCode="JOBDEPART_VIEW">
                        <button type="button" class="btn btn-system " onclick="view()">查看</button>
                    </house:authorize>
                    <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
                 </div>
             </div>
            <div id="content-list">
                <table id= "dataTable"></table>
                <div id="dataTablePager"></div>
            </div>
        </div> 
    </div>
</body>
</html>


