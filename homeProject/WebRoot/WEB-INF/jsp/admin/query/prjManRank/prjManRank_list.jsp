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
	<title>项目经理排名</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
	</style>
	<script type="text/javascript" defer>
		var dateFrom,dateTo;
		//数字显示规则
		var formatoption = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2},
		formatoption2 = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, suffix: "%"},
		formatoptionDay = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0};
		var colModel = [
			{name: "number", index: "number", width: 80, label: "编号", align: "left", frozen: true, hidden: true},
			{name: "namechi", index: "namechi", width: 60, label: "姓名", align: "left", frozen: true, count:true},
			{name: "department2", index: "department2", width: 70, label: "工程部", align: "left", frozen: true},
			{name: "prjleveldescr", index: "prjleveldescr", width: 60, label: "星级", align: "left", frozen: true},
			{name: "againcount", index: "againcount", width: 80, label: "翻单数", align: "right", formatter:"number", formatoptions: formatoptionDay},
			{name: "againcountscore", index: "againcountscore", width: 100, label: "翻单数得分", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "againamount", index: "againamount", width: 80, label: "翻单金额", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "againamountscore", index: "againamountscore", width: 100, label: "翻单金额得分", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "completeontimecount", index: "completeontimecount", width: 80, label: "按时完工数", align: "right", formatter:"number", formatoptions: formatoptionDay},
			{name: "completecount", index: "completecount", width: 80, label: "完工数", align: "right", formatter:"number", formatoptions: formatoptionDay},
			{name: "completeontimerate", index: "completeontimerate", width: 80, label: "按时完工率", align: "right", formatter:"number", formatoptions: formatoption2},
			{name: "completeontimeratescore", index: "completeontimeratescore", width: 110, label: "按时完工率得分", align: "right", formatter:"number", formatoptions: formatoption},
			// {name: "avgconstructdayscore", index: "avgconstructdayscore", width: 100, label: "平均工期得分", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "chgamount", index: "chgamount", width: 80, label: "增减金额", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "chgamountscore", index: "chgamountscore", width: 100, label: "增减项得分", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "twosaleamount", index: "twosaleamount", width: 80, label: "二次销售额", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "twosaleamountscore", index: "twosaleamountscore", width: 100, label: "二次销售得分", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "totalscore", index: "totalscore", width: 80, label: "总分", align: "right", formatter:"number", formatoptions: formatoption},
			{name: "totalconstructday", index: "totalconstructday", width: 80, label: "总施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, hidden: true},
			{name: "avgconstructday", index: "avgconstructday", width: 100, label: "平均施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay},
			{name: "isworkingcount", index: "isworkingcount", width: 100, label: "当前在建套数", align: "right", formatter:"number", formatoptions: formatoptionDay},
			{name: "workinglatecount", index: "workinglatecount", width: 100, label: "当前在建拖期套数", align: "right", formatter:"number", formatoptions: formatoption, hidden: true},
			{name: "workinglaterate", index: "workinglaterate", width: 110, label: "当前在建拖期率", align: "right", formatter:"number", formatoptions: formatoption2},
			{name: "setupcount", index: "setupcount", width: 100, label: "本期安排单量", align: "right", formatter:"number", formatoptions: formatoptionDay},
		];
		//初始化表格
	    var jqGridOption = {
			styleUI: "Bootstrap",
			datatype: "json",
			rowNum: -1,
			colModel: colModel,
			ondblClickRow: function(){
				view();
			},
			loadComplete: function(){
				refreshSumAndAvg();
				frozenHeightReset("dataTable");
			},
		};
		// 刷新合计和平均值
		function refreshSumAndAvg() {
			refreshSum("againcount", 0);
			refreshSum("againamount", 2);
			refreshSum("completecount", 0);
			refreshSum("completeontimecount", 0);
			refreshSum("totalconstructday");
			avgRate("completeontimerate","completeontimecount","completecount", true, 2);
			avgRate("avgconstructday","totalconstructday","completecount",false, 0);
			refreshSum("chgamount", 2);
			refreshSum("twosaleamount", 2);
			refreshSum("isworkingcount", 0);
			refreshSum("workinglatecount", 0);
			avgRate("workinglaterate","workinglatecount","isworkingcount", true, 2);
			refreshSum("setupcount", 0);
			//显示冻结列的底部汇总栏
			displayFrozenBottom();
		}
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
		// 日期格式化到季-开始
		function formatQuarterBegin(strTime) {
			if (strTime){
				var date = new Date(strTime);
				var y = date.getFullYear();
				var m = 11;
				var quarter4_prev = new Date((y-1)+"-11-16 00:00:00");
				var quarter1 = new Date(y+"-02-16 00:00:00");
				var quarter2 = new Date(y+"-05-16 00:00:00");
				var quarter3 = new Date(y+"-08-16 00:00:00");
				var quarter4 = new Date(y+"-11-16 00:00:00");
				if (date > quarter4_prev && date < quarter1) {
					y--;
					m = 11;
				} else if (date > quarter1 && date < quarter2) {
					m = 2;
				} else if (date > quarter2 && date < quarter3) {
					m = 5;
				} else if (date > quarter3 && date < quarter4) {
					m = 8;
				} else if (date > quarter4) {
					m = 11;
				}
			    return y+"-"+(m>9?m:'0'+m)+"-01";
			}
			return "";
		}
		// 日期格式化到季-结束
		function formatQuarterEnd(strTime) {
			if (strTime){
				var date = new Date(strTime);
				var y = date.getFullYear();
				var m = 1, d = 31;
				var quarter4_prev = new Date((y-1)+"-11-16 00:00:00");
				var quarter1 = new Date(y+"-02-16 00:00:00");
				var quarter2 = new Date(y+"-05-16 00:00:00");
				var quarter3 = new Date(y+"-08-16 00:00:00");
				var quarter4 = new Date(y+"-11-16 00:00:00");
				if (date > quarter4_prev && date < quarter1) {
					m = 1;
				} else if (date > quarter1 && date < quarter2) {
					m = 4;
					d = 30;
				} else if (date > quarter2 && date < quarter3) {
					m = 7;
				} else if (date > quarter3 && date < quarter4) {
					m = 10;
				} else if (date > quarter4) {
					y++;
					m = 1;
				}
			    return y+"-"+(m>9?m:'0'+m)+"-"+(d>9?d:'0'+d);
			}
			return "";
		}
		function quarterBegin(dp) {//根据日期显示季度的范围
			var date = dp?new Date(dp.cal.getNewDateStr()):new Date();
			var beginDateNow = formatQuarterBegin(date);
			$("#dateFrom").val(beginDateNow);
			if ("" == $("#dateTo").val()) quarterEnd(dp);
		}
		function quarterEnd(dp) {
			var date = dp?new Date(dp.cal.getNewDateStr()):new Date();
			var endDateNow = formatQuarterEnd(date);
			$("#dateTo").val(endDateNow);
			if ("" == $("#dateFrom").val()) quarterBegin(dp);
		}
		//刷新sum并取decimalPlaces位小数
		function refreshSum(colModel_name, decimalPlaces) {
			decimalPlaces = decimalPlaces===undefined?2:decimalPlaces;
			var colModel_name_sum = myRound($("#dataTable").getCol(colModel_name,false,"sum"), decimalPlaces);
			var sumObj = {}; //json先要用{}定义，再传参
			sumObj[colModel_name] = colModel_name_sum;//要用参数作为名字时，用[]
			$("#dataTable").footerData("set", sumObj);
		}
		//刷新avg并取decimalPlaces位小数
		function refreshAvg(colModel_name, decimalPlaces) {
			decimalPlaces = decimalPlaces===undefined?2:decimalPlaces;
			var colModel_name_avg = myRound($("#dataTable").getCol(colModel_name,false,"avg"), decimalPlaces);
			var avgObj = {};
			avgObj[colModel_name] = colModel_name_avg;
			$("#dataTable").footerData("set", avgObj);
		}
		//平均百分率
		function avgRate(avgName, sonName, motherName, isRate, decimalPlaces) {
			isRate = isRate===undefined?true:isRate;
			decimalPlaces = decimalPlaces===undefined?2:decimalPlaces;
			var sonSum = $("#dataTable").getCol(sonName,false,"sum");
			var motherSum = $("#dataTable").getCol(motherName,false,"sum");
			var avgSum = motherSum==0?0:myRound(sonSum/motherSum*(isRate?100:1), decimalPlaces);
			var avgObj = {};
			avgObj[avgName] = avgSum;
			$("#dataTable").footerData("set", avgObj);
		}
		//查询
		function goto_query(tableId){
			if ("" == $("#dateFrom").val() || "" == $("#dateTo").val()) {
				art.dialog({content: "请选择开始和结束日期",width: 220});
				return;
			}
			if (!tableId || typeof(tableId)!="string"){
				tableId = "dataTable";
			}
			$(".s-ico").css("display","none");
			$("#"+tableId).jqGrid("setGridParam",{
				url: "${ctx}/admin/prjManRank/goJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1,
				sortname:''
			}).trigger("reloadGrid");
			dateFrom = $("#dateFrom").val();
			dateTo = $("#dateTo").val();
		}
		function view() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("view",{
					title:"项目经理排名--查看明细",
					url:"${ctx}/admin/prjManRank/goView",
					postData:{number: ret.number, dateFrom: dateFrom, dateTo: dateTo},
					width:1050,
					height:650,
					// returnFun:goto_query
				});
			} else {
				art.dialog({
					content : "请选择一条记录"
				});
			}
		}
		$(function(){
			quarterBegin();
			// quarterEnd();
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
				height: $(document).height()-$("#content-list").offset().top-63,
				postData: postData,
				page: 1,
			}));
			$("#dataTable").jqGrid("setFrozenColumns");
			$("#clear").on("click",function(){
				zTreeCheckFalse("department2");
			});
			$("#view").on("click", function () {
				view();
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
						<label>开始日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'dateTo\')}',readOnly:true})" />
						<!-- <input type="text" id="dateFrom" name="dateFrom" class="i-date"  
							onclick="WdatePicker({onpicked:function(dp){quarterBegin(dp);},skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'dateTo\')}',readOnly:true})" /> -->
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"  
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'dateFrom\')}',readOnly:true})" />
						<!-- <input type="text" id="dateTo" name="dateTo" class="i-date"  
							onclick="WdatePicker({onpicked:function(dp){quarterEnd(dp);},skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'dateFrom\')}',readOnly:true})" /> -->
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
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
				<house:authorize authCode="PRJMANRANK_VIEW">
                     <button type="button" class="btn btn-system " id="view">查看明细</button>
				</house:authorize>
                <house:authorize authCode="PRJMANRANK_EXCEL">
                     <button type="button" class="btn btn-system " onclick="doExcelNow('项目经理排名')">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
