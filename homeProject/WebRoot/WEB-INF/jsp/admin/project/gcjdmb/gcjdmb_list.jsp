<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>工程进度列表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable", {
				height:$(document).height()-$("#content-list").offset().top-95,
				url:"${ctx}/admin/gcjdmb/goJqGrid",
				postData:$("#page_form").jsonForm(),
				colModel : [			
					{name: "no", index: "no", width: 120, label: "模板编号", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 100, label: "模板名称", sortable: true, align: "left"},
					{name: "typedescr", index: "typedescr", width: 100, label: "类型", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "操作人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作代码", sortable: true, align: "left"}
	            ]
			});
			
		});
		function doExcel(){
			var url = "${ctx}/admin/gcjdmb/doExcel";
			doExcelAll(url);
		}
		function add(){
        	Global.Dialog.showDialog("gcjdmbAdd",{
        		title:"新增工程进度模板",
        	  	url:"${ctx}/admin/gcjdmb/goSave",
        	  	height: 600,
        	  	width:1250,
        	  	returnFun:goto_query
        	});
		}
		function update(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
	        	Global.Dialog.showDialog("gcjdmbUpdate",{
	        		title:"修改工程进度模板",
	        	  	url:"${ctx}/admin/gcjdmb/goUpdate",
	        	  	postData:{
	        	  		no:ret.no
	        	  	},
	        	  	height: 700,
	        	  	width:1250,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function view(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
	        	Global.Dialog.showDialog("gcjdmbView",{
	        		title:"查看工程进度模板",
	        	  	url:"${ctx}/admin/gcjdmb/goView",
	        	  	postData:{
	        	  		no:ret.no
	        	  	},
	        	  	height: 600,
	        	  	width:1250,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value="${data.expired!='T'?'F':'' }"/>
					<ul class="ul-form">
						<li>
							<label>模板编号</label>
							<input type="text" id="no" name="no"/>
						</li>
						<li>
							<label>模板名称</label>
							<input type="text" id="descr" name="descr"/>
						</li>
						<li>
							<label>类型</label>
							<house:xtdm id="type" dictCode="PROGTEMPTYPE"></house:xtdm>
						</li>
					
						<li class="search-group-shrink">
							<input type="checkbox" id="expired_show" name="expired_show"
									value="${data.expired}" onclick="hideExpired(this)"
									${data.expired!='T'?'checked':'' }/><p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>
				</form>
			</div>
			<div class="clear_float"> </div>
			
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button id="addGcjdmb" type="button" class="btn btn-system" onclick="add()">新增</button>
					<button id="updateGcjdmb" type="button" class="btn btn-system" onclick="update()">编辑</button>
	            	<house:authorize authCode="GCJDMB_VIEW">
						<button id="viewGcjdmb" type="button" class="btn btn-system" onclick="view()">查看</button>
	                </house:authorize>
					<button id="excelGcjdmb" type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


