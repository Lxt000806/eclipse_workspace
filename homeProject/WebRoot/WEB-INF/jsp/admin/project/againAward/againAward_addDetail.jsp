<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>签单奖励管理-新增-新增明细</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("addDetailDataTable",{
				url:"${ctx}/admin/againAward/goJqGridAddDetail",
				postData:{
					status:"${data.status}",
					codes:$("#codes").val(),
				},
				height:300,
	        	styleUI: "Bootstrap",
				multiselect: true,
				colModel : [
					{name : "address",index : "address",width : 140,label:"楼盘",sortable : true,align : "left"},
				  	{name : "custdescr",index : "custdescr",width : 90,label:"客户名称",sortable : true,align : "left"},
				  	{name : "custstatusdescr",index : "custstatusdescr",width : 90,label:"客户状态",sortable : true,align : "left"},
			      	{name : "layoutdescr",index : "layoutdescr",width : 70,label:"户型",sortable : true,align : "left"},
			      	{name : "area",index : "area",width : 70,label:"面积",sortable : true,align : "right"},
			      	{name : "businessmen",index : "businessmen",width : 100,label:"业务员",sortable : true,align : "left"},
			      	{name : "businessmandepts",index : "businessmandepts",width : 120,label:"业务员二级部门",sortable : true,align : "left"},
			      	{name : "designmen",index : "designmen",width : 100,label:"设计师",sortable : true,align : "left"},
			      	{name : "designmandepts",index : "designmandepts",width : 120,label:"设计师二级部门",sortable : true,align : "left"},
			      	{name : "contractfee",index : "contractfee",width : 90,label:"合同总价",sortable : true,align : "right"},
			      	{name : "amount",index : "amount",width : 90,label:"奖励金额",sortable : true,align : "right", hidden:true},
			      	{name : "receiveamount",index : "receiveamount",width : 90,label:"已领金额",sortable : true,align : "right"},
			      	{name : "hadcalcperfdescr",index : "hadcalcperfdescr",width : 90,label:"已算业绩",sortable : true,align : "left"},
			      	{name : "paidamount",index : "paidamount",width : 80,label:"已付款",sortable : true,align : "right"},
			      	{name : "firstpay",index : "firstpay",width : 80,label:"一期款",sortable : true,align : "right"},
			      	{name : "paytypedescr",index : "paytypedescr",width : 90,label:"付款类型",sortable : true,align : "left"},
			      	{name : "setdate",index : "setdate",width : 140,label:"下定日期",sortable : true,align : "left",formatter:formatTime},
			      	{name : "signdate",index : "signdate",width : 140,label:"签订日期",sortable : true,align : "left",formatter:formatTime},
			      	{name : "sourcedescr",index : "sourcedescr",width : 90,label:"客户来源",sortable : true,align : "left"},
			      	{name : "code",index : "code",width : 90,label:"code",sortable : true,align : "left", hidden:true}
	            ]
			});
			$("#address").on("keyup", function(e){
				if(e.keyCode === 13){
					search();
				}
			});
		});
		function search(){
			$("#addDetailDataTable").jqGrid("setGridParam", {
				url:"${ctx}/admin/againAward/goJqGridAddDetail",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		
		function save(){
		
			var ids = $("#addDetailDataTable").jqGrid("getGridParam", "selarrrow")
			
			if (ids.length == 0) {
				art.dialog({content:"请选择数据后保存"})		
				return
			}

            var bonusScheme = $("#bonusScheme").val()
			if (!bonusScheme) {
			    art.dialog({content:"请选择奖励方案"})
			    return
			}
			
			var selectedRows = []
			$.each(ids, function(i, id) {
				var rowData = $("#addDetailDataTable").jqGrid("getRowData", id)
				selectedRows.push(rowData)
			})
			
			Global.Dialog.returnData = {
			    bonusScheme: bonusScheme,
			    selectedRows: selectedRows,
			    amount:$("#amount").val()
			}
			
			closeWin()
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
					<form class="form-search">
					    <ul class="ul-form">
					        <li>
					            <button id="saveBut" type="button" class="btn btn-system btn-xs" onclick="save()">保存</button>
					        </li>
					        <li>
					            <button id="closeBut" type="button" class="btn btn-system btn-xs" onclick="closeWin(false)">关闭</button>
					        </li>
					        <li>
					            <label>奖励方案</label>
								<select name="bonusScheme" id="bonusScheme" style="width: 120px">								    
								    <option value="">请选择</option>
								    <option value="1">业务员</option>
								    <option value="2">设计师</option>
								    <option value="3">业务员和设计师</option>
								</select>
					        </li>
					        <li>	
								<label>奖励金额</label>
								<input type="text" id="amount" name="amount" value="0" />
							</li>
					    </ul>
					</form>
				</div>
			</div>
			<div class="panel panel-info">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="codes" name="codes" value="${data.codes }" />
					<ul class="ul-form">
						<li>	
							<label>客户状态</label>
							<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="${data.status }" unShowValue="1,2"></house:xtdmMulit>
						</li>
						<li>							
							<label>签订日期从</label>
							<input type="text" id="signDateFrom" name="signDateFrom" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.signDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>								
							<label>至</label>
							<input type="text" id="signDateTo" name="signDateTo" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${data.signDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>	
							<label>客户来源</label>
							<house:xtdmMulit id="source" dictCode="CUSTOMERSOURCE" selectedValue="${data.source }"></house:xtdmMulit>
						</li>
						<li>							
							<label>下定日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>								
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>	
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${data.address}" />
						</li>
						<li>	
							<label>已付款≥</label>
							<input type="text" id="paidAmount" name="paidAmount" value="" />
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="search()">查询</button>
						</li>
					</ul>
				</form>
			</div>
			
			<table id="addDetailDataTable"></table>
			<div id="addDetailDataTablePager"></div>
		</div>
	</body>
</html>
