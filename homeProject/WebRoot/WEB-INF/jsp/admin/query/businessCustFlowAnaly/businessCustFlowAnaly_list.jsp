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
	<title>业务客户流向分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.query-form {
			margin-bottom: 5px;
		}
		.frozen-div .jqg-third-row-header {
			height: 53px;
		}
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid {
			width: auto !important;
		}
		.ui-jqgrid .ui-jqgrid-view {
			width: auto !important;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
		.ui-jqgrid-sdiv {
			width: auto !important;
		}
		.nav .nav-tabs {
			background: #FFFFFF;
		}
		.eChart {
			width: 100%;
			height: 350px;
			padding-top: 0px;
			border: 1px solid #dfdfdf;
			border-top: 0px;
			background:#FFFFFF;
		}
	</style>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
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
							sql="select code,desc1 from tDepartment1 a where a.Expired='F' and a.DepType in('0','1')
								and ('${user.custRight}'='3' or ('${user.custRight}'='2' and 
								exists(select 1 from tCZYDept in_a where in_a.Department1=a.Code and in_a.CZYBH='${user.czybh}'))) "
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.department1}" 
							onCheck="checkDepartment1('','','','1','${user.custRight=='2'?user.czybh:''}')">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>二级部门</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select code,desc1 from tDepartment2 a 
								where a.Expired='F' and a.DepType='1' and a.Department1='${employee.department1}' 
								and ('${user.custRight}'='3' or ('${user.custRight}'='2' and (
								exists (select 1 from tCZYDept in_a where in_a.Department2=a.Code and in_a.CZYBH='${user.czybh}')
								or exists (select 1 from tCZYDept in_b where in_b.Department1=a.Department1 and in_b.CZYBH='${user.czybh}')))) "
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.department2}">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>业务员</label>
						<input type="text" id="empCode" name="empCode" />
					</li>
					<li hidden="true">
						<label>三级部门</label> 
						<house:DictMulitSelect id="department3" dictCode=""
							sql="select code,desc1 from tDepartment3 where 1=2" 
							sqlLableKey="desc1" sqlValueKey="code">
						</house:DictMulitSelect>
					</li>
					<li>
						<label>开始日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" /><!-- ,maxDate:'#F{$dp.$D(\'dateTo\')}' -->
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"  
							onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" /><!-- ,minDate:'#F{$dp.$D(\'dateFrom\')}' -->
					</li>
					<li>
						<label>汇总方式</label>
						<select id="statistcsMethod" name="statistcsMethod" style="width: 160px;">
							<option value="1">一级部门</option>							
							<option value="2" selected="selected">二级部门</option>
							<option value="3">设计师</option>							
						</select>
					</li>
					<li>
						<label>统计方式</label>
						<select id="statistcsType" name="statistcsType" style="width: 160px;" onchange="changeType()">
							<option value="1" selected="selected">部门</option>							
							<option value="2">员工</option>
						</select>
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
		<div class="pageContent">
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#tabView_crtRate" data-toggle="tab" id="crtRate">分派单数（占比）</a>
					</li>
					<li>
						<a href="#tabView_setRate" data-toggle="tab" id="setRate">下定数（占比）</a>
					</li>
					<li>
						<a href="#tabView_signRate" data-toggle="tab" id="signRate">签单数（占比）</a>
					</li>
					<li>
						<a href="#tabView_contractFeeRate" data-toggle="tab" id="contractFeeRate">签单金额（占比）</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="designCustSourceChart" class="eChart"></div>
				</div>
			</div>
			<div class="btn-panel" style="margin-top: 5px;">
				<div class="btn-group-sm">
					<house:authorize authCode="DESIGNCUSTSOURCEANALY_VIEW">
						<button type="button" class="btn btn-system " id="view">查看</button>
					</house:authorize>
					<house:authorize authCode="BUSINESSCUSTFLOWANALY_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcelNow('业务客户流向分析')">导出excel</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/commons/echarts/dist/echarts.min.js" defer></script><!-- 图表 -->
	<script type="text/javascript" defer>
		var dateFrom,dateTo,sTabName="分派单数";
		//数字显示规则
		var formatoption = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2},
		formatoption2 = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, suffix: "%"},
		formatoptionDay = {decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0};
		var colModel = [
			{name: "empcode", index: "empcode", width: 80, label: "员工编号", sortable: true, align: "left", hidden: true},
			{name: "namechi", index: "namechi", width: 80, label: "员工姓名", sortable: true, align: "left",count:true},
			{name: "department1descr", index: "department1descr", width: 80, label: "一级部门", align: "left", frozen: true},
			{name: "department1", index: "department1", width: 80, label: "一级部门", align: "left", frozen: true, hidden: true},
			{name: "department2descr", index: "department2descr", width: 80, label: "二级部门", align: "left", frozen: true},
			{name: "department2", index: "department2", width: 80, label: "二级部门", align: "left", frozen: true, hidden: true},
			{name: "crtcount", index: "crtcount", width: 80, label: "派单数", align: "right", formatter:"number", formatoptions: formatoption, sum: true},
			{name: "totalcrt", index: "totalcrt", width: 80, label: "派单总数", align: "right", formatter:"number", formatoptions: formatoption, hidden: true},
			{name: "crtrate", index: "crtrate", width: 80, label: "派单占比", align: "right", formatter:"number", formatoptions: formatoption2, sum: true},
			{name: "setcount", index: "setcount", width: 80, label: "下定数", align: "right", formatter:"number", formatoptions: formatoption, sum: true},
			{name: "totalset", index: "totalset", width: 80, label: "下定总数", align: "right", formatter:"number", formatoptions: formatoption, hidden: true},
			{name: "setrate", index: "setrate", width: 80, label: "下定占比", align: "right", formatter:"number", formatoptions: formatoption2, sum: true},
			{name: "signcount", index: "signcount", width: 80, label: "签单数", align: "right", formatter:"number", formatoptions: formatoption, sum: true},
			{name: "totalsign", index: "totalsign", width: 80, label: "签单总数", align: "right", formatter:"number", formatoptions: formatoption, hidden: true},
			{name: "signrate", index: "signrate", width: 80, label: "签单占比", align: "right", formatter:"number", formatoptions: formatoption2, sum: true},
			{name: "sumcontractfee", index: "sumcontractfee", width: 80, label: "签单金额", align: "right", formatter:"number", formatoptions: formatoption, sum: true},
			{name: "cursetcount", index: "cursetcount", width: 110, label: "当月来客下定数", align: "right", formatter:"number", formatoptions: formatoption, sum: true},
			{name: "setcrtpercent", index: "setcrtpercent", width: 90, label: "当期下定率", align: "right", formatter:"number", formatoptions: formatoption2},
			{name: "cursetsigncount", index: "cursetsigncount", width: 110, label: "当月下定签单数", align: "right", formatter:"number", formatoptions: formatoption, sum: true},
			{name: "cursignperc", index: "cursignperc", width: 90, label: "当期签单率", align: "right", formatter:"number", formatoptions: formatoption2},
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
				loadChart(sTabName);
			},
		};
		
		// 刷新合计和平均值
		function refreshSumAndAvg() {
			refreshSum("crtcount", 2);
			refreshSum("totalcrt", 2);//计算时需要
			// refreshSum("crtrate", 2);
			totalRate("crtrate","crtcount","totalcrt", true, 2);
			refreshSum("setcount", 2);
			refreshSum("totalset", 2);
			// refreshSum("setrate", 2);
			totalRate("setrate","setcount","totalset", true, 2);
			refreshSum("signcount", 2);
			refreshSum("totalsign", 2);
			// refreshSum("signrate", 2);
			totalRate("signrate","signcount","totalsign", true, 2);
			refreshSum("sumcontractfee", 2);
			refreshSum("cursetcount", 2);
			refreshSum("cursetsigncount", 2);
			//显示冻结列的底部汇总栏
			displayFrozenBottom();
		}
		// 清空下拉选择树选项
		function zTreeCheckFalse(id) {
			$("#"+id).val("");
			$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
		}
		function dateBegin() {
			var firstDate = new Date();
			firstDate.setDate(1); //第一天
			var endDate = new Date(firstDate);
			endDate.setMonth(firstDate.getMonth() + 1);
			endDate.setDate(0);
			$("#dateFrom").val(formatDate(firstDate));
			if ("" == $("#dateTo").val()) $("#dateTo").val(formatDate(endDate));
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
			var avgSum = motherSum==0?0:myRound((sonSum)/(motherSum)*(isRate?100:1), decimalPlaces);
			var avgObj = {};
			avgObj[avgName] = avgSum;
			$("#dataTable").footerData("set", avgObj);
		}
		// 百分率总和
		function totalRate(totalName, sonName, motherName, isRate, decimalPlaces) {
			isRate = isRate===undefined?true:isRate;
			decimalPlaces = decimalPlaces===undefined?2:decimalPlaces;
			var sonSum = $("#dataTable").getCol(sonName,false,"sum");
			var motherSum = $("#dataTable").getCol(motherName,false,"")[0];
			var totalSum = motherSum==0?0:myRound(sonSum/motherSum*(isRate?100:1), decimalPlaces);
			var totalObj = {};
			totalObj[totalName] = totalSum;
			$("#dataTable").footerData("set", totalObj);
		}
		//查询
		function goto_query(tableId){
			if (("" == $("#department1").val() || $.trim($("#department1_NAME").val())=='') && $("#statistcsType").val()=="1") {
				art.dialog({content: "请选择一级部门",width: 220});
				return;
			}
			if ($("#empCode").val()=="" && $("#statistcsType").val()=="2") {
				art.dialog({content: "请选择业务员",width: 220});
				return;
			}
			if ("" == $("#dateFrom").val() || "" == $("#dateTo").val()) {
				art.dialog({content: "请选择开始和结束日期",width: 220});
				return;
			}
			if (!tableId || typeof(tableId)!="string"){
				tableId = "dataTable";
			}
			$(".s-ico").css("display","none");
			if ($("#statistcsMethod").val() == "2") {
				$("#"+tableId).jqGrid("setGridParam",{
					url: "${ctx}/admin/businessCustFlowAnaly/goJqGrid",
					postData:$("#page_form").jsonForm(),
					page:1,
					sortname:''
				}).jqGrid("destroyFrozenColumns").showCol("department2descr")
				.hideCol("department1descr")
				.hideCol("namechi").jqGrid("setFrozenColumns").trigger("reloadGrid");
			}else if ($("#statistcsMethod").val() == "1"){
				$("#"+tableId).jqGrid("setGridParam",{
					url: "${ctx}/admin/businessCustFlowAnaly/goJqGrid",
					postData:$("#page_form").jsonForm(),
					page:1,
					sortname:''
				}).jqGrid("destroyFrozenColumns").showCol("department1descr")
				.hideCol("department2descr")
				.hideCol("namechi").jqGrid("setFrozenColumns").trigger("reloadGrid");
			}else{
				$("#"+tableId).jqGrid("setGridParam",{
					url: "${ctx}/admin/businessCustFlowAnaly/goJqGrid",
					postData:$("#page_form").jsonForm(),
					page:1,
					sortname:''
				}).jqGrid("destroyFrozenColumns").hideCol("department1descr")
				.hideCol("department2descr")
				.showCol("namechi").jqGrid("setFrozenColumns").trigger("reloadGrid");
			}
			
			dateFrom = $("#dateFrom").val();
			dateTo = $("#dateTo").val();
		}
		//eChart图表
		function loadChart(tabName){
			sTabName=tabName;	
			var arrDept=[]; //部门数据
			var arrData=[];//展示数据
			var rowData = $("#dataTable").jqGrid("getRowData");
			var statistcsMethod=$("#statistcsMethod").val();
			if(rowData){
				var datas = JSON.parse(JSON.stringify(rowData));
				datas.sort(function(a, b) {
			 	 	if (tabName == "分派单数") {
		               	if (parseFloat(a.crtcount) > parseFloat(b.crtcount))
		              		return -1; //降序
		          		else
		                    return 1;
		            } 
		            if (tabName == "下定数") {
		            	if (parseFloat(a.setcount) > parseFloat(b.setcount))
		              		return -1; 
		          		else
		                    return 1;
		            }
		            if (tabName == "签单数") {
		                if (parseFloat(a.signcount) > parseFloat(b.signcount))
		              		return -1; 
		          		else
		                    return 1;
		            }
		            if (tabName =="签单金额"){
		                if (parseFloat(a.contractfee) > parseFloat(b.contractfee))
		              		return -1; 
		          		else
		                    return 1;  
		            } 
				});
				datas.forEach(function(v,k){
					if(tabName=="分派单数"){
						if(v.crtcount>0){
						   if (statistcsMethod=="2"){
						    	arrDept.push(v.department2descr);
						    	arrData.push({name:v.department2descr,value:v.crtcount});
						    }else if (statistcsMethod=="1"){
						     	arrDept.push(v.department1descr);
						     	arrData.push({name:v.department1descr,value:v.crtcount});
						    }else{
						    	arrDept.push(v.namechi);
						     	arrData.push({name:v.namechi,value:v.crtcount});
						    }
						}
					}else if(tabName=="下定数"){
						if(v.setcount>0){
							if (statistcsMethod=="2"){
						    	arrDept.push(v.department2descr);
								arrData.push({name:v.department2descr,value:v.setcount});
						    }else if (statistcsMethod=="1"){
						     	arrDept.push(v.department1descr);
								arrData.push({name:v.department1descr,value:v.setcount});
						    }else{
						    	arrDept.push(v.namechi);
						     	arrData.push({name:v.namechi,value:v.setcount});
						    }
						}
					}else if(tabName=="签单数"){
						if(v.signcount>0){
						    if (statistcsMethod=="2"){
						    	arrDept.push(v.department2descr);
								arrData.push({name:v.department2descr,value:v.signcount});
						    }else if (statistcsMethod=="1"){
						     	arrDept.push(v.department1descr);
								arrData.push({name:v.department1descr,value:v.signcount});
						    }else{
						    	arrDept.push(v.namechi);
						     	arrData.push({name:v.namechi,value:v.signcount});
						    }
						}
					}else{
						if(v.sumcontractfee>0){
							if (statistcsMethod=="2"){
						    	arrDept.push(v.department2descr);
								arrData.push({name:v.department2descr,value:v.sumcontractfee});
						    }else if (statistcsMethod=="1"){
						     	arrDept.push(v.department1descr);
								arrData.push({name:v.department1descr,value:v.sumcontractfee});
						    }else{
						    	arrDept.push(v.namechi);
						     	arrData.push({name:v.namechi,value:v.sumcontractfee});
						    }	
						}	
					}    		
				});	
			} 
		 	var designCustSourceChart = echarts.init(document.getElementById("designCustSourceChart"));
		    // 指定图表的配置项和数据
		    option = {
		        backgroundColor: "#FFFFFF",
		        color:["#006633","#CC6600","#CC9999","#336699","#339999","#CC99CC","#9999FF","#CCCCCC","#999999"],
		        legend: {
		        	type: 'scroll',
		            x: "center",
		            y:"bottom",
		            data:arrDept,
		        },
		        series: [
		            {
		                name:"业务客户流向分析",
		                type:"pie",
		                radius: "75%",
		                itemStyle : {
		                    normal : {
		                        label : {
		                            show : true,
		                            // formatter: "{b}:  ({d}%)",//显示标签
		                            formatter:function(data){
		                            	return data.name+'：'+data.percent.toFixed(1)+"%";
		                            } 
		                        },
		                        labelLine : {
		                            show : true,//显示标签线
		                        },
		                        //borderWidth:1, //设置border的宽度有多大
		                        borderColor:"#fff",
		                    },
		                },
		                data:arrData,
		            }
		        ]
		    };
		    designCustSourceChart.setOption(option); 
		}
		function view() {
			var ret = selectDataTableRow();
			if (ret) {
				var data = $("#page_form").jsonForm();
				data.department1Code = ret.department1;
				data.department2Code = ret.department2;
				data.number = ret.empcode;
				Global.Dialog.showDialog("view",{
					title:"业务客户流向分析--查看明细",
					url:"${ctx}/admin/businessCustFlowAnaly/goView",
					postData:data,
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
		
		//修改统计方式
		function changeType(){
			var statistcsType=$("#statistcsType").val();
			if(statistcsType=="1"){
				$("#empCode").parent().addClass("hidden");
				$("#department1,#department2").parent().removeClass("hidden");
				$("#empCode,#openComponent_employee_empCode").val("");
			}else{
				$("#department1,#department2").parent().addClass("hidden");
				$("#empCode").parent().removeClass("hidden");
				$("#department1_NAME,#department2_NAME").val("");
				zTreeCheckFalse("department1");
				zTreeCheckFalse("department2");
			}
		}
		
		$(function(){
			$("#empCode").openComponent_employee();
			changeType();
			dateBegin();
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
				height: 260,
				postData: postData,
				page: 1,
			}));
			$("#dataTable").jqGrid("setFrozenColumns");
			$("#clear").on("click",function(){
				zTreeCheckFalse("department1");
				zTreeCheckFalse("department2");
				$("#statistcsMethod").val(2);
			});
			$("#crtRate").on("click", function () {
				loadChart("分派单数");
			});
			$("#setRate").on("click", function () {
				loadChart("下定数");
			});
			$("#signRate").on("click", function () {
				loadChart("签单数");
			});
			$("#contractFeeRate").on("click", function () {
				loadChart("签单金额");
			});
			$("#view").on("click", function () {
				view();
			});
		});
	</script>
</body>
</html>
