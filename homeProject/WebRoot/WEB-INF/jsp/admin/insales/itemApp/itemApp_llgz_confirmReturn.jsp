<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>审核退回领料单页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$("#confirmReturnMainManCode").openComponent_employee();
				Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "confirmReturnItemType1", "confirmReturnItemType2")
				Global.JqGrid.initJqGrid("confirmReturnDataTable",{
					height:390,
					width:1200,
        			autowidth: false,
					url:"${ctx}/admin/itemApp/goConfirmReturnJqGrid",
					postData:{
						itemType1:$("#confirmReturnItemType1").val(),
						itemType2:$("#confirmReturnItemType2").val(),
						mainManCode:$("#confirmReturnMainManCode").val()
					},
					styleUI:"Bootstrap",
					colModel:[
						{name: "typedescr", index: "typedescr", width: 100, label: "领料类型", sortable: true, align: "left"},
						{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
						{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
						{name: "issetitemdescr", index: "issetitemdescr", width: 73, label: "套餐材料", sortable: true, align: "left"},
						{name: "documentno", index: "documentno", width: 100, label: "档案编号", sortable: true, align: "left"},
						{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
						{name: "no", index: "no", width: 100, label: "领料单号", sortable: true, align: "left"},
						{name: "preappno", index: "preappno", width: 100, label: "预申请单号", sortable: true, align: "left"},
						{name: "date", index: "date", width: 100, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
						{name: "custdescr", index: "custdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
						{name: "statusdescr", index: "statusdescr", width: 120, label: "领料单状态", sortable: true, align: "left"},
						{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
						{name: "sendtypedescr", index: "sendtypedescr", width: 100, label: "发货类型", sortable: true, align: "left"},
						{name: "delivtypedescr", index: "delivtypedescr", width: 100, label: "配送方式", sortable: true, align: "left"},
						{name: "amount", index: "amount", width: 90, label: "成本总价", sortable: true, align: "right", hidden: true},
						{name: "projectamount", index: "projectamount", width: 130, label: "项目经理结算总价", sortable: true, align: "right", hidden: true},
						{name: "splstatusdescr", index: "splstatusdescr", width: 96, label: "供应商状态", sortable: true, align: "left"},
						{name: "arrivedate", index: "arrivedate", width: 108, label: "预计到货时间", sortable: true, align: "left", formatter: formatTime},
						{name: "splremark", index: "splremark", width: 153, label: "供应商备注", sortable: true, align: "left"},
						{name: "appname", index: "appname", width: 80, label: "申请人员", sortable: true, align: "left"},
						{name: "confirmname", index: "confirmname", width: 80, label: "审批人员", sortable: true, align: "left"},
						{name: "confirmdate", index: "confirmdate", width: 100, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
						{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
						{name: "projectman", index: "projectman", width: 100, label: "项目经理", sortable: true, align: "left"},
						{name: "phone", index: "phone", width: 100, label: "电话", sortable: true, align: "left"},
						{name: "printtimes", index: "printtimes", width: 80, label: "打印次数", sortable: true, align: "right"},
						{name: "printczydescr", index: "printczydescr", width: 100, label: "打印人", sortable: true, align: "left"},
						{name: "printdate", index: "printdate", width: 140, label: "打印日期", sortable: true, align: "left", formatter: formatTime},
						{name: "mainmandescr", index: "mainmandescr", width: 72, label: "主材管家", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
						{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
						{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"},
						
						{name: "type", index: "type", width: 100, label: "type", sortable: true, align: "left",hidden:true}
					]
				});
			});
			function confirmReturnSearch(){
				$("#confirmReturnDataTable").jqGrid("setGridParam",{
					postData:{
						itemType1:$("#confirmReturnItemType1").val(),
						itemType2:$("#confirmReturnItemType2").val(),
						mainManCode:$("#confirmReturnMainManCode").val()
					},
					page:1,
					sortname:""
				}).trigger("reloadGrid");
			}
		</script>
	</head>
	<body>
		<div class="query-form" style="border:0px;">
			<form action="" method="post" id="page_form" class="form-search">
				<ul class="ul-form">
					<li>
                        <label>材料类型1</label>
                        <select id="confirmReturnItemType1" name="confirmReturnItemType1"></select>
                    </li>
                    <li>
                        <label>材料类型2</label>
                        <select id="confirmReturnItemType2" name="confirmReturnItemType2"></select>
                    </li>
					<li>
						<label>主材管家</label>
						<input type="text" id="confirmReturnMainManCode" />
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="confirmReturnSearch()">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div>
			<table id="confirmReturnDataTable"></table>
			<div id="confirmReturnDataTablePager"></div>
		</div>
	</body>
</html>

