<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<style type="text/css">
	.tab-form {
		margin-bottom: 0px;
		margin-top: 0px;
		border-top-width: 0px;
	}
</style>
<script type="text/javascript">
	$(function(){
		//客户付款
		var gridOption ={
			url:"${ctx}/admin/custPay/goPayInfoJqGrid",
			postData:{code:"${customer.code}"},
		    rowNum:1000000,
			height: $(document).height()-$("#content-list-8").offset().top-415,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel: [
				{name: "adddate", index: "adddate", width: 127, label: "登记日期", sortable: true, align: "left", formatter: formatTime},
				{name: "date", index: "date", width: 137, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
				{name: "amount", index: "amount", width: 100, label: "付款金额", sortable: true, align: "right", sum: true},
			], 
			onSortCol:function(index, iCol, sortorder){
				var rows = $("#dataTable_custPayDate").jqGrid("getRowData");
	   			rows.sort(function (a, b) {
	   				var reg = /^[0-9]+.?[0-9]*$/;
					if(reg.test(a[index])){
	   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
					}else{
	   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
					}  
	   			});    
	   			Global.JqGrid.clearJqGrid("dataTable_custPayDate"); 
	   			$.each(rows,function(k,v){
					Global.JqGrid.addRowData("dataTable_custPayDate", v);
				});
			},
 		};
		//初始化送货申请明细
		Global.JqGrid.initJqGrid("dataTable_custPayDate",gridOption);
		//增减信息
		var gridOption ={
			url:"${ctx}/admin/prjDelayAnaly/goChgInfoOrderDateJqGrid",
			postData:{code:"${customer.code}"},
		    rowNum:10000000,
			height: $(document).height()-$("#content-list-8-2").offset().top-415,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "date", index: "date", width: 120, label: "增减日期", sortable: true, align: "left", formatter: formatTime},
				{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "项目", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 60, label: "金额", sortable: true, align: "right", sum: true}
			], 
			onSortCol:function(index, iCol, sortorder){
				var rows = $("#dataTable_chgInfo").jqGrid("getRowData");
				rows.sort(function (a, b) {
					var reg = /^[0-9]+.?[0-9]*$/;
					if(reg.test(a[index])){
						return (a[index]-b[index])*(sortorder=="desc"?1:-1);
					}else{
						return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
					}  
				});    
				Global.JqGrid.clearJqGrid("dataTable_chgInfo"); 
				$.each(rows,function(k,v){
					Global.JqGrid.addRowData("dataTable_chgInfo", v);
				});
			}, 
 		};
       //初始化集成进度明细
	   Global.JqGrid.initJqGrid("dataTable_chgInfo",gridOption);
	});
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="query-form tab-form">
		<form action="" method="post" id="page_form" class="form-search">
			<input type="hidden" name="jsonString" value="" />
			<ul class="ul-form">
				<li>
					<label>设计费</label>
					<input type="text" id="designfee"
						name="designfee" style="width:160px;"
						value="${customerPayMap.designfee}" readonly="readonly" />
				</li>
				<li>
					<label>首批付款</label> 
					<input type="text" id="firstpay"
						name="firstpay" style="width:160px;"
						value="${customerPayMap.firstpay}" readonly="readonly" />
				</li>
				<li>
					<label>二批付款</label> 
					<input type="text" id="secondpay"
						name="secondpay" style="width:160px;"
						value="${customerPayMap.secondpay}" readonly="readonly" />
				</li>
				<li>
					<label>三批付款</label> 
					<input type="text" id="thirdpay"
						name="thirdpay" style="width:160px;"
						value="${customerPayMap.thirdpay}" readonly="readonly" />
				</li>
				<li>
					<label>尾批付款</label> 
					<input type="text" id="fourpay"
						name="fourpay" style="width:160px;"
						value="${customerPayMap.fourpay}" readonly="readonly" />
				</li>
				<li>
					<label>工程分期合计</label> 
					<input type="text" id="sumpay"
						name="sumpay" style="width:160px;"
						value="${customerPayMap.sumpay}" readonly="readonly" />
				</li>
				<li>
					<label>增减总金额</label> 
					<input type="text" id="zjzje"
						name="zjzje" style="width:160px;" value="${customerPayMap.zjzje}"
						readonly="readonly" />
				</li>
				<li >
					<label>已付款</label> 
					<input type="text" id="haspay"
						name="haspay" style="width:160px;"
						value="${customerPayMap.haspay}" readonly="readonly" />
				</li>
				<li>
					<label>本期付款期数</label> 
					<input type="text" id="nowNum"
						name="nowNum" style="width:160px;" value="${balanceMap.nowNum}"
						readonly="readonly" />
				</li>
				<li>
					<label>本期应付余额</label> 
					<input type="text" id="nowShouldPay"
						name="nowShouldPay" style="width:160px;"
						value="${balanceMap.nowShouldPay}" readonly="readonly" />
				</li>
				<li>
					<label>下期应付(含本期)</label> 
					<input type="text"
						id="nextShouldPay" name="nextShouldPay" style="width:160px;"
						value="${balanceMap.nextShouldPay}" readonly="readonly" />
				</li>
			</ul>
		</form>
	</div>
	<div class="container-fluid" >
		<ul class="nav nav-tabs" >
			<li class="active">
				<a href="#tab_payInfo" data-toggle="tab">付款信息</a>
			</li>
			<li class="">
				<a href="#tab_chgInfo" data-toggle="tab">增减信息</a>
			</li>
		</ul> 
		<div class="tab-content">
			<div id="tab_payInfo" class="tab-pane fade in active"> 
				<div id="content-list-8">
					<table id="dataTable_custPayDate"></table>
				</div>
			</div>
			<div id="tab_chgInfo" class="tab-pane fade"> 
				<div id="content-list-8-2">
					<table id="dataTable_chgInfo"></table>
				</div>
			</div>
		</div>
	</div>
</div>
