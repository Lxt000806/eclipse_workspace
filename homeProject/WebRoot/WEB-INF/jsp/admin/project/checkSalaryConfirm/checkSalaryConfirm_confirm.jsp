<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>结算工资发放确认</title>
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
		      	<button type="button" id="confirmBtn" class="btn btn-system" >确认</button>
				<button type="button" id="closeBtn" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" value="${custCheck.custCode}" readonly="true" />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${custCheck.address}" readonly="true" />
						</li>
						<li id="salaryConfirmCZY_show">	
							<label>确认人</label>
							<input type="text" id="salaryConfirmCZY" name="salaryConfirmCZY" style="width:79px;" value="${custCheck.salaryConfirmCZY}" readonly="true"/> 
							<input type="text" id="salaryConfirmCZYDescr" name="salaryConfirmCZYDescr" style="width:79px;" value="${custCheck.salaryConfirmCZYDescr}" readonly="true"/> 
						</li>	
						<li id="salaryConfirmDate_show">
							<label>确认时间</label>
							<input type="text" id="salaryConfirmDate" name="salaryConfirmDate"  value="<fmt:formatDate value='${custCheck.salaryConfirmDate}' pattern='yyyy-MM-dd'/>"   disabled="true"/>	
						</li>	
						
				</ul>
			</form>
		</div>
	</div>
	<div class="container-fluid" >  
		<ul class="nav nav-tabs" >
			<li class="active"><a href="#custVisit_tabView_customer" data-toggle="tab">工资发放明细</a></li>  
		</ul>
	    <div class="tab-content">  
			<div id="custVisit_tabView_customer"  class="tab-pane fade in active"> 
	         	<div class="body-box-list">
					
					<div id="content-list">
						<table id="dataTable"></table>
						<div id="dataTablePager"></div>
					</div>
				</div>
			</div> 
		</div>	
	</div>
</div>	
<script>
	$(function() {
		var originalData = $("#page_form").serialize();
		var gridOption = {
			url:"${ctx}/admin/checkSalaryConfirm/goSalaryDetailJqGrid",
			postData:{custCode:"${custCheck.custCode}"},
			sortable: true,
			height : $(document).height()-$("#content-list").offset().top - 80,
			rowNum : 100000,
			pager:'1',
			colModel : [
				{name: "workercodedescr", index: "workercodedescr", width: 100, label: "工人", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种", sortable: true, align: "left"},
				{name: "comedate", index: "comedate", width: 100, label: "安排进场时间", sortable: true, align: "left",formatter: formatDate},
				{name: "fixsalary", index: "fixsalary", width: 90, label: "工资定额", sortable: true, align: "right",sum:true},
				{name: "paysalary", index: "paysalary", width: 90, label: "已发放工资", sortable: true, align: "right",sum:true},
				{name: "payper", index: "payper", width: 70, label: "发放占比", sortable: true, align: "right",formatter:"number",formatoptions:{decimalPlaces: 0, suffix: "%"}},	
				{name: "appamount", index: "appamount", width: 90, label: "已申请工资", sortable: true, align: "right",sum:true}
			],
			loadonce: true,
			gridComplete:function(){
				var sumpayper=0;
				var sumfixsalary=myRound($("#dataTable").getCol('fixsalary',false,'sum')); 
				var sumpaysalary=myRound($("#dataTable").getCol('paysalary',false,'sum'));
		        if(sumfixsalary!=0){
		         	sumpayper=myRound((sumpaysalary)*100.00/sumfixsalary,0);	
		        }
		        $("#dataTable").footerData('set', {'payper': sumpayper});     
	        }	
		};
	    if("V"=="${custCheck.m_umState}"){
			$("#confirmBtn").remove();
			$("#Confirm_show").remove();	
		}else{
			$("#salaryConfirmCZY_show").remove();
			$("#salaryConfirmDate_show").remove();		
		}
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	});
	
	$("#confirmBtn").on("click",function(){
		var datas = $("#page_form").jsonForm();
		$.ajax({
			url:"${ctx}/admin/checkSalaryConfirm/doConfirm",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "确认数据出错"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
	    	}
		});
		
	});
</script>	
</body>
</html>


