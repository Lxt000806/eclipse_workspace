<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>ItemPreApp查询code</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>

		<script type="text/javascript">
			/**初始化表格*/
			$(function(){
				$("#custCode").openComponent_customer({
					condition:{
						status:"4,5",
						disabledEle:"status_NAME"
					}
				});
				
				Global.JqGrid.initJqGrid("codeDetailDataTable",{
					height:200,
					rowNum: 1000000,
					styleUI:"Bootstrap",
					colModel : [
						{name: "itemtype2descr", index: "itemtype2descr", width: 100, label: "材料类型2", sortable: true, align: "left"},
						{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
						{name: "itemdescr", index: "itemdescr", width: 260, label: "材料名称", sortable: true, align: "left"},
						{name: "suppldescr", index: "suppldescr", width: 170, label: "供应商", sortable: true, align: "left"},
						{name: "qty", index: "qty", width: 100, label: "数量", sortable: true, align: "left"},
						{name: "reqqty", index: "reqqty", width: 100, label: "需求数量", sortable: true, align: "left"},
						{name: "sendqty", index: "sendqty", width: 100, label: "已发货数量", sortable: true, align: "left"},
						{name: "fixareadescr", index: "fixareadescr", width: 136, label: "区域", sortable: true, align: "left"},
						{name: "intproddescr", index: "intproddescr", width: 100, label: "集成成品", sortable: true, align: "left"},
						{name: "remarks", index: "remarks", width: 173, label: "备注", sortable: true, align: "left"}		  
		            ]
				});
				
				Global.JqGrid.initJqGrid("codeDataTable",{
					height:220,
					rowNum: 1000000,
					styleUI:"Bootstrap",
					url:"${ctx}/admin/itemPreApp/goCodeJqGrid",
					postData:$("#page_form").jsonForm(),
					colModel : [
						{name: "no", index: "no", width: 109, label: "单号", sortable: true, align: "left"},
						{name: "custcode", index: "custcode", width: 88, label: "客户编号", sortable: true, align: "left"},
						{name: "custdescr", index: "custdescr", width: 86, label: "客户名称", sortable: true, align: "left"},
						{name: "address", index: "address", width: 149, label: "楼盘", sortable: true, align: "left"},
						{name: "itemtype1descr", index: "itemtype1descr", width: 104, label: "材料类型1", sortable: true, align: "left"},
						{name: "issetitemdescr", index: "issetitemdescr", width: 93, label: "套餐材料", sortable: true, align: "left"},
						{name: "statusdescr", index: "statusdescr", width: 102, label: "状态", sortable: true, align: "left"},
						{name: "appczydescr", index: "appczydescr", width: 90, label: "申请人员", sortable: true, align: "left"},
						{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
						{name: "confirmczydescr", index: "confirmczydescr", width: 90, label: "审核人员", sortable: true, align: "left"},
						{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
						{name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left"}			  
		            ],
		            gridComplete:function(){
		            	var ret = selectDataTableRow("codeDataTable");
		            	if(ret){
		            		searchCodeDetail(ret.no.trim());
		            	}
		            },
					ondblClickRow:function(rowid,iRow,iCol,e){
						var selectRow = $("#codeDataTable").jqGrid("getRowData", rowid);
		            	Global.Dialog.returnData = selectRow;
		            	Global.Dialog.closeDialog();
		            },
		            onSelectRow:function(rowid,status){
		            	var ret = selectDataTableRow("codeDataTable");
		            	if(ret){
		            		searchCodeDetail(ret.no.trim());
		            	}
		            }
				});
			});
			function search(){
				$("#codeDataTable").jqGrid("setGridParam",{
					url:"${ctx}/admin/itemPreApp/goCodeJqGrid",
					postData:$("#page_form").jsonForm(),
					page:1,
					sortname:""
				}).trigger("reloadGrid");
			}
			function searchCodeDetail(no){	
				$("#codeDetailDataTable").jqGrid("setGridParam",{
					url:"${ctx}/admin/itemPreApp/goCodeDetailJqGrid",
					postData:{no:no},
					page:1,
					sortname:""
				}).trigger("reloadGrid");
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="requestPage" name="requestPage" value="${data.requestPage }" />
					<ul class="ul-form">
						<li>
							<label>单号</label>
							<input type="text" id="no" name="no" value="${data.no}" />	
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" value="${data.custCode}" />
						</li>
						<li>
							<label>材料类型1</label>
							<house:dict id="itemType1" dictCode="" 
										sql="select cbm,note from tXTDM  where id='ITEMRIGHT' and cbm in ${data.itemRight } order by IBM ASC " 
										sqlValueKey="cbm" sqlLableKey="note" value="${data.itemType1}" >
							</house:dict>
						</li>
						<li>
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="PREAPPSTATUS" selectedValue="${data.status }" unShowValue="${data.unShowStatus }"></house:xtdmMulit>
						</li>
						<li>
							<label>申请日期从</label>
							<input type="text" id="appDateFrom" name="appDateFrom" class="i-date" 
								   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value="${data.appDateFrom }" pattern="yyyy-MM-dd"/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="appDateTo" name="appDateTo" class="i-date" 
								   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value="${data.appDateTo }" pattern="yyyy-MM-dd"/>"/>
						</li>
						<li>
							<button type="button" class="btn  btn-sm btn-system " onclick="search();" >查询</button>
						</li>
					</ul>
				</form>
			</div>
			
			<div class="pageContent">
				<div id="content-list">
					<table id="codeDataTable"></table>
					<table id="codeDetailDataTable"></table>
				</div>
			</div> 
		</div>
	</body>
</html>


