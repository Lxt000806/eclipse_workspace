<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_llmx_jcll",{
		url:"${ctx}/admin/jcxdybb/goJcllJqGrid",
		postData:{code:"${customer.code}",dateFrom:"2018-12-01",dateTo:"2018-12-31"},
		height:230,
		rowNum: 10000,
		styleUI:"Bootstrap",
		colModel : [
		    {name: "no", index: "no", width: 93, label: "领料单号", sortable: true, align: "left", count: true},
			{name: "confirmdate", index: "confirmdate", width: 98, label: "审核时间", sortable: true, align: "left"},
			{name: "jccpname", index: "jccpname", width: 91, label: "集成成品名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 83, label: "材料编号", sortable: true, align: "left"},
			{name: "clname", index: "clname", width: 75, label: "材料名称", sortable: true, align: "left"},
			{name: "llqty", index: "llqty", width: 80, label: "领料数量", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 180, label: "单价", sortable: true, align: "left"},
			{name: "markup", index: "markup", width: 146, label: "折扣", sortable: true, align: "left"},
			{name: "jsje", index: "jsje", width: 80, label: "决算金额", sortable: true, align: "left",sum:true},
			{name: "clxqqty", index: "clxqqty", width: 80, label: "材料需求数量", sortable: true, align: "right"},
			{name: "lineamount", index: "lineamount", width: 89, label: "材料需求总价", sortable: true, align: "left", sum: true},
        ]
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_llmx_cgll",{
		url:"${ctx}/admin/jcxdybb/goCgllJqGrid",
		postData:{code:"${customer.code}",dateFrom:"2018-12-01",dateTo:"2018-12-31"},
		height:230,
		rowNum: 10000,
		styleUI:"Bootstrap",
		rowNum: 10000,
		colModel : [
		    {name: "no", index: "no", width: 93, label: "领料单号", sortable: true, align: "left", count: true},
			{name: "confirmdate", index: "confirmdate", width: 98, label: "审核时间", sortable: true, align: "left"},
			{name: "jccpname", index: "jccpname", width: 91, label: "集成成品名称", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 83, label: "材料编号", sortable: true, align: "left"},
			{name: "clname", index: "clname", width: 75, label: "材料名称", sortable: true, align: "left"},
			{name: "llqty", index: "llqty", width: 80, label: "领料数量", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 180, label: "单价", sortable: true, align: "left"},
			{name: "markup", index: "markup", width: 146, label: "折扣", sortable: true, align: "left"},
			{name: "jsje", index: "jsje", width: 80, label: "决算金额", sortable: true, align: "left",sum:true},
			{name: "clxqqty", index: "clxqqty", width: 80, label: "材料需求数量", sortable: true, align: "right"},
			{name: "lineamount", index: "lineamount", width: 89, label: "材料需求总价", sortable: true, align: "left", sum: true},
        ]
	});
});

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_tcmx">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_llmx_jcll" data-toggle="tab">集成领料</a></li>
	        <li class=""><a href="#tab_llmx_cgll" data-toggle="tab">橱柜领料</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_llmx_jcll" class="tab-pane fade in active"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_llmx_jcll"></table>
					</div>
				</div>
	        </div>  
	        <div id="tab_llmx_cgll" class="tab-pane fade"> 
	         	<div class="pageContent">
					<div id="content-list" style="padding-top: 10px;">
						<table id="dataTable_llmx_cgll"></table>
					</div>
				</div>
	        </div>
	    </div>  
	</div>
</div>
