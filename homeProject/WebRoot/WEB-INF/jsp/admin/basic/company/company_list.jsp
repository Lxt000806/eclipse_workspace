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
	<title>公司列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${company.expired}" />
				<input type="hidden" name="jsonString" value=""/><%-- 导出EXCEL用 --%>
				<ul class="ul-form">
					<li>
						<label for="cmpnyName">公司名称</label>
						<input type="text" id="cmpnyName" name="cmpnyName"/>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${company.expired }" 
							onclick="hideExpired(this)" ${company.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
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
                	<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" id="signSite" onclick="signSite()">
						<span>打卡地点</span>
					</button>
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
	</div>
	<script type="text/javascript">
		function add(){
			Global.Dialog.showDialog("companyAdd",{
				title: "添加公司",
				url: "${ctx}/admin/company/goSave",
				height: 440,
				width: 820,
				returnFun: goto_query
			});
		}
		function update(){
			var ret = selectDataTableRow();
		    if (ret) {
		      Global.Dialog.showDialog("companyUpdate",{
				  title:"修改公司",
				  url:"${ctx}/admin/company/goUpdate",
				  postData: {m_umState: "M", id: ret.Code},
				  height:460,
				  width:820,
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
		      Global.Dialog.showDialog("companyCopy",{
				  title:"复制公司",
				  url:"${ctx}/admin/company/goSave?id="+ret.Code,
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
		      Global.Dialog.showDialog("companyView",{
				  title:"查看公司",
				  url:"${ctx}/admin/company/goDetail",
				  postData: {m_umState: "V", id: ret.Code},
				  height:460,
				  width:820
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		/*function del(){
			var url = "${ctx}/admin/company/doDelete";
			beforeDel(url);
		}*/
		//导出EXCEL
		function doExcel(){
			var url = "${ctx}/admin/company/doExcel";
			doExcelAll(url);
		}
		function signSite(){
			var ret = selectDataTableRow();
		    if (ret) {
		      Global.Dialog.showDialog("signSite",{
				  title:"编辑打卡地点",
				  url:"${ctx}/admin/company/goSignSite",
				  postData: {id: ret.Code},
				  height:575,
				  width:953,
				  returnFun: goto_query
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		/**初始化表格*/
		$(function(){
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/company/goJqGrid",
				height:$(document).height()-$("#content-list").offset().top-70,
				postData: $("#page_form").jsonForm(),
				colModel : [
				  {name: "Code",index: "Code",width: 80,label:"公司编号",sortable: true,align: "left"},
			      {name: "CmpnyName",index: "CmpnyName",width: 180,label:"公司名",sortable: true,align: "left"},
			      {name: "Desc1",index: "Desc1",width: 180,label:"公司全称",sortable: true,align: "left"},
			      {name: "Desc2",index: "Desc2",width: 180,label:"公司简称",sortable: true,align: "left"},
			      {name: "Type",index: "Type",width: 60,label:"类型",sortable: true,align: "left",hidden: true},
			      {name: "typedescr",index: "typedescr",width: 60,label:"类型",sortable: true,align: "left"},
			      {name: "CmpnyAddress",index: "CmpnyAddress",width: 200,label:"公司地址",sortable: true,align: "left"},
			      {name: "CmpnyPhone",index: "CmpnyPhone",width: 100,label:"公司电话",sortable: true,align: "left"},
			      {name: "CmpnyFax",index: "CmpnyFax",width: 100,label:"公司传真",sortable: true,align: "left"},
			      {name: "CmpnyURL",index: "CmpnyURL",width: 160,label:"公司网站",sortable: true,align: "left"},
			      {name: "PricRemark",index: "PricRemark",width: 160,label:"预算打印基础报价说明",sortable: true,align: "left",hidden: true},
			      {name: "ItemRemark",index: "ItemRemark",width: 160,label:"预算打印基础物料说明",sortable: true,align: "left",hidden: true},
			      {name: "SoftPhone",index: "SoftPhone",width: 100,label:"软装部电话",sortable: true,align: "left",hidden: true},
			      {name: "MainItemRem",index: "MainItemRem",width: 160,label:"预算打印主材说明",sortable: true,align: "left",hidden: true},
			      {name: "ServiceRem",index: "ServiceRem",width: 160,label:"预算打印服务性产品说明",sortable: true,align: "left",hidden: true},
			      {name: "LastUpdate",index: "LastUpdate",width: 120,label:"最后修改时间",sortable: true,align: "left",formatter: formatTime},
			      {name: "LastUpdatedBy",index: "LastUpdatedBy",width: 70,label:"修改人",sortable: true,align: "left"},
			      {name: "Expired",index: "Expired",width: 80,label:"过期标志",sortable: true,align: "left"},
			      {name: "ActionLog",index: "ActionLog",width: 80,label:"操作日志",sortable: true,align: "left"}
	            ],
	            ondblClickRow: function () {
	            	view()
	            }
			});
		});
	</script>
</body>
</html>
