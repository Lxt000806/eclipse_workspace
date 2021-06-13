<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>发货时限管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/sendTime/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap',
			autoScroll: true, 
			colModel : [
				{name: "no", index: "no", width: 110, label: "发货时限编号", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 120, label: "材料类型1", sortable: true, align: "left"},
				{name: "producttypedescr", index: "producttypedescr", width: 120, label: "产品类型", sortable: true, align: "left"},
				{name: "issetitemdescr", index: "issetitemdescr", width: 120, label: "是否限制材料", sortable: true, align: "left"},
				{name: "sendday", index: "sendday", width: 80, label: "送货天数", sortable: true, align: "right"},
				{name: "prior", index: "prior", width: 80, label: "优先级", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 150, label: "上次更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "上次更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 100, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
			],
		});
		//新增
		$("#add").on("click",function(){
				Global.Dialog.showDialog("goSave",{
					title:"发货时限管理--增加",
					url:"${ctx}/admin/sendTime/goSave",
				    height:730,
				    width:780,
					returnFun:goto_query
				});
		});
		//编辑
		$("#update").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
								content:"请选择一条记录！",
								width: 200
							});
					return;
				}
				Global.Dialog.showDialog("goUpdate",{
					title:"发货时限管理--编辑",
					url:"${ctx}/admin/sendTime/goUpdate?id="+ret.no,
				    height:730,
				    width:780,
					returnFun:goto_query
				});
		});
		//查看
		$("#view").on("click",function(){
				var ret = selectDataTableRow();
				if(!ret){
					art.dialog({
								content:"请选择一条记录！",
								width: 200
							});
					return;
				}
				Global.Dialog.showDialog("goDetail",{
					title:"发货时限管理--查看",
					url:"${ctx}/admin/sendTime/goDetail?id="+ret.no,
				    height:730,
				    width:780,
					returnFun:goto_query
				});
		});
		//删除
		$("#del").on("click",function(){
			var ret = selectDataTableRow();
			if (ret) {
				var isSuppl=isSupplierTime(ret.no);
				if(isSuppl){
					art.dialog({
						content : "供应商引用了发货时限，不允许删除",
					});
					return;
				}
				art.dialog({
					content : "确定要删除发货时限["+ret.no+"]的信息吗？",
					ok : function() {
						$.ajax({
							url : "${ctx}/admin/sendTime/doDelete?deleteIds=" + ret.no,
							type : "post",
							dataType : "json",
							cache : false,
							error : function(obj) {
								art.dialog({
									content : "删除出错,请重试",
									time : 1000,
									beforeunload : function() {
										goto_query();
									}
								});
							},
							success : function(obj) {
								if (obj.rs) {
									goto_query();
								} else {
									$("#_form_token_uniq_id").val(obj.token.token);
									art.dialog({
										content : obj.msg,
										width : 200
									});
								}
							}
						});
					},
					cancel : function() {
						goto_query();
					}
				});
			} else {
				art.dialog({
					content : "请选择一条记录"
				});
			}
		});
	});
	function doExcel(){
		var url = "${ctx}/admin/sendTime/doExcel";
		doExcelAll(url);
	}
	//供应商是否引用发货时限
	function isSupplierTime(no){
		var isSupplierTime=false;
		$.ajax({
			url:"${ctx}/admin/sendTime/isSupplierTime",
			type:"post",
			data:{no:no},
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(obj.length>0){
					isSupplierTime=true;
				}
			}
		});
		return isSupplierTime;
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" >
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="" /> 
				<ul class="ul-form">
					<li class="form-validate"><label>发货时限编号</label> <input type="text"
						id="no"  name="no" style="width:160px;"
						value="${sendTime.no}" />
					</li>
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1"></select>
					</li>
					<li><label>产品类型</label> <house:xtdm id="productType"
									dictCode="APPPRODUCTTYPE" value="${sendTime.productType}"
									></house:xtdm>
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${sendTime.expired}"
						onclick="hideExpired(this)" ${sendTime.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<button type="button" class="btn btn-system" id="add">
				<span>新增</span>
			</button>
			<button type="button" class="btn btn-system" id="update">
				<span>编辑</span>
			</button>
			<button type="button" class="btn btn-system" id="del">
				<span>删除</span>
			</button>
			<house:authorize authCode="SENDTIME_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<button type="button" class="btn btn-system" onclick="doExcel()">
				<span>导出excel</span>
			</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
