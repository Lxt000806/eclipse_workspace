<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--项目名称</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	var postData = $("#page_form").jsonForm();

        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/builder/goSpcbuilderAddJqGrid",
			postData:postData,
			styleUI: 'Bootstrap',
			height:$(document).height()-$("#content-list").offset().top-70,
			multiselect:true,		
			colModel : [
			  {name : 'Code',index : 'Code',width : 100,label:'项目名称编号',sortable : true,align : "left"},
		      {name : 'Descr',index : 'Descr',width : 200,label:'项目名称',sortable : true,align : "left"},
		      {name : 'Adress',index : 'Adress',width : 200,label:'地址',sortable : true,align : "left"},
		      {name : 'regiondescr1',index : 'regiondescr1',width : 70,label:'区域',sortable : true,align : "left"},
		      {name : 'regiondescr2',index : 'regiondescr2',width : 70,label:'二级区域',sortable : true,align : "left",}
            ],
		});
		
		
	$("#saveBtn").on("click",function(){
       	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
       	if(ids.length==0){
       		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
       		return;
       	}
       	var selectRows = [];
   		$.each(ids,function(k,id){
   			var row = $("#dataTable").jqGrid('getRowData', id);
   			selectRows.push(row);
   		});
   		Global.Dialog.returnData = selectRows;
   		closeWin();
       });
});
</script>
</head>
    
<body>
	<div class="body-box-list">
	<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" id="spcBuilder" name="spcBuilder"  value="${spcBuilder.code }" />
					<ul class="ul-form">
						<li>
							<label>项目名称</label>
							<input type="text" id="descr" name="descr"  value="${builder.descr }" />
						</li>
						<li hidden="true">
							<label>arr</label>
							<input type="text" id="arr" name="arr"  value="${arr}" />
						</li>	
						<li id="loadMore">
						<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm"
							onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


