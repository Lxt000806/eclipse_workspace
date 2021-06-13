<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>仓库发货申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>领料单号</label>
							<input type="text" id="iaNo" name="iaNo" style="width:160px;" />
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address" style="width:160px;" />
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>
						   	<label>仓库</label>
							<input type="text" id="whCode" name="whCode" />
						</li>
						<li>
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="PRESENDSTATUS" selectedValue="0,1,2,3,4"></house:xtdmMulit>                     
						</li>
						<li>
							<label>材料类型1</label> 
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " id="clear" onclick="clearForm();">清空</button>
						</li>
					</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
				<house:authorize authCode="PREITEMAPPSEND_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="PREITEMAPPSEND_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>	
				<button type="button" class="btn btn-system" id="submit">
					<span>提交</span>
				</button>
				<house:authorize authCode="PREITEMAPPSEND_CANCEL">							
					<button type="button" class="btn btn-system" id="cancel">
						<span>取消</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="PREITEMAPPSEND_RETURN">							
					<button type="button" class="btn btn-system" id="return">
						<span>退回</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="PREITEMAPPSEND_SEND">
					<button type="button" class="btn btn-system" id="send">
						<span>发货</span>
					</button>
				</house:authorize>
				<house:authorize authCode="PREITEMAPPSEND_PARTSEND">
					<button type="button" class="btn btn-system" id="partSend">
						<span>部分发货</span>
					</button>	
				</house:authorize>
				<house:authorize authCode="PREITEMAPPSEND_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="PREITEMAPPSEND_PRINT">
					<button type="button" class="btn btn-system" id="print">
						<span>打印</span>
					</button>
				</house:authorize>	
				<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
					<span>导出Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
	<script type="text/javascript"> 
		$(function(){
			$("#whCode").openComponent_wareHouse();
			//初始化材料类型1
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
			var postData = $("#page_form").jsonForm();
			//初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/preItemAppSend/goJqGrid",
				postData:{status:"0,1,2,3,4" } ,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI: 'Bootstrap', 
				colModel : [
					{name: "pk", index: "pk", width: 57, label: "编号", sortable: true, align: "left", hidden: true},
					{name: "custcode", index: "custcode", width:70, label: "客户编号", sortable: true, align: "left", hidden: true},
					{name: "no", index: "no", width: 100, label: "发货申请单号", sortable: true, align: "left"},
					{name: "iano", index: "iano", width: 78, label: "领料单号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 188, label: "楼盘", sortable: true, align: "left"},
					{name: "custname", index: "custname", width: 87, label: "客户名称", sortable: true, align: "left"},
					{name: "status", index: "status", width: 73, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "statusdescr", index: "statusdescr", width: 73, label: "状态", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
					{name: "desc1", index: "desc1", width: 155, label: "仓库", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 160, label: "备注", sortable: true, align: "left"},
					{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "appczydescr", index: "appczydescr", width: 88, label: "申请人", sortable: true, align: "left"},
					{name: "senddate", index: "senddate", width: 120, label: "发货时间", sortable: true, align: "left", formatter: formatTime},
					{name: "sendapp", index: "sendapp", width: 77, label: "发货人", sortable: true, align: "left"},
					{name: "itemsendbatchno", index: "itemsendbatchno", width: 120, label: "发货批次", sortable: true, align: "left", hidden: true},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 91, label: "最后修改人员", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"},
					{name: "itemtype1", index: "itemtype1", width:10 , label: "itemtype1", sortable: true, align: "left", hidden: true},
					{name: "whcode", index: "whcode", width:10 , label: "whcode", sortable: true, align: "left", hidden: true},
				],
				ondblClickRow:function(){
					view();
				}
			});
			$("#save").on("click", function () {
				Global.Dialog.showDialog("add", {
					title : "仓库发货申请——新增",
					url : "${ctx}/admin/preItemAppSend/goAdd",
					postData : {m_umState: "A"},
					width : 1155,
					height : 730,
					returnFun : goto_query
				});
			});
			$("#update").on("click", function () {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				$.ajax({ // 验证状态是否发生改变
					url:"${ctx}/admin/preItemAppSend/selectPreItemAppSend",
					type:"post",
					data:{no:ret.no, status:ret.status},
					dataType:"json",
					async:false,
					success:function (json) {
						if (!json.rs) {
							art.dialog({
								content: json.msg,
							});
							return;
						} else {
							var status = $.trim(ret.status);
							if ("0" != status && "1" != status) {
								art.dialog({
									content: "请选择一条记录仓库发货申请单处于【"+ret.statusdescr+"】状态,不能进行编辑操作"
								});
								return;
							}
							Global.Dialog.showDialog("udpate", {
								title : "仓库发货申请——编辑",
								url : "${ctx}/admin/preItemAppSend/goUpdate",
								postData : {m_umState: "M", no: ret.no},
								width : 1155,
								height : 730,
								returnFun : goto_query
							});
						}
					}
				});
			});
			$("#submit").on("click", function () {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				$.ajax({ // 验证状态是否发生改变
					url:"${ctx}/admin/preItemAppSend/selectPreItemAppSend",
					type:"post",
					data:{no:ret.no, status:ret.status},
					dataType:"json",
					async:false,
					success:function (json) {
						if (!json.rs) {
							art.dialog({
								content: json.msg,
							});
							return;
						} else {
							var status = $.trim(ret.status);
							if ("0" != status && "1" != status) {
								art.dialog({
									content: "仓库发货申请单处于【"+ret.statusdescr+"】状态,不能进行提交操作"
								});
								return;
							}
							Global.Dialog.showDialog("udpate", {
								title : "仓库发货申请——提交",
								url : "${ctx}/admin/preItemAppSend/goUpdate",
								postData : {m_umState: "T", no: ret.no},
								width : 1155,
								height : 730,
								returnFun : goto_query
							});
						}
					}
				});
			});
			$("#cancel").on("click", function () {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				$.ajax({ // 验证状态是否发生改变
					url:"${ctx}/admin/preItemAppSend/selectPreItemAppSend",
					type:"post",
					data:{no:ret.no, status:ret.status},
					dataType:"json",
					success:function (json) {
						if (!json.rs) {
							art.dialog({
								content: json.msg,
							});
							return;
						} else {
							var status = $.trim(ret.status);
							if ("0" != status && "1" != status && "2" != status) {
								art.dialog({
									content: "仓库发货申请单处于【"+ret.statusdescr+"】状态,不能进行取消操作"
								});
								return;
							}
							$.ajax({ // 验证状态是否发生改变
								url:"${ctx}/admin/preItemAppSend/isHaveSend",
								type:"post",
								data:{no:ret.no},
								dataType:"json",
								success:function (json) {
									if (!json.rs) {
										art.dialog({
											content: "仓库发货申请单已部分发货,不能进行取消操作",
										});
										return;
									} else {
										Global.Dialog.showDialog("udpate", {
											title : "仓库发货申请——取消",
											url : "${ctx}/admin/preItemAppSend/goCancel",
											postData : {m_umState: "Q", no: ret.no},
											width : 1155,
											height : 730,
											returnFun : goto_query
										});
									}
								}
							});
						}
					}
				});
			});
			$("#return").on("click", function () {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				$.ajax({ // 验证状态是否发生改变
					url:"${ctx}/admin/preItemAppSend/selectPreItemAppSend",
					type:"post",
					data:{no:ret.no, status:ret.status},
					dataType:"json",
					success:function (json) {
						if (!json.rs) {
							art.dialog({
								content: json.msg,
							});
							return;
						} else {
							var status = $.trim(ret.status);
							if ("2" != status) {
								art.dialog({
									content: "仓库发货申请单处于【"+ret.statusdescr+"】状态,不能进行退回操作"
								});
								return;
							}
							$.ajax({ // 验证状态是否发生改变
								url:"${ctx}/admin/preItemAppSend/isHaveSend",
								type:"post",
								data:{no:ret.no},
								dataType:"json",
								success:function (json) {
									if (!json.rs) {
										art.dialog({
											content: "仓库发货申请单已部分发货,不能进行退回操作",
										});
										return;
									} else {
										Global.Dialog.showDialog("udpate", {
											title : "仓库发货申请——退回",
											url : "${ctx}/admin/preItemAppSend/goReturn",
											postData : {m_umState: "H", no: ret.no},
											width : 1155,
											height : 730,
											returnFun : goto_query
										});
									}
								}
							});
						}
					}
				});
			});
			$("#send").on("click", function () {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				$.ajax({ // 验证状态是否发生改变
					url:"${ctx}/admin/preItemAppSend/selectPreItemAppSend",
					type:"post",
					data:{no:ret.no, status:ret.status},
					dataType:"json",
					success:function (json) {
						if (!json.rs) {
							art.dialog({
								content: json.msg,
							});
							return;
						} else {
							var status = $.trim(ret.status);
							if ("2" != status) {
								art.dialog({
									content: "仓库发货申请单处于【"+ret.statusdescr+"】状态,不能进行发货操作"
								});
								return;
							}
							Global.Dialog.showDialog("udpate", {
								title : "仓库发货申请——发货",
								url : "${ctx}/admin/preItemAppSend/goSend",
								postData : {m_umState: "F", no: ret.no},
								width : 1155,
								height : 730,
								returnFun : goto_query
							});
						}
					}
				});
			});
			$("#partSend").on("click", function () {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				$.ajax({ // 验证状态是否发生改变
					url:"${ctx}/admin/preItemAppSend/selectPreItemAppSend",
					type:"post",
					data:{no:ret.no, status:ret.status},
					dataType:"json",
					success:function (json) {
						if (!json.rs) {
							art.dialog({
								content: json.msg,
							});
							return;
						} else {
							var status = $.trim(ret.status);
							if ("2" != status) {
								art.dialog({
									content: "仓库发货申请单处于【"+ret.statusdescr+"】状态,不能进行发货操作"
								});
								return;
							}
							Global.Dialog.showDialog("udpate", {
								title : "仓库发货申请——仓库部分发货",
								url : "${ctx}/admin/preItemAppSend/goPartSend",
								postData : {m_umState: "S", no: ret.no},
								width : 1155,
								height : 730,
								returnFun : goto_query
							});
						}
					}
				});
			});
			$("#view").on("click", function () {
				view();
			});
			$("#print").on("click", function () {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("print", {
					title : "仓库发货申请——打印",
					url : "${ctx}/admin/preItemAppSend/goPrint",
					postData : {no: ret.no, whCode:ret.whcode, whDescr:ret.desc1},
					width : 445,
					height : 200,
					returnFun : goto_query
				});
			});
			$("#clear").on("click", function () {
				$("#status").val("");
				$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
			});
		});
		function view() {
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("udpate", {
				title : "仓库发货申请——查看",
				url : "${ctx}/admin/preItemAppSend/goView",
				postData : {m_umState: "V", no: ret.no},
				width : 1155,
				height : 730,
				returnFun : goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/preItemAppSend/doExcel";
			doExcelAll(url);
		}
	</script>
</body>	
</html>
