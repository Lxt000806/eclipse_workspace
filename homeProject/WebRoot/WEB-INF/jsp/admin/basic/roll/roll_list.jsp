<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>角色列表</title>
    <%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
    Global.Dialog.showDialog("positionAdd",{
          title:"添加角色",
          url:"${ctx}/admin/roll/goSave",
          height: 400,
          width:400,
          returnFun: goto_query
        });
}

function update(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("positionUpdate",{
          title:"修改角色",
          url:"${ctx}/admin/roll/goUpdate?id="+ret.code,
          height:450,
          width:400,
          returnFun: goto_query
        });
    } else {
        art.dialog({
            content: "请选择一条记录"
        });
    }
}

function copy(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("rollCopy",{
          title:"复制角色",
          url:"${ctx}/admin/roll/goSave?id="+ret.code,
          height:400,
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
      Global.Dialog.showDialog("rollView",{
          title:"查看角色",
          url:"${ctx}/admin/roll/goDetail?id="+ret.code,
          height:400,
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
    var url = "${ctx}/admin/roll/doExcel";
    doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/roll/goJqGrid",
            height:$(document).height()-$("#content-list").offset().top-100,
            styleUI:"Bootstrap",
            colModel : [
              {name : 'code',index : 'code',width : 100,label:'角色编码',sortable : true,align : "left"},
              {name : 'descr',index : 'descr',width : 100,label:'角色名称',sortable : true,align : "left"},
              {name : 'childcodedescr',index : 'childcode',width : 100,label:'下级角色',sortable : true,align : "left"},
              {name : 'department1descr',index : 'department1',width : 100,label:'默认一级部门',sortable : true,align : "left"},
              {name : 'department2descr',index : 'department2',width : 100,label:'默认二级部门',sortable : true,align : "left"},
              {name : 'department3descr',index : 'department3',width : 100,label:'默认三级部门',sortable : true,align : "left"},
              {name : 'issaledescr',index : 'issale',width : 100,label:'是否销售角色',sortable : true,align : "left"},
              {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left", formatter:formatTime},
              {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
              {name : 'expired',index : 'expired',width : 100,label:'过期标志',sortable : true,align : "left"},
              {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
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
                        <label>角色编码</label>
                        <input type="text" id="code" name="code" style="width:160px;"  value="${position.code}" />
                    </li>
                     <li>
                        <label>角色名称</label>
                        <input type="text" id="descr" name="descr" style="width:160px;"  value="${position.desc2}" />
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
                    <button type="button" class="btn btn-system " onclick="copy()">复制</button>
                    <house:authorize authCode="POSITION_VIEW">
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


