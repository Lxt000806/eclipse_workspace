<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理--成绩查询</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    $("#empCode").openComponent_employee();
        function getData(data){
            if (!data) return;
            validateRefresh('openComponent_customer_custCode');
        }
    
    Global.JqGrid.initJqGrid("dataTable", {
            url : "${ctx}/admin/course/doQueryScore",
            height : $(document).height() - $("#content-list").offset().top - 100,
            styleUI : 'Bootstrap',
            colModel : [
                    {name:"numsdescr", index:"numsdescr", width:120, label:"课程期数名称", sortable:true, align:"left"},
                    {name:"number", index:"number", width:80, label:"员工编号", sortable:true, align:"right"},
                    {name: "namechi", index: "nameChi", width: 70, label: "姓名", sortable: true, align: "left"},
                    {name: "gender", index: "gender", width: 70, label: "性别", sortable: true, align: "left"},
                    {name: "department1descr", index: "department1Descr", width: 80, label: "一级部门", sortable: true, align: "left"},
                    {name: "department2descr", index: "department2Descr", width: 80, label: "二级部门", sortable: true, align: "left"},
                    {name: "department3descr", index: "department3Descr", width: 80, label: "三级部门", sortable: true, align: "left"},
                    {name: "positionname", index: "positionName", width: 80, label: "职位", sortable: true, align: "left"},
                    {name: "joindate", index: "joinDate", width: 120, label: "加入日期", sortable: true, align: "left", formatter:formatTime},
                    {name: "phone", index: "phone", width: 80, label: "电话", sortable: true, align: "right"},
                    {name: "score", index: "score", width: 60, label: "成绩", sortable: true, align: "right"},
                    {name: "ispass", index: "isPass", hidden:true},
                    {name: "ispassdescr", index: "isPassDescr", width: 70, label: "是否毕业", sortable: true, align: "left"},
                    {name: "ismakeup", index: "isMakeUp", hidden:true},
                    {name: "ismakeupdescr", index: "isMakeUpDescr", width: 70, label: "是否补考", sortable: true, align: "left"},
                    {name: "makeupscore", index: "makeUpScore", width: 70, label: "补考成绩", sortable: true, align: "right"},
                    {name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"}
            ]
    });
});

//导出成绩明细到Excel
function doExcel(){
    var url = "${ctx}/admin/course/doScoreDetailInfoExcel";
    doExcelAll(url);
}
</script>
</head>
<body>
<div class="body-box-list">
        <div class="panel panel-system">
           <div class="panel-body">
               <div class="btn-group-xs">
                   <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
                       <span>关闭</span>
                   </button>
               </div>
           </div>
        </div>
        <div class="query-form">
            <form action="" method="post" id="page_form" class="form-search">
                <input type="hidden" name="jsonString" value="" />
                <ul class="ul-form">
                    <li>
                        <label>课程类型</label>
                        <house:dict id="courseType" dictCode="" sql="select Code,Descr from tCourseType where expired = 'F' order by Code" 
                                sqlValueKey="Code" sqlLableKey="Descr" style="width:160px;" />
                    </li>
                    <li>
                        <label>开课时间</label>
                        <input type="text" id="earliestBeginDate"
                            name="earliestBeginDate" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;" />
                    </li>
                    <li>
                        <label>至</label>
                        <input type="text" id="lastestBeginDate"
                            name="lastestBeginDate" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:160px;" />
                    </li>
                    <li>
                        <label>是否毕业</label>
                        <select name="isPass" style="width:160px;">
                            <option></option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </li>
                    <li>
                        <label>期数</label>
                        <input type="text" id="nums" name="nums" style="width:160px;" />
                    </li>
                    <li>
                        <label>课程名称</label>
                        <input type="text" id="courseDescr" name="courseDescr" style="width:160px;" />
                    </li>
                    <li>
                        <label>员工</label>
                        <input type="text" id="empCode" name="empCode" style="width:160px;" />
                    </li>
                    <li>
                        <label>是否补考</label>
                        <select name="isMakeUp" style="width:160px;">
                            <option></option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </li>
                    <li class="search-group">
                        <input type="checkbox" id="expired" name="expired" onclick="hideExpired(this)" checked="checked" />
                        <p>隐藏过期</p>
                        <button type="button" class="btn  btn-sm btn-system "
                            onclick="goto_query();">查询</button>
                        <button type="button" class="btn btn-sm btn-system "
                            onclick="clearForm();">清空</button></li>
                </ul>
            </form>
        </div>
        <div class="clear_float"></div>
    </div>
    <form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-list">
            <div>
                <ul class="ul-form">
                    <div class="btn-panel">
                        <div class="btn-group-sm">
                            <button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
                        </div>
                    </div>
                    <div class="tab-content">
                        <div id="content-list">
                            <table id="dataTable"></table>
                            <div id="dataTablePager"></div>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
    </form>
</body>
</html>
