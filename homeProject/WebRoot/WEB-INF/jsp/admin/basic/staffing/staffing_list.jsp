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
	<title>人员编制统计</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		/*.form-search {
			padding-bottom: 8px !important;
		}
		.btn-default {
		    padding: 2px 33px;
		    font-size: 13px;
		}
		#departType {
			padding-bottom: 3px;
			margin-left: 72px;
		}*/
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
	</style>
	<script type="text/javascript" defer>
		var gridOption; //表格参数
		var departType = "department1";//查询部门类型
		$(function() {
			Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
			gridOption = {
				height: $(document).height()-$("#content-list").offset().top - 88,
				rowNum:100000,
				colModel: [
					{name:"dept1descr",index:"一级部门",width:80,label:"一级部门",align:"left",frozen:true},
					{name:"dept2descr",index:"二级部门",width:80,label:"二级部门",align:"left",hidden:true,frozen:true},
					{name:"dept3descr",index:"三级部门",width:80,label:"三级部门",align:"left",hidden:true,frozen:true},
					{name:"totalplannum",index:"合计|编制数",width:60,label:"编制数",align:"right",sum:true},
					{name:"totalrealnum",index:"合计|人数",width:60,label:"人数",align:"right",sum:true},
					{name:"totalbusimannum",index:"合计|业务员",width:60,label:"业务员",align:"right",sum:true},
					{name:"totaldesimannum",index:"合计|设计师",width:60,label:"设计师",align:"right",sum:true},
					{name:"totaldrawmannum",index:"合计|绘图员",width:60,label:"绘图员",align:"right",sum:true},
					{name:"totalothernum",index:"合计|其他",width:60,label:"其他",align:"right",sum:true},
					{name:"plannum",index:"本部门|编制数",width:60,label:"编制数",align:"right",sum:true},
					{name:"realnum",index:"本部门|人数",width:60,label:"人数",align:"right",sum:true},
					{name:"busimannum",index:"本部门|业务员",width:60,label:"业务员",align:"right",sum:true},
					{name:"desimannum",index:"本部门|设计师",width:60,label:"设计师",align:"right",sum:true},
					{name:"drawmannum",index:"本部门|绘图员",width:60,label:"绘图员",align:"right",sum:true},
					{name:"othernum",index:"本部门|其他",width:60,label:"其他",align:"right",sum:true},
					{name:"subdeptplannum",index:"下级部门|编制数",width:60,label:"编制数",align:"right",sum:true},
					{name:"subdeptrealnum",index:"下级部门|人数",width:60,label:"人数",align:"right",sum:true},
					{name:"subdeptbusimannum",index:"下级部门|业务员",width:60,label:"业务员",align:"right",sum:true},
					{name:"subdeptdesimannum",index:"下级部门|设计师",width:60,label:"设计师",align:"right",sum:true},
					{name:"subdeptdrawmannum",index:"下级部门|绘图员",width:60,label:"绘图员",align:"right",sum:true},
					{name:"subdeptothernum",index:"下级部门|其他",width:60,label:"其他",align:"right",sum:true},
				],
				loadComplete: function(){
					frozenHeightReset("dataTable");
				},
			};

			Global.JqGrid.initJqGrid("dataTable",$.extend(gridOption, {
				url: "${ctx}/admin/staffing/goJqGrid",
				postData: {departType:departType},
			}));

			jQuery("#dataTable").jqGrid("setGroupHeaders", {
				useColSpanStyle: true, //true:和并列，false:不和并列
				groupHeaders:[
					{startColumnName: "totalplannum", numberOfColumns: 6, titleText: "合计"},
					{startColumnName: "plannum", numberOfColumns: 6, titleText: "本部门"},
					{startColumnName: "subdeptplannum", numberOfColumns: 6, titleText: "下级部门"},
				]
			});

			$("#dataTable").jqGrid("setFrozenColumns");

			$("#clear").on("click", function() {
				$("#departType").val(departType);
			});

			$("#departType").on("change",function() {
				departType = $("#departType").val();
				if ("department1" == departType) {
					$("#department2").val("");
					$("#department2").prop("disabled", true);
					$("#department3").val("");
					$("#department3").prop("disabled", true);
				} else if ("department2" == departType) {
					$("#department2").prop("disabled", false);
					$("#department3").val("");
					$("#department3").prop("disabled", true);
				} else {
					$("#department2").prop("disabled", false);
					$("#department3").prop("disabled", false);
				}
			});
		});

		function goto_query() {
			var postData = $("#page_form").jsonForm();
			postData.departType = departType;
			postData.code = postData.department3;
			switch (departType) {
			case "department1":
				$("#dataTable").jqGrid("setGridParam", {
						page:1, //必加
						postData: postData
					})
   					.jqGrid("destroyFrozenColumns")
					.hideCol("dept2descr")
					.hideCol("dept3descr")
   					.jqGrid("setFrozenColumns")
					.trigger("reloadGrid");
				break;
			case "department2":
				$("#dataTable").jqGrid("setGridParam", {
						page:1, //必加
						postData: postData
					})
					.jqGrid("destroyFrozenColumns")
					.showCol("dept2descr")
					.hideCol("dept3descr")
					.jqGrid("setFrozenColumns")
					.trigger("reloadGrid");
				break;
			case "department3":
				$("#dataTable").jqGrid("setGridParam", {
						page:1, //必加
						postData: postData
					})
					.jqGrid("destroyFrozenColumns")
					.showCol("dept2descr")
					.showCol("dept3descr")
					.jqGrid("setFrozenColumns")
					.trigger("reloadGrid");
				break;
			default:
				art.dialog({content: "请选择统计类型", width: 200,});
				break;
			}
		}

		function doExcelNow(){
		 	var url = "${ctx}/admin/staffing/doExcel";
		 	var pageFormId="page_form";
			var tableId="dataTable";
			var colModel = $("#"+tableId).jqGrid("getGridParam","colModel");
			var rows = $("#"+tableId).jqGrid("getRowData");
			if (!rows || rows.length==0){
				art.dialog({
					content: "无数据导出"
				});
				return;
			}
			var datas = {
				colModel: JSON.stringify(colModel),
				rows: JSON.stringify(rows),
			};
			$.form_submit($("#"+pageFormId).get(0), {
				"action": url,
				"jsonString": JSON.stringify(datas)
			});
		}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<div class="row">
						<div class="col-xs-8">
							<li>
								<label for="department1">一级部门</label>
								<select id="department1" name="department1"></select>
							</li>
							<li>
								<label for="department2">二级部门</label>
								<select id="department2" name="department2" disabled="true"></select>
							</li>
							<li>
								<label for="department3">三级部门</label>
								<select id="department3" name="department3" disabled="true"></select>
							</li>
							<li>
								<label for="departType">统计方式</label>
								<select name="departType" id="departType">
									<option value="department1">按一级部门统计</option>
									<option value="department2">按二级部门统计</option>
									<option value="department3">按三级部门统计</option>
								</select>
							</li>
							<!-- <div class="btn-group" id="departType" data-toggle="buttons">
								<label class="btn btn-default active">
									<input type="radio" name="departType" value="department1" />按一级部门统计
								</label>
								<label class="btn btn-default">
									<input type="radio" name="departType" value="department2" />按二级部门统计
								</label>
								<label class="btn btn-default">
									<input type="radio" name="departType" value="department3" />按三级部门统计
								</label>
							</div> -->
						</div>
						<div class="col-xs-4">
							<li>
								<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system" id="clear" onclick="clearForm();">清空</button>
							</li>
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" onclick="doExcelNow();">
					<span>输出到Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<!-- <div id="dataTablePager"></div> -->
		</div>
	</div>
</body>
</html>
