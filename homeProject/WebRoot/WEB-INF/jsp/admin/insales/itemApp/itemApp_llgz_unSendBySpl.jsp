<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>供应商未发货领料单页面</title>
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
				$("#unSendBySplSupplCode").openComponent_supplier({
					condition:{
						itemRight:"${data.itemRightForSupplier}"
					}
				});
				$("#unSendBySplMainManCode").openComponent_employee();
				Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "unSendBySplItemType1", "unSendBySplItemType2")
				Global.JqGrid.initJqGrid("unSendBySplDataTable",{
					height:330,
					width:1200,
        			autowidth: false,
					url:"${ctx}/admin/itemApp/goUnSendBySplJqGrid",
					postData:{
						itemType1:$("#unSendBySplItemType1").val(),
						itemType2:$("#unSendBySplItemType2").val(),
						arriveDateFrom:$("#arriveDateFrom").val(),
						arriveDateTo:$("#arriveDateTo").val(),
						mainManCode:$("#unSendBySplMainManCode").val(),
						supplCode:$("#unSendBySplSupplCode").val(),
						region:$("#region").val(),
						prjRegion:$("#prjRegion").val(),
						delivType:$("#delivType1").val(),
						notifySendDateFrom:$("#notifySendDateFrom").val(),
						notifySendDateTo:$("#notifySendDateTo").val(),
					},
					styleUI:"Bootstrap",
					colModel:[
						{name: "no", index: "no", width: 75, label: "领料单号", sortable: true, align: "left"},
						{name: "address", index: "address", width: 210, label: "楼盘", sortable: true, align: "left"},
						{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
						{name: "splstatusdescr", index: "splstatusdescr", width: 80, label: "供应商状态", sortable: true, align: "left"},
						{name: "splremark", index: "splremark", width: 210, label: "供应商备注", sortable: true, align: "left"},
						{name: "confirmdate", index: "confirmdate", width: 110, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
						{name: "arrivedate", index: "arrivedate", width: 110, label: "预计到货时间", sortable: true, align: "left", formatter: formatTime},
						{name: "notifysenddate", index: "notifysenddate", width: 110, label: "通知发货日期", sortable: true, align: "left", formatter: formatTime},
						{name: "waitdays", index: "waitdays", width: 80, label: "等待天数", sortable: true, align: "right"},
						{name: "statusdescr", index: "statusdescr", width: 80, label: "领料单状态", sortable: true, align: "left"},
						{name: "issetitemdescr", index: "issetitemdescr", width: 73, label: "套餐材料", sortable: true, align: "left"},
						{name: "remarks", index: "remarks", width: 150, label: "备注", sortable: true, align: "left"},
						{name: "itemtype1descr", index: "itemtype1descr", width: 74, label: "材料类型1", sortable: true, align: "left"},
						{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
						{name: "custdescr", index: "custdescr", width: 70, label: "客户名称", sortable: true, align: "left"},
						{name: "projectman", index: "projectman", width: 70, label: "项目经理", sortable: true, align: "left"},
						{name: "phone", index: "phone", width: 72, label: "电话", sortable: true, align: "left"},
						{name: "mainmandescr", index: "mainmandescr", width: 70, label: "主材管家", sortable: true, align: "left"},
						{name: "typedescr", index: "typedescr", width: 70, label: "领料类型", sortable: true, align: "left"},
						{name: "itemtype2descr", index: "itemtype2descr", width: 74, label: "材料类型2", sortable: true, align: "left"},
						{name: "preappno", index: "preappno", width: 80, label: "预申请单号", sortable: true, align: "left"},
						{name: "sendtypedescr", index: "sendtypedescr", width: 70, label: "发货类型", sortable: true, align: "left"},
						{name: "delivtypedescr", index: "delivtypedescr", width: 70, label: "配送方式", sortable: true, align: "left"},
						{name: "amount", index: "amount", width: 70, label: "成本总价", sortable: true, align: "right", hidden: true},
						{name: "projectamount", index: "projectamount", width: 130, label: "项目经理结算总价", sortable: true, align: "right", hidden: true},
						{name: "followremark", index: "followremark", width: 124, label: "领料跟踪备注", sortable: true, align: "left"},
						{name: "appname", index: "appname", width: 70, label: "申请人员", sortable: true, align: "left"},
						{name: "date", index: "date", width: 100, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
						{name: "confirmname", index: "confirmname", width: 80, label: "审批人员", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
						{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
						{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"},				
						
						{name: "type", index: "type", width: 100, label: "type", sortable: true, align: "left",hidden:true}
					]
				});
			});
			function unSendBySplSearch(){
				$("#unSendBySplDataTable").jqGrid("setGridParam",{
					postData:{
						itemType1:$("#unSendBySplItemType1").val(),
						itemType2:$("#unSendBySplItemType2").val(),
						arriveDateFrom:$("#arriveDateFrom").val(),
						arriveDateTo:$("#arriveDateTo").val(),
						mainManCode:$("#unSendBySplMainManCode").val(),
						supplCode:$("#unSendBySplSupplCode").val(),
						region:$("#region").val(),
						prjRegion:$("#prjRegion").val(),
						delivType:$("#delivType1").val(),
						notifySendDateFrom:$("#notifySendDateFrom").val(),
						notifySendDateTo:$("#notifySendDateTo").val(),
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
                        <select id="unSendBySplItemType1" name="unSendBySplItemType1"></select>
                    </li>
                    <li>
                        <label>材料类型2</label>
                        <select id="unSendBySplItemType2" name="unSendBySplItemType2"></select>
                    </li>
					<li>
						<label>供应商编号</label>
						<input type="text" id="unSendBySplSupplCode"/>
					</li>
					<li>
						<label>计划发货时间</label>
						<input type="text" id="arriveDateFrom" name="arriveDateFrom" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="arriveDateTo" name="arriveDateTo" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value="${data.arriveDateTo }" pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>主材管家</label>
						<input type="text" id="unSendBySplMainManCode"/>
					</li>
					<li>
						<label>片区</label>
						<house:DictMulitSelect id="region" dictCode="" 
						sql="select code,descr from tRegion where 1=1 and expired='F' " sqlLableKey="Descr" sqlValueKey="Code"></house:DictMulitSelect>
					</li>
					<li>
						<label>工程大区</label>
						<house:dict id="prjRegion" dictCode="" sql="select  Code,code+' '+descr Descr from tPrjRegion where expired ='F' " 
						sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
					</li>
					<li>
						<label>配送方式</label>
						<house:xtdm id="delivType1" dictCode="DELIVTYPE"/>
					</li>
					<li>
						<label>通知发货日期</label>
						<input type="text" id="notifySendDateFrom" name="notifySendDateFrom" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="notifySendDateTo" name="notifySendDateTo" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system " onclick="unSendBySplSearch()">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div>
			<table id="unSendBySplDataTable"></table>
			<div id="unSendBySplDataTablePager"></div>
		</div>
	</body>
</html>

