<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>部门人员</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("departmentAdd",{
		  title:"添加部门人员",
		  url:"${ctx}/admin/department/goDepEmpAdd",
		  postData:{code:"${department.code}"},
		  height: 700,
		  width:1000,
		  returnFun: goto_query
	});
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/department/goEmpJqGrid",
			postData:{code:"${department.code}",expired:"F"},
			height:500,
			colModel : [
			  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",hidden:true},
			  {name : 'department',index : 'department',width : 80,label:'部门编号',sortable : true,align : "left"},
			  {name : 'departmentdesc2',index : 'departmentdesc2',width : 80,label:'部门名称',sortable : true,align : "left"},
			  {name : 'empcode',index : 'cmpcode',width : 80,label:'人员编号',sortable : true,align : "left"},
		      {name : 'empname',index : 'desc2',width : 100,label:'人员姓名',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'过期标志',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"},
            ],
            multiselect: true,
		});
});
function del(){
	var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
	var numbers="";
	var pks="";
	if(ids.length == 0){
		art.dialog({
			content:"请至少选择一条记录"
		});				
		return ;
	}
	$.each(ids,function(i,id){
		var rowData = $("#dataTable").jqGrid("getRowData", id);
		if(numbers != ""){
			numbers += ",";
		}
		numbers += rowData.empcode;
		if(pks != ""){
			pks += ",";
		}
		pks += rowData.pk;
	}); 
	 art.dialog({
		 content:'您确定要删除吗？',
		 lock: true,
		 ok: function () {
	        $.ajax({
				url:"${ctx}/admin/department/doSave",
				type: 'post',
				data: {empPk:pks,code:"${department.code}",m_umState:"ED",empCode:numbers},
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		goto_query();
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
						});
			    	}
			    }
			 });
	    	return true;
		},
		cancel: function () {
			return true;
		}
	}); 
}
function changeExpired(){
	var expired="";
	if($("#expired").is(':checked')){
		expired="F";
	}else{
		expired="T";
	}
	$("#dataTable").jqGrid("setGridParam", {
	  	url:"${ctx}/admin/department/goEmpJqGrid",
	  	postData:{code:"${department.code}",expired:expired},
	}).trigger("reloadGrid");
}
</script>
</head>
    
<body>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
					<c:if test="${department.isActual.trim()=='0'}">
						<button type="button" class="btn btn-system" id="save"
							onclick="add()">
							<span>新增</span>
						</button>
						<button type="button" class="btn btn-system" id="del"
							onclick="del()">
							<span>删除</span>
						</button>
					</c:if>
					<button type="button" class="btn btn-system" id="close"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					<input type="checkbox" id="expired"  style="position:absolute;left:185px;top:5px;" 
					name="expired" value="F" onclick="changeExpired();" checked }/>
					<p style=" position:absolute;left:200px;top:10px;">隐藏过期</p>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


