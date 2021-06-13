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
	<title>优惠额度变动记录</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
		    <form class="form-search" id="page_form" action="" method="post" target="targetFrame">
		    	<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li>
						<label>客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;"/>
					</li>
					<li>
						<label>楼盘地址</label>
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li>
						<label>客户赠送项目编号</label>
						<input type="text" name="custGiftPK">
					</li>
					<li>
						<label>定责单号</label>
						<input type="text" name="fixDutyManPK">
					</li>
					<li class="search-group">
						<li class="search-group"><input type="checkbox" id="expired_show"
                        name="expired_show" value="${discAmtTran.expired}"
                        onclick="hideExpired(this)" ${discAmtTran.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="DISCAMTTRAN_SAVE">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
			</house:authorize>
			<house:authorize authCode="DISCAMTTRAN_UPDATE">
				<button type="button" class="btn btn-system" id="update">
					<span>编辑</span>
				</button>
			</house:authorize>
			<house:authorize authCode="DISCAMTTRAN_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
			</house:authorize>
			<house:authorize authCode="DISCAMTTRAN_EXCEL">
				<button type="button" class="btn btn-system" id="excel">
					<span>导出excel</span>
				</button>
			</house:authorize>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div> 
	<script>
		$(function(){
			var postData = $("#page_form").jsonForm();
			var gridOption ={
				url:"${ctx}/admin/discAmtTran/goJqGrid",
				postData:postData,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI:"Bootstrap", 
				colModel : [
					{name:"pk", index:"pk", width:80, label:"PK", sortable:true, align:"left", hidden:true},
					{name:"custcode", index:"custcode", width:80, label:"客户编号", sortable:true, align:"left"},
					{name:"address", index:"address", width:160, label:"楼盘地址", sortable:true, align:"left"},
					{name:"type", index:"type", width:80, label:"类型", sortable:true, align:"left", hidden: true},
					{name:"typedescr", index:"typedescr", width:80, label:"类型", sortable:true, align:"left"},
					{name:"amount", index:"amount", width:80, label:"金额", sortable:true, align:"right"},
					{name:"isriskfund", index:"isriskfund", width:80, label:"是否风控基金", sortable:true, align:"left", hidden:true},
					{name:"isriskfunddescr", index:"isriskfunddescr", width:80, label:"是否风控基金", sortable:true, align:"left"},
					{name:"custgiftpk", index:"custgiftpk", width:80, label:"客户赠送项目编号", sortable:true, align:"left"},
					{name:"fixdutymanpk", index:"fixdutymanpk", width:80, label:"定责单号", sortable:true, align:"left"},
					{name:"isextra", index:"isextra", width:80, label:"是否额外赠送", sortable:true, align:"left", hidden:true},
					{name:"isextradescr", index:"isextradescr", width:80, label:"是否额外赠送", sortable:true, align:"left"},
					{name:"remarks", index:"remarks", width:160, label:"备注", sortable:true, align:"left"},
                    {name:"lastupdate", index:"lastupdate", width:120, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime},
                    {name:"lastupdatedby", index:"lastupdatedby", width:60, label:"修改人", sortable:true, align:"left"},
                    {name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left"},
                    {name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"}
				],
				ondblClickRow: function(){
					view();
				}
			};
			
			Global.JqGrid.initJqGrid("dataTable",gridOption);

			$("#save").on("click",function(){
				Global.Dialog.showDialog("save",{
					title:"优惠额度变动记录——新增",
					url:"${ctx}/admin/discAmtTran/goSave",
					postData:{
						m_umState: "A"
					},
					height:421,
					width:732,
					returnFun:goto_query
				});
			});
			
			$("#update").on("click",function(){
				var ret=selectDataTableRow();
				if(!ret){
					art.dialog({
			  			content: "请选择一条记录",
			  		});
			  		return;
				}
				
				var today=formatDate(new Date());
				var lastUpdate=formatDate(ret.lastupdate);
				var lastUpdatedBy=$.trim(ret.lastupdatedby);
				var czybh=$.trim("${sessionScope.USER_CONTEXT_KEY.czybh}");
				if(today!=lastUpdate || lastUpdatedBy!=czybh ){
					art.dialog({
						content: "只能修改本人当天添加的记录",
					});
					return;
				}
				
				Global.Dialog.showDialog("save",{
					title:"优惠额度变动记录——编辑",
					url:"${ctx}/admin/discAmtTran/goUpdate",
					postData:{
						m_umState: "M",
						pk: ret.pk,
					},
					height:421,
					width:732,
					returnFun:goto_query
				});
			});
			
			$("#view").on("click", function () {
				view();
			});

			$("#excel").on("click", function () {
				doExcel();
			});
		});

		function view(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
		  			content: "请选择一条记录",
		  		});
		  		return;
			}
			Global.Dialog.showDialog("view",{
				title:"优惠额度变动记录——查看",
				url:"${ctx}/admin/discAmtTran/goView",
				postData:{
					m_umState: "V",
					pk: ret.pk,
				},
				height:421,
				width:732,
				returnFun:goto_query
			});
		}

		function doExcel(){
			var url = "${ctx}/admin/discAmtTran/doExcel";
			doExcelAll(url);
		}
	</script>
</body>	
</html>
