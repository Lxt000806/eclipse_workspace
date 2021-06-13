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
	<title>套餐主项目价格管理</title>
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
						<label>客户类型</label>
						<house:dict id="custType" dictCode="" 
							sql="select Code,Code+' '+Desc1 Descr from tCusttype where Expired='F' order by dispSeq " 
							sqlValueKey="Code" sqlLableKey="Descr"/>
					</li>
					<li>
						<label>面积从</label>
						<input type="text" id="fromArea" name="fromArea" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="toArea" name="toArea" style="width:160px;"
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
					</li>
					<li class="search-group">
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
			<house:authorize authCode="SETMAINITEMPRICE_ADD">
				<button type="button" class="btn btn-system" id="add">
					<span>新增</span>
				</button>
			</house:authorize>
			<house:authorize authCode="SETMAINITEMPRICE_EDIT">
				<button type="button" class="btn btn-system" id="edit">
					<span>编辑</span>
				</button>
			</house:authorize>
		  	<house:authorize authCode="SETMAINITEMPRICE_DELETE">
				<button type="button" class="btn btn-system" id="delete">
					<span>删除</span>
				</button>
		  	</house:authorize>
			<house:authorize authCode="SETMAINITEMPRICE_VIEW">
				<button type="button" class="btn btn-system" id="view">
					<span>查看</span>
				</button>
		  	</house:authorize>
		  	<house:authorize authCode="SETMAINITEMPRICE_ADDFROMEXCEL">
				<button type="button" class="btn btn-system" id="addFromExcel">
					<span>从Excel导入</span>
				</button>
		  	</house:authorize>
		  	<house:authorize authCode="SETMAINITEMPRICE_EXCEL">
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
			var gridOption = {
				url: "${ctx}/admin/setMainItemPrice/goJqGrid",
				postData: postData,
				height: $(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap", 
				colModel: [
					{name: "pk", index: "pk", width: 80, label: "编号", sortable: true, align: "left", hidden: true},
					{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left", hidden: true},
					{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
					{name: "fromarea", index: "fromarea", width: 80, label: "起始面积", sortable: true, align: "right"},
					{name: "toarea", index: "toarea", width: 80, label: "截止面积", sortable: true, align: "right"},
					{name: "baseprice", index: "baseprice", width: 80, label: "基准单价", sortable: true, align: "right"},
					{name: "basearea", index: "basearea", width: 80, label: "基准面积", sortable: true, align: "right"},
					{name: "unitprice", index: "unitprice", width: 90, label: "每平米单价", sortable: true, align: "right"},
					{name: "basetoiletcount", index: "basetoiletcount", width: 90, label: "基准卫生间数", sortable: true, align: "right"},
					{name: "toiletprice", index: "toiletprice", width: 90, label: "卫生间单价", sortable: true, align: "right"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"},
				],
				ondblClickRow: function(){
					view();
				}
			};
			
			Global.JqGrid.initJqGrid("dataTable",gridOption);

			$("#add").on("click",function(){
				Global.Dialog.showDialog("add",{
					title:"套餐主项目查表报价——新增",
					url:"${ctx}/admin/setMainItemPrice/goAdd",
					postData:{m_umState: "A"},
					height:325,
					width:705,
					returnFun:goto_query
				});
			});
			
			$("#edit").on("click",function(){
				ret=selectDataTableRow();
				if(!ret){
					art.dialog({
		       			content: "请选择一条记录",
		       		});
		       		return;
				}
				Global.Dialog.showDialog("edit",{
					title:"套餐主项目查表报价——编辑",
					url:"${ctx}/admin/setMainItemPrice/goEdit",
					postData:{
						m_umState:"M",
						pk:ret.pk,
					},
					height:325,
					width:705,
					returnFun:goto_query
				});
			});
			
			$("#delete").on("click",function(){
				var url = "${ctx}/admin/setMainItemPrice/doDelete";
				beforeDel(url,"pk","删除该条记录");
				returnFun: goto_query;
				return true;
			});

			$("#view").on("click", function () {
				view();
			});

			$("#excel").on("click", function () {
				doExcel();
			});
			
			$("#addFromExcel").on("click",function(){
				Global.Dialog.showDialog("Import",{
					title:"套餐主项目查表报价——从Excel导入",
					url:"${ctx}/admin/setMainItemPrice/goAddFromExcel",
					height:600,
					width:950,
					returnFun:function(data){
						goto_query();
					}
				});
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
				title:"套餐主项目查表报价——查看",
				url:"${ctx}/admin/setMainItemPrice/goView",
				postData:{
					m_umState:"V",
					pk:ret.pk,
				},
				height:325,
				width:705,
				// returnFun:goto_query
			});
		}

		function doExcel(){
			var url = "${ctx}/admin/setMainItemPrice/doExcel";
			doExcelAll(url);
		}
	</script>
</body>	
</html>
