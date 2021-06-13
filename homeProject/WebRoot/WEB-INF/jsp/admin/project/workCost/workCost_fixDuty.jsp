<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html lang="zh">
<head>
	<title>工人定责管理——明细查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/workCost/goFixDutyJqGrid",
				postData:{keys: "${keys}"},
				height:$(document).height()-$("#content-list").offset().top-75,
				styleUI: "Bootstrap", 
				sortable: true,
				multiselect: true,
				rowNum:10000000,
				colModel : [
					{name: "fdno", index: "fdno", width: 88, label: "定责申请单号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 149, label: "楼盘", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 96, label: "客户编号", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 96, label: "客户类型", sortable: true, align: "left", hidden: true},
					{name: "worktype12", index: "worktype12", width: 70, label: "工种", sortable: true, align: "left", hidden: true},
					{name: "worktype12descr", index: "worktype12descr", width: 70, label: "工种", sortable: true, align: "left"},
					{name: "appworkercode", index: "appworkercode", width: 75, label: "工人", sortable: true, align: "left", hidden: true},
					{name: "appworkdescr", index: "appworkdescr", width: 75, label: "工人", sortable: true, align: "left"},
					{name: "cardid", index: "cardid", width: 149, label: "工人卡号", sortable: true, align: "left"},
					{name: "cardid2", index: "cardid2", width: 149, label: "工人卡号2", sortable: true, align: "left", hidden: true},
					{name: "actname", index: "actname", width: 90, label: "持卡人姓名", sortable: true, align: "left", hidden: true},
					{name: "dutyman", index: "dutyman", width: 80, label: "判责人", sortable: true, align: "left", hidden: true},
					{name: "dutymandescr", index: "dutymandescr", width: 80, label: "判责人", sortable: true, align: "left"},
					{name: "dutydate", index: "dutydate", width: 120, label: "判责时间", sortable: true, align: "left", formatter: formatTime},
					{name: "faulttype", index: "faulttype", width: 100, label: "责任人类型", sortable: true, align: "left", hidden: true},
					{name: "faulttypedescr", index: "faulttypedescr", width: 100, label: "责任人类型", sortable: true, align: "left"},
					{name: "appamount", index: "appamount", width: 80, label: "金额", sortable: true, align: "right"},
					{name: "empcode", index: "empcode", width: 80, label: "员工姓名", sortable: true, align: "left", hidden: true},
					{name: "empdescr", index: "empdescr", width: 80, label: "员工姓名", sortable: true, align: "left"},
					{name: "position", index: "position", width: 80, label: "职位类型", sortable: true, align: "left"},
					{name: "workercode", index: "workercode", width: 80, label: "工人姓名", sortable: true, align: "left", hidden: true},
					{name: "workdescr", index: "workdescr", width: 80, label: "工人姓名", sortable: true, align: "left"},
					{name: "supplcode", index: "supplcode", width: 80, label: "供应商", sortable: true, align: "left", hidden: true},
					{name: "suppldescr", index: "suppldescr", width: 80, label: "供应商", sortable: true, align: "left"},
					{name: "issalary", index: "issalary", width: 80, label: "正常工资", sortable: true, align: "left", hidden: true},
					{name: "issalarydescr", index: "issalarydescr", width: 80, label: "正常工资", sortable: true, align: "left"},
					{name: "applyman", index: "applyman", width: 80, label: "申请人", sortable: true, align: "left", hidden: true},
					{name: "applymandescr", index: "applymandescr", width: 80, label: "申请人", sortable: true, align: "left", hidden: true},
					{name: "iswithhold", index: "iswithhold", width: 80, label: "是否预扣", sortable: true, align: "left", hidden: true},
					{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "isconfirm", index: "isconfirm", width: 80, label: "审核标志", sortable: true, align: "left", hidden: true},
					{name: "reffixdutymanpk", index: "reffixdutymanpk", width: 80, label: "关联定责人员PK", sortable: true, align: "left", hidden: true},
					{name: "salarytype", index: "salarytype", width: 80, label: "工资类型", sortable: true, align: "left", hidden: true},
					{name: "worktype1", index: "worktype1", width: 80, label: "工种类型1", sortable: true, align: "left", hidden: true},
					{name: "worktype2", index: "worktype2", width: 80, label: "工种类型2", sortable: true, align: "left", hidden: true},
					{name: "custdescr", index: "custdescr", width: 80, label: "客户名称", sortable: true, align: "left", hidden: true},
					{name: "documentno", index: "documentno", width: 80, label: "档案编号", sortable: true, align: "left", hidden: true},
					{name: "custstatus", index: "custstatus", width: 80, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "statusdescr", index: "statusdescr", width: 80, label: "状态描述", sortable: true, align: "left", hidden: true},
					{name: "signdate", index: "signdate", width: 80, label: "签约日期", sortable: true, align: "left", hidden: true, formatter: formatDate},
					{name: "checkstatus", index: "checkstatus", width: 80, label: "结算状态", sortable: true, align: "left", hidden: true},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 80, label: "结算状态", sortable: true, align: "left", hidden: true},
					{name: "salarytypedescr", index: "salarytypedescr", width: 80, label: "工资类型", sortable: true, align: "left", hidden: true},
					{name: "worktype1descr", index: "worktype1descr", width: 80, label: "工种类型1", sortable: true, align: "left", hidden: true},
					{name: "worktype2descr", index: "worktype2descr", width: 80, label: "工种类型2", sortable: true, align: "left", hidden: true},
					{name: "issigndescr", index: "issigndescr", width: 80, label: "签约类型", sortable: true, align: "left", hidden: true},
					{name: "iswithholddescr", index: "iswithholddescr", width: 80, label: "是否预扣", sortable: true, align: "left", hidden: true},
					{name: "iswateritemctrl", index: "iswateritemctrl", width: 80, label: "水电发包工人", sortable: true, align: "left", hidden: true},
					{name: "iswateritemctrldescr", index: "iswateritemctrldescr", width: 80, label: "水电发包工人", sortable: true, align: "left", hidden: true},
					{name: "idnum", index: "idnum", width: 80, label: "身份证", sortable: true, align: "left", hidden: true},
					{name: "qualityfeebegin", index: "qualityfeebegin", width: 80, label: "质保金起始金额", sortable: true, align: "left", hidden: true},
					{name: "remarks", index: "remarks", width: 135, label: "请款说明", sortable: true, align: "left", hidden: true},
				],
				/*onSelectRow: function (rowid, status) {
					if (status) {
						var rowData = $("#dataTable").jqGrid("getRowData", rowid);
						$.ajax({
							url:'${ctx}/admin/workCostDetail/findPrjByCodeWork',
							type: 'post',
							data: {'custCode':rowData.custcode,'workType2':rowData.worktype2},
							dataType: 'json',
							cache: false,
							async: false,
							error: function(obj){
								showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
							},
							success: function(obj){
								rowData.prjitem = obj.PrjItem!=null?obj.PrjItem:"";
								rowData.enddate = obj.EndDate!=null?new Date(parseInt(obj.EndDate)).format("yyyy-MM-dd hh:mm:ss"):"";
								rowData.confirmdate = obj.ConfirmDate!=null?new Date(parseInt(obj.ConfirmDate)).format("yyyy-MM-dd hh:mm:ss"):"";
								rowData.confirmczydescr = obj.ConfirmCZYDescr!=null?obj.ConfirmCZYDescr:"";
								rowData.totalamount = obj.Amount!=null?obj.Amount:"0";
								rowData.gotamount = obj.ConfirmAmount!=null?obj.ConfirmAmount:"0";
								rowData.workerplanoffer = obj.WorkerPlanOffer==undefined?"0":obj.WorkerPlanOffer;
							}
						});
						$.ajax({
							url:"${ctx}/admin/workCostDetail/findCostByCodeWork",
							type: "post",
							data: {"custCode":rowData.custcode,"workType2":rowData.worktype2},
							dataType: "json",
							cache: false,
							async: false,
							error: function(obj){
								showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
							},
							success: function(obj){
								rowData.allcustctrl = obj.AllCustCtrl;
								rowData.allcustcost = obj.AllCustCost;
								rowData.allleavecustcost = obj.AllLeaveCustCost;
								if(rowData.worktype2!=""){
									rowData.custctrl = obj.CustCtrl;
									rowData.custctrl_kzx = (parseFloat(obj.CustCtrl)*1.1).toFixed(2);//保留两位小数
									rowData.custcost = obj.CustCost;
									rowData.leavecustcost = obj.LeaveCustCost;
								}
							}
						});
						selectRows.push(rowData);
					} else {
						$.each(selectRows,function(i, val){
							var rowData = $("#dataTable").jqGrid("getRowData", rowid);
							if (rowData.reffixdutymanpk == val.reffixdutymanpk) {
								selectRows.splice(i, 1);
								return false;
							}
						});
					}
				},*/
				loadComplete: function(){
					// $("#jqgh_dataTable_cb").remove();
				},
			});
			$("#saveBtn").on("click", function () {
				var ids=$("#dataTable").jqGrid("getGridParam", "selarrrow");
				if(ids.length == 0){
					art.dialog({
						content:"请选择数据后保存"
					});				
					return ;
				}
				$.each(ids,function(k,id){
					var row = $("#dataTable").jqGrid("getRowData", id);
					selectRows.push(row);
				});
				art.dialog({content: "添加成功！",width: 200,time:800});
				Global.Dialog.returnData = selectRows;
				closeWin();
			});
		});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-sm btn-system" id="saveBtn" title="保存">保存</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
