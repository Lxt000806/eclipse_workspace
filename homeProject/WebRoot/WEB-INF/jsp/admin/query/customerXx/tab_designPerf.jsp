<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function() {
	$("#remainDisc_span").popover({trigger:"hover"});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_basePersonalCommi",{
		height:230,
		rowNum: 10000,
		colModel : [
		    {name: "descr", index: "descr", width: 100, label: "基础项目", sortable: true, align: "left", count: true},
            {name: "qty", index: "qty", width: 100, label: "数量", sortable: true, align: "right"},
			{name: "unitprice", index: "unitprice", width: 80, label: "人工单价", sortable: true, align: "right"},	
			{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "right"},	
			{name: "lineamount", index: "LineAmount", width: 80, label: "总金额", sortable: true, align: "right", sum: true},	
			{name: "perfper", index: "perfper", width: 80, label: "业绩比例", sortable: true, align: "right"},	
			{name: "commiperc", index: "commiperc", width: 80, label: "提成比例", sortable: true, align: "right"},	
			{name: "commi", index: "commi", width: 80, label: "提成金额", sortable: true, align: "right", sum: true},	
        ],
       
	});
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_zcCommi",{
		height:230,
		rowNum: 10000,
		colModel : [
            {name: "descr", index: "descr", width: 150, label: "材料名称", sortable: true, align: "left", count: true},
            {name: "qty", index: "qty", width: 100, label: "数量", sortable: true, align: "right", count: true},
			{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "right", sum: true},	
			{name: "processcost", index: "processcost", width: 80, label: "其他费用", sortable: true, align: "right", sum: true},	
			{name: "lineamount", index: "LineAmount", width: 80, label: "总金额", sortable: true, align: "right", sum: true},	
			{name: "perfper", index: "perfper", width: 80, label: "业绩比例", sortable: true, align: "right"},	
			{name: "commiperc", index: "commiperc", width: 80, label: "提成比例", sortable: true, align: "right"},	
			{name: "commi", index: "commi", width: 80, label: "提成金额", sortable: true, align: "right", sum: true},	
			{name: "issetdescr", index: "issetdescr", width: 100, label: "是否套餐", sortable: true, align: "left"},
	  ],
	});
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_rzCommi",{
		height:230,
		rowNum: 10000,
		colModel : [
            {name: "descr", index: "descr", width: 150, label: "材料名称", sortable: true, align: "left", count: true},
            {name: "qty", index: "qty", width: 100, label: "数量", sortable: true, align: "right", count: true},
			{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "right", sum: true},	
			{name: "processcost", index: "processcost", width: 80, label: "其他费用", sortable: true, align: "right", sum: true},	
			{name: "lineamount", index: "LineAmount", width: 80, label: "总金额", sortable: true, align: "right", sum: true},	
			{name: "perfper", index: "perfper", width: 80, label: "业绩比例", sortable: true, align: "right"},	
			{name: "commiperc", index: "commiperc", width: 80, label: "提成比例", sortable: true, align: "right"},	
			{name: "commi", index: "commi", width: 80, label: "提成金额", sortable: true, align: "right", sum: true},
        ],
	});
	
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable_jcCommi",{
		height:230,
		rowNum: 10000,
		colModel : [
            {name: "descr", index: "descr", width: 150, label: "材料名称", sortable: true, align: "left", count: true},
            {name: "qty", index: "qty", width: 100, label: "数量", sortable: true, align: "right", count: true},
			{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "right", sum: true},	
			{name: "processcost", index: "processcost", width: 80, label: "其他费用", sortable: true, align: "right", sum: true},	
			{name: "lineamount", index: "LineAmount", width: 80, label: "总金额", sortable: true, align: "right", sum: true},	
			{name: "itemperfper", index: "itemperfper", width: 100, label: "材料业绩比例", sortable: true, align: "right"},	
			{name: "perfper", index: "perfper", width: 80, label: "业绩比例", sortable: true, align: "right"},	
			{name: "commiperc", index: "commiperc", width: 80, label: "提成比例", sortable: true, align: "right"},	
			{name: "commi", index: "commi", width: 80, label: "提成金额", sortable: true, align: "right", sum: true},	
        ],
	});
	if ("${customer.type}"=="1" || "${customer.type}"=="3" ){
		loadCommiDetailTable();
	}
});

function loadCommiDetailTable(){
	$("#dataTable_basePersonalCommi").jqGrid("setGridParam",{url:"${ctx}/admin/customerXx/goBasePersonalEstimateJqGrid",datatype:'json',postData:{code:"${customer.code}"},page:1}).trigger("reloadGrid");
	$("#dataTable_zcCommi").jqGrid("setGridParam",{url:"${ctx}/admin/customerXx/goZCEstimateJqGrid",datatype:'json',postData:{code:"${customer.code}"},page:1}).trigger("reloadGrid");
	$("#dataTable_rzCommi").jqGrid("setGridParam",{url:"${ctx}/admin/customerXx/goRZEstimateJqGrid",datatype:'json',postData:{code:"${customer.code}"},page:1}).trigger("reloadGrid");
	$("#dataTable_jcCommi").jqGrid("setGridParam",{url:"${ctx}/admin/customerXx/goJCEstimateJqGrid",datatype:'json',postData:{code:"${customer.code}"},page:1}).trigger("reloadGrid");
}

function doExcel(){
	if ($("#id_commiDetail>ul li a[href='#tab_basePersonalCommi']").parent().hasClass("active")){
		doExcelNow('基础个性化提成明细','dataTable_basePersonalCommi','dataForm');
	}
	else if ($("#id_commiDetail>ul li a[href='#tab_zcCommi']").parent().hasClass("active")){
		doExcelNow('主材提成明细','dataTable_zcCommi','dataForm');
	}
	else if ($("#id_commiDetail>ul li a[href='#tab_rzCommi']").parent().hasClass("active")){
		doExcelNow('软装提成明细','dataTable_rzCommi','dataForm');
	}
	else if ($("#id_commiDetail>ul li a[href='#tab_jcCommi']").parent().hasClass("active")){
		doExcelNow('集成提成明细','dataTable_jcCommi','dataForm');
	}
	
}
</script>

