<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>供应商未接收领料单页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$("#unReceiveBySplSupplCode").openComponent_supplier({
			    	condition:{
			    		itemRight:"${data.itemRightForSupplier}"
			    	}
				});
				$("#unReceiveBySplMainManCode").openComponent_employee();
				Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "unReceiveBySplItemType1", "unReceiveBySplItemType2")
				Global.JqGrid.initJqGrid("unReceiveBySplDataTable",{
					height:360,
					width:1200,
        			autowidth: false,
					url:"${ctx}/admin/itemApp/goUnReceiveBySplJqGrid",
					postData:{
						itemType1:$("#unReceiveBySplItemType1").val(),
						itemType2:$("#unReceiveBySplItemType2").val(),
						confirmDateFrom:$("#confirmDateFrom").val(),
						confirmDateTo:$("#confirmDateTo").val(),
						mainManCode:$("#unReceiveBySplMainManCode").val(),
						supplCode:$("#unReceiveBySplSupplCode").val(),
						delivType:$("#delivType").val()
					},
					styleUI:"Bootstrap",
					colModel:[
						{name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
						{name: "itemtype2descr", index: "itemtype2descr", width: 70, label: "材料类型2", sortable: true, align: "left"},
						{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
						{name: "custdescr", index: "custdescr", width: 70, label: "客户名称", sortable: true, align: "left"},
						{name: "address", index: "address", width: 210, label: "楼盘", sortable: true, align: "left"},
						{name: "no", index: "no", width: 75, label: "领料单号", sortable: true, align: "left"},
						{name: "issetitemdescr", index: "issetitemdescr", width: 73, label: "套餐材料", sortable: true, align: "left"},
						{name: "date", index: "date", width: 100, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
						{name: "confirmdate", index: "confirmdate", width: 100, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
						{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
						{name: "delivtypedescr", index: "delivtypedescr", width: 75, label: "配送方式", sortable: true, align: "left"},
						{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
						{name: "projectman", index: "projectman", width: 70, label: "项目经理", sortable: true, align: "left"},
						{name: "phone", index: "phone", width: 70, label: "电话", sortable: true, align: "left"},
						{name: "mainmandescr", index: "mainmandescr", width: 70, label: "主材管家", sortable: true, align: "left"},
						{name: "appname", index: "appname", width: 70, label: "申请人员", sortable: true, align: "left"},
						{name: "confirmname", index: "confirmname", width: 70, label: "审批人员", sortable: true, align: "left"},
						{name: "sendtypedescr", index: "sendtypedescr", width: 70, label: "发货类型", sortable: true, align: "left"},
						{name: "statusdescr", index: "statusdescr", width: 80, label: "领料单状态", sortable: true, align: "left"},
						{name: "splstatusdescr", index: "splstatusdescr", width: 80, label: "供应商状态", sortable: true, align: "left"},
						{name: "splremark", index: "splremark", width: 153, label: "供应商备注", sortable: true, align: "left"},
						{name: "amount", index: "amount", width: 70, label: "成本总价", sortable: true, align: "right", hidden: true},
						{name: "projectamount", index: "projectamount", width: 130, label: "项目经理结算总价", sortable: true, align: "right", hidden: true},
						{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
						{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
						{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"},
						
						{name: "type", index: "type", width: 100, label: "type", sortable: true, align: "left",hidden:true}
					]
				});
			});
			function unReceiveBySplSearch(){
				$("#unReceiveBySplDataTable").jqGrid("setGridParam",{
					postData:{
						itemType1:$("#unReceiveBySplItemType1").val(), 
						itemType2:$("#unReceiveBySplItemType2").val(), 
						confirmDateFrom:$("#confirmDateFrom").val(), 
						confirmDateTo:$("#confirmDateTo").val(),
						mainManCode:$("#unReceiveBySplMainManCode").val(), 
						supplCode:$("#unReceiveBySplSupplCode").val(),
						delivType:$("#delivType").val()
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
                        <select id="unReceiveBySplItemType1" name="unReceiveBySplItemType1"></select>
                    </li>
                    <li>
                        <label>材料类型2</label>
                        <select id="unReceiveBySplItemType2" name="unReceiveBySplItemType2"></select>
                    </li>
					<li>
						<label>供应商编号</label>
						<input type="text" id="unReceiveBySplSupplCode" />
					</li>
					<li>
						<label>审核日期</label>
						<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value="${data.confirmDateTo }" pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>主材管家</label>
						<input type="text" id="unReceiveBySplMainManCode"/>
					</li>
					<li>
						<label>配送方式</label>
						<house:xtdm id="delivType" dictCode="DELIVTYPE"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="unReceiveBySplSearch()">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div>
			<table id="unReceiveBySplDataTable"></table>
			<div id="unReceiveBySplDataTablePager"></div>
		</div>
	</body>
</html>

