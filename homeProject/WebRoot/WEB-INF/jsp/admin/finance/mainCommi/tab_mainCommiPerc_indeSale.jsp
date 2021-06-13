<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="${resourceRoot}/js/iss.core.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("mainCommiPercIndeSale_dataTable", {
			url:"${ctx}/admin/mainCommiPercIndeSale/goJqGrid",
			postData:postData,
			sortable: true,
			height: 390,
			colModel: [
				{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden: true},       
				{name: "code", index: "code", width: 70, label: "职位编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 70, label: "职位名称", sortable: true, align: "left"},
				{name: "saletypedescr", index: "saletypedescr", width: 70, label: "销售类型", sortable: true, align: "left"},
				{name: "saletype", index: "saletype", width: 70, label: "销售类型", sortable: true, align: "left",hidden: true},
				{name: "maincommiperc", index: "maincommiperc", width: 100, label: "主材提成比例", sortable: true, align: "right"},
				{name: "servcommiperc", index: "servcommiperc", width: 100, label: "服务性产品提成比例", sortable: true, align: "right"},
				{name: "eleccommiperc", index: "eleccommiperc", width: 100, label: "电器提成比例", sortable: true, align: "right"},
				{name:"lastupdate", index:"lastupdate", width:120, label:"最后修改时间", sortable:true, align:"left", formatter:formatTime},
				{name:"lastupdatedby", index:"lastupdatedby", width:70, label:"修改人", sortable:true, align:"left"},
				{name:"expired", index:"expired", width:80, label:"是否过期", sortable:true, align:"left"},
				{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"},
			],
			ondblClickRow: function(){
				indeSaleView();
			}
		});
		$("#indeSaleSave").on("click", function() {
			Global.Dialog.showDialog("save", {
				title : "主材独立销售提成比例设置——新增",
				url : "${ctx}/admin/mainCommiPercIndeSale/goSave",
				height:450,
				width : 445,
				returnFun : goto_query
			});
		});
		$("#indeSaleCopy").on("click", function() {
			var ret=selectDataTableRow("mainCommiPercIndeSale_dataTable");
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("copy", {
				title : "主材独立销售提成比例设置——复制",
				url : "${ctx}/admin/mainCommiPercIndeSale/goSave",
				postData:{
					id:ret.pk
				},
				height:450,
				width : 445,
				returnFun : goto_query
			});
		});
		
		$("#indeSaleUpdate").on("click",function(){
			var ret=selectDataTableRow("mainCommiPercIndeSale_dataTable");
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"主材独立销售提成比例设置——编辑",
				url:"${ctx}/admin/mainCommiPercIndeSale/goUpdate",
				postData:{
					id:ret.pk
				},
				height:450,
				width:445,
				returnFun:goto_query
			});
		});
		
		//删除
		$("#indeSaleDelete").on("click",function(){
			var ret = selectDataTableRow("mainCommiPercIndeSale_dataTable");
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
						url:"${ctx}/admin/mainCommiPercIndeSale/doDelete?id="+ret.pk,
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
					    		$("#mainCommiPercIndeSale_dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					 
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
	function indeSaleView(){
		var ret=selectDataTableRow("mainCommiPercIndeSale_dataTable");
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"主材独立销售提成比例设置——查看",
			url:"${ctx}/admin/mainCommiPercIndeSale/goDetail",
			postData:{
				id:ret.pk
			},
			height:400,
			width:445,
			returnFun:goto_query
		});
	}
	function doExcel(){
		var url = "${ctx}/admin/mainCommiPercIndeSale/doExcel";
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
					<button type="button" class="btn btn-system" id="indeSaleSave">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="indeSaleCopy">
						<span>复制</span>
					</button>
					<button type="button" class="btn btn-system" id="indeSaleUpdate">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" id="indeSaleDelete">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system" id="indeSaleView" onclick="indeSaleView()">
						<span>查看</span>
					</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="mainCommiPercIndeSale_dataTable"></table>
	</div>
</div>



