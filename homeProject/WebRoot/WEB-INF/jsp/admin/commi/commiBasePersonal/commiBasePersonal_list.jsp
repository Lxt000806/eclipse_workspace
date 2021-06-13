<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CommiBasePersonal列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("commiBasePersonalAdd",{
		  title:"添加基础个性化提成",
		  url:"${ctx}/admin/commiBasePersonal/goSave",
		  height:270,
		  width:430,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("commiBasePersonalUpdate",{
		  title:"修改基础个性化提成",
		  url:"${ctx}/admin/commiBasePersonal/goUpdate?id="+ret.pk,
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
      Global.Dialog.showDialog("commiBasePersonalView",{
		  title:"查看基础个性化提成",
		  url:"${ctx}/admin/commiBasePersonal/goDetail?id="+ret.pk,
		  height:300,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var ret = selectDataTableRow("dataTable");
	if(!ret){
		art.dialog({
			content:"请选择一条数据 进行删除",
		});
		return;
	}
	art.dialog({
		content: '是删除该记录？',
		lock: true,
		width: 160,
		height: 50,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/commiBasePersonal/doDelete?id="+ret.pk,
				type: "post",
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: "删除成功",
							time: 1000,
						});
						goto_query();
					}else{
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content: obj.msg,
							width: 200
						});
					}
				}
			});
			
		},
		cancel: function () {
			return;
		}
	});
	
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/commiBasePersonal/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiBasePersonal/goJqGrid",
			multiselect: false,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : 'left',hidden:true},
			  {name : 'baseitemtype1',index : 'baseitemtype1',width : 100,label:'基础类型1',sortable : true, align : 'left', hidden:true},
		      {name : 'baseitemtype1descr',index : 'baseitemtype1descr',width : 100,label:'基础类型1',sortable : true, align : 'left'},
		      {name : 'commiper',index : 'commiper',width : 100,label:'提成点',sortable : true,align : 'left'},
		      {name: "lastupdate", index: "lastupdate", width: 142, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			  {name: "lastupdatedby", index: "lastupdatedby", width: 118, label: "修改人", sortable: true, align: "left"},
			  {name: "expired", index: "expired", width: 102, label: "是否过期", sortable: true, align: "left"},
			  {name: "actionlog", index: "actionlog", width: 120, label: "操作", sortable: true, align: "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${commiBasePersonal.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>基装类型1</label>
						<house:dict id="baseItemType1" dictCode="" sql="select code,code+' '+Descr fd from tBaseItemType1 order by DispSeq"
					     sqlLableKey="fd" sqlValueKey="code"></house:dict>
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
				   </li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="btn-panel">
			<!--panelBar-->
			<div class="btn-group-sm">
            	<ul>
					<house:authorize authCode="COMMIBASEPERSONAL_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">新增</button>
					</house:authorize>
					<house:authorize authCode="COMMIBASEPERSONAL_UPDATE">
						<button type="button" class="btn btn-system " onclick="update()">修改</button>
					</house:authorize>
					<house:authorize authCode="COMMIBASEPERSONAL_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<house:authorize authCode="COMMIBASEPERSONAL_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="COMMIBASEPERSONAL_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				</ul>
			 </div>
		</div><!--panelBar end-->
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>

</body>
</html>


