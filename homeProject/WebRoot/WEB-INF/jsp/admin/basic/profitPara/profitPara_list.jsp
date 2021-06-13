<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>毛利参数列表</title>
    <%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function update(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("profitParaUpdate",{
          title:"修改毛利参数",
          url:"${ctx}/admin/profitPara/goUpdate?pk="+ret.PK,
          height:650,
          width:750,
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
      Global.Dialog.showDialog("profitParaView",{
          title:"查看毛利参数",
          url:"${ctx}/admin/profitPara/goView?pk="+ret.PK,
          height:650,
          width:750
        });
    } else {
        art.dialog({
            content: "请选择一条记录"
        });
    }
}

//导出EXCEL
function doExcel(){
    var url = "${ctx}/admin/profitPara/doExcel";
    doExcelAll(url);
}

/**初始化表格*/
$(function(){
        //初始化表格
        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/profitPara/goJqGrid",
            height:$(document).height()-$("#content-list").offset().top-100,
            styleUI:"Bootstrap",
            rowNum:100,
            colModel : [
              {name:'PK',index:'PK', width:50, label:"编码", sortable:true, align:"left"},
              {name:'DesignCalPer',index : 'DesignCalPer',width : 110,label:'设计费提成比例',sortable : true,align : "right"},
              {name : 'CostPer',index : 'CostPer',width : 110,label:'预提费用比例',sortable : true,align : "right"},
              {name : 'BaseCalPer',index : 'BaseCalPer',width : 110,label:'基础提成比例',sortable : true,align : "right"},
              {name : 'MainCalPer',index : 'MainCalPer',width : 110,label:'主材提成比例',sortable : true,align : "right"},
              {name : 'ServProPer',index : 'ServProPer',width : 140,label:'服务性产品预估毛利率',sortable : true,align : "right"},
              {name : 'ServCalPer',index : 'ServCalPer',width : 130,label:'服务性产品提成比例',sortable : true,align : "right"},
              {name : 'JobCtrl',index : 'JobCtrl',width : 120,label:'基础发包比例控制',sortable : true,align : "right"},
              {name : 'JobLowPer',index : 'JobLowPer',width : 80,label:'发包低比例',sortable : true,align : "right"},
              {name : 'JobHighPer',index : 'JobHighPer',width : 80,label:'发包高比例',sortable : true,align : "right"},
              {name : 'IntProPer',index : 'IntProPer',width : 110,label:'集成预估毛利率',sortable : true,align : "right"},
              {name : 'IntCalPer',index : 'IntCalPer',width : 90,label:'集成提成比例',sortable : true,align : "right"},
              {name : 'CupProPer',index : 'CupProPer',width : 100,label:'橱柜预估毛利率',sortable : true,align : "right"},
              {name : 'CupCalPer',index : 'CupCalPer',width : 90,label:'橱柜提成比例',sortable : true,align : "right"},
              {name : 'SoftProPer',index : 'SoftProPer',width : 100,label:'软装预估毛利率',sortable : true,align : "right"},
              {name : 'SoftCalPer',index : 'SoftCalPer',width : 90,label:'软装提成比例',sortable : true,align : "right"},
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
                        <input type="text" id="pk" name="pk" />
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
                    <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                    <house:authorize authCode="PROFITPARA_VIEW">
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


