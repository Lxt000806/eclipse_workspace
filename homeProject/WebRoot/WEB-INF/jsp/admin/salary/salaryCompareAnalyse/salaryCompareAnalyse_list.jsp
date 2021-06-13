<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料销售分析--按单品</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
	function doExcel() {
		var url ="${ctx}/admin/itemSaleAnalyse/doExcel";
		doExcelAll(url);
	}
$(function(){
	//初始化表格
	/* Global.JqGrid.initJqGrid("dataTable",{
		//url:"${ctx}/admin/salaryCompareAnalyse/goJqGrid",
		height:50,
		styleUI: "Bootstrap",
		postData:$("#page_form").jsonForm(),
		colModel : [
		  {name : "abasesalary",index : "abasesalary",width : 75,label:"本月应发",sortable : true,align : "right"},
		  {name : "bbasesalary",index : "bbasesalary",width : 75,label:"对比月应发",sortable : true,align : "right"},
	      {name : "chgbasesalary",index : "chgbasesalary",width : 75,label:"应发变动",sortable : true,align : "right"},
	      {name : "basesalaryper", index: "basesalaryper", width: 95, label: "应发变动比例", sortable: true, align: "right"},
	      {name : "bempnum",index : "bempnum",width : 75,label:"上月人数",sortable : true,align : "right"},
	      {name : "aempnum", index: "aempnum", width:75 , label: "本月人数", sortable: true, align: "right"},
	      {name : "chgempnum", index: "chgempnum", width: 75, label: "人员变动", sortable: true, align: "right"},
	      {name : "ajoinnum", index: "ajoinnum", width: 95, label: "本月入职人数", sortable: true, align: "right"},
	      {name : "ajoinbasesalary", index: "ajoinbasesalary", width: 95, label: "本月入职应发", sortable: true, align: "right"},
	      {name : "ajoinrealpaysalary", index: "ajoinrealpaysalary", width: 95, label: "本月入职实发", sortable: true, align: "right"},
	      {name : "aleavenum", index: "aleavenum", width: 95, label: "本月离职人数", sortable: true, align: "right"},
	      {name : "aleavebasesalary", index: "aleavebasesalary", width: 95, label: "本月离职应发", sortable: true, align: "right"},
	      {name : "aleaverealpaysalary", index: "aleaverealpaysalary", width: 95, label: "本月离职实发", sortable: true, align: "right"},
	      {name : "aunpaidsalary",index : "aunpaidsalary",width : 95,label:"本月未付合计",sortable : true,align : "right",},
	      {name : "bunpaidsalary",index : "bunpaidsalary",width : 95,label:"上月未付合计",sortable : true,align : "right",},
	    ]
	}); */
	window.query = function(dataTableName, url){
		var salaryScheme = $("#salaryScheme").val();
		if(salaryScheme == ""){
		
			return;
		}
				
		$("#"+dataTableName).jqGrid("setGridParam",{
			postData:$("#page_form").jsonForm(),page:1,url: url}).trigger("reloadGrid");
	};
	
	window.goto_query = function(){
		var salaryScheme = $("#salaryScheme").val();
		var compareMon = $("#compareMon").val();
		var salaryMon = $("#salaryMon").val();
		if(salaryScheme == ""){
			art.dialog({
				content:"请选择薪酬方案"
			});
			return;
		}
		if(salaryMon == ""){
			art.dialog({
				content:"请选择薪酬月份"
			});
			return;
		}
		if(compareMon == ""){
			art.dialog({
				content:"请选择对比月份"
			});
			return;
		}	
		
		$.ajax({
		     url : "${ctx}/admin/salaryCompareAnalyse/goJqGrid",
		     type : "post",
		     data : {
		          salaryScheme:salaryScheme,salaryMon:salaryMon,compareMon:compareMon
		     },
		     async:false,
		     dataType : "json",
		     cache : false,
		     error : function(obj) {
		          art.dialog({
			 		 content:"未查到相关记录！"
				  });
		     },
		     success : function(obj) {
		        setAnalysisData(obj);
		     }
		});
	};
});

