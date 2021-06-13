<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>材料预算赠品管理添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	//初始化材料类型1，2，3三级联动
	$("#supplCode").openComponent_supplier({showValue:"${item.supplCode}",showLabel:"${supplDescr}"});
	
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
									firstValue:'${item.itemType1 }',
									secondSelect:'itemType2',
									secondValue:'${item.itemType2 }',
									thirdSelect:'itemType1',
									thirdValue:'${item.itemType1 }',
								});
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList");
	var postData = $("#page_form").jsonForm();
	//var puno = $.trim($("#puno").val());
    //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			//url:"${ctx}/admin/itemPlan/goBaseItemPlanJqGrid?custCode=${customer.code}",
			url:"${url}",
			height:$(document).height()-$("#content-list").offset().top-105,
			cellsubmit:'clientArray',
			multiselect:true,
			rowNum:1000,
			colModel : [
					{name: "itemplanpk", index: "itemplanpk", width: 84, label: "itemplanpk", sortable: false, align: "left", hidden: true},
					{name: "iscommi", index: "iscommi", width: 84, label: "审核标识", sortable: false, align: "left",formatter:function(){return 1;}, hidden: true},
					{name: "fixareapk", index: "fixareapk", width: 122, label: "区域名称", sortable:false, align: "left", hidden: true},
					{name: "fixareadescr", index: "fixareadescr", width: 122, label: "区域名称", sortable:false, align: "left"},
					{name: "isfixprice", index: "isfixprice", width: 122, label: "是否固定价格", sortable:false, align: "left", hidden: true},
					{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable:false, align: "left", hidden: true},
					{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable:false, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable:false, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable:false, align: "left",},
					{name: "baseitemcode", index: "baseitemcode", width: 85, label: "基础项目编号", sortable:false, align: "left"},
					{name: "baseitemdescr", index: "baseitemdescr", width: 150, label: "基础项目名称", sortable:false, align: "left",},
					{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
					{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable:false, align: "left", hidden: true},
					{name: "projectqty", index: "projectqty", width: 80, label: "预估施工量", sortable:false, align: "right",hidden: true},
					{name: "qty", index: "qty", width: 70, label: "数量", sortable:false, align: "right",},
					{name: "autoqty", index: "autoqty", width: 80, label: "系统计算量", sortable:false, align: "right",hidden: true},
					{name: "uom", index: "uom", width: 50, label: "单位", sortable:false, align: "left",},
					{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable:false, align: "right",},
					{name: "beflineamount", index: "beflineamount", width: 90, label: "折扣前金额", sortable:false, align: "right", sum: true},
					{name: "markup", index: "markup", width: 60, label: "折扣", sortable:false, align: "right",hidden: true},
					{name: "tmplineamount", index: "tmplineamount", width: 80, label: "小计", sortable:false, align: "right", sum: true,hidden: true},
					{name: "processcost", index: "processcost", width: 80, label: "其他费用", sortable:false, align: "right", sum: true,hidden: true},
					{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable:false, align: "right", sum: true},
					{name: "isoutsetdescr", index: "isoutsetdescr", width: 100, label: "套餐外材料", sortable:false, align: "left", hidden: true},
					{name: "remark", index: "remark", width: 140, label: "材料描述", sortable:false, align: "left",editable:true,edittype:'textarea'},
					{name: "algorithm", index: "algorithm", width: 85, label: "算法", sortable:false, align: "left", hidden: true},
					{name: "algorithmdescr", index: "algorithmdescr", width: 85, label: "算法", sortable:false, align: "left",hidden: true},
					{name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left",hidden: true},
					{name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型", sortable:false, align: "left",hidden: true},
					{name: "cuttype", index: "cuttype", width: 65, label: "切割类型",  width: 0.5,sortable:false, align: "left",hidden: true},
					{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable:false, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable:false, align: "left"},
					{name: "expired", index: "expired", width: 77, label: "是否过期", sortable:false, align: "left", hidden: true},
					{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable:false, align: "left"},
					{name: "dispseq", index: "dispseq", width: 84, label: "dispseq", sortable:false, align: "left", hidden: true},
					{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable:false, align: "left", hidden: true},
					{name: "isoutset", index: "isoutset", width: 122, label: "是否套餐外", sortable:false, align: "left",hidden:true},
					{name: "isservice", index: "isservice", width: 122, label: "是否服务性产品", sortable:false, align: "left",formatter:function(){return 0}, hidden: true},
					{name: "cost", index: "cost", width: 122, label: "成本", sortable:false, align: "right",hidden: true},
		            {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable:false, align: "left"},
		            {name: "preplanareapk", index: "preplanareapk", width: 60, label: "空间pk", sortable:false, align: "left", hidden: true},
		       		{name: "doorpk", index: "doorpk", width: 65, label: "门窗Pk",  width: 0.5,sortable:false, align: "left",hidden: true},
		       		{name: "tempdtpk", index: "tempdtpk",  label: "模板pk",  width: 0.01,sortable:false, align: "left",hidden: true},
		       		{name: "qtymodifycount", index: "qtymodifycount", width: 84, label: "数量被修改", sortable:false, align: "left", hidden: true},
				    {name: "markupmodifycount", index: "markupmodifycount", width: 122, label: "折扣被修改", sortable:false, align: "left", hidden: true},
				    {name: "supplpromitempk", index: "supplpromitempk", width: 122, label: "供应商促销pk", sortable:false, align: "left",hidden:true},
				    {name: "projectcost", index: "projectcost", width: 122, label: "项目经理结算价", sortable:false, align: "left",hidden:true},    
            ],
	});
	if("${url}".split("goBaseItemPlan").length>1){
		jQuery("#dataTable").setGridParam().hideCol("itemcode").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("itemdescr").trigger("reloadGrid");
	}else{
		jQuery("#dataTable").setGridParam().hideCol("baseitemcode").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("baseitemdescr").trigger("reloadGrid");
	}
	console.log("${url}".split("goBaseItemPlanJqGrid"));

	$("#saveBtn").on("click",function(){
		var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
		if(ids.length==0){
		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
			return;
		}
		var selectRows = [];
		$.each(ids,function(k,id){
			var row = $("#dataTable").jqGrid('getRowData', id);
			selectRows.push(row);
		});
		Global.Dialog.returnData = selectRows;
		closeWin();
	});
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${item.expired}" />
				<ul class="ul-form">
					<li>
						<label>材料名称</label>
						<input type="text" id="itemdescr" name="itemdescr" style="width:160px;"/>
					</li>
					<li class="search-group">					
						<input type="checkbox"  id="expired_show" name="expired_show"
						 value="${item.expired }" onclick="hideExpired(this)" 
						 ${purchase.expired!='T'?'checked':'' } /><p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>
</html>


