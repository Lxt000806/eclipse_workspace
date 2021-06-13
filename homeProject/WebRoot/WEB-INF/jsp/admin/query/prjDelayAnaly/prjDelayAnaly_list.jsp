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
	<title>工程进度拖延分析</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
		.not-begin-bg{ 
          	background-color:lightgray !important;
        }
        .begin-working-bg{
			background-color:orange !important;
        }
        .work-delay-bg{ 
          	background-color:#ff5757 !important;
        }
        .complete-bg{ 
          	background-color:mediumseagreen !important;
        }
	</style>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<!-- <li>
						<label>面漆已完成</label>
						<input type="checkbox" id="printDone" name="printDone" onclick="isChecked(this, 'printDone')"/>
					</li>
					<li>
						<label>已竣工</label>
						<input type="checkbox" id="completed" name="completed" onclick="isChecked(this, 'completed')"/>
					</li> -->
					<li>
						<label for="searchType">搜索选项</label>
						<select name="searchType" id="searchType" onchange="changeSearch(this.value)">
							<!-- <option value="">请选择...</option> -->
							<option value="printWorking">面漆未完成</option>
							<option value="printDone">面漆已完成</option>
							<option value="completed">已竣工</option>
						</select>
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select rtrim(Code) fd1, rtrim(Desc1) fd2 from tDepartment2 where expired='F' and DepType='3' order by DispSeq " 
							sqlLableKey="fd2" sqlValueKey="fd1">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>工地状态</label>
						<house:DictMulitSelect id="status" dictCode="" 
							sql="select CBM,NOTE from tXTDM where ID='CUSTOMERSTATUS' and CBM in ('4','5')" 
							sqlLableKey="NOTE" sqlValueKey="CBM" selectedValue="4">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>开工日期从</label> 
						<input type="text" id="beginDateFrom" name="beginDateFrom" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="beginDateTo" name="beginDateTo" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>面漆验收时间从</label> 
						<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>竣工时间从</label> 
						<input type="text" id="endDateFrom" name="endDateFrom" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="endDateTo" name="endDateTo" class="i-date"
						onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" />
					</li>
					<li>
						<label>施工天数小于</label>
						<input type="text" id="constructDay" name="constructDay" style="width:160px;" 
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
							onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" name="address">
					</li>
					<li>
						<label>面积从</label>
						<input type="text" id="areaFrom" name="areaFrom" style="width:160px;" 
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
							onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
							/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="areaTo" name="areaTo" style="width:160px;" 
							onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
							onafterpaste="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"
							/>
					</li>
					<li>
						<label>客户类型</label>
						<house:DictMulitSelect id="custType" dictCode="" 
							sql="select Code,Desc1 from tCustType where IsAddAllInfo<>'0' order by dispSeq" 
							sqlLableKey="Desc1" sqlValueKey="Code">
						</house:DictMulitSelect>
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
				<house:authorize authCode="PRJDELAYANALY_VIEW">
                    <button type="button" class="btn btn-system " id="view">查看</button>
				</house:authorize>
				<house:authorize authCode="PRJDELAYANALY_EXCEL">
					<button type="button" class="btn btn-system "
						onclick="doExcelNow()">导出excel</button>
				</house:authorize>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		var dateFrom,dateTo;
		//数字显示规则
		var formatoption = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2},
		formatoption2 = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, suffix: "%"},
		formatoptionDay = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0};
		var colModel = [
			{name: "custcode", index: "custcode", width: 80, label: "</br>客户编号", align: "left", frozen: true, hidden: true},
			{name: "address", index: "address", width: 160, label: "</br>楼盘", align: "left", count:true, frozen: true},
			{name: "projectmandescr", index: "projectmandescr", width: 80, label: "</br>项目经理", align: "left", frozen: true},
			{name: "custtype", index: "custtype", width: 80, label: "</br>客户类型", align: "left", frozen: true, hidden: true},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "</br>客户类型", align: "left", frozen: true},
			{name: "area", index: "area", width: 60, label: "</br>面积", align: "right", frozen: true},
			{name: "playdays", index: "playdays", width: 80, label: "计划</br>施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay},
			{name: "begindate", index: "begindate", width: 80, label: "</br>开工日期", sortable: true, align: "left",formatter:formatDate},
			{name: "enddate", index: "enddate", width: 80, label: "</br>竣工日期", sortable: true, align: "left",formatter:formatDate},
			{name: "constdays", index: "constdays", width: 80, label: "</br>施工天数 ", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "wallprintdays", index: "wallprintdays", width: 80, label: "砌墙-面漆</br>天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "constdelaydays", index: "constdelaydays", width: 80, label: "施工</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "setdelaydays", index: "setdelaydays", width: 80, label: "安排</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "itemdelaydays", index: "itemdelaydays", width: 80, label: "其中材料</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "paydelaydays", index: "paydelaydays", width: 80, label: "其中付款</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "walldays", index: "walldays", width: 80, label: "砌墙</br>施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "wallsigndays", index: "wallsigndays", width: 80, label: "砌墙</br>签到天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "wallenddate", index: "wallenddate", width: 80, label: "砌墙</br>完工日期", sortable: true, align: "left",formatter: formatDate, hidden: true},
			{name: "walldelaydays", index: "walldelaydays", width: 80, label: "砌墙</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "wallstopdays", index: "wallstopdays", width: 80, label: "砌墙后</br>停工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "waterdays", index: "waterdays", width: 80, label: "水电</br>施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "watersigndays", index: "watersigndays", width: 80, label: "水电</br>签到天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "waterenddate", index: "waterenddate", width: 80, label: "水电</br>完工日期", sortable: true, align: "left",formatter: formatDate, hidden: true},
			{name: "waterdelaydays", index: "waterdelaydays", width: 80, label: "水电</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "waterstopdays", index: "waterstopdays", width: 80, label: "水电后</br>停工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "leveldays", index: "leveldays", width: 80, label: "找平</br>施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "levelsigndays", index: "levelsigndays", width: 80, label: "找平</br>签到天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "levelenddate", index: "levelenddate", width: 80, label: "找平</br>完工日期", sortable: true, align: "left",formatter: formatDate, hidden: true},
			{name: "leveldelaydays", index: "leveldelaydays", width: 80, label: "找平</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "levelstopdays", index: "levelstopdays", width: 80, label: "找平后</br>停工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "wooddays", index: "wooddays", width: 80, label: "木作</br>施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "woodsigndays", index: "woodsigndays", width: 80, label: "木作</br>签到天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "woodenddate", index: "woodenddate", width: 80, label: "木作</br>完工日期", sortable: true, align: "left",formatter: formatDate, hidden: true},
			{name: "wooddelaydays", index: "wooddelaydays", width: 80, label: "木作</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "facedays", index: "facedays", width: 80, label: "饰面</br>施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "facesigndays", index: "facesigndays", width: 80, label: "饰面</br>签到天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "faceenddate", index: "faceenddate", width: 80, label: "饰面</br>完工日期", sortable: true, align: "left",formatter: formatDate, hidden: true},
			{name: "facedelaydays", index: "facedelaydays", width: 80, label: "饰面</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "facestopdays", index: "facestopdays", width: 80, label: "饰面后</br>停工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "printdays", index: "printdays", width: 80, label: "油漆</br>施工天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "printsigndays", index: "printsigndays", width: 80, label: "油漆</br>签到天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "printenddate", index: "printenddate", width: 80, label: "油漆</br>完工日期", sortable: true, align: "left",formatter: formatDate, hidden: true},
			{name: "printdelaydays", index: "printdelaydays", width: 80, label: "油漆</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "tilesetdelaydays", index: "tilesetdelaydays", width: 80, label: "瓷砖选定</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "stonesetdelaydays", index: "stonesetdelaydays", width: 80, label: "石材选定</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "tilesenddelaydays", index: "tilesenddelaydays", width: 80, label: "瓷砖配送</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "stonesenddelaydays", index: "stonesenddelaydays", width: 80, label: "石材配送</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "seconddelaydays", index: "seconddelaydays", width: 80, label: "二期款</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
			{name: "thirddelaydays", index: "thirddelaydays", width: 80, label: "三期款</br>延误天数", align: "right", formatter:"number", formatoptions: formatoptionDay, avg: true},
		];
		//初始化表格
	    var jqGridOption = {
			styleUI: "Bootstrap",
			datatype: "json",
			rowNum: -1,
			colModel: colModel,
			onSortColEndFlag:true,
			ondblClickRow: function(){
				view();
			},
			loadComplete: function(){
				refreshSumAndAvg();
				frozenHeightReset("dataTable");
				//添加背景色
				var ids = $("#dataTable").getDataIDs();
				var itemTypes = ["wall","water","level","wood","face","print"];
				for(var i=0;i<ids.length;i++){
					var rowData = $("#dataTable").getRowData(ids[i]);
					$.each(itemTypes,function (j, val) {
						if(0>=parseFloat(rowData[val+"days"])){
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"days']").addClass("not-begin-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"signdays']").addClass("not-begin-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"delaydays']").addClass("not-begin-bg");
							// $("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"stopdays']").addClass("not-begin-bg");
						}else if (""==rowData[val+"enddate"]) {
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"days']").addClass("begin-working-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"signdays']").addClass("begin-working-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"delaydays']").addClass("begin-working-bg");
						}else if (0>=parseFloat(rowData[val+"delaydays"])) {
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"days']").addClass("complete-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"signdays']").addClass("complete-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"delaydays']").addClass("complete-bg");
							// $("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"stopdays']").addClass("complete-bg");
						} else {
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"days']").addClass("work-delay-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"signdays']").addClass("work-delay-bg");
							$("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"delaydays']").addClass("work-delay-bg");
							// $("#"+ids[i]).find("td[aria-describedby='dataTable_"+val+"stopdays']").addClass("working-bg");
						}
					});
				}
			},
			// 前端动态修改排序
			onSortCol:function(index, iCol, sortorder){
				$("#dataTable").jqGrid("destroyFrozenColumns");//先销毁冻结列
				var rows = $("#dataTable").jqGrid("getRowData");
				rows.sort(function (a, b) {
					var reg = /^[0-9]+.?[0-9]*$/;
					if(reg.test(a[index])){
						return (a[index]-b[index])*(sortorder=="desc"?1:-1);
					}else{
						return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
					}
				});
				Global.JqGrid.clearJqGrid("dataTable"); 
				$.each(rows,function(k,v){
					Global.JqGrid.addRowData("dataTable", v);
				});
				$("#dataTable").jqGrid("setFrozenColumns");//再恢复冻结列
			},
		};
		//导出EXCEL
		function doExcelNow(){
			var url = "${ctx}/admin/prjDelayAnaly/doExcel";
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
			if($.isChrome()){
				$.downloadExcel_xmlHttpRequest(
					$("#"+pageFormId).get(0), 
					{
						"action": url,
						"jsonString": JSON.stringify(datas)
					}, 
					art.dialog({
						content: "导出中...", 
						lock: true,
						esc: false,
						unShowOkButton: true
					})
				);
			}else{
				$.form_submit($("#"+pageFormId).get(0), {
					"action": url,
					"jsonString": JSON.stringify(datas)
				});
			}
		}
		// 刷新合计和平均值
		function refreshSumAndAvg() {
			refreshAvg("constdays");refreshAvg("wallprintdays");refreshAvg("constdelaydays");
			refreshAvg("setdelaydays");refreshAvg("itemdelaydays");refreshAvg("paydelaydays");
			refreshAvg("walldays");refreshAvg("wallsigndays");refreshAvg("walldelaydays");
			refreshAvg("wallstopdays");refreshAvg("waterdays");refreshAvg("watersigndays");
			refreshAvg("waterdelaydays");refreshAvg("waterstopdays");refreshAvg("leveldays");
			refreshAvg("levelsigndays");refreshAvg("leveldelaydays");refreshAvg("levelstopdays");
			refreshAvg("wooddays");refreshAvg("woodsigndays");refreshAvg("wooddelaydays");
			refreshAvg("facedays");refreshAvg("facesigndays");refreshAvg("facedelaydays");
			refreshAvg("facestopdays");refreshAvg("printdays");refreshAvg("printsigndays");
			refreshAvg("printdelaydays");refreshAvg("tilesetdelaydays");refreshAvg("stonesetdelaydays");
			refreshAvg("tilesenddelaydays");refreshAvg("stonesenddelaydays");refreshAvg("seconddelaydays");
			refreshAvg("thirddelaydays");
			//显示冻结列的底部汇总栏
			displayFrozenBottom();
		}
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
		// 日期格式化-提前1个月
		function formatDateBegin(strTime) {
			if (strTime){
				var date = new Date(strTime);
				var newDate = new Date(date.setMonth(date.getMonth()-1));
				var y = newDate.getFullYear();
				var m = newDate.getMonth() + 1;
				var d = newDate.getDate();
			    return y+"-"+(m>9?m:'0'+m)+"-"+(d>9?d:'0'+d);
			}
			return "";
		}
		//设置面漆验收时间
		function setConfirmDate() {
			var date = new Date();
			var dateNow = formatDateBegin(date);
			$("#confirmDateFrom").val(dateNow);
			$("#confirmDateTo").val(formatDate(date));
		}
		//设置竣工时间
		function setEndDate() {
			var date = new Date();
			var dateNow = formatDateBegin(date);
			$("#endDateFrom").val(dateNow);
			$("#endDateTo").val(formatDate(date));
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
			var formData = $("#page_form").jsonForm();
			if ("" == formData.searchType) {
				art.dialog({content: "搜索选项不能为空",width: 220});
				$("#searchType").val("printWorking");
				changeSearch("printWorking");
				return;
			}
			if ("printDone" == formData.searchType) {
				if ("" == $("#confirmDateFrom").val() && ""==$("#confirmDateTo").val()) {
					art.dialog({content: "面漆验收时间不能为空",width: 220});
					return;
				}
			}
			if ("completed" == formData.searchType) {
				if ("" == $("#endDateFrom").val() && ""==$("#endDateTo").val()) {
					art.dialog({content: "竣工时间不能为空",width: 220});
					return;
				}
			}
			if (!tableId || typeof(tableId)!="string") tableId = "dataTable";
			$(".s-ico").css("display","none");
			$("#"+tableId).jqGrid("setGridParam",{
				url: "${ctx}/admin/prjDelayAnaly/goJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1,
				sortname:''
			}).trigger("reloadGrid");
		}
		// 根据勾选赋值
/*		function isChecked(obj, name){
			$("#"+name).val($(obj).is(":checked")?"1":"0");
			if ("printDone"==name) {
				if ($(obj).is(":checked")) {
					setConfirmDate();
					if ("1"!=$("#completed").val()) checkMulitTree("status", "4");
				} else {
					checkMulitTree("status", "4");
					$("#confirmDateFrom").val("");
					$("#confirmDateTo").val("");
					$("#endDateFrom").val("");
					$("#endDateTo").val("");
					$("#completed").val("0").removeProp("checked");
				}
			} else {
				if ($(obj).is(":checked")) {
					checkMulitTree("status", "4,5");
				}
			}
		}*/
		// 搜索选项改变
		function changeSearch(searchType) {
			if ("" == searchType) return;
			$("#status_NAME").removeProp("disabled");
			if ("completed" == searchType) {
				checkMulitTree("status", "4,5");
				setEndDate();
			} else {
				checkMulitTree("status", "4");
				if ("printWorking" == searchType) {
					$("#status_NAME").prop("disabled",true);
					$("#confirmDateFrom").val("");
					$("#confirmDateTo").val("");
					$("#endDateFrom").val("");
					$("#endDateTo").val("");
				} else {
					setConfirmDate();
				}
			}
		}
		function view() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("view",{
					title:"查看客户工程进度",
					url:"${ctx}/admin/prjDelayAnaly/goView",
					postData:{code:ret.custcode},
					width:1100,
					height:715,
					// returnFun:goto_query
				});
			} else {
				art.dialog({
					content : "请选择一条记录"
				});
			}
		}
		$(function(){
			$("#status_NAME").prop("disabled",true);
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
				height: $(document).height()-$("#content-list").offset().top-80,
				postData: postData,
				page: 1,
			}));
			$("#dataTable").jqGrid("setFrozenColumns");
			$("#clear").on("click",function(){
				zTreeCheckFalse("department2");
				zTreeCheckFalse("status");
				zTreeCheckFalse("custType");
			});
			$("#view").on("click", function () {
				view();
			});
		});
	</script>
</body>
</html>
