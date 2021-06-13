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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>优惠控制管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${discAmtCtrl.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>客户类型</label> 
						<house:DictMulitSelect id="custType" dictCode="" 
							sql="select Code,Desc1 Descr from tCusttype where Expired='F'" 
							sqlValueKey="Code" sqlLableKey="Descr">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>优惠金额分类</label> 
						<house:DictMulitSelect id="discAmtType" dictCode="" 
							sql="select Code,Descr from tItemType1 where Expired='F' order by DispSeq" 
							sqlValueKey="Code" sqlLableKey="Descr">
						</house:DictMulitSelect>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${discAmtCtrl.expired}" 
							onclick="hideExpired(this)" ${discAmtCtrl.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " id="clear">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
				<house:authorize authCode="discAmtCtrl_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
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
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/discAmtCtrl/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"pk", index:"pk", width:60, label:"编号", sortable:true, align:"left", hidden: true},
					{name:"custtype", index:"custtype", width:80, label:"客户类型", sortable:true, align:"left", hidden: true},
					{name:"custtypedescr", index:"custtypedescr", width:80, label:"客户类型", sortable:true, align:"left"},
					{name:"discamttype", index:"discamttype", width:100, label:"优惠金额分类", sortable:true, align:"left", hidden: true},
					{name:"discamttypedescr", index:"discamttypedescr", width:110, label:"优惠金额分类", sortable:true, align:"left"},
					{name:"maxdiscamtexpr", index:"maxdiscamtexpr", width:200, label:"最高优惠金额公式", sortable:true, align:"left"},
					{name:"iscupboarddescr", index:"iscupboarddescr", width:80, label:"是否橱柜", sortable:true, align:"left"},
					{name:"iscupboard", index:"iscupboard", width:80, label:"是否橱柜", sortable:true, align:"left", hidden: true},
					{name:"isservicedescr", index:"isservicedescr", width:110, label:"是否服务性产品", sortable:true, align:"left"},
					{name:"isservice", index:"isservice", width:110, label:"是否服务性产品", sortable:true, align:"left", hidden: true},
		    		{name:"lastupdate", index:"lastupdate", width:120, label:"最后修改时间", sortable:true, align:"left", formatter:formatTime},
		      		{name:"lastupdatedby", index:"lastupdatedby", width:70, label:"修改人", sortable:true, align:"left"},
		     		{name:"expired", index:"expired", width:80, label:"是否过期", sortable:true, align:"left"},
		    		{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"},
				],
				ondblClickRow: function(){
					view();
				}
			});
			$("#clear").on("click", function () {
				clearForm();
				$("#custType").val("");
				$("#discAmtType").val("");
				$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
				$.fn.zTree.getZTreeObj("tree_discAmtType").checkAllNodes(false);
			});
			$("#save").on("click", function() {
				Global.Dialog.showDialog("save", {
					title : "分段优惠——新增",
					url : "${ctx}/admin/discAmtCtrl/goSave",
					height : 293,
					width : 674,
					returnFun : goto_query
				});
			});
			$("#update").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("update",{
					title:"分段优惠——编辑",
					url:"${ctx}/admin/discAmtCtrl/goUpdate",
					postData:{
						m_umState: "M",
						pk: ret.pk,
						custType: $.trim(ret.custtype),
						discAmtType: $.trim(ret.discamttype),
						maxDiscAmtExpr: ret.maxdiscamtexpr,
						isCupBoard: ret.iscupboard,
						isService: ret.isservice,
						expired: ret.expired,
					},
					height:293,
					width:674,
					returnFun:goto_query
				});
			});
			// $("#delete").on("click",function(){
			// 	var url = "${ctx}/admin/discAmtCtrl/doDelete";
			// 	beforeDel(url,"code","删除该分段优惠的信息");
			// 	returnFun: goto_query;
			// 	return true;
			// });
		});
		function view(){
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"分段优惠——查看",
				url:"${ctx}/admin/discAmtCtrl/goView",
				postData:{
					m_umState: "V",
					custType: $.trim(ret.custtype),
					discAmtType: $.trim(ret.discamttype),
					maxDiscAmtExpr: ret.maxdiscamtexpr,
					isCupBoard: ret.iscupboard,
					isService: ret.isservice,
					expired: ret.expired,
				},
				height:293,
				width:674,
				returnFun:goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/discAmtCtrl/doExcel";
			doExcelAll(url);
		}
	</script>
</body>
</html>
