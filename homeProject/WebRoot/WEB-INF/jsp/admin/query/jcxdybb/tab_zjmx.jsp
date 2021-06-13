<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_zjmx_jczj",{
		url:"${ctx}/admin/jcxdybb/goJczjJqGrid",
		postData:{code:"${customer.code}",dateFrom:"2018-12-01",dateTo:"2018-12-31"},
		height:230,
		rowNum: 10000,
		styleUI:"Bootstrap",
		colModel : [
		    {name: "no", index: "no", width: 93, label: "增减单号", sortable: true, align: "left", count: true},
			{name: "date", index: "date", width: 98, label: "变更时间", sortable: true, align: "left"},
			{name: "befamount", index: "befamount", width: 91, label: "优惠前金额", sortable: true, align: "left",sum:true},
			{name: "discamount", index: "discamount", width: 83, label: "优惠金额", sortable: true, align: "left",sum:true},
			{name: "amount", index: "amount", width: 75, label: "总价", sortable: true, align: "left",sum:true},
        ]
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_zjmx_cgzj",{
		url:"${ctx}/admin/jcxdybb/goCgzjJqGrid",
		postData:{code:"${customer.code}",dateFrom:"2018-12-01",dateTo:"2018-12-31"},
		height:230,
		rowNum: 10000,
		styleUI:"Bootstrap",
		rowNum: 10000,
		colModel : [
		    {name: "no", index: "no", width: 93, label: "增减单号", sortable: true, align: "left", count: true},
			{name: "date", index: "date", width: 98, label: "变更时间", sortable: true, align: "left"},
			{name: "befamount", index: "befamount", width: 91, label: "优惠前金额", sortable: true, align: "left",sum:true},
			{name: "discamount", index: "discamount", width: 83, label: "优惠金额", sortable: true, align: "left",sum:true},
			{name: "amount", index: "amount", width: 75, label: "总价", sortable: true, align: "left",sum:true},
        ]
	});
});

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_tcmx">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_zjmx_jczj" data-toggle="tab">集成领料</a></li>
	        <li class=""><a href="#tab_zjmx_cgzj" data-toggle="tab">橱柜领料</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_zjmx_jczj" class="tab-pane fade in active"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_zjmx_jczj"></table>
					</div>
				</div>
	        </div>  
	        <div id="tab_zjmx_cgzj" class="tab-pane fade"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_zjmx_cgzj"></table>
					</div>
				</div>
	        </div>
	    </div>  
	</div>
</div>
