<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>社保公积金参数管理--人员名单</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" method="post" target="targetFrame">
            <input type="hidden" id="socialInsurParam" name="socialInsurParam" value="${pk}"/>
            <ul class="ul-form">
                <li>
                    <label>查询条件</label>
                    <input type="text" id="queryCondition" name="queryCondition" placeholder="姓名/工号/身份证/财务编码"/>
                </li>
                <li>
                    <label>一级部门</label>
                    <house:DictMulitSelect id="department1" dictCode="" sqlLableKey="desc1" sqlValueKey="code"
                        sql="select code, desc1 from tDepartment1 where Expired='F'"></house:DictMulitSelect>
                </li>
                <li>
                    <label>人员状态</label>
                    <house:xtdm id="status" dictCode="EMPSTS" value="ACT"></house:xtdm>
                </li>
                <li>
                    <button type="button" class="btn btn-system btn-sm" onclick="goto_query()">查询</button>
                    <button type="button" class="btn btn-system btn-sm" onclick="clearForm()">清空</button>
                </li>
            </ul>
        </form>
    </div>

    <div id="content-list">
        <table id="dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>
<script type="text/javascript">

    $(function() {

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/salaryEmp/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "empname", index: "empname", width: 80, label: "姓名", sortable: true, align: "left"},
                {name: "idnum", index: "idnum", width: 120, label: "身份证号", sortable: true, align: "right"},
                {name: "empcode", index: "empcode", width: 80, label: "工号", sortable: true, align: "right"},
                {name: "department1descr", index: "department1descr", width: 100, label: "一级部门", sortable: true, align: "right"},
                {name: "posiclassdescr", index: "posiclassdescr", width: 100, label: "岗位类别", sortable: true, align: "right"},
                {name: "posileveldescr", index: "posileveldescr", width: 100, label: "岗位级别", sortable: true, align: "right"},
                {name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "right"},
                {name: "joindate", index: "joindate", width: 80, label: "入职日期", sortable: true, align: "left", formatter: formatDate},
                {name: "regulardate", index: "regulardate", width: 80, label: "转正日期", sortable: true, align: "left", formatter: formatDate},
                {name: "leavedate", index: "leavedate", width: 80, label: "离职日期", sortable: true, align: "left", formatter: formatDate},
                {name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
            ],
        })

    })
    
    function clearForm() {
        $("#page_form input[type=text]").val('')
        $("#page_form select").val('')
        
        $("#department1").val('');
        $.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false)
    }

</script>
</body>
</html>
