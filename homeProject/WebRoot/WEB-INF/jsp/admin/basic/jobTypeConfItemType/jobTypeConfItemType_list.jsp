<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>任务材料分类对应列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("jobTypeConfItemTypeAdd",{
		  title:"添加任务材料分类对应关系",
		  url:"${ctx}/admin/jobTypeConfItemType/goSave",
		  height: 330,
		  width:400,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("jobTypeConfItemTypeUpdate",{
		  title:"修改任务材料分类对应关系",
		  url:"${ctx}/admin/jobTypeConfItemType/goUpdate?pk="+ret.pk,
		  height:330,
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
      Global.Dialog.showDialog("jobTypeConfItemTypeView",{
		  title:"查看任务材料分类对应关系",
		  url:"${ctx}/admin/jobTypeConfItemType/goDetail?pk="+ret.pk,
		  height:330,
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
	var url = "${ctx}/admin/jobTypeConfItemType/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/jobTypeConfItemType/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI:"Bootstrap",
            rowNum:100,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'编号',sortable : true,align : "left",key : true,hidden:true},
			  {name : 'jobtype',index : 'jobtype',width : 100,label:'任务类型',sortable : true,align : "left",hidden:true},
		      {name : 'jobtypedescr',index : 'jobtypedescr',width : 100,label:'任务类型',sortable : true,align : "left"},
		      {name : 'confitemtype',index : 'confitemtype',width : 100,label:'施工材料类型',sortable : true,align : "left",hidden:true},
		      {name : 'confitemtypedescr',index : 'confitemtypedescr',width : 100,label:'施工材料类型',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'过期标志',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${jobTypeConfItemType.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
                    <li>
                        <label>任务类型</label>
                        <house:dict id="jobType" dictCode="" sql=" select Code,Descr from tJobType where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Descr" />
                    </li>
                    <li>
                        <label>施工材料类型</label>
                        <house:dict id="confItemType" dictCode="" sql=" select Code,Descr from tConfItemType where expired = 'F' order by Code " 
                                    sqlValueKey="Code" sqlLableKey="Descr" />
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
		<div class="clear_float"> </div>
		<!--query-form-->
        <div class="pageContent">
            <!--panelBar-->
            <div class="btn-panel">
                <div class="btn-group-sm">
                    <button type="button" class="btn btn-system " onclick="add()">新增</button>
                    <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                    <house:authorize authCode="JOBDEPART_VIEW">
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


