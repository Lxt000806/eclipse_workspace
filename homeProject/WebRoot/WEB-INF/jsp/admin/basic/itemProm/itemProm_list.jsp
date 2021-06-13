<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>材料促销管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemProm/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "pk", index: "pk", width: 98, label: "编号", sortable: true, align: "left",hidden:true},
				{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 140, label: "供应商", sortable: true, align: "left"},
				{name: "promprice", index: "promprice", width: 70, label: "促销价", sortable: true, align: "right"},
				{name: "promcost", index: "promcost", width: 70, label: "促销进价", sortable: true, align: "right"},
				{name: "begindate", index: "begindate", width: 90, label: "促销开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 90, label: "促销结束日期", sortable: true, align: "left", formatter: formatDate},
				{name: "lastupdate", index: "lastupdate", width: 136, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
			],
		});
		$("#itemCode").openComponent_item();
		$("#supplCode").openComponent_supplier();
		
	});
	function doExcel(){
		var url = "${ctx}/admin/itemProm/doExcel";
		doExcelAll(url);
	}
	function save(){
		Global.Dialog.showDialog("save",{
			title:"材料促销管理--新增",
			url:"${ctx}/admin/itemProm/goSave",
			height:400,
			width:650,
			returnFun:goto_query
		});
	}
	function update(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("udpate",{
			title:"材料促销管理--编辑",
			url:"${ctx}/admin/itemProm/goUpdate?pk="+ret.pk,
			height:400,
			width:650,
			returnFun:goto_query
		});
	}
	function view(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"材料促销管理--查看",
			url:"${ctx}/admin/itemProm/goDetail?pk="+ret.pk,
			height:400,
			width:650,
			returnFun:goto_query
		});
	}
	function del(){
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content : "确定要删除该条促销记录吗？",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/itemProm/doDelete?deleteIds=" + ret.pk,
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
	}
	function updatePrice(){
		art.dialog({
			 content:"确定要立即更新促销价吗？",
			 lock: true,
			 ok: function () {
				$.ajax({
					url:"${ctx}/admin/itemProm/updatePrice",
					type:"post",
					dataType:"json",
					cache:false,
					async:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						goto_query();
					}
				});
			 },
			cancel: function () {
					return true;
			}
		}); 
	}
	function itemQuery(){
		Global.Dialog.showDialog("itemQuery",{
			title:"材料促销查询",
			url:"${ctx}/admin/itemProm/goItemQuery",
			height:700,
			width:1200,
			returnFun:goto_query
		});
	}
	//导入excel
	function importExcel(){
	   	Global.Dialog.showDialog("importExcel",{
			title:"从excel导入",
			url:"${ctx}/admin/itemProm/goImport",
			height:600,
			width:1150,
			returnFun:goto_query
		});
	}
	//修改itemType1，更新传给供应商的itemType1 
	function itemType1Change(){
			$("#supplCode").openComponent_supplier({
	   			condition:{
	   				itemType1:$("#itemType1").val(),
	   				isDisabled:"0"
	   			}
	   		});
	 }
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="" /> 
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1" onchange="itemType1Change()"></select>
					</li>
					<li><label>供应商</label> <input type="text" id="supplCode"
						name="supplCode" />
					</li>
					<li><label>促销日期从</label> <input type="text" id="beginDate"
						name="beginDate" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="endDate"
						name="endDate" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>材料编码</label> <input type="text" id="itemCode"
						name="itemCode" />
					</li>
					<li class="search-group-shrink"><input type="checkbox"
						id="expired_show" name="expired_show"
						value="${itemProm.expired}" onclick="hideExpired(this)"
						${itemProm.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-system btn-sm"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<button type="button" class="btn btn-system" onclick="save()">
				<span>新增</span>
			</button>
			<button type="button" class="btn btn-system"
				onclick="update()">
				<span>编辑</span>
			</button>
			<button type="button" class="btn btn-system"
				onclick="del()">
				<span>删除</span>
			</button>
			<button type="button" class="btn btn-system"
				onclick="importExcel()">
				<span>从excel导入</span>
			</button>
			<button type="button" class="btn btn-system"
				onclick="updatePrice()">
				<span>更新促销价</span>
			</button>
			<house:authorize authCode="ITEMPROM_VIEW">
				<button type="button" class="btn btn-system"
					onclick="view()">
					<span>查看</span>
				</button>
			</house:authorize>
			<button type="button" class="btn btn-system" onclick="itemQuery()">
				<span>促销材料查询</span>
			</button>
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
