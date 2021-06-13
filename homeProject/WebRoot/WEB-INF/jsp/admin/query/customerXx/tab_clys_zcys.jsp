<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		$("#page_form_${param.itemType1}_${param.isService}").setTable();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_clys_${param.itemType1}_${param.isService}",{
			url:"${ctx}/admin/customerXx/goJqGrid_clys_zcys",
			postData:{custCode:'${customer.code}',itemType1:'${param.itemType1}',isService:'${param.isService}'},
			rowNum: 10000,
			height:425,
			colModel : [
				{name : "iscommi",index : "iscommi",width : 100,label:"是否提成",sortable : true,align : "left",count:true,hidden: ${param.itemType1=="ZC" && param.isService==1?"false":"true"}},
			    {name: "fixareadescr", index: "fixareadescr", width: 70, label: "区域名称", sortable: true, align: "left", count: true},
			    {name : "intproddescr",index : "intproddescr",width : 100,label:"集成成品",sortable : true,align : "left",hidden: ${param.itemType1=="JC"?"false":"true"}},
				{name: "itemdescr", index: "itemdescr", width: 200, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left",
					hidden: ${param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
				{name: "projectqty", index: "projectqty", width: 80, label: "预估施工量", sortable: true, align: "right", sum: true},
				{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right", sum: true},
				{name: "uom", index: "uom", width: 45, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 65, label: "单价", sortable: true, align: "right"},
				{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right", 
					hidden: ${costRight=='1' && fn:contains(itemRight,param.itemType1)==true?'false':'true'}},
				{name : 'costall',index : 'costall',width : 80,label:'成本总价',sortable : true,align : "right",sum:true,
			    	  hidden: ${costRight=='1' && fn:contains(itemRight,param.itemType1)==true?'false':'true'}},
				{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
				{name: "markup", index: "markup", width: 48, label: "折扣", sortable: true, align: "right"},
				{name: "tmplineamount", index: "tmplineamount", width: 65, label: "小计", sortable: true, align: "right", sum: true,hidden: ${param.itemType1=="JC"?"true":"false"}},
				{name: "processcost", index: "processcost", width: 70, label: "其他费用", sortable: true, align: "right", sum: true},
				{name: "lineamount", index: "lineamount", width: 65, label: "总价", sortable: true, align: "right", sum: true},
				{name: "profitper", index: "profitper", width: 53, label: "毛利率", sortable: true, align: "right", 
					hidden: ${fn:contains(itemRight,'ZC')==true && param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
				{name: "remark", index: "remark", width: 120, label: "材料描述", sortable: true, align: "left"},
				{name: "itemsetdescr", index: "itemsetdescr", width: 100, label: "套餐包", sortable: true, align: "left",
					hidden: ${param.itemType1=='RZ'?'false':'true'}},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外材料", sortable: true, align: "left",
					hidden: ${param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
				// 主材预算增加提成类型、提成比例，服务性产品、软装、集成预算不用加 add by zb
				{name: "commitypedescr", index: "commitypedescr", width: 80, label: "提成类型", sortable: true, align: "left",
					hidden: ${costRight=='1' && param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
				{name: "commiperc", index: "commiperc", width: 80, label: "提成比例", sortable: true, align: "right",
					hidden: ${costRight=='1' && param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
				{name: "lastupdate", index: "lastupdate", width: 111, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 58, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 48, label: "操作", sortable: true, align: "left"},
            ],
            grouping: true,
            groupingView: {
                groupField: ["itemtype2descr"],
                groupColumnShow: [true],
                groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
                groupOrder: ["asc"],
                groupCollapse: false
            }
		});
		//$("#dataTable_clys_${param.itemType1}_${param.isService}").jqGrid('filterToolbar');
});
function goto_query_zcys(tableId){
	var id = tableId.replace('dataTable_clys','itemType2_clys');
	var str = $("#"+id).val();
	$("#"+tableId).jqGrid("setGridParam",{postData:{itemType2:str},page:1}).trigger("reloadGrid");
}
</script>
<div class="body-box-list">
	<div class="query-form" style="border: 0px;">
		<form role="form" class="form-search" id="page_form_${param.itemType1}_${param.isService}"  action="" method="post" target="targetFrame" >
				<ul class="ul-form">
					<li>
						<label style="width: 60px;">材料类型2</label>
						<house:DataSelect id="itemType2_clys_${param.itemType1}_${param.isService}" className="ItemType2" keyColumn="code" valueColumn="descr" 
						value="${itemPlan.itemType2 }" filterValue="expired='F' and itemType1='${param.itemType1}'" style="width:160px;"></house:DataSelect>
					</li>
					<li style="width: 100px;">
					<button type="button" class="btn btn-sm btn-system" onclick="goto_query_zcys('dataTable_clys_${param.itemType1}_${param.isService}');">查询</button>
					</li>
				</ul>
		</form>
	</div>
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_clys_${param.itemType1}_${param.isService}"></table>
	</div>
</div>
