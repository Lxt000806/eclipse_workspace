<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		$("#page_form_${param.itemType1}_${param.isService}").setTable();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_clxq_${param.itemType1}_${param.isService}",{
			url:"${ctx}/admin/customerXx/goJqGrid_clxq_zcxq",
			postData:{custCode:'${customer.code}',itemType1:'${param.itemType1}',isService:'${param.isService}'},
			rowNum: 10000,
			height:380,
			colModel : [
					{name : 'iscommi',index : 'iscommi',width : 60,label:'是否提成',sortable : true,align : "left",count:true,hidden: ${param.itemType1=='ZC' && param.isService==1?'false':'true'}},
					{name : 'fixareadescr',index : 'fixareadescr',width : 80,label:'区域名称',sortable : true,align : "left",count:true},
					{name : 'intproddescr',index : 'intproddescr',width : 80,label:'集成成品',sortable : true,align : "left",hidden: ${param.itemType1=='JC'?'false':'true'}},
					{name : 'itemdescr',index : 'itemdescr',width : 150,label:'材料名称',sortable : true,align : "left"},
					{name : 'itemtype2descr',index : 'itemtype2descr',width : 74,label:'材料类型2',sortable : true,align : "left"},
					{name : 'sqlcodedescr',index : 'sqlcodedescr',width : 50,label:'品牌',sortable : true,align : "left",hidden: ${param.itemType1=='RZ'?'false':'true'}},
					{name : 'qty',index : 'qty',width : 50,label:'数量',sortable : true,align : "right",sum:true},
					{name : 'sendqtybywh',index : 'sendqtybywh',width : 60,label:'仓库',sortable : true,align : "left"},
					{name : 'sendqtybyspl',index : 'sendqtybyspl',width : 60,label:'供应商',sortable : true,align : "left"},
					{name : 'sendqty',index : 'sendqty',width : 60,label:'总数量',sortable : true,align : "right",sum:true},
					{name : 'ischeckoutqty',index : 'ischeckoutqty',width : 80,label:'已结算数量',sortable : true,align : "right"},
					{name : 'openqty',index : 'ischeckoutqty',width : 80,label:'已申请数量',sortable : true,align : "right",hidden: ${param.itemType1=='RZ'?'false':'true'}},
					{name : 'confirmedqty',index : 'ischeckoutqty',width : 80,label:'已审核数量',sortable : true,align : "right",hidden: ${param.itemType1=='RZ'?'false':'true'}},
					{name : 'uom',index : 'uom',width : 50,label:'单位',sortable : true,align : "left"},
					{name : 'unitprice',index : 'unitprice',width : 50,label:'单价',sortable : true,align : "right"},
					{name : 'cost',index : 'cost',width : 50,label:'成本',sortable : true,align : "right",sum:true,
					  hidden: ${costRight=='1' && fn:contains(itemRight,param.itemType1)==true?'false':'true'}},
					{name : 'purchasecost',index : 'purchasecost',width : 70,label:'采购成本',sortable : true,align : "right",sum:true,
					  hidden: ${costRight=='1' && fn:contains(itemRight,param.itemType1)==true?'false':'true'}},
					{name : 'costall',index : 'costall',width : 70,label:'成本总价',sortable : true,align : "right",sum:true,
					  hidden: ${costRight=='1' && fn:contains(itemRight,param.itemType1)==true?'false':'true'}},
					{name : 'lineamount',index : 'lineamount',width : 60,label:'总价',sortable : true,align : "right",sum:true},
					{name : 'beflineamount',index : 'beflineamount',width : 80,label:'折扣前金额',sortable : true,align : "right",sum:true},
					{name : 'markup',index : 'markup',width : 50,label:'折扣',sortable : true,align : "right"},
					{name : 'tmplineamount',index : 'tmplineamount',width : 60,label:'小计',sortable : true,align : "right"},
					{name : 'processcost',index : 'processcost',width : 70,label:'其他费用',sortable : true,align : "right",sum:true},
					{name : 'disccost',index : 'disccost',width : 70,label:'优惠分摊',sortable : true,align : "right",sum:true,hidden: ${param.itemType1=='RZ'?'false':'true'}},
					{name : 'aftdiscamount',index : 'aftdiscamount',width : 80,label:'优惠后总价',sortable : true,align : "right",sum:true,hidden: ${param.itemType1=='RZ'?'false':'true'}},
					{name : 'remark',index : 'remark',width : 100,label:'材料描述',sortable : true,align : "left"},
					{name: "itemsetdescr", index: "itemsetdescr", width: 50, label: "套餐包", sortable: true, align: "left",
						hidden: ${param.itemType1=='RZ'?'false':'true'}},
					{name: "isoutsetdescr", index: "isoutsetdescr", width: 80, label: "套餐外材料", sortable: true, align: "left",
						hidden: ${param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
					// 主材预算增加提成类型、提成比例，服务性产品、软装、集成预算不用加 add by zb
					{name: "commitypedescr", index: "commitypedescr", width: 80, label: "提成类型", sortable: true, align: "left",
				  		hidden: ${costRight=='1' && param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
					{name: "commiperc", index: "commiperc", width: 80, label: "提成比例", sortable: true, align: "right",
				  		hidden: ${costRight=='1' && param.itemType1=='ZC' && param.isService=='0'?'false':'true'}},
					{name : 'lastupdate',index : 'lastupdate',width : 120,label:'最后更新时间',sortable : true,align : "left",formatter: formatTime},
					{name : 'lastupdatedby',index : 'lastupdatedby',width : 90,label:'最后更新人员',sortable : true,align : "left"},
					{name : 'expired',index : 'expired',width : 60,label:'是否过期',sortable : true,align : "left"},
					{name : 'actionlog',index : 'actionlog',width : 60,label:'操作日志',sortable : true,align : "left"}
            ],
            grouping: true,
            groupingView: {
                groupField: ["${param.itemType1}"=="JC"?"fixareadescr":"itemtype2descr"],
                groupColumnShow: [true],
                groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
                groupOrder: ["asc"],
                groupCollapse: false
            }
		});
		$("#dataTable_clxq_${param.itemType1}_${param.isService}").jqGrid('setGroupHeaders', {
		    useColSpanStyle: true, 
		    groupHeaders:[{startColumnName: 'sendqtybywh', numberOfColumns: 3, titleText: '已发货数量'}]
		});
		//$("#dataTable_clxq_${param.itemType1}_${param.isService}").jqGrid('filterToolbar');
		$('input','#page_form_${param.itemType1}_${param.isService}').keydown(function(e){
			if(e.keyCode==13){
				goto_query_zcxq('dataTable_clxq_${param.itemType1}_${param.isService}');
			}
		});
});
function goto_query_zcxq(tableId){
	var id = '_zcxq'+tableId.replace('dataTable_clxq','');
	var itemType2 = $("#itemType2"+id).val();
	var fixareadescr = $("#fixareadescr"+id).val();
	var itemdescr = $("#itemdescr"+id).val();
	var intproddescr = $("#intproddescr"+id).val();
	var isCupboard = $("#isCupboard"+id).val();
	$("#"+tableId).jqGrid("setGridParam",
	   {
	       postData: {
	           itemType2:itemType2,
	           fixareadescr:fixareadescr,
	           itemdescr:itemdescr,
	           intproddescr:intproddescr,
	           isCupboard: isCupboard
	       },
	       page:1
	   }).trigger("reloadGrid");
}
//查看小计
function showTmplineAmount() {
   Global.Dialog.showDialog("tmplineAmountView", {
      title: "查看小计",
      url: "${ctx}/admin/customerXx/goTmplineAmountView",
      height: 600,
      width: 1000
   });
}
</script>
<style>
<!--
.ui-th-column, .ui-jqgrid .ui-jqgrid-htable th.ui-th-column{
vertical-align: middle;
}
-->
</style>
<div class="body-box-list" style="margin-top: 5px;">
	<div class="query-form" style="border: 0px;">
		<form role="form" class="form-search" id="page_form_${param.itemType1}_${param.isService}"  action="" method="post" target="targetFrame" >
				<ul class="ul-form">
					<li style="width: 250px;">
						<label style="width: 60px;">材料类型2</label>
						<house:DataSelect id="itemType2_zcxq_${param.itemType1}_${param.isService}" className="ItemType2" keyColumn="code" valueColumn="descr" 
						value="${itemReq.itemType2 }" filterValue="expired='F' and itemType1='${param.itemType1}'" style="width:120px;"></house:DataSelect>
					</li>
					<li style="width: 250px;display:${param.itemType1=='JC'?'':'none'}">
                        <label style="width: 60px;">是否橱柜</label>
                        <house:xtdm id="isCupboard_zcxq_${param.itemType1}_${param.isService}" dictCode="YESNO" style="width:120px;"></house:xtdm>
                    </li>
					<li style="width: 250px;">
						<label style="width: 60px;">区域名称</label>
						<input type="text" id="fixareadescr_zcxq_${param.itemType1}_${param.isService}" name="fixareadescr_zcxq_${param.itemType1}_${param.isService}" style="width:120px;" value="${itemReq.fixareadescr }" />
					</li>
					<li style="width: 250px;">
						<label style="width: 60px;">材料名称</label>
						<input type="text" id="itemdescr_zcxq_${param.itemType1}_${param.isService}" name="itemdescr_zcxq_${param.itemType1}_${param.isService}" style="width:120px;" value="${itemReq.itemdescr }" />
					</li>
					<li style="width: 250px;display:${param.itemType1=='JC'?'':'none'}">
						<label style="width: 60px;">集成成品</label>
						<input type="text" id="intproddescr_zcxq_${param.itemType1}_${param.isService}" name="intproddescr_zcxq_${param.itemType1}_${param.isService}" style="width:120px;" value="${itemReq.intproddescr }" />
					</li>
					<li style="width: 100px;">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query_zcxq('dataTable_clxq_${param.itemType1}_${param.isService}');">查询</button>
					</li>
					<c:if test="${param.itemType1=='RZ' }">
						<li style="width: 100px;">
							<button type="button" class="btn btn-sm btn-system" onclick="showTmplineAmount();">查看小计</button>
						</li>
					</c:if>
				</ul>
		</form>
	</div>
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTable_clxq_${param.itemType1}_${param.isService}"></table>
	</div>
</div>
