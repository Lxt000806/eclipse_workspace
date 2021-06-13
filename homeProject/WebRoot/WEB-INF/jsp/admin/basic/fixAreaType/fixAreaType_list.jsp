<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>装修区域类型列表</title>
    <%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
    Global.Dialog.showDialog("fixAreaTypeAdd",{
        title:"新增装修区域类型",
        url:"${ctx}/admin/fixAreaType/goSave",
        height:300,
        width:400,
        returnFun: goto_query
     });
}

function update(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("profitParaUpdate",{
          title:"修改装修区域类型",
          url:"${ctx}/admin/fixAreaType/goUpdate?code="+ret.Code,
          height:300,
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
      Global.Dialog.showDialog("fixAreaTypeView",{
          title:"查看装修区域类型",
          url:"${ctx}/admin/fixAreaType/goView?code="+ret.Code,
          height:300,
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
    var url = "${ctx}/admin/fixAreaType/doExcel";
    doExcelAll(url);
}

/**初始化表格*/
$(function(){
        //初始化表格
        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/fixAreaType/goJqGrid",
            height:$(document).height()-$("#content-list").offset().top-100,
            styleUI:"Bootstrap",
            rowNum:100,
            colModel : [
              {name:'Code',index:'Code', width:50, label:"编号", sortable:true, align:"left"},
              {name:'Descr',index : 'Descr',width : 110,label:'名称',sortable : true,align : "left"},
              {name : 'LastUpdate',index : 'LastUpdate',width : 120,label:'最后修改时间',sortable : true,align : "left", formatter:formatTime},
              {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 60,label:'修改人',sortable : true,align : "left"},
              {name : 'Expired',index : 'Expired',width : 70,label:'过期标志',sortable : true,align : "left"},
              {name : 'ActionLog',index : 'ActionLog',width : 50,label:'操作',sortable : true,align : "left"}
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
                        <label>名称</label>
                        <input type="text" id="descr" name="descr" />
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
                    <house:authorize authCode="FIXAREATYPE_VIEW">
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