function chgSalaryMon(){
	var salaryMon = $("#salaryMon").val();
	$.ajax({
	     url : "${ctx}/admin/salaryCompareAnalyse/getLastMon",
	     type : "post",
	     data : {
	          salaryMon: salaryMon
	     },
	     async:false,
	     dataType : "json",
	     cache : false,
	     error : function(obj) {
	          art.dialog({
		 		 content:"未查到相关记录！"
			  });
	     },
	     success : function(obj) {
	        if(obj){
	        	$("#compareMon").val(obj.lastMon);	
	        	$("#salaryScheme").val(obj.salaryScheme);	
	        }
	     }
	});
}

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li class="form-validate">
						<label style="width:80px">薪酬月份</label>
						<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon order by salaryMon desc"  
							 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMon }" onchange="chgSalaryMon()"></house:dict>
					</li>
					<li class="form-validate">
						<label style="width:80px">对比月份</label>
						<house:dict id="compareMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon order by salaryMon desc"  
							 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.compareMon }"></house:dict>
					</li>
					<li class="form-validate">
						<label style="width:80px">薪酬方案</label>
						<house:dict id="salaryScheme" dictCode="" sql="select pk,descr from tSalaryScheme"  
							 sqlValueKey="pk" sqlLableKey="descr" value="${salaryData.salaryScheme }"></house:dict>
					</li>
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div id="content-list">
				<!-- <table id= "dataTable"></table> -->
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >  
				<li class="active"><a href="#tab_analysis" data-toggle="tab">分析概况</a></li>
				<li ><a href="#tab_joinEmp" data-toggle="tab" onclick="query('joinEmpDataTable', '${ctx}/admin/salaryCompareAnalyse/goJoinEmpJqGrid')">本月入职人员</a></li>
				<li class="" onclick="query('leaveEmpDataTable', '${ctx}/admin/salaryCompareAnalyse/goLeaveEmpJqGrid')"><a href="#tab_leaveEmp" data-toggle="tab">本月离职人员</a></li>
				<li class="" onclick="query('baseSalaryChgEmpDataTable', '${ctx}/admin/salaryCompareAnalyse/goBaseSalaryChgEmpJqGrid')"><a href="#tab_baseSalaryChgEmp" data-toggle="tab">应发变动名单</a></li>
				<li class="" onclick="query('realPayDataTable', '${ctx}/admin/salaryCompareAnalyse/goRealPayJqGrid')"><a href="#tab_realPayChgEmp" data-toggle="tab">实发变动名单</a></li>
				<li class="" onclick="query('unPaidEmpDataTable', '${ctx}/admin/salaryCompareAnalyse/goUnPaidEmpJqGrid')"><a href="#tab_umPaidEmp" data-toggle="tab">本月未付明细</a></li>
			</ul> 
			<div class="tab-content">  
				<div id="tab_analysis"  class="tab-pane fade in active"> 
	         		<jsp:include page="tab_analysis.jsp"></jsp:include>
		        </div> 
				<div id="tab_joinEmp"  class="tab-pane fade "> 
	         		<jsp:include page="tab_joinEmp.jsp"></jsp:include>
		        </div> 
		        <div id="tab_leaveEmp"  class="tab-pane fade "> 
	         		<jsp:include page="tab_leaveEmp.jsp"></jsp:include>
		        </div> 
		        <div id="tab_baseSalaryChgEmp"  class="tab-pane fade "> 
	         		<jsp:include page="tab_baseSalaryChgEmp.jsp"></jsp:include>
		        </div> 
		        <div id="tab_realPayChgEmp"  class="tab-pane fade "> 
	         		<jsp:include page="tab_realPayChgEmp.jsp"></jsp:include>
		        </div> 
		        <div id="tab_umPaidEmp"  class="tab-pane fade "> 
	         		<jsp:include page="tab_umPaidEmp.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
	</div>
</body>
</html>


