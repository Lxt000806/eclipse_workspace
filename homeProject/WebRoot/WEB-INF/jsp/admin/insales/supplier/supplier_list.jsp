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
	<title>供应商管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript" defer>
		function add(){
			Global.Dialog.showDialog("add",{
				title:"供应商管理--增加",
				url:"${ctx}/admin/supplier/goAdd",
				postData:{
					m_umState:"A",
				},
				width:813,
				height:720,
				returnFun: goto_query
			});
		}

		function copy(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录",
				});
				return;
			}
			Global.Dialog.showDialog("copy",{
				title:"供应商管理--复制",
				url:"${ctx}/admin/supplier/goCopy",
				postData:{
					m_umState:"C",
					code:ret.code,
				},
				width:813,
				height:720,
				returnFun: goto_query
			});
		}

		function update(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录",
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"供应商管理--编辑",
				url:"${ctx}/admin/supplier/goUpdate",
				postData:{
					m_umState:"M",
					code:ret.code,
				},
				width:813,
				height:720,
				returnFun: goto_query
			});
		}

		function view(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录",
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"供应商管理--查看",
				url:"${ctx}/admin/supplier/goView",
				postData:{
					m_umState:"V",
					code:ret.code,
				},
				width:813,
				height:720,
				returnFun: goto_query
			});
		}
		function setDeliveryDeadline(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录",
				});
				return;
			}
			Global.Dialog.showDialog("setDeliveryDeadline",{
				title:"供应商管理--发货时限信息",
				url:"${ctx}/admin/supplier/goSetDeliveryDeadline",
				postData:{
					code:ret.code,
				},
				width:813,
				height:640,
				returnFun: goto_query
			});
		}
		function doExcel(){
			doExcelAll("${ctx}/admin/supplier/doExcel");
		}
		$(function(){
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/supplier/goSupplierJqGrid",
				sortable:true,
				height:$(document).height()-$("#content-list").offset().top-68,
				colModel : [
					{name: "code", index: "code", width: 70, label: "商家编号", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 100, label: "商家名称", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "供应商类型", sortable: true, align: "left"},
					{name: "address", index: "address", width: 150, label: "地址", sortable: true, align: "left"},
					{name: "contact", index: "contact", width: 70, label: "联系人", sortable: true, align: "left"},
					{name: "isspecdaydescr", index: "isspecdaydescr", width: 119, label: "是否指定结算日期", sortable: true, align: "left"},
					{name: "specday", index: "specday", width: 90, label: "每月结算日期", sortable: true, align: "right"},
					{name: "billcycle", index: "billcycle", width: 70, label: "结算周期", sortable: true, align: "right"},
					{name: "prepaybalance", index: "prepaybalance", width: 80, label: "预付金余额", sortable: true, align: "right"},
					{name: "phone1", index: "phone1", width: 80, label: "电话1", sortable: true, align: "left"},
					{name: "phone2", index: "phone2", width: 80, label: "电话2", sortable: true, align: "left"},
					{name: "fax1", index: "fax1", width: 90, label: "传真1", sortable: true, align: "left"},
					{name: "fax2", index: "fax2", width: 90, label: "传真2", sortable: true, align: "left"},
					{name: "mobile1", index: "mobile1", width: 80, label: "手机1", sortable: true, align: "left"},
					{name: "mobile2", index: "mobile2", width: 80, label: "手机2", sortable: true, align: "left"},
					{name: "email1", index: "email1", width: 90, label: "邮件地址1", sortable: true, align: "left"},
					{name: "email2", index: "email2", width: 90, label: "邮件地址2", sortable: true, align: "left"},
					{name: "actname", index: "actname", width: 80, label: "账户名", sortable: true, align: "left"},
					{name: "cardid", index: "cardid", width: 154, label: "卡号", sortable: true, align: "left"},
					{name: "bank", index: "bank", width: 80, label: "开户行", sortable: true, align: "left"},
					{name: "iswebdescr", index: "iswebdescr", width: 80, label: "网淘供应商", sortable: true, align: "left"},
					{name: "department2descr", index: "department2descr", width: 80, label: "软装战队", sortable: true, align: "left"},
					{name: "purchcostmodeldescr", index: "purchcostmodeldescr", width: 100, label: "采购成本模式", sortable: true, align: "left"},
					{name: "inordertypedescr", index: "inordertypedescr", width: 100, label: "套内拆单模式", sortable: true, align: "left"},
					{name: "outordertypedescr", index: "outordertypedescr", width: 100, label: "套外拆单模式", sortable: true, align: "left"},
					{name: "iscontaintaxdescr", index: "iscontaintaxdescr", width: 60, label: "含税价", sortable: true, align: "left"},
					{name: "isgroupdescr", index: "isgroupdescr", width: 110, label: "是否集团供应商", sortable: true, align: "left"},
					{name: "preorderday", index: "preorderday", width: 95, label: "订货提前天数", sortable: true, align: "right"},
					{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 65, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 65, label: "操作", sortable: true, align: "left"},
				],
				ondblClickRow: function(){
					view();
				},
			});
		});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label for="code">商家编号</label>
						<input type="text" id="code" name="code" />
					</li>
					<li>
						<label for="descr">商家名称</label>
						<input type="text" id="descr" name="descr" />
					</li>
					<li>
						<label for="itemType1">供应商类型</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${supplier.expired }" 
							onclick="hideExpired(this)" ${supplier.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SUPPLIER_ADD">
					<button type="button" class="btn btn-system" id="add" onclick="add()">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIER_COPY">								
					<button type="button" class="btn btn-system" id="copy" onclick="copy()">
						<span>复制</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIER_UPDATE">
					<button type="button" class="btn btn-system" id="update" onclick="update()">
						<span>编辑</span>
					</button>
				</house:authorize>			
				<house:authorize authCode="SUPPLIER_VIEW">
                	<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
                </house:authorize>
                <house:authorize authCode="SUPPLIER_SETDELIVERYDEADLINE">
					<button type="button" class="btn btn-system" id="update" onclick="setDeliveryDeadline()">
						<span>发货时限设定</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel();">
					<span>输出到Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
