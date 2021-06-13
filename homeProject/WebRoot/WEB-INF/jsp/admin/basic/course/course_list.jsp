<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function() {
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
    Global.Dialog.showDialog("courseAdd", {
        title : "课程管理-新增",
        url : "${ctx}/admin/course/goAddCourse",
        height : 700,
        width : 1280,
        returnFun : goto_query
    });
}

function update() {
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content:"请选择一条记录"
        });
        return;
    }
    Global.Dialog.showDialog("courseUpdate", {
        title : "课程管理--编辑",
        url : "${ctx}/admin/course/goUpdateCourse?code=" + ret.code,
        height : 700,
        width : 1300,
        returnFun : goto_query
    });  
}

function queryScore(){
    Global.Dialog.showDialog("courseQueryScore", {
        title : "课程管理--成绩查询",
        url : "${ctx}/admin/course/goQueryScore",
        height : 700,
        width : 1400
    });
}

function view() {
    var ret = selectDataTableRow();
    if (ret) {
        Global.Dialog.showDialog("courseView", {
            title : "课程管理--查看",
            url : "${ctx}/admin/course/goViewCourse?code=" + ret.code,
            height : 700,
            width : 1300
        });
    } else {
        art.dialog({
            content : "请选择一条记录"
        });
    }
}

    //导出EXCEL
    function doExcel() {
        var url = "${ctx}/admin/course/doExcel";
        doExcelAll(url);
    }

    /**初始化表格*/
    $(function() {
        Global.JqGrid.initJqGrid("dataTable", {
            url : "${ctx}/admin/course/goJqGrid",
            height : $(document).height() - $("#content-list").offset().top
                    - 90,
            styleUI : 'Bootstrap',
            colModel : [
                    {name: "code", index: "code", width: 70, label: "编号", sortable: true, align: "left"},
                    {name: "coursetype", index: "coursetype", width: 70, label: "课程类型", sortable: true, align: "left"},
                    {name: "nums", index: "nums", width: 70, label: "课程期数", sortable: true, align: "right"},
                    {name: "numsdescr", index: "numsdescr", width: 110, label: "课程期数名称", sortable: true, align: "left"},
                    {name: "begindate", index: "begindate", width: 140, label: "开始时间", sortable: true, align: "left",formatter: formatTime},
                    {name: "enddate", index: "enddate", width: 140, label: "结束时间", sortable: true, align: "left",formatter: formatTime},
                    {name: "remark", index: "remark", width: 150, label: "备注", sortable: true, align: "left"},
                    {name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left",formatter: formatTime},
                    {name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后更新人员", sortable: true, align: "left"},
                    {name: "expired", index: "expired", width: 80, label: "过期标志", sortable: true, align: "left"},
                    {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
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
                    <li>
                        <label>课程编号</label>
                        <input type="text" id="code" name="code" style="width:160px;" />
                    </li>
                     <li>
                        <label>课程类型</label>
                        <house:dict id="courseTypeName" dictCode="" sql=" select Code,Descr from tCourseType where expired = 'F' order by Code " 
                                    sqlValueKey="Descr" sqlLableKey="Descr" />
                    </li>
                    <li>
                        <label>期数</label>
                        <input type="text" id="nums" name="nums" style="width:160px;" />
                    </li>
                    <li>
                        <label>开课时间</label>
                        <input type="text" id="earliestBeginDate"
                            name="earliestBeginDate" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                            style="width:160px;" />
                    </li>
                    <li>
                        <label>至</label>
                        <input type="text" id="lastestBeginDate"
                            name="lastestBeginDate" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                            style="width:160px;" />
                    </li>
                    
                    <li>
                        <label>课程名称</label>
                        <input type="text" id="descr" name="descr" style="width:160px;" />
                    </li>
                    <li class="search-group"><input type="checkbox" id="expired"
                        name="expired"
                        onclick="hideExpired(this)" ${endProfPer.expired!='T' ?'checked':'' }/>
                        <p>隐藏过期</p>
                        <button type="button" class="btn  btn-sm btn-system "
                            onclick="goto_query();">查询</button>
                        <button type="button" class="btn btn-sm btn-system "
                            onclick="clearForm();">清空</button></li>
                </ul>
            </form>
        </div>
        <div class="btn-panel">
            <div class="btn-group-sm">
                <button type="button" class="btn btn-system " onclick="add()">新增</button>
                <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                <button type="button" class="btn btn-system " onclick="queryScore()">成绩查询</button>
                <house:authorize authCode="COURSE_VIEW">
                    <button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
                </house:authorize>
                <button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
            <div id="dataTablePager"></div>
        </div>
    </div>
</body>
</html>
