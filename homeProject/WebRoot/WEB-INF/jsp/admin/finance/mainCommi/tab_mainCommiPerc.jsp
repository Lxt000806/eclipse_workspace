<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/mainCommiPerc/goJqGrid",
			postData:postData,
			sortable: true,
			height: 390,
			colModel: [
				{name: "code", index: "code", width: 70, label: "职位编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 70, label: "职位名称", sortable: true, align: "left"},
				{name: "commiperc", index: "commiperc", width: 70, label: "提成比例", sortable: true, align: "right"},
				{name:"lastupdate", index:"lastupdate", width:120, label:"最后修改时间", sortable:true, align:"left", formatter:formatTime},
				{name:"lastupdatedby", index:"lastupdatedby", width:70, label:"修改人", sortable:true, align:"left"},
				{name:"expired", index:"expired", width:80, label:"是否过期", sortable:true, align:"left"},
				{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"},
			],
			ondblClickRow: function(){
				view();
			}
		});
		$("#save").on("click", function() {
			Global.Dialog.showDialog("save", {
				title : "主材非独立销售提成比例设置——新增",
				url : "${ctx}/admin/mainCommiPerc/goSave",
				height:450,
				width : 445,
				returnFun : goto_query
			});
		});
		$("#copy").on("click", function() {
			var ret=selectDataTableRow("dataTable");
			dataTable
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("copy", {
				title : "主材非独立销售提成比例设置——复制",
				url : "${ctx}/admin/mainCommiPerc/goSave",
				postData:{
					id:ret.code
				},
				height:250,
				width :440,
				returnFun : goto_query
			});
		});
		
		$("#update").on("click",function(){
			var ret=selectDataTableRow("dataTable");
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"主材非独立销售提成比例设置——编辑",
				url:"${ctx}/admin/mainCommiPerc/goUpdate",
				postData:{
					id:ret.code
				},
				height:250,
				width :440,
				returnFun:goto_query
			});
		});
		
		$("#delete").on("click",function(){
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
						url:"${ctx}/admin/mainCommiPerc/doDelete?id="+ret.code,
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
					    		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
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
		}); 	
		
	});
	function view(){
		var ret=selectDataTableRow("dataTable");
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"主材非独立销售提成比例设置——查看",
			url:"${ctx}/admin/mainCommiPerc/goDetail",
			postData:{
				id:ret.code
			},
			height:250,
			width :440,
			returnFun:goto_query
		});
	}
	function doExcel(){
		var url = "${ctx}/admin/mainCommiPerc/doExcel";
		doExcelAll(url);
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="page_form2" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="copy">
						<span>复制</span>
					</button>
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" id="delete">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="dataTable"></table>
	</div>
</div>



