<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>课程管理--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
//查看成绩明细信息
function goViewScoreDetailInfo(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog({
            content : "请选择一条记录"
        });
        return;
    }
    var viewUrl = "${ctx}/admin/course/goViewScoreDetailInfo?number=" + ret.number
                    + "&nameChi=" + ret.namechi + "&score=" + ret.score + "&isPass=" + ret.ispass
                    + "&isMakeUp=" + ret.ismakeup + "&makeUpScore=" + ret.makeupscore + "&remark=" + ret.remark;
    Global.Dialog.showDialog("viewScoreDetailInfo", {
        title : "成绩明细信息-查看",
        url : viewUrl,
        height : 400,
        width : 500
    });
}
    
$(function(){
    Global.JqGrid.initJqGrid("dataTable", {
            url : "${ctx}/admin/course/getCourseScore?code=${course.code}",
            height : $(document).height() - $("#content-list").offset().top - 100,
            styleUI : 'Bootstrap',
            rowNum:-1,
            colModel : [
                    {name:"number", index:"number", width:100, label:"员工编号", sortable:true, align:"left"},
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
                    {name: "remark", index: "remark", width: 150, label: "备注", sortable: true, align: "left"}
            ]
    });
});
</script>
</head>
<body>
    <form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="closeBut"
                            onclick="closeWin(true)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-body">
                    <input type="hidden" name="jsonString" value="" />
                    <input type="hidden" name="expired" id="expired" value="${course.expired}" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:100px;"><span
                                    class="required">*</span>课程编号</label> <input type="text" id="code"
                                maxlength="10" name="code" style="width:150px;"
                                readonly="readonly" value="${course.code}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>课程名称</label>
                                <input type="text" id="descr" name="descr" readonly="readonly"
                                    style="width:150px;" value="${course.descr}"/>
                            </li>
                            
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>课程类型</label>
                                <house:dict id="courseType" dictCode="" sql=" select Code,Descr from tCourseType where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Descr" value="${course.courseType.trim()}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>课程期数</label>
                                <input type="text" id="nums" name="nums" style="width:150px;"
                                    readonly="readonly" value="${course.nums}" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>开始时间</label>
                                <input type="text" id="beginDate" name="beginDate" class="i-date"
                                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                    style="width:150px;" value="<fmt:formatDate value="${course.beginDate}" pattern='yyyy-MM-dd' />" />
                            </li>
                            
                            <li class="form-validate">
                                <label style="width:100px;"><span class="required">*</span>结束时间</label>
                                <input type="text" id="endDate" name="endDate" class="i-date"
                                    onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="width:150px;"
                                    value="<fmt:formatDate value="${course.beginDate}" pattern='yyyy-MM-dd' />" />
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;">备注</label>
                                <textarea id="remark" name="remark" style="width:150px;" readonly="readonly">${course.remark}</textarea>
                            </li>
                            <li class="form-validate">
                                <label style="width:100px;"></label>
                                <input type="checkbox" onclick="checkExpired(this)" 
                                    ${course.expired.equals('T')?'checked':''} />过期
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
            <div>
                <ul class="ul-form">
                    <div class="btn-panel">
                        <div class="btn-group-sm">
                            <button type="button" class="btn btn-system " onclick="goViewScoreDetailInfo()">查看</button>
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
