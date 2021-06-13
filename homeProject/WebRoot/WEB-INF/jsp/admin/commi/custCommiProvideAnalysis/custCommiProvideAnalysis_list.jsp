<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>停发楼盘</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function view(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"楼盘提成发放总额分析-查看",
			postData:{address:ret.Address,custCode:ret.Code,mon:$("#mon").val(),type:$("#type").val()},
			url:"${ctx}/admin/custCommiProvideAnalysis/goView",
			height: 700,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "Code", index: "Code", width: 70, label: "客户编号", sortable: true, align: "left"},
				{name: "Address", index: "Address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "CustTypeDescr", index: "CustTypeDescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "PayTypeDescr", index: "PayTypeDescr", width: 80, label: "付款方式", sortable: true, align: "left"},
				{name: "Area", index: "Area", width: 60, label: "面积", sortable: true, align: "right"},
				{name: "TotalCommi", index: "TotalCommi", width: 80, label: "合计提成", sortable: true, align: "right",sum:true},
				{name: "ContractFee", index: "ContractFee", width: 80, label: "合同金额", sortable: true, align: "right",sum:true},
				{name: "CommiRatio", index: "CommiRatio", width: 80, label: "提成占比", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"},sum:true},
				{name: "RecalPerf", index: "RecalPerf", width: 80, label: "业绩金额", sortable: true, align: "right",sum:true},
				{name: "CommiPerfRatio", index: "CommiPerfRatio", width: 80, label: "提成业绩占比", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
				{name: "DesignFeeCommi", index: "DesignFeeCommi", width: 95, label: "累计设计提成", sortable: true, align: "right",sum:true},
				{name: "DesignFee", index: "DesignFee", width: 80, label: "设计费", sortable: true, align: "right",sum:true},
				{name: "DesignFeeRatio", index: "DesignFeeRatio", width: 95, label: "设计提成占比", sortable: true, align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"},sum:true},
				{name: "IndCommi", index: "IndCommi", width: 120, label: "关联独立销售提成", sortable: true, align: "right",sum:true},
				{name: "SupplCommi", index: "SupplCommi", width: 80, label: "返利提成", sortable: true, align: "right",sum:true},
			],
			rowNum:100000000,
			ondblClickRow: function(){
				view();
			},
			gridComplete:function(){
	        	countRate();
	        },
	        loadonce:true
		});
	});
	
	//计算合计栏比率
	function countRate(){
		var TotalCommi = parseFloat($("#dataTable").getCol('TotalCommi', false, 'sum'));
	    var ContractFee = parseFloat($("#dataTable").getCol('ContractFee', false, 'sum'));
	    var DesignFeeCommi = parseFloat($("#dataTable").getCol('DesignFeeCommi', false, 'sum'));
	    var DesignFee = parseFloat($("#dataTable").getCol('DesignFee', false, 'sum'));
	    var RecalPerf = parseFloat($("#dataTable").getCol('RecalPerf', false, 'sum'));
	    
	 	var CommiRatio=ContractFee==0?0:myRound(TotalCommi/ContractFee*100,2);
	 	var DesignFeeRatio=DesignFee==0?0:myRound(DesignFeeCommi/DesignFee*100,2);
	 	var CommiPerfRatio=RecalPerf==0?0:myRound(TotalCommi/RecalPerf*100,2);
	 	
	    $("#dataTable").footerData('set', {"CommiRatio": CommiRatio});
	    $("#dataTable").footerData('set', {"DesignFeeRatio": DesignFeeRatio});
	    $("#dataTable").footerData('set', {"CommiPerfRatio": CommiPerfRatio}); 
	}
	
	function goto_query(){
		var address=$("#address").val();
		var mon=$("#mon").val();
		var type=$("#type").val();
		
		if(address == "" && mon == ""){
			art.dialog({content: "楼盘、计算月份最少要选择一个！"});
			return false;
		}
		$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/custCommiProvideAnalysis/goJqGrid",postData:$("#page_form").jsonForm(),page:1,datatype:"json"}).trigger("reloadGrid");
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>计算月份</label>
						<input type="text" id="mon" name="mon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"  value="${preMon}"/>
					</li>
					<li>
						<label>提成节点</label> 
						<select id="type" name ="type">
							<option value="">请选择...</option>
							<option value="1">1  一期到账</option>
							<option value="2">2  二期到账</option>
							<option value="3">3  三期到账</option>
							<option value="4">4  结算</option>
						</select>
					</li>
					
					<li>
						<label>楼盘</label> 
						<input type="text" id="address" name="address" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="CUSTCOMMIPROVIDEANALYSIS_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">
							查看
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTCOMMIPROVIDEANALYSIS_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcelNow('楼盘提成发放总额分析')">
							导出到Excel
						</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div> 
	</div>
</body>
</html>
