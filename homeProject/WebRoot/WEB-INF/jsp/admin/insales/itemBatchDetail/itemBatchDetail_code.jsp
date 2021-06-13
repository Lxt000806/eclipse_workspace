<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>材料批次编号</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");		
		$("#page_form select").val('');
	}
	function goto_query(){
		if($("#ibdno").val()==""){
			$("#dataTable").jqGrid("clearGridData");
			return;
		}
		var postData = $("#page_form").jsonForm();
		$("#dataTable").jqGrid("setGridParam", {
	    	url: "${ctx}/admin/itemBatchDetail/goItemBatchDetailJqGrid",
	    	postData: postData
	  	}).trigger("reloadGrid");
	}
	//确定
	function save(){
		var selectRows = [{no:$("#ibdno").val()}];	
		Global.Dialog.returnData = selectRows;
		Global.Dialog.closeDialog();
	}
	
	//输出到Excel
	function excel(){	
		var excelName="材料批次明细-"+'${itemBatchDetail.custCode}'+"-"+$.trim($("#ibdno").val());
		doExcelNow(excelName,"dataTable","page_form");
	};
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			rowNum:100000,  
			pager :'1',
			colModel : [
				{name: "opertypedescr", index: "opertypedescr", width: 68, label: "操作类型", sortable: true, align: "left"},
				{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间", sortable: true, align: "left"},
				{name: "areaattr", index: "areaattr", width: 70, label: "区域分类", sortable: true, align: "left"},
				{name: "itcode", index: "itcode", width: 70, label: "材料编号", sortable: true, align: "left"},
				{name: "itcodedescr", index: "itcodedescr", width: 150, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 140, label: "选材备注", sortable: true, align: "left"},
				{name: "oldreqpk", index: "oldreqpk", width: 70, label: "原需求PK", sortable: true, align: "left",hidden:true},
				{name: "olditemcode", index: "olditemcode", width: 80, label: "原材料编号", sortable: true, align: "left"},
				{name: "olditemdescr", index: "olditemdescr", width: 120, label: "原材料名称", sortable: true, align: "left"},
				{name: "fixareadescr", index: "fixareadescr", width: 70, label: "区域", sortable: true, align: "left"},
				{name: "olditemtype2descr", index: "olditemtype2descr", width: 110, label: "原材料类型2", sortable: true, align: "left"},
				{name: "oldqty", index: "oldqty", width: 55, label: "原数量", sortable: true, align: "right"},
				{name: "olduomdescr", index: "olduomdescr", width: 55, label: "原单位", sortable: true, align: "left"},
				{name: "oldisservicedescr", index: "oldisservicedescr", width: 100, label: "原服务性产品", sortable: true, align: "left"},
				{name: "oldisoutsetdescr", index: "oldisoutsetdescr", width: 100, label: "原套餐外材料", sortable: true, align: "left"},	
			]	
		});
	});
	
</script>
</head>   
<body>
	<div class="body-box-list">
		<div id="buttonView" class="panel panel-system"  >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button type="button" class="btn btn-system "  id="saveBtn" onclick="save()">确定</button>
						<button type="button" class="btn btn-system "  id="excel" onclick="excel()">输出到Excel</button>
						<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
		</div>
		<div class="query-form">
		  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="custCode" name="custCode"  value="${itemBatchDetail.custCode}" />
			<input type="hidden" id="itemType1" name="itemType1"  value="${itemBatchDetail.itemType1}" />
			<ul class="ul-form">	
				<li><label>批次编号</label> 
					<house:dict id="ibdno" dictCode=""
						sql=" select a.No,a.No+' '+a.Remarks+' '+b.ZWXM+convert(varchar(100), getdate(), 10)  Descr from tItemBatchHeader a
							  left join tCZYBM b on b.CZYBH=a.LastUpdatedBy 
							  where a.Expired='F' and a.BatchType='1' and a.status='1'
						      and a.custCode='${itemBatchDetail.custCode}' and a.itemType1='${itemBatchDetail.itemType1}' 
						      and  not exists(select 1 from titemchg ch where ch.custCode='${itemBatchDetail.custCode}' and ch.ItemBatchNo=a.no 
						      and ch.IsService='${itemBatchDetail.isService}' and ch.No<>'${itemBatchDetail.chgNo}') 
						      order by date desc "
						sqlValueKey="No" sqlLableKey="Descr" onchange="goto_query()"/>
				</li>
				<li>
					<label>操作类型</label>
					 <house:xtdm id="operType" dictCode="IBDTOPERTYPE" onchange="goto_query()" ></house:xtdm>	
			    </li>
				<li class="search-group"><input type="checkbox" id="expired" name="expired"
					value="${baseItemPlanBak.expired}" onclick="hideExpired(this)" ${itemBatchDetail!='T' ?'checked':'' }/>
					<p>隐藏过期</p>
					<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
					<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
				</li>
			</ul>
		  </form>
		</div>
		
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>