<div class="body-box-list" style="margin-top: 0px;">
    <div class="panel-body">
       <ul class="ul-form">
       		<li>
				<label>总提成</label>
				<input type="text" id="designerTotalCommi" name="designerTotalCommi"  value="${perfEstimateMap.DesignerTotalCommi}" readonly="readonly" />
			</li>
			<li>
				<label>业绩提成</label>
				<input type="text" id="designerPerfCommi" name="designerPerfCommi"  value="${perfEstimateMap.DesignerPerfCommi}" readonly="readonly" />
			</li>
			<li>
				<label>销售提成</label>
				<input type="text" id="designerSalesCommi" name="designerSalesCommi"  value="${perfEstimateMap.DesignerSalesCommi}" readonly="readonly" />
			</li>
			<li>
				<label>设计费提成</label>
				<input type="text" id="designerDesignFeeCommi" name="designerDesignFeeCommi"  value="${perfEstimateMap.DesignerDesignFeeCommi}" readonly="readonly" />
			</li>
			<li>
				<label>业绩金额</label>
				<input type="text" id="recalPerf" name="recalPerf"  value="${perfEstimateMap.RecalPerf}" readonly="readonly" />
				<span class="glyphicon glyphicon-question-sign" id = "remainDisc_span" 
											data-container="body"  
											data-content="${perfEstimateMap.PerfExprRemarks}【${perfEstimateMap.PerfExprWithNum}】"  
											data-placement="bottom" trigger="hover"
											style="font-size: 16px;color:rgb(25,142,222);"></span>
			</li>
			<%--<li>
				<label>业绩公式说明</label>
				<input type="text" id="perfExprRemarks" name="perfExprRemarks"  value="${perfEstimateMap.PerfExprRemarks}" readonly="readonly" />
			</li>	
			<li>
				<label>业绩公式</label>
				<input type="text" id="perfExprWithNum" name="perfExprWithNum"  value="${perfEstimateMap.PerfExprWithNum}" readonly="readonly" />
			</li>
			--%>
			<li>
				<label style="width: 78px;">提成点</label>
				<input type="text" id="designerPerfCommiPer" name="designerPerfCommiPer"  value="${perfEstimateMap.DesignerPerfCommiPer}" readonly="readonly" />
			</li>
			
			<li>
				<label>基础个性化提成</label>
				<input type="text" id="basePersonalCommi" name="basePersonalCommi"  value="${perfEstimateMap.BasePersonalCommi}" readonly="readonly" />
			</li>
			<li>
				<label>主材提成</label>
				<input type="text" id="zcCommi" name="zcCommi"  value="${perfEstimateMap.ZCCommi}" readonly="readonly" />
			</li>
			<li>
				<label>软装提成</label>
				<input type="text" id="rzCommi" name="rzCommi"  value="${perfEstimateMap.RZCommi}" readonly="readonly" />
			</li>
			<li>
				<label>集成提成</label>
				<input type="text" id="jcCommi" name="jcCommi"  value="${perfEstimateMap.JCCommi}" readonly="readonly" />
			</li>
			<li>
				<label>设计费</label>
				<input type="text" id="designFee" name="designFee"  value="${perfEstimateMap.DesignFee}" readonly="readonly" />
			</li>
			<li>
				<label>标准绘图费</label>
				<input type="text" id="stdDrawFee" name="stdDrawFee"  value="${perfEstimateMap.StdDrawFee}" readonly="readonly" />
			</li>
			<li>
				<label>标准效果图</label>
				<input type="text" id="stdColorDrawFee" name="stdColorDrawFee"  value="${perfEstimateMap.StdColorDrawFee}" readonly="readonly" />
			</li>
			 
	   </ul>	
    </div>
    <div class="container-fluid" id="id_commiDetail">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_basePersonalCommi" data-toggle="tab">基础个性化提成明细</a></li>
	        <li class=""><a href="#tab_zcCommi" data-toggle="tab">主材提成明细</a></li>
	        <li class=""><a href="#tab_rzCommi" data-toggle="tab">软装提成明细</a></li>
	        <li class=""><a href="#tab_jcCommi" data-toggle="tab">集成提成明细</a></li>
	        <a href="javascript:void(0)" onclick="doExcel()" style="position: relative;left: 10px;top: 4px;font-size: 13px;">【导出excel】</a>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_basePersonalCommi" class="tab-pane fade in active"> 
	         	<div class="body-box-list">
					<div class="pageContent" style="padding-top: 10px;">
						<table id="dataTable_basePersonalCommi"></table>
					</div>
				</div>
	        </div>  
	        <div id="tab_zcCommi" class="tab-pane fade"> 
	         	<div class="body-box-list">
					<div class="pageContent" style="padding-top: 10px;">
						<table id="dataTable_zcCommi"></table>
					</div>
				</div>
	        </div>
	        <div id="tab_rzCommi" class="tab-pane fade"> 
	         	<div class="body-box-list">
					<div class="pageContent" style="padding-top: 10px;">
						<table id="dataTable_rzCommi"></table>
					</div>
				</div>
	        </div>
	        <div id="tab_jcCommi" class="tab-pane fade"> 
	         	<div class="body-box-list">
					<div class="pageContent" style="padding-top: 10px;">
						<table id="dataTable_jcCommi"></table>
					</div>
				</div>
	        </div>  
	    </div>  
	</div>
</div>



