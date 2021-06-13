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
	<title>设计师排名</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript" defer>
		//导出EXCEL
		function doExcel(){
			var url = "${ctx}/admin/designManRanking/doExcel";
			doExcelAll(url);
		}
		// 日期格式化到月
		function formatMonth(strTime) {
			if (strTime){
				var date = new Date(strTime);
				var y = date.getFullYear();
				var m = date.getMonth()+1;
			    return y+"-"+(m>9?m:'0'+m);
			}
			return "";
		}
		//刷新sum并取两位小数
		function refreshSum(colModel_name) {
			var colModel_name_sum = myRound($("#dataTable").getCol(colModel_name,false,"sum"), 2);
			var sumObj = {}; //json先要用{}定义，再传参
			sumObj[colModel_name] = colModel_name_sum;//要用参数作为名字时，用[]
			$("#dataTable").footerData("set", sumObj);
		}
		//刷新avg并取两位小数
		function refreshAvg(colModel_name) {
			var colModel_name_avg = myRound($("#dataTable").getCol(colModel_name,false,"avg"), 1);
			var avgObj = {}; //json先要用{}定义，再传参
			avgObj[colModel_name] = colModel_name_avg;
			$("#dataTable").footerData("set", avgObj);
		}
		$(function(){
			var monthNow = formatMonth(new Date());
			$("#dateFrom").val(monthNow);
			$("#dateTo").val(monthNow);
			var postData = $("#page_form").jsonForm();
			// 客户类型默认非独立销售
			postData.custType = "${defaultStaticCustType}";
		    //初始化表格
		    var jqGridOption = {
				height: $(document).height()-$("#content-list").offset().top-63,
				styleUI: "Bootstrap",
				datatype: "json",
				rowNum: 100000,
				colModel: [
					{name: "number", index: "number", width: 80, label: "设计师编号", align: "left", frozen: true, hidden: true},
					{name: "namechi", index: "namechi", width: 70, label: "设计师", align: "left", frozen: true},
					{name: "positiondescr", index: "positiondescr", width: 70, label: "职位", align: "left"},
					{name: "dept1descr", index: "dept1descr", width: 90, label: "一级", align: "left"},
					{name: "depart2andleader", index: "depart2andleader", width: 90, label: "二级", align: "left"},
					// {name: "二级", index: "二级", width: 70, label: "二级", align: "left"},
					// {name: "部门经理", index: "部门经理", width: 70, label: "部门经理", align: "left"},
					{name: "setcount", index: "setcount", width: 60, label: "下定数", sortable: true,sorttype: "number", align: "right", sum: true},
					{name: "unsetcount", index: "unsetcount", width: 60, label: "退定数",sortable: true, sorttype: "number", align: "right"},
					{name: "realsetcount", index: "realsetcount", width: 80, label: "实际下定数",sortable: true, sorttype: "number", align: "right"},
					{name: "setscore", index: "setscore", width: 80, label: "下定数得分",sortable: true, sorttype: "number", align: "right",
						formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1}},
					{name: "crtcount", index: "crtcount", width: 60, label: "接待数",sortable: true, sorttype: "number", align: "right"},
					{name: "setcrtcount", index: "setcrtcount", width: 105, label: "当期接待下定数", sorttype:"number", align: "right"},
					{name: "setcrtrate", index: "setcrtrate", width: 60, label: "下定率", sortable: true,sorttype: "number", align: "right", 
						formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, suffix: "%"}},
					{name: "setcrtratescore", index: "setcrtratescore", width: 80, label: "下定率得分", sortable: true, sorttype: "number", align: "right",
						formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1}},
					{name: "signcount", index: "signcount", width: 60, label: "签单数",sortable: true, sorttype: "number", align: "right"},
					{name: "cancelcount", index: "cancelcount", width: 70, label: "合同退单",sortable: true, sorttype: "number", align: "right"},
					{name: "realsigncount", index: "realsigncount", width: 80, label: "实际签单量", sorttype: "number", align: "right"},
					{name: "signscore", index: "signscore", width: 80, label: "签单数得分", sorttype: "number", align: "right",
						formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1}},
					{name: "contractfee", index: "contractfee", width: 90, label: "有效合同金额", sorttype: "number", align: "right"},
					{name: "chgamount", index: "chgamount", width: 70, label: "增减金额", sorttype: "number", align: "right"},
					{name: "realcontractfee", index: "realcontractfee", width: 105, label: "实际有效合同额", sorttype: "number", align: "right"},
					{name: "contractfeescore", index: "contractfeescore", width: 90, label: "合同金额得分", sorttype: "number", align: "right",
						formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1}},
					{name: "totalscore", index: "totalscore", width: 60, label: "总分", sorttype: "number", align: "right",
						formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1}},
					{name: "ranknum", index: "ranknum", width: 90, label: "最终考核排名", sorttype: "number", align: "right"},
			    ],   
			    gridComplete:function(){
					refreshSum("setcount");
					refreshSum("unsetcount");
					refreshSum("realsetcount");
					refreshAvg("setscore");
					refreshSum("crtcount");
					refreshSum("setcrtcount");
					refreshAvg("setcrtrate");
					refreshAvg("setcrtratescore");
					refreshSum("signcount");
					refreshSum("cancelcount");
					refreshSum("realsigncount");
					refreshAvg("signscore");
					refreshSum("contractfee");
					refreshSum("chgamount");
					refreshSum("realcontractfee");
					refreshAvg("contractfeescore");
					refreshAvg("totalscore");
				}, 
				loadComplete: function(){
					frozenHeightReset("dataTable");
				},
			};
			Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
				url: "${ctx}/admin/designManRanking/goJqGrid",
				postData: postData,
				page: 1,
			}));
			$("#dataTable").jqGrid("setFrozenColumns");
			$("#clear").on("click",function(){
				$("#department1").val("");
				$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
				$("#custType").val("");
				$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
			});
		});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>一级部门</label>
						<house:DictMulitSelect id="department1" dictCode="" 
							sql="select code,desc1 from tDepartment1 where Expired='F' and DepType in ('0', '2') order by cast(Code as decimal)" 
							sqlLableKey="desc1" sqlValueKey="code" >
						</house:DictMulitSelect>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${defaultStaticCustType}"></house:custTypeMulit>
					</li>
					<li>
						<label>开始月份</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" />
					</li>
					<li>
						<label>截至月份</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system " 
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							id="clear" onclick="clearForm();">清空</button>
					</li>		
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
	       <div class="btn-group-sm">
                <house:authorize authCode="DESIGNMANRANKING_EXCEL">
                     <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<!-- <div id="dataTablePager"></div> -->
			</div>
		</div> 
	</div>
</body>
</html>


