<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>团队信息列表</title>
    <%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
    Global.Dialog.showDialog("positionAdd",{
          title:"团队信息管理--新增",
          url:"${ctx}/admin/team/goSave",
          height: 650,
          width:800,
          returnFun: goto_query
        });
}

function update(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("teamUpdate",{
          title:"修改应聘人员信息",
          url:"${ctx}/admin/team/goUpdate?code="+ret.code,
          height:650,
          width:800,
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
      Global.Dialog.showDialog("teamView",{
          title:"查看团队信息",
          url:"${ctx}/admin/team/goView?code="+ret.code,
          height:650,
          width:800
        });
    } else {
        art.dialog({
            content: "请选择一条记录"
        });
    }
}

//导出EXCEL
function doExcel(){
    var url = "${ctx}/admin/team/doExcel";
    doExcelAll(url);
}

/**初始化表格*/
$(function(){
        //初始化表格
        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/team/goJqGrid",
            height:$(document).height()-$("#content-list").offset().top-100,
            styleUI:"Bootstrap",
            rowNum:100,
            colModel : [
              {name:'code',index:'code', width:50, label:"编号", sortable:true, align:"left"},
              {name:'desc1',index : 'desc1',width : 120,label:'团队名称',sortable : true,align : "left"},
              {name:'iscalcperfdescr',index : 'iscalcperfdescr',width : 100,label:'是否计算业绩',sortable : true,align : "left"},
              {name : 'remark',index : 'remark',width : 750,label:'描述',sortable : true,align : "left"},
              {name : 'lastupdate',index : 'lastupdate',width : 120,label:'最后修改时间',sortable : true,align : "left", formatter:formatTime},
              {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
              {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
              {name : 'actionlog',index : 'actionlog',width : 80,label:'操作标志',sortable : true,align : "left"}
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
                <input type="hidden" id="othersEntering" name="othersEntering" value="T" />
                <ul class="ul-form">
                    <li>
                        <label>编号</label>
                        <input type="text" id="code" name="code" />
                    </li>
                     <li>
                        <label>团队名称</label>
                        <input type="text" id="desc1" name="desc1" />
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
                    <house:authorize authCode="TEAM_VIEW">
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


