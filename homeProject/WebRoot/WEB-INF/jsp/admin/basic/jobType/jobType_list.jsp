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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>任务类型管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${jobType.expired}" />
				<input type="hidden" name="jsonString" value=""/><%-- 导出EXCEL用 --%>
				<ul class="ul-form">
					<li>
						<label for="code">编号</label>
						<input type="text" id="code" name="code"/>
					</li>
					<li>
						<label for="descr">名称</label>
						<input type="text" id="descr" name="descr"/>
					</li>
					<li>
						<label>材料类型1</label>
						<house:DictMulitSelect id="itemType1" dictCode="" 
							sql="select Code,Descr from tItemType1 where Expired='F' " 
							sqlLableKey="Descr" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${jobType.expired }" 
							onclick="hideExpired(this)" ${jobType.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" id="clear" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="add" onclick="add()">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="update" onclick="update()">
						<span>编辑</span>
					</button>
                	<house:authorize authCode="JOBTYPE_VIEW">
	                	<button type="button" class="btn btn-system" id="view" onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="JOBTYPE_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel();">
							<span>输出到Excel</span>
						</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
	<script type="text/javascript">
		function add(){
			Global.Dialog.showDialog("jobTypeAdd",{
				title: "添加信息",
				url: "${ctx}/admin/jobType/goSave",
				postData: {m_umState: "A"},
				height: 500,
				width: 815,
				returnFun: goto_query
			});
		}
		function update(){
			var ret = selectDataTableRow();
		    if (ret) {
		      Global.Dialog.showDialog("jobTypeUpdate",{
					title:"修改信息",
					url:"${ctx}/admin/jobType/goUpdate",
					postData: {m_umState: "M", id: ret.code},
					height:500,
					width:815,
					returnFun: goto_query
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		/*function copy(){
			var ret = selectDataTableRow();
		    if (ret) {
		      Global.Dialog.showDialog("jobTypeCopy",{
				  title:"复制信息",
				  url:"${ctx}/admin/jobType/goSave?id="+ret.Code,
				  height:600,
				  width:1000,
				  returnFun: goto_query
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}*/
		function view(){
			var ret = selectDataTableRow();
		    if (ret) {
		      Global.Dialog.showDialog("jobTypeView",{
				  title:"查看信息",
				  url:"${ctx}/admin/jobType/goDetail",
				  postData: {m_umState: "V", id: ret.code},
				  height:500,
				  width:815
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		/*function del(){
			var url = "${ctx}/admin/jobType/doDelete";
			beforeDel(url);
		}*/
		//导出EXCEL
		function doExcel(){
			var url = "${ctx}/admin/jobType/doExcel";
			doExcelAll(url);
		}
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
		/**初始化表格*/
		$(function(){
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/jobType/goJqGrid",
				height:$(document).height()-$("#content-list").offset().top-70,
				postData: $("#page_form").jsonForm(),
				colModel : [
				  {name: "code",index: "code",width: 60,label:"编号",sortable: true,align: "left"},
				  {name: "itemtype1",index: "itemtype1",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "itemtype1descr",index: "itemtype1descr",width: 80,label:"材料类型1",sortable: true,align: "left"},
				  {name : "iscupboarddescr",index : "iscupboarddescr",width : 120,label:"是否橱柜",sortable : true,align : "left"},
				  {name : "ismaterialsendjobdescr",index : "ismaterialsendjobdescr",width : 120,label:"是否材料发货任务",sortable : true,align : "left"},
				  {name: "descr",index: "descr",width: 80,label:"名称",sortable: true,align: "left"},
				  {name: "chooseman",index: "chooseman",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "choosemandescr",index: "choosemandescr",width: 110,label:"是否选择处理人",sortable: true,align: "left"},
				  {name: "department1",index: "department1",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "department1descr",index: "department1descr",width: 80,label:"一级部门",sortable: true,align: "left"},
				  {name: "department2",index: "department2",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "department2descr",index: "department2descr",width: 80,label:"二级部门",sortable: true,align: "left"},
				  {name: "position",index: "position",width: 160,label:"职位",sortable: true,align: "left"},
				  {name: "remarks",index: "remarks",width: 200,label:"备注",sortable: true,align: "left"},
				  {name: "isjobdepart",index: "isjobdepart",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "isjobdepartdescr",index: "isjobdepartdescr",width: 120,label:"是否限定任务部门",sortable: true,align: "left"},
				  {name: "role",index: "role",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "roledescr",index: "roledescr",width: 90,label:"角色",sortable: true,align: "left"},
				  {name: "isneedsuppl",index: "isneedsuppl",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "isneedsuppldescr",index: "isneedsuppldescr",width: 110,label:"是否指定供应商",sortable: true,align: "left"},
				  {name: "isdispcustphn",index: "isdispcustphn",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "isdispcustphndescr",index: "isdispcustphndescr",width: 100,label:"显示客户电话",sortable: true,align: "left"},
				  {name: "canendcust",index: "canendcust",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "canendcustdescr",index: "canendcustdescr",width: 120,label:"是否允许完工工地",sortable: true,align: "left"},
				  {name: "isneedpic",index: "isneedpic",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "isneedpicdescr",index: "isneedpicdescr",width: 100,label:"是否需要拍照",sortable: true,align: "left"},
				  {name: "isneedreqdescr",index: "isneedreqdescr",width: 120,label:"有需求才允许申请",sortable: true,align: "left"},
				  {name: "prjitem",index: "prjitem",width: 80,label:"编号",sortable: true,align: "left", hidden: true},
				  {name: "prjitemdescr",index: "prjitemdescr",width: 110,label:"项目未验收提醒",sortable: true,align: "left"},
			      {name: "lastupdate",index: "lastupdate",width: 120,label:"最后修改时间",sortable: true,align: "left",formatter: formatTime},
			      {name: "lastupdatedby",index: "lastupdatedby",width: 70,label:"修改人",sortable: true,align: "left"},
			      {name: "expired",index: "expired",width: 80,label:"过期标志",sortable: true,align: "left"},
			      {name: "actionlog",index: "actionlog",width: 80,label:"操作日志",sortable: true,align: "left"}
	            ],
	            ondblClickRow: function () {
	            	view();
	            }
			});
			$("#clear").on("click", function () {
				zTreeCheckFalse("itemType1");
			});
		});
	</script>
</body>
</html>
