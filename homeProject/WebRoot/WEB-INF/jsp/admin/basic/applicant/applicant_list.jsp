<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <title>职位列表</title>
    <%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function showOthersEntering(obj){
    if ($(obj).is(':checked')){
        $('#othersEntering').val('F');
    }else{
        $('#othersEntering').val('T');
    }
}

function add(){
    Global.Dialog.showDialog("positionAdd",{
          title:"应聘人员管理--新增",
          url:"${ctx}/admin/applicant/goSave",
          height: 600,
          width:700,
          returnFun: goto_query
        });
}

function update(){
    var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("applicantUpdate",{
          title:"修改应聘人员信息",
          url:"${ctx}/admin/applicant/goUpdate?pk="+ret.pk,
          height:600,
          width:700,
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
      Global.Dialog.showDialog("applicantCopy",{
          title:"复制应聘人员信息",
          url:"${ctx}/admin/applicant/goSave?pk="+ret.pk,
          height:600,
          width:700,
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
      Global.Dialog.showDialog("positionView",{
          title:"查看应聘人员信息",
          url:"${ctx}/admin/applicant/goView?pk="+ret.pk,
          height:600,
          width:700
        });
    } else {
        art.dialog({
            content: "请选择一条记录"
        });
    }
}

//导出EXCEL
function doExcel(){
    var url = "${ctx}/admin/applicant/doExcel";
    doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/applicant/goJqGrid",
            height:$(document).height()-$("#content-list").offset().top-100,
            styleUI:"Bootstrap",
            rowNum:100,
            colModel : [
              {name:'pk',index:'pk', width:50, label:"序号", sortable:true, align:"right"},
              {name:'namechi',index : 'namechi',width : 80,label:'姓名',sortable : true,align : "left"},
              {name : 'genderdescr',index : 'gender',width : 50,label:'性别',sortable : true,align : "left"},
              {name : 'phone',index : 'phone',width : 90,label:'联系电话',sortable : true,align : "left"},
              {name : 'deptdescr',index : 'deptdescr',width : 80,label:'应聘部门',sortable : true,align : "left"},
              {name : 'dept1descr',index : 'dept1descr',width : 80,label:'一级部门',sortable : true,align : "left"},
              {name : 'positiondescr',index : 'positiondescr',width : 80,label:'应聘岗位',sortable : true,align : "left"},
              {name : 'appdate',index : 'appdate',width : 80,label:'面试时间',sortable : true,align : "left", formatter:formatDate},
              {name : 'statusdescr',index : 'status',width : 50,label:'状态',sortable : true,align : "left"},
              {name : 'comeindate',index : 'comeindate',width : 80,label:'预入时间',sortable : true,align : "left", formatter:formatDate},
              {name : 'birtplace',index : 'birtplace',width : 50,label:'籍贯',sortable : true,align : "left"},
              {name : 'idnum',index : 'idnum',width : 130,label:'身份证号',sortable : true,align : "left"},
              {name : 'birth',index : 'birth',width : 80,label:'出生日期',sortable : true,align : "left", formatter:formatDate},
              {name : 'age',index : 'birth',width : 50,label:'年龄',sortable : true,align : "right"},
              {name : 'address',index : 'address',width : 200,label:'地址',sortable : true,align : "left"},
              {name : 'edu',index : 'edu',width : 70,label:'文化程度',sortable : true,align : "left"},
              {name : 'school',index : 'school',width : 120,label:'毕业院校',sortable : true,align : "left"},
              {name : 'schdept',index : 'schdept',width : 100,label:'专业',sortable : true,align : "left"},
              {name : 'source',index : 'source',width : 80,label:'途径',sortable : true,align : "left"},
              {name : 'remarks',index : 'remarks',width : 120,label:'备注',sortable : true,align : "left"},
              {name : 'lastupdate',index : 'lastupdate',width : 120,label:'录入时间',sortable : true,align : "left", formatter:formatTime},
              {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'录入人',sortable : true,align : "left"},
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
                        <label>序号</label>
                        <input type="text" id="pk" name="pk" style="width:160px;" />
                    </li>
                     <li>
                        <label>姓名</label>
                        <input type="text" id="nameChi" name="nameChi" style="width:160px;" />
                    </li>
                    <li>
                        <label>电话</label>
                        <input type="text" id="phone" name="phone" style="width:160px;" />
                    </li>
                    <li>
                        <label>面试岗位</label>
                        <input type="text" id="positionDescr" name="positionDescr" style="width:160px;" />
                    </li>
                    <li>
                        <label>状态</label>
                        <house:dict dictCode="" id="status" sql="select cbm, note from tXTDM where ID = 'APPLICANTSTS'"
                            sqlLableKey="note" sqlValueKey="cbm" />
                    </li>
                    <li>
                        <label>应聘部门</label>
                        <input type="text" id="deptDescr" name="deptDescr" style="width:160px;" />
                    </li>
                    <li>
                        <label>途径</label>
                        <input type="text" id="source" name="source" style="width:160px;" />
                    </li>
                    <li>
                        <label>应聘日期</label>
                        <input type="text" id="dateTo"
                            name="dateTo" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                            style="width:160px;" />
                    </li>
                    <li>
                        <label>至</label>
                        <input type="text" id="dateFrom"
                            name="dateFrom" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                            style="width:160px;" />
                    </li>
                    <li>
                        <label>预入日期</label>
                        <input type="text" id="earliestComeInDate"
                            name="earliestComeInDate" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                            style="width:160px;" />
                    </li>
                    <li>
                        <label>至</label>
                        <input type="text" id="lastestComeInDate"
                            name="lastestComeInDate" class="i-date"
                            onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                            style="width:160px;" />
                    </li>
                    <li class="search-group">
                        <input type="checkbox" onclick="hideExpired(this)" checked="checked" />
                        <p>隐藏过期</p>
                        <input type="checkbox" onclick="showOthersEntering(this)" style="margin-left:20px;"/>
                        <p>只查看本人录入</p>
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
                    <house:authorize authCode="APPLICANT_ADD">
                        <button type="button" class="btn btn-system " onclick="add()">新增</button>
                    </house:authorize>
                    <house:authorize authCode="APPLICANT_UPDATE">
                        <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                    </house:authorize>
                    <house:authorize authCode="APPLICANT_COPY">
                        <button type="button" class="btn btn-system " onclick="copy()">复制</button>
                    </house:authorize>
                    <house:authorize authCode="APPLICANT_VIEW">
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


