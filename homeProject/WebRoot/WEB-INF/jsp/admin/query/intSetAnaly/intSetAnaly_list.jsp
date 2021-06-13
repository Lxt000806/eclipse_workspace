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
	<title>集成安装分析</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>出货日期从</label> 
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>到</label> 
						<input type="text" id="dateTo" name="dateTo" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>交付日期从</label> 
						<input type="text" id="deliverDateFrom" name="deliverDateFrom" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>到</label> 
						<input type="text" id="deliverDateTo" name="deliverDateTo" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>材料类型2</label>
						<house:DictMulitSelect id="itemType2" dictCode="" 
							sql="select 'CG' fd1,'橱柜' fd2 union all select 'YG' fd1,'衣柜' fd2 " 
							sqlLableKey="fd2" sqlValueKey="fd1" selectedValue="CG,YG">
						</house:DictMulitSelect>
					</li>
					<li>
						<label for="department2">集成部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' 
								and DepType='6' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>设计师</label>
						<input type="text" id="designMan" name="designMan" style="width:160px;"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " id="clear"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="INTSETANALY_VIEW">
                    <button type="button" class="btn btn-system " id="view">查看领料明细</button>
				</house:authorize>
				<house:authorize authCode="INTSETANALY_EXCEL">
					<button type="button" class="btn btn-system "
						onclick="doExcelNow('集成安装分析')">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var dateFrom,dateTo,itemType2=$("#itemType2").val();
		//数字显示规则
		var formatoption = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2},
		formatoption2 = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, suffix: "%"},
		formatoptionDay = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0};
		var colModel = [
			{name: "address", index: "address", width: 200, label: "楼盘", align: "left", count: true, frozen: true},
			{name: "regiondescr", index: "regiondescr", width: 80, label: "一级区域", align: "left",},
			{name: "custcode", index: "custcode", width: 80, label: "客户编号", align: "left", hidden: true},
			{name: "itemtype2", index: "itemtype2", width: 80, label: "类型", align: "left"},
			{name: "status", index: "status", width: 80, label: "状态", align: "left"},
			{name: "salefee", index: "salefee", width: 80, label: "销售额", align: "right"},
			{name: "date", index: "date", width: 100, label: "出货申请日期", sortable: true, align: "left",formatter:formatDate},
			{name: "senddate", index: "senddate", width: 90, label: "出货日期", sortable: true, align: "left",formatter:formatDate},
			//modify by xzy 20200805
			//{name: "lastwhsend", index: "lastwhsend", width: 100, label: "仓库发货日期", sortable: true, align: "left",formatter:formatDate},
			{name: "intappdate", index: "intappdate", width: 90, label: "下单日期", sortable: true, align: "left",formatter:formatDate},
			{name: "deliverdate", index: "deliverdate", width: 90, label: "交付日期", sortable: true, align: "left",formatter:formatDate},
			{name: "installdays", index: "installdays", width: 90, label: "安装天数", align: "right", formatter:"number", formatoptions: formatoptionDay},
			{name: "installdate", index: "installdate", width: 90, label: "安装日期", sortable: true, align: "left",formatter:formatDate},
			{name: "lastiasend", index: "lastiasend", width: 120, label: "最后领料发货日期", sortable: true, align: "left",formatter:formatDate},
			{name: "designdate", index: "designdate", width: 120, label: "设计完成日期", sortable: true, align: "left",formatter:formatDate},
			{name: "workenddate", index: "workenddate", width: 120, label: "工人完成日期", sortable: true, align: "left",formatter:formatDate},
			{name: "zpconfirmdate", index: "zpconfirmdate", width: 120, label: "找平验收日期", sortable: true, align: "left",formatter:formatDate},
			{name: "faceconfirmdate", index: "faceconfirmdate", width: 100, label: "饰面验收日期", sortable: true, align: "left",formatter:formatDate},
			{name: "cjconfirmdate", index: "cjconfirmdate", width: 100, label: "冲筋验收日期", sortable: true, align: "left",formatter:formatDate},
			{name: "gdconfirmdate", index: "gdconfirmdate", width: 120, label: "刮底打磨验收日期", sortable: true, align: "left",formatter:formatDate},
			{name: "mqconfirmdate", index: "mqconfirmdate", width: 100, label: "面漆验收日期", sortable: true, align: "left",formatter:formatDate},
			{name: "jgconfirmdate", index: "jgconfirmdate", width: 100, label: "竣工验收日期", sortable: true, align: "left",formatter:formatDate},
			{name: "isreplenish", index: "isreplenish", width: 80, label: "是否补货", align: "left"},
			{name: "intdesigner", index: "intdesigner", width: 90, label: "集成设计师", align: "left"},
			{name: "cupdesigner", index: "cupdesigner", width: 90, label: "橱柜设计师", align: "left"},
			{name: "cannotinsremark", index: "cannotinsremark", width: 240, label: "不能安装备注", align: "left"},
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
				if($("#itemType2").val().indexOf("YG")==-1){
					$("#dataTable").jqGrid('showCol', "cupdesigner");
					$("#dataTable").jqGrid('hideCol', "cjconfirmdate");
					$("#dataTable").jqGrid('hideCol', "faceconfirmdate");
					$("#dataTable").jqGrid('hideCol', "intsenddate");
					$("#dataTable").jqGrid('hideCol', "intdesigner");
				}else if($("#itemType2").val().indexOf("CG")==-1){
					$("#dataTable").jqGrid('hideCol', "cupdesigner");
					$("#dataTable").jqGrid('showCol', "cjconfirmdate");
					$("#dataTable").jqGrid('showCol', "intappdate");	
					$("#dataTable").jqGrid('showCol', "intdesigner");	
				}else{
					$("#dataTable").jqGrid('showCol', "cupdesigner");
					$("#dataTable").jqGrid('showCol', "intdesigner");	
					$("#dataTable").jqGrid('showCol', "cjconfirmdate");
					$("#dataTable").jqGrid('showCol', "intappdate");	
					$("#dataTable").jqGrid('showCol', "intdesigner");	
				}
				
				/* if($("#itemType2").val()=="CG"){
					$("#dataTable").jqGrid('showCol', "mqconfirmdate");
					$("#dataTable").jqGrid('showCol', "cupdesigner");
					$("#dataTable").jqGrid('hideCol', "cjconfirmdate");
					$("#dataTable").jqGrid('hideCol', "intsenddate");
					$("#dataTable").jqGrid('hideCol', "intdesigner");
				}else{
					$("#dataTable").jqGrid('hideCol', "mqconfirmdate");
					$("#dataTable").jqGrid('hideCol', "cupappdate");
					$("#dataTable").jqGrid('hideCol', "cupdesigner");
					$("#dataTable").jqGrid('showCol', "cjconfirmdate");
					$("#dataTable").jqGrid('showCol', "faceconfirmdate");
					$("#dataTable").jqGrid('showCol', "intappdate");	
					$("#dataTable").jqGrid('showCol', "intdesigner");	
				} */
				//addToolTipForColumnheader("dataTable");
			},
		};
		
		$("#designMan").openComponent_employee();	
	
		/* function addToolTipForColumnheader(gridID){
		    var columnNameList=$('#'+gridID)[0].p.colNames;
		    for (var i = 0; i < columnNameList.length; i++){
		        var columnName=$('#'+gridID)[0].p.colModel[i].name;
		        $('#'+gridID+'_'+columnName).attr("title", $('#'+gridID)[0].p.colNames[i]);
		    }
		} */
		// 刷新合计和平均值
		function refreshSumAndAvg() {
			//显示冻结列的底部汇总栏
			displayFrozenBottom();
		}
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
		// 日期格式化-开始
		function formatDateBegin(strTime) {
			if (strTime){
				var date = new Date(strTime);
				var dateValue = date.valueOf();
				dateValue -= 30 * 24 * 60 * 60 * 1000;
				var newDate = new Date(dateValue);
				var y = newDate.getFullYear();
				var m = newDate.getMonth() + 1;
				var d = newDate.getDate();
			    return y+"-"+(m>9?m:'0'+m)+"-"+(d>9?d:'0'+d);
			}
			return "";
		}
		// 日期格式化-结束
		function formatDateEnd(strTime) {
			if (strTime){
				var date = new Date(strTime);
				var dateValue = date.valueOf();
				dateValue -= 16 * 24 * 60 * 60 * 1000;
				var newDate = new Date(dateValue);
				var y = newDate.getFullYear();
				var m = newDate.getMonth() + 1;
				var d = newDate.getDate();
			    return y+"-"+(m>9?m:'0'+m)+"-"+(d>9?d:'0'+d);
			}
			return "";
		}
		function setBeginDate() {//根据日期显示季度的范围
			var date = new Date();
			var endDateNow = formatDateEnd(date);
			$("#dateTo").val(endDateNow);
			var beginDateNow = formatDateBegin(endDateNow);
			$("#dateFrom").val(beginDateNow);
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
			if (("" == $("#dateFrom").val() || "" == $("#dateTo").val())&&("" == $("#deliverDateFrom").val() || "" == $("#deliverDateTo").val())) {
				art.dialog({content: "请选择开始和结束日期",width: 220});
				return;
			}
			if($("#itemType2").val()==""){
				art.dialog({content: "请选择材料类型2"});
				return;
			}
			if (!tableId || typeof(tableId)!="string"){
				tableId = "dataTable";
			}
			$(".s-ico").css("display","none");
			$("#"+tableId).jqGrid("setGridParam",{
				url: "${ctx}/admin/intSetAnaly/goJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1,
				sortname:''
			}).trigger("reloadGrid");
			itemType2 = $("#itemType2").val();
			dateFrom = $("#dateFrom").val();
			dateTo = $("#dateTo").val();
		}
		function view() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("view",{
					title:"集成安装分析--查看领料明细",
					url:"${ctx}/admin/intSetAnaly/goView",
					postData:{code:ret.custcode,IsCupboard:ret.itemtype2=="橱柜"?"1":"0"},
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
			setBeginDate();
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
				height: $(document).height()-$("#content-list").offset().top-63,
				postData: postData,
				page: 1,
			}));
			$("#dataTable").jqGrid("setFrozenColumns");
			$("#view").on("click", function () {
				view();
			});
		});
	</script>
</body>
</html>
