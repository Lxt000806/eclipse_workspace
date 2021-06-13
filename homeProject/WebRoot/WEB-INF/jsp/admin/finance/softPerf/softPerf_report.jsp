<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>报表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_softPerf.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/softPerf/doReportExcel";
		doExcelAll(url);
	}
	function view(){
		var ret =selectDataTableRow();
		var countType=$.trim($("#countType").val());
		var no=$.trim($("#no").val());
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("viewReport",{
			title:"报表明细——查看",
			postData:{no:no,countType:countType,designMan:ret.number,buyer:ret.buyer, department2: ret.department2},
			url:"${ctx}/admin/softPerf/goViewReport",
			height: 700,
		 	width:1150,
			returnFun: goto_query 
		});
	}
	/**初始化表格*/
	$(function(){
		$("#department2").openComponent_department2({condition:{department1:"04"}});	
		$("#no").openComponent_softPerf({showValue:"${softPerf.no}"});	
		$("#empCode").openComponent_employee();	
		
		Global.JqGrid.initJqGrid("dataTable",{
			
			postData:{countType:"1"},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "number", index: "number", width: 70, label: "员工编号", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 73, label: "员工姓名", sortable: true, align: "left"},
				{name: "dept1descr", index: "dept1descr", width: 100, label: "一级部门", sortable: true, align: "left"},
				{name: "department2", index: "department2", width: 100, label: "二级部门", sortable: true, align: "left", hidden:true},
				{name: "depa2descr", index: "depa2descr", width: 100, label: "二级部门", sortable: true, align: "left"},
				{name: "businessmanamount", index: "businessmanamount", width: 123, label: "业务员业绩", sortable: true, align: "left",sum:true},
				{name: "designamount", index: "designamount", width: 118, label: "设计师业绩", sortable: true, align: "left",sum:true},
				{name: "buyeramount", index: "buyeramount", width: 118, label: "买手业绩", sortable: true, align: "left",sum:true},
				{name: "buyer2amount", index: "buyer2amount", width: 118, label: "买手2业绩", sortable: true, align: "left",sum:true},
				{name: "businessper", index: "businessper", width: 118, label: "业务员提成", sortable: true, align: "left",sum:true},
				{name: "designper", index: "designper", width: 118, label: "设计师提成", sortable: true, align: "left",sum:true},
				{name: "buyerper", index: "buyerper", width: 118, label: "买手提成", sortable: true, align: "left",sum:true},
				{name: "buyer2per", index: "buyer2per", width: 118, label: "买手2提成", sortable: true, align: "left",sum:true},
				{name: "allper", index: "allper", width: 118, label: "总提成", sortable: true, align: "left",sum:true},
				{name: "idnum", index: "idnum", width: 130, label: "身份证号", sortable: true, align: "left"},
				{name: "consigncmpdescr", index: "consigncmpdescr", width: 80, label: "签约公司", sortable: true, align: "left"},
			],
			ondblClickRow: function(){
				view();
			}
		});
		
		window.goto_query = function(){
			countType=$.trim($("#countType").val());
			var no=$.trim($("#no").val());
			if(no == ""){
				art.dialog({
					content:"请选择周期编号",
				});
				return;
			}
			$("#dataTable").jqGrid("setGridParam",{datatype:'json',url:"${ctx}/admin/softPerf/goReportJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
			if(countType=='1'){
				jQuery("#dataTable").setGridParam().showCol("number").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("namechi").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("idnum").trigger("reloadGrid");
                jQuery("#dataTable").setGridParam().showCol("consigncmpdescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("TeamDescr").trigger("reloadGrid");
				
			}else if(countType=='2'){
				jQuery("#dataTable").setGridParam().hideCol("number").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("namechi").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("idnum").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("consigncmpdescr").trigger("reloadGrid");
			}
		}
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>周期编号</label>
						<input type="text" style="width:160px;" id="no" name="no"/>
					</li>
					<li>
						<label>领料审核日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>部门</label>
						<input type="text" style="width:160px;" id="department2" name="department2"/>

					</li>
					<li>
						<label>员工</label>
						<input type="text" style="width:160px;" id="empCode" name="empCode"/>
					</li>
					<li>
						<label>统计方式</label>
						<select id="countType" name="countType" style="width: 160px;" value="1">
							<option value="1">1 按人员统计</option>
							<option value="2">2 按部门统计</option>
						</select>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
