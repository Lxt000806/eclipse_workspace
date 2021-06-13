<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>材料毛利率调整</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
    $("#custCode").openComponent_customer({showLabel:"${endProfPer.custDescr}",showValue:"${endProfPer.custCode}",condition:{status:"5"} ,callBack: getData});
    function getData(data){
        if (!data) return;
        validateRefresh('openComponent_customer_custCode'); 
    }
    $("#dataForm").bootstrapValidator("addField", "openComponent_customer_custCode", {  
         validators: {  
             remote: {
                 message: '',
                 url: '${ctx}/admin/customer/getCustomer',
                 data: getValidateVal,
                 delay: 2 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
             }
         }  
     });
});

function add() {
    Global.Dialog.showDialog("endProfPerAdd", {
        title : "材料毛利率-新增",
        url : "${ctx}/admin/endProfPer/goAdd",
        height : 550,
        width : 600,
        returnFun : goto_query
    });
}
function copy() {
    var ret = selectDataTableRow();
    if (ret) {
        Global.Dialog.showDialog("endProfPerCopy", {
            title : "材料毛利率--复制",
            url : "${ctx}/admin/endProfPer/goCopy?code=" + ret.code,
            height : 550,
            width : 600,
            returnFun : goto_query
        });
    } else {
        art.dialog({
            content : "请选择一条记录"
        });
    }
}
function update() {
    var ret = selectDataTableRow();
    if (ret) {
        Global.Dialog.showDialog("endProfPerUpdate", {
            title : "材料毛利率--编辑",
            url : "${ctx}/admin/endProfPer/goUpdate?code=" + ret.code,
            height : 550,
            width : 600,
            returnFun : goto_query
        });
    } else {
        art.dialog({
            content : "请选择一条记录"
        });
    }
}

function view() {
    var ret = selectDataTableRow();
    if (ret) {
        Global.Dialog.showDialog("endProfPer", {
            title : "材料毛利率--查看",
            url : "${ctx}/admin/endProfPer/goDetail?code=" + ret.code,
            height : 550,
            width : 600,
        });
    } else {
        art.dialog({
            content : "请选择一条记录"
        });
    }
}

    //导出EXCEL
    function doExcel() {
        var url = "${ctx}/admin/endProfPer/doExcel";
        doExcelAll(url);
    }
    
    //从EXCEL导入
    function importExcel(){ 
        Global.Dialog.showDialog("Add",{            
          title:"材料毛利率--从execl导入",
          url:"${ctx}/admin/endProfPer/goLoad",
          height: 700,
          width:1200,
          returnFun: goto_query
        });
    }
    
    /**初始化表格*/
    $(function() {
        Global.JqGrid.initJqGrid("dataTable", {
            url : "${ctx}/admin/endProfPer/goJqGrid",
            height : $(document).height() - $("#content-list").offset().top
                    - 90,
            styleUI : 'Bootstrap',
            colModel : [
                    {name: "code", index: "code", width: 80, label: "客户编号", sortable: true, align: "left"},
                    {name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
                    {name: "layoutdescr", index: "layoutdescr", width: 170, label: "楼盘", sortable: true, align: "left"},
                    {name: "mainproper", index: "mainproper", width: 80, label: "主材毛利率", sortable: true, align: "right"},
                    {name: "servproper", index: "servproper", width: 115, label: "服务性产品毛利率", sortable: true, align: "right"},
                    {name: "intproper", index: "intproper", width: 110, label: "集成橱柜毛利率", sortable: true, align: "right"},
                    {name: "softproper", index: "softproper", width: 80, label: "软装毛利率", sortable: true, align: "right"},
                    {name: "curtainproper", index: "curtainproper", width: 80, label: "窗帘毛利率", sortable: true, align: "right"},
                    {name: "furnitureproper", index: "furnitureproper", width: 100, label: "家具毛利率", sortable: true, align: "right"},
                    {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "right",formatter: formatTime},
                    {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
                    {name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
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
                <ul class="ul-form">
                    <li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
                        style="width:160px;" value="${endProfPer.custCode}" />
                    </li>
                     <li><label>客户名称</label> <input type="text" id="custDescr"
                        name="custDescr" style="width:160px;" value="${endProfPer.custDescr}" />
                    </li>
                    
                    <li><label>楼盘</label> <input type="text" id="custAddress"
                        name="custAddress" style="width:160px;" value="${endProfPer.custAddress}" />
                    </li>
                    <li class="search-group"><input type="checkbox" id="expired"
                        name="expired" value="${endProfPer.expired}"
                        onclick="hideExpired(this)" ${endProfPer.expired!='T' ?'checked':'' }/>
                        <p>隐藏过期</p>
                        <button type="button" class="btn  btn-sm btn-system "
                            onclick="goto_query();">查询</button>
                        <button type="button" class="btn btn-sm btn-system "
                            onclick="clearForm();">清空</button></li>
                </ul>
            </form>
        </div>

        <div class="clear_float"></div>
        <div class="btn-panel">
            <div class="btn-group-sm">
                <button type="button" class="btn btn-system " onclick="add()">新增</button>
                <button id="btnDel" type="button" class="btn btn-system "
                    onclick="copy()">复制</button>
                <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                <house:authorize authCode="ENDPROFPER_VIEW">
                    <button id="btnView" type="button" class="btn btn-system "
                        onclick="view()">查看</button>
                </house:authorize>
                <button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
                <house:authorize authCode="ENDPROFPER_Excel">
                    <button type="button" class="btn btn-system " onclick="importExcel()">从Excel导入</button>
                </house:authorize>

            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
            <div id="dataTablePager"></div>
        </div>
    </div>
</body>
</html>
