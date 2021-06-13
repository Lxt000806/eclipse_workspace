<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>停发楼盘</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function add(){
		Global.Dialog.showDialog("add",{
			title:"停发楼盘--增加",
			url:"${ctx}/admin/commiNotProvideCustStakeholder/goSave",
			height: 450,
		 	width:700,
			returnFun: goto_query 
		});
	}
	
	function update(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"停发楼盘--编辑",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/commiNotProvideCustStakeholder/goUpdate",
			height: 450,
		 	width:700,
			returnFun: goto_query 
		});
	}
	
	function view(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"停发楼盘-查看",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/commiNotProvideCustStakeholder/goView",
			height: 450,
		 	width:700,
			returnFun: goto_query 
		});
	}
	
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		$("#role").openComponent_roll();
		
		if("${m_umState}"=="V"){
			$(".viewFlag").attr("disabled",true);
		}
		
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiNotProvideCustStakeholder/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 100, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 80, label: "角色", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 70, label: "停发类型", sortable: true, align: "left"},
				{name: "reprovidemon", index: "reprovidemon", width: 70, label: "补发月份", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/commiNotProvideCustStakeholder/doExcel";
		doExcelAll(url);
	}
	
	function del(){
		beforeDel("${ctx}/admin/commiNotProvideCustStakeholder/doDelete","pk","删除该记录");
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>楼盘</label> 
						<input type="text" id="address" name="address" />
					</li>
					<li>
						<label>角色</label>
						<input type="text" id="role" name="role" style="width:160px;"  />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system viewFlag" onclick="add()">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system viewFlag"
						onclick="update()">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system viewFlag"
						onclick="del()">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system"
						onclick="view()">
						<span>查看 </span>
					</button>
				</div>
			</div> 
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
